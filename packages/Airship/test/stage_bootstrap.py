#!/usr/bin/env python3
"""Offline, fail-closed stage0/1/2 compiler acceptance (not a sandbox).

Usage: python3 -B stage_bootstrap.py --repo CHECKOUT --output NEW_DIRECTORY
                                     --seeds SEEDS_JSON --review REVIEW_JSON
                                     --review-sha256 INDEPENDENT_EXPECTED_SHA256

SEEDS_JSON must contain {"path": absolute_path, "sha256": expected_digest}
for node, git, java, airship, java_compiler, java_backend. Tools must be
executables. Airship is an explicitly selected Java-capable bootstrap JS;
java_compiler and java_backend are separately provisioned flatc.jar and
flat-es6.jar. This program never downloads, builds Maven, or trusts local dist.
An independently reviewed seed manifest is the trust root, not a manifest
synthesized from whatever artifacts happen to be present.
REVIEW_JSON schema: {"schema": 1, "seeds_sha256": "...", "reviewer": "...",
"provenance": "producer receipt locator/identity", "source_revision": "..."}.
Its expected hash must come from the reviewer/protected provisioning context;
this harness cannot establish that a self-asserted reviewer is trustworthy.
Default limits: one hour aggregate, ten minutes/command, 2 GiB run output,
256 MiB source snapshot. Explicit local experiments may select up to three hours
aggregate and one hour/command. Storage is a sampled watchdog, not an OS disk quota.

Every stage uses a new source snapshot, FLAT_HOME, HOME, and log directory.
Only stage0 has Java jars. Stage1/2 select a verified read-only previous-stage
compiler explicitly through Airship's --flatc-script option. Compiler inputs
never alias the output being built; the guard verifies identity without rewriting
compiler argv. All subprocesses are serial and audited.
Any failed/missing gate fails the run; success requires stage1/2 byte equality.
Source comparisons use relative manifest keys and exact content hashes; only
known generated/cache directories are excluded (EXCLUDED below). Captured local
origin is required and restricted to credential-free FlatLang GitHub URLs. Each
snapshot has standalone minimal .git metadata (not primary-worktree pointers);
its HEAD/config hashes are recorded and checked separately from package sources. Artifact bytes
are never normalized, including embedded absolute paths. Such differences fail.

Limits: trusted JavaScript can bypass hooks using native addons, process.binding,
VM contexts, or by modifying the guard/policy/journal. Stage0 JVM/native tool
internals are not intercepted. Hooks are not filesystem confinement or an OS
network/process sandbox. Provision OS isolation separately for untrusted inputs.
Shell requests are converted to direct argv only for the documented forms;
unsupported forms fail, rather than claiming shell:true itself is contained.
A seed Airship lacking --debug or printing a different lookup contract fails
preflight; no seed lookup is inferred from mocks or substituted compiler argv.
"""
import argparse
import shlex
import hashlib
import json
import os
from pathlib import Path
import shutil
import re
import signal
import subprocess
import select
import sys
import tempfile
import time
import secrets


class ScopeChannel:
    """Per-command inherited pipes; <=4096 atomic bounded frames per command."""
    def __init__(self, env):
        policy = json.loads(Path(env['FLAT_BOOTSTRAP_POLICY']).read_text())
        fixtures = policy.get('fixtures', [])
        if not isinstance(fixtures, list) or len(fixtures) > 128:
            raise ValueError('Invalid fixture declaration capacity')
        self.expected = {entry['id']: entry for entry in fixtures}
        if len(self.expected) != len(fixtures):
            raise ValueError('Duplicate fixture declaration')
        for entry in fixtures:
            if type(entry.get('count')) is not int or not 0 < entry['count'] <= 128:
                raise ValueError('Invalid fixture invocation count')
        self.read, self.write = os.pipe()
        self.ack_read, self.ack_write = os.pipe()
        os.set_blocking(self.read, False)
        os.set_blocking(self.ack_read, False)
        os.set_blocking(self.ack_write, False)
        os.set_blocking(self.write, False)
        self.scope = secrets.token_hex(16)
        self.buffer = b''
        self.frames = 0
        self.events = []
        self.cancelled = False
        self.hellos = 0
        self.started = {}
        self.finished = set()
        self.counts = {key: 0 for key in self.expected}
        self.env = dict(env)
        self.env['FLAT_PROCESS_SCOPE'] = json.dumps({
            'id': self.scope, 'requestFd': self.write, 'ackFd': self.ack_read,
            'policySha256': digest(Path(env['FLAT_BOOTSTRAP_POLICY']))})

    def drain(self):
        while True:
            try:
                block = os.read(self.read, 4096)
            except BlockingIOError:
                break
            if not block:
                break
            self.buffer += block
            if len(self.buffer) > 8192:
                raise ValueError('Process scope frame capacity exceeded')
            while b'\n' in self.buffer:
                line, self.buffer = self.buffer.split(b'\n', 1)
                self.frames += 1
                if len(line) > 1024 or self.frames > 4096:
                    raise ValueError('Process scope frame capacity exceeded')
                frame = json.loads(line)
                self.events.append(frame)
                if (frame.get('scope') != self.scope or frame.get('kind') not in
                        ('hello', 'cancel', 'fixture-start', 'fixture-end')
                        or type(frame.get('pid')) is not int or frame['pid'] <= 0):
                    raise ValueError('Invalid process scope frame')
                if frame['kind'] == 'hello':
                    self.hellos += 1
                    os.write(self.ack_write, b'A')
                elif frame['kind'] == 'cancel':
                    self.cancelled = True
                else:
                    identity = (frame['pid'], frame.get('invocation'))
                    key = frame.get('fixture')
                    if key not in self.expected or type(identity[1]) is not int:
                        raise ValueError('Unknown fixture outcome identity')
                    if frame['kind'] == 'fixture-start':
                        if identity in self.started:
                            raise ValueError('Duplicate fixture invocation')
                        self.started[identity] = key
                        self.counts[key] += 1
                        if self.counts[key] > self.expected[key]['count']:
                            raise ValueError('Extra fixture invocation')
                    else:
                        if self.started.get(identity) != key or identity in self.finished:
                            raise ValueError('Unmatched fixture terminal outcome')
                        self.finished.add(identity)
                        if frame.get('outcome') != self.expected[key]['outcome']:
                            raise ValueError('Unexpected fixture terminal outcome')
        if self.cancelled:
            raise ValueError('Process scope cancellation requested')

    def close(self):
        for fd in (self.read, self.write, self.ack_read, self.ack_write):
            os.close(fd)


def process_identity(pid):
    """Kernel start identity; never infer ownership from a PID or command text."""
    if sys.platform.startswith('linux'):
        fields = Path(f'/proc/{pid}/stat').read_text().rsplit(')', 1)[1].split()
        return {'pid': pid, 'pgid': int(fields[2]), 'session': int(fields[3]),
                'start': fields[19], 'state': fields[0], 'source': 'proc-stat'}
    if sys.platform == 'darwin':
        import ctypes
        class BSDInfo(ctypes.Structure):
            _fields_ = ([(name, ctypes.c_uint32) for name in (
                'flags', 'status', 'xstatus', 'pid', 'ppid', 'uid', 'gid', 'ruid',
                'rgid', 'svuid', 'svgid', 'rfu')] +
                [('comm', ctypes.c_char * 16), ('name', ctypes.c_char * 32)] +
                [(name, ctypes.c_uint32) for name in (
                    'nfiles', 'pgid', 'pjobc', 'e_tdev', 'e_tpgid')] +
                [('nice', ctypes.c_int32), ('start_sec', ctypes.c_uint64),
                 ('start_usec', ctypes.c_uint64)])
        info = BSDInfo()
        libproc = ctypes.CDLL('/usr/lib/libproc.dylib', use_errno=True)
        libproc.proc_pidinfo.argtypes = [ctypes.c_int, ctypes.c_int, ctypes.c_uint64,
                                        ctypes.c_void_p, ctypes.c_int]
        libproc.proc_pidinfo.restype = ctypes.c_int
        size = libproc.proc_pidinfo(pid, 3, 0, ctypes.byref(info), ctypes.sizeof(info))
        if size != ctypes.sizeof(info) or info.pid != pid:
            raise OSError(ctypes.get_errno(), 'Cannot obtain kernel process identity')
        return {'pid': pid, 'pgid': info.pgid, 'session': os.getsid(pid),
                'start': f'{info.start_sec}:{info.start_usec}',
                'state': 'Z' if info.status == 5 else 'running', 'source': 'proc-pidinfo'}
    raise OSError('Kernel process start identity unsupported on this platform')


def unreaped_child_finished(pid):
    """Observe our exited child without releasing its kernel PID reservation.

    Darwin libproc stops reporting BSD identity for zombies. waitid WNOWAIT
    proves this is still our unreaped direct child, not a recycled PID.
    """
    if sys.platform != 'darwin':
        return False
    import ctypes
    libc = ctypes.CDLL(None, use_errno=True)
    info = (ctypes.c_int * 32)()
    libc.waitid.argtypes = [ctypes.c_int, ctypes.c_uint, ctypes.c_void_p, ctypes.c_int]
    libc.waitid.restype = ctypes.c_int
    # Darwin P_PID=1, WNOHANG=1, WEXITED=4, WNOWAIT=0x20.
    result = libc.waitid(1, pid, ctypes.byref(info), 1 | 4 | 0x20)
    if result != 0:
        raise OSError(ctypes.get_errno(), 'Cannot observe unreaped child')
    return info[0] == signal.SIGCHLD and info[3] == pid and info[2] in (1, 2, 3)


def owned_process_identity(process):
    expected = getattr(process, '_stage_identity', None)
    if expected is None or process.returncode is not None:
        return False
    if expected.get('source') == 'proc-pidinfo' and unreaped_child_finished(process.pid):
        return True
    try:
        actual = process_identity(process.pid)
    except ProcessLookupError:
        # Only WNOWAIT evidence for this direct child permits use of its pinned
        # spawn identity after libproc stops exposing zombie metadata.
        return unreaped_child_finished(process.pid)
    matched = all(actual[key] == expected[key] for key in (
        'pid', 'pgid', 'session', 'start', 'source')) and actual['pgid'] == actual['session'] == process.pid
    if not matched:
        process._stage_identity_observation = {'expected': dict(expected), 'observed': actual, 'error': None}
    return matched


def process_finished(process):
    """Observe completion without reaping or combining separate identity samples.

    Darwin can drop BSD/session metadata between the WNOWAIT probe and the
    libproc read. Only a later WNOWAIT result for this still-unreaped direct
    child resolves that transition; a live mismatching identity is never owned.
    """
    expected = getattr(process, '_stage_identity', None)
    if expected is None or process.returncode is not None:
        raise ValueError('Process scope identity unavailable or changed')
    keys = ('pid', 'pgid', 'session', 'start', 'source')
    darwin = expected.get('source') == 'proc-pidinfo'
    attempts = 6 if darwin else 1
    for attempt in range(attempts):
        if darwin and unreaped_child_finished(process.pid):
            return True
        actual, failure = None, None
        try:
            actual = process_identity(process.pid)
        except OSError as error:
            failure = {'type': type(error).__name__, 'errno': error.errno, 'message': str(error)}
        if actual is not None and all(actual[key] == expected[key] for key in keys):
            return actual['state'] == 'Z'
        process._stage_identity_observation = {'expected': dict(expected), 'observed': actual, 'error': failure}
        # Recheck after the identity read, not before it alone. No signal or
        # reap occurs while the kernel is transitioning the child to terminal.
        if darwin and unreaped_child_finished(process.pid):
            return True
        if attempt + 1 < attempts:
            time.sleep(0.01)
    raise ValueError('Process scope identity unavailable or changed: '
                     + json.dumps(process._stage_identity_observation, sort_keys=True))


def durable_process_receipt(path, value):
    """Exclusive receipts: a previous unknown observation is never rewritten."""
    with Path(path).open('x') as target:
        json.dump(value, target, sort_keys=True)
        target.write('\n')
        target.flush()
        os.fsync(target.fileno())
    directory = os.open(str(Path(path).parent), os.O_RDONLY)
    try:
        os.fsync(directory)
    finally:
        os.close(directory)


def settle_process_group(process, observation=None):
    """Bounded finalization; signals require a still-pinned spawn identity."""
    observation = observation if observation is not None else {}
    observation.update(pid=process.pid, identity=getattr(process, '_stage_identity', None),
                       status='cleanup-unknown', cleanup_unknown=True,
                       wait_completed=False, child_reaped=False,
                       process_group_absent=False, errors=[])
    errors = observation['errors']
    pgid = (getattr(process, '_stage_identity', None) or {}).get('pgid')
    def present():
        if pgid is None:
            return True
        try:
            os.killpg(pgid, 0)
            return True
        except ProcessLookupError:
            return False
        except OSError as error:
            probes = observation.setdefault('group_probe_errors', [])
            if len(probes) < 8:
                probes.append(str(error))
            return True
    def deliver(signum):
        try:
            if not owned_process_identity(process):
                errors.append('Signal withheld: spawn identity unavailable or changed')
                return
            os.killpg(pgid, signum)
        except ProcessLookupError:
            pass
        except (OSError, ValueError) as error:
            errors.append(f'Signal {signum} unconfirmed: {error}')
    try:
        # A naturally exited child still reserves its PID until wait. Reap it
        # before probing Darwin groups: a zombie-only group can return EPERM.
        # Do not turn that error into absence, and never signal after reaping.
        finished = False
        try:
            finished = process_finished(process)
        except (OSError, ValueError) as error:
            errors.append(f'completion identity unconfirmed: {error}')
        if finished:
            # A live descendant keeps the group signalable even after leader
            # exit. While the unreaped leader still pins PGID, settle those
            # descendants. EPERM here is not absence: defer to post-wait probe.
            try:
                os.killpg(pgid, 0)
            except ProcessLookupError:
                pass
            except PermissionError:
                pass
            else:
                deliver(signal.SIGTERM)
                deadline = time.monotonic() + 2
                while time.monotonic() < deadline:
                    try:
                        os.killpg(pgid, 0)
                    except (ProcessLookupError, PermissionError):
                        break
                    time.sleep(0.01)
                else:
                    deliver(signal.SIGKILL)
        if not finished:
            deliver(signal.SIGTERM)
            deadline = time.monotonic() + 2
            while time.monotonic() < deadline:
                try:
                    if process_finished(process):
                        finished = True
                        break
                except (OSError, ValueError) as error:
                    # Kernel exit metadata can disappear before waitid exposes
                    # the terminal child. Retry within the same finite grace;
                    # final wait plus group absence still owns the verdict.
                    if (getattr(process, '_stage_identity', None) or {}).get('source') != 'proc-pidinfo':
                        errors.append(f'completion identity unconfirmed: {error}')
                        break
                time.sleep(0.01)
            if not finished:
                if not finished:
                    deliver(signal.SIGKILL)
            if finished:
                # Retain leader ownership until surviving descendants are killed.
                try:
                    os.killpg(pgid, 0)
                except (ProcessLookupError, PermissionError):
                    pass
                else:
                    deliver(signal.SIGKILL)
    finally:
        # Even permission/identity failures must attempt a finite reap and leave
        # explicit non-success evidence. Never signal after this wait reaps PID.
        try:
            process.wait(timeout=2)
            observation.update(wait_completed=True, child_reaped=True)
            if isinstance(getattr(process, '_stage_identity_observation', None), dict):
                observation['completion_identity_observation'] = process._stage_identity_observation
        except (subprocess.TimeoutExpired, OSError) as error:
            errors.append(f'wait unconfirmed: {error}')
        deadline = time.monotonic() + 2
        while present() and time.monotonic() < deadline:
            time.sleep(0.01)
        absent = not present()
        observation.update(exit_code=process.returncode, process_group_absent=absent)
        if absent and observation['child_reaped'] and pgid is not None and not errors:
            observation.update(status='settled', cleanup_unknown=False)
    if observation['status'] != 'settled':
        raise ValueError('Process scope cleanup-unknown: owned group absence/reap unconfirmed')

HERE = Path(__file__).resolve().parent
PRODUCTS = ("packages/Flat-Compiler/dist/es6/index.js", "packages/Airship/dist/airship.js",
            "packages/Flat-Compiler/dist/es6/production-driver.js")
# README acceptance set; all are mandatory, never conditional on availability.
REQUIRED_SUITES = ('Airship', 'Flat-Project-Loading', 'Flat-Parser', 'Flat-Name-Binding',
                   'Flat-Bound-IR', 'Flat-ES6-Bound-Writer', 'Flat-Compiler')
EXCLUDED = {".git", "dist", "target", "node_modules", "__pycache__"}
MAX_FILES = 100000
MAX_SNAPSHOT_BYTES = 256 * 1024 * 1024


def execution_limits(total, command):
    if not (0 < command <= 3600 and command <= total <= 10800):
        raise ValueError('Budgets require 0 < command <= 3600 and command <= run <= 10800 seconds')
    return total, command


class RunBudget:
    """Aggregate trusted-build watchdog, including snapshots, logs and outputs.

    Sampling cannot impose a hard filesystem quota: one child write may overshoot
    before the next poll. Use an OS quota for a strict allocation ceiling.
    """
    def __init__(self, root, seconds=3600, max_bytes=2 * 1024**3):
        if seconds <= 0 or max_bytes <= 0:
            raise ValueError('Run budgets must be positive')
        self.root, self.max_bytes = root, max_bytes
        self.deadline = time.monotonic() + seconds

    def check(self, storage=True):
        if time.monotonic() >= self.deadline:
            raise ValueError('Aggregate run deadline exceeded')
        total = 0
        if storage and self.root.exists():
            for directory, dirs, files in os.walk(self.root, followlinks=False):
                dirs[:] = [name for name in dirs if not (Path(directory) / name).is_symlink()]
                for name in files:
                    path = Path(directory) / name
                    if not path.is_symlink():
                        total += path.stat().st_size
                        if total > self.max_bytes:
                            raise ValueError('Aggregate run storage capacity exceeded')
                if time.monotonic() >= self.deadline:
                    raise ValueError('Aggregate run deadline exceeded')


def reviewed_json(path, expected_sha256, label):
    """Hash and parse the same bounded bytes; reject ambiguous duplicate keys."""
    with Path(path).open('rb') as source:
        raw = source.read(1024 * 1024 + 1)
    if len(raw) > 1024 * 1024:
        raise ValueError(f'{label} exceeds receipt capacity')
    if not expected_sha256 or hashlib.sha256(raw).hexdigest() != expected_sha256:
        raise ValueError(f'{label} digest mismatch')
    def unique_object(pairs):
        value = {}
        for key, entry in pairs:
            if key in value:
                raise ValueError(f'{label} contains duplicate key: {key}')
            value[key] = entry
        return value
    def finite_constant(value):
        raise ValueError(f'{label} contains non-finite number: {value}')
    return json.loads(raw, object_pairs_hook=unique_object, parse_constant=finite_constant)


def reviewed_seeds(seeds, review, expected_sha256):
    value = reviewed_json(review, expected_sha256, 'Review receipt')
    if (not isinstance(value, dict) or
            set(value) != {'schema', 'seeds_sha256', 'reviewer', 'provenance', 'source_revision'} or
            type(value['schema']) is not int or value['schema'] != 1):
        raise ValueError('Unsupported seed review receipt schema')
    if value['seeds_sha256'] != digest(seeds):
        raise ValueError('Reviewed seed manifest mismatch')
    if any(not isinstance(value[key], str) or not value[key].strip()
           for key in ('reviewer', 'provenance', 'source_revision')):
        raise ValueError('Review requires reviewer, provenance and producer source revision')
    return value


SEED_NAMES = {'node', 'git', 'java', 'airship', 'java_compiler', 'java_backend'}


def load_reviewed_bundle(seeds_file, review_file, review_sha256):
    """Validate an independently provisioned bundle; never create its trust root."""
    review = reviewed_seeds(seeds_file, review_file, review_sha256)
    entries = reviewed_json(seeds_file, review['seeds_sha256'], 'Reviewed seed manifest')
    if not isinstance(entries, dict) or set(entries) != SEED_NAMES:
        raise ValueError('--seeds requires exactly: ' + ', '.join(sorted(SEED_NAMES)))
    seeds = {name: (verified_seed(entries[name]), entries[name]['sha256'])
             for name in sorted(SEED_NAMES)}
    for name in ('node', 'git', 'java'):
        if not os.access(seeds[name][0], os.X_OK):
            raise ValueError(f'{name} must be executable')
    return entries, review, seeds


def source_revision(repo, git):
    result = subprocess.run([str(git), '-C', str(repo), 'rev-parse', '--verify', 'HEAD'],
                            capture_output=True, text=True, check=True, timeout=10)
    revision = result.stdout.strip()
    if len(revision) != 40 or any(char not in '0123456789abcdef' for char in revision):
        raise ValueError('Invalid source Git revision')
    return revision


def validate_origin(origin):
    # Metadata-only identity, never a transport instruction. Restrict to the
    # workspace's public FlatLang namespace; reject credentials/config injection.
    if not isinstance(origin, str) or not re.fullmatch(
            r'(?:git@github\.com:|https://github\.com/)FlatLang/[A-Za-z0-9_-]+(?:\.git)?', origin):
        raise ValueError('Missing or unsupported source origin; require credential-free FlatLang GitHub origin')
    return origin


def capture_origin(repo, git):
    try:
        result = subprocess.run([str(git), '-C', str(repo), 'config', '--local', '--get',
                                 'remote.origin.url'], capture_output=True, text=True,
                                check=True, timeout=10)
    except subprocess.CalledProcessError as error:
        raise ValueError('Source local origin is required') from error
    # Remove exactly the CLI record terminator, not arbitrary whitespace/content.
    return validate_origin(result.stdout.removesuffix('\n'))


def snapshot_origin_manifest(workspace):
    return {'.git/' + name: digest(workspace / '.git' / name)
            for name in ('HEAD', 'config')}


def install_snapshot_origin(workspace, origin):
    origin = validate_origin(origin)
    metadata = workspace / '.git'
    metadata.mkdir(exist_ok=False)
    (metadata / 'objects').mkdir()
    (metadata / 'refs/heads').mkdir(parents=True)
    (metadata / 'HEAD').write_text('ref: refs/heads/bootstrap-snapshot\n')
    (metadata / 'config').write_text('[core]\n\trepositoryformatversion = 0\n\tbare = false\n'
                                   '[remote "origin"]\n\turl = "' + origin + '"\n')
    for name in ('HEAD', 'config'):
        (metadata / name).chmod(0o444)
    return snapshot_origin_manifest(workspace)


def source_provenance(repo, git):
    revision = source_revision(repo, git)
    result = subprocess.run([str(git), '-C', str(repo), 'status', '--porcelain=v1', '-z',
                             '--untracked-files=all'], capture_output=True, text=True,
                            check=True, timeout=10)
    return {'baseline_revision': revision, 'dirty': bool(result.stdout),
            'status_porcelain_v1_z': result.stdout,
            'status_sha256': hashlib.sha256(result.stdout.encode()).hexdigest()}


def digest(path):
    value = hashlib.sha256()
    with Path(path).open("rb") as source:
        for block in iter(lambda: source.read(1024 * 1024), b""):
            value.update(block)
    return value.hexdigest()


def verified_seed(entry):
    if (not isinstance(entry, dict) or set(entry) != {'path', 'sha256'} or
            not isinstance(entry['path'], str) or not isinstance(entry['sha256'], str) or
            len(entry['sha256']) != 64 or any(char not in '0123456789abcdef' for char in entry['sha256'])):
        raise ValueError('Seed entry requires exactly absolute path and lowercase SHA-256')
    path = Path(entry["path"])
    if not path.is_absolute() or not path.is_file() or path.stat().st_size == 0:
        raise ValueError(f"Seed must be a nonempty absolute file: {path}")
    path = path.resolve()
    if digest(path) != entry["sha256"]:
        raise ValueError(f"Seed digest mismatch: {path}")
    return path


def fresh_directory(path):
    Path(path).mkdir(parents=True, exist_ok=False)


def inventory(root, max_bytes=MAX_SNAPSHOT_BYTES, budget=None):
    """Finite metadata manifest; file contents are hashed in streaming blocks."""
    result = {}
    total_bytes = 0
    for directory, directories, files in os.walk(root, followlinks=False):
        if budget:
            budget.check(storage=False)
        directories[:] = sorted(name for name in directories if name not in EXCLUDED)
        for name in directories + sorted(files):
            path = Path(directory) / name
            if path.is_symlink():
                raise ValueError(f"Source symlinks are not admitted: {path}")
            if path.is_file():
                total_bytes += path.stat().st_size
                if total_bytes > max_bytes:
                    raise ValueError('Source snapshot byte capacity exceeded')
                key = path.relative_to(root).as_posix()
                if len(result) >= MAX_FILES:
                    raise ValueError("Source manifest exceeds file capacity")
                result[key] = digest(path)
    return dict(sorted(result.items()))


def artifacts(root):
    result = {}
    for name in PRODUCTS:
        path = root / name
        if (not path.is_file() or path.is_symlink() or path.resolve() != root.resolve() / name
                or not path.stat().st_size):
            raise ValueError(f"Missing fresh stage product: {path}")
        result[name] = digest(path)
    return result


def compare_products(first, second):
    if first != second:
        raise ValueError("Stage1/2 artifact hashes differ; reproducible self-host gate failed")


def write_json(path, value):
    # A reader must see either the last complete receipt or the new one, never a
    # partially written JSON document. Stages and commands are serialized.
    temporary = path.with_name(path.name + '.tmp')
    try:
        temporary.write_text(json.dumps(value, indent=2, sort_keys=True) + "\n")
        temporary.replace(path)
    finally:
        if temporary.exists():
            temporary.unlink()


def admission_records(journal, offset=0):
    """Bound disk evidence; stream records instead of collecting child history."""
    if not journal.exists():
        return
    if journal.stat().st_size > 16 * 1024 * 1024:
        raise ValueError('Subprocess admission journal exceeded capacity')
    with journal.open() as source:
        source.seek(offset)
        while True:
            line = source.readline(1024 * 1024 + 1)
            if not line:
                break
            if len(line) > 1024 * 1024:
                raise ValueError('Subprocess admission record exceeded capacity')
            record = json.loads(line)
            if not isinstance(record, dict):
                raise ValueError('Invalid subprocess admission record')
            yield record


class CommandFailure(ValueError):
    """Preserve a real child exit status in failed stage evidence."""
    def __init__(self, command, exit_code, log):
        self.exit_code = exit_code
        super().__init__(f'Command exited {exit_code}: {command}; see {log}')


def run_command(command, cwd, env, log, journal, timeout=600, budget=None,
                ready_gate=None, cleanup_observation=None):
    """One process group at a time; disk-backed logs, finite time/output budgets."""
    if budget:
        budget.check()
        timeout = min(timeout, max(0, budget.deadline - time.monotonic()))
    channel = ScopeChannel(env) if env.get('FLAT_BOOTSTRAP_POLICY') else None
    previous_handlers = {}
    def interrupted(signum, frame):
        if observation.get('status') == 'spawning':
            observation['pending_host_signal'] = signum
            return
        raise ValueError(f'Process scope cancelled by host signal {signum}')
    observation = cleanup_observation if cleanup_observation is not None else {}
    observation.update(status='spawning', wait_completed=False, child_reaped=False,
                       process_group_absent=False)
    try:
        for signum in (signal.SIGINT, signal.SIGTERM):
            previous_handlers[signum] = signal.signal(signum, interrupted)
        return _run_command(command, cwd, channel.env if channel else env, log, journal,
                            timeout, budget, ready_gate, observation, channel)
    finally:
        for signum, handler in previous_handlers.items():
            signal.signal(signum, handler)
        if channel:
            try:
                write_json(log.with_suffix(log.suffix + '.scope.json'), {
                    'schema': 'flat.stage.process-scope.v1', 'scope': channel.scope,
                    'ownership': 'external-command-group', 'scope_cancelled': channel.cancelled,
                    'hello_count': channel.hellos, 'frame_count': channel.frames,
                    'events': channel.events,
                    'fixtures_started': len(channel.started), 'fixtures_finished': len(channel.finished),
                    'cleanup': observation,
                    'claim': 'command-scope only; not per-operation descendant cleanup'})
            finally:
                channel.close()


def _run_command(command, cwd, env, log, journal, timeout, budget, ready_gate,
                 cleanup_observation, channel):
    with log.open("xb") as output:
        process = subprocess.Popen(command, cwd=cwd, env=env, stdout=output,
                                   stderr=subprocess.STDOUT, start_new_session=True,
                                   pass_fds=(channel.write, channel.ack_read) if channel else ())
        deadline = time.monotonic() + timeout
        try:
            identity = process_identity(process.pid)
            if identity['pgid'] != process.pid or identity['session'] != process.pid:
                raise ValueError('Spawn did not create an owned process session')
            process._stage_identity = identity
            cleanup_observation.update(pid=process.pid, pgid=identity['pgid'],
                                       identity=identity)
            durable_process_receipt(log.with_suffix(log.suffix + '.process.json'), {
                'schema': 'flat.stage.process-identity.v1', 'identity': identity,
                'status': 'running', 'claim': 'spawn identity, not cleanup evidence'})
            cleanup_observation['status'] = 'running'
            if cleanup_observation.get('pending_host_signal'):
                raise ValueError('Process scope cancelled by host signal during spawn')
            if ready_gate:
                ready_gate(process)
            while not process_finished(process):
                if channel:
                    channel.drain()
                if budget:
                    budget.check()
                if time.monotonic() > deadline or log.stat().st_size > 100 * 1024 * 1024 or (
                        journal.exists() and journal.stat().st_size > 16 * 1024 * 1024):
                    raise ValueError(f"Command exceeded time/log capacity: {log}")
                time.sleep(0.05)
            if channel:
                channel.drain()
        finally:
            # Cleanup must not be interrupted by a repeated caller signal.
            saved = {s: signal.signal(s, signal.SIG_IGN) for s in (signal.SIGINT, signal.SIGTERM)}
            try:
                settle_process_group(process, cleanup_observation)
            finally:
                try:
                    durable_process_receipt(log.with_suffix(log.suffix + '.settlement.json'), {
                        'schema': 'flat.stage.process-settlement.v1', **cleanup_observation})
                finally:
                    for s, handler in saved.items():
                        signal.signal(s, handler)
        if process.returncode:
            raise CommandFailure(command, process.returncode, log)
    if channel:
        channel.drain()
        if (not channel.hellos or channel.buffer or len(channel.finished) != len(channel.started)
                or any(channel.counts[key] != entry['count'] for key, entry in channel.expected.items())):
            raise ValueError('Process scope startup/terminal fixture accounting incomplete')
    if log.stat().st_size > 100 * 1024 * 1024:
        raise ValueError(f"Command exceeded log capacity: {log}")
    if journal.exists() and journal.stat().st_size > 16 * 1024 * 1024:
        raise ValueError(f'Command exceeded admission journal capacity: {journal}')
    for record in admission_records(journal):
        if record['decision'] == 'reject':
            raise ValueError(f"Rejected subprocess (even if caught): {journal}")
    return process.returncode


def observe_subprocess_cleanup(output, python=None, timeout=10):
    """Actual watchdog observations, never production compiler lifecycle evidence.

    Each fixture writes one FIFO-ready byte after creating a temporary output,
    then blocks on a separate unreleased FIFO gate. No scheduling sleep proves
    readiness. The watchdog uses its normal process-group cleanup and wait path.
    """
    if not 0 < timeout <= 60:
        raise ValueError('Lifecycle watchdog timeout must be in (0, 60] seconds')
    output = Path(output).resolve()
    fresh_directory(output)
    python = Path(python or sys.executable).resolve()
    receipt = {'schema': 1, 'scope': 'watchdog-fixture-only', 'status': 'running',
               'production_compiler_executed': False, 'children_reaped': False,
               'temporary_outputs_removed': False, 'scenarios': [],
               'python': str(python), 'python_sha256': digest(python)}
    fixture = ("import os, pathlib, sys\n"
               "pathlib.Path(sys.argv[3]).write_text('temporary lifecycle output')\n"
               "print('lifecycle fixture created temporary output; entering controlled gate', flush=True)\n"
               "with open(sys.argv[1], 'wb', buffering=0) as ready: ready.write(b'R')\n"
               "with open(sys.argv[2], 'rb', buffering=0) as gate: gate.read(1)\n")
    class Cancelled(ValueError):
        pass
    try:
        for scenario in ('cancel', 'timeout'):
            observed = {'scenario': scenario, 'ready_observed': False}
            receipt['scenarios'].append(observed)
            log = output / (scenario + '.log')
            with tempfile.TemporaryDirectory(prefix='lifecycle-', dir=output) as temporary:
                temporary = Path(temporary)
                ready, gate, artifact = (temporary / name for name in ('ready', 'gate', 'partial.js'))
                os.mkfifo(ready)
                os.mkfifo(gate)
                # O_RDWR keeps the read gate open without an EOF-before-writer race.
                ready_fd = os.open(ready, os.O_RDWR | os.O_NONBLOCK)
                def await_ready(process):
                    readable, _, _ = select.select([ready_fd], [], [], timeout)
                    if not readable or os.read(ready_fd, 1) != b'R':
                        raise ValueError('Lifecycle fixture did not enter controlled gate')
                    observed['ready_observed'] = artifact.is_file() and process.poll() is None
                    if not observed['ready_observed']:
                        raise ValueError('Lifecycle fixture readiness was incomplete')
                    if scenario == 'cancel':
                        raise Cancelled('controlled cancellation after child-ready gate')
                command = [str(python), '-I', '-c', fixture, str(ready), str(gate), str(artifact)]
                observed['argv'] = command
                try:
                    # timeout=0 expires only after the explicit ready gate returns.
                    run_command(command, temporary, {'PATH': str(python.parent)}, log,
                                output / 'unused-journal', timeout=0, ready_gate=await_ready,
                                cleanup_observation=observed)
                    raise RuntimeError('Blocked lifecycle fixture unexpectedly completed')
                except Cancelled as error:
                    observed['termination_reason'] = 'cancel'
                    observed['error'] = str(error)
                except ValueError as error:
                    if scenario != 'timeout' or not observed['ready_observed'] or 'time/log capacity' not in str(error):
                        raise
                    observed['termination_reason'] = 'timeout'
                    observed['error'] = str(error)
                finally:
                    os.close(ready_fd)
                observed['temporary_output_existed_before_cleanup'] = artifact.is_file()
            observed['temporary_outputs_removed'] = not temporary.exists()
            observed['log'] = log.name
            observed['log_sha256'] = digest(log)
            if not all(observed.get(key) is True for key in (
                    'ready_observed', 'wait_completed', 'child_reaped', 'process_group_absent',
                    'temporary_output_existed_before_cleanup', 'temporary_outputs_removed')):
                raise ValueError('Subprocess lifecycle cleanup did not settle')
        receipt.update(status='observed', children_reaped=True, temporary_outputs_removed=True)
        return receipt
    except BaseException as error:
        unknown = any(item.get('cleanup_unknown') for item in receipt['scenarios'])
        receipt.update(status='cleanup-unknown' if unknown else 'failed',
                       cleanup_unknown=unknown, error=str(error))
        raise
    finally:
        write_json(output / 'subprocess-cleanup.json', receipt)


def snapshot(repo, destination, manifest, budget=None):
    # Validate the bounded manifest before creating any output. A reviewed source
    # identity must never be interpreted as an absolute path or traversal.
    repo, destination = Path(repo).resolve(), Path(destination).resolve()
    if destination == repo or repo in destination.parents or destination in repo.parents:
        raise ValueError('Source snapshot and output must be separate trees')
    if not manifest or len(manifest) > MAX_FILES:
        raise ValueError('Source manifest must be nonempty and within file capacity')
    for name in manifest:
        if not isinstance(name, str):
            raise ValueError('Source manifest paths must be strings')
        relative = Path(name)
        if (relative.is_absolute() or
                relative.as_posix() != name or any(part in ('.', '..') for part in relative.parts) or
                not relative.parts or relative.parts[0] != 'packages' or
                any(part in EXCLUDED for part in relative.parts)):
            raise ValueError(f'Invalid source manifest path: {name}')
        source = repo / relative
        if not source.is_file() or source.resolve() != source:
            raise ValueError(f'Source must be a canonical regular file: {source}')
    fresh_directory(destination)
    for name, expected in manifest.items():
        if budget:
            budget.check(storage=False)
        source = repo / name
        if digest(source) != expected:
            raise ValueError(f"Source changed during snapshot: {source}")
        target = destination / name
        target.parent.mkdir(parents=True, exist_ok=True)
        shutil.copyfile(source, target)
        target.chmod(source.stat().st_mode & 0o777)
        if digest(target) != expected:
            raise ValueError(f"Snapshot changed during copy: {source}")


def copy_seed(source, destination, expected):
    source, destination = Path(source), Path(destination)
    if destination.exists() or destination.is_symlink() or source.resolve() == destination.resolve():
        raise ValueError(f'Seed destination must be fresh and separate: {destination}')
    if digest(source) != expected:
        raise ValueError(f"Producer seed changed: {source}")
    destination.parent.mkdir(parents=True, exist_ok=True)
    with source.open('rb') as input_file, destination.open('xb') as output_file:
        shutil.copyfileobj(input_file, output_file, length=1024 * 1024)
    destination.chmod(0o444)
    if digest(destination) != expected:
        raise ValueError(f"Copied seed digest mismatch: {destination}")
    return destination


def verify_resolution(log, expected, runtime='flat'):
    """Verify real Airship --debug output, not a reimplementation of resolution.

    Exact one tool argv must select the admitted script or stage0 jar. No ANSI,
    path normalization or substring matching can establish tool selection.
    """
    matches = []
    executable = 'node' if runtime == 'flat' else 'java'
    prefix = ['node', str(expected)] if runtime == 'flat' else ['java', '-jar', str(expected)]
    if not log.is_file():
        raise ValueError("Missing real Airship resolution log")
    with log.open() as source:
        for line in source:
            try:
                words = shlex.split(line)
            except ValueError:
                continue
            if words and words[0] == executable:
                matches.append(words)
    if len(matches) != 1 or matches[0][:len(prefix)] != prefix:
        raise ValueError("Real Airship debug compiler lookup does not match explicit seed")
    return matches[0]


LIFECYCLE_SOURCE = 'packages/Airship/src/flat/airship/Airship_Test.flat'
# Frozen consumer source reviewed for these three literal compiler probes. A
# source change requires an explicit inventory review, never automatic discovery.
LIFECYCLE_SOURCE_SHA256 = '9b6a6cbb16a5fcf3f1a8e43e5a42a8828103481b79d56d7bdc9c078221d983bc'
LIFECYCLE_SCRIPTS = (
    'process.exit(0);',
    "const fs=require('fs'); if(fs.readFileSync(process.argv[2],'utf8')!=='committed') process.exit(9); process.exit(7);",
    "const fs=require('fs'); if(fs.readFileSync(process.argv[2],'utf8')!=='committed') process.exit(9);",
)


def companion_contract(workspace):
    group = json.loads((workspace / 'packages/Airship/flat.json').read_text()).get(
        'sources', {}).get('owned-process-test', {})
    expected = {'mainClass': 'flat/airship/AirshipOwnedProcess_TestSuite',
                'source': 'src/**/*_Test*.flat', 'outFile': 'owned-process-test.js'}
    if (any(group.get(key) != value for key, value in expected.items()) or
            group.get('dependencies', {}).get('https://github.com/FlatLang/Test.git') != 'master'):
        raise ValueError('Mandatory Airship owned-process-test source group is missing/incompatible')


def lifecycle_inventory(workspace, runner):
    source = workspace / LIFECYCLE_SOURCE
    if not source.is_file() or digest(source) != LIFECYCLE_SOURCE_SHA256:
        raise ValueError('Airship source differs from reviewed lifecycle fixture inventory')
    owner = workspace / 'packages/Airship'
    return [{'id': f'airship-lifecycle-{index}', 'command': 'node',
             'args': ['{lifecycle}/compiler.cjs'] + ([] if index == 0 else ['{lifecycle}/selected.js']),
             'lifecycleStep': index,
             'scriptSha256': hashlib.sha256(script.encode()).hexdigest(),
             'runner': str(runner), 'runnerSha256': digest(runner), 'cwd': str(owner),
             'files': [{'path': str(source), 'sha256': LIFECYCLE_SOURCE_SHA256}],
             'count': 1, 'outcome': {'exit': 7 if index == 1 else 0, 'signal': None}}
            for index, script in enumerate(LIFECYCLE_SCRIPTS)]


def process_companion_gates(workspace, directory, env, policy, node, execute,
                            budget=None, command_seconds=600):
    """Extra mandatory gates; never replace any of the seven package suites."""
    dist = workspace / 'packages/Airship/dist'
    if dist.exists() and any(dist.rglob('owned-process-test.js')):
        raise ValueError('Ordinary companion output already exists before build')
    execute('Airship', 'install', 'owned-process-test', runner=workspace / PRODUCTS[1])
    candidates = list(dist.rglob('owned-process-test.js'))
    if len(candidates) != 1:
        raise ValueError('Expected exactly one fresh ordinary companion output')
    companion = candidates[0]
    if companion.is_symlink() or companion.resolve() != companion or not companion.stat().st_size:
        raise ValueError('Missing fresh ordinary companion output')
    expected = digest(companion)
    ordinary_env = {key: value for key, value in env.items()
                    if key not in ('NODE_OPTIONS', 'FLAT_BOOTSTRAP_POLICY', 'FLAT_PROCESS_SCOPE')}
    log = directory / 'logs/ordinary-companion.log'
    run_command([str(node), str(companion)], workspace / 'packages/Airship', ordinary_env,
                log, directory / 'ordinary-journal.jsonl', budget=budget, timeout=command_seconds)
    # The output assertion prevents an empty/wrong entrypoint from greenwashing
    # the private-group companion. Read a bounded summary, never a whole log.
    with log.open('rb') as stream:
        stream.seek(max(0, log.stat().st_size - 4096))
        if b'Ordinary process companion: 6 cases, 0 failures' not in stream.read(4096):
            raise ValueError('Ordinary companion did not report all six cases')
    if digest(companion) != expected:
        raise ValueError('Ordinary companion output mutated')
    cancellations = []
    for signum in ('SIGINT', 'SIGTERM'):
        cancellations.append(external_cancellation_gate(workspace, directory, env, policy,
                                                       node, signum, budget, command_seconds))
    return {'ordinary_companion_sha256': expected, 'ordinary_log_sha256': digest(log),
            'external_cancellations': cancellations}


def external_cancellation_gate(workspace, directory, env, policy, node, signum,
                               budget=None, command_seconds=600):
    if signum not in ('SIGINT', 'SIGTERM'):
        raise ValueError('Unsupported cancellation signal')
    generated = workspace / PRODUCTS[1]
    # Load the fresh generated bundle, preserving initialization and its actual
    # consumer implementation, but replace only the CLI-main invocation. This
    # is a dedicated consumer probe, not a claim that the CLI command succeeded.
    marker = 'Airship.main(flat_main_args);'
    # Single generated-program boundary: the VM must compile one complete
    # bundle. Cap materialization at 100 MiB; never collect project sources.
    if generated.stat().st_size > 100 * 1024 * 1024:
        raise ValueError('Generated consumer exceeds probe byte capacity')
    if generated.read_text().count(marker) != 1:
        raise ValueError('Generated Airship main contract changed')
    wrapper = workspace / f'packages/Airship/dist/es6/external-cancellation-{signum}.js'
    child = "process.on('SIGTERM',()=>process.exit(0));process.kill(process.ppid," + json.dumps(signum) + ");setInterval(()=>{},1000);"
    replacement = ('await AirshipProcess.execute(FlatArray.jsStringArrayToFlatArray(' +
                   json.dumps(['node', '-e', child]) + '), flat_null, true);' +
                   "throw Error('External cancellation unexpectedly returned');")
    wrapper.parent.mkdir(parents=True, exist_ok=True)
    wrapper.write_text("const fs=require('node:fs'),vm=require('node:vm');\n" +
                      'const source=fs.readFileSync(' + json.dumps(str(generated)) + ", 'utf8');\n" +
                      'vm.compileFunction(source.replace(' + json.dumps(marker) + ',' +
                      json.dumps(replacement) + "),['require','__filename','__dirname'],{importModuleDynamically:vm.constants.USE_MAIN_CONTEXT_DEFAULT_LOADER,filename:" +
                      json.dumps(str(generated)) + '})(require(' + json.dumps('node:module') +
                      ').createRequire(' + json.dumps(str(generated)) + '),' +
                      json.dumps(str(generated)) + ',' + json.dumps(str(generated.parent)) + ');\n')
    entry = {'id': f'external-{signum}', 'command': 'node', 'args': ['-e', child],
             'runner': str(wrapper), 'runnerSha256': digest(wrapper), 'cwd': str(workspace),
             'files': [{'path': str(generated), 'sha256': digest(generated)}],
             'count': 1, 'outcome': {'exit': 0, 'signal': None}}
    selected = directory / f'external-{signum}-policy.json'
    write_json(selected, {**policy, 'fixtures': [entry]})
    log = directory / f'logs/external-{signum}.log'
    try:
        run_command([str(node), str(wrapper)], workspace,
                    {**env, 'FLAT_BOOTSTRAP_POLICY': str(selected)}, log,
                    Path(policy['journal']), budget=budget, timeout=command_seconds)
    except ValueError as error:
        if 'Process scope cancellation requested' not in str(error):
            raise
    else:
        raise ValueError('External cancellation command unexpectedly succeeded')
    receipt = json.loads(log.with_suffix('.log.scope.json').read_text())
    if (not receipt['scope_cancelled'] or receipt['fixtures_started'] != 1 or
            sum(event['kind'] == 'cancel' for event in receipt['events']) != 1 or
            not receipt['cleanup'].get('process_group_absent') or
            not receipt['cleanup'].get('child_reaped')):
        raise ValueError('External cancellation missing request or confirmed cleanup')
    return {'signal': signum, 'generated_airship_sha256': digest(generated),
            'probe_sha256': digest(wrapper), 'policy_sha256': digest(selected),
            'log_sha256': digest(log), 'scope_sha256': digest(log.with_suffix('.log.scope.json'))}


def stage_command(node, runner, workspace, number, package, operation, source=None,
                  jar=None, compiler_seed=None):
    """Public Airship argv; stage tools are inputs, never workspace outputs."""
    if number not in (0, 1, 2):
        raise ValueError('Unknown bootstrap stage')
    if operation not in ('install', 'run') or package not in REQUIRED_SUITES:
        raise ValueError('Unsupported stage workload')
    if number == 0 and (not jar or compiler_seed):
        raise ValueError('Stage0 requires only the explicit Java compiler seed')
    if number > 0 and (not compiler_seed or jar):
        raise ValueError('Generated stages require only an explicit generated compiler')
    command = [str(node), str(runner), '--use-links', '-f',
               str(workspace / 'packages' / package), '--flatc-runtime',
               'java' if number == 0 else 'flat', operation]
    if source:
        command.append(source)
    command += ['-t', 'es6']
    if operation == 'install':
        command += ['--flatc-jar', str(jar)] if number == 0 else ['--flatc-script', str(compiler_seed)]
    return command


def stage_public_workloads(repo, output, directory, sources, seeds, products, budget, seconds):
    """Separate fresh public workloads, alongside (never replacing) seven suites."""
    import dogfood_acceptance as acceptance
    results = {}
    for name in ('airship-toolchain', 'core-lr'):
        context = acceptance.create_production_workspace(repo, output / (directory.name + '-public-' + name),
                                                         sources, seeds, products, directory, budget)
        results[name] = acceptance.run_public_workload(context, name, seeds, budget, seconds)
    return results


def stage(number, repo, output, sources, seeds, previous=None, budget=None, command_seconds=600,
          origin=None):
    """Retain a failed setup receipt too; never overwrite an existing stage."""
    if number not in (0, 1, 2) or (number > 0 and previous is None):
        raise ValueError('Invalid stage or missing previous-stage tools')
    directory = output / f"stage{number}"
    fresh_directory(directory)
    manifest_file = directory / 'manifest.json'
    write_json(manifest_file, {'schema': 2, 'stage': number, 'status': 'setting-up',
                               'commands': [], 'runtime': 'java' if number == 0 else 'flat'})
    try:
        return _stage(number, repo, output, sources, seeds, previous, budget,
                      command_seconds, origin)
    except BaseException as error:
        manifest = json.loads(manifest_file.read_text())
        manifest.update(status='failed', error=str(error))
        write_json(manifest_file, manifest)
        raise


def _stage(number, repo, output, sources, seeds, previous=None, budget=None, command_seconds=600,
           origin=None):
    directory = output / f"stage{number}"
    workspace = directory / "workspace"
    snapshot(repo, workspace, sources, budget=budget)
    origin_metadata = install_snapshot_origin(workspace, origin)
    home = directory / "home"
    home.mkdir()
    flat_home = directory / "flat-home"
    flat_home.mkdir()
    logs = directory / "logs"
    logs.mkdir()
    seed_dir = directory / "seeds"
    seed_dir.mkdir()
    if number == 0:
        airship = copy_seed(seeds["airship"][0], seed_dir / "airship.js", seeds["airship"][1])
        compiler_seed = None
        compiler_hash = None
        jar = copy_seed(seeds["java_compiler"][0],
                        workspace / "packages/Flat/target/flatc.jar", seeds["java_compiler"][1])
        backend_jar = copy_seed(seeds["java_backend"][0],
                                workspace / "packages/Flat-ES6/target/flat-es6.jar",
                                seeds["java_backend"][1]).resolve(strict=True)
    else:
        airship = copy_seed(previous["airship"], seed_dir / "airship.js", previous["airship_hash"])
        compiler_seed = copy_seed(previous["compiler"], seed_dir / "flatc.js", previous["compiler_hash"])
        compiler_hash = previous["compiler_hash"]
        jar = None
        backend_jar = None
    # Existing Airship versions may use FLAT_HOME links instead of workspace
    # resolution. Both point at this stage's source copy, never the live checkout.
    for package in sorted((workspace / "packages").iterdir()):
        if package.is_dir() and (package / "flat.json").is_file():
            link = flat_home / "packages/github.com/FlatLang" / package.name / "[link]"
            link.parent.mkdir(parents=True)
            link.symlink_to(package, target_is_directory=True)
    mapped_compiler = compiler_seed if compiler_seed else workspace / PRODUCTS[0]
    journal = directory / "admissions.jsonl"
    policy = {
        "stage": number, "root": str(workspace), "node": str(seeds["node"][0]),
        "nodeSha256": seeds["node"][1], "git": str(seeds["git"][0]), "gitSha256": seeds["git"][1],
        "java": str(seeds["java"][0]) if number == 0 else None,
        "javaSha256": seeds["java"][1] if number == 0 else None,
        "javaJar": str(jar) if jar else None, "javaJarSha256": seeds["java_compiler"][1] if jar else None,
        "javaBackendJar": str(backend_jar) if backend_jar else None,
        "javaBackendJarSha256": seeds["java_backend"][1] if backend_jar else None,
        "compiler": str(mapped_compiler), "seed": str(compiler_seed) if compiler_seed else None,
        "seedSha256": compiler_hash, "airship": str(airship), "airshipSha256": digest(airship),
        "compilerOutput": str(workspace / PRODUCTS[0]),
        "journal": str(journal),
    }
    policy_file = directory / "policy.json"
    write_json(policy_file, policy)
    guard = copy_seed(HERE / "stage_subprocess_guard.cjs", seed_dir / "guard.cjs",
                      digest(HERE / "stage_subprocess_guard.cjs"))
    # Deliberately do not inherit JAVA_TOOL_OPTIONS, NODE_OPTIONS, FLAT_HOME,
    # npm/git configuration, provider secrets, or other ambient build selection.
    env = {"HOME": str(home), "FLAT_HOME": str(flat_home), "PATH": str(seeds["node"][0].parent),
           "LANG": "C.UTF-8", "TZ": "UTC", "SOURCE_DATE_EPOCH": "0",
           'GIT_CONFIG_NOSYSTEM': '1', 'GIT_CONFIG_GLOBAL': os.devnull,
           "AIRSHIP_LOCK_FILE_ENABLED": "false", "NODE_NO_WARNINGS": "1",
           "FLAT_BOOTSTRAP_POLICY": str(policy_file), "NODE_OPTIONS": "--require=" + str(guard)}
    manifest = {"schema": 2, "stage": number, "status": "running", "sources": sources,
                "runtime": "java" if number == 0 else "flat",
                "compiler_selection": "reviewed-java-seed" if number == 0 else "explicit-generated-script",
                'policy': policy, 'commands': [], 'source_origin': origin,
                'tool_sha256': {name: expected for name, (_, expected) in seeds.items()
                                if number == 0 or name in ('node', 'git')},
                'guard_sha256': digest(guard), 'policy_sha256': digest(policy_file),
                'source_origin_metadata': origin_metadata}
    manifest_file = directory / "manifest.json"
    write_json(manifest_file, manifest)

    fresh_tests = {}
    generated_runner_hash = None

    def execute(package, operation, source=None, runner=airship):
        if snapshot_origin_manifest(workspace) != origin_metadata:
            raise ValueError('Isolated source origin metadata changed')
        if runner == airship and digest(runner) != policy["airshipSha256"]:
            raise ValueError("Immutable Airship seed changed")
        if runner != airship and (runner != workspace / PRODUCTS[1] or
                                  digest(runner) != generated_runner_hash):
            raise ValueError('Generated Airship changed after production')
        if compiler_seed and digest(compiler_seed) != compiler_hash:
            raise ValueError("Immutable compiler seed changed")
        if digest(seeds["node"][0]) != seeds["node"][1]:
            raise ValueError("Pinned Node executable changed")
        command = stage_command(seeds['node'][0], runner, workspace, number,
                                package, operation, source, jar, compiler_seed)
        manifest["commands"].append({"argv": command, "status": "running",
                                      "runner_sha256": digest(runner),
                                      "compiler_sha256": digest(jar if jar else compiler_seed)})
        write_json(manifest_file, manifest)
        dist = workspace / "packages" / package / "dist"
        if operation == "install" and source == "test":
            if dist.exists() and any(dist.rglob("test.js")):
                raise ValueError("Test output already exists before its build gate")
        offset = journal.stat().st_size if journal.exists() else 0
        if operation == 'install':
            resolution_log = logs / f"{len(manifest['commands']):02d}-resolution.log"
            debug_policy_file = directory / 'debug-policy.json'
            write_json(debug_policy_file, {**policy, 'debugOnly': True})
            debug_env = {**env, 'FLAT_BOOTSTRAP_POLICY': str(debug_policy_file)}
            run_command(command + ['--debug'], workspace / 'packages' / package,
                        debug_env, resolution_log, journal, budget=budget, timeout=command_seconds)
            manifest['commands'][-1]['resolution_argv'] = verify_resolution(
                resolution_log, jar if number == 0 else mapped_compiler,
                runtime='java' if number == 0 else 'flat')
            manifest['commands'][-1]['resolution_log_sha256'] = digest(resolution_log)
            write_json(manifest_file, manifest)
        command_env = env
        if operation == 'run' and package == 'Airship':
            candidates = fresh_tests.get('Airship', {})
            if len(candidates) != 1:
                raise ValueError('Expected exactly one fresh Airship test runner for fixture inventory')
            selected_runner = Path(next(iter(candidates)))
            fixture_policy = {**policy, 'fixtures': lifecycle_inventory(workspace, selected_runner)}
            fixture_policy_file = directory / 'airship-test-policy.json'
            write_json(fixture_policy_file, fixture_policy)
            command_env = {**env, 'FLAT_BOOTSTRAP_POLICY': str(fixture_policy_file)}
            manifest['commands'][-1]['fixture_policy_sha256'] = digest(fixture_policy_file)
        exit_code = run_command(command, workspace / "packages" / package, command_env,
                                logs / f"{len(manifest['commands']):02d}.log", journal, budget=budget,
                                timeout=command_seconds)
        if operation == "install":
            expected = str(seeds["java"][0]) if number == 0 else str(seeds["node"][0])
            expected_script = str(jar) if number == 0 else str(mapped_compiler)
            if not any(record["command"] == expected and expected_script in record["args"]
                       for record in admission_records(journal, offset)):
                raise ValueError("Install did not invoke the admitted compiler seed")
            if source == "test":
                generated = list(dist.rglob("test.js")) if dist.exists() else []
                if not generated or any(path.is_symlink() or not path.stat().st_size for path in generated):
                    raise ValueError("Test installation did not produce a fresh test.js")
                fresh_tests[package] = {str(path): digest(path) for path in generated}
        else:
            executed = [Path(record["args"][0]) for record in admission_records(journal, offset)
                        if record["command"] == str(seeds["node"][0]) and record["args"]
                        and record["args"][0].endswith("/test.js")]
            selected = workspace / "packages" / package / "dist"
            if not any(selected in path.parents and str(path) in fresh_tests.get(package, {})
                       and path.is_file() and digest(path) == fresh_tests[package][str(path)]
                       for path in executed):
                raise ValueError("Run test did not execute a fresh generated package test.js")
            manifest["commands"][-1]["test_artifacts"] = {
                str(path.relative_to(workspace)): digest(path) for path in executed
                if selected in path.parents}
        manifest["commands"][-1].update(status="passed", exit_code=exit_code,
                                         log_sha256=digest(logs / f"{len(manifest['commands']):02d}.log"))
        write_json(manifest_file, manifest)

    try:
        companion_contract(workspace)
        execute("Flat-Compiler", "install", "flatc")
        execute("Flat-Compiler", "install", "production-driver")
        execute("Airship", "install")
        products = artifacts(workspace)
        generated_runner_hash = products[PRODUCTS[1]]
        # Generated Airship installs and runs every suite using the same selected
        # compiler; implicit production Core dependencies remain untrimmed.
        for package in REQUIRED_SUITES:
            execute(package, "install", "test", runner=workspace / PRODUCTS[1])
            execute(package, "run", "test", runner=workspace / PRODUCTS[1])
        manifest['process_companion_gates'] = process_companion_gates(
            workspace, directory, env, policy, seeds['node'][0], execute, budget, command_seconds)
        manifest['public_workloads'] = stage_public_workloads(repo, output, directory, sources, seeds,
                                                              products, budget, command_seconds)
        if artifacts(workspace) != products:
            raise ValueError("Acceptance tests changed stage tool outputs")
        if snapshot_origin_manifest(workspace) != origin_metadata:
            raise ValueError('Isolated source origin metadata changed')
        if inventory(workspace, budget=budget) != sources:
            raise ValueError("Build changed source/dependency snapshot")
        if digest(airship) != policy["airshipSha256"] or (compiler_seed and digest(compiler_seed) != compiler_hash):
            raise ValueError("Build mutated an immutable seed")
        manifest.update(status="passed", products=products, admissions_sha256=digest(journal))
        return {"compiler": workspace / PRODUCTS[0], "compiler_hash": products[PRODUCTS[0]],
                "airship": workspace / PRODUCTS[1], "airship_hash": products[PRODUCTS[1]],
                "driver": workspace / PRODUCTS[2], "driver_hash": products[PRODUCTS[2]]}
    except BaseException as error:
        if manifest["commands"] and manifest["commands"][-1]["status"] == "running":
            manifest["commands"][-1]["status"] = "failed"
            manifest["commands"][-1]["exit_code"] = getattr(error, 'exit_code', None)
            manifest["commands"][-1]["error"] = str(error)
        manifest.update(status="failed", error=str(error))
        raise
    finally:
        # Bounded by the fixed stage command count. Keep evidence for failures,
        # not only successful commands, without collecting log contents in RAM.
        retained = [path for index in range(1, len(manifest['commands']) + 1)
                    for suffix in ('', '-resolution')
                    for path in (logs / f'{index:02d}{suffix}.log',
                                 logs / f'{index:02d}{suffix}.log.scope.json')]
        retained += [sidecar for index in range(1, len(manifest['commands']) + 1)
                     for suffix in ('', '-resolution')
                     for ending in ('.process.json', '.settlement.json')
                     for sidecar in (logs / f'{index:02d}{suffix}.log{ending}',)]
        retained += [logs / 'ordinary-companion.log']
        for signum in ('SIGINT', 'SIGTERM'):
            retained += [logs / f'external-{signum}.log', logs / f'external-{signum}.log.scope.json',
                         directory / f'external-{signum}-policy.json']
        retained += [directory / 'airship-test-policy.json']
        manifest['retained_files'] = {
            path.relative_to(directory).as_posix(): digest(path)
            for path in retained if path.is_file() and not path.is_symlink()}
        if journal.is_file():
            manifest['admissions_sha256'] = digest(journal)
        for index, command in enumerate(manifest['commands'], 1):
            log = logs / f'{index:02d}.log'
            if log.is_file():
                command['log_sha256'] = digest(log)
            resolution = logs / f'{index:02d}-resolution.log'
            if resolution.is_file():
                command['resolution_log_sha256'] = digest(resolution)
        write_json(manifest_file, manifest)


def main():
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("--repo", required=True, type=Path)
    parser.add_argument("--output", required=True, type=Path)
    parser.add_argument("--seeds", required=True, type=Path)
    parser.add_argument('--review', required=True, type=Path,
                        help='Independently reviewed schema-1 seed provenance receipt')
    parser.add_argument('--review-sha256', required=True,
                        help='Expected review digest from protected provisioning, not calculated here')
    parser.add_argument('--preflight-only', action='store_true',
                        help='Validate reviewed seed delivery and write seed-preflight.json; launch no tools')
    parser.add_argument('--run-seconds', type=int, default=3600,
                        help='Aggregate deadline, default 3600; explicit experiments capped at 10800')
    parser.add_argument('--command-seconds', type=int, default=600,
                        help='Per-command deadline, default 600; maximum 3600')
    args = parser.parse_args()
    run_seconds, command_seconds = execution_limits(args.run_seconds, args.command_seconds)
    repo, output = args.repo.resolve(), args.output.resolve()
    if output == repo or repo in output.parents or output in repo.parents:
        parser.error("--output must be a new directory separate from the checkout")
    entries, review, seeds = load_reviewed_bundle(args.seeds, args.review, args.review_sha256)
    budget = RunBudget(output, seconds=run_seconds)
    if args.preflight_only:
        fresh_directory(output)
        write_json(output / 'seed-preflight.json', {
            'schema': 1, 'status': 'validated-not-executed',
            'acceptance': False, 'review_sha256': args.review_sha256,
            'seeds_manifest_sha256': digest(args.seeds), 'review': review,
            'seeds': {name: {'path': str(path), 'sha256': expected}
                      for name, (path, expected) in seeds.items()},
            'producer_contract': {
                'airship': 'standalone Java-capable JS with --use-links, --flatc-jar and --debug',
                'java_compiler': 'standalone executable jar',
                'java_backend': 'standalone ES6 backend jar',
                'generated': '--flatc-runtime flat --flatc-script; no Java or output aliasing'},
        })
        return
    # Snapshot only package inputs; do not import existing ignored/generated outputs.
    provenance = source_provenance(repo, seeds['git'][0])
    provenance['source_origin'] = capture_origin(repo, seeds['git'][0])
    revision = provenance['baseline_revision']
    sources = {"packages/" + name: value for name, value in inventory(repo / "packages", budget=budget).items()}
    if not sources:
        parser.error("No package sources found")
    fresh_directory(output)
    # Preserve the reviewed bytes, not a JSON reserialization whose digest no
    # longer matches the independently provisioned trust root.
    copy_seed(args.seeds, output / 'seeds.json', review['seeds_sha256'])
    copy_seed(args.review, output / 'review.json', args.review_sha256)
    source_manifest = output / "sources.json"
    write_json(source_manifest, sources)
    source_manifest.chmod(0o444)
    source_manifest_digest = digest(source_manifest)
    write_json(output / 'source-provenance.json', provenance)
    receipt = {"schema": 2, "status": "running", 'source_revision': revision,
               'stages': [], 'acceptance_scope': 'isolated generated-tool rebuild and seven production suites',
               'seed_manifest_sha256': digest(args.seeds), 'seed_review_sha256': args.review_sha256,
               'seed_producer_revision': review['source_revision'],
               'seed_producer_provenance': review['provenance'],
               'baseline_revision': revision, 'dirty': provenance['dirty'],
               'source_provenance_file_sha256': digest(output / 'source-provenance.json'),
               'input_identity': 'revision plus exact package source hashes; dirty inputs permitted',
               'limits': {'run_seconds': run_seconds, 'output_bytes': 2 * 1024**3,
                          'snapshot_bytes': MAX_SNAPSHOT_BYTES, 'command_seconds': command_seconds},
               "source_manifest_file_sha256": source_manifest_digest,
               "source_manifest_sha256": hashlib.sha256(
        json.dumps(sources, sort_keys=True).encode()).hexdigest()}
    write_json(output / 'acceptance.json', receipt)
    try:
        previous = None
        stages = []
        for number in range(3):
            previous = stage(number, repo, output, sources, seeds, previous, budget=budget,
                             command_seconds=command_seconds, origin=provenance['source_origin'])
            stages.append(previous)
            receipt['stages'].append({
                'stage': number, 'manifest': f'stage{number}/manifest.json',
                'manifest_sha256': digest(output / f'stage{number}/manifest.json'),
                'runtime': 'java' if number == 0 else 'flat',
                'products': {PRODUCTS[0]: previous['compiler_hash'], PRODUCTS[1]: previous['airship_hash'],
                             PRODUCTS[2]: previous['driver_hash']}})
            write_json(output / 'acceptance.json', receipt)
        compare_products({key: value for key, value in stages[1].items() if key.endswith('_hash')},
                         {key: value for key, value in stages[2].items() if key.endswith('_hash')})
        if capture_origin(repo, seeds['git'][0]) != provenance['source_origin']:
            raise ValueError('Source local origin changed during acceptance')
        if source_revision(repo, seeds['git'][0]) != revision:
            raise ValueError('Source Git revision changed during acceptance')
        budget.check()
        if inventory(repo / "packages", budget=budget) != {key.removeprefix("packages/"): value for key, value in sources.items()}:
            raise ValueError("Live sources changed during acceptance")
        if digest(source_manifest) != source_manifest_digest:
            raise ValueError("Immutable source manifest changed")
        receipt["status"] = "passed"
    except BaseException as error:
        receipt.update(status="failed", error=str(error))
        raise
    finally:
        write_json(output / "acceptance.json", receipt)


if __name__ == "__main__":
    main()

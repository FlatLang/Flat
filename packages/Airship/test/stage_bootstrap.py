#!/usr/bin/env python3
"""Offline, fail-closed stage0/1/2 compiler acceptance (not a sandbox).

Usage: python3 -B stage_bootstrap.py --repo CHECKOUT --output NEW_DIRECTORY
                                     --seeds SEEDS_JSON

SEEDS_JSON must contain {"path": absolute_path, "sha256": expected_digest}
for node, git, java, airship, java_compiler, java_backend. Tools must be
executables. Airship is an explicitly selected Java-capable bootstrap JS;
java_compiler and java_backend are separately provisioned flatc.jar and
flat-es6.jar. This program never downloads, builds Maven, or trusts local dist.
An independently reviewed seed manifest is the trust root, not a manifest
synthesized from whatever artifacts happen to be present.

Every stage uses a new source snapshot, FLAT_HOME, HOME, and log directory.
Only stage0 has Java jars. All compiler launches in stage1/2 are redirected
from Airship's existing workspace lookup to a verified read-only previous-stage
seed, never from the output being built. The guard verifies this mapping and
never rewrites compiler argv. All subprocesses are serial and audited.
Any failed/missing gate fails the run; success requires stage1/2 byte equality.
Source comparisons use relative manifest keys and exact content hashes; only
known generated/cache directories are excluded (EXCLUDED below). Artifact bytes
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
from contextlib import contextmanager, nullcontext
import shlex
import hashlib
import json
import os
from pathlib import Path
import shutil
import signal
import subprocess
import time

HERE = Path(__file__).resolve().parent
PRODUCTS = ("packages/Flat-Compiler/dist/es6/index.js", "packages/Airship/dist/airship.js")
EXCLUDED = {".git", "dist", "target", "node_modules", "__pycache__"}
MAX_FILES = 100000


def digest(path):
    value = hashlib.sha256()
    with Path(path).open("rb") as source:
        for block in iter(lambda: source.read(1024 * 1024), b""):
            value.update(block)
    return value.hexdigest()


def verified_seed(entry):
    path = Path(entry["path"])
    if not path.is_absolute() or not path.is_file() or path.stat().st_size == 0:
        raise ValueError(f"Seed must be a nonempty absolute file: {path}")
    path = path.resolve()
    if digest(path) != entry["sha256"]:
        raise ValueError(f"Seed digest mismatch: {path}")
    return path


def fresh_directory(path):
    Path(path).mkdir(parents=True, exist_ok=False)


def inventory(root):
    """Finite metadata manifest; file contents are hashed in streaming blocks."""
    result = {}
    for directory, directories, files in os.walk(root, followlinks=False):
        directories[:] = sorted(name for name in directories if name not in EXCLUDED)
        for name in directories + sorted(files):
            path = Path(directory) / name
            if path.is_symlink():
                raise ValueError(f"Source symlinks are not admitted: {path}")
            if path.is_file():
                key = path.relative_to(root).as_posix()
                if len(result) >= MAX_FILES:
                    raise ValueError("Source manifest exceeds file capacity")
                result[key] = digest(path)
    return dict(sorted(result.items()))


def artifacts(root):
    result = {}
    for name in PRODUCTS:
        path = root / name
        if not path.is_file() or path.is_symlink() or not path.stat().st_size:
            raise ValueError(f"Missing fresh stage product: {path}")
        result[name] = digest(path)
    return result


def compare_products(first, second):
    if first != second:
        raise ValueError("Stage1/2 artifact hashes differ; reproducible self-host gate failed")


def write_json(path, value):
    path.write_text(json.dumps(value, indent=2, sort_keys=True) + "\n")


def run_command(command, cwd, env, log, journal, timeout=1200):
    """One process group at a time; disk-backed logs, finite time/output budgets."""
    with log.open("xb") as output:
        process = subprocess.Popen(command, cwd=cwd, env=env, stdout=output,
                                   stderr=subprocess.STDOUT, start_new_session=True)
        deadline = time.monotonic() + timeout
        try:
            while process.poll() is None:
                if time.monotonic() > deadline or log.stat().st_size > 100 * 1024 * 1024 or (
                        journal.exists() and journal.stat().st_size > 16 * 1024 * 1024):
                    raise ValueError(f"Command exceeded time/log capacity: {log}")
                time.sleep(0.05)
            if process.returncode:
                raise ValueError(f"Command exited {process.returncode}: {command}; see {log}")
        finally:
            # Also terminate surviving descendants of a parent that already exited.
            try:
                os.killpg(process.pid, signal.SIGTERM)
            except ProcessLookupError:
                pass
            try:
                process.wait(timeout=2)
            except subprocess.TimeoutExpired:
                os.killpg(process.pid, signal.SIGKILL)
                process.wait()
            try:
                os.killpg(process.pid, signal.SIGKILL)
            except ProcessLookupError:
                pass
    if log.stat().st_size > 100 * 1024 * 1024:
        raise ValueError(f"Command exceeded log capacity: {log}")
    if journal.exists():
        if journal.stat().st_size > 16 * 1024 * 1024:
            raise ValueError("Subprocess admission journal exceeded capacity")
        with journal.open() as source:
            for line in source:
                if json.loads(line)["decision"] == "reject":
                    raise ValueError(f"Rejected subprocess (even if caught): {journal}")


def snapshot(repo, destination, manifest):
    fresh_directory(destination)
    for name, expected in manifest.items():
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
    if digest(source) != expected:
        raise ValueError(f"Producer seed changed: {source}")
    destination.parent.mkdir(parents=True, exist_ok=True)
    shutil.copyfile(source, destination)
    destination.chmod(0o444)
    if digest(destination) != expected:
        raise ValueError(f"Copied seed digest mismatch: {destination}")
    return destination


@contextmanager
def compiler_mapping(lookup, seed, backup, producing=False):
    """Use Airship's filesystem lookup unchanged, never guard argv rewriting.

    Preserve a newly built compiler outside its lookup path while the producer
    seed is admitted. Only the compiler build may replace the seed facade.
    """
    preserved = lookup.is_file() and not lookup.is_symlink()
    if preserved:
        if backup.exists():
            raise ValueError("Compiler mapping backup already exists")
        lookup.rename(backup)
    if lookup.is_symlink():
        if lookup.resolve() != seed:
            raise ValueError("Compiler facade points at wrong seed")
    else:
        lookup.parent.mkdir(parents=True, exist_ok=True)
        lookup.symlink_to(seed)
    try:
        yield
        if not producing and (not lookup.is_symlink() or lookup.resolve() != seed):
            raise ValueError("Non-compiler command replaced seed facade")
    finally:
        if preserved:
            if lookup.is_file() or lookup.is_symlink():
                lookup.unlink()
            backup.rename(lookup)


def verify_resolution(log, expected):
    """Verify real Airship --debug output, not a reimplementation of resolution.

    No ANSI/path normalization or substring matches: exactly one printed argv
    must start with the existing `node <workspace compiler>` public contract.
    """
    matches = []
    if not log.is_file():
        raise ValueError("Missing real Airship resolution log")
    with log.open() as source:
        for line in source:
            try:
                words = shlex.split(line)
            except ValueError:
                continue
            if words and words[0] == 'node':
                matches.append(words)
    if len(matches) != 1 or len(matches[0]) < 2 or matches[0][1] != str(expected):
        raise ValueError("Real Airship debug compiler lookup does not match seed facade")
    return matches[0]


def stage(number, repo, output, sources, seeds, previous=None):
    directory = output / f"stage{number}"
    fresh_directory(directory)
    workspace = directory / "workspace"
    snapshot(repo, workspace, sources)
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
        copy_seed(seeds["java_backend"][0], workspace / "packages/Flat-ES6/target/flat-es6.jar",
                  seeds["java_backend"][1])
    else:
        airship = copy_seed(previous["airship"], seed_dir / "airship.js", previous["airship_hash"])
        compiler_seed = copy_seed(previous["compiler"], seed_dir / "flatc.js", previous["compiler_hash"])
        compiler_hash = previous["compiler_hash"]
        jar = None
    # Existing Airship versions may use FLAT_HOME links instead of workspace
    # resolution. Both point at this stage's source copy, never the live checkout.
    for package in sorted((workspace / "packages").iterdir()):
        if package.is_dir() and (package / "flat.json").is_file():
            link = flat_home / "packages/github.com/FlatLang" / package.name / "[link]"
            link.parent.mkdir(parents=True)
            link.symlink_to(package, target_is_directory=True)
    mapped_compiler = workspace / PRODUCTS[0]
    if compiler_seed:
        # Airship checks existence at its normal lookup path before spawning.
        # This facade satisfies that check; Node follows the symlink normally.
        # The guard verifies realpath/hash and does not substitute another argv.
        # A successful compiler must atomically replace it;
        # artifacts() rejects a leftover facade as stale output.
        mapped_compiler.parent.mkdir(parents=True, exist_ok=True)
        mapped_compiler.symlink_to(compiler_seed)
    journal = directory / "admissions.jsonl"
    policy = {
        "stage": number, "root": str(workspace), "node": str(seeds["node"][0]),
        "nodeSha256": seeds["node"][1], "git": str(seeds["git"][0]), "gitSha256": seeds["git"][1],
        "java": str(seeds["java"][0]) if number == 0 else None,
        "javaSha256": seeds["java"][1] if number == 0 else None,
        "javaJar": str(jar) if jar else None, "javaJarSha256": seeds["java_compiler"][1] if jar else None,
        "compiler": str(workspace / PRODUCTS[0]), "seed": str(compiler_seed) if compiler_seed else None,
        "seedSha256": compiler_hash, "airship": str(airship), "airshipSha256": digest(airship),
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
           "AIRSHIP_LOCK_FILE_ENABLED": "false", "NODE_NO_WARNINGS": "1",
           "FLAT_BOOTSTRAP_POLICY": str(policy_file), "NODE_OPTIONS": "--require=" + str(guard)}
    manifest = {"stage": number, "status": "running", "sources": sources,
                "policy": policy, "commands": []}
    manifest_file = directory / "manifest.json"
    write_json(manifest_file, manifest)

    fresh_tests = {}

    def execute(package, operation, source=None, runner=airship):
        if runner == airship and digest(runner) != policy["airshipSha256"]:
            raise ValueError("Immutable Airship seed changed")
        if compiler_seed and digest(compiler_seed) != compiler_hash:
            raise ValueError("Immutable compiler seed changed")
        if digest(seeds["node"][0]) != seeds["node"][1]:
            raise ValueError("Pinned Node executable changed")
        command = [str(seeds["node"][0]), str(runner), "--use-links", "-f",
                   str(workspace / "packages" / package), "--flatc-runtime",
                   "java" if number == 0 else "flat", operation]
        if source:
            command.append(source)
        command += ["-t", "es6"]
        if jar and operation == "install":
            command += ["--flatc-jar", str(jar)]
        manifest["commands"].append({"argv": command, "status": "running"})
        write_json(manifest_file, manifest)
        dist = workspace / "packages" / package / "dist"
        if operation == "install" and source == "test":
            if dist.exists() and any(dist.rglob("test.js")):
                raise ValueError("Test output already exists before its build gate")
        offset = journal.stat().st_size if journal.exists() else 0
        mapping = compiler_mapping(mapped_compiler, compiler_seed, seed_dir / 'built-compiler.js',
                                   producing=operation == 'install' and source == 'flatc') if compiler_seed else nullcontext()
        with mapping:
            if number > 0 and operation == 'install':
                resolution_log = logs / f"{len(manifest['commands']):02d}-resolution.log"
                run_command(command + ['--debug'], workspace / 'packages' / package,
                            env, resolution_log, journal)
                manifest['commands'][-1]['resolution_argv'] = verify_resolution(resolution_log, mapped_compiler)
                manifest['commands'][-1]['resolution_log_sha256'] = digest(resolution_log)
                write_json(manifest_file, manifest)
            run_command(command, workspace / "packages" / package, env,
                        logs / f"{len(manifest['commands']):02d}.log", journal)
        records = []
        if journal.exists():
            with journal.open() as source_log:
                source_log.seek(offset)
                records = [json.loads(line) for line in source_log]
        if operation == "install":
            expected = str(seeds["java"][0]) if number == 0 else str(seeds["node"][0])
            expected_script = str(jar) if number == 0 else str(mapped_compiler)
            if not any(record["command"] == expected and expected_script in record["args"]
                       for record in records):
                raise ValueError("Install did not invoke the admitted compiler seed")
            if source == "test":
                generated = list(dist.rglob("test.js")) if dist.exists() else []
                if not generated or any(path.is_symlink() or not path.stat().st_size for path in generated):
                    raise ValueError("Test installation did not produce a fresh test.js")
                fresh_tests[package] = {str(path): digest(path) for path in generated}
        else:
            executed = [Path(record["args"][0]) for record in records
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
        manifest["commands"][-1].update(status="passed", log_sha256=digest(logs / f"{len(manifest['commands']):02d}.log"))
        write_json(manifest_file, manifest)

    try:
        execute("Flat-Compiler", "install", "flatc")
        execute("Airship", "install")
        products = artifacts(workspace)
        # Tests are compiled with the producer seed, run through the newly built
        # Airship, and must include their real implicit Core dependency unchanged.
        for package in ("Flat-Compiler", "Airship"):
            execute(package, "install", "test")
            execute(package, "run", "test", runner=workspace / PRODUCTS[1])
        if artifacts(workspace) != products:
            raise ValueError("Acceptance tests changed stage tool outputs")
        if inventory(workspace) != sources:
            raise ValueError("Build changed source/dependency snapshot")
        if digest(airship) != policy["airshipSha256"] or (compiler_seed and digest(compiler_seed) != compiler_hash):
            raise ValueError("Build mutated an immutable seed")
        manifest.update(status="passed", products=products)
        return {"compiler": workspace / PRODUCTS[0], "compiler_hash": products[PRODUCTS[0]],
                "airship": workspace / PRODUCTS[1], "airship_hash": products[PRODUCTS[1]]}
    except BaseException as error:
        if manifest["commands"] and manifest["commands"][-1]["status"] == "running":
            manifest["commands"][-1]["status"] = "failed"
        manifest.update(status="failed", error=str(error))
        raise
    finally:
        write_json(manifest_file, manifest)


def main():
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("--repo", required=True, type=Path)
    parser.add_argument("--output", required=True, type=Path)
    parser.add_argument("--seeds", required=True, type=Path)
    args = parser.parse_args()
    repo, output = args.repo.resolve(), args.output.resolve()
    if output == repo or repo in output.parents:
        parser.error("--output must be a new directory outside the checkout")
    entries = json.loads(args.seeds.read_text())
    names = {"node", "git", "java", "airship", "java_compiler", "java_backend"}
    if set(entries) != names:
        parser.error("--seeds requires exactly: " + ", ".join(sorted(names)))
    seeds = {name: (verified_seed(entries[name]), entries[name]["sha256"]) for name in sorted(names)}
    for name in ("node", "git", "java"):
        if not os.access(seeds[name][0], os.X_OK):
            parser.error(f"{name} must be executable")
    # Snapshot only package inputs; do not import existing ignored/generated outputs.
    sources = {"packages/" + name: value for name, value in inventory(repo / "packages").items()}
    if not sources:
        parser.error("No package sources found")
    fresh_directory(output)
    write_json(output / "seeds.json", entries)
    source_manifest = output / "sources.json"
    write_json(source_manifest, sources)
    source_manifest.chmod(0o444)
    source_manifest_digest = digest(source_manifest)
    receipt = {"status": "running", "source_manifest_file_sha256": source_manifest_digest,
               "source_manifest_sha256": hashlib.sha256(
        json.dumps(sources, sort_keys=True).encode()).hexdigest()}
    try:
        previous = None
        stages = []
        for number in range(3):
            previous = stage(number, repo, output, sources, seeds, previous)
            stages.append(previous)
        compare_products({key: value for key, value in stages[1].items() if key.endswith('_hash')},
                         {key: value for key, value in stages[2].items() if key.endswith('_hash')})
        if inventory(repo / "packages") != {key.removeprefix("packages/"): value for key, value in sources.items()}:
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

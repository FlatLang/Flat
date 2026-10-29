#!/usr/bin/env python3
"""Runner settlement regressions. Initial swarm: authored, deliberately NOT RUN."""
import importlib.util
import json
import os
from pathlib import Path
import signal
import subprocess
import sys
import tempfile
import unittest
from unittest import mock

HERE = Path(__file__).resolve().parent
SPEC = importlib.util.spec_from_file_location('runner_settlement_bootstrap', HERE / 'stage_bootstrap.py')
bootstrap = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(bootstrap)


def identity(start='123'):
    return dict(pid=43210, pgid=43210, session=43210, start=start,
                state='running', source='controlled-kernel')


class Process:
    pid = 43210
    returncode = None

    def __init__(self, known=True, wait_error=None):
        if known:
            self._stage_identity = identity()
        self.wait_error = wait_error
        self.waits = []

    def wait(self, timeout):
        self.waits.append(timeout)
        if self.wait_error:
            raise self.wait_error
        self.returncode = 0
        return 0


class RunnerSettlementTests(unittest.TestCase):
    def settle(self, process, killpg):
        # A controlled clock drives every deadline, with no timing sleeps.
        clock = iter(range(100))
        observed = {}
        with mock.patch.object(bootstrap.time, 'monotonic', side_effect=lambda: next(clock)), \
                mock.patch.object(bootstrap.time, 'sleep'), \
                mock.patch.object(bootstrap, 'process_identity', return_value=identity()), \
                mock.patch.object(bootstrap.os, 'killpg', side_effect=killpg):
            try:
                bootstrap.settle_process_group(process, observed)
            except ValueError:
                self.assertEqual(observed['status'], 'cleanup-unknown')
            else:
                self.assertEqual(observed['status'], 'settled')
        self.assertEqual(process.waits, [2])
        return observed

    def test_permission_denied_still_waits_and_remains_unknown_after_exit_zero(self):
        delivered = []
        def denied(pgid, signum):
            delivered.append(signum)
            raise PermissionError(1, 'controlled EPERM')
        process = Process()
        observed = self.settle(process, denied)
        self.assertEqual(process.returncode, 0)
        self.assertTrue(observed['child_reaped'])
        self.assertTrue(observed['cleanup_unknown'])
        self.assertFalse(observed['process_group_absent'])
        self.assertIn(signal.SIGTERM, delivered)
        self.assertIn(signal.SIGKILL, delivered)
        self.assertLessEqual(len(observed['errors']), 11)

    def test_unknown_identity_never_probes_or_signals_guessed_group(self):
        killpg = mock.Mock(side_effect=AssertionError('must not use guessed PGID'))
        observed = self.settle(Process(known=False), killpg)
        killpg.assert_not_called()
        self.assertTrue(observed['cleanup_unknown'])
        self.assertTrue(observed['child_reaped'])

    def test_reused_pid_identity_withholds_both_signals(self):
        process = Process()
        process._stage_identity = identity('old-start')
        calls = []
        def probe(pgid, signum):
            calls.append(signum)
            if signum != 0:
                self.fail('signal to reused PID')
        observed = self.settle(process, probe)
        self.assertTrue(observed['cleanup_unknown'])
        self.assertTrue(calls)
        self.assertEqual(set(calls), {0})

    def test_wait_timeout_is_finite_explicit_unknown(self):
        process = Process(wait_error=subprocess.TimeoutExpired('controlled', 2))
        def absent(pgid, signum):
            raise ProcessLookupError()
        observed = self.settle(process, absent)
        self.assertFalse(observed['wait_completed'])
        self.assertFalse(observed['child_reaped'])
        self.assertTrue(observed['process_group_absent'])
        self.assertTrue(observed['cleanup_unknown'])

    def test_confirmed_absence_and_reap_settle(self):
        def absent(pgid, signum):
            raise ProcessLookupError()
        observed = self.settle(Process(), absent)
        self.assertTrue(observed['wait_completed'])
        self.assertTrue(observed['process_group_absent'])
        self.assertFalse(observed['cleanup_unknown'])

    def test_exited_child_reaped_before_group_probe_and_eperm_stays_unknown(self):
        for denied in (False, True):
            process = Process()
            events = []
            original_wait = process.wait
            def wait(timeout):
                events.append('wait')
                return original_wait(timeout)
            process.wait = wait
            def probe(pgid, signum):
                events.append(('probe', signum))
                self.assertEqual(signum, 0, 'No signal to exited zombie-only group')
                if process.returncode is None:
                    raise PermissionError('controlled Darwin zombie-only group')
                if denied:
                    raise PermissionError('controlled Darwin post-reap EPERM')
                raise ProcessLookupError()
            observed = {}
            clock = iter(range(100))
            with self.subTest(denied=denied), \
                 mock.patch.object(bootstrap, 'process_finished', return_value=True), \
                 mock.patch.object(bootstrap.os, 'killpg', side_effect=probe), \
                 mock.patch.object(bootstrap.time, 'monotonic', side_effect=lambda: next(clock)), \
                 mock.patch.object(bootstrap.time, 'sleep'):
                if denied:
                    with self.assertRaisesRegex(ValueError, 'cleanup-unknown'):
                        bootstrap.settle_process_group(process, observed)
                else:
                    bootstrap.settle_process_group(process, observed)
            self.assertIn('wait', events)
            self.assertEqual(observed['cleanup_unknown'], denied)
            self.assertEqual(observed['process_group_absent'], not denied)

    def test_reaped_process_is_never_signal_owner(self):
        process = Process()
        process.returncode = 0
        with mock.patch.object(bootstrap, 'process_identity') as read:
            self.assertFalse(bootstrap.owned_process_identity(process))
        read.assert_not_called()

    def test_completion_rechecks_darwin_terminal_after_identity_read(self):
        process = Process()
        process._stage_identity = dict(identity(), source='proc-pidinfo')
        # Terminal readiness appears only after libproc returns changed exit metadata.
        with mock.patch.object(bootstrap, 'unreaped_child_finished', side_effect=[False, True]), \
             mock.patch.object(bootstrap, 'process_identity', return_value=dict(identity(), session=0)), \
             mock.patch.object(bootstrap.os, 'killpg') as signal_group:
            self.assertTrue(bootstrap.process_finished(process))
        signal_group.assert_not_called()
        self.assertIsNone(process.returncode)
        self.assertEqual(process.waits, [])
        self.assertEqual(process._stage_identity_observation['observed']['session'], 0)

    def test_completion_retries_metadata_gap_but_not_persistent_identity_change(self):
        process = Process()
        process._stage_identity = dict(identity(), source='proc-pidinfo')
        for observed in (dict(process._stage_identity, start='reused'),
                         PermissionError(1, 'denied'), ProcessLookupError(3, 'exit metadata gone')):
            with self.subTest(observed=observed), \
                 mock.patch.object(bootstrap, 'unreaped_child_finished', return_value=False), \
                 mock.patch.object(bootstrap, 'process_identity', side_effect=(
                     [observed] * 6 if not isinstance(observed, Exception) else observed)), \
                 mock.patch.object(bootstrap.time, 'sleep') as pause, \
                 mock.patch.object(bootstrap.os, 'killpg') as signal_group:
                with self.assertRaisesRegex(ValueError, 'identity unavailable or changed'):
                    bootstrap.process_finished(process)
                self.assertEqual(pause.call_count, 5)
                signal_group.assert_not_called()
                self.assertEqual(process.waits, [])
        with mock.patch.object(bootstrap, 'unreaped_child_finished', side_effect=[False, False, True]), \
             mock.patch.object(bootstrap, 'process_identity', side_effect=ProcessLookupError(3, 'transition')), \
             mock.patch.object(bootstrap.time, 'sleep'):
            self.assertTrue(bootstrap.process_finished(process))

    def test_completion_uses_one_consistent_live_identity_sample(self):
        process = Process()
        with mock.patch.object(bootstrap, 'process_identity', side_effect=[identity(), dict(identity(), start='other')]) as read:
            self.assertFalse(bootstrap.process_finished(process))
        self.assertEqual(read.call_count, 1)

    def test_zombie_completion_check_does_not_poll_or_reap(self):
        process = Process()
        with mock.patch.object(bootstrap, 'process_identity',
                               return_value={**identity(), 'state': 'Z'}):
            self.assertTrue(bootstrap.process_finished(process))
        self.assertIsNone(process.returncode)
        self.assertEqual(process.waits, [])

    def test_receipt_is_fsynced_and_old_unknown_receipt_is_immutable(self):
        with tempfile.TemporaryDirectory() as directory:
            receipt = Path(directory) / 'unknown.json'
            original = b'{"status":"UNKNOWN","historical":true}\n'
            receipt.write_bytes(original)
            with self.assertRaises(FileExistsError):
                bootstrap.durable_process_receipt(receipt, {'status': 'settled'})
            self.assertEqual(receipt.read_bytes(), original)
            with mock.patch.object(bootstrap.os, 'fsync') as sync:
                bootstrap.durable_process_receipt(Path(directory) / 'fresh.json', identity())
            self.assertEqual(sync.call_count, 2)

    def test_scope_pipes_close_even_when_scope_receipt_write_fails(self):
        channel = mock.Mock()
        channel.env = {}
        channel.scope = 'controlled-scope'
        channel.cancelled = False
        channel.hellos = channel.frames = 0
        channel.events = []
        channel.started = channel.finished = {}
        with mock.patch.object(bootstrap, 'ScopeChannel', return_value=channel), \
                mock.patch.object(bootstrap, '_run_command', return_value=0), \
                mock.patch.object(bootstrap, 'write_json', side_effect=OSError('disk full')):
            with self.assertRaisesRegex(OSError, 'disk full'):
                bootstrap.run_command(['controlled'], HERE, {'FLAT_BOOTSTRAP_POLICY': 'unused'},
                                      HERE / 'unused-log', HERE / 'unused-journal')
        channel.close.assert_called_once_with()

    def test_identity_capture_failure_still_waits_and_writes_unknown_receipt(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            process = Process(known=False)
            clock = iter(range(100))
            with mock.patch.object(bootstrap.subprocess, 'Popen', return_value=process), \
                    mock.patch.object(bootstrap, 'process_identity', side_effect=OSError('identity denied')), \
                    mock.patch.object(bootstrap.time, 'monotonic', side_effect=lambda: next(clock)), \
                    mock.patch.object(bootstrap.time, 'sleep'), \
                    mock.patch.object(bootstrap.os, 'killpg') as signal_group:
                with self.assertRaisesRegex(ValueError, 'cleanup-unknown'):
                    bootstrap.run_command(['controlled'], root, {}, root / 'log', root / 'journal')
            signal_group.assert_not_called()
            self.assertEqual(process.waits, [2])
            terminal = json.loads((root / 'log.settlement.json').read_text())
            self.assertEqual(terminal['status'], 'cleanup-unknown')
            self.assertIsNone(terminal['identity'])
            self.assertTrue(terminal['child_reaped'])
            self.assertTrue(terminal['cleanup_unknown'])

    def test_spawn_receipt_failure_still_finalizes_owned_group(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            process = Process()
            observed = {}
            original = bootstrap.durable_process_receipt
            def persist(path, value):
                if str(path).endswith('.process.json'):
                    raise OSError('controlled persistence failure')
                original(path, value)
            with mock.patch.object(bootstrap.subprocess, 'Popen', return_value=process), \
                    mock.patch.object(bootstrap, 'process_identity', return_value=identity()), \
                    mock.patch.object(bootstrap, 'durable_process_receipt', side_effect=persist), \
                    mock.patch.object(bootstrap.os, 'killpg', side_effect=ProcessLookupError):
                with self.assertRaisesRegex(OSError, 'controlled persistence failure'):
                    bootstrap.run_command(['controlled'], root, {}, root / 'log', root / 'journal',
                                          cleanup_observation=observed)
            self.assertEqual(process.waits, [2])
            self.assertTrue(observed['child_reaped'])
            terminal = json.loads((root / 'log.settlement.json').read_text())
            self.assertEqual(terminal['status'], 'settled')

    @unittest.skipUnless(sys.platform.startswith('linux') or sys.platform == 'darwin',
                         'requires supported kernel start identity')
    def test_fast_natural_exit_is_pinned_until_cleanup_and_preserves_nonzero(self):
        for code in (0, 7):
            with self.subTest(code=code), tempfile.TemporaryDirectory() as directory:
                root = Path(directory)
                observed = {}
                def invoke():
                    return bootstrap.run_command([sys.executable, '-I', '-c',
                        f'raise SystemExit({code})'], root, os.environ.copy(), root / 'log',
                        root / 'journal', timeout=10, cleanup_observation=observed)
                if code:
                    with self.assertRaises(bootstrap.CommandFailure) as raised:
                        invoke()
                    self.assertEqual(raised.exception.exit_code, code)
                else:
                    self.assertEqual(invoke(), 0)
                self.assertEqual(observed['exit_code'], code)
                self.assertEqual(observed['status'], 'settled')
                self.assertTrue(observed['child_reaped'])
                self.assertTrue(observed['process_group_absent'])

    @unittest.skipUnless(sys.platform.startswith('linux') or sys.platform == 'darwin',
                         'requires supported kernel start identity')
    def test_controlled_child_identity_persisted_before_gate_and_cancel_settled(self):
        # Child only waits for TERM in its own new session. Readiness uses a FIFO,
        # not a sleep, and no compiler, Java, network, or historical PID is used.
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            ready = root / 'ready'
            os.mkfifo(ready)
            fd = os.open(ready, os.O_RDWR | os.O_NONBLOCK)
            log = root / 'child.log'
            observed = {}
            fixture = ('import os,signal,sys\n'
                       'with open(sys.argv[1],"wb",buffering=0) as f: f.write(b"R")\n'
                       'while True: signal.pause()\n')
            def gate(process):
                receipt = json.loads(log.with_suffix('.log.process.json').read_text())
                self.assertEqual(receipt['identity']['pid'], process.pid)
                self.assertEqual(receipt['identity']['pgid'], process.pid)
                self.assertEqual(receipt['identity']['session'], process.pid)
                self.assertTrue(receipt['identity']['start'])
                readable, _, _ = bootstrap.select.select([fd], [], [], 5)
                self.assertTrue(readable, 'controlled child did not become ready')
                self.assertEqual(os.read(fd, 1), b'R')
                raise ValueError('controlled gate cancellation')
            try:
                with self.assertRaisesRegex(ValueError, 'controlled gate cancellation'):
                    bootstrap.run_command([sys.executable, '-I', '-c', fixture, str(ready)],
                        root, os.environ.copy(), log, root / 'journal', timeout=10,
                        ready_gate=gate, cleanup_observation=observed)
            finally:
                os.close(fd)
            terminal = json.loads(log.with_suffix('.log.settlement.json').read_text())
            self.assertEqual(terminal['status'], 'settled')
            self.assertTrue(terminal['child_reaped'])
            self.assertTrue(terminal['process_group_absent'])
            self.assertFalse(terminal['cleanup_unknown'])


if __name__ == '__main__':
    unittest.main()

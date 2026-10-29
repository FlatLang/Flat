"""Production workload boundary regressions. No compiler or child process is launched."""
from pathlib import Path
import tempfile
import unittest
from unittest import mock

import dogfood_acceptance as gate


class DriverWorkloadTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        self.workspace = self.root / 'workspace'
        (self.workspace / 'packages/Flat-Core').mkdir(parents=True)
        self.driver = self.root / 'production-driver.js'
        self.compiler = self.root / 'compiler.js'
        self.node = self.root / 'node'
        for path in (self.driver, self.compiler, self.node):
            path.write_text(path.name)
        self.context = {'workspace': self.workspace, 'compiler': self.compiler,
                        'policy': {'seedSha256': gate.bootstrap.digest(self.compiler)},
                        'env': {}, 'journal': self.root / 'journal'}
        self.seeds = {'node': (self.node, gate.bootstrap.digest(self.node))}
        self.destination = self.workspace / 'packages/Flat-Core/dist/test.js'
        self.exit_code = 0
        self.process_absent = True
        self.publish = False
        self.staging = False
        self.mutate_driver = False
        self.mutate_sources = False

    def command(self, argv, cwd, env, log, journal, **kwargs):
        kwargs['cleanup_observation'].update(pid=123, exit_code=self.exit_code,
            wait_completed=True, child_reaped=True, process_group_absent=self.process_absent)
        if self.publish or self.staging:
            self.destination.parent.mkdir(parents=True, exist_ok=True)
            path = self.destination if self.publish else self.destination.parent / '.flat-tmp-owned'
            path.write_text('retained')
        if self.mutate_driver:
            self.driver.write_text('changed driver')
        if self.mutate_sources:
            self.sources['packages/Flat-Core/src/Input.flat'] = '2' * 64
        if self.exit_code:
            raise gate.bootstrap.CommandFailure(argv, self.exit_code, log)

    def invoke(self, scenario='forward', capacity=1):
        self.sources = {'packages/Flat-Core/src/Input.flat': '1' * 64}
        with mock.patch.object(gate, 'scenario_sources', side_effect=lambda *args: dict(self.sources)), \
                mock.patch.object(gate.bootstrap, 'run_command', side_effect=self.command) as command:
            result = gate.run_driver_workload(self.context, self.seeds, None, 60,
                self.driver, gate.bootstrap.digest(self.driver), scenario, capacity,
                ['src', '-o', 'test.js'], self.destination, self.root / 'run.log')
            self.assertEqual(command.call_args.args[0][:4],
                             [str(self.node), str(self.driver), scenario, str(capacity)])
            return result

    def test_all_existing_eight_driver_workloads_preserve_selection(self):
        for scenario in gate.DRIVER_SCENARIOS:
            for capacity in (1, 3):
                with self.subTest(scenario=scenario, capacity=capacity):
                    self.exit_code = 1 if scenario.startswith('cancel-') else 0
                    cleanup, staging = self.invoke(scenario, capacity)
                    self.assertTrue(cleanup['child_reaped'])
                    self.assertEqual(staging, [])

    def test_cancellation_must_exit_cancelled(self):
        with self.assertRaisesRegex(ValueError, 'terminal exit mismatch'):
            self.invoke('cancel-after-read-forward')

    def test_cancelled_invocation_must_not_publish(self):
        self.exit_code, self.publish = 1, True
        with self.assertRaisesRegex(ValueError, 'published terminal output'):
            self.invoke('cancel-after-stage-reverse')

    def test_terminal_staging_is_rejected_not_deleted(self):
        self.staging = True
        with self.assertRaisesRegex(ValueError, 'retained terminal staging'):
            self.invoke()
        self.assertTrue((self.destination.parent / '.flat-tmp-owned').is_file())

    def test_child_reaping_does_not_substitute_for_process_group_absence(self):
        self.process_absent = False
        with self.assertRaisesRegex(ValueError, 'process_group_absent'):
            self.invoke()

    def test_driver_is_pinned_after_execution(self):
        self.mutate_driver = True
        with self.assertRaisesRegex(ValueError, 'input tool changed'):
            self.invoke()

    def test_sources_are_pinned_across_each_invocation(self):
        self.mutate_sources = True
        with self.assertRaisesRegex(ValueError, 'changed source inputs'):
            self.invoke()

    def test_failure_is_not_misclassified_as_cancellation(self):
        self.exit_code = 2
        with self.assertRaises(gate.bootstrap.CommandFailure):
            self.invoke('cancel-after-stage-reverse')

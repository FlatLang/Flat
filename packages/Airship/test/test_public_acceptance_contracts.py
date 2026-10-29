"""Public delivery receipt contracts; no compiler execution."""
import copy
import json
import shutil
from pathlib import Path
import tempfile
import unittest
from unittest import mock
import dogfood_acceptance as gate


class PublicAcceptanceContracts(unittest.TestCase):
    def test_missing_public_workloads_and_invalidations_fail_closed(self):
        for value in (None, {}, {'core-lr': {}}, {'unknown': {}}):
            with self.subTest(value=value), self.assertRaises(ValueError):
                gate.validate_public_workloads(Path('/receipt'), value, {}, {}, {})
        for value in (None, {}, {'dependency': {}}, {'unknown': {}}):
            with self.subTest(value=value), self.assertRaises(ValueError):
                gate.validate_invalidations(Path('/receipt'), value, {}, {}, {})
        verdict = gate.production_gates({'status': 'passed', 'full_acceptance': True})
        self.assertFalse(verdict['gates']['public-production-workloads'])
        for name in gate.INVALIDATIONS:
            self.assertFalse(verdict['gates']['incremental-' + name])

    def test_stage_public_workloads_are_separate_and_not_suite_replacements(self):
        import stage_bootstrap as bootstrap
        with mock.patch.object(gate, 'create_production_workspace', side_effect=[{'id': 1}, {'id': 2}]) as create, \
             mock.patch.object(gate, 'run_public_workload', side_effect=[{'receipt': 1}, {'receipt': 2}]) as run:
            result = bootstrap.stage_public_workloads(Path('/repo'), Path('/out'), Path('/out/stage2'),
                                                      {'sources': 'hash'}, {}, {}, None, 60)
        self.assertEqual(set(result), {'airship-toolchain', 'core-lr'})
        self.assertEqual([call.args[1] for call in run.call_args_list], ['airship-toolchain', 'core-lr'])
        self.assertEqual(create.call_count, 2)
        self.assertEqual(len(bootstrap.REQUIRED_SUITES), 7)

    def test_public_producer_builds_fresh_public_groups_and_validator_rejects_forgery(self):
        temporary = tempfile.TemporaryDirectory()
        self.addCleanup(temporary.cleanup)
        root = Path(temporary.name).resolve()
        values = {}
        for name in ('airship-toolchain', 'core-lr'):
            with self.subTest(name=name):
                directory = root / ('public-' + name)
                workspace = directory / 'workspace'
                files = {'packages/Flat-Core/flat.json': json.dumps({'sources': {'main': {'source': 'src'}}}),
                         'packages/Flat-Core/src/Real.flat': 'real production source',
                         'packages/Flat-Core/workloads/ProductionCoreWorkload.flat': 'real workload',
                         'packages/Airship/src/flat/airship/AirshipToolchain_Test.flat': 'fixture source'}
                for relative, contents in files.items():
                    path = workspace / relative
                    path.parent.mkdir(parents=True, exist_ok=True)
                    path.write_text(contents)
                shutil.copyfile(Path(__file__).parent.parent / 'src/flat/airship/AirshipToolchain_Test.flat',
                                workspace / 'packages/Airship/src/flat/airship/AirshipToolchain_Test.flat')
                (directory / 'seeds').mkdir()
                compiler, runner, node = directory / 'seeds/flatc.js', directory / 'seeds/airship.js', root / 'node'
                for path in (compiler, runner, node):
                    path.write_text(path.name)
                reference = lambda path: {'path': path.relative_to(root).as_posix(), 'sha256': gate.bootstrap.digest(path)}
                seed = {'path': str(node), 'sha256': gate.bootstrap.digest(node)}
                context = {'workspace': workspace, 'directory': directory, 'runner': runner, 'compiler': compiler,
                           'policy': {}, 'env': {}}
                baseline = gate.scenario_sources(None, workspace)
                cleanup = {'pid': 42, 'exit_code': 0, 'wait_completed': True, 'child_reaped': True,
                           'process_group_absent': True}
                calls = []
                def product(context, argv, cwd, seeds, budget, seconds):
                    calls.append(argv)
                    package, filename = (('Airship', 'toolchain-test.js') if name == 'airship-toolchain'
                                         else ('Flat-Core', 'production-core-workload.js'))
                    destination = workspace / 'packages' / package / 'dist/es6' / filename
                    if len(calls) == 1:
                        self.assertFalse(destination.exists())
                        destination.parent.mkdir(parents=True)
                        destination.write_text('generated fresh artifact')
                    log = directory / ('build.log' if len(calls) == 1 else 'execute.log')
                    log.write_text('production-core-workload:ok\n')
                    journal = log.with_suffix('.journal')
                    journal.write_text(json.dumps({'decision': 'allow', 'command': str(node),
                                                   'args': [str(compiler), 'src']}) + '\n')
                    if len(calls) == 2 and name == 'airship-toolchain':
                        self.assertEqual(context['env']['AIRSHIP_TEST_FLATC_SCRIPT'], str(compiler))
                        selected = workspace / 'packages/Airship/dist/es6/selected-airship.js'
                        self.assertEqual(context['env']['AIRSHIP_TEST_AIRSHIP_SCRIPT'], str(selected))
                        self.assertEqual(selected.read_bytes(), runner.read_bytes())
                        scope = {'schema': 'flat.stage.process-scope.v1', 'ownership': 'external-command-group',
                                 'scope_cancelled': False, 'hello_count': 1, 'frame_count': 8,
                                 'fixtures_started': 8, 'fixtures_finished': 8, 'cleanup': cleanup,
                                 'events': [{'kind': 'fixture-end', 'fixture': fixture,
                                             'outcome': {'exit': code, 'signal': None}}
                                            for fixture, code in [('airship-toolchain-fail', 7), ('airship-toolchain-cli-fail', 7)]
                                            + [('airship-cli-' + label, 1) for label in ('fail', 'invalid', 'unknown', 'missing')]
                                            + [('airship-compile-cli-invalid', 1), ('airship-compile-direct-invalid', 1)]]}
                        log.with_suffix('.log.scope.json').write_text(json.dumps(scope))
                    return {'argv': argv, 'node_sha256': seed['sha256'], 'exit_status': 0,
                            'log': reference(log), 'journal': reference(journal), 'tool': reference(Path(argv[1])),
                            'admission_offset': 0, 'process_cleanup': cleanup, 'temporary_outputs_after': []}
                with mock.patch.object(gate, 'product_command', side_effect=product):
                    result = gate.run_public_workload(context, name, {'node': (node, seed['sha256'])}, None, 30)
                tools = {gate.bootstrap.PRODUCTS[0]: gate.bootstrap.digest(compiler),
                         gate.bootstrap.PRODUCTS[1]: gate.bootstrap.digest(runner)}
                values[name] = result
                self.assertIn('toolchain-test' if name == 'airship-toolchain' else 'install', calls[0])
        # Both ordinary dict entries are validated; no iteration overrides and no
        # mocked validator. These are isolated parser fixtures, not production evidence.
        gate.validate_public_workloads(root, values, baseline, tools, seed)
        changed = copy.deepcopy(values)
        scope_path = root / changed['airship-toolchain']['fixture_scope']['path']
        original_scope = scope_path.read_text()
        scope = json.loads(original_scope)
        scope['events'].pop()
        scope_path.write_text(json.dumps(scope))
        changed['airship-toolchain']['fixture_scope']['sha256'] = gate.bootstrap.digest(scope_path)
        with self.assertRaisesRegex(ValueError, 'Missing actual toolchain fixture outcome'):
            gate.validate_public_workloads(root, changed, baseline, tools, seed)
        scope_path.write_text(original_scope)
        changed = copy.deepcopy(values)
        changed['airship-toolchain']['selected_tools']['airship']['sha256'] = '0' * 64
        with self.assertRaisesRegex(ValueError, 'Explicit public toolchain selection'):
            gate.validate_public_workloads(root, changed, baseline, tools, seed)
        for name in values:
            changed = copy.deepcopy(values)
            changed[name]['execute']['argv'][1] = str(root / 'stale.js')
            with self.subTest(name=name), self.assertRaises(ValueError):
                gate.validate_public_workloads(root, changed, baseline, tools, seed)

    def test_unknown_incremental_execution_kind_is_rejected(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary).resolve()
            with self.assertRaisesRegex(ValueError, 'Unknown production execution kind'):
                gate.validate_production_selection(root, {'execution_kind': 'self-asserted', 'package': 'Flat-Core'},
                                                    'Flat-Core', {}, {})

    def test_incremental_reader_rechecks_tool_and_aliases(self):
        command = {'argv': ['/node', '/receipt/compiler.js'], 'node_sha256': 'a' * 64,
                   'log_reference': {'path': 'a', 'sha256': 'a' * 64}, 'log': {},
                   'admission_reference': {}, 'journal': {}}
        with self.assertRaisesRegex(ValueError, 'aliases changed'):
            gate.validate_incremental_run(Path('/receipt'), {'commands': [command, command]},
                                          Path('/receipt/workspace'), {'test.js': 'a' * 64}, 'b' * 64)

    def test_unknown_scenario_cannot_parse_as_cancel_after_stage(self):
        with self.assertRaisesRegex(ValueError, 'Unknown production driver scenario'):
            gate.production_read_records(Path('/missing'), [], 'cancel-invented', 1, 'a' * 64, Path('/out'))

    def test_schema2_receipt_forwards_independent_digests_without_claiming_acceptance(self):
        class BoundaryReached(Exception):
            pass
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            path = root / 'evidence.json'
            path.write_text(json.dumps({'schema': 2}))
            with mock.patch.object(gate, 'validate_production_evidence', side_effect=BoundaryReached) as validate:
                with self.assertRaises(BoundaryReached):
                    gate.validate_evidence(path, gate.bootstrap.digest(path), root / 'stages',
                                           scaling_plan_sha='b' * 64, review_sha256='a' * 64)
                validate.assert_called_once_with(path, {'schema': 2}, root / 'stages', 'b' * 64, 'a' * 64)
            # No production validators mocked: a self-described receipt cannot
            # supply its own trust-root digest or get as far as stage file reads.
            with self.assertRaisesRegex(ValueError, 'Independent seed review'):
                gate.validate_evidence(path, gate.bootstrap.digest(path), root / 'stages')

    def test_review_digest_is_required_and_forwarded_before_production_reads(self):
        # Unit-only boundary observation; never manufacture a production verdict.
        class BoundaryReached(Exception):
            pass
        with mock.patch.object(gate, 'validate_bootstrap', side_effect=BoundaryReached) as validate:
            with self.assertRaises(BoundaryReached):
                gate.validate_production_evidence(Path('/receipt/evidence.json'), {'schema': 2}, Path('/stages'),
                                                  scaling_plan_sha='b' * 64, review_sha256='a' * 64)
            validate.assert_called_once_with(Path('/stages'), require_public=True, review_sha256='a' * 64)
        for digest in (None, '', True, 'not-a-digest'):
            with self.subTest(digest=digest), mock.patch.object(gate.bootstrap, 'load_reviewed_bundle') as load:
                with self.assertRaisesRegex(ValueError, 'Independent seed review'):
                    gate.validate_production_evidence(Path('/receipt/evidence.json'), {'schema': 2}, Path('/stages'),
                                                      review_sha256=digest)
                load.assert_not_called()

    def test_reviewed_scaling_digest_is_an_external_argument(self):
        import inspect
        self.assertIn('review_sha256', inspect.signature(gate.validate_evidence).parameters)
        self.assertIn('review_sha256', inspect.signature(gate.validate_production_evidence).parameters)
        self.assertIn('review_sha256', inspect.signature(gate.validate_bootstrap).parameters)
        self.assertIn('scaling_plan_sha', inspect.signature(gate.validate_evidence).parameters)
        self.assertIn('scaling_plan_sha', inspect.signature(gate.validate_production_evidence).parameters)


if __name__ == '__main__':
    unittest.main()

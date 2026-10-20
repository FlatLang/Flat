#!/usr/bin/env python3
"""Receipt-only tests: no compiler, Airship, Java, subprocess, or network execution."""
import copy
import json
from pathlib import Path
import tempfile
import unittest

from unittest import mock
import dogfood_acceptance as gate


class AcceptanceTests(unittest.TestCase):
    def setUp(self):
        self.temporary = tempfile.TemporaryDirectory()
        self.addCleanup(self.temporary.cleanup)
        self.root = Path(self.temporary.name)
        self.stages = self.root / 'bootstrap'
        (self.stages / 'stage2').mkdir(parents=True)
        self.sources = {'packages/Flat-Core/flat.json': '1' * 64,
                        'packages/Flat-Core/src/Input.flat': '2' * 64}
        self.tools = {'packages/Flat-Compiler/dist/es6/index.js': '3' * 64,
                      'packages/Airship/dist/airship.js': '4' * 64}
        self.write(self.stages / 'sources.json', self.sources)
        self.write(self.stages / 'acceptance.json', {'schema': 2, 'status': 'passed'})
        for number in range(3):
            workspace = self.stages / f'stage{number}/workspace'
            for name in self.tools:
                path = workspace / name
                path.parent.mkdir(parents=True, exist_ok=True)
                path.write_text('// generated tool\n')
                self.tools[name] = gate.bootstrap.digest(path)
            commands = []
            for suite in gate.SUITES:
                for operation in ('install', 'run'):
                    commands.append({'argv': ['node', 'airship.js', '-f', '/snapshot/packages/' + suite,
                                               operation, 'test'], 'status': 'passed',
                                     'runner_sha256': '1' * 64, 'compiler_sha256': '2' * 64,
                                     'log_sha256': '3' * 64})
            directory = self.stages / f'stage{number}'
            (directory / 'logs').mkdir()
            journal = directory / 'admissions.jsonl'
            journal.write_text('{}\n')
            for index, command in enumerate(commands, 1):
                log = directory / f'logs/{index:02d}.log'
                log.write_text('passed\n')
                command['log_sha256'] = gate.bootstrap.digest(log)
            self.write(self.stages / f'stage{number}/manifest.json', {
                'schema': 2, 'stage': number, 'status': 'passed', 'products': self.tools,
                'runtime': 'java' if number == 0 else 'flat',
                'compiler_selection': 'reviewed-java-seed' if number == 0 else 'explicit-generated-script',
                'commands': commands, 'admissions_sha256': gate.bootstrap.digest(journal)})
        self.options = {'target': 'es6', 'flatc_runtime': 'flat'}
        self.dependencies = {'packages/Flat-Core/flat.json': '1' * 64}
        self.run = {'exit_status': 0, 'argv': ['/pinned/node', '/stage2/airship.js', 'run', 'test'],
                    'log': self.reference('execution.log', 'assertion passed\n'),
                    'diagnostics': self.reference('diagnostics.json', '[]\n'),
                    'subprocess_journal': self.reference('journal.jsonl', json.dumps({
                        'decision': 'allow', 'command': '/pinned/node', 'args': ['/generated/test.js']}) + '\n'),
                    'artifacts': {'test.js': self.reference('test.js', '// generated\n')},
                    'assertions': ['runtime assertion observed'], 'java_invocations': 0}
        self.value = {'schema': 1, 'status': 'passed',
                      'source_manifest_sha256': gate.bootstrap.digest(self.stages / 'sources.json'),
                      'tools': self.tools, 'options': self.options,
                      'options_sha256': gate.fingerprint(self.options), 'dependencies': self.dependencies,
                      'core': dict(copy.deepcopy(self.run), production_inputs=True, package='Flat-Core',
                                   assertions=['Double.equals(Object)']),
                      'suites': {suite: copy.deepcopy(self.run) for suite in gate.SUITES},
                      'incremental': {}, 'schedules': [], 'lifecycle': copy.deepcopy(self.run)}
        for name in gate.SCENARIOS:
            sources = dict(self.sources)
            if name == 'changed':
                sources['packages/Flat-Core/src/Input.flat'] = '5' * 64
            if name == 'removed':
                del sources['packages/Flat-Core/src/Input.flat']
            self.value['incremental'][name] = {
                'sources': sources, 'source_manifest_sha256': gate.fingerprint(sources),
                'options_sha256': self.value['options_sha256'], 'tools': self.tools,
                'dependencies': self.dependencies, 'incremental': copy.deepcopy(self.run),
                'clean': copy.deepcopy(self.run), 'reused_cache': name == 'warm'}
        for workers, order in ((1, 'forward'), (4, 'reverse')):
            self.value['schedules'].append(dict(copy.deepcopy(self.run), concurrency=workers,
                                                completion_order=order, controlled=True))
        self.value['lifecycle'].update(dict.fromkeys((
            'backpressure', 'capacity_limit', 'bounded_in_flight', 'unrelated_progress',
            'failure_isolation', 'cancellation_observed', 'children_reaped', 'temporary_outputs_removed'), True))

    def write(self, path, value):
        path.write_text(json.dumps(value))

    def reference(self, name, text):
        path = self.root / name
        path.write_text(text)
        return {'path': name, 'sha256': gate.bootstrap.digest(path)}

    def validate(self):
        path = self.root / 'evidence.json'
        self.write(path, self.value)
        return gate.validate_evidence(path, gate.bootstrap.digest(path), self.stages)

    def test_watchdog_fixture_never_promotes_to_production_lifecycle(self):
        scenarios = []
        for name in ('cancel', 'timeout'):
            reference = self.reference(name + '.log', 'fixture reached ready gate')
            scenarios.append(dict.fromkeys(('ready_observed', 'wait_completed', 'child_reaped',
                'process_group_absent', 'temporary_output_existed_before_cleanup', 'temporary_outputs_removed'), True))
            scenarios[-1].update(scenario=name, termination_reason=name, pid=123, exit_code=-15,
                argv=['/pinned/python', '-I', '-c', 'fixture'], log=reference['path'], log_sha256=reference['sha256'])
        value = {'schema': 1, 'scope': 'watchdog-fixture-only', 'status': 'observed',
                 'production_compiler_executed': False, 'children_reaped': True,
                 'temporary_outputs_removed': True, 'python': '/pinned/python',
                 'python_sha256': 'a' * 64, 'scenarios': scenarios}
        path = self.root / 'subprocess-cleanup.json'
        self.write(path, value)
        parsed = gate.watchdog_capability(path)
        self.assertFalse(parsed['production_lifecycle_satisfied'])
        self.assertFalse(parsed['production_compiler_executed'])
        for key, changed in (('production_compiler_executed', True), ('scope', 'production-compiler')):
            original = value[key]
            value[key] = changed
            self.write(path, value)
            with self.assertRaisesRegex(ValueError, 'cannot claim'):
                gate.watchdog_capability(path)
            value[key] = original
        value['scenarios'][1]['process_group_absent'] = False
        self.write(path, value)
        with self.assertRaisesRegex(ValueError, 'Incomplete'):
            gate.watchdog_capability(path)
        value['scenarios'][1]['process_group_absent'] = True
        self.write(path, value)
        (self.root / 'cancel.log').write_text('changed')
        with self.assertRaisesRegex(ValueError, 'changed evidence'):
            gate.watchdog_capability(path)

    def test_actual_diagnostic_batch_completeness_and_revision_binding(self):
        log = self.root / 'batch.log'
        batch = {'schema': 'flat.compiler.diagnostics.v1', 'revision': 'sha256:' + '1' * 64,
                 'complete': True, 'records': []}
        log.write_text(json.dumps(batch) + '\n')
        persistent = [{'inputFingerprint': batch['revision']}]
        records = gate.diagnostic_batches(log, self.root, persistent)
        commands = [{'compiler_invocations': 1, 'diagnostic_batches': records,
                     'persistent': [{'reused': True}], 'compiler_sessions': []}]
        self.assertTrue(gate.semantic_diagnostics(commands)['complete'])
        with self.assertRaisesRegex(ValueError, 'revision'):
            gate.diagnostic_batches(log, self.root, [])
        records[0]['complete'] = False
        self.assertFalse(gate.semantic_diagnostics(commands)['complete'])

    def test_actual_lifecycle_measurements_do_not_claim_child_cleanup(self):
        value = {'schema': 'flat.compiler.lifecycle.v1', 'scenario': 'cancel', 'concurrency': 3,
                 'activeCapacity': 3, 'retainedCapacity': 4, 'peakActive': 3, 'peakRetained': 4,
                 'enteredCount': 3, 'settledCount': 3, 'activeAfter': 0, 'inFlightAfter': 0,
                 'retainedSourcesAfter': 0, 'retainedCodeCharactersAfter': 0, 'pendingArtifactSendsAfter': 0,
                 'cleanupWaitSamples': 3, 'executorActiveAfter': 0, 'executorQueuedAfter': 0,
                 'unrelatedArtifactsBeforeFirstSettlement': None, 'pendingArtifactSendsAtPressure': None,
                 'bufferedArtifactsAtPressure': None, 'artifactBackpressure': False, 'cancelled': True,
                 'cleanupSettled': True, 'settlementOrder': ['Value2.flat', 'Value1.flat', 'Value0.flat'],
                 'unobserved': ['children_reaped', 'temporary_outputs_removed']}
        log = self.root / 'lifecycle.log'
        log.write_text(json.dumps(value) + '\n')
        self.assertEqual(gate.lifecycle_records(log), [value])
        value['peakActive'] = 4
        log.write_text(json.dumps(value) + '\n')
        with self.assertRaisesRegex(ValueError, 'capacity violation'):
            gate.lifecycle_records(log)
        value['peakActive'] = 3
        value['unobserved'] = []
        log.write_text(json.dumps(value) + '\n')
        with self.assertRaisesRegex(ValueError, 'cannot claim'):
            gate.lifecycle_records(log)

    def test_semantic_diagnostics_normalize_only_source_roots_preserving_order_and_ranges(self):
        span = {'startLine': 2, 'startColumn': 3, 'endLine': 2, 'endColumn': 8,
                'startTokenIndex': 4, 'endTokenIndex': 6}
        left = [{'code': 'type.invalid', 'message': 'invalid call', 'source': '/cold/packages/P/Input.flat', 'range': span}]
        right = [dict(left[0], source='/warm/packages/P/Input.flat')]
        a = gate.normalize_diagnostics(left, Path('/cold'))
        b = gate.normalize_diagnostics(right, Path('/warm'))
        self.assertEqual(a, b)
        self.assertNotEqual(a, gate.normalize_diagnostics([dict(right[0], code='type.other')], Path('/warm')))
        modified = dict(span, endColumn=9)
        self.assertNotEqual(a, gate.normalize_diagnostics([dict(right[0], range=modified)], Path('/warm')))
        with self.assertRaisesRegex(ValueError, 'outside'):
            gate.normalize_diagnostics(left, Path('/warm'))

    def test_missing_and_warm_diagnostics_are_not_assumed_empty(self):
        self.assertFalse(gate.semantic_diagnostics([])['complete'])
        commands = [{'compiler_invocations': 1, 'compiler_sessions': [{'diagnosticCount': 0, 'cancelled': False}],
                     'persistent': [{'reused': False}]}]
        self.assertEqual(gate.semantic_diagnostics(commands)['records'], [])
        commands[0]['persistent'][0]['reused'] = True
        self.assertFalse(gate.semantic_diagnostics(commands)['complete'])
        commands[0]['persistent'][0]['reused'] = False
        commands[0]['compiler_sessions'][0]['diagnosticCount'] = 1
        self.assertFalse(gate.semantic_diagnostics(commands)['complete'])

    def test_persistent_cli_records_bind_tool_and_actual_invalidation(self):
        value = {'schema': 'flat.compiler.incremental.v1', 'scope': 'persistent-cli-package',
                 'reused': True, 'reason': 'exact-hit', 'manifestPublished': False,
                 'unchangedSources': 2, 'changedSources': 0, 'addedSources': 0, 'removedSources': 0,
                 'toolFingerprint': 'sha256:' + 'a' * 64, 'inputFingerprint': 'sha256:' + 'b' * 64}
        log = self.root / 'persistent.log'
        log.write_text(json.dumps(value) + '\n')
        records = gate.persistent_records(log, 'a' * 64)
        measured = {'commands': [{'persistent': records}]}
        self.assertTrue(gate.incremental_observation('warm', measured)['proven'])
        self.assertFalse(gate.incremental_observation('changed', measured)['proven'])
        with self.assertRaisesRegex(ValueError, 'different compiler'):
            gate.persistent_records(log, 'c' * 64)

    def test_persistent_metadata_is_retained_but_not_program_equivalence(self):
        workspace = self.root / 'metadata'
        dist = workspace / 'packages/Flat-Core/dist'
        dist.mkdir(parents=True)
        (dist / 'test.js').write_text('generated')
        before = gate.output_inventory(workspace, 'Flat-Core')
        (dist / 'test.js.flat-incremental.json').write_text('{"physical":"location"}')
        self.assertEqual(before, gate.output_inventory(workspace, 'Flat-Core'))

    def test_actual_compiler_evidence_lines_are_parsed_without_invented_observations(self):
        record = {'schema': 'flat.compiler.session.v1', 'sourceCount': 2, 'artifactCount': 1,
                  'diagnosticCount': 0, 'cancelled': True, 'cleanupSettled': True,
                  'peakActiveSources': 1, 'peakRetainedSources': 2,
                  'peakActiveSourceReads': 0, 'peakRetainedSourceReads': 0,
                  'reuse': {'scope': 'exact-in-process-package', 'reused': False, 'reason': 'cold',
                            'unchangedSources': 0, 'changedSources': 0, 'addedSources': 2,
                            'removedSources': 0, 'toolFingerprint': 'test-tool'}}
        log = self.root / 'compiler.log'
        log.write_text('ordinary test output\n' + json.dumps(record) + '\n')
        self.assertEqual(gate.compiler_session_records(log), [record])
        record['sourceCount'] = True
        log.write_text(json.dumps(record) + '\n')
        with self.assertRaisesRegex(ValueError, 'session count'):
            gate.compiler_session_records(log)

    def test_read_concurrency_replays_only_observed_compiler_command(self):
        workspace = self.root / 'probe'
        dist = workspace / 'packages/Flat-Core/dist'
        dist.mkdir(parents=True)
        (dist / 'test.js').write_text('generated')
        logs = self.root / 'logs'
        logs.mkdir()
        journal = self.root / 'probe.jsonl'
        journal.write_text(json.dumps({'decision': 'allow', 'command': '/node', 'args': ['/flatc.js']}) + '\n')
        context = {'workspace': workspace, 'directory': self.root, 'journal': journal, 'env': {},
                   'commands': [{'compiler_argv': ['/node', '/flatc.js', 'src', '-target', 'es6',
                                                    '-threads', '8', '-o', 'dist/test.js']}]}
        def run(argv, cwd, env, log, journal, **kwargs):
            log.write_text('compiled')
        with mock.patch.object(gate.bootstrap, 'run_command', side_effect=run):
            results = gate.read_concurrency_probe(context, 'Flat-Core', {}, None, 10)
        self.assertEqual([result['threads'] for result in results], [1, 2])
        self.assertTrue(all(result['controlled_completion_order'] is None for result in results))
        for result in results:
            self.assertEqual(result['argv'].count('-threads'), 1)

    def test_production_mutation_paths_stay_in_owned_snapshot(self):
        name = 'packages/Flat-Core/src/Input.flat'
        self.assertEqual(gate.mutation_path(name, 'Flat-Core', self.sources), name)
        for invalid in ('packages/Airship/src/Test.flat', '../escape',
                        'packages/Flat-Core/src/FlatCore_TestSuite.flat'):
            with self.subTest(path=invalid), self.assertRaises(ValueError):
                gate.mutation_path(invalid, 'Flat-Core', self.sources)

    def test_output_inventory_includes_stale_files_for_equivalence(self):
        workspace = self.root / 'work'
        dist = workspace / 'packages/Flat-Core/dist/es6'
        dist.mkdir(parents=True)
        (dist / 'test.js').write_text('generated')
        first = gate.output_inventory(workspace, 'Flat-Core')
        (dist / 'stale.js').write_text('obsolete')
        self.assertNotEqual(first, gate.output_inventory(workspace, 'Flat-Core'))

    def test_real_execution_uses_explicit_stage2_tools_and_admissions(self):
        directory = self.root / 'production'
        workspace = directory / 'workspace'
        package = workspace / 'packages/Flat-Core'
        package.mkdir(parents=True)
        (directory / 'logs').mkdir()
        runner = directory / 'airship.js'
        compiler = directory / 'flatc.js'
        node = directory / 'node'
        for path in (runner, compiler, node):
            path.write_text('pinned tool')
        journal = directory / 'admissions.jsonl'
        context = {'directory': directory, 'workspace': workspace, 'runner': runner, 'compiler': compiler,
                   'journal': journal, 'commands': [], 'env': {}, 'policy': {
                       'airshipSha256': gate.bootstrap.digest(runner), 'seedSha256': gate.bootstrap.digest(compiler)}}
        seeds = {'node': (node, gate.bootstrap.digest(node))}
        def execute(argv, cwd, env, log, admission, **kwargs):
            log.write_text('executed')
            script = compiler
            if 'install' in argv:
                output = package / 'dist/es6/test.js'
                output.parent.mkdir(parents=True)
                output.write_text('// actual generated test stand-in for orchestration unit test')
            else:
                script = package / 'dist/es6/test.js'
            with admission.open('a') as out:
                out.write(json.dumps({'decision': 'allow', 'command': str(node), 'args': [str(script)]}) + '\n')
        with mock.patch.object(gate.bootstrap, 'run_command', side_effect=execute) as run:
            result = gate.execute_production(context, 'Flat-Core', seeds, None, 20)
        self.assertEqual(result['status'], 'passed')
        self.assertIsNone(result['reuse_observed'])
        self.assertIsNone(result['summary'])
        self.assertEqual(run.call_count, 2)
        command = run.call_args_list[0].args[0]
        self.assertIn('--flatc-script', command)
        self.assertIn(str(compiler), command)
        self.assertIn('--flatc-runtime', command)

    def test_complete_receipt_aggregates_without_executing_tools(self):
        result = self.validate()
        self.assertEqual(result['status'], 'blocked')
        self.assertFalse(result['full_acceptance'])
        self.assertEqual(result['suites'], list(gate.SUITES))

    def test_bootstrap_missing_suite_and_mutated_tool_are_failures(self):
        path = self.stages / 'stage1/manifest.json'
        manifest = gate.read_json(path)
        manifest['commands'] = manifest['commands'][:-1]
        self.write(path, manifest)
        with self.assertRaisesRegex(ValueError, 'Missing bootstrap'):
            self.validate()

    def test_bootstrap_logs_are_bound_to_receipt(self):
        (self.stages / 'stage2/logs/01.log').write_text('changed')
        with self.assertRaisesRegex(ValueError, 'changed evidence'):
            self.validate()

    def test_bootstrap_products_are_bound_to_retained_files(self):
        (self.stages / 'stage1/workspace' / gate.bootstrap.PRODUCTS[0]).write_text('stale')
        with self.assertRaisesRegex(ValueError, 'changed evidence'):
            self.validate()

    def test_missing_suite_never_skips(self):
        del self.value['suites']['Flat-Parser']
        with self.assertRaisesRegex(ValueError, 'Missing run'):
            self.validate()

    def test_missing_lifecycle_never_skips(self):
        del self.value['lifecycle']['children_reaped']
        with self.assertRaisesRegex(ValueError, 'lifecycle'):
            self.validate()

    def test_stale_tool_and_source_evidence_rejected(self):
        for key in ('tools', 'source_manifest_sha256'):
            with self.subTest(key=key):
                saved = self.value[key]
                self.value[key] = {}
                with self.assertRaises(ValueError):
                    self.validate()
                self.value[key] = saved

    def test_modified_artifact_rejected(self):
        (self.root / 'test.js').write_text('stale')
        with self.assertRaisesRegex(ValueError, 'changed evidence'):
            self.validate()

    def test_java_cannot_hide_behind_zero_counter(self):
        entry = self.reference('java.jsonl', json.dumps({
            'decision': 'allow', 'command': '/bin/java', 'args': ['-jar', 'compiler.jar']}) + '\n')
        self.value['core']['subprocess_journal'] = entry
        with self.assertRaisesRegex(ValueError, 'Java invocation'):
            self.validate()

    def test_caught_admission_rejection_is_failure(self):
        self.value['core']['subprocess_journal'] = self.reference(
            'rejected.jsonl', json.dumps({'decision': 'reject', 'command': 'curl', 'args': []}) + '\n')
        with self.assertRaisesRegex(ValueError, 'subprocess admission'):
            self.validate()

    def test_changed_and_removed_must_actually_mutate_sources(self):
        for name in ('changed', 'removed'):
            with self.subTest(name=name):
                saved = self.value['incremental'][name]
                self.value['incremental'][name] = copy.deepcopy(self.value['incremental']['cold'])
                with self.assertRaises(ValueError):
                    self.validate()
                self.value['incremental'][name] = saved

    def test_incremental_and_schedule_outputs_must_match(self):
        other = self.reference('different.js', '// different')
        for run in (self.value['incremental']['warm']['incremental'], self.value['schedules'][1]):
            saved = run['artifacts']['test.js']
            run['artifacts']['test.js'] = other
            with self.assertRaisesRegex(ValueError, 'differ|Schedule-dependent'):
                self.validate()
            run['artifacts']['test.js'] = saved

    def test_serial_only_or_sleep_based_schedules_rejected(self):
        self.value['schedules'][1]['concurrency'] = 1
        with self.assertRaisesRegex(ValueError, 'Serial/parallel'):
            self.validate()
        self.value['schedules'][1]['concurrency'] = 4
        self.value['schedules'][1]['controlled'] = False
        with self.assertRaisesRegex(ValueError, 'controlled'):
            self.validate()

    def test_path_escape_and_symlink_rejected(self):
        for name in ('../escape', '/absolute', 'a/../test.js'):
            with self.subTest(name=name), self.assertRaises(ValueError):
                gate.file_reference(self.root, {'path': name, 'sha256': '0' * 64})
        (self.root / 'linked.js').symlink_to(self.root / 'test.js')
        with self.assertRaisesRegex(ValueError, 'Linked'):
            gate.file_reference(self.root, {'path': 'linked.js', 'sha256': gate.bootstrap.digest(self.root / 'test.js')})

    def test_duplicate_json_keys_and_bad_digest_rejected(self):
        path = self.root / 'duplicate.json'
        path.write_text('{"status":"failed","status":"passed"}')
        with self.assertRaisesRegex(ValueError, 'Duplicate'):
            gate.read_json(path)
        with self.assertRaisesRegex(ValueError, 'digest mismatch'):
            gate.validate_evidence(path, '0' * 64, self.stages)


if __name__ == '__main__':
    unittest.main()

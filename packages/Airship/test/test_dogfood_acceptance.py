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
            self.assertIn('cleanup_observation', kwargs)
            kwargs['cleanup_observation'].update(pid=42, exit_code=0, wait_completed=True,
                                                 child_reaped=True, process_group_absent=True)
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
        for observed in result['commands']:
            self.assertEqual(gate.command_cleanup(self.root, observed)['process']['pid'], 42)
            self.assertEqual(observed['temporary_outputs_after'], [])
        self.assertIsNone(result['reuse_observed'])
        self.assertIsNone(result['summary'])
        self.assertEqual(run.call_count, 2)
        command = run.call_args_list[0].args[0]
        self.assertIn('--flatc-script', command)
        self.assertIn(str(compiler), command)
        self.assertIn('--flatc-runtime', command)

    def lifecycle_sample(self, scenario='package-forward', concurrency=3):
        order = ['Value0.flat', 'Value1.flat', 'Value2.flat', 'Value3.flat']
        if scenario == 'package-reverse':
            order[:concurrency] = reversed(order[:concurrency])
        return {'schema': 'flat.compiler.lifecycle.v1', 'scenario': scenario,
                'concurrency': concurrency, 'activeCapacity': concurrency,
                'retainedCapacity': concurrency + 1, 'peakActive': concurrency,
                'peakRetained': concurrency, 'enteredCount': 4, 'settledCount': 4,
                'settlementOrder': order, 'activeAfter': 0, 'executorActiveAfter': 0,
                'executorQueuedAfter': 0, 'inFlightAfter': 0, 'retainedSourcesAfter': 0,
                'retainedCodeCharactersAfter': 0, 'pendingArtifactSendsAfter': 0,
                'artifactBackpressure': False, 'cancelled': False, 'cleanupSettled': True,
                'unrelatedArtifactsBeforeFirstSettlement': None,
                'pendingArtifactSendsAtPressure': None, 'bufferedArtifactsAtPressure': None,
                'cleanupWaitSamples': 0, 'unobserved': ['children_reaped', 'temporary_outputs_removed']}

    def test_lifecycle_measurements_close_only_their_actual_scope(self):
        log = self.root / 'lifecycle.log'
        samples = [self.lifecycle_sample(concurrency=1), self.lifecycle_sample(),
                   self.lifecycle_sample('package-reverse')]
        log.write_text(''.join(json.dumps(value) + '\n' for value in samples))
        observed = gate.lifecycle_records(log)
        receipt = {'stage2_suites': {'Flat-Compiler': {'commands': [{'lifecycle': observed}]}}}
        result = gate.production_gates(receipt)
        self.assertTrue(result['gates']['controlled-package-schedules'])
        self.assertFalse(result['gates']['compiler-lifecycle-scenarios'])
        self.assertFalse(result['full_acceptance'])
        # Fixture observations and caller-authored broad flags cannot promote scope.
        receipt.update(full_acceptance=True, status='passed', subprocess_cleanup={
            'children_reaped': True, 'temporary_outputs_removed': True},
            watchdog_capability={'status': 'observed'})
        self.assertEqual(gate.production_gates(receipt), result)
        receipt['stage2_suites']['Flat-Compiler']['commands'][0]['lifecycle'].pop()
        self.assertFalse(gate.production_gates(receipt)['gates']['controlled-package-schedules'])

    def test_lifecycle_rejects_bad_capacity_cleanup_and_fake_reverse(self):
        log = self.root / 'lifecycle.log'
        for field, bad in (('peakActive', 4), ('peakRetained', 5), ('activeAfter', 1),
                           ('executorQueuedAfter', 1), ('cleanupSettled', False),
                           ('settledCount', 3), ('concurrency', True),
                           ('unobserved', []), ('settlementOrder', ['Value0.flat'] * 4)):
            sample = self.lifecycle_sample()
            sample[field] = bad
            log.write_text(json.dumps(sample) + '\n')
            with self.subTest(field=field), self.assertRaises(ValueError):
                gate.lifecycle_records(log)
        sample = self.lifecycle_sample('package-reverse')
        sample['settlementOrder'] = self.lifecycle_sample()['settlementOrder']
        log.write_text(json.dumps(sample) + '\n')
        with self.assertRaisesRegex(ValueError, 'settlement order'):
            gate.lifecycle_records(log)

    def driver_log(self, scenario='forward', capacity=1):
        destination = self.root / 'production-test.js'
        arguments = ['src', '-target', 'es6', '-o', destination.name]
        revision = 'sha256:' + 'b' * 64
        events = []
        def event(transition, admitted, settled, active, retained):
            events.append({'schema': 'flat.compiler.source-read-event.v1', 'sequence': len(events) + 1,
                'revision': revision, 'destination': str(destination), 'capacity': capacity,
                'source': 'src/Actual.flat', 'transition': transition,
                'admitted': admitted, 'settled': settled, 'active': active, 'retained': retained})
        event('admitted', 1, 0, 0, 1)
        event('started', 1, 0, 1, 1)
        cancelled = scenario.startswith('cancel-')
        if cancelled:
            event('cancel-readiness-after-read' if scenario == 'cancel-after-read'
                  else 'cancel-readiness-after-stage', 1, 0, 1, 1)
        event('settled', 1, 1, 0, 1)
        event('consumed', 1, 1, 0, 0)
        digest = gate.hashlib.sha256()
        for value in events:
            digest.update(json.dumps([value['transition'], value['source']], separators=(',', ':')).encode() + b'\n')
        report = {'schema': 'flat.compiler.source-reads.v1', 'scenario': scenario, 'capacity': capacity,
            'revision': revision, 'destination': str(destination),
            'optionsFingerprint': 'sha256:' + gate.fingerprint(arguments + ['-threads', str(capacity)]),
            'input': {'schema': 'flat.compiler.incremental.v1', 'toolFingerprint': 'sha256:' + 'a' * 64,
                      'inputFingerprint': revision, 'reused': False},
            'admitted': 1, 'settled': 1, 'activeAfter': 0, 'retainedAfter': 0,
            'peakActive': 1, 'peakRetained': 1, 'exitCode': 1 if cancelled else 0,
            'cancellationRequested': cancelled, 'eventDigest': 'sha256:' + digest.hexdigest(),
            'artifactDeliveryFailures': 0, 'manifestDeliveryFailures': 0,
            'session': {'schema': 'flat.compiler.session.v1', 'cancelled': cancelled, 'cleanupSettled': True}}
        batch = {'schema': 'flat.compiler.diagnostics.v1', 'revision': revision,
                 'complete': not cancelled, 'records': []}
        log = self.root / 'production-driver.log'
        def write():
            log.write_text(''.join(json.dumps(value) + '\n' for value in events + [batch, report]))
        write()
        return log, arguments, destination, events, report, batch, write

    def test_production_driver_launches_pinned_builder_and_all_real_scenarios(self):
        directory = self.root / 'driver-launch'
        workspace = directory / 'workspace'
        (directory / 'logs').mkdir(parents=True)
        (workspace / 'packages/Flat-Core').mkdir(parents=True)
        (workspace / 'packages/Flat-Compiler').mkdir(parents=True)
        compiler = directory / 'flatc.js'
        node = directory / 'node'
        compiler.write_text('pinned compiler')
        node.write_text('pinned node')
        context = {'directory': directory, 'workspace': workspace, 'compiler': compiler,
                   'journal': directory / 'admissions.jsonl', 'env': {},
                   'policy': {'seedSha256': gate.bootstrap.digest(compiler)}}
        context['journal'].write_text('')
        driver = workspace / 'packages/Flat-Compiler/dist/es6/production-driver.js'
        calls = []
        def execute(argv, cwd, env, log, journal, **kwargs):
            calls.append(argv)
            log.write_text('actual producer record parsing covered independently')
            if argv[1] == str(compiler):
                driver.parent.mkdir(parents=True, exist_ok=True)
                driver.write_text('built by pinned compiler')
                return 0
            self.assertEqual(argv[1], str(driver))
            scenario, capacity = argv[2:4]
            destination = Path(argv[argv.index('-d') + 1]) / argv[argv.index('-o') + 1]
            code = 1 if scenario.startswith('cancel-') else 0
            kwargs['cleanup_observation'].update(pid=42, exit_code=code,
                wait_completed=True, child_reaped=True, process_group_absent=True)
            if not code:
                destination.parent.mkdir(parents=True, exist_ok=True)
                destination.write_text('same compiled production output')
            if code:
                raise gate.bootstrap.CommandFailure(argv, code, log)
            return 0
        def report(log, arguments, scenario, capacity, tool, destination):
            self.assertEqual(tool, gate.bootstrap.digest(driver))
            return {'scenario': scenario, 'capacity': capacity}
        with mock.patch.object(gate, 'execute_production') as prepare, \
             mock.patch.object(gate, 'observed_compiler_arguments', return_value=['src', '-target', 'es6']), \
             mock.patch.object(gate.bootstrap, 'run_command', side_effect=execute), \
             mock.patch.object(gate, 'production_read_records', side_effect=report), \
             mock.patch.object(gate, 'manifest_lifecycle', return_value=[]), \
             mock.patch.object(gate, 'artifact_lifecycle', return_value=True), \
             mock.patch.object(gate, 'driver_source_inputs', return_value={}):
            result = gate.run_production_driver(context, {'node': (node, gate.bootstrap.digest(node))}, None, 20)
        self.assertEqual([call.args[1] for call in prepare.call_args_list], ['Flat-Compiler', 'Flat-Core'])
        self.assertEqual(len(calls), 9)
        self.assertEqual(calls[0][:2], [str(node), str(compiler)])
        self.assertIn('flat/compiler/CompilerProductionDriver', calls[0])
        self.assertEqual({(run['scenario'], run['capacity']) for run in result['runs']},
            {(scenario, capacity) for scenario in ('forward', 'reverse', 'cancel-after-read', 'cancel-after-stage')
             for capacity in (1, 3)})
        self.assertEqual(result['driver']['sha256'], gate.bootstrap.digest(driver))

    def test_driver_records_require_actual_work_and_cancellation_readiness(self):
        for scenario in ('forward', 'reverse', 'cancel-after-read', 'cancel-after-stage'):
            log, argv, destination, events, report, batch, write = self.driver_log(scenario)
            self.assertEqual(gate.production_read_records(log, argv, scenario, 1, 'a' * 64, destination), report)
            if scenario.startswith('cancel-'):
                events[2]['transition'] = 'consumed'
                write()
                with self.assertRaises(ValueError):
                    gate.production_read_records(log, argv, scenario, 1, 'a' * 64, destination)

    def test_driver_schedule_equivalence_compares_outputs_and_diagnostics(self):
        source = self.root / 'driver-source.flat'
        source.write_text('real source stand-in for receipt-only test')
        compiler = self.root / 'builder.js'
        compiler.write_text('pinned builder')
        driver = self.root / 'driver.js'
        driver.write_text('pinned built driver')
        build_log = self.root / 'driver-build.log'
        build_log.write_text('compiler completed')
        reference = lambda path: {'path': path.relative_to(self.root).as_posix(), 'sha256': gate.bootstrap.digest(path)}
        sources = {'packages/Flat-Core/src/Input.flat': gate.bootstrap.digest(source)}
        destination = self.root / 'workspace/packages/Flat-Core/dist/production-driver/forward-1/test.js'
        artifact = self.root / 'driver-output.js'
        artifact.write_text('same generated artifact')
        report = {'admitted': 1, 'exitCode': 0}
        value = {'source_manifest_sha256': gate.fingerprint(sources), 'build_source_manifest': sources,
                 'compiler_sha256': gate.bootstrap.digest(compiler), 'compiler': reference(compiler),
                 'driver': reference(driver), 'build_log': reference(build_log), 'runs': []}
        for scenario in ('forward', 'reverse', 'cancel-after-read', 'cancel-after-stage'):
            for capacity in (1, 3):
                log = self.root / f'equivalence-{scenario}-{capacity}.log'
                log.write_text(json.dumps({'schema': 'flat.compiler.diagnostics.v1', 'records': []}) + '\n')
                value['runs'].append({'scenario': scenario, 'capacity': capacity, 'arguments': [],
                    'destination': str(destination), 'log': reference(log), 'report': dict(report),
                    'inputs': {name: {'sha256': sha, 'reference': reference(source)} for name, sha in sources.items()},
                    'process_cleanup': {'pid': 42, 'exit_code': 0, 'wait_completed': True,
                                        'child_reaped': True, 'process_group_absent': True},
                    'temporary_outputs_after': [], 'artifact_lifecycle': [], 'manifest_lifecycle': [],
                    'artifacts': {'test.js': reference(artifact)} if not scenario.startswith('cancel-') else {}})
        with mock.patch.object(gate, 'production_read_records', return_value=report), \
             mock.patch.object(gate, 'manifest_lifecycle', return_value=[]), \
             mock.patch.object(gate, 'artifact_lifecycle', return_value=True):
            self.assertTrue(gate.validate_driver_evidence(self.root, value, gate.fingerprint(sources),
                                                        gate.bootstrap.digest(compiler)))
            other = self.root / 'schedule-dependent.js'
            other.write_text('different generated artifact')
            value['runs'][1]['artifacts']['test.js'] = reference(other)
            with self.assertRaisesRegex(ValueError, 'schedule-dependent artifact'):
                gate.validate_driver_evidence(self.root, value, gate.fingerprint(sources), gate.bootstrap.digest(compiler))
            value['runs'][1]['artifacts']['test.js'] = reference(artifact)
            log = self.root / value['runs'][1]['log']['path']
            log.write_text(json.dumps({'schema': 'flat.compiler.diagnostics.v1', 'records': [
                {'code': 'different', 'message': 'semantic diagnostic', 'source': 'src/Input.flat', 'range': None}]}) + '\n')
            value['runs'][1]['log'] = reference(log)
            with self.assertRaisesRegex(ValueError, 'schedule-dependent diagnostics'):
                gate.validate_driver_evidence(self.root, value, gate.fingerprint(sources), gate.bootstrap.digest(compiler))

    def test_reverse_driver_validates_settlement_not_digest_equality(self):
        log, argv, destination, events, report, batch, write = self.driver_log('reverse', 2)
        revision = report['revision']
        events.clear()
        def event(source, transition, admitted, settled, active, retained):
            events.append({'schema': 'flat.compiler.source-read-event.v1', 'sequence': len(events) + 1,
                'revision': revision, 'destination': str(destination), 'capacity': 2,
                'source': source, 'transition': transition, 'admitted': admitted,
                'settled': settled, 'active': active, 'retained': retained})
        event('A.flat', 'admitted', 1, 0, 0, 1)
        event('A.flat', 'started', 1, 0, 1, 1)
        event('B.flat', 'admitted', 2, 0, 1, 2)
        event('B.flat', 'started', 2, 0, 2, 2)
        event('B.flat', 'settled', 2, 1, 1, 2)
        event('A.flat', 'settled', 2, 2, 0, 2)
        event('A.flat', 'consumed', 2, 2, 0, 1)
        event('B.flat', 'consumed', 2, 2, 0, 0)
        report.update(admitted=2, settled=2, peakActive=2, peakRetained=2)
        def digest():
            value = gate.hashlib.sha256()
            for item in events:
                value.update(json.dumps([item['transition'], item['source']], separators=(',', ':')).encode() + b'\n')
            report['eventDigest'] = 'sha256:' + value.hexdigest()
        digest()
        write()
        self.assertEqual(gate.production_read_records(log, argv, 'reverse', 2, 'a' * 64, destination), report)
        # Recompute an internally consistent digest for a falsely labelled order:
        # digest integrity alone must not establish reverse scheduling.
        events[4]['source'], events[5]['source'] = 'A.flat', 'B.flat'
        digest()
        write()
        with self.assertRaisesRegex(ValueError, 'settlement order'):
            gate.production_read_records(log, argv, 'reverse', 2, 'a' * 64, destination)

    def test_driver_rejects_stale_tampered_warm_and_incomplete_records(self):
        for field, value in (('revision', 'sha256:' + 'c' * 64), ('eventDigest', 'sha256:' + '0' * 64),
                             ('optionsFingerprint', 'sha256:' + '0' * 64), ('admitted', 2),
                             ('activeAfter', 1), ('peakActive', 2), ('exitCode', 1)):
            log, argv, destination, events, report, batch, write = self.driver_log()
            report[field] = value
            write()
            with self.subTest(field=field), self.assertRaises(ValueError):
                gate.production_read_records(log, argv, 'forward', 1, 'a' * 64, destination)
        log, argv, destination, events, report, batch, write = self.driver_log()
        report['input']['reused'] = True
        write()
        with self.assertRaisesRegex(ValueError, 'warm'):
            gate.production_read_records(log, argv, 'forward', 1, 'a' * 64, destination)
        report['input']['reused'] = False
        batch['complete'] = False
        write()
        with self.assertRaisesRegex(ValueError, 'Incomplete production schedule diagnostics'):
            gate.production_read_records(log, argv, 'forward', 1, 'a' * 64, destination)
        log.write_text(json.dumps(report) + '\n')
        with self.assertRaisesRegex(ValueError, 'Missing production source-read events'):
            gate.production_read_records(log, argv, 'forward', 1, 'a' * 64, destination)

    def test_driver_argument_selection_preserves_admitted_dependencies(self):
        args = ['src', '-l', '/known/library', '-library', '-threads', '8', '-main', 'Old',
                '-d', 'dist', '-o', 'test.js', '-target', 'es6']
        self.assertEqual(gate.driver_arguments(args, Path('/output/driver.js'), 'flat/compiler/CompilerProductionDriver'),
            ['src', '-l', '/known/library', '-target', 'es6', '-x', '**/*_Test*.flat',
             '-main', 'flat/compiler/CompilerProductionDriver',
             '-d', '/output', '-o', 'driver.js'])

    def test_predeclared_scaling_plan_and_rss_units(self):
        path = self.root / 'scaling-plan.json'
        source_sha = 'a' * 64
        plan = {'schema': 'flat.dogfood.scaling-plan.v1', 'source_manifest_sha256': source_sha,
            'tiers': [{'package': package, 'min_source_bytes': 100 * 2 ** index,
                       'min_sources': 1, 'max_rss_bytes': 100000000}
                      for index, package in enumerate(('Flat-Core', 'Flat-Parser', 'Flat-Compiler'))],
            'max_rss_growth_numerator': 2, 'max_rss_growth_denominator': 1,
            'min_input_growth_factor': 2,
            'timer': {'kind': 'bsd-time', 'path': '/usr/bin/time', 'sha256': 'b' * 64}}
        self.write(path, plan)
        self.assertEqual(gate.scaling_plan(path, gate.bootstrap.digest(path), source_sha), plan)
        with self.assertRaisesRegex(ValueError, 'digest mismatch'):
            gate.scaling_plan(path, '0' * 64, source_sha)
        with self.assertRaisesRegex(ValueError, 'Stale'):
            gate.scaling_plan(path, gate.bootstrap.digest(path), 'c' * 64)
        for field in ('max_rss_growth_numerator', 'min_input_growth_factor'):
            changed = copy.deepcopy(plan)
            changed[field] = 0
            self.write(path, changed)
            with self.assertRaises(ValueError):
                gate.scaling_plan(path, gate.bootstrap.digest(path), source_sha)
        rss = self.root / 'rss.txt'
        rss.write_text('  123456 maximum resident set size\n')
        self.assertEqual(gate.rss_bytes(rss, 'bsd-time'), 123456)
        rss.write_text('flat-maxrss-kib=123\n')
        self.assertEqual(gate.rss_bytes(rss, 'gnu-time'), 123 * 1024)
        rss.write_text('flat-maxrss-kib=123\nflat-maxrss-kib=124\n')
        with self.assertRaisesRegex(ValueError, 'ambiguous'):
            gate.rss_bytes(rss, 'gnu-time')
        verdict = gate.production_gates({})
        self.assertFalse(verdict['gates']['production-measured-memory-scaling'])
        self.assertFalse(verdict['full_acceptance'])

    def test_scaling_enforces_predeclared_size_rss_and_growth_budgets(self):
        plan_path = self.root / 'budget-plan.json'
        packages = ('Flat-Core', 'Flat-Parser', 'Flat-Compiler')
        source_sha = 'a' * 64
        plan = {'schema': 'flat.dogfood.scaling-plan.v1', 'source_manifest_sha256': source_sha,
            'tiers': [{'package': package, 'min_source_bytes': 10 * 2 ** index,
                       'min_sources': 1, 'max_rss_bytes': 1000} for index, package in enumerate(packages)],
            'max_rss_growth_numerator': 2, 'max_rss_growth_denominator': 1,
            'min_input_growth_factor': 2,
            'timer': {'kind': 'gnu-time', 'path': '/usr/bin/time', 'sha256': 'b' * 64}}
        # GNU measurements are KiB, so declare budgets before validating them.
        for tier in plan['tiers']:
            tier['max_rss_bytes'] = 100000
        self.write(plan_path, plan)
        reference = lambda path: {'path': path.relative_to(self.root).as_posix(), 'sha256': gate.bootstrap.digest(path)}
        value = {'plan': reference(plan_path), 'tiers': []}
        baseline = {}
        for index, package in enumerate(packages):
            source = self.root / f'source-{index}.flat'
            source.write_text('x' * (10 * 2 ** index))
            identity = f'packages/{package}/src/Input.flat'
            baseline[identity] = gate.bootstrap.digest(source)
            rss = self.root / f'peak-{index}.rss'
            rss.write_text('flat-maxrss-kib=10\n')
            log = self.root / f'scale-{index}.log'
            log.write_text(json.dumps({'schema': 'flat.compiler.diagnostics.v1', 'complete': True, 'records': []}) + '\n')
            workspace = self.root / f'workspace-{index}'
            artifact = workspace / 'packages' / package / 'dist' / 'scaling' / 'test.js'
            artifact.parent.mkdir(parents=True)
            artifact.write_text('generated code')
            ordinary = {'package': package, 'workspace': workspace.relative_to(self.root).as_posix(),
                'commands': [{'compiler_argv': ['/node', '/compiler.js', 'src']}],
                'artifacts': {'test.js': gate.bootstrap.digest(artifact)}}
            value['tiers'].append({'package': package, 'log': reference(log), 'rss': reference(rss),
                'ordinary': ordinary,
                'artifact': reference(artifact), 'arguments': gate.driver_arguments(['src'], artifact), 'destination': str(artifact),
                'report': {'admitted': 1}, 'rss_bytes': 10240,
                'inputs': {identity: {'sha256': baseline[identity], 'bytes': source.stat().st_size,
                                     'reference': reference(source)}}})
        with mock.patch.object(gate, 'production_read_records', return_value={'admitted': 1}), \
             mock.patch.object(gate, 'validate_production_run', return_value=baseline), \
             mock.patch.object(gate, 'semantic_diagnostics', return_value={'complete': True, 'records': []}):
            self.assertTrue(gate.validate_scaling(self.root, value, source_sha, baseline, 'c' * 64))
            # Even identical bytes in another valid hash-bound file do not
            # identify the selected compiler destination.
            tier = value['tiers'][0]
            original_reference = tier['artifact']
            arbitrary = self.root / 'arbitrary.js'
            arbitrary.write_text('generated code')
            tier['artifact'] = reference(arbitrary)
            with self.assertRaisesRegex(ValueError, 'does not identify tier destination'):
                gate.validate_scaling(self.root, value, source_sha, baseline, 'c' * 64)
            tier['artifact'] = original_reference
            log = self.root / tier['log']['path']
            log.write_text(json.dumps({'schema': 'flat.compiler.diagnostics.v1', 'complete': True,
                'records': [{'code': 'wrong', 'message': 'different semantic result',
                             'source': 'src/Input.flat', 'range': None}]}) + '\n')
            tier['log'] = reference(log)
            with self.assertRaisesRegex(ValueError, 'diagnostics differ from ordinary'):
                gate.validate_scaling(self.root, value, source_sha, baseline, 'c' * 64)
            log.write_text(json.dumps({'schema': 'flat.compiler.diagnostics.v1', 'complete': False, 'records': []}) + '\n')
            tier['log'] = reference(log)
            with self.assertRaisesRegex(ValueError, 'Incomplete scaling/baseline diagnostics'):
                gate.validate_scaling(self.root, value, source_sha, baseline, 'c' * 64)
            log.write_text(json.dumps({'schema': 'flat.compiler.diagnostics.v1', 'complete': True, 'records': []}) + '\n')
            tier['log'] = reference(log)
            ordinary_digest = tier['ordinary']['artifacts']['test.js']
            tier['ordinary']['artifacts']['test.js'] = '0' * 64
            with self.assertRaisesRegex(ValueError, 'artifact bytes differ from ordinary'):
                gate.validate_scaling(self.root, value, source_sha, baseline, 'c' * 64)
            tier['ordinary']['artifacts']['test.js'] = ordinary_digest
            value['tiers'][1]['rss_bytes'] = 1
            with self.assertRaisesRegex(ValueError, 'RSS budget'):
                gate.validate_scaling(self.root, value, source_sha, baseline, 'c' * 64)
            value['tiers'][1]['rss_bytes'] = 10240
            plan['tiers'][0]['min_source_bytes'] = 11
            self.write(plan_path, plan)
            value['plan'] = reference(plan_path)
            with self.assertRaisesRegex(ValueError, 'below predeclared'):
                gate.validate_scaling(self.root, value, source_sha, baseline, 'c' * 64)
            plan['tiers'][0]['min_source_bytes'] = 10
            plan['max_rss_growth_numerator'] = 1
            plan['max_rss_growth_denominator'] = 2
            self.write(plan_path, plan)
            value['plan'] = reference(plan_path)
            with self.assertRaisesRegex(ValueError, 'memory growth'):
                gate.validate_scaling(self.root, value, source_sha, baseline, 'c' * 64)

    def test_manifest_lifecycle_uses_manifest_binding_and_real_owned_events(self):
        log = self.root / 'manifest.log'
        output = self.root / 'production/output.js'
        destination = str(output) + '.flat-incremental.json'
        temporary = destination + '.tmp-observed'
        revision = 'sha256:' + 'a' * 64
        report = {'scenario': 'forward', 'revision': revision, 'destination': str(output),
                  'manifestDeliveryFailures': 0}
        records = []
        for transition, path in (('temporary-created', temporary),
                ('temporary-created', temporary + '/manifest'), ('published', None),
                ('temporary-removed', temporary + '/manifest'), ('temporary-removed', temporary),
                ('finalized', temporary)):
            records.append({'schema': 'flat.compiler.manifest-lifecycle.v1', 'sequence': len(records) + 1,
                'revision': revision, 'destination': destination, 'identity': 'output.js.flat-incremental.json',
                'transition': transition, 'path': path, 'failure': None,
                'removed': transition in ('temporary-removed', 'finalized')})
        def write():
            log.write_text(''.join(json.dumps(value) + '\n' for value in records))
        write()
        self.assertEqual(gate.manifest_lifecycle(log, report), records)
        for field, bad in (('revision', 'sha256:' + 'b' * 64), ('destination', str(output)),
                           ('sequence', 99), ('failure', 'failed finalizer'), ('removed', False)):
            original = records[-1][field]
            records[-1][field] = bad
            write()
            with self.subTest(field=field), self.assertRaises(ValueError):
                gate.manifest_lifecycle(log, report)
            records[-1][field] = original
        write()
        report['manifestDeliveryFailures'] = 1
        with self.assertRaisesRegex(ValueError, 'delivery incomplete'):
            gate.manifest_lifecycle(log, report)
        report['manifestDeliveryFailures'] = 0
        report['scenario'] = 'cancel-after-stage'
        with self.assertRaisesRegex(ValueError, 'published a manifest'):
            gate.manifest_lifecycle(log, report)
        log.write_text('')
        self.assertEqual(gate.manifest_lifecycle(log, report), [])
        report['scenario'] = 'forward'
        with self.assertRaisesRegex(ValueError, 'No actual temporary'):
            gate.manifest_lifecycle(log, report)

    def test_artifact_lifecycle_requires_owned_create_remove_and_finalization(self):
        path = self.root / 'artifact-lifecycle.log'
        destination = self.root / 'production/output.js'
        temporary = str(destination.parent / 'spool.flat-tmp-owned')
        report = {'revision': 'sha256:' + 'a' * 64, 'destination': str(destination), 'artifactDeliveryFailures': 0}
        records = []
        for transition in ('temporary-created', 'temporary-removed', 'finalized'):
            records.append({'schema': 'flat.compiler.artifact-lifecycle.v1',
                'revision': report['revision'], 'destination': report['destination'], 'identity': 'output.js',
                'sequence': len(records) + 1, 'transition': transition, 'path': temporary,
                'removed': transition != 'temporary-created', 'failure': None})
        def write():
            path.write_text(''.join(json.dumps(record) + '\n' for record in records))
        write()
        self.assertTrue(gate.artifact_lifecycle(path, report, True))
        records[-1]['failure'] = 'cleanup failed'
        write()
        with self.assertRaisesRegex(ValueError, 'finalization failure'):
            gate.artifact_lifecycle(path, report, True)
        records[-1]['failure'] = None
        records.pop()
        write()
        with self.assertRaisesRegex(ValueError, 'Incomplete'):
            gate.artifact_lifecycle(path, report, True)
        path.write_text('')
        self.assertTrue(gate.artifact_lifecycle(path, report, False))
        with self.assertRaisesRegex(ValueError, 'No actual temporary'):
            gate.artifact_lifecycle(path, report, True)

    def test_nullable_cleanup_wait_is_unmeasured_not_zero_or_pass(self):
        log = self.root / 'nullable-lifecycle.log'
        sample = self.lifecycle_sample('running-source-cancellation')
        sample.update(cancelled=True, cleanupWaitSamples=None)
        log.write_text(json.dumps(sample) + '\n')
        records = gate.lifecycle_records(log)
        self.assertIsNone(records[0]['cleanupWaitSamples'])
        verdict = gate.production_gates({'stage2_suites': {
            'Flat-Compiler': {'commands': [{'lifecycle': records}]}}})
        self.assertFalse(verdict['gates']['cancellation-waits-for-cleanup'])
        sample['cleanupWaitSamples'] = -1
        log.write_text(json.dumps(sample) + '\n')
        with self.assertRaisesRegex(ValueError, 'nullable lifecycle'):
            gate.lifecycle_records(log)

    def test_temporary_output_inventory_observes_without_deleting(self):
        workspace = self.root / 'temporary-workspace'
        dist = workspace / 'packages/P/dist'
        dist.mkdir(parents=True)
        (dist / 'test.js').write_text('production output')
        self.assertEqual(gate.temporary_output_inventory(workspace), [])
        lock = dist / 'test.js.flat-incremental.json.lock'
        lock.mkdir()
        staged = dist / 'spool.flat-tmp-owned'
        staged.mkdir()
        payload = staged / 'artifact-0.spool'
        payload.write_text('unfinished production payload')
        observed = gate.temporary_output_inventory(workspace)
        self.assertIn(lock.relative_to(workspace).as_posix(), observed)
        self.assertIn(payload.relative_to(workspace).as_posix(), observed)
        self.assertTrue(payload.exists())
        self.assertTrue(lock.exists())

    def test_process_cleanup_flags_are_not_promoted_to_cancellation_cleanup(self):
        commands = [{'process_cleanup': {'wait_completed': True, 'child_reaped': True,
                                        'process_group_absent': True}, 'temporary_outputs_after': []}]
        receipt = {'stage2_suites': {'Flat-Compiler': {'commands': commands}}}
        verdict = gate.production_gates(receipt)
        self.assertTrue(verdict['gates']['production-command-reaping'])
        self.assertTrue(verdict['gates']['production-terminal-staging-absent'])
        self.assertFalse(verdict['gates']['production-subprocess-filesystem-cleanup'])
        commands[0]['process_cleanup']['process_group_absent'] = False
        self.assertFalse(gate.production_gates(receipt)['gates']['production-command-reaping'])
        commands[0]['temporary_outputs_after'] = ['packages/P/dist/spool.flat-tmp-owned']
        self.assertFalse(gate.production_gates(receipt)['gates']['production-terminal-staging-absent'])

    def test_producer_records_reject_duplicate_and_truncated_json(self):
        log = self.root / 'lifecycle.log'
        log.write_text('{"schema":"flat.compiler.lifecycle.v1","scenario":"a","scenario":"b"}\n')
        with self.assertRaisesRegex(ValueError, 'Duplicate'):
            gate.lifecycle_records(log)
        log.write_text('{"schema":"flat.compiler.lifecycle.v1"\n')
        with self.assertRaisesRegex(ValueError, 'Malformed'):
            gate.lifecycle_records(log)

    def test_production_run_rechecks_retained_logs_and_artifacts(self):
        directory = self.root / 'measured'
        directory.mkdir()
        workspace = directory / 'workspace'
        workspace.mkdir()
        sources = directory / 'observed-sources.json'
        self.write(sources, self.sources)
        log = directory / 'run.log'
        log.write_text('ordinary successful run\n')
        journal = directory / 'admissions.jsonl'
        journal.write_text(json.dumps({'decision': 'allow', 'command': '/node',
                                       'args': ['/generated/test.js']}) + '\n')
        def reference(path):
            return {'path': path.relative_to(self.root).as_posix(), 'sha256': gate.bootstrap.digest(path)}
        command = {'status': 'passed', 'exit_status': 0, 'argv': ['/node', '/airship.js', 'run', 'test'],
                   'log_reference': reference(log),
                   'admission_reference': reference(journal), 'admission_offset': 0, 'admission_count': 1,
                   'persistent': [], 'diagnostic_batches': [], 'compiler_sessions': [], 'lifecycle': []}
        run = {'status': 'passed', 'package': 'Flat-Core', 'workspace': 'measured/workspace',
               'source_receipt': reference(sources), 'sources_sha256': gate.fingerprint(self.sources),
               'artifacts': {'test.js': self.run['artifacts']['test.js']['sha256']},
               'artifact_references': {'test.js': self.run['artifacts']['test.js']},
               'commands': [copy.deepcopy(command), copy.deepcopy(command)]}
        run['commands'][0].update(argv=['/node', '/airship.js', 'install', 'test',
                                          '--flatc-script', '/flatc.js'], compiler_invocations=0)
        for index, observed in enumerate(run['commands']):
            cleanup_path = directory / f'cleanup-{index}.json'
            process = {'pid': 42, 'exit_code': 0, 'wait_completed': True,
                       'child_reaped': True, 'process_group_absent': True}
            self.write(cleanup_path, {'schema': 'flat.dogfood.command-cleanup.v1',
                'scope': 'production-command', 'argv': observed['argv'],
                'log_sha256': observed['log_reference']['sha256'],
                'admissions_sha256': observed['admission_reference']['sha256'],
                'process': process, 'temporary_outputs_after': []})
            observed.update(cleanup_reference=reference(cleanup_path), process_cleanup=process,
                            temporary_outputs_after=[])
        self.assertEqual(gate.validate_production_run(self.root, run, 'a' * 64), self.sources)
        run['commands'][0]['lifecycle'] = [self.lifecycle_sample()]
        with self.assertRaisesRegex(ValueError, 'Lifecycle observations changed'):
            gate.validate_production_run(self.root, run, 'a' * 64)
        run['commands'][0]['lifecycle'] = []
        run['commands'][0]['compiler_invocations'] = 1
        with self.assertRaisesRegex(ValueError, 'admission count mismatch'):
            gate.validate_production_run(self.root, run, 'a' * 64)
        run['commands'][0]['compiler_invocations'] = 0
        run['commands'][1]['admission_count'] = 2
        with self.assertRaisesRegex(ValueError, 'boundary mismatch'):
            gate.validate_production_run(self.root, run, 'a' * 64)
        run['commands'][1]['admission_count'] = 1
        log.write_text('tampered')
        with self.assertRaisesRegex(ValueError, 'changed evidence'):
            gate.validate_production_run(self.root, run, 'a' * 64)

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

#!/usr/bin/env python3
"""Isolated tests; never invoke Airship, Java, Maven, or the network."""
import hashlib
import importlib.util
import json
import os
from pathlib import Path
import shutil
import subprocess
import tempfile
import unittest

from unittest import mock
import sys

HERE = Path(__file__).resolve().parent
SPEC = importlib.util.spec_from_file_location("stage_bootstrap", HERE / "stage_bootstrap.py")
bootstrap = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(bootstrap)


class ProcessScopeTests(unittest.TestCase):
    def test_reviewed_lifecycle_inventory_real_children(self):
        for variant in ('exact', 'changed', 'wrong-path', 'missing-step'):
            with self.subTest(variant=variant), tempfile.TemporaryDirectory() as directory:
                root = Path(directory).resolve()
                owner = root / 'packages/Airship'
                owner.mkdir(parents=True)
                original = HERE.parent / 'src/flat/airship/Airship_Test.flat'
                source = root / bootstrap.LIFECYCLE_SOURCE
                source.parent.mkdir(parents=True)
                shutil.copyfile(original, source)
                runner = owner / 'runner.js'
                scripts = list(bootstrap.LIFECYCLE_SCRIPTS)
                if variant == 'changed':
                    scripts[0] = 'process.exit(9);'
                prefix = '.other-' if variant == 'wrong-path' else '.airship-generated-lifecycle-'
                runner.write_text("const fs=require('fs'),cp=require('child_process');" +
                    'const d=' + json.dumps(prefix + '123') + ';fs.mkdirSync(d);' +
                    'const scripts=' + json.dumps(scripts) + ';' +
                    'for(let i=0;i<' + ('2' if variant == 'missing-step' else '3') + ';i++){' +
                    "fs.writeFileSync(d+'/compiler.cjs',scripts[i]);" +
                    "if(i)fs.writeFileSync(d+'/selected.js','committed');" +
                    "try{cp.spawnSync('node',[d+'/compiler.cjs',...(i?[d+'/selected.js']:[])]);}catch(e){} }")
                node = Path(shutil.which('node')).resolve()
                policy = root / 'policy.json'
                journal = root / 'journal'
                policy.write_text(json.dumps({'stage': 1, 'root': str(root), 'node': str(node),
                    'nodeSha256': bootstrap.digest(node), 'journal': str(journal),
                    'fixtures': bootstrap.lifecycle_inventory(root, runner)}))
                env = {**os.environ, 'FLAT_BOOTSTRAP_POLICY': str(policy),
                       'NODE_OPTIONS': '--require=' + str(HERE / 'stage_subprocess_guard.cjs')}
                invoke = lambda: bootstrap.run_command([str(node), str(runner)], owner, env,
                                                       root / 'log', journal, timeout=10)
                if variant == 'exact':
                    self.assertEqual(invoke(), 0)
                else:
                    with self.assertRaises(ValueError):
                        invoke()

    def test_external_gate_uses_actual_frozen_consumer_and_requires_cancellation(self):
        source = (HERE.parent / 'src/flat/airship/AirshipProcess.flat').read_text()
        implementation = source.split('#{executeInProcessScope} = ', 1)[1].split('\n            };', 1)[0] + '\n};'
        for variant in ('real', 'no-request', 'bad-main'):
            with self.subTest(variant=variant), tempfile.TemporaryDirectory() as directory:
                root = Path(directory).resolve()
                generated = root / bootstrap.PRODUCTS[1]
                generated.parent.mkdir(parents=True)
                execute = implementation if variant == 'real' else 'async function(){return 0;};'
                generated.write_text('const execute=' + execute +
                    "const AirshipProcess={execute: a=>execute(require('child_process'),a,'.',true)};" +
                    'const FlatArray={jsStringArrayToFlatArray:a=>a},flat_null=null,flat_main_args=[];' +
                    "process.on('unhandledRejection',()=>process.exit(1));" +
                    '(async()=>{' + ('Airship.main(flat_main_args);' if variant != 'bad-main' else '') + '})();')
                node = Path(shutil.which('node')).resolve()
                journal = root / 'journal'
                policy = {'stage': 1, 'root': str(root), 'node': str(node),
                          'nodeSha256': bootstrap.digest(node), 'journal': str(journal)}
                (root / 'logs').mkdir()
                env = {**os.environ, 'NODE_OPTIONS': '--require=' + str(HERE / 'stage_subprocess_guard.cjs')}
                if variant == 'real':
                    for signum in ('SIGINT', 'SIGTERM'):
                        receipt = bootstrap.external_cancellation_gate(root, root, env, policy, node,
                                                                        signum, command_seconds=10)
                        self.assertEqual(receipt['signal'], signum)
                else:
                    with self.assertRaises(ValueError):
                        bootstrap.external_cancellation_gate(root, root, env, policy, node,
                                                             'SIGTERM', command_seconds=10)

    def test_environment_spoof_cannot_publish_capability(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory).resolve()
            policy = root / 'policy.json'
            policy.write_text('{}')
            env = {**os.environ, 'FLAT_BOOTSTRAP_POLICY': str(policy),
                   'FLAT_PROCESS_SCOPE': json.dumps({'id': 'a' * 32, 'requestFd': 3,
                       'ackFd': 4, 'policySha256': '0' * 64})}
            result = subprocess.run([shutil.which('node'), '--require',
                str(HERE / 'stage_subprocess_guard.cjs'), '-e', 'process.exit(0)'],
                env=env, capture_output=True, text=True, timeout=10)
            self.assertNotEqual(result.returncode, 0)
            self.assertIn('Invalid process scope', result.stderr)

    def test_cleanup_requires_group_absence_even_after_leader_exit(self):
        process = mock.Mock(pid=12345, returncode=0)
        # Leader already exited; a descendant survives TERM until KILL.
        killed = False
        def kill(pid, sig):
            nonlocal killed
            if sig == bootstrap.signal.SIGKILL:
                killed = True
            if sig == 0 and killed:
                raise ProcessLookupError()
        with mock.patch.object(bootstrap.os, 'killpg', side_effect=kill) as calls, \
                mock.patch.object(bootstrap.time, 'monotonic', side_effect=range(100)), \
                mock.patch.object(bootstrap.time, 'sleep'):
            observed = {}
            bootstrap.settle_process_group(process, observed)
            calls.assert_any_call(12345, bootstrap.signal.SIGTERM)
            calls.assert_any_call(12345, bootstrap.signal.SIGKILL)
            self.assertTrue(observed['process_group_absent'])
        with mock.patch.object(bootstrap.os, 'killpg'), \
                mock.patch.object(bootstrap.time, 'monotonic', side_effect=range(100)), \
                mock.patch.object(bootstrap.time, 'sleep'):
            with self.assertRaisesRegex(ValueError, 'cleanup unconfirmed'):
                bootstrap.settle_process_group(process)

    def test_scope_frames_reject_wrong_identity_and_capacity(self):
        with tempfile.TemporaryDirectory() as directory:
            p = Path(directory) / 'policy'
            p.write_text('{}')
            channel = bootstrap.ScopeChannel({'FLAT_BOOTSTRAP_POLICY': str(p)})
            try:
                os.write(channel.write, b'{"kind":"cancel","scope":"wrong","pid":1}\n')
                with self.assertRaisesRegex(ValueError, 'Invalid process scope'):
                    channel.drain()
                os.write(channel.write, b'x' * 1025 + b'\n')
                with self.assertRaisesRegex(ValueError, 'capacity'):
                    channel.drain()
            finally:
                channel.close()

    def test_exact_fixture_outcome_accounting_and_detached_rejection(self):
        for variant in ('exact', 'wrong-exit', 'unconsumed', 'detached', 'mutated-runner'):
            with self.subTest(variant=variant), tempfile.TemporaryDirectory() as directory:
                root = Path(directory).resolve()
                node = Path(shutil.which('node')).resolve()
                script = 'process.exit(7)'
                runner = root / 'runner.js'
                runner.write_text("try { require('child_process').spawnSync('node',['-e'," +
                    json.dumps(script) + '], {detached:' + ('true' if variant == 'detached' else 'false') +
                    "}); } catch (_) {}")
                entry = {'id': 'expected-seven', 'command': 'node', 'args': ['-e', script],
                         'runner': str(runner), 'runnerSha256': bootstrap.digest(runner),
                         'cwd': str(root), 'count': 1, 'files': [],
                         'outcome': {'exit': 0 if variant == 'wrong-exit' else 7, 'signal': None}}
                if variant == 'unconsumed':
                    entry['args'] = ['-e', 'not invoked']
                    runner.write_text('')
                    entry['runnerSha256'] = bootstrap.digest(runner)
                if variant == 'mutated-runner':
                    runner.write_text(runner.read_text() + '\n// changed')
                policy = root / 'policy.json'
                journal = root / 'journal'
                policy.write_text(json.dumps({'stage': 1, 'root': str(root), 'node': str(node),
                    'nodeSha256': bootstrap.digest(node), 'journal': str(journal), 'fixtures': [entry]}))
                env = {**os.environ, 'FLAT_BOOTSTRAP_POLICY': str(policy),
                       'NODE_OPTIONS': '--require=' + str(HERE / 'stage_subprocess_guard.cjs')}
                if variant == 'exact':
                    self.assertEqual(bootstrap.run_command([str(node), str(runner)], root, env,
                        root / 'log', journal, timeout=10), 0)
                else:
                    with self.assertRaises(ValueError):
                        bootstrap.run_command([str(node), str(runner)], root, env,
                                              root / 'log', journal, timeout=10)

    def test_real_leader_exit_with_term_resistant_descendant(self):
        if not hasattr(os, 'fork'):
            self.skipTest('POSIX process-group fixture')
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            pid_file = root / 'descendant.pid'
            fixture = ("import os,signal,sys\n"
                "r,w=os.pipe()\n"
                "pid=os.fork()\n"
                "if pid==0:\n"
                " os.close(r); signal.signal(signal.SIGTERM,signal.SIG_IGN)\n"
                " open(sys.argv[1],'w').write(str(os.getpid()))\n"
                " os.write(w,b'R'); os.close(w)\n"
                " while True: signal.pause()\n"
                "os.close(w); assert os.read(r,1)==b'R'; os.close(r)\n")
            observed = {}
            self.assertEqual(bootstrap.run_command([sys.executable, '-I', '-c', fixture, str(pid_file)],
                root, os.environ.copy(), root / 'log', root / 'journal', timeout=10,
                cleanup_observation=observed), 0)
            self.assertTrue(observed['process_group_absent'])
            self.assertTrue(observed['child_reaped'])
            with self.assertRaises(ProcessLookupError):
                os.kill(int(pid_file.read_text()), 0)

    def test_guarded_startup_success_and_actual_signal_cancellation(self):
        for cancelled in (False, True):
            with self.subTest(cancelled=cancelled), tempfile.TemporaryDirectory() as directory:
                root = Path(directory).resolve()
                node = Path(shutil.which('node')).resolve()
                main = root / 'main.js'
                main.write_text("const k=Symbol.for('flat.stage.processScope.v1'),s=globalThis[k];"
                    "if(!Object.isFrozen(s)||Object.keys(s).length!==4) throw Error('shape');"
                    "if(Object.getOwnPropertyDescriptor(globalThis,k).writable) throw Error('mutable');"
                    "let seen=0; const off=s.subscribeCancellation(()=>seen++); off();"
                    + ("s.subscribeCancellation(()=>console.log('cancel-observed'));"
                       "process.kill(process.pid,'SIGTERM'); setInterval(()=>{},1000);" if cancelled else
                       "console.log('capability-ready');"))
                policy = root / 'policy.json'
                journal = root / 'journal'
                policy.write_text(json.dumps({'stage': 1, 'root': str(root), 'node': str(node),
                    'nodeSha256': bootstrap.digest(node), 'journal': str(journal)}))
                env = {**os.environ, 'FLAT_BOOTSTRAP_POLICY': str(policy),
                       'NODE_OPTIONS': '--require=' + str(HERE / 'stage_subprocess_guard.cjs')}
                if cancelled:
                    with self.assertRaisesRegex(ValueError, 'cancellation'):
                        bootstrap.run_command([str(node), str(main)], root, env, root / 'log', journal, timeout=10)
                    self.assertIn('cancel-observed', (root / 'log').read_text())
                else:
                    self.assertEqual(bootstrap.run_command([str(node), str(main)], root, env,
                        root / 'log', journal, timeout=10), 0)
                receipt = json.loads((root / 'log.scope.json').read_text())
                self.assertEqual(receipt['scope_cancelled'], cancelled)
                self.assertTrue(receipt['cleanup']['process_group_absent'])

    def test_pinned_shell_and_missing_executable_fixture_outcomes(self):
        for missing in (False, True):
            with self.subTest(missing=missing), tempfile.TemporaryDirectory() as directory:
                root = Path(directory).resolve()
                node = Path(shutil.which('node')).resolve()
                command = str(root / 'absent-executable') if missing else '/bin/sh'
                args = [] if missing else ['-c', 'exit 7']
                runner = root / 'runner.js'
                runner.write_text("require('child_process').spawnSync(" + json.dumps(command) +
                                  ',' + json.dumps(args) + ');')
                entry = {'id': 'exact-fixture', 'command': command, 'args': args,
                         'runner': str(runner), 'runnerSha256': bootstrap.digest(runner),
                         'cwd': str(root), 'files': [], 'count': 1,
                         'outcome': {'error': 'ENOENT'} if missing else {'exit': 7, 'signal': None}}
                if not missing:
                    entry['commandSha256'] = bootstrap.digest(Path(command))
                policy = root / 'policy.json'
                journal = root / 'journal'
                policy.write_text(json.dumps({'stage': 1, 'root': str(root), 'node': str(node),
                    'nodeSha256': bootstrap.digest(node), 'journal': str(journal), 'fixtures': [entry]}))
                env = {**os.environ, 'FLAT_BOOTSTRAP_POLICY': str(policy),
                       'NODE_OPTIONS': '--require=' + str(HERE / 'stage_subprocess_guard.cjs')}
                self.assertEqual(bootstrap.run_command([str(node), str(runner)], root, env,
                    root / 'log', journal, timeout=10), 0)
                receipt = json.loads((root / 'log.scope.json').read_text())
                self.assertEqual(receipt['fixtures_started'], 1)
                self.assertEqual(receipt['fixtures_finished'], 1)

    def test_guarded_startup_and_nested_cancellation(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory).resolve()
            node = Path(shutil.which('node')).resolve()
            child = root / 'child.js'
            child.write_text("globalThis[Symbol.for('flat.stage.processScope.v1')].requestCancellation('nested');")
            main = root / 'main.js'
            main.write_text("const s=globalThis[Symbol.for('flat.stage.processScope.v1')];"
                            "if(!Object.isFrozen(s)||s.version!==1) throw Error('missing capability');"
                            "require('child_process').spawnSync('node',[" + json.dumps(str(child)) + "]); ")
            policy = root / 'policy.json'
            journal = root / 'journal'
            policy.write_text(json.dumps({'stage': 1, 'root': str(root), 'node': str(node),
                'nodeSha256': bootstrap.digest(node), 'journal': str(journal)}))
            env = {**os.environ, 'FLAT_BOOTSTRAP_POLICY': str(policy),
                   'NODE_OPTIONS': '--require=' + str(HERE / 'stage_subprocess_guard.cjs')}
            observed = {}
            with self.assertRaisesRegex(ValueError, 'cancel'):
                bootstrap.run_command([str(node), str(main)], root, env, root / 'log', journal,
                                      timeout=10, cleanup_observation=observed)
            self.assertTrue(observed['process_group_absent'])
            self.assertTrue(observed['child_reaped'])


class BootstrapTests(unittest.TestCase):
    def test_setup_failure_retains_manifest_and_partial_workspace(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            output = root / 'output'
            output.mkdir()

            def partial_snapshot(repo, destination, sources, budget=None):
                destination.mkdir()
                (destination / 'partial').write_text('retained diagnostic input')
                raise ValueError('source changed during snapshot')

            with mock.patch.object(bootstrap, 'snapshot', side_effect=partial_snapshot):
                with self.assertRaisesRegex(ValueError, 'source changed'):
                    bootstrap.stage(0, root / 'repo', output, {}, {})
            receipt = json.loads((output / 'stage0/manifest.json').read_text())
            self.assertEqual(receipt['status'], 'failed')
            self.assertEqual(receipt['stage'], 0)
            self.assertEqual(receipt['commands'], [])
            self.assertTrue((output / 'stage0/workspace/partial').is_file())
            with self.assertRaises(FileExistsError):
                bootstrap.stage(0, root / 'repo', output, {}, {})
            self.assertEqual(json.loads((output / 'stage0/manifest.json').read_text()), receipt)

    def test_reviewed_receipt_copy_preserves_exact_bytes(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            source = root / 'review.json'
            raw = b'{ "schema": 1, "provenance": "reviewed bytes" }\n'
            source.write_bytes(raw)
            target = root / 'retained/review.json'
            expected = hashlib.sha256(raw).hexdigest()
            bootstrap.copy_seed(source, target, expected)
            self.assertEqual(target.read_bytes(), raw)
            self.assertEqual(bootstrap.digest(target), expected)

    def test_observed_watchdog_cleanup_uses_ready_gate_and_retains_receipt(self):
        with tempfile.TemporaryDirectory() as directory:
            output = Path(directory).resolve() / 'observed'
            # Readiness is a FIFO handshake; Popen.wait may internally poll with
            # sleeps, which are an implementation detail, not a readiness proof.
            receipt = bootstrap.observe_subprocess_cleanup(output)
            self.assertEqual(receipt['status'], 'observed')
            self.assertEqual(receipt['scope'], 'watchdog-fixture-only')
            self.assertFalse(receipt['production_compiler_executed'])
            self.assertTrue(receipt['children_reaped'])
            self.assertTrue(receipt['temporary_outputs_removed'])
            self.assertEqual([value['termination_reason'] for value in receipt['scenarios']], ['cancel', 'timeout'])
            for value in receipt['scenarios']:
                self.assertTrue(value['wait_completed'])
                self.assertTrue(value['process_group_absent'])
                self.assertTrue(value['child_reaped'])
                self.assertEqual(value['exit_code'], -bootstrap.signal.SIGTERM)
                self.assertEqual(bootstrap.digest(output / value['log']), value['log_sha256'])
            self.assertEqual(json.loads((output / 'subprocess-cleanup.json').read_text()), receipt)
            self.assertEqual(sorted(path.name for path in output.iterdir()),
                             ['cancel.log', 'subprocess-cleanup.json', 'timeout.log'])

    def test_lifecycle_fixture_launch_failure_never_claims_cleanup_success(self):
        with tempfile.TemporaryDirectory() as directory:
            output = Path(directory).resolve() / 'failed'
            with mock.patch.object(bootstrap.subprocess, 'Popen', side_effect=OSError('launch failed')):
                with self.assertRaisesRegex(OSError, 'launch failed'):
                    bootstrap.observe_subprocess_cleanup(output)
            receipt = json.loads((output / 'subprocess-cleanup.json').read_text())
            self.assertEqual(receipt['status'], 'failed')
            self.assertFalse(receipt['children_reaped'])
            self.assertFalse(receipt['temporary_outputs_removed'])
            self.assertFalse(any(path.name.startswith('lifecycle-') for path in output.iterdir()))

    def test_lifecycle_timeout_capacity_is_validated_before_output(self):
        with tempfile.TemporaryDirectory() as directory:
            output = Path(directory) / 'unused'
            for timeout in (0, -1, 61):
                with self.assertRaises(ValueError):
                    bootstrap.observe_subprocess_cleanup(output, timeout=timeout)
            self.assertFalse(output.exists())

    def test_reviewed_bundle_checks_delivery_before_any_tool_launch(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            tool = root / 'seed'
            tool.write_text('reviewed test seed; never execute')
            tool.chmod(0o755)
            seeds = root / 'seeds.json'
            seeds.write_text(json.dumps({name: {'path': str(tool.resolve()), 'sha256': bootstrap.digest(tool)}
                                         for name in bootstrap.SEED_NAMES}))
            review = root / 'review.json'
            review.write_text(json.dumps({'schema': 1, 'seeds_sha256': bootstrap.digest(seeds),
                                          'reviewer': 'test reviewer', 'provenance': 'test producer receipt',
                                          'source_revision': 'a' * 40}))
            with mock.patch.object(bootstrap.subprocess, 'run') as run, \
                    mock.patch.object(bootstrap.subprocess, 'Popen') as start:
                entries, receipt, selected = bootstrap.load_reviewed_bundle(seeds, review, bootstrap.digest(review))
                self.assertEqual(set(selected), bootstrap.SEED_NAMES)
                self.assertEqual(receipt['provenance'], 'test producer receipt')
                run.assert_not_called()
                start.assert_not_called()
                tool.write_text('changed after review')
                with self.assertRaisesRegex(ValueError, 'Seed digest mismatch'):
                    bootstrap.load_reviewed_bundle(seeds, review, bootstrap.digest(review))

    def test_preflight_records_delivery_without_claiming_acceptance(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory).resolve()
            output = root / 'preflight'
            tool = root / 'seed'
            tool.write_text('not executed')
            seeds = {name: (tool, bootstrap.digest(tool)) for name in bootstrap.SEED_NAMES}
            argv = ['stage_bootstrap.py', '--repo', str(root / 'repo'), '--output', str(output),
                    '--seeds', str(root / 'seeds.json'), '--review', str(root / 'review.json'),
                    '--review-sha256', 'a' * 64, '--preflight-only']
            with mock.patch.object(sys, 'argv', argv), \
                    mock.patch.object(bootstrap, 'load_reviewed_bundle', return_value=({}, {'schema': 1}, seeds)), \
                    mock.patch.object(bootstrap, 'digest', return_value='b' * 64), \
                    mock.patch.object(bootstrap, 'stage') as stage, \
                    mock.patch.object(bootstrap.subprocess, 'run') as run:
                bootstrap.main()
                stage.assert_not_called()
                run.assert_not_called()
            receipt = json.loads((output / 'seed-preflight.json').read_text())
            self.assertEqual(receipt['status'], 'validated-not-executed')
            self.assertFalse(receipt['acceptance'])
            self.assertFalse((output / 'acceptance.json').exists())
            self.assertEqual(set(receipt['seeds']), bootstrap.SEED_NAMES)

    def test_failed_command_preserves_exit_status(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            with self.assertRaises(bootstrap.CommandFailure) as caught:
                bootstrap.run_command([sys.executable, '-c', 'raise SystemExit(7)'], root,
                                      os.environ.copy(), root / 'command.log', root / 'journal')
            self.assertEqual(caught.exception.exit_code, 7)
            self.assertIn('Command exited 7', str(caught.exception))

    def test_receipt_publication_is_atomic_and_preserves_last_complete_value(self):
        with tempfile.TemporaryDirectory() as directory:
            path = Path(directory) / 'manifest.json'
            bootstrap.write_json(path, {'status': 'running'})
            with mock.patch.object(Path, 'replace', side_effect=OSError('publication failed')):
                with self.assertRaisesRegex(OSError, 'publication failed'):
                    bootstrap.write_json(path, {'status': 'passed'})
            self.assertEqual(json.loads(path.read_text()), {'status': 'running'})
            self.assertFalse(path.with_name('manifest.json.tmp').exists())
            bootstrap.write_json(path, {'status': 'passed'})
            self.assertEqual(json.loads(path.read_text()), {'status': 'passed'})

    def test_admission_records_are_streamed_from_command_offset_and_bounded(self):
        with tempfile.TemporaryDirectory() as directory:
            path = Path(directory) / 'journal.jsonl'
            self.assertEqual(list(bootstrap.admission_records(path)), [])
            first = json.dumps({'decision': 'allow', 'command': 'old'}) + '\n'
            second = json.dumps({'decision': 'allow', 'command': 'selected'}) + '\n'
            path.write_text(first + second)
            self.assertEqual(list(bootstrap.admission_records(path, len(first))),
                             [{'decision': 'allow', 'command': 'selected'}])
            path.write_text('x' * (1024 * 1024 + 1))
            with self.assertRaisesRegex(ValueError, 'record exceeded capacity'):
                list(bootstrap.admission_records(path))
            path.write_text('[]\n')
            with self.assertRaisesRegex(ValueError, 'Invalid subprocess admission record'):
                list(bootstrap.admission_records(path))

    def test_snapshot_byte_capacity_and_aggregate_limits(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            (root / 'input.flat').write_bytes(b'12345')
            with self.assertRaisesRegex(ValueError, 'snapshot byte'):
                bootstrap.inventory(root, max_bytes=4)
            budget = bootstrap.RunBudget(root, seconds=10, max_bytes=4)
            with self.assertRaisesRegex(ValueError, 'storage'):
                budget.check()
            with mock.patch.object(bootstrap.time, 'monotonic', return_value=100):
                budget = bootstrap.RunBudget(root, seconds=1, max_bytes=100)
            with mock.patch.object(bootstrap.time, 'monotonic', return_value=102):
                with self.assertRaisesRegex(ValueError, 'deadline'):
                    budget.check()

    def test_review_requires_independent_manifest_digest_and_provenance(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            seeds = root / 'seeds.json'
            seeds.write_text('{}')
            review = root / 'review.json'
            review.write_text(json.dumps({'schema': 1, 'seeds_sha256': bootstrap.digest(seeds),
                                         'reviewer': 'designated reviewer', 'provenance': 'offline rebuild receipt',
                                         'source_revision': 'a' * 40}))
            accepted = bootstrap.reviewed_seeds(seeds, review, bootstrap.digest(review))
            self.assertEqual(accepted['reviewer'], 'designated reviewer')
            seeds.write_text('{"changed":true}')
            with self.assertRaises(ValueError):
                bootstrap.reviewed_seeds(seeds, review, bootstrap.digest(review))
            with self.assertRaises(ValueError):
                bootstrap.reviewed_seeds(seeds, review, '0' * 64)

    def test_revision_records_dirty_source_identity(self):
        completed = subprocess.CompletedProcess([], 0, stdout='a' * 40 + '\n')
        with mock.patch.object(bootstrap.subprocess, 'run', return_value=completed) as run:
            self.assertEqual(bootstrap.source_revision(Path('/repo'), Path('/pinned/git')), 'a' * 40)
            self.assertEqual(run.call_args.kwargs['timeout'], 10)

    def test_expired_budget_does_not_launch_a_command(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            with mock.patch.object(bootstrap.time, 'monotonic', return_value=0):
                budget = bootstrap.RunBudget(root, seconds=1)
            with mock.patch.object(bootstrap.time, 'monotonic', return_value=2), \
                    mock.patch.object(bootstrap.subprocess, 'Popen') as start:
                with self.assertRaisesRegex(ValueError, 'deadline'):
                    bootstrap.run_command(['not-launched'], root, {}, root / 'log',
                                          root / 'journal', budget=budget)
                start.assert_not_called()

    def test_budget_failure_terminates_running_process_group(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            process = mock.Mock(pid=12345)
            process.poll.return_value = None
            budget = mock.Mock(deadline=bootstrap.time.monotonic() + 100)
            budget.check.side_effect = [None, ValueError('Aggregate run storage capacity exceeded')]
            killed = False
            def kill_group(pid, sig):
                nonlocal killed
                if sig == bootstrap.signal.SIGKILL:
                    killed = True
                if sig == 0 and killed:
                    raise ProcessLookupError()
            with mock.patch.object(bootstrap.subprocess, 'Popen', return_value=process), \
                    mock.patch.object(bootstrap.os, 'killpg', side_effect=kill_group) as kill:
                with self.assertRaisesRegex(ValueError, 'storage'):
                    bootstrap.run_command(['not-executed'], root, {}, root / 'log',
                                          root / 'journal', budget=budget)
                kill.assert_any_call(12345, bootstrap.signal.SIGTERM)
                kill.assert_any_call(12345, bootstrap.signal.SIGKILL)
                process.wait.assert_called()

    def test_seed_entry_rejects_unknown_keys_and_non_digest(self):
        with tempfile.TemporaryDirectory() as directory:
            seed = Path(directory) / 'seed'
            seed.write_text('seed')
            for entry in ({'path': str(seed), 'sha256': bootstrap.digest(seed), 'reviewed': True},
                          {'path': str(seed), 'sha256': 'not-a-digest'}):
                with self.assertRaises(ValueError):
                    bootstrap.verified_seed(entry)

    def test_ci_has_required_non_skippable_stage_invocation(self):
        workflow = (HERE.parents[2] / '.github/workflows/flat-compiler-test.yml').read_text()
        self.assertIn('stage-acceptance:', workflow)
        self.assertIn('inputs.review_sha256', workflow)
        self.assertIn('--review-sha256 "$REVIEW_SHA256"', workflow)
        stage_job = workflow.split('  stage-acceptance:', 1)[1].split('\n  build:', 1)[0]
        self.assertIn('runs-on: ubuntu-latest', stage_job)
        self.assertNotIn('self-hosted', stage_job)
        self.assertNotIn('environment:', stage_job)
        self.assertIn('timeout-minutes: 65', stage_job)
        self.assertNotIn('continue-on-error', stage_job)
        self.assertIn("if: github.event_name == 'workflow_dispatch'", stage_job)
        self.assertIn('workflow_dispatch:', workflow)
        self.assertIn('inputs.seed_manifest', stage_job)
        self.assertIn('inputs.review_receipt', stage_job)
        self.assertIn('inputs.review_sha256', stage_job)
        self.assertIn('test -n "$SEEDS" && test -f "$SEEDS"', stage_job)
        self.assertIn('stage_bootstrap.py', stage_job)

    def test_source_provenance_records_baseline_and_dirty_manifest(self):
        revision = subprocess.CompletedProcess([], 0, stdout='a' * 40 + '\n')
        dirty = subprocess.CompletedProcess([], 0, stdout=' M packages/Airship/src/Input.flat\0')
        with mock.patch.object(bootstrap.subprocess, 'run', side_effect=[revision, dirty]):
            value = bootstrap.source_provenance(Path('/repo'), Path('/pinned/git'))
        self.assertEqual(value['baseline_revision'], 'a' * 40)
        self.assertTrue(value['dirty'])
        self.assertEqual(value['status_porcelain_v1_z'], dirty.stdout)
        self.assertEqual(value['status_sha256'], hashlib.sha256(dirty.stdout.encode()).hexdigest())

    def test_explicit_experiment_budgets_are_capped(self):
        self.assertEqual(bootstrap.execution_limits(10800, 3000), (10800, 3000))
        for total, command in ((10801, 3000), (10800, 3601), (100, 101), (0, 1)):
            with self.assertRaises(ValueError):
                bootstrap.execution_limits(total, command)

    def test_snapshot_origin_real_git_lookup_without_primary_git_pointer(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory).resolve()
            origin = 'git@github.com:FlatLang/Flat.git'
            metadata = bootstrap.install_snapshot_origin(root, origin)
            package = root / 'packages/Flat-Compiler'
            package.mkdir(parents=True)
            result = subprocess.run([shutil.which('git'), 'config', '--get', 'remote.origin.url'],
                                    cwd=package, capture_output=True, text=True, timeout=10,
                                    env={**os.environ, 'GIT_CONFIG_NOSYSTEM': '1',
                                         'GIT_CONFIG_GLOBAL': os.devnull})
            self.assertEqual(result.returncode, 0, result.stderr)
            self.assertEqual(result.stdout, origin + '\n')
            self.assertTrue((root / '.git').is_dir())
            self.assertFalse((root / '.git').is_symlink())
            self.assertEqual(metadata['.git/config'], bootstrap.digest(root / '.git/config'))
            self.assertNotIn('include', (root / '.git/config').read_text())

    def test_origin_capture_missing_or_hostile_fails_closed(self):
        valid = subprocess.CompletedProcess([], 0, stdout='https://github.com/FlatLang/Flat.git\n')
        with mock.patch.object(bootstrap.subprocess, 'run', return_value=valid) as run:
            self.assertEqual(bootstrap.capture_origin(Path('/repo'), Path('/git')),
                             'https://github.com/FlatLang/Flat.git')
            self.assertIn('--local', run.call_args.args[0])
        for origin in ('', 'https://example.invalid/Flat.git', 'git@github.com:FlatLang/Flat.git\n[include]',
                       'https://user:secret@github.com/FlatLang/Flat.git', 'file:///tmp/Flat',
                       'git@github.com:FlatLang/Flat.git"', 'https://github.com/FlatLang/../Flat.git'):
            with self.subTest(origin=origin), tempfile.TemporaryDirectory() as directory:
                with self.assertRaises(ValueError):
                    bootstrap.install_snapshot_origin(Path(directory), origin)
        with mock.patch.object(bootstrap.subprocess, 'run',
                               side_effect=subprocess.CalledProcessError(1, ['git'])):
            with self.assertRaisesRegex(ValueError, 'origin'):
                bootstrap.capture_origin(Path('/repo'), Path('/git'))

    def test_seed_digest_is_required_and_verified(self):
        with tempfile.TemporaryDirectory() as directory:
            path = Path(directory) / "seed"
            path.write_text("seed")
            with self.assertRaises(ValueError):
                bootstrap.verified_seed({"path": str(path), "sha256": "0" * 64})
            digest = hashlib.sha256(b"seed").hexdigest()
            self.assertEqual(bootstrap.verified_seed({"path": str(path), "sha256": digest}), path.resolve())

    def test_artifact_parent_symlink_cannot_supply_outside_stale_output(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory).resolve()
            workspace = root / 'workspace'
            outside = root / 'outside'
            for name in bootstrap.PRODUCTS:
                path = workspace / name
                path.parent.mkdir(parents=True, exist_ok=True)
                path.write_text('fresh test output')
            product = workspace / bootstrap.PRODUCTS[0]
            product.unlink()
            product.parent.rmdir()
            outside.mkdir()
            (outside / product.name).write_text('outside stale output')
            product.parent.symlink_to(outside, target_is_directory=True)
            with self.assertRaisesRegex(ValueError, 'Missing fresh stage product'):
                bootstrap.artifacts(workspace)

    def test_outputs_cannot_be_empty_or_stale(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            with self.assertRaises(ValueError):
                bootstrap.artifacts(root)
            for name in bootstrap.PRODUCTS:
                path = root / name
                path.parent.mkdir(parents=True, exist_ok=True)
                path.write_text("output")
            self.assertEqual(len(bootstrap.artifacts(root)), 2)
            with self.assertRaises(FileExistsError):
                bootstrap.fresh_directory(root)

    def test_snapshot_excludes_generated_and_rejects_symlinks(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            (root / "src").mkdir()
            (root / "src/input.flat").write_text("source")
            (root / "dist").mkdir()
            (root / "dist/old.js").write_text("stale")
            self.assertEqual(list(bootstrap.inventory(root)), ["src/input.flat"])
            (root / "src/link.flat").symlink_to(root / "src/input.flat")
            with self.assertRaises(ValueError):
                bootstrap.inventory(root)

    def test_stage0_resolution_must_select_reviewed_jar_exactly(self):
        with tempfile.TemporaryDirectory() as directory:
            log = Path(directory) / 'resolution.log'
            expected = Path('/reviewed/compiler seed.jar')
            log.write_text('java -jar "/reviewed/compiler seed.jar" -target es6\n')
            self.assertEqual(bootstrap.verify_resolution(log, expected, 'java')[:3],
                             ['java', '-jar', str(expected)])
            for text in ('java -jar /wrong.jar\n', 'java -version\n',
                         'java -jar "/reviewed/compiler seed.jar"\njava -jar other.jar\n'):
                log.write_text(text)
                with self.assertRaises(ValueError):
                    bootstrap.verify_resolution(log, expected, 'java')

    def test_real_resolution_log_requires_exact_single_lookup(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            log = root / 'resolution.log'
            expected = root / 'packages/Flat-Compiler/dist/es6/index.js'
            with self.assertRaises(ValueError):
                bootstrap.verify_resolution(log, expected)
            for content in ('node /wrong/index.js -target es6\n',
                            f'prefix node {expected} -target es6\n',
                            f'node {expected} -target es6\nnode {expected}\n'):
                log.write_text(content)
                with self.assertRaises(ValueError):
                    bootstrap.verify_resolution(log, expected)
            log.write_text(f'node {expected} -target es6\n')
            self.assertEqual(bootstrap.verify_resolution(log, expected)[1], str(expected))

    def test_stage_commands_select_tools_explicitly_without_output_facades(self):
        workspace = Path('/isolated/workspace')
        seed = Path('/isolated/seeds/flatc.js')
        for number in (1, 2):
            command = bootstrap.stage_command('/node', '/airship.js', workspace, number,
                                              'Flat-Compiler', 'install', 'flatc', compiler_seed=seed)
            self.assertEqual(command[command.index('--flatc-script') + 1], str(seed))
            self.assertEqual(command[command.index('--flatc-runtime') + 1], 'flat')
            self.assertIn('--use-links', command)
            self.assertNotIn('--flatc-jar', command)
            self.assertNotIn(str(workspace / bootstrap.PRODUCTS[0]), command)
        command = bootstrap.stage_command('/node', '/seed-airship.js', workspace, 0,
                                          'Airship', 'install', jar='/seed.jar')
        self.assertIn('--flatc-jar', command)
        self.assertNotIn('--flatc-script', command)
        for number, options in ((0, {}), (1, {}), (0, {'jar': '/seed.jar', 'compiler_seed': seed}),
                                (1, {'jar': '/seed.jar', 'compiler_seed': seed})):
            with self.subTest(number=number, options=options), self.assertRaises(ValueError):
                bootstrap.stage_command('/node', '/airship.js', workspace, number,
                                        'Airship', 'install', **options)

    def test_companion_gate_requires_fresh_output_six_cases_and_two_scopes(self):
        for variant in ('exact', 'missing-output', 'wrong-summary', 'cancel-failure'):
            with self.subTest(variant=variant), tempfile.TemporaryDirectory() as directory:
                root = Path(directory).resolve()
                (root / 'logs').mkdir()
                output = root / 'packages/Airship/dist/owned-process-test.js'
                def install(*args, **kwargs):
                    self.assertEqual(args, ('Airship', 'install', 'owned-process-test'))
                    if variant != 'missing-output':
                        output.parent.mkdir(parents=True)
                        output.write_text('fresh companion')
                def ordinary(command, cwd, env, log, journal, **kwargs):
                    self.assertNotIn('NODE_OPTIONS', env)
                    self.assertNotIn('FLAT_BOOTSTRAP_POLICY', env)
                    self.assertNotIn('FLAT_PROCESS_SCOPE', env)
                    log.write_text('Ordinary process companion: ' +
                                   ('5' if variant == 'wrong-summary' else '6') + ' cases, 0 failures')
                    return 0
                with mock.patch.object(bootstrap, 'run_command', side_effect=ordinary), \
                        mock.patch.object(bootstrap, 'external_cancellation_gate',
                            side_effect=ValueError('missing cancellation') if variant == 'cancel-failure'
                            else lambda *a: {'signal': a[5]}) as cancellation:
                    env = {'NODE_OPTIONS': 'guard', 'FLAT_BOOTSTRAP_POLICY': 'policy', 'FLAT_PROCESS_SCOPE': 'scope'}
                    if variant == 'exact':
                        result = bootstrap.process_companion_gates(root, root, env, {}, Path('/node'), install)
                        self.assertEqual([item['signal'] for item in result['external_cancellations']],
                                         ['SIGINT', 'SIGTERM'])
                        self.assertEqual(cancellation.call_count, 2)
                    else:
                        with self.assertRaises(ValueError):
                            bootstrap.process_companion_gates(root, root, env, {}, Path('/node'), install)

    def test_companion_manifest_contract_is_mandatory(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            manifest = root / 'packages/Airship/flat.json'
            manifest.parent.mkdir(parents=True)
            manifest.write_text(json.dumps({'sources': {}}))
            with self.assertRaisesRegex(ValueError, 'owned-process-test'):
                bootstrap.companion_contract(root)
            value = {'mainClass': 'flat/airship/AirshipOwnedProcess_TestSuite',
                     'source': 'src/**/*_Test*.flat', 'outFile': 'owned-process-test.js',
                     'dependencies': {'https://github.com/FlatLang/Test.git': 'master'}}
            manifest.write_text(json.dumps({'sources': {'owned-process-test': value}}))
            bootstrap.companion_contract(root)
            value['mainClass'] = 'flat/airship/Airship_TestSuite'
            manifest.write_text(json.dumps({'sources': {'owned-process-test': value}}))
            with self.assertRaises(ValueError):
                bootstrap.companion_contract(root)

    def test_reviewed_lifecycle_inventory_requires_exact_source(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory).resolve()
            source = root / 'packages/Airship/src/flat/airship/Airship_Test.flat'
            source.parent.mkdir(parents=True)
            source.write_text('unreviewed source')
            runner = root / 'test.js'
            runner.write_text('runner')
            with self.assertRaisesRegex(ValueError, 'reviewed lifecycle'):
                bootstrap.lifecycle_inventory(root, runner)

    def test_frozen_lifecycle_inventory_exact_contract(self):
        root = HERE.parents[2]
        source = root / bootstrap.LIFECYCLE_SOURCE
        self.assertEqual(bootstrap.digest(source),
                         '1221b785ea5d530d888341ac683e4a77c21d4f140b9fc054372f4d371d1b95a8')
        scripts = (
            'process.exit(0);',
            "const fs=require('fs'); if(fs.readFileSync(process.argv[2],'utf8')!=='committed') process.exit(9); process.exit(7);",
            "const fs=require('fs'); if(fs.readFileSync(process.argv[2],'utf8')!=='committed') process.exit(9);",
        )
        self.assertEqual(bootstrap.LIFECYCLE_SCRIPTS, scripts)
        entries = bootstrap.lifecycle_inventory(root, source)
        self.assertEqual(len(entries), 3)
        for index, entry in enumerate(entries):
            self.assertEqual(entry['id'], f'airship-lifecycle-{index}')
            self.assertEqual(entry['command'], 'node')
            self.assertEqual(entry['args'], ['{lifecycle}/compiler.cjs'] +
                             ([] if index == 0 else ['{lifecycle}/selected.js']))
            self.assertEqual(entry['lifecycleStep'], index)
            self.assertEqual(entry['count'], 1)
            self.assertEqual(entry['outcome'], {'exit': 7 if index == 1 else 0, 'signal': None})
            self.assertEqual(entry['scriptSha256'], hashlib.sha256(scripts[index].encode()).hexdigest())
            self.assertEqual(entry['runnerSha256'], bootstrap.digest(source))
            self.assertEqual(entry['files'], [{'path': str(source), 'sha256': bootstrap.digest(source)}])

    def test_stage_chain_isolated_explicit_and_missing_gate_fails(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory).resolve()
            repo = root / "repo"
            for name in ('Airship', 'Flat-Project-Loading', 'Flat-Parser', 'Flat-Name-Binding',
                         'Flat-Bound-IR', 'Flat-ES6-Bound-Writer', 'Flat-Compiler'):
                package = repo / "packages" / name
                package.mkdir(parents=True)
                (package / "flat.json").write_text('{}')
            sources = bootstrap.inventory(repo)
            seed_file = root / "seed"
            seed_file.write_text('seed')
            seeds = {name: (seed_file, bootstrap.digest(seed_file)) for name in
                     ("airship", "node", "git", "java", "java_compiler", "java_backend")}
            output = root / "output"
            output.mkdir()
            calls = []

            def fake_command(command, cwd, env, log, journal, **kwargs):
                policy = json.loads(Path(env['FLAT_BOOTSTRAP_POLICY']).read_text())
                workspace = Path(policy['root'])
                self.assertNotEqual(workspace, repo)
                self.assertTrue(Path(env['HOME']).is_relative_to(output))
                self.assertTrue(Path(env['FLAT_HOME']).is_relative_to(output))
                runtime = command[command.index('--flatc-runtime') + 1]
                self.assertEqual(runtime, 'java' if policy['stage'] == 0 else 'flat')
                if policy['stage'] > 0:
                    self.assertFalse((workspace / 'packages/Flat/target').exists())
                    self.assertEqual(bootstrap.digest(Path(policy['seed'])), policy['seedSha256'])
                    self.assertNotEqual(Path(policy['seed']), workspace / bootstrap.PRODUCTS[0])
                    self.assertNotIn('--flatc-jar', command)
                if '--debug' in command:
                    self.assertIs(policy.get('debugOnly'), True)
                    if policy['stage'] > 0:
                        self.assertFalse(Path(policy['compiler']).is_symlink())
                        self.assertEqual(Path(policy['compiler']), Path(policy['seed']))
                        self.assertEqual(command[command.index('--flatc-script') + 1], policy['seed'])
                        log.write_text('node ' + policy['compiler'] + ' -target es6\n')
                    else:
                        self.assertEqual(command[command.index('--flatc-jar') + 1], policy['javaJar'])
                        log.write_text('java -jar ' + policy['javaJar'] + ' -target es6\n')
                    return
                calls.append((policy['stage'], command))
                log.write_text('fake runner; not compiler acceptance')
                install = 'install' in command
                if install:
                    if 'test' in command:
                        self.assertEqual(Path(command[1]), workspace / bootstrap.PRODUCTS[1])
                    if policy['stage'] > 0:
                        self.assertEqual(command[command.index('--flatc-script') + 1], policy['seed'])
                    if command[command.index('install') + 1] == 'flatc':
                        names = (bootstrap.PRODUCTS[0],)
                    elif command[command.index('install') + 1] == '-t':
                        names = (bootstrap.PRODUCTS[1],)
                    else:
                        names = ()
                    for name in names:
                        product = workspace / name
                        product.parent.mkdir(parents=True, exist_ok=True)
                        if product.is_symlink():
                            product.unlink()
                        product.write_text('deterministic test product')
                    if command[command.index('install') + 1] == 'test':
                        test = cwd / 'dist/es6/test.js'
                        test.parent.mkdir(parents=True, exist_ok=True)
                        test.write_text('test')
                    record = {'command': policy['java'] if policy['stage'] == 0 else policy['node'],
                              'args': ['-jar', policy['javaJar']] if policy['stage'] == 0 else [policy['compiler']],
                              'decision': 'allow'}
                else:
                    test = cwd / 'dist/es6/test.js'
                    self.assertTrue(test.is_file())
                    record = {'command': policy['node'], 'args': [str(test)], 'decision': 'allow'}
                with journal.open('a') as stream:
                    stream.write(json.dumps(record) + '\n')
                return 0

            with mock.patch.object(bootstrap, 'run_command', side_effect=fake_command), \
                    mock.patch.object(bootstrap, 'companion_contract'), \
                    mock.patch.object(bootstrap, 'lifecycle_inventory', return_value=[]), \
                    mock.patch.object(bootstrap, 'process_companion_gates', return_value={'mock': True}) as gates:
                previous = None
                for number in range(3):
                    previous = bootstrap.stage(number, repo, output, sources, seeds, previous,
                                               origin='git@github.com:FlatLang/Flat.git')
                    manifest = json.loads((output / f'stage{number}/manifest.json').read_text())
                    self.assertEqual(manifest['schema'], 2)
                    self.assertEqual(manifest['status'], 'passed')
                    self.assertEqual(manifest['runtime'], 'java' if number == 0 else 'flat')
                    if number == 0:
                        backend = output / 'stage0/workspace/packages/Flat-ES6/target/flat-es6.jar'
                        self.assertEqual(manifest['policy']['javaBackendJar'], str(backend.resolve(strict=True)))
                        self.assertEqual(manifest['policy']['javaBackendJarSha256'], seeds['java_backend'][1])
                        self.assertEqual(bootstrap.digest(backend), seeds['java_backend'][1])
                    else:
                        self.assertIsNone(manifest['policy']['javaBackendJar'])
                        self.assertIsNone(manifest['policy']['javaBackendJarSha256'])
                    self.assertEqual(manifest['admissions_sha256'], bootstrap.digest(output / f'stage{number}/admissions.jsonl'))
                    self.assertTrue(all(item['exit_code'] == 0 for item in manifest['commands']))
                    self.assertTrue(all(len(item['runner_sha256']) == 64 and len(item['compiler_sha256']) == 64
                                        for item in manifest['commands']))
                    self.assertEqual(manifest['source_origin'], 'git@github.com:FlatLang/Flat.git')
                    self.assertEqual(set(manifest['source_origin_metadata']), {'.git/HEAD', '.git/config'})
                    self.assertEqual(manifest['guard_sha256'], bootstrap.digest(
                        output / f'stage{number}/seeds/guard.cjs'))
                    self.assertEqual(manifest['policy_sha256'], bootstrap.digest(
                        output / f'stage{number}/policy.json'))
                    for name, expected in manifest['retained_files'].items():
                        self.assertEqual(expected, bootstrap.digest(output / f'stage{number}' / name))
                    self.assertEqual(set(manifest['tool_sha256']),
                                     set(seeds) if number == 0 else {'node', 'git'})
                    self.assertEqual(len(manifest['commands']), 16)
                    installed = [Path(item['argv'][item['argv'].index('-f') + 1]).name
                                 for item in manifest['commands']
                                 if 'install' in item['argv'] and 'test' in item['argv']]
                    self.assertEqual(installed, ['Airship', 'Flat-Project-Loading', 'Flat-Parser',
                                                'Flat-Name-Binding', 'Flat-Bound-IR',
                                                'Flat-ES6-Bound-Writer', 'Flat-Compiler'])
            self.assertEqual(gates.call_count, 3)
            self.assertEqual([number for number, _ in calls], [0] * 16 + [1] * 16 + [2] * 16)
            self.assertFalse((repo / bootstrap.PRODUCTS[0]).exists())
            # A zero-exit no-op producer cannot green the stage, even if supplied
            # with an old compiler seed at Airship's existence-check location.
            failed = root / 'failed'
            failed.mkdir()
            with mock.patch.object(bootstrap, 'run_command'), mock.patch.object(bootstrap, 'companion_contract'):
                with self.assertRaises(ValueError):
                    bootstrap.stage(1, repo, failed, sources, seeds, previous,
                                    origin='git@github.com:FlatLang/Flat.git')
            receipt = json.loads((failed / 'stage1/manifest.json').read_text())
            self.assertEqual(receipt['status'], 'failed')
            self.assertEqual(receipt['commands'][-1]['status'], 'failed')
            # Retain failed debug-command output, status and digest too.
            failed_logs = root / 'failed-logs'
            failed_logs.mkdir()

            def failed_command(command, cwd, env, log, journal, **kwargs):
                log.write_text('retained producer failure')
                raise bootstrap.CommandFailure(command, 23, log)

            with mock.patch.object(bootstrap, 'run_command', side_effect=failed_command), \
                    mock.patch.object(bootstrap, 'companion_contract'):
                with self.assertRaises(bootstrap.CommandFailure):
                    bootstrap.stage(1, repo, failed_logs, sources, seeds, previous,
                                    origin='git@github.com:FlatLang/Flat.git')
            receipt = json.loads((failed_logs / 'stage1/manifest.json').read_text())
            self.assertEqual(receipt['status'], 'failed')
            self.assertEqual(receipt['commands'][-1]['exit_code'], 23)
            log = failed_logs / 'stage1/logs/01-resolution.log'
            self.assertEqual(receipt['commands'][-1]['resolution_log_sha256'], bootstrap.digest(log))
            self.assertEqual(receipt['retained_files']['logs/01-resolution.log'], bootstrap.digest(log))

    def test_rejection_journal_overrides_zero_exit(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            journal = root / 'admissions.jsonl'
            journal.write_text(json.dumps({'decision': 'reject'}) + '\n')
            with self.assertRaisesRegex(ValueError, 'Rejected subprocess'):
                bootstrap.run_command([sys.executable, '-c', 'pass'], root, os.environ.copy(),
                                      root / 'log', journal)

    def test_compiler_facade_is_not_a_fresh_artifact(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            seed = root / 'seed.js'
            seed.write_text('seed')
            for name in bootstrap.PRODUCTS:
                product = root / name
                product.parent.mkdir(parents=True, exist_ok=True)
                product.symlink_to(seed)
            with self.assertRaisesRegex(ValueError, 'Missing fresh'):
                bootstrap.artifacts(root)

    def test_manifests_compare_hashes_not_stage_paths(self):
        bootstrap.compare_products({"x": "a"}, {"x": "a"})
        with self.assertRaises(ValueError):
            bootstrap.compare_products({"x": "a"}, {"x": "b"})


class GuardTests(unittest.TestCase):
    def setUp(self):
        self.temporary = tempfile.TemporaryDirectory()
        self.root = Path(self.temporary.name).resolve()
        self.node = Path(shutil.which("node")).resolve()
        self.seed = self.root / "seed.js"
        self.seed.write_text("console.log('immutable seed')")
        self.compiler = self.root / "work/packages/Flat-Compiler/dist/es6/index.js"
        self.compiler.parent.mkdir(parents=True)
        self.compiler.symlink_to(self.seed)
        self.journal = self.root / "admissions.jsonl"
        self.policy = self.root / "policy.json"
        self.policy.write_text(json.dumps({
            "stage": 1, "root": str(self.root / "work"), "node": str(self.node),
            "compiler": str(self.compiler), "seed": str(self.seed),
            "seedSha256": hashlib.sha256(self.seed.read_bytes()).hexdigest(),
            "journal": str(self.journal), "git": shutil.which("git"),
        }))
        self.env = {**os.environ, "FLAT_BOOTSTRAP_POLICY": str(self.policy),
                    "NODE_OPTIONS": "--require=" + str(HERE / "stage_subprocess_guard.cjs")}

    def tearDown(self):
        self.temporary.cleanup()

    def run_probe(self, expression):
        probe = self.root / "work/probe.js"
        probe.parent.mkdir(exist_ok=True)
        probe.write_text(expression)
        return subprocess.run([str(self.node), str(probe)], env=self.env,
                              capture_output=True, text=True, timeout=10)

    def test_stage0_revalidates_copied_backend_before_every_java_admission(self):
        # Stub only the OS launch boundary, before loading the real guard. No JVM
        # is launched; both admissions still exercise its full stage0 policy.
        for mutation in ('unchanged', 'bytes', 'missing', 'symlink', 'missing-policy'):
            with self.subTest(mutation=mutation):
                self.journal.unlink(missing_ok=True)
                stage = self.root / 'work'
                backend = stage / 'packages/Flat-ES6/target/flat-es6.jar'
                backend.parent.mkdir(parents=True, exist_ok=True)
                backend.unlink(missing_ok=True)
                bootstrap.copy_seed(self.seed, backend, bootstrap.digest(self.seed))
                jar = stage / 'flatc.jar'
                jar.write_text('reviewed compiler fixture')
                java = self.root / 'java'
                java.write_text('reviewed executable fixture; never executed')
                policy = {
                    'stage': 0, 'root': str(stage), 'java': str(java),
                    'javaSha256': bootstrap.digest(java),
                    'javaJar': str(jar), 'javaJarSha256': bootstrap.digest(jar),
                    'javaBackendJar': str(backend), 'javaBackendJarSha256': bootstrap.digest(backend),
                    'journal': str(self.journal),
                }
                self.policy.write_text(json.dumps(policy))
                if mutation == 'bytes':
                    change = "fs.chmodSync(backend, 0o644); fs.writeFileSync(backend, 'mutated after copy');"
                elif mutation == 'missing':
                    change = 'fs.unlinkSync(backend);'
                elif mutation == 'symlink':
                    change = 'fs.unlinkSync(backend); fs.symlinkSync(' + json.dumps(str(self.seed)) + ', backend);'
                else:
                    change = ''
                if mutation == 'missing-policy':
                    del policy['javaBackendJar']
                    self.policy.write_text(json.dumps(policy))
                expression = (
                    "const fs = require('node:fs'), cp = require('node:child_process');"
                    "let launches = 0; cp.spawnSync = () => { launches++; return {status:0}; };"
                    'require(' + json.dumps(str(HERE / 'stage_subprocess_guard.cjs')) + ');'
                    'const backend = ' + json.dumps(str(backend)) + ';'
                    'const args = ["-jar", ' + json.dumps(str(jar)) + '];'
                    "try { cp.spawnSync('java', args); } catch (_) {}" + change +
                    "try { cp.spawnSync('java', args); } catch (_) {}"
                    'console.log(JSON.stringify({launches}));')
                result = subprocess.run([str(self.node), '-e', expression],
                                        env={**self.env, 'NODE_OPTIONS': ''},
                                        capture_output=True, text=True, timeout=10)
                self.assertEqual(result.returncode, 0, result.stderr)
                expected = 2 if mutation == 'unchanged' else 0 if mutation == 'missing-policy' else 1
                self.assertEqual(json.loads(result.stdout)['launches'], expected)
                records = list(bootstrap.admission_records(self.journal))
                self.assertEqual(records[-1]['decision'],
                                 'allow' if mutation == 'unchanged' else 'reject')
                self.assertEqual(sum(record['decision'] == 'allow' for record in records), expected)

    def test_detached_children_and_identity_overrides_are_rejected(self):
        for options in ({'detached': True}, {'argv0': 'java'}, {'uid': 0}, {'gid': 0}):
            with self.subTest(options=options):
                result = self.run_probe(
                    "try { require('child_process').spawn('node', [" +
                    json.dumps(str(self.compiler)) + '], ' + json.dumps(options) +
                    "); } catch (_) {}")
                self.assertEqual(result.returncode, 0, result.stderr)
                records = list(bootstrap.admission_records(self.journal))
                self.assertEqual(records[-1]['decision'], 'reject')
                self.assertFalse(any(record['decision'] == 'allow' for record in records))

    def test_symlinked_cwd_outside_stage_is_rejected(self):
        link = self.root / 'work/escaped-cwd'
        link.symlink_to(self.root, target_is_directory=True)
        result = self.run_probe(
            "try { require('child_process').spawnSync('node', [" +
            json.dumps(str(self.compiler)) + '], {cwd:' + json.dumps(str(link)) +
            "}); } catch (_) {}")
        self.assertEqual(result.returncode, 0, result.stderr)
        records = list(bootstrap.admission_records(self.journal))
        self.assertEqual(records[-1]['decision'], 'reject')
        self.assertIn('cwd outside stage', records[-1]['reason'])

    def test_child_environment_cannot_inject_tool_configuration(self):
        child = self.root / 'work/environment.js'
        child.write_text("console.log(JSON.stringify(process.env))")
        options = {'env': {'NODE_PATH': '/unreviewed', 'JAVA_TOOL_OPTIONS': '-agentlib:bad',
                           'DYLD_INSERT_LIBRARIES': '/unreviewed', 'GIT_CONFIG_COUNT': '1',
                           'GIT_CONFIG_KEY_0': 'remote.origin.url', 'GIT_CONFIG_VALUE_0': 'bad',
                           'FLAT_TEST_VALUE': 'preserved'}}
        result = self.run_probe("require('child_process').spawnSync('node', [" +
                                json.dumps(str(child)) + '], {...' + json.dumps(options) +
                                ", stdio:'inherit'});")
        self.assertEqual(result.returncode, 0, result.stderr)
        env = json.loads(result.stdout)
        for name in ('NODE_PATH', 'JAVA_TOOL_OPTIONS', 'DYLD_INSERT_LIBRARIES',
                     'GIT_CONFIG_COUNT', 'GIT_CONFIG_KEY_0', 'GIT_CONFIG_VALUE_0'):
            self.assertNotIn(name, env)
        self.assertEqual(env['GIT_CONFIG_NOSYSTEM'], '1')
        self.assertEqual(env['FLAT_TEST_VALUE'], 'preserved')
        self.assertIn('--require=', env['NODE_OPTIONS'])

    def test_compiler_is_mapped_to_verified_seed(self):
        result = self.run_probe("require('child_process').spawnSync('node', [" +
                                json.dumps(str(self.compiler)) + "], {stdio:'inherit'});")
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertIn("immutable seed", result.stdout)

    def test_explicit_compiler_script_outside_workspace_is_verified(self):
        policy = json.loads(self.policy.read_text())
        policy['compiler'] = str(self.seed)
        self.policy.write_text(json.dumps(policy))
        result = self.run_probe("require('child_process').spawnSync('node', [" +
                                json.dumps(str(self.seed)) + "], {stdio:'inherit'});")
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertIn('immutable seed', result.stdout)
        records = [json.loads(line) for line in self.journal.read_text().splitlines()]
        self.assertEqual(records[-1]['args'][0], str(self.seed))
        self.assertEqual(records[-1]['decision'], 'allow')

    def test_generated_compiler_output_is_not_an_admitted_seed(self):
        output = self.root / 'work/generated-flatc.js'
        output.write_text("console.log('must not run compiler output')")
        policy = json.loads(self.policy.read_text())
        policy['compiler'] = str(self.seed)
        policy['compilerOutput'] = str(output)
        self.policy.write_text(json.dumps(policy))
        result = self.run_probe("try {require('child_process').spawnSync('node', [" +
                                json.dumps(str(output)) + "], {stdio:'inherit'});} catch(e) {}")
        self.assertNotIn('must not run compiler output', result.stdout)
        self.assertIn('not an admitted compiler input', self.journal.read_text())

    def test_explicit_script_digest_mismatch_is_rejected(self):
        policy = json.loads(self.policy.read_text())
        policy['compiler'] = str(self.seed)
        self.policy.write_text(json.dumps(policy))
        self.seed.write_text("console.log('replaced seed')")
        result = self.run_probe("try { require('child_process').spawnSync('node', [" +
                                json.dumps(str(self.seed)) + "]); } catch(e) {}")
        self.assertNotIn('replaced seed', result.stdout)
        self.assertIn('seed/tool digest mismatch', self.journal.read_text())

    def test_guard_records_exact_real_git_origin_lookup(self):
        origin = 'git@github.com:FlatLang/Flat.git'
        bootstrap.install_snapshot_origin(self.root / 'work', origin)
        result = self.run_probe("const r=require('child_process').spawnSync('git', "
                                "['config','--get','remote.origin.url'], {cwd:" +
                                json.dumps(str(self.root / 'work')) +
                                ",encoding:'utf8'}); process.stdout.write(r.stdout); process.exit(r.status);")
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(result.stdout, origin + '\n')
        records = [json.loads(line) for line in self.journal.read_text().splitlines()]
        self.assertEqual(records[0]['decision'], 'allow')
        self.assertEqual(records[0]['args'], ['config', '--get', 'remote.origin.url'])

    def test_entrypoint_runtime_shapes_are_explicit_and_confined(self):
        package = self.root / 'work/packages/Flat-ES6-Bound-Writer'
        runner = package / 'dist/es6/test.js'
        runner.parent.mkdir(parents=True)
        imported = package / '.flat-es6-entrypoint-123/main.js'
        imported.parent.mkdir(); imported.write_text("if(require.main===module) process.exit(9);")
        failed = package / '.flat-es6-entrypoint-failure-123/main.js'
        failed.parent.mkdir(); failed.write_text("console.error('entrypoint failed'); process.exit(1);")
        def probe(args):
            runner.write_text("const r=require('child_process').spawnSync('node', " + json.dumps(args) +
                              ", {env:{}, encoding:'utf8'}); console.log(r.status); ")
            return subprocess.run([str(self.node), str(runner)], cwd=package, env=self.env,
                                  capture_output=True, text=True, timeout=10)
        for args, status in [(['-e', "require('./.flat-es6-entrypoint-123/main.js')"], '0'),
                             (['--unhandled-rejections=strict', '.flat-es6-entrypoint-failure-123/main.js'], '1')]:
            result = probe(args)
            self.assertEqual(result.returncode, 0, result.stderr)
            self.assertEqual(result.stdout.strip(), status)
            self.assertFalse(any(json.loads(line)['decision'] == 'reject'
                                 for line in self.journal.read_text().splitlines()))
        failed.write_text('process.exit(0)')
        self.journal.unlink()
        result = probe(['--unhandled-rejections=strict', '.flat-es6-entrypoint-failure-123/main.js'])
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertTrue(any(json.loads(line)['decision'] == 'reject'
                            for line in self.journal.read_text().splitlines()))
        for args in (['-e', "require('./.flat-es6-entrypoint-123/main.js');process.exit(0)"],
                     ['-e', "require('/tmp/other.js')"],
                     ['--unhandled-rejections=strict', '.flat-es6-entrypoint-123/main.js'],
                     ['--no-warnings', '.flat-es6-entrypoint-123/main.js']):
            result = probe(args)
            self.assertNotEqual(result.returncode, 0)
        # Symlink substitution is forbidden even if the lexical fixture name fits.
        imported.unlink(); imported.symlink_to(self.seed)
        self.assertNotEqual(probe(['-e', "require('./.flat-es6-entrypoint-123/main.js')"]).returncode, 0)

    def test_entrypoint_probe_keeps_recursive_guard(self):
        package = self.root / 'work/packages/Flat-ES6-Bound-Writer'
        runner = package / 'dist/es6/test.js'; runner.parent.mkdir(parents=True)
        fixture = package / '.flat-es6-entrypoint-456/main.js'; fixture.parent.mkdir()
        fixture.write_text("require('child_process').spawnSync('java',['-version']);")
        runner.write_text("try { require('child_process').spawnSync('node', " +
                          json.dumps(['-e', "require('./.flat-es6-entrypoint-456/main.js')"]) +
                          ", {env:{}}); } catch(e) {}")
        subprocess.run([str(self.node), str(runner)], cwd=package, env=self.env,
                       capture_output=True, timeout=10, check=True)
        self.assertIn('reject', self.journal.read_text())

    def test_debug_only_policy_rejects_compiler_execution(self):
        policy = json.loads(self.policy.read_text())
        policy['debugOnly'] = True
        self.policy.write_text(json.dumps(policy))
        result = self.run_probe("require('child_process').spawnSync('node', [" +
                                json.dumps(str(self.compiler)) + "], {stdio:'inherit'});")
        self.assertNotEqual(result.returncode, 0)
        self.assertNotIn('immutable seed', result.stdout)
        self.assertIn('debug-only', self.journal.read_text())

    def test_unmapped_compiler_is_rejected_not_substituted(self):
        self.compiler.unlink()
        self.compiler.write_text("console.log('mutable compiler')")
        result = self.run_probe("require('child_process').spawnSync('node', [" +
                                json.dumps(str(self.compiler)) + "], {stdio:'inherit'});")
        self.assertNotEqual(result.returncode, 0)
        self.assertNotIn('mutable compiler', result.stdout)
        self.assertIn('not the immutable seed', self.journal.read_text())

    def test_mutated_seed_rejected(self):
        self.seed.write_text("console.log('tampered')")
        result = self.run_probe("require('child_process').spawnSync('node', [" +
                                json.dumps(str(self.compiler)) + "]);")
        self.assertNotEqual(result.returncode, 0)

    def test_forbidden_subprocesses_even_if_caught_are_journaled(self):
        for expression in [
            "cp.spawnSync('/usr/bin/java', ['-version'])",
            "cp.spawnSync('git', ['clone', 'https://example.invalid/a'])",
            "cp.spawnSync('sh', ['-c', 'true'])",
            "cp.execSync('true')",
            "cp.spawnSync('node', ['-e', 'process.exit(0)'])",
            "cp.spawnSync('node', ['missing.js'], {shell:true})",
            "cp.fork('missing.js')",
        ]:
            with self.subTest(expression=expression):
                result = self.run_probe("const cp=require('child_process'); try {" + expression + "} catch(e) {}")
                self.assertEqual(result.returncode, 0, result.stderr)
                records = [json.loads(line) for line in self.journal.read_text().splitlines()]
                self.assertTrue(any(record["decision"] == "reject" for record in records))
                self.journal.unlink()

    def test_ignored_child_failure_is_still_rejected(self):
        child = self.root / 'work/failure.js'
        child.write_text('process.exit(7)')
        for method in ('spawnSync', 'execFileSync'):
            with self.subTest(method=method):
                result = self.run_probe("try {require('child_process')." + method + "('node', [" +
                                        json.dumps(str(child)) + "]);} catch(e) {}")
                self.assertEqual(result.returncode, 0, result.stderr)
                self.assertIn('reject', self.journal.read_text())
                self.journal.unlink()

    def test_network_and_worker_bypasses_are_rejected(self):
        for expression in [
            "require('net').connect(1, '127.0.0.1')",
            "require('https').get('https://example.invalid')",
            "fetch('https://example.invalid')",
            "new (require('worker_threads').Worker)('process.exit(0)', {eval:true})",
        ]:
            with self.subTest(expression=expression):
                result = self.run_probe("try {" + expression + "} catch(e) {}")
                self.assertEqual(result.returncode, 0, result.stderr)
                self.assertIn('reject', self.journal.read_text())
                self.journal.unlink()

    def test_child_cannot_drop_guard_with_custom_environment(self):
        child = self.root / "work/child.js"
        child.write_text("require('child_process').spawnSync('java', ['-version']);")
        result = self.run_probe("const r=require('child_process').spawnSync('node', [" +
                                json.dumps(str(child)) + "], {env:{}, encoding:'utf8'}); process.exit(r.status);")
        self.assertNotEqual(result.returncode, 0)
        self.assertIn('reject', self.journal.read_text())


if __name__ == "__main__":
    unittest.main()

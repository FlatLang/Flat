"""Exact public CLI negative inventory; isolated guard processes, no Flat compile."""
import json
from pathlib import Path
import shutil
import tempfile
import unittest
import stage_bootstrap as bootstrap
import dogfood_acceptance as gate


class PublicCliAdmissionTests(unittest.TestCase):
    def test_all_configured_cli_operations_have_exact_outcomes(self):
        for index in range(4):
            with self.subTest(index=index), tempfile.TemporaryDirectory() as temporary:
                root = Path(temporary).resolve()
                owner = root / 'packages/Airship'
                fixture_root = owner / '.airship-toolchain-test-123'
                (fixture_root / 'src').mkdir(parents=True)
                (fixture_root / 'src/Main.flat').write_text('not valid Flat syntax')
                source = root / 'packages/Airship/src/flat/airship/AirshipToolchain_Test.flat'
                source.parent.mkdir(parents=True)
                shutil.copyfile(Path(__file__).parent.parent / 'src/flat/airship/AirshipToolchain_Test.flat', source)
                selected, compiler, runner = owner / 'selected.js', root / 'compiler.js', owner / 'suite.js'
                selected.write_text('process.exit(1);')
                compiler.write_text('compiler input')
                runner.write_text('parent')
                entry = gate.toolchain_cli_inventory(root, runner, selected, compiler)[index]
                script = fixture_root / 'absent-compiler.js' if index == 3 else compiler
                args = [str(selected), '--use-links', '-f', str(fixture_root), '--flatc-runtime', 'flat',
                        '--flatc-script', str(script), '-t', 'es6', '-r', 'node'] + entry['operation']
                runner.write_text("require('child_process').spawnSync('node'," + json.dumps(args) + ');')
                entry['runnerSha256'] = bootstrap.digest(runner)
                node = Path(shutil.which('node')).resolve()
                policy = root / 'policy.json'
                policy.write_text(json.dumps({'stage': 1, 'root': str(root), 'node': str(node),
                    'nodeSha256': bootstrap.digest(node), 'journal': str(root / 'journal'), 'fixtures': [entry]}))
                env = {'PATH': str(node.parent), 'FLAT_BOOTSTRAP_POLICY': str(policy),
                       'NODE_OPTIONS': '--require=' + str(Path(__file__).parent / 'stage_subprocess_guard.cjs')}
                self.assertEqual(bootstrap.run_command([str(node), str(runner)], owner, env,
                                  root / 'run.log', root / 'journal', timeout=10), 0)

    def test_invalid_compile_replays_only_previously_admitted_argv(self):
        for variant in ('exact', 'argv', 'state', 'zero', 'no-prior'):
            with self.subTest(variant=variant), tempfile.TemporaryDirectory() as temporary:
                root = Path(temporary).resolve()
                owner = root / 'packages/Airship'
                fixture_root = owner / '.airship-toolchain-test-123'
                (fixture_root / 'src').mkdir(parents=True)
                (fixture_root / 'src/Main.flat').write_text('valid source' if variant == 'state' else 'not valid Flat syntax')
                source = root / 'packages/Airship/src/flat/airship/AirshipToolchain_Test.flat'
                source.parent.mkdir(parents=True)
                shutil.copyfile(Path(__file__).parent.parent / 'src/flat/airship/AirshipToolchain_Test.flat', source)
                compiler, selected, runner = root / 'compiler.js', owner / 'selected.js', owner / 'suite.js'
                compiler.write_text('process.exit(' + ('0' if variant == 'zero' else '1') + ');')
                selected.write_text('selected tool')
                runner.write_text('parent')
                entry = gate.toolchain_compile_inventory(root, runner, selected, compiler)[1]
                args = [str(compiler), str(fixture_root / 'src'), '-target', 'es6', '-main', 'app/Main']
                node = Path(shutil.which('node')).resolve()
                journal = root / 'journal'
                journal.write_text('' if variant == 'no-prior' else json.dumps(
                    {'decision': 'allow', 'command': str(node), 'args': args}) + '\n')
                if variant == 'argv':
                    args += ['--extra']
                runner.write_text("require('child_process').spawnSync('node'," + json.dumps(args) + ');')
                entry['runnerSha256'] = bootstrap.digest(runner)
                policy = root / 'policy.json'
                policy.write_text(json.dumps({'stage': 1, 'root': str(root), 'node': str(node),
                    'nodeSha256': bootstrap.digest(node), 'journal': str(journal), 'fixtures': [entry]}))
                env = {'PATH': str(node.parent), 'FLAT_BOOTSTRAP_POLICY': str(policy),
                       'NODE_OPTIONS': '--require=' + str(Path(__file__).parent / 'stage_subprocess_guard.cjs')}
                invoke = lambda: bootstrap.run_command([str(node), str(runner)], owner, env,
                                                       root / 'run.log', journal, timeout=10)
                if variant == 'exact':
                    self.assertEqual(invoke(), 0)
                else:
                    with self.assertRaises((ValueError, bootstrap.CommandFailure)):
                        invoke()

    def test_exact_public_negative_admission_and_rejections(self):
        for variant in ('exact', 'argv', 'tool', 'source', 'zero', 'missing'):
            with self.subTest(variant=variant), tempfile.TemporaryDirectory() as temporary:
                root = Path(temporary).resolve()
                owner = root / 'packages/Airship'
                fixture_root = owner / '.airship-toolchain-test-123'
                fixture_root.mkdir(parents=True)
                (fixture_root / 'src').mkdir()
                (fixture_root / 'src/Main.flat').write_text('not valid Flat syntax')
                source = root / 'packages/Airship/src/flat/airship/AirshipToolchain_Test.flat'
                source.parent.mkdir(parents=True)
                shutil.copyfile(Path(__file__).parent.parent / 'src/flat/airship/AirshipToolchain_Test.flat', source)
                selected = owner / 'dist/es6/selected-airship.js'
                selected.parent.mkdir(parents=True)
                selected.write_text('process.exit(' + ('0' if variant == 'zero' else '1') + ');')
                compiler = root / 'compiler.js'
                compiler.write_text('compiler input')
                runner = owner / 'suite.js'
                runner.write_text('parent fixture')
                entries = gate.toolchain_cli_inventory(root, runner, selected, compiler)
                args = [str(selected), '--use-links', '-f', str(fixture_root), '--flatc-runtime', 'flat',
                        '--flatc-script', str(compiler), '-t', 'es6', '-r', 'node', 'run', 'fail']
                if variant == 'argv':
                    args.append('--unreviewed')
                if variant == 'tool':
                    selected.write_text('process.exit(2);')
                if variant == 'source':
                    source.write_text('changed source')
                runner.write_text("require('child_process').spawnSync('node'," + json.dumps(args) + ',{stdio:"inherit"});')
                # Bind final parent executable, not an incidental pre-edit hash.
                for entry in entries:
                    entry['runnerSha256'] = bootstrap.digest(runner)
                node = Path(shutil.which('node')).resolve()
                policy = root / 'policy.json'
                policy.write_text(json.dumps({'stage': 1, 'root': str(root), 'node': str(node),
                    'nodeSha256': bootstrap.digest(node), 'journal': str(root / 'journal'),
                    'fixtures': [] if variant == 'missing' else [entries[0]]}))
                env = {'PATH': str(node.parent), 'FLAT_BOOTSTRAP_POLICY': str(policy),
                       'NODE_OPTIONS': '--require=' + str(Path(__file__).parent / 'stage_subprocess_guard.cjs')}
                invoke = lambda: bootstrap.run_command([str(node), str(runner)], owner, env,
                                                       root / 'run.log', root / 'journal', timeout=10)
                if variant == 'exact':
                    self.assertEqual(invoke(), 0)
                else:
                    with self.assertRaises((ValueError, bootstrap.CommandFailure)):
                        invoke()

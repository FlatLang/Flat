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


class BootstrapTests(unittest.TestCase):
    def test_seed_digest_is_required_and_verified(self):
        with tempfile.TemporaryDirectory() as directory:
            path = Path(directory) / "seed"
            path.write_text("seed")
            with self.assertRaises(ValueError):
                bootstrap.verified_seed({"path": str(path), "sha256": "0" * 64})
            digest = hashlib.sha256(b"seed").hexdigest()
            self.assertEqual(bootstrap.verified_seed({"path": str(path), "sha256": digest}), path.resolve())

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

    def test_filesystem_seed_mapping_restores_built_tool_without_argv_substitution(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory).resolve()
            seed = root / 'seed.js'
            seed.write_text('immutable compiler')
            lookup = root / 'packages/Flat-Compiler/dist/es6/index.js'
            lookup.parent.mkdir(parents=True)
            lookup.write_text('newly built compiler')
            backup = root / 'backup.js'
            with bootstrap.compiler_mapping(lookup, seed, backup):
                self.assertTrue(lookup.is_symlink())
                self.assertEqual(lookup.resolve(), seed)
                self.assertEqual(backup.read_text(), 'newly built compiler')
            self.assertFalse(lookup.is_symlink())
            self.assertEqual(lookup.read_text(), 'newly built compiler')
            self.assertFalse(backup.exists())
            with self.assertRaisesRegex(RuntimeError, 'build failed'):
                with bootstrap.compiler_mapping(lookup, seed, backup):
                    raise RuntimeError('build failed')
            self.assertEqual(lookup.read_text(), 'newly built compiler')

    def test_stage_chain_isolated_explicit_and_missing_gate_fails(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory).resolve()
            repo = root / "repo"
            for name in ("Flat-Compiler", "Airship"):
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

            def fake_command(command, cwd, env, log, journal):
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
                    self.assertTrue(Path(policy['compiler']).is_symlink())
                    self.assertEqual(Path(policy['compiler']).resolve(), Path(policy['seed']))
                    log.write_text('node ' + policy['compiler'] + ' -target es6\n')
                    return
                calls.append((policy['stage'], command))
                log.write_text('fake runner; not compiler acceptance')
                install = 'install' in command
                if install:
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

            with mock.patch.object(bootstrap, 'run_command', side_effect=fake_command):
                previous = None
                for number in range(3):
                    previous = bootstrap.stage(number, repo, output, sources, seeds, previous)
                    manifest = json.loads((output / f'stage{number}/manifest.json').read_text())
                    self.assertEqual(manifest['status'], 'passed')
                    self.assertEqual(len(manifest['commands']), 6)
            self.assertEqual([number for number, _ in calls], [0] * 6 + [1] * 6 + [2] * 6)
            self.assertFalse((repo / bootstrap.PRODUCTS[0]).exists())
            # A zero-exit no-op producer cannot green the stage, even if supplied
            # with an old compiler seed at Airship's existence-check location.
            failed = root / 'failed'
            failed.mkdir()
            with mock.patch.object(bootstrap, 'run_command'):
                with self.assertRaises(ValueError):
                    bootstrap.stage(1, repo, failed, sources, seeds, previous)
            receipt = json.loads((failed / 'stage1/manifest.json').read_text())
            self.assertEqual(receipt['status'], 'failed')

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

    def test_compiler_is_mapped_to_verified_seed(self):
        result = self.run_probe("require('child_process').spawnSync('node', [" +
                                json.dumps(str(self.compiler)) + "], {stdio:'inherit'});")
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertIn("immutable seed", result.stdout)

    def test_unmapped_compiler_is_rejected_not_substituted(self):
        self.compiler.unlink()
        self.compiler.write_text("console.log('mutable compiler')")
        result = self.run_probe("require('child_process').spawnSync('node', [" +
                                json.dumps(str(self.compiler)) + "], {stdio:'inherit'});")
        self.assertNotEqual(result.returncode, 0)
        self.assertNotIn('mutable compiler', result.stdout)
        self.assertIn('not mapped', self.journal.read_text())

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

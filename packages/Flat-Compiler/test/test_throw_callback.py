from pathlib import Path
import json
import subprocess
import tempfile
import unittest

PACKAGE = Path(__file__).resolve().parents[1]
SOURCE = PACKAGE / 'test-fixtures/throw-callback/Main.flat'


class ThrowCallbackTest(unittest.TestCase):
    def test_transfer_and_missing_operand(self):
        for valid in [True, False]:
            with self.subTest(valid=valid), tempfile.TemporaryDirectory() as tmp:
                src = Path(tmp) / 'Main.flat'
                text = SOURCE.read_text()
                src.write_text(text if valid else text.replace('throw marker', 'throw'))
                js = Path(tmp) / 'program.js'
                compile = subprocess.run(['node', PACKAGE / 'dist/es6/index.js', src,
                    '-main', 'probe/Main', '-target', 'es6', '-o', js], cwd=tmp,
                    capture_output=True, text=True, timeout=45)
                if valid:
                    self.assertEqual(compile.returncode, 0, compile.stderr)
                    run = subprocess.run(['node', js], capture_output=True, text=True, timeout=15)
                    self.assertEqual((run.returncode, run.stdout, run.stderr), (0, 'caught:original\n', ''))
                else:
                    self.assertNotEqual(compile.returncode, 0)
                    self.assertFalse(js.exists())
                    reports = [json.loads(line) for line in (compile.stdout + compile.stderr).splitlines() if line.startswith('{')]
                    record, = next(r['records'] for r in reports if r.get('schema') == 'flat.compiler.diagnostics.v1')
                    self.assertEqual(record['code'], 'invalid-statement-syntax')
                    self.assertIn('Throw requires a value', record['message'])
                    self.assertEqual((record['range']['startLine'], record['range']['startColumn'], record['range']['endColumn']), (7, 24, 29))

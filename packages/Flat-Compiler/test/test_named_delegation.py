from pathlib import Path
import subprocess
import tempfile
import unittest

PACKAGE = Path(__file__).resolve().parents[1]


class NamedDelegationTest(unittest.TestCase):
    def test_evaluation_order_and_skipped_default(self):
        with tempfile.TemporaryDirectory() as tmp:
            out = Path(tmp) / 'main.js'
            subprocess.run(['node', PACKAGE / 'dist/es6/index.js',
                            PACKAGE / 'test-fixtures/delegation/Main.flat', '-target', 'es6',
                            '-main', 'probe/Main', '-o', out], cwd=tmp, check=True)
            self.assertEqual(subprocess.check_output(['node', out], text=True), '3\n1\n1 5 3\n')

    def test_rejects_invalid_named_targets(self):
        base = (PACKAGE / 'test-fixtures/delegation/Main.flat').read_text()
        for args in ['unknown: 1', 'last: 2', '1, first: 2', 'first: true']:
            with self.subTest(args=args), tempfile.TemporaryDirectory() as tmp:
                src = Path(tmp) / 'Main.flat'
                src.write_text(base.replace('last: Main.mark(3), first: Main.mark(1)', args))
                out = Path(tmp) / 'main.js'
                result = subprocess.run(['node', PACKAGE / 'dist/es6/index.js', src, '-target', 'es6',
                                         '-main', 'probe/Main', '-o', out], cwd=tmp, capture_output=True, text=True)
                self.assertNotEqual(result.returncode, 0)
                self.assertIn("No overload of 'Main'", result.stderr + result.stdout)
                self.assertFalse(out.exists())

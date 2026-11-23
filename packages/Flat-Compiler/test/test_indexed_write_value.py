"""Compile indexed writes with the public Flat compiler; never reuse emitted JS."""
from pathlib import Path
import subprocess
import tempfile
import unittest

PACKAGE = Path(__file__).resolve().parents[1]
FIXTURE = PACKAGE / 'test-fixtures/indexed-write-value/Main.flat'


class IndexedWriteTests(unittest.TestCase):
    def compile(self, fixture, output):
        return subprocess.run(['node', PACKAGE / 'dist/es6/index.js', fixture, '-target', 'es6',
                               '-main', 'probe/Main', '-o', output], cwd=output.parent,
                              timeout=30, capture_output=True, text=True)

    def test_evaluates_operands_once_and_returns_the_converted_rhs(self):
        with tempfile.TemporaryDirectory() as tmp:
            output = Path(tmp) / 'main.js'
            result = self.compile(FIXTURE, output)
            self.assertEqual(result.returncode, 0, result.stderr)
            run = subprocess.run(['node', output], cwd=tmp, check=True, timeout=10,
                                 capture_output=True, text=True)
            self.assertEqual(run.stdout.strip(), 'RIV 65 67 0 1')

    def test_invalid_setters_emit_no_artifact(self):
        cases = [
            ('set { writes = writes + 1; stored = value + key; }', '', 'does not declare a setter'),
            ('owner()[key()] = rhs()', 'owner()[true] = rhs()', 'Indexed assignment cannot convert'),
            ('owner()[key()] = rhs()', 'owner()[key()] = true', 'Indexed assignment cannot convert'),
        ]
        for old, new, diagnostic in cases:
            with self.subTest(new=new), tempfile.TemporaryDirectory() as tmp:
                source = FIXTURE.read_text()
                self.assertEqual(source.count(old), 1)
                fixture, output = Path(tmp) / 'Main.flat', Path(tmp) / 'main.js'
                fixture.write_text(source.replace(old, new))
                result = self.compile(fixture, output)
                self.assertNotEqual(result.returncode, 0)
                self.assertIn(diagnostic, result.stdout + result.stderr)
                self.assertFalse(output.exists())


if __name__ == '__main__':
    unittest.main()

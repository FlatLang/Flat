"""Check nested empty-array construction through the ordinary compiler and Node."""
from pathlib import Path
import subprocess
import tempfile
import unittest

PACKAGE = Path(__file__).resolve().parents[1]


class NestedEmptyArrayTests(unittest.TestCase):
    def test_empty_outer_and_independent_rows(self):
        fixture = PACKAGE / 'test-fixtures/nested-empty-array'
        with tempfile.TemporaryDirectory() as tmp:
            output = Path(tmp) / 'main.js'
            subprocess.run(['node', PACKAGE / 'dist/es6/index.js', fixture / 'Array.flat',
                            fixture / 'Main.flat', '-target', 'es6', '-main', 'probe/Main',
                            '-o', output], cwd=tmp, check=True, timeout=30)
            result = subprocess.run(['node', output], cwd=tmp, check=True, timeout=10,
                                    capture_output=True, text=True)
            self.assertEqual(result.stdout, '0 2 1 0\n')


if __name__ == '__main__':
    unittest.main()

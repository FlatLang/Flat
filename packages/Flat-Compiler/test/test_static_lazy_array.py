"""Run the static lazy array fixture through the built public compiler."""
from pathlib import Path
import subprocess
import tempfile
import unittest

PACKAGE = Path(__file__).resolve().parents[1]


class StaticLazyArrayTests(unittest.TestCase):
    def test_delayed_single_evaluation_and_array_identity(self):
        with tempfile.TemporaryDirectory() as tmp:
            output = Path(tmp) / 'main.js'
            subprocess.run(['node', PACKAGE / 'dist/es6/index.js',
                            PACKAGE / 'test-fixtures/static-lazy-array/Main.flat', '-target', 'es6',
                            '-main', 'probe/Main', '-o', output], cwd=tmp, check=True, timeout=30,
                           stdout=subprocess.PIPE)
            result = subprocess.run(['node', output], cwd=tmp, check=True, timeout=10,
                                    capture_output=True, text=True)
            self.assertEqual(result.stdout.strip(), 'once:same:1,2')


if __name__ == '__main__':
    unittest.main()

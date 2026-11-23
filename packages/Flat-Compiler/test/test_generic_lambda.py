"""Run the generic lambda parameter fixture through the built public compiler."""
from pathlib import Path
import subprocess
import tempfile
import unittest

PACKAGE = Path(__file__).resolve().parents[1]


class GenericLambdaTests(unittest.TestCase):
    def test_generic_parameter_preserves_argument_identity(self):
        with tempfile.TemporaryDirectory() as tmp:
            output = Path(tmp) / 'main.js'
            subprocess.run(['node', PACKAGE / 'dist/es6/index.js',
                            PACKAGE / 'test-fixtures/generic-lambda/Main.flat', '-target', 'es6',
                            '-main', 'probe/Main', '-o', output], cwd=tmp, check=True, timeout=30,
                           stdout=subprocess.PIPE)
            result = subprocess.run(['node', output], cwd=tmp, check=True, timeout=10,
                                    capture_output=True, text=True)
            self.assertEqual(result.stdout.strip(), 'same-pair')


if __name__ == '__main__':
    unittest.main()

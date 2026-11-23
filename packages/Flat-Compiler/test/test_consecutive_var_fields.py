"""Run the consecutive mutable field fixture through the built public compiler."""
from pathlib import Path
import subprocess
import tempfile
import unittest

PACKAGE = Path(__file__).resolve().parents[1]


class ConsecutiveVarFieldsTests(unittest.TestCase):
    def test_independent_initializers_and_per_instance_mutation(self):
        with tempfile.TemporaryDirectory() as tmp:
            output = Path(tmp) / 'main.js'
            subprocess.run(['node', PACKAGE / 'dist/es6/index.js',
                            PACKAGE / 'test-fixtures/consecutive-var-fields/Main.flat', '-target', 'es6',
                            '-main', 'probe/Main', '-o', output], cwd=tmp, check=True, timeout=30,
                           stdout=subprocess.PIPE)
            result = subprocess.run(['node', output], cwd=tmp, check=True, timeout=10,
                                    capture_output=True, text=True)
            self.assertEqual(result.stdout.strip(), '1,2,1')


if __name__ == '__main__':
    unittest.main()

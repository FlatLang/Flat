"""Check applied constructor parameter types with the built public compiler."""
from pathlib import Path
import subprocess
import tempfile
import unittest

PACKAGE = Path(__file__).resolve().parents[1]
FIXTURE = PACKAGE / 'test-fixtures/constructor-type-order/Main.flat'


class ConstructorTypeOrderTests(unittest.TestCase):
    def compile_fixture(self, source, output):
        return subprocess.run(['node', PACKAGE / 'dist/es6/index.js', source, '-target', 'es6',
                               '-main', 'probe/Main', '-o', output], cwd=output.parent,
                              timeout=30, capture_output=True, text=True)

    def test_substitutes_both_arguments_in_order(self):
        with tempfile.TemporaryDirectory() as tmp:
            output = Path(tmp) / 'main.js'
            result = self.compile_fixture(FIXTURE, output)
            self.assertEqual(result.returncode, 0, result.stderr)
            run = subprocess.run(['node', output], cwd=tmp, check=True, timeout=10,
                                 capture_output=True, text=True)
            self.assertEqual(run.stdout.strip(), '17,true')

    def test_swapped_application_is_not_convertible(self):
        with tempfile.TemporaryDirectory() as tmp:
            source = Path(tmp) / 'Main.flat'
            source.write_text(FIXTURE.read_text().replace('Duo<Int, Bool>(17, true)',
                                                          'Duo<Bool, Int>(true, 17)'))
            output = Path(tmp) / 'main.js'
            result = self.compile_fixture(source, output)
            self.assertNotEqual(result.returncode, 0)
            self.assertIn("No overload of 'Check'", result.stderr + result.stdout)
            self.assertFalse(output.exists())


if __name__ == '__main__':
    unittest.main()

"""Exercise trait static lazy defaults through fresh public compiler outputs."""
from pathlib import Path
import subprocess
import tempfile
import unittest

PACKAGE = Path(__file__).resolve().parents[1]
FIXTURE = PACKAGE / 'test-fixtures/trait-static-lazy/Main.flat'


class TraitStaticLazyTests(unittest.TestCase):
    def compile(self, source, output):
        return subprocess.run(['node', PACKAGE / 'dist/es6/index.js', source,
                               '-target', 'es6', '-main', 'probe/Main', '-o', output],
                              cwd=output.parent, capture_output=True, text=True, timeout=30)

    def test_inherits_and_overrides_cached_getters(self):
        with tempfile.TemporaryDirectory() as tmp:
            output = Path(tmp) / 'main.js'
            result = self.compile(FIXTURE, output)
            self.assertEqual(result.returncode, 0, result.stderr)
            ran = subprocess.run(['node', output], check=True, capture_output=True, text=True, timeout=10)
            self.assertEqual(ran.stdout.strip(), 'inherited:2 override:1')

    def test_unsupported_forms_do_not_emit(self):
        for declaration, diagnostic in [
                ('trait T { static visible lazy Int value }', "Expected '=>'"),
                ('trait T { static visible lazy Int value => }', 'Expected static lazy property value'),
                ('trait T { static visible lazy Int value => 1 } class C implements T { visible Int value => 2 }', 'Incompatible override'),
                ('trait T<E> { static visible lazy Int value => 1 } class C implements T<Int> {}', 'Generic static trait property defaults are unsupported'),
                ('trait A { static visible lazy Int value => 1 } trait B { static visible lazy Int value => 2 } class C implements A, B {}', 'Ambiguous static trait property')]:
            with self.subTest(declaration=declaration), tempfile.TemporaryDirectory() as tmp:
                source, output = Path(tmp) / 'Main.flat', Path(tmp) / 'main.js'
                source.write_text('package probe\n' + declaration + '\nclass Main { public static main() {} }\n')
                result = self.compile(source, output)
                self.assertNotEqual(result.returncode, 0)
                self.assertIn(diagnostic, result.stdout + result.stderr)
                self.assertFalse(output.exists())


if __name__ == '__main__':
    unittest.main()

from pathlib import Path
import subprocess
import tempfile
import unittest

PACKAGE = Path(__file__).resolve().parents[1]


class BareFieldTest(unittest.TestCase):
    def test_later_line_required(self):
        text = (PACKAGE / 'test-fixtures/bare-field/Main.flat').read_text()
        for separator in ['\n', ' ']:
            with self.subTest(separator=separator), tempfile.TemporaryDirectory() as tmp:
                src = Path(tmp) / 'Main.flat'
                src.write_text(text.replace('= 3\n    Int second', '= 3' + separator + '    Int second'))
                js = Path(tmp) / 'program.js'
                result = subprocess.run(['node', PACKAGE / 'dist/es6/index.js', src, '-main', 'probe/Main',
                    '-target', 'es6', '-o', js], capture_output=True, text=True, cwd=tmp, timeout=45)
                if separator == '\n':
                    self.assertEqual(result.returncode, 0, result.stderr)
                    self.assertEqual(subprocess.check_output(['node', js], text=True, timeout=15), '7\n')
                else:
                    self.assertNotEqual(result.returncode, 0)
                    self.assertIn("Unexpected token 'Int'", result.stdout + result.stderr)
                    self.assertFalse(js.exists())

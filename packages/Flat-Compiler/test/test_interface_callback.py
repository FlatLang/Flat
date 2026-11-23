from pathlib import Path
import subprocess
import tempfile
import unittest

PACKAGE = Path(__file__).resolve().parents[1]


class InterfaceCallbackTest(unittest.TestCase):
    def test_default_and_override(self):
        text = (PACKAGE / 'test-fixtures/interface-callback/Main.flat').read_text()
        for invalid in [False, True]:
            with self.subTest(invalid=invalid), tempfile.TemporaryDirectory() as tmp:
                src = Path(tmp) / 'Main.flat'
                js = Path(tmp) / 'program.js'
                src.write_text(text.replace('onResult(Event)', 'onResult(Event,)') if invalid else text)
                result = subprocess.run(['node', PACKAGE / 'dist/es6/index.js', src, '-target', 'es6',
                    '-main', 'probe/Main', '-o', js], capture_output=True, text=True, cwd=tmp, timeout=45)
                if invalid:
                    self.assertNotEqual(result.returncode, 0)
                    self.assertIn("Expected callback parameter after ','", result.stderr + result.stdout)
                    self.assertFalse(js.exists())
                else:
                    self.assertEqual(result.returncode, 0, result.stderr)
                    self.assertEqual(subprocess.check_output(['node', js], text=True, timeout=15), '3\n7\n')

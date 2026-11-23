"""Regression for the public Node entrypoint; requires a built production driver."""
import json
from pathlib import Path
import subprocess
import tempfile
import unittest


PACKAGE = Path(__file__).resolve().parents[1]
DRIVER = PACKAGE / 'dist/es6/production-driver.js'


class ProductionDriverEntrypointTests(unittest.TestCase):
    def test_user_scenario_is_not_discarded(self):
        with tempfile.TemporaryDirectory() as tmp:
            run = subprocess.run(['node', str(DRIVER), 'not-a-mode', '1', '-library'],
                                 cwd=tmp, capture_output=True, text=True, timeout=30)
            self.assertNotEqual(run.returncode, 0)
            self.assertIn('Unknown production observation scenario', run.stderr + run.stdout)
            self.assertNotIn('flat.compiler.source-reads.v1', run.stdout)

    def test_script_token_is_not_the_scenario(self):
        with tempfile.TemporaryDirectory() as tmp:
            destination = Path(tmp).resolve() / 'output with spaces.js'
            run = subprocess.run([
                'node', str(DRIVER), 'forward', '1',
                str(PACKAGE / 'test-fixtures/reduced-flat-core-class.flat'),
                '-target', 'es6', '-target-runtime', 'node', '-library', '-o', str(destination),
            ], cwd=tmp, capture_output=True, text=True, timeout=30)
            self.assertEqual(run.returncode, 0, run.stderr + run.stdout)
            reports = [json.loads(line) for line in run.stdout.splitlines() if line.startswith('{')]
            report, = [r for r in reports if r.get('schema') == 'flat.compiler.source-reads.v1']
            self.assertEqual((report['scenario'], report['capacity'], report['destination']),
                             ('forward', 1, str(destination)))
            self.assertEqual((report['admitted'], report['settled']), (1, 1))
            self.assertTrue(destination.is_file())


if __name__ == '__main__':
    unittest.main()

"""Incremental producer regression tests. No project subprocesses are executed.

Initial L18 implementation wave: authored only; deliberately NOT RUN.
"""
import copy
from pathlib import Path
import tempfile
import unittest
from unittest import mock

import dogfood_acceptance as gate


class IncrementalWorkloadTests(unittest.TestCase):
    def record(self, reason='exact-hit', **changes):
        value = dict(reused=reason == 'exact-hit', reason=reason,
                     manifestPublished=reason != 'exact-hit', unchangedSources=3,
                     changedSources=0, addedSources=0, removedSources=0)
        value.update(changes)
        return value

    def measured(self, *records):
        return {'commands': [{'persistent': list(records)}]}

    def test_install_skip_is_not_persistent_reuse(self):
        result = gate.incremental_observation('warm', self.measured())
        self.assertFalse(result['proven'])
        self.assertIn('No actual persistent', result['blocker'])

    def test_warm_requires_all_exact_nonpublishing_hits(self):
        warm = self.record()
        self.assertTrue(gate.incremental_observation('warm', self.measured(warm))['proven'])
        for change in ({'manifestPublished': True}, {'changedSources': 1},
                       {'addedSources': 1}, {'removedSources': 1}, {'reused': False}):
            with self.subTest(change=change):
                self.assertFalse(gate.incremental_observation(
                    'warm', self.measured(dict(warm, **change)))['proven'])
        self.assertFalse(gate.incremental_observation(
            'warm', self.measured(warm, self.record('cold')))['proven'])

    def test_source_invalidation_requires_published_closure_miss(self):
        for name, count in (('changed', 'changedSources'), ('dependency', 'changedSources'),
                            ('removed', 'removedSources')):
            with self.subTest(name=name):
                hit = self.record('closure-changed', **{count: 1})
                self.assertTrue(gate.incremental_observation(name, self.measured(hit))['proven'])
                self.assertFalse(gate.incremental_observation(
                    name, self.measured(dict(hit, manifestPublished=False)))['proven'])
                self.assertFalse(gate.incremental_observation(
                    name, self.measured(dict(hit, reason='cold')))['proven'])

    def test_configuration_workloads_require_real_configuration_miss(self):
        for name in ('options', 'configuration', 'tool'):
            self.assertTrue(gate.incremental_observation(
                name, self.measured(self.record('configuration-changed')))['proven'])
            self.assertFalse(gate.incremental_observation(
                name, self.measured(self.record('cold')))['proven'])
        with self.assertRaisesRegex(ValueError, 'Unknown incremental'):
            gate.incremental_observation('typo', self.measured(self.record()))

    def test_relocation_is_root_scoped_and_preserves_option_order(self):
        left = dict(workspace=Path('/retained/workspace'), compiler=Path('/retained/seeds/flatc.js'),
                    directory=Path('/retained'))
        right = dict(workspace=Path('/clean/workspace'), compiler=Path('/clean/seeds/flatc.js'),
                     directory=Path('/clean'))
        argv = ['/node', '/retained/seeds/flatc.js', '/retained/workspace/packages/Core/src',
                '-l', '/retained/flat-home/packages/Test/[link]/src', '-main', 'flat/Main',
                '/retained-other/unchanged']
        self.assertEqual(gate.incremental_arguments(argv, left, right),
                         ['/node', '/clean/seeds/flatc.js', '/clean/workspace/packages/Core/src',
                          '-l', '/clean/flat-home/packages/Test/[link]/src', '-main', 'flat/Main',
                          '/retained-other/unchanged'])
        self.assertEqual(argv[1], '/retained/seeds/flatc.js')

    def test_option_change_removes_aliases_without_changing_other_inputs(self):
        argv = ['/node', '/tool.js', 'src', '--threads', '1', '-main', 'flat/Main']
        self.assertEqual(gate.incremental_option(argv, ('-threads', '--threads'), '2'),
                         ['/node', '/tool.js', 'src', '-main', 'flat/Main', '-threads', '2'])
        with self.assertRaisesRegex(ValueError, 'Malformed'):
            gate.incremental_option(['--threads'], ('--threads',), '2')

    def test_mutations_preserve_original_bytes_and_remove_only_selected_source(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            source = root / 'Input.flat'
            source.write_bytes(b'class Input {}\r\n')
            gate.mutate_incremental_source(root, 'Input.flat')
            self.assertTrue(source.read_bytes().startswith(b'class Input {}\r\n'))
            self.assertIn(b'invalidation probe', source.read_bytes())
            gate.mutate_incremental_source(root, 'Input.flat', remove=True)
            self.assertFalse(source.exists())
            with self.assertRaisesRegex(ValueError, 'production Flat source'):
                gate.mutate_incremental_source(root, 'Missing.flat')

    def test_equal_artifacts_do_not_hide_unequal_inputs_or_diagnostics(self):
        sources = {'packages/Core/Input.flat': 'a' * 64}
        run = dict(sources_sha256=gate.fingerprint(sources), artifacts={'test.js': 'b' * 64},
                   commands=[])
        diagnostics = {'complete': True, 'records': []}
        with mock.patch.object(gate, 'semantic_diagnostics', return_value=diagnostics):
            result = gate.compare_incremental_workload('warm', run, copy.deepcopy(run), sources)
            self.assertTrue(result['diagnostic_equivalence_proven'])
            self.assertFalse(result['incremental_reuse_proven'])
            with self.assertRaisesRegex(ValueError, 'inputs differ'):
                gate.compare_incremental_workload('warm', run, dict(run, sources_sha256='x'), sources)
            with self.assertRaisesRegex(ValueError, 'artifacts differ'):
                gate.compare_incremental_workload('warm', run, dict(run, artifacts={}), sources)
        with mock.patch.object(gate, 'semantic_diagnostics', side_effect=[diagnostics,
                               {'complete': False, 'records': []}]):
            self.assertFalse(gate.compare_incremental_workload(
                'warm', run, run, sources)['diagnostic_equivalence_proven'])

    def test_tool_mutation_never_overwrites_reviewed_seed(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            (root / 'seeds').mkdir()
            seed = root / 'seeds/flatc.js'
            seed.write_bytes(b'console.log("compiler");\n')
            context = dict(directory=root, compiler=seed, policy={})
            original = seed.read_bytes()
            gate.incremental_tool(context)
            baseline = gate.bootstrap.digest(context['compiler'])
            self.assertEqual(context['compiler'].read_bytes(), original)
            gate.incremental_tool(context, changed=True)
            self.assertNotEqual(gate.bootstrap.digest(context['compiler']), baseline)
            self.assertEqual(seed.read_bytes(), original)
            self.assertEqual(context['policy']['seedSha256'], gate.bootstrap.digest(context['compiler']))


if __name__ == '__main__':
    unittest.main()

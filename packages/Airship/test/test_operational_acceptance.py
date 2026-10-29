"""T6 fail-closed operational contracts. Authored in initial wave; NOT RUN."""
import copy
import json
from pathlib import Path
import tempfile
import unittest
from unittest import mock

import dogfood_acceptance as gate


class OperationalAcceptanceTests(unittest.TestCase):
    def setUp(self):
        self.temporary = tempfile.TemporaryDirectory()
        self.addCleanup(self.temporary.cleanup)
        self.root = Path(self.temporary.name).resolve()

    def write(self, path, value):
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(json.dumps(value))

    def ref(self, path):
        return {'path': path.relative_to(self.root).as_posix(), 'sha256': gate.bootstrap.digest(path)}

    def chain_fixture(self):
        seeds = {name: (self.root / ('reviewed-' + name), 'a' * 64) for name in gate.bootstrap.SEED_NAMES}
        entries = {name: {'path': str(path), 'sha256': digest} for name, (path, digest) in seeds.items()}
        review = {'seeds_sha256': 'b' * 64}
        baseline = {'packages/Flat-Core/src/Input.flat': 'c' * 64}
        self.write(self.root / 'sources.json', baseline)
        receipt = {'schema': 2, 'status': 'passed', 'seed_review_sha256': 'd' * 64, 'seed_manifest_sha256': 'b' * 64,
                   'source_manifest_file_sha256': gate.bootstrap.digest(self.root / 'sources.json'), 'stages': []}
        previous = None
        for number in range(3):
            directory = self.root / f'stage{number}'
            workspace = directory / 'workspace'
            runner = directory / 'seeds/airship.js'
            compiler = (workspace / 'packages/Flat/target/flatc.jar' if number == 0
                        else directory / 'seeds/flatc.js')
            selected_runner = entries['airship']['sha256'] if number == 0 else previous[gate.bootstrap.PRODUCTS[1]]
            selected_compiler = entries['java_compiler']['sha256'] if number == 0 else previous[gate.bootstrap.PRODUCTS[0]]
            products = {name: 'e' * 64 for name in gate.bootstrap.PRODUCTS}
            required = [('Flat-Compiler', 'install', 'flatc'), ('Flat-Compiler', 'install', 'production-driver'),
                        ('Airship', 'install', None)]
            required += [(package, operation, 'test') for package in gate.SUITES for operation in ('install', 'run')]
            commands = []
            for index, (package, operation, group) in enumerate(required):
                argv = gate.bootstrap.stage_command(seeds['node'][0], runner if index < 3 else workspace / gate.bootstrap.PRODUCTS[1],
                    workspace, number, package, operation, group,
                    jar=compiler if number == 0 else None, compiler_seed=compiler if number else None)
                commands.append({'argv': argv, 'status': 'passed', 'exit_code': 0,
                                 'compiler_sha256': selected_compiler,
                                 'runner_sha256': selected_runner if index < 3 else products[gate.bootstrap.PRODUCTS[1]]})
            manifest = directory / 'manifest.json'
            for index in range(len(commands)):
                log = directory / f'logs/{index + 1:02d}.log'
                log.parent.mkdir(parents=True, exist_ok=True)
                log.write_text('command log')
                identity = {'pid': index + 42, 'pgid': index + 42, 'session': index + 42, 'start': 'controlled'}
                process = {'identity': identity, 'pid': identity['pid'], 'status': 'settled', 'cleanup_unknown': False,
                           'exit_code': 0, 'wait_completed': True, 'child_reaped': True, 'process_group_absent': True}
                self.write(log.with_suffix('.log.process.json'), {'schema': 'flat.stage.process-identity.v1', 'identity': identity})
                self.write(log.with_suffix('.log.settlement.json'), dict(process, schema='flat.stage.process-settlement.v1'))
            retained = {path.relative_to(directory).as_posix(): gate.bootstrap.digest(path)
                        for path in (directory / 'logs').iterdir() if path.name.endswith('.json')}
            self.write(manifest, {'schema': 2, 'stage': number, 'status': 'passed', 'retained_files': retained,
                                  'products': products, 'sources': baseline, 'commands': commands})
            (directory / 'admissions.jsonl').write_text('')
            receipt['stages'].append({'stage': number, 'manifest': manifest.relative_to(self.root).as_posix(),
                'manifest_sha256': gate.bootstrap.digest(manifest), 'products': products})
            previous = products
        self.write(self.root / 'acceptance.json', receipt)
        return (entries, review, seeds), receipt

    def test_chain_requires_external_review_not_receipt_claim(self):
        with mock.patch.object(gate.bootstrap, 'load_reviewed_bundle') as load:
            for digest in (None, '', 'not-a-digest'):
                with self.subTest(digest=digest), self.assertRaisesRegex(ValueError, 'Independent seed review'):
                    gate.validate_bootstrap_chain(self.root, digest)
            load.assert_not_called()

    def test_chain_rejects_forged_manifest_and_command_even_with_new_hash(self):
        bundle, receipt = self.chain_fixture()
        with mock.patch.object(gate.bootstrap, 'load_reviewed_bundle', return_value=bundle), \
             mock.patch.object(gate, 'file_reference'):
            gate.validate_bootstrap_chain(self.root, 'd' * 64)
            path = self.root / 'stage1/manifest.json'
            value = gate.read_json(path)
            value['commands'][0]['argv'][-1] = '/unreviewed/compiler.js'
            self.write(path, value)
            receipt['stages'][1]['manifest_sha256'] = gate.bootstrap.digest(path)
            self.write(self.root / 'acceptance.json', receipt)
            with self.assertRaisesRegex(ValueError, 'exact command/tool chain'):
                gate.validate_bootstrap_chain(self.root, 'd' * 64)

    def test_chain_manifest_digest_is_checked_before_decoding(self):
        bundle, receipt = self.chain_fixture()
        with mock.patch.object(gate.bootstrap, 'load_reviewed_bundle', return_value=bundle):
            # Put exact stage0 seed identities behind refs without admitting any executable.
            with mock.patch.object(gate, 'file_reference', side_effect=ValueError('changed evidence')) as read:
                with self.assertRaisesRegex(ValueError, 'changed evidence'):
                    gate.validate_bootstrap_chain(self.root, 'd' * 64)
            self.assertEqual(read.call_args.args[1]['path'], 'stage0/manifest.json')

    def test_chain_later_java_admission_fails_even_when_sources_and_hashes_match(self):
        bundle, receipt = self.chain_fixture()
        journal = self.root / 'stage2/admissions.jsonl'
        journal.write_text(json.dumps({'decision': 'allow', 'command': '/java', 'args': ['-jar', 'tool.jar']}) + '\n')
        with mock.patch.object(gate.bootstrap, 'load_reviewed_bundle', return_value=bundle), \
             mock.patch.object(gate, 'file_reference'), self.assertRaisesRegex(ValueError, 'Java invocation'):
            gate.validate_bootstrap_chain(self.root, 'd' * 64)

    def test_missing_reviewed_files_are_not_recovered_from_local_tools(self):
        with mock.patch.object(gate.bootstrap, 'verified_seed') as select:
            with self.assertRaises(FileNotFoundError):
                gate.bootstrap.load_reviewed_bundle(self.root / 'gone-seeds.json', self.root / 'gone-review.json', 'a' * 64)
            select.assert_not_called()

    def test_durable_settlement_cannot_be_asserted_without_matching_sidecars(self):
        workspace, driver, plan, tier, command, path = self.measurement_fixture()
        settled = self.root / command['settlement_reference']['path']
        old = settled.read_bytes()
        value = gate.read_json(settled)
        value['cleanup_unknown'] = True
        self.write(settled, value)
        # Updating the self-reported hash still cannot turn an unknown receipt into success.
        command['settlement_reference'] = self.ref(settled)
        self.write(path, command)
        tier['measurement'] = self.ref(path)
        with self.assertRaisesRegex(ValueError, 'Unbound or unknown'):
            gate.validate_scaling_command(self.root, tier, plan, workspace, gate.bootstrap.digest(driver))
        self.assertNotEqual(settled.read_bytes(), old)
        # This is an isolated test receipt, never a historical run receipt.

    def test_mutations_cannot_change_unrelated_sources_or_remove_multiple_inputs(self):
        first, second = 'packages/Flat-Core/src/First.flat', 'packages/Flat-Core/src/Second.flat'
        baseline = {first: 'a' * 64, second: 'b' * 64, 'packages/Flat-Core/flat.json': 'c' * 64}
        manifests = {'cold': baseline, 'warm': baseline, 'changed': dict(baseline, **{first: 'd' * 64}),
                     'removed': {key: value for key, value in baseline.items() if key != second}}
        mutations = {'changed': first, 'removed': second}
        gate.validate_mutation_manifests(baseline, manifests, mutations)
        changed = copy.deepcopy(manifests)
        changed['changed'][second] = 'e' * 64
        with self.assertRaisesRegex(ValueError, 'unrelated'):
            gate.validate_mutation_manifests(baseline, changed, mutations)
        changed = copy.deepcopy(manifests)
        del changed['removed'][first]
        with self.assertRaisesRegex(ValueError, 'unrelated'):
            gate.validate_mutation_manifests(baseline, changed, mutations)
        with self.assertRaisesRegex(ValueError, 'Missing exact'):
            gate.validate_mutation_manifests(baseline, manifests, {})

    def test_unknown_or_zero_invocation_does_not_prove_empty_diagnostics(self):
        for count in (True, -1, '1', None):
            command = {'compiler_invocations': count, 'compiler_sessions': [{'diagnosticCount': 0, 'cancelled': False}]}
            with self.subTest(count=count):
                self.assertFalse(gate.semantic_diagnostics([command])['complete'])
        self.assertFalse(gate.semantic_diagnostics([])['complete'])

    def measurement_fixture(self):
        workspace = self.root / 'tier/workspace'
        driver, node = self.root / 'driver.js', self.root / 'node'
        driver.write_text('pinned driver')
        node.write_text('pinned node')
        log, journal, rss = self.root / 'measure.log', self.root / 'measure.journal', self.root / 'measure.rss'
        log.write_text('actual measurements')
        journal.write_text('')
        rss.write_text('flat-maxrss-kib=1\n')
        plan = {'timer': {'kind': 'gnu-time', 'path': '/reviewed/time'}}
        tier = {'package': 'Flat-Core', 'arguments': ['src'], 'log': self.ref(log), 'rss': self.ref(rss),
                'ordinary': {'commands': [{'argv': [str(node), '/airship.js']}]}}
        command = {'schema': 'flat.dogfood.scaling-command.v1', 'cwd': str(workspace / 'packages/Flat-Core'),
                   'argv': ['/reviewed/time', '-f', 'flat-maxrss-kib=%M', '-o', str(rss), str(node), str(driver), 'forward', '3', 'src'],
                   'log': tier['log'], 'journal': self.ref(journal), 'admission_offset': 0,
                   'driver': self.ref(driver), 'node': {'path': str(node), 'sha256': gate.bootstrap.digest(node)},
                   'process_cleanup': {'pid': 42, 'exit_code': 0, 'wait_completed': True,
                                       'child_reaped': True, 'process_group_absent': True}, 'temporary_outputs_after': []}
        process = command['process_cleanup']
        process.update(identity={'pid': 42, 'pgid': 42, 'session': 42, 'start': 'controlled'},
                       status='settled', cleanup_unknown=False)
        spawn = log.with_suffix('.log.process.json')
        settlement = log.with_suffix('.log.settlement.json')
        self.write(spawn, {'schema': 'flat.stage.process-identity.v1', 'identity': process['identity']})
        self.write(settlement, dict(process, schema='flat.stage.process-settlement.v1'))
        command.update(spawn_reference=self.ref(spawn), settlement_reference=self.ref(settlement))
        path = self.root / 'measurement.json'
        self.write(path, command)
        tier['measurement'] = self.ref(path)
        return workspace, driver, plan, tier, command, path

    def test_scaling_measurement_reconstructs_exact_timer_node_driver_and_cleanup(self):
        workspace, driver, plan, tier, command, path = self.measurement_fixture()
        gate.validate_scaling_command(self.root, tier, plan, workspace, gate.bootstrap.digest(driver))
        for key, bad in (('argv', command['argv'] + ['--unreviewed']), ('cwd', '/other'),
                         ('admission_offset', True), ('temporary_outputs_after', ['stale'])):
            changed = dict(command, **{key: bad})
            self.write(path, changed)
            tier['measurement'] = self.ref(path)
            with self.subTest(key=key), self.assertRaises(ValueError):
                gate.validate_scaling_command(self.root, tier, plan, workspace, gate.bootstrap.digest(driver))
        changed = copy.deepcopy(command)
        changed['process_cleanup']['cleanup_unknown'] = True
        self.write(path, changed)
        tier['measurement'] = self.ref(path)
        with self.assertRaisesRegex(ValueError, 'Unbound or unknown durable process settlement'):
            gate.validate_scaling_command(self.root, tier, plan, workspace, gate.bootstrap.digest(driver))
        del tier['measurement']
        with self.assertRaises((KeyError, ValueError)):
            gate.validate_scaling_command(self.root, tier, plan, workspace, gate.bootstrap.digest(driver))

    def test_scaling_unknown_timer_cannot_be_made_reviewed_by_flag(self):
        path = self.root / 'review.json'
        for value in ('NaN', 'Infinity', '-Infinity'):
            path.write_text('{"measurement":' + value + '}')
            with self.assertRaisesRegex(ValueError, 'non-finite'):
                gate.bootstrap.reviewed_json(path, gate.bootstrap.digest(path), 'Review')

    def test_cancelled_driver_never_accepts_claimed_artifact(self):
        workspace = self.root / 'workspace'
        destination = workspace / 'packages/Flat-Core/dist/production-driver/cancel-after-read-forward-1/test.js'
        run = {'scenario': 'cancel-after-read-forward', 'capacity': 1, 'destination': str(destination),
               'log': {'path': 'read.log'}, 'inputs': {}, 'artifacts': {'test.js': {}}}
        with mock.patch.object(gate, 'driver_source_inputs', return_value={}), \
             self.assertRaisesRegex(ValueError, 'Cancelled driver published'):
            gate.validate_driver_inputs(self.root, {}, run, {'admitted': 0})


if __name__ == '__main__':
    unittest.main()

"""Scaling measurement regression contracts. Initial-wave tests: NOT RUN."""
import copy
import json
from pathlib import Path
import tempfile
import unittest

import dogfood_acceptance as gate


class ScalingMeasurementTests(unittest.TestCase):
    def setUp(self):
        self.temporary = tempfile.TemporaryDirectory()
        self.addCleanup(self.temporary.cleanup)
        self.root = Path(self.temporary.name)
        self.source_sha = 'a' * 64
        self.plan = {
            'schema': 'flat.dogfood.scaling-plan.v1',
            'source_manifest_sha256': self.source_sha,
            'tiers': [dict(package=package, min_source_bytes=10 * 2 ** index,
                           min_sources=1, max_rss_bytes=1024 * 1024,
                           max_retained_sources=3, max_retained_source_bytes=1000)
                      for index, package in enumerate(('Flat-Project-Loading', 'Flat-Core', 'Flat-Parser'))],
            'max_rss_growth_numerator': 2, 'max_rss_growth_denominator': 1,
            'min_input_growth_factor': 2,
            'timer': dict(kind='bsd-time', path='/usr/bin/time', sha256='b' * 64)}

    def read_plan(self, value=None, source_sha=None, digest=None):
        path = self.root / 'plan.json'
        path.write_text(json.dumps(self.plan if value is None else value))
        return gate.scaling_plan(path, digest or gate.bootstrap.digest(path), source_sha or self.source_sha)

    def test_reviewed_digest_and_source_binding(self):
        self.assertEqual(self.read_plan(), self.plan)
        with self.assertRaisesRegex(ValueError, 'digest mismatch'):
            self.read_plan(digest='0' * 64)
        with self.assertRaisesRegex(ValueError, 'Stale'):
            self.read_plan(source_sha='c' * 64)

    def test_distinct_materially_growing_bounded_plan(self):
        mutations = []
        for count in (0, 2, 9):
            value = copy.deepcopy(self.plan)
            value['tiers'] = (value['tiers'] * 3)[:count]
            mutations.append(value)
        value = copy.deepcopy(self.plan)
        value['tiers'][1]['package'] = value['tiers'][0]['package']
        mutations.append(value)
        value = copy.deepcopy(self.plan)
        value['tiers'][1]['min_source_bytes'] = 19
        mutations.append(value)
        value = copy.deepcopy(self.plan)
        value['tiers'][0] = None
        mutations.append(value)
        for field in ('min_source_bytes', 'min_sources', 'max_rss_bytes',
                      'max_retained_sources', 'max_retained_source_bytes'):
            for invalid in (None, 0, -1, True, '100'):
                value = copy.deepcopy(self.plan)
                value['tiers'][0][field] = invalid
                mutations.append(value)
        for value in mutations:
            with self.subTest(value=value), self.assertRaises(ValueError):
                self.read_plan(value)

    def test_validator_requires_archived_matching_timer(self):
        self.read_plan()
        path = self.root / 'plan.json'
        value = {'plan': {'path': 'plan.json', 'sha256': gate.bootstrap.digest(path)}, 'tiers': []}
        with self.assertRaisesRegex(ValueError, 'Missing pinned RSS timer'):
            gate.validate_scaling(self.root, value, self.source_sha, {}, 'c' * 64)
        timer = self.root / 'timer.bin'
        timer.write_bytes(b'not-the-reviewed-timer')
        value['timer'] = {'path': 'timer.bin', 'sha256': gate.bootstrap.digest(timer)}
        with self.assertRaisesRegex(ValueError, 'timer identity mismatch'):
            gate.validate_scaling(self.root, value, self.source_sha, {}, 'c' * 64)

    def test_pinned_timer_contract_and_rss_parser(self):
        for timer in (None, {}, dict(kind='unknown', path='/usr/bin/time', sha256='b' * 64),
                      dict(kind='bsd-time', path='time', sha256='b' * 64),
                      dict(kind='bsd-time', path='/usr/bin/time', sha256='bad')):
            value = copy.deepcopy(self.plan)
            value['timer'] = timer
            with self.subTest(timer=timer), self.assertRaises(ValueError):
                self.read_plan(value)
        path = self.root / 'rss'
        for kind, text, expected in (('bsd-time', ' 123 maximum resident set size\n', 123),
                                     ('gnu-time', 'flat-maxrss-kib=123\n', 123 * 1024)):
            path.write_text(text)
            self.assertEqual(gate.rss_bytes(path, kind), expected)
            for invalid in ('', text + text, text.replace('123', '0')):
                path.write_text(invalid)
                with self.assertRaises(ValueError):
                    gate.rss_bytes(path, kind)
        path.write_text('flat-maxrss-kib=1\n')
        with self.assertRaisesRegex(ValueError, 'Unknown'):
            gate.rss_bytes(path, 'anything')
        link = self.root / 'linked-rss'
        link.symlink_to(path)
        with self.assertRaises(ValueError):
            gate.rss_bytes(link, 'gnu-time')

    def retention_fixture(self):
        workspace = self.root / 'workspace'
        source = workspace / 'packages/Flat-Core/src/Input.flat'
        source.parent.mkdir(parents=True, exist_ok=True)
        source.write_text('1234567890')
        inputs = {'packages/Flat-Core/src/Input.flat': {'bytes': 10}}
        events = [dict(schema='flat.compiler.source-read-event.v1', source=str(source),
                       transition=transition, retained=retained)
                  for transition, retained in (('admitted', 1), ('started', 1),
                                                ('settled', 1), ('consumed', 0))]
        report = dict(capacity=3, admitted=1, peakRetained=1, retainedAfter=0)
        return workspace, inputs, events, report

    def observe(self, workspace, inputs, events, report):
        log = self.root / 'driver.log'
        log.write_text(''.join(json.dumps(event) + '\n' for event in events))
        return gate.scaling_retention(log, inputs, workspace, 'Flat-Core', report)

    def test_actual_retention_and_cleanup_are_required(self):
        workspace, inputs, events, report = self.retention_fixture()
        self.assertEqual(self.observe(workspace, inputs, events, report),
                         dict(scope='accepted-driver-source-read-window', peak_sources=1,
                              peak_source_bytes=10, sources_after=0, source_bytes_after=0))
        for changed in ([], events[:-1], events + [events[-1]], [events[0]] + events):
            with self.subTest(events=changed), self.assertRaises(ValueError):
                self.observe(workspace, inputs, changed, report)
        changed = copy.deepcopy(events)
        changed[0]['retained'] = 0
        with self.assertRaisesRegex(ValueError, 'Inconsistent'):
            self.observe(workspace, inputs, changed, report)
        report['peakRetained'] = 2
        with self.assertRaisesRegex(ValueError, 'cleanup'):
            self.observe(workspace, inputs, events, report)

    def test_retention_bytes_measure_overlapping_read_window(self):
        workspace, inputs, events, report = self.retention_fixture()
        second = copy.deepcopy(events)
        for event in second:
            event['source'] = str(workspace / 'packages/Flat-Core/src/Second.flat')
        inputs['packages/Flat-Core/src/Second.flat'] = {'bytes': 20}
        combined = [events[0], second[0], events[1], events[2], events[3],
                    second[1], second[2], second[3]]
        for event, count in zip(combined, (1, 2, 2, 2, 1, 1, 1, 0)):
            event['retained'] = count
        report.update(admitted=2, peakRetained=2)
        observed = self.observe(workspace, inputs, combined, report)
        self.assertEqual(observed['peak_source_bytes'], 30)
        self.assertEqual(observed['peak_sources'], 2)
        report['capacity'] = 1
        with self.assertRaisesRegex(ValueError, 'capacity'):
            self.observe(workspace, inputs, combined, report)

    def test_missing_observations_and_budget_growth_fail_closed(self):
        tiers = [dict(inputs={'input': dict(bytes=10 * 2 ** index)}, rss_bytes=100,
                      retention=dict(scope='accepted-driver-source-read-window', peak_sources=1,
                                     peak_source_bytes=10, sources_after=0, source_bytes_after=0))
                 for index in range(3)]
        gate.scaling_measurement_budgets(self.plan, tiers)
        for field, invalid in (('peak_sources', 4), ('peak_source_bytes', 1001),
                               ('sources_after', 1), ('source_bytes_after', 1),
                               ('scope', 'whole-compiler')):
            changed = copy.deepcopy(tiers)
            changed[0]['retention'][field] = invalid
            with self.subTest(field=field), self.assertRaises(ValueError):
                gate.scaling_measurement_budgets(self.plan, changed)
        changed = copy.deepcopy(tiers)
        del changed[0]['retention']
        with self.assertRaisesRegex(ValueError, 'retention'):
            gate.scaling_measurement_budgets(self.plan, changed)
        changed = copy.deepcopy(tiers)
        changed[1]['rss_bytes'] = 201
        with self.assertRaisesRegex(ValueError, 'memory growth'):
            gate.scaling_measurement_budgets(self.plan, changed)
        changed = copy.deepcopy(tiers)
        changed[0]['inputs']['input']['bytes'] = 11
        with self.assertRaisesRegex(ValueError, 'materially grow'):
            gate.scaling_measurement_budgets(self.plan, changed)
        self.assertEqual(self.plan['max_rss_growth_numerator'], 2)


if __name__ == '__main__':
    unittest.main()

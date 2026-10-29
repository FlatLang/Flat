#!/usr/bin/env python3
"""Full dogfood gate layered on stage_bootstrap's reviewed, guarded bootstrap.

Evidence is a trusted-producer receipt, not a sandbox or a proof of arbitrary
claims. Its independent SHA-256 pins delivery; command logs and artifact hashes
are checked, but producers must actually observe assertions. See root README.
Missing production evidence fails, never downgrades to bootstrap-only success.
"""
import argparse
import hashlib
import json
import os
import shutil
from pathlib import Path, PurePosixPath
import re
import sys

import stage_bootstrap as bootstrap

SUITES = ('Airship', 'Flat-Project-Loading', 'Flat-Parser', 'Flat-Name-Binding',
          'Flat-Bound-IR', 'Flat-ES6-Bound-Writer', 'Flat-Compiler')
SCENARIOS = ('cold', 'warm', 'changed', 'removed')
INVALIDATIONS = ('dependency', 'options', 'configuration', 'tool')
DRIVER_SCENARIOS = ('forward', 'reverse', 'cancel-after-read-forward', 'cancel-after-stage-reverse')
LEGACY_DRIVER_SCENARIOS = ('cancel-after-read', 'cancel-after-stage')
MAX_RECEIPT_BYTES = 8 * 1024 * 1024
MAX_REFERENCES = 100000


def read_json(path):
    if path.is_symlink() or not path.is_file() or path.stat().st_size > MAX_RECEIPT_BYTES:
        raise ValueError(f'Missing, linked, or oversized receipt: {path}')
    def unique(pairs):
        result = {}
        for key, value in pairs:
            if key in result:
                raise ValueError(f'Duplicate receipt key: {key}')
            result[key] = value
        return result
    def invalid_constant(value):
        raise ValueError(f'Non-finite receipt number: {value}')
    return json.loads(path.read_text(), object_pairs_hook=unique, parse_constant=invalid_constant)


def require(condition, message):
    if not condition:
        raise ValueError(message)


def sha(value):
    return (isinstance(value, str) and len(value) == 64
            and all(character in '0123456789abcdef' for character in value))


def fingerprint(value):
    return hashlib.sha256(json.dumps(value, sort_keys=True, separators=(',', ':')).encode()).hexdigest()


def file_reference(root, entry):
    require(isinstance(entry, dict) and set(entry) == {'path', 'sha256'}, 'Invalid file reference')
    name = entry['path']
    require(isinstance(name, str) and bool(name) and '\\' not in name, 'Invalid evidence path')
    relative = PurePosixPath(name)
    require(not relative.is_absolute() and '..' not in relative.parts and relative.as_posix() == name,
            'Evidence path must be normalized and relative')
    path = root / name
    require(root.resolve() in path.resolve().parents, 'Evidence escapes receipt directory')
    cursor = path
    while cursor != root:
        require(not cursor.is_symlink(), 'Linked evidence is not admitted')
        cursor = cursor.parent
    require(path.is_file() and sha(entry['sha256']) and bootstrap.digest(path) == entry['sha256'],
            f'Missing or changed evidence: {name}')
    return entry['sha256']


def hash_manifest(value, name, nonempty=True):
    require(isinstance(value, dict) and (bool(value) or not nonempty)
            and len(value) <= MAX_REFERENCES, f'Invalid {name} manifest')
    for path, digest in value.items():
        require(isinstance(path, str) and bool(path) and '\\' not in path
                and not PurePosixPath(path).is_absolute() and PurePosixPath(path).as_posix() == path
                and '..' not in PurePosixPath(path).parts and sha(digest), f'Invalid {name} entry')
    return value


def run_evidence(root, value):
    require(isinstance(value, dict), 'Missing run evidence')
    require(value.get('exit_status') == 0 and type(value.get('exit_status')) is int,
            'Run did not exit successfully')
    argv = value.get('argv')
    require(isinstance(argv, list) and bool(argv) and all(isinstance(arg, str) and arg for arg in argv),
            'Missing exact command argv')
    file_reference(root, value['log'])
    artifacts = value.get('artifacts')
    require(isinstance(artifacts, dict) and bool(artifacts) and len(artifacts) <= MAX_REFERENCES,
            'Missing generated artifacts')
    for entry in artifacts.values():
        file_reference(root, entry)
    diagnostics = file_reference(root, value['diagnostics'])
    assertions = value.get('assertions')
    require(isinstance(assertions, list) and bool(assertions)
            and all(isinstance(item, str) and item.strip() for item in assertions),
            'Missing observed runtime assertions')
    require(value.get('java_invocations') == 0 and type(value.get('java_invocations')) is int,
            'Java-free evidence missing')
    file_reference(root, value['subprocess_journal'])
    journal = root / value['subprocess_journal']['path']
    require(journal.stat().st_size <= 16 * 1024 * 1024, 'Admission journal exceeds capacity')
    count = 0
    with journal.open() as records:
        for line in records:
            require(len(line) <= 1024 * 1024, 'Admission record exceeds capacity')
            record = json.loads(line)
            require(record.get('decision') == 'allow', 'Rejected or unknown subprocess admission')
            command = record.get('command', '')
            require(Path(command).name.lower() not in ('java', 'java.exe')
                    and not any(str(arg).lower().endswith('.jar') for arg in record.get('args', [])),
                    'Java invocation in generated-tool evidence')
            count += 1
    require(count > 0, 'Empty subprocess admission journal')
    # Equality uses semantic output names, not physical scenario directories.
    return ({name: entry['sha256'] for name, entry in artifacts.items()}, diagnostics)


def validate_bootstrap_chain(stages, review_sha256):
    """Reconstruct reviewed delivery and the three-stage command/product chain.

    The digest is supplied by the protected caller, never recovered from the
    receipt being admitted. Missing historical inputs are an error, not recovery
    permission to select another executable with a convenient name.
    """
    require(sha(review_sha256), 'Independent seed review digest required')
    entries, review, seeds = bootstrap.load_reviewed_bundle(
        stages / 'seeds.json', stages / 'review.json', review_sha256)
    receipt = read_json(stages / 'acceptance.json')
    require(type(receipt.get('schema')) is int and receipt['schema'] == 2 and receipt.get('status') == 'passed',
            'Bootstrap chain is not a completed schema-2 receipt')
    require(receipt.get('seed_review_sha256') == review_sha256
            and receipt.get('seed_manifest_sha256') == review['seeds_sha256'],
            'Bootstrap reviewed delivery identity mismatch')
    baseline = hash_manifest(read_json(stages / 'sources.json'), 'bootstrap source')
    require(receipt.get('source_manifest_file_sha256') == bootstrap.digest(stages / 'sources.json'),
            'Bootstrap source receipt identity mismatch')
    stage_refs = receipt.get('stages')
    require(isinstance(stage_refs, list) and len(stage_refs) == 3, 'Incomplete bootstrap chain references')
    previous = None
    for number, reference in enumerate(stage_refs):
        require(isinstance(reference, dict) and type(reference.get('stage')) is int
                and reference['stage'] == number and reference.get('manifest') == f'stage{number}/manifest.json',
                'Bootstrap stage reference order mismatch')
        file_reference(stages, {'path': reference['manifest'], 'sha256': reference.get('manifest_sha256')})
        stage = read_json(stages / reference['manifest'])
        require(type(stage.get('schema')) is int and stage['schema'] == 2
                and type(stage.get('stage')) is int and stage['stage'] == number
                and stage.get('status') == 'passed', 'Incomplete bootstrap chain stage')
        products = hash_manifest(stage.get('products'), 'stage product')
        require(reference.get('products') == products and stage.get('sources') == baseline,
                'Bootstrap stage source/product reference mismatch')
        directory = stages / f'stage{number}'
        workspace = directory / 'workspace'
        compiler_sha = entries['java_compiler']['sha256'] if number == 0 else previous[bootstrap.PRODUCTS[0]]
        runner_sha = entries['airship']['sha256'] if number == 0 else previous[bootstrap.PRODUCTS[1]]
        runner = directory / 'seeds/airship.js'
        file_reference(stages, {'path': runner.relative_to(stages).as_posix(), 'sha256': runner_sha})
        compiler = (workspace / 'packages/Flat/target/flatc.jar' if number == 0
                    else directory / 'seeds/flatc.js')
        file_reference(stages, {'path': compiler.relative_to(stages).as_posix(), 'sha256': compiler_sha})
        if number == 0:
            file_reference(stages, {'path': (workspace / 'packages/Flat-ES6/target/flat-es6.jar').relative_to(stages).as_posix(),
                                   'sha256': entries['java_backend']['sha256']})
        commands = stage.get('commands')
        required = [('Flat-Compiler', 'install', 'flatc'), ('Flat-Compiler', 'install', 'production-driver'),
                    ('Airship', 'install', None)]
        required += [(package, operation, 'test') for package in SUITES for operation in ('install', 'run')]
        require(isinstance(commands, list) and len(required) <= len(commands) <= 128,
                'Incomplete bootstrap prerequisite/suite commands')
        for index, (package, operation, group) in enumerate(required):
            command = commands[index]
            selected_runner = runner if index < 3 else workspace / bootstrap.PRODUCTS[1]
            expected = bootstrap.stage_command(seeds['node'][0], selected_runner, workspace, number,
                                               package, operation, group,
                                               jar=compiler if number == 0 else None,
                                               compiler_seed=compiler if number else None)
            require(command.get('status') == 'passed' and type(command.get('exit_code')) is int
                    and command['exit_code'] == 0, 'Bootstrap command terminal exit unproved')
            log = directory / f'logs/{index + 1:02d}.log'
            process_value = {'log': {'path': log.relative_to(stages).as_posix()},
                             **process_receipt_references(stages, log)}
            for key in ('spawn_reference', 'settlement_reference'):
                relative = (stages / process_value[key]['path']).relative_to(directory).as_posix()
                require(stage.get('retained_files', {}).get(relative) == process_value[key]['sha256'],
                        'Bootstrap durable process receipt missing from pinned manifest')
            settlement = read_json(log.with_suffix(log.suffix + '.settlement.json'))
            process_value['process_cleanup'] = {key: item for key, item in settlement.items() if key != 'schema'}
            validate_process_receipts(stages, process_value, 0)
            require(command.get('argv') == expected and command.get('compiler_sha256') == compiler_sha
                    and command.get('runner_sha256') == (runner_sha if index < 3 else products[bootstrap.PRODUCTS[1]]),
                    'Bootstrap exact command/tool chain mismatch')
        if number > 0:
            admission_records(directory / 'admissions.jsonl')  # rejects Java even if its exit was zero
        previous = products
    return {'review_sha256': review_sha256, 'seeds_sha256': review['seeds_sha256']}


def validate_bootstrap(stages, require_public=False, review_sha256=None):
    """Consume F's schema-2 receipt; never infer success from directory presence."""
    if require_public:
        validate_bootstrap_chain(stages, review_sha256)
    receipt = read_json(stages / 'acceptance.json')
    require(receipt.get('schema') == 2 and receipt.get('status') == 'passed',
            'Bootstrap schema-2 acceptance did not pass')
    products = []
    for number in range(3):
        stage = read_json(stages / f'stage{number}/manifest.json')
        require(stage.get('schema') == 2 and stage.get('stage') == number
                and stage.get('status') == 'passed', 'Incomplete bootstrap stage')
        require(stage.get('runtime') == ('java' if number == 0 else 'flat'),
                'Bootstrap runtime selection mismatch')
        require(stage.get('compiler_selection') == ('reviewed-java-seed' if number == 0
                else 'explicit-generated-script'), 'Bootstrap compiler selection mismatch')
        selected = hash_manifest(stage.get('products'), 'stage product')
        require(set(selected) == set(bootstrap.PRODUCTS), 'Missing generated stage tool')
        for name, digest in selected.items():
            file_reference(stages / f'stage{number}/workspace', {'path': name, 'sha256': digest})
        if require_public:
            require(stage.get('public_workloads') is not None, 'Missing bootstrap public production workloads')
            validate_public_workloads(stages, stage['public_workloads'], read_json(stages / 'sources.json'), selected,
                                      read_json(stages / 'seeds.json')['node'])
        products.append(selected)
        directory = stages / f'stage{number}'
        file_reference(directory, {'path': 'admissions.jsonl', 'sha256': stage.get('admissions_sha256')})
        commands = stage.get('commands')
        require(isinstance(commands, list) and bool(commands), 'Missing bootstrap commands')
        for index, command in enumerate(commands, 1):
            file_reference(directory, {'path': f'logs/{index:02d}.log',
                                       'sha256': command.get('log_sha256')})
            require(command.get('status') == 'passed' and sha(command.get('runner_sha256'))
                    and sha(command.get('compiler_sha256')) and sha(command.get('log_sha256')),
                    'Incomplete bootstrap command identities')
        for suite in SUITES:
            for operation in ('install', 'run'):
                require(any(operation in command.get('argv', []) and 'test' in command['argv']
                            and any(str(arg).endswith('/packages/' + suite) for arg in command['argv'])
                            for command in commands), f'Missing bootstrap {suite} {operation} test')
    require(products[1] == products[2], 'Stage1/2 tool identities differ')
    return products[2]


def validate_evidence(path, expected, stages, scaling_plan_sha=None, review_sha256=None):
    require(sha(expected) and bootstrap.digest(path) == expected, 'Evidence receipt digest mismatch')
    value = read_json(path)
    require(isinstance(value, dict) and type(value.get('schema')) is int
            and value['schema'] in (1, 2), 'Unknown evidence receipt schema')
    if value['schema'] == 2:
        return validate_production_evidence(path, value, stages, scaling_plan_sha, review_sha256)
    root = path.parent.resolve()
    require(value.get('schema') == 1 and value.get('status') == 'passed', 'Incomplete evidence receipt')
    source = read_json(stages / 'sources.json')
    receipt = read_json(stages / 'acceptance.json')
    require(receipt.get('status') == 'passed', 'Bootstrap acceptance did not pass')
    require(value.get('source_manifest_sha256') == bootstrap.digest(stages / 'sources.json'),
            'Evidence source snapshot does not match bootstrap')
    tools = validate_bootstrap(stages)
    require(value.get('tools') == tools,
            'Evidence must select stage2 generated tool identities')
    options = value.get('options')
    require(isinstance(options, dict) and options.get('target') == 'es6'
            and options.get('flatc_runtime') == 'flat', 'Explicit Flat ES6 options required')
    require(value.get('options_sha256') == fingerprint(options), 'Options fingerprint mismatch')
    dependencies = hash_manifest(value.get('dependencies'), 'dependency')
    require(isinstance(source, dict) and bool(source), 'Missing bootstrap source inventory')
    require(all(source.get(name) == digest for name, digest in dependencies.items()),
            'Dependency manifest does not match production source inputs')
    require(any(name.endswith('/flat.json') for name in dependencies), 'Dependency package options missing')
    core = value.get('core', {})
    run_evidence(root, core)
    require(core.get('production_inputs') is True and core.get('package') == 'Flat-Core',
            'Core gate requires untrimmed production inputs')
    require('Double.equals(Object)' in core['assertions'], 'Core Double.equals(Object) execution missing')
    for suite in SUITES:
        run_evidence(root, value.get('suites', {}).get(suite))
    scenarios = value.get('incremental', {})
    for name in SCENARIOS:
        scenario = scenarios.get(name, {})
        inputs = hash_manifest(scenario.get('sources'), f'{name} source')
        require(scenario.get('source_manifest_sha256') == fingerprint(inputs), 'Scenario fingerprint mismatch')
        incremental = run_evidence(root, scenario.get('incremental'))
        clean = run_evidence(root, scenario.get('clean'))
        require(incremental == clean, f'{name}: incremental/clean outputs or diagnostics differ')
        require(scenario.get('options_sha256') == value['options_sha256']
                and scenario.get('tools') == value['tools']
                and scenario.get('dependencies') == dependencies, 'Scenario build context mismatch')
    baseline = scenarios['cold']['sources']
    require(baseline == source and scenarios['warm']['sources'] == baseline,
            'Cold/warm source manifests differ from production snapshot')
    require(scenarios['warm'].get('reused_cache') is True, 'Warm cache reuse not observed')
    changed = scenarios['changed']['sources']
    removed = scenarios['removed']['sources']
    require(set(changed) == set(baseline) and changed != baseline, 'Changed-input scenario did not change bytes')
    require(set(removed) < set(baseline) and all(baseline[name] == digest for name, digest in removed.items()),
            'Removed-input scenario must remove production inputs')
    schedules = value.get('schedules')
    require(isinstance(schedules, list) and 2 <= len(schedules) <= 32, 'Missing bounded schedule evidence')
    results, concurrency, orders = [], set(), set()
    for schedule in schedules:
        workers = schedule.get('concurrency')
        require(type(workers) is int and 1 <= workers <= 256, 'Invalid schedule concurrency')
        concurrency.add(workers)
        orders.add(schedule.get('completion_order'))
        require(schedule.get('controlled') is True, 'Timing sleeps are not controlled schedule evidence')
        results.append(run_evidence(root, schedule))
    require(1 in concurrency and any(number > 1 for number in concurrency)
            and {'forward', 'reverse'} <= orders, 'Serial/parallel reversed schedules required')
    require(all(result == results[0] for result in results), 'Schedule-dependent output or diagnostics')
    lifecycle = value.get('lifecycle', {})
    run_evidence(root, lifecycle)
    required = ('backpressure', 'capacity_limit', 'bounded_in_flight', 'unrelated_progress',
                'failure_isolation', 'cancellation_observed', 'children_reaped', 'temporary_outputs_removed')
    require(all(lifecycle.get(name) is True for name in required), 'Missing observed lifecycle evidence')
    return {'schema': 1, 'status': 'blocked', 'full_acceptance': False,
            'blockers': ['Legacy assertion-only receipt has no bound production observations'], 'evidence_sha256': expected,
            'bootstrap_receipt_sha256': bootstrap.digest(stages / 'acceptance.json'),
            'source_manifest_sha256': value['source_manifest_sha256'], 'tools': value['tools'],
            'options_sha256': value['options_sha256'], 'suites': list(SUITES),
            'gates': ['production-core', 'stage0-1-2', 'java-free', 'incremental', 'schedules', 'lifecycle']}


def production_gates(receipt):
    """Derive coverage from validated producer measurements, never verdict flags.

    Memory-sink schedules and watchdog fixtures deliberately have narrower scopes
    than production artifacts and operating-system cleanup.
    """
    gates = {}
    gates['public-production-workloads'] = set(receipt.get('public_workloads', {})) == {'airship-toolchain', 'core-lr'}
    gates['ordinary-production-products'] = (set(receipt.get('ordinary_products', {}).get('products', {}))
                                              == {'Airship', 'Flat-Compiler'})
    gates['invalid-production-controls'] = set(receipt.get('ordinary_products', {}).get('controls', {})) == set(INVALID_CONTROLS)
    workloads = receipt.get('workloads', {})
    for name in SCENARIOS:
        workload = workloads.get(name, {})
        retained, clean = workload.get('retained_output'), workload.get('clean')
        present = isinstance(retained, dict) and isinstance(clean, dict)
        gates['incremental-' + name] = bool(present and
            incremental_observation(name, retained)['proven'] and
            retained['artifacts'] == clean['artifacts'] and
            retained['sources_sha256'] == clean['sources_sha256'] and
            semantic_diagnostics(retained['commands'])['complete'] and
            semantic_diagnostics(clean['commands'])['complete'] and
            semantic_diagnostics(retained['commands'])['records'] ==
            semantic_diagnostics(clean['commands'])['records'])
    for name in INVALIDATIONS:
        entry = receipt.get('invalidations', {}).get(name, {})
        retained, clean = entry.get('retained_output'), entry.get('clean')
        gates['incremental-' + name] = bool(isinstance(retained, dict) and isinstance(clean, dict)
            and incremental_observation(name, retained)['proven']
            and retained.get('artifacts') == clean.get('artifacts')
            and retained.get('sources_sha256') == clean.get('sources_sha256')
            and semantic_diagnostics(retained.get('commands', []))['complete']
            and semantic_diagnostics(clean.get('commands', []))['complete']
            and semantic_diagnostics(retained['commands'])['records'] == semantic_diagnostics(clean['commands'])['records'])
    suites = receipt.get('stage2_suites', {})
    gates['seven-production-suites'] = set(suites) == set(SUITES)
    gates['structured-production-diagnostics'] = bool(suites) and all(
        semantic_diagnostics(run.get('commands', []))['complete'] for run in suites.values())
    records = [record for command in suites.get('Flat-Compiler', {}).get('commands', [])
               for record in command.get('lifecycle', [])]
    scenarios = {value['scenario'] for value in records}
    expected = {'package-forward', 'package-reverse', 'source-failure-isolation',
                'package-cancellation', 'running-source-cancellation',
                'artifact-backpressure-cancellation', 'queued-source-cancellation'}
    gates['compiler-lifecycle-scenarios'] = expected <= scenarios
    schedules = [value for value in records if value['scenario'] in ('package-forward', 'package-reverse')]
    gates['controlled-package-schedules'] = (
        any(value['concurrency'] == 1 and value['scenario'] == 'package-forward' for value in schedules)
        and any(value['concurrency'] > 1 and value['scenario'] == 'package-forward' for value in schedules)
        and any(value['concurrency'] > 1 and value['scenario'] == 'package-reverse' for value in schedules))
    gates['backpressure-cancellation'] = any(
        value['scenario'] == 'artifact-backpressure-cancellation' and value['cancelled']
        and value['artifactBackpressure'] and (value['pendingArtifactSendsAtPressure'] or 0) > 0
        for value in records)
    gates['failure-isolation'] = any(value['scenario'] == 'source-failure-isolation'
        and (value['unrelatedArtifactsBeforeFirstSettlement'] or 0) > 0 for value in records)
    gates['cancellation-waits-for-cleanup'] = any(value['cancelled'] and (value['cleanupWaitSamples'] or 0) > 0
                                               for value in records)
    # The current producer explicitly exports these as unobserved. Neither a
    # caller-authored boolean nor a separate watchdog fixture closes this gate.
    measured_runs = list(suites.values()) + [workload[mode]
        for workload in workloads.values() for mode in ('retained_output', 'clean')
        if isinstance(workload, dict) and isinstance(workload.get(mode), dict)]
    measured_runs += [entry[mode] for entry in receipt.get('invalidations', {}).values()
                      if isinstance(entry, dict) for mode in ('baseline', 'retained_output', 'clean')
                      if isinstance(entry.get(mode), dict)]
    commands = [command for run in measured_runs for command in run.get('commands', [])]
    gates['production-command-reaping'] = bool(commands) and all(
        command.get('process_cleanup', {}).get('wait_completed') is True
        and command.get('process_cleanup', {}).get('child_reaped') is True
        and command.get('process_cleanup', {}).get('process_group_absent') is True
        for command in commands)
    gates['production-terminal-staging-absent'] = bool(commands) and all(
        command.get('temporary_outputs_after') == [] for command in commands)
    gates['production-measured-memory-scaling'] = bool(receipt.get('scaling', {}).get('tiers'))
    driver = receipt.get('production_driver', {})
    driver_runs = driver.get('runs', [])
    gates['production-workload-controlled-schedules'] = (len(driver_runs) == 8
        and {(run.get('scenario'), run.get('capacity')) for run in driver_runs}
        == {(scenario, capacity) for scenario in DRIVER_SCENARIOS for capacity in (1, 3)})
    gates['production-structural-cancellation'] = gates['production-workload-controlled-schedules'] and all(
        run.get('report', {}).get('cancellationRequested') is True
        and run.get('report', {}).get('session', {}).get('cleanupSettled') is True
        for run in driver_runs if run.get('scenario', '').startswith('cancel-'))
    gates['production-subprocess-filesystem-cleanup'] = bool(driver_runs) and all(
        run.get('artifact_lifecycle') is not None and run.get('temporary_outputs_after') == []
        and all(run.get('process_cleanup', {}).get(name) is True
                for name in ('wait_completed', 'child_reaped', 'process_group_absent')) for run in driver_runs)
    gates['production-incremental-finalization'] = bool(driver_runs) and all(
        run.get('report', {}).get('input', {}).get('reused') is False
        and run.get('temporary_outputs_after') == []
        and run.get('report', {}).get('session', {}).get('cleanupSettled') is True
        for run in driver_runs)
    # Every stream is re-read from a hash-bound log before this verdict. Cold
    # successful runs must own and finalize real temporaries; cancellation may
    # precede creation, but must not publish or leave staging/locks behind.
    gates['incremental-temporary-lifecycle-observed'] = bool(driver_runs) and all(
        'manifest_lifecycle' in run and run.get('temporary_outputs_after') == []
        and (run['scenario'].startswith('cancel-') or bool(run['manifest_lifecycle'])) for run in driver_runs)
    gates['cancellation-waits-for-cleanup'] = (gates['cancellation-waits-for-cleanup']
        or gates['production-structural-cancellation'])
    blockers = [name for name, observed in gates.items() if not observed]
    return {'gates': gates, 'blockers': blockers, 'full_acceptance': not blockers,
            'status': 'passed' if not blockers else 'blocked'}


def validate_production_selection(root, run, package, tools, node_seed):
    """Bind public command selection to the stage2 tools and this source workspace.

    Kept separate from log parsing so historical run readers retain their identity;
    schema-2 aggregate acceptance always requires this stronger production binding.
    """
    require(isinstance(run, dict), 'Missing production selection')
    if run.get('execution_kind') == 'ordinary-stage2-cli':
        require(run.get('package') == package, 'Production package selection mismatch')
        selected_node = Path(node_seed['path'])
        require(selected_node.is_file() and bootstrap.digest(selected_node) == node_seed['sha256'],
                'Production Node identity mismatch')
        commands = run.get('commands')
        require(isinstance(commands, list) and len(commands) == 2, 'Incomplete production command pair')
        for command in commands:
            require(command.get('argv', [None])[0] == node_seed['path']
                    and command.get('node_sha256') == node_seed['sha256'], 'Production Node identity mismatch')
        require(PurePosixPath(run['workspace']).as_posix() == run['workspace']
                and not PurePosixPath(run['workspace']).is_absolute()
                and '..' not in PurePosixPath(run['workspace']).parts, 'Invalid production workspace identity')
        return root / run['workspace']
    require(run.get('execution_kind') is None, 'Unknown production execution kind')
    require(isinstance(run, dict) and run.get('package') == package,
            'Production package selection mismatch')
    name = run.get('workspace')
    require(isinstance(name, str) and name and '\\' not in name,
            'Invalid production workspace identity')
    relative = PurePosixPath(name)
    require(not relative.is_absolute() and '..' not in relative.parts and relative.as_posix() == name,
            'Invalid production workspace identity')
    workspace = root / name
    require(root.resolve() in workspace.resolve().parents, 'Production workspace escapes evidence root')
    directory = workspace.parent
    compiler, runner = directory / 'seeds/flatc.js', directory / 'seeds/airship.js'
    for path, expected in ((compiler, tools[bootstrap.PRODUCTS[0]]),
                           (runner, tools[bootstrap.PRODUCTS[1]])):
        file_reference(root, {'path': path.relative_to(root).as_posix(), 'sha256': expected})
    node = Path(node_seed['path'])
    require(node.is_absolute() and node.is_file()
            and sha(node_seed.get('sha256')) and bootstrap.digest(node) == node_seed['sha256'],
            'Production Node identity mismatch')
    commands = run.get('commands')
    require(isinstance(commands, list) and len(commands) == 2, 'Incomplete production command pair')
    for operation, command in zip(('install', 'run'), commands):
        expected = [str(node), str(runner), '--use-links', '-f', str(workspace / 'packages' / package),
                    '--flatc-runtime', 'flat', operation, 'test', '-t', 'es6']
        if operation == 'install':
            expected += ['--flatc-script', str(compiler)]
        require(isinstance(command, dict) and command.get('argv') == expected,
                'Production command selection mismatch')
    return workspace


def validate_incremental_run(root, run, workspace, inventory, compiler_sha):
    commands = run.get('commands')
    require(isinstance(commands, list) and len(commands) == 2, 'Incomplete incremental command pair')
    for index, command in enumerate(commands):
        require(command.get('log_reference') == command.get('log')
                and command.get('admission_reference') == command.get('journal'), 'Incremental reference aliases changed')
        seed = {'path': command.get('argv', [None])[0], 'sha256': command.get('node_sha256')}
        admissions = validate_product_command(root, command, 0, seed)
        require(command.get('cwd') == str(workspace / 'packages/Flat-Core'), 'Incremental cwd mismatch')
        if index == 0:
            require(command['tool']['sha256'] == compiler_sha, 'Incremental compiler bytes mismatch')
            require(isinstance(admissions, list), 'Invalid incremental admissions')
            require(type(command.get('compiler_invocations')) is int and command['compiler_invocations'] == 1
                    and len(command.get('persistent', [])) == 1, 'Missing incremental invocation count')
            require(command.get('compiler_argv') == command['argv'], 'Incremental compiler argv changed')
        else:
            generated = {str(workspace / 'packages/Flat-Core/dist' / name): digest
                         for name, digest in inventory.items() if PurePosixPath(name).name == 'test.js'}
            require(len(command['argv']) == 2 and command['argv'][1] in generated
                    and command['tool']['sha256'] == generated[command['argv'][1]],
                    'Unbound incremental generated runner')
            require(command['tool']['path'] == command['argv'][1][len(str(root)) + 1:],
                    'Incremental runner tool reference mismatch')
        log = root / command['log_reference']['path']
        require(command.get('persistent') == persistent_records(log, compiler_sha), 'Persistent observations changed')
        require(command.get('diagnostic_batches') == diagnostic_batches(log, workspace, command['persistent']),
                'Diagnostic observations changed')
        require(command.get('compiler_sessions') == compiler_session_records(log), 'Session observations changed')
        require(command.get('lifecycle') == lifecycle_records(log), 'Lifecycle observations changed')


def validate_production_run(root, run, compiler_sha):
    """Re-read pinned artifacts and logs; serialized observations are not authority."""
    require(isinstance(run, dict) and run.get('status') == 'passed', 'Incomplete production run')
    file_reference(root, run['source_receipt'])
    require(PurePosixPath(run['source_receipt']['path']).name == 'observed-sources.json',
            'Production source reference must identify observed inventory')
    sources = hash_manifest(read_json(root / run['source_receipt']['path']), 'production source')
    require(fingerprint(sources) == run.get('sources_sha256'), 'Stale production source inventory')
    workspace_name = PurePosixPath(run.get('workspace', ''))
    require(workspace_name.parts and not workspace_name.is_absolute() and '..' not in workspace_name.parts,
            'Invalid production workspace identity')
    workspace = root.joinpath(*workspace_name.parts)
    require(root.resolve() in workspace.resolve().parents, 'Production workspace escapes evidence root')
    inventory = hash_manifest(run.get('artifacts'), 'production artifact')
    require(any(PurePosixPath(name).name == 'test.js' for name in inventory),
            'Missing generated production test artifact')
    artifacts = run.get('artifact_references')
    require(isinstance(artifacts, dict) and set(artifacts) == set(inventory),
            'Incomplete retained artifacts')
    for name, reference in artifacts.items():
        file_reference(root, reference)
        require(reference['sha256'] == run['artifacts'][name], 'Production artifacts changed')
    if run.get('execution_kind') == 'ordinary-stage2-cli':
        validate_incremental_run(root, run, workspace, inventory, compiler_sha)
        return sources
    require(run.get('execution_kind') is None, 'Unknown production execution kind')
    commands = run.get('commands')
    require(isinstance(commands, list) and len(commands) == 2, 'Incomplete production command pair')
    require('install' in commands[0].get('argv', []) and 'run' in commands[1].get('argv', []),
            'Production install/run order mismatch')
    for command in commands:
        file_reference(root, command['log_reference'])
        file_reference(root, command['admission_reference'])
        log = root / command['log_reference']['path']
        journal = root / command['admission_reference']['path']
        require(type(command.get('admission_offset')) is int and command['admission_offset'] >= 0
                and type(command.get('admission_count')) is int and command['admission_count'] > 0,
                'Invalid production admission boundary')
        admissions = admission_records(journal, command['admission_offset'])
        require(len(admissions) == command['admission_count'], 'Production admission boundary mismatch')
        require(len(admissions) == command['admission_count'] and admissions,
                'Missing production admissions')
        require(command.get('status') == 'passed' and type(command.get('exit_status')) is int
                and command['exit_status'] == 0,
                'Production command did not complete')
        command_cleanup(root, command)
        argv = command.get('argv')
        require(isinstance(argv, list) and all(isinstance(item, str) for item in argv)
                and ('install' in argv or 'run' in argv), 'Missing admitted production argv')
        if 'install' in argv:
            require('--flatc-script' in argv, 'Missing explicit compiler selection')
            index = argv.index('--flatc-script')
            require(index + 1 < len(argv), 'Missing explicit compiler identity')
            compiler = argv[index + 1]
            count = sum(compiler in record.get('args', []) for record in admissions)
            expected_compiler_argv = next(([argv[0]] + record['args'] for record in admissions
                                           if compiler in record.get('args', [])), None)
            require(command.get('compiler_argv') == expected_compiler_argv,
                    'Compiler invocation arguments changed')
            require(all(record.get('command') == argv[0] for record in admissions
                        if compiler in record.get('args', [])), 'Compiler admitted through different Node')
            require(type(command.get('compiler_invocations')) is int
                    and count == command['compiler_invocations'], 'Compiler admission count mismatch')
            require(len(command.get('compiler_sessions', [])) <= count,
                    'Compiler session count exceeds actual admissions')
        else:
            generated = {str(workspace / 'packages' / run['package'] / 'dist' / name)
                         for name in inventory if PurePosixPath(name).name == 'test.js'}
            require(any(record.get('command') == argv[0] and record.get('args')
                        and record['args'][0] in generated for record in admissions),
                    'Missing generated suite execution admission')
        if run.get('package') == 'Airship' and 'run' in argv:
            validate_airship_fixtures(root, command, workspace, run['artifacts'], sources, admissions)
        else:
            require(not any(name.startswith('fixture_') for name in command),
                    'Fixture policy outside Airship run')
        require(command.get('persistent') == persistent_records(log, compiler_sha), 'Persistent observations changed')
        require(command.get('diagnostic_batches') == diagnostic_batches(log, workspace, command['persistent']),
                'Diagnostic observations changed')
        require(command.get('compiler_sessions') == compiler_session_records(log), 'Session observations changed')
        require(command.get('lifecycle') == lifecycle_records(log), 'Lifecycle observations changed')
    return sources


def validate_invalidations(root, values, baseline, tools, node_seed):
    require(isinstance(values, dict) and set(values) == set(INVALIDATIONS), 'Incomplete production invalidations')
    for name in INVALIDATIONS:
        entry = values[name]
        require(isinstance(entry, dict), 'Missing invalidation observations')
        compiler_sha = entry.get('compiler_sha256')
        require(sha(compiler_sha), 'Missing invalidation compiler identity')
        if name != 'tool':
            require(compiler_sha == tools[bootstrap.PRODUCTS[0]], 'Non-tool invalidation changed compiler')
        manifests = {}
        for mode in ('baseline', 'retained_output', 'clean'):
            run = entry.get(mode)
            validate_production_selection(root, run, 'Flat-Core', tools, node_seed)
            selected = tools[bootstrap.PRODUCTS[0]] if mode == 'baseline' else compiler_sha
            manifests[mode] = validate_production_run(root, run, selected)
        require(manifests['baseline'] == baseline, 'Invalidation baseline source mismatch')
        require(manifests['retained_output'] == manifests['clean'] == entry.get('source_manifest'),
                'Invalidation comparison input mismatch')
        before = entry['baseline']['commands'][0]
        after = entry['retained_output']['commands'][0]
        rebuilt = entry['clean']['commands'][0]
        require(before.get('execution_kind') is None, 'Unknown incremental command kind')
        old, new = before['argv'], after['argv']
        source_context = {'workspace': root / entry['retained_output']['workspace'],
                          'directory': (root / entry['retained_output']['workspace']).parent,
                          'compiler': Path(new[1])}
        clean_context = {'workspace': root / entry['clean']['workspace'],
                         'directory': (root / entry['clean']['workspace']).parent,
                         'compiler': Path(rebuilt['argv'][1])}
        require(incremental_arguments(new, source_context, clean_context) == rebuilt['argv'],
                'Invalidation clean compiler options differ')
        old_records, new_records = before['persistent'], after['persistent']
        require(old_records and new_records and old_records[-1]['inputFingerprint'] != new_records[-1]['inputFingerprint'],
                'Invalidation did not change actual input fingerprint')
        if name == 'dependency':
            mutation = entry.get('mutation')
            require(isinstance(mutation, str) and mutation in baseline
                    and not mutation.startswith('packages/Flat-Core/') and mutation.endswith('.flat'),
                    'Invalid dependency mutation')
            changed = manifests['retained_output']
            require(set(changed) == set(baseline)
                    and {key for key in baseline if baseline[key] != changed[key]} == {mutation},
                    'Dependency invalidation changed unrelated inputs')
            units = schema_log_records(root / before['log_reference']['path'], 'flat.compiler.invocation-source.v1')
            identity = str(root / entry['baseline']['workspace'] / mutation)
            require(any(unit.get('identity') == identity
                        and unit.get('contentFingerprint') == 'sha256:' + baseline[mutation]
                        for unit in units), 'Dependency mutation was not actually admitted')
            require(new == old, 'Dependency invalidation changed compiler options')
        else:
            require(manifests['retained_output'] == baseline, 'Configuration invalidation changed sources')
            if name in ('options', 'configuration'):
                aliases, expected = ((('-threads', '--threads', '-parallel'), '2') if name == 'options'
                                     else (('--max-artifact-bytes',), '67108865'))
                require(new == incremental_option(old, aliases, expected), 'Invalidation option change mismatch')
            else:
                old_tool, new_tool = root / before['tool']['path'], root / after['tool']['path']
                require(compiler_sha != tools[bootstrap.PRODUCTS[0]]
                        and new_tool.read_bytes() == old_tool.read_bytes() + b'\n// dogfood tool invalidation\n',
                        'Tool invalidation bytes mismatch')
                require(new == [new[0], str(new_tool)] + old[2:], 'Tool invalidation changed compiler options')
        left, right = (semantic_diagnostics(entry[mode]['commands']) for mode in ('retained_output', 'clean'))
        require(entry['retained_output']['artifacts'] == entry['clean']['artifacts']
                and left['complete'] and right['complete'] and left['records'] == right['records'],
                'Invalidation artifacts/diagnostics incomplete or unequal')


def validate_mutation_manifests(baseline, manifests, mutations):
    require(isinstance(mutations, dict) and set(mutations) == {'changed', 'removed'},
            'Missing exact production mutations')
    changed = mutation_path(mutations['changed'], 'Flat-Core', baseline)
    removed = mutation_path(mutations['removed'], 'Flat-Core', baseline)
    require(changed != removed, 'Changed and removed mutation identities collide')
    require(manifests['cold'] == manifests['warm'] == baseline, 'Cold/warm source mismatch')
    require({key for key in baseline if manifests['changed'].get(key) != baseline[key]} == {changed}
            and set(manifests['changed']) == set(baseline), 'Changed scenario modified unrelated inputs')
    require(set(baseline) - set(manifests['removed']) == {removed}, 'Removed scenario modified unrelated inputs')
    require(set(manifests['removed']) < set(baseline)
            and all(baseline[key] == digest for key, digest in manifests['removed'].items()),
            'Removed scenario did not remove only selected source input')


def validate_production_evidence(path, value, stages, scaling_plan_sha=None, review_sha256=None):
    require(type(value.get('schema')) is int and value['schema'] == 2,
            'Unknown production evidence schema')
    tools = validate_bootstrap(stages, require_public=True, review_sha256=review_sha256)
    require(value.get('producer') == 'dogfood_acceptance.py' and value.get('tools') == tools,
            'Production evidence tool identity mismatch')
    require(value.get('source_manifest_sha256') == bootstrap.digest(stages / 'sources.json'),
            'Production evidence source snapshot mismatch')
    require(value.get('options') == {'target': 'es6', 'flatc_runtime': 'flat'}, 'Production options mismatch')
    root = path.parent.resolve()
    baseline = hash_manifest(read_json(stages / 'sources.json'), 'bootstrap source')
    node_seed = read_json(stages / 'seeds.json')['node']
    workloads = value.get('workloads', {})
    require(isinstance(workloads, dict) and set(workloads) == set(SCENARIOS), 'Incomplete production scenarios')
    manifests = {}
    for name in SCENARIOS:
        workload = workloads[name]
        require(isinstance(workload, dict), 'Missing production scenario observations')
        for mode in ('retained_output', 'clean'):
            validate_production_selection(root, workload.get(mode), 'Flat-Core', tools, node_seed)
        sources = validate_production_run(root, workload['retained_output'], tools[bootstrap.PRODUCTS[0]])
        clean = validate_production_run(root, workload['clean'], tools[bootstrap.PRODUCTS[0]])
        require(sources == clean == workload.get('source_manifest'), 'Comparison input mismatch')
        left_records = [record for command in workload['retained_output']['commands']
                        for record in command.get('persistent', [])]
        require(left_records and incremental_observation(name, workload['retained_output'])['proven'],
                'Missing actual incremental scenario observation')
        if name == 'warm':
            cold_records = [record for command in workloads['cold']['retained_output']['commands']
                            for record in command.get('persistent', [])]
            require(cold_records and left_records[-1]['inputFingerprint'] == cold_records[-1]['inputFingerprint'],
                    'Warm input fingerprint differs from cold')
        require(workload['retained_output']['artifacts'] == workload['clean']['artifacts'],
                'Incremental clean artifact comparison mismatch')
        left, right = (semantic_diagnostics(workload[mode]['commands']) for mode in ('retained_output', 'clean'))
        require(left['complete'] and right['complete'] and left['records'] == right['records'],
                'Incremental clean diagnostics incomplete or unequal')
        manifests[name] = sources
    validate_mutation_manifests(baseline, manifests, value.get('mutations'))
    validate_public_workloads(root, value.get('public_workloads'), baseline, tools, node_seed)
    validate_invalidations(root, value.get('invalidations'), baseline, tools, node_seed)
    suites = value.get('stage2_suites', {})
    require(isinstance(suites, dict) and set(suites) == set(SUITES), 'Missing production suite')
    for suite, run in suites.items():
        validate_production_selection(root, run, suite, tools, node_seed)
        require(validate_production_run(root, run, tools[bootstrap.PRODUCTS[0]]) == baseline,
                'Suite source snapshot mismatch')
    if value.get('ordinary_products') is not None:
        validate_ordinary_products(root, value['ordinary_products'], baseline, tools,
                                   read_json(stages / 'seeds.json')['node'])
    driver = value.get('production_driver')
    if driver is not None:
        require(driver.get('driver', {}).get('sha256') == tools[bootstrap.PRODUCTS[2]],
                'Production driver differs from stage2 product bytes')
        validate_driver_evidence(root, driver, fingerprint(baseline), tools[bootstrap.PRODUCTS[0]])
    if value.get('scaling') is not None:
        require(sha(scaling_plan_sha) and value['scaling'].get('plan', {}).get('sha256') == scaling_plan_sha,
                'Scaling requires independently reviewed plan digest')
        require(driver is not None, 'Scaling requires pinned production driver')
        require(value['scaling'].get('compiler_sha256') == tools[bootstrap.PRODUCTS[0]],
                'Scaling ordinary baseline selected different compiler bytes')
        for tier in value['scaling'].get('tiers', []):
            ordinary = tier.get('ordinary', {})
            validate_production_selection(root, ordinary, tier.get('package'), tools, node_seed)
        validate_scaling(root, value['scaling'], value['source_manifest_sha256'], baseline, driver['driver']['sha256'])
    return dict(schema=2, evidence_sha256=bootstrap.digest(path),
                bootstrap_receipt_sha256=bootstrap.digest(stages / 'acceptance.json'),
                source_manifest_sha256=value['source_manifest_sha256'], tools=tools,
                **production_gates(value))


def watchdog_capability(path):
    """Validate F's observed fixture receipt without promoting its scope."""
    value = read_json(path)
    require(value.get('schema') == 1 and value.get('scope') == 'watchdog-fixture-only'
            and value.get('production_compiler_executed') is False,
            'Watchdog fixture receipt cannot claim production compiler execution')
    require(value.get('status') == 'observed' and value.get('children_reaped') is True
            and value.get('temporary_outputs_removed') is True, 'Incomplete watchdog capability observation')
    require(sha(value.get('python_sha256')) and isinstance(value.get('python'), str),
            'Missing watchdog interpreter identity')
    scenarios = value.get('scenarios')
    require(isinstance(scenarios, list) and len(scenarios) == 2
            and {item.get('scenario') for item in scenarios} == {'cancel', 'timeout'},
            'Both observed watchdog scenarios required')
    for scenario in scenarios:
        for name in ('ready_observed', 'wait_completed', 'child_reaped', 'process_group_absent',
                     'temporary_output_existed_before_cleanup', 'temporary_outputs_removed'):
            require(scenario.get(name) is True, 'Incomplete observed watchdog cleanup')
        require(scenario.get('termination_reason') == scenario['scenario']
                and type(scenario.get('pid')) is int and scenario['pid'] > 0
                and type(scenario.get('exit_code')) is int and scenario['exit_code'] != 0,
                'Invalid watchdog termination observation')
        require(isinstance(scenario.get('argv'), list) and bool(scenario['argv'])
                and scenario['argv'][0] == value['python'], 'Watchdog tool identity mismatch')
        file_reference(path.parent.resolve(), {'path': scenario.get('log'), 'sha256': scenario.get('log_sha256')})
    return {'scope': value['scope'], 'status': 'observed', 'receipt_sha256': bootstrap.digest(path),
            'observation': value, 'production_compiler_executed': False,
            'production_lifecycle_satisfied': False,
            'limitation': 'Watchdog cancellation/timeout capability only, not compiler-owned cancellation or artifact cleanup'}


def mutation_path(name, package, sources):
    prefix = f'packages/{package}/src/'
    require(isinstance(name, str) and name.startswith(prefix) and name in sources
            and '..' not in PurePosixPath(name).parts and name.endswith('.flat')
            and not name.endswith('_TestSuite.flat'),
            'Mutation must select an existing production package source, not a suite entrypoint')
    return name


def temporary_output_inventory(workspace):
    """Observe only compiler-owned staging names; never delete possible evidence.

    A terminal empty inventory proves absence, not that a temporary ever existed
    or that cancellation ran a finalizer. Those require owner-emitted events.
    Traversal includes ignored staging directories and excludes workspace links.
    """
    result = []
    visited = 0
    for directory, directories, files in os.walk(workspace / 'packages', followlinks=False):
        directories[:] = sorted(name for name in directories if not (Path(directory) / name).is_symlink()
                                and name not in ('.git', 'node_modules'))
        for name in directories + sorted(files):
            visited += 1
            require(visited <= MAX_REFERENCES, 'Temporary output traversal capacity exceeded')
            if ('.flat-tmp-' in name or '.flat-incremental.json.tmp-' in name
                    or name.endswith('.flat-incremental.json.lock') or name.endswith('.spool')):
                result.append((Path(directory) / name).relative_to(workspace).as_posix())
    return result


def command_cleanup(root, command):
    file_reference(root, command['cleanup_reference'])
    value = read_json(root / command['cleanup_reference']['path'])
    require(value.get('schema') == 'flat.dogfood.command-cleanup.v1'
            and value.get('scope') == 'production-command'
            and value.get('argv') == command['argv']
            and value.get('log_sha256') == command['log_reference']['sha256']
            and value.get('admissions_sha256') == command['admission_reference']['sha256'],
            'Unbound production cleanup observation')
    process = value.get('process')
    require(isinstance(process, dict) and type(process.get('pid')) is int and process['pid'] > 0
            and type(process.get('exit_code')) is int and process['exit_code'] == command['exit_status'],
            'Missing production process identity')
    for name in ('wait_completed', 'child_reaped', 'process_group_absent'):
        require(type(process.get(name)) is bool, 'Missing process cleanup measurement')
    require(process == command.get('process_cleanup')
            and value.get('temporary_outputs_after') == command.get('temporary_outputs_after')
            and isinstance(value.get('temporary_outputs_after'), list), 'Changed cleanup measurement')
    return value


def output_inventory(workspace, package):
    directory = workspace / 'packages' / package / 'dist'
    require(directory.is_dir(), 'Package install produced no dist directory')
    result = {}
    total = 0
    for path in sorted(directory.rglob('*')):
        require(not path.is_symlink(), 'Generated artifact symlink rejected')
        if path.is_file():
            if path.name.endswith('.flat-incremental.json'):
                continue  # retained separately: includes physical source identities
            total += path.stat().st_size
            require(total <= 256 * 1024 * 1024 and len(result) < MAX_REFERENCES,
                    'Generated artifact inventory capacity exceeded')
            result[path.relative_to(directory).as_posix()] = bootstrap.digest(path)
    require(bool(result), 'Package install produced no artifacts')
    return result


def scenario_sources(sources, workspace):
    # inventory excludes generated outputs and hashes content in bounded blocks.
    return {'packages/' + name: digest for name, digest in bootstrap.inventory(workspace / 'packages').items()}


def admission_records(path, offset=0):
    require(path.is_file() and path.stat().st_size <= 16 * 1024 * 1024,
            'Missing/oversized production admission journal')
    records = []
    with path.open() as source:
        source.seek(offset)
        for line in source:
            require(len(line) <= 1024 * 1024, 'Admission record exceeds capacity')
            record = json.loads(line)
            require(record.get('decision') == 'allow', 'Rejected production subprocess')
            require(Path(record.get('command', '')).name.lower() not in ('java', 'java.exe')
                    and not any(str(arg).endswith('.jar') for arg in record.get('args', [])),
                    'Java invocation rejected in production workload')
            records.append(record)
    return records


def create_production_workspace(repo, directory, sources, seeds, tools, stage2, budget):
    bootstrap.fresh_directory(directory)
    workspace = directory / 'workspace'
    bootstrap.snapshot(repo, workspace, sources, budget=budget)
    origin = read_json(stage2 / 'manifest.json')['source_origin']
    bootstrap.install_snapshot_origin(workspace, origin)
    for name in ('home', 'flat-home', 'seeds', 'logs'):
        (directory / name).mkdir()
    for package in sorted((workspace / 'packages').iterdir()):
        if package.is_dir() and (package / 'flat.json').is_file():
            link = directory / 'flat-home/packages/github.com/FlatLang' / package.name / '[link]'
            link.parent.mkdir(parents=True)
            link.symlink_to(package, target_is_directory=True)
    compiler = bootstrap.copy_seed(stage2 / 'workspace' / bootstrap.PRODUCTS[0],
                                   directory / 'seeds/flatc.js', tools[bootstrap.PRODUCTS[0]])
    runner = bootstrap.copy_seed(stage2 / 'workspace' / bootstrap.PRODUCTS[1],
                                 directory / 'seeds/airship.js', tools[bootstrap.PRODUCTS[1]])
    guard = bootstrap.copy_seed(Path(bootstrap.__file__).parent / 'stage_subprocess_guard.cjs',
                                directory / 'seeds/guard.cjs',
                                bootstrap.digest(Path(bootstrap.__file__).parent / 'stage_subprocess_guard.cjs'))
    journal = directory / 'admissions.jsonl'
    policy = {'stage': 2, 'root': str(workspace), 'node': str(seeds['node'][0]),
              'nodeSha256': seeds['node'][1], 'git': str(seeds['git'][0]), 'gitSha256': seeds['git'][1],
              'java': None, 'javaSha256': None, 'javaJar': None, 'javaJarSha256': None,
              'compiler': str(compiler), 'seed': str(compiler), 'seedSha256': bootstrap.digest(compiler),
              'compilerOutput': str(workspace / bootstrap.PRODUCTS[0]),
              'airship': str(runner), 'airshipSha256': bootstrap.digest(runner), 'journal': str(journal)}
    bootstrap.write_json(directory / 'policy.json', policy)
    env = {'HOME': str(directory / 'home'), 'FLAT_HOME': str(directory / 'flat-home'),
           'PATH': str(seeds['node'][0].parent), 'LANG': 'C.UTF-8', 'TZ': 'UTC', 'SOURCE_DATE_EPOCH': '0',
           'GIT_CONFIG_NOSYSTEM': '1', 'GIT_CONFIG_GLOBAL': os.devnull,
           'AIRSHIP_LOCK_FILE_ENABLED': 'false', 'NODE_NO_WARNINGS': '1',
           'FLAT_BOOTSTRAP_POLICY': str(directory / 'policy.json'), 'NODE_OPTIONS': '--require=' + str(guard)}
    return {'workspace': workspace, 'directory': directory, 'runner': runner, 'compiler': compiler,
            'journal': journal, 'env': env, 'commands': [], 'policy': policy,
            'bootstrap_stage': stage2, 'tools': dict(tools), 'source_manifest': dict(sources)}


def airship_fixture_policy(workspace, generated, policy):
    """Only the just-installed ordinary Airship test runner gets these fixtures."""
    dist = workspace / 'packages/Airship/dist'
    candidates = [dist / name for name in generated if PurePosixPath(name).name == 'test.js']
    require(len(candidates) == 1, 'Expected exactly one fresh Airship test runner for fixture inventory')
    runner = candidates[0]
    require(runner.is_file() and not runner.is_symlink() and runner.resolve() == runner
            and bootstrap.digest(runner) == generated[runner.relative_to(dist).as_posix()],
            'Fresh Airship test runner changed')
    require('fixtures' not in policy, 'Production default policy must not declare fixtures')
    return {**policy, 'fixtures': bootstrap.lifecycle_inventory(workspace, runner)}


def validate_airship_fixtures(root, command, workspace, generated, sources, admissions):
    for name in ('fixture_policy_reference', 'fixture_base_policy_reference', 'fixture_scope_reference'):
        file_reference(root, command[name])
    base = read_json(root / command['fixture_base_policy_reference']['path'])
    require(base.get('root') == str(workspace), 'Foreign Airship fixture policy workspace')
    expected = airship_fixture_policy(workspace, generated, base)
    require(read_json(root / command['fixture_policy_reference']['path']) == expected,
            'Changed production Airship fixture policy')
    require(sources.get(bootstrap.LIFECYCLE_SOURCE) == bootstrap.LIFECYCLE_SOURCE_SHA256,
            'Airship fixture source is not in pinned workload')
    entries = expected['fixtures']
    runner = entries[0]['runner']
    require(any(record.get('command') == command['argv'][0] and record.get('args') == [runner]
                for record in admissions), 'Missing exact fresh Airship fixture runner admission')
    scope = read_json(root / command['fixture_scope_reference']['path'])
    events = scope.get('events')
    require(scope.get('schema') == 'flat.stage.process-scope.v1'
            and scope.get('ownership') == 'external-command-group'
            and scope.get('scope_cancelled') is False
            and scope.get('fixtures_started') == len(entries) == scope.get('fixtures_finished')
            and scope.get('cleanup') == command['process_cleanup']
            and isinstance(events, list) and len(events) <= 4096
            and scope.get('frame_count') == len(events), 'Incomplete Airship fixture process scope')
    require(all(event.get('scope') == scope.get('scope') and type(event.get('pid')) is int
                and event['pid'] > 0 for event in events), 'Foreign Airship fixture scope')
    hellos = [event for event in events if event.get('kind') == 'hello']
    require(hellos and scope.get('hello_count') == len(hellos), 'Missing Airship fixture scope handshake')
    outcomes = [event for event in events if event.get('kind') != 'hello']
    require(len(outcomes) == 2 * len(entries), 'Unexpected Airship fixture event count')
    identities = set()
    for index, entry in enumerate(entries):
        start, end = outcomes[index * 2:index * 2 + 2]
        identity = (start.get('pid'), start.get('invocation'))
        require(start.get('kind') == 'fixture-start' and end.get('kind') == 'fixture-end'
                and start.get('fixture') == entry['id'] == end.get('fixture')
                and type(identity[1]) is int and identity not in identities
                and identity == (end.get('pid'), end.get('invocation'))
                and end.get('exit') == entry['outcome']['exit']
                and end.get('signal') == entry['outcome']['signal'],
                'Changed Airship fixture order/count/outcome')
        identities.add(identity)


def execute_production(context, package, seeds, budget, seconds):
    workspace, directory = context['workspace'], context['directory']
    generated = None
    for operation in ('install', 'run'):
        # Public Airship interface; do not call stage_command with Flat-Core,
        # because F deliberately limits that helper to its seven bootstrap suites.
        argv = [str(seeds['node'][0]), str(context['runner']), '--use-links', '-f',
                str(workspace / 'packages' / package), '--flatc-runtime', 'flat', operation, 'test', '-t', 'es6']
        if operation == 'install':
            argv += ['--flatc-script', str(context['compiler'])]
        require(bootstrap.digest(context['runner']) == context['policy']['airshipSha256']
                and bootstrap.digest(context['compiler']) == context['policy']['seedSha256']
                and bootstrap.digest(seeds['node'][0]) == seeds['node'][1], 'Production tool input changed')
        offset = context['journal'].stat().st_size if context['journal'].exists() else 0
        log = directory / f"logs/{len(context['commands']) + 1:03d}.log"
        command = {'argv': argv, 'status': 'running', 'log': str(log.relative_to(directory))}
        context['commands'].append(command)
        bootstrap.write_json(directory / 'commands.json', context['commands'])
        cleanup = {}
        command_env = context['env']
        if package == 'Airship' and operation == 'run':
            selected_policy = airship_fixture_policy(workspace, generated, context['policy'])
            policy_path = log.with_suffix('.policy.json')
            bootstrap.write_json(policy_path, selected_policy)
            command_env = {**command_env, 'FLAT_BOOTSTRAP_POLICY': str(policy_path)}
            reference = lambda path: {'path': path.relative_to(directory.parent).as_posix(),
                                      'sha256': bootstrap.digest(path)}
            command['fixture_policy_reference'] = reference(policy_path)
            command['fixture_base_policy_reference'] = reference(Path(context['env']['FLAT_BOOTSTRAP_POLICY']))
        try:
            bootstrap.run_command(argv, workspace / 'packages' / package, command_env, log,
                                  context['journal'], timeout=seconds, budget=budget,
                                  cleanup_observation=cleanup)
        finally:
            # Persist measured process termination even on a failed command; a
            # failed/partial run never becomes successful acceptance evidence.
            command['process_cleanup'] = cleanup
            command['temporary_outputs_after'] = temporary_output_inventory(workspace)
            bootstrap.write_json(directory / 'commands.json', context['commands'])
        records = admission_records(context['journal'], offset)
        if package == 'Airship' and operation == 'run':
            command['fixture_scope_reference'] = reference(log.with_suffix(log.suffix + '.scope.json'))
            validate_airship_fixtures(directory.parent, command, workspace, generated,
                                     scenario_sources(None, workspace), records)
        if operation == 'install':
            generated = output_inventory(workspace, package)
            # Empty compiler admissions are recorded as a possible install cache
            # skip, not asserted to be a CompilerPackageReuse hit.
            command['compiler_argv'] = next(([str(seeds['node'][0])] + record['args'] for record in records
                                               if str(context['compiler']) in record.get('args', [])), None)
            command['compiler_invocations'] = sum(str(context['compiler']) in record.get('args', [])
                                                  for record in records)
        else:
            selected = workspace / 'packages' / package / 'dist'
            tests = [Path(record['args'][0]) for record in records
                     if record.get('command') == str(seeds['node'][0]) and record.get('args')
                     and str(record['args'][0]).endswith('/test.js')]
            require(any(selected in test.parents and test.is_file()
                        and generated.get(test.relative_to(selected).as_posix()) == bootstrap.digest(test)
                        for test in tests), 'Run did not execute this install generated test.js')
        command.update(status='passed', exit_status=0, log_sha256=bootstrap.digest(log),
                       admissions_sha256=bootstrap.digest(context['journal']),
                       compiler_sessions=compiler_session_records(log),
                       persistent=persistent_records(log, context['policy']['seedSha256']))
        command['diagnostic_batches'] = diagnostic_batches(log, workspace, command['persistent'])
        command['lifecycle'] = lifecycle_records(log)
        retained_journal = log.with_suffix('.admissions.jsonl')
        shutil.copyfile(context['journal'], retained_journal)
        evidence_root = directory.parent
        command['log_reference'] = {'path': log.relative_to(evidence_root).as_posix(),
                                    'sha256': bootstrap.digest(log)}
        command['admission_reference'] = {'path': retained_journal.relative_to(evidence_root).as_posix(),
                                          'sha256': bootstrap.digest(retained_journal)}
        command['admission_offset'] = offset
        command['admission_count'] = len(records)
        cleanup_path = log.with_suffix('.cleanup.json')
        bootstrap.write_json(cleanup_path, {'schema': 'flat.dogfood.command-cleanup.v1',
            'scope': 'production-command', 'argv': argv, 'log_sha256': bootstrap.digest(log),
            'admissions_sha256': bootstrap.digest(retained_journal),
            'process': cleanup, 'temporary_outputs_after': command['temporary_outputs_after']})
        command['cleanup_reference'] = {'path': cleanup_path.relative_to(evidence_root).as_posix(),
                                         'sha256': bootstrap.digest(cleanup_path)}
        bootstrap.write_json(directory / 'commands.json', context['commands'])
    require(output_inventory(workspace, package) == generated, 'Execution changed generated artifacts')
    manifests = {}
    for path in sorted((workspace / 'packages' / package / 'dist').rglob('*.flat-incremental.json')):
        require(not path.is_symlink() and path.stat().st_size <= 4 * 1024 * 1024,
                'Invalid persistent manifest artifact')
        manifests[path.relative_to(workspace).as_posix()] = bootstrap.digest(path)
    observation = directory / ('observation-' + str(len(context['commands'])))
    observation.mkdir()
    bootstrap.write_json(observation / 'observed-sources.json', scenario_sources(None, workspace))
    artifact_references = {}
    for name, digest in generated.items():
        destination = observation / 'artifacts' / name
        destination.parent.mkdir(parents=True, exist_ok=True)
        shutil.copyfile(workspace / 'packages' / package / 'dist' / name, destination)
        require(bootstrap.digest(destination) == digest, 'Retained artifact changed during copy')
        artifact_references[name] = {'path': destination.relative_to(directory.parent).as_posix(), 'sha256': digest}
    source_receipt = observation / 'observed-sources.json'
    return {'status': 'passed', 'artifacts': generated, 'persistent_manifests': manifests,
            'package': package, 'workspace': workspace.relative_to(directory.parent).as_posix(),
            'source_receipt': {'path': source_receipt.relative_to(directory.parent).as_posix(),
                               'sha256': bootstrap.digest(source_receipt)},
            'artifact_references': artifact_references,
            'commands': list(context['commands'][-2:]),
            'sources_sha256': fingerprint(scenario_sources(None, workspace)),
            'runtime_observation': 'ordinary generated test.js exited zero; individual test assertions are in retained log',
            'summary': None, 'reuse_observed': None}


def schema_log_records(log, schema):
    require(log.is_file() and not log.is_symlink() and log.stat().st_size <= 100 * 1024 * 1024, 'Missing/oversized producer log')
    result = []
    def unique(pairs):
        value = {}
        for key, item in pairs:
            require(key not in value, 'Duplicate producer key')
            value[key] = item
        return value
    with log.open() as source:
        while True:
            line = source.readline(1024 * 1024 + 1)
            if not line:
                break
            require(len(line) <= 1024 * 1024, 'Producer line exceeds capacity')
            if not line.lstrip().startswith('{'):
                continue
            try:
                value = json.loads(line, object_pairs_hook=unique)
            except json.JSONDecodeError:
                require(schema not in line, 'Malformed producer record')
                continue
            if isinstance(value, dict):
                observed_schema = value.get('schema')
                family = schema.rsplit('.v', 1)[0] + '.v'
                require(not (isinstance(observed_schema, str) and observed_schema.startswith(family)
                            and observed_schema != schema), 'Unknown producer schema version')
            if isinstance(value, dict) and value.get('schema') == schema:
                capacity = MAX_REFERENCES if schema == 'flat.compiler.source-read-event.v1' else 4096
                require(len(result) < capacity, 'Producer record capacity exceeded')
                result.append(value)
    return result


def diagnostic_batches(log, workspace, persistent):
    records = schema_log_records(log, 'flat.compiler.diagnostics.v1')
    admitted = {value['inputFingerprint'] for value in persistent}
    for value in records:
        require(type(value.get('complete')) is bool and value.get('revision') in admitted,
                'Diagnostic revision is not bound to actual persistent input fingerprint')
        value['records'] = normalize_diagnostics(value.get('records'), workspace)
    return records


def lifecycle_records(log):
    records = schema_log_records(log, 'flat.compiler.lifecycle.v1')
    mandatory = ('concurrency', 'activeCapacity', 'retainedCapacity', 'peakActive', 'peakRetained',
                 'enteredCount', 'settledCount', 'activeAfter', 'inFlightAfter', 'retainedSourcesAfter',
                 'retainedCodeCharactersAfter', 'pendingArtifactSendsAfter')
    optional = ('cleanupWaitSamples', 'executorActiveAfter', 'executorQueuedAfter', 'unrelatedArtifactsBeforeFirstSettlement',
                'pendingArtifactSendsAtPressure', 'bufferedArtifactsAtPressure')
    for value in records:
        require(isinstance(value.get('scenario'), str) and bool(value['scenario']), 'Missing lifecycle scenario')
        for name in mandatory:
            require(type(value.get(name)) is int and value[name] >= 0, 'Invalid lifecycle measured count')
        for name in optional:
            require(name in value and (value[name] is None or type(value[name]) is int and value[name] >= 0),
                    'Invalid nullable lifecycle measurement')
        for name in ('artifactBackpressure', 'cancelled', 'cleanupSettled'):
            require(type(value.get(name)) is bool, 'Missing lifecycle measured state')
        require(isinstance(value.get('settlementOrder'), list)
                and all(isinstance(item, str) for item in value['settlementOrder']), 'Invalid settlement order')
        require(value.get('unobserved') == ['children_reaped', 'temporary_outputs_removed'],
                'Memory lifecycle test cannot claim subprocess/filesystem cleanup')
        require(value['concurrency'] > 0 and value['activeCapacity'] == value['concurrency']
                and (value['peakActive'] > 0 or value['scenario'] == 'queued-source-cancellation')
                and value['peakActive'] <= value['activeCapacity']
                and value['peakRetained'] <= value['retainedCapacity'], 'Observed lifecycle capacity violation')
        require(len(value['settlementOrder']) == value['settledCount']
                and len(set(value['settlementOrder'])) == len(value['settlementOrder']),
                'Incomplete lifecycle settlement order')
        if value['scenario'] in ('package-forward', 'package-reverse'):
            expected = ['Value0.flat', 'Value1.flat', 'Value2.flat', 'Value3.flat']
            if value['scenario'] == 'package-reverse':
                expected[:value['concurrency']] = reversed(expected[:value['concurrency']])
            require(value['settlementOrder'] == expected and not value['cancelled'],
                    'Unobserved controlled package settlement order')
        require(value['enteredCount'] == value['settledCount'] and value['cleanupSettled']
                and all(value[name] == 0 for name in ('activeAfter', 'inFlightAfter', 'retainedSourcesAfter',
                                                      'retainedCodeCharactersAfter', 'pendingArtifactSendsAfter')),
                'Observed lifecycle cleanup did not settle')
        require(all(value[name] in (None, 0) for name in ('executorActiveAfter', 'executorQueuedAfter')),
                'Observed executor cleanup did not settle')
    return records


def semantic_diagnostics(commands):
    """Prove empty success diagnostics from actual terminal sessions.

    Nonzero counts require the structured owning-domain diagnostic bridge; never
    parse range/code out of presentation text or equate missing output with [].
    Persistent hits currently lack that completeness marker, so remain unknown.
    """
    installs = [command for command in commands if command.get('compiler_invocations') is not None]
    batches = [value for command in installs for value in command.get('diagnostic_batches', [])]
    if not installs or any(type(command.get('compiler_invocations')) is not int
                           or command['compiler_invocations'] < 0 for command in installs):
        return {'complete': False, 'records': None, 'reason': 'Missing/invalid compiler invocation observations'}
    invocations = sum(command['compiler_invocations'] for command in installs)
    if batches and len(batches) == invocations and all(value['complete'] for value in batches):
        records = [record for batch in batches for record in batch['records']]
        return {'complete': True, 'records': records, 'fingerprint': fingerprint(records),
                'source': 'actual flat.compiler.diagnostics.v1 batches'}
    if batches:
        return {'complete': False, 'records': None,
                'reason': 'Incomplete or duplicate structured diagnostic batches'}
    sessions = [value for command in commands for value in command.get('compiler_sessions', [])]
    persistent = [value for command in installs for value in command.get('persistent', [])]
    if not sessions or len(sessions) != invocations or any(value['reused'] for value in persistent):
        return {'complete': False, 'records': None,
                'reason': 'Missing terminal diagnostics completeness for every admitted compiler invocation'}
    if any(value['diagnosticCount'] != 0 or value['cancelled'] for value in sessions):
        return {'complete': False, 'records': None,
                'reason': 'Nonempty diagnostics require structured semantic code/source/range records'}
    return {'complete': True, 'records': [], 'source': 'actual terminal session diagnosticCount=0',
            'fingerprint': fingerprint([])}


def normalize_diagnostics(records, workspace):
    """Compare actual typed diagnostics without parsing presentation text.

    Does not invent ranges, discard diagnostic order, strip messages, or rewrite
    arbitrary strings. Only known workspace prefixes in the source identity are
    normalized; completeness is checked against every admitted invocation.
    """
    require(isinstance(records, list) and len(records) <= 4096, 'Diagnostic record capacity exceeded')
    normalized = []
    for value in records:
        require(isinstance(value, dict) and set(value) == {'code', 'message', 'source', 'range'},
                'Structured diagnostic fields required')
        require(all(isinstance(value[name], str) and value[name] for name in ('code', 'message', 'source')),
                'Diagnostic semantic identity missing')
        source = value['source']
        path = Path(source)
        if path.is_absolute():
            try:
                source = path.relative_to(workspace).as_posix()
            except ValueError:
                raise ValueError('Diagnostic source outside known workspace')
        require('..' not in PurePosixPath(source).parts and not PurePosixPath(source).is_absolute(),
                'Diagnostic source must be stable relative identity')
        span = value['range']
        if span is not None:
            fields = {'startLine', 'startColumn', 'endLine', 'endColumn', 'startTokenIndex', 'endTokenIndex'}
            require(isinstance(span, dict) and set(span) == fields
                    and all(type(span[name]) is int for name in fields), 'Diagnostic range contract mismatch')
            require(min(span['startLine'], span['startColumn'], span['endLine'], span['endColumn']) > 0
                    and 0 <= span['startTokenIndex'] <= span['endTokenIndex']
                    and (span['startLine'], span['startColumn']) <= (span['endLine'], span['endColumn']),
                    'Invalid diagnostic source range')
        normalized.append(dict(value, source=source))
    return normalized


def persistent_records(log, expected_tool=None):
    require(log.is_file() and log.stat().st_size <= 100 * 1024 * 1024, 'Missing/oversized persistent log')
    result = []
    with log.open() as source:
        for line in source:
            require(len(line) <= 1024 * 1024, 'Persistent evidence line exceeds capacity')
            if not line.lstrip().startswith('{'):
                continue
            try:
                value = json.loads(line)
            except json.JSONDecodeError:
                continue
            if not isinstance(value, dict) or value.get('schema') != 'flat.compiler.incremental.v1':
                continue
            require(len(result) < 4096 and value.get('scope') == 'persistent-cli-package',
                    'Invalid persistent evidence scope/capacity')
            for name in ('reused', 'manifestPublished'):
                require(type(value.get(name)) is bool, 'Invalid persistent evidence state')
            for name in ('unchangedSources', 'changedSources', 'addedSources', 'removedSources'):
                require(type(value.get(name)) is int and value[name] >= 0, 'Invalid persistent source count')
            for name in ('toolFingerprint', 'inputFingerprint'):
                field = value.get(name, '')
                require(isinstance(field, str) and field.startswith('sha256:') and sha(field[7:]),
                        'Invalid persistent fingerprint')
            require(expected_tool is None or value['toolFingerprint'] == 'sha256:' + expected_tool,
                    'Persistent evidence selected different compiler bytes')
            require(value.get('reason') in ('exact-hit', 'cold', 'configuration-changed', 'closure-changed', 'output-changed'),
                    'Unknown persistent invalidation reason')
            result.append(value)
    return result


def incremental_observation(name, measured):
    records = [record for command in measured.get('commands', []) for record in command.get('persistent', [])]
    if not records:
        return {'proven': False, 'blocker': 'No actual persistent CLI records emitted'}
    if name == 'warm':
        proven = all(value['reused'] and value['reason'] == 'exact-hit'
                     and not value['manifestPublished']
                     and all(value[key] == 0 for key in ('changedSources', 'addedSources', 'removedSources'))
                     for value in records)
    elif name == 'cold':
        proven = all(not value['reused'] and value['reason'] == 'cold'
                     and value['manifestPublished'] for value in records)
    elif name in ('changed', 'removed', 'dependency'):
        key = 'removedSources' if name == 'removed' else 'changedSources'
        proven = any(not value['reused'] and value['reason'] == 'closure-changed'
                     and value['manifestPublished'] and value[key] > 0 for value in records)
    elif name in ('options', 'configuration', 'tool'):
        proven = any(not value['reused'] and value['reason'] == 'configuration-changed'
                     and value['manifestPublished'] for value in records)
    else:
        raise ValueError('Unknown incremental workload: ' + name)
    return {'proven': proven, 'scope': 'persistent-cli-package', 'records': records}


def incremental_arguments(arguments, source_context, destination_context):
    """Relocate only known workspace/tool roots; preserve all admitted CLI options."""
    result = []
    roots = ((str(source_context['workspace']), str(destination_context['workspace'])),
             (str(source_context['compiler']), str(destination_context['compiler'])),
             (str(source_context['directory']), str(destination_context['directory'])))
    for argument in arguments:
        for old, new in roots:
            if argument == old or argument.startswith(old + os.sep):
                argument = new + argument[len(old):]
                break
        result.append(argument)
    return result


def mutate_incremental_source(workspace, relative, remove=False):
    identity = PurePosixPath(relative)
    require(not identity.is_absolute() and '..' not in identity.parts and identity.parts,
            'Incremental mutation requires a relative source identity')
    path = workspace / relative
    require(path.is_file() and not path.is_symlink() and relative.endswith('.flat'),
            'Incremental mutation requires a production Flat source')
    if remove:
        path.unlink()
    else:
        with path.open('ab') as stream:
            stream.write(b'\n// dogfood acceptance changed-input invalidation probe\n')


def execute_incremental_cli(context, arguments, seeds, budget, seconds):
    """Replay the ordinary Airship-admitted stage2 CLI, not another install skip.

    Compilation and generated execution retain independent admission/log records.
    Artifact/source observations are immutable copies, including on later mutation.
    """
    workspace, directory = context['workspace'], context['directory']
    root = directory.parent
    require(arguments[:2] == [str(seeds['node'][0]), str(context['compiler'])],
            'Incremental replay must select the admitted stage2 compiler')
    require(bootstrap.digest(context['compiler']) == context['policy']['seedSha256'],
            'Incremental compiler identity changed')
    commands = []
    compiled_artifacts = None
    for argv in (arguments, None):
        if argv is None:
            compiled_artifacts = output_inventory(workspace, 'Flat-Core')
            tests = sorted((workspace / 'packages/Flat-Core/dist').rglob('test.js'))
            require(len(tests) == 1, 'Incremental generated runner is ambiguous')
            argv = [str(seeds['node'][0]), str(tests[0])]
        raw = product_command(context, argv, workspace / 'packages/Flat-Core', seeds, budget, seconds)
        require(raw['exit_status'] == 0, 'Incremental CLI/execution failed; see retained product command log')
        log = root / raw['log']['path']
        persistent = persistent_records(log, context['policy']['seedSha256'])
        command = dict(raw, status='passed', log_reference=raw['log'],
                       admission_reference=raw['journal'], persistent=persistent,
                       compiler_sessions=compiler_session_records(log), lifecycle=lifecycle_records(log))
        if not commands:
            observed = persistent_records(log, context['policy']['seedSha256'])
            require(len(observed) == 1, 'Incremental CLI must emit one actual persistent observation')
            command.update(compiler_invocations=1, compiler_argv=list(argv))
        command['diagnostic_batches'] = diagnostic_batches(log, workspace, persistent)
        commands.append(command)
    generated = output_inventory(workspace, 'Flat-Core')
    require(generated == compiled_artifacts, 'Incremental execution changed generated artifacts')
    ordinal = len(context['product_commands'])
    observation = directory / ('incremental-observation-' + str(ordinal))
    observation.mkdir()
    sources = scenario_sources(None, workspace)
    bootstrap.write_json(observation / 'observed-sources.json', sources)
    reference = lambda path: {'path': path.relative_to(root).as_posix(), 'sha256': bootstrap.digest(path)}
    artifacts = {}
    for name, digest in generated.items():
        destination = observation / 'artifacts' / name
        destination.parent.mkdir(parents=True, exist_ok=True)
        shutil.copyfile(workspace / 'packages/Flat-Core/dist' / name, destination)
        require(bootstrap.digest(destination) == digest, 'Incremental artifact changed during copy')
        artifacts[name] = reference(destination)
    return {'status': 'passed', 'execution_kind': 'ordinary-stage2-cli', 'package': 'Flat-Core',
            'workspace': workspace.relative_to(root).as_posix(), 'commands': commands,
            'source_receipt': reference(observation / 'observed-sources.json'),
            'sources_sha256': fingerprint(sources), 'artifacts': generated,
            'artifact_references': artifacts,
            'persistent_manifests': {path.relative_to(workspace).as_posix(): bootstrap.digest(path)
                for path in sorted((workspace / 'packages/Flat-Core/dist').rglob('*.flat-incremental.json'))}}


def incremental_option(arguments, aliases, value):
    result, index = [], 0
    while index < len(arguments):
        if arguments[index] in aliases:
            require(index + 1 < len(arguments), 'Malformed incremental option')
            index += 2
        else:
            result.append(arguments[index])
            index += 1
    return result + [aliases[0], value]


def incremental_tool(context, changed=False):
    """Private mutable workload tool, never the immutable reviewed seed itself."""
    tool = context['directory'] / ('seeds/incremental-tool-changed.js' if changed
                                   else 'seeds/incremental-tool.js')
    original = context['directory'] / 'seeds/flatc.js'
    tool.write_bytes(original.read_bytes() + (b'\n// dogfood tool invalidation\n' if changed else b''))
    tool.chmod(0o444)
    context['compiler'] = tool
    context['policy'].update(compiler=str(tool), seed=str(tool), seedSha256=bootstrap.digest(tool))
    bootstrap.write_json(context['directory'] / 'policy.json', context['policy'])


def run_incremental_invalidations(repo, output, stages, sources, seeds, tools, budget, seconds,
                                  source_context, admitted, receipt):
    """Independent baseline/change/clean triples prevent cross-case contamination.

    Configuration here is an effective compiler resource configuration, not a
    cosmetic flat.json edit that the compiler cannot observe. Dependency identity
    comes from the actual emitted source closure, never guessed package reachability.
    """
    for name in ('dependency', 'options', 'configuration', 'tool'):
        retained = create_production_workspace(repo, output / ('invalidate-' + name), sources,
                                               seeds, tools, stages / 'stage2', budget)
        clean = create_production_workspace(repo, output / ('invalidate-clean-' + name), sources,
                                            seeds, tools, stages / 'stage2', budget)
        arguments = incremental_arguments(admitted, source_context, retained)
        if name == 'options':
            arguments = incremental_option(arguments, ('-threads', '--threads', '-parallel'), '1')
        if name == 'configuration':
            arguments = incremental_option(arguments, ('--max-artifact-bytes',), '67108864')
        if name == 'tool':
            old_tool = str(retained['compiler'])
            incremental_tool(retained)
            arguments = [str(retained['compiler']) if arg == old_tool else arg for arg in arguments]
        entry = {'status': 'running', 'scope': 'ordinary-stage2-cli',
                 'workspace': retained['workspace'].relative_to(output).as_posix()}
        receipt['invalidations'][name] = entry
        bootstrap.write_json(output / 'production-evidence.json', receipt)
        baseline = execute_incremental_cli(retained, arguments, seeds, budget, seconds)
        entry['baseline'] = baseline
        bootstrap.write_json(output / 'production-evidence.json', receipt)
        mutation = None
        if name == 'dependency':
            log = output / baseline['commands'][0]['log_reference']['path']
            units = schema_log_records(log, 'flat.compiler.invocation-source.v1')
            candidates = []
            for unit in units:
                identity = Path(unit['identity'])
                try:
                    relative = identity.relative_to(retained['workspace']).as_posix()
                except ValueError:
                    continue
                if (relative in sources and relative.endswith('.flat')
                        and not relative.startswith('packages/Flat-Core/')):
                    candidates.append(relative)
            require(candidates, 'No actually admitted dependency source available for invalidation')
            mutation = sorted(set(candidates))[0]
            mutate_incremental_source(retained['workspace'], mutation)
            mutate_incremental_source(clean['workspace'], mutation)
        elif name == 'options':
            arguments = incremental_option(arguments, ('-threads', '--threads', '-parallel'), '2')
        elif name == 'configuration':
            arguments = incremental_option(arguments, ('--max-artifact-bytes',), '67108865')
        else:
            old_tool = str(retained['compiler'])
            incremental_tool(retained, changed=True)
            incremental_tool(clean, changed=True)
            arguments = [str(retained['compiler']) if arg == old_tool else arg for arg in arguments]
        expected = scenario_sources(None, retained['workspace'])
        require(scenario_sources(None, clean['workspace']) == expected, 'Invalidation clean inputs differ')
        entry.update(mutation=mutation, source_manifest=expected,
                     compiler_sha256=retained['policy']['seedSha256'],
                     configuration_scope='effective compiler CLI configuration')
        measured = execute_incremental_cli(retained, arguments, seeds, budget, seconds)
        entry['retained_output'] = measured
        bootstrap.write_json(output / 'production-evidence.json', receipt)
        rebuilt = execute_incremental_cli(clean, incremental_arguments(arguments, retained, clean),
                                          seeds, budget, seconds)
        entry.update(compare_incremental_workload(name, measured, rebuilt, expected))
        require(scenario_sources(None, retained['workspace']) == expected,
                'Invalidation workload mutated production inputs')
        bootstrap.write_json(output / 'production-evidence.json', receipt)


def compare_incremental_workload(name, measured, rebuilt, sources):
    require(measured['sources_sha256'] == rebuilt['sources_sha256'] == fingerprint(sources),
            name + ': comparison inputs differ')
    require(measured['artifacts'] == rebuilt['artifacts'], name + ': retained-output/clean artifacts differ')
    left, right = (semantic_diagnostics(run['commands']) for run in (measured, rebuilt))
    observation = incremental_observation(name, measured)
    return {'status': 'passed', 'retained_output': measured, 'clean': rebuilt,
            'source_manifest': sources, 'incremental_observation': observation,
            'incremental_reuse_proven': observation['proven'],
            'diagnostics': {'retained_output': left, 'clean': right},
            'diagnostic_equivalence_proven': left['complete'] and right['complete']
                and left['records'] == right['records']}


def compiler_session_records(log):
    """Read actual CompilerEvidence.render lines, not caller-authored booleans."""
    require(log.is_file() and log.stat().st_size <= 100 * 1024 * 1024, 'Missing/oversized compiler evidence log')
    result = []
    with log.open(errors='strict') as source:
        for line in source:
            require(len(line) <= 1024 * 1024, 'Compiler evidence line capacity exceeded')
            if not line.lstrip().startswith('{'):
                continue
            try:
                value = json.loads(line)
            except json.JSONDecodeError:
                continue  # ordinary diagnostic text is not evidence
            if not isinstance(value, dict) or value.get('schema') != 'flat.compiler.session.v1':
                continue
            require(len(result) < 4096, 'Compiler session evidence capacity exceeded')
            for name in ('sourceCount', 'artifactCount', 'diagnosticCount', 'peakActiveSources',
                         'peakRetainedSources', 'peakActiveSourceReads', 'peakRetainedSourceReads'):
                require(type(value.get(name)) is int and value[name] >= 0, 'Invalid actual compiler session count')
            for name in ('cancelled', 'cleanupSettled'):
                require(type(value.get(name)) is bool, 'Invalid actual compiler session lifecycle')
            reuse = value.get('reuse')
            require(isinstance(reuse, dict) and reuse.get('scope') == 'exact-in-process-package'
                    and type(reuse.get('reused')) is bool, 'Unknown compiler reuse evidence scope')
            for name in ('unchangedSources', 'changedSources', 'addedSources', 'removedSources'):
                require(type(reuse.get(name)) is int and reuse[name] >= 0, 'Invalid actual reuse count')
            require(isinstance(reuse.get('reason'), str) and isinstance(reuse.get('toolFingerprint'), str),
                    'Missing actual reuse identity')
            result.append(value)
    return result


def read_concurrency_probe(context, package, seeds, budget, seconds):
    """Replay admitted real compiler argv, changing only public -threads selection."""
    compiler_argv = next((item.get('compiler_argv') for item in reversed(context['commands'])
                          if item.get('compiler_argv')), None)
    require(compiler_argv is not None, 'Cannot measure read concurrency without an observed compiler invocation')
    original = output_inventory(context['workspace'], package)
    results = []
    for count in (1, 2):
        if context.get('policy'):
            require(bootstrap.digest(context['compiler']) == context['policy']['seedSha256']
                    and bootstrap.digest(seeds['node'][0]) == seeds['node'][1],
                    'Read-concurrency probe tool identity changed')
        argv = list(compiler_argv)
        # The CLI documents these aliases; no invented scheduler options.
        index = 2
        while index < len(argv):
            if argv[index] in ('-threads', '--threads', '-parallel'):
                require(index + 1 < len(argv), 'Malformed admitted compiler thread argv')
                del argv[index:index + 2]
            else:
                index += 1
        argv += ['-threads', str(count)]
        log = context['directory'] / f'logs/read-concurrency-{count}.log'
        bootstrap.run_command(argv, context['workspace'] / 'packages' / package,
                              context['env'], log, context['journal'], timeout=seconds, budget=budget)
        admission_records(context['journal'])
        actual = output_inventory(context['workspace'], package)
        require(actual == original, 'Read-concurrency selection changed generated artifact bytes')
        results.append({'threads': count, 'argv': argv, 'exit_status': 0, 'artifacts': actual,
                        'log_sha256': bootstrap.digest(log), 'controlled_completion_order': None,
                        'observed_read_peaks': None,
                        'limitation': 'CLI option selection only; overlap and reverse schedules are not observed'})
    return results


def scaling_plan(path, expected, source_sha):
    value = bootstrap.reviewed_json(path, expected, 'Scaling plan')
    require(isinstance(value, dict) and value.get('schema') == 'flat.dogfood.scaling-plan.v1'
            and sha(source_sha) and value.get('source_manifest_sha256') == source_sha, 'Stale scaling plan')
    tiers = value.get('tiers')
    require(isinstance(tiers, list) and 3 <= len(tiers) <= 8
            and all(isinstance(tier, dict) for tier in tiers), 'Three distinct production tiers required')
    require(all(isinstance(tier.get('package'), str) for tier in tiers)
            and len({tier['package'] for tier in tiers}) == len(tiers), 'Three distinct production tiers required')
    for tier in tiers:
        require(tier.get('package') in ('Flat-Core',) + SUITES, 'Unknown production scaling package')
        for name in ('min_source_bytes', 'min_sources', 'max_rss_bytes',
                     'max_retained_sources', 'max_retained_source_bytes'):
            require(type(tier.get(name)) is int and tier[name] > 0, 'Explicit positive scaling budgets required')
    for name in ('max_rss_growth_numerator', 'max_rss_growth_denominator', 'min_input_growth_factor'):
        require(type(value.get(name)) is int and value[name] > 0, 'Explicit scaling growth budget required')
    require(value['min_input_growth_factor'] >= 2, 'Scaling input tiers must materially grow')
    require(all(right['min_source_bytes'] >= left['min_source_bytes'] * value['min_input_growth_factor']
                for left, right in zip(tiers, tiers[1:])), 'Predeclared scaling tiers must materially grow')
    timer = value.get('timer', {})
    require(isinstance(timer, dict) and timer.get('kind') in ('bsd-time', 'gnu-time') and isinstance(timer.get('path'), str)
            and Path(timer['path']).is_absolute() and sha(timer.get('sha256')), 'Pinned RSS timer required')
    return value


def rss_bytes(path, kind):
    require(kind in ('bsd-time', 'gnu-time'), 'Unknown RSS timer kind')
    require(path.is_file() and not path.is_symlink() and path.stat().st_size <= 65536, 'Missing/oversized RSS observation')
    text = path.read_text()
    matches = re.findall(r'^\s*(\d+)\s+maximum resident set size\s*$', text, re.M) if kind == 'bsd-time' else re.findall(r'^flat-maxrss-kib=(\d+)\s*$', text, re.M)
    require(len(matches) == 1 and int(matches[0]) > 0, 'Missing/ambiguous measured maximum RSS')
    return int(matches[0]) * (1024 if kind == 'gnu-time' else 1)


def driver_source_inputs(log, workspace, package, root):
    result = {}
    for event in schema_log_records(log, 'flat.compiler.source-read-event.v1'):
        if event['transition'] != 'admitted':
            continue
        source = Path(event['source'])
        if not source.is_absolute():
            source = workspace / 'packages' / package / source
        require(source.is_file() and not source.is_symlink() and workspace in source.resolve().parents,
                'Driver source outside production workspace')
        result[source.relative_to(workspace).as_posix()] = {'sha256': bootstrap.digest(source),
            'bytes': source.stat().st_size, 'reference': {
                'path': source.relative_to(root).as_posix(), 'sha256': bootstrap.digest(source)}}
    return result


def scaling_retention(log, inputs, workspace, package, report):
    """Measure the accepted driver read window, not total compiler heap retention.

    Byte counts are exact source-file bytes held by the observed window. RSS
    independently measures the timer child process high-water mark. Neither is
    an assertion that syntax/semantic graphs have been released.
    """
    require(isinstance(inputs, dict) and inputs, 'Missing scaling retention input inventory')
    capacity = report.get('capacity')
    require(type(capacity) is int and capacity > 0, 'Missing scaling retention capacity')
    retained = {}
    admitted = consumed = peak_count = peak_bytes = 0
    events = schema_log_records(log, 'flat.compiler.source-read-event.v1')
    require(events, 'Missing scaling retention observations')
    for event in events:
        source = Path(event['source'])
        if not source.is_absolute():
            source = workspace / 'packages' / package / source
        require(workspace.resolve() in source.resolve().parents, 'Scaling retention source outside workspace')
        name = source.relative_to(workspace).as_posix()
        require(name in inputs and type(inputs[name].get('bytes')) is int and inputs[name]['bytes'] > 0,
                'Scaling retention source outside inventory')
        transition = event.get('transition')
        if transition == 'admitted':
            require(name not in retained and len(retained) < capacity, 'Scaling retention capacity exceeded')
            retained[name] = inputs[name]['bytes']
            admitted += 1
        elif transition == 'consumed':
            require(name in retained, 'Scaling source consumed without retention')
            del retained[name]
            consumed += 1
        else:
            require(transition in ('started', 'settled') and name in retained,
                    'Unexpected scaling retention transition')
        require(type(event.get('retained')) is int and event['retained'] == len(retained),
                'Inconsistent scaling retained source count')
        peak_count = max(peak_count, len(retained))
        peak_bytes = max(peak_bytes, sum(retained.values()))
    require(not retained and admitted == consumed == len(inputs) == report.get('admitted')
            and report.get('retainedAfter') == 0 and report.get('peakRetained') == peak_count,
            'Incomplete scaling retention cleanup')
    return {'scope': 'accepted-driver-source-read-window', 'peak_sources': peak_count,
            'peak_source_bytes': peak_bytes, 'sources_after': 0, 'source_bytes_after': 0}


def scaling_measurement_budgets(plan, tiers):
    """Shared producer/consumer check; a failed run cannot revise its plan."""
    require(len(tiers) == len(plan['tiers']), 'Incomplete scaling tier matrix')
    sizes, peaks = [], []
    for expected, tier in zip(plan['tiers'], tiers):
        inputs = tier['inputs']
        size = sum(entry['bytes'] for entry in inputs.values())
        require(size >= expected['min_source_bytes'] and len(inputs) >= expected['min_sources'],
                'Scaling workload below predeclared size')
        peak = tier['rss_bytes']
        require(type(peak) is int and 0 < peak <= expected['max_rss_bytes'], 'Scaling RSS budget exceeded')
        observed = tier.get('retention', {})
        require(observed.get('scope') == 'accepted-driver-source-read-window'
                and type(observed.get('peak_sources')) is int
                and 0 < observed['peak_sources'] <= expected['max_retained_sources']
                and type(observed.get('peak_source_bytes')) is int
                and 0 < observed['peak_source_bytes'] <= expected['max_retained_source_bytes']
                and type(observed.get('sources_after')) is int and observed['sources_after'] == 0
                and type(observed.get('source_bytes_after')) is int and observed['source_bytes_after'] == 0,
                'Missing/out-of-budget scaling retention observations')
        sizes.append(size)
        peaks.append(peak)
    require(all(right >= left * plan['min_input_growth_factor'] for left, right in zip(sizes, sizes[1:])),
            'Production scaling tiers do not materially grow')
    require(all(right * plan['max_rss_growth_denominator'] <= left * plan['max_rss_growth_numerator']
                for left, right in zip(peaks, peaks[1:])), 'Production memory growth budget exceeded')


def run_scaling(context, driver, contexts, seeds, budget, seconds, plan_path, plan_sha, source_sha):
    plan = scaling_plan(plan_path, plan_sha, source_sha)
    root, workspace = context['directory'].parent, context['workspace']
    # Pin the reviewed plan before the first measurement; never derive budgets
    # from measured peaks. Package choices are actual admitted production builds.
    saved = context['directory'] / 'scaling-plan.json'
    shutil.copyfile(plan_path, saved)
    require(bootstrap.digest(saved) == plan_sha, 'Scaling plan changed while pinning measurement')
    reference = lambda path: {'path': path.relative_to(root).as_posix(), 'sha256': bootstrap.digest(path)}
    result = {'plan': reference(saved), 'compiler_sha256': context['policy']['seedSha256'], 'tiers': []}
    timer = Path(plan['timer']['path'])
    require(not timer.is_symlink() and timer.is_file()
            and bootstrap.digest(timer) == plan['timer']['sha256'], 'RSS timer identity changed')
    timer_evidence = context['directory'] / 'scaling-timer.bin'
    shutil.copyfile(timer, timer_evidence)
    require(bootstrap.digest(timer_evidence) == plan['timer']['sha256'], 'Copied RSS timer identity changed')
    result['timer'] = reference(timer_evidence)
    for index, tier in enumerate(plan['tiers']):
        require(not timer.is_symlink() and bootstrap.digest(timer) == plan['timer']['sha256'], 'RSS timer identity changed')
        package = tier['package']
        # Materialize the ordinary local dependency closure through pinned
        # Airship, not copied/missing generated dependencies from another root.
        source_root = context['workspace']
        tier_context = create_production_workspace(source_root, root / f'scaling-tier-{index}',
            context['source_manifest'], seeds, context['tools'], context['bootstrap_stage'], budget)
        workspace = tier_context['workspace']
        ordinary = execute_production(tier_context, package, seeds, budget, seconds)
        arguments = observed_compiler_arguments(tier_context)
        require(bootstrap.digest(driver) == context['driver_sha256']
                and bootstrap.digest(seeds['node'][0]) == seeds['node'][1], 'Scaling tool identity changed')
        destination = workspace / 'packages' / package / 'dist' / f'scaling-{index}/test.js'
        require(not destination.exists(), 'Scaling destination is not cold')
        arguments = driver_arguments(arguments, destination)
        log = tier_context['directory'] / f'logs/scaling-{index}.log'
        rss = tier_context['directory'] / f'logs/scaling-{index}.rss'
        timer_args = ['-l', '-o', str(rss)] if plan['timer']['kind'] == 'bsd-time' else ['-f', 'flat-maxrss-kib=%M', '-o', str(rss)]
        argv = [str(timer)] + timer_args + [str(seeds['node'][0]), str(driver), 'forward', '3'] + arguments
        require(not temporary_output_inventory(workspace), 'Scaling starts with unsettled staging')
        sources_before = scenario_sources(None, workspace)
        require(sources_before == context['source_manifest'], 'Scaling baseline sources changed')
        cleanup = {}
        journal = tier_context['journal']
        offset = journal.stat().st_size if journal.exists() else 0
        bootstrap.run_command(argv, workspace / 'packages' / package, tier_context['env'], log, journal,
                              timeout=seconds, budget=budget, cleanup_observation=cleanup)
        admission_records(journal, offset)
        require(scenario_sources(None, workspace) == sources_before, 'Scaling changed production sources')
        require(bootstrap.digest(driver) == context['driver_sha256']
                and bootstrap.digest(seeds['node'][0]) == seeds['node'][1], 'Scaling tool changed during measurement')
        require(not temporary_output_inventory(workspace), 'Scaling left temporary outputs')
        require(type(cleanup.get('pid')) is int and cleanup['pid'] > 0
                and type(cleanup.get('exit_code')) is int and cleanup['exit_code'] == 0
                and all(cleanup.get(key) is True for key in ('wait_completed', 'child_reaped', 'process_group_absent'))
                and cleanup.get('cleanup_unknown', False) is False, 'Scaling process cleanup incomplete')
        command_path = log.with_suffix('.measurement.json')
        bootstrap.write_json(command_path, {'schema': 'flat.dogfood.scaling-command.v1', 'argv': argv,
            'cwd': str(workspace / 'packages' / package), 'log': reference(log),
            'journal': reference(journal), 'admission_offset': offset,
            'driver': reference(driver), 'node': {'path': str(seeds['node'][0]), 'sha256': seeds['node'][1]},
            'process_cleanup': cleanup, 'temporary_outputs_after': [],
            **process_receipt_references(root, log)})
        report = production_read_records(log, arguments, 'forward', 3, bootstrap.digest(driver), destination)
        # Record actual admitted file bytes, not nominal package size. The input
        # fingerprint independently binds their content/options/tool.
        inputs = {}
        for event in schema_log_records(log, 'flat.compiler.source-read-event.v1'):
            if event['transition'] != 'admitted':
                continue
            source = Path(event['source'])
            if not source.is_absolute():
                source = workspace / 'packages' / package / source
            require(source.is_file() and not source.is_symlink() and workspace in source.resolve().parents,
                    'Scaling source outside production workspace')
            inputs[source.relative_to(workspace).as_posix()] = {'sha256': bootstrap.digest(source),
                'bytes': source.stat().st_size, 'reference': reference(source)}
        require(bootstrap.digest(timer) == plan['timer']['sha256'], 'RSS timer identity changed during measurement')
        retention = scaling_retention(log, inputs, workspace, package, report)
        result['tiers'].append({'package': package, 'arguments': arguments, 'destination': str(destination),
            'log': reference(log), 'rss': reference(rss), 'rss_bytes': rss_bytes(rss, plan['timer']['kind']),
            'retention': retention, 'ordinary': ordinary, 'measurement': reference(command_path),
            'inputs': inputs, 'report': report, 'artifact': reference(destination)})
        scaling_measurement_budgets(dict(plan, tiers=plan['tiers'][:index + 1]), result['tiers'])
    return result


def process_receipt_references(root, log):
    return {name: {'path': path.relative_to(root).as_posix(), 'sha256': bootstrap.digest(path)}
            for name, path in (('spawn_reference', log.with_suffix(log.suffix + '.process.json')),
                               ('settlement_reference', log.with_suffix(log.suffix + '.settlement.json')))}


def validate_process_receipts(root, value, expected_exit):
    log = root / value['log']['path']
    for name, suffix in (('spawn_reference', '.process.json'), ('settlement_reference', '.settlement.json')):
        file_reference(root, value[name])
        require(root / value[name]['path'] == log.with_suffix(log.suffix + suffix),
                'Process receipt does not belong to command log')
    spawn = read_json(root / value['spawn_reference']['path'])
    settled = read_json(root / value['settlement_reference']['path'])
    process = value['process_cleanup']
    identity = process.get('identity', {})
    require(isinstance(identity, dict) and type(identity.get('pid')) is int and identity['pid'] > 0
            and identity.get('pgid') == identity['pid'] == identity.get('session')
            and isinstance(identity.get('start'), str) and bool(identity['start'])
            and process.get('pid') == identity['pid']
            and all(process.get(key) is True for key in ('wait_completed', 'child_reaped', 'process_group_absent')),
            'Missing durable process ownership/cleanup observations')
    require(spawn.get('schema') == 'flat.stage.process-identity.v1'
            and spawn.get('identity') == process.get('identity')
            and settled.get('schema') == 'flat.stage.process-settlement.v1'
            and {key: item for key, item in settled.items() if key != 'schema'} == process
            and process.get('status') == 'settled' and process.get('cleanup_unknown') is False
            and type(process.get('exit_code')) is int and process['exit_code'] == expected_exit,
            'Unbound or unknown durable process settlement')
    return process


def validate_scaling_command(root, tier, plan, workspace, driver_sha):
    file_reference(root, tier['measurement'])
    command = read_json(root / tier['measurement']['path'])
    require(command.get('schema') == 'flat.dogfood.scaling-command.v1'
            and command.get('cwd') == str(workspace / 'packages' / tier['package'])
            and command.get('log') == tier['log'], 'Unbound scaling measurement command')
    file_reference(root, command['driver'])
    require(command['driver']['sha256'] == driver_sha, 'Scaling command driver identity mismatch')
    node = command.get('node', {})
    require(isinstance(node.get('path'), str) and Path(node['path']).is_absolute() and sha(node.get('sha256'))
            and bootstrap.digest(Path(node['path'])) == node['sha256'], 'Scaling command Node identity mismatch')
    ordinary = tier['ordinary']['commands'][0]
    require(ordinary['argv'][0] == node['path'], 'Scaling changed ordinary Node selection')
    rss = root / tier['rss']['path']
    options = (['-l', '-o', str(rss)] if plan['timer']['kind'] == 'bsd-time'
               else ['-f', 'flat-maxrss-kib=%M', '-o', str(rss)])
    require(command.get('argv') == [plan['timer']['path']] + options
            + [node['path'], str(root / command['driver']['path']), 'forward', '3'] + tier['arguments'],
            'Scaling timer/driver argv mismatch')
    file_reference(root, command['journal'])
    offset = command.get('admission_offset')
    require(type(offset) is int and offset >= 0, 'Invalid scaling admission boundary')
    admission_records(root / command['journal']['path'], offset)
    process = validate_process_receipts(root, command, 0)
    require(type(process.get('pid')) is int and process['pid'] > 0
            and type(process.get('exit_code')) is int and process['exit_code'] == 0
            and all(process.get(key) is True for key in ('wait_completed', 'child_reaped', 'process_group_absent'))
            and process.get('cleanup_unknown', False) is False and command.get('temporary_outputs_after') == [],
            'Scaling process cleanup incomplete')
    return command


def validate_scaling(root, value, source_sha, baseline, driver_sha):
    require(isinstance(value, dict), 'Missing measured production scaling')
    file_reference(root, value['plan'])
    plan = scaling_plan(root / value['plan']['path'], value['plan']['sha256'], source_sha)
    require(isinstance(value.get('timer'), dict), 'Missing pinned RSS timer observation')
    require(file_reference(root, value['timer']) == plan['timer']['sha256'], 'Scaling RSS timer identity mismatch')
    tiers = value.get('tiers')
    require(isinstance(tiers, list) and len(tiers) == len(plan['tiers']), 'Incomplete scaling tier matrix')
    sizes, peaks = [], []
    for expected, tier in zip(plan['tiers'], tiers):
        require(tier.get('package') == expected['package'], 'Scaling tier selection mismatch')
        for name in ('log', 'rss', 'artifact'):
            file_reference(root, tier[name])
        destination = Path(tier['destination'])
        require(destination.is_absolute() and str(root / tier['artifact']['path']) == str(destination),
                'Scaling artifact reference does not identify tier destination')
        ordinary = tier.get('ordinary')
        require(isinstance(ordinary, dict) and ordinary.get('package') == expected['package'],
                'Missing corresponding ordinary production baseline')
        require(validate_production_run(root, ordinary, value.get('compiler_sha256')) == baseline,
                'Scaling baseline source snapshot mismatch')
        workspace = root / ordinary['workspace']
        validate_scaling_command(root, tier, plan, workspace, driver_sha)
        require(workspace / 'packages' / expected['package'] / 'dist' in destination.parents,
                'Scaling destination outside tier workspace')
        command = ordinary['commands'][0]
        compiler_argv = command.get('compiler_argv')
        require(isinstance(compiler_argv, list) and len(compiler_argv) > 2
                and driver_arguments(compiler_argv[2:], destination) == tier['arguments'],
                'Scaling arguments differ from ordinary production baseline')
        candidates = [digest for name, digest in ordinary['artifacts'].items()
                      if PurePosixPath(name).name == destination.name]
        require(len(candidates) == 1 and candidates[0] == tier['artifact']['sha256'],
                'Scaling artifact bytes differ from ordinary production baseline')
        report = production_read_records(root / tier['log']['path'], tier['arguments'], 'forward', 3,
                                         driver_sha, destination)
        require(report == tier.get('report'), 'Changed scaling producer observations')
        ordinary_diagnostics = semantic_diagnostics(ordinary['commands'])
        batches = schema_log_records(root / tier['log']['path'], 'flat.compiler.diagnostics.v1')
        require(ordinary_diagnostics['complete'] and len(batches) == 1 and batches[0].get('complete') is True,
                'Incomplete scaling/baseline diagnostics')
        require(normalize_diagnostics(batches[0].get('records'), workspace) == ordinary_diagnostics['records'],
                'Scaling diagnostics differ from ordinary production baseline')
        inputs = tier.get('inputs')
        require(isinstance(inputs, dict) and len(inputs) == report['admitted'], 'Incomplete scaling source inventory')
        require(inputs == driver_source_inputs(root / tier['log']['path'], workspace, expected['package'], root),
                'Scaling source inventory differs from actual admitted inputs')
        retention = scaling_retention(root / tier['log']['path'], inputs, workspace, expected['package'], report)
        require(retention == tier.get('retention'), 'Changed/missing scaling retention observations')
        for name, entry in inputs.items():
            require(file_reference(root, entry['reference']) == entry.get('sha256')
                    and (root / entry['reference']['path']).stat().st_size == entry.get('bytes'),
                    'Scaling input size/content changed')
            require(baseline.get(name) == entry.get('sha256') and type(entry.get('bytes')) is int and entry['bytes'] > 0,
                    'Scaling input not in admitted production snapshot')
        peak = rss_bytes(root / tier['rss']['path'], plan['timer']['kind'])
        require(type(tier.get('rss_bytes')) is int and peak == tier['rss_bytes'],
                'Scaling RSS budget observation changed')
    scaling_measurement_budgets(plan, tiers)
    return True


def driver_arguments(arguments, destination, main=None):
    """Keep admitted inputs/dependencies; replace only executable selection/output."""
    result = []
    index = 0
    while index < len(arguments):
        option = arguments[index]
        if option in ('-threads', '-parallel', '-o', '-d') or (main and option == '-main'):
            index += 1 if option == '-parallel' else 2
        elif option == '-library':
            if not main:
                result.append(option)
            index += 1
        elif main and not option.startswith('-'):
            index += 1  # suite-only source roots cannot select the production entrypoint
        else:
            result.append(option)
            index += 1
            if option.startswith('-'):
                require(index < len(arguments), 'Missing admitted compiler option value')
                result.append(arguments[index])
                index += 1
    if main:
        result.insert(0, 'src')
        if '-x' not in result:
            result += ['-x', '**/*_Test*.flat']
        result += ['-main', main]
    return result + ['-d', str(destination.parent), '-o', destination.name]


def production_read_records(log, arguments, scenario, capacity, tool_sha, destination):
    """Verify bounded producer transitions against its terminal digest and inputs."""
    require(scenario in DRIVER_SCENARIOS + LEGACY_DRIVER_SCENARIOS,
            'Unknown production driver scenario')
    # Legacy cancellation receipts retain read compatibility only. Their missing
    # order declaration never satisfies the current aggregate scenario matrix.
    ordered_cancellation = scenario in DRIVER_SCENARIOS and scenario.startswith('cancel-')
    reports = schema_log_records(log, 'flat.compiler.source-reads.v1')
    require(len(reports) == 1, 'Missing/duplicate production source-read report')
    report = reports[0]
    require(report.get('scenario') == scenario and type(report.get('capacity')) is int and report['capacity'] == capacity
            and report.get('destination') == str(destination), 'Production driver selection mismatch')
    options_digest = hashlib.sha256(json.dumps(arguments + ['-threads', str(capacity)],
        ensure_ascii=False, separators=(',', ':')).encode()).hexdigest()
    require(report.get('optionsFingerprint') == 'sha256:' + options_digest,
            'Production driver options fingerprint mismatch')
    admitted = report.get('input', {})
    require(admitted.get('schema') == 'flat.compiler.incremental.v1'
            and admitted.get('toolFingerprint') == 'sha256:' + tool_sha
            and admitted.get('inputFingerprint') == report.get('revision')
            and admitted.get('reused') is False, 'Unbound/warm production driver input')
    require(isinstance(report.get('revision'), str) and report['revision'].startswith('sha256:')
            and sha(report['revision'][7:]), 'Missing production input revision')
    events = schema_log_records(log, 'flat.compiler.source-read-event.v1')
    require(events, 'Missing production source-read events')
    digest = hashlib.sha256()
    window, settled, active = [], [], set()
    retained = set()
    observed_peak_active = observed_peak_retained = 0
    admitted_count = settled_count = 0
    cancellation_readiness = []
    for sequence, event in enumerate(events, 1):
        require(event.get('sequence') == sequence and event.get('revision') == report['revision']
                and event.get('destination') == str(destination) and event.get('capacity') == capacity,
                'Stale/out-of-order production source-read event')
        identity, transition = event.get('source'), event.get('transition')
        require(isinstance(identity, str) and identity, 'Missing observed source identity')
        for name in ('admitted', 'settled', 'active', 'retained'):
            require(type(event.get(name)) is int and event[name] >= 0, 'Invalid source-read event count')
        require(event['active'] <= capacity and event['retained'] <= capacity,
                'Production read capacity exceeded')
        if transition == 'admitted':
            if len(settled) == len(window) and window:
                window, settled = [], []
            require(len(window) < capacity and identity not in window, 'Unbounded/duplicate read window')
            window.append(identity)
            require(identity not in retained, 'Read admitted before prior consumption')
            retained.add(identity)
            admitted_count += 1
        elif transition == 'started':
            require(identity in window and identity not in active, 'Read started without admission')
            active.add(identity)
        elif transition == 'settled':
            require(identity in active and identity not in settled, 'Read settled without active work')
            active.remove(identity)
            settled.append(identity)
            settled_count += 1
            if not scenario.startswith('cancel-') or ordered_cancellation:
                order = list(reversed(window)) if scenario.endswith('reverse') else window
                require(settled == order[:len(settled)], 'Production settlement order mismatch')
        elif transition == 'consumed':
            require(identity in retained and identity not in active, 'Read consumed without settled ownership')
            retained.remove(identity)
        elif transition in ('cancel-readiness-after-read', 'cancel-readiness-after-stage'):
            cancellation_readiness.append(transition)
        else:
            raise ValueError('Unknown production read transition')
        require(event['admitted'] == admitted_count and event['settled'] == settled_count
                and event['active'] == len(active) and event['retained'] == len(retained),
                'Inconsistent production event counters')
        observed_peak_active = max(observed_peak_active, len(active))
        observed_peak_retained = max(observed_peak_retained, len(retained))
        digest.update(json.dumps([transition, identity], ensure_ascii=False, separators=(',', ':')).encode() + b'\n')
    require(report.get('eventDigest') == 'sha256:' + digest.hexdigest(), 'Production event digest mismatch')
    for name in ('admitted', 'settled', 'activeAfter', 'retainedAfter', 'peakActive', 'peakRetained', 'exitCode'):
        require(type(report.get(name)) is int and report[name] >= 0, 'Invalid terminal read measurement')
    require(report['admitted'] == admitted_count == report['settled'] == settled_count
            and admitted_count > 0 and not active and report['activeAfter'] == report['retainedAfter'] == 0
            and report['peakActive'] == observed_peak_active
            and report['peakRetained'] == observed_peak_retained
            and 0 < report['peakActive'] <= capacity and 0 < report['peakRetained'] <= capacity,
            'Production reads incomplete or capacity exceeded')
    cancelled = scenario.startswith('cancel-')
    require(report.get('cancellationRequested') is cancelled
            and report['exitCode'] == (1 if cancelled else 0), 'Production cancellation/exit mismatch')
    if cancelled:
        transition = ('cancel-readiness-after-read' if scenario.startswith('cancel-after-read')
                      else 'cancel-readiness-after-stage')
        require(cancellation_readiness == [transition], 'Missing actual production cancellation readiness')
    else:
        require(not cancellation_readiness, 'Unexpected production cancellation readiness')
        require(report['peakActive'] == capacity and admitted_count >= capacity,
                'Production schedule did not exercise selected capacity')
    summary = report.get('session', {})
    require(type(report.get('artifactDeliveryFailures')) is int and report['artifactDeliveryFailures'] == 0,
            'Artifact observation delivery incomplete')
    require(summary.get('schema') == 'flat.compiler.session.v1'
            and summary.get('cancelled') is cancelled and summary.get('cleanupSettled') is True,
            'Production session cleanup incomplete')
    batches = schema_log_records(log, 'flat.compiler.diagnostics.v1')
    require(len(batches) == 1 and batches[0].get('revision') == report['revision'],
            'Missing production diagnostic revision')
    if not cancelled:
        require(batches[0].get('complete') is True, 'Incomplete production schedule diagnostics')
    return report


def observed_compiler_arguments(context):
    commands = admission_records(context['journal'])
    values = [record['args'][1:] for record in commands
              if record.get('args') and record['args'][0] == str(context['compiler'])]
    require(values, 'No admitted compiler argv for production driver')
    return values[-1]


def run_driver_workload(context, seeds, budget, seconds, driver, driver_sha,
                        scenario, capacity, arguments, destination, log):
    """Observe one cold production invocation; never infer cancellation from exit alone.

    Owner lifecycle records are consumed by the caller. This boundary pins tools
    around execution and observes process and disk cleanup without deleting evidence.
    """
    require(scenario in DRIVER_SCENARIOS
            and type(capacity) is int and capacity in (1, 3), 'Unknown production workload')
    workspace = context['workspace']
    manifest = Path(str(destination) + '.flat-incremental.json')
    require(not destination.exists() and not destination.is_symlink()
            and not manifest.exists() and not manifest.is_symlink(),
            'Controlled driver destination is not cold')
    require(not temporary_output_inventory(workspace), 'Production workload starts with unsettled staging')
    sources_before = fingerprint(scenario_sources(None, workspace))
    def pinned_tools():
        require(not driver.is_symlink() and bootstrap.digest(driver) == driver_sha
                and bootstrap.digest(seeds['node'][0]) == seeds['node'][1]
                and bootstrap.digest(context['compiler']) == context['policy']['seedSha256'],
                'Production driver input tool changed')
    pinned_tools()
    cleanup = {}
    cancelled = scenario.startswith('cancel-')
    exit_code = 0
    try:
        bootstrap.run_command([str(seeds['node'][0]), str(driver), scenario, str(capacity)] + arguments,
                              workspace / 'packages/Flat-Core', context['env'], log, context['journal'],
                              timeout=seconds, budget=budget, cleanup_observation=cleanup)
    except bootstrap.CommandFailure as error:
        exit_code = error.exit_code
        if not cancelled or exit_code != 1:
            raise
    require(exit_code == (1 if cancelled else 0), 'Production workload terminal exit mismatch')
    pinned_tools()
    require(sources_before == fingerprint(scenario_sources(None, workspace)),
            'Production workload changed source inputs')
    require(type(cleanup.get('pid')) is int and cleanup['pid'] > 0
            and type(cleanup.get('exit_code')) is int and cleanup['exit_code'] == exit_code,
            'Missing production workload process identity')
    for name in ('wait_completed', 'child_reaped', 'process_group_absent'):
        require(cleanup.get(name) is True, 'Production workload process cleanup incomplete: ' + name)
    temporary_outputs = temporary_output_inventory(workspace)
    require(not temporary_outputs, 'Production workload retained terminal staging')
    if cancelled:
        require(not destination.exists() and not destination.is_symlink()
                and not manifest.exists() and not manifest.is_symlink(),
                'Cancelled production workload published terminal output')
    return cleanup, temporary_outputs


def run_production_driver(context, seeds, budget, seconds):
    """Build through stage2 and run the real driver over admitted production inputs."""
    directory, workspace = context['directory'], context['workspace']
    evidence_root = directory.parent
    execute_production(context, 'Flat-Compiler', seeds, budget, seconds)
    arguments = observed_compiler_arguments(context)
    driver = workspace / 'packages/Flat-Compiler/dist/es6/production-driver.js'
    build_args = driver_arguments(arguments, driver,
                                  'flat/compiler/CompilerProductionDriver')
    log = directory / 'logs/driver-build.log'
    require(bootstrap.digest(context['compiler']) == context['policy']['seedSha256']
            and bootstrap.digest(seeds['node'][0]) == seeds['node'][1], 'Production driver builder input changed')
    bootstrap.run_command([str(seeds['node'][0]), str(context['compiler'])] + build_args,
                          workspace / 'packages/Flat-Compiler', context['env'], log,
                          context['journal'], timeout=seconds, budget=budget)
    admission_records(context['journal'])
    require(driver.is_file() and not driver.is_symlink(), 'Production driver artifact missing')
    driver_sha = bootstrap.digest(driver)
    context['driver_sha256'] = driver_sha
    reference = lambda path: {'path': path.relative_to(evidence_root).as_posix(), 'sha256': bootstrap.digest(path)}
    result = {'driver': reference(driver), 'compiler': reference(context['compiler']),
              'build_log': reference(log), 'build_argv': build_args,
              'compiler_sha256': context['policy']['seedSha256'],
            'build_source_manifest': scenario_sources(None, workspace),
              'source_manifest_sha256': fingerprint(scenario_sources(None, workspace)), 'runs': []}
    execute_production(context, 'Flat-Core', seeds, budget, seconds)
    package_args = observed_compiler_arguments(context)
    for scenario in DRIVER_SCENARIOS:
        for capacity in (1, 3):
            require(bootstrap.digest(driver) == driver_sha and bootstrap.digest(seeds['node'][0]) == seeds['node'][1],
                    'Production driver input tool changed')
            destination = workspace / 'packages/Flat-Core/dist/production-driver' / f'{scenario}-{capacity}/test.js'
            require(not destination.exists(), 'Controlled driver destination is not cold')
            argv = driver_arguments(package_args, destination)
            log = directory / f'logs/driver-{scenario}-{capacity}.log'
            cleanup, temporary_outputs = run_driver_workload(
                context, seeds, budget, seconds, driver, driver_sha,
                scenario, capacity, argv, destination, log)
            admission_records(context['journal'])
            report = production_read_records(log, argv, scenario, capacity, driver_sha, destination)
            outputs = {}
            if not scenario.startswith('cancel-'):
                require(destination.is_file(), 'Production schedule did not publish output')
                outputs[destination.name] = reference(destination)
            artifact_lifecycle(log, report, not scenario.startswith('cancel-after-read'))
            manifest_records = manifest_lifecycle(log, report)
            result['runs'].append({'scenario': scenario, 'capacity': capacity, 'arguments': argv,
                'manifest_lifecycle': manifest_records,
                'inputs': driver_source_inputs(log, workspace, 'Flat-Core', evidence_root),
                'artifact_lifecycle': schema_log_records(log, 'flat.compiler.artifact-lifecycle.v1'),
                'destination': str(destination), 'log': reference(log), 'report': report,
                'process_cleanup': cleanup, 'temporary_outputs_after': temporary_outputs,
                **process_receipt_references(evidence_root, log),
                'artifacts': outputs})
            bootstrap.write_json(directory / 'driver-evidence.json', result)
    require(result['source_manifest_sha256'] == fingerprint(scenario_sources(None, workspace)),
            'Production driver changed source inputs')
    return result


def artifact_lifecycle(log, report, require_temporary):
    require(report.get('artifactDeliveryFailures') == 0, 'Artifact observation delivery incomplete')
    records = schema_log_records(log, 'flat.compiler.artifact-lifecycle.v1')
    return temporary_lifecycle(records, report['revision'], report['destination'], require_temporary)


def manifest_lifecycle(log, report):
    require(type(report.get('manifestDeliveryFailures')) is int and report['manifestDeliveryFailures'] == 0,
            'Manifest observation delivery incomplete')
    records = schema_log_records(log, 'flat.compiler.manifest-lifecycle.v1')
    destination = report['destination'] + '.flat-incremental.json'
    require_temporary = not report['scenario'].startswith('cancel-')
    temporary_lifecycle(records, report['revision'], destination, require_temporary)
    if require_temporary:
        require(sum(record['transition'] == 'published' for record in records) == 1,
                'Manifest publication not observed')
    else:
        require(not any(record['transition'] == 'published' for record in records),
                'Cancelled production invocation published a manifest')
    return records


def temporary_lifecycle(records, revision, destination, require_temporary):
    """Validate owner events without substituting terminal absence for ownership."""
    created, removed, finalized = set(), set(), set()
    for sequence, record in enumerate(records, 1):
        require(record.get('sequence') == sequence and record.get('revision') == revision
                and record.get('destination') == destination
                and isinstance(record.get('identity'), str) and record['identity'],
                'Unbound artifact lifecycle event')
        transition, path = record.get('transition'), record.get('path')
        require(record.get('failure') is None and type(record.get('removed')) is bool,
                'Artifact finalization failure')
        if transition == 'published':
            require(path is None, 'Unexpected publication path')
            continue
        require(isinstance(path, str) and Path(path).is_absolute(), 'Missing owned temporary path')
        require(Path(destination).parent in Path(path).parents, 'Temporary outside invocation destination')
        if transition == 'temporary-created':
            require(path not in created, 'Duplicate temporary creation')
            created.add(path)
        elif transition == 'temporary-removed':
            require(path in created and path not in removed and record['removed'], 'Removal without owned creation')
            removed.add(path)
        elif transition == 'finalized':
            require(path in created and record['removed'], 'Unsettled artifact finalization')
            finalized.add(path)
        else:
            raise ValueError('Unknown artifact lifecycle transition')
    require(not require_temporary or created, 'No actual temporary lifecycle observed')
    require(created == removed and all(any(path == owner or Path(owner) in Path(path).parents
            for owner in finalized) for path in created), 'Incomplete artifact temporary cleanup')
    return True


def validate_driver_inputs(root, value, run, report):
    destination = Path(run['destination'])
    require(destination.is_absolute() and len(destination.parents) >= 6
            and destination.name == 'test.js', 'Invalid controlled driver destination')
    workspace = destination.parents[5]
    require(root.resolve() in workspace.resolve().parents
            and destination == workspace / 'packages/Flat-Core/dist/production-driver'
                / f"{run['scenario']}-{run['capacity']}" / 'test.js', 'Controlled driver destination selection mismatch')
    actual = driver_source_inputs(root / run['log']['path'], workspace, 'Flat-Core', root)
    require(actual == run.get('inputs') and len(actual) == report['admitted'],
            'Driver input inventory differs from actual read events')
    cancelled = run['scenario'].startswith('cancel-')
    manifest = Path(str(destination) + '.flat-incremental.json')
    if cancelled:
        require(run.get('artifacts') == {} and not destination.exists() and not manifest.exists(),
                'Cancelled driver published artifact or manifest')
    else:
        artifact = run.get('artifacts', {}).get('test.js')
        require(isinstance(artifact, dict) and set(run['artifacts']) == {'test.js'}
                and root / artifact['path'] == destination, 'Driver artifact does not identify destination')
        batches = schema_log_records(root / run['log']['path'], 'flat.compiler.diagnostics.v1')
        require(len(batches) == 1 and batches[0].get('complete') is True,
                'Incomplete controlled driver diagnostics')
    return workspace


def validate_driver_evidence(root, value, sources_sha, compiler_sha):
    require(isinstance(value, dict) and value.get('source_manifest_sha256') == sources_sha
            and value.get('compiler_sha256') == compiler_sha, 'Stale production driver source/tool identity')
    require(value.get('build_source_manifest') and fingerprint(value['build_source_manifest']) == sources_sha,
            'Production driver build source identity mismatch')
    driver_sha = file_reference(root, value['driver'])
    require(file_reference(root, value['compiler']) == compiler_sha, 'Production driver builder bytes changed')
    file_reference(root, value['build_log'])
    runs = value.get('runs')
    expected = {(scenario, capacity) for scenario in DRIVER_SCENARIOS for capacity in (1, 3)}
    require(isinstance(runs, list) and len(runs) == len(expected)
            and all(isinstance(run, dict) and type(run.get('capacity')) is int for run in runs)
            and {(run.get('scenario'), run.get('capacity')) for run in runs} == expected,
            'Incomplete production driver scenario matrix')
    outputs, diagnostics = [], []
    for run in runs:
        file_reference(root, run['log'])
        report = production_read_records(root / run['log']['path'], run['arguments'], run['scenario'],
                                         run['capacity'], driver_sha, Path(run['destination']))
        require(report == run.get('report'), 'Changed production driver report')
        validate_driver_inputs(root, value, run, report)
        inputs = run.get('inputs')
        require(isinstance(inputs, dict) and len(inputs) == report['admitted'], 'Missing production driver source identities')
        for name, entry in inputs.items():
            require(value['build_source_manifest'].get(name) == entry.get('sha256')
                    and file_reference(root, entry['reference']) == entry['sha256'], 'Stale production driver source content')
        process = validate_process_receipts(root, run, report['exitCode'])
        require(type(process.get('pid')) is int and process['pid'] > 0
                and process.get('exit_code') == report['exitCode']
                and all(process.get(name) is True for name in ('wait_completed', 'child_reaped', 'process_group_absent')),
                'Production driver process cleanup incomplete')
        require(run.get('temporary_outputs_after') == [], 'Production driver left temporary outputs')
        require(run.get('manifest_lifecycle') == manifest_lifecycle(root / run['log']['path'], report),
                'Changed manifest lifecycle observations')
        artifact_lifecycle(root / run['log']['path'], report,
                           not run['scenario'].startswith('cancel-after-read'))
        require(run.get('artifact_lifecycle') == schema_log_records(root / run['log']['path'],
                    'flat.compiler.artifact-lifecycle.v1'), 'Changed artifact lifecycle observations')
        for reference in run.get('artifacts', {}).values():
            file_reference(root, reference)
        if run['scenario'] in ('forward', 'reverse'):
            require(run.get('artifacts'), 'Missing scheduled production artifacts')
            outputs.append({name: reference['sha256'] for name, reference in run['artifacts'].items()})
            batch = schema_log_records(root / run['log']['path'], 'flat.compiler.diagnostics.v1')[0]
            workspace = Path(run['destination']).parents[5]
            diagnostics.append(normalize_diagnostics(batch['records'], workspace))
    require(all(output == outputs[0] for output in outputs), 'Production schedule-dependent artifact bytes')
    require(all(records == diagnostics[0] for records in diagnostics), 'Production schedule-dependent diagnostics')
    return True


def node_json_digest(value):
    """Node JSON.stringify-compatible contract values: ordered keys, UTF-8."""
    return hashlib.sha256(json.dumps(value, ensure_ascii=False, separators=(',', ':'),
                                    allow_nan=False).encode()).hexdigest()


def invocation_admission(log, expected):
    require(isinstance(expected, dict), 'Missing actual invalid invocation admission bindings')
    schemas = {'flat.compiler.invocation-source.v1', 'flat.compiler.invocation-admission.v1',
               'flat.compiler.diagnostics.v1'}
    # Read all three schemas in physical log order. Separate schema scans would
    # accept a seal before its sources or admission emitted after diagnostics.
    ordered = []
    require(log.is_file() and not log.is_symlink() and log.stat().st_size <= 100 * 1024 * 1024,
            'Missing/oversized invocation log')
    def unique(pairs):
        result = {}
        for key, value in pairs:
            require(key not in result, 'Duplicate invocation record key')
            result[key] = value
        return result
    with log.open() as stream:
        while True:
            line = stream.readline(1024 * 1024 + 1)
            if not line:
                break
            require(len(line) <= 1024 * 1024, 'Invocation line capacity exceeded')
            if not line.lstrip().startswith('{'):
                continue
            try:
                value = json.loads(line, object_pairs_hook=unique)
            except json.JSONDecodeError:
                require(not any(schema in line for schema in schemas), 'Malformed invocation record')
                continue
            if isinstance(value, dict) and value.get('schema') in schemas:
                require(len(ordered) < MAX_REFERENCES, 'Invocation stream capacity exceeded')
                ordered.append(value)
    seals = [index for index, value in enumerate(ordered)
             if value['schema'] == 'flat.compiler.invocation-admission.v1']
    require(len(seals) == 1, 'Missing actual invalid invocation admission or duplicate seal')
    index = seals[0]
    sources, seal, batches = ordered[:index], ordered[index], ordered[index + 1:]
    require(sources and len(batches) == 1 and batches[0]['schema'] == 'flat.compiler.diagnostics.v1',
            'Incomplete/reordered invocation admission and diagnostics')
    revision = seal.get('revision')
    entries, previous = [], None
    expected_sources = expected['sources']
    for identity, digest in expected_sources.items():
        retained = Path(expected.get('retained_sources', {}).get(identity, identity))
        require(retained.is_file() and not retained.is_symlink() and bootstrap.digest(retained) == digest,
                'Invocation retained source bytes changed')
    for sequence, source in enumerate(sources, 1):
        require(set(source) == {'schema', 'revision', 'sequence', 'identity', 'contentFingerprint'}
                and source['schema'] == 'flat.compiler.invocation-source.v1'
                and type(source['sequence']) is int and source['sequence'] == sequence
                and source['revision'] == revision, 'Reordered/foreign invocation source')
        identity = source['identity']
        require(isinstance(identity, str) and Path(identity).is_absolute()
                and str(Path(identity)) == identity and '..' not in Path(identity).parts,
                'Invalid invocation source identity')
        # JS compares UTF-16 code units, not Unicode scalar values or locale.
        key = identity.encode('utf-16-be', errors='surrogatepass')
        require(previous is None or previous < key, 'Unordered/duplicate invocation source identity')
        previous = key
        digest = expected_sources.get(identity)
        require(sha(digest) and source['contentFingerprint'] == 'sha256:' + digest,
                'Invocation source bytes differ from retained inputs')
        entries.append({'identity': identity, 'digest': digest})
    require(set(expected_sources) == {entry['identity'] for entry in entries}, 'Incomplete invocation source inventory')
    require(set(seal) == {'schema', 'revision', 'destination', 'toolFingerprint', 'optionsFingerprint',
                         'configurationFingerprint', 'sourceInventoryFingerprint', 'sourceCount'},
            'Invalid invocation admission seal fields')
    options, tool = expected['options'], expected['tool_sha256']
    require(sha(tool) and isinstance(options, list) and all(isinstance(value, str) for value in options),
            'Missing selected invocation options/tool')
    output_budget = 67108864
    for offset, token in enumerate(options):
        if token == '--max-artifact-bytes':
            require(offset + 1 < len(options) and options[offset + 1].isascii()
                    and options[offset + 1].isdecimal(), 'Invalid selected artifact budget')
            output_budget = int(options[offset + 1])
            require(1 <= output_budget <= 2147483647, 'Invalid selected artifact budget')
    configuration = node_json_digest({'schema': 'flat.compiler.incremental.v1', 'publication': 'staged-sha256-v1',
        'options': options, 'tool': tool, 'node': expected['node_version'], 'maxSourceBytes': 4194304,
        'maxOutputBytes': output_budget})
    computed_revision = 'sha256:' + node_json_digest({'configuration': configuration, 'entries': entries})
    require(type(seal['sourceCount']) is int and seal['sourceCount'] == len(entries)
            and seal['destination'] == expected['destination']
            and seal['toolFingerprint'] == 'sha256:' + tool
            and seal['optionsFingerprint'] == 'sha256:' + node_json_digest(options)
            and seal['configurationFingerprint'] == 'sha256:' + configuration
            and seal['sourceInventoryFingerprint'] == 'sha256:' + node_json_digest(entries)
            and revision == computed_revision and batches[0].get('revision') == revision,
            'Altered invocation seal/configuration/diagnostic revision')
    return batches


def invalid_diagnostics(log, workspace, expected=None):
    batches = schema_log_records(log, 'flat.compiler.diagnostics.v1')
    require(len(batches) == 1, 'Invalid control requires one terminal diagnostic batch')
    batch = batches[0]
    revision = batch.get('revision', '')
    require(isinstance(revision, str) and revision.startswith('sha256:') and sha(revision[7:])
            and batch.get('complete') is True and bool(batch.get('records')),
            'Invalid control requires complete nonempty revision-bound diagnostics')
    batch['records'] = normalize_diagnostics(batch['records'], workspace)
    require(expected is not None, 'Missing actual invalid invocation admission bindings')
    admitted = invocation_admission(log, expected)
    require(admitted[0]['revision'] == revision, 'Changed invocation diagnostic revision')
    for record in batch['records']:
        require(str(workspace / record['source']) in expected['sources'],
                'Diagnostic source outside actual invocation closure')
    return batches


INVALID_CONTROLS = {
    'syntax': '\nclass DogfoodInvalidSyntax {\n',
    'name': '\nclass DogfoodInvalidName { public invalid() -> Int => dogfoodMissingSymbol }\n',
}


def product_command(context, argv, cwd, seeds, budget, seconds, expected_exit=0):
    """Retain real top-level execution, including deliberately failed compilation."""
    directory, root = context['directory'], context['directory'].parent
    ordinal = len(context.setdefault('product_commands', [])) + 1
    log = directory / f'logs/product-{ordinal}.log'
    offset = context['journal'].stat().st_size if context['journal'].exists() else 0
    cleanup = {}
    require(argv[0] == str(seeds['node'][0]), 'Product command must select reviewed Node path')
    require(bootstrap.digest(seeds['node'][0]) == seeds['node'][1], 'Product Node identity changed')
    tool = Path(argv[1])
    require(tool.is_file() and not tool.is_symlink(), 'Missing ordinary product executable')
    tool_sha = bootstrap.digest(tool)
    code = 0
    try:
        bootstrap.run_command(argv, cwd, context['env'], log, context['journal'], timeout=seconds,
                              budget=budget, cleanup_observation=cleanup)
    except bootstrap.CommandFailure as error:
        code = error.exit_code
    if context['journal'].exists():
        admission_records(context['journal'], offset)
    journal = log.with_suffix('.admissions.jsonl')
    if context['journal'].exists():
        shutil.copyfile(context['journal'], journal)
    else:
        journal.write_text('')
    reference = lambda path: {'path': path.relative_to(root).as_posix(), 'sha256': bootstrap.digest(path)}
    command = {'argv': argv, 'cwd': str(cwd), 'exit_status': code, 'log': reference(log),
               'journal': reference(journal), 'admission_offset': offset,
               'tool': reference(tool), 'node_sha256': seeds['node'][1], 'process_cleanup': cleanup,
               'temporary_outputs_after': temporary_output_inventory(context['workspace'])}
    context['product_commands'].append(command)
    bootstrap.write_json(directory / 'product-commands.json', context['product_commands'])
    require(bootstrap.digest(tool) == tool_sha, 'Executed ordinary product bytes changed')
    return command


NODE_VERSION_PROBE = "console.log(JSON.stringify({schema:'flat.dogfood.node-version.v1',version:process.versions.node}));\n"


def observe_node_version(context, seeds, budget, seconds):
    probe = context['directory'] / 'node-version.cjs'
    probe.write_text(NODE_VERSION_PROBE)
    command = product_command(context, [str(seeds['node'][0]), str(probe)],
                              context['workspace'], seeds, budget, seconds)
    require(command['exit_status'] == 0, 'Node version probe failed')
    return {'command': command, 'version': measured_node_version(context['directory'].parent, command)}


def measured_node_version(root, command):
    file_reference(root, command['tool'])
    require((root / command['tool']['path']).read_text() == NODE_VERSION_PROBE,
            'Changed Node version probe')
    file_reference(root, command['log'])
    records = schema_log_records(root / command['log']['path'], 'flat.dogfood.node-version.v1')
    require(len(records) == 1 and set(records[0]) == {'schema', 'version'}, 'Missing Node version observation')
    version = records[0]['version']
    require(isinstance(version, str) and len(version) < 100 and len(version.split('.')) == 3
            and all(part.isascii() and part.isdecimal() for part in version.split('.')),
            'Invalid measured Node version')
    return version


def ordinary_source_closure(log, workspace, baseline):
    """Last successful Core invocation defines the actual public source closure.

    Bind its complete ordered source stream to the independently pinned workspace
    manifest, never to source digests asserted by the invalid invocation itself.
    """
    sources = schema_log_records(log, 'flat.compiler.invocation-source.v1')
    seals = schema_log_records(log, 'flat.compiler.invocation-admission.v1')
    require(seals, 'Missing successful Core invocation admission')
    seal = seals[-1]
    require(type(seal.get('sourceCount')) is int and 0 < seal['sourceCount'] <= MAX_REFERENCES,
            'Invalid successful Core source count')
    selected = [value for value in sources if value.get('revision') == seal.get('revision')]
    require(len(selected) == seal['sourceCount'], 'Incomplete/duplicate successful Core admission')
    result = {}
    for sequence, value in enumerate(selected, 1):
        require(value.get('sequence') == sequence and type(value.get('sequence')) is int,
                'Unordered successful Core source stream')
        identity = value.get('identity')
        require(isinstance(identity, str) and Path(identity).is_absolute(), 'Invalid Core source identity')
        try:
            relative = Path(identity).relative_to(workspace).as_posix()
        except ValueError:
            raise ValueError('Core source outside retained workspace')
        digest = baseline.get(relative)
        require(sha(digest) and value.get('contentFingerprint') == 'sha256:' + digest
                and identity not in result, 'Core closure differs from pinned workload')
        result[identity] = digest
    entries = [{'identity': identity, 'digest': result[identity]} for identity in result]
    require(seal.get('sourceInventoryFingerprint') == 'sha256:' + node_json_digest(entries),
            'Changed Core source inventory seal')
    return result


def invalid_bindings(command, destination, closure, node_version, mutation_identity, retained, digest):
    require(mutation_identity in closure, 'Invalid mutation was not in actual Core source closure')
    sources = dict(closure)
    sources[mutation_identity] = digest
    return {'options': command['argv'][2:], 'tool_sha256': command['tool']['sha256'],
            'node_version': node_version, 'destination': str(destination), 'sources': sources,
            'retained_sources': {mutation_identity: str(retained)}}


def run_ordinary_products(context, seeds, budget, seconds, mutation):
    workspace, root = context['workspace'], context['directory'].parent
    node, compiler, runner = str(seeds['node'][0]), context['compiler'], context['runner']
    reference = lambda path: {'path': path.relative_to(root).as_posix(), 'sha256': bootstrap.digest(path)}
    result = {'workspace': workspace.relative_to(root).as_posix(),
              'sources': scenario_sources(None, workspace), 'products': {}}
    for package, group in (('Airship', None), ('Flat-Compiler', 'flatc')):
        relative = bootstrap.PRODUCTS[1 if package == 'Airship' else 0]
        destination = workspace / relative
        require(not destination.exists(), 'Ordinary product destination is not fresh')
        argv = [node, str(runner), '--use-links', '-f', str(workspace / 'packages' / package),
                '--flatc-runtime', 'flat', 'install'] + ([group] if group else []) + [
                '-t', 'es6', '--flatc-script', str(compiler)]
        command = product_command(context, argv, workspace, seeds, budget, seconds)
        require(command['exit_status'] == 0, 'Ordinary main build failed')
        require(destination.is_file(), 'Ordinary main product not generated')
        result['products'][package] = {'artifact': reference(destination), 'build': command}
    fresh_airship = workspace / bootstrap.PRODUCTS[1]
    fresh_compiler = workspace / bootstrap.PRODUCTS[0]
    # Invoke the generated ordinary Airship, not its test entrypoint, and make
    # it select the generated ordinary compiler through the public interface.
    argv = [node, str(fresh_airship), '--use-links', '-f', str(workspace / 'packages/Flat-Core'),
            '--flatc-runtime', 'flat', 'install', 'test', '-t', 'es6', '--flatc-script', str(fresh_compiler)]
    # Explicitly admit the freshly built compiler as an immutable compiler input
    # in this private policy only; never change the stage2 tool receipt.
    context['policy'].update(compiler=str(fresh_compiler), seed=str(fresh_compiler),
                            seedSha256=bootstrap.digest(fresh_compiler))
    bootstrap.write_json(context['directory'] / 'policy.json', context['policy'])
    result['core_install'] = product_command(context, argv, workspace, seeds, budget, seconds)
    require(result['core_install']['exit_status'] == 0, 'Ordinary Core install failed')
    admissions = admission_records(root / result['core_install']['journal']['path'],
                                   result['core_install']['admission_offset'])
    invocations = [entry['args'][1:] for entry in admissions
                   if entry.get('args') and entry['args'][0] == str(fresh_compiler)]
    require(invocations, 'Generated Airship did not execute generated ordinary compiler')
    closure = ordinary_source_closure(root / result['core_install']['log']['path'], workspace, result['sources'])
    result['node_runtime'] = observe_node_version(context, seeds, budget, seconds)
    result['core_run'] = product_command(context, [node, str(fresh_airship), '--use-links', '-f',
        str(workspace / 'packages/Flat-Core'), '--flatc-runtime', 'flat', 'run', 'test', '-t', 'es6'],
        workspace, seeds, budget, seconds)
    require(result['core_run']['exit_status'] == 0, 'Ordinary Core execution failed')
    core_dist = workspace / 'packages/Flat-Core/dist'
    tests = sorted(core_dist.rglob('test.js'))
    require(len(tests) == 1, 'Ambiguous ordinary Core test artifact')
    result['core_artifact'] = reference(tests[0])
    result['controls'] = {}
    original = (workspace / mutation).read_bytes()
    for name, suffix in INVALID_CONTROLS.items():
        (workspace / mutation).write_bytes(original + suffix.encode())
        control_sources = scenario_sources(None, workspace)
        destination = core_dist / ('invalid-' + name) / 'test.js'
        args = driver_arguments(invocations[-1], destination)
        try:
            command = product_command(context, [node, str(fresh_compiler)] + args,
                                      workspace / 'packages/Flat-Core', seeds, budget, seconds, expected_exit=1)
            # Retain actual invalid bytes and execution before interpreting the
            # diagnostic contract, including unsupported/incomplete failures.
            retained = context['directory'] / ('invalid-' + name + '.flat')
            shutil.copyfile(workspace / mutation, retained)
            result['controls'][name] = {'command': command,
                'mutation': mutation, 'source': reference(retained), 'sources': control_sources,
                'destination': str(destination), 'output_exists': destination.exists(),
                'output': reference(destination) if destination.is_file() else None}
            bootstrap.write_json(context['directory'] / 'ordinary-products.json', result)
            require(command['exit_status'] == 1, 'Invalid control unexpectedly succeeded')
            require(not destination.exists(), 'Invalid control published executable output')
            bindings = invalid_bindings(command, destination, closure, result['node_runtime']['version'],
                str(workspace / mutation), retained, bootstrap.digest(retained))
            result['controls'][name]['diagnostics'] = invalid_diagnostics(root / command['log']['path'], workspace, bindings)
        finally:
            (workspace / mutation).write_bytes(original)
    require(scenario_sources(None, workspace) == result['sources'], 'Ordinary product workload changed source inputs')
    return result


def validate_product_command(root, command, expected_exit, node_seed):
    node = Path(node_seed['path'])
    require(command.get('argv', [None])[0] == str(node)
            and command.get('node_sha256') == node_seed['sha256']
            and node.is_file() and bootstrap.digest(node) == node_seed['sha256'],
            'Ordinary command must select reviewed Node path and bytes')
    for name in ('log', 'journal', 'tool'):
        file_reference(root, command[name])
    argv = command.get('argv', [])
    require(type(command.get('admission_offset')) is int and command['admission_offset'] >= 0,
            'Invalid ordinary command admission boundary')
    require(type(command.get('exit_status')) is int and command['exit_status'] == expected_exit,
            'Invalid ordinary command exit status')
    require(len(argv) >= 2 and str(root / command['tool']['path']) == argv[1]
            and command.get('exit_status') == expected_exit and sha(command.get('node_sha256')),
            'Unbound ordinary product execution')
    cleanup = command.get('process_cleanup', {})
    require(type(cleanup.get('pid')) is int and cleanup['pid'] > 0 and cleanup.get('exit_code') == expected_exit
            and all(cleanup.get(name) is True for name in ('wait_completed', 'child_reaped', 'process_group_absent'))
            and command.get('temporary_outputs_after') == [], 'Ordinary product cleanup incomplete')
    return admission_records(root / command['journal']['path'], command['admission_offset'])


def validate_ordinary_products(root, value, baseline, tools, node_seed):
    require(isinstance(value, dict) and value.get('sources') == baseline, 'Missing/stale ordinary production products')
    workspace = root / value['workspace']
    require(root.resolve() in workspace.resolve().parents, 'Ordinary workspace escapes evidence root')
    require(scenario_sources(None, workspace) == baseline, 'Ordinary production sources changed')
    products = value.get('products', {})
    require(set(products) == {'Airship', 'Flat-Compiler'}, 'Missing ordinary main products')
    for package, group in (('Airship', None), ('Flat-Compiler', 'flatc')):
        relative = bootstrap.PRODUCTS[1 if package == 'Airship' else 0]
        product = products[package]
        file_reference(root, product['artifact'])
        require(str(root / product['artifact']['path']) == str(workspace / relative), 'Wrong ordinary product destination')
        command = product['build']
        admissions = validate_product_command(root, command, 0, node_seed)
        argv = command['argv']
        require(command['tool']['sha256'] == tools[bootstrap.PRODUCTS[1]]
                and '--flatc-script' in argv and '--flatc-runtime' in argv
                and argv[argv.index('--flatc-runtime') + 1] == 'flat'
                and argv[argv.index('-f') + 1] == str(workspace / 'packages' / package)
                and argv[argv.index('install') + 1:] == ([group] if group else []) + [
                    '-t', 'es6', '--flatc-script', argv[argv.index('--flatc-script') + 1]],
                'Ordinary main build selection mismatch')
        selected = Path(argv[argv.index('--flatc-script') + 1])
        require(bootstrap.digest(selected) == tools[bootstrap.PRODUCTS[0]]
                and any(str(selected) in entry.get('args', []) for entry in admissions),
                'Ordinary main build did not use pinned stage2 compiler')
    install = value['core_install']
    admissions = validate_product_command(root, install, 0, node_seed)
    fresh_compiler = products['Flat-Compiler']['artifact']
    fresh_airship = products['Airship']['artifact']
    compiler = str(root / fresh_compiler['path'])
    require(install['tool'] == fresh_airship and '--flatc-script' in install['argv']
            and install['argv'][install['argv'].index('-f') + 1] == str(workspace / 'packages/Flat-Core')
            and install['argv'][install['argv'].index('--flatc-runtime') + 1] == 'flat'
            and install['argv'][install['argv'].index('install') + 1] == 'test'
            and install['argv'][install['argv'].index('--flatc-script') + 1] == compiler,
            'Core build did not select fresh ordinary products')
    invocations = [entry['args'][1:] for entry in admissions if entry.get('args') and entry['args'][0] == compiler]
    require(invocations, 'No fresh ordinary compiler execution')
    closure = ordinary_source_closure(root / install['log']['path'], workspace, baseline)
    runtime = value['node_runtime']
    validate_product_command(root, runtime['command'], 0, node_seed)
    require(runtime['command']['argv'] == [install['argv'][0], str(root / runtime['command']['tool']['path'])],
            'Changed Node runtime probe argv')
    node_version = measured_node_version(root, runtime['command'])
    require(runtime.get('version') == node_version, 'Changed measured Node version')
    batches = diagnostic_batches(root / install['log']['path'], workspace,
                                  persistent_records(root / install['log']['path'], fresh_compiler['sha256']))
    require(len(batches) == len(invocations) and all(batch['complete'] for batch in batches),
            'Ordinary Core compilation diagnostics incomplete')
    run = value['core_run']
    executed = validate_product_command(root, run, 0, node_seed)
    file_reference(root, value['core_artifact'])
    require(workspace / 'packages/Flat-Core/dist' in (root / value['core_artifact']['path']).parents
            and Path(value['core_artifact']['path']).name == 'test.js'
            and run['tool'] == fresh_airship
            and run['argv'][run['argv'].index('-f') + 1] == str(workspace / 'packages/Flat-Core')
            and run['argv'][run['argv'].index('run') + 1] == 'test'
            and any(entry.get('args') and entry['args'][0] == str(root / value['core_artifact']['path'])
            for entry in executed), 'Ordinary Airship did not execute generated Core artifact')
    require(set(value.get('controls', {})) == set(INVALID_CONTROLS), 'Missing invalid production controls')
    for name, control in value['controls'].items():
        command = control['command']
        require(command.get('node_sha256') == install.get('node_sha256')
                and command['argv'][0] == install['argv'][0], 'Invalid control changed Node identity')
        validate_product_command(root, command, 1, node_seed)
        require(command['tool'] == fresh_compiler, 'Invalid control selected different compiler')
        mutation = mutation_path(control['mutation'], 'Flat-Core', baseline)
        file_reference(root, control['source'])
        expected = (workspace / mutation).read_bytes() + INVALID_CONTROLS[name].encode()
        require((root / control['source']['path']).read_bytes() == expected, 'Invalid source bytes do not match control')
        sources = dict(baseline, **{mutation: control['source']['sha256']})
        require(control.get('sources') == sources, 'Invalid control changed unrelated production inputs')
        destination = Path(control['destination'])
        require(control.get('output_exists') is False and control.get('output') is None,
                'Invalid control output was observed')
        require(workspace / 'packages/Flat-Core/dist' in destination.parents and not destination.exists()
                and command['argv'][2:] == driver_arguments(invocations[-1], destination),
                'Invalid control destination/arguments mismatch')
        bindings = invalid_bindings(command, destination, closure, node_version, str(workspace / mutation),
                                    root / control['source']['path'], control['source']['sha256'])
        require(control.get('diagnostics') == invalid_diagnostics(root / command['log']['path'], workspace, bindings),
                'Changed invalid-control diagnostic evidence')
    return True


def run_public_workload(context, name, seeds, budget, seconds):
    workspace, root = context['workspace'], context['directory'].parent
    package, group, filename = (('Airship', 'toolchain-test', 'toolchain-test.js') if name == 'airship-toolchain'
                                else ('Flat-Core', None, 'production-core-workload.js'))
    baseline = scenario_sources(None, workspace)
    original_manifest = None
    if name == 'core-lr':
        manifest = workspace / 'packages/Flat-Core/flat.json'
        original_manifest = context['directory'] / 'core-original-flat.json'
        shutil.copyfile(manifest, original_manifest)
        data = read_json(manifest)
        data['sources']['main'].update(mainClass='core/workloads/ProductionCoreWorkload', outFile=filename)
        bootstrap.write_json(manifest, data)
        source = workspace / 'packages/Flat-Core/workloads/ProductionCoreWorkload.flat'
        destination = workspace / 'packages/Flat-Core/src/core/workloads/ProductionCoreWorkload.flat'
        require(not destination.exists(), 'Core workload destination is not fresh')
        destination.parent.mkdir(parents=True, exist_ok=True)
        shutil.copyfile(source, destination)
    expected_sources = scenario_sources(None, workspace)
    destination = workspace / 'packages' / package / 'dist/es6' / filename
    require(not destination.exists(), 'Public workload artifact is not fresh')
    arguments = [str(seeds['node'][0]), str(context['runner']), '--use-links', '-f',
                 str(workspace / 'packages' / package), '--flatc-runtime', 'flat', 'install']
    arguments += ([group] if group else []) + ['-t', 'es6', '--flatc-script', str(context['compiler'])]
    build = product_command(context, arguments, workspace / 'packages' / package, seeds, budget, seconds)
    require(build['exit_status'] == 0 and destination.is_file(), 'Public workload build failed')
    reference = lambda path: {'path': path.relative_to(root).as_posix(), 'sha256': bootstrap.digest(path)}
    artifact = reference(destination)
    inventory = []
    if name == 'airship-toolchain':
        selected_airship = workspace / 'packages/Airship/dist/es6/selected-airship.js'
        require(not selected_airship.exists(), 'Explicit public Airship destination is not fresh')
        shutil.copyfile(context['runner'], selected_airship)
        require(bootstrap.digest(selected_airship) == bootstrap.digest(context['runner']),
                'Explicit public Airship copy changed')
        inventory = toolchain_inventory(workspace, destination)
        inventory += toolchain_cli_inventory(workspace, destination, selected_airship, context['compiler'])
        inventory += toolchain_compile_inventory(workspace, destination, selected_airship, context['compiler'])
        policy_file = context['directory'] / 'toolchain-policy.json'
        bootstrap.write_json(policy_file, dict(context['policy'], fixtures=inventory))
        context['env'] = dict(context['env'], FLAT_BOOTSTRAP_POLICY=str(policy_file),
                              AIRSHIP_TEST_FLATC_SCRIPT=str(context['compiler']),
                              AIRSHIP_TEST_AIRSHIP_SCRIPT=str(selected_airship))
    execute = product_command(context, [str(seeds['node'][0]), str(destination)],
                              workspace / 'packages' / package, seeds, budget, seconds)
    require(execute['exit_status'] == 0 and bootstrap.digest(destination) == artifact['sha256'],
            'Public workload execution failed or artifact changed')
    require(scenario_sources(None, workspace) == expected_sources, 'Public workload changed source inputs')
    source_file = context['directory'] / 'public-workload-sources.json'
    bootstrap.write_json(source_file, expected_sources)
    result = {'name': name, 'package': package, 'group': group, 'workspace': workspace.relative_to(root).as_posix(),
              'baseline': baseline, 'source_receipt': reference(source_file), 'artifact': artifact,
              'build': build, 'execute': execute, 'fixtures': inventory}
    if name == 'airship-toolchain':
        result['selected_tools'] = {'airship': reference(selected_airship), 'flatc': reference(context['compiler'])}
        result['fixture_scope'] = reference((root / execute['log']['path']).with_suffix('.log.scope.json'))
    else:
        result['original_manifest'] = reference(original_manifest)
        text = (root / execute['log']['path']).read_text()
        require('production-core-workload:ok' in text, 'Missing actual Core workload completion marker')
    return result


TOOLCHAIN_SOURCE_SHA256 = '6104ffeaa7bfee99ef07b850b0fa3dbb3f1396bc4921ce24a0e7c39439564486'


def toolchain_cli_inventory(workspace, runner, selected, compiler):
    source = workspace / 'packages/Airship/src/flat/airship/AirshipToolchain_Test.flat'
    require(bootstrap.digest(source) == TOOLCHAIN_SOURCE_SHA256,
            'Public toolchain source differs from reviewed CLI inventory')
    return [{'id': 'airship-cli-' + name, 'command': 'node', 'toolchainCli': True,
             'runner': str(runner), 'runnerSha256': bootstrap.digest(runner),
             'selected': str(selected), 'selectedSha256': bootstrap.digest(selected),
             'compiler': str(compiler), 'compilerSha256': bootstrap.digest(compiler),
             'operation': operation, 'sourceState': state, 'count': 1,
             'files': [{'path': str(source), 'sha256': TOOLCHAIN_SOURCE_SHA256}],
             'outcome': {'exit': 1, 'signal': None}}
            for name, operation, state in (
                ('fail', ['run', 'fail'], 'present'),
                ('invalid', ['install', 'main'], 'invalid'),
                ('unknown', ['install', 'not-a-source'], 'invalid'),
                ('missing', ['install', 'main'], 'absent-compiler'))]


def toolchain_compile_inventory(workspace, runner, selected, compiler):
    templates = toolchain_cli_inventory(workspace, runner, selected, compiler)
    return [{**templates[1], 'id': identifier, 'toolchainCli': False, 'toolchainCompile': True,
             'runner': str(executable), 'runnerSha256': bootstrap.digest(executable)}
            for identifier, executable in (('airship-compile-cli-invalid', selected),
                                           ('airship-compile-direct-invalid', runner))]


def toolchain_inventory(workspace, runner):
    source = workspace / 'packages/Airship/src/flat/airship/AirshipToolchain_Test.flat'
    selected = workspace / 'packages/Airship/dist/es6/selected-airship.js'
    return [{'id': identifier, 'command': 'node', 'args': ['-e', 'process.exit(7)'],
             'toolchainFixture': True, 'runner': str(executable), 'runnerSha256': bootstrap.digest(executable),
             'cwd': str(workspace / 'packages/Airship'), 'count': 1,
             'files': [{'path': str(source), 'sha256': bootstrap.digest(source)}],
             'outcome': {'exit': 7, 'signal': None}}
            for identifier, executable in (('airship-toolchain-fail', runner),
                                           ('airship-toolchain-cli-fail', selected))]


def validate_public_workloads(root, values, baseline, tools, node_seed):
    require(isinstance(values, dict) and set(values) == {'airship-toolchain', 'core-lr'},
            'Missing public production workloads')
    for name, value in values.items():
        require(value.get('name') == name and value.get('baseline') == baseline, 'Public workload baseline mismatch')
        workspace = root / value['workspace']
        require(root.resolve() in workspace.resolve().parents, 'Public workload workspace escapes receipt')
        file_reference(root, value['source_receipt'])
        sources = hash_manifest(read_json(root / value['source_receipt']['path']), 'public workload source')
        require(scenario_sources(None, workspace) == sources, 'Public workload retained sources changed')
        package, group, filename = (('Airship', 'toolchain-test', 'toolchain-test.js') if name == 'airship-toolchain'
                                    else ('Flat-Core', None, 'production-core-workload.js'))
        require(value.get('package') == package and value.get('group') == group, 'Public workload selection mismatch')
        compiler = workspace.parent / 'seeds/flatc.js'
        require(bootstrap.digest(compiler) == tools[bootstrap.PRODUCTS[0]], 'Public workload compiler mismatch')
        build, execute = value['build'], value['execute']
        admissions = validate_product_command(root, build, 0, node_seed)
        require(build['tool']['sha256'] == tools[bootstrap.PRODUCTS[1]]
                and build['argv'][2:] == ['--use-links', '-f', str(workspace / 'packages' / package),
                    '--flatc-runtime', 'flat', 'install'] + ([group] if group else [])
                    + ['-t', 'es6', '--flatc-script', str(compiler)], 'Public workload build selection mismatch')
        require(any(record.get('command') == node_seed['path'] and str(compiler) in record.get('args', [])
                    for record in admissions), 'Missing public workload compiler admission')
        file_reference(root, value['artifact'])
        destination = workspace / 'packages' / package / 'dist/es6' / filename
        require(str(root / value['artifact']['path']) == str(destination), 'Public workload artifact destination mismatch')
        validate_product_command(root, execute, 0, node_seed)
        require(execute['argv'] == [node_seed['path'], str(destination)] and execute['tool'] == value['artifact'],
                'Public workload execution identity mismatch')
        if name == 'airship-toolchain':
            selected = workspace / 'packages/Airship/dist/es6/selected-airship.js'
            require(value.get('selected_tools') == {
                        'airship': {'path': selected.relative_to(root).as_posix(), 'sha256': tools[bootstrap.PRODUCTS[1]]},
                        'flatc': {'path': compiler.relative_to(root).as_posix(), 'sha256': tools[bootstrap.PRODUCTS[0]]}},
                    'Explicit public toolchain selection mismatch')
            for tool in value['selected_tools'].values():
                file_reference(root, tool)
            require(sources == baseline and value['fixtures'] == toolchain_inventory(workspace, destination)
                    + toolchain_cli_inventory(workspace, destination, selected, compiler)
                    + toolchain_compile_inventory(workspace, destination, selected, compiler),
                    'Public toolchain fixture/source inventory changed')
            require(bootstrap.digest(workspace / 'packages/Airship/src/flat/airship/AirshipToolchain_Test.flat')
                    == baseline['packages/Airship/src/flat/airship/AirshipToolchain_Test.flat'],
                    'Toolchain fixture source differs from bootstrap')
            file_reference(root, value['fixture_scope'])
            scope = read_json(root / value['fixture_scope']['path'])
            events = scope.get('events', [])
            require(scope.get('schema') == 'flat.stage.process-scope.v1'
                    and scope.get('ownership') == 'external-command-group' and scope.get('scope_cancelled') is False
                    and scope.get('hello_count', 0) > 0 and scope.get('frame_count') == len(events)
                    and scope.get('fixtures_started') == scope.get('fixtures_finished') == 8
                    and scope.get('cleanup') == execute['process_cleanup']
                    and sorted((event.get('fixture'), event.get('outcome', {}).get('exit'))
                               for event in events if event.get('kind') == 'fixture-end'
                               and event.get('outcome', {}).get('signal') is None)
                        == sorted([('airship-toolchain-cli-fail', 7), ('airship-toolchain-fail', 7)]
                                  + [('airship-cli-' + name, 1) for name in ('fail', 'invalid', 'unknown', 'missing')]
                                  + [('airship-compile-cli-invalid', 1), ('airship-compile-direct-invalid', 1)]),
                    'Missing actual toolchain fixture outcome')
        else:
            changed = {'packages/Flat-Core/flat.json',
                       'packages/Flat-Core/src/core/workloads/ProductionCoreWorkload.flat'}
            require({key for key in set(sources) | set(baseline) if sources.get(key) != baseline.get(key)} == changed,
                    'Core workload changed unrelated production inputs')
            require(file_reference(root, value['original_manifest']) == baseline['packages/Flat-Core/flat.json'],
                    'Core original manifest differs from bootstrap')
            expected_manifest = read_json(root / value['original_manifest']['path'])
            expected_manifest['sources']['main'].update(mainClass='core/workloads/ProductionCoreWorkload', outFile=filename)
            core_manifest = read_json(workspace / 'packages/Flat-Core/flat.json')
            require(core_manifest == expected_manifest, 'Core workload changed unrelated manifest options')
            require(core_manifest.get('sources', {}).get('main', {}).get('mainClass') == 'core/workloads/ProductionCoreWorkload'
                    and core_manifest['sources']['main'].get('outFile') == filename,
                    'Core workload manifest selection mismatch')
            require(sources[next(key for key in changed if key.endswith('.flat'))]
                    == baseline['packages/Flat-Core/workloads/ProductionCoreWorkload.flat'], 'Core workload source differs')
            require('production-core-workload:ok' in (root / execute['log']['path']).read_text(),
                    'Missing actual Core workload completion marker')


def produce_evidence(repo, output, stages, seeds_file, review_file, review_sha, changed, removed,
                     seconds=600, run_seconds=10800, scaling_plan_path=None, scaling_plan_sha=None):
    """Execute supported production paths; never manufacture unavailable summary observations."""
    require(output.resolve() != stages.resolve() and output.resolve() not in stages.resolve().parents
            and stages.resolve() not in output.resolve().parents, 'Production and bootstrap outputs must be disjoint')
    tools = validate_bootstrap(stages, require_public=True, review_sha256=review_sha)
    if scaling_plan_path is not None:
        scaling_plan(scaling_plan_path, scaling_plan_sha, bootstrap.digest(stages / 'sources.json'))
    review = bootstrap.reviewed_seeds(seeds_file, review_file, review_sha)
    require(review == read_json(stages / 'review.json')
            and read_json(seeds_file) == read_json(stages / 'seeds.json'), 'Reviewed bootstrap inputs differ')
    entries = bootstrap.reviewed_json(seeds_file, review['seeds_sha256'], 'Reviewed seed manifest')
    seeds = {name: (bootstrap.verified_seed(entries[name]), entries[name]['sha256'])
             for name in ('node', 'git')}
    sources = read_json(stages / 'sources.json')
    require(scenario_sources(None, repo) == sources, 'Live sources differ from bootstrap')
    mutation_path(changed, 'Flat-Core', sources)
    mutation_path(removed, 'Flat-Core', sources)
    require(changed != removed, 'Changed and removed inputs must differ')
    bootstrap.execution_limits(run_seconds, seconds)
    bootstrap.fresh_directory(output)
    budget = bootstrap.RunBudget(output, seconds=run_seconds)
    receipt = {'schema': 2, 'producer': 'dogfood_acceptance.py', 'status': 'running',
               'source_manifest_sha256': bootstrap.digest(stages / 'sources.json'), 'tools': tools,
               'mutations': {'changed': changed, 'removed': removed},
               'options': {'target': 'es6', 'flatc_runtime': 'flat'}, 'workloads': {},
               'blockers': [], 'full_acceptance': False}
    try:
        bootstrap.observe_subprocess_cleanup(output / 'watchdog-capability')
        receipt['watchdog_capability'] = watchdog_capability(output / 'watchdog-capability/subprocess-cleanup.json')
        receipt['watchdog_capability']['harness_sha256'] = bootstrap.digest(Path(bootstrap.__file__))
        ordinary_context = create_production_workspace(repo, output / 'ordinary-products', sources, seeds,
                                                         tools, stages / 'stage2', budget)
        receipt['public_workloads'] = {}
        for public_name in ('airship-toolchain', 'core-lr'):
            public_context = create_production_workspace(repo, output / ('public-' + public_name), sources,
                                                         seeds, tools, stages / 'stage2', budget)
            receipt['public_workloads'][public_name] = run_public_workload(public_context, public_name,
                                                                          seeds, budget, seconds)
            bootstrap.write_json(output / 'production-evidence.json', receipt)
        receipt['ordinary_products'] = run_ordinary_products(ordinary_context, seeds, budget, seconds, changed)
        bootstrap.write_json(output / 'production-evidence.json', receipt)
        live = create_production_workspace(repo, output / 'incremental', sources, seeds, tools, stages / 'stage2', budget)
        # Airship supplies the production source closure once. Every subsequent
        # workload invokes that ordinary CLI directly so an install skip cannot
        # masquerade as persistent compiler reuse.
        admitted = None
        for name in SCENARIOS:
            receipt['workloads'][name] = {'status': 'running', 'workspace': 'incremental/workspace'}
            bootstrap.write_json(output / 'production-evidence.json', receipt)
            if name == 'changed':
                mutate_incremental_source(live['workspace'], changed)
            if name == 'removed':
                shutil.copyfile(repo / changed, live['workspace'] / changed)
                mutate_incremental_source(live['workspace'], removed, remove=True)
            expected_sources = scenario_sources(None, live['workspace'])
            if admitted is None:
                measured = execute_production(live, 'Flat-Core', seeds, budget, seconds)
                install = measured['commands'][0]
                admissions = admission_records(output / install['admission_reference']['path'],
                                              install['admission_offset'])
                invocations = [[str(seeds['node'][0])] + record['args'] for record in admissions
                               if record.get('args') and record['args'][0] == str(live['compiler'])]
                require(invocations, 'Cold install did not admit the ordinary compiler CLI')
                admitted = invocations[-1]
                require(any(str(live['workspace'] / 'packages/Flat-Core/dist') in argument
                            for argument in admitted), 'Last admitted CLI did not build Core output')
            else:
                measured = execute_incremental_cli(live, admitted, seeds, budget, seconds)
            receipt['workloads'][name]['retained_output'] = measured
            bootstrap.write_json(output / 'production-evidence.json', receipt)
            require(scenario_sources(None, live['workspace']) == expected_sources, 'Build mutated production inputs')
            clean = create_production_workspace(repo, output / f'clean-{name}', sources, seeds, tools,
                                                 stages / 'stage2', budget)
            if name == 'changed':
                mutate_incremental_source(clean['workspace'], changed)
            if name == 'removed':
                mutate_incremental_source(clean['workspace'], removed, remove=True)
            require(scenario_sources(None, clean['workspace']) == expected_sources, 'Comparison inputs differ')
            rebuilt = execute_incremental_cli(clean, incremental_arguments(admitted, live, clean),
                                              seeds, budget, seconds)
            receipt['workloads'][name] = compare_incremental_workload(name, measured, rebuilt, expected_sources)
            bootstrap.write_json(output / 'production-evidence.json', receipt)
        receipt['invalidations'] = {}
        run_incremental_invalidations(repo, output, stages, sources, seeds, tools, budget, seconds,
                                      live, admitted, receipt)
        receipt['read_concurrency'] = read_concurrency_probe(live, 'Flat-Core', seeds, budget, seconds)
        compiler_context = create_production_workspace(repo, output / 'compiler-lifecycle', sources, seeds,
                                                        tools, stages / 'stage2', budget)
        receipt['compiler_suite'] = execute_production(compiler_context, 'Flat-Compiler', seeds, budget, seconds)
        emitted = []
        for command in compiler_context['commands']:
            emitted.extend(compiler_session_records(compiler_context['directory'] / command['log']))
        receipt['compiler_lifecycle'] = [value for command in compiler_context['commands']
                                         for value in command.get('lifecycle', [])]
        if not receipt['compiler_lifecycle']:
            receipt['blockers'].append('Generated compiler suite emitted no measured lifecycle records.')
        receipt['subprocess_cleanup'] = {'scope': 'per-production-command',
            'observations': 'commands[].cleanup_reference',
            'unobserved': ['production-cancellation', 'temporary-output-created-then-removed'],
            'reason': 'Terminal process waits and staging absence are measured; owner readiness/cancellation and temporary lifecycle events are not exported'}
        receipt['compiler_sessions'] = emitted
        receipt['compiler_session_log_sha256'] = {
            command['log']: command['log_sha256'] for command in compiler_context['commands']}
        if not emitted:
            receipt['blockers'].append('Generated Flat-Compiler suite emitted no CompilerEvidence.render session records.')
        else:
            receipt['session_observations'] = {
                'cleanup_unsettled_records': sum(not value['cleanupSettled'] for value in emitted),
                'cancelled_records': sum(value['cancelled'] for value in emitted),
                'in_process_reuse_records': sum(value['reuse']['reused'] for value in emitted),
                'scope': 'actual emitted sessions; no inferred workload identity or persistent cache acceptance'}
            require(all(value['cleanupSettled'] for value in emitted), 'Compiler session cleanup did not settle')
        suite_contexts = {'Flat-Compiler': compiler_context}
        receipt['stage2_suites'] = {'Flat-Compiler': receipt['compiler_suite']}
        for suite in SUITES:
            if suite == 'Flat-Compiler':
                continue
            suite_context = create_production_workspace(repo, output / ('suite-' + suite), sources, seeds,
                                                        tools, stages / 'stage2', budget)
            suite_contexts[suite] = suite_context
            receipt['stage2_suites'][suite] = execute_production(suite_context, suite, seeds, budget, seconds)
            bootstrap.write_json(output / 'production-evidence.json', receipt)
        require(scenario_sources(None, repo) == sources, 'Live source inputs changed during production run')
        driver_context = create_production_workspace(repo, output / 'production-driver', sources, seeds,
                                                      tools, stages / 'stage2', budget)
        receipt['production_driver'] = run_production_driver(driver_context, seeds, budget, seconds)
        if scaling_plan_path is not None:
            suite_contexts['Flat-Core'] = driver_context
            driver_path = output / receipt['production_driver']['driver']['path']
            receipt['scaling'] = run_scaling(driver_context, driver_path, suite_contexts, seeds, budget, seconds,
                                              scaling_plan_path, scaling_plan_sha,
                                              bootstrap.digest(stages / 'sources.json'))
        receipt.update(production_gates(receipt))
        bootstrap.write_json(output / 'production-evidence.json', receipt)
        verdict = validate_production_evidence(output / 'production-evidence.json', receipt, stages,
                                               scaling_plan_sha, review_sha)
        receipt.update({key: verdict[key] for key in ('gates', 'blockers', 'full_acceptance', 'status')})
        return receipt
    except BaseException as error:
        receipt.update(status='failed', error=str(error))
        raise
    finally:
        bootstrap.write_json(output / 'production-evidence.json', receipt)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    for name in ('repo', 'output', 'seeds', 'review'):
        parser.add_argument('--' + name, required=True, type=Path)
    parser.add_argument('--evidence', type=Path)
    parser.add_argument('--produce', action='store_true',
                        help='Execute actual production workloads; emits blocked receipt for unavailable contracts')
    parser.add_argument('--scaling-plan', type=Path, help='Predeclared production tiers and pinned RSS timer/budgets')
    parser.add_argument('--scaling-plan-sha256', help='Independent reviewed scaling plan digest')
    parser.add_argument('--changed-input', help='Existing Flat-Core production source for controlled byte change')
    parser.add_argument('--removed-input', help='Reviewed removable Flat-Core production source')
    parser.add_argument('--bootstrap-output', type=Path,
                        help='Consume an existing completed stage_bootstrap output instead of rerunning it')
    parser.add_argument('--review-sha256', required=True)
    parser.add_argument('--evidence-sha256')
    parser.add_argument('--run-seconds', type=int, default=10800)
    parser.add_argument('--command-seconds', type=int, default=600)
    args = parser.parse_args()
    repo, output = args.repo.resolve(), args.output.resolve()
    require(repo not in output.parents and output != repo and output not in repo.parents,
            'Output must be disjoint from checkout')
    bootstrap.execution_limits(args.run_seconds, args.command_seconds)
    require(args.scaling_plan is None or args.scaling_plan_sha256, 'Scaling plan requires independent digest')
    if args.produce:
        require(args.bootstrap_output is not None and args.changed_input and args.removed_input,
                '--produce requires --bootstrap-output, --changed-input, and --removed-input')
        receipt = produce_evidence(repo, output, args.bootstrap_output.resolve(), args.seeds.resolve(),
                         args.review.resolve(), args.review_sha256, args.changed_input, args.removed_input,
                         args.command_seconds, args.run_seconds,
                         scaling_plan_path=args.scaling_plan, scaling_plan_sha=args.scaling_plan_sha256)
        if receipt['status'] != 'passed':
            raise SystemExit('Production workloads completed with unobserved gates; see production-evidence.json')
        return
    require(args.evidence is not None and args.evidence_sha256,
            'Validation requires --evidence and --evidence-sha256')
    # Reject missing evidence up front, rather than spend hours then discover provisioning is absent.
    read_json(args.evidence)
    require(sha(args.evidence_sha256) and bootstrap.digest(args.evidence) == args.evidence_sha256,
            'Evidence receipt digest mismatch')
    bootstrap.fresh_directory(output)
    receipt = {'schema': 1, 'status': 'running'}
    try:
        command = [sys.executable, '-B', str(Path(bootstrap.__file__).resolve()),
                   '--repo', str(repo), '--output', str(output / 'bootstrap'),
                   '--seeds', str(args.seeds.resolve()), '--review', str(args.review.resolve()),
                   '--review-sha256', args.review_sha256, '--run-seconds', str(args.run_seconds),
                   '--command-seconds', str(args.command_seconds)]
        budget = bootstrap.RunBudget(output, seconds=args.run_seconds)
        stages = args.bootstrap_output.resolve() if args.bootstrap_output else output / 'bootstrap'
        if not args.bootstrap_output:
            bootstrap.run_command(command, repo, dict(os.environ), output / 'bootstrap.log',
                                  output / 'orchestrator-journal.jsonl', timeout=args.run_seconds, budget=budget)
        else:
            review = bootstrap.reviewed_seeds(args.seeds, args.review, args.review_sha256)
            require(review == read_json(stages / 'review.json'), 'Bootstrap seed review differs')
            require(read_json(args.seeds) == read_json(stages / 'seeds.json'), 'Bootstrap seeds differ')
        require(bootstrap.inventory(repo / 'packages') == {
            name.removeprefix('packages/'): digest
            for name, digest in read_json(stages / 'sources.json').items()},
            'Current production sources differ from accepted bootstrap snapshot')
        receipt = validate_evidence(args.evidence.resolve(), args.evidence_sha256, stages,
                                    args.scaling_plan_sha256, args.review_sha256)
        budget.check()
        require(receipt.get('status') == 'passed',
                'Full acceptance blocked by unsupported producer contracts; hand-authored observations cannot close these gates')
    except BaseException as error:
        receipt.update(status='failed', error=str(error))
        raise
    finally:
        bootstrap.write_json(output / 'dogfood-acceptance.json', receipt)


if __name__ == '__main__':
    main()

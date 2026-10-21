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
    return json.loads(path.read_text(), object_pairs_hook=unique)


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
        require(isinstance(path, str) and bool(path) and not PurePosixPath(path).is_absolute()
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


def validate_bootstrap(stages):
    """Consume F's schema-2 receipt; never infer success from directory presence."""
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


def validate_evidence(path, expected, stages):
    require(sha(expected) and bootstrap.digest(path) == expected, 'Evidence receipt digest mismatch')
    value = read_json(path)
    if value.get('schema') == 2:
        return validate_production_evidence(path, value, stages)
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
    commands = [command for run in suites.values() for command in run.get('commands', [])]
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
    gates['production-workload-controlled-schedules'] = len(driver_runs) == 8 and all(
        (scenario, capacity) in {(run.get('scenario'), run.get('capacity')) for run in driver_runs}
        for scenario in ('forward', 'reverse', 'cancel-after-read', 'cancel-after-stage') for capacity in (1, 3))
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


def validate_production_run(root, run, compiler_sha):
    """Re-read pinned artifacts and logs; serialized observations are not authority."""
    require(isinstance(run, dict) and run.get('status') == 'passed', 'Incomplete production run')
    file_reference(root, run['source_receipt'])
    observation = (root / run['source_receipt']['path']).parent
    sources = read_json(observation / 'observed-sources.json')
    require(fingerprint(sources) == run.get('sources_sha256'), 'Stale production source inventory')
    workspace_name = PurePosixPath(run.get('workspace', ''))
    require(workspace_name.parts and not workspace_name.is_absolute() and '..' not in workspace_name.parts,
            'Invalid production workspace identity')
    workspace = root.joinpath(*workspace_name.parts)
    artifacts = run.get('artifact_references')
    require(isinstance(artifacts, dict) and set(artifacts) == set(run.get('artifacts', {})),
            'Incomplete retained artifacts')
    for name, reference in artifacts.items():
        file_reference(root, reference)
        require(reference['sha256'] == run['artifacts'][name], 'Production artifacts changed')
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
        require(command.get('status') == 'passed' and command.get('exit_status') == 0,
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
            require(count == command.get('compiler_invocations'), 'Compiler admission count mismatch')
        else:
            require(any(any(str(argument).endswith('/test.js') for argument in record.get('args', []))
                        for record in admissions), 'Missing generated suite execution admission')
        require(command.get('persistent') == persistent_records(log, compiler_sha), 'Persistent observations changed')
        require(command.get('diagnostic_batches') == diagnostic_batches(log, workspace, command['persistent']),
                'Diagnostic observations changed')
        require(command.get('compiler_sessions') == compiler_session_records(log), 'Session observations changed')
        require(command.get('lifecycle') == lifecycle_records(log), 'Lifecycle observations changed')
    return sources


def validate_production_evidence(path, value, stages):
    tools = validate_bootstrap(stages)
    require(value.get('producer') == 'dogfood_acceptance.py' and value.get('tools') == tools,
            'Production evidence tool identity mismatch')
    require(value.get('source_manifest_sha256') == bootstrap.digest(stages / 'sources.json'),
            'Production evidence source snapshot mismatch')
    require(value.get('options') == {'target': 'es6', 'flatc_runtime': 'flat'}, 'Production options mismatch')
    root = path.parent.resolve()
    baseline = read_json(stages / 'sources.json')
    workloads = value.get('workloads', {})
    require(set(workloads) == set(SCENARIOS), 'Incomplete production scenarios')
    manifests = {}
    for name in SCENARIOS:
        workload = workloads[name]
        sources = validate_production_run(root, workload['retained_output'], tools[bootstrap.PRODUCTS[0]])
        clean = validate_production_run(root, workload['clean'], tools[bootstrap.PRODUCTS[0]])
        require(sources == clean == workload.get('source_manifest'), 'Comparison input mismatch')
        manifests[name] = sources
    require(manifests['cold'] == manifests['warm'] == baseline, 'Cold/warm source mismatch')
    require(set(manifests['changed']) == set(baseline) and manifests['changed'] != baseline,
            'Changed scenario did not change source bytes')
    require(set(manifests['removed']) < set(baseline)
            and all(baseline[key] == digest for key, digest in manifests['removed'].items()),
            'Removed scenario did not remove source inputs')
    suites = value.get('stage2_suites', {})
    require(set(suites) == set(SUITES), 'Missing production suite')
    for suite, run in suites.items():
        require(run.get('package') == suite, 'Production suite identity mismatch')
        require(validate_production_run(root, run, tools[bootstrap.PRODUCTS[0]]) == baseline,
                'Suite source snapshot mismatch')
    driver = value.get('production_driver')
    if driver is not None:
        validate_driver_evidence(root, driver, fingerprint(baseline), tools[bootstrap.PRODUCTS[0]])
    if value.get('scaling') is not None:
        require(driver is not None, 'Scaling requires pinned production driver')
        require(value['scaling'].get('compiler_sha256') == tools[bootstrap.PRODUCTS[0]],
                'Scaling ordinary baseline selected different compiler bytes')
        validate_scaling(root, value['scaling'], value['source_manifest_sha256'], baseline, driver['driver']['sha256'])
    return dict(schema=2, evidence_sha256=bootstrap.digest(path), **production_gates(value))


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
            'journal': journal, 'env': env, 'commands': [], 'policy': policy}


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
        try:
            bootstrap.run_command(argv, workspace / 'packages' / package, context['env'], log,
                                  context['journal'], timeout=seconds, budget=budget,
                                  cleanup_observation=cleanup)
        finally:
            # Persist measured process termination even on a failed command; a
            # failed/partial run never becomes successful acceptance evidence.
            command['process_cleanup'] = cleanup
            command['temporary_outputs_after'] = temporary_output_inventory(workspace)
            bootstrap.write_json(directory / 'commands.json', context['commands'])
        records = admission_records(context['journal'], offset)
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
    records = [record for command in measured['commands'] for record in command.get('persistent', [])]
    if not records:
        return {'proven': False, 'blocker': 'No actual persistent CLI records emitted'}
    if name == 'warm':
        proven = all(value['reused'] and value['reason'] == 'exact-hit' for value in records)
    elif name == 'cold':
        proven = all(not value['reused'] and value['reason'] == 'cold' for value in records)
    else:
        key = 'changedSources' if name == 'changed' else 'removedSources'
        proven = any(not value['reused'] and value[key] > 0 for value in records)
    return {'proven': proven, 'scope': 'persistent-cli-package', 'records': records}


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
    require(sha(expected) and bootstrap.digest(path) == expected, 'Scaling plan digest mismatch')
    value = read_json(path)
    require(value.get('schema') == 'flat.dogfood.scaling-plan.v1'
            and value.get('source_manifest_sha256') == source_sha, 'Stale scaling plan')
    tiers = value.get('tiers')
    require(isinstance(tiers, list) and 3 <= len(tiers) <= 8
            and len({tier.get('package') for tier in tiers}) == len(tiers), 'Three distinct production tiers required')
    for tier in tiers:
        require(tier.get('package') in ('Flat-Core',) + SUITES, 'Unknown production scaling package')
        for name in ('min_source_bytes', 'min_sources', 'max_rss_bytes'):
            require(type(tier.get(name)) is int and tier[name] > 0, 'Explicit positive scaling budgets required')
    for name in ('max_rss_growth_numerator', 'max_rss_growth_denominator', 'min_input_growth_factor'):
        require(type(value.get(name)) is int and value[name] > 0, 'Explicit scaling growth budget required')
    require(value['min_input_growth_factor'] >= 2, 'Scaling input tiers must materially grow')
    timer = value.get('timer', {})
    require(timer.get('kind') in ('bsd-time', 'gnu-time') and isinstance(timer.get('path'), str)
            and Path(timer['path']).is_absolute() and sha(timer.get('sha256')), 'Pinned RSS timer required')
    return value


def rss_bytes(path, kind):
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


def run_scaling(context, driver, contexts, seeds, budget, seconds, plan_path, plan_sha, source_sha):
    plan = scaling_plan(plan_path, plan_sha, source_sha)
    root, workspace = context['directory'].parent, context['workspace']
    # Pin the reviewed plan before the first measurement; never derive budgets
    # from measured peaks. Package choices are actual admitted production builds.
    saved = context['directory'] / 'scaling-plan.json'
    shutil.copyfile(plan_path, saved)
    reference = lambda path: {'path': path.relative_to(root).as_posix(), 'sha256': bootstrap.digest(path)}
    result = {'plan': reference(saved), 'compiler_sha256': context['policy']['seedSha256'], 'tiers': []}
    timer = Path(plan['timer']['path'])
    for index, tier in enumerate(plan['tiers']):
        require(not timer.is_symlink() and bootstrap.digest(timer) == plan['timer']['sha256'], 'RSS timer identity changed')
        package = tier['package']
        # Materialize the ordinary local dependency closure through pinned
        # Airship, not copied/missing generated dependencies from another root.
        ordinary = execute_production(context, package, seeds, budget, seconds)
        arguments = observed_compiler_arguments(context)
        require(bootstrap.digest(driver) == context['driver_sha256']
                and bootstrap.digest(seeds['node'][0]) == seeds['node'][1], 'Scaling tool identity changed')
        destination = workspace / 'packages' / package / 'dist' / f'scaling-{index}/test.js'
        require(not destination.exists(), 'Scaling destination is not cold')
        arguments = driver_arguments(arguments, destination)
        log = context['directory'] / f'logs/scaling-{index}.log'
        rss = context['directory'] / f'logs/scaling-{index}.rss'
        timer_args = ['-l', '-o', str(rss)] if plan['timer']['kind'] == 'bsd-time' else ['-f', 'flat-maxrss-kib=%M', '-o', str(rss)]
        bootstrap.run_command([str(timer)] + timer_args + [str(seeds['node'][0]), str(driver), 'forward', '3'] + arguments,
                              workspace / 'packages' / package, context['env'], log, context['journal'],
                              timeout=seconds, budget=budget)
        admission_records(context['journal'])
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
        result['tiers'].append({'package': package, 'arguments': arguments, 'destination': str(destination),
            'log': reference(log), 'rss': reference(rss), 'rss_bytes': rss_bytes(rss, plan['timer']['kind']),
            'ordinary': ordinary,
            'inputs': inputs, 'report': report, 'artifact': reference(destination)})
    return result


def validate_scaling(root, value, source_sha, baseline, driver_sha):
    require(isinstance(value, dict), 'Missing measured production scaling')
    file_reference(root, value['plan'])
    plan = scaling_plan(root / value['plan']['path'], value['plan']['sha256'], source_sha)
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
        for name, entry in inputs.items():
            require(file_reference(root, entry['reference']) == entry.get('sha256')
                    and (root / entry['reference']['path']).stat().st_size == entry.get('bytes'),
                    'Scaling input size/content changed')
            require(baseline.get(name) == entry.get('sha256') and type(entry.get('bytes')) is int and entry['bytes'] > 0,
                    'Scaling input not in admitted production snapshot')
        size = sum(entry['bytes'] for entry in inputs.values())
        peak = rss_bytes(root / tier['rss']['path'], plan['timer']['kind'])
        require(size >= expected['min_source_bytes'] and len(inputs) >= expected['min_sources'],
                'Scaling workload below predeclared size')
        require(peak == tier.get('rss_bytes') and peak <= expected['max_rss_bytes'], 'Scaling RSS budget exceeded')
        sizes.append(size)
        peaks.append(peak)
    require(all(right >= left * plan['min_input_growth_factor'] for left, right in zip(sizes, sizes[1:])),
            'Production scaling tiers do not materially grow')
    require(all(right * plan['max_rss_growth_denominator'] <= left * plan['max_rss_growth_numerator']
                for left, right in zip(peaks, peaks[1:])), 'Production memory growth budget exceeded')
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
            admitted_count += 1
        elif transition == 'started':
            require(identity in window and identity not in active, 'Read started without admission')
            active.add(identity)
        elif transition == 'settled':
            require(identity in active and identity not in settled, 'Read settled without active work')
            active.remove(identity)
            settled.append(identity)
            settled_count += 1
            if not scenario.startswith('cancel-'):
                order = list(reversed(window)) if scenario == 'reverse' else window
                require(settled == order[:len(settled)], 'Production settlement order mismatch')
        elif transition == 'consumed':
            require(identity in window, 'Read consumed without admission')
        elif transition in ('cancel-readiness-after-read', 'cancel-readiness-after-stage'):
            cancellation_readiness.append(transition)
        else:
            raise ValueError('Unknown production read transition')
        require(event['admitted'] == admitted_count and event['settled'] == settled_count
                and event['active'] == len(active), 'Inconsistent production event counters')
        digest.update(json.dumps([transition, identity], ensure_ascii=False, separators=(',', ':')).encode() + b'\n')
    require(report.get('eventDigest') == 'sha256:' + digest.hexdigest(), 'Production event digest mismatch')
    for name in ('admitted', 'settled', 'activeAfter', 'retainedAfter', 'peakActive', 'peakRetained', 'exitCode'):
        require(type(report.get(name)) is int and report[name] >= 0, 'Invalid terminal read measurement')
    require(report['admitted'] == admitted_count == report['settled'] == settled_count
            and admitted_count > 0 and not active and report['activeAfter'] == report['retainedAfter'] == 0
            and 0 < report['peakActive'] <= capacity and 0 < report['peakRetained'] <= capacity,
            'Production reads incomplete or capacity exceeded')
    cancelled = scenario.startswith('cancel-')
    require(report.get('cancellationRequested') is cancelled
            and report['exitCode'] == (1 if cancelled else 0), 'Production cancellation/exit mismatch')
    if cancelled:
        transition = 'cancel-readiness-after-read' if scenario == 'cancel-after-read' else 'cancel-readiness-after-stage'
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
    for scenario in ('forward', 'reverse', 'cancel-after-read', 'cancel-after-stage'):
        for capacity in (1, 3):
            require(bootstrap.digest(driver) == driver_sha and bootstrap.digest(seeds['node'][0]) == seeds['node'][1],
                    'Production driver input tool changed')
            destination = workspace / 'packages/Flat-Core/dist/production-driver' / f'{scenario}-{capacity}/test.js'
            require(not destination.exists(), 'Controlled driver destination is not cold')
            argv = driver_arguments(package_args, destination)
            log = directory / f'logs/driver-{scenario}-{capacity}.log'
            cleanup = {}
            try:
                bootstrap.run_command([str(seeds['node'][0]), str(driver), scenario, str(capacity)] + argv,
                                      workspace / 'packages/Flat-Core', context['env'], log, context['journal'],
                                      timeout=seconds, budget=budget, cleanup_observation=cleanup)
            except bootstrap.CommandFailure as error:
                if not scenario.startswith('cancel-') or error.exit_code != 1:
                    raise
            admission_records(context['journal'])
            report = production_read_records(log, argv, scenario, capacity, driver_sha, destination)
            outputs = {}
            if not scenario.startswith('cancel-'):
                require(destination.is_file(), 'Production schedule did not publish output')
                outputs[destination.name] = reference(destination)
            artifact_lifecycle(log, report, scenario != 'cancel-after-read')
            manifest_records = manifest_lifecycle(log, report)
            result['runs'].append({'scenario': scenario, 'capacity': capacity, 'arguments': argv,
                'manifest_lifecycle': manifest_records,
                'inputs': driver_source_inputs(log, workspace, 'Flat-Core', evidence_root),
                'artifact_lifecycle': schema_log_records(log, 'flat.compiler.artifact-lifecycle.v1'),
                'destination': str(destination), 'log': reference(log), 'report': report,
                'process_cleanup': cleanup, 'temporary_outputs_after': temporary_output_inventory(workspace),
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


def validate_driver_evidence(root, value, sources_sha, compiler_sha):
    require(isinstance(value, dict) and value.get('source_manifest_sha256') == sources_sha
            and value.get('compiler_sha256') == compiler_sha, 'Stale production driver source/tool identity')
    require(value.get('build_source_manifest') and fingerprint(value['build_source_manifest']) == sources_sha,
            'Production driver build source identity mismatch')
    driver_sha = file_reference(root, value['driver'])
    require(file_reference(root, value['compiler']) == compiler_sha, 'Production driver builder bytes changed')
    file_reference(root, value['build_log'])
    runs = value.get('runs')
    expected = {(scenario, capacity) for scenario in ('forward', 'reverse', 'cancel-after-read', 'cancel-after-stage')
                for capacity in (1, 3)}
    require(isinstance(runs, list) and len(runs) == len(expected)
            and {(run.get('scenario'), run.get('capacity')) for run in runs} == expected,
            'Incomplete production driver scenario matrix')
    outputs, diagnostics = [], []
    for run in runs:
        file_reference(root, run['log'])
        report = production_read_records(root / run['log']['path'], run['arguments'], run['scenario'],
                                         run['capacity'], driver_sha, Path(run['destination']))
        require(report == run.get('report'), 'Changed production driver report')
        inputs = run.get('inputs')
        require(isinstance(inputs, dict) and len(inputs) == report['admitted'], 'Missing production driver source identities')
        for name, entry in inputs.items():
            require(value['build_source_manifest'].get(name) == entry.get('sha256')
                    and file_reference(root, entry['reference']) == entry['sha256'], 'Stale production driver source content')
        process = run.get('process_cleanup', {})
        require(type(process.get('pid')) is int and process['pid'] > 0
                and process.get('exit_code') == report['exitCode']
                and all(process.get(name) is True for name in ('wait_completed', 'child_reaped', 'process_group_absent')),
                'Production driver process cleanup incomplete')
        require(run.get('temporary_outputs_after') == [], 'Production driver left temporary outputs')
        require(run.get('manifest_lifecycle') == manifest_lifecycle(root / run['log']['path'], report),
                'Changed manifest lifecycle observations')
        artifact_lifecycle(root / run['log']['path'], report, run['scenario'] != 'cancel-after-read')
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


def produce_evidence(repo, output, stages, seeds_file, review_file, review_sha, changed, removed,
                     seconds=600, run_seconds=10800, scaling_plan_path=None, scaling_plan_sha=None):
    """Execute supported production paths; never manufacture unavailable summary observations."""
    tools = validate_bootstrap(stages)
    if scaling_plan_path is not None:
        scaling_plan(scaling_plan_path, scaling_plan_sha, bootstrap.digest(stages / 'sources.json'))
    review = bootstrap.reviewed_seeds(seeds_file, review_file, review_sha)
    require(review == read_json(stages / 'review.json')
            and read_json(seeds_file) == read_json(stages / 'seeds.json'), 'Reviewed bootstrap inputs differ')
    entries = read_json(seeds_file)
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
               'options': {'target': 'es6', 'flatc_runtime': 'flat'}, 'workloads': {},
               'blockers': [], 'full_acceptance': False}
    try:
        bootstrap.observe_subprocess_cleanup(output / 'watchdog-capability')
        receipt['watchdog_capability'] = watchdog_capability(output / 'watchdog-capability/subprocess-cleanup.json')
        receipt['watchdog_capability']['harness_sha256'] = bootstrap.digest(Path(bootstrap.__file__))
        live = create_production_workspace(repo, output / 'incremental', sources, seeds, tools, stages / 'stage2', budget)
        # Full production Core compilation/execution, never a source-trimmed fixture.
        for name in SCENARIOS:
            if name == 'changed':
                path = live['workspace'] / changed
                with path.open('a') as source:
                    source.write('\n// dogfood acceptance changed-input invalidation probe\n')
            if name == 'removed':
                # Restore the changed file, then remove one explicitly selected
                # production input. Invalid removal must fail visibly, not skip.
                shutil.copyfile(repo / changed, live['workspace'] / changed)
                (live['workspace'] / removed).unlink()
            expected_sources = scenario_sources(None, live['workspace'])
            measured = execute_production(live, 'Flat-Core', seeds, budget, seconds)
            require(scenario_sources(None, live['workspace']) == expected_sources, 'Build mutated production inputs')
            clean = create_production_workspace(repo, output / f'clean-{name}', sources, seeds, tools,
                                                 stages / 'stage2', budget)
            if name == 'changed':
                shutil.copyfile(live['workspace'] / changed, clean['workspace'] / changed)
            if name == 'removed':
                (clean['workspace'] / removed).unlink()
            require(scenario_sources(None, clean['workspace']) == expected_sources, 'Comparison inputs differ')
            rebuilt = execute_production(clean, 'Flat-Core', seeds, budget, seconds)
            require(measured['artifacts'] == rebuilt['artifacts'], f'{name}: retained-output/clean artifacts differ')
            observation = incremental_observation(name, measured)
            receipt['workloads'][name] = {'status': 'passed', 'retained_output': measured, 'clean': rebuilt,
                'source_manifest': expected_sources, 'incremental_observation': observation,
                'incremental_reuse_proven': observation['proven'],
                'diagnostics': {'retained_output': semantic_diagnostics(measured['commands']),
                                'clean': semantic_diagnostics(rebuilt['commands'])},
                'diagnostic_equivalence_proven': (
                    semantic_diagnostics(measured['commands'])['complete']
                    and semantic_diagnostics(rebuilt['commands'])['complete']
                    and semantic_diagnostics(measured['commands'])['records']
                    == semantic_diagnostics(rebuilt['commands'])['records'])}
            bootstrap.write_json(output / 'production-evidence.json', receipt)
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
        verdict = validate_production_evidence(output / 'production-evidence.json', receipt, stages)
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
    require(repo not in output.parents and output != repo, 'Output must be outside checkout')
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
        receipt = validate_evidence(args.evidence.resolve(), args.evidence_sha256, stages)
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

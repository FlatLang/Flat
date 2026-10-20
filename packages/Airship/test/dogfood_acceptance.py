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
            'blockers': list(CONTRACT_BLOCKERS), 'evidence_sha256': expected,
            'bootstrap_receipt_sha256': bootstrap.digest(stages / 'acceptance.json'),
            'source_manifest_sha256': value['source_manifest_sha256'], 'tools': value['tools'],
            'options_sha256': value['options_sha256'], 'suites': list(SUITES),
            'gates': ['production-core', 'stage0-1-2', 'java-free', 'incremental', 'schedules', 'lifecycle']}


CONTRACT_BLOCKERS = [
    'Persistent CLI evidence is now emitted; production cold/warm/changed/removed observations still require execution.',
    'Structured deterministic diagnostic evidence is not exported by CompilerCli.',
    'Airship does not forward -threads; controlled forward/reverse source-read gates require an in-process executor.',
    'CompilerSession cancellation/backpressure/cleanup observations are not exported by ordinary CLI execution.',
]


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
        bootstrap.run_command(argv, workspace / 'packages' / package, context['env'], log,
                              context['journal'], timeout=seconds, budget=budget)
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
        bootstrap.write_json(directory / 'commands.json', context['commands'])
    require(output_inventory(workspace, package) == generated, 'Execution changed generated artifacts')
    manifests = {}
    for path in sorted((workspace / 'packages' / package / 'dist').rglob('*.flat-incremental.json')):
        require(not path.is_symlink() and path.stat().st_size <= 4 * 1024 * 1024,
                'Invalid persistent manifest artifact')
        manifests[path.relative_to(workspace).as_posix()] = bootstrap.digest(path)
    return {'status': 'passed', 'artifacts': generated, 'persistent_manifests': manifests,
            'commands': list(context['commands'][-2:]),
            'sources_sha256': fingerprint(scenario_sources(None, workspace)),
            'runtime_observation': 'ordinary generated test.js exited zero; individual test assertions are in retained log',
            'summary': None, 'reuse_observed': None}


def schema_log_records(log, schema):
    require(log.is_file() and log.stat().st_size <= 100 * 1024 * 1024, 'Missing/oversized producer log')
    result = []
    with log.open() as source:
        for line in source:
            require(len(line) <= 1024 * 1024, 'Producer line exceeds capacity')
            if not line.lstrip().startswith('{'):
                continue
            try:
                value = json.loads(line)
            except json.JSONDecodeError:
                continue
            if isinstance(value, dict) and value.get('schema') == schema:
                require(len(result) < 4096, 'Producer record capacity exceeded')
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
                 'retainedCodeCharactersAfter', 'pendingArtifactSendsAfter', 'cleanupWaitSamples')
    optional = ('executorActiveAfter', 'executorQueuedAfter', 'unrelatedArtifactsBeforeFirstSettlement',
                'pendingArtifactSendsAtPressure', 'bufferedArtifactsAtPressure')
    for value in records:
        require(isinstance(value.get('scenario'), str) and bool(value['scenario']), 'Missing lifecycle scenario')
        for name in mandatory:
            require(type(value.get(name)) is int and value[name] >= 0, 'Invalid lifecycle measured count')
        for name in optional:
            require(value.get(name) is None or type(value[name]) is int and value[name] >= 0,
                    'Invalid nullable lifecycle measurement')
        for name in ('artifactBackpressure', 'cancelled', 'cleanupSettled'):
            require(type(value.get(name)) is bool, 'Missing lifecycle measured state')
        require(isinstance(value.get('settlementOrder'), list)
                and all(isinstance(item, str) for item in value['settlementOrder']), 'Invalid settlement order')
        require(value.get('unobserved') == ['children_reaped', 'temporary_outputs_removed'],
                'Memory lifecycle test cannot claim subprocess/filesystem cleanup')
        require(value['concurrency'] > 0 and value['peakActive'] <= value['activeCapacity']
                and value['peakRetained'] <= value['retainedCapacity'], 'Observed lifecycle capacity violation')
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
    """Comparator contract for a future typed producer, not a log-text parser.

    Does not invent ranges, discard diagnostic order, strip messages, or rewrite
    arbitrary strings. Only known workspace prefixes in the source identity are
    normalized. Until an actual producer emits records this cannot close a gate.
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


def produce_evidence(repo, output, stages, seeds_file, review_file, review_sha, changed, removed,
                     seconds=600, run_seconds=10800):
    """Execute supported production paths; never manufacture unavailable summary observations."""
    tools = validate_bootstrap(stages)
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
               'blockers': list(CONTRACT_BLOCKERS), 'full_acceptance': False}
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
        receipt['subprocess_cleanup'] = {'children_reaped': None, 'temporary_outputs_removed': None,
            'reason': 'Guarded process runner terminates groups but exports no measured cleanup receipt; memory lifecycle tests do not prove it'}
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
        receipt['stage2_suites'] = {'Flat-Compiler': receipt['compiler_suite']}
        for suite in SUITES:
            if suite == 'Flat-Compiler':
                continue
            suite_context = create_production_workspace(repo, output / ('suite-' + suite), sources, seeds,
                                                        tools, stages / 'stage2', budget)
            receipt['stage2_suites'][suite] = execute_production(suite_context, suite, seeds, budget, seconds)
            bootstrap.write_json(output / 'production-evidence.json', receipt)
        require(scenario_sources(None, repo) == sources, 'Live source inputs changed during production run')
        receipt['status'] = 'blocked'
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
    if args.produce:
        require(args.bootstrap_output is not None and args.changed_input and args.removed_input,
                '--produce requires --bootstrap-output, --changed-input, and --removed-input')
        produce_evidence(repo, output, args.bootstrap_output.resolve(), args.seeds.resolve(),
                         args.review.resolve(), args.review_sha256, args.changed_input, args.removed_input,
                         args.command_seconds, args.run_seconds)
        raise SystemExit('Production workloads completed, but full acceptance is blocked; see production-evidence.json')
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

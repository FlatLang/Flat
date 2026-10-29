'use strict';
// Trusted-build admission, not a hostile-code/OS sandbox. Every rejection is fatal
// to the harness even when package code catches the exception or ignores status.
const fs = require('node:fs');
const path = require('node:path');
const crypto = require('node:crypto');
const cp = require('node:child_process');
const moduleApi = require('node:module');
const policyFile = process.env.FLAT_BOOTSTRAP_POLICY;
if (!policyFile) throw Error('Missing bootstrap subprocess policy');
const policy = JSON.parse(fs.readFileSync(policyFile, 'utf8'));
const hash = file => {
  const value = crypto.createHash('sha256');
  const buffer = Buffer.allocUnsafe(1024 * 1024);
  const fd = fs.openSync(file, 'r');
  try {
    let count;
    while ((count = fs.readSync(fd, buffer, 0, buffer.length, null)) > 0) {
      value.update(buffer.subarray(0, count));
    }
  } finally { fs.closeSync(fd); }
  return value.digest('hex');
};
let scopeTransport;
let scopeSend;
let fixtureSequence = 0;
if (process.env.FLAT_PROCESS_SCOPE) {
  const scope = JSON.parse(process.env.FLAT_PROCESS_SCOPE);
  if (!/^[a-f0-9]{32}$/.test(scope.id) || scope.policySha256 !== hash(policyFile) ||
      !Number.isInteger(scope.requestFd) || !Number.isInteger(scope.ackFd) ||
      scope.requestFd < 3 || scope.ackFd < 3 || scope.requestFd > 256 || scope.ackFd > 256 ||
      scope.requestFd === scope.ackFd ||
      ![0, 1, 2].includes(policy.stage) || fs.realpathSync(policy.root) !== policy.root ||
      process.execPath !== policy.node || hash(policy.node) !== policy.nodeSha256) {
    throw Error('Invalid process scope policy/channel');
  }
    const descriptor = fs.fstatSync(scope.requestFd);
    const acknowledgement = fs.fstatSync(scope.ackFd);
    if (!descriptor.isFIFO() || !acknowledgement.isFIFO()) throw Error('Process scope requires inherited pipes');
  const send = (kind, fields = {}) => {
    const frame = Buffer.from(JSON.stringify({kind, scope: scope.id, pid: process.pid, ...fields}) + '\n');
    if (frame.length > 1024 || fs.writeSync(scope.requestFd, frame) !== frame.length) {
      throw Error('Process scope channel failed');
    }
  };
  send('hello');
  const ack = Buffer.alloc(1);
  const startupDeadline = Date.now() + 5000;
  while (true) {
    try {
      if (fs.readSync(scope.ackFd, ack, 0, 1, null) !== 1 || ack[0] !== 65) {
        throw Error('Process scope handshake failed');
      }
      break;
    } catch (error) {
      if (error.code !== 'EAGAIN' || Date.now() >= startupDeadline) throw error;
      Atomics.wait(new Int32Array(new SharedArrayBuffer(4)), 0, 0, 10);
    }
  }
  let cancelled = false;
  const listeners = new Set();
  const cancel = reason => {
    if (cancelled) return;
    cancelled = true;
    send('cancel'); // Host owns all group signals; never forward them here.
    for (const listener of listeners) {
      try { listener(reason); } catch (_) { /* cancellation is already fatal */ }
    }
    listeners.clear();
  };
  const capability = Object.freeze({version: 1, ownership: 'external-command-group',
    requestCancellation(reason) { cancel(String(reason).slice(0, 256)); },
    subscribeCancellation(callback) {
      if (typeof callback !== 'function') throw TypeError('Cancellation callback required');
      if (cancelled) callback('cancelled');
      else {
        if (listeners.size >= 128) throw Error('Cancellation subscriber capacity exceeded');
        listeners.add(callback);
      }
      return () => { listeners.delete(callback); };
    }});
  Object.defineProperty(globalThis, Symbol.for('flat.stage.processScope.v1'),
    {value: capability, writable: false, configurable: false});
  process.on('SIGINT', () => cancel('SIGINT'));
  process.on('SIGTERM', () => cancel('SIGTERM'));
  scopeTransport = scope;
  scopeSend = send;
}
function record(decision, command, args, reason) {
  if (fs.existsSync(policy.journal) && fs.statSync(policy.journal).size > 16 * 1024 * 1024) {
    throw Error('Bootstrap admission journal capacity exceeded');
  }
  fs.appendFileSync(policy.journal, JSON.stringify({decision, command, args, reason}) + '\n');
}
function reject(command, args, reason) {
  record('reject', String(command), args, reason);
  throw Error('Bootstrap subprocess rejected: ' + reason);
}
function verify(file, digest) {
  if (!digest || hash(file) !== digest) reject(file, [], 'seed/tool digest mismatch');
}
function unquote(value) {
  if (typeof value !== 'string' || /[\x00\n\r]/.test(value)) throw Error('Invalid subprocess argument');
  // Undo System.sanitizeCliArgument wrapping, then pass argv directly without
  // ever invoking a shell. JSON/application-property arguments remain literal.
  if (value.startsWith('"') && value.endsWith('"')) return value.slice(1, -1).replace(/\\"/g, '"');
  if (value.startsWith("'") && value.endsWith("'")) return value.slice(1, -1).replace(/\\'/g, "'");
  return value;
}
let lifecycleDirectory;
let lifecycleStep = 0;
function lifecycleMatch(entry, args, options) {
  if (!Number.isInteger(entry.lifecycleStep) || entry.lifecycleStep !== lifecycleStep ||
      args.length !== (lifecycleStep === 0 ? 1 : 2)) return false;
  const owner = path.join(policy.root, 'packages/Airship');
  const selected = path.resolve(options.cwd || process.cwd(), args[0]);
  const directory = path.dirname(selected);
  if (!new RegExp('^\\.airship-generated-lifecycle-[0-9]{1,16}$').test(path.basename(directory)) ||
      path.dirname(directory) !== owner || path.basename(selected) !== 'compiler.cjs' ||
      (lifecycleDirectory && lifecycleDirectory !== directory) ||
      fs.realpathSync(selected) !== selected ||
      (args.length === 2 && path.resolve(owner, args[1]) !== path.join(directory, 'selected.js'))) return false;
  verify(selected, entry.scriptSha256);
  if (lifecycleStep > 0) {
    const committed = path.join(directory, 'selected.js');
    if (fs.realpathSync(committed) !== committed || !fs.statSync(committed).isFile() ||
        fs.statSync(committed).size !== 9 || fs.readFileSync(committed, 'utf8') !== 'committed') {
      throw Error('Lifecycle fixture committed output changed');
    }
  }
  lifecycleDirectory = directory;
  lifecycleStep++;
  return true;
}

function toolchainMatch(entry, args, options) {
  if (JSON.stringify(args) !== JSON.stringify(['-e', 'process.exit(7)'])) return false;
  const owner = path.join(policy.root, 'packages/Airship');
  const cwd = fs.realpathSync(options.cwd || process.cwd());
  return path.dirname(cwd) === owner && /^\.airship-toolchain-test-[0-9]{1,16}$/.test(path.basename(cwd));
}

function toolchainCliMatch(entry, args, options) {
  if (!process.argv[1] || entry.runner !== fs.realpathSync(process.argv[1])) return false;
  if (args[0] !== entry.selected) return false;
  const owner = path.join(policy.root, 'packages/Airship');
  const root = args[3];
  if (typeof root !== 'string' || path.dirname(root) !== owner ||
      !/^\.airship-toolchain-test-[0-9]{1,16}$/.test(path.basename(root)) ||
      fs.realpathSync(root) !== root || fs.realpathSync(options.cwd || process.cwd()) !== owner) return false;
  const compiler = entry.sourceState === 'absent-compiler' ? path.join(root, 'absent-compiler.js') : entry.compiler;
  const expected = [entry.selected, '--use-links', '-f', root, '--flatc-runtime', 'flat',
    '--flatc-script', compiler, '-t', 'es6', '-r', 'node', ...entry.operation];
  if (JSON.stringify(args) !== JSON.stringify(expected)) return false;
  verify(entry.selected, entry.selectedSha256);
  verify(entry.compiler, entry.compilerSha256);
  if (fs.realpathSync(entry.selected) !== entry.selected ||
      !entry.selected.startsWith(owner + path.sep)) throw Error('Public CLI tool identity changed');
  if (!['present', 'invalid', 'absent-compiler'].includes(entry.sourceState) ||
      JSON.stringify(entry.outcome) !== JSON.stringify({exit: 1, signal: null})) throw Error('Invalid public CLI outcome');
  if (entry.sourceState !== 'present' &&
      fs.readFileSync(path.join(root, 'src/Main.flat'), 'utf8') !== 'not valid Flat syntax') {
    throw Error('Public CLI mutation state changed');
  }
  if (entry.sourceState === 'absent-compiler' && fs.existsSync(compiler)) throw Error('Missing compiler fixture exists');
  return true;
}

function toolchainCompileMatch(entry, args, options) {
  if (!process.argv[1] || entry.runner !== fs.realpathSync(process.argv[1])) return false;
  const owner = path.join(policy.root, 'packages/Airship');
  if (fs.realpathSync(options.cwd || process.cwd()) !== owner || args.length < 2) return false;
  const root = path.dirname(args[1]);
  if (path.dirname(root) !== owner || !/^\.airship-toolchain-test-[0-9]{1,16}$/.test(path.basename(root)) ||
      args[1] !== path.join(root, 'src') || fs.realpathSync(root) !== root) return false;
  const missing = entry.sourceState === 'absent-compiler';
  if (args[0] !== (missing ? path.join(root, 'absent-compiler.js') : entry.compiler)) return false;
  verify(entry.compiler, entry.compilerSha256);
  if (missing && fs.existsSync(args[0])) throw Error('Missing compiler fixture exists');
  if (fs.readFileSync(path.join(root, 'src/Main.flat'), 'utf8') !== 'not valid Flat syntax') return false;
  // Exact replay of the previously admitted ordinary main compile argv, with
  // only the reviewed missing-script substitution. Bound by the existing 16MiB
  // journal ceiling. No new flag, source, output, or dependency is admitted.
  if (fs.statSync(policy.journal).size > 16 * 1024 * 1024) throw Error('Journal capacity exceeded');
  const expected = [entry.compiler, ...args.slice(1)];
  const prior = fs.readFileSync(policy.journal, 'utf8').trim().split('\n').map(line => JSON.parse(line));
  return args.includes('-main') && args[args.indexOf('-main') + 1] === 'app/Main' &&
    prior.some(record => record.decision === 'allow' && record.command === policy.node &&
      JSON.stringify(record.args) === JSON.stringify(expected));
}

function admit(command, args = [], options = {}) {
  const original = command;
  let expectedExit = 0;
  let nodeProbe = false;
  let fixture;
  try {
    command = unquote(command);
    args = args.map(unquote);
    // System.execute may use shell:true for a script string. Support only an
    // unambiguous node + relative JS path; never execute a shell or shell grammar.
    if (options.shell && command.startsWith('node ')) {
      const words = command.split(' ');
      if (words.length !== 2 || args.length || !/^[A-Za-z0-9_./-]+\.js$/.test(words[1])) throw Error('Unsupported script command');
      command = words[0]; args = words.slice(1);
    }
    const name = path.basename(command);
    // Explicit immutable test exceptions only; never infer from package name or
    // accept arbitrary eval/shell/nonzero status. Host verifies cardinality too.
    const fixtures = policy.fixtures || [];
    if (fixtures.length > 128) throw Error('Fixture declaration capacity exceeded');
    fixture = fixtures.find(entry => entry.command === command &&
      (entry.toolchainCompile ? toolchainCompileMatch(entry, args, options) :
        (entry.toolchainCli ? toolchainCliMatch(entry, args, options) :
        (entry.toolchainFixture ? toolchainMatch(entry, args, options) :
        (entry.lifecycleStep === undefined ? JSON.stringify(entry.args) === JSON.stringify(args) :
          lifecycleMatch(entry, args, options))))) &&
      (entry.toolchainCompile || entry.toolchainCli || entry.toolchainFixture || entry.cwd === fs.realpathSync(options.cwd || process.cwd())) &&
      process.argv[1] && entry.runner === fs.realpathSync(process.argv[1]));
    if (fixture) {
      if (!fixture.runner.startsWith(policy.root + path.sep) ||
          fs.realpathSync(fixture.runner) !== fixture.runner) throw Error('Fixture runner outside canonical workspace');
      if (!scopeSend || options.shell || !Number.isInteger(fixture.count) || fixture.count <= 0 ||
          fixture.count > 128 || !/^[a-zA-Z0-9_-]{1,64}$/.test(fixture.id)) {
        throw Error('Invalid immutable fixture declaration');
      }
      verify(fixture.runner, fixture.runnerSha256);
      if (fixture.toolchainCli || fixture.toolchainCompile) expectedExit = 1;
      if (!Array.isArray(fixture.files) || fixture.files.length > 16) throw Error('Fixture file capacity exceeded');
      for (const file of fixture.files) {
        if (fs.realpathSync(file.path) !== file.path || !file.path.startsWith(policy.root + path.sep)) {
          throw Error('Fixture file outside canonical workspace');
        }
        verify(file.path, file.sha256);
      }
    }
    if (policy.debugOnly && name !== 'git') throw Error('debug-only policy prohibits executable launches');
    if (options.detached) throw Error('Detached subprocess escapes stage process-group ownership');
    if (options.argv0 || options.uid !== undefined || options.gid !== undefined) {
      throw Error('Subprocess identity overrides are not admitted');
    }
    if (options.cwd) {
      const cwd = fs.realpathSync(options.cwd);
      if (cwd !== policy.root && !cwd.startsWith(policy.root + path.sep)) {
        throw Error('Subprocess cwd outside stage');
      }
    }
    if (fixture && !fixture.toolchainCli && !fixture.toolchainCompile) {
      if (command === 'node' || command === policy.node) {
        verify(policy.node, policy.nodeSha256);
        command = policy.node;
      } else {
        if (command !== '/bin/sh' && !(fixture.outcome && fixture.outcome.error === 'ENOENT')) {
          throw Error('Only pinned POSIX shell or absent workspace fixture is admitted');
        }
        if (!path.isAbsolute(command)) throw Error('Fixture executable must be absolute');
        if (fixture.outcome && fixture.outcome.error === 'ENOENT') {
          if (!command.startsWith(policy.root + path.sep) || fs.existsSync(command)) {
            throw Error('Missing-executable fixture must be absent inside workspace');
          }
        } else verify(command, fixture.commandSha256);
      }
    } else if (name === 'node' || command === policy.node) {
      if (command !== 'node' && command !== policy.node) throw Error('Unpinned Node executable');
      if (args.length && args[0].startsWith('-')) {
        const owner = path.join(policy.root, 'packages/Flat-ES6-Bound-Writer');
        const runner = path.join(owner, 'dist/es6/test.js');
        if (!process.argv[1] || fs.realpathSync(process.argv[1]) !== runner ||
            path.resolve(options.cwd || process.cwd()) !== owner || args.length !== 2 || options.shell) {
          throw Error('Node flags/eval require the exact Bound-Writer test runtime');
        }
        let fixture;
        if (args[0] === '-e') {
          const match = /^require\('\.\/(\.flat-es6-entrypoint-[0-9]{1,16}\/main\.js)'\)$/.exec(args[1]);
          if (!match) throw Error('Only exact entrypoint import probe is admitted');
          fixture = match[1];
        } else if (args[0] === '--unhandled-rejections=strict' &&
                   /^\.flat-es6-entrypoint-failure-[0-9]{1,16}\/main\.js$/.test(args[1])) {
          fixture = args[1];
          expectedExit = 1; // This named test asserts exactly one, never any failure.
        } else throw Error('Unsupported Node test runtime flags');
        const selected = path.join(owner, fixture);
        if (fs.realpathSync(selected) !== selected || !fs.statSync(selected).isFile() ||
            fs.statSync(selected).size > 1024 * 1024) throw Error('Probe fixture must be a bounded regular workspace file');
        nodeProbe = true;
      }
      if (!nodeProbe) {
      if (fixture && fixture.toolchainCompile) {
        verify(policy.node, policy.nodeSha256);
        command = policy.node;
      } else {
      if (!args.length || args[0].startsWith('-')) throw Error('Node requires a script, not flags/eval');
      let script = path.resolve(options.cwd || process.cwd(), args[0]);
      const lookup = path.join(fs.realpathSync(path.dirname(script)), path.basename(script));
      if (policy.stage > 0 && policy.compilerOutput &&
          (script === policy.compilerOutput || fs.realpathSync(script) === policy.compilerOutput)) {
        throw Error('Compiler output is not an admitted compiler input');
      }
      if (lookup === policy.compiler && policy.stage > 0) {
        // Admission only: Airship explicitly selected this immutable script.
        // Retain realpath/hash checks; never substitute another compiler argv.
        if (fs.realpathSync(script) !== policy.seed) throw Error('Compiler script is not the immutable seed');
        const seedStat = fs.lstatSync(policy.seed);
        if (!seedStat.isFile() || fs.realpathSync(policy.seed) !== policy.seed || (seedStat.mode & 0o222)) {
          throw Error('Compiler seed must remain a canonical read-only regular file');
        }
        verify(policy.seed, policy.seedSha256);
      } else if (script === policy.airship) {
        verify(script, policy.airshipSha256);
      } else {
        if (policy.stage > 0 && script === policy.compilerOutput) {
          throw Error('Compiler output is not an admitted compiler input');
        }
        const real = fs.realpathSync(script);
        if (policy.stage > 0 && real === policy.compilerOutput) {
          throw Error('Compiler output alias is not an admitted compiler input');
        }
        if (!real.startsWith(policy.root + path.sep) || !real.endsWith('.js')) {
          throw Error('Script outside stage workspace');
        }
        script = real;
      }
      args[0] = script;
      }
      }
      if (policy.nodeSha256) verify(policy.node, policy.nodeSha256);
      command = policy.node;
    } else if (policy.stage === 0 && name === 'java') {
      if (command !== 'java' && command !== policy.java) throw Error('Unpinned Java executable');
      if (args[0] !== '-jar' || path.resolve(args[1] || '') !== policy.javaJar) {
        throw Error('Only explicit stage0 compiler jar is permitted');
      }
      verify(policy.java, policy.javaSha256);
      verify(policy.javaJar, policy.javaJarSha256);
      // flatc dynamically loads the ES6 backend; it is an execution input even
      // though it is absent from argv. Recheck each launch, not just its copy.
      if (!policy.javaBackendJar || fs.realpathSync(policy.javaBackendJar) !== policy.javaBackendJar) {
        throw Error('Stage0 backend jar must retain its canonical reviewed path');
      }
      verify(policy.javaBackendJar, policy.javaBackendJarSha256);
      command = policy.java;
    } else if (name === 'git') {
      if (command !== 'git' && command !== policy.git) throw Error('Unpinned Git executable');
      // Airship probes origins in local source trees; no fetch, clone, hooks,
      // configuration writes, helpers, arbitrary aliases, or network operations.
      if (JSON.stringify(args) !== JSON.stringify(['config', '--get', 'remote.origin.url'])) {
        throw Error('Only local Git origin lookup is permitted');
      }
      if (policy.gitSha256) verify(policy.git, policy.gitSha256);
      command = policy.git;
    } else throw Error('Executable is not admitted');
  } catch (error) {
    return reject(original, args, error.message);
  }
  record('allow', command, args, 'explicit stage policy');
  const childEnv = {...(options.env || process.env)};
  // Application variables may vary; tool/configuration selection may not.
  for (const key of Object.keys(childEnv)) {
    if (/^(?:NODE_PATH|JAVA_TOOL_OPTIONS|JDK_JAVA_OPTIONS|_JAVA_OPTIONS|LD_.*|DYLD_.*|GIT_.*)$/.test(key)) {
      delete childEnv[key];
    }
  }
  childEnv.GIT_CONFIG_NOSYSTEM = '1';
  childEnv.GIT_CONFIG_GLOBAL = process.platform === 'win32' ? 'NUL' : '/dev/null';
  let stdio = options.stdio;
  if (scopeTransport && (command === policy.node || fixture)) {
    if (stdio === undefined) stdio = ['pipe', 'pipe', 'pipe'];
    else if (typeof stdio === 'string' && ['inherit', 'ignore', 'pipe'].includes(stdio)) stdio = [stdio, stdio, stdio];
    else if (Array.isArray(stdio)) stdio = [...stdio];
    else return reject(command, args, 'Unsupported scope stdio');
    for (const fd of [scopeTransport.requestFd, scopeTransport.ackFd]) {
      while (stdio.length <= fd) stdio.push('ignore');
      if (stdio[fd] !== 'ignore' && stdio[fd] !== fd) return reject(command, args, 'Reserved scope descriptor');
      stdio[fd] = fd;
    }
  }
  return [command, args, {...options, ...(stdio ? {stdio} : {}), shell: false, detached: false, env: {
    ...childEnv,
    FLAT_PROCESS_SCOPE: scopeTransport ? JSON.stringify(scopeTransport) : '',
    FLAT_BOOTSTRAP_POLICY: policyFile,
    NODE_OPTIONS: '--require=' + __filename,
    FLAT_HOME: process.env.FLAT_HOME, HOME: process.env.HOME,
    PATH: process.env.PATH,
  }}, expectedExit, fixture];
}
for (const method of ['spawn', 'spawnSync', 'execFile', 'execFileSync']) {
  const original = cp[method];
  cp[method] = function(command, args, options, callback) {
    if (!Array.isArray(args)) {
      callback = typeof options === 'function' ? options : callback;
      options = args || {}; args = [];
    }
    if (typeof options === 'function') { callback = options; options = {}; }
    const admitted = admit(command, args, options || {});
    const fixture = admitted.pop();
    const expectedExit = admitted.pop();
    const invocation = ++fixtureSequence;
    let terminal = false;
    const outcome = value => {
      if (!fixture || terminal) return;
      terminal = true;
      scopeSend('fixture-end', {fixture: fixture.id, invocation, outcome: value});
    };
    if (fixture) scopeSend('fixture-start', {fixture: fixture.id, invocation});
    let result;
    try {
      result = original.call(cp, ...admitted, ...(callback ? [callback] : []));
    } catch (error) {
      if (fixture) outcome(error.status !== undefined ? {exit: error.status, signal: error.signal || null} :
        {error: error.code || 'UNKNOWN'});
      else record('reject', admitted[0], admitted[1], 'Subprocess execution failed');
      throw error;
    }
    if (fixture) {
      if (method.endsWith('Sync')) {
        outcome(result && result.error ? {error: result.error.code || 'UNKNOWN'} :
          {exit: result && typeof result === 'object' ? result.status : 0,
           signal: result && typeof result === 'object' ? result.signal : null});
      } else {
        result.once('error', error => outcome({error: error.code || 'UNKNOWN'}));
        result.once('close', (code, signal) => outcome({exit: code, signal}));
      }
    }
    const compilerOrNode = !fixture && (admitted[0] === policy.node || admitted[0] === policy.java);
    if (compilerOrNode) {
      if (method.endsWith('Sync')) {
        if (result && typeof result === 'object' && (result.error || result.signal || result.status !== expectedExit)) {
          record('reject', admitted[0], admitted[1], 'Child failed or was signaled');
        }
      } else if (result && typeof result.on === 'function') {
        result.on('error', () => record('reject', admitted[0], admitted[1], 'Child could not start'));
        result.on('close', (code, signal) => {
          if (code !== expectedExit || signal) record('reject', admitted[0], admitted[1], 'Child failed or was signaled');
        });
      }
    }
    return result;
  };
}
for (const method of ['exec', 'execSync', 'fork']) {
  cp[method] = (...args) => reject(method, args, 'Unstructured subprocess API');
}
// Prevent in-process downloads and worker contexts that would evade this
// inherited child-process policy. These hooks are for trusted build programs;
// hostile code requires an external OS sandbox in addition to this harness.
const net = require('node:net');
net.Socket.prototype.connect = (...args) => reject('network', [], 'Network is disabled');
for (const name of ['http', 'https']) {
  const transport = require('node:' + name);
  for (const method of ['request', 'get']) {
    transport[method] = () => reject(name, [], 'Network is disabled');
  }
}
require('node:dgram').createSocket = () => reject('dgram', [], 'Network is disabled');
require('node:worker_threads').Worker = function() {
  return reject('Worker', [], 'Uninstrumented worker contexts are disabled');
};
globalThis.fetch = () => reject('fetch', [], 'Network is disabled');
moduleApi.syncBuiltinESMExports();

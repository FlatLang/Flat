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
function admit(command, args = [], options = {}) {
  const original = command;
  let expectedExit = 0;
  let nodeProbe = false;
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
    if (policy.debugOnly && name !== 'git') throw Error('debug-only policy prohibits executable launches');
    if (options.cwd && !path.resolve(options.cwd).startsWith(policy.root + path.sep) &&
        path.resolve(options.cwd) !== policy.root) throw Error('Subprocess cwd outside stage');
    if (name === 'node' || command === policy.node) {
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
      if (!args.length || args[0].startsWith('-')) throw Error('Node requires a script, not flags/eval');
      let script = path.resolve(options.cwd || process.cwd(), args[0]);
      const lookup = path.join(fs.realpathSync(path.dirname(script)), path.basename(script));
      if (lookup === policy.compiler && policy.stage > 0) {
        // Admission only: Airship explicitly selected this immutable script.
        // Retain realpath/hash checks; never substitute another compiler argv.
        if (fs.realpathSync(script) !== policy.seed) throw Error('Compiler script is not the immutable seed');
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
      if (policy.nodeSha256) verify(policy.node, policy.nodeSha256);
      command = policy.node;
    } else if (policy.stage === 0 && name === 'java') {
      if (command !== 'java' && command !== policy.java) throw Error('Unpinned Java executable');
      if (args[0] !== '-jar' || path.resolve(args[1] || '') !== policy.javaJar) {
        throw Error('Only explicit stage0 compiler jar is permitted');
      }
      verify(policy.java, policy.javaSha256);
      verify(policy.javaJar, policy.javaJarSha256);
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
  return [command, args, {...options, shell: false, env: {
    ...(options.env || process.env),
    FLAT_BOOTSTRAP_POLICY: policyFile,
    NODE_OPTIONS: '--require=' + __filename,
    FLAT_HOME: process.env.FLAT_HOME, HOME: process.env.HOME,
    PATH: process.env.PATH,
  }}, expectedExit];
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
    const expectedExit = admitted.pop();
    let result;
    try {
      result = original.call(cp, ...admitted, ...(callback ? [callback] : []));
    } catch (error) {
      record('reject', admitted[0], admitted[1], 'Subprocess execution failed');
      throw error;
    }
    const compilerOrNode = admitted[0] === policy.node || admitted[0] === policy.java;
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

# Ternary branch ownership regression

Source fix packaged successfully as a distinct test compiler. The first narrow
workspace regression compile then exited 1 after 32.88 seconds (1200-second
watchdog), with one diagnostic: `Could not decode syntax 'Console.out'` in
`flat/test/TestRunner.flat`. Runtime was skipped (planned 180-second limit).
No ternary ownership NPE appeared, but the regression is NOT green. Stopped at
that first new root; no retry or Binder run. Evidence:
`/tmp/flat-finish-validation/java-ternary/narrow-attempt/`.
Maven `mvn -B -ntp -f packages/Flat-Java-Compiler/pom.xml package` passed under
a 1200-second process-group watchdog (~18 seconds), with no Maven tests present.
JAR SHA-256: `4a2256e0196f332a73f4af2f1885d50bec3310395ac72360a5561bb30a25f849`.
Source patch SHA-256: `8f5212fee4724be92b0d2bb07958780d8d40fcdb470d2800b35de5a6a90e35c9`.
Full toolchain/source receipts: `/tmp/flat-finish-validation/java-ternary/`.
Selected seed remained unchanged. Binder and narrow regression execution have
NOT run against these new bytes. Sources frozen pending targeted validation.

## Root

Binder evidence: `/tmp/flat-finish-validation/binding/two-root-repaired/build.log`
contains `Value.getTypeClass(Value.java:636)` called by
`TernaryOperation.decodeStatement(TernaryOperation.java:205)` with no Program.
The interleaved progress line is not a reliable Flat source locator.

`src/main/flat/tree/variables/Array.java:onAdded` lowers an initializer, constructs
a generated MethodCall, then invokes `replaceWith(call)`. `Node.replace` detaches
the Array and installs the replacement. `TernaryOperation.decodeStatement` added
the branch (triggering that callback), but retained and subsequently queried the
original detached `falseValue` local. In particular, `return flag ? existing :
[21, 22]` decodes the initializer against Return, adds it to the ternary, then
encounters this stale branch during common-type lookup. This is a concrete
source-level mechanism matching the stack; the exact Binder expression is not
yet identified without a targeted diagnostic compile.

The fix refreshes both branch references from the ternary after child insertion.
It does not clone/mutate declarations, reattach lowered-away Arrays, infer a
fallback class, catch exceptions, bypass type compatibility, or weaken semantic
errors. Common-type selection continues using the owning attached expressions.
No Value.getTypeClass null guard is added.

## Regression

The standalone `bootstrap/ternary/TernaryArrayOwnershipRegression.flat` exercises
the false array-initializer branch and existing-array branch, checking contents,
existing-reference identity, and fresh allocation on repeated fallback calls.
It uses exceptions rather than Test/Logger instrumentation and has a separate
manifest, leaving the seven callback cases unchanged. Failures remain nonzero.

Deferred commands (repository root, assigned heavy slot only):

```sh
mvn -B -ntp -f packages/Flat-Java-Compiler/pom.xml package
airship --use-links -f /Users/braden/GitHub/Flat/packages/Flat-Java-Compiler/test/ternary/flat.json --flatc-runtime java --flatc-jar /Users/braden/GitHub/Flat/packages/Flat-Java-Compiler/target/flatc.jar install test -t es6 -- -engine-jar /Users/braden/GitHub/Flat/packages/Flat-ES6/target/flat-es6.jar
node packages/Flat-Java-Compiler/test/ternary/dist/es6/test.js
```

Record new source/JAR hashes and bound compilation/execution with process-group
watchdogs. Do not overwrite `packages/Flat/target/flatc.jar` or admit these bytes
as a seed. A Binder rerun must explicitly select the new distinct test compiler;
its exact failing expression still requires confirmation. If it still fails,
add narrowly targeted branch/expression/parent-chain diagnostics in a distinct
test version rather than a broad catch or another uninformative long retry.

Only static diff/whitespace review ran this wave; no build, install or execution.
No dependencies added to production, barriers, queues, indexes, scheduling or
cancellation changes. Callback execution remains unproven with baseline-present
64 diagnostics; this fix makes no claim to resolve that separate fixture setup.

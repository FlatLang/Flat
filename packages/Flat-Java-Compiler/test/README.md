# Bootstrap callback return boxing regression

## Latest baseline comparison — no proven guard regression

A single selected-seed baseline build used the same real-workspace manifest,
explicit ES6 backend and dependency options as the new-compiler attempt, changing
only compiler selection and appending isolated `/tmp` output paths. Both exited
1: baseline in 32.95 seconds, new compiler in 31.54 seconds, under 1200-second
process-group watchdogs. No runtime executed (0/7 cases).

Both produced 64 diagnostics. Exact diagnostic multiset comparison differs only
in a generated lambda ID (`lambda121` baseline versus `lambda68` new compiler)
for the same missing-return-type error. After normalizing only those generated
IDs: **0 added / 0 removed**, preserving diagnostic multiplicities.

Classification: 17 fixture diagnostics (9 Console/System presentation errors,
8 generic helper/callable decoding/type errors or downstream unresolved-result
assertions); 47 dependency diagnostics (Logger 43, IO/File 3, TestRunner 1).
These are baseline-present failures, not evidence that the changed boxing guard
introduced them. Missing default presentation imports and legacy callable/generic
fixture syntax need separate repair; the comparison alone does not prove every
cascade's root. Compilation failure masks runtime behavior, so neither absence
of later regressions nor callback correctness is established.

Full logs, exact argv, source/JAR hashes, per-file diagnostic classification,
and delta: `/tmp/flat-finish-validation/java-callback/baseline-comparison/`.
The selected seed SHA-256 remained unchanged. No production source adjustment,
compiler rebuild, seed copy/admission, or quiet validation retry followed this
comparison. Source remains frozen.

Architectural contract remains return-boundary representation conversion in the
Java frontend: box concrete primitive AST results before reference/generic return
erasure, preserve reference/null/already-boxed identity, and do not unbox a
returned closure. No target-runtime value-shape guessing or Future compensation.
This contract is the intended fix, not a claim of successful execution validation.

## Current source freeze (source-only fixture repair)

The real-workspace manifest now declares `main.source: "bootstrap"` as well as
its test source, avoiding installed Airship's implicit missing `src` fallback.
The corrected workspace build reached Java validation, then failed with four
diagnostics: failure to decode `let callback = intCallback()` and three unknown
method definitions. Full evidence is in
`/tmp/flat-finish-validation/java-callback/manifest-corrected-attempt/`.

The fixture now declares the callable explicitly as
`let callback() -> Int = intCallback()` and uses the seventh test title
`returned callable preserves closure representation`. The previous title
contained `class`; the legacy class decoder searches base-scope text without
skipping backtick literal names, and the failed log showed a spurious nested
`CallbackBoxing_Test.function` class. Avoid reserved declaration words such as
`class` and `external` in these bootstrap test titles; this is a fixture naming
fix, not a parser behavior change. No title currently contains either word.

All seven cases and every assertion are preserved. Static source comparison and
`git diff --check -- packages/Flat-Java-Compiler` passed; NO build or execution
was run after this repair. The runner still requires exactly seven results.
Compilation/runtime remain unvalidated pending the next assigned heavy slot.
This section supersedes historical attempt status and suggested wiring below.

Status: Java compiler and ES6 backend Maven `package` succeeded. Callback fixture
compilation/execution remains BLOCKED before compiler launch: the installed Airship
crashes in `getSourceForPackage` with `TypeError: Cannot read properties of undefined
(reading 'containsKey')`. Stopped at that first root failure; no retry or broad use.
No selected seed was rebuilt, replaced, or admitted.

## Latest distinct-test validation

Full commands, statuses, toolchain and hashes are recorded under
`/tmp/flat-finish-validation/java-callback/` (`status.json`,
`backend-status.json`, `fixture-status.json`, and full logs).

- Compiler: `mvn -B -ntp -f packages/Flat-Java-Compiler/pom.xml package` passed;
  Maven reports no tests to run.
- Backend: `mvn -B -ntp -f /Users/braden/GitHub/Flat/packages/Flat-ES6/pom.xml package`
  passed, without source changes or Maven install.
- Fixture: copied `test/flat.json` and `test/bootstrap` into the evidence directory's
  fresh `fixture/`; invoked `/Users/braden/.flat/bin/airship --use-links -f` that
  directory with `--flatc-runtime java --flatc-jar` the explicit rebuilt compiler,
  `--debug install test -t es6 -- -engine-jar` the explicit rebuilt ES6 backend.
  Exit 1 in Airship before Java launch; output remained isolated. Runtime not run.

Compiler SHA-256: `a2c703560829c6f01810514e1037c52b6e297ff4e1bf704f4f867635032ba032`.
Backend SHA-256: `30b288ea133c4bd712151dec36f080a42d5105c7c6bc72ece4eebcd1dfacccb2`.
Selected seed SHA-256, unchanged before/after:
`2a8e38fb47fec8be506aeac3e5375d73b0c28c50cc6496f190377b5542e99118`.

The backend built against the existing local Maven compiler dependency, whose
recorded SHA-256 is `940c57bf4f1c8223a00465e8de8eb19f23f6c12b5485e2fb93df024a804b7c33`;
this is observed test-build input, not reviewed seed provenance. No acceptance
or client/backend runtime compatibility is claimed from a Maven build alone.

`test/flat.json` now provides the dedicated fixture registration; use its `test`
source, not the formerly suggested unregistered `callback-boxing` source below.
The older coordinator wiring/deferred-command discussion is historical and is
superseded by this concrete attempt. Diagnose the Airship setup failure before
resuming; no broader validation is authorized by this evidence.

## Contract and source owner

`src/main/flat/tree/Return.java:checkPrimitiveType` owns return representation
conversion in the imported Java compiler. Previously its function-type guard
skipped both unboxing and boxing. Keep that guard for unboxing (returning a
first-class function must not unbox the closure), but apply boxing whenever the
returned AST value is primitive and the receiving method return is not.

Use the existing `SyntaxUtils.autoboxPrimitive(node, concreteType)` overload.
It inserts the concrete wrapper constructor before generic return erasure;
it does not inspect JavaScript values or assume a target-specific box layout.
Reference/String/null and existing boxes do not satisfy the primitive predicate
and pass through unchanged. Subsequent validation sees the wrapper as a
reference, so it must not box it again. Primitive-to-primitive returns are
unchanged. No Future runtime compensation belongs here.

## Owning regression

`bootstrap/boxing/CallbackBoxing_Test.flat` contains seven executable tests:

- explicit zero/negative Int and inferred arithmetic callback returns;
- true and false Bool callback returns, subsequently used as Bool;
- String contents, length and reference identity;
- object identity through repeated Future reads;
- null preservation and successful Future completion;
- already boxed Int and Bool identity through a generic callback helper;
- direct generic Int/Bool callbacks and returning a first-class Int function.

`CallbackBoxing_TestSuite.flat` is a standalone entry point. It streams test
results into two counters, exits nonzero on any failure, and requires exactly
seven results so an empty/disconnected suite cannot pass. No timing sleeps,
whole-result collection, or runtime wrapper-shape guesses are used.

The existing `packages/Future/src/flat/future/Future_Test.flat` also exercises
Int/Bool/String/reference/null/boxed payloads, cancellation and failure isolation.
It was inspected, not modified; that package is outside this lane's ownership.

## Coordinator wiring required (not performed)

This imported Java package's `flat.json` exposes only Maven build; its POM has
no language fixture runner. Register a dedicated test source selecting this
package's `test/**/*.flat`, with main class
`bootstrap/boxing/CallbackBoxing_TestSuite` and dependencies Future and Test
(including their normal transitive runtime dependencies). Do not add these Flat
test sources to Maven's Java production source directory. Run the fixture against
the rebuilt compiler explicitly; do not accidentally select a stale default JAR.
Manifest/central workflow wiring is intentionally deferred to the coordinator.

## Deferred validation and seed admission

From repository root, in the coordinated validation slot only:

```sh
mvn -f packages/Flat-Java-Compiler/pom.xml package
# REVIEWED_JAR must identify newly built/admitted bytes, not an inferred default.
airship --use-links -f packages/Future install test -t es6 --flatc-jar "$REVIEWED_JAR"
airship --use-links -f packages/Future run test
# After registration of the fixture source as callback-boxing:
airship --use-links -f packages/Flat-Java-Compiler install callback-boxing -t es6 --flatc-jar "$REVIEWED_JAR"
airship --use-links -f packages/Flat-Java-Compiler run callback-boxing
```

The last two commands require coordinator source/script registration; they are
not claimed to work against the current manifest. Also run the Java compiler
workflow's Stability-Test build/execution against the admitted JAR, followed by
coordinated stage1/2 and acceptance suites. Package builds do not establish
self-hosting or lifecycle/scaling acceptance.

`pom.xml` builds the executable assembly as this package's `target/flatc.jar`;
its install phase additionally installs the artifact into local Maven state.
The matching CI build uses `mvn ... install`. Prefer `package` when only
producing reviewable compiler bytes. `.github/actions/setup-flat-monorepo`
separately copies the compiler into `packages/Flat/target/flatc.jar`; do NOT run
that setup action or overwrite that selected seed as part of this source fix.

A changed compiler requires a newly reviewed seed bundle: record the exact
producer source revision/dirty patch provenance, build command/toolchain and
artifact SHA-256, update the java_compiler seed entry and independently reviewed
receipt, and obtain the protected expected review digest. The stage bootstrap
loader verifies seed bytes and review provenance; a source edit does not update
those receipts or admit a new seed automatically. Preserve existing selected
seed bytes until explicitly coordinated admission.

No production dependencies, package responsibilities, queues, barriers, shared
indexes or scheduling/cancellation behavior are added by this fix. Runtime
correctness, regression compilation, and seed provenance admission remain
unvalidated until that coordinated slot.

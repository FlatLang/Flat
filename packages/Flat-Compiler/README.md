# Flat Compiler

## Flat-written ES6 driver

`flatc` composes the project argument adapter, source discovery, bound package
linker and transactional artifact sinks. It never falls back to the legacy writer
or Java. Unsupported syntax/semantics fail visibly. Source readiness is not
full production dogfooding or self-host acceptance.

```sh
flatc src -l dependencies/Core/src -target es6 -main app/Main -o dist/program.js
flatc 'src/**/*.flat' -target es6 -library -o dist/library.js
```

Sources and `-l` dependencies may be files, directories or glob roots. `-x` /
`--exclude` matches named files/directories and glob/regex exclusions. Discovery
retains identities, not source text, and lists one directory at a time. Limits:
4096 selected source identities, 16384 directory visits and depth 128 by default.
Duplicate canonical identities are coalesced. Directory cycles are skipped.
Foreign target files and trailing extensions such as `.flat.bak` are rejected.
Base `.flat`, shared `.js.flat`, then `.es6.flat` declarations are ordered before
binding, including direct provider compilation. Capacity exhaustion is an error,
not truncation. `--max-sources` and `--max-types` set explicit positive limits.

`-target es6` is required. Select `-library` or `-main qualified/Class` and
`-o file.js` or `-d directory`. Libraries default to `library.js` in a directory.
Configured `-default-import` / `-default-static-import` values are supported.
`--target`, `--exclude` and `--threads` alias their single-dash forms. `-t` is
ambiguous and rejected. Target-runtime and application-property selection are
still unsupported. Quiet mode never suppresses failures.

`CompilerSourceProvider.load(identity)` is an asynchronous source contract, even
for memory-backed providers. Polymorphic callers await the immutable source before
inspecting its identity/text; session wrappers checkpoint after load settlement.
It does not add a whole-package barrier or change bounded read admission.

`-threads N` (also `--threads N`, `-parallel`) bounds overlapping source-provider
reads. It does **not** claim threaded parsing, semantic parallelism or filesystem
parallelism: current production FileReader is synchronous. The package linker
consumes stable source order and binds the finite declaration closure serially.
One source's tokens are materialized for the existing scanner/parser contract;
complete syntax contexts form the binder's named signature barrier. Defaults are
1 Mi UTF-16 units per source, 131072 tokens per source, and 4096 types/fragments.
At most N read results are retained. Lexer token-capacity checking currently
follows materialization, so the source-character limit is the pre-lex bound.
Final fragment assembly materializes one artifact, capped by the CLI spool's
`--max-artifact-bytes` budget (default 67108864). These are explicit bounds, not
claims of bounded memory independent of configuration.

Compilation stages exactly one artifact before atomic destination replacement.
A compilation failure leaves a prior artifact unchanged. Cancellation suppresses
later source admission/publication and checks between source loads, syntax
partitions and emitted types. Already admitted reads are awaited before session
completion. The synchronous semantic binder is not preemptible. CLI owns spool
publication/cleanup; a session's sink commit alone is not final publication.

## In-process lifecycle and exact reuse evidence

- `Compiler.startPackage(provider, sink, artifactIdentity, session, mainClass,
defaultImports)` starts with preattached observers. `session.cancel()` requests
  cooperative cancellation. Diagnostics/progress are bounded live topics;
  artifacts are a bounded single-consumer channel, not replay.
- `CompilerEngine(maxActiveSources: N)` selects the package read window. The
  independent-source scheduler also accepts `maxQueuedSources`; that separate
  scheduler is not the package semantic scheduler.
- `Compiler.compilePackage(...)` is the closed-and-drained compatibility adapter.
- `Compiler.compilePackageSnapshots(sources, sink, artifactIdentity, mainClass,
defaultImports, toolFingerprint)` opts into exact in-process reuse, requiring an
  engine configured with `CompilerPackageReuse(maxSources, maxBytes)`.

`CompilationSummary` exposes terminal counts, `cancelled`, `packageDiagnostic`,
`cleanupSettled`, `peakActiveSources` / `peakRetainedSources` (independent-source
scheduler), and `peakActiveSourceReads` / `peakRetainedSourceReads` (package reads).
Completion means owned actions/reads settled, not that the CLI published a file.

`summary.reuse` exposes `reused`, `reason` (`disabled`, `cold`, `exact-hit`,
`configuration-changed`, `closure-changed`), `unchangedSources`, `changedSources`,
`addedSources`, `removedSources`, and `toolFingerprint`. A hit compares complete
source identity/text closure, configured imports, entrypoint, linker identity and
limits, and caller-supplied tool identity. Use a verified tool digest for that
identity; the compiler does not verify it. Output path is deliberately not a key:
fresh sessions/sinks receive the cached code under their requested destination.
Failure/cancellation cannot populate reuse. Cached strings are detached.

`CompilerEvidence.render(summary)` produces one JSON line with schema
`flat.compiler.session.v1`, these terminal fields and an explicitly scoped reuse
object. `render(summary, scenario)` accepts a harness-owned scenario label; CLI
uses `cli-package`. It includes `diagnostics:{complete,records}`. Successful
noncancelled sessions report an empty complete list; a single owning binding
failure reports its actual code/name/source/range. Unstructured/multiple failures
and cancellation report incomplete diagnostics, never invented ranges. Persistent
hits report complete empty diagnostics only when the validated manifest retained
that successful completeness marker. Unknown legacy manifests are misses.

It performs JSON escaping and bounds receipt strings; it does not mark a
workload accepted or write a receipt file. A harness must attach the source/tool
provenance and generated execution assertions independently.

## Persistent ordinary CLI incremental state

Every successful ordinary CLI build publishes a bounded sidecar
`<output>.flat-incremental.json`. Separate invocations verify SHA-256 fingerprints
of the complete selected source/dependency closure, exact CLI arguments (including
imports, options, entrypoint and budgets), actual executing compiler bytes and
Node version. A hit additionally hashes the current output; absent, altered or
oversized output cannot be reused. Removed sources change the closure key.
No mtime or process-local cache is used as authority.

`CompilerIncrementalState` wraps the source provider and checks each loaded text
against its admitted digest. CLI rediscovers closure membership and rehashes inputs
before publication, so changed/added/removed inputs during compilation fail. The
output is atomically published first; the manifest is then staged, fsynced and
atomically renamed. A crash between these commits yields a safe miss. Failed or
cancelled compilation leaves the prior manifest and prior artifact unchanged.
Concurrent builds of the same destination fail on its exclusive `.lock` directory;
a crashed process can leave a stale lock requiring explicit operator cleanup.
Locks are never stolen. Lock acquisition fails immediately with the exact lock
path and recovery guidance. `owner.json` records PID, host, start timestamp,
compiler and destination; this is inspection metadata, not proof that a PID still
belongs to the same process. If a crash leaves a lock: stop/quiesce all writers to
that destination, inspect the owner on its recorded host, then quarantine/rename
that exact lock directory and retry. Never remove a lock solely because a PID is
absent locally, a timeout elapsed, or metadata is missing. There is no automatic
recovery race that could delete an active owner. Destination trees/state files remain caller-trusted, not
an adversarial symlink sandbox.

Hashing uses a 64 KiB buffer and closes descriptors in `finally`; no source text
closure is retained by fingerprinting. Manifest reads/writes are capped at 4 MiB,
metadata at 16384 sources / 65536 arguments, tool hashing at 256 MiB, and source/
output hashing at the configured source/artifact limits. The manifest contains
source identities and digests, not source text or generated payloads. It is an
explicit destination-scoped metadata materialization boundary. Whole-package
recompilation occurs on a miss; member-level incremental compilation is not claimed.

CLI prints real session JSON when compilation runs and persistent JSON on successful
cold/warm outcomes. `CompilerEvidence.renderPersistent(state)` produces schema
`flat.compiler.incremental.v1`, scope `persistent-cli-package`, `reused`, `reason`,
unchanged/changed/added/removed source counts, tool/input SHA-256 fingerprints and
`manifestPublished`. A warm hit has no fabricated compiler session/read peak record.
These are producer observations, not acceptance verdicts. Tests include reopened
state and separate generated compiler processes; none have run in this wave.

This is bounded **whole-package in-process reuse**, distinct from the persistent
CLI mechanism above. Neither mechanism claims member-level invalidation or full
production acceptance. Repeating cold CLI builds is not incremental proof.

## Dedicated diagnostic batches

CLI emits one `flat.compiler.diagnostics.v1` JSON line for each admitted terminal
compilation or validated warm hit:
`{schema,revision,complete,records}`. `revision` equals the persistent input SHA-256
fingerprint (sources/dependencies/configuration/tool), not a Git revision or mtime.
`records` preserves the owning diagnostic order and exact `{code,message,source,
range}` values. Ranges include start/end line/column and token indexes; no path or
message rewriting is performed. H may normalize only its known workspace roots.
`CompilerEvidence.renderDiagnostics(summary, revision)` is the public producer.
`CompilerIncrementalState.cachedDiagnostics()` revalidates a warm hit and requires
a prior successful manifest with complete empty diagnostics before emitting `[]`.
Pre-session errors explicitly emit `complete:false` with revision
`unavailable-before-terminal-session`; untyped failures/cancellation also remain
incomplete. Missing output is never interpreted as empty diagnostics. A complete
semantic batch is not an assertion that artifact publication subsequently succeeded;
exit status and persistent-publication evidence are separate requirements.

## Focused infrastructure diagnosis

The existing test executable accepts an explicit diagnostic selection:

```sh
node packages/Flat-Compiler/dist/es6/test.js --infrastructure
```

It runs the complete admission, evidence, source-read schedule and incremental
suites through their existing public `runTests` contracts, with the same result
counts and failure exit status. `Compiler_TestSuite.runInfrastructure(onResult)`
is the corresponding in-process entrypoint. No arguments still run the full
registered suite unchanged. The subset is labelled diagnostic and never replaces
full-suite or stage acceptance. Incremental process tests still require the fresh
ordinary `dist/es6/index.js` compiler prerequisite; no test is skipped.

## Original-workload frontier (finish-1105 wave1, SOURCE-READY / NOT RUN)

Historical round17 (`validationT5round17.md`) reported a successful unchanged Core
library comparison at capacity 1/3 × forward/reverse, then failed while compiling
`ProductionCoreWorkload.leftDeclared`: `trace += "L"` was rejected as requiring an
instance receiver. It did **not** reach workload execution/stdout or the full
suite. That is an old observed frontier, not current acceptance. Static-field
binding belongs to Name-Binding; no semantic workaround belongs in Compiler.
Keep the original workload's declared String, LR checks and complete Core closure.
The root language coverage matrix remains source-backed/unvalidated; these
selectors implement no language feature and promote no matrix entry.

Two focused diagnostics now reuse existing tests without replacing acceptance:

- `--evidence` runs all `CompilerEvidence_Test` tests with normal result accounting,
  nonempty-result enforcement, interruption guard and failure exit status.
- `--workload-execution` calls
  `CompilerSourceReadSchedule_Test.assertProductionWorkloadExecution()`, the same
  assertion body called by the registered workload test. It retains complete Core,
  capacity 1/3 × forward/reverse, exact generated-code equality, source-read drain
  and capacity checks, the 32 MiB artifact ceiling, Node exit zero, exact stdout
  `production-core-workload:ok\n` and empty stderr. It fails on the first actual
  assertion/compilation failure; it does not print success on that path.

Use one selector per invocation. No arguments still run every registered test;
`--infrastructure` still includes the unchanged four-schedule Core library and
workload cases. Neither focused selector is a receipt or full-suite acceptance.
The workload helper retains the existing one-package materialization and artifact
comparison boundary; no new production barrier, queue or dependency is added.

### Coordinator-only validation sequence after writer release

Do **not** execute the historical `validateT5round17.py`: its output root and
expected tool hashes are historical. Read it as a recipe for fresh bounded process
receipts, source/tool closure checks and cleanup accounting. The sequence below
has **NOT RUN** in wave1. All commands run from repository root, offline, through
`/usr/bin/sandbox-exec -p '(version 1)(allow default)(deny network*)'` followed by
`/Users/braden/.flat/bin/airship`; arguments below follow that common prefix.
Use the coordinator's bounded runner for timeout, disk-log bounds, natural exit,
reaping and process-group absence. Stop on the first failure/unknown cleanup.

1. Before building, `git diff --check -- packages/Flat-Compiler`. Retain fresh
   source hashes for production/test Flat sources, manifests, test helpers,
   root/package guidance and relevant workflows/docs across the local dependency
   closure (including resolved workspace links). Record the public Airship
   launcher and installed JS, bootstrap compiler JAR, ES6 backend JAR and bounded
   runner identities. Verify source/tool stability before and after every command.
2. `--use-links -f packages/Flat-Compiler --flatc-runtime java install flatc -t es6`
   (1200 s cap), then the same command with `install test -t es6` (1200 s cap).
   Require fresh build-start mtimes, changed prior mtimes, sizes and hashes for
   `dist/es6/index.js` and `dist/es6/test.js`. Missing local dependencies fail;
   do not download replacements. The full package test also requires the fresh
   ordinary `packages/Airship/dist/airship.js` prerequisite; coordinator must admit
   its identity or build it separately once before the frozen validation run.
3. `--use-links -f packages/Flat-Compiler run test -- --evidence` (60 s cap), then
   `--use-links -f packages/Flat-Compiler run test -- --workload-syntax` (30 s cap).
   Run the owning Binder/backend static-field regressions first under their lane
   handoffs; this Compiler diagnostic is not a semantic repair or substitute.
4. `--use-links -f packages/Flat-Compiler run test -- --workload-execution`
   (1200 s cap). This reaches the original static-field/workload frontier without
   first recompiling Core as a library four times. Retain actual diagnostic
   records, terminal failure and cleanup completeness; do not invent source ranges
   when only method/token text is observed.
5. Only after the focused probe passes,
   `--use-links -f packages/Flat-Compiler run test -- --infrastructure` (600 s cap),
   then `--use-links -f packages/Flat-Compiler run test` (1200 s cap). These still
   execute the unchanged Core-library assertions and all original tests. A timeout
   is failure/unknown, not permission to omit a schedule or reduce the workload.

This avoids unnecessary full-Core cycles while chasing early blockers: build
once per settled source/tool closure, run narrow owning regressions, then probe
only the original executable workload. Reuse those fresh artifacts while hashes
remain unchanged; do not rebuild between diagnostic selectors. Do not cache or
skip Core assertions inside infrastructure/full acceptance, reuse old test
results as current evidence, or continue after source drift. This remains an
ordinary explicit local Java-bootstrap diagnostic sequence, not reviewed
stage0/1/2, seed promotion, guarded production or self-host acceptance.

Test-first handoff: the two selector regressions in `CompilerEvidence_Test` were
written before the selector helpers existed; source inspection confirmed helper
references only in those new tests at that point. No compiler/test execution was
authorized, so an executed red-to-green claim is unavailable. The unchanged
workload test remains the original semantic fail-first experiment from round17;
re-run it against the settled Binder/backend changes, retaining the new result
rather than treating historical failure or success as acceptance.

## Explicit nullable Core boundary slice (SOURCE-READY, UNVALIDATED)

The user explicitly chose strict nonnullable assignment and authorized correcting
nullable intent in the original workload. Future evidence must identify this
source revision: the workload is no longer byte-identical to the round17/wave11
input. The workload differs only by nullable local/parameter annotations; all original runtime
assertions, LR checks, complete Core input, schedules and output ceilings remain.
There is no implicit nullable inference/lifting policy change or implicit
nullable-to-nonnullable conversion permission in this slice.

| Contract                            | Source-authored coverage / required owning support                                                                                                                                                                                                                                                                                   |
| ----------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| Explicit `T?` locals and parameters | Workload `String?`, `StringCharArray?`, `Object?` null locals; corresponding Core tests. Parser/Binder must retain explicit type and source ranges; nonnullable null assignment must still reject.                                                                                                                                   |
| Guarded nullable String input       | `nonemptyString(String?)` keeps `value != null && value.count > 0`; null, empty, whitespace and NUL assertions unchanged. Requires short-circuit local/parameter narrowing, not unchecked member access.                                                                                                                             |
| Nullable exception message          | `Exception(visible String? message = "")` captures the message once and normalizes the local with `capturedMessage ?: ""` before native Error creation and presentation. `toString` reads class name first and retains the null short-circuit before `count`; the field itself remains nullable. No field narrowing fact is assumed. |
| Nullable equality inputs            | `Object.equals(Object?)` and all Core Object overrides retain their original guards/identity semantics; nullable inputs must preserve virtual dispatch. `String.equals(String?)`, base/JS/Java `StringCharArray.equals(StringCharArray?)`, and `Null.equals(Null?)` keep their original absence behavior.                            |
| Reference identity                  | JS/Java `Object.referenceEquals(Object?, Object?)` compares either absent operand without dereferencing. Core tests cover both absent, either absent and identity.                                                                                                                                                                   |
| Strict rejection                    | Binder owns negative tests for null-to-nonnullable initialization/arguments and unguarded nullable dereference. Core annotations do not authorize a conversion or guard relaxation.                                                                                                                                                  |

Exact annotated API families: `Object.equals`; `Object.referenceEquals` JS/Java;
`String.equals(Object?)` / `String.equals(String?)`; Object equality overrides in
`List`, `StringCharArray`, `Class`, `Bool`, `Null`, `Byte`, `Char`, `Int`, `Long`,
`Float`, `Double`, `Short`; `StringCharArray.equals(StringCharArray?)` base/JS/Java;
`Null.equals(Null?)`; `Exception` message constructor parameter/visible field;
workload-only `nonemptyString`. The Object overrides must agree with the widened
base input contract, not introduce unrelated nullable APIs. Primitive numeric
value overloads, Class-to-Class equality and exception subclass message inputs
remain nonnullable.

Required flow proofs include presence guards, `(other || !this)` under a nonnull
receiver, `other != null && ...`, `if (!another) return false`, the JS storage
`if (other == null) return false`, and exception fallback/formatting guards.
No suppression or forced cast to bypass nullability was added. The Java typed
StringCharArray overlay now matches the nullable contract and returns false for
null before reading `other.count`; the remaining equality algorithm is unchanged.
Core coverage includes nullable null/equal/unequal/different-length/empty operands;
Java execution is still unvalidated.

Exception normalization/presentation captures nullable `message` into a stable
local at the existing boundary; the constructor retains native Error creation
once and `toString` reads class name before message. Formatting still skips the
count call for an absent message, and preserves empty/whitespace/NUL behavior.
Repeated mutable-field reads are not treated as local flow facts.

### Local-flow subset and unresolved longer guards

`CompilerPackage_Test` adds generated execution for explicit `Item?` null/present
locals, nullable parameter dispatch, guarded field read and captured `?:` fallback,
checking exact `0 7 true true\\n`, empty stderr and exit zero. Negative compilation
cases retain zero-artifact requirements for reassignment after a guard, unguarded
nullable dereference and nullable-to-nonnullable argument use. These tests were
authored before Exception repair; executed fail-first/green is NOT RUN.

The Binder subset deliberately forgets facts at member/property reads and calls,
and never assumes mutable-field stability. Remaining Core shapes are therefore
not claimed closed: `(other || !this) && other.class.isOfType(...) && ...other...`
in String, Class, StringCharArray and numeric Object equality; String typed equality
`...count == other.count && compareTo(other)`; Bool equality's metadata read/cast;
List's metadata calls after early presence return; and storage equality after
`count`/array-conversion calls. These need resolved receiver/member effect and
stability evidence (or a separately reviewed minimal source repair), not blanket
fact retention. Presence guards alone do not prove those entire chains safe.

`FlatCore_Test` adds nullable identity/String equality checks and retains existing
exception/storage checks; `Double_Test` null locals now explicitly use `Object?`.
All original workload assertions remain present. Tests/builds are NOT RUN while
slice writers are active. After their settlement, validate owning Parser,
Type-System and Binder changes first, then Core, BoundWriter and the Compiler
focused workload, infrastructure and full suite. Java-bootstrap compatibility of
new `T?` source is also unvalidated. Selector success is not full acceptance;
reviewed seed provisioning is still absent.

## Guarded bootstrap and production CI (L24, IMPLEMENTED UNVALIDATED)

The repository [full dogfood workflow](../../.github/workflows/full-dogfood-acceptance.yml)
now orders the owning bootstrap/receipt regressions before protected provisioning,
a **new** stage0/1/2 bootstrap, source/scaling-timer admission, stage2 production
and independent supplemental evidence validation. The protected job retains local
seed/guard identities on one ephemeral runner. Ordinary PR/push harness checks are
not production acceptance; the final result explicitly says NOT REQUESTED. A
dispatch cannot succeed if a prerequisite was skipped, unknown, failed, or lacked
a terminal acceptance output. Bootstrap equality is necessary, not sufficient.

Existing source entrypoints (commands for the later authorized validation wave):

```sh
# Narrow owning harness suites, not self-host acceptance.
python3 -B -m unittest discover -s packages/Airship/test -p test_stage_bootstrap.py
python3 -B -m unittest discover -s packages/Airship/test -p test_dogfood_acceptance.py
# NEW_STAGES and NEW_PRODUCTION must not exist and must be outside checkout.
python3 -B packages/Airship/test/stage_bootstrap.py \
  --repo "$PWD" --output "$NEW_STAGES" --seeds "$SEEDS" \
  --review "$REVIEW" --review-sha256 "$REVIEW_SHA256"
python3 -B packages/Airship/test/dogfood_acceptance.py --produce \
  --repo "$PWD" --output "$NEW_PRODUCTION" --bootstrap-output "$NEW_STAGES" \
  --seeds "$SEEDS" --review "$REVIEW" --review-sha256 "$REVIEW_SHA256" \
  --changed-input "$CHANGED" --removed-input "$REMOVED" \
  --scaling-plan "$SCALING_PLAN" --scaling-plan-sha256 "$SCALING_PLAN_SHA256"
```

The producer requires real reviewed Core sources; invalid removal, missing driver
contracts and unobserved gates fail, never skip. Full supplemental validation uses
the existing non-`--produce` CLI with `--evidence` and an independently supplied
`--evidence-sha256`; a producer receipt alone does not close all completion gates.
Trusted seed/review provenance and source-bound scaling inputs must be provisioned
and approved, not synthesized from whichever artifacts are present. Exact
commands, tool/source hashes, logs and failure/unknown outcomes must remain
available with the acceptance run. See the root CI/completion matrix for inputs,
retention limits and unresolved operator prerequisites.

All commands above and the workflow changes are **NOT RUN this wave**. The
workflow-authored mocked producer tests cover nonterminal/unknown/missing status
without executing production workloads. No current-wave completion is asserted.

## Post-swarm delivery status

All24 initial source writers settled. Production dispatch now has only two
inputs, `dispatch_manifest` and its independently reviewed SHA-256, within GitHub's
ten-input limit. Strict schema `flat.dogfood.dispatch.v1` includes exact current
`source_revision` and all former configuration fields; paths, independent hashes,
control characters and duplicate/missing/unknown keys are checked before exporting
fixed environment names. No review bundle or digest is synthesized by CI. The
root README defines the exact manifest contract.

Current all7 integration barrier is open for coordinator validation; T7 performs
offline workflow syntax checks only, not compiler execution. Direct bootstrap and
producer validators now require external `review_sha256=REVIEW_SHA256` as well as
the independent scaling-plan hash. The workflow supplies the exact separately
reviewed digest from the pinned dispatch manifest; no receipt field or freshly
computed hash substitutes for review. `validate_bootstrap_chain` reconstructs
reviewed seeds and all three stages before public production evidence admission.
Current bootstrap source requires three products (compiler, Airship, production
driver), all seven ordinary suites, and separate guarded public workloads
`airship-toolchain` and `core-lr` at each stage. Current producer repeats both in
fresh scenario workspaces using stage2 tools. Workflow now explicitly validates
bootstrap public evidence and both current producer and independent supplemental
verdicts, with the independently pinned scaling hash forwarded to validators.
`delivery_complete` can become true only from a successful authorized dispatch and
all required terminal outcomes, never from source-ready wiring alone.

The toolchain group produces `dist/es6/toolchain-test.js` and requires
`AIRSHIP_TEST_FLATC_SCRIPT` pointing to the independently bootstrapped compiler.
Its expected failures need explicit owning guard/lifecycle admission; do not add
it to the seven-suite list or execute it unguarded as acceptance. The full Core
workload contract is `core/workloads/ProductionCoreWorkload`, artifact
`production-core-workload.js`, exact stdout `production-core-workload:ok\n`, empty
stderr and exit zero against the complete Core closure. See root README for
operator prerequisites. The old external swarm-1026 evidence tree was reported
lost: no trusted seed/review, supplemental evidence, timer identity, reviewed
changed/removable source or source-bound scaling plan is presumed available.
Operators must re-provision exact independently reviewed bytes and hashes; CI
must fail rather than manufacture or download replacements.

Provisioning records exact reviewed/resolved seed paths and hashes and checks the
reviewed canonical RSS timer before stages. Missing external bundle/timer/review
is a retained failure, not permission to discover replacement artifacts. The
reviewed plan is parsed with the existing 1 MiB reviewed-JSON bound; full schema
and fresh source-manifest binding remain mandatory after bootstrap.

Scaling budget numbers from L23 are unreviewed proposals. A final source-bound
plan, actual RSS timer identity and independent plan hash are required. Workflow
plan binding now checks producer and supplemental archived plan references against
that independent hash and the fresh bootstrap source digest. No trusted seed,
review, removable-source approval or measurement was fabricated.

## Deferred verification

Source tests cover glob/dependency closure beyond 64 files, exclusions and finite
limits, target overlay order, gated/reversed read completion at capacities 1/2,
cancellation drain, generated CLI execution and reuse hit/change/removal/tool
invalidation receipts. Tests/builds are intentionally not executed in the current
source-first implementation wave. Full seven-suite production acceptance,
Java-free Airship/compiler rebuilds, persistent incremental runtime verification and scaling
acceptance remain unverified.

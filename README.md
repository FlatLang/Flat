# FlatLang Monorepo

This repository is the FlatLang package monorepo.

All Flat packages in the local FlatLang workspace are organized under `packages/`.

## Layout

- `packages/Flat` - legacy Flat compiler package (this repo's original contents)
- `packages/*` - all other Flat packages imported as git subtrees

## Subtree Updates

Each package is tracked from its `master` branch and can be updated with:

```bash
git subtree pull --prefix packages/<PackageName> <repository> master
```

## Full dogfooding acceptance

Full dogfooding means ordinary Airship install/test/run workflows build and execute real production ES6/Node packages, including Airship and the Flat-written compiler themselves—not source-trimmed fixtures. Targeted, reviewed Core null-safety migrations at affected calls are permitted; this is the only exception to unchanged-production-Core acceptance. Strict nullable conversion policy remains unchanged: no global relaxation, trimmed substitutes, hidden Java fallback, or hand-appended generated entrypoint calls. Each migration requires valid/invalid cases and generated runtime evidence. An explicitly identified Java bootstrap seed is not self-hosting acceptance.

### Required suites and commands

The recovered seven-suite acceptance set is **Airship, Flat-Project-Loading, Flat-Parser, Flat-Name-Binding, Flat-Bound-IR, Flat-ES6-Bound-Writer, and Flat-Compiler**. This names required workloads, not seven verified passes. Historical provenance: canonical session `be12989b-866f-45c8-9ccf-96b55d08e7e2`, event 561.

From the repository root, substitute each package name and serialize builds that share generated outputs:

```sh
airship --use-links -f packages/<Package> install test -t es6
airship --use-links -f packages/<Package> run test
```

For explicit Flat-written compiler acceptance, select the runtime rather than relying on defaults:

```sh
airship --use-links -f packages/<Package> install test --flatc-runtime flat -t es6
airship --use-links -f packages/<Package> run test
```

Use the built production Airship executable for its self-rebuild gate, as in [airship-build.yml](.github/workflows/airship-build.yml). Record source revision/dirty-input hashes, compiler and Airship identities, exact commands, exit status and generated execution assertions. Missing Flat artifacts must fail visibly, not trigger a Java fallback; stale generated files cannot establish a pass.

### Fail-closed full acceptance automation (source-ready, unverified)

`packages/Airship/test/dogfood_acceptance.py` composes the existing F-owned
`stage_bootstrap.py` CLI; it does not implement another bootstrap, process guard,
compiler API, or Java fallback. The bootstrap retains `sources.json`,
`stageN/manifest.json`, stage workspaces and `acceptance.json`. Its schema-2
stage receipts identify explicit runtime/compiler selection, runner/compiler
hashes and generated products. The aggregator checks all three stages, all seven
install/run suites and the three retained products: compiler `index.js`, Airship
`airship.js`, and compiler `production-driver.js`, including exact stage1/stage2
product equality.
A passed bootstrap alone is **not** full production acceptance.

After the verification wave is authorized, provision independently reviewed seed
inputs and run the existing bootstrap interface:

```sh
python3 -B packages/Airship/test/stage_bootstrap.py \
  --repo "$PWD" --output "$STAGES" --seeds "$SEEDS" \
  --review "$REVIEW" --review-sha256 "$REVIEW_SHA256"
```

Then execute production acceptance workloads using those generated tools and
retain their evidence. Validate the complete evidence without rebuilding tools:

```sh
python3 -B packages/Airship/test/dogfood_acceptance.py \
  --repo "$PWD" --output "$NEW_OUTPUT" --bootstrap-output "$STAGES" \
  --seeds "$SEEDS" --review "$REVIEW" --review-sha256 "$REVIEW_SHA256" \
  --evidence "$PRODUCTION_EVIDENCE" --evidence-sha256 "$EVIDENCE_SHA256"
```

Outputs must be new directories outside the checkout. Omitting
`--bootstrap-output` runs the existing bootstrap first; evidence must still match
its source and generated tool hashes. The independent evidence digest comes from
review/provisioning, not an automatically trusted digest of arbitrary local files.
The current source inventory must equal the retained bootstrap inventory.

The supplemental **aggregator-local schema 1** is not a new required E/F public
API. It is an integration envelope for actual producer observations; producer
adapters and provisioning remain pending. Its fields are:

- `schema: 1`, `status: "passed"`, `source_manifest_sha256` (hash of bootstrap
  `sources.json`), `tools` (exact stage2 `products` map), `options` (including
  `target: "es6"`, `flatc_runtime: "flat"`), `options_sha256` (canonical JSON,
  sorted keys, compact separators), and `dependencies` (source-relative hashes,
  including package manifests; entries must match bootstrap sources).
- `core`: an execution record with `package: "Flat-Core"`,
  `production_inputs: true`, and the observed `Double.equals(Object)` assertion.
  `suites` maps each of the seven required suite names to an execution record.
- `incremental`: `cold`, `warm`, `changed`, `removed` records each contain
  `sources`, canonical `source_manifest_sha256`, matching `tools`, `dependencies`,
  `options_sha256`, and separate `incremental`/`clean` execution records. Output
  and diagnostic hashes must agree pairwise. Warm requires observed
  `reused_cache: true`; changed requires changed bytes; removed requires a strict
  source subset. This does not claim arbitrary source removals must be valid:
  producers must choose a valid removable input and prove stale-output removal.
- `schedules`: bounded execution records with integer `concurrency`,
  `completion_order` and `controlled: true`; serial and parallel (>1) plus
  forward/reverse controlled schedules must produce identical observable output.
- `lifecycle`: execution record with observed `backpressure`, `capacity_limit`,
  `bounded_in_flight`, `unrelated_progress`, `failure_isolation`,
  `cancellation_observed`, `children_reaped`, `temporary_outputs_removed` all true.

Every execution record contains exact `argv`, integer `exit_status: 0`, nonempty
observed `assertions`, integer `java_invocations: 0`, and file references for
`log`, `diagnostics`, `subprocess_journal`, plus `artifacts` keyed by stable
semantic output name. A file reference is `{ "path": "relative/file", "sha256":
"64-lowercase-hex-digest" }`; files are retained relative to the evidence receipt,
without symlinks/path escapes. Admission journals use the existing guard's JSONL
`decision`, `command`, `args` records; rejected subprocesses and Java invocations
fail even if the caller caught them. Artifact/diagnostic equality is by hashes,
not scenario-directory names. Logs and hashes bind trusted-producer observations;
the validator cannot independently prove assertions merely because a receipt says
true. Review must inspect those observations and ensure no trimmed inputs or
hand-appended entrypoints. No producer receipt or adapter is fabricated here.

`.github/workflows/full-dogfood-acceptance.yml` separates owning bootstrap/receipt
regressions from the explicitly dispatched gate on a protected, provisioned
`flat-dogfood` runner. It now builds **fresh** stage0/1/2 outputs in the dispatched
run; `bootstrap_output` is a new, nonexistent absolute directory, not a reusable
completed receipt. Missing prerequisites fail the dispatch result, never silently
skip to acceptance. PR/push runs explicitly report **production NOT REQUESTED**;
they do not execute PR code on the protected runner. Provisioning approval,
workflow syntax, regression execution and production execution are all
**IMPLEMENTED UNVALIDATED in swarm-1026**; no current-wave pass is claimed.

Validation commands for the later verification wave (not evidence of a pass):

```sh
python3 -B -m unittest discover -s packages/Airship/test -p test_dogfood_acceptance.py
python3 -B -m unittest discover -s packages/Airship/test -p test_stage_bootstrap.py
git diff --check
```

The acceptance harness adds only bounded receipt metadata materialization
(8 MiB per JSON receipt, 100,000 manifest entries, 32 schedules); file digests are
streamed. Stage completion and final evidence comparison are explicit offline
acceptance barriers, not production compiler dataflow barriers. No production
package dependency or responsibility moves.

### Executing supplemental production workloads (source-ready, unverified)

The harness now has a real producer mode. It copies the complete verified source
snapshot into isolated ordinary package workspaces, pins generated **stage2**
Airship/compiler inputs and the existing F-owned subprocess guard, and invokes
public Airship `install test` / `run test` for **Flat-Core**. It requires the run
to launch the newly installed generated `test.js`, records exact commands/logs
and admission hashes, and inventories all package outputs (including stale ones).
No source-trimmed replacement or hand-appended entrypoint is used.

After verification is authorized:

```sh
python3 -B packages/Airship/test/dogfood_acceptance.py --produce \
  --repo "$PWD" --output "$NEW_PRODUCTION_OUTPUT" --bootstrap-output "$STAGES" \
  --seeds "$SEEDS" --review "$REVIEW" --review-sha256 "$REVIEW_SHA256" \
  --changed-input "$REVIEWED_CORE_SOURCE" --removed-input "$REVIEWED_REMOVABLE_CORE_SOURCE"
```

`--changed-input` and `--removed-input` are existing `packages/Flat-Core/src/`
files, not suite entrypoints. Review must select a valid independently removable
input; an invalid removal fails the command rather than skipping it. The producer
compares cold, repeated-install, changed-byte (appended comment), and removed-input
retained-output builds against independent clean builds with exactly equal input
inventories. All generated artifact hashes must match. This proves only those
measured comparisons, **not persistent incremental cache reuse** or structured
diagnostic equivalence. It also replays an actually admitted compiler invocation
with the documented `-threads 1` and `-threads 2`, comparing generated artifacts;
this does not prove overlap or a controlled completion schedule.

Producer mode installs/runs all seven suites again with the immutable generated
stage2 tools (bootstrap stage2 itself used stage1 compiler inputs), retaining each
workload's commands and artifacts. It includes the real Flat-Compiler suite and
new controlled lifecycle tests when registered. It extracts bounded standalone
`CompilerEvidence.render` JSON lines (`schema: "flat.compiler.session.v1"`) from
actual retained command logs, validates terminal counts/cleanup/reuse fields, and
records observed sessions without inventing scenario identities. No emitted
records is an explicit blocker; asserted test success is not translated into
fabricated backpressure or cancellation counters. CLI emission and stable
lifecycle scenario labels remain integration needs at this point.

Producer `production-evidence.json` uses schema 2, records actual observations and
explicit unavailable values (`summary`, `reuse_observed`, controlled order/read
peaks). It always remains **blocked/nonzero for full acceptance** with current
contracts. Missing public contracts found in current source:

- `Compiler.compile`/`compilePackageSnapshots` and `CompilationSummary` support
  bounded in-process sessions, but `CompilerCli` does not export summary JSON.
- `CompilerPackageReuse` is bounded in-process whole-package reuse; a new CLI
  process cannot share it. There is no persistent ordinary-Airship incremental
  cache contract. A repeated install is not labeled a cache hit.
- `-threads N` controls source-read admission only; Airship does not forward it,
  and CLI exposes no controlled/reversed executor gates.
- `CompilerSession` exposes cancellation, backpressure and cleanup to attached
  in-process observers, not through a CLI evidence bridge. Killing a Node process
  cannot substitute for observing compiler-owned cancellation/cleanup.

Until real bridges exist, even a complete hand-authored schema-1 envelope cannot
produce a passing aggregate receipt. The protected workflow executes producer
mode first, retains failures/blocked evidence, and cannot silently pass the missing
gates. Tests include mocked orchestration (no real subprocesses), output-inventory
stale-file detection and mutation path admission; these tests are not production
evidence and have not been run in this source-first wave.

Current integration update: E now emits `flat.compiler.session.v1` and
`flat.compiler.incremental.v1` from the CLI. The producer parses persistent
`scope: "persistent-cli-package"` records, verifies actual executing compiler
SHA-256, and observes cold/exact-hit/changedSources/removedSources directly.
Persistent `.flat-incremental.json` files remain retained with hashes separately
from program-artifact comparison because they contain physical source identities.
Read-concurrency probes run after the incremental scenarios, so their thread
options cannot accidentally invalidate the warm-hit measurement. The older
contract-gap notes below describe the initial inspection; persistent CLI emission
is no longer missing. Structured diagnostic equivalence and labeled controlled
lifecycle observations remain outstanding.

Scoped Python verification: `python3 -B -m unittest discover -s
packages/Airship/test -p test_dogfood_acceptance.py` passed **22 tests**; the scoped
`git diff --check` also passed. These are receipt/mocked producer checks only;
no production compiler, Core, seven-suite, or self-host acceptance was executed.

## Current structured diagnostic/lifecycle integration

CLI now prints real terminal/session and persistent-cache records. For diagnostic
comparison the producer can prove **empty** diagnostic lists only when every
admitted compiler invocation has a noncancelled terminal session with
`diagnosticCount == 0`. Missing session output and persistent-hit output are not
silently interpreted as empty. Nonempty diagnostics remain blocked: presentation
`flatc: ...` strings cannot safely reconstruct semantic codes and end ranges.

The H comparator contract preserves ordered `{code,message,source,range}` records,
normalizes only the known workspace prefix in `source`, and retains message/code
and all `startLine/startColumn/endLine/endColumn/startTokenIndex/endTokenIndex`
values (or explicit null range). An actual E producer still must emit a versioned
complete/count-marked diagnostic batch from domain diagnostics; comparator unit
fixtures are not a producer. G lifecycle tests currently assert real controlled
transitions but need labeled rendered summaries and measured gate counters in the
retained log before H can associate observations with controlled scenarios.

Latest producer mapping: actual `flat.compiler.diagnostics.v1` batches are now
parsed with `revision` bound to an emitted persistent `inputFingerprint`, explicit
`complete`, and ordered semantic records. Warm empty batches are accepted only
when that actual complete record exists. Actual `flat.compiler.lifecycle.v1`
measurements are parsed with nullable measurements preserved, bounded capacities
checked, entered/settled and terminal cleanup counters validated. Their explicit
`unobserved: ["children_reaped", "temporary_outputs_removed"]` is required; memory
scenario results cannot close guarded subprocess/filesystem cleanup gates. The
receipt retains those as null until the subprocess harness supplies observations.
All parsed measurements remain linked to retained command logs, stage2 tool
hashes and the complete production source manifest. **26 scoped Python tests
passed**; no production execution has been performed.

The producer now invokes F's actual `observe_subprocess_cleanup` capability
scenario separately and validates its `subprocess-cleanup.json` plus retained
cancel/timeout logs and interpreter/harness hashes. This evidence remains
`watchdog-fixture-only`, `production_compiler_executed: false`, and cannot satisfy
production lifecycle gates. Negative tests reject scope promotion, incomplete
process-group observations and changed logs. **27 scoped Python tests passed**.
The supplied retained watchdog receipt was also parsed successfully (SHA-256
`eaf418556d74207dc14da7a8af6d6ff4b49c9635415a6799d3b617a2a87e951c`);
this validates fixture capability provenance only, not production cancellation.

### Product acceptance matrix

| Gate                                          | Required evidence                                                                                                                                                                                                                                                                                                |
| --------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Real production packages and Core null safety | Fresh seven-suite results plus ordinary package install/test/run through the public CLI, not source-trimmed fixtures. Execute normal generated Node artifacts against production Char, nonempty String, collections and runtime dependencies; only targeted, reviewed Core null-safety migrations are permitted. |
| Language closure                              | Valid/invalid syntax and ranges, deterministic declarations/imports, type/overload checks, lowering, ES6 emission and generated execution for required production features; include neighboring-feature interactions. Parsing alone is syntax support.                                                           |
| String evaluation order                       | Production String concatenation must log exactly `LR`, evaluate each side once and assert exact contents for declared+declared, native+declared and declared+native. The recovered historical requirement was a C regression; ES6 needs its own execution evidence.                                              |
| Self-hosting and bootstrap                    | Airship and the compiler rebuild with the Flat-written compiler; isolated stages identify seed/tool inputs and compare reproducible artifacts. Ordinary development requires no Java. **Full acceptance unverified.**                                                                                            |
| Bounded scaling and determinism               | Concurrency 1 and greater than 1 yield identical diagnostics/artifacts; controlled schedules prove capacity/backpressure, unrelated progress, scoped failure, cancellation and cleanup. Document every barrier/materialization boundary. **Full acceptance unverified.**                                         |
| Incremental builds                            | Source/options/dependency/tool fingerprints drive invalidation; changed, unchanged and removed inputs produce correct results; cold/warm builds agree without stale artifacts. **Full acceptance unverified.**                                                                                                   |
| CI                                            | Clean bootstrap, package execution, self-host rebuild and deterministic/incremental checks run with explicit runtime selection and artifact provenance. Existing Java-seeded workflows are not proof of Java-free acceptance. **Full acceptance unverified.**                                                    |

### Source-backed coverage, not completion claims

The reported 413 Name-Binding and 31 harness results are narrow validation, not full production self-hosting acceptance. The return-ternary reference rule is scoped to return expressions: compatible reference/generic value and null branches, including reversed/nested ternaries, retain conditional evaluation and source ranges. It does not permit nullable-to-nonnullable conversion in direct returns, local assignments, call arguments or defaults, nor admit scalar/null or incompatible-reference branches. Direct nullable conversions remain strict. See [binding cases](packages/Flat-Name-Binding/src/flat/semantic/binding/primitive/PrimitiveClassPartitionBinder_Test.flat) and [generated execution cases](packages/Flat-Compiler/src/flat/compiler/CompilerPackage_Test.flat).

| Null-safety acceptance  | Required matrix                                                                                                                                                                                                                                                                          |
| ----------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Return ternary          | Valid reference/generic value-or-null returns, both branch orders and nesting; reject scalar/null, unrelated references and uses outside the scoped return rule. Execute null/value paths with each condition and selected branch evaluated once.                                        |
| Targeted Core migration | Review each affected call against the unchanged strict policy; cover valid nonnull/null-handled paths and invalid direct nullable conversions, then execute production-shaped behavior without source trimming.                                                                          |
| Safe-null callback      | **Pending:** prove compatible callback binding/captures, skipped callback construction and side effects for a null receiver, once-only execution on the nonnull path, and rejection of incompatible/direct nullable arguments. Narrow safe-call tests do not establish this full matrix. |

[CompilerPackage_Test.flat](packages/Flat-Compiler/src/flat/compiler/CompilerPackage_Test.flat) contains Airship-to-Flat-to-Node, production Char/String, concatenation-order, provider-order and namespace acceptance cases. [CompilerCli_Test.flat](packages/Flat-Compiler/src/flat/compiler/CompilerCli_Test.flat) covers production Char/nonempty String and normal executable entrypoints. Test presence is not a fresh passing result or proof of the entire production closure.

The feature table below distinguishes parser-only scalar-native storage, qualified/nested field types and postfix `$` from reduced semantic/generated-execution slices for native storage clearing and condition guards. Control-flow coverage is likewise scoped, not general language completion. Required closure extends through imports, receivers/properties, generics/nullability, native representation, callbacks/async, control flow/exceptions and target overlays; each needs owning-layer tests and generated execution before promotion to implemented coverage. Consult current sources and validation results rather than treating an individual failing probe as a permanent blocker.

## Language coverage

Coverage below describes source-authored slices, not current-wave executed tests or full language-feature/self-hosting completion. Product acceptance requires the separate gates below. All swarm-1026 additions are **IMPLEMENTED UNVALIDATED** until the coordinator opens and completes the later validation barrier.

### Finish-1028 delivery CI (T7: SOURCE-READY, UNVALIDATED)

The initial seven-lane implementation wave performed no verification. The
coordinator has now opened the all7 integration barrier. T7 offline workflow
YAML/shell/embedded-Python checks are recorded in `integrationT7.md`; they do not
prove compilation, generated runtime, stage execution, scaling or CI acceptance.
Earlier historical suite counts do not validate current source.

Current source has separate mandatory workloads, not replacements for suites:

| Contract                    | Current CI coverage                                                                                                              | Remaining prerequisite                                                                                                                 |
| --------------------------- | -------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------- |
| Seven bootstrap suites      | Airship ordinary `test` plus the other six packages; prerequisites build compiler, production driver, then Airship               | Actual fresh runs and three-product stage equality unobserved                                                                          |
| Generated Airship toolchain | `sources.toolchain-test` -> `AirshipToolchain_TestSuite` -> `dist/es6/toolchain-test.js`; separate from Airship_TestSuite        | Current `stage_public_workloads` and producer both run guarded `airship-toolchain` in separate fresh workspaces; execution UNVALIDATED |
| Full Core workload          | `Flat-Core/workloads/ProductionCoreWorkload.flat`, public name `core-lr`, plus independent char_integration test                 | Current stage and producer public workload paths compile/execute this separately; execution UNVALIDATED                                |
| Scaling                     | Producer measures real ordinary builds with generated driver and pinned timer/plan; hosted CI includes `test_dogfood_scaling.py` | L23 ceilings are proposals only; final source binding, actual timer hash and independent review/hash required                          |

The protected workflow now requires `validate_bootstrap(..., require_public=True)`
before production using the externally supplied `review_sha256`; both direct
bootstrap calls and current producer validation pass `REVIEW_SHA256` from the
independently pinned dispatch manifest. Current `validate_bootstrap_chain`
reconstructs the reviewed seed bundle, exact three-stage manifest references,
prerequisite/command order, selected previous-stage tools, subprocess settlement
sidecars, Java exclusion and product equality. The review digest is never inferred
from the receipt under validation. Independently pinned scaling validation applies
to both supplemental and current producer receipts, and a fresh `validate_production_evidence` verdict
with `status=passed` and `full_acceptance=true`. It never infers a pass from output
directory presence or a status label alone. The final hosted summary derives
`delivery_complete` from successful prerequisite jobs and the explicit terminal
output, not a hardcoded historical gap/pass. No dispatch has been performed here.

`toolchain-test` requires `AIRSHIP_TEST_FLATC_SCRIPT` naming the selected immutable
generated compiler. Current harness code owns exact fixture admission, source and
artifact identities and expected-negative exit observations. Do not replace it
with unguarded shell execution or append it as an ordinary package suite. Current
stage implementation runs public workloads alongside every stage, retaining
three stage products and seven ordinary suites unchanged. Producer repeats both
public workloads with stage2 tools in independent workspaces. Compiler/driver,
Airship, source snapshots, HOME/FLAT_HOME, logs and subprocess policies remain
scenario-local; no shared generated output is reused as acceptance authority.

Seed provisioning loads the exact six-entry reviewed bundle using the external
review digest and retains both reviewed and resolved seed paths plus hashes. Seed
path resolution follows the owning loader; no PATH substitution or arbitrary
artifact discovery is performed. Node/Git/Java require executable permission;
Airship JS and both jars require nonempty reviewed bytes, not executable mode.
Provisioning also admits the scaling plan via bounded independently pinned JSON
and checks its canonical nonlinked executable BSD/GNU timer and reviewed hash
before any expensive stages; final plan/source binding is checked only against
the new stage snapshot. Seed or timer failure is a retained provisioning error,
not authority to pick another binary. A review digest authenticates the selected
review bytes, not the reviewer's honesty; protected approval must establish that
independent trust. No restored external input is assumed.

#### Operator inputs before production dispatch

1. Freeze/review the exact delivery commit and full package source manifest. Seed
   review `source_revision` records seed-producer provenance, not approval of this
   delivery source. Protected environment approval must separately bind current
   commit and local inputs.
2. Provision independently reviewed Node/Git/Java executables, bootstrap Airship
   JS, `flatc.jar` and `flat-es6.jar`, with exact absolute paths/hashes. Supply the
   seed manifest, review receipt binding its digest and provenance, and independent
   expected review SHA-256. No latest release or historical pass is trusted here.
3. Select reviewed existing real Core changed/removable paths. Prove removal is
   independently valid and preserves suite entrypoints; no removable default is
   invented. Invalid removal fails.
4. Review `flat.dogfood.scaling-plan.v1` against final `sources.json` digest with
   3–8 distinct real package tiers, positive `min_sources`, `min_source_bytes`,
   `max_rss_bytes`, `max_retained_sources`, `max_retained_source_bytes`, material
   input growth >=2, explicit RSS growth numerator/denominator, and actual absolute
   BSD/GNU timer path/kind/hash. L23 Project-Loading/Core/Parser floors and budgets
   are **unapproved proposals**, not admitted dependency inventories. Read retention
   measures the source-read window, not semantic heap. Independently pin the plan
   hash before measurement; do not inflate ceilings from failed measurements.
5. Supply independent supplemental evidence/references for exact sources/products
   with independently reviewed expected hash. CI now binds **both** producer and
   supplemental archived scaling plans to dispatch `scaling_plan_sha256` and the
   fresh source manifest; self-supplied hashes alone cannot close that gate.
   Current producer and supplemental CLI validation both receive the independent
   scaling-plan hash; archived plan hashes alone are not independent review anchors.
6. Provision offline local Git origin/dependencies, canonical fresh bootstrap
   parent, disjoint dedicated output/evidence trees, protected ephemeral POSIX
   runner/environment, disk/artifact quotas and retention policy. GitHub limits
   workflow_dispatch to ten inputs. This workflow now uses only two:
   `dispatch_manifest` and independent `dispatch_manifest_sha256`. The manifest
   carries every original configuration value; no review is synthesized in CI.

The former external `/Users/braden/Flat-dogfood-evidence/swarm-1026` tree was
reported lost. No seed bundle, independent review, timer digest, scaling plan,
supplemental evidence/reference tree, or accepted workspace is recovered or trusted
from that locator. Operators must supply **newly located and independently
reviewed** exact files/hashes named above plus the independently pinned dispatch
manifest. No runnable example with dummy trusted hashes is provided. This is a
missing-external-input prerequisite, not authorization to synthesize approval.

No current-wave production execution or CI dispatch was performed. Post-barrier
integration performed offline workflow syntax review only. Historical evidence
remains historical; current source readiness is not completion.

### CI/completion coverage matrix (T7, NOT RUN this wave)

| Gate                            | Source contract / required evidence                                                                                                                                                                                                | Current-wave status                                      |
| ------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------- |
| Owning bootstrap regressions    | `test_stage_bootstrap.py`; Node subprocess guard/lifecycle fixtures; no production acceptance claim                                                                                                                                | Wired, NOT RUN                                           |
| Receipt/CLI failure propagation | `test_dogfood_acceptance.py` plus workflow-authored mocked producer cases for blocked/unknown/running/failed/unsupported/missing status and exceptions                                                                             | Authored/wired, NOT RUN                                  |
| Public workload regressions     | `test_public_acceptance_contracts.py`: separate stage workloads, exact source-group/compiler selection and expected-negative fixture evidence                                                                                      | Wired, NOT RUN                                           |
| Clean bootstrap                 | Reviewed seed manifest + independently pinned review; fresh snapshots, guarded stage0 Java seed, Java-free stage1/2; retained seven-suite commands, public toolchain/Core workloads, source/tool hashes and three-product equality | Wired, NOT RUN                                           |
| Stage2 production               | Fresh accepted bootstrap and exact dispatched revision; independently pinned source-bound scaling plan/timer; real Core install/run and incremental probes                                                                         | Wired, NOT RUN; unsupported producer contracts must fail |
| Full completion evidence        | Independently reviewed supplemental evidence and referenced files plus producer success; exact prerequisite outcomes and artifact upload success                                                                                   | NOT ACCEPTED; absent/unknown is not success              |

The protected job keeps fresh bootstrap and production steps on the **same**
ephemeral runner because seed paths, immutable snapshots and subprocess admissions
are local identities. Its explicit step order is owning harness job -> pinned
dispatch admission -> provisioning and seed preflight -> clean guarded bootstrap
with public workloads -> strict public-stage/source/scaling-timer admission ->
production -> plan binding + supplemental/current producer validation -> retention. The always-run hosted
`acceptance-result` job rejects skipped, failed or missing prerequisites on a
dispatch, including a missing `full_acceptance` output. No package production
scheduling barrier is introduced.

Dispatch accepts only `dispatch_manifest` (canonical absolute local regular file)
and independent `dispatch_manifest_sha256`. The exact UTF-8 JSON bytes must be
independently reviewed and SHA-256 pinned before dispatch; maximum 64 KiB,
no duplicate/unknown/missing keys, and exact schema `flat.dogfood.dispatch.v1`.
Required `source_revision` must equal the dispatched commit. The other exact keys
are `seed_manifest`, `review_receipt`, `review_sha256`, `bootstrap_output`,
`changed_input`, `removed_input`, `production_evidence`,
`production_evidence_sha256`, `scaling_plan`, and `scaling_plan_sha256`.
The original inputs are thus preserved as manifest values rather than direct
workflow inputs. Canonical absolute existing nonlinked files are required for
seed/review/evidence/plan; bootstrap output must be new with a canonical existing
parent. Changed/removed values must be canonical repository-relative existing
nonlinked `.flat` files under `packages/Flat-Core/src/`. Digest fields are lowercase
64-hex and review/evidence/plan bytes must match before **any** configuration is
exported through fixed `GITHUB_ENV` names. Control characters and duplicate JSON
keys are rejected. The exact admitted manifest and admission/failure log are
retained. The manifest pin authenticates the reviewed configuration bundle; it
does not replace seed, evidence or scaling review, and does not assert that a
chosen Core removal is semantically valid.

The manifest values designate reviewed `seed_manifest`, `review_receipt`, independent
`review_sha256`, **new nonexistent** `bootstrap_output`, reviewed `changed_input`
and `removed_input`, existing `production_evidence` and independent
`production_evidence_sha256`, plus existing `scaling_plan` and independent
`scaling_plan_sha256`. Provision all paths locally, offline, outside checkout;
bootstrap, run-output and supplemental trees must be dedicated and disjoint.
Approval must bind the exact dispatched commit and all supplied hashes/paths.
The workflow does not invent reviews, derive trusted review hashes from files,
download latest tools, or silently reuse local dist. The Python orchestrator
binary/version and workflow/harness/guard hashes are recorded separately from
reviewed seed executable identities; observed Python identity is not seed trust.

Run receipts, command logs, partial bootstrap workspaces, reviewed inputs,
supplemental references and final prerequisite outcomes have always-run retention
steps (14 days). Unsafe/unadmitted paths are not uploaded. Runner loss, hard
cancellation or unavailable artifact service can still prevent upload; missing
artifacts remain **UNKNOWN**, not accepted. Retention capacity/quotas and protected
environment/branch policy require coordinator/operator review before dispatch.
No build, test, syntax, formatting, lint, diff check or runtime verification was
performed in the initial implementation wave.

Numeric-unboxing contract tests do not establish production Core `Double.equals(Object)` execution. That gate remains pending fresh production-input compilation and generated-code execution after the Core migration; full dogfooding acceptance remains separate.

| Feature                                       | Current coverage                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       | Owning tests                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| --------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Chained-label statement match                 | Literal labels share one switch body; selector evaluated once; source-token ranges and dangling-label rejection. Foreach continue advances nearest loop through if/switch; nested loop continue isolation covered; reduced break remains unsupported pending a dedicated vertical slice. Ordinary while-loop continue retains lexical loop context through try/catch/finally and executes cleanup under Node. Collection lowering recursively handles switch cases and try/catch/finally bodies, preserving immutable container metadata and execution order. Foreach/C-style-for continue crossing cleanup remains rejected at its source range; cleanup-aware advancement remains unsupported. Not general pattern matching or complete Int support. | Parser control tests, BoundIR_Test, Es6ReceiverOverloadExecution_Test (bounded Node)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| Scalar-native storage syntax                  | Syntax-supported: explicit `native` evidence on function/constructor parameters, stored fields, and locals; separate from `native_array`, with source ranges and overlay retention. Semantic adaptation and generated execution are separate acceptance work; no native-return grammar added.                                                                                                                                                                                                                                                                                                                                                                                                                                                          | [Parameter/field parser](packages/Flat-Parser/src/flat/parser/flat/PrimitiveClassParser_Test.flat), [Local parser](packages/Flat-Parser/src/flat/parser/flat/PrimitiveStatementParser_Test.flat)                                                                                                                                                                                                                                                                                                                                                                                                                                      |
| Native storage clear                          | Reduced bound/lowered/ES6 execution slice. Dedicated native-storage clearing; no global nullable-to-element conversion. Full real Array closure remains incomplete.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    | [Binding](packages/Flat-Name-Binding/src/flat/semantic/binding/primitive/PrimitiveClassPartitionBinder_Test.flat), [IR](packages/Flat-Bound-IR/src/flat/bound/BoundIR_Test.flat), [Node execution](packages/Flat-ES6-Bound-Writer/src/flat/writer/es6/bound/Es6ArrayOverloadExecution_Test.flat)                                                                                                                                                                                                                                                                                                                                      |
| Generic/reference declaration condition guard | Reduced typed-Bool guard slice with binding and ES6 execution. Initializer evaluated once; nullable Bool requires presence and true; primitive-condition rejection retained. Not general truthiness or complete generic/reference semantics.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           | [Binding](packages/Flat-Name-Binding/src/flat/semantic/binding/primitive/PrimitiveClassPartitionBinder_Test.flat), [Node execution](packages/Flat-ES6-Bound-Writer/src/flat/writer/es6/bound/Es6ArrayOverloadExecution_Test.flat)                                                                                                                                                                                                                                                                                                                                                                                                     |
| Generic qualified/nested field type syntax    | Syntax-supported only, including malformed-input diagnostics. Semantic binding, lowering, emission, and generated execution pending.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   | [Parser](packages/Flat-Parser/src/flat/parser/flat/PrimitiveClassParser_Test.flat)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| Numeric-family unboxing to Double             | Typed IR/binding/ES6/Node subset: resolved Number/virtual `realValue`, source and projection once, preserving NaN/infinities/signed zero. Null/non-number throws TypeError; no String/Bool coercion. Integral targets and missing projection evidence receive source-ranged unsupported diagnostics; general narrowing remains pending. Legacy coercion IR is not accepted production semantics.                                                                                                                                                                                                                                                                                                                                                       | [IR](packages/Flat-Bound-IR/src/flat/bound/BoundIR_Test.flat), [Conversions](packages/Flat-Type-System/src/flat/semantic/types/TypeConversion_Test.flat), [Partition binding](packages/Flat-Name-Binding/src/flat/semantic/binding/primitive/PrimitiveClassPartitionBinder_Test.flat), [Fallback binding](packages/Flat-Name-Binding/src/flat/semantic/binding/primitive/PrimitiveExpressionBinder_Test.flat), [Emission](packages/Flat-ES6-Bound-Writer/src/flat/writer/es6/bound/Es6ExpressionEmitter_Test.flat), [Node execution](packages/Flat-ES6-Bound-Writer/src/flat/writer/es6/bound/Es6ReceiverOverloadExecution_Test.flat) |
| Postfix dollar suffix (`$`)                   | Syntax-supported only: token/source ranges and call/member/reference intent. Semantic behavior, lowering, emission, and generated execution pending. Distinct from postfix increment.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  | [Lexer](packages/Flat-Lexer/src/flat/lexer/Lexer_Test.flat), [Expression parser](packages/Flat-Parser/src/flat/parser/flat/PrimitiveExpressionParser_Test.flat), [Class parser](packages/Flat-Parser/src/flat/parser/flat/PrimitiveClassParser_Test.flat)                                                                                                                                                                                                                                                                                                                                                                             |

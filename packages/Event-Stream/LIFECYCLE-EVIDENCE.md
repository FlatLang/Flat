# Controlled lifecycle evidence

## Current source freeze: generic payload audit

The latest source-only correction has **not been built or run**. Previous runtime
receipts do not validate it. No new lifecycle observations are claimed.

The later source-only async-context correction adds `$` at all 22 concrete
`intFuture`/`boolFuture` factory call sites. Seed `MethodCall.decodeStatement`
implicitly appends `get()` to non-Future methods returning Future; `$` suppresses
that automatic await. Factories remain synchronous, return ownership immediately,
and launch only their async completion runners with `$`. The runners alone await
the concrete callback; `CompletableFuture.complete(T)` and `error(Exception)` are
synchronous per both Future declarations. Reference factories return their future
variables directly; method-reference `failFirst` is not an auto-awaited call.
Gates, releases, result assertions and lifecycle measurements are unchanged.
No build or runtime validation has been performed for this correction.

All Event-Stream native completion sites have been audited:

- `ConcurrentMapOperation.js.flat`: output-pressure, ordered-emitter and final
  operation `Future<Bool>` payloads are boxed through a Flat `Object` and native
  handoff. The completed/cancelled flags themselves remain native JS booleans.
- `PendingChannelReceive.js.flat`, `PendingChannelSend.js.flat` and
  `PendingExecutorTask.js.flat`: payloads are existing typed result objects,
  passed through their existing native-result handoffs; no change required.
- `ConcurrentMapTask.js.flat`: input and mapping-future result are generic
  payloads and must retain their representation and reference identity. No
  `typeof` normalization, numeric type guessing or fallback coercion is added.
- Legacy `EventStream.waitFor` uses a typed `fromResolve<DataType>` resolver,
  forwarding the already-generic event payload rather than returning a concrete
  primitive through an erased native callback.

The seed artifact showed `Future.from<Int>` arithmetic callbacks returning raw
JS numbers. That helper belongs to Future/compiler, outside this write scope.
Every mapper producer in `ConcurrentMap_Test` now uses `Future.create<T>()` and
its typed `complete(T)`/`error(Exception)` boundary instead. Int and Bool async
fixture producers use **concrete** callback return types and concrete local
values before typed completion; the completion boundary boxes them for generic
storage. Cancellation leaves cancelled futures settled and does not overwrite
terminal results; mapper cleanup/finally execution is still governed by the
same fixture gates. Reference and String producers complete typed futures too.
Existing assertions and schedules are preserved.

New regression coverage checks arithmetic Int and inverted Bool payloads through
both generic equality and concrete reads, true/false Bool completion payloads,
and reference/null identity preservation. Nested future fixture arrays use
`Array<Future.CompletableFuture<T>>()`, avoiding the seed's incorrect nested
array-literal emission. This fixes owned producers, not arbitrary malformed
third-party `Future<Out>` values; the public mapping contract now explicitly
states that distinction. General `Future.from<T>` boxing repair remains an
out-of-scope Future/compiler interface gap for its owner.

No production dependencies, barriers, queues, indexes or capacity changes were
introduced. Later fresh Event-Stream validation is still required with the
commands below; the shared heavy slot is not used in this source-only wave.

These are in-process regression fixtures, not Java-free stage1/stage2 or
production Core/Airship acceptance. Source changes in this wave are
**implemented but unvalidated**. No measurements have been collected by running
these tests in this wave. JSON lines below describe records emitted by a later
successful test execution; their presence alone is not acceptance.

## Event-Stream records

`ConcurrentMap_Test` is already registered in `EventStream_TestSuite`.
All records use `schema: "flat.eventstream.lifecycle.v1"` and a closed ASCII
`scenario` label. Counts are JSON integers, `cancelled` is a JSON boolean,
`settlementOrder` is an array of fixture input indexes, and `unobserved` is an
array of strings. Fields not applicable to a scenario are omitted, not zero.
No pass/status flag is emitted. Assertions and operation completion precede
record emission.

Exact per-scenario fields (in addition to `schema`, `scenario`, `unobserved`):

- `ordered-forward`, `ordered-reverse`: `concurrency`, `settlementOrder`,
  `settledCount`, `peakActive`, `activeAfter`, `peakInFlight`, `peakReorder`,
  `inFlightAfter`, `activeTasksAfter`, `reorderAfter`,
  `pendingInputReceivesAfter`, `pendingOutputSendsAfter`,
  `pendingOutputReceivesAfter`.
- `unordered-failure-isolation`: `concurrency`, `settlementOrder`,
  `settledCount`, `unrelatedOutputsBeforeFirstSettlement`, `failedResults`,
  `peakInFlight`, `inFlightAfter`, `activeTasksAfter`,
  `pendingOutputSendsAfter`. Here “first” means input index 0, not the earliest
  callback to finish. Order and counts are collected from mapper settlement
  and received result observations, then asserted before emission.
- `upstream-failure-drain`: `concurrency`, `settledCount`,
  `cleanupWaitSamples`, `peakInFlight`, `inFlightAfter`, `activeTasksAfter`,
  `pendingOutputSendsAfter`, `pendingInputReceivesAfter`.
- `output-pressure-cancellation`: `concurrency`, `pendingOutputSendsAtPressure`,
  `inFlightAtPressure`, `peakInFlight`, `inFlightAfter`, `activeTasksAfter`,
  `pendingOutputSendsAfter`, `pendingOutputReceivesAfter`,
  `pendingInputReceivesAfter`, `cancelled`.

Ordered schedules run at concurrency 1 and 3 in both directions. Each window
awaits entry before releasing a mapper, then awaits each mapper's `finally`
settlement before releasing the next. The next window cannot enter while the
current one is fully active. Output identities and values remain source ordered.
Fixture size is six: this is not a production materialization/barrier.

Unordered failure isolation holds input 0, observes failure at input 1, then
successful output at input 2 before releasing input 0. Upstream-failure drain
runs at concurrency 1 and 3 with every mapper suspended in `finally`; operation
completion must remain pending before each cleanup release. Output-pressure tests use
a rendezvous output and direct owned futures at concurrency 1 and 3: one send
is observed blocked, repeated cancellation releases it, cancels the other
mapping futures, and drains internal ownership without closing caller input.
This does **not** prove arbitrary async callback preemption or callback-finally
cleanup; that observation is explicitly unobserved in these cancellation records.
`allocation_bytes` and `throughput` are unobserved in every scenario.

## Compiler records

`CompilerDogfoodLifecycle_Test.flat` emits existing
`flat.compiler.lifecycle.v1` records plus authoritative
`CompilerEvidence.render(summary)` records. Compiler implementation remains
owned by Flat-Compiler, not Event-Stream.

The lifecycle schema contains exactly: `schema`, `scenario`, `concurrency`,
`settlementOrder`, `activeCapacity`, `retainedCapacity`, `peakActive`,
`peakRetained`, `enteredCount`, `settledCount`, `activeAfter`,
`executorActiveAfter`, `executorQueuedAfter`, `inFlightAfter`,
`retainedSourcesAfter`, `retainedCodeCharactersAfter`,
`pendingArtifactSendsAfter`, `artifactBackpressure`, `cancelled`,
`cleanupSettled`, `unrelatedArtifactsBeforeFirstSettlement`,
`pendingArtifactSendsAtPressure`, `bufferedArtifactsAtPressure`,
`cleanupWaitSamples`, `unobserved`.

Scenario/order are strings/string arrays. Backpressure/cancel/cleanup are
booleans. Counts/capacities are integers. Unmeasured executor counts, unrelated
progress, pressure snapshots, and cleanup-wait samples are **null**, not zero.
`children_reaped` and `temporary_outputs_removed` remain explicitly unobserved:
the fixtures own no child process or disk artifact lifecycle.

Package artifact equality now covers concurrency 1 and 3, forward and reverse.
Artifact-pressure cancellation now covers both concurrency settings. Existing
failure retirement and controlled running/queued cancellation tests remain in
place. These observations do not establish production incremental/scaling,
process reaping, temporary-file cleanup, or self-host acceptance.

## Deferred validation / integration

Run from the repository root during coordinated validation, not this wave:

```sh
airship --use-links -f packages/Event-Stream install test -t es6
airship --use-links -f packages/Event-Stream run test
airship --use-links -f packages/Flat-Compiler install test -t es6
airship --use-links -f packages/Flat-Compiler run test
```

No new dependency, public API, production queue/index, or suite registration is
required. Existing acceptance consumers must tolerate nullable
`cleanupWaitSamples`; do not turn null or absent observations into pass flags.
Production child/temp-output observations must come from their owning lifecycle
implementations and later coordinated acceptance, not these fixtures.

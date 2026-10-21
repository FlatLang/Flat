# Production compiler source-read driver (source-only)

Implemented but **unvalidated**. This is an opt-in in-process driver over the real
CLI, persistent fingerprint producer, filesystem provider, package scheduler and
session cancellation. It is not a bootstrap/Java fallback, production acceptance
verdict, manifest change or replacement for seven-suite validation.

## Invocation / coordinator wiring

Build the `production-driver` source set in `flat.json` as an ordinary ES6
executable in a later coordinated validation wave (bin `flatc-production-driver`).
Invoke its Node artifact with:

```
<forward|reverse|cancel-after-read|cancel-after-stage> <capacity> <flatc arguments...>
```

Compiler arguments omit the executable token. The driver appends `-threads N`
using its capacity, so the observed schedule matches package read capacity. Use
fresh, isolated destinations for each run. Warm hits fail visibly: they cannot
prove actual source work. Cancellation intentionally returns CLI exit code 1.
Run forward/reverse with capacity 1 and >1 against the same admitted real project;
retain actual outputs/hashes and compare normalized diagnostic batches externally.
Output paths/options are deliberately fingerprinted exactly, not normalized away.

`CompilerCli.run(args, observer, sourceReadSchedule)` is the in-process integration
API; existing callers need neither optional parameter. The typed observer is
`CompilerInvocationObserver extends CompilerSourceReadObserver`. It binds revision,
destination and actual incremental evidence before loading sources. Read and
artifact readiness callbacks receive the active session and identity. A driver
may call `session.cancel()` there. Session cleanup then drains admitted work;
CLI finalization disposes spool and incremental lock before returning.

## Bounded schedule semantics

`CompilerSourceReadSchedule.forward(N)` / `.reverse(N)` are single-use, window-
scoped schedules. At most N reads and N completion gates are retained. Actual
filesystem loading is unchanged. Completed reads wait for controlled release in
their finally blocks; their scheduler settlement order is forward/reverse. This
is a named **controlled source-read window settlement barrier**, not a claim of
parallel native synchronous IO or a new ordinary-compilation phase barrier.
Only controlled invocations await a whole window before stable consumption.
Cancellation stops further admission and releases/drains every admitted window.
Observers are synchronous and must not reenter scheduling or retain an unbounded
history; throwing fails the owning compilation. Ordinary runs have no schedule.

## Exact driver record fields

Each line is JSON. No pass flag is emitted.

`flat.compiler.source-read-event.v1`:

- `schema`, `revision`, `destination`, `transition`, `source`: strings.
- `sequence`, `admitted`, `settled`, `active`, `retained`, `capacity`: integers.
- transitions: `admitted`, `started`, `settled`, `consumed`,
  `cancel-readiness-after-read`, `cancel-readiness-after-stage`.

`flat.compiler.source-reads.v1`:

- `schema`, `scenario`, `revision`, `destination`, `optionsFingerprint`,
  `eventDigest`: strings. Scenario is the selected mode above.
- `capacity`, `admitted`, `settled`, `activeAfter`, `retainedAfter`, `peakActive`,
  `peakRetained`, `exitCode`: integers.
- `cancellationRequested`: boolean read back from the actual session.
- `artifactDeliveryFailures`: integer delivery-failure count (successful report
  requires zero; any failed callback emission prevents report completion).
- `manifestDeliveryFailures`: integer callback delivery-failure count; nonzero
  prevents successful driver report completion.
- `input`: actual `flat.compiler.incremental.v1` admission record, including
  `inputFingerprint` and `toolFingerprint`; it is not publication evidence.
- `session`: actual terminal `flat.compiler.session.v1` summary.

The event digest incrementally hashes JSON `[transition, source]` followed by LF
for each emitted transition (SHA-256); no in-memory project history is retained.
The options fingerprint hashes JSON of the exact effective argument array in
UTF-8. Persistent input evidence binds source content/options/tool independently.
Session completion/cleanup counts do not prove subprocess reaping or disk cleanup.
Actual artifact temporary create/remove/finalizer events stream through
`CompilerArtifactLifecycleObserver`, selected by
`CompilerInvocationObserver.artifactLifecycleObserver()`. CLI passes the same
admitted revision to both sinks and the actual destination root to the spool.
The driver emits `flat.compiler.artifact-lifecycle.v1` with exact fields:

- `schema`, `revision`, `identity`, `destination`, `transition`: strings.
- `sequence`: monotonically increasing integer for this artifact observer.
- `path`: actual temporary path string, or null for publication.
- `removed`: boolean from the storage observation (false where not applicable).
- `failure`: actual finalizer exception message or null.

Transitions are `temporary-created`, `temporary-removed`, `published`, `finalized`.
Callbacks follow actual existence/absence checks in the artifact storage owner;
no session-completion inference is used. Random temporary paths are preserved as
observed; stable correlation is revision + destination + artifact identity.
Delivery failure is counted in one scalar and fails driver reporting, rather than
pretending that absence of records means successful cleanup. Process cleanup
remains outside this artifact-only scope.

### Manifest lifecycle stream

`CompilerInvocationObserver.manifestLifecycleObserver()` supplies the optional
`manifestObserver:` constructor argument to `CompilerIncrementalState`. The
frozen incremental producer reuses the exact artifact lifecycle callbacks;
no callbacks are synthesized by CLI or driver. Driver records use schema
`flat.compiler.manifest-lifecycle.v1`, with exactly the same fields/types and
transition names as the artifact stream above, but an independent sequence.

Bindings use the admitted input revision, manifest filename as `identity`, and
exact normalized `<output>.flat-incremental.json` path as `destination` (not the
compiled output path). `path` is the actual manifest temporary directory/payload
observed by the incremental owner. `published` follows the manifest rename;
`finalized` describes verified cleanup of its owned temporary tree, not lock
release or process cleanup. A failed finalizer emits its actual failure and
removal state; driver delivery errors are counted separately in
`manifestDeliveryFailures`. No event history is accumulated. Cancellation before
manifest publication ordinarily creates no manifest temporaries; absence of
records is not fabricated into a cleanup-success flag. Warm hits likewise need
not create or republish a manifest.

## Bounded filesystem integration

`FilesystemCompilerSourceProvider(values, maxSourceBytes)` now uses
`FileReader.readAllContentsBounded`, default 4 MiB, and CLI supplies four times the
linker character capacity. Directory discovery bounds immediate entries before
filtering/sorting with `maxDirectoryEntries` (default 16384), counting hidden and
ignored entries too. Hidden source exclusion is preserved after enumeration.
Overflow throws instead of retaining/accepting a prefix.

## Deferred validation

```
airship --use-links -f packages/Flat-Compiler install test -t es6
airship --use-links -f packages/Flat-Compiler run test
```

New schedule suite and the driver executable are registered centrally. Acceptance
launch/consumption remains coordinator/acceptance-worker work. Do not count
source-authored tests or unexecuted producer records as acceptance evidence.

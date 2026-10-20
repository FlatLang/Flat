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

## Deferred verification

Source tests cover glob/dependency closure beyond 64 files, exclusions and finite
limits, target overlay order, gated/reversed read completion at capacities 1/2,
cancellation drain, generated CLI execution and reuse hit/change/removal/tool
invalidation receipts. Tests/builds are intentionally not executed in the current
source-first implementation wave. Full seven-suite production acceptance,
Java-free Airship/compiler rebuilds, persistent incremental runtime verification and scaling
acceptance remain unverified.

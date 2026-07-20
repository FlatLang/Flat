# Flat-Compiler Agent Guidance

Follow the repository-root `AGENTS.md`. This file adds compiler-orchestration rules.

## Responsibility

`Flat-Compiler` coordinates source discovery, frontend work, semantic readiness, lowering, targets, artifacts, diagnostics, telemetry, and cancellation. It must not absorb package-owned lexer, parser, semantic, lowering, or backend logic.

## Architecture

- Represent an active compilation as a session with independent diagnostics, progress, artifact, and completion channels.
- Keep CLI parsing, terminal presentation, and process exit outside compiler internals.
- Schedule work by dependency readiness, not by completion of a whole previous phase.
- Use source-, declaration-, type-, member-, graph-component-, target-, and artifact-scoped sealing instead of broad project barriers.
- A blocked work item must release its worker and become runnable again when its dependencies change.
- Keep source/member work bounded with explicit queue capacities and maximum concurrency.
- Never use a project-wide `Promise.all`, `Async.all`, or full materialization as the default phase boundary.
- Track queue depth, in-flight work, time blocked by backpressure, time to first declaration/artifact, and cancellation/failure counts.
- Keep compilation behavior deterministic across concurrency settings and task schedules.
- Artifact publication must be atomic at the artifact boundary; unrelated artifacts may continue independently.

## Tests

- Drive compiler behavior through an in-process test harness, not parsed CLI logs.
- Every vertical language slice ends with generated ES6 execution.
- Include tests where one source/member is gated and unrelated work reaches later stages first.
- Include scope-local failure and cancellation tests.
- Verify concurrency 1 and higher concurrency produce identical diagnostics and artifact hashes.
- Keep package-level dogfooding tests distinct from parse-only frontend corpus tests.

# Flat-ES6-Writer Agent Guidance

Follow the repository-root `AGENTS.md`. This file adds ES6 backend rules.

## Responsibility

Consume resolved, typed, and lowered compiler units and emit deterministic ES6 artifacts. The backend must not recover missing semantic phases.

## Backend invariants

- Do not perform name resolution, type inference, overload selection, generic binding, or interpretation of unlowered Flat syntax.
- Reject unsupported lowered constructs with structured diagnostics.
- Emit function, type, module, or other independently owned fragments concurrently where practical.
- Give every fragment a deterministic artifact identity and canonical position independent of completion order.
- Keep artifact reorder buffers bounded; spill fragments rather than retaining unlimited output in memory.
- Prefer module/artifact-local finalization over whole-program concatenation.
- If one bundled file is required, concatenate bounded/spooled fragments as an artifact-local finalization step.
- Generated names and mangling derive from stable semantic identities.
- Commit completed artifacts atomically and clean temporary fragments on failure or cancellation.

## Tests

- Add focused exact-output tests for small lowered units.
- Add execution tests for complete language behavior.
- Test unsupported-input diagnostics.
- Test out-of-order fragment completion and bounded assembly.
- Test identical output hashes across concurrency settings and randomized schedules.
- Test cancellation/failure cleanup and atomic artifact publication.

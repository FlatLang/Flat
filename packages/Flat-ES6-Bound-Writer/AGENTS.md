# Flat-ES6-Bound-Writer Agent Guidance

Follow the repository-root `AGENTS.md`.

## Responsibility

Consume only reduced immutable `Flat-Bound-IR` units and emit deterministic ES6 fragments and artifacts. This is the new compiler backend; `Flat-ES6-Writer` remains the legacy AST compatibility backend during migration.

## Boundaries

- Do not import parser, AST, compiler orchestration, or semantic index implementations.
- Do not perform name resolution, inference, overload selection, or language lowering.
- Reject unsupported reduced input with structured diagnostics.
- Derive names, fragment identities, and ordering from stable semantic identities.
- Keep assembly bounded/spooled and artifact commit atomic.

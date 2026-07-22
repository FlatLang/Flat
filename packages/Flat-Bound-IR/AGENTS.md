# Flat-Bound-IR Agent Guidance

Follow the repository-root `AGENTS.md`.

## Responsibility

Own immutable target-independent bound/typed program, type, function/member, statement, and expression contracts consumed by lowering and target backends.

## Boundaries

- Consume stable compiler identities and semantic types; do not import parser implementations, compiler orchestration, or target writers.
- Every expression carries an explicit semantic type; conversion and call nodes carry selected semantic evidence.
- Function/member units must be independently publishable and lowerable.
- Do not expose mutable syntax, semantic indexes, executor state, or target-specific representation.

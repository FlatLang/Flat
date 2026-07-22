# Flat-Declaration-Index Agent Guidance

Follow the repository-root `AGENTS.md`.

## Responsibility

Own immutable declaration-symbol publication, partitioned package/type/member indexes, hierarchical scope lifecycle, deterministic duplicate results, and pending lookup readiness.

## Boundaries

- Do not import parser implementations, compiler orchestration, type checking, lowering, or target backends.
- Symbols must derive stable identities from immutable compiler-model identities and ranges.
- A scope remains open until explicitly sealed; missing results are published only after the relevant scope chain seals.
- Pending lookups suspend through futures and must not occupy a controlled executor worker.
- Duplicate publication keeps the first source-ordered symbol and reports the rejected symbol explicitly.
- Partition state by package/type/member ownership; do not add a global mutable semantic model.

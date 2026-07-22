# Flat-Name-Binding Agent Guidance

Follow the repository-root `AGENTS.md`.

## Responsibility

Own immutable binder work units, dependency-driven name lookup, bound-reference results, and deterministic binding diagnostics.

## Boundaries

- Consume compiler-model identities and declaration-index contracts; do not import parser implementations, compiler orchestration, type checking, lowering, or target backends.
- Binder work must be independently schedulable by function body, field initializer, or static block.
- Pending symbol readiness must suspend the work item without occupying an executor worker.
- Preserve source-derived deterministic lookup candidate and diagnostic order.
- Keep type selection, conversion, overload ranking, and lowering outside this package.

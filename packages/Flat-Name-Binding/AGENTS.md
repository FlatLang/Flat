# Flat-Name-Binding Agent Guidance

Follow the repository-root `AGENTS.md`.

## Responsibility

Own immutable binder work units, dependency-driven name lookup, source-syntax binding for independently schedulable bodies, bound-reference results, and deterministic binding diagnostics.

## Boundaries

- Consume compiler-model identities, parser syntax contracts, declaration-index contracts, and type/Bound contracts; do not import compiler orchestration, lowering, or target backends.
- Binder work must be independently schedulable by function body, field initializer, or static block.
- Pending symbol readiness must suspend the work item without occupying an executor worker.
- Preserve source-derived deterministic lookup candidate and diagnostic order.
- Binding may invoke target-independent type operations and construct typed Bound nodes after names are resolved; keep reusable type rules in Flat-Type-System.
- Keep overload ranking implementations and lowering outside this package.

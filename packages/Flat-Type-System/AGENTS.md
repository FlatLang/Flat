# Flat-Type-System Agent Guidance

Follow the repository-root `AGENTS.md`.

## Responsibility

Own target-independent semantic types, conversions, call applicability/ranking, generic substitution/inference, and function-local type/control-flow results.

## Boundaries

- Consume stable compiler-model and bound-reference contracts; do not import parser implementations, compiler orchestration, lowering, or target backends.
- Types and conversions must be immutable and target-independent.
- Error and unknown types must preserve failure isolation and suppress invalid cascades without hiding independent diagnostics.
- Inheritance stabilization must remain component-local; do not create a whole-project type barrier.
- Overload ordering and diagnostics must derive from stable semantic identities, never task completion order.

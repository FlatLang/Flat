# Flat-Project-Loading Agent Guidance

Follow the repository-root `AGENTS.md`.

## Responsibility

Own immutable project/package/source-root/dependency/import/default/exclusion/target option contracts and source-provider behavior.

## Boundaries

- Do not parse CLI arguments or import compiler orchestration, parser, semantic, lowering, or backend packages.
- Providers expose immutable source snapshots; callers choose scheduling and buffering.
- Filesystem providers may enumerate one configured root but must not introduce a whole-project compiler barrier.
- Configuration validates ownership and required values at construction.

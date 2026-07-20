# Parser-Core Agent Guidance

Follow the repository-root `AGENTS.md`. This file adds generic parser-engine rules.

## Responsibility

Keep generic incremental parser mechanics here. Flat-specific grammar and semantic policy belong elsewhere.

## Streaming invariants

- Preserve ordered token consumption within each source while allowing independent sources to progress concurrently.
- Publish immutable completed syntax units at safe grammar boundaries instead of waiting for EOF when correctness permits earlier publication.
- Do not expose mutable matcher, parser-stack, or parse-context state after publication.
- Do not retain a complete token stream when a bounded lookbehind/window, body source range, or explicit spool is sufficient.
- Define ownership and lifetime for tokens, matches, contexts, and published units.
- Parser recovery, failure, and cancellation should remain source- or syntax-unit-local whenever possible.
- Backtracking and partial matching must have bounded, observable retention behavior.
- EOF is a semantic event: report incomplete constructs and flush only valid completed units.

## Tests

Cover:

- Arbitrary source-chunk boundaries.
- Partial matches and backtracking.
- Safe incremental publication boundaries.
- No premature publication.
- EOF and trailing-token diagnostics.
- Recovery after invalid syntax.
- Bounded token/match retention.
- Cancellation cleanup.
- Independent progress across sources.
- Identical parse results under different chunking and scheduling.

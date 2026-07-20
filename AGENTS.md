# FlatLang Monorepo Agent Guidance

## Repository workflow

- Treat `packages/` as the FlatLang package workspace. Read the target package's `flat.json`, tests, and matching `.github/workflows/` file before changing it.
- Preserve package and imported-subtree boundaries. Keep changes in the package that owns the behavior unless an API change genuinely crosses packages.
- Build and test Flat packages through Airship from the repository root with local workspace resolution:
  ```bash
  airship --use-links -f packages/<Package> install test -t es6
  airship --use-links -f packages/<Package> run test
  ```
- Run the narrow owning-package suite first, then suites for directly affected dependents. Run `git diff --check` before finishing.
- Do not commit generated `dist/`, `target/`, cache, or temporary fixture output unless a task explicitly requires it.
- Do not stage or commit the local compiler progress document.

## Package architecture and domain boundaries

- Each package must own one coherent domain, capability, or infrastructure concern and expose a small, self-describing public contract.
- Put behavior in the package whose domain owns the decision. Do not place behavior in a convenient caller merely to avoid changing the owning package.
- Keep orchestration separate from domain implementation. Coordinators may compose package APIs; lower-level packages must not depend on coordinators.
- Dependencies must follow a clear direction. Frontend packages must not depend on semantic or backend packages; target backends must not be imported into parsing or semantic domains.
- Production dependency cycles are prohibited. Test-only dependencies may bridge layers only when they do not leak into production APIs.
- Do not make packages depend on each other for incidental helpers. Keep a helper local unless it represents a stable, reusable domain abstraction.
- Do not create generic `Utils`, `Common`, `Models`, or catch-all packages. Shared packages require a cohesive domain and more than one legitimate consumer.
- Do not turn existing shared packages into dumping grounds. New types belong there only when the package owns their lifecycle and semantics.
- Prefer domain-specific types and operations over loosely typed maps, generic event names, boolean mode flags, and cross-package mutable state.
- Public APIs must express domain concepts, ownership, lifecycle, failure, ordering, and cancellation clearly enough that callers do not need implementation knowledge.
- Keep implementation details private. Do not expose parser stacks, target-specific details, mutable indexes, queue internals, or transport/runtime mechanics through unrelated package APIs.
- A package must be independently understandable from its name, `flat.json`, public source types, and tests. Update package descriptions when responsibilities change.
- A cross-package change requires an explicit contract change, tests in the owning package, and validation of directly affected consumers.
- Adding a production dependency requires documenting why it belongs at that architectural layer and why a smaller local abstraction is insufficient.
- Extract a new package only when the responsibility is cohesive, independently testable, reusable, and has a stable boundary. Do not create speculative packages or one-type wrappers.

### Compiler package direction

The intended production dependency direction is:

```text
compiler orchestration
    -> source/project loading
    -> lexer and parser APIs
    -> syntax/AST contracts
    -> declaration and semantic contracts
    -> bound/lowered IR contracts
    -> target backends
    -> artifact sinks
```

- Generic infrastructure packages must not import compiler orchestration or language-specific packages.
- `Parser-Core` remains language-neutral; Flat grammar belongs in `Flat-Parser`.
- Syntax/AST packages must not depend on target writers.
- Semantic and lowering decisions must remain target-independent.
- Target writers consume lowered contracts and must not reach back into parser state.
- `Flat-Compiler` composes the graph but should contain minimal domain behavior itself.

## Compiler development priorities

- Complete the Flat-written compiler through TDD-driven vertical slices.
- Preserve streaming and event-driven processing, bounded memory, maximum practical concurrency/parallelism, deterministic behavior, and failure isolation.
- ES6/Node is the first complete target. The legacy Java compiler is a behavioral oracle and bootstrap mechanism, not the architecture to copy.

## Dataflow invariants

- Prefer bounded typed flows and immutable work units over whole-phase arrays.
- Do not add a whole-project barrier when synchronization can be scoped to a source, scope, package, type, function, inheritance component, target, or artifact.
- Every barrier must have a named semantic reason, explicit scope, and test coverage.
- Every flow edge must define capacity, ordering, ownership, failure, completion, and cancellation behavior.
- Do not introduce unbounded queues, implicit replay backlogs, or unbounded in-memory artifact reordering.
- Treat `consumeAll`, `toArray`, `toSet`, broad `Async.all`, and equivalent collection operations as explicit barriers. Document why complete materialization is required and why a smaller boundary is insufficient.
- Do not occupy a worker while waiting for a symbol, scope, dependency, or upstream result. Suspend the work item and return the worker to the executor.
- Published syntax, semantic, lowered, and artifact units must be immutable.
- Partition shared indexes by semantic ownership. Do not protect the entire compiler model with one global lock.
- Derive symbol IDs, generated names, diagnostic order, and output order from stable source/semantic identities—not task completion order.
- Processing may be unordered; observable diagnostics and artifacts must be deterministic.
- Keep semantic resolution, type inference, overload selection, and language lowering out of target writers.
- Keep process exits and presentation logging at CLI boundaries. Internal APIs return results, diagnostics, streams, or failures.
- Propagate cancellation structurally and isolate recoverable failures to the smallest valid source/type/member/work unit.

## TDD requirements

- Start behavior changes with a failing test, implement the smallest complete behavior, then refactor.
- Test the narrowest owning layer and add an end-to-end execution test for every completed language feature.
- Concurrency-sensitive changes must test applicable properties: backpressure, capacity limits, bounded in-flight work, overlap, no unrelated head-of-line blocking, ordering, schedule independence, failure isolation, cancellation, and cleanup.
- Prefer controlled executors/gates and deterministic schedules over timing sleeps.
- Run schedule-sensitive tests with concurrency 1 and greater than 1; use randomized or reversed completion order where practical.

## Language feature definition of done

A language feature is complete only when applicable coverage includes:

- Valid and invalid syntax.
- Accurate source ranges and diagnostics.
- Declaration/name resolution.
- Type checking and overload behavior.
- Lowering into the documented backend input subset.
- ES6 emission.
- Generated-code execution.
- Interaction with neighboring features.
- A coverage-matrix update.

Parsing alone means **syntax-supported**, not implemented.

## Validation reporting

Before finishing, report:

- Files and behavior changed.
- Exact build/test commands run and their results.
- Relevant suites not run and why.
- Any new barrier, materialization point, queue, mutable shared index, or determinism risk introduced.
- Any production dependency added or package responsibility moved, including its architectural rationale.

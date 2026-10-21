# Bound lowering handoff (source-only)

## Implemented contracts

`BoundTreeLoweringPass` is the package-local-domain recursive rewrite contract for
bound function units. It copies immutable nodes, preserving selected types,
conversion evidence, identities, dispatch/async/suppress-await flags, catch flags,
switch fallthrough, constructor calls/delegation, parameter defaults, external
rendering/name bindings and all existing branch/loop owners. All current concrete
bound expression and statement classes have explicit dispatch entries; unknown
nodes fail rather than silently hiding an unlowered child. Core, native and
deferred passes specialize their own rich forms. Collection lowering delegates
otherwise-unhandled children to the same traversal.

`BoundEvaluationExpression(range, semanticType, setup, value)` is canonical,
target-independent expression-local sequencing. Its `setup()` accessor returns a
copy. Setup executes in order only when this operand is evaluated; then `value`
executes exactly once. Abrupt completion skips the value. No function, lexical
scope, control-owner, exception, async, or cancellation boundary is introduced.
Semantic declaration identities and original visibility remain authoritative.
Nested evaluation nodes compose in place. In particular:

- `a && E`, `a || E`, ternary arms, fallback arms and safe-call arguments evaluate
  `E` only when selected.
- A loop-condition evaluation runs on every condition check, including after
  continue; it is not a one-time preheader.
- A receiver/argument/array-element evaluation stays after earlier operands and
  before later ones. Earlier values must be stabilized before any later setup.
- Return/break/continue/throw in setup retain their enclosing owner; finally runs
  normally, including when it overrides an earlier exit. Async suspension and
  cancellation propagate in that same enclosing computation.
- Deferred lambda creation setup stays at creation; a real lambda's body/setup
  stays inside that lambda and runs only when invoked. No extra lambda is added.

Deferred lowering no longer concatenates argument preludes before a call. Only a
whole expression statement's outermost evaluation prelude is flattened, where
there is no earlier sibling or selection boundary. Fallback and safe access/call
lowering also use this node instead of an IIFE. Their receiver/left operand is
stored exactly once using deterministic source-derived symbol identity, and their
conditional value stays in the enclosing control scope.

## Required consumer wiring outside this lane

The ES6 bound writer does **not** yet consume `BoundEvaluationExpression`.
Production completion is blocked on this explicit API change. Its smallest needed
capability is expression emission that can split into enclosing statement/control
flow while returning a typed result reference, preserving operand evaluation
order and lazy selection. It must handle nested evaluations in defaults,
constructor arguments, external interpolation, lambda bodies and loop conditions,
with owner-aware abrupt exits. An eager prelude, comma-only encoding for arbitrary
statements, or IIFE is not an implementation of this contract. Writer snapshots
and execution tests expecting collection-generated IIFEs must be updated to test
single evaluation, actual short-circuit execution, cleanup, async/cancellation and
original control ownership instead. No writer files were changed here.

No manifest or central registration changes are needed for the owning tests;
they are in already-registered `LoweringPass_Test` and `BoundIR_Test`.

## Implemented cleanup-aware foreach latch

`BoundLoopLatchStatement(range, condition, body, advance, loopIdentity)` is the
canonical typed pre-test loop. `loopIdentity` is a `SyntaxIslandIdentity`;
`bodyValues()` and `advanceValues()` return snapshots. `unitKind()` is
`loop-latch`. Foreach initialization remains outside and runs once. Its condition
is checked before each iteration, with expression-local setup evaluated at each
check. Its advance is stored once, separately from its body, and executes after
normal completion or a continue targeting this loop **after** body cleanup has
finished, only if that completion survives cleanup. A cleanup replacement of an
exit determines the surviving completion. No advance runs for an exit from this
loop by break, return, throw or cancellation. A switch-local break can leave the
body to complete normally; a switch does not intercept a loop continue.

Advance is not a finally block. Its own suspension/failure/cancellation propagates
without replay. A continue inside advance proceeds to the next condition check,
not back into advance. A break inside advance exits. Nested loops and actual
lambda/function bodies retain their own control owners; evaluation-expression
setup retains the enclosing owner. Consumer lowering must preserve these typed
regions, not inject advance at continue sites or inspect emitter label spelling.

Collection lowering now produces this node for every foreach and removes its old
advance-injection/rejection logic. Every recursive pass traverses condition, body
and advance while preserving the exact loop identity. The ES6 backend consumer
is still required outside this lane. `BoundBreakStatement(range)` is also supplied
for existing recursive visitor/test references: nearest loop/switch break owner,
`unitKind() == "break"`. No binder constructor signatures changed.

## Lambda declaration boundary (compatible trailing constructor parameter)

`BoundLambdaExpression(..., statementValues = BoundStatement[],
declarationContract: FunctionSemanticType = null)` retains the binder-selected
unsubstituted callback declaration independently from the concrete checked
`semanticType`. Declaration arity and async shape must match; generic result and
parameter types need not equal their concrete substitutions. All IR lambda copies
preserve the exact declaration object. Null means use the concrete contract.

Backend must first honor concrete body and explicit-return conversions, including
boxing a concrete primitive result when crossing generic storage, and only then
apply declared generic erasure. Do not infer numeric representation from a generic
return or runtime value. Async results are converted at the callback result
boundary, not by converting a promise container. No constructor arguments before
this optional trailing parameter changed. Binder already supplies the named
argument; backend must read it. Generic _function-reference adapters_ are separate
from lambda creation and remain an upstream binder/compiler residual, not repaired
by inventing an IR lambda contract in the writer.

## Evidence and deferred validation

Authored source tests cover recursive structured bodies/catch/finally/switch,
lambda bodies, parameter defaults, parent/delegating constructor arguments,
array sizes/indexes, interpolation/external value segments, conversions, hidden
rich-form rejection, lazy boolean/conditional operands, repeated conditions,
ordered argument-local setup, safe-call selection in both lowering orders,
immutable setup snapshots and preserved abrupt-control nodes.

Owning validation used the explicitly selected Java bootstrap compiler with a
1200-second process-group build watchdog. Initial build failed on a bootstrap
reserved-word collision in a backtick test name; renaming only the test title
allowed the single retry to reach 67 syntax/type/API diagnostics. All 67 have
source dispositions and repairs (typed iteration arrays, explicit cast boundaries,
named lambda parameters, reserved local renames, and the missing static owner
constructor argument). Neither build produced a fresh test artifact; runtime was
not run. The repairs and subsequent lambda declaration API remain unvalidated.
Full logs, receipts, artifact hashes and diagnostic dispositions are retained in
`/tmp/flat-finish-validation/ir`. No further builds are authorized in this lane.
Later validation from the repository root, when the coordinator grants a slot:

```sh
airship --use-links -f packages/Flat-Bound-IR install test -t es6
airship --use-links -f packages/Flat-Bound-IR run test
```

Then validate directly affected Name-Binding, ES6-Bound-Writer and Compiler
consumers and generated-code execution after writer wiring. No new dependencies,
whole-project materializations, queues, shared indexes or scheduling barriers were
introduced. Traversal allocations remain scoped to existing immutable function,
branch and expression units; recursion depth follows input tree depth.

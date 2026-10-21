# ES6 evaluation/latch integration — source freeze

Implemented, not compiled or executed in this wave.

## Consumer contract

`Es6CodeResult.setup` contains enclosing-owner statements; `code` is the resulting
operand. Callers must consume both. `Es6EvaluationPlan` captures earlier operands,
conversions and instance dispatch selection before later operand setup; conditional
and short-circuit branches remain selected control flow. Setup locals retain
semantic names and function visibility rather than gaining an artificial lambda
scope. Nested expression setup does not create a function, async or exception
boundary. Temporary allocation is local to an emission owner; static initializer
prefixes include the owning stable type identity.

Statement, store, return, throw, repeated condition, switch selection, function
and lambda defaults/body, constructor/delegation arguments and static initializer
consumers are wired. Lambda defaults run on invocation only. Setup is not emitted
in a synthetic IIFE. Existing fallback/safe-field execution fixtures now consume
setup as well as the result expression.

`loop-latch` and `break` are accepted. The body is a labeled block inside the
pre-test loop. A body continue breaks that block: JavaScript cleanup completes
before the stored advance. Cleanup overriding that exit with break/return/throw
skips advance. A continue in condition/advance targets the loop directly. Nested
loops reset the continue owner; switch selection retains it and owns its breaks.
Advance is never implemented as finally and is never copied into a continue.

## Exact remaining upstream contract gap

Opaque `BoundExternalStatement` interpolation supplies only arbitrary target text
plus value segments. It has no structured evaluation/selection insertion point.
If a segment value carries statement setup, moving it before the external text
can violate lazy/loop/exception/control ownership; embedding it in an IIFE violates
`BoundEvaluationExpression`. The writer explicitly returns
`es6.external-evaluation-context` for this case instead of discarding setup.
Upstream must either publish a structured target-expression/statement insertion
contract (including lazy selection and abrupt-control ownership), or guarantee
external segments are setup-free after lowering. No Bound IR files were edited.
Ordinary setup-free external segments remain supported.

## Authored execution evidence (not run)

Already-registered `Es6RepresentationExecution_Test` now covers selected nested
setup, short circuit, earlier operand stabilization, return/throw setup with
finally, normal/continue/break/return/throw and cleanup overriding those exits,
repeated conditions, nested loop/switch continuation ownership, advance continue,
foreach lowering through function emission, lambda/default invocation selection,
parent argument sequencing, async suspension and rejection without replay.
`Es6ExceptionExecution_Test` retains fallback/safe-field outcomes while consuming
the new setup contract. No suite registration or manifest changes are needed.

No new dependency, whole-project barrier, queue, shared mutable index or scheduler
claim. The visitor and generated temporaries are scoped to an expression/function.
Residual scaling risk: the capability scan uses the immutable recursive visitor
and may revisit/copy subtrees; pathological deeply nested expressions need later
scaling validation. Function-local temporary counters require an orchestrator per
concurrent owner; the coordinator now constructs independent task-local unit,
statement and expression orchestration while retaining the selected pure emitters.

## Lambda declaration-contract integration (source-only)

Lambda emission reads `declarationContract` without substituting it. Expression
results and block returns execute concrete conversions first, then box primitive
results at a declared generic result boundary. Real nested lambdas get independent
return owners. Null/reference/already-boxed results remain identity; async boxing
occurs on the payload after suspension, not on its promise. Primitive-to-generic
conversion nodes also require the same evidence, preventing erasure from hiding a
missing box.

`Es6ExpressionEmitter(boxingConstructors: CallableSignature[])` accepts canonical
constructors explicitly resolved by the semantic owner. Each must be synchronous,
owned, single-parameter, return its owner, and exactly match the concrete primitive
parameter type. Missing, ambiguous or incompatible evidence returns
`es6.generic-result-boxing-evidence`. No primitive-name-to-wrapper lookup exists.

Per-lambda production evidence is now wired: `resultBoxingConstructor` is read
from `BoundLambdaExpression`, passed directly for expression results, and retained
only in that lambda's statement owner for block returns. An explicitly supplied
incompatible constructor fails closed without consulting emitter-wide defaults.
The binder's `adaptCallbackReference` creates the same lambda/invocation shape;
writer coverage includes that adapter shape without eager invocation. The frozen
IR slot and recursive-copy preservation have been inspected. No compiler registry
wiring is needed for binder-produced callback nodes. Generic parameter adapters
that binder rejects for missing parameter representation evidence remain upstream.
Tests use the default writer with per-node constructor evidence; no runtime
constructor lookup or canonical-name guessing was added. None were run.

## Java seed compatibility audit (source-only)

Audited writer additions against current IR signatures and adjacent seed-compatible
source conventions. Replaced inline cast/member conditions with typed locals,
ternary constructor-array selection with typed assignment, implicit callback
parameters with explicit names, ambiguous loop arrays with explicit element types,
and optional lambda declaration arguments with named arguments. Renamed the host
interpolation test title to avoid the reserved target-code keyword; assertions
and generated execution behavior are unchanged. Existing executor submission and
observer calls retain their immediate-Future `$` markers; this wave introduced no
new Future-returning helper calls. Public APIs are unchanged by this syntax audit.
Source frozen and ready for validation, pending explicit Binder source-freeze
confirmation. Do not start a build while Binder is still repairing its runtime
roots. Reserved validation limits: build 1200 seconds, runtime 180 seconds, retain
full receipts, stop at the first new root without a quiet retry.

## Latest validation and fixture disposition

`/tmp/flat-finish-validation/writer/ordinal-repaired/` contains the fresh selected
Java-seed build/runtime receipts: build exit 0, runtime 173/175, all 38
representation tests reached (37 passed). Source inputs remained stable and the
fresh bundle hash was b2710104968a1482c553216105c8c2c5146d65c582fd380c735760aaaf25a871.
The two failures are now source-repaired but not revalidated:

- The lazy/nested setup fixture concatenated independent orchestrator namespaces;
  it now emits both expressions through the same statement orchestrator. Both
  expressions, source-order/single-evaluation checks and lazy throw guards remain.
- The explicit generic-prefix fixture admitted Value only. Binder's precise cause
  is missing canonical Bool storage constructor evidence, not inference. It now
  admits the canonical `flat/primitive` Bool constructor source context, emits both
  types, and checks returned canonical Bool instances before projecting their
  resolved storage field. The existing `true false` output assertion remains.
  Binder disposition: `/tmp/flat-finish-validation/binding/writer-all-callback/`.

No production global counter, guessed box, assertion weakening or API change was
introduced by these fixture repairs. Source frozen pending Parser's sole-heavy
validation slot release; no build/run after these repairs.

## Deferred validation (repository root; serialized by coordinator)

```sh
airship --use-links -f packages/Flat-Bound-IR install test -t es6
airship --use-links -f packages/Flat-Bound-IR run test
airship --use-links -f packages/Flat-Name-Binding install test -t es6
airship --use-links -f packages/Flat-Name-Binding run test
airship --use-links -f packages/Flat-ES6-Bound-Writer install test -t es6
airship --use-links -f packages/Flat-ES6-Bound-Writer run test
```

Then compiler and production dogfood/lifecycle/scaling acceptance. Only scoped
`git diff --check` ran in this wave; it passed. Full acceptance is not claimed.

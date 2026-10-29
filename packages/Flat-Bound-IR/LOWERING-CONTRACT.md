# Bound lowering handoff (source-only)

## Contextual floating constants (T1/T2/T3 source wave, NOT RUN)

`BoundLiteralExpression(range, semanticType, sourceValue, canonicalValue=null)`
retains original source lexeme and range independently from selected canonical
Float/Double constant text. `emissionValue()` chooses canonicalValue if supplied,
otherwise the legacy sourceValue. Canonical text is accepted only on primitive
Float/Double, nonempty; semantic syntax/rounding/overflow/underflow are exclusively
Type-System decisions consumed by Binder. No backend host parse, Math.fround or
rounding rule. Recursive/core/collection/deferred lowering retains immutable
literal evidence. ES6 emits selected decimal text verbatim, including -0; exact
binary32 values are exactly representable as ES6 Numbers. BoundIRAssertions
continues to report sourceValue as provenance, not executable constant text.

Registered source-only BoundFloatingLiteral_Test checks canonical/source/range
preservation and backward compatibility. Es6FloatingLiteralExecution_Test authors
binary32 oracle execution (1e-8, even/odd midpoint ties, subnormal, signed zero),
exact just-above-midpoint double-rounding trap, binary64 round trip and unary
negative zero. Math.fround appears only in regression oracle programs, never
production emission. Tests NOT RUN until all semantic producer/IR/backend source
writers finish. No dependency/queue/barrier or shared mutable index added.

## Approved normalized constructor plans (T2/T3 implementation, NOT RUN)

`BoundConstructorPlan(entry, fieldInitializer, preBaseStatements, parentCall,
initializationCalls, delegationPath, range)` is immutable target-independent
allocation evidence. `BoundFunctionUnit` appends optional `constructorPlan`.
Planned entries have empty raw body/parent/delegation. All constructor entries in
one owner are planned together; raw guards remain unchanged for raw owners.
Defaults stay on entry parameters and execute only in allocation-helper prologue;
preBase contains delegation bindings/defaults, not duplicate entry defaults.

Plan validation checks unique selected path, reverse body-stage identities,
shared once-first field helper, exact published synchronous Unit helper signatures,
helper parameter ownership, complete path suffix consistency, exact direct-parent
evidence, receiver-free lexical setup, default forward/self reference rejection,
and no suspension/control escape. Typed native references are visited; opaque
receiver/control/eval source rejects conservatively rather than rewriting text.
All array storage is defensive. Helpers derive identities through the two public
static BoundConstructorPlan identity functions, never from display names.

Recursive lowering copies plans/defaults/setup/selected calls with conversion
objects/ranges/path identity intact. ES6 planned emission preserves primary new
and selected overload static-factory ABI, forwarding original omission unchanged.
Private allocation helper runs entry prologue/setup then exact selected parent
allocation helper, root allocates one most-derived reference object, then current
fields and reversed constructor body stages run on that same receiver. Separate
Unit helpers preserve return/finally boundaries. Missing own parent protocol is
an explicit TypeError, never a raw-primary or Reflect fallback. Ordinary raw
emission remains separate. Root/native admission is producer-owned; no native
internal-slot allocation inference added.

Registered source-only BoundConstructorPlan_Test and
Es6ConstructorPlanExecution_Test cover immutable copies, cycles, stage mismatches,
missing targets, receiver/capture/forward-default/base rejection, multihop unwind,
parent overload selection, entry/dependent defaults, stage/parent argument once,
fields/prototype/receiver identity once, return/finally and exception isolation,
supplied null versus omission, and missing raw-parent protocol. No tests run in
the two-lane implementation wave. Owner-local constructor graph normalization
belongs to Binder; no project barrier/queue/dependency was added here.

## T3 conditional common-reference handoff (2026-10-28, NOT RUN)

The existing `BoundConditionalExpression` and `BoundConversionExpression` are
sufficient for a hierarchy-proven common reference join. No new IR node, generic
erasure, native text coercion, or backend type-selection rule is needed.

The semantic producer supplies a Bool condition and a chosen common result type.
Each differing branch is converted _inside its branch_ using applicable evidence
whose source is the original branch type and destination is the chosen result.
For the Array text-mapping frontier this means declared SourceString -> canonical
declared Object and generic E -> that same proven Object. The false branch keeps
the original generic expression under its reference-upcast node, not a String
conversion, primitive builtin Object replacement, or erased literal. Nullable
results require explicit nullable-lift/null evidence; a nullable branch cannot be
published as a nonnullable result. Canonical declaration identity and hierarchy
registration are producer evidence, never inferred from runtime values or names.

`BoundConditionalEvidence_Test` covers evidence, source types, reference identity
and range preservation through recursive, core-language and collection-control
copies; mismatched unconverted branches and nullable narrowing remain rejected.
`Es6ConditionalReferenceExecution_Test` authors generated execution for declared
Object array mapping with quoted/interpolated text, original generic object and
boxed scalar identity, branch-only conversion/allocation, condition/operand once,
raw quote/apostrophe/backslash/newline preservation, and both direct conditional
and branch-local setup paths. Separate nullable execution checks null selection
and original reference identity. Tests are registered but **NOT RUN** in this
implementation-first wave. No production behavior change was required in the
lowering or writer: existing copies retain selected conversion objects and the
writer emits reference upcasts as value-preserving operations.

No queue, barrier, dependency, whole-project materialization or mutable index is
introduced. Only existing expression-local immutable nodes and test-local arrays
are used. Full semantic producer integration and real Core execution remain for
the coordinator after all seven implementers finish.

## L07 initial-wave selected-call evidence tightening (unvalidated)

`BoundCallExpression` now rejects missing required or excess arguments,
non-applicable argument conversions, conversion destinations that do not match
selected parameter types, and receiver-conversion evidence without a receiver.
The existing source-type check and defensive array snapshots remain intact.
Optional trailing parameters may still be omitted. Arguments are in selected
parameter order; source-order named-argument evaluation remains a producer
responsibility. These checks mirror the existing constructor-delegation evidence
contract and add no backend dependency or public constructor signature change.
Canonical text wrapping remains a selected constructor call; its argument
conversion targets native storage, not the constructed text result type.

New `BoundIR_Test` regressions cover arity, optional suffix omission, inapplicable
and mismatched evidence, null entries, immutable argument/conversion snapshots,
selected async dispatch evidence through recursive lowering, text constructor
storage evidence, and orphan receiver evidence. Tests were authored only. No
build, test, syntax check, formatting check or other verification was performed
for this initial wave, per user override. Historical evidence below is unchanged
and is not evidence for these changes.

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

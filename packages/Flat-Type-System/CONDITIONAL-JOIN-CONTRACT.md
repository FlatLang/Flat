# Conditional reference join contract (T1)

`TypeOperations.conditional(conditionType, trueType, falseType, range, hierarchy = null)`
retains the original four-argument behavior. The optional hierarchy adds a join only
after existing Bool/error/identity/null/nullable rules. It must be sealed and valid;
an unavailable, unsealed, or invalid hierarchy adds no conversion privileges.

`TypeHierarchyComponent.commonReference(left, right)` requires a valid sealed
component and returns a declared non-generic target only when:

- both operands are non-nullable references;
- `TypeConversions.classify(operand, target, hierarchy: component)` is applicable
  and implicit for each operand;
- the target implicitly converts to every other admitted declared non-generic
  common target, and exactly one such most-specific target exists.

This is semantic specificity, not shortest-path sum, declaration order, branch
order, or spelling. Incomparable shared interfaces are ambiguous even if a root
also admits both operands. No built-in Object fallback, primitive receiver
projection/boxing, erased generic target, explicit cast, or String coercion is
introduced. `genericArity` exposes immutable declaration arity;
`TypeConversions.isReference` exposes category only, not convertibility.

For unconstrained `E` plus source-declared String, the existing generic-to-root
conversion admits only the explicitly registered canonical root; String must
independently upcast to it. Merely naming a declaration Object is insufficient.

## Consumer integration (outside T1 ownership)

Pass the same sealed hierarchy to the operation and to **both** branch conversion
classifications. Materialize each conversion in the bound conditional using the
consumer's existing conversion mechanism. `TypedOperationResult.conversion`
remains unset: it cannot represent two independent branch conversions. Preserve
the false branch's original value/reference; do not coerce both branches to String.
No Binder or Writer edits are included in T1.

## Authored coverage (owning suite executed through explicit Java bootstrap diagnostic)

| Area                 | TypeOperations_Test coverage                                                                                           |
| -------------------- | ---------------------------------------------------------------------------------------------------------------------- |
| Ancestry             | nearest shared ancestor, direct ancestor, both branch orders                                                           |
| Determinism          | reversed declaration insertion, incomparable interface ambiguity with admitted root                                    |
| Generic/String       | explicit canonical root, branch conversion classifications, missing root registration                                  |
| Null safety          | original null lift and identity nullable pairing; no nullable ancestor expansion                                       |
| Failures             | unrelated primitive, wrong condition, absent operand declaration, missing parent, invalid/unsealed hierarchy           |
| Generic preservation | differing instances join non-generic ancestor, never erased generic declaration                                        |
| Generic bounds       | concrete reference bound uses nearest ancestor; primitive/nullable/missing/chained bounds reject without root fallback |

Generic instance targets are not synthesized. Explicit generic bounds can prove
upcasts only through a concrete non-nullable reference bound and the same
applicable implicit classifier; primitive, nullable, missing, and chained generic
bounds do not acquire a root fallback. Unconstrained E retains the explicitly
registered canonical-root rule.

No new pipeline barrier, queue, shared mutable index, dependency, or package
responsibility is introduced. Lookup materializes the finite ancestor closure of
one operand (deduplicated by declaration identity), including declared generic
nodes only for traversal, never as erased result targets. This operation-local
materialization is needed to compare every admitted ancestor for ambiguity; no
whole-component/project collection or synchronization is introduced. Candidate
comparison is quadratic in that ancestry size plus existing conversion traversal;
sealed declaration lookup uses binary search over existing sorted declarations,
not a linear component scan. Ancestry memory/depth is bounded by the existing
component graph, not a fixed language-wide limit. No asynchronous waiting or
cancellation boundary is added.

All initial tests were authored before execution. After the explicit Java bootstrap
diagnostic grant, installed public Airship rebuilt this package's current sources
with `--flatc-runtime java`; the fresh ES6 test suite reported **All 80 Successful**.
This is ordinary Java-bootstrap diagnostic evidence, not reviewed-seed admission,
post-bootstrap stage acceptance, or full Flat-written compiler dogfood acceptance.

# Production Core execution workload

`ProductionCoreWorkload.flat` is an executable regression, not a replacement Core
package. `packages/Airship/test/char_integration.py` selects the complete unchanged
`Flat-Core/src` tree, retains the production manifest dependencies, adds this
entrypoint, requests the Flat compiler explicitly, and executes the emitted ES6.
The existing four-file Char inspection test remains separate.

## Coverage matrix

| Contract                      | Execution assertion                                                                                                    |
| ----------------------------- | ---------------------------------------------------------------------------------------------------------------------- |
| Declared + declared           | Exact `declared-left\|declared-right`, count, trace `LR`                                                               |
| Native + declared             | Exact `native-left\|declared-right`, count, trace `LR`                                                                 |
| Declared + native             | Exact `declared-left\|native-right`, count, trace `LR`                                                                 |
| Native + native               | Explicit production String construction, exact `native-left\|native-right`, count, trace `LR`                          |
| Empty / special mixed strings | Empty, embedded NUL, accented character and newline retained; trace `LR`                                               |
| String presence / nonempty    | Empty reference present, null absent; explicit nonempty predicate rejects null/empty and accepts whitespace/NUL        |
| Char                          | Static/instance/NUL conversion, repeat including zero, case conversion, digit boundaries, whitespace, wrapper mutation |
| StringCharArray               | Exact contents, copy append position, independent storage, typed null equality                                         |
| Collections                   | Remove/insert/append, reverse isolation, clear storage ownership, IntRange join and half-open contains                 |
| Numeric text                  | Signed Int/Long, zero, fractional Double, signed fraction parsing                                                      |
| Double.equals(Object)         | Cross-wrapper zero, equal/unequal negative fractions, null and nonnumeric rejection                                    |
| Exceptions                    | Empty/null/populated message formatting, typed throw/catch, invalid/valid substring                                    |

String conditions are presence checks, not nonempty checks. The local
`nonemptyString` predicate names that distinct application contract explicitly;
it is not a new Core API or a binder workaround. Exception formatting owns the
same nonempty-message decision. Native/native concatenation remains native until
explicit construction of a production String for observation.

The workload throws on the first violated contract and prints exactly
`production-core-workload:ok` only after every assertion executes. The harness
also requires fresh build prerequisites, a nonempty published artifact, exact
execution output, unchanged artifact bytes, and unchanged complete Core inputs.
It does not invoke a Java fallback, rewrite generated code, or synthesize Core
declarations. A successful lightweight Char test is not success for this gate.

## Initial-wave status

IMPLEMENTED UNVALIDATED. No build, test, syntax check, or other verification was
performed in the implementation-only swarm wave. The coordinator must first
open the global validation barrier. After prerequisites are rebuilt, the
intended focused command is:

```sh
python3 -B packages/Airship/test/char_integration.py ProductionCoreAirshipIntegration
```

Compiler-suite wiring is proposed in the L17 source handoff rather than changed
across lane ownership. No production source closure is removed to accommodate
unsupported binder behavior.

# Flat-Parser Agent Guidance

Follow the repository-root `AGENTS.md` and `packages/Parser-Core/AGENTS.md`. This file adds Flat grammar rules.

## Responsibility

Keep Flat syntax and grammar publication policy here. Project-wide resolution, type checking, and target behavior do not belong in parser patterns.

## Grammar invariants

- Add positive and negative tests for each grammar feature.
- Preserve accurate source ranges and stable source-order identities.
- Explicitly define safe publication boundaries for package declarations, imports, type headers, member signatures, initializers, and bodies.
- Do not add grammar behavior that depends on a global project model.
- Forward references are handled by declaration indexing and scope sealing, not parser-global state.
- Published declarations and bodies must be immutable and independently schedulable where grammar permits.
- Parser support alone does not complete a language feature.

## Tests

- Test minimal and complex valid forms.
- Test incomplete, ambiguous, and invalid forms.
- Test source ranges and diagnostics.
- Test interactions between neighboring grammar features.
- Test declaration/signature publication before unrelated later bodies finish.
- Test that delayed parsing of one source does not block publication from another.
- Keep a corpus test over real compiler-package sources in addition to focused pattern tests.

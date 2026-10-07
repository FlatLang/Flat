# Flat Compiler

## Flat-written compiler CLI: ES6 libraries

The Flat-written `flatc` CLI routes a bounded package through
`Compiler.compilePackage`; it does not use the legacy AST writer as a fallback.
For example, from the repository root with a bootstrapped `flatc` available:

```bash
flatc packages/Flat-Core/src/flat/primitive/number/Char.flat \
  -l packages/Flat-Core/src/flat/primitive/number/Number.flat \
  -l packages/Flat-Core/src/flat/primitive/number/Integer.flat \
  -l packages/Flat-Core/src/flat/primitive/Primitive.flat \
  -target es6 -library -o characters.js
```

- Sources and `-l` dependencies may be existing files or directories. Directory
  discovery includes `.flat` and `.js.flat`; source text is loaded only after
  selection. Selection is limited to 64 unique files, although the existing IO
  directory enumeration itself materializes a root's file listing.
- `-x` / `--exclude` matches named files/directories by canonical path identity,
  then retains the existing glob/regular-expression matching as fallback.
  `--target` aliases `-target`; `--threads` aliases `-threads`. Ambiguous `-t`
  is rejected. `help` (including `help --all`) and `--help` print usage.
- Use `-o file.js` or `-d directory`; directory-only library output is
  `library.js`. A bare output filename with `-d` is relative to that directory.
  Successful compilation atomically replaces one artifact. Compilation failure
  leaves an existing artifact unchanged and publishes no partial artifact.
- Only explicit `-target es6 -library` mode is supported. Executable `-main`,
  target-runtime selection, non-default concurrency, default imports and
  application properties are diagnosed as unsupported. Quiet/progress-suppression
  flags are accepted; failure reporting is never suppressed. Exact package failures
  are presented from the bounded terminal summary diagnostic, without topic replay.

This is a library compilation slice, **not full compiler self-dogfooding**.
Generated libraries have no executable main bootstrap or public export contract.
Language support remains limited by the real parser/binder/lowering/backend
contracts; unsupported source behavior fails rather than being bypassed.
Airship's current `--flatc-runtime flat` argument forwarding does not yet supply
all target/output/dependency options automatically; direct CLI invocation is the
supported route for this slice.

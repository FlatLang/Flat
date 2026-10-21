# Bounded filesystem operations

## Public contract (ES6 / Node 20)

- `FileReader.readAllContentsBounded(Int maxBytes) -> String` opens a private
  read-only descriptor at offset zero, independently of reader cursor state.
  It accepts UTF-8 text up to `maxBytes` **bytes**, including an empty file for
  limit zero. It reads at most `maxBytes + 1` bytes, throwing
  `Exception("File byte limit exceeded")` on overflow. It never returns a
  truncated prefix. Short reads are accumulated; UTF-8 is decoded only after
  collection, so characters spanning reads remain intact. Malformed UTF-8 uses
  Node's replacement-character behavior, matching existing unbounded reads.
- `File.getChildFilesBounded(Int maxEntries) -> File[]` enumerates immediate
  children, including hidden entries, directories, and symbolic links, without
  recursion, stat calls, or filtering. It retains at most `maxEntries` children
  and observes at most one additional entry to detect overflow, throwing
  `Exception("Directory entry limit exceeded")`. Limit zero accepts only an
  empty directory. Directory buffering is explicitly one entry, not a whole
  directory listing. Native enumeration order is unspecified: sort the bounded
  result before producing deterministic observable traversal.

Negative limits throw before opening a handle. Neither operation relies on a
preflight size/count. Private handles are closed in `finally` on success,
overflow, native errors, or result-construction failure; close failures also
propagate as Flat exceptions. These operations are synchronous and do not retain
background work or queues. Callers check cancellation between operations; there
is no mid-operation cancellation or atomic filesystem snapshot guarantee.
Concurrent mutation can change observed contents/entries, but cannot bypass the
consumption/retention bounds. Limits bound data, not elapsed time on special files.

Read memory is O(maxBytes): chunks of at most 64 KiB, a final byte buffer and
UTF-8 string may coexist during decoding. This is deliberate bounded
materialization because the API returns a complete string. Enumeration memory is
O(maxEntries), plus one native directory entry and per-entry path/name storage.
These APIs do not promise a constant-byte bound independent of path lengths.
Java/C currently throw explicit unsupported-target exceptions for both new APIs;
there is no unbounded fallback. Existing APIs remain unchanged.

## Consumer handoff

ProjectLoading should replace preflight-plus-unbounded reads with
`readAllContentsBounded`, and whole-directory enumeration with
`getChildFilesBounded`. Entry limits count **all** entries, before consumer
filtering. Consumer code owns deterministic sorting, recursion limits, aggregate
project budgets, diagnostic translation, and cancellation between IO calls.
No consumer or manifest changes are included in this IO-only scope.

## Existing-path canonicalization (ES6)

`getCanonicalPathSync()` / async `getCanonicalPath()` now use Node's native
filesystem realpath operations; `getCanonicalFileSync()` / async
`getCanonicalFile()` retain their wrapping contracts. Results are absolute
physical paths. Relative symlink targets are resolved against the link's parent,
not the process cwd. Ancestor links, chained links, and symlink/`..` ordering use
filesystem semantics. No `path.resolve` pre-normalization is performed.

This is existing-path canonicalization, not a missing-output-path constructor:
missing paths, dangling links, cycles, non-directory components and permission
failures throw Flat exceptions prefixed `Could not canonicalize path: ` with the
native error code/message. There is no lexical fallback, recursive Flat walk,
whole-project scan, cache, or retained global index. Native symlink traversal
limits bound cycles; resolution is scoped to one path. Async resolution remains
native asynchronous IO. Java/C implementations are unchanged by this fix.

Regression tests cover real existing paths, relative inputs, relative/absolute
and chained directory links, ancestor links, symlink/`..`, the system temp path
(including macOS `/tmp -> private/tmp`), and missing/dangling/cyclic/non-directory
errors in both synchronous and asynchronous methods, with fixture cleanup.

**Validation status:** the earlier IO 9/9 run predates this canonicalization fix
and does not validate the current source. New source and tests are frozen pending
coordinated rebuild/retest; no build or runtime probe was run for this fix.
After IO, directly affected Airship and ProjectLoading suites also need fresh
validation. No installed tool or generated artifact was patched.

## Deferred validation

Implementation and regression tests are source-only, not validated in this wave.
Tests are in the already registered `IO_Test` class: controlled native fixtures
cover short reads, UTF-8 boundaries, growing inputs, overflow, handle zero,
negative limits, native failures and cleanup; a real temporary-directory test
covers the Node filesystem adapters. Fixtures restore native bindings in finally.
The non-JS contract test checks explicit unsupported behavior.

From repository root, during coordinated validation:

```sh
airship --use-links -f packages/IO install test -t es6
airship --use-links -f packages/IO run test
airship --use-links -f packages/Flat-Project-Loading install test -t es6
airship --use-links -f packages/Flat-Project-Loading run test
```

Java/C execution remains deferred to their supported harnesses; their existing
file APIs are incomplete and this change does not claim to complete those targets.
No production dependencies, new queues, shared indexes, or package moves added.

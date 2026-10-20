```
Flat's package manager

USAGE:
    airship <command> [options]

COMMANDS:
    setup        Setup the Airship CLI
    install      Install a Flat package
    get          Install the specified dependency
    uninstall    Uninstall a Flat package
    reinstall    Reinstall a Flat package
    update       Update a Flat package's dependencies
    clean        Clean a Flat package's out directory
    run          Run a script defined in a flat.json
    init         Initialize a new Flat package, guided by a CLI prompt
    create       Create Flat package scaffoldings
    bin          Generate a Flat package binary file
    add          Add a Flat package as a dependency
    remove       Remove a Flat package from the dependecies
    help, ?      Print help information
    publish      Publish a new release

OPTIONS:
    --version                          Print version info and exit
    --target, -t                       Specify the target language to compile the package to
    --target-runtime, --runtime, -r    Specify the target runtime the binary will build for
    --file, -f                         Specify an explicit flat.json file to reference when
                                       running the Airship commands
    --source, -s                       Specify an explicit package source to reference when
                                       running the Airship commands
    --debug, -d                        Print the command used to compile the package to stdout
                                       and exit
    --sync, -s                         Run the compilation synchronously
    --no-replace-log-line              Do not replace the logging info output after each line
    --quiet, -q                        Do not print Airship log messages
    --flatc-runtime                    Specify the Flat compiler runtime
    --flatc-script                     Select an immutable generated compiler script
                                       (requires --flatc-runtime flat)
    --use-links                        Use packages installed as symbolic links where possible
                                       in favor of any version defined in a flat.json file
    --purge                            Uninstall the dependency from the disk
```

---

`airship setup`

```
Setup the Airship CLI

USAGE:
    airship setup [options]

OPTIONS:
    --uninstall    Uninstall the Airship CLI package
    --reinstall    Reinstall the Airship CLI package
```

---

`airship install`

```
Install a Flat package

USAGE:
    airship install <installationTarget> [--link]

ARGUMENTS:
    <installationTarget>    The package installation target

OPTIONS:
    --link    Link the Flat package to the location in $FLAT_HOME/packages
```

---

`airship get`

```
Install the specified dependency

USAGE:
    airship get <dependency>

ARGUMENTS:
    <dependency>    The dependencies to get
```

---

`airship uninstall`

```
Uninstall a Flat package

USAGE:
    airship uninstall <installationTarget> [--link]

ARGUMENTS:
    <installationTarget>    The installation target to uninstall

OPTIONS:
    --link    Uninstall a symbolic link of the Flat package from the location in
              $FLAT_HOME/packages
```

---

`airship reinstall`

```
Reinstall a Flat package

USAGE:
    airship reinstall <installationTarget> [--link]

ARGUMENTS:
    <installationTarget>    The installation target to reinstall

OPTIONS:
    --link    Link the Flat package to the location in $FLAT_HOME/packages
```

---

`airship update`

```
Update a Flat package's dependencies

USAGE:
    airship update <installationTarget>

ARGUMENTS:
    <installationTarget>    The installation target to update the dependencies for
```

---

`airship run`

```
Run a script defined in a flat.json

USAGE:
    airship run <script>

ARGUMENTS:
    <script>    The name of the script to run
```

---

`airship init`

```
Initialize a new Flat package, guided by a CLI prompt

USAGE:
    airship init <packageName>

ARGUMENTS:
    <packageName>    The name of the package to initialize
```

---

`airship create`

```
Create Flat package scaffoldings

USAGE:
    airship create <arguments>

ARGUMENTS:
    <pipeline>       The type of pipeline to create
    <pipelineArg>    An argument to pass to the pipeline creation
```

---

`airship bin`

```
Generate a Flat package binary file

USAGE:
    airship bin <binary>

ARGUMENTS:
    <binary>    The name of the binary to create
```

---

`airship add`

```
Add a Flat package as a dependency

USAGE:
    airship add <dependency>

ARGUMENTS:
    <dependency>    The dependency to add
```

---

`airship remove`

```
Remove a Flat package from the dependecies

USAGE:
    airship remove <dependency>

ARGUMENTS:
    <dependency>    The dependency to remove
```

---

`airship help`

```
Print help information

USAGE:
    airship help <name> [--all]

ARGUMENTS:
    <name>    The name of the argument, command, or option to get help on

OPTIONS:
    --all    Print all the help information recursively for an argument, command, or option
```

---

`airship --target`

```
Specify the target language to compile the package to

USAGE:
    airship --target <target>

ARGUMENTS:
    <target>    The language to compile the package to
```

---

`airship --target-runtime`

```
Specify the target runtime the binary will build for

USAGE:
    airship --target-runtime <runtime>

ARGUMENTS:
    <runtime>    The language runtime to compile the package for
```

---

`airship --file`

```
Specify an explicit flat.json file to reference when running the Airship commands

USAGE:
    airship --file <file>

ARGUMENTS:
    <file>    The flat.json file location to use
```

---

`airship --source`

```
Specify an explicit package source to reference when running the Airship commands

USAGE:
    airship --source <sourceValue>

ARGUMENTS:
    <sourceValue>    The source to target. e.g. main/test
```

---

`airship --flatc-runtime`

```
Specify the Flat compiler runtime

USAGE:
    airship --flatc-runtime <runtime>

ARGUMENTS:
    <runtime>    The flatc runtime to compile the package with
```

For `--flatc-runtime flat`, Airship resolves the installed `Flat-Compiler` package
through the usual workspace/link rules (`--use-links`) and runs its ES6 `flatc`
source artifact with Node. It does not substitute a global `flatc` binary, fetch
an absent compiler, or bootstrap it implicitly. Bootstrap the selected package
explicitly with the legacy runtime first:

```bash
airship --use-links -f packages/Flat-Compiler --flatc-runtime java install flatc -t es6
```

For an isolated generated-tool rebuild, select a compiler script explicitly:

```bash
node /isolated/previous/airship.js --use-links -f packages/Flat-Compiler \
  --flatc-runtime flat --flatc-script /isolated/previous/flatc.js install flatc -t es6
```

`--flatc-script` is a compiler **input**, independent of the output being built.
It bypasses workspace compiler lookup, requires an existing file and
explicit `--flatc-runtime flat`, and rejects `--flatc-jar`. Missing scripts fail
closed; no global compiler, Java fallback, download or implicit bootstrap is used.
Relative script paths are interpreted from the invoking working directory, not
from `-f`. Explicit runtime/script/jar selections propagate into child dependency
Airship instances, so nested installs cannot silently restore the Java default.
Callers that require reproducible inputs must pin and verify file hashes;
Airship selection alone does not make a mutable script immutable. Direct normalized
input/output path aliasing is rejected before dependency acquisition; trusted
callers must additionally prohibit symlink aliases (the isolated harness does).

### Isolated stage interface (source-ready; execution unverified)

The existing harness CLI remains:

```bash
python3 -B packages/Airship/test/stage_bootstrap.py \
  --repo /absolute/checkout --output /absolute/new-output \
  --seeds /absolute/seeds.json --review /absolute/review.json \
  --review-sha256 INDEPENDENT_REVIEW_DIGEST
```

Before any builds, approved seed delivery can be checked without launching Node,
Git, Java, Airship or a compiler by adding `--preflight-only` to that command and
using a distinct new output directory. It writes only `seed-preflight.json` with
`status: validated-not-executed` and `acceptance: false`; it never manufactures a
seed manifest, review receipt or acceptance receipt. Validation hashes each named
input and checks executable permissions for Node/Git/Java. It does not prove that
a candidate Airship supports the required CLI, jars are runnable, or native tools
have their platform runtime libraries. Stage execution proves the actual CLI
selection via a real `--debug` command for every install (including stage0), then
requires recorded compiler invocation and fresh outputs.

Provisioning must supply all six absolute-file entries (`node`, `git`, `java`,
`airship`, `java_compiler`, `java_backend`) with lowercase SHA-256 values. Node,
Git and Java must be platform-compatible executable files and have their normal
OS/JDK dependencies available. The Airship JS and Java jars must be standalone
or use runtime resources provisioned by their independently reviewed producer;
this six-file interface does not implicitly copy arbitrary vendor directories.
A local ignored `dist/` or `target/` file is a candidate, not provenance. No seed
or review file is generated by this harness. The review receipt must name its
producer evidence and seed-source revision; the independently supplied receipt
hash is the trust input. A protected CI job must deliver those reviewed files;
workflow inputs pointing at nonexistent hosted-runner paths are not delivery.

Once source mutation is paused and verification is authorized, run the harness
unit tests first, then preflight and full stages with independently delivered
inputs (use different, nonexistent output paths):

```bash
python3 -B -m unittest discover -s packages/Airship/test -p test_stage_bootstrap.py
python3 -B packages/Airship/test/stage_bootstrap.py --repo "$PWD" \
  --output "$PREFLIGHT_OUTPUT" --seeds "$SEEDS" --review "$REVIEW" \
  --review-sha256 "$REVIEW_SHA256" --preflight-only
python3 -B packages/Airship/test/stage_bootstrap.py --repo "$PWD" \
  --output "$STAGE_OUTPUT" --seeds "$SEEDS" --review "$REVIEW" \
  --review-sha256 "$REVIEW_SHA256"
```

### Compilation failure and selected-output lifecycle

Ordinary install now invalidates the selected source output file immediately
before compiler execution (after debug/preflight selection). A nonzero exit always
fails, even if a file was written. Zero exit also fails when the selected output
was not freshly created. Partial selected output is removed on compiler failure;
assets, binary installation and success logging happen only after validation.
Sibling outputs are not deleted. Byte-identical rebuilds are valid: freshness is
established by absence before invocation, not changed hashes or wall-clock mtimes.
The previous selected output is deliberately not retained as a runnable artifact
after a failed install. Install commands sharing an output must remain serialized.
Targets without a declared output filename retain status-only validation; normal
ES6 package sources have explicit/default filenames. No generated tool runtime is
patched by these source changes; installed old Airship must be rebuilt explicitly.

The regression covers a Java compiler invocation whose final diagnostic reported
failure but whose outer command returned zero and left the prior artifact untouched.
Tests include nonzero-with-existing-output, zero-without-output, stale invalidation,
sibling preservation, deterministic rewrite, and real minimal Node subprocess
status/partial-file lifecycle under both compiler-runtime selections. Those minimal
subprocess fixtures are not production compiler substitutes or language acceptance.

### Observed subprocess watchdog cleanup

`stage_bootstrap.observe_subprocess_cleanup(new_output_path, python=None, timeout=10)`
is the supplemental producer seam for watchdog-only lifecycle evidence. It runs
minimal Python children, not compiler substitutes: each creates a temporary output,
sends a FIFO-ready byte, and blocks on an unreleased FIFO. Controlled cancellation
and timeout are applied only after observed readiness. Both exercise the same
`run_command` process-group termination and bounded `wait` cleanup path used by
stage commands. FIFO readiness, not a sleep duration, establishes the schedule.

`subprocess-cleanup.json` records `schema: 1`, `scope: watchdog-fixture-only`,
`production_compiler_executed: false`, `status: observed` only on actual successful
observations, `children_reaped`, `temporary_outputs_removed`, selected Python
path/hash and per-scenario argv/PID/termination reason/exit status, wait completion,
direct-child reaping, process-group absence, temporary-output existence/removal,
and relative retained log paths/hashes (`cancel.log`, `timeout.log`). Failure writes
`status: failed` without promoting aggregate success. Temporary FIFO/output
fixtures are removed; receipts and logs remain for the caller to retain. Each
fixture is a single direct child in a new process group; this is not proof about
arbitrary grandchildren or production compiler memory/session cleanup. Consumers
must keep those separately scoped production gates blocked until measured.

### Experimental Java bootstrap producer (not acceptance)

Repository producer references are `.github/actions/setup-flat-monorepo/action.yml`,
`packages/Flat/pom.xml` and `packages/Flat-ES6/pom.xml`. The action builds Java
Flat, registers `flat:flat:1.0-SNAPSHOT`, then builds the ES6 backend. It does not
produce an independently reviewed seed receipt. Its synthetic package Git remotes
are dependency-lookup metadata, **not** producer source provenance. The release
Airship downloader selects a mutable latest release by default and likewise does
not supply independent artifact verification.

When an explicitly experimental bootstrap build is authorized and the build slot
is free, the following producer shape uses real untrimmed source copies (not a Git
worktree). `EXPERIMENT` must be a fresh external directory containing copied
`packages/Flat` and `packages/Flat-ES6` inputs without old `target/`, plus a copied
existing Maven dependency cache at `m2/`. Capture/hash those inputs before building.
`MVN` and `JAVA_HOME` must select recorded actual tools; do not substitute a system
Java launcher for the selected JDK. These commands are a proposal, not evidence of
execution or cached dependency availability:

```bash
"$MVN" -o -B -Dmaven.repo.local="$EXPERIMENT/m2" \
  -f "$EXPERIMENT/packages/Flat/pom.xml" package
"$MVN" -o -B -Dmaven.repo.local="$EXPERIMENT/m2" \
  -f "$EXPERIMENT/packages/Flat/pom.xml" install:install-file \
  -Dfile="$EXPERIMENT/packages/Flat/target/flatc.jar" \
  -DgroupId=flat -DartifactId=flat -Dversion=1.0-SNAPSHOT \
  -Dpackaging=jar -DgeneratePom=true
"$MVN" -o -B -Dmaven.repo.local="$EXPERIMENT/m2" \
  -f "$EXPERIMENT/packages/Flat-ES6/pom.xml" package
```

Do not replace `package` with Flat's `install` lifecycle here: its POM executes
another `mvn install:install-file` without explicitly inheriting offline/cache
selection. The explicit registration command above wires the backend dependency
to the just-produced compiler. Offline misses are real prerequisites, not reasons
to silently retry online. Produce `target/flatc.jar` and `target/flat-es6.jar` in
these isolated inputs; do not promote existing checkout outputs as fresh builds.
Java Flat loads the backend via `URLClassLoader` and requires its expected target
package layout, not a standalone backend CLI main method.

The experimental producer receipt should contain:

- `purpose: experimental-bootstrap`, `acceptance: false`, `review_status: unreviewed`;
- baseline revision, exact dirty source input hashes, copied input manifest hash;
- selected Maven/Java/Node/Git identities, resolved paths and SHA-256 values;
- copied Maven-cache input inventory/hash, resolved project/plugin dependencies;
- exact argv, cwd, deliberate environment selection, exit status and log hash for
  each command, plus fresh output jar hashes and sizes;
- the separate existing Airship JS seed's actual resolved file/hash and known
  origin (or explicit unknown producer provenance), never just its wrapper path;
- later real compiler/Airship invocation assertions and generated output hashes,
  clearly distinguished from successful jar packaging alone.

Such a receipt proves what the development experiment used, not independent
approval. An independent reviewer must inspect the producer evidence, approve the
six seed entries and deliver the schema-1 review plus its expected digest through
a separate trusted channel before `stage_bootstrap.py` can admit them. The full
stage CLI has no unreviewed/experimental bypass. A development producer receipt
must never be relabeled as that review or written as a passing acceptance receipt.

The output directory must not exist. Seed/review schemas and aggregate limits
are documented in the harness module. Stage0 uses explicitly reviewed Java seed
tools; that stage alone is **not** self-hosting evidence. Stages1/2 copy the prior
stage's generated compiler and Airship into read-only independent input files.
They select the compiler using `--flatc-script`, never a symlink over an output.
Each stage builds the compiler and Airship through ordinary `install`, then uses
the newly generated Airship for ordinary `install test` and `run test` across all
seven required suites. Stage1/2 disallow Java launches and network admission.
Stage1/2 compiler and Airship artifact bytes must match exactly. No entrypoint
calls are appended to generated code.

Stable outputs consumed by the acceptance driver:

- `acceptance.json`: schema 2, `status` (`running`, `passed`, `failed`), baseline
  revision, dirty-input provenance, immutable source-manifest hashes, limits and
  `stages` entries with `stage`, relative `manifest`, `manifest_sha256`, `runtime`
  and relative-path-to-SHA-256 `products` map. A failed run retains evidence.
- `stageN/manifest.json`: schema 2, stage/runtime/compiler selection, source hashes,
  subprocess policy, command argv/status, runner/compiler hashes, resolution/log
  hashes, fresh executed test artifact hashes, final product hashes and admission
  journal hash. Only `status: passed` is a completed stage.
- `stageN/workspace/packages/Flat-Compiler/dist/es6/index.js` and
  `stageN/workspace/packages/Airship/dist/airship.js`: fresh stage products.
- `stageN/admissions.jsonl` and `stageN/logs/`: bounded, disk-backed subprocess
  evidence, retained for both success and failure.

The harness terminates child process groups on timeout/cancellation. It does not
automatically delete evidence or the output root; callers own retention cleanup.
Hooks enforce trusted-build admission, **not an OS sandbox**. Seven-suite,
self-rebuild and byte equality receipts do not establish all incremental/scaling
or broader product acceptance gates. No pass is claimed until actual execution.

Airship forwards sources, dependency sources, exclusions, target, output directory
and filename, and `-main` or `-library` according to the package source. Executable
packages require compiler support for `-main`; forwarding alone does not provide
an executable bootstrap. Target-runtime selection and default imports are rejected
until the Flat compiler supports those contracts.

The four-file Char execution integration is
`python3 packages/Airship/test/char_integration.py`. Build prerequisites are:

```bash
airship --use-links --sync -f packages/Airship install -t es6
airship --use-links --sync -f packages/Airship install char-inspection-test -t es6
airship --use-links --sync -f packages/Flat-Compiler install flatc -t es6
```

The harness uses temporary manifests and unchanged production-file symlinks
outside the checkout. Compilation and inspection execution route through Airship.
The `char-inspection-test` source has test-only lexer, parser, identity-model and
ES6 name-mangler dependencies: these reproduce the existing Compiler acceptance
probe using stable identities rather than guessing private emitted names. They
are not Airship production dependencies. The probe writes a distinct temporary
`inspect.js`, preserves the original library bytes, and asserts `true`, `false`,
`aaa` for unchanged Char whitespace/repeat behavior. The four-file token
materialization is a test-only, single-source identity derivation, not a compiler
pipeline barrier. Temporary files are cleaned on success and failure.

---

`airship publish`

```
Publish a new release

USAGE:
    airship publish <--version> [options]

ARGUMENTS:
    <--version>    Specify the version to publish

OPTIONS:
    --bump    Bump the version number
    --push    Push the release commit and tag
```

---

`airship publish --bump`

```
Bump the version number

USAGE:
    airship publish --bump <major|minor|patch>

COMMANDS:
    major    Bump the version by a major version. e.g. from v1.2.3 to v2.0.0
    minor    Bump the version by a minor version. e.g. from v1.2.3 to v1.3.0
    patch    Bump the version by a patch version. e.g. from v1.2.3 to v1.2.4
```

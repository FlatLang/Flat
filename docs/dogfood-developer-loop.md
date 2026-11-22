# ES6 developer loop: build, run, break, repair

Start at the root of a clean checkout in a POSIX shell. This loop uses the public Airship CLI to compile one scratch package with the Flat-written compiler, then runs its JavaScript with Node.

**Bring the tools first.** You need `node` on `PATH`, a compatible generated Airship script and an independently built ES6 `flatc` script, with their runtime dependencies available. Set `AIRSHIP_JS` and `FLATC_JS` to their absolute paths outside the scratch package. A checkout alone does not supply these executables. If you lack them, stop at the [bootstrap prerequisites](../README.md#full-dogfooding-acceptance); `--flatc-runtime flat` does not bootstrap a compiler or fall back to Java. The example uses the real implicit Core and needs its dependency closure available to Airship.

The generated Airship must recognize `--flatc-script`. If it prints `Invalid CLI argument: '--flatc-script'`, it has stopped before invoking the compiler. Select a compatible Airship build; rebuilding the app or removing the flag does not verify the selected compiler.

## Install and run

Create a new directory **under `packages/`** so `--use-links` can resolve workspace packages. Use the same shell for all steps.

```sh
app=$(mktemp -d "$PWD/packages/dogfood.XXXXXX")
mkdir "$app/src"
cat > "$app/flat.json" <<'JSON'
{
  "name": "Dogfood", "version": "0.0.0", "defaultTarget": "es6",
  "sources": {"main": {"source": "src", "mainClass": "app/Main", "outFile": "index.js"}},
  "scripts": {"main": "node dist/es6/index.js"}
}
JSON
cat > "$app/src/Main.flat" <<'FLAT'
package app
class Main {
    public static main() { external js { console.log('one'); } }
}
FLAT
ship() {
    node "${AIRSHIP_JS:?set AIRSHIP_JS}" --use-links -f "$app" \
        --flatc-runtime flat --flatc-script "${FLATC_JS:?set FLATC_JS}" \
        -t es6 -r node "$@"
}
ship install main && ship run main
```

`install main` builds the named source into `$app/dist/es6/index.js`; `run main` invokes the manifest script. Look for `one` from the program. If install fails, inspect the first dependency or compiler diagnostic before trying to run anything.

## Edit, break, fix

1. Edit `$app/src/Main.flat`: change `'one'` to `'two'`. Repeat `ship install main && ship run main`; expect `two`.
2. Save that working source (`cp "$app/src/Main.flat" "$app/Main.good"`). Replace the contents of `src/Main.flat` in the scratch package with `not valid Flat syntax`. Run `ship install main`; expect a diagnostic and nonzero exit. A previous `index.js` can remain on disk. Running it would test the **old** program, not the broken edit.
3. Restore with `cp "$app/Main.good" "$app/src/Main.flat"`, then repeat `ship install main && ship run main`; expect `two` again. Discard only the scratch directory when done.

These are expected observations, not a full-build acceptance result. A failure while compiling Core can precede compilation of your edit; do not replace Core with a trimmed fixture to call the loop successful. The [Airship toolchain test](../packages/Airship/src/flat/airship/AirshipToolchain_Test.flat) owns the public install/run and failed-install cases; the [CLI tests](../packages/Flat-Compiler/src/flat/compiler/CompilerCli_Test.flat) cover editing source and preserving prior output on failure.

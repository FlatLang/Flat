#!/usr/bin/env python3
"""Char inspection and complete production-Core execution through fresh Airship.

Run only after building Airship's main CLI and Flat-Compiler's flatc source.
The original four-file Char check remains independent of the full-Core workload.
A test-only Flat adapter derives invocation names using the compiler's syntax
identity and name-mangler APIs, then writes a distinct inspection runner.
"""
import hashlib
import json
import os
from pathlib import Path
import subprocess
import tempfile
import time
import unittest


REPO = Path(__file__).resolve().parents[3]
AIRSHIP = REPO / "packages/Airship/dist/airship.js"
COMPILER = REPO / "packages/Flat-Compiler/dist/es6/index.js"
PROBE = REPO / "packages/Airship/dist/es6/char-inspection-test.js"
CORE = REPO / "packages/Flat-Core/src/flat/primitive"


def run_command(command, log, timeout=1200):
    """Capture directly to disk; no pipe-backed unbounded process output."""
    with log.open("wb") as output:
        process = subprocess.Popen(command, stdout=output, stderr=subprocess.STDOUT,
                                   env={**os.environ, "AIRSHIP_LOCK_FILE_ENABLED": "false",
                                        "NODE_NO_WARNINGS": "1"})
        start = time.monotonic()
        try:
            while process.poll() is None:
                if log.stat().st_size > 100 * 1024 * 1024:
                    raise AssertionError(f"Output exceeded 100 MB: {log}")
                if time.monotonic() - start > timeout:
                    raise AssertionError(f"Command timed out: {command}")
                time.sleep(0.1)
        finally:
            if process.poll() is None:
                process.terminate()
                try:
                    process.wait(timeout=10)
                except subprocess.TimeoutExpired:
                    process.kill()
                    process.wait()
    if log.stat().st_size > 100 * 1024 * 1024:
        raise AssertionError(f"Output exceeded 100 MB: {log}")
    text = log.read_text(errors="replace")
    if process.returncode or "Compilation failed" in text:
        raise AssertionError(f"Actual exit {process.returncode}: {command}\n{text[-16000:]}")
    return text


def manifest(directory, source, dependencies=None, script=None):
    directory.mkdir(parents=True)
    data = {"name": "Char integration fixture", "version": "0.0.0",
            "defaultTarget": "es6", "sources": {"main": {
                "source": source, "outFile": "characters.js",
                "dependencies": dependencies or {}}}}
    if script:
        data["scripts"] = {"acceptance": script}
    (directory / "flat.json").write_text(json.dumps(data, indent=2) + "\n")


class CharAirshipIntegration(unittest.TestCase):
    def test_unchanged_char_library_through_airship_cli(self):
        for artifact, package in ((AIRSHIP, "Airship"), (COMPILER, "Flat-Compiler"), (PROBE, "Airship")):
            self.assertTrue(artifact.is_file(), f"Build prerequisite missing: {artifact}")
            newest_source = max(path.stat().st_mtime_ns for path in
                                (REPO / "packages" / package / "src").rglob("*.flat"))
            if artifact == PROBE:
                newest_source = max(newest_source,
                                    (REPO / "packages/Airship/test/flat/airship/CharInspection.flat").stat().st_mtime_ns)
            self.assertGreaterEqual(artifact.stat().st_mtime_ns, newest_source,
                                    f"Fresh build prerequisite stale: {artifact}")
        sources = [CORE / "number/Char.flat", CORE / "number/Number.flat",
                   CORE / "number/Integer.flat", CORE / "Primitive.flat"]
        before = [hashlib.sha256(path.read_bytes()).hexdigest() for path in sources]
        with tempfile.TemporaryDirectory(prefix="airship-char-integration-") as temporary:
            root = Path(temporary).resolve()
            packages = root / "packages"
            packages.mkdir()
            for name in ("Flat-Compiler", "Flat-ES6"):
                (packages / name).symlink_to(REPO / "packages" / name, target_is_directory=True)
            dependencies = {}
            for path in sources[1:]:
                selected = root / "declarations" / path.stem / "Flat-Core"
                manifest(selected, path.name)
                (selected / path.name).symlink_to(path)
                dependencies[str(selected)] = "local"
            fixture = packages / "Flat-Core"
            manifest(fixture, "Char.flat", dependencies,
                     ["node --check dist/es6/characters.js", "node inspect.js"])
            (fixture / "Char.flat").symlink_to(sources[0])
            command = ["node", str(AIRSHIP), "--use-links", "--sync", "-f", str(fixture),
                       "--flatc-runtime", "flat", "install", "-t", "es6"]
            run_command(command, root / "compile.log")
            output = fixture / "dist/es6/characters.js"
            self.assertTrue(output.is_file(), "Airship must publish the requested artifact")
            self.assertGreater(output.stat().st_size, 0)
            original_artifact = output.read_bytes()
            # CLI retains the selected File.normalizedLocation as identity; canonical
            # paths deduplicate selection only. Give the adapter the same symlink path.
            run_command(["node", str(PROBE), str(fixture / "Char.flat"), str(output),
                         str(fixture / "inspect.js")], root / "probe.log")
            actual = run_command(["node", str(AIRSHIP), "--use-links", "-f", str(fixture),
                                  "run", "acceptance"], root / "execute.log")
            self.assertEqual(actual, "true\nfalse\naaa\n")
            self.assertEqual(original_artifact, output.read_bytes(), "Probe must not alter the library")
        self.assertEqual(before, [hashlib.sha256(path.read_bytes()).hexdigest() for path in sources])


class ProductionCoreAirshipIntegration(unittest.TestCase):
    """A separate full-closure gate; the four-file Char gate is not Core coverage."""

    def test_complete_production_core_workload(self):
        core = REPO / "packages/Flat-Core"
        workload = core / "workloads/ProductionCoreWorkload.flat"
        for artifact, package in ((AIRSHIP, "Airship"), (COMPILER, "Flat-Compiler")):
            self.assertTrue(artifact.is_file(), f"Build prerequisite missing: {artifact}")
            newest_source = max(path.stat().st_mtime_ns for path in
                                (REPO / "packages" / package / "src").rglob("*.flat"))
            self.assertGreaterEqual(artifact.stat().st_mtime_ns, newest_source,
                                    f"Fresh build prerequisite stale: {artifact}")
        production = sorted((core / "src").rglob("*.flat"))
        self.assertTrue(production, "Production Core inventory must not be empty")
        inputs = [core / "flat.json", workload, *production]
        before = {str(path): hashlib.sha256(path.read_bytes()).hexdigest() for path in inputs}

        def assert_inputs_unchanged():
            self.assertEqual(production, sorted((core / "src").rglob("*.flat")),
                             "Compilation must not remove or add production Core sources")
            self.assertEqual(before, {str(path): hashlib.sha256(path.read_bytes()).hexdigest()
                                      for path in inputs}, "Compilation must not rewrite Core inputs")

        self.addCleanup(assert_inputs_unchanged)
        with tempfile.TemporaryDirectory(prefix="airship-production-core-") as temporary:
            root = Path(temporary).resolve()
            packages = root / "packages"
            packages.mkdir()
            # Preserve all workspace dependency options and production source bytes.
            # No four-file selection, declaration replacement, or source rewriting.
            for package in sorted((REPO / "packages").iterdir()):
                if package.is_dir() and package.name != "Flat-Core":
                    (packages / package.name).symlink_to(package, target_is_directory=True)
            fixture = packages / "Flat-Core"
            source = fixture / "src"
            source.mkdir(parents=True)
            for child in sorted((core / "src").iterdir()):
                (source / child.name).symlink_to(child, target_is_directory=child.is_dir())
            owned_workloads = source / "core/workloads"
            owned_workloads.mkdir(parents=True)
            (owned_workloads / workload.name).symlink_to(workload)
            data = json.loads((core / "flat.json").read_text())
            data["sources"]["main"]["mainClass"] = "core/workloads/ProductionCoreWorkload"
            data["sources"]["main"]["outFile"] = "production-core-workload.js"
            (fixture / "flat.json").write_text(json.dumps(data, indent=2) + "\n")
            run_command(["node", str(AIRSHIP), "--use-links", "--sync", "-f", str(fixture),
                         "--flatc-runtime", "flat", "install", "-t", "es6"], root / "compile.log")
            artifact = fixture / "dist/es6/production-core-workload.js"
            self.assertTrue(artifact.is_file(), "Flat compiler must publish the full-Core workload")
            self.assertGreater(artifact.stat().st_size, 0)
            published = hashlib.sha256(artifact.read_bytes()).hexdigest()
            actual = run_command(["node", str(artifact)], root / "execution.log")
            self.assertEqual(actual, "production-core-workload:ok\n")
            self.assertEqual(published, hashlib.sha256(artifact.read_bytes()).hexdigest(),
                             "Execution must not rewrite the compiler artifact")


if __name__ == "__main__":
    unittest.main()

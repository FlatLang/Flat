#!/usr/bin/env bash
set -euo pipefail

TARGET="${1:-es6}"
TARGET_LOWER="$(printf '%s' "$TARGET" | tr '[:upper:]' '[:lower:]')"
case "$TARGET_LOWER" in
  es6)
    ENGINE_PACKAGE="packages/Flat-ES6"
    ENGINE_JAR="$ENGINE_PACKAGE/target/flat-es6.jar"
    ;;
  js)
    ENGINE_PACKAGE="packages/Flat-JS"
    ENGINE_JAR="$ENGINE_PACKAGE/target/flat-js.jar"
    ;;
  *)
    echo "Unsupported Airship bootstrap target: $TARGET" >&2
    exit 1
    ;;
esac

mkdir -p packages/Airship/dist

mvn -f packages/Flat/pom.xml install
mvn -f "$ENGINE_PACKAGE/pom.xml" package

mapfile -t FLATC_ARGS < <(node <<'NODE'
const fs = require('fs');
const path = require('path');

const packagesDir = 'packages';
const rootPackage = 'Airship';

function readPackage(packageName) {
  const file = path.join(packagesDir, packageName, 'flat.json');
  return JSON.parse(fs.readFileSync(file, 'utf8'));
}

function packageNameFromDependency(dependency) {
  return dependency.replace(/\.git$/i, '').split('/').pop();
}

function mainSource(pkg) {
  const sources = pkg.sources || {};
  return sources.main || sources[pkg.defaultSource] || Object.values(sources)[0] || {};
}

const seen = new Set();
function visit(packageName) {
  if (seen.has(packageName)) return;
  seen.add(packageName);

  const pkg = readPackage(packageName);
  const source = mainSource(pkg);
  for (const dependency of Object.keys(source.dependencies || {})) {
    if (dependency.includes('github.com/FlatLang/')) {
      visit(packageNameFromDependency(dependency));
    }
  }
}

visit(rootPackage);

const root = readPackage(rootPackage);
const rootSource = mainSource(root);
const appProps = {
  name: root.name,
  version: root.version,
  description: root.description,
  author: root.author,
  license: root.license,
  defaultTarget: root.defaultTarget,
};

const args = [];

for (const packageName of [...seen].sort()) {
  const pkg = readPackage(packageName);
  const source = mainSource(pkg);
  const sourcePath = path.join(packagesDir, packageName, source.source || 'src');

  if (packageName !== rootPackage) {
    args.push('-l', sourcePath);
  }

  if (pkg.sources && pkg.sources.test && pkg.sources.test.source) {
    args.push('-x', path.join(packagesDir, packageName, pkg.sources.test.source));
  }

  for (const excluded of source.excluded || []) {
    const location = typeof excluded === 'string' ? excluded : excluded.location;
    if (location) args.push('-x', path.join(packagesDir, packageName, location));
  }
}

for (const dependency of Object.keys(rootSource.dependencies || {})) {
  if (!dependency.includes('github.com/FlatLang/')) continue;

  const packageName = packageNameFromDependency(dependency);
  const source = mainSource(readPackage(packageName));

  for (const defaultExport of source.defaultExports || []) {
    args.push('-default-import', defaultExport);
  }
}

args.push('-app-props', JSON.stringify(appProps));

for (const arg of args) {
  console.log(arg);
}
NODE
)

java -jar packages/Flat/target/flatc.jar \
  packages/Airship/src \
  -install-dir "${FLAT_HOME:-$HOME/.flat}/packages" \
  -o packages/Airship/dist/airship.js \
  -d packages/Airship/dist \
  -target "$TARGET_LOWER" \
  -engine-jar "$ENGINE_JAR" \
  -parallel \
  "${FLATC_ARGS[@]}" \
  -main flat/airship/Airship

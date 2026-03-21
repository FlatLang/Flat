# FlatLang Monorepo

This repository is the FlatLang package monorepo.

All Flat packages in the local FlatLang workspace are organized under `packages/`.

## Layout

- `packages/Flat` - legacy Flat compiler package (this repo's original contents)
- `packages/*` - all other Flat packages imported as git subtrees

## Subtree Updates

Each package is tracked from its `master` branch and can be updated with:

```bash
git subtree pull --prefix packages/<PackageName> <repository> master
```

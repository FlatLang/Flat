Flat Programming Language
===========================

### Flat Organization Build Status:
[![Status Badges](https://flatlang.org/api/pills.svg?nocache=true)](https://flatlang.org/api/pills.svg)

A high-level multi-paradigm programming language used as an intermediary between transpilation.

### This repository is _DEPRECATED_.

This is a very old and ugly repository that should long be dead.

...however, this repository is still under active development because it is the only current working compiler. The compiler is currently being rewritten into several different modules, written in the Flat language itself:

* [Flat-Compiler](https://github.com/FlatLang/Flat-Compiler) - Imports all of the front-end/middle-end/back-end compiler components and utilities as dependencies and provides the CLI for compiling Flat code
* [Flat-AST](https://github.com/FlatLang/Flat-AST) - Defines the Flat AST structure
* [Flat-Lexer](https://github.com/FlatLang/Flat-Lexer) - The new lexer engine
* [Parser-Core](https://github.com/FlatLang/Parser-Core) - The new generic code parser engine (for all languages that have an implemented grammar)
* [Flat-Parser](https://github.com/FlatLang/Flat-Parser) - The Flat language grammar definition for Parser-Core
* [Flat-Writer](https://github.com/FlatLang/Flat-Writer) - The new generic code generator/writer engine
* [Flat-Compiler-Models](https://github.com/FlatLang/Flat-Compiler-Models) - Common models used throughout the compiler modules

© 2014-2023 Flat

```
Flat Compiler CLI

Usage:
    flatc <source> <help> [options]

Arguments:
    <source>    The file or directory to include in the compilation

Commands:
    help, ?    Print help information

Options:
    --exclude, -x         Exclude specified file or directory from compilation
    --threads, -t         Specify the max amount of threads to use during compilation if parallel
    --language, --lang    Specify the source language to compile
    --target, -t          Specify the target language to compile to
    --sync                Run the compilation synchronously
    --dry                 Skip the code generation phase of compilation
```

--------------------------------------------------------------------------------

`flatc <source>`

```
The file or directory to include in the compilation

Usage:
    flatc <source>
```

--------------------------------------------------------------------------------

`flatc --exclude`

```
Exclude specified file or directory from compilation

Usage:
    flatc --exclude <source>

Arguments:
    <source>    Exclude files or directories from compilation
```

--------------------------------------------------------------------------------

`flatc --exclude <source>`

```
Exclude files or directories from compilation

Usage:
    flatc --exclude <source>
```

--------------------------------------------------------------------------------

`flatc --threads`

```
Specify the max amount of threads to use during compilation if parallel

Usage:
    flatc --threads <count>

Arguments:
    <count>    The max amount of threads to use
```

--------------------------------------------------------------------------------

`flatc --threads <count>`

```
The max amount of threads to use

Usage:
    flatc --threads <count>
```

--------------------------------------------------------------------------------

`flatc --language`

```
Specify the source language to compile

Usage:
    flatc --language <name>

Arguments:
    <name>    The name of the source language
```

--------------------------------------------------------------------------------

`flatc --language <name>`

```
The name of the source language

Usage:
    flatc --language <name>
```

--------------------------------------------------------------------------------

`flatc --target`

```
Specify the target language to compile to

Usage:
    flatc --target <languageName>

Arguments:
    <languageName>    The target language to compile to
```

--------------------------------------------------------------------------------

`flatc --target <languageName>`

```
The target language to compile to

Usage:
    flatc --target <languageName>
```

--------------------------------------------------------------------------------

`flatc --sync`

```
Run the compilation synchronously

Usage:
    flatc --sync
```

--------------------------------------------------------------------------------

`flatc --dry`

```
Skip the code generation phase of compilation

Usage:
    flatc --dry
```

--------------------------------------------------------------------------------

`flatc help`

```
Print help information

Usage:
    flatc help <name> [--all]

Arguments:
    <name>    The name of the argument, command, or option to get help on

Options:
    --all    Print all the help information recursively for an argument, command, or option
```

--------------------------------------------------------------------------------

`flatc help <name>`

```
The name of the argument, command, or option to get help on

Usage:
    flatc help <name>
```

--------------------------------------------------------------------------------

`flatc help --all`

```
Print all the help information recursively for an argument, command, or option

Usage:
    flatc help --all
```
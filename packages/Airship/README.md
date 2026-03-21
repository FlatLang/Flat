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
    --use-links                        Use packages installed as symbolic links where possible
                                       in favor of any version defined in a flat.json file
    --purge                            Uninstall the dependency from the disk
```

-----------------------------------------------------------------------------------------------

`airship setup`

```
Setup the Airship CLI

USAGE:
    airship setup [options]

OPTIONS:
    --uninstall    Uninstall the Airship CLI package
    --reinstall    Reinstall the Airship CLI package
```

-----------------------------------------------------------------------------------------------

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

-----------------------------------------------------------------------------------------------

`airship get`

```
Install the specified dependency

USAGE:
    airship get <dependency>

ARGUMENTS:
    <dependency>    The dependencies to get
```

-----------------------------------------------------------------------------------------------

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

-----------------------------------------------------------------------------------------------

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

-----------------------------------------------------------------------------------------------

`airship update`

```
Update a Flat package's dependencies

USAGE:
    airship update <installationTarget>

ARGUMENTS:
    <installationTarget>    The installation target to update the dependencies for
```

-----------------------------------------------------------------------------------------------

`airship run`

```
Run a script defined in a flat.json

USAGE:
    airship run <script>

ARGUMENTS:
    <script>    The name of the script to run
```

-----------------------------------------------------------------------------------------------

`airship init`

```
Initialize a new Flat package, guided by a CLI prompt

USAGE:
    airship init <packageName>

ARGUMENTS:
    <packageName>    The name of the package to initialize
```

-----------------------------------------------------------------------------------------------

`airship create`

```
Create Flat package scaffoldings

USAGE:
    airship create <arguments>

ARGUMENTS:
    <pipeline>       The type of pipeline to create
    <pipelineArg>    An argument to pass to the pipeline creation
```

-----------------------------------------------------------------------------------------------

`airship bin`

```
Generate a Flat package binary file

USAGE:
    airship bin <binary>

ARGUMENTS:
    <binary>    The name of the binary to create
```

-----------------------------------------------------------------------------------------------

`airship add`

```
Add a Flat package as a dependency

USAGE:
    airship add <dependency>

ARGUMENTS:
    <dependency>    The dependency to add
```

-----------------------------------------------------------------------------------------------

`airship remove`

```
Remove a Flat package from the dependecies

USAGE:
    airship remove <dependency>

ARGUMENTS:
    <dependency>    The dependency to remove
```

-----------------------------------------------------------------------------------------------

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

-----------------------------------------------------------------------------------------------

`airship --target`

```
Specify the target language to compile the package to

USAGE:
    airship --target <target>

ARGUMENTS:
    <target>    The language to compile the package to
```

-----------------------------------------------------------------------------------------------

`airship --target-runtime`

```
Specify the target runtime the binary will build for

USAGE:
    airship --target-runtime <runtime>

ARGUMENTS:
    <runtime>    The language runtime to compile the package for
```

-----------------------------------------------------------------------------------------------

`airship --file`

```
Specify an explicit flat.json file to reference when running the Airship commands

USAGE:
    airship --file <file>

ARGUMENTS:
    <file>    The flat.json file location to use
```

-----------------------------------------------------------------------------------------------

`airship --source`

```
Specify an explicit package source to reference when running the Airship commands

USAGE:
    airship --source <sourceValue>

ARGUMENTS:
    <sourceValue>    The source to target. e.g. main/test
```

-----------------------------------------------------------------------------------------------

`airship --flatc-runtime`

```
Specify the Flat compiler runtime

USAGE:
    airship --flatc-runtime <runtime>

ARGUMENTS:
    <runtime>    The flatc runtime to compile the package with
```

-----------------------------------------------------------------------------------------------

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

-----------------------------------------------------------------------------------------------

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

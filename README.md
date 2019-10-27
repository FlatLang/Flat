Nova Programming Language
===========================

Nova is a light-weight general purpose programming language that cross-compiles to multiple different programming languages.

__Aim__

  * Generate fancy code analytics with the [Nova Reactor](https://github.com/NovaFoundation/Nova-Reactor)
  * Increase code reusability
  * Induce natrually parallelizable code
  * Ensure a smooth transition between running code on various operating systems
  * Deliver a uniform exception handling environment
  * Simplify common tasks and concepts

Getting Started
===============

__Pre-requisites__

  * The early version of the compiler uses Java 1.8 or later
  * A windows/mac/linux machine

__Installation for compiling to C__

  * If not already, install GCC from https://gcc.gnu.org/install
  * Add GCC to system path http://stackoverflow.com/questions/5733220/how-to-add-mingw-bin-directory-to-my-system-path
  * Restart your IDE and/or terminal to make path changes take place

__Instructions of Use for IDE__

  * Open this repository as a project
  * Run it
  * Hope for the best

__Instructions for makefile__
  * Open terminal
  * Navigate to repository directory
  * Run `make` command

__Instructions of Use for the Jar__

  * To run the compiler, first locate the Nova.jar file
  * In command line or terminal run the jar file with the required arguments
  * The required arguments include: The input files and the output destination

__An Example command to compile a source from the Jar__

`java -jar Nova.jar SourceFile1.nova SourceFile2.nova -o OutputExecutable.exe`

  * The `java -jar Nova.jar` component of the command is needed to run the compiler program.
  * The `SourceFile.nova SourceFile2.nova` components of the command are the input files to compile. There is no limit to how many source files can be compiled at a time, however at least one is required.
  * The `-o OutputExecutable.exe` component of the command specifies the name of the executable to produce. The extension of the executable will vary depending on the operating system you are compiling for.

__Optional arguments to the Compiler Include__

  * `-csource` Output the compiled C source files to the console during compilation. Verbose must be enabled.
  * `-verbose` or `-v` Output debugging messages during compilation.
  * `-dir` Used to specify the directory in which to include in the compilation.
  * `-dry` Perform a dry-run of the compilation. Do not compile into an executable.
  * `-gcc` Compile the program with the GCC (GNU Compiler Collection) compiler.
  * `-tcc` Compile the program with the TCC (Tiny C Compiler) compiler.
  * `-output-directory` Specify a directory to output the generated c files in.
  * `-cargs` Display the arguments that were passed to the C compiler.
  * `-nogc` Compile the program without garbage collection. (Not recommended)
  * `-small` Generate the smallest possible executable output.
  * `-single-thread` Compile the program with only a single thread.

© 2014-2019 Nova

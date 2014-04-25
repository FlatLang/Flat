Fathom Programming Language
===========================

The Fathom programming language is a c-based language that compiles into portable c code. The Java-like syntax makes Fathom a great choice for those who are not looking to spend countless hours learning new concepts. The main idea behind Fathom is to make cross-platform code run natively on a multitude of devices.



__Aim__

  * Manage abstraction at a high level without a large performance hit.
  * Engineer an easy-to-integrate architecture for using c code within a project.
  * Ensure a smooth transition between running code on various operating systems.
  * Deliver a uniform exception handling environment.
  * Simplify common tasks and concepts.
  * Restore the intuitive nature of Object Oriented Programming by reducing the use of conventions.
  * Include support for clear and concise documentation of code.
  * Increase the opportunity to reuse code in future instances.



__Pre-requisites__

  * The early version of the compiler uses Java 1.6 or later.
  * Any Windows 95 (x86) machine or later. (Or any machine that contains GCC/Clang)
  * Patience. (There will be bugs)



__Instructions of Use__

  * To run the compiler, first locate the Fathom.jar file.
  * In command line or terminal run the jar file with the required arguments.
  * The required arguments include: The input files and the output destination.



__An Example command to compile a source__

`java -jar Fathom.jar SourceFile1.fat SourceFile2.fat -o OutputExecutable.exe`

  * The `java -jar Fathom.jar` component of the command is needed to run the compiler program.
  * The `SourceFile.fat SourceFile2.fat` components of the command are the input files to compile. There is no limit to how many source files can be compiled at a time, however at least one is required.
  * The `-o OutputExecutable.exe` component of the command specifies the name of the executable to produce. The extension of the executable will vary depending on the operating system you are compiling for.



__Optional arguments to the Compiler Include__

  * `-csource` Output the compiled c source files during compilation.
  * `-verbose` or `-v` Output debugging messages during compilation.
  * `-dir` Used to specify the directory in which to include in the compilation.
  * `-dry` Perform a dry-run of the compilation. Do not compile into an executable.
  * `-gcc` Compile the c code with the GCC (GNU Compiler Collection) compiler.
  * `-tcc` Compile the c code with the TCC (Tiny C Compiler) compiler.
  * `-keepc` Keep the c output files that were generated during the compilation process.
  * `-cargs` Display the arguments that were passed to the c compiler.

The default c compiler is TCC because it is more stable at the moment.



© 2014 Fathomsoft

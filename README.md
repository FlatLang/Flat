Fathom Programming Language
===========================

The Fathom programming language is a c-based language that compiles into portable c code. The Java-like syntax makes Fathom a great choice for those who are not looking to spend countless hours learning new concepts. The main idea behind Fathom is to make cross-platform code run natively on a multitude of devices.

<br>
Instructions of Use:
  * To run the compiler, first locate the Fathom.jar file.
  * In command line or terminal run the jar file with the required arguments.
  * The required arguments include: The input files and the output destination.


<br>
An Example command to compile a source:<br>
`java -jar Fathom.jar SourceFile1.fat SourceFile2.fat -o OutputExecutable.exe`

  * The `java -jar Fathom.jar` component of the command is needed to run the compiler program.
  * The `SourceFile.fat SourceFile2.fat` components of the command are the input files to compile. There is no limit to how many source files can be compiled at a time, however at least one is required.
  * The `-o OutputExecutable.exe` component of the command specifies the name of the executable to produce. The extension of the executable will vary depending on the operating system you are compiling for.

<br>
Optional arguments to the Compiler Include:
  * `-csource` Output the compiled c source files during compilation.
  * `-verbose` or `-v` Output debugging messages during compilation.
  * `-gcc` Compile the c code with the GCC (GNU Compiler Collection) compiler.
  * `-tcc` Compile the c code with the TCC (Tiny C Compiler) compiler.

The default c compiler is GCC because of its wide support.

© 2014 Fathomsoft

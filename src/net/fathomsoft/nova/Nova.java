/**
 * The Nova Programming Language. Write Explosive Code.
 * Copyright (C) 2014  Braden Steffaniak <BradenSteffaniak@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * The Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.fathomsoft.nova;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.ClassNode;
import net.fathomsoft.nova.tree.FileNode;
import net.fathomsoft.nova.tree.MethodNode;
import net.fathomsoft.nova.tree.ProgramNode;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.tree.TreeNode;
import net.fathomsoft.nova.tree.exceptionhandling.ExceptionNode;
import net.fathomsoft.nova.util.Command;
import net.fathomsoft.nova.util.CommandListener;
import net.fathomsoft.nova.util.FileUtils;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * File used for the compilation process.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:00:04 PM
 * @version	v0.2.6 May 24, 2014 at 6:06:20 PM
 */
public class Nova
{
	private int					compiler;
	
	private long				flags;
	private long				startTime, endTime;
	
	private File				outputFile, workingDir;
	
	private SyntaxTree			tree;
	
	private ArrayList<String>	includeDirectories, errors, messages;
	
	private ArrayList<File>		inputFiles, cSourceFiles, cHeaderFiles;
	
	private List<File>			lingeringFiles;
	
	private static final int	OS;
	
	private static final String	OUTPUT_EXTENSION;
	
	public static final boolean	ANDROID_DEBUG = false;
	public static final boolean	DEBUG         = true;
	
	public static final int		BENCHMARK     = 0;
	
	public static final long	CSOURCE       = 0x1l;
	public static final long	FORMATC       = 0x10l;
	public static final long	VERBOSE       = 0x100l;
	public static final long	DRY_RUN       = 0x1000l;
	public static final long	KEEP_C        = 0x10000l;
	public static final long	C_ARGS        = 0x100000l;
	public static final long	RUNTIME       = 0x1000000l;
	public static final long	LIBRARY       = 0x10000000l;
	
	public static final int		GCC           = 1;
	public static final int		TCC           = 2;
	public static final int		CLANG         = 3;
	
	public static final int		WINDOWS       = 1;
	public static final int		MACOSX        = 2;
	public static final int		LINUX         = 3;
	
	public static final String	LANGUAGE_NAME = "Nova";
	public static final String	VERSION       = "v0.2.6";
	
	/**
	 * Find out which operating system the compiler is running on.
	 */
	static
	{
		String osName = System.getProperty("os.name").toLowerCase();
		
		if (osName.startsWith("win"))
		{
			OS = WINDOWS;
			OUTPUT_EXTENSION = ".exe";
		}
		else if (osName.startsWith("mac"))
		{
			OS = MACOSX;
			OUTPUT_EXTENSION = "";
		}
		else if (osName.startsWith("lin"))
		{
			OS = LINUX;
			OUTPUT_EXTENSION = "";
		}
		else
		{
			OS = 0;
			OUTPUT_EXTENSION = "";
		}
	}
	
	/**
	 * Method called whenever the compiler is invoked. Supplies the
	 * needed information for compiling the given files.
	 * 
	 * @param args The String array containing the locations of the files
	 * 		to compile, as well as other compiler arguments.
	 */
	public static void main(String args[])
	{
		Nova nova = new Nova(args);
	}
	
	/**
	 * Method used to initialize the compiler data and start the
	 * compilation process.
	 * 
	 * @param args The String array containing the locations of the files
	 * 		to compile, as well as other compiler arguments.
	 */
	private Nova(String args[])
	{
		if (BENCHMARK > 0)
		{
			try
			{
				System.out.println("Preparing Benchmark...");
				
				Thread.sleep(1000);
				
				System.out.println("Starting...");
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		lingeringFiles     = new LinkedList<File>();
		inputFiles         = new ArrayList<File>();
		cSourceFiles       = new ArrayList<File>();
		cHeaderFiles       = new ArrayList<File>();
		includeDirectories = new ArrayList<String>();
		errors             = new ArrayList<String>();
		messages           = new ArrayList<String>();
		
		compile(args);
	}
	
	/**
	 * Compile the input files given within the args.
	 * 
	 * @param args The String array containing the locations of the files
	 * 		to compile, as well as other compiler arguments.
	 */
	private void compile(String args[])
	{
		String directory = getWorkingDirectoryPath() + "example/";
		
		if (OS == WINDOWS)
		{
			compiler = TCC;
		}
		else
		{
			compiler = GCC;
		}
		
		if (DEBUG)
		{
			args = new String[]
			{
				directory + "Test.fat",
				directory + "IO.fat",
				directory + "String.fat",
				directory + "ExceptionData.fat",
				directory + "ArrayList.fat",
				directory + "Math.fat",
				directory + "Time.fat",
				directory + "Person.fat",
				directory + "DivideByZeroException.fat",
//				directory + "Nova.fat",
				directory + "Object.fat",
				directory + "List.fat",
				directory + "ListNode.fat",
				directory + "Thread.fat",
				directory + "Exception.fat",
				directory + "BodyBuilder.fat",
				directory + "Integer.fat",
				"-o", directory + "bin/Executable" + OUTPUT_EXTENSION,
				"-dir", '"' + directory + "../include" + '"',
				"-run",
//				"-csource",
				"-formatc",
				"-v",
//				"-gcc",
				"-cargs",
				"-keepc",
//				"-library",
			};
		}
		if (ANDROID_DEBUG)
		{
			enableFlag(DRY_RUN);
		}
		
		parseArguments(args);
		
		log("Nova " + VERSION + " Copyright (C) 2014  Braden Steffaniak <BradenSteffaniak@gmail.com>\n" +
				"This program comes with ABSOLUTELY NO WARRANTY\n" + //; for details type show w." +
				"This is free software, and you are welcome to redistribute it\n" +
				"under certain conditions");//; type show c for details.");
		
		workingDir = new File(directory);
		
		startTimer();
		
		// Try to create a Syntax Tree for the given file.
		try
		{
			int times = 1;
			
			if (BENCHMARK > 0)
			{
				times = BENCHMARK;
			}
			
			for (int i = 0; i < times; i++)
			{
				tree = new SyntaxTree(inputFiles.toArray(new File[0]), this);
			}
			
			tree.generateCOutput();
		}
		catch (IOException e1)
		{
			error("Could not generate the syntax tree for the file '");// + file.getName() + "'.");
			
			e1.printStackTrace();
			
			completed();
		}
		
		insertMainMethod();
		
		long time = System.currentTimeMillis() - startTime;
		
		String str = "Nova compile time: " + time + "ms";
		
		if (BENCHMARK > 0)
		{
			if (BENCHMARK > 1)
			{
				str += " (Average of " + Math.round(time / (float)BENCHMARK) + "ms)";
			}
			
			System.out.println(str);
		}
		else
		{
			log(str);
		}
		
		if (isFlagEnabled(FORMATC))
		{
			log("Formatting the output c output code...");
			tree.formatCOutput();
		}
		
		String headers[]   = tree.getCHeaderOutput();
		String sources[]   = tree.getCSourceOutput();
		String filenames[] = tree.getFilenames();
		
		if (isFlagEnabled(CSOURCE))
		{
			for (int i = 0; i < headers.length; i++)
			{
				log(headers[i]);
				log(sources[i]);
			}
		}
		
		for (int i = 0; i < inputFiles.size(); i++)
		{
			String header   = headers[i];
			String source   = sources[i];
			String fileName = filenames[i];
			
			try
			{
				File headerFile = FileUtils.writeFile(fileName + ".h", workingDir, header);
				File sourceFile = FileUtils.writeFile(fileName + ".c", workingDir, source);
				
				cHeaderFiles.add(headerFile);
				cSourceFiles.add(sourceFile);
				
				if (!isFlagEnabled(KEEP_C))
				{
					lingeringFiles.add(headerFile);
					lingeringFiles.add(sourceFile);
				}
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
				
				completed();
			}
		}
		
		if (!isFlagEnabled(DRY_RUN))
		{
			compileC();
		}
		else
		{
			completed();
		}
	}
	
	/**
	 * Insert the main method into the correct file. Also set up the
	 * initialization for the program within the main method.
	 */
	private void insertMainMethod()
	{
		MethodNode mainMethod = tree.getMainMethod();
		
		if (mainMethod == null)
		{
			if (!isFlagEnabled(LIBRARY))
			{
				SyntaxMessage.error("No main method found in program", this);
				
				completed();
			}
			
			return;
		}
		
		FileNode   fileNode   = (FileNode)mainMethod.getAncestorOfType(FileNode.class);
		
		if (mainMethod != null)
		{
			ClassNode classNode = (ClassNode)mainMethod.getAncestorOfType(ClassNode.class);

//			StringBuilder staticClassImport = new StringBuilder();
//			StringBuilder staticClassInit   = new StringBuilder();
//			StringBuilder staticClassFree   = new StringBuilder();
			
			ProgramNode root = tree.getRoot();
			
			for (int i = 0; i < root.getChildren().size(); i++)
			{
				TreeNode child = root.getChild(i);
				
				if (child instanceof FileNode)
				{
					FileNode f = (FileNode)child;
						
					for (int j = 0; j < f.getChildren().size(); j++)
					{
						TreeNode child2 = f.getChild(j);
						
						if (child2 instanceof ClassNode)
						{
							ClassNode c = (ClassNode)child2;
							
							if (c.containsStaticData())
							{
//								staticClassImport.append("#include \"" + c.getName() + ".h\"").append('\n');
//								staticClassInit.append("__static__").append(c.getName()).append(" = ").append(LANGUAGE_NAME.toLowerCase()).append('_').append(c.getName()).append('_').append(c.getName()).append("(0);").append('\n');
//								staticClassFree.append(LANGUAGE_NAME.toLowerCase()).append("_del_").append(c.getName()).append("(&__static__").append(c.getName()).append(", 0);").append('\n');
							}
						}
					}
				}
			}
			
			StringBuilder mainMethodText = new StringBuilder();
			
			mainMethodText.append('\n').append('\n');
			mainMethodText.append("#include <stdio.h>").append('\n');
			mainMethodText.append("#include <string.h>").append('\n');
//			mainMethodText.append(staticClassImport);
			//mainMethodText.append("jmp_buf __").append(LANGUAGE_NAME.toUpperCase()).append("__jmp_buf;").append('\n');
			mainMethodText.append('\n');
			mainMethodText.append("int main(int argc, char** argvs)").append('\n');
			mainMethodText.append("{").append('\n');
			mainMethodText.append	("String** args = (String**)malloc(argc * sizeof(String));").append('\n');
			mainMethodText.append	("int      i;").append('\n').append('\n');
			mainMethodText.append	("ExceptionData* ").append(ExceptionNode.EXCEPTION_DATA_IDENTIFIER).append(" = 0;").append('\n');
//			mainMethodText.append	(staticClassInit);
			mainMethodText.append	('\n');
			mainMethodText.append	("for (i = 0; i < argc; i++)").append('\n');
			mainMethodText.append	("{").append('\n');
			mainMethodText.append		("char* str = (char*)malloc(sizeof(char) * strlen(argvs[i]) + 1);").append('\n');
			mainMethodText.append		("copy_string(str, argvs[i]);").append('\n');
			mainMethodText.append		("args[i] = ").append(LANGUAGE_NAME.toLowerCase()).append("_String_String(0, str);").append('\n');
			mainMethodText.append	("}").append('\n');
			mainMethodText.append	('\n');
			mainMethodText.append	("TRY").append('\n');
			mainMethodText.append	('{').append('\n');
			mainMethodText.append		(mainMethod.generateCSourceName()).append('(').append(ExceptionNode.EXCEPTION_DATA_IDENTIFIER).append(", args);").append('\n');
			mainMethodText.append	('}').append('\n');
			mainMethodText.append	("CATCH (1)").append('\n');
			mainMethodText.append	('{').append('\n');
			mainMethodText.append		("printf(\"You broke it.\");").append('\n');
			mainMethodText.append		(LANGUAGE_NAME.toLowerCase()).append("_IO_waitForEnter(0);").append('\n');
			mainMethodText.append	('}').append('\n');
			mainMethodText.append	("FINALLY").append('\n');
			mainMethodText.append	('{').append('\n');
			mainMethodText.append		('\n');
			mainMethodText.append	('}').append('\n');
			mainMethodText.append	("END_TRY;").append('\n');
//			mainMethodText.append	(staticClassFree);
			mainMethodText.append	("free(args);").append('\n');
			mainMethodText.append	('\n');
			mainMethodText.append	("return 0;").append('\n');
			mainMethodText.append("}");
			
			String newSource = fileNode.generateCSource() + mainMethodText.toString();
			
			newSource = SyntaxUtils.formatText(newSource);
			
			fileNode.setSource(newSource);
		}
	}
	
	/**
	 * Compile the generated c code into an executable file.
	 */
	private void compileC()
	{
		StringBuilder cmd = new StringBuilder();
		
		if (compiler == GCC)
		{
			cmd.append("gcc ");//("compiler/gcc/bin/gcc.exe ");
		}
		else if (compiler == TCC)
		{
			cmd.append("compiler/tcc/tcc.exe ");
		}
		else if (compiler == CLANG)
		{
			cmd.append("clang ");
		}
		
//		if (OS == MACOSX)
//		{
//			cmd.append("-Wno-all ");
//		}
		
		for (int i = 0; i < includeDirectories.size(); i++)
		{
			String dir = includeDirectories.get(i);
			
			cmd.append("-I").append(dir).append(' ');
		}
		
		String libDir = getLibraryDir();
		
		cmd.append("-L").append(libDir).append(" -lFathom -lThread ");
		
		for (File sourceFile : cSourceFiles)
		{
			cmd.append('"').append(sourceFile.getAbsolutePath()).append('"').append(' ');
		}
		
		cmd.append("-o ").append('"').append(outputFile.getAbsolutePath()).append('"').append(' ');
		
		cmd.append("-O3 ");
//		cmd.append("-s ");
		
		if (OS == LINUX)
		{
			cmd.append("-lm ");
		}
		
		if (isFlagEnabled(C_ARGS))
		{
			System.out.println(cmd);
		}
		
		final Command command = new Command(cmd.toString(), workingDir);
		
		try
		{
			command.execute();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		command.addCommandListener(new CommandListener()
		{
			boolean failed = false;
			
			@Override
			public void resultReceived(int result)
			{
				if (!failed)
				{
					log("Compilation succeeded.");
				}
				else
				{
					System.err.println("Compilation failed.");
				}
			}
			
			@Override
			public void messageReceived(String message)
			{
				System.out.println(message);
			}
			
			@Override
			public void errorReceived(String message)
			{
				if (compiler == TCC)
				{
					if (message.contains("error: "))
					{
						failed = true;
					}
				}
				else if (compiler == GCC)
				{
					if (message.contains("error: "))
					{
						failed = true;
					}
				}
				else
				{
					failed = true;
				}
				
				System.err.println(message);
			}
			
			@Override
			public void commandExecuted()
			{
				try
				{
					command.terminate();
					
					completed();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Format a path according to how the specified OS needs it.
	 * 
	 * @param path The path to format for the OS standards.
	 * @return The formatted path.
	 */
	private String formatPath(String path)
	{
		if (OS == WINDOWS)
		{
			return '"' + path + '"';
		}
		else
		{
			return StringUtils.escapeSpaces(path);
		}
	}
	
	/**
	 * Get the directory that holds the Nova library.
	 * 
	 * @return The location of the directory that holds the library.
	 */
	private String getLibraryDir()
	{
		return formatPath(workingDir + "/../lib");
	}
	
	/**
	 * Output the log message from the compiler.
	 * 
	 * @param message The message describing what happened.
	 */
	public void log(String message)
	{
		log(flags, message);
	}
	
	/**
	 * Output the log message from the compiler.
	 * 
	 * @param flags The flags that verify whether the compiler is verbose.
	 * @param message The message describing what happened.
	 */
	public static void log(long flags, String message)
	{
		if (isFlagEnabled(flags, VERBOSE))
		{
			System.out.println(message);
		}
	}
	
	/**
	 * Output a warning message from the compiler.
	 * 
	 * @param message The message describing the warning.
	 */
	public void warning(String message)
	{
		errors.add("Warning: " + message);
	}
	
	/**
	 * Output an error message from the compiler.
	 * 
	 * @param message The message describing the error.
	 */
	public void error(String message)
	{
		if (!isFlagEnabled(DRY_RUN))
		{
			enableFlag(DRY_RUN);
			
			errors.add("Compilation failed.");
		}
		
		errors.add("Error: " + message);
	}
	
	/**
	 * Parse the arguments passed to the compiler.
	 * 
	 * @param args The list of arguments to parse.
	 */
	private void parseArguments(String args[])
	{
		// Start off the lastInput index to -1 because it will start
		// checking for (index - 1).
		// (index starts at 0, therefore 0 - 1 = -1)
		int     lastInput = -1;
		
		// Declare and initialize two booleans used to keep track of
		// whether or not the argument parser is expecting a certain
		// type of input at the current argument.
		boolean expectingOutputFile       = false;
		boolean expectingIncludeDirectory = false;
		
		// Iterate through all of the arguments.
		for (int i = 0; i < args.length; i++)
		{
			// Lowercase the argument for easier non-case-sensitive String
			// matching.
			String arg = args[i].toLowerCase();
			
			// Create temporary variables holding the current values.
			boolean expectingIncludeDirectoryTemp = expectingIncludeDirectory;
			
			// Set the variables to false in the expectation of a
			// different type of argument.
			expectingIncludeDirectory = false;
			
			// Check all other types of arguments.
			
			// If the user is trying to set the output location.
			if (arg.equals("-o"))
			{
				expectingOutputFile = true;
			}
			// If the user is trying to set the source include directory.
			else if (arg.equals("-dir"))
			{
				expectingIncludeDirectory = true;
			}
			// If the user wants to run the application after compilation.
			else if (arg.equals("-run"))
			{
				enableFlag(RUNTIME);
			}
			// If the user wants to view the c source output.
			else if (arg.equals("-csource"))
			{
				enableFlag(CSOURCE);
			}
			// If the user wants to format the c source output.
			else if (arg.equals("-formatc"))
			{
				enableFlag(FORMATC);
			}
			// If the user wants a more verbose compilation output,
			// explaining each step.
			else if (arg.equals("-verbose") || arg.equals("-v"))
			{
				if (BENCHMARK <= 0)
				{
					enableFlag(VERBOSE);
				}
			}
			// If the user wants to use the GCC c compiler.
			else if (arg.equals("-gcc"))
			{
				compiler = GCC;
			}
			// If the user wants to use the TCC c compiler.
			else if (arg.equals("-tcc"))
			{
				compiler = TCC;
			}
			// If the user wants to use the CLANG LLVM compiler.
			else if (arg.equals("-clang"))
			{
				compiler = CLANG;
			}
			// If the user wants to perform a dry run of the compilation
			// process.
			else if (arg.equals("-dry"))
			{
				enableFlag(DRY_RUN);
			}
			// If the user wants to keep the files that hold the c output.
			else if (arg.equals("-keepc"))
			{
				enableFlag(KEEP_C);
			}
			// If the user wants to obtain the c compiler arguments.
			else if (arg.equals("-cargs"))
			{
				enableFlag(C_ARGS);
			}
			// If the user wants to output a library instead of an
			// executable.
			else if (arg.equals("-library"))
			{
				enableFlag(LIBRARY);
			}
			// If none of the arguments were matched, check these:
			else
			{
				expectingIncludeDirectory = expectingIncludeDirectoryTemp;
				
				// If the argument is one of the first arguments passed
				// (If it is one of the sources to compile)
				if (lastInput == i - 1)
				{
					File file = new File(args[i]);
					
					inputFiles.add(file);
					
					lastInput = i;
				}
				// Check if we are still dealing with any  ongoing arguments
				// still.
				else if (expectingOutputFile)
				{
					outputFile = new File(args[i]);
					
					expectingOutputFile = false;
				}
				else if (expectingIncludeDirectory)
				{
					if (args[i].startsWith("\""))
					{
						args[i] = args[i].substring(1, args[i].length() - 1);
					}
					
					includeDirectories.add(formatPath(args[i]));
				}
			}
		}
		
		validateInputFiles();
		
//		if (outputFile == null)
//		{
//			enableFlag(RUNTIME);
//			
//			outputFile = new File(workingDir, "bin/Executa.exe");
//		}
	}
	
	/**
	 * Validate that the input files end with .fat. If any of them
	 * do not, an error will be output. Also outputs an error if the
	 * input file does not exist or is a directory.
	 */
	private void validateInputFiles()
	{
		boolean working = true;
		
		for (File f : inputFiles)
		{
			if (!f.getName().toLowerCase().endsWith(".fat"))
			{
				working = false;
				
				error("Input file '" + f.getName() + "' must have an extension of .fat");
			}
			else if (!f.exists())
			{
				working = false;
				
				error("Input file '" + f.getAbsolutePath() + "' does not exist.");
			}
			else if (!f.isFile())
			{
				working = false;
				
				error("Input file '" + f.getAbsolutePath() + "' is not a file.");
			}
		}
		
		if (!working)
		{
			startTimer();
			stopTimer();
			
			completed();
		}
	}
	
	/**
	 * Enable the specified flag.
	 * 
	 * @param flag The flag to set enable.
	 */
	private void enableFlag(long flag)
	{
		flags |= flag;
	}
	
	/**
	 * Disable the specified flag.
	 * 
	 * @param flag The flag to disable.
	 */
	private void disableFlag(long flag)
	{
		flags = flags & (~flag);
	}
	
	/**
	 * Check if the specific flag is enabled for the compiler.
	 * 
	 * @param flag The flag to check if is enabled.
	 * @return Whether or not the flag is enabled.
	 */
	private boolean isFlagEnabled(long flag)
	{
		return isFlagEnabled(flags, flag);
	}
	
	/**
	 * Check if the specific flag is enabled for the given set of flags.
	 * 
	 * @param flags The flags to verify the flag with.
	 * @param flag The flag to check if is enabled.
	 * @return Whether or not the flag is enabled.
	 */
	public static boolean isFlagEnabled(long flags, long flag)
	{
		return (flags & flag) != 0;
	}
	
	/**
	 * Get the working directory of the compiler.
	 * 
	 * @return The working directory of the compiler.
	 */
	private static String getWorkingDirectoryPath()
	{
		if (ANDROID_DEBUG)
		{
			return "/mnt/sdcard/AppProjects/Nova/";
		}
			
		return System.getProperty("user.dir").replace('\\', '/') + "/";
	}
	
	/**
	 * Start the compilation timer.
	 */
	private void startTimer()
	{
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * Stop the compilation timer.
	 */
	private void stopTimer()
	{
		endTime = System.currentTimeMillis();
	}
	 
	/**
	 * Get the time the compiler took to compile the input files.
	 * 
	 * @return The time in milliseconds it took to compile.
	 */
	private long getCompileTime()
	{
		return endTime - startTime;
	}
	
	/**
	 * Delete the files that are left over from the compilation process.
	 */
	private void deleteLingeringFiles()
	{
		Iterator<File> files = lingeringFiles.iterator();
		
		while (files.hasNext())
		{
			File file = files.next();
			
			if (!file.delete())
			{
				System.err.println("Error: Was not able to delete file: " + file.getAbsolutePath());
			}
		}
	}
	
	/**
	 * Output all of the stored errors, warnings, and other messages.
	 */
	private void outputMessages()
	{
		for (String s : messages)
		{
			System.out.println(s);
		}
		
		for (String s : errors)
		{
			System.err.println(s);
		}
	}
	
	/**
	 * Method called whenever the compilation sequence has completed.
	 */
	public void completed()
	{
		stopTimer();
		
		log("Compile time: " + getCompileTime() + "ms");
		
		deleteLingeringFiles();
		
		outputMessages();
		
		if (isFlagEnabled(RUNTIME))
		{
//			final Command c = new Command("start bin/Executa.exe", workingDir);
//			
//			c.addCommandListener(new CommandListener()
//			{
//				
//				@Override
//				public void resultReceived(int result)
//				{
//					if (result != 0)
//					{
//						System.err.println("error.");
//					}
//				}
//				
//				@Override
//				public void messageReceived(String message)
//				{
//					System.out.println(message);
//				}
//				
//				@Override
//				public void errorReceived(String message)
//				{
//					System.err.println(message);
//				}
//				
//				@Override
//				public void commandExecuted()
//				{
//					try
//					{
//						c.terminate();
//					}
//					catch (InterruptedException e)
//					{
//						e.printStackTrace();
//					}
//				}
//			});
//			try
//			{
//				c.execute();
//			}
//			catch (IOException e)
//			{
//				e.printStackTrace();
//			}
		}
		
		if (!ANDROID_DEBUG)
		{
			System.exit(0);
		}
	}
}

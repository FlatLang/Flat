/**
 * The Fathom Programming Language. Write Unbelievable Code.
 *  Copyright (C) 2014  Braden Steffaniak <BradenSteffaniak@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.fathomsoft.fathom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.fathomsoft.fathom.tree.ClassNode;
import net.fathomsoft.fathom.tree.FileNode;
import net.fathomsoft.fathom.tree.MethodNode;
import net.fathomsoft.fathom.tree.ProgramNode;
import net.fathomsoft.fathom.tree.SyntaxTree;
import net.fathomsoft.fathom.tree.TreeNode;
import net.fathomsoft.fathom.util.Command;
import net.fathomsoft.fathom.util.CommandListener;
import net.fathomsoft.fathom.util.FileUtils;
import net.fathomsoft.fathom.util.StringUtils;
import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * File used for the compilation process.
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:00:04 PM
 * @since	v0.1
 * @version	Mar 28, 2014 at 6:15:04 PM
 * @version	v0.2
 */
public class Fathom
{
	private int					compiler;
	
	private long				flags;
	private long				startTime, endTime;
	
	private File				outputFile, workingDir;
	
	private SyntaxTree			tree;
	
	private ArrayList<String>	includeDirectories;
	
	private ArrayList<File>		inputFiles, cSourceFiles, cHeaderFiles;
	
	private List<File>			lingeringFiles;
	
	public static final boolean	ANDROID_DEBUG = false;
	
	public static final boolean	DEBUG         = true;
	
	private static final int	os;
	
	public static final long	CSOURCE       = 0x1l;
	public static final long	VERBOSE       = 0x10l;
	public static final long	DRY_RUN       = 0x100l;
	public static final long	KEEP_C        = 0x1000l;
	public static final long	C_ARGS        = 0x10000l;
	public static final long	RUNTIME       = 0x100000l;
	
	public static final int		GCC           = 1;
	public static final int		TCC           = 2;
	public static final int		CLANG         = 3;
	
	public static final int		WINDOWS       = 1;
	public static final int		MACOSX        = 2;
	public static final int		LINUX         = 3;
	
	public static final String	LANGUAGE_NAME = "Fathom";
	
	/**
	 * Find out which operating system the compiler is running on.
	 */
	static
	{
		String osName = System.getProperty("os.name").toLowerCase();
		
		if (osName.startsWith("win"))
		{
			os = WINDOWS;
		}
		else if (osName.startsWith("mac"))
		{
			os = MACOSX;
		}
		else if (osName.startsWith("lin"))
		{
			os = LINUX;
		}
		else
		{
			os = 0;
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
		Fathom fathom = new Fathom(args);
	}
	
	/**
	 * Method used to initialize the compiler data and start the
	 * compilation process.
	 * 
	 * @param args The String array containing the locations of the files
	 * 		to compile, as well as other compiler arguments.
	 */
	private Fathom(String args[])
	{
		lingeringFiles     = new LinkedList<File>();
		inputFiles         = new ArrayList<File>();
		cSourceFiles       = new ArrayList<File>();
		cHeaderFiles       = new ArrayList<File>();
		includeDirectories = new ArrayList<String>();
		
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
		startTimer();
		
		String directory = getWorkingDirectoryPath() + "example/";
		
		if (os == WINDOWS)
		{
			compiler = TCC;
		}
		else
		{
			compiler = CLANG;
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
				"-o", directory + "bin/Executable.exe",
				"-run",
				"-dir", '"' + directory + "../include\"",
				"-csource",
				"-v",
				"-cargs",
				"-keepc"
			};
		}
		if (ANDROID_DEBUG)
		{
			enableFlag(DRY_RUN);
		}
		
		parseArguments(args);
		
		log("Fathom  Copyright (C) 2014  Braden Steffaniak <BradenSteffaniak@gmail.com>\n" +
				"This program comes with ABSOLUTELY NO WARRANTY\n" + //; for details type show w." +
				"This is free software, and you are welcome to redistribute it\n" +
				"under certain conditions");//; type show c for details.");
		
		workingDir = new File(directory);
		
		// Try to create a Syntax Tree for the given file.
		try
		{
			tree = new SyntaxTree(inputFiles.toArray(new File[0]), flags);
			
			tree.generateCOutput();
		}
		catch (IOException e1)
		{
			error("Could not generate the syntax tree for the file '");// + file.getName() + "'.");
			
			e1.printStackTrace();
			
			completed();
		}
		
//		log("Generating the output c header code from the input file '" + file.getName() + "'...");
//		String headers[] = tree.getHeaderOutput();
//		
//		log("Generating the output c source code from the input file '" + file.getName() + "'...");
//		String sources[] = tree.getSourceOutput();
		
		insertMainMethod();
		
		long time = System.currentTimeMillis() - startTime;
		
		log("Fathom compile time: " + time + "ms");
		
		if (isFlagEnabled(CSOURCE))
		{
			log("Formatting the output c output code...");
			tree.formatCOutput();
		}
		
		String headers[] = tree.getCHeaderOutput();
		String sources[] = tree.getCSourceOutput();
		
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
			File   file   = inputFiles.get(i);
			String header = headers[i];
			String source = sources[i];
			
			String fileName = file.getName();
			fileName        = fileName.substring(0, fileName.lastIndexOf('.'));
			
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
		FileNode   fileNode   = (FileNode)mainMethod.getAncestorOfType(FileNode.class);
		
		if (mainMethod != null)
		{
			ClassNode classNode = (ClassNode)mainMethod.getAncestorOfType(ClassNode.class);

			StringBuilder staticClassInit = new StringBuilder();
			StringBuilder staticClassFree = new StringBuilder();
			
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
//								staticClassInit.append("SET_INSTANCE(").append(c.getName()).append(", __static__").append(c.getName()).append(')').append(';').append('\n');
								staticClassInit.append("__static__").append(c.getName()).append(" = ").append("new_").append(c.getName()).append("(0);").append('\n');
								staticClassFree.append("del_").append(c.getName()).append("(__static__").append(c.getName()).append(", 0);").append('\n');
							}
						}
					}
				}
			}
			
			StringBuilder mainMethodText = new StringBuilder();
			
			mainMethodText.append('\n').append('\n');
			mainMethodText.append("#include \"Fathom.h\"").append('\n');
			mainMethodText.append("#include <stdio.h>").append('\n');
			//mainMethodText.append("jmp_buf __FATHOM__jmp_buf;").append('\n');
			mainMethodText.append('\n');
			mainMethodText.append("int main(int argc, char** argvs)").append('\n');
			mainMethodText.append("{").append('\n');
			mainMethodText.append("String** args = (String**)malloc(argc * sizeof(String));").append('\n');
			mainMethodText.append("int      i;").append('\n').append('\n');
			mainMethodText.append("ExceptionData* __FATHOM__exception_data = 0;").append('\n');
			mainMethodText.append(staticClassInit);
			mainMethodText.append('\n');
			mainMethodText.append("for (i = 0; i < argc; i++)").append('\n');
			mainMethodText.append("{").append('\n');
			mainMethodText.append("args[i] = new_String(0, argvs[i]);").append('\n');
			mainMethodText.append("}").append('\n');
			mainMethodText.append('\n');
			mainMethodText.append("TRY").append('\n');
			mainMethodText.append('{').append('\n');
			mainMethodText.append("__static__").append(classNode.getName()).append("->main(__static__").append(classNode.getName()).append(", __FATHOM__exception_data, args);").append('\n');
			mainMethodText.append('}').append('\n');
			mainMethodText.append("CATCH (1)").append('\n');
			mainMethodText.append('{').append('\n');
			mainMethodText.append("printf(\"You broke it.\");").append('\n');
			mainMethodText.append("__static__IO->waitForEnter(__static__IO, 0);").append('\n');
			mainMethodText.append('}').append('\n');
			mainMethodText.append("FINALLY").append('\n');
			mainMethodText.append('{').append('\n');
			mainMethodText.append('\n');
			mainMethodText.append('}').append('\n');
			mainMethodText.append("END_TRY;").append('\n');
			mainMethodText.append(staticClassFree);
			mainMethodText.append("free(args);").append('\n');
			mainMethodText.append("return 0;").append('\n');
			mainMethodText.append("}");
			
			String newSource = fileNode.generateCSourceOutput() + mainMethodText.toString();
			
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
			cmd.append("clang -Wno-all ");
		}
		
		for (int i = 0; i < includeDirectories.size(); i++)
		{
			String dir = includeDirectories.get(i);
			
			cmd.append("-I").append(dir).append(' ');
		}
		
		String libDir = getLibraryDir();
		
		cmd.append("-L").append(libDir).append(" -lFathom ");
		
		for (File sourceFile : cSourceFiles)
		{
			cmd.append('"').append(sourceFile.getAbsolutePath()).append('"').append(' ');
		}
		
		cmd.append("-o ").append('"').append(outputFile.getAbsolutePath()).append('"').append(' ');
		
		cmd.append("-O2");
//		cmd.append("-s");
		
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
			
			@Override
			public void resultReceived(int result)
			{
				if (result == 0)
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
		if (os == WINDOWS)
		{
			return '"' + path + '"';
		}
		else
		{
			return StringUtils.escapeSpaces(path);
		}
	}
	
	/**
	 * Get the directory that holds the Fathom library.
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
	private void log(String message)
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
	 * Output an error message from the compiler.
	 * 
	 * @param message The message describing the error.
	 */
	public static void error(String message)
	{
		System.err.println(message);
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
			// If the user wants a more verbose compilation output,
			// explaining each step.
			else if (arg.equals("-verbose") || arg.equals("-v"))
			{
				enableFlag(VERBOSE);
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
		
//		if (outputFile == null)
//		{
//			enableFlag(RUNTIME);
//			
//			outputFile = new File(workingDir, "bin/Executa.exe");
//		}
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
	 * @param The flags to verify the flag with.
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
			return "/mnt/sdcard/AppProjects/Fathom/";
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
	 * Method called whenever the compilation sequence has completed.
	 */
	private void completed()
	{
		stopTimer();
		
		log("Compile time: " + getCompileTime() + "ms");
		
		deleteLingeringFiles();
		
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

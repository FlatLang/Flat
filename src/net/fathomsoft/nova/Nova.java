package net.fathomsoft.nova;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.match.Match;
import net.fathomsoft.nova.util.*;

import static java.util.Arrays.stream;
import static net.fathomsoft.nova.util.FileUtils.formatPath;

/**
 * Class used for the compilation process.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:00:04 PM
 * @version	v0.2.44 Jul 13, 2015 at 1:28:17 AM
 */
public class Nova
{
	private boolean				testClasses;
	
	public boolean deleteOutputDirectory;
	
	private long				flags;
	private long				startTime, endTime;
	
	public File				outputFile;
	
	public File					workingDir;
	
	public String target = "c";
	
	private SyntaxTree			tree;
	
	public ArrayList<String>	externalImports;
	private ArrayList<String> errors, warnings, messages;
	
	public File outputDirectory;
	
	public HashMap<String, String> outputDirectories;
	
	
	public HashSet<String> includeDirectories;
	public ArrayList<File>		inputFiles;//, includeDirectories;
	
	public CodeGeneratorEngine codeGeneratorEngine;
	public CompileEngine compileEngine;
	
	public static final int	OS;
	
	public static final String EXECUTABLE_EXTENSION, DYNAMIC_LIB_EXT;
	
	public boolean				isTesting     = false;
	
	public static final boolean	ANDROID_DEBUG = false;
	public static final boolean	DEBUG         = true;
	
	// Set to 0 to not benchmark.
	public static final int		BENCHMARK     = 0;

	public static final long	SINGLE_THREAD = 0x1000000000000000l;
	public static final long	SMALL_BIN     = 0x0100000000000000l;
	public static final long	NO_GC         = 0x0010000000000000l;
	public static final long	LIBRARY       = 0x0001000000000000l;
	public static final long	RUNTIME       = 0x0000100000000000l;
	public static final long	C_ARGS        = 0x0000010000000000l;
	//////////////////////////////////////////////
	public static final long	SINGLE_FILE   = 0x0000001000000000l;
	public static final long	DRY_RUN       = 0x0000000100000000l;
	public static final long	VERBOSE       = 0x0000000010000000l;
	public static final long	FORMATC       = 0x0000000001000000l;
	public static final long	CSOURCE       = 0x0000000000100000l;
	public static final long	NO_C_OUTPUT   = 0x0000000000010000l;
	public static final long	NO_OPTIMIZE   = 0x0000000000001000l;
	
	public static final int		WINDOWS       = 1;
	public static final int		MACOSX        = 2;
	public static final int		LINUX         = 3;
	
	public static final String	LANGUAGE_NAME = "Nova";
	public static final String	VERSION       = "v0.2.44";
	
	/**
	 * Find out which operating system the compiler is running on.
	 */
	static
	{
		String osName = System.getProperty("os.name").toLowerCase();
		
		if (osName.startsWith("win"))
		{
			OS = WINDOWS;
			EXECUTABLE_EXTENSION = ".exe";
			DYNAMIC_LIB_EXT  = ".dll";
		}
		else if (osName.startsWith("mac"))
		{
			OS = MACOSX;
			EXECUTABLE_EXTENSION = "";
			DYNAMIC_LIB_EXT  = ".dylib";
		}
		else if (osName.startsWith("lin"))
		{
			OS = LINUX;
			EXECUTABLE_EXTENSION = "";
			DYNAMIC_LIB_EXT  = ".so";
		}
		else
		{
			OS = 0;
			EXECUTABLE_EXTENSION = "";
			DYNAMIC_LIB_EXT  = "";
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
		Nova nova = new Nova();
		
		nova.compile(args, true);
		
		nova.compileEngine.compile();
	}
	
	/**
	 * Method used to initialize the compiler data and start the
	 * compilation process.
	 */
	public Nova()
	{
		if (BENCHMARK > 0)
		{
			try
			{
				System.out.println("Preparing Benchmark...");
				
				Thread.sleep(100);
				
				System.out.println("Starting...");
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			enableFlag(DRY_RUN);
		}
		
		inputFiles         = new ArrayList<>();
		externalImports    = new ArrayList<>();
		errors             = new ArrayList<>();
		warnings           = new ArrayList<>();
		messages           = new ArrayList<>();
		
//		cSourceFiles       = new ArrayList<>();
//		cHeaderFiles       = new ArrayList<>();
		includeDirectories = new HashSet<>();
		outputDirectories  = new HashMap<>();
		
		testClasses = false;//BENCHMARK <= 0;
	}
	
	private void startEngines()
	{
		String formattedTarget = null;
		
		if (target.equals("c"))
		{
			formattedTarget = "C";
		}
		else if (target.equals("js"))
		{
			formattedTarget = "JS";
		}
		else if (target.equals("java"))
		{
			formattedTarget = "Java";
		}
		
		try
		{
			URL url = new File("../Nova-" + formattedTarget + "/out/production/Nova-" + formattedTarget).toURL();
			
			// Create a new class loader with the directory
			ClassLoader cl = new URLClassLoader(new URL[] { url });
			
			Class codeGeneratorEngineClass = cl.loadClass("nova." + target + ".engines." + formattedTarget + "CodeGeneratorEngine");
			Class compileEngineClass = cl.loadClass("nova." + target + ".engines." + formattedTarget + "CompileEngine");
			
			java.lang.reflect.Constructor codeGeneratorEngineConstructor = codeGeneratorEngineClass.getConstructor(Nova.class);
			java.lang.reflect.Constructor compileEngineConstructor = compileEngineClass.getConstructor(Nova.class);
			
			codeGeneratorEngine = (CodeGeneratorEngine)codeGeneratorEngineConstructor.newInstance(this);
			compileEngine = (CompileEngine)compileEngineConstructor.newInstance(this);
		}
		catch (InvocationTargetException e)
		{
			System.exit(1);
		}
		catch (InstantiationException e)
		{
			System.exit(2);
		}
		catch (IllegalAccessException e)
		{
			System.exit(3);
		}
		catch (NoSuchMethodException e)
		{
			System.exit(4);
		}
		catch (ClassNotFoundException e)
		{
			System.exit(5);
		}
		catch (MalformedURLException e)
		{
			System.exit(6);
		}
		
		//codeGeneratorEngine = new CCodeGeneratorEngine(this);
		//compileEngine = new CCompileEngine(this);
	}
	
	/**
	 * Compile the input files given within the args.
	 * 
	 * @param args The String array containing the locations of the files
	 * 		to compile, as well as other compiler arguments.
	 */
	public void compile(String args[], boolean generateCode)
	{
		String workingPath = getWorkingDirectoryPath();
		String directory = workingPath + "../Misc/example/";
		
		if (DEBUG)
		{
			testClasses();
			
			String target = "c";
			
			args = new String[]
			{
				"../Compiler",
				"../Misc/example",
				"../Misc/stabilitytest", 
				"-output-directory", "../NovaCompilerOutput/" + target,
				"-package-output-directory", "nova", "../StandardLibrary/" + target,
//				"-dir", formatPath(directory + "../example"),
//				"-dir", formatPath(directory + "../stabilitytest"),
//				"-run",
//				"-csource",
				"-formatc",
				//testClasses ? "-v" : "",
				"-v",
//				"-gcc",
//				"-tcc",
//				"-small",
//				"-cargs",
//				"-keepc",
				"-single-thread",
				"-single-file",
				"-main",
//				"example/Lab",
				"stabilitytest/StabilityTest",
//				"example/SvgChart",
//				"example/HashMapDemo",
//				"example/HashSetDemo",
//				"compiler/Compiler",
//				"-nogc",
//				"-no-c-output",
//				"-dry",
//				"-no-notes",
//				"-no-warnings",
//				"-no-errors",
				"-no-optimize",
				"-target", target,
//				"-library",
			};
		}
		if (ANDROID_DEBUG)
		{
			enableFlag(DRY_RUN);
		}
		
		String postArgs[] = new String[]
		{
			"-dir", formatPath(workingPath + "include"),
			"-dir", formatPath(workingPath + "include/gc"),
			"-dir", formatPath(workingPath + "include/nova_mysql"),
			"-dir", formatPath(workingPath + "include/nova_openssl"),
			"-dir", formatPath(workingPath),
			formatPath(directory + "bin/Executable"),// + EXECUTABLE_EXTENSION),
		};
		
//		for (String location : standardFiles)
//		{
//			location = removeSurroundingQuotes(location);
//			
//			inputFiles.add(new File(location));
//		}
		
		args = prependArguments(args, new String[] { "../StandardLibrary/nova" });
		args = appendArguments(args, postArgs);
		
		parseArguments(args);
		
		codeGeneratorEngine.initializeOutputDirectory();
		
//		log("Nova " + VERSION + " Copyright (C) 2014  Braden Steffaniak <BradenSteffaniak@gmail.com>\n" +
//				"This program comes with ABSOLUTELY NO WARRANTY\n" + //; for details type show w." +
//				"This is free software, and you are welcome to redistribute it\n" +
//				"under certain conditions");//; type show c for details.");
		
		workingDir = new File(getWorkingDirectoryPath());
		
		startTimer();
		
		createSyntaxTree(generateCode);
	}
	
	private String[] prependArguments(String args[], String ... newData)
	{
		String temp[] = new String[args.length + newData.length];
		
		System.arraycopy(newData, 0, temp, 0, newData.length);
		System.arraycopy(args, 0, temp, newData.length, args.length);
		
		return temp;
	}
	
	private String[] appendArguments(String args[], String ... newData)
	{
		String temp[] = new String[args.length + newData.length];
		
		System.arraycopy(args, 0, temp, 0, args.length);
		System.arraycopy(newData, 0, temp, args.length, newData.length);
		
		return temp;
	}
	
	private void createSyntaxTree(boolean generateCode)
	{
		// Try to create a Syntax Tree for the given file.
		try
		{
			int times = 1;
			
			if (BENCHMARK > 0)
			{
				times = BENCHMARK;
			}
			
			try
			{
				for (int i = 0; i < times; i++)
				{
					long before = System.currentTimeMillis();
					
					tree = new SyntaxTree(inputFiles.toArray(new File[0]), this);
					
					codeGeneratorEngine.tree = tree;
					compileEngine.tree = tree;
					
					if (containsErrors())
					{
						enableFlag(DRY_RUN);
						completed(false);
					}
					if (generateCode)
					{
						long time = System.currentTimeMillis();
						long newTime = time;
						
						log("Generating output... ", false);
						codeGeneratorEngine.generateOutput();
						log("took " + ((newTime = System.currentTimeMillis()) - time) + "ms");
						time = newTime;
						
						log("Inserting main method... ", false);
						codeGeneratorEngine.insertMainMethod();
						log("took " + ((newTime = System.currentTimeMillis()) - time) + "ms");
						time = newTime;
						
						log("Formatting output... ", false);
						codeGeneratorEngine.formatOutput();
						log("took " + ((newTime = System.currentTimeMillis()) - time) + "ms");
						time = newTime;
						
						log("Writing files... ", false);
						codeGeneratorEngine.writeFiles();
						log("took " + ((newTime = System.currentTimeMillis()) - time) + "ms");
						time = newTime;
						
					}
					
					long time = System.currentTimeMillis() - before;
					
					if (BENCHMARK > 1)
					{
						System.out.println("Benchmark " + (i + 1) + ": " + time + "ms");
					}
				}
			}
			catch (SyntaxErrorException e)
			{
				enableFlag(DRY_RUN);
				completed(false);
			}
		}
		catch (IOException e1)
		{
			error("Could not generate the syntax tree for the file '");// + file.getName() + "'.");
			
			e1.printStackTrace();
			
			completed(false);
		}
		
		long   time = System.currentTimeMillis() - startTime;
		
		String str  = LANGUAGE_NAME + " compile time: " + time + "ms";
		
		outputMessages();
		
		if (BENCHMARK > 0)
		{
			if (BENCHMARK > 1)
			{
				str += " (Average of " + String.format("%.3f", time / (float)BENCHMARK) + "ms)";
			}
			
			System.out.println(str);
		}
		else
		{
			log(str);
		}
	}
	
	/**
	 * Output the log message from the compiler.
	 * 
	 * @param message The message describing what happened.
	 */
	public void log(String message)
	{
		log(message, true);
	}
	
	public void log(String message, boolean newLine)
	{
		log(flags, message, newLine);
	}
	
	/**
	 * Output the log message from the compiler.
	 * 
	 * @param flags The flags that verify whether the compiler is verbose.
	 * @param message The message describing what happened.
	 */
	public static void log(long flags, String message)
	{
		log(flags, message, true);
	}
	
	public static void log(long flags, String message, boolean newLine)
	{
		if (isFlagEnabled(flags, VERBOSE))
		{
			System.out.print(message + (newLine ? '\n' : ""));
		}
	}
	
	/**
	 * Output a warning message from the compiler.
	 * 
	 * @param message The message describing the warning.
	 */
	public void warning(String message)
	{
		warnings.add("Warning: " + message);
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
		}
		
		String error = "Error: " + message;
		
		errors.add(error);
	}
	
	/**
	 * Get whether or not the compilation has accumulated any errors.
	 * 
	 * @return Whether or not there are any errors currently.
	 */
	public boolean containsErrors()
	{
		return errors.size() > 0;
	}
	
	/**
	 * Add the given external import location to be added to the
	 * compilation list.
	 * 
	 * @param file The File that is importing the location.
	 * @param location The location that is being imported.
	 */
	public void addExternalImport(FileDeclaration file, String location)
	{
		if (!StringUtils.containsString(location, FileDeclaration.DEFAULT_IMPORTS))
		{
//			location = file.getFile().getParent() + "/" + location;
			location = location.substring(0, location.length() - 1) + "c"; 
			location = FileUtils.findFileLocation(location, includeDirectories.toArray(new String[0]));
			
			if (location != null && !StringUtils.containsString(externalImports, location))
			{
				externalImports.add(location);
			}
		}
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
		int lastInput = -1;
		int skip = 0;
		
		for (int i = 0; i < args.length; i++)
		{
			if (args[i].startsWith("\""))
			{
				args[i] = StringUtils.removeSurroundingQuotes(args[i]);
			}
			
			if (args[i].toLowerCase().equals("-target"))
			{
				validateArgumentSize(args, i + 1);
				
				target = args[i + 1].toLowerCase();
			}
		}
		
		startEngines();
		
		// Iterate through all of the arguments.
		for (int i = 0; i < args.length; i++)
		{
			if (skip > 0)
			{
				skip--;
				
				continue;
			}
			
			// Lowercase the argument for easier non-case-sensitive String
			// matching.
			String arg = args[i].toLowerCase();
			
			if (arg.length() <= 0)
			{
				if (lastInput == i - 1)
				{
					lastInput = i;
				}
				
				continue;
			}
			
			if (compileEngine.checkArgument(arg, args, i))
			{
				
			}
			// If the user is trying to set the output location.
			else if (arg.equals("-o"))
			{
				validateArgumentSize(args, i + 1);
				
				outputFile = new File(args[i + 1]);
				
				skip = 1;
			}
			// If the user is trying to set the source include directory.
			else if (arg.equals("-dir"))
			{
				validateArgumentSize(args, i + 1);
				
				includeDirectories.add(formatPath(args[i + 1]));
				
				skip = 1;
			}
			// If the user wants to run the application after compilation.
			else if (arg.equals("-run"))
			{
				enableFlag(RUNTIME);
			}
			// Do not use bdw garbage collection.
			else if (arg.equals("-nogc"))
			{
				enableFlag(NO_GC);
			}
			else if (arg.equals("-no-c-output"))
			{
				enableFlag(NO_C_OUTPUT);
			}
			else if (arg.equals("-no-optimize"))
			{
				enableFlag(NO_OPTIMIZE);
			}
			else if (arg.equals("-target"))
			{
				skip = 1;
			}
			else if (arg.equals("-main"))
			{
				validateArgumentSize(args, i + 1);
				
				codeGeneratorEngine.mainClass = args[i + 1];
				
				skip = 1;
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
			// If the user wants to generate the output to a single file
			else if (arg.equals("-single-file"))
			{
				enableFlag(SINGLE_FILE);
			}
			// If the user wants to run a single threaded compilation
			else if (arg.equals("-single-thread"))
			{
				enableFlag(SINGLE_THREAD);
			}
			// If the user wants to perform a dry run of the compilation
			// process.
			else if (arg.equals("-dry"))
			{
				enableFlag(DRY_RUN);
			}
			// If the user wants to obtain the c compiler arguments.
			else if (arg.equals("-cargs"))
			{
				enableFlag(C_ARGS);
			}
			// If the user wants to generate a smaller executable output.
			else if (arg.equals("-small"))
			{
				enableFlag(SMALL_BIN);
			}
			// If the user wants to output a library instead of an
			// executable.
			else if (arg.equals("-library"))
			{
				enableFlag(LIBRARY);
			}
			// Specify a custom output directory.
			else if (arg.equals("-output-directory"))
			{
				validateArgumentSize(args, i + 1);
				
				outputDirectory = new File(args[i + 1]);
				
				skip = 1;
			}
			// Specify a custom output directory for a specified package.
			else if (arg.equals("-package-output-directory"))
			{
				validateArgumentSize(args, i + 2);
				
				outputDirectories.put(args[i + 1], args[i + 2]);

				skip = 2;
			}
			// If none of the arguments were matched, check these:
			else
			{
				// If the argument is one of the first arguments passed
				// (If it is one of the sources to compile)
				if (lastInput == i - 1)
				{
					File file = new File(args[i]);
					
					inputFiles.add(file);
					
					lastInput = i;
				}
				else if (i == args.length - 1)
				{
					outputFile = new File(args[i]);
				}
				else
				{
					error("Unknown argument '" + args[i] + "'");
					completed(false);
				}
			}
		}
		
		validateInputFiles();
	}
	
	private void validateArgumentSize(String[] args, int size)
	{
		if (args.length <= size)
		{
			error("Invalid arguments passed");
			completed(false);
		}
	}
	
	private void addFilesFromDirectory(File directory)
	{
		stream(directory.listFiles()).filter(x -> x.getName().toLowerCase().endsWith(".nova")).forEach(x -> inputFiles.add(x));
		stream(directory.listFiles()).filter(x -> x.isDirectory()).forEach(this::addFilesFromDirectory);
	}
	
	/**
	 * Validate that the input files end with .fat. If any of them
	 * do not, an error will be output. Also outputs an error if the
	 * input file does not exist or is a directory.
	 */
	private void validateInputFiles()
	{
		boolean working = true;
		
		for (int i = 0; i < inputFiles.size(); i++)
		{
			File f = inputFiles.get(i);
			
			if (!f.isFile())
			{
				if (f.isDirectory())
				{
					addFilesFromDirectory(f);
					
					includeDirectories.add(f.getParentFile().getAbsolutePath());
				}
				else
				{
					working = false;
					
					error("Input file '" + f.getAbsolutePath() + "' is not a file.");
				}
			}
			else if (!f.getName().toLowerCase().endsWith(".nova"))
			{
				working = false;
				
				error("Input file '" + f.getName() + "' must have an extension of .nova");
			}
			else if (!f.exists())
			{
				working = false;
				
				error("Input file '" + f.getAbsolutePath() + "' does not exist.");
			}
		}
		
		for (int i = inputFiles.size() - 1; i >= 0; i--)
		{
			if (inputFiles.get(i).isDirectory())
			{
				inputFiles.remove(i);
			}
		}
		
		if (!working)
		{
			startTimer();
			stopTimer();
			
			outputMessages();
			
			completed(false);
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
	public boolean isFlagEnabled(long flag)
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
	
	public void setTestClasses(boolean testClasses)
	{
		this.testClasses = testClasses;
	}
	
	public SyntaxTree getTree()
	{
		return tree;
	}
	
	/**
	 * Output all of the stored errors, warnings, and other messages.
	 */
	private void outputMessages()
	{
		outputMessages(true);
	}
	
	private void outputMessages(boolean success)
	{
		outputMessages(success, warnings.size(), errors.size());
	}
	
	private void outputMessages(boolean success, int warningCount, int errorCount)
	{
		for (String s : messages)
		{
			System.out.println(s);
		}

		String status = success ? "succeeded" : "failed";
		String errorsText = "";
		String warningsText = "";
		
		if (warningCount > 0)
		{
			warningsText = " " + warningCount + " warning" + (warningCount > 1 ? "s" : "");
		}
		if (errorCount > 0)
		{
			status = "failed";
			
			errorsText = " " + errorCount + " error" + (errorCount > 1 ? "s" : "");
		}
		
		String with = errorsText.length() + warningsText.length() > 0 ? " with" : "";
		String message = "Compilation " + status + with + errorsText + warningsText;
		
		for (String s : warnings)
		{
			System.out.println(s);
		}
		for (String s : errors)
		{
			System.err.println(s);
		}
		
		if (status.equals("succeeded"))
		{
			log(message);
		}
		else
		{
			System.err.println(message);
		}
	}
	
	/**
	 * Method called whenever the compilation sequence has completed.
	 */
	public void completed(boolean success)
	{
		completed(success, warnings.size(), errors.size());
	}
	
	public void completed(boolean success, int warningCount, int errorCount)
	{
		stopTimer();
		
		log("Compile time: " + getCompileTime() + "ms");
		
		outputMessages(success, warningCount, errorCount);
		
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
	
	/**
	 * Used to represent a debugging breakpoint...
	 * 
	 * @param condition Whether or not to break.
	 */
	public static void debuggingBreakpoint(boolean condition)
	{
		if (condition)
		{
			System.out.println("Breakpoint");
		}
	}
	
	public static Nova generateTemporaryController()
	{
		Nova controller = new Nova();
		controller.isTesting = true;
		controller.setTestClasses(false);
		controller.compile(new String[0], false);
		
		return controller;
	}
	
	/**
	 * Call the test case methods for all of the classes to make sure they
	 * are working correctly.
	 */
	private void testClasses()
	{
		if (testClasses)
		{
			System.out.println("Testing classes");
			
			TestContext context = new TestContext();
			
			String error = null;
			
			error = ArgumentList.test(context);
			
			if (error == null)
			{
				error = Assignment.test(context);
				
				if (error == null)
				{
					error = BinaryOperation.test(context);
					
					if (error == null)
					{
						error = Cast.test(context);
						
						if (error == null)
						{
							error = ClassDeclaration.test(context);
							
							if (error == null)
							{
								error = Closure.test(context);
								
								if (error == null)
								{
									error = ClosureDeclaration.test(context);
									
									if (error == null)
									{
										error = Condition.test(context);
										
										if (error == null)
										{
											error = Constructor.test(context);
											
											if (error == null)
											{
												error = Destructor.test(context);
												
												if (error == null)
												{
													error = Dimensions.test(context);
													
													if (error == null)
													{
														error = ElseStatement.test(context);
														
														if (error == null)
														{
															error = ExternalMethodDeclaration.test(context);
															
															if (error == null)
															{
																error = ExternalType.test(context);
																
																if (error == null)
																{
																	error = ExternalTypeList.test(context);
																	
																	if (error == null)
																	{
																		error = FileDeclaration.test(context);
																		
																		if (error == null)
																		{
																			error = ForLoop.test(context);
																			
																			if (error == null)
																			{
																				error = Identifier.test(context);
																				
																				if (error == null)
																				{
																					error = IfStatement.test(context);
																					
																					if (error == null)
																					{
																						error = IIdentifier.test(context);
																						
																						if (error == null)
																						{
																							error = Import.test(context);
																							
																							if (error == null)
																							{
																								error = ImportList.test(context);
																								
																								if (error == null)
																								{
																									error = InstanceDeclaration.test(context);
																									
																									if (error == null)
																									{
																										error = Instantiation.test(context);
																										
																										if (error == null)
																										{
																											error = IValue.test(context);
																											
																											if (error == null)
																											{
																												error = Literal.test(context);
																												
																												if (error == null)
																												{
																													error = LocalDeclaration.test(context);
																													
																													if (error == null)
																													{
																														error = Loop.test(context);
																														
																														if (error == null)
																														{
																															error = LoopInitialization.test(context);
																															
																															if (error == null)
																															{
																																error = LoopUpdate.test(context);
																																
																																if (error == null)
																																{
																																	error = MethodCall.test(context);
																																	
																																	if (error == null)
																																	{
																																		error = MethodCallArgumentList.test(context);
																																		
																																		if (error == null)
																																		{
																																			error = MethodDeclaration.test(context);
																																			
																																			if (error == null)
																																			{
																																				error = MethodList.test(context);
																																				
																																				if (error == null)
																																				{
																																					error = Node.test(context);
																																					
																																					if (error == null)
																																					{
																																						error = Operator.test(context);
																																						
																																						if (error == null)
																																						{
																																							error = Parameter.test(context);
																																							
																																							if (error == null)
																																							{
																																								error = ParameterList.test(context);
																																								
																																								if (error == null)
																																								{
																																									error = Priority.test(context);
																																									
																																									if (error == null)
																																									{
																																										error = Program.test(context);
																																										
																																										if (error == null)
																																										{
																																											error = Return.test(context);
																																											
																																											if (error == null)
																																											{
																																												error = Scope.test(context);
																																												
																																												if (error == null)
																																												{
																																													error = SyntaxTree.test(context);
																																													
																																													if (error == null)
																																													{
																																														error = TreeGenerator.test(context);
																																														
																																														if (error == null)
																																														{
																																															error = UnaryOperation.test(context);
																																															
																																															if (error == null)
																																															{
																																																error = Until.test(context);
																																																
																																																if (error == null)
																																																{
																																																	error = Value.test(context);
																																																	
																																																	if (error == null)
																																																	{
																																																		error = VTable.test(context);
																																																		
																																																		if (error == null)
																																																		{
																																																			error = GenericCompatible.test(context);

																																																			if (error == null)
																																																			{
																																																				error = WhileLoop.test(context);
																																																				
																																																				if (error == null)
																																																				{
																																																					error = Match.test(context);
																																																				}
																																																			}
																																																		}
																																																	}
																																																}
																																															}
																																														}
																																													}
																																												}
																																											}
																																										}
																																									}
																																								}
																																							}
																																						}
																																					}
																																				}
																																			}
																																		}
																																	}
																																}
																															}
																														}
																													}
																												}
																											}
																										}
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
			if (error != null)
			{
				System.err.println("Pre-compilation class tests failed:");
				System.err.println(error);
				
				completed(true);
			}
			
			System.out.println("Done testing classes");
		}
	}
}

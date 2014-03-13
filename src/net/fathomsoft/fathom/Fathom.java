package net.fathomsoft.fathom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.fathomsoft.fathom.tree.SyntaxTree;
import net.fathomsoft.fathom.util.Command;
import net.fathomsoft.fathom.util.CommandListener;
import net.fathomsoft.fathom.util.FileUtils;

/**
 * File used for the compilation process.
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:00:04 PM
 * @since	v0.1
 * @version	Jan 5, 2014 at 9:00:04 PM
 * @version	v0.1
 */
public class Fathom
{
	private int					flags;
	
	private long				startTime, endTime;
	
	private ArrayList<File>		inputFiles, cSourceFiles, cHeaderFiles;
	
	private List<File>			lingeringFiles;
	
	public static final boolean	ANDROID_DEBUG = false;
	
	public static final boolean	DEBUG         = true;
	
	public static final int		CSOURCE       = 0x1;
	public static final int		VERBOSE       = 0x2;
	
	public static final String	LANGUAGE_NAME = "Fathom";
	
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
		lingeringFiles = new LinkedList<File>();
		inputFiles     = new ArrayList<File>();
		cSourceFiles   = new ArrayList<File>();
		cHeaderFiles   = new ArrayList<File>();
		
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
		
		String directory = getWorkingDirectoryPath();
		
		if (DEBUG)
		{
			args = new String[] { directory + "Test.fat", directory + "IO.fat", directory + "String.fat", "-csource", "-v" };
		}
		
		parseArguments(args);
		
		SyntaxTree tree = null;
		
		File cClass     = new File("CClass.h");
		
		File workingDir = new File(directory);
		
		for (File file : inputFiles)
		{
			log("Generating syntax tree for the file '" + file.getName() + "'...");
			
			// Try to create a Syntax Tree for the given file.
			try
			{
				tree = new SyntaxTree(file);
			}
			catch (IOException e1)
			{
				error("Could not generate the syntax tree for the file '" + file.getName() + "'.");
				
				e1.printStackTrace();
				
				completed();
			}
			
			log("Generating the output c header code from the input file '" + file.getName() + "'...");
			String header = tree.getRoot().generateCHeaderOutput();
			
			log("Generating the output c source code from the input file '" + file.getName() + "'...");
			String source = tree.getRoot().generateCSourceOutput();
			
			if (isFlagSet(CSOURCE))
			{
				log("Formatting the output c header code...");
				header = tree.formatText(header);
				log("Formatting the output c source code...");
				source = tree.formatText(source);
			
				log(header);
				log(source);
			}
			
			String fileName = file.getName();
			fileName        = fileName.substring(0, fileName.lastIndexOf('.'));
			
			try
			{
				File headerFile = FileUtils.writeFile(fileName + ".h", workingDir, header);
				File sourceFile = FileUtils.writeFile(fileName + ".c", workingDir, source);
				
				cHeaderFiles.add(headerFile);
				cSourceFiles.add(sourceFile);
				
				lingeringFiles.add(headerFile);
				lingeringFiles.add(sourceFile);
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
				
				completed();
			}
		}
		
//		completed();
		compileC(workingDir, cClass);
	}
	
	private void compileC(File workingDir, File cClass)
	{
		StringBuilder cmd = new StringBuilder();
		
//		cmd.append("gcc/bin/gcc.exe ").append("-E -P ").append('"').append(stdioFile.getAbsolutePath()).append('"').append("");
//		
//		final Command command2 = new Command(cmd.toString(), workingDir);
		
		cmd = new StringBuilder();
		
		cmd.append("gcc/bin/gcc.exe ");
		cmd.append('"').append(cClass.getAbsolutePath()).append('"').append(' ');
		
		for (File sourceFile : cSourceFiles)
		{
			cmd.append('"').append(sourceFile.getAbsolutePath()).append('"').append("").append(' ');
		}
		
		cmd.append("-o Executable.exe");
		
		final Command command = new Command(cmd.toString(), workingDir);
		
//		command2.addCommandListener(new CommandListener()
//		{
//			private StringBuilder output = new StringBuilder();
//			
//			@Override
//			public void resultReceived(int result)
//			{
//				if (result == 0)
//				{
////					System.out.println(output);
////					String prototypes[] = PrototypeFinder.findPrototypes(output.toString());
////					
////					System.out.println("Prototypes:");
////					
////					for (int i = 0; i < prototypes.length; i++)
////					{
////						System.out.println(prototypes[i]);
////					}
//					
//					try
//					{
//						System.out.println("Done");
//						
//						command2.terminate();
//						command.execute();
//					}
//					catch (InterruptedException e)
//					{
//						e.printStackTrace();
//					}
//					catch (IOException e)
//					{
//						e.printStackTrace();
//					}
//				}
//			}
//			
//			@Override
//			public void messageReceived(String message)
//			{
//				output.append(message);//.append('\n');
//			}
//			
//			@Override
//			public void errorReceived(String message)
//			{
//				System.err.println(message);
//			}
//			
//			@Override
//			public void commandExecuted()
//			{
//				
//			}
//		});
		
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
				System.out.println(result);
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
	 * Output the log message from the compiler.
	 * 
	 * @param message The message describing what happened.
	 */
	private void log(String message)
	{
		if (isFlagSet(VERBOSE))
		{
			System.out.println(message);
		}
	}
	
	/**
	 * Output an error message from the compiler.
	 * 
	 * @param message The message describing the error.
	 */
	private static void error(String message)
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
		int lastInput = -1;
		
		for (int i = 0; i < args.length; i++)
		{
			String arg = args[i].toLowerCase();
			
			if (arg.equals("-csource"))
			{
				setFlag(CSOURCE);
			}
			else if (arg.equals("-verbose") || arg.equals("-v"))
			{
				setFlag(VERBOSE);
			}
			else if (lastInput == i - 1)
			{
				File file = new File(args[i]);
				
				inputFiles.add(file);
				
				lastInput = i;
			}
		}
	}
	
	/**
	 * Set the specified flag as on.
	 * 
	 * @param flag The flag to set as on.
	 */
	private void setFlag(int flag)
	{
		flags |= flag;
	}
	
	/**
	 * Check if the specific flag is set for the compiler.
	 * 
	 * @param flag The flag to check if is set.
	 * @return Whether or not the flag is set.
	 */
	private boolean isFlagSet(int flag)
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
	 * Method called whenever the compilation sequence has completed.
	 */
	private void completed()
	{
		stopTimer();
		
		log("Compile time: " + getCompileTime());
		
		Iterator<File> files = lingeringFiles.iterator();
		
		while (files.hasNext())
		{
			File file = files.next();
			
			if (!file.delete())
			{
				System.err.println("Error: Was not able to delete file: " + file.getAbsolutePath());
			}
		}
		
		if (!ANDROID_DEBUG)
		{
			System.exit(0);
		}
	}
}

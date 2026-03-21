package nova.c.engines;

import net.fathomsoft.nova.CompileEngine;
import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.tree.FileDeclaration;
import net.fathomsoft.nova.tree.Parameter;
import net.fathomsoft.nova.util.Command;
import net.fathomsoft.nova.util.CommandListener;
import net.fathomsoft.nova.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static net.fathomsoft.nova.Nova.*;
import static net.fathomsoft.nova.util.FileUtils.formatPath;
import static nova.c.nodewriters.Writer.getWriter;

public class CCompileEngine extends CompileEngine
{
	public boolean forceRecompile, forceCheck, singleFile;
	
	public int compiler;
	
	public long flags;
	
	private String[] hiddenCompilerMessages;
	
	public static final int		GCC           = 1;
	public static final int		TCC           = 2;
	public static final int		CLANG         = 3;
	
	public static final long	NO_ERRORS       = 0x10000l;
	public static final long	NO_WARNINGS     = 0x01000l;
	public static final long	NO_NOTES        = 0x00100l;
	public static final long	LINE_NUMBERS    = 0x00010l;
	public static final long	FORCE_RECOMPILE = 0x00001l;
	
	public CCompileEngine(Nova controller)
	{
		super(controller);
		
		if (OS == WINDOWS)
		{
			compiler = GCC;
		}
		else
		{
			compiler = GCC;
		}
		
		ArrayList<String> compileMessages = new ArrayList<>();
		
		if ((flags & NO_NOTES) != 0)
		{
			compileMessages.add("note");
		}
		if ((flags & NO_WARNINGS) != 0)
		{
			compileMessages.add("warning");
		}
		if ((flags & NO_ERRORS) != 0)
		{
			compileMessages.add("error");
			compileMessages.add("fatal error");
		}
		
		hiddenCompilerMessages = compileMessages.toArray(new String[0]);
	}
	
	@Override
	public void addIncludeDirectories(HashSet<String> directories)
	{
		super.addIncludeDirectories(directories);
		
		directories.add(controller.targetEngineWorkingDir.getAbsolutePath());
	}
	
	@Override
	public boolean checkArgument(String arg)
	{
		// If the user wants to use the GCC c compiler.
		if (arg.equals("-gcc"))
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
		else if (arg.equals("-no-notes"))
		{
			flags |= NO_NOTES;
		}
		else if (arg.equals("-no-warnings"))
		{
			flags |= NO_WARNINGS;
		}
		else if (arg.equals("-no-errors"))
		{
			flags |= NO_ERRORS;
		}
		else if (arg.equals("-line-numbers"))
		{
			flags |= LINE_NUMBERS;
		}
		else if (arg.equals("-f") || arg.equals("-force"))
		{
			flags |= FORCE_RECOMPILE;
			
			forceRecompile = true;
		}
		else if (arg.equals("-force-check"))
		{
			forceCheck = true;
		}
		else if (arg.equals("-single-file"))
		{
			singleFile = true;
		}
		else 
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * Compile the generated c code into an executable file.
	 */
	public void compile()
	{
		final StringBuilder cmd = new StringBuilder();
		
		final String extension = OS == WINDOWS ? ".exe" : ""; 
		
		Optional<String> make = Arrays.stream(new String[] { "make", "mingw32-make", "nmake" }).filter(x ->
			Stream.of(System.getenv("PATH").split(Pattern.quote(File.pathSeparator)))
				.map(Paths::get)
				.anyMatch(path -> Files.exists(path.resolve(x + extension)))).findFirst();
		
		if (make.isPresent())
		{
			String extraArgs = "";
			
			if (compiler == GCC)
			{
				extraArgs += "NOVA_CC=gcc ";
				
				if ((flags & LINE_NUMBERS) != 0)
				{
					extraArgs += "LINE_NUMBERS=true ";
				}
				
				if (!controller.isFlagEnabled(NO_OPTIMIZE))
				{
					if (controller.isFlagEnabled(SMALL_BIN))
					{
						extraArgs += "SMALL_BIN=true ";
					}
				}
				else
				{
					extraArgs += "NO_OPTIMIZE=true ";
				}
			}
			else if (compiler == TCC)
			{
//				compilerDir = new File(StringUtils.removeSurroundingQuotes(formatPath(controller.targetEngineWorkingDir.getAbsolutePath() + "/compiler/tcc")));
				
				extraArgs += "NOVA_CC=tcc ";
			}
			else if (compiler == CLANG)
			{
				extraArgs += "NOVA_CC=clang ";
			}
			
			if (controller.isFlagEnabled(Nova.NO_GC))
			{
				extraArgs += "NO_GC=true ";
			}
			
			if (controller.isFlagEnabled(Nova.LIBRARY))
			{
				extraArgs += "LIBRARY=true ";
			}
			
			FileDeclaration[] files = tree.getFiles();
			
			for (FileDeclaration sourceFile : files)
			{
				File dir = controller.outputDirectory;
				
				if (controller.outputDirectories.containsKey(sourceFile.getPackage().getRootFolder()))
				{
					dir = new File(controller.outputDirectories.get(sourceFile.getPackage().getRootFolder()));
				}
				
				cmd.append(formatPath(dir.getAbsolutePath() + "/" + getWriter(sourceFile).generateSourceName())).append(' ');
			}
			
			for (String external : controller.externalImports)
			{
				cmd.append(formatPath(external)).append(' ');
			}
			
			//		cmd.append("-Ofast ");
			//		cmd.append("-s ");
			
			if (controller.isFlagEnabled(C_ARGS))
			{
				controller.log(cmd.toString());
			}
			
			if (controller.isFlagEnabled(DRY_RUN))
			{
				controller.completed(true);
			}
			
			controller.log("Compiling C sources...");
			
			extraArgs = extraArgs.length() > 0 ? " " + extraArgs.trim() : "";
			
			String script = make.get() + " install -j" + extraArgs;
			
			controller.log(script);
			
			final Command command = new Command(script, controller.outputDirectory);
			
			command.addCommandListener(new CommandListener()
			{
				boolean failed = false;
				
				int errorCount, warningCount;
				
				String currentMessage = "";
				
				@Override
				public void resultReceived(int result)
				{
					if (!stream(hiddenCompilerMessages).anyMatch(x -> currentMessage.contains(x + ":")))
					{
						System.err.println(currentMessage.trim());
					}
					
					if (!failed)
					{
						//	log("Compilation succeeded.");
					}
					else
					{
						//System.err.println("Compilation failed.");
					}
				}
				
				@Override
				public void messageReceived(String message)
				{
					if (message.contains(": ***"))
					{
						addError(message);
					}
					else
					{
						System.out.println(message);
					}
				}
				
				@Override
				public void errorReceived(String message)
				{
					if (compiler == TCC)
					{
						if (message.contains("error: "))
						{
							addError(message);
						}
					}
					else if (compiler == GCC)
					{
						if (message.contains("\nerror: ") || message.contains(": error: ") || message.contains(": fatal error: "))
						{
							addError(message);
						}
					}
					else
					{
						addError(message);
					}
					
					if (message.contains(": ***") || message.contains("ld.exe: "))
					{
						addError(message);
					}
				}
				
				private void addError(String message)
				{
					failed = true;
					
					currentMessage += message;
					
					if (message.trim().startsWith("^")) // position indicator
					{
						//"(.+?(:\\s*?(\\d+:[\\n\\r]|((warning|error):[^^]+))))+"
						String[] matches = stream(hiddenCompilerMessages).filter(x -> !currentMessage.contains(x + ":")).toArray(String[]::new);
						
						if (matches.length > 0)
						{
							stream(matches).map(String::toLowerCase).forEach(x ->
							{
								if (x.contains("error"))
									errorCount++;
								else if (x.contains("warning"))
									warningCount++;
							});
							
							System.err.println(currentMessage);
						}
						
						currentMessage = "";
					}
					else
					{
						currentMessage += "\n";
					}
				}
				
				@Override
				public void commandExecuted()
				{
					if (controller.deleteOutputDirectory)
					{
						controller.log("Deleting output directory...");
						
						if (!FileUtils.deleteDirectory(controller.outputDirectory))
						{
							controller.log("Failed to delete temporary output directory at " + controller.outputDirectory.getAbsolutePath());
						}
					}
					
					try
					{
						command.terminate();
						
						controller.completed(!failed, warningCount, errorCount);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			});
			
			try
			{
				command.execute();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			controller.error("Could not find make in PATH");
		}
	}
}
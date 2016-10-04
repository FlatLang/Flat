package net.fathomsoft.nova;

import net.fathomsoft.nova.tree.FileDeclaration;
import net.fathomsoft.nova.util.Command;
import net.fathomsoft.nova.util.CommandListener;
import net.fathomsoft.nova.util.FileUtils;
import net.fathomsoft.nova.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.util.Arrays.stream;
import static net.fathomsoft.nova.Nova.*;
import static net.fathomsoft.nova.util.FileUtils.formatPath;

public class CCompileEngine extends CompileEngine
{
	private int compiler;
	
	private long flags;
	
	private String[]            visibleCompilerMessages;
	
	public static final int		GCC           = 1;
	public static final int		TCC           = 2;
	public static final int		CLANG         = 3;
	
	public static final long	NO_ERRORS     = 0x0000000000000100l;
	public static final long	NO_WARNINGS   = 0x0000000000000010l;
	public static final long	NO_NOTES      = 0x0000000000000001l;
	
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
		
		if ((flags & NO_NOTES) == 0)
		{
			compileMessages.add("note");
		}
		if ((flags & NO_WARNINGS) == 0)
		{
			compileMessages.add("warning");
		}
		if ((flags & NO_ERRORS) == 0)
		{
			compileMessages.add("error");
			compileMessages.add("fatal error");
		}
		
		visibleCompilerMessages = compileMessages.toArray(new String[0]);
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
		
		File compilerDir  = null;
		
		if (compiler == GCC)
		{
			compilerDir = controller.workingDir;
			
			cmd.append("gcc -pipe -mwindows -mconsole ");
			
			if (Nova.OS == WINDOWS)
			{
				cmd.append("-Wl,--enable-stdcall-fixup ");
			}
			
			if (!controller.isFlagEnabled(NO_OPTIMIZE))
			{
				if (controller.isFlagEnabled(SMALL_BIN))
				{
					cmd.append("-ffast-math ");
				}
				else
				{
					cmd.append("-march=native -fomit-frame-pointer ");
				}
			}
		}
		else if (compiler == TCC)
		{
			compilerDir = new File(StringUtils.removeSurroundingQuotes(formatPath(controller.workingDir + "/compiler/tcc")));
			
			cmd.append("compiler/tcc/tcc.exe ");
		}
		else if (compiler == CLANG)
		{
			cmd.append("clang ");
		}
		
		if (!controller.isFlagEnabled(NO_GC))
		{
			cmd.append("-DUSE_GC ");
		}
		
		if (controller.isFlagEnabled(SMALL_BIN))
		{
			cmd.append("-Os -s ");
		}
		else if (!controller.isFlagEnabled(NO_OPTIMIZE))
		{
			cmd.append("-O2 ");
		}
		
		controller.includeDirectories.forEach(dir -> cmd.append("-I").append(formatPath(dir)).append(' '));
		
		cmd.append("-I").append(formatPath(controller.outputDirectory.getAbsolutePath())).append(' ');
		
		controller.outputDirectories.forEach((key, value) -> cmd.append("-I").append(formatPath(new File(value).getAbsolutePath())).append(' '));
		
		String libDir    = controller.outputFile.getParentFile().getAbsolutePath();
		String incDir    = controller.workingDir + "/include/";

//		String libNova   = formatPath(libDir + "libNova" + DYNAMIC_LIB_EXT);
//		String libThread = formatPath(libDir + "libThread" + DYNAMIC_LIB_EXT);
//		String libGC     = formatPath(libDir + "gc" + DYNAMIC_LIB_EXT);

//		cmd.append(libNova).append(' ');
//		cmd.append(libThread).append(' ');
//		cmd.append(libGC).append(' ');
		
		cmd.append(formatPath(((CCodeGeneratorEngine)controller.codeGeneratorEngine).nativeInterfaceSource.getAbsolutePath())).append(' ');
		cmd.append(formatPath(incDir + "Nova.c")).append(' ');
//		cmd.append(formatPath(incDir + "LibNovaThread.c")).append(' ');
		
		FileDeclaration[] files = tree.getFiles();
		
		for (FileDeclaration sourceFile : files)
		{
			File dir = controller.outputDirectory;
			
			if (controller.outputDirectories.containsKey(sourceFile.getPackage().getRootFolder()))
			{
				dir = new File(controller.outputDirectories.get(sourceFile.getPackage().getRootFolder()));
			}
			
			cmd.append(formatPath(dir.getAbsolutePath() + "/" + sourceFile.getTarget().generateSourceName())).append(' ');
		}
		
		cmd.append("-o ").append(formatPath(controller.outputFile.getAbsolutePath())).append(' ');
		
		if (OS == LINUX)
		{
			cmd.append("-L/usr/include/openssl ");
		}
		
		cmd.append("-L" + formatPath(libDir) + " -lcrypto ");

//		cmd.append("-Ofast ");
//		cmd.append("-s ");
		
		if (!controller.isFlagEnabled(NO_GC))
		{
			String l = "-L";//OS == WINDOWS ? "" : "-L";
			
			cmd.append("-lgc ");//-Wl," + l + formatPath(libDir) + " ");
			
			if (OS != MACOSX)
			{
				//	cmd.append("-Wl,-R ");
			}
		}
		
		if (OS == LINUX || OS == MACOSX)
		{
			cmd.append("-lpthread ");
		}
		if (OS == LINUX)// || OS == MACOSX)
		{
			cmd.append("-lm -ldl -lc -lmysqlclient ");
		}
		else if (OS == WINDOWS)
		{
			cmd.append("-lws2_32 -lmysql ");
		}
		
		if (controller.isFlagEnabled(C_ARGS))
		{
			System.out.println(cmd);
		}
		
		if (controller.isFlagEnabled(DRY_RUN))
		{
			controller.completed(true);
		}
		
		controller.log("Compiling C sources...");
		
		final Command command = new Command(cmd.toString(), compilerDir);
		
		command.addCommandListener(new CommandListener()
		{
			boolean failed = false;
			
			int errorCount, warningCount;
			
			String currentMessage = "";
			
			@Override
			public void resultReceived(int result)
			{
				if (stream(visibleCompilerMessages).anyMatch(x -> currentMessage.contains(x + ":")))
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
					if (message.contains("\nerror: ") || message.contains(": error: ") || message.contains(": fatal error: "))
					{
						failed = true;
					}
				}
				else
				{
					failed = true;
				}
				
				currentMessage += message;
				
				if (message.trim().startsWith("^"))
				{
					//"(.+?(:\\s*?(\\d+:[\\n\\r]|((warning|error):[^^]+))))+"
					String[] matches = stream(visibleCompilerMessages).filter(x -> currentMessage.contains(x + ":")).toArray(String[]::new);
					
					if (matches.length > 0)
					{
						stream(matches).map(String::toLowerCase).forEach(x -> {
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
}
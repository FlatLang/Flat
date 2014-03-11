package net.fathomsoft.fathom;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.fathomsoft.fathom.tree.SyntaxTree;
import net.fathomsoft.fathom.util.Command;
import net.fathomsoft.fathom.util.CommandListener;
import net.fathomsoft.fathom.util.Demacro;
import net.fathomsoft.fathom.util.FileUtils;
import net.fathomsoft.fathom.util.Macro;
import net.fathomsoft.fathom.util.MacroList;
import net.fathomsoft.fathom.util.PrototypeFinder;
import net.fathomsoft.fathom.util.TierMatcher;
import net.fathomsoft.fathom.util.TierPattern;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:00:04 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:00:04 PM
 * @version	v
 */
public class Fathom
{
	public static final boolean	ANDROID_DEBUG = false;
	
	public static final String	LANGUAGE_NAME = "Fathom";
	
	public static void main(String args[]) throws IOException
	{
		args = new String[] { "src/test.fat" };
		
		String directory = null;
		
		if (ANDROID_DEBUG)
		{
			directory = "/mnt/sdcard/AppProjects/Fathom/";
		}
		else
		{
			directory = System.getProperty("user.dir").replace('\\', '/') + "/";
		}
		
		File file = new File(directory + "IO.fat");
		
		SyntaxTree tree = null;
		
		final long start = System.currentTimeMillis();
		
		tree = new SyntaxTree(file);
		
//		String cclass = FileUtils.readFile(new File("CClass.h"));
		
		String header = tree.getRoot().generateCHeaderOutput();
		String source = tree.getRoot().generateCSourceOutput();
		
//		System.out.println(tree.formatText(header));
		
		File stdioFile = new File("stdio.h");
		
//		HashMap<String, MacroList> macros = Demacro.parseMacros(cclass);
		
//		String sources[] = Demacro.demacro(macros, 0, header, source);
		
		header = tree.formatText(header);
		source = tree.formatText(source);
		
		System.out.println(header);
		System.out.println(source);
		
		File workingDir = new File(directory);
		
		File headerFile = FileUtils.writeFile("test.h", workingDir, header);
		File sourceFile = FileUtils.writeFile("test.c", workingDir, source);
		
		headerFile.deleteOnExit();
		sourceFile.deleteOnExit();
		
		StringBuilder cmd = new StringBuilder();
		
		cmd.append("gcc/bin/gcc.exe ").append("-E -P ").append('"').append(stdioFile.getAbsolutePath()).append('"').append("");
		
		final Command command = new Command(cmd.toString(), workingDir);
		
		cmd = new StringBuilder();
		
		cmd.append("gcc/bin/gcc.exe ").append('"').append(sourceFile.getAbsolutePath()).append('"').append("");
		
		final Command command2 = new Command(cmd.toString(), workingDir);
		
		command.addCommandListener(new CommandListener()
		{
			private StringBuilder output = new StringBuilder();
			
			@Override
			public void resultReceived(int result)
			{
				if (result == 0)
				{
					System.out.println(output);
					String prototypes[] = PrototypeFinder.findPrototypes(output.toString());
					
					System.out.println("Prototypes:");
					
					for (int i = 0; i < prototypes.length; i++)
					{
						System.out.println(prototypes[i]);
					}
					
					try
					{
						System.out.println("Done");
						
						command.terminate();
						command2.execute();
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
			
			@Override
			public void messageReceived(String message)
			{
				output.append(message);//.append('\n');
			}
			
			@Override
			public void errorReceived(String message)
			{
				System.err.println(message);
			}
			
			@Override
			public void commandExecuted()
			{
				
			}
		});
		
//		try
//		{
//			command.execute();
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
		
		command2.addCommandListener(new CommandListener()
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
					command2.terminate();
					
					System.out.println("Done2");
					long end = System.currentTimeMillis();
					System.out.println("Compile time: " + (end - start));
					System.exit(0);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}

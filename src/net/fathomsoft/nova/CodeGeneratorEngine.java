package net.fathomsoft.nova;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.util.FileUtils;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public abstract class CodeGeneratorEngine
{
	public Nova controller;
	
	public SyntaxTree tree;
	
	public String				mainClass;
	
	public CodeGeneratorEngine(Nova controller)
	{
		this.controller = controller;
		this.tree = controller.getTree();
	}
	
	public File writeFile(String fileName, File parentDir, String source) throws IOException
	{
		return FileUtils.writeFile(fileName, parentDir, source);
	}
	
	public String formatText(String text)
	{
		return SyntaxUtils.formatText(text);
	}
	
	public File getOutputDirectory(FileDeclaration file)
	{
		File outputDir = controller.outputDirectory;
		
		String basePackage = file.getPackage().getRootFolder();
		
		if (controller.outputDirectories.containsKey(basePackage))
		{
			outputDir = new File(controller.outputDirectories.get(basePackage));
		}
		
		return outputDir;
	}
	
	public File initializeOutputDirectory()
	{
		if (controller.outputDirectory == null)
		{
			try
			{
				controller.outputDirectory = Files.createTempDirectory("tempNovaOutput").toFile();
				
				controller.deleteOutputDirectory = true;
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
		
		return controller.outputDirectory;
	}
	
	/**
	 * Generate the C Source and Header output from the data contained
	 * within the syntax tree.
	 */
	public abstract void generateOutput();
	
	public abstract void formatOutput();
	
	public abstract void writeFiles();
	
	/**
	 * Insert the main method into the correct file. Also set up the
	 * initialization for the program within the main method.
	 */
	public abstract void insertMainMethod();
}
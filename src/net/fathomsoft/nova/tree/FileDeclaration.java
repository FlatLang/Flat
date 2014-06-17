package net.fathomsoft.nova.tree;

import java.io.File;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.FileUtils;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Class used to organize the Files that are fed to the compiler.
 * Each file has a file name that is stored in the name field variable
 * of this class.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Feb 18, 2014 at 8:57:00 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class FileDeclaration extends Node
{
	//TODO: package name here?
	
	private String	header, source;
	
	private File	file;
	
	/**
	 * The default imports that each file uses.
	 */
	private static final String DEFAULT_IMPORTS[];
	
	/**
	 * Initialize the defaultImports constant.
	 */
	static
	{
		DEFAULT_IMPORTS = new String[]
		{
			"Nova.h",
			"ExceptionHandler.h",
			"ExceptionData",
			"Object",
			"String",
			"Math",
			"IO",
			"Integer",
			"Long",
			"Double",
			"Char",
			"DivideByZeroException"
		};
	}
	
	/**
	 * Instantiate and initialize the default values.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#TreeNode(Node, Location)
	 */
	public FileDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		ImportList imports = new ImportList(this, locationIn);
		
		addChild(imports, this);
		
		addDefaultImportNodes();
	}
	
	/**
	 * Get whether or not the given location has been imported.
	 * 
	 * @param importLocation The location of the import.
	 * @return Whether or not the given location has been imported.
	 */
	public boolean containsImport(String importLocation)
	{
		return getImport(importLocation) != null;
	}
	
	/**
	 * Get the Import node with the given import location, if it exists.
	 * 
	 * @param importLocation The location of the import.
	 * @return The ImportNode with the specified import location, if it
	 * 		exists.
	 */
	public Import getImport(String importLocation)
	{
		return getImportListNode().getImport(importLocation);
	}
	
	/**
	 * Get whether or not the given location is an external C import.
	 * 
	 * @return Whether or not the given location is an external C import.
	 */
	public boolean isExternalImport(String importLocation)
	{
		return getImportListNode().isExternal(importLocation);
	}
	
	/**
	 * Get whether or not the FileNode contains the ClassNode with
	 * the specified name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class Person
	 * {
	 * 	
	 * 	...
	 * 	
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>containsClass("Person")</code>" would return
	 * true.
	 * 
	 * @param className The name of the class to search for.
	 * @return Whether or not the FileNode contains the ClassNode
	 * 		with the specified name.
	 */
	public boolean containsClass(String className)
	{
		return getClassNode(className) != null;
	}
	
	/**
	 * Get the FileNode's ClassNode with the specified name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class Person
	 * {
	 * 	
	 * 	...
	 * 	
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>getClass("Person")</code>" would return the
	 * ClassNode for the "<code>age</code>" int field.
	 * 
	 * @param className The name of the class to search for.
	 * @return The ClassNode for the class, if it exists.
	 */
	public ClassDeclaration getClassNode(String className)
	{
		ClassDeclaration clazz = getClassNode();
		
		if (clazz.getName().equals(className))
		{
			return clazz;
		}
		
		return null;
	}
	
	/**
	 * Get the ClassNode of the class that is contained within the file.
	 * 
	 * @return The ClassNode instance from the file.
	 */
	public ClassDeclaration getClassNode()
	{
		return (ClassDeclaration)getChild(1);
	}
	
	/**
	 * Get the File that this FileNode represents.
	 * 
	 * @return The File that this FileNode represents.
	 */
	public File getFile()
	{
		return file;
	}
	
	/**
	 * Set the File that this FileNode represents.
	 * 
	 * @param file The File that this FileNode represents.
	 */
	public void setFile(File file)
	{
		this.file = file;
	}
	
	/**
	 * Get the name of the file without the extension.<br>
	 * <br>
	 * For example: A getName() call for a FileNode of Test.nova would
	 * return "Test"
	 * 
	 * @return The name of the file without the extension.
	 */
	public String getName()
	{
		return FileUtils.removeFileExtension(file.getName());
	}
	
	/**
	 * Get the name of the file that will be output as a C header file.<br>
	 * <br>
	 * For example: A generateCHeaderName() call for a FileNode of Test.nova
	 * would return "nova_Test.h"
	 * 
	 * @return The name of the file output as a C header file.
	 */
	public String generateCHeaderName()
	{
		return getClassNode().generateCSourceName() + ".h";
	}
	
	/**
	 * Get the name of the file that will be output as a C source file.<br>
	 * <br>
	 * For example: A generateCSourceName() call for a FileNode of Test.nova
	 * would return "nova_Test.c"
	 * 
	 * @return The name of the file output as a C source file.
	 */
	public String generateCSourceName()
	{
		return getClassNode().generateCSourceName() + ".c";
	}
	
	/**
	 * Make sure that the Class declarations are valid.
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	public void validateClasses(int phase)
	{
		ClassDeclaration clazz = getClassNode();
		
		if (clazz == null)
		{
			SyntaxMessage.error("File must contain a class", this);
		}
		
		clazz.validateDeclaration(phase);
		
		if (clazz.validate(phase) == null)
		{
			getParent().removeChild(this);
		}
	}
	
	/**
	 * Make sure that the Field declarations are valid.
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	public void validateFields(int phase)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			if (child != getImportListNode())
			{
				ClassDeclaration clazz = (ClassDeclaration)child;
				
				clazz.validateFields(phase);
			}
		}
	}
	
	/**
	 * Make sure that the Method declarations are valid.
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	public void validateMethods(int phase)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			if (child != getImportListNode())
			{
				ClassDeclaration clazz = (ClassDeclaration)child;
				
				clazz.validateMethods(phase);
			}
		}
	}
	
	/**
	 * Get the ImportListNode that contains all of the imports used within
	 * the file.
	 * 
	 * @return The ImportListNode instance.
	 */
	public ImportList getImportListNode()
	{
		return (ImportList)getChild(0);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#addChild(Node)
	 */
	@Override
	public void addChild(Node child)
	{
		if (child instanceof Import)
		{
			getImportListNode().addChild(child);
		}
		else if (child instanceof ClassDeclaration)
		{
			super.addChild(child);
		}
		else
		{
			SyntaxMessage.error("Unexpected statement", child);
			
			//super.addChild(child);
		}
	}

	/**
	 * @see net.fathomsoft.nova.tree.Identifier#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		if (header == null)
		{
			StringBuilder builder = new StringBuilder();
			
			String definitionName = "FILE_" + getName() + "_" + Nova.LANGUAGE_NAME.toUpperCase();
			
			builder.append("#pragma once").append('\n');
			builder.append("#ifndef ").append(definitionName).append('\n');
			builder.append("#define ").append(definitionName).append("\n\n");

			builder.append(generateDummyTypes()).append('\n');
			
			ImportList imports = getImportListNode();
			
			builder.append(imports.generateCHeader());
			
			for (int i = 0; i < getNumChildren(); i++)
			{
				Node child = getChild(i);
				
				if (child != imports)
				{
					builder.append(child.generateCHeader());
				}
			}
			
			builder.append('\n').append("#endif");
			
			header = builder.toString();
		}
		
		return header;
	}

	/**
	 * @see net.fathomsoft.nova.tree.Identifier#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		if (source == null)
		{
			StringBuilder builder = new StringBuilder();
			
			builder.append("#include <precompiled.h>\n\n");
			
			for (int i = 0; i < getNumChildren(); i++)
			{
				Node child = getChild(i);
				
				builder.append(child.generateCSource());
			}
			
			source = builder.toString();
		}
		
		return source;
	}
	
	/**
	 * Set the header output String for the File.
	 * 
	 * @param header The header output String.
	 */
	public void setHeader(String header)
	{
		this.header = header;
	}
	
	/**
	 * Set the source output String for the File.
	 * 
	 * @param source The source output String.
	 */
	public void setSource(String source)
	{
		this.source = source;
	}
	
	/**
	 * Add all of the default imports that each file must include. The
	 * default imports are included within the {@link #DEFAULT_IMPORTS}
	 * array.
	 */
	private void addDefaultImportNodes()
	{
		for (String importLoc : DEFAULT_IMPORTS)
		{
			Import importNode = Import.decodeStatement(this, "import \"" + importLoc + "\"", getLocationIn(), true, false);
			
			addChild(importNode);
		}
	}
	
	/**
	 * Generate dummy class declarations for each of the imported files.
	 * This is needed in a situation when a class imports a class that
	 * in returns needs to import the respective one. In other words,
	 * the chicken vs egg scenario.
	 * 
	 * @return The generated code used for generating the dummy class
	 * 		types.
	 */
	private String generateDummyTypes()
	{
		StringBuilder builder = new StringBuilder();
		
//		builder.append("typedef struct ExceptionData ExceptionData;\n");
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			if (child instanceof ClassDeclaration)
			{
				ClassDeclaration clazz = (ClassDeclaration)child;
				
				builder.append("typedef struct ").append(clazz.getName()).append(' ').append(clazz.getName()).append(';').append('\n');
			}
		}
		
//		ImportListNode imports = getImportListNode();
//		
//		for (int i = 0; i < imports.getNumChildren(); i++)
//		{
//			ImportNode node = (ImportNode)imports.getChild(i);
//			
//			if (!node.isExternal())
//			{
//				String name = node.getLocationNode().getName();
//				
//				builder.append("typedef struct ").append(name).append(' ').append(name).append(';').append('\n');
//			}
//		}
		
		return builder.toString();
	}
	
	/**
	 * Format the C Header output, if the output has been generated.
	 */
	public void formatCHeaderOutput()
	{
		if (header == null)
		{
			return;
		}
		
		setHeader(SyntaxUtils.formatText(header));
	}
	
	/**
	 * Format the C Source output, if the output has been generated.
	 */
	public void formatCSourceOutput()
	{
		if (source == null)
		{
			return;
		}
		
		setSource(SyntaxUtils.formatText(source));
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public FileDeclaration clone(Node temporaryParent, Location locationIn)
	{
		FileDeclaration node = new FileDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given FileNde with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public FileDeclaration cloneTo(FileDeclaration node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Get the name of the file as the toString().
	 * 
	 * @return The name of the file.
	 */
	public String toString()
	{
		return file.getName();
	}
}
package net.fathomsoft.nova.tree;

import java.io.File;
import java.util.ArrayList;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
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
 * @version	v0.2.33 Sep 29, 2014 at 10:29:33 AM
 */
public class FileDeclaration extends Node
{
	//TODO: package name here?
	
	private StringBuilder	header, source;
	
	private File			file;
	
	private ArrayList<ClosureDeclaration> closures;
	
	/**
	 * The default imports that each file uses.
	 */
	public static final String DEFAULT_IMPORTS[];
	
	/**
	 * Initialize the defaultImports constant.
	 */
	static
	{
		DEFAULT_IMPORTS = new String[]
		{
			"Nova.h",
			"ExceptionHandler.h",
			Nova.getClassLocation("ExceptionData"),
			Nova.getClassLocation("Exception"),
			Nova.getClassLocation("DivideByZeroException"),
			Nova.getClassLocation("Console"),
			Nova.getClassLocation("Number"),
			Nova.getClassLocation("Byte"),
			Nova.getClassLocation("Short"),
			Nova.getClassLocation("Int"),
			Nova.getClassLocation("Long"),
			Nova.getClassLocation("Float"),
			Nova.getClassLocation("Double"),
			Nova.getClassLocation("Null"),
			Nova.getClassLocation("Char"),
			Nova.getClassLocation("Bool"),
			Nova.getClassLocation("GC"),
			Nova.getClassLocation("Object"),
			Nova.getClassLocation("String"),
			Nova.getClassLocation("System"),
			Nova.getClassLocation("Math"),
		};
	}
	
	/**
	 * Instantiate and initialize the default values.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public FileDeclaration(Node temporaryParent, Location locationIn, File file)
	{
		super(temporaryParent, locationIn);
		
		closures  = new ArrayList<ClosureDeclaration>();
		this.file = file;
		
		ImportList imports = new ImportList(this, locationIn);
		
		addChild(imports, this);
		
		addDefaultImports();
		
		Package p = Package.generateDefaultPackage(this, Location.INVALID);
		setPackage(p);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}
	
	/**
	 * Add all of the imports of the classes that are within the same
	 * directory of the specified file.
	 */
	public void addAutoImports()
	{
		getProgram().addAutoImports(this);
	}
	
	/**
	 * Add an Import node from the given location.
	 * 
	 * @param loc The location to add an import from.
	 * @return The generated Import node.
	 */
	public Import addImport(String loc)
	{
		if (containsImport(loc) || !SyntaxUtils.isAbsoluteClassLocation(loc) && getClassDeclaration() != null && getClassDeclaration().getName().equals(loc))
		{
			return null;
		}
		
		Import importNode = Import.decodeStatement(this, "import \"" + loc + "\"", getLocationIn(), true);
		
		addChild(importNode);
		
		return importNode;
	}
	
	/**
	 * Register the ClosureDeclaration so that the FileDeclaration
	 * can define the closure type during generation.
	 * 
	 * @param closure The ClosureDeclaration to register.
	 * @return The id of the registered ClosureDeclaration.
	 */
	public int registerClosure(ClosureDeclaration closure)
	{
		closures.add(closure);
		
		return closures.size();
	}
	
	/**
	 * Get whether or not the given location has been imported.
	 * 
	 * @param importLocation The location of the import.
	 * @return Whether or not the given location has been imported.
	 */
	public boolean containsImport(String importLocation)
	{
		return containsImport(importLocation, true);
	}
	
	/**
	 * Get whether or not the given location has been imported.
	 * 
	 * @param importLocation The location of the import.
	 * @param absoluteLocation Whether or not the importLocation is an
	 * 		package relative path (true), or just a class name (false).
	 * @return Whether or not the given location has been imported.
	 */
	public boolean containsImport(String importLocation, boolean absoluteLocation)
	{
		return getImport(importLocation, absoluteLocation) != null;
	}
	
	/**
	 * Get the Import node with the given import location, if it exists.
	 * 
	 * @param importLocation The location of the import.
	 * @return The Import with the specified import location, if it
	 * 		exists.
	 */
	public Import getImport(String importLocation)
	{
		return getImport(importLocation, true);
	}
	
	/**
	 * Get the Import node with the given import location, if it exists.
	 * 
	 * @param importLocation The location of the import.
	 * @param absoluteLocation Whether or not the importLocation is an
	 * 		package relative path (true), or just a class name (false).
	 * @return The Import with the specified import location, if it
	 * 		exists.
	 */
	public Import getImport(String importLocation, boolean absoluteLocation)
	{
		Import node = getImportList().getImport(importLocation, absoluteLocation);
		
		if (node != null)
		{
			node.markUsed();
		}
		
		return node;
	}
	
	/**
	 * Get whether or not the given location is an external C import.
	 * 
	 * @return Whether or not the given location is an external C import.
	 */
	public boolean isExternalImport(String importLocation)
	{
		return getImportList().isExternal(importLocation);
	}
	
	/**
	 * Get whether or not the FileDeclaration contains the ClassDeclaration with
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
	 * @return Whether or not the FileDeclaration contains the ClassDeclaration
	 * 		with the specified name.
	 */
	public boolean containsClass(String className)
	{
		return getClassDeclaration(className) != null;
	}
	
	/**
	 * Get the FileDeclaration's ClassDeclaration with the specified name.<br>
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
	 * ClassDeclaration for the "<code>age</code>" int field.
	 * 
	 * @param className The name of the class to search for.
	 * @return The ClassDeclaration for the class, if it exists.
	 */
	public ClassDeclaration getClassDeclaration(String className)
	{
		ClassDeclaration clazz = getClassDeclaration();
		
		if (clazz != null && clazz.getName().equals(className))
		{
			return clazz;
		}
		
		return null;
	}
	
	public Package getPackage()
	{
		if (getNumChildren() > getNumDecodedChildren() && getChild(getNumDecodedChildren()) instanceof Package)
		{
			return (Package)getChild(getNumDecodedChildren());
		}
		
		return null;
	}
	
	private void setPackage(Package n)
	{
		if (getPackage() != null)
		{
			removeChild(getNumDecodedChildren());
		}
		
		addChild(getNumDecodedChildren(), n);
	}
	
	/**
	 * Get the ClassDeclaration of the class that is contained within the file.
	 * 
	 * @return The ClassDeclaration instance from the file.
	 */
	public ClassDeclaration getClassDeclaration()
	{
		if (getNumChildren() > getNumDecodedChildren())
		{
			int offset = 1;
			
			if (getPackage() != null)
			{
				if (getNumChildren() <= getNumDecodedChildren() + 1)
				{
					SyntaxMessage.error("Missing class declaration", this);
				}
				
				offset++;
			}
			
			return (ClassDeclaration)getChild(super.getNumDefaultChildren() + offset);
		}
		
		return null;
	}
	
	/**
	 * Get the File that this FileDeclaration represents.
	 * 
	 * @return The File that this FileDeclaration represents.
	 */
	public File getFile()
	{
		return file;
	}
	
	/**
	 * Get the name of the file without the extension.<br>
	 * <br>
	 * For example: A getName() call for a FileDeclaration of Test.nova would
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
	 * For example: A generateCHeaderName() call for a FileDeclaration of Test.nova
	 * would return "nova_Test.h"
	 * 
	 * @return The name of the file output as a C header file.
	 */
	public String generateCHeaderName()
	{
		return getPackage().generateCHeaderLocation() + "/" + getClassDeclaration().generateCSourceName() + ".h";
	}
	
	/**
	 * Get the name of the file that will be output as a C source file.<br>
	 * <br>
	 * For example: A generateCSourceName() call for a FileDeclaration of Test.nova
	 * would return "nova_Test.c"
	 * 
	 * @return The name of the file output as a C source file.
	 */
	public String generateCSourceName()
	{
		return getPackage().generateCHeaderLocation() + "/" + getClassDeclaration().generateCSourceName() + ".c";
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#validate(int)
	 */
	@Override
	public Node validate(int phase)
	{
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			validateImports();
		}
		
		return this;
	}
	
	private void validateImports()
	{
		for (int i = getImportList().getNumChildren() - 1; i >= 0; i--)
		{
			Import node = (Import)getImportList().getChild(i);
			
			if (!node.isUsed() || node.getLocation().equals(getName()))
			{
				getImportList().removeChild(i);
			}
		}
	}
	
	/**
	 * Get the ImportList that contains all of the imports used within
	 * the file.
	 * 
	 * @return The ImportList instance.
	 */
	public ImportList getImportList()
	{
		return (ImportList)getChild(0);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#addChild(Node)
	 */
	@Override
	public void addChild(Node child)
	{
		if (child instanceof Package)
		{
			if (!getPackage().isDefaultPackage())
			{
				SyntaxMessage.error("Package statement must be the first statement in the file", child);
			}
			
			setPackage((Package)child);
		}
		else if (child instanceof Import)
		{
			getImportList().addChild(child);
		}
		else if (child instanceof ClassDeclaration)
		{
			super.addChild(child);
		}
		else
		{
			SyntaxMessage.error("Unexpected statement", child);
		}
	}

	/**
	 * @see net.fathomsoft.nova.tree.Identifier#generateCHeader(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		if (header == null)
		{
			String definitionName = "FILE_" + getName() + "_" + Nova.LANGUAGE_NAME.toUpperCase();
			
			builder.append("#pragma once").append('\n');
			builder.append("#ifndef ").append(definitionName).append('\n');
			builder.append("#define ").append(definitionName).append("\n\n");
			
			generateDummyTypes(builder).append('\n');
			
			ImportList imports = getImportList();
			
			imports.generateCHeader(builder);
			
			builder.append('\n');
			
			for (int i = 0; i < getNumChildren(); i++)
			{
				Node child = getChild(i);
				
				if (child != imports)
				{
					child.generateCHeader(builder);
				}
			}
			
			builder.append('\n').append("#endif");
			
			header = builder;
		}
		
		return header;
	}

	/**
	 * @see net.fathomsoft.nova.tree.Identifier#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		if (source == null)
		{
			builder.append("#include <precompiled.h>\n");
			getImportList().generateCSource(builder).append('\n');
			
			generateClosureDefinitions(builder).append('\n');
			
			for (int i = 0; i < getNumChildren(); i++)
			{
				Node child = getChild(i);
				
				if (child != getImportList())
				{
					child.generateCSource(builder);
				}
			}
			
			source = builder;
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
		this.header = new StringBuilder(header);
	}
	
	/**
	 * Set the source output String for the File.
	 * 
	 * @param source The source output String.
	 */
	public void setSource(String source)
	{
		this.source = new StringBuilder(source);
	}
	
	/**
	 * Add all of the default imports that each file must include. The
	 * default imports are included within the {@link #DEFAULT_IMPORTS}
	 * array.
	 */
	private void addDefaultImports()
	{
		for (String importLoc : DEFAULT_IMPORTS)
		{
			addImport(importLoc).markUsed();
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
	private StringBuilder generateDummyTypes(StringBuilder builder)
	{
//		builder.append("typedef struct ExceptionData ExceptionData;\n");
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			if (child instanceof ClassDeclaration)
			{
				ClassDeclaration clazz = (ClassDeclaration)child;
				
				builder.append("typedef struct ").append(clazz.generateCSourceName()).append(' ').append(clazz.generateCSourceName()).append(';').append('\n');
			}
		}
		
//		ImportList imports = getImportList();
//		
//		for (int i = 0; i < imports.getNumChildren(); i++)
//		{
//			Import node = (Import)imports.getChild(i);
//			
//			if (!node.isExternal())
//			{
//				String name = node.getLocationNode().getName();
//				
//				builder.append("typedef struct ").append(name).append(' ').append(name).append(';').append('\n');
//			}
//		}
		
		return builder;
	}
	
	/**
	 * Generate the type definitions for the closures used within the
	 * file.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @return The StringBuilder with the appended data.
	 */
	private StringBuilder generateClosureDefinitions(StringBuilder builder)
	{
		for (ClosureDeclaration closure : closures)
		{
			closure.generateCClosureDefinition(builder);
		}
		
		return builder;
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
		
		setHeader(SyntaxUtils.formatText(header.toString()));
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
		
		setSource(SyntaxUtils.formatText(source.toString()));
	}
	
	public static FileDeclaration generateTemporaryFile(Node parent, Location locationIn)
	{
		FileDeclaration node = new FileDeclaration(parent, locationIn, new File("Temp"));
		
		return node;
	}
	
	public static FileDeclaration generateTemporaryHierarchy(Nova controller)
	{
		Program p = Program.generateTemporaryHierarchy(controller);
		
		FileDeclaration file = generateTemporaryFile(p, Location.INVALID);
		p.addChild(file);
		
		return file;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public FileDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		FileDeclaration node = new FileDeclaration(temporaryParent, locationIn, null);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link FileDeclaration} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public FileDeclaration cloneTo(FileDeclaration node)
	{
		super.cloneTo(node);
		
		node.file     = new File(file.getAbsolutePath());
		node.closures = new ArrayList<ClosureDeclaration>();
		
		for (ClosureDeclaration c : closures)
		{
			node.closures.add(c);
		}
		
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
	
	/**
	 * Test the FileDeclaration class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
}
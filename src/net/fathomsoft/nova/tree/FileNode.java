package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Class used to organize the Files that are fed to the compiler.
 * Each file has a file name that is stored in the name field variable
 * of this class.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Feb 18, 2014 at 8:57:00 PM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class FileNode extends IdentifierNode
{
	//TODO: package name here?
	
	private String	header, source;
	
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
			"stdlib.h",
			"CClass.h",
			"ExceptionHandler.h",
			"Fathom.h",
			"ExceptionData",
			"Object",
			"String",
			"Math",
			"IO",
			"Integer",
			"DivideByZeroException"
		};
	}
	
	/**
	 * Instantiate and initialize the default values.
	 * 
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode)
	 */
	public FileNode(TreeNode temporaryParent)
	{
		super(temporaryParent);
		
		ImportListNode imports = new ImportListNode(this);
		
		super.addChild(imports);
		
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
	public ImportNode getImport(String importLocation)
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
		return getClass(className) != null;
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
	public ClassNode getClass(String className)
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != getImportListNode())
			{
				ClassNode clazz = (ClassNode)child;
				
				if (clazz.getName().equals(className))
				{
					return clazz;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Make sure that the Class declarations are valid.
	 */
	public void validateClasses()
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != getImportListNode())
			{
				ClassNode clazz = (ClassNode)child;
				
				clazz.validateDeclaration();
			}
		}
	}
	
	/**
	 * Make sure that the Field declarations are valid.
	 */
	public void validateFields()
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != getImportListNode())
			{
				ClassNode clazz = (ClassNode)child;
				
				clazz.validateFields();
			}
		}
	}
	
	/**
	 * Make sure that the Method declarations are valid.
	 */
	public void validateMethods()
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != getImportListNode())
			{
				ClassNode clazz = (ClassNode)child;
				
				clazz.validateMethods();
			}
		}
	}
	
	/**
	 * Get the ImportListNode that contains all of the imports used within
	 * the file.
	 * 
	 * @return The ImportListNode instance.
	 */
	public ImportListNode getImportListNode()
	{
		return (ImportListNode)getChild(0);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.IdentifierNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		return "";
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
		if (child instanceof ImportNode)
		{
			getImportListNode().addChild(child);
		}
		else if (child instanceof ClassNode)
		{
			super.addChild(child);
		}
		else
		{
			SyntaxMessage.error("Unexpected statement", getFileNode(), child.getLocationIn(), getController());
			
			//super.addChild(child);
		}
	}

	/**
	 * @see net.fathomsoft.nova.tree.IdentifierNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		if (header == null)
		{
			StringBuilder builder = new StringBuilder();
			
			String definitionName = "FILE_" + getName() + "_" + Nova.LANGUAGE_NAME.toUpperCase();
			
			builder.append("#ifndef ").append(definitionName).append('\n');
			builder.append("#define ").append(definitionName).append("\n\n");
			
			builder.append(generateDummyTypes()).append('\n');
			
			ImportListNode imports = getImportListNode();
			
			builder.append(imports.generateCHeader());
			
			for (int i = 0; i < getChildren().size(); i++)
			{
				TreeNode child = getChild(i);
				
				if (child != imports)
				{
					builder.append(child.generateCHeader());
				}
			}
			
			builder.append("#endif");
			
			header = builder.toString();
		}
		
		return header;
	}

	/**
	 * @see net.fathomsoft.nova.tree.IdentifierNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		if (source == null)
		{
			StringBuilder builder = new StringBuilder();
			
			ImportNode thisImport = new ImportNode(this);
			thisImport.setImportLocation(getName());
			
			builder.append(thisImport.generateCSource());
			
			if (getImportListNode().getChildren().size() <= 0)
			{
				builder.append('\n');
			}
			
			for (int i = 0; i < getChildren().size(); i++)
			{
				TreeNode child = getChild(i);
				
				builder.append(child.generateCSource());
			}
			
			source = builder.toString();
		}
		
		return source;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return null;
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
			ImportNode importNode = new ImportNode(this);
			
			if (importLoc.endsWith(".h"))
			{
				importLoc = importLoc.substring(0, importLoc.length() - 2);
				
				importNode.setExternal(true);
			}
			
			importNode.setImportLocation(importLoc);
			
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
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child instanceof ClassNode)
			{
				ClassNode clazz = (ClassNode)child;
				
				builder.append("typedef struct ").append(clazz.getName()).append(' ').append(clazz.getName()).append(';').append('\n');
			}
		}
		
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
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public FileNode clone(TreeNode temporaryParent)
	{
		FileNode node = new FileNode(temporaryParent);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given FileNde with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public FileNode cloneTo(FileNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
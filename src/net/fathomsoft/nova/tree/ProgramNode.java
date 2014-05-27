package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.util.Location;

/**
 * TreeNode extension that represents a whole Nova program. The
 * purpose of this Node is to keep track of each FileNode within
 * a compiled program and contain methods to manipulate the
 * FileNodes.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2 Apr 14, 2014 at 11:52:33 PM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class ProgramNode extends TreeNode
{
	private Nova	controller;
	
	/**
	 * Instantiate and initialize a ProgramNode that contains a reference
	 * to the compiler's controller.
	 * 
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode)
	 * 
	 * @param controller The controller of the compiler.
	 */
	public ProgramNode(Nova controller)
	{
		super(null, null);
		
		this.controller = controller;
	}
	
	/**
	 * Override addChild(TreeNode) method to make it synchronized. Needs
	 * to be synchronized so that the threads dont try to write their
	 * file nodes to the ProgramNode at the same time and end up creating
	 * empty spaces in the tree.
	 * 
	 * @see net.fathomsoft.nova.tree.TreeNode#addChild(net.fathomsoft.nova.tree.TreeNode)
	 */
	public synchronized void addChild(TreeNode child)
	{
		super.addChild(child);
	}
	
	/**
	 * Get the compiler's controller. The controller is used for
	 * logging, error output, and other compiler options.
	 * 
	 * @return The compiler's controller instance.
	 */
	public Nova getController()
	{
		return controller;
	}
	
	/**
	 * Make sure that the Class declarations are valid.
	 */
	public void validateClasses()
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			FileNode node = (FileNode)getChild(i);
			
			node.validateClasses();
		}
	}
	
	/**
	 * Make sure that the Field declarations are valid.
	 */
	public void validateFields()
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			FileNode node = (FileNode)getChild(i);
			
			node.validateFields();
		}
	}
	
	/**
	 * Make sure that the Method declarations are valid.
	 */
	public void validateMethods()
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			FileNode node = (FileNode)getChild(i);
			
			node.validateMethods();
		}
	}
	
	/**
	 * Get the ProgramNode's ClassNode with the specified name.<br>
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
			FileNode  node  = (FileNode)getChild(i);
			
			ClassNode clazz = node.getClass(className);
			
			if (clazz != null)
			{
				return clazz;
			}
		}
		
		return null;
	}
	
	/**
	 * Get the ProgramNode's FileNode with the specified name.
	 * 
	 * @param filename The name of the file to search for.
	 * @return The FileNode for the file, if it exists.
	 */
	public FileNode getFile(String filename)
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			FileNode node = (FileNode)getChild(i);
			
			if (node.getName().equals(filename))
			{
				return node;
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateJavaSource());
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateCHeader());
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateCSource());
		}
		
		return builder.toString();
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
	 * Format the C Header output to follow syntactical rules.
	 */
	public void formatCHeaderOutput()
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child instanceof FileNode)
			{
				FileNode fileNode = (FileNode)child;
				
				fileNode.formatCHeaderOutput();
			}
		}
	}
	
	/**
	 * Format the C Source output to follow syntactical rules.
	 */
	public void formatCSourceOutput()
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child instanceof FileNode)
			{
				FileNode fileNode = (FileNode)child;
				
				fileNode.formatCSourceOutput();
			}
		}
	}
	
	/**
	 * Format the C Header and Source output to follow syntactical rules.
	 */
	public void formatCOutput()
	{
		formatCHeaderOutput();
		formatCSourceOutput();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public ProgramNode clone(TreeNode temporaryParent, Location locationIn)
	{
		ProgramNode node = new ProgramNode(controller);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ProgramNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ProgramNode cloneTo(ProgramNode node)
	{
		super.cloneTo(node);
		
		node.controller = controller;
		
		return node;
	}
}
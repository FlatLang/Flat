package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.util.Location;

/**
 * Node extension that represents a whole Nova program. The
 * purpose of this Node is to keep track of each FileDeclaration within
 * a compiled program and contain methods to manipulate the
 * FileDeclarations.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2 Apr 14, 2014 at 11:52:33 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class Program extends Node
{
	private Nova	controller;
	
	/**
	 * Instantiate and initialize a Program that contains a reference
	 * to the compiler's controller.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 * 
	 * @param controller The controller of the compiler.
	 */
	public Program(Nova controller)
	{
		super(null, null);
		
		this.controller = controller;
	}
	
	/**
	 * Override addChild(Node) method to make it synchronized. Needs
	 * to be synchronized so that the threads dont try to write their
	 * file nodes to the Program at the same time and end up creating
	 * empty spaces in the tree.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#addChild(net.fathomsoft.nova.tree.Node)
	 */
	public synchronized void addChild(Node child)
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
	 * 
	 * @param phase The phase that the node is being validated in.
	 */
	public void validateClasses(int phase)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			FileDeclaration node = (FileDeclaration)getChild(i);
			
			node.validateClasses(phase);
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
			FileDeclaration node = (FileDeclaration)getChild(i);
			
			node.validateFields(phase);
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
			FileDeclaration node = (FileDeclaration)getChild(i);
			
			node.validateMethods(phase);
		}
	}
	
	/**
	 * Get the Program's ClassDeclaration with the specified name.<br>
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
	 * ClassDeclaration for the "<code>Person</code>" class.
	 * 
	 * @param className The name of the class to search for.
	 * @return The ClassDeclaration for the class, if it exists.
	 */
	public ClassDeclaration getClassDeclaration(String className)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			FileDeclaration  node  = (FileDeclaration)getChild(i);
			
			ClassDeclaration clazz = node.getClassDeclaration(className);
			
			if (clazz != null)
			{
				return clazz;
			}
		}
		
		return null;
	}
	
	/**
	 * Get the Program's FileDeclaration with the specified name.
	 * 
	 * @param filename The name of the file to search for.
	 * @return The FileDeclaration for the file, if it exists.
	 */
	public FileDeclaration getFile(String filename)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			FileDeclaration node = (FileDeclaration)getChild(i);
			
			if (node.getName().equals(filename))
			{
				return node;
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			builder.append(getChild(i).generateCHeader());
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			builder.append(getChild(i).generateCSource());
		}
		
		return builder.toString();
	}
	
	/**
	 * Format the C Header output to follow syntactical rules.
	 */
	public void formatCHeaderOutput()
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			if (child instanceof FileDeclaration)
			{
				FileDeclaration fileDeclaration = (FileDeclaration)child;
				
				fileDeclaration.formatCHeaderOutput();
			}
		}
	}
	
	/**
	 * Format the C Source output to follow syntactical rules.
	 */
	public void formatCSourceOutput()
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			if (child instanceof FileDeclaration)
			{
				FileDeclaration fileDeclaration = (FileDeclaration)child;
				
				fileDeclaration.formatCSourceOutput();
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
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Program clone(Node temporaryParent, Location locationIn)
	{
		Program node = new Program(controller);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given Program with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Program cloneTo(Program node)
	{
		super.cloneTo(node);
		
		node.controller = controller;
		
		return node;
	}
}
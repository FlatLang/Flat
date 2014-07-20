package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Location;

/**
 * Node extension that keeps track of a set of arguments that
 * are used in an abstract manner.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 10, 2014 at 3:12:54 AM
 * @version	v0.2.14 Jul 19, 2014 at 7:33:13 PM
 */
public class ArgumentList extends Node
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ArgumentList(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			if (i > 0)
			{
				builder.append(", ");
			}
			
			getChild(i).generateNovaInput(builder);
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return generateCSourceFragment(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			child.generateCSourceFragment(builder);
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public ArgumentList clone(Node temporaryParent, Location locationIn)
	{
		ArgumentList node = new ArgumentList(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ArgumentList with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ArgumentList cloneTo(ArgumentList node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the ArgumentListNode class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test()
	{
		
		
		return null;
	}
}
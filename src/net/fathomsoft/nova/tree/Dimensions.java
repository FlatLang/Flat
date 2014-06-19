package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Location;

/**
 * Node extension that contains all of the dimension attributes
 * for an array declaration.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.5 May 19, 2014 at 12:09:41 AM
 * @version	v0.2.14 Jun 18, 2014 at 10:11:40 PM
 */
public class Dimensions extends Node
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Dimensions(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
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
			
			builder.append('[').append(child.generateCSourceFragment()).append(']');
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(boolean)
	 */
	@Override
	public String generateNovaInput(boolean outputChildren)
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			builder.append('[').append(child.generateNovaInput()).append(']');
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Dimensions clone(Node temporaryParent, Location locationIn)
	{
		Dimensions node = new Dimensions(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given Dimensions with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Dimensions cloneTo(Dimensions node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
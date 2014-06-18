package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Location;

/**
 * Node extension that represents the initialization section of the
 * for loop. For instance: "for (int i = 0; i < 10; i++)" the first
 * section containing "int i = 0" is the initialization section.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:01:46 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class LoopInitialization extends Node
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public LoopInitialization(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public LoopInitialization clone(Node temporaryParent, Location locationIn)
	{
		LoopInitialization node = new LoopInitialization(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given LoopLoopInitialization with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public LoopInitialization cloneTo(LoopInitialization node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
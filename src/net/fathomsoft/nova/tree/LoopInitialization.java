package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Location;

/**
 * Node extension that represents the initialization section of the
 * for loop. For instance: "for (int i = 0; i < 10; i++)" the first
 * section containing "int i = 0" is the initialization section.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:01:46 PM
 * @version	v0.2.19 Jul 26, 2014 at 12:30:24 AM
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
	 * Fill the given {@link LoopLoopInitialization} with the data that is in the
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
	
	/**
	 * Test the LoopInitialization class type to make sure everything
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
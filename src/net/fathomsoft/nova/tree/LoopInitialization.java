package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * Node extension that represents the initialization section of the
 * for loop. For instance: "for (int i = 0; i &lt; 10; i++)" the first
 * section containing "int i = 0" is the initialization section.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:01:46 PM
 * @version	v0.2.26 Aug 6, 2014 at 2:48:50 PM
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
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public LoopInitialization clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		LoopInitialization node = new LoopInitialization(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public LoopInitialization cloneTo(LoopInitialization node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link LoopInitialization} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public LoopInitialization cloneTo(LoopInitialization node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the LoopInitialization class type to make sure everything
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
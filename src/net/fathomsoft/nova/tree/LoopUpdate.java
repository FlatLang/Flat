package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Location;

/**
 * TreeNode extension that represents the update section of the for loop.
 * For instance: "for (int i = 0; i < 10; i++)" the last section
 * containing "i++" is the update section.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:03:37 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class LoopUpdate extends Node
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#TreeNode(Node, Location)
	 */
	public LoopUpdate(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public LoopUpdate clone(Node temporaryParent, Location locationIn)
	{
		LoopUpdate node = new LoopUpdate(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given LoopUpdateNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public LoopUpdate cloneTo(LoopUpdate node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
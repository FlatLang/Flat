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
public class LoopUpdateNode extends TreeNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public LoopUpdateNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode, Location)
	 */
	@Override
	public LoopUpdateNode clone(TreeNode temporaryParent, Location locationIn)
	{
		LoopUpdateNode node = new LoopUpdateNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given LoopUpdateNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public LoopUpdateNode cloneTo(LoopUpdateNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
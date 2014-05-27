package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Location;

/**
 * TreeNode extension that represents the initialization section of the
 * for loop. For instance: "for (int i = 0; i < 10; i++)" the first
 * section containing "int i = 0" is the initialization section.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:01:46 PM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class LoopInitializationNode extends TreeNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode)
	 */
	public LoopInitializationNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public LoopInitializationNode clone(TreeNode temporaryParent, Location locationIn)
	{
		LoopInitializationNode node = new LoopInitializationNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given LoopInitializationNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public LoopInitializationNode cloneTo(LoopInitializationNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
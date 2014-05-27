package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Location;

/**
 * TreeNode extension that represents a condition. Conditions can be
 * found in if/else if statements, for/while loops, etc.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:57:40 PM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class ConditionNode extends TreeNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public ConditionNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public ConditionNode clone(TreeNode temporaryParent, Location locationIn)
	{
		ConditionNode node = new ConditionNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ConditionNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ConditionNode cloneTo(ConditionNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
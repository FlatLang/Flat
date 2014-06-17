package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Location;

/**
 * TreeNode extension that represents a condition. Conditions can be
 * found in if/else if statements, for/while loops, etc.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:57:40 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class Condition extends Node
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#TreeNode(Node, Location)
	 */
	public Condition(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Condition clone(Node temporaryParent, Location locationIn)
	{
		Condition node = new Condition(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ConditionNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Condition cloneTo(Condition node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
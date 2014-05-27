package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Location;

/**
 * TreeNode extension that contains all of the dimension attributes
 * for an array declaration.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.5 May 19, 2014 at 12:09:41 AM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class DimensionsNode extends TreeNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public DimensionsNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append('[').append(child.generateCSourceFragment()).append(']');
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public DimensionsNode clone(TreeNode temporaryParent, Location locationIn)
	{
		DimensionsNode node = new DimensionsNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given DimensionsNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public DimensionsNode cloneTo(DimensionsNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
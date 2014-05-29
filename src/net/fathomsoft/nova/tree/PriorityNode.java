package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Location;

/**
 * ValueNode extension that represents an operation within parentheses.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.10 May 29, 2014 at 1:50:25 PM
 * @version	v0.2.10 May 29, 2014 at 5:14:07 PM
 */
public class PriorityNode extends ValueNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public PriorityNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		return generateCSourceFragment();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return super.generateChildrenCSourceFragment(false);
	}
	
	/**
	 * Decode the given statement into a PriorityNode instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>externalHeaderName</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		PriorityNode instance.
	 * @param location The location of the statement in the source code.
	 * @return The generated node, if it was possible to translated it
	 * 		into a PriorityNode.
	 */
	public static PriorityNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		if (statement.charAt(0) == '(')
		{
			PriorityNode n = new PriorityNode(parent, location);
			
			if (statement.charAt(statement.length() - 1) != ')')
			{
				SyntaxMessage.error("Missing ending parenthesis", n);
			}
			
			
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public PriorityNode clone(TreeNode temporaryParent, Location locationIn)
	{
		PriorityNode node = new PriorityNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given PriorityNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public PriorityNode cloneTo(PriorityNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
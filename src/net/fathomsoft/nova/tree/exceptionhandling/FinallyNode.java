package net.fathomsoft.nova.tree.exceptionhandling;

import net.fathomsoft.nova.tree.TreeNode;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * ExceptionHandlingNode extension that represents the declaration of a
 * finally node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 22, 2014 at 4:02:21 PM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class FinallyNode extends ExceptionHandlingNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode)
	 */
	public FinallyNode(TreeNode temporaryParent)
	{
		super(temporaryParent);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("FINALLY").append('\n');
		builder.append('{').append('\n');
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append(child.generateCSource());
		}
		
		builder.append('}').append('\n');
		builder.append("END_TRY;").append('\n');
		
		return builder.toString();
	}
	
	/**
	 * Decode the given statement into a FinallyNode instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * The only correct input is "finally"
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		FinallyNode instance.
	 * @param location The location of the statement in the source code.
	 * @return The generated node, if it was possible to translated it
	 * 		into a FinallyNode.
	 */
	public static FinallyNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		if (Regex.matches(statement, 0, Patterns.FINALLY))
		{
			FinallyNode n = new FinallyNode(parent);
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public FinallyNode clone(TreeNode temporaryParent)
	{
		FinallyNode node = new FinallyNode(temporaryParent);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given FinallyNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public FinallyNode cloneTo(FinallyNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
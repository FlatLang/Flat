package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * LoopNode extension that represents the declaration of a "while loop"
 * node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:55:59 PM
 * @version	v0.2.11 May 31, 2014 at 1:19:11 PM
 */
public class WhileLoopNode extends LoopNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public WhileLoopNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get the TreeNode that describes the condition section of the while
	 * loop. For instance: "while (i < 10)" the contents between the
	 * parenthesis is the condition.
	 * 
	 * @return The TreeNode instance that describes the condition section
	 * 		of the while loop.
	 */
	public TreeNode getConditionNode()
	{
		return getChild(1);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
		if (getChildren().size() <= 1)
		{
			addChild(1, child);
		}
		else
		{
			super.addChild(child);
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		TreeNode condition    = getConditionNode();
		
		builder.append("while (").append(condition.generateCSource()).append(')').append('\n');
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != getConditionNode())
			{
				builder.append(child.generateCSource());
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * Decode the given statement into a WhileLoopNode instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>while (currentNode != null)</li>
	 * 	<li>while (true)</li>
	 * 	<li>while (number.isEven())</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		WhileLoopNode instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a WhileLoopNode.
	 */
	public static WhileLoopNode decodeStatement(TreeNode parent, String statement, Location location, boolean require)
	{
		if (Regex.matches(statement, 0, Patterns.PRE_WHILE))
		{
			WhileLoopNode n = new WhileLoopNode(parent, location);
			
			Bounds bounds   = Regex.boundsOf(statement, Patterns.WHILE_CONTENTS);
			
			if (bounds.getStart() >= 0)
			{
				Location newLoc    = new Location();
				newLoc.setLineNumber(location.getLineNumber());
				newLoc.setBounds(location.getStart() + bounds.getStart(), location.getStart() + bounds.getEnd());
				
				String   contents  = statement.substring(bounds.getStart(), bounds.getEnd());
				
				TreeNode condition = BinaryOperatorNode.decodeStatement(parent, contents, newLoc, require);
				
				if (condition == null)
				{
					if (condition == null)
					{
						condition = getExistingNode(parent, contents);
					}
					if (condition == null && SyntaxUtils.isLiteral(contents))
					{
						LiteralNode literal = LiteralNode.decodeStatement(n, contents, newLoc, require);
						
						condition = literal;
					}
					if (condition == null)
					{
//						SyntaxMessage.error("Could not decode conditional statement '" + condition + "'", parent.getFileNode(), newLoc, parent.getController());
						
						return null;
					}
				}
				
				n.addChild(condition);
					
				return n;
			}
			else
			{
				SyntaxMessage.error("While loop missing condition", n);
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public WhileLoopNode clone(TreeNode temporaryParent, Location locationIn)
	{
		WhileLoopNode node = new WhileLoopNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given WhileLoopNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public WhileLoopNode cloneTo(WhileLoopNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}

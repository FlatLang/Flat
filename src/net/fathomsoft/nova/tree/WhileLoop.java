package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * LoopNode extension that represents the declaration of a "while loop"
 * node type. See {@link #decodeStatement(Node, String, Location, boolean, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:55:59 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class WhileLoop extends Loop
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#TreeNode(Node, Location)
	 */
	public WhileLoop(Node temporaryParent, Location locationIn)
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
	public Node getConditionNode()
	{
		return getChild(1);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		Node condition    = getConditionNode();
		
		builder.append("while (").append(condition.generateCSource()).append(')').append('\n');
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
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
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a WhileLoopNode.
	 */
	public static WhileLoop decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		if (Regex.matches(statement, 0, Patterns.PRE_WHILE))
		{
			WhileLoop n = new WhileLoop(parent, location);
			
			Bounds bounds   = Regex.boundsOf(statement, Patterns.WHILE_CONTENTS);
			
			if (bounds.getStart() >= 0)
			{
				Location newLoc    = new Location();
				newLoc.setLineNumber(location.getLineNumber());
				newLoc.setBounds(location.getStart() + bounds.getStart(), location.getStart() + bounds.getEnd());
				
				String   contents  = statement.substring(bounds.getStart(), bounds.getEnd());
				
				Node condition = BinaryOperator.decodeStatement(parent, contents, newLoc, require, false);
				
				if (condition == null)
				{
					if (condition == null)
					{
						condition = SyntaxTree.getExistingNode(parent, contents);
					}
					if (condition == null && SyntaxUtils.isLiteral(contents))
					{
						Literal literal = Literal.decodeStatement(n, contents, newLoc, require, false);
						
						condition = literal;
					}
					if (condition == null)
					{
//						SyntaxMessage.error("Could not decode conditional statement '" + condition + "'", parent.getFileNode(), newLoc, parent.getController());
						
						return null;
					}
				}
				
				n.addChild(condition, n);
				
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
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public WhileLoop clone(Node temporaryParent, Location locationIn)
	{
		WhileLoop node = new WhileLoop(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given WhileLoopNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public WhileLoop cloneTo(WhileLoop node)
	{
		super.cloneTo(node);
		
		return node;
	}
}

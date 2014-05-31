package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * TreeNode extension that represents the declaration of an "if statement"
 * node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:57:13 PM
 * @version	v0.2.11 May 31, 2014 at 1:19:11 PM
 */
public class IfStatementNode extends TreeNode
{
	/**
	 * Instantiate a new IfStatementNode and initialize the default
	 * values.
	 */
	public IfStatementNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		ArgumentListNode condition = new ArgumentListNode(this, locationIn);
		ScopeNode        scopeNode = new ScopeNode(this, locationIn);
		
		addChild(condition);
		addChild(scopeNode);
	}
	
	/**
	 * Get the ArgumentListNode that contains the condition for the if
	 * statement.
	 * 
	 * @return The ArgumentListNode instance.
	 */
	public ArgumentListNode getCondition()
	{
		return (ArgumentListNode)getChild(0);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#getScopeNode()
	 */
	@Override
	public ScopeNode getScopeNode()
	{
		return (ScopeNode)getChild(1);
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
		if (child instanceof ScopeNode || child instanceof ArgumentListNode)
		{
			super.addChild(child);
		}
		else
		{
			getScopeNode().addChild(child);
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("if (");
		
		ArgumentListNode condition = getCondition();
		
		for (int i = 0; i < condition.getChildren().size(); i++)
		{
			TreeNode child = condition.getChild(i);
			
			builder.append(child.generateCSourceFragment());
		}
		
		builder.append(')').append('\n');
//		builder.append('{').append('\n');
//		
//		for (int i = 0; i < getChildren().size(); i++)
//		{
//			TreeNode child = getChild(i);
//			
//			if (child != getCondition())
//			{
//				builder.append(child.generateCSourceOutput());
//			}
//		}
//		
//		builder.append('}').append('\n');
		builder.append(getScopeNode().generateCSource());
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return null;
	}
	
	/**
	 * Decode the given statement into a IfStatementNode instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>if (index >= array.size())</li>
	 * 	<li>if (getParent().isAlive())</li>
	 * 	<li>if (!person.canWalk() && !person.isVegetable())</li>
	 * 	<li>if ((age + 2 >= 21 && gender == "male") || gender == "female")</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		IfStatementNode instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a IfStatementNode.
	 */
	public static IfStatementNode decodeStatement(TreeNode parent, String statement, Location location, boolean require)
	{
		if (Regex.matches(statement, 0, Patterns.PRE_IF))
		{
			IfStatementNode n = new IfStatementNode(parent, location);
			
			Bounds bounds     = Regex.boundsOf(statement, Patterns.IF_CONTENTS);
			
			if (bounds.getStart() >= 0)
			{
				String contents = statement.substring(bounds.getStart(), bounds.getEnd());
				
				Location newLoc = new Location();
				newLoc.setLineNumber(location.getLineNumber());
				newLoc.setBounds(location.getStart() + bounds.getStart(), location.getStart() + bounds.getEnd());
				
				TreeNode condition = BinaryOperatorNode.decodeStatement(parent, contents, newLoc, require);
				

				if (condition == null)
				{
					condition = getExistingNode(parent, contents);
					
//					SyntaxMessage.error("Could not decode condition", parent.getFileNode(), newLoc, parent.getController());
				}
				if (condition == null)
				{
					return null;
				}
				
				n.getCondition().addChild(condition);
				
				return n;
			}
			else
			{
				SyntaxMessage.error("If statement missing condition", n);
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public IfStatementNode clone(TreeNode temporaryParent, Location locationIn)
	{
		IfStatementNode node = new IfStatementNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given IfStatementNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public IfStatementNode cloneTo(IfStatementNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}

package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * TreeNode extension that represents the declaration of an "else
 * statement" node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:57:13 PM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class ElseStatementNode extends TreeNode
{
	/**
	 * Instantiate a new ElseStatementNode and initialize the default
	 * values.
	 * 
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public ElseStatementNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		ScopeNode scopeNode = new ScopeNode(this, locationIn);
		
		addChild(scopeNode);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#getScopeNode()
	 */
	@Override
	public ScopeNode getScopeNode()
	{
		return (ScopeNode)getChild(0);
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
		if (child instanceof ScopeNode || (getChildren().size() <= 1 && child instanceof IfStatementNode))
		{
			super.addChild(child);
		}
		else
		{
			getScopeNode().addChild(child);
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		return null;
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("else");
		
		if (getChildren().size() == 2)
		{
			TreeNode child = getChild(1);
			
			if (child instanceof IfStatementNode)
			{
				builder.append(' ').append(child.generateCSource());
				
				// Delete the new line at the end.
				builder.deleteCharAt(builder.length() - 1);
			}
		}
		
		builder.append('\n');
	
		builder.append(getScopeNode().generateCSource());
		
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
	 * Decode the given statement into a ElseStatementNode instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>else</li>
	 * 	<li>else if (!person.canWalk() && !person.isVegetable())</li>
	 * 	<li>else doSomethingInOneLine()</li>
	 * 	<li>else counter++</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ElseStatementNode instance.
	 * @param location The location of the statement in the source code.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ElseStatementNode.
	 */
	public static ElseStatementNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		Bounds bounds = Regex.boundsOf(statement, Patterns.ELSE);
		
		if (bounds.getStart() == 0)
		{
			ElseStatementNode n  = new ElseStatementNode(parent, location);
			
			String   ending      = statement.substring(bounds.getEnd());
			
			Location newLocation = new Location(location);
			newLocation.setBounds(location.getStart() + bounds.getEnd(), location.getStart() + statement.length());
			
			if (ending.length() > 0)
			{
				TreeNode contents = TreeNode.decodeStatement(parent, ending, newLocation);
				
				if (contents != null)
				{
					n.addChild(contents);
				}
			}
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public ElseStatementNode clone(TreeNode temporaryParent, Location locationIn)
	{
		ElseStatementNode node = new ElseStatementNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given IfStatementNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ElseStatementNode cloneTo(ElseStatementNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}

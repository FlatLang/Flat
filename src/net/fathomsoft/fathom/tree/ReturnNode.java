package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:58:29 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:58:29 PM
 * @version	v
 */
public class ReturnNode extends TreeNode
{
	public ReturnNode()
	{
		
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("return ");
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append(child.generateJavaSourceOutput());
		}
		
		builder.append(';').append('\n');
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		return null;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("return");
		
		if (getChildren().size() > 0)
		{
			builder.append(' ');
		}
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child instanceof VariableNode)
			{
				builder.append(((VariableNode) child).generateVariableUseOutput());
			}
			else
			{
				builder.append(child.generateCSourceOutput());
			}
		}
		
		builder.append(';').append('\n');
		
		return builder.toString();
	}
	
	public static ReturnNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		ReturnNode n = null;
		
		if (Regex.indexOf(statement, Patterns.PRE_RETURN) == 0)
		{
			n = new ReturnNode();
			
			int returnStartIndex = Regex.indexOf(statement, Patterns.POST_RETURN);
			
			if (returnStartIndex >= 0)
			{
				statement = statement.substring(returnStartIndex);
				
				Location newLoc = new Location();
				newLoc.setLineNumber(location.getLineNumber());
				newLoc.setOffset(location.getOffset() + returnStartIndex);
				
				TreeNode child = BinaryOperatorNode.decodeStatement(parent, statement, newLoc);
				
				n.addChild(child);
			}
		}
		
		return n;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public ReturnNode clone()
	{
		ReturnNode clone = new ReturnNode();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			clone.addChild(child.clone());
		}
		
		return clone;
	}
}
package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.error.SyntaxError;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 10:00:11 PM
 * @since	v
 * @version	Jan 5, 2014 at 10:00:11 PM
 * @version	v
 */
public class UnaryOperatorNode extends TreeNode
{
	public UnaryOperatorNode()
	{
		
	}
	
	public boolean checkOperator()
	{
		if (getChildren().size() <= 0)
		{
			SyntaxError.outputNewError("Missing operator", getLocationIn());
			
			return false;
		}
		
		TreeNode node = null;
		
		for (int i = 0; i < getChildren().size() && node instanceof OperatorNode == false; i++)
		{
			node = getChild(i);
		}
		
		if (node instanceof OperatorNode)
		{
			return true;
		}
		else
		{
			SyntaxError.outputNewError("Missing operator", getLocationIn());
			
			return false;
		}
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		if (!checkOperator())
		{
			return null;
		}
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateJavaSourceOutput());
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
		return null;
	}
}
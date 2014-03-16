package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 10:34:30 PM
 * @since	v
 * @version	Jan 5, 2014 at 10:34:30 PM
 * @version	v
 */
public class LiteralNode extends TreeNode
{
	private String	value;
	
	public LiteralNode()
	{
		
	}
	
	public String getValue()
	{
		return value;
	}
	
	public void setValue(String value)
	{
		if (SyntaxUtils.isStringLiteral(value))
		{
			value = "new_String(" + value + ")";
		}
		
		this.value = value;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		return value;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		return value;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
	{
		return value;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public LiteralNode clone()
	{
		LiteralNode clone = new LiteralNode();
		clone.setValue(getValue());
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			clone.addChild(child.clone());
		}
		
		return clone;
	}
}
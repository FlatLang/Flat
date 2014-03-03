package net.fathomsoft.fathom.tree;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:19:40 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:19:40 PM
 * @version	v
 */
public class OperatorNode extends TreeNode
{
	private String	operator;
	
	public OperatorNode()
	{
		
	}
	
	public String getOperator()
	{
		return operator;
	}
	
	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		return operator;
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
		return operator;
	}
}
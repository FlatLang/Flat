package net.fathomsoft.nova.tree;

/**
 * TreeNode extension that represents an operator in an operation.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:19:40 PM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class OperatorNode extends TreeNode
{
	private String	operator;
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode)
	 */
	public OperatorNode(TreeNode temporaryParent)
	{
		super(temporaryParent);
	}
	
	/**
	 * Get the value of the operator. For example: '+', '*', '&&', etc.
	 * 
	 * @return The value of the operator.
	 */
	public String getOperator()
	{
		return operator;
	}
	
	/**
	 * Set the operator value. For example: '+', '*', '&&', etc.
	 * 
	 * @param operator The new value of the operator.
	 */
	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		return operator;
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
		return operator;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return operator;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public OperatorNode clone(TreeNode temporaryParent)
	{
		OperatorNode node = new OperatorNode(temporaryParent);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given OperatorNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public OperatorNode cloneTo(OperatorNode node)
	{
		super.cloneTo(node);
		
		node.setOperator(getOperator());
		
		return node;
	}
}
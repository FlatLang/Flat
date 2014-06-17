package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Location;

/**
 * Node extension that represents an operator in an operation.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:19:40 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class Operator extends Value
{
	private String	operator;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Operator(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
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
		if (operator.contains("=") || operator.equals("!") || operator.equals("&&") || operator.equals("||"))
		{
			setType("bool");
		}
		
		this.operator = operator;
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		return operator;
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		return operator;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return operator;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(boolean)
	 */
	public String generateNovaInput(boolean outputChildren)
	{
		return operator;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Operator clone(Node temporaryParent, Location locationIn)
	{
		Operator node = new Operator(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given OperatorNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Operator cloneTo(Operator node)
	{
		super.cloneTo(node);
		
		node.setOperator(getOperator());
		
		return node;
	}
}
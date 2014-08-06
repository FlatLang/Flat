package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Node extension that represents an operator in an operation.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:19:40 PM
 * @version	v0.2.26 Aug 6, 2014 at 2:48:50 PM
 */
public class Operator extends IValue
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
	 * Get the value of the operator. For example: '+', '*', '&amp;&amp;', etc.
	 * 
	 * @return The value of the operator.
	 */
	public String getOperator()
	{
		return operator;
	}
	
	/**
	 * Set the operator value. For example: '+', '*', '&amp;&amp;', etc.
	 * 
	 * @param operator The new value of the operator.
	 */
	public void setOperator(String operator)
	{
		this.operator = operator;
		
		updateType();
	}
	
	/**
	 * Get the left Value that the operator is being used with.
	 * 
	 * @return The left Value that the operator is being used with.
	 */
	public Value getLeftOperand()
	{
		return (Value)getParent().getChildBefore(this);
	}
	
	/**
	 * Get the right Value that the operator is being used with.
	 * 
	 * @return The right Value that the operator is being used with.
	 */
	public Value getRightOperand()
	{
		return (Value)getParent().getChildAfter(this);
	}
	
	/**
	 * Update the type that the operation returns.
	 */
	public void updateType()
	{
		if (operator.contains("=") || operator.equals("!") || operator.equals("&&") || operator.equals("||"))
		{
			setType("bool");
		}
		else if (getParent().containsChild(this))
		{
			String type = SyntaxUtils.getTypeInCommon(getLeftOperand(), getRightOperand()).getType();
			
			if (getLeftOperand().isPrimitive() && getRightOperand().isPrimitive())
			{
				type = SyntaxUtils.getHighestPrimitiveType(getLeftOperand().getType(), getRightOperand().getType());
			}
			
			setType(type);
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return generateCSourceFragment(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		return builder.append(operator);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		return builder.append(operator);
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
	 * Fill the given {@link Operator} with the data that is in the
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
	
	/**
	 * Test the Operator class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(Nova controller, ClassDeclaration clazz, BodyMethodDeclaration method)
	{
		
		
		return null;
	}
}
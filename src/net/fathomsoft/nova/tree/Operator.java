package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Node extension that represents an operator in an operation.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:19:40 PM
 * @version	v0.2.35 Oct 5, 2014 at 11:22:42 PM
 */
public class Operator extends IValue
{
	private String	operator;
	
	// Logical operators
	public static final String	AND = "&&", AND_C = "&&";
	public static final String	OR  = "||",  OR_C  = "||";
	
	// Math operators
	public static final String	DIVIDE     = "/",  DIVIDE_C     = "/";
	public static final String	MULTIPLY   = "*",  MULTIPLY_C   = "*";
	public static final String	MODULO     = "%",  MODULO_C     = "%";
	public static final String	ADD        = "+",  ADD_C        = "+";
	public static final String	SUBTRACT   = "-",  SUBTRACT_C   = "-";
	public static final String	ASSIGN     = "=",  ASSIGN_C     = "=";
	public static final String	BANG       = "!",  BANG_C       = "!";
	public static final String	EQUALS     = "==", EQUALS_C     = "==";
	public static final String	NOT_EQUAL  = "!=", NOT_EQUAL_C  = "!=";
	public static final String	INCREMENT  = "++", INCREMENT_C  = "++";
	public static final String	DECREMENT  = "--", DECREMENT_C  = "--";
	public static final String	GREATER    = ">",  GREATER_C    = ">";
	public static final String	LESS       = "<",  LESS_C       = "<";
	public static final String	GREATER_EQ = ">=", GREATER_EQ_C = ">=";
	public static final String	LESS_EQ    = "<=", LESS_EQ_C    = "<=";
	public static final String	L_SHIFT    = "<<", L_SHIFT_C    = "<<";
	public static final String	R_SHIFT    = ">>", R_SHIFT_C    = ">>";
	
	public static final String	OPERATORS[] = new String[] { AND, OR, DIVIDE, MULTIPLY, MODULO, INCREMENT, DECREMENT,
																EQUALS, BANG, ASSIGN, NOT_EQUAL, ADD, SUBTRACT,
																L_SHIFT, R_SHIFT, GREATER_EQ, LESS_EQ, GREATER, LESS, };
	
	public static final String	UNARY_OPERATORS[]          = new String[] { BANG, INCREMENT, DECREMENT, SUBTRACT };
	public static final String	UNARY_OPERATORS_NO_MINUS[] = new String[] { BANG, INCREMENT, DECREMENT };
	public static final String	MINUS[]                    = new String[] { SUBTRACT };
	
	public static final String	BINARY_OPERATORS[] = new String[] { AND, OR, DIVIDE, MULTIPLY, MODULO, ADD, SUBTRACT,
																EQUALS, NOT_EQUAL, ASSIGN, GREATER_EQ, LESS_EQ, L_SHIFT,
																R_SHIFT, GREATER, LESS };
	
	public static final String	LOGICAL_OPERATORS[] = new String[] { AND, OR, DIVIDE, MULTIPLY, MODULO, ADD, SUBTRACT,
																EQUALS, NOT_EQUAL, GREATER_EQ, LESS_EQ, L_SHIFT,
																R_SHIFT, GREATER, LESS };
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Operator(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get the value of the operator. For example: '+', '*', 'and', etc.
	 * 
	 * @return The value of the operator.
	 */
	public String getOperator()
	{
		return operator;
	}
	
	/**
	 * Set the operator value. For example: '+', '*', 'and', etc.
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
	
	public boolean isComparison()
	{
		switch (operator)
		{
			case AND: 
			case OR: return false;
			default:
				return true;
		}
	}
	
	public boolean isConjunction()
	{
		switch (operator)
		{
			case AND: 
			case OR: return true;
			default:
				return false;
		}
	}
	
	/**
	 * Update the type that the operation returns.
	 */
	public void updateType()
	{
		if (!StringUtils.containsString(operator, OPERATORS))
		{
			SyntaxMessage.error("Unknown operator '" + operator + "'", this);
		}
		
		if (operator.equals(EQUALS) || operator.equals(NOT_EQUAL) || operator.equals(BANG) || operator.equals(AND) || operator.equals(OR))
		{
			setType("Bool");
		}
		else if (!isDecoding())
		{
			String type = SyntaxUtils.getTypeInCommon(getLeftOperand().getReturnedNode(), getRightOperand().getReturnedNode()).getType();
			
			if (getLeftOperand().getReturnedNode().isPrimitive() && getRightOperand().getReturnedNode().isPrimitive())
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
		if (operator.equals(AND))
		{
			return builder.append(AND_C);
		}
		else if (operator.equals(OR))
		{
			return builder.append(OR_C);
		}
		
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
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Operator clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
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
	public static String test(TestContext context)
	{
		
		
		return null;
	}
}
package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

import static java.util.Arrays.stream;

/**
 * Node extension that represents an operator in an operation.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:19:40 PM
 * @version	v0.2.36 Oct 13, 2014 at 12:16:42 AM
 */
public class Operator extends IValue
{
	public String	operator;
	
	// Logical operators
	public static final String	AND = "&&", AND_C = "&&";
	public static final String	OR  = "||",  OR_C  = "||";
	
	// Math operators
	public static final String	DIVIDE      = "/",  DIVIDE_C      = "/";
	public static final String	MULTIPLY    = "*",  MULTIPLY_C    = "*";
	public static final String	MODULO      = "%",  MODULO_C      = "%";
	public static final String	ADD         = "+",  ADD_C         = "+";
	public static final String	SUBTRACT    = "-",  SUBTRACT_C    = "-";
	public static final String	ASSIGN      = "=",  ASSIGN_C      = "=";
	public static final String	BANG        = "!",  BANG_C        = "!";
	public static final String	EQUALS      = "==", EQUALS_C      = "==";
	public static final String	NOT_EQUAL   = "!=", NOT_EQUAL_C   = "!=";
	public static final String	INCREMENT   = "++", INCREMENT_C   = "++";
	public static final String	DECREMENT   = "--", DECREMENT_C   = "--";
	public static final String	BITWISE_AND = "&",  BITWISE_AND_C = "&";
	public static final String	BITWISE_OR  = "|",  BITWISE_OR_C  = "|";
	public static final String	GREATER     = ">",  GREATER_C     = ">";
	public static final String	LESS        = "<",  LESS_C        = "<";
	public static final String	GREATER_EQ  = ">=", GREATER_EQ_C  = ">=";
	public static final String	LESS_EQ     = "<=", LESS_EQ_C     = "<=";
	public static final String	L_SHIFT     = "<<", L_SHIFT_C     = "<<";
	public static final String	R_SHIFT     = ">>", R_SHIFT_C     = ">>";
	
	public static final String	SHORTHAND_DIVIDE      = "/=",  SHORTHAND_DIVIDE_C      = "/=";
	public static final String	SHORTHAND_MULTIPLY    = "*=",  SHORTHAND_MULTIPLY_C    = "*=";
	public static final String	SHORTHAND_MODULO      = "%=",  SHORTHAND_MODULO_C      = "%=";
	public static final String	SHORTHAND_ADD         = "+=",  SHORTHAND_ADD_C         = "+=";
	public static final String	SHORTHAND_SUBTRACT    = "-=",  SHORTHAND_SUBTRACT_C    = "-=";
	public static final String	SHORTHAND_BITWISE_AND = "&=",  SHORTHAND_BITWISE_AND_C = "&=";
	public static final String	SHORTHAND_BITWISE_OR  = "|=",  SHORTHAND_BITWISE_OR_C  = "|=";
	public static final String	SHORTHAND_L_SHIFT     = "<<=", SHORTHAND_L_SHIFT_C     = "<<=";
	public static final String	SHORTHAND_R_SHIFT     = ">>=", SHORTHAND_R_SHIFT_C     = ">>=";
	
	public static final String	OPERATORS[] = new String[] { SHORTHAND_ADD, SHORTHAND_SUBTRACT, SHORTHAND_MULTIPLY,
																SHORTHAND_DIVIDE, SHORTHAND_MODULO, SHORTHAND_L_SHIFT,
																SHORTHAND_R_SHIFT, SHORTHAND_BITWISE_AND, SHORTHAND_BITWISE_OR,
																AND, OR, DIVIDE, MULTIPLY, MODULO, INCREMENT, DECREMENT,
																EQUALS, BANG, ASSIGN, NOT_EQUAL, ADD, SUBTRACT,
																L_SHIFT, R_SHIFT, GREATER_EQ, LESS_EQ, GREATER, LESS,
																BITWISE_AND, BITWISE_OR  };
	
	public static final String	UNARY_OPERATORS[]          = new String[] { BANG, INCREMENT, DECREMENT, SUBTRACT };
	public static final String	UNARY_OPERATORS_NO_MINUS[] = new String[] { BANG, INCREMENT, DECREMENT };
	public static final String	MINUS[]                    = new String[] { SUBTRACT };
	
	public static final String	BINARY_OPERATORS[] = new String[] { SHORTHAND_ADD, SHORTHAND_SUBTRACT, SHORTHAND_MULTIPLY,
																SHORTHAND_DIVIDE, SHORTHAND_MODULO, SHORTHAND_L_SHIFT,
																SHORTHAND_R_SHIFT, SHORTHAND_BITWISE_AND, SHORTHAND_BITWISE_OR,
																AND, OR, DIVIDE, MULTIPLY, MODULO, ADD, SUBTRACT,
																EQUALS, NOT_EQUAL, ASSIGN, GREATER_EQ, LESS_EQ, L_SHIFT,
																R_SHIFT, GREATER, LESS, BITWISE_AND, BITWISE_OR };
	
	public static final String	LOGICAL_OPERATORS[] = new String[] { SHORTHAND_ADD, SHORTHAND_SUBTRACT, SHORTHAND_MULTIPLY,
																SHORTHAND_DIVIDE, SHORTHAND_MODULO, SHORTHAND_L_SHIFT,
																SHORTHAND_R_SHIFT, SHORTHAND_BITWISE_AND, SHORTHAND_BITWISE_OR,
																AND, OR, DIVIDE, MULTIPLY, MODULO, ADD, SUBTRACT,
																EQUALS, NOT_EQUAL, GREATER_EQ, LESS_EQ, L_SHIFT,
																R_SHIFT, GREATER, LESS, BITWISE_AND, BITWISE_OR };
																
    public static final String SHORTHAND_OPERATORS[] = new String[] { SHORTHAND_ADD, SHORTHAND_SUBTRACT, SHORTHAND_MULTIPLY,
                                                                    SHORTHAND_DIVIDE, SHORTHAND_MODULO, SHORTHAND_L_SHIFT,
                                                                    SHORTHAND_R_SHIFT, SHORTHAND_BITWISE_AND, SHORTHAND_BITWISE_OR };
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
	
	public boolean isShorthand()
    {
        return stream(SHORTHAND_OPERATORS).anyMatch(x -> x.equals(operator));
    }
	
    public String getNonShorthand()
	{
		switch (operator)
		{
			case SHORTHAND_ADD:
				return ADD;
			case SHORTHAND_BITWISE_AND:
				return BITWISE_AND;
			case SHORTHAND_BITWISE_OR:
				return BITWISE_OR;
			case SHORTHAND_DIVIDE:
				return DIVIDE;
			case SHORTHAND_L_SHIFT:
				return L_SHIFT;
			case SHORTHAND_MODULO:
				return MODULO;
			case SHORTHAND_MULTIPLY:
				return MULTIPLY;
			case SHORTHAND_R_SHIFT:
				return R_SHIFT;
			case SHORTHAND_SUBTRACT:
				return SUBTRACT;
		}
		
		return null;
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
	
	public boolean isArithmetic()
	{
		switch (operator)
		{
			case MULTIPLY:
			case DIVIDE:
			case ADD:
			case SUBTRACT:
			case MODULO: return true;
			default:
				return false;
		}
	}
	
	public boolean isNumerical()
	{
		if (isArithmetic())
		{
			return true;
		}
		
		switch (operator)
		{
			case BITWISE_AND:
			case BITWISE_OR:
			case GREATER:
			case LESS:
			case GREATER_EQ:
			case LESS_EQ:
			case L_SHIFT:
			case R_SHIFT: return true;
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
			Value l = getLeftOperand().getReturnedNode();
			Value r = getRightOperand().getReturnedNode();
			
			r = r instanceof BinaryOperation ? ((BinaryOperation)r).getLeftOperand().getReturnedNode() : r;
			
			ClassDeclaration common = SyntaxUtils.getTypeInCommon(l, r);
			
			if (common == null)
			{
				String left = l.getType();
				String right = r.getType();
				
				SyntaxUtils.getTypeInCommon(l, r);
				
				SyntaxMessage.error("Type '" + left + "' is not compatible with type '" + right + "'", this);
			}
			
			String type = common.getType();
			
			if (getLeftOperand().getReturnedNode().isPrimitive() && getRightOperand().getReturnedNode().isPrimitive())
			{
				type = SyntaxUtils.getHighestPrimitiveType(getLeftOperand().getReturnedNode().getType(), getRightOperand().getReturnedNode().getType());
			}
			
			setType(type);
		}
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
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public Operator cloneTo(Operator node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link Operator} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Operator cloneTo(Operator node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
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
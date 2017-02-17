package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.generics.GenericTypeArgument;
import net.fathomsoft.nova.tree.generics.GenericTypeArgumentList;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * {@link IValue} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class TernaryOperation extends IValue implements Accessible
{
	public boolean safeNavigation;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public TernaryOperation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		GenericTypeArgumentList implementation = new GenericTypeArgumentList(this, locationIn.asNew());
		addChild(implementation, this);
	}
	
	@Override
	public int getNumDecodedChildren()
	{
		return super.getNumDecodedChildren() + 3;
	}
	
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}
	
	public Value getCondition()
	{
		return (Value)getChild(super.getNumDefaultChildren() + 1);
	}
	
	public Value getTrueValue()
	{
		return (Value)getChild(super.getNumDefaultChildren() + 2);
	}
	
	public Value getFalseValue()
	{
		return (Value)getChild(super.getNumDefaultChildren() + 3);
	}

	@Override
	public GenericTypeArgumentList getGenericTypeArgumentList()
	{
		return (GenericTypeArgumentList)getChild(super.getNumDefaultChildren() + 0);
	}
	
	@Override
	public boolean isSafeNavigation()
	{
		return safeNavigation;
	}
	
	@Override
	public void setSafeNavigation(boolean safeNavigation)
	{
		this.safeNavigation = safeNavigation;
	}
	
	public static TernaryOperation generateDefault(Node parent, Location location)
	{
		TernaryOperation n = new TernaryOperation(parent, location);
		
		n.addChild(Literal.generateDefault(n, location));
		n.addChild(Literal.generateDefault(n, location));
		n.addChild(Literal.generateDefault(n, location));
		
		return n;
	}
	
	/**
	 * Decode the given statement into a {@link TernaryOperation} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>expression ? trueValue : falseValue</li>
	 * 	<li>expression ?: ifExpressionNullValue</li>
	 * </ul>
	 *
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link TernaryOperation} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link TernaryOperation}.
	 */
	public static TernaryOperation decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		int questionMarkIndex = SyntaxUtils.findCharInBaseScope(statement, '?');
		
		int next = StringUtils.findNextNonWhitespaceIndex(statement, questionMarkIndex + 1);
		
		while (next > 0 && statement.charAt(next) == '.')
		{
			questionMarkIndex = SyntaxUtils.findCharInBaseScope(statement, '?', next + 1);
			next = StringUtils.findNextNonWhitespaceIndex(statement, questionMarkIndex + 1);
		}
		
		if (questionMarkIndex > 0)
		{
			int colonIndex = SyntaxUtils.findCharInBaseScope(statement, ':', questionMarkIndex + 1);
			
			if (colonIndex > questionMarkIndex)
			{
				TernaryOperation n = new TernaryOperation(parent, location);
				
				String conditionString = statement.substring(0, questionMarkIndex).trim();
				
				Value condition = SyntaxTree.decodeValue(parent, conditionString, location, require);
				
				if (condition == null)
				{
					SyntaxMessage.queryError("Unable to decode ternary operation condition '" + conditionString + "'", n, require);
					
					return null;
				}
				
				condition.onAfterDecoded();
				
				String trueValueString = statement.substring(questionMarkIndex + 1, colonIndex).trim();
				
				Value trueValue = null;
				
				boolean traditional = trueValueString.length() > 0;
				
				if (traditional)
				{
					trueValue = SyntaxTree.decodeValue(n, trueValueString, location, require);
				}
				else // elvis operator
				{
					BinaryOperation nullCheck = BinaryOperation.generateNullCheck(n, condition);
					
					Variable local = getLocalVariableFromNullCheck(nullCheck);
					
					trueValue = local.getDeclaration().generateUsableVariable(n, condition.getLocationIn());
					
					condition = nullCheck;
				}
				
				String falseValueString = statement.substring(colonIndex + 1).trim();
				
				Value falseValue = SyntaxTree.decodeValue(parent, falseValueString, location, require);
				
				if (trueValue == null || falseValue == null)
				{
					SyntaxMessage.queryError("Unable to decode ternary operation false value '" + conditionString + "'", n, require);
					
					return null;
				}
				
				n.addChild(condition);
				n.addChild(trueValue);
				n.addChild(falseValue);
				
				if (traditional && !"Bool".equals(condition.getReturnedNode().getType()))
				{
					condition.replaceWithNullCheck();
				}
				
				ClassDeclaration commonClass = null;
				
				Value v = Literal.isNullLiteral(trueValue.getReturnedNode()) ? falseValue.getReturnedNode() : trueValue.getReturnedNode();
				
				if (Literal.isNullLiteral(trueValue))
				{
					commonClass = falseValue.getReturnedNode().getTypeClass();
				}
				else if (Literal.isNullLiteral(falseValue))
				{
					commonClass = trueValue.getReturnedNode().getTypeClass();
				}
				else
				{
					if (trueValue.getReturnedNode().getTypeClass() == null || falseValue.getReturnedNode().getTypeClass() == null)
					{
						return null;
					}
					
					commonClass = trueValue.getReturnedNode().getTypeClass().getCommonAncestor(falseValue.getReturnedNode().getTypeClass());
				}
				
				n.setType(v); // Set the array type data and stuff like that
				n.setTypeValue(commonClass.getType()); // Set the actual class type to the common class
				
				return n;
			}
		}
		
		return null;
	}
	
	public static Variable getLocalVariableFromNullCheck(BinaryOperation nullCheck)
	{
		return (Variable)((Assignment)((Priority)nullCheck.getLeftOperand()).getContents()).getAssigneeNode();
	}
	
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		result.returnedNode = checkSafeNavigation();
		
		return result;
	}
	
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		if (containsProperty("safeNavigation"))
		{
			return builder.append(getProperty("safeNavigation").toString());
		}
		
		return getCondition().generateNovaInput(builder).append(" ? ").append(getTrueValue().generateNovaInput()).append(" : ").append(getFalseValue().generateNovaInput());
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public TernaryOperation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		TernaryOperation node = new TernaryOperation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public TernaryOperation cloneTo(TernaryOperation node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link TernaryOperation} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public TernaryOperation cloneTo(TernaryOperation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.safeNavigation = safeNavigation;
		
		return node;
	}
	
	/**
	 * Test the {@link TernaryOperation} class type to make sure everything
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
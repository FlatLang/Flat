package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.generics.GenericTypeArgumentList;
import flat.tree.variables.Variable;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

public class TernaryOperation extends IValueValidator implements AccessibleValidator
{
	public boolean safeNavigation;
	public boolean chainNavigation;

	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public TernaryOperation(NodeValidator temporaryParent, Location locationIn)
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
	
	public ValueValidator getCondition()
	{
		return (ValueValidator)getChild(super.getNumDefaultChildren() + 1);
	}
	
	public ValueValidator getTrueValue()
	{
		return (ValueValidator)getChild(super.getNumDefaultChildren() + 2);
	}
	
	public ValueValidator getFalseValue()
	{
		return (ValueValidator)getChild(super.getNumDefaultChildren() + 3);
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

	@Override
	public boolean isChainNavigation()
	{
		return chainNavigation;
	}

	@Override
	public void setChainNavigation(boolean chainNavigation)
	{
		this.chainNavigation = chainNavigation;
	}

	@Override
	public boolean isInstance() {
		return false; // FIXME
	}

	public static TernaryOperation generateDefault(NodeValidator parent, Location location)
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
	public static TernaryOperation decodeStatement(NodeValidator parent, String statement, Location location, boolean require)
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
				
				ValueValidator condition = SyntaxTree.decodeValue(parent, conditionString, location, require);
				
				if (condition == null)
				{
					SyntaxMessage.queryError("Unable to decode ternary operation condition '" + conditionString + "'", n, require);
					
					return null;
				}
				
				condition.onAfterDecoded();
				
				String trueValueString = statement.substring(questionMarkIndex + 1, colonIndex).trim();
				
				ValueValidator trueValue = null;
				
				boolean traditional = trueValueString.length() > 0;
				
				if (traditional)
				{
					trueValue = SyntaxTree.decodeValue(n, trueValueString, location, require);
				}
				else // elvis operator
				{
					BinaryOperationValidator nullCheck = BinaryOperationValidator.generateNullCheck(n, condition);
					
					Variable local = getLocalVariableFromNullCheck(nullCheck);
					local.declaration.setProperty("userMade", true);
					local.declaration.setProperty("elvis", true);
					
					trueValue = local.getDeclaration().generateUsableVariable(n, condition.getLocationIn());
					
					condition = nullCheck;
				}
				
				String falseValueString = statement.substring(colonIndex + 1).trim();
				
				ValueValidator falseValue = SyntaxTree.decodeValue(parent, falseValueString, location, require);
				
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
				
				ClassDeclarationValidator commonClass = null;

				ValueValidator falseReturnedValue = falseValue.getReturnedNode();
				ValueValidator trueReturnedValue = trueValue.getReturnedNode();
				
				ValueValidator v = Literal.isNullLiteral(trueReturnedValue) ? falseReturnedValue : trueReturnedValue;

				if (Literal.isNullLiteral(trueValue))
				{
					commonClass = falseReturnedValue.getTypeClass();
				}
				else if (Literal.isNullLiteral(falseValue))
				{
					commonClass = trueReturnedValue.getTypeClass();
				}
				else
				{
					if (trueReturnedValue.getGenericTypeParameter() == falseReturnedValue.getGenericTypeParameter() && trueReturnedValue.getGenericTypeParameter() != null) {

					}
					else if (trueReturnedValue.getTypeClass() == null || falseReturnedValue.getTypeClass() == null)
					{
						return null;
					}
					
					commonClass = SyntaxUtils.getTypeInCommon(trueReturnedValue, falseReturnedValue);
				}
				
				n.setType(v); // Set the array type data and stuff like that
				n.setTypeValue(commonClass.getType()); // Set the actual class type to the common class
				
				return n;
			}
		}
		
		return null;
	}
	
	public static Variable getLocalVariableFromNullCheck(BinaryOperationValidator nullCheck)
	{
		return (Variable)((AssignmentValidator)((Priority)nullCheck.getLeftOperand()).getContents()).getAssigneeNode();
	}
	
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		result.returnedNodeValidator = checkSafeNavigation();
		
		return result;
	}
	
	@Override
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren)
	{
		if (containsProperty("safeNavigation"))
		{
			return builder.append(getProperty("safeNavigation").toString());
		}
		
		return getCondition().generateFlatInput(builder).append(" ? ").append(getTrueValue().generateFlatInput()).append(" : ").append(getFalseValue().generateFlatInput());
	}
	
	/**
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public TernaryOperation clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		TernaryOperation node = new TernaryOperation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
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
		node.chainNavigation = chainNavigation;

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
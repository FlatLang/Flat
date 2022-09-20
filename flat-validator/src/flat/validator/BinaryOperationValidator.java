package flat.validator;

import flat.Flat;
import flat.TestContext;
import flat.ValidationResult;
import flat.error.Message;
import flat.error.SyntaxErrorException;
import flat.error.SyntaxMessage;
import flat.tree.exceptionhandling.Throw;
import flat.tree.variables.Variable;
import flat.util.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Node extension that represents the use of a binary operator.
 * For example: "153 + 4 / 2" represents two binary operator nodes.
 * The first node consists of the "153" as the left hand value, and
 * the binary node "4 / 2"
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:20:35 PM
 * @version	v0.2.44 Jul 13, 2015 at 1:28:17 AM
 */
public class BinaryOperationValidator extends IValueValidator
{
	private static int uniqueID = 0;
	
	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public BinaryOperationValidator(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public boolean isComparison()
	{
		return getOperator().isComparison();
	}
	
	public boolean isConjunction()
	{
		return getOperator().isConjunction();
	}
	
	@Override
	public String getGenericReturnType(boolean checkCast)
	{
		return getType();
	}
	
	/**
	 * @see ValueValidator#getReturnedNode()
	 */
	@Override
	public ValueValidator getReturnedNode()
	{
		return this;
	}
	
	/**
	 * Get the left operand within the BinaryOperation.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * 43 - 12</pre></blockquote>
	 * In the previous binary operation, 43 is the left operand.
	 * 
	 * @return The left operand in the operation.
	 */
	public ValueValidator getLeftOperand()
	{
		return (ValueValidator)getChild(0);
	}
	
	/**
	 * Get the operator within the BinaryOperation.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * 43 - 12</pre></blockquote>
	 * In the previous binary operation, '-' is the operator.
	 * 
	 * @return The operator in the operation.
	 */
	public Operator getOperator()
	{
		return (Operator)getChild(1);
	}
	
	/**
	 * Get the right operand within the BinaryOperation.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * 43 - 12</pre></blockquote>
	 * In the previous binary operation, 12 is the right operand.
	 * 
	 * @return The right operand in the operation.
	 */
	public ValueValidator getRightOperand()
	{
		return (ValueValidator)getChild(2);
	}
	
	/**
	 * @see NodeValidator#generateFlatInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren)
	{
		if (getNumChildren() == 1)
		{
			return getLeftOperand().generateFlatInput(builder, outputChildren);
		}
		
		return getLeftOperand().generateFlatInput(builder, outputChildren).append(' ').append(getOperator().generateFlatInput(outputChildren)).append(' ').append(getRightOperand().generateFlatInput(outputChildren));
	}
	
	public static BinaryOperationValidator generateNullCheck(NodeValidator parent, ValueValidator value)
	{
		return generateNullCheck(parent, value, value);
	}
	
	public static BinaryOperationValidator generateNullCheck(NodeValidator parent, ValueValidator value, ValueValidator root)
	{
		NodeValidator scopeNodeValidator = parent.getNearestScopeAncestor();

		if ((scopeNodeValidator instanceof ControlStatement || scopeNodeValidator instanceof WhileLoop))// && scopeNode.isDecoding())
		{
			scopeNodeValidator = scopeNodeValidator.getParent().getAncestorWithScope();
		}

		Variable local = scopeNodeValidator.getScope().createLocalVariable(root);
		local.detach();
		local.declaration.setProperty("userMade", false);
		local.declaration.setProperty("fromAssignment", true);
		local.declaration.setProperty("requiresPrecedingDeclaration", true);

		BinaryOperationValidator nullCheck = BinaryOperationValidator.generateDefault(parent, value.getLocationIn());

		AssignmentValidator assignmentValidator = AssignmentValidator.generateDefault(parent, value.getLocationIn());
		assignmentValidator.getAssigneeNodes().addChild(local);
		assignmentValidator.addChild(root);
		
		nullCheck.getLeftOperand().replaceWith(Priority.generateFrom(assignmentValidator));
		nullCheck.getOperator().setOperator("!=");
		nullCheck.getRightOperand().replaceWith(Literal.decodeStatement(parent, "null", value.getLocationIn(), true));
		
		return nullCheck;
	}
	
	public static BinaryOperationValidator generateDefault(NodeValidator parent, Location location)
	{
		return (BinaryOperationValidator)decodeStatement(parent, "null == null", location, true);
	}
	
	/**
	 * Validate that the given statement is a possible candidate to being
	 * a binary operator node.
	 * 
	 * @param statement The statement to validate.
	 * @return Whether or not the statement is valid.
	 */
	private static boolean validateStatement(NodeValidator nodeValidator, String statement)
	{
		if (SyntaxUtils.isLiteral(nodeValidator, statement))
		{
			return false;
		}
		if (!SyntaxUtils.strictStripGenerics(statement).equals(statement))
		{
			return false;
		}
		
		Bounds operatorLoc = StringUtils.findStrings(statement, Operator.LOGICAL_OPERATORS);
		if (operatorLoc.getStart() == 0) operatorLoc = StringUtils.findStrings(statement, Operator.LOGICAL_OPERATORS, operatorLoc.getEnd() + 1);
		
		if (operatorLoc.getStart() < 0)
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * Decode the given statement into a BinaryOperation if possible.
	 * If it is not possible, the method will return null. The requirements
	 * of a BinaryOperation include: Contains an operator such as '!=',
	 * '>=', '<=', '>', '<', '*', '/', '+', '-', and '%' surrounded by two
	 * comparable values.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>5 / 2</li>
	 * 	<li>(a.getWidth() + b.getHeight) / array.getSize()</li>
	 * 	<li>variableName % 2 == 0</li>
	 * </ul>
	 * 
	 * The last two examples contain two BinaryOperations.
	 * 
	 * @param parent The parent of the current statement.
	 * @param statement The statement to translate into a BinaryOperation
	 * 		if possible.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated Node, if it was possible to translated it
	 * 		into a BinaryOperation.
	 */
	public static ValueValidator decodeStatement(NodeValidator parent, String statement, Location location, boolean require)
	{
		if (!validateStatement(parent, statement))
		{
			return null;
		}
		if (statement.length() <= 0)
		{
			return null;
		}
		
		UnaryOperation preTest = UnaryOperation.decodeStatement(parent, statement, location, false);
		
		if (preTest != null)
		{
			return preTest;
		}
		
		Location operatorLoc    = new Location(location);
		Bounds   operatorBounds = StringUtils.findStrings(statement, Operator.LOGICAL_OPERATORS);
		if (operatorBounds.getStart() == 0) operatorBounds = StringUtils.findStrings(statement, Operator.LOGICAL_OPERATORS, operatorBounds.getEnd() + 1);
		
		operatorLoc.setBounds(operatorBounds);
		operatorLoc.setLineNumber(location.getLineNumber());
		
		if (statement.charAt(operatorLoc.getStart()) == '>' && statement.charAt(operatorLoc.getStart() - 1) == '=')
		{
			return null;
		}
		
		try
		{
			if (operatorBounds.isValid())
			{
				BinaryOperationValidator n = new BinaryOperationValidator(parent, location);
				
				n.decodeOperands(statement, operatorLoc, require);
				n.getOperator().updateType();
				n.setType(n.getOperator().getType());
				
				return n.optimizeOperation();
			}
			else
			{
				return createNode(parent, statement, location);
			}
		}
		catch (BinarySyntaxException e)
		{
			return null;
		}
	}
	
	/**
	 * Decode the left and right operands.
	 * 
	 * @param statement The statement to translate into a BinaryOperation
	 * 		if possible.
	 * @param operatorLoc The Location of the operator in the operation.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 */
	private void decodeOperands(String statement, Location operatorLoc, boolean require)
	{
		ValueValidator lhn = decodeLeftOperand(statement, operatorLoc);
		
		if (!operatorLoc.getBounds().isValid())
		{
			throw new BinarySyntaxException("Could not decode operation.");
		}
		
		String   operatorVal = statement.substring(operatorLoc.getStart(), operatorLoc.getEnd());
		Location location    = new Location(getLocationIn());
		Operator operator    = new Operator(this, location);
		operator.setOperator(operatorVal);
		addChild(operator);
		
		lhn.onAfterDecoded();
		
		ValueValidator rhn = decodeRightOperand(statement, operatorLoc, require);
		
		rhn.onAfterDecoded();
		
		validateOperation(lhn, rhn, operator);
		
		if (operatorVal.equals(Operator.DIVIDE))
		{
//			validateDivideByZero(rhn);
		}
	}
	
	/**
	 * Validate that the operands are compatible with the operation.
	 * 
	 * @param lhn The left operand.
	 * @param rhn The right operand.
	 * @param operator The operator of the operation.
	 */
	private void validateOperation(ValueValidator lhn, ValueValidator rhn, Operator operator)
	{
		NodeValidator parent = getParent();
		
		if (!parent.isWithinExternalContext() && !lhn.isExternalType() && !rhn.isExternalType())
		{
			ValueValidator returnedLeft  = lhn.getReturnedNode();
			returnedLeft = returnedLeft.getFlatTypeValue(returnedLeft);
			
			ValueValidator returnedRight = rhn.getReturnedNode();
			returnedRight = returnedRight.getFlatTypeValue(returnedRight);
			
			ClassDeclarationValidator common = SyntaxUtils.getTypeInCommon(returnedLeft, returnedRight);
			
			operator.updateType();
			String operatorType = operator.getType();
			
			if (common == null)
			{
				ClassDeclarationValidator integerClass = parent.getProgram().getClassDeclaration("flat/primitive/number/Int");
				
				ClassDeclarationValidator leftClass  = returnedLeft.getTypeClass();
				ClassDeclarationValidator rightClass = returnedRight.getTypeClass();
				
				if (operatorType == null || !operatorType.equals("Bool") || rightClass != null && !rightClass.isOfType(integerClass) && !rightClass.isOfType(integerClass))
				{
					if (lhn instanceof LocalDeclarationValidator && rhn instanceof LocalDeclarationValidator)
					{
						throw new BinarySyntaxException("", false);
					}
					lhn.getReturnedNode().getTypeClass();
					rhn.getReturnedNode().getTypeClass();
					SyntaxMessage.error("Type '" + returnedLeft.getType() + "' and '" + returnedRight.getType() + "' are not compatible", this);
				}
			}
			
			if (operatorType != null)
			{
				setType(operatorType);
			}
			else
			{
				setType(common.getName());
			}
		}
	}
	
	/**
	 * Validate that the denominator of the division is not zero. If this
	 * assumption cannot be made, then it adds a check before the division
	 * is made.
	 * 
	 * @param value The value that is the denominator.
	 */
	private void validateDivideByZero(ValueValidator value)
	{
		ValueValidator denominator = null;
		Location location    = new Location(getLocationIn());
		
		if (value instanceof BinaryOperationValidator)
		{
			BinaryOperationValidator bon = (BinaryOperationValidator)value;
			
			denominator = (ValueValidator)bon.getChild(0);
		}
		else
		{
			denominator = value;
		}
		
		NodeValidator nearest = getAncestorWithScope();
		
		// TODO: Take into consideration if there is a method call or modifications
		// to the any of the other variables in the operation.
		Variable checkVar = generateDivideByZeroCheck(nearest, denominator, location);
		
		if (checkVar != null)
		{
			Location rhnLoc = new Location(value.getLocationIn());
			
			replace(value, checkVar.clone(this, rhnLoc));
		}
	}
	
	/**
	 * Method used to generate a divide by zero check before any division.
	 * This ensures that there never is a division by zero without an
	 * exception being thrown.
	 * 
	 * @param parent The parent of the node to create.
	 * @param value The denominator that is being divided.
	 * @param location The location of the division in the source code.
	 * @return The node to check the exception.
	 */
	private static Variable generateDivideByZeroCheck(NodeValidator parent, ValueValidator value, Location location)
	{
		String denominator = value.generateFlatInput().toString();
		
		if (SyntaxUtils.isNumber(denominator))
		{
			if (SyntaxUtils.isZero(denominator))
			{
				SyntaxMessage.error("Cannot divide by zero", parent, location);
			}
			
			return null;
		}
		
		int checkId = ++uniqueID;
		
		String denominatorVar = Flat.LANGUAGE_NAME.toLowerCase() + "_zero_check" + checkId++;
		
		AssignmentValidator assignmentValidator = AssignmentValidator.decodeStatement(parent, value.getType() + " " + denominatorVar + " = " + denominator, location, true);
		
		Variable assignee = (Variable) assignmentValidator.getAssigneeNode();
//		assignee.setForceOriginalName(true);
		assignee.getDeclaration().setForceOriginalName(true);
		assignee.setForceOriginalName(true);
		
		parent.addChild(assignmentValidator);
		
		IfStatement ifStatement = IfStatement.decodeStatement(parent, "if (" + denominatorVar + " == 0)", location, true);
		parent.addChild(ifStatement);
		
		Throw throwNode = generateDivideByZeroThrow(parent, location);
		ifStatement.addChild(throwNode);
		
		return assignee;
	}
	
	/**
	 * Generate an exception throw node for a DivideByZeroException.
	 * 
	 * @param parent The parent of the node to create.
	 * @param location The location of the division.
	 * @return The Throw for the DivideByZeroException.
	 */
	private static Throw generateDivideByZeroThrow(NodeValidator parent, Location location)
	{
		Throw throwNode = Throw.decodeStatement(parent, "throw new DivideByZeroException()", location, true);
		
		return throwNode;
	}
	
	/**
	 * Decode the left operand of the operation.
	 * 
	 * @param statement The statement to translate into a BinaryOperation
	 * 		if possible.
	 * @param operatorLoc The Location of the operator in the operation.
	 * @return The left hand operand.
	 */
	private ValueValidator decodeLeftOperand(String statement, Location operatorLoc)
	{
		ValueValidator lhn = null;
		Bounds lhb = new Bounds(0, StringUtils.findNextNonWhitespaceIndex(statement, operatorLoc.getStart() - 1, -1) + 1);
		
		if (!lhb.isValid())
		{
			throw new BinarySyntaxException("Could not decode left hand value.");
		}
		
		Location location = new Location(getLocationIn());
		
		UnaryOperation unary = testUnaryOperator(getParent(), statement, location);
		
		if (unary != null)
		{
			lhn = unary;
			
			int offset = lhn.getLocationIn().getEnd() - location.getStart();
			
			operatorLoc.setBounds(StringUtils.findStrings(statement, Operator.LOGICAL_OPERATORS, offset));
		}
		else
		{
			Location lhl = location.asNew();
			lhl.setBounds(lhb);
			lhl.setLineNumber(operatorLoc.getLineNumber());
			
			// The left-hand value.
			String lhv = lhb.extractString(statement);

			if (lhv.length() <= 0)
			{
				throw new BinarySyntaxException("Could not decode left hand value.");
			}
			
			// The left-hand node.
			lhn = createNode(getParent(), lhv, lhl);
			
			if (lhn == null)
			{
				throw new BinarySyntaxException("Could not decode left hand value.");
			}
		
			Location leftLoc = location.asNew();
			leftLoc.setBounds(lhb);
			lhn.setLocationIn(leftLoc);
		}
		
		addChild(lhn);
		
		return lhn;
	}
	
	/**
	 * Decode the right operand of the operation.
	 * 
	 * @param statement The statement to translate into a BinaryOperation
	 * 		if possible.
	 * @param operatorLoc The Location of the operator in the operation.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The right operand.
	 */
	private ValueValidator decodeRightOperand(String statement, Location operatorLoc, boolean require)
	{
		int rhIndex = StringUtils.findNextNonWhitespaceIndex(statement, operatorLoc.getEnd());
		
		if (rhIndex < 0)
		{
			throw new BinarySyntaxException("Could not decode right hand value.");
		}
		
		// Decode the value on the right.
		statement = statement.substring(rhIndex);
		
		Location location = new Location(getLocationIn());
		
		ValueValidator rhn = SyntaxTree.decodeValue(getParent(), statement, location, require);
		
		if (rhn == null)
		{
//			SyntaxMessage.error("Cannot decode binary operation '" + statement + "'", parent.getFileDeclaration(), location, parent.getController());

			throw new BinarySyntaxException("Could not decode right hand value.");
		}

		Location rightLoc = location.asNew();
		rightLoc.setBounds(rhIndex, statement.length());
		rhn.setLocationIn(rightLoc);
		
		addChild(rhn);
		
		return rhn;
	}
	
	/**
	 * Get the node that represents value for the statement.
	 * 
	 * @param parent The parent of the statement.
	 * @param statement The statement containing the value.
	 * @param location The location of the statement in the source code.
	 * @return The generated Node instance.
	 */
	private static ValueValidator createNode(NodeValidator parent, String statement, Location location)
	{
		Location loc = location.asNew();
		loc.setLineNumber(location.getLineNumber());
		
		ValueValidator node = SyntaxTree.decodeValue(parent, statement, loc, false);
		
		if (node instanceof ValueValidator == false)
		{
			throw new BinarySyntaxException("Statement '" + statement + "' does not return a value.");
		}
		
		return node;
	}
	
	/**
	 * Try to decode a UnaryOperation from the given statement, if it
	 * is possible.
	 * 
	 * @param parent The parent of the given statement.
	 * @param statement The statement to decode the UnaryOperation
	 * 		from.
	 * @param location The location of the statement in the source code.
	 * @return The UnaryOperation instance, if one exists. Null
	 * 		otherwise.
	 */
	private static UnaryOperation testUnaryOperator(NodeValidator parent, String statement, Location location)
	{
		Bounds unaryOperationBounds = findUnaryOperation(statement);
		
		if (!unaryOperationBounds.isValid())
		{
			return null;
		}
		
		Location newLoc = location.asNew();
		newLoc.addOffset(unaryOperationBounds.getStart());
		newLoc.setBounds(location.getStart() + unaryOperationBounds.getStart(), location.getStart() + unaryOperationBounds.getEnd());
		
		String expression = statement.substring(unaryOperationBounds.getStart(), unaryOperationBounds.getEnd());
		
		return UnaryOperation.decodeStatement(parent, expression, newLoc, false);
	}
	
	/**
	 * Find the location of a unary operation in the given statement,
	 * if one exists. If one doesnt exist, then Bounds.EMPTY is returned.
	 * 
	 * @param statement The statement to search for the operation in.
	 * @return The Bounds of the unary operation, if one exists. If one
	 * 		doesnt exist, then Bounds.EMPTY is returned.
	 */
	private static Bounds findUnaryOperation(String statement)
	{
		Bounds operatorLoc = findUnaryOperator(statement);
		
		if (!operatorLoc.isValid())
		{
			return Bounds.EMPTY;
		}
		
		Bounds operationLoc = operatorLoc.clone();
		
		if (operatorLoc.getStart() == 0)
		{
			Bounds bounds = StringUtils.findStrings(statement, StringUtils.SYMBOLS, operatorLoc.getEnd() + 1);
			
			if (!bounds.isValid())
			{
				return Bounds.EMPTY;
			}
			
			int end = StringUtils.findNextNonWhitespaceIndex(statement, bounds.getStart() - 1, -1) + 1;
			
			if (end <= 0)
			{
				return Bounds.EMPTY;
			}
			
			operationLoc.setEnd(end);
		}
		else
		{
			operationLoc.setStart(0);
		}
		
		return operationLoc;
	}
	
	/**
	 * Try to find the location of a unary operator in the given statement,
	 * if one exists. If one doesnt exist, then Bounds.EMPTY is returned.
	 * 
	 * @param statement The statement to search through.
	 * @return The Bounds of the unary operator, if one exists. If one
	 * 		doesnt exist, then Bounds.EMPTY is returned.
	 */
	private static Bounds findUnaryOperator(String statement)
	{
		Bounds bounds   = Bounds.EMPTY;
		int    count    = 0;
		
		while (count < 2)
		{
			Bounds symbolLoc = StringUtils.findStrings(statement, StringUtils.SYMBOLS);
			
			if (symbolLoc.getStart() < 0)
			{
				return Bounds.EMPTY;
			}
			
			Bounds unaryLoc = StringUtils.findStrings(statement, Operator.UNARY_OPERATORS);
			
			int end = 0;
			
			if (symbolLoc.getStart() == unaryLoc.getStart())
			{
				end = unaryLoc.getEnd();
			}
			else
			{
				end = StringUtils.findNextWhitespaceIndex(statement, symbolLoc.getEnd());
				
				if (end < 0)
				{
					end = statement.length();
				}
			}
			
			bounds = new Bounds(symbolLoc.getStart(), end);
			
			if (symbolLoc.getStart() == unaryLoc.getStart())
			{
				return bounds;
			}
			
			count++;
		}
		
		return Bounds.EMPTY;
	}
	
	/**
	 * Convert "<code>str + obj</code>" type String concatenations
	 * to "<code>str1.concat(obj.toString())</code>" type concatenations.
	 * This also works if the obj is primitive by autoboxing the data
	 * and then calling to toString() on the autoboxed object. If an
	 * optimization is made, the generated concatenation operation
	 * is then returned as a Node. If no optimizations can be made,
	 * then the calling Node is returned (The BinaryOperation).
	 * 
	 * @return The generated concatenation operation if generated. If not,
	 * 		then the calling Node, BinaryOperation, is returned.
	 */
	private ValueValidator optimizeOperation()
	{
		if (getOperator().isShorthand())
		{
			if (getLeftOperand().getReturnedNode() instanceof AccessibleValidator)
			{
				AccessibleValidator a = (AccessibleValidator)getLeftOperand().getReturnedNode();
				
				if (a.isAccessed() && a.getReferenceNode().toValue().getTypeClass().containsArrayBracketOverload())
				{
					ValueValidator value = (ValueValidator)getLeftOperand().clone(getLeftOperand().getParent(), getLeftOperand().getLocationIn());
					
					ValueValidator output = BinaryOperationValidator.decodeStatement(getParent(), value.generateFlatInput() + " " + getOperator().getNonShorthand() + " " + getRightOperand().generateFlatInput(), getLocationIn(), true);
					
					MethodCall call = SyntaxUtils.generateArraySetterCallFromAccess((Variable)a, output);
					
					((Variable)a).replaceWith(call);
					
					return getLeftOperand();
				}
			}
		}
		
		Trait overload = getOperator().getOperatorOverload();
		
		if (overload != null)
		{
			ValueValidator left = getLeftOperand();
			ValueValidator right = getRightOperand();

			if (overload.getName().equals("PlusOperator"))
			{
				boolean leftString = SyntaxUtils.isString(left);
				boolean rightString = SyntaxUtils.isString(right);
				
				if (leftString || rightString)
				{
					ValueValidator nonString = null;
	
					if (!leftString)
					{
						nonString = left;
					}
					else if (!rightString)
					{
						nonString = right;
					}
	
					if (nonString != null)
					{
						ValueValidator stringOutput = generateStringOutput(nonString);
						
						if (stringOutput == null)
						{
							return null;
						}
						
						if (!leftString)
						{
							left = stringOutput;
						}
						else if (!rightString)
						{
							right = stringOutput;
						}
					}
	
					MethodCall concat = MethodCall.decodeStatement(left.getReturnedNode(), "plus(null)", Location.INVALID, true);
					
					((AccessibleValidator)left.getReturnedNode()).setAccessedNode(concat);
					concat.getArgumentList().getVisibleChild(0).replaceWith(right);
					
					return left;
				}
			}
			else if (overload.getName().equals("PlusEqualsOperator"))
			{
				boolean leftString = SyntaxUtils.isString(left);
				boolean rightString = SyntaxUtils.isString(right);
				
				if (leftString)
				{
					if (!rightString)
					{
						right = generateStringOutput(right);
					}
					
					String statement = left.generateFlatInput() + " = " + left.generateFlatInput() + ".concat(" + right.generateFlatInput() + ")";
					AssignmentValidator strConcat = AssignmentValidator.decodeStatement(getParent(), statement, left.getLocationIn(), false);
					
					return strConcat;
				}
			}
			
			ValueValidator value = checkOperatorOverload(overload);
			
			if (value != null)
			{
				return value;
			}
			
			if (overload.getName().equals("NotEqualToOperator"))
			{
				overload = (Trait)getProgram().getClassDeclaration("flat/operators/EqualsOperator");
				
				value = checkOperatorOverload(overload);
				
				if (value != null)
				{
					UnaryOperation operation = new UnaryOperation(getParent(), getLocationIn());
					
					operation.addChild(new Operator(value, value.getLocationIn(), "!"));
					operation.addChild(value);
					operation.setType("Bool");
					
					return operation;
				}
			}
		}
		
		return convertPrimitiveComparison();
	}
	
	public ValueValidator convertPrimitiveComparison()
	{
		if (getOperator().isEquivalenceOperator())
		{
			ValueValidator nonNull = null;
			
			if (Literal.isNullLiteral(getLeftOperand()))
			{
				nonNull = getRightOperandValue();
			}
			else if (Literal.isNullLiteral(getRightOperandValue()))
			{
				nonNull = getLeftOperand();
			}
			
			if (nonNull != null && !Literal.isNullLiteral(nonNull))
			{
				ValueValidator returned = nonNull.getReturnedNode();
				
				if (returned.isPrimitive())
				{
					Literal bool = (Literal)Literal.decodeStatement(parent, getOperator().getOperator().equals(Operator.EQUALS) ? "false" : "true", getLocationIn(), true, true);
					
					if (nonNull instanceof BinaryOperationValidator)
					{
						BinaryOperationValidator op = (BinaryOperationValidator)nonNull;
						
						op.getLeftOperand().replaceWith(bool);
						
						return op;
					}
					else
					{
						return bool;
					}
				}
			}
		}
		
		return this;
	}
	
	public ValueValidator checkOperatorOverload(Trait overload)
	{
		ValueValidator left = getLeftOperand();
		ValueValidator right = getRightOperand();

		ValueValidator leftOperand = left;
		ValueValidator rightOperand = right;

		if (rightOperand instanceof BinaryOperationValidator) {
			rightOperand = ((BinaryOperationValidator)rightOperand).getLeftOperand();
		}

		if (
			!Literal.isNullLiteral(leftOperand) &&
				!Literal.isNullLiteral(rightOperand) &&
				!leftOperand.getReturnedNode().isExternalType() &&
				!rightOperand.getReturnedNode().isExternalType() &&
				leftOperand.getReturnedNode().getType() != null &&
				rightOperand.getReturnedNode().getType() != null &&
				leftOperand.getReturnedNode().isPrimitive() ^ rightOperand.getReturnedNode().isPrimitive()
		) {
			if (leftOperand.getReturnedNode().isPrimitive()) {
				rightOperand.replaceWithUnboxedValue(rightOperand.getReturnedNode().getFlatType(rightOperand));
				right = getRightOperand();
			} else {
				left.replaceWithUnboxedValue(leftOperand.getReturnedNode().getFlatType(leftOperand));
				left = getLeftOperand();
			}
		}

		leftOperand = left;
		rightOperand = right;

		if (rightOperand instanceof BinaryOperationValidator) {
			rightOperand = ((BinaryOperationValidator)rightOperand).getLeftOperand();
		}

		ValueValidator leftReturned = left.getReturnedNode();
		ClassDeclarationValidator leftClass = leftReturned.getTypeClass();
		ClassDeclarationValidator objectClass = getProgram().getClassDeclaration("flat/Object");

		if (leftClass != null && !leftReturned.isPrimitive() && leftClass != objectClass && leftReturned.getArrayDimensions() == 0 && getRightOperandValue().getReturnedNode().getArrayDimensions() == 0)
		{
			if (leftClass.isOfType(overload))
			{
				FlatMethodDeclarationValidator method = overload.getMethods()[0];
				
				if (getParentMethod() == null || getParentMethod() != method)
				{
					MethodDeclarationValidator[] methods = leftClass.getMethods(method.getName());
					
					Stream<MethodDeclarationValidator> validMethods = Arrays.stream(methods)
						.filter(x -> x != method && x.getDeclaringClass() != objectClass && x.getParameterList().getNumParameters() == 1);
					
					validMethods = validMethods.sorted((o1, o2) -> o1.getDeclaringClass().getDistanceFrom(overload) - o2.getDeclaringClass().getDistanceFrom(overload));
					
					ArrayList<MethodDeclarationValidator> list = validMethods.collect(Collectors.toCollection(ArrayList::new));
					
					FlatMethodDeclarationValidator validMethod = list.size() > 0 ? (FlatMethodDeclarationValidator)list.get(0) : method;
					
//					if (list.size() > 0)
					{
						//GenericTypeArgument arg = validMethod.getParameter(0).getGenericTypeParameter().getCorrespondingArgument(leftReturned);
						ValueValidator required = validMethod.getParameter(0);
						ValueValidator rightNode = getRightOperandValue();
						
						if (Literal.isNullLiteral(leftReturned) || Literal.isNullLiteral(rightNode))
						{
							return null;
						}
						
						if (required != null && SyntaxUtils.isTypeCompatible(this, required, rightNode.getReturnedNode()))
						{
							ValueValidator param = validMethod.getParameter(0);
							String defaultValue = param.isPrimitive() ? "0" : "null";
							
							MethodCall call = MethodCall.decodeStatement(left.getReturnedNode(), validMethod.getName() + "(" + defaultValue + ")", leftReturned.getLocationIn(), true, true, validMethod);
							
							((AccessibleValidator)left.getReturnedNode()).setAccessedNode(call);
							
							if (right instanceof BinaryOperationValidator)
							{
								rightNode.replaceWith(left);
								
								call.getArgumentList().getVisibleChild(0).replaceWith(rightNode);
								
								return right;
							}
							else
							{
								call.getArgumentList().getVisibleChild(0).replaceWith(right);
								
								return left;
							}
						}
					}
				}
			}
		}
		
		ClassDeclarationValidator rightClass = right.getReturnedNode().getTypeClass();

		if (overload.getName().equals("EqualsOperator")) {
			if (
				!Literal.isNullLiteral(leftOperand) &&
					!Literal.isNullLiteral(rightOperand) &&
					!leftOperand.getReturnedNode().isExternalType() &&
					!rightOperand.getReturnedNode().isExternalType() &&
					leftOperand.getReturnedNode().getType() != null &&
					rightOperand.getReturnedNode().getType() != null &&
					leftOperand.getReturnedNode().isPrimitive() ^ rightOperand.getReturnedNode().isPrimitive()
			) {
				FlatMethodDeclarationValidator validMethod = (FlatMethodDeclarationValidator) getFileDeclaration().getImportedClass(this, "Object").getMethods("equals")[0];
				ValueValidator param = validMethod.getParameter(0);
				String defaultValue = param.isPrimitive() ? "0" : "null";

				MethodCall call = MethodCall.decodeStatement(left.getReturnedNode(), validMethod.getName() + "(" + defaultValue + ")", leftReturned.getLocationIn(), true, true, validMethod);

				((AccessibleValidator)left.getReturnedNode()).setAccessedNode(call);

				ValueValidator rightNode = getRightOperandValue();

				if (right instanceof BinaryOperationValidator)
				{
					rightNode.replaceWith(left);

					call.getArgumentList().getVisibleChild(0).replaceWith(rightNode);

					return right;
				}
				else
				{
					call.getArgumentList().getVisibleChild(0).replaceWith(rightNode);

					return left;
				}
			}
			if ((leftClass != null && leftClass.isOfType("flat/String") || rightClass != null && rightClass.isOfType("flat/String"))) {
				SyntaxMessage.warning("Object reference comparison done instead of String comparison for this operation", this);
			}
		}

		return  null;
	}
	
	/**
	 * Generate a String return value for the given Value. If the
	 * given Value is already a String, this simply returns the
	 * given node. Else it a toString() method call on the given
	 * Object. (Autoboxes if needed)
	 * 
	 * @param nonString The Value to convert to a String.
	 * @return The generated String type Value.
	 */
	private ValueValidator generateStringOutput(ValueValidator nonString)
	{
		ValueValidator ret = nonString.getReturnedNode();
		
		if (ret.isPrimitiveType())
		{
			StaticClassReference ref = StaticClassReference.decodeStatement(nonString.getParent(), ret.getTypeClassName(), nonString.getLocationIn(), true);
			
			MethodCall toString = MethodCall.decodeStatement(ref, "toString(" + nonString.generateFlatInput() + ")", nonString.getLocationIn(), true);
			ref.setAccessedNode(toString);
			
			toString.getArgumentList().getVisibleChild(0).replaceWith(nonString);
			
			return ref;
		}
		else
		{
			MethodCall toString = MethodCall.decodeStatement(nonString, "toString()", nonString.getLocationIn(), true);
			
			((AccessibleValidator)ret).setAccessedNode(toString);
			
			return nonString;
		}
	}
	
	/**
	 * If the given Node is a primitive, return the autoboxed form.
	 * Else, simply return the given Node.
	 * 
	 * @param value The value to autobox if needed.
	 * @return The autoboxed primitive value.
	 */
	private ValueValidator replaceWithObjectiveValue(ValueValidator value)
	{
		ValueValidator returned = value.getReturnedNode();
		ValueValidator newVal   = null;
		
		/*if (returned.isPrimitiveType())
		{
			newVal = SyntaxUtils.autoboxPrimitive(value);
		}
		else */if (value instanceof Literal && Literal.isNullLiteral(value))
		{
			newVal = Literal.decodeStatement(this, "\"null\"", value.getLocationIn(), true);
		}
		
		if (newVal != null)
		{
			replace(value, newVal);
			
			return newVal;
		}
		
		return value;
	}
	
	public ValueValidator getRightOperandValue()
	{
		return getRightOperand() instanceof BinaryOperationValidator ? ((BinaryOperationValidator)getRightOperand()).getLeftOperand() : getRightOperand();
	}
	
	public boolean isZeroComparison()
	{
		return getLeftOperand() instanceof Literal && ((Literal)getLeftOperand()).value.equals("0") ||
			getRightOperandValue() instanceof Literal && ((Literal)getRightOperandValue()).value.equals("0");
	}
	
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		ValueValidator left = getLeftOperand().getReturnedNode();
		ValueValidator right = getRightOperandValue().getReturnedNode();
		
		if (getOperator().isNumerical() ||
			getOperator().isComparison() && left.isPrimitive() ^ right.isPrimitive() && !isZeroComparison())
		{
			if (!left.isPrimitive() && !left.isExternal())
			{
				getLeftOperand().replaceWithUnboxedValue();
			}
			if (!right.isPrimitive() && !right.isExternal())
			{
				getRightOperandValue().replaceWithUnboxedValue();
			}
		}
		
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			ValueValidator lhn = getLeftOperand();
			ValueValidator rhn = getRightOperand();
			
			Operator operator = getOperator();
			
			if (operator.isConjunction())
			{
				if ((parent instanceof BinaryOperationValidator == false || ((BinaryOperationValidator)parent).getOperator().isConjunction()) &&
					!"Bool".equals(lhn.getReturnedNode().getType()) && !Literal.isNullLiteral(lhn))
				{
					lhn.replaceWithNullCheck();
				}
				if ((rhn instanceof BinaryOperationValidator == false || ((BinaryOperationValidator)rhn).getOperator().isConjunction()) &&
					!"Bool".equals(rhn.getReturnedNode().getType()) && !Literal.isNullLiteral(rhn))
				{
					rhn.replaceWithNullCheck();
				}
			}
		}
		
		return result;
	}
	
	@Override
	@Deprecated
	public BinaryOperationValidator replaceWithNullCheck()
	{
		Operator operator = getOperator();
		ValueValidator right = getRightOperand();
		
		BinaryOperationValidator operation = super.replaceWithNullCheck();
		
		BinaryOperationValidator rhn = BinaryOperationValidator.generateDefault(operation, operation.getLocationIn());
		
		rhn.getLeftOperand().replaceWith(operation.getRightOperand());
		rhn.getOperator().replaceWith(operator);
		rhn.getRightOperand().replaceWith(right);
		
		return operation;
	}
	
	/**
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public BinaryOperationValidator clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		BinaryOperationValidator node = new BinaryOperationValidator(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
	 */
	public BinaryOperationValidator cloneTo(BinaryOperationValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link BinaryOperationValidator} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public BinaryOperationValidator cloneTo(BinaryOperationValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Exception thrown when there is a problem decoding a binary
	 * operation's syntax.
	 * 
	 * @author	Braden Steffaniak
	 * @since	v0.2.13 Jun 12, 2014 at 11:35:19 AM
	 * @version	v0.2.13 Jun 12, 2014 at 11:35:19 AM
	 */
	private static class BinarySyntaxException extends SyntaxErrorException
	{
		private boolean require;
		
		public BinarySyntaxException(String message)
		{
			this(message, true);
		}
		
		public BinarySyntaxException(String message, boolean require)
		{
			super(message, Message.ERROR);
			
			this.require = require;
		}
	}
	
	/**
	 * Test the BinaryOperation class type to make sure everything
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
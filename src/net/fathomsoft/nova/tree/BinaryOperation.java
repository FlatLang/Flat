package net.fathomsoft.nova.tree;

import java.util.regex.Matcher;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.exceptionhandling.Throw;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;
 
/**
 * Node extension that represents the use of a binary operator.
 * For example: "153 + 4 / 2" represents two binary operator nodes.
 * The first node consists of the "153" as the left hand value, and
 * the binary node "4 / 2"
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:20:35 PM
 * @version	v0.2.29 Aug 29, 2014 at 3:17:45 PM
 */
public class BinaryOperation extends IValue
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public BinaryOperation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getReturnedNode()
	 */
	@Override
	public Value getReturnedNode()
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
	public Value getLeftOperand()
	{
		return (Value)getChild(0);
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
	public Value getRightOperand()
	{
		return (Value)getChild(2);
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
		if (getNumChildren() == 1)
		{
			return getLeftOperand().generateCSourceFragment(builder);
		}
		
		return getLeftOperand().generateCSourceFragment(builder).append(' ').append(getOperator().generateCSourceFragment()).append(' ').append(getRightOperand().generateCSourceFragment());
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		if (getNumChildren() == 1)
		{
			return getLeftOperand().generateNovaInput(builder, outputChildren);
		}
		
		return getLeftOperand().generateNovaInput(builder, outputChildren).append(' ').append(getOperator().generateNovaInput(outputChildren)).append(' ').append(getRightOperand().generateNovaInput(outputChildren));
	}
	
	/**
	 * Decode the given statement into a BinaryOperation if possible.
	 * If it is not possible, the method will return null. The requirements
	 * of a BinaryOperation include: Contains an operator such as '!=',
	 * '&gt;=', '&lt;=', '&gt;', '&lt;', '*', '/', '+', '-', and '%' surrounded by two
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
	public static Value decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (!validateStatement(statement))
		{
			return null;
		}
		
		// Pattern used to find word boundaries. 
		Matcher matcher = Patterns.PRE_OPERATORS.matcher(statement);
		Value   node    = decodeStatement(parent, statement, matcher, location, require);
		
		if (node == null)
		{
			if (require)
			{
//				SyntaxMessage.error("Could not decode binary operation '" + statement + "'", parent, location);
			}
		}
		
		return node;
	}
	
	/**
	 * Validate that the given statement is a possible candidate to being
	 * a binary operator node.
	 * 
	 * @param statement The statement to validate.
	 * @return Whether or not the statement is valid.
	 */
	private static boolean validateStatement(String statement)
	{
		if (SyntaxUtils.isLiteral(statement) || SyntaxUtils.isInstantiationCall(statement))
		{
			return false;
		}
		
		Bounds operatorLoc = StringUtils.findStrings(statement, Operator.LOGICAL_OPERATORS);
		
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
	 * @param matcher The matcher for the statement.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated Node, if it was possible to translated it
	 * 		into a BinaryOperation.
	 */
	private static Value decodeStatement(Node parent, String statement, Matcher matcher, Location location, boolean require)
	{
		if (statement.length() <= 0)
		{
			return null;
		}
		
		UnaryOperation preTest = UnaryOperation.decodeStatement(parent, statement, location, false);
		
		if (preTest != null)
		{
			return preTest;
		}
		
		Bounds operatorLoc = StringUtils.findStrings(statement, Operator.LOGICAL_OPERATORS);
		
		try
		{
			if (operatorLoc.isValid())
			{
				BinaryOperation n = new BinaryOperation(parent, location);
				
				n.decodeOperands(statement, operatorLoc, matcher, require);
				n.getOperator().updateType();
				n.setType(n.getOperator().getType());
				
				return n.optimizeStringConcatenation();
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
	 * @param operatorLoc The Bounds of the operator in the operation.
	 * @param matcher The matcher for the statement.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 */
	private void decodeOperands(String statement, Bounds operatorLoc, Matcher matcher, boolean require)
	{
		Value lhn = decodeLeftOperand(statement, operatorLoc);
		
		if (!operatorLoc.isValid())
		{
			throw new BinarySyntaxException("Could not decode operation.");
		}
		
		String   operatorVal = statement.substring(operatorLoc.getStart(), operatorLoc.getEnd());
		Location location    = new Location(getLocationIn());
		Operator operator    = new Operator(this, location);
		operator.setOperator(operatorVal);
		addChild(operator);
		
		Value rhn = decodeRightOperand(statement, operatorLoc, matcher, require);
		
		validateOperation(lhn, rhn, operator);
		
		if (operatorVal.equals(Operator.DIVIDE))
		{
			validateDivideByZero(rhn);
		}
	}
	
	/**
	 * Validate that the operands are compatible with the operation.
	 * 
	 * @param lhn The left operand.
	 * @param rhn The right operand.
	 * @param operator The operator of the operation.
	 */
	private void validateOperation(Value lhn, Value rhn, Operator operator)
	{
		Node parent = getParent();
		
		if (!parent.isWithinExternalContext() && !lhn.isExternalType() && !rhn.isExternalType())
		{
			ClassDeclaration common = SyntaxUtils.getTypeInCommon(lhn.getReturnedNode(), rhn.getReturnedNode());
			
			operator.updateType();
			String operatorType = operator.getType();
			
			if (common == null)
			{
				ClassDeclaration integerClass = parent.getProgram().getClassDeclaration("nova/standard/Int");
				
				if (operatorType == null || !operatorType.equals("bool") || !lhn.getReturnedNode().getTypeClass().isOfType(integerClass) && !rhn.getReturnedNode().getTypeClass().isOfType(integerClass))
				{
					SyntaxMessage.error("Type '" + lhn.getType() + "' and '" + rhn.getType() + "' are not compatible", this);
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
	private void validateDivideByZero(Value value)
	{
		Value    denominator = null;
		Location location    = new Location(getLocationIn());
		
		if (value instanceof BinaryOperation)
		{
			BinaryOperation bon = (BinaryOperation)value;
			
			denominator = (Value)bon.getChild(0);
		}
		else
		{
			denominator = value;
		}
		
		Node nearest = getAncestorWithScope();
		
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
	 * @param denominator The denominator that is being divided.
	 * @param location The location of the division in the source code.
	 * @return The node to check the exception.
	 */
	private static Variable generateDivideByZeroCheck(Node parent, Value abstractValue, Location location)
	{
		String denominator = abstractValue.generateNovaInput().toString();
		
		if (SyntaxUtils.isNumber(denominator))
		{
			if (SyntaxUtils.isZero(denominator))
			{
				SyntaxMessage.error("Cannot divide by zero", parent, location);
			}
			
			return null;
		}
		
		int checkId = parent.getParentMethod(true).generateUniqueID();
		
		String denominatorVar = Nova.LANGUAGE_NAME.toLowerCase() + "_zero_check" + checkId++;
		
		Assignment assignment = Assignment.decodeStatement(parent, abstractValue.getType() + " " + denominatorVar + " = " + denominator, location, true);
		
		Variable assignee = (Variable)assignment.getAssigneeNode();
//		assignee.setForceOriginalName(true);
		assignee.getDeclaration().setForceOriginalName(true);
		assignee.setForceOriginalName(true);
		
		parent.addChild(assignment);
		
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
	private static Throw generateDivideByZeroThrow(Node parent, Location location)
	{
		Throw throwNode = Throw.decodeStatement(parent, "throw new DivideByZeroException()", location, true);
		
		return throwNode;
	}
	
	/**
	 * Decode the left operand of the operation.
	 * 
	 * @param statement The statement to translate into a BinaryOperation
	 * 		if possible.
	 * @param operatorLoc The Bounds of the operator in the operation.
	 * @return The left hand operand.
	 */
	private Value decodeLeftOperand(String statement, Bounds operatorLoc)
	{
		Value  lhn = null;
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
			
			StringUtils.findStrings(statement, Operator.LOGICAL_OPERATORS, offset).cloneTo(operatorLoc);
		}
		else
		{
			Location lhl = location.asNew();
			lhl.setBounds(lhb);
			
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
	 * @param operatorLoc The Bounds of the operator in the operation.
	 * @param matcher The matcher for the statement.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The right operand.
	 */
	private Value decodeRightOperand(String statement, Bounds operatorLoc, Matcher matcher, boolean require)
	{
		int rhIndex = StringUtils.findNextNonWhitespaceIndex(statement, operatorLoc.getEnd());
		
		if (rhIndex < 0)
		{
			throw new BinarySyntaxException("Could not decode right hand value.");
		}
		
		// Decode the value on the right.
		statement = statement.substring(rhIndex);
		
		matcher.reset(statement);
		
		Location location = new Location(getLocationIn());
		
		Value rhn = decodeStatement(getParent(), statement, matcher, location, require);
		
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
	private static Value createNode(Node parent, String statement, Location location)
	{
		Value node = SyntaxTree.decodeValue(parent, statement, location, false);
		
		if (node instanceof Value == false)
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
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The UnaryOperation instance, if one exists. Null
	 * 		otherwise.
	 */
	private static UnaryOperation testUnaryOperator(Node parent, String statement, Location location)
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
	private Value optimizeStringConcatenation()
	{
		if (!getOperator().getOperator().equals("+"))
		{
			return this;
		}
		
		Value left  = getLeftOperand();
		Value right = getRightOperand();
		
		boolean leftString  = SyntaxUtils.isString(left);
		boolean rightString = SyntaxUtils.isString(right);
		
		if (leftString || rightString)
		{
			Value nonString = null;
			
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
				Value stringOutput = generateStringOutput(nonString);
				
				if (!leftString)
				{
					left = stringOutput;
				}
				else if (!rightString)
				{
					right = stringOutput;
				}
			}
			
			String statement = left.generateNovaInput() + ".concat(" + right.generateNovaInput() + ")";
			Value  strConcat = (Value)SyntaxTree.decodeScopeContents(getParent(), statement, left.getLocationIn(), false);
			
			return strConcat;
		}
		
		return this;
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
	private Value generateStringOutput(Value nonString)
	{
		Value obj = replaceWithObjectiveValue(nonString);
		
		if (!obj.getType().equals("String"))
		{
			// TODO: Can optimize to simply add a .toString() MethodCall child.
			String methodCall = obj.generateNovaInput() + ".toString()";
			
			Location location = new Location(nonString.getLocationIn());
			
			nonString = (Value)SyntaxTree.decodeScopeContents(getParent(), methodCall, location, false);
			
			replace(obj, nonString);
		}
		
		return nonString;
	}
	
	/**
	 * If the given Node is a primitive, return the autoboxed form.
	 * Else, simply return the given Node.
	 * 
	 * @param value The value to autobox if needed.
	 * @return The autoboxed primitive value.
	 */
	private Value replaceWithObjectiveValue(Value value)
	{
		Value returned = value.getReturnedNode();
		Value newVal   = null;
		
		if (returned.isPrimitiveType())
		{
			newVal = SyntaxUtils.autoboxPrimitive(value);
		}
		else if (value instanceof Literal && ((Literal)value).getValue().equals("null"))
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
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public BinaryOperation clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		BinaryOperation node = new BinaryOperation(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link BinaryOperation} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public BinaryOperation cloneTo(BinaryOperation node)
	{
		super.cloneTo(node);
		
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
		boolean require;
		
		/**
		 * @see net.fathomsoft.nova.error.SyntaxErrorException#SyntaxErrorException(String)
		 */
		public BinarySyntaxException(String message)
		{
			this(message, true);
		}
		
		/**
		 * @see net.fathomsoft.nova.error.SyntaxErrorException#SyntaxErrorException(String)
		 */
		public BinarySyntaxException(String message, boolean require)
		{
			super(message);
			
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
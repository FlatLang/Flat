package net.fathomsoft.nova.tree;

import java.io.ObjectInputStream.GetField;
import java.util.regex.Matcher;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.Test;
import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.exceptionhandling.ThrowNode;
import net.fathomsoft.nova.tree.variables.VariableNode;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;
 
/**
 * TreeNode extension that represents the use of a binary operator.
 * For example: "153 + 4 / 2" represents two binary operator nodes.
 * The first node consists of the "153" as the left hand value, and
 * the binary node "4 / 2"
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:20:35 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class BinaryOperatorNode extends ValueNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public BinaryOperatorNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.ValueNode#getReturnedNode()
	 */
	@Override
	public ValueNode getReturnedNode()
	{
		ValueNode child = getLeftOperand();
		
		return child.getReturnedNode();
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
	public ValueNode getLeftOperand()
	{
		return (ValueNode)getChild(0);
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
	public OperatorNode getOperatorNode()
	{
		return (OperatorNode)getChild(1);
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
	public ValueNode getRightOperand()
	{
		return (ValueNode)getChild(2);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		if (getNumChildren() == 1)
		{
			return getLeftOperand().generateJavaSource();
		}
		
		return getLeftOperand().generateJavaSource() + ' ' + getOperatorNode().generateJavaSource() + ' ' + getRightOperand().generateJavaSource();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		return generateCSourceFragment();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		if (getNumChildren() == 1)
		{
			return getLeftOperand().generateCSourceFragment();
		}
		
		return getLeftOperand().generateCSourceFragment() + ' ' + getOperatorNode().generateCSourceFragment() + ' ' + getRightOperand().generateCSourceFragment();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateNovaInput(boolean)
	 */
	@Override
	public String generateNovaInput(boolean outputChildren)
	{
		if (getNumChildren() == 1)
		{
			return getLeftOperand().generateNovaInput(outputChildren);
		}
		
		return getLeftOperand().generateNovaInput(outputChildren) + ' ' + getOperatorNode().generateNovaInput(outputChildren) + ' ' + getRightOperand().generateNovaInput(outputChildren);
	}
	
	/**
	 * Decode the given statement into a BinaryOperatorNode if possible.
	 * If it is not possible, the method will return null. The requirements
	 * of a BinaryOperatorNode include: Contains an operator such as '!=',
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
	 * The last two examples contain two BinaryOperatorNodes.
	 * 
	 * @param parent The parent of the current statement.
	 * @param statement The statement to translate into a BinaryOperatorNode
	 * 		if possible.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated TreeNode, if it was possible to translated it
	 * 		into a BinaryOperatorNode.
	 */
	public static ValueNode decodeStatement(TreeNode parent, String statement, Location location, boolean require, boolean scope)
	{
		if (!validateStatement(statement))
		{
			return null;
		}
		
		// Pattern used to find word boundaries. 
		Matcher   matcher = Patterns.PRE_OPERATORS.matcher(statement);
		
		ValueNode node    = decodeStatement(parent, statement, matcher, location, require, scope);
		
		if (node == null)
		{
//			SyntaxMessage.error("Could not decode binary operation '" + statement + "'", parent.getFileNode(), location, parent.getController());
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
		if (SyntaxUtils.isLiteral(statement) || SyntaxUtils.isMethodCall(statement))
		{
			return false;
		}
		
		Bounds operatorLoc = StringUtils.findStrings(statement, StringUtils.BINARY_OPERATORS);
		
		if (operatorLoc.getStart() < 0)
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * Decode the given statement into a BinaryOperatorNode if possible.
	 * If it is not possible, the method will return null. The requirements
	 * of a BinaryOperatorNode include: Contains an operator such as '!=',
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
	 * The last two examples contain two BinaryOperatorNodes.
	 * 
	 * @param parent The parent of the current statement.
	 * @param statement The statement to translate into a BinaryOperatorNode
	 * 		if possible.
	 * @param matcher The matcher for the statement.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated TreeNode, if it was possible to translated it
	 * 		into a BinaryOperatorNode.
	 */
	private static ValueNode decodeStatement(TreeNode parent, String statement, Matcher matcher, Location location, boolean require, boolean scope)
	{
		Bounds operatorLoc = StringUtils.findStrings(statement, StringUtils.BINARY_OPERATORS);
		
		if (statement.length() <= 0)
		{
			return null;
		}
		
		UnaryOperatorNode preTest = UnaryOperatorNode.decodeStatement(parent, statement, location, require, scope);
		
		if (preTest != null)
		{
			return preTest;
		}
		
		try
		{
			if (operatorLoc.getStart() >= 0)
			{
				BinaryOperatorNode n = new BinaryOperatorNode(parent, location);
				
				n.decodeOperands(statement, operatorLoc, matcher, require, scope);
				
				return n;
			}
			else
			{
				ValueNode v =  createNode(parent, statement, location);
				
				return v;
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
	 * @param statement The statement to translate into a BinaryOperatorNode
	 * 		if possible.
	 * @param operatorLoc The Bounds of the operator in the operation.
	 * @param matcher The matcher for the statement.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 */
	private void decodeOperands(String statement, Bounds operatorLoc, Matcher matcher, boolean require, boolean scope)
	{
		ValueNode lhn = decodeLeftOperand(statement, operatorLoc);
		
		String   operatorVal = statement.substring(operatorLoc.getStart(), operatorLoc.getEnd());
		
		Location location    = new Location(getLocationIn());
		
		OperatorNode operator = new OperatorNode(this, location);
		operator.setOperator(operatorVal);
		addChild(operator);
		
		ValueNode rhn = decodeRightOperand(statement, operatorLoc, matcher, require, scope);
		
		validateOperation(lhn, rhn, operator);
		
		if (operatorVal.equals("/"))
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
	private void validateOperation(ValueNode lhn, ValueNode rhn, OperatorNode operator)
	{
		TreeNode parent = getParent();
		
		if (!parent.isWithinExternalContext() && !lhn.isExternalType() && !rhn.isExternalType())
		{
			ClassNode common    = SyntaxUtils.getTypeInCommon(lhn.getReturnedNode(), rhn.getReturnedNode());
			
			String operatorType = operator.getType();
			
			if (common == null)
			{
				ClassNode integerClass = parent.getProgramNode().getClassNode("Integer");
				
				if (!lhn.getTypeClass().isOfType(integerClass) && !rhn.getTypeClass().isOfType(integerClass) || operatorType == null || !operatorType.equals("bool"))
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
	private void validateDivideByZero(ValueNode value)
	{
		ValueNode denominator = null;
		
		Location  location    = new Location(getLocationIn());
		
		if (value instanceof BinaryOperatorNode)
		{
			BinaryOperatorNode bon = (BinaryOperatorNode)value;
			
			denominator = (ValueNode)bon.getChild(0);
		}
		else
		{
			denominator = value;
		}
		
		TreeNode nearest  = getAncestorWithScope();
		
		// TODO: Take into consideration if there is a method call or modifications
		// to the any of the other variables in the operation.
		VariableNode checkVar = generateDivideByZeroCheck(nearest, denominator, location);
		
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
	private static VariableNode generateDivideByZeroCheck(TreeNode parent, ValueNode value, Location location)
	{
		String denominator = value.generateNovaInput();
		
		if (SyntaxUtils.isNumber(denominator))
		{
			if (SyntaxUtils.isZero(denominator))
			{
				SyntaxMessage.error("Cannot divide by zero", parent, location);
			}
			
			return null;
		}
		
		int checkId = parent.getMethodNode().generateUniqueID();
		
		String denominatorVar = Nova.LANGUAGE_NAME.toLowerCase() + "_zero_check" + checkId++;
		
		String highestType = "double";
		
		AssignmentNode assignmentNode = AssignmentNode.decodeStatement(parent, highestType + " " + denominatorVar + " = " + denominator, location, true, false);
		
		VariableNode assignee = assignmentNode.getAssigneeNode();
		assignee.setForceOriginalName(true);
		assignee.getDeclaration().setForceOriginalName(true);
		
		parent.addChild(assignmentNode);
		
		IfStatementNode ifStatement = IfStatementNode.decodeStatement(parent, "if (" + denominatorVar + " == 0)", location, true, true);
		parent.addChild(ifStatement);
		
		ThrowNode throwNode = generateDivideByZeroThrow(parent, location);
		ifStatement.addChild(throwNode);
		
		return assignee;
	}
	
	/**
	 * Generate an exception throw node for a DivideByZeroException.
	 * 
	 * @param parent The parent of the node to create.
	 * @param location The location of the division.
	 * @return The ThrowNode for the DivideByZeroException.
	 */
	private static ThrowNode generateDivideByZeroThrow(TreeNode parent, Location location)
	{
		ThrowNode throwNode = ThrowNode.decodeStatement(parent, "throw new DivideByZeroException()", location, true, false);
		
		return throwNode;
	}
	
	/**
	 * Decode the left operand of the operation.
	 * 
	 * @param statement The statement to translate into a BinaryOperatorNode
	 * 		if possible.
	 * @param operatorLoc The Bounds of the operator in the operation.
	 * @return The left hand operand.
	 */
	private ValueNode decodeLeftOperand(String statement, Bounds operatorLoc)
	{
		ValueNode lhn = null;
		
		Bounds    lhb = new Bounds(0, StringUtils.findNextNonWhitespaceIndex(statement, operatorLoc.getStart() - 1, -1) + 1);
		
		Location  location = new Location(getLocationIn());
		
		UnaryOperatorNode unary = testUnaryOperator(getParent(), statement, location);
		
		if (unary != null)
		{
			lhn = unary;
			
			int offset = lhn.getLocationIn().getEnd() - location.getStart();
			
			StringUtils.findStrings(statement, offset, StringUtils.BINARY_OPERATORS).cloneTo(operatorLoc);
		}
		else
		{
			Location lhl = new Location(location);
			lhl.setBounds(lhb.getStart(), lhb.getEnd());
			
			// The left-hand value.
			String lhv = statement.substring(lhb.getStart(), lhb.getEnd());

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
		
			Location leftLoc = new Location(location);
			leftLoc.setBounds(lhb.getStart(), lhb.getEnd());
			lhn.setLocationIn(leftLoc);
		}
		
		addChild(lhn);
		
		return lhn;
	}
	
	/**
	 * Decode the right operand of the operation.
	 * 
	 * @param statement The statement to translate into a BinaryOperatorNode
	 * 		if possible.
	 * @param operatorLoc The Bounds of the operator in the operation.
	 * @param matcher The matcher for the statement.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The right operand.
	 */
	private ValueNode decodeRightOperand(String statement, Bounds operatorLoc, Matcher matcher, boolean require, boolean scope)
	{
		int rhIndex = StringUtils.findNextNonWhitespaceIndex(statement, operatorLoc.getEnd());
		
		// Decode the value on the right.
		statement = statement.substring(rhIndex);
		
		matcher.reset(statement);
		
		Location location = new Location(getLocationIn());
		
		ValueNode rhn = decodeStatement(getParent(), statement, matcher, location, require, scope);
		
		if (rhn == null)
		{
//			SyntaxMessage.error("Cannot decode binary operation '" + statement + "'", parent.getFileNode(), location, parent.getController());

			throw new BinarySyntaxException("Could not decode right hand value.");
		}

		Location rightLoc = new Location(location);
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
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated TreeNode instance.
	 */
	private static ValueNode createNode(TreeNode parent, String statement, Location location)
	{
		return (ValueNode)SyntaxTree.decodeScopeContents(parent, statement, location, false, false);
	}
	
	/**
	 * Try to decode a UnaryOperatorNode from the given statement, if it
	 * is possible.
	 * 
	 * @param parent The parent of the given statement.
	 * @param statement The statement to decode the UnaryOperatorNode
	 * 		from.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The UnaryOperatorNode instance, if one exists. Null
	 * 		otherwise.
	 */
	private static UnaryOperatorNode testUnaryOperator(TreeNode parent, String statement, Location location)
	{
		Bounds unaryOperationBounds = findUnaryOperation(statement);
		
		if (!unaryOperationBounds.isValid())
		{
			return null;
		}
		
		Location newLoc = new Location(location);
		newLoc.addOffset(unaryOperationBounds.getStart());
		newLoc.setBounds(location.getStart() + unaryOperationBounds.getStart(), location.getStart() + unaryOperationBounds.getEnd());
		
		String expression = statement.substring(unaryOperationBounds.getStart(), unaryOperationBounds.getEnd());
		
		return UnaryOperatorNode.decodeStatement(parent, expression, newLoc, false, false);
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
			Bounds bounds = StringUtils.findStrings(statement, operatorLoc.getEnd() + 1, StringUtils.SYMBOLS);
			
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
			
			Bounds unaryLoc = StringUtils.findStrings(statement, StringUtils.UNARY_OPERATORS);
			
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
	 * Validate the operator values. For instance, concatenates Strings
	 * that are added using the + operator.
	 * 
	 * @param phase The phase that the node is being validated in.
	 * @see net.fathomsoft.nova.tree.TreeNode#validate(int)
	 */
	@Override
	public TreeNode validate(int phase)
	{
		return validate(getParent(), true);
	}
	
	/**
	 * Validate the operator values. For instance, concatenates Strings
	 * that are added using the + operator.
	 * 
	 * @param parent The parent of the current node being validated.
	 * @param checkParent Whether or not to validate the parent as well.
	 */
	private TreeNode validate(TreeNode parent, boolean checkParent)
	{
		if (parent.isWithinExternalContext() || checkParent && parent instanceof BinaryOperatorNode)
		{
			return this;
		}
		
		ValueNode right = getRightOperand();
		
		if (right instanceof BinaryOperatorNode)
		{
			BinaryOperatorNode bin = (BinaryOperatorNode)right;
			
			bin.validate(this, false);
		}
		
		if (getOperatorNode().getOperator().equals("+"))
		{
			return optimizeStringConcatenation();
		}
		
		return this;
	}
	
	/**
	 * Convert "<code>str + obj</code>" type String concatenations
	 * to "<code>str1.concat(obj.toString())</code>" type concatenations.
	 * This also works if the obj is primitive by autoboxing the data
	 * and then calling to toString() on the autoboxed object. If an
	 * optimization is made, the generated concatenation operation
	 * is then returned as a TreeNode. If no optimizations can be made,
	 * then the calling TreeNode is returned (The BinaryOperatorNode).
	 * 
	 * @return The generated concatenation operation if generated. If not,
	 * 		then the calling TreeNode, BinaryOperatorNode, is returned.
	 */
	private TreeNode optimizeStringConcatenation()
	{
		ValueNode left  = getLeftOperand();
		ValueNode right = getRightOperand();
		
		boolean leftString  = SyntaxUtils.isString(left);
		boolean rightString = SyntaxUtils.isString(right);
		
		if ((leftString || rightString))
		{
			ValueNode nonString = null;
			
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
				ValueNode stringOutput = generateStringOutput(nonString);
				
				if (!leftString)
				{
					left = stringOutput;
				}
				else if (!rightString)
				{
					right = stringOutput;
				}
			}
			
			String   statement = left.generateNovaInput() + ".concat(" + right.generateNovaInput() + ")";
			
			TreeNode strConcat = SyntaxTree.decodeScopeContents(getParent(), statement, left.getLocationIn(), false);
			
			getParent().replace(this, strConcat);
			
			return strConcat;
		}
		
		return this;
	}
	
	/**
	 * Generate a String return value for the given ValueNode. If the
	 * given ValueNode is already a String, this simply returns the
	 * given node. Else it a toString() method call on the given
	 * Object. (Autoboxes if needed)
	 * 
	 * @param nonString The ValueNode to convert to a String.
	 * @return The generated String type ValueNode.
	 */
	private ValueNode generateStringOutput(ValueNode nonString)
	{
		ValueNode obj = replaceWithObjectiveValue(nonString);
		
		if (!obj.getType().equals("String"))
		{
			// TODO: Can optimize to simply add a .toString() MethodCallNode child.
			String methodCall = obj.generateNovaInput() + ".toString()";
			
			Location location = new Location(nonString.getLocationIn());
			
			nonString = (ValueNode)SyntaxTree.decodeScopeContents(getParent(), methodCall, location, false);
			
			replace(obj, nonString);
		}
		
		return nonString;
	}
	
	/**
	 * If the given TreeNode is a primitive, return the autoboxed form.
	 * Else, simply return the given TreeNode.
	 * 
	 * @param value The value to autobox if needed.
	 * @return The autoboxed primitive value.
	 */
	private ValueNode replaceWithObjectiveValue(ValueNode value)
	{
		ValueNode returned = value.getReturnedNode();
		
		if (returned.isPrimitiveType())
		{
			InstantiationNode autobox = SyntaxUtils.autoboxPrimitive(value);
			
			replace(value, autobox);
			
			return autobox;
		}
		
		return value;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode, Location)
	 */
	@Override
	public BinaryOperatorNode clone(TreeNode temporaryParent, Location locationIn)
	{
		BinaryOperatorNode node = new BinaryOperatorNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given BinaryOperatorNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public BinaryOperatorNode cloneTo(BinaryOperatorNode node)
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
		/**
		 * @see net.fathomsoft.nova.error.SyntaxErrorException#SyntaxErrorException(String)
		 */
		public BinarySyntaxException(String message)
		{
			super(message);
		}
	}
}
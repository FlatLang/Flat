package net.fathomsoft.nova.tree;

import java.util.regex.Matcher;

import net.fathomsoft.nova.Test;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.exceptionhandling.ThrowNode;
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
 * @version	v0.2.12 Jun 1, 2014 at 7:28:35 PM
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
		ValueNode child = (ValueNode)getChild(0);
		
		return child.getReturnedNode();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			builder.append(getChild(i).generateJavaSource());
			
			if (i < getNumChildren() - 1)
			{
				builder.append(' ');
			}
		}
		
		return builder.toString();
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
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			TreeNode child = getChild(i);
			
			if (i > 0)
			{
				builder.append(' ');
			}
			
			builder.append(child.generateCSourceFragment());
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateNovaInput(boolean)
	 */
	@Override
	public String generateNovaInput(boolean outputChildren)
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			TreeNode child = getChild(i);
			
			if (i > 0)
			{
				builder.append(' ');
			}
			
			builder.append(child.generateNovaInput());
		}
		
		return builder.toString();
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
	private static TreeNode generateDivideByZeroCheck(TreeNode parent, String denominator, Location location)
	{
		if (SyntaxUtils.isNumber(denominator))
		{
			try
			{
				int num = Integer.parseInt(denominator);
				
				if (num != 0)
				{
					return null;
				}
				else
				{
					return generateDivideByZeroThrow(parent, location);
				}
			}
			catch (NumberFormatException e)
			{
				double num = Double.parseDouble(denominator);
				
				if (num != 0)
				{
					return null;
				}
				else
				{
					return generateDivideByZeroThrow(parent, location);
				}
			}
		}
		
//		String denominatorVar = "__" + Nova.LANGUAGE_NAME.toUpperCase() + "__zero_check" + checkId++;
//		
//		LocalVariableNode declaration = LocalVariableNode.decodeStatement(parent, "int " + denominatorVar + " = " + denominator, location);
//		parent.addChild(declaration);
		
		IfStatementNode ifStatement = IfStatementNode.decodeStatement(parent, "if (" + denominator + " == 0)", location, true, true);
		parent.addChild(ifStatement);
		
		ThrowNode throwNode = generateDivideByZeroThrow(parent, location);
		ifStatement.addChild(throwNode);
		
		return ifStatement;
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
		if (SyntaxUtils.isLiteral(statement))
		{
			return null;
		}
		if (SyntaxUtils.isMethodCall(statement))
		{
			return null;
		}
		
		Bounds operatorLoc = StringUtils.findStrings(statement, StringUtils.BINARY_OPERATORS);
		
		if (operatorLoc.getStart() < 0)
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
		
		if (operatorLoc.getStart() >= 0)
		{
			BinaryOperatorNode n = new BinaryOperatorNode(parent, location);
			
			Bounds    lhb = new Bounds(0, StringUtils.findNextNonWhitespaceIndex(statement, operatorLoc.getStart() - 1, -1) + 1);
			
			ValueNode lhn = null;
			
			UnaryOperatorNode unary = testUnaryOperator(parent, statement, location);
			
			if (unary != null)
			{
				lhn = unary;
				
				int offset = lhn.getLocationIn().getEnd() - location.getStart();
				
				operatorLoc = StringUtils.findStrings(statement, offset, StringUtils.BINARY_OPERATORS);
			}
			else
			{
				Location lhl = new Location(location);
				lhl.setBounds(lhb.getStart(), lhb.getEnd());
				
				// The left-hand value.
				String lhv = statement.substring(lhb.getStart(), lhb.getEnd());
	
				if (lhv.length() <= 0)
				{
					return null;
				}
				
				// The left-hand node.
				lhn = createNode(parent, lhv, lhl);
				
				if (lhn == null)
				{
//					if (!require)
//					{
						return null;
//					}
//					
//					if (SyntaxUtils.isValidIdentifier(lhv))
//					{
//						SyntaxMessage.error("Unknown identifier '" + lhv + "'", parent, lhl);
//					}
//					else
//					{
//						SyntaxMessage.error("Unknown value of '" + lhv + "'", parent, lhl);
//					}
				}
			
				Location leftLoc = new Location(location);
				leftLoc.setBounds(lhb.getStart(), lhb.getEnd());
				lhn.setLocationIn(leftLoc);
			}
			
			n.addChild(lhn);
			
			String operatorVal = statement.substring(operatorLoc.getStart(), operatorLoc.getEnd());
			
			OperatorNode operator = new OperatorNode(n, location);
			operator.setOperator(operatorVal);
			n.addChild(operator);
			
			int rhIndex = StringUtils.findNextNonWhitespaceIndex(statement, operatorLoc.getEnd());
			
			// Decode the value on the right.
			statement = statement.substring(rhIndex);
			
			matcher.reset(statement);
			
			ValueNode rhn = decodeStatement(parent, statement, matcher, location, require, scope);
			
			if (rhn == null)
			{
//				SyntaxMessage.error("Cannot decode binary operation '" + statement + "'", parent.getFileNode(), location, parent.getController());
				
				return null;
			}

			Location rightLoc = new Location(location);
			rightLoc.setBounds(rhIndex, statement.length());
			rhn.setLocationIn(rightLoc);
			
			n.addChild(rhn);
			
			if (!parent.isWithinExternalContext())
			{
				ClassNode common    = SyntaxUtils.getTypeInCommon(lhn.getReturnedNode(), rhn.getReturnedNode());
				
				String operatorType = operator.getType();
				
				if (common == null)
				{
					ClassNode integerClass = parent.getProgramNode().getClass("Integer");
					
					if (!lhn.getTypeClass().isOfType(integerClass) && !rhn.getTypeClass().isOfType(integerClass) || operatorType == null || !operatorType.equals("bool"))
					{
						if (!require)
						{
							return null;
						}
						
						SyntaxMessage.error("Type '" + lhn.getType() + "' and '" + rhn.getType() + "' are not compatible", n);
					}
				}
				
				if (operatorType != null)
				{
					n.setType(operatorType);
				}
				else
				{
					n.setType(common.getName());
				}
			}
			
			if (operatorVal.equals("/"))
			{
				TreeNode denominator = null;
				
				if (rhn instanceof BinaryOperatorNode)
				{
					BinaryOperatorNode bon = (BinaryOperatorNode)rhn;
					
					denominator = bon.getChild(0);
				}
				else
				{
					denominator = rhn;
				}
				
				if (denominator instanceof IdentifierNode)
				{
					IdentifierNode id = (IdentifierNode)denominator;
					
					TreeNode divideByZeroCheck = generateDivideByZeroCheck(parent, id.generateNovaInput(), location);
					
					parent.addChild(divideByZeroCheck);
				}
				else if (denominator instanceof LiteralNode)
				{
					LiteralNode literal = (LiteralNode)denominator;
					
					String      value   = literal.getValue();
					
					if (SyntaxUtils.isNumber(value))
					{
						double num = 0;
						
						if (SyntaxUtils.isInteger(value))
						{
							num = Integer.parseInt(value);
						}
						else if (SyntaxUtils.isDouble(value))
						{
							num = Double.parseDouble(value);
						}
							
						if (num == 0)
						{
							SyntaxMessage.error("Cannot divide by zero", parent, location);
						}
					}
				}
			}
			
			return n;
		}
		else
		{
			return createNode(parent, statement, location);
		}
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
		if (SyntaxUtils.isLiteral(statement))
		{
			LiteralNode literal = LiteralNode.decodeStatement(parent, statement, location, true, false);
			
			return literal;
		}
		else if (SyntaxUtils.isExternal(parent.getFileNode(), statement))
		{
			String      value   = statement.substring(statement.indexOf('.') + 1);
		
			LiteralNode literal = LiteralNode.decodeStatement(parent, value, location, true, false);
			
			return literal;
		}
		
		return (ValueNode)decodeScopeContents(parent, statement, location, false, false);
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
		String operator    = null;
		
		int    operatorLoc = 0;
		int    count       = 0;
		
		while (count < 2)
		{
			Bounds symbolLoc = StringUtils.findStrings(statement, StringUtils.SYMBOLS);
			
			if (symbolLoc.getStart() < 0)
			{
				return null;
			}
			
			Bounds unaryLoc  = StringUtils.findStrings(statement, StringUtils.UNARY_OPERATORS);
			
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
			
			operatorLoc = symbolLoc.getStart();
			operator    = statement.substring(symbolLoc.getStart(), end);
			
			if (symbolLoc.getStart() == unaryLoc.getStart())
			{
				break;
			}
			
			count++;
		}
		
		if (count >= 2)
		{
			return null;
		}
		
		Location newLoc = new Location(location);
		
		int      start  = -1;
		int      end    = -1;
		
		if (operatorLoc == 0)
		{
			Bounds bounds = StringUtils.findStrings(statement, operatorLoc + operator.length() + 1, StringUtils.SYMBOLS);
			
			if (bounds.getStart() < 0)
			{
				return null;
			}
			
			end = StringUtils.findNextNonWhitespaceIndex(statement, bounds.getStart() - 1, -1) + 1;
			
			if (end <= 0)
			{
				return null;
			}
			
			start = operatorLoc;
		}
		else
		{
			start = 0;
			end   = operatorLoc + operator.length();
		}
			
		newLoc.setOffset(location.getOffset() + start);
		newLoc.setBounds(location.getStart() + start, location.getStart() + end);
		
		String            expression = statement.substring(start, end);
		
		UnaryOperatorNode unary      = UnaryOperatorNode.decodeStatement(parent, expression, newLoc, false, false);
		
		return unary;
	}
	
	/**
	 * Validate the operator values. For instance, concatenates Strings
	 * that are added using the + operator.
	 */
	public void validate()
	{
		validate(getParent(), true);
	}
	
	/**
	 * Validate the operator values. For instance, concatenates Strings
	 * that are added using the + operator.
	 * 
	 * @param parent The parent of the current node being validated.
	 * @param checkParent Whether or not to validate the parent as well.
	 */
	private void validate(TreeNode parent, boolean checkParent)
	{
		if (checkParent && parent instanceof BinaryOperatorNode)
		{
			return;
		}
		
		TreeNode left         = getChild(0);
		OperatorNode operator = (OperatorNode)getChild(0 + 1);
		TreeNode right        = getChild(0 + 2);
		
		if (right instanceof BinaryOperatorNode)
		{
			BinaryOperatorNode bin = (BinaryOperatorNode)right;
			
			bin.validate(this, false);
			
			right = getChild(0 + 2);
		}
		
		if (operator.getOperator().equals("+"))
		{
			boolean leftString  = SyntaxUtils.isString(left);
			boolean rightString = SyntaxUtils.isString(right);
			
			if ((leftString || rightString) && !parent.isWithinExternalContext())
			{
				TreeNode nonString   = null;
				TreeNode replacement = null;
				
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
					if (nonString instanceof LiteralNode)
					{
						LiteralNode       literal = (LiteralNode)nonString;
						InstantiationNode autobox = SyntaxUtils.autoboxPrimitive(parent, literal);
						
						replace(nonString, autobox);
						
						String            methodCall   = autobox.generateNovaInput() + ".toString()";
						InstantiationNode toStringCall = (InstantiationNode)TreeNode.decodeScopeContents(parent, methodCall, literal.getLocationIn(), false);
						
						replace(autobox, toStringCall);
						
						replacement = toStringCall;
					}
					else if (nonString instanceof ValueNode)
					{
						ValueNode      value    = (ValueNode)nonString;
						
						IdentifierNode accessed = value.getLastAccessedNode();
						
						if ((accessed == null || accessed.isPrimitive()) || accessed.isPrimitive())
						{
							InstantiationNode autobox = SyntaxUtils.autoboxPrimitive(parent, value);
							
							replace(nonString, autobox);
							
							String            methodCall   = autobox.generateNovaInput() + ".toString()";
							
							InstantiationNode toStringCall = (InstantiationNode)TreeNode.decodeScopeContents(parent, methodCall, value.getLocationIn(), false);
							
							replace(autobox, toStringCall);
							
							replacement = toStringCall;
						}
					}
				}
				
				if (replacement != null)
				{
					if (!leftString)
					{
						left = replacement;
					}
					else if (!rightString)
					{
						right = replacement;
					}
				}
				
				String   statement = left.generateNovaInput() + ".concat(" + right.generateNovaInput() + ")";
				
				TreeNode strConcat = decodeScopeContents(parent, statement, left.getLocationIn(), false);
				
				parent.replace(this, strConcat);
			}
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
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
}

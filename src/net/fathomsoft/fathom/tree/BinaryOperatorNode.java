/**
 * The Fathom Programming Language. Write Unbelievable Code.
 *  Copyright (C) 2014  Braden Steffaniak <BradenSteffaniak@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.fathomsoft.fathom.tree;

import java.util.regex.Matcher;

import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.tree.exceptionhandling.ThrowNode;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.StringUtils;
import net.fathomsoft.fathom.util.SyntaxUtils;
 
/**
 * TreeNode extension that represents the use of a binary operator.
 * For example: "153 + 4 / 2" represents two binary operator nodes.
 * The first node consists of the "153" as the left hand value, and
 * the binary node "4 / 2"
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:20:35 PM
 * @version	v0.2.4 May 17, 2014 at 9:55:04 PM
 */
public class BinaryOperatorNode extends TreeNode
{
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateJavaSource());
			
			if (i < getChildren().size() - 1)
			{
				builder.append(' ');
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		return generateCSourceFragment();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append(child.generateCSourceFragment());
			
			if (i < getChildren().size() - 1)
			{
				builder.append(' ');
			}
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
		
//		String denominatorVar = "__" + Fathom.LANGUAGE_NAME.toUpperCase() + "__zero_check" + checkId++;
//		
//		LocalVariableNode declaration = LocalVariableNode.decodeStatement(parent, "int " + denominatorVar + " = " + denominator, location);
//		parent.addChild(declaration);
		
		IfStatementNode ifStatement = IfStatementNode.decodeStatement(parent, "if (" + denominator + " == 0)", location);
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
		ThrowNode throwNode = ThrowNode.decodeStatement(parent, "throw new DivideByZeroException()", location);
		
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
	 * @return The generated TreeNode, if it was possible to translated it
	 * 		into a BinaryOperatorNode.
	 */
	public static TreeNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		if (SyntaxUtils.isMethodCall(statement))
		{
			return null;
		}
		if (!Regex.matches(statement, 0, Patterns.PRE_OPERATORS))
		{
			return null;
		}
		
		// Pattern used to find word boundaries. 
		Matcher matcher = Patterns.PRE_OPERATORS.matcher(statement);
		
		TreeNode node   = decodeStatement(parent, statement, matcher, location);
		
		if (node == null)
		{
			SyntaxMessage.error("Cannot decode binary operation '" + statement + "'", parent.getFileNode(), location, parent.getController());
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
	 * @return The generated TreeNode, if it was possible to translated it
	 * 		into a BinaryOperatorNode.
	 */
	private static TreeNode decodeStatement(TreeNode parent, String statement, Matcher matcher, Location location)
	{
		Bounds operatorLoc = StringUtils.findStrings(statement, StringUtils.BINARY_OPERATORS);
		
		if (operatorLoc.getStart() >= 0)
		{
			BinaryOperatorNode node = new BinaryOperatorNode();
			node.setLocationIn(location);
			
			Bounds lhb = new Bounds(0, StringUtils.findNextNonWhitespaceIndex(statement, operatorLoc.getStart() - 1, -1) + 1);
			
			// Decode the value on the left.
//			Bounds bounds  = Regex.boundsOf(statement, Patterns.PRE_OPERATORS);
//			Bounds bounds2 = Regex.boundsOf(statement, Patterns.POST_OPERATORS);
			
//			bounds.setEnd(StringUtils.findNextNonWhitespaceIndex(statement, bounds.getEnd() - 1, -1) + 1);
			
			// The left-hand value.
			String   lhv = statement.substring(lhb.getStart(), lhb.getEnd());
			
			// The left-hand node.
			TreeNode lhn = createNode(parent, lhv, location);
			
			if (lhn == null)
			{
				if (SyntaxUtils.isValidIdentifier(lhv))
				{
					SyntaxMessage.error("Unknown identifier '" + lhv + "'", parent.getFileNode(), location, parent.getController());
				}
				else
				{
					SyntaxMessage.error("Unknown value of '" + lhv + "'", parent.getFileNode(), location, parent.getController());
				}
				
				return null;
			}
			
			Location leftLoc = new Location(location);
			leftLoc.setBounds(lhb.getStart(), lhb.getEnd());
			lhn.setLocationIn(leftLoc);
			
			node.addChild(lhn);
			
			String operatorVal = statement.substring(operatorLoc.getStart(), operatorLoc.getEnd());
			
			OperatorNode operator = new OperatorNode();
			operator.setOperator(operatorVal);
			node.addChild(operator);
			
			int rhIndex = StringUtils.findNextNonWhitespaceIndex(statement, operatorLoc.getEnd());
			
			// Decode the value on the right.
			statement = statement.substring(rhIndex);
			
			matcher.reset(statement);
			TreeNode rhn = decodeStatement(parent, statement, matcher, location);
			
			if (rhn == null)
			{
//				SyntaxMessage.error("Cannot decode binary operation '" + statement + "'", parent.getFileNode(), location, parent.getController());
				
				return null;
			}

			Location rightLoc = new Location(location);
			leftLoc.setBounds(rhIndex, statement.length());
			rhn.setLocationIn(rightLoc);
			
			node.addChild(rhn);
			
			if (operatorVal.equals("/"))
			{
				IdentifierNode id = (IdentifierNode)rhn;
				
				TreeNode divideByZeroCheck = generateDivideByZeroCheck(parent, id.getName(), location);
				
				parent.addChild(divideByZeroCheck);
			}
			
			return node;
		}
		else
		{
			return createNode(parent, statement, location);
			
//			ClassNode thisClass  = (ClassNode)parentNode.getAncestorOfType(ClassNode.class, true);
//			ClassNode nodesClass = (ClassNode)node.getAncestorOfType(ClassNode.class, true);
//			
//			if (node instanceof VariableNode)
//			{
//				if (thisClass == nodesClass)
//				{
//					
//				}
//			}
		}
	}
	
	/**
	 * Get the node that represents value for the statement.
	 * 
	 * @param parent The parent of the statement.
	 * @param statement The statement containing the value.
	 * @param location The location of the statement in the source code.
	 * @return The generated TreeNode instance.
	 */
	private static TreeNode createNode(TreeNode parent, String statement, Location location)
	{
		if (SyntaxUtils.isLiteral(statement))
		{
			LiteralNode literal = new LiteralNode();
			
			literal.setValue(statement, parent.isWithinExternalContext());
			
			return literal;
		}
//		else if (SyntaxUtils.isInstantiation(statement))
//		{
//			InstantiationNode node = InstantiationNode.decodeStatement(parent, statement, location);
//			
//			return node;
//		}
//		else if (SyntaxUtils.isMethodCall(statement))
//		{
//			MethodCallNode node = MethodCallNode.decodeStatement(parent, statement, location);
//			
//			return node;
//		}
//		else if (SyntaxUtils.isValidIdentifier(statement))
//		{
//			VariableNode node = TreeNode.getExistingNode(parent, statement);
//			
//			if (node != null)
//			{
//				node = node.clone();
//				
//				return node;
//			}
//		}
		else if (SyntaxUtils.isExternal(parent.getFileNode(), statement))
		{
			String value = statement.substring(statement.indexOf('.') + 1);
		
			LiteralNode node = new LiteralNode();
			node.setValue(value, parent.isWithinExternalContext());
			
			return node;
		}
//		else if (SyntaxUtils.isValidArrayAccess(statement))
//		{
//			ArrayAccessNode node = ArrayAccessNode.decodeStatement(parent, statement, location);
//			
//			return node;
//		}
		
		return decodeScopeContents(parent, statement, location);
	}
	
	public void validate()
	{
		validate(getParent(), true);
	}
	
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
			if (SyntaxUtils.isString(left) && SyntaxUtils.isString(right) && !getParent().isWithinExternalContext())
			{
				String   statement = left.generateFathomInput() + ".concat(" + right.generateFathomInput() + ")";
				
				TreeNode strConcat = decodeScopeContents(parent, statement, left.getLocationIn());
				
				parent.replace(this, strConcat);
			}
		}
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public BinaryOperatorNode clone()
	{
		BinaryOperatorNode node = new BinaryOperatorNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given BinaryOperatorNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public BinaryOperatorNode clone(BinaryOperatorNode node)
	{
		super.clone(node);
		
		return node;
	}
}

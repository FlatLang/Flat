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
 * @version	v0.2 Mar 30, 2014 at 2:40:35 PM
 */
public class BinaryOperatorNode extends TreeNode
{
	private static int checkId;

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateJavaSourceOutput());
			
			if (i < getChildren().size() - 1)
			{
				builder.append(' ');
			}
		}
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		return null;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
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
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return generateCSourceOutput();
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
		if (!Regex.matches(statement, 0, Patterns.PRE_OPERATORS))
		{
			return null;
		}
		
		// Pattern used to find word boundaries. 
		Matcher matcher = Patterns.PRE_OPERATORS.matcher(statement);
		
		return decodeStatement(parent, statement, matcher, location);
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
	private static TreeNode decodeStatement(TreeNode parentNode, String statement, Matcher matcher, Location location)
	{
		if (matcher.find())
		{
			BinaryOperatorNode node = new BinaryOperatorNode();
			node.setLocationIn(location);
			
			// Decode the value on the left.
			Bounds bounds  = Regex.boundsOf(statement, Patterns.PRE_OPERATORS);
			Bounds bounds2 = Regex.boundsOf(statement, Patterns.POST_OPERATORS);
			
			bounds.setEnd(StringUtils.findNextNonWhitespaceIndex(statement, bounds.getEnd() - 1, -1) + 1);
			
			// The left-hand value.
			String   lhv = statement.substring(bounds.getStart(), bounds.getEnd());
			
			// The left-hand node.
			TreeNode lhn = createNode(parentNode, lhv, location);
			
			node.addChild(lhn);
			
			// Decode the operator.
			Bounds opBounds = new Bounds(0, 0);
			opBounds.setStart(StringUtils.findNextNonWhitespaceIndex(statement, bounds.getEnd()));
			opBounds.setEnd(StringUtils.findNextNonWhitespaceIndex(statement, bounds2.getStart() - 1, -1) + 1);
			
			String operatorVal    = statement.substring(opBounds.getStart(), opBounds.getEnd());
			
			OperatorNode operator = new OperatorNode();
			operator.setOperator(operatorVal);
			node.addChild(operator);
			
			// Decode the value on the right.
			statement = statement.substring(bounds2.getStart());
			
			matcher.reset(statement);
			TreeNode rhn = decodeStatement(parentNode, statement, matcher, location);
			
			node.addChild(rhn);
			
			if (operatorVal.equals("/"))
			{
				TreeNode divideByZeroCheck = generateDivideByZeroCheck(parentNode, rhn.generateCSourceFragment(), location);
				
				parentNode.addChild(divideByZeroCheck);
			}
			
			return node;
		}
		else if (matcher.hitEnd())
		{
			return createNode(parentNode, statement, location);
			
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
		
		return null;
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
//		IdentifierNode node = TreeNode.getExistingNode(parentNode, statement);
//		
//		if (node != null)
//		{
//			node = node.clone();
//			
//			String visibility = "";
//			
//			if (node instanceof FieldNode)
//			{
//				FieldNode field = (FieldNode)node;
//				
//				if (field.getVisibility() == FieldNode.PRIVATE)
//				{
//					visibility = MethodNode.getObjectReferenceIdentifier() + "->" + "prv->";
//				}
//			}
//			
//			return node;
//		}
		
		if (SyntaxUtils.isLiteral(statement))
		{
			LiteralNode literal = new LiteralNode();
			
			literal.setValue(statement, parent.isWithinExternalContext());
			
			return literal;
		}
		else if (SyntaxUtils.isInstantiation(statement))
		{
			InstantiationNode node = InstantiationNode.decodeStatement(parent, statement, location);
			
			return node;
		}
		else if (SyntaxUtils.isMethodCall(statement))
		{
			MethodCallNode node = MethodCallNode.decodeStatement(parent, statement, location);
			
			return node;
		}
		else if (SyntaxUtils.isValidIdentifier(statement))
		{
			IdentifierNode node = TreeNode.getExistingNode(parent, statement);
			
			if (node != null)
			{
				node = node.clone();
				
				return node;
			}
			if (statement.equals("this"))
			{
				LiteralNode literal = new LiteralNode();
				
				literal.setValue(MethodNode.getObjectReferenceIdentifier(), parent.isWithinExternalContext());
				
				return literal;
			}
		}
		else if (SyntaxUtils.isValidArrayAccess(statement))
		{
			ArrayAccessNode node = ArrayAccessNode.decodeStatement(parent, statement, location);
			
			return node;
		}
		
		SyntaxMessage.error("Could not parse operation '" + statement + "'", location);
		
		return null;
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
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			node.addChild(child.clone());
		}
		
		return node;
	}
}

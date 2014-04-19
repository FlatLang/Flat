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

import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.tree.variables.LocalVariableNode;
import net.fathomsoft.fathom.tree.variables.VariableNode;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.StringUtils;
import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * TreeNode extension that contains information describing a variable
 * assignment.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:19:44 PM
 * @version	v0.2 March 28, 2014 at 9:19:44 PM
 */
public class AssignmentNode extends TreeNode
{
	/**
	 * Get the node that stores the variable that is having its value
	 * assigned. In other words, the left hand value of the equation.
	 * For instance, in the statement: "int j = 35" int j is the left
	 * hand value of the equation.<br>
	 * <br>
	 * This does not return a VariableNode type because an ArrayAccessNode
	 * is also a valid left hand value to an equation.
	 * 
	 * @return The TreeNode that holds the value of the variable that
	 * 		is to be assigned.
	 */
	public TreeNode getVariableNode()
	{
		return getChild(0);
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getChild(0).generateJavaSourceOutput()).append(" = ");
		
		for (int i = 1; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateJavaSourceOutput());
		}
		
		builder.append(';').append('\n');
		
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
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderFragment()
	 */
	@Override
	public String generateCHeaderFragment()
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
		
		builder.append(generateCSourceFragment());
		
		builder.append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getVariableNode().generateCSourceFragment());
		
		builder.append(" = ");
		
		for (int i = 1; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append(child.generateCSourceFragment());
		}
		
		return builder.toString();
	}
	
	/**
	 * Decode the given statement into an AssignmentNode if possible. If
	 * it is not possible, then null is returned.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>int variableName = 45</li>
	 * 	<li>personsName = "Bob"</li>
	 * 	<li>Person myPeep = new Person(54)</li>
	 * 	<li>area = width * height / 2</li>
	 * 	<li>int newSize = array.getSize() + 5</li>
	 * </ul>
	 * 
	 * @param parent The parent of the current statement.
	 * @param statement The statement to decode into an AssignmentNode.
	 * @param location The location of the statement in the source code.
	 * @return The new AssignmentNode if it decodes properly. If not,
	 * 		it returns null.
	 */
	public static AssignmentNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		return decodeStatement(parent, statement, location, true);
	}
	
	/**
	 * Decode the given statement into an AssignmentNode if possible. If
	 * it is not possible, then null is returned.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>int variableName = 45</li>
	 * 	<li>personsName = "Bob"</li>
	 * 	<li>Person myPeep = new Person(54)</li>
	 * 	<li>area = width * height / 2</li>
	 * 	<li>int newSize = array.getSize() + 5</li>
	 * </ul>
	 * 
	 * @param parent The parent of the current statement.
	 * @param statement The statement to decode into an AssignmentNode.
	 * @param location The location of the statement in the source code.
	 * @param addDeclaration Whether or not to add the declaration to the
	 * 		nearest scope, if the left hand value of the equation is a
	 * 		variable declaration.
	 * @return The new AssignmentNode if it decodes properly. If not,
	 * 		it returns null.
	 */
	public static AssignmentNode decodeStatement(TreeNode parent, String statement, Location location, boolean addDeclaration)
	{
		if (!SyntaxUtils.isVariableAssignment(statement))
		{
			return null;
		}
		
		AssignmentNode n = new AssignmentNode();
		
		Bounds bounds    = Regex.boundsOf(statement, Patterns.PRE_EQUALS_SIGN);
		
		int equalsIndex  = StringUtils.findNextNonWhitespaceIndex(statement, bounds.getEnd());
		
		int endIndex     = StringUtils.findNextNonWhitespaceIndex(statement, equalsIndex - 1, -1) + 1;
		
		String variable  = statement.substring(0, endIndex);
		
		TreeNode varNode = null;
		
		if (SyntaxUtils.isValidIdentifierAccess(variable))
		{
			if (SyntaxUtils.isValidArrayAccess(variable))
			{
				varNode = ArrayAccessNode.decodeStatement(parent, variable, location);
			}
			else
			{
				varNode = (VariableNode)TreeNode.getExistingNode(parent, variable);
				
				if (varNode == null)
				{
					SyntaxMessage.error("Variable '" + variable + "' was not declared", location);
					
					return null;
				}
				
				varNode = varNode.clone();
			}
		}
		else
		{
			LocalVariableNode var = LocalVariableNode.decodeStatement(parent, variable, location);
			
			if (addDeclaration)
			{
				if (var != null)
				{
					TreeNode scope = getAncestorWithScope(parent);
					
					if (scope != null)
					{
						scope.addChild(var.clone());
					}
					
					varNode = var;
				}
				else
				{
					varNode = TreeNode.decodeStatement(parent, variable, location);
					
					if (varNode == null)
					{
						VariableNode vNode = new VariableNode();
						vNode.setName(variable);
						
						varNode = vNode;
					}
					else if (varNode instanceof ArrayAccessNode == false)
					{
						SyntaxMessage.error("Undefined variable '" + variable + "'", location);
						
						return null;
					}
				}
			}
			else
			{
//				IdentifierNode id = new IdentifierNode();
//				id.setName(var.getName());
				
				varNode = var;
			}
		}
		
		n.addChild(varNode);
		
		int      rhsIndex = StringUtils.findNextNonWhitespaceIndex(statement, equalsIndex + 1);
		
		// Right-hand side of the equation.
		String   rhs      = statement.substring(rhsIndex);
		
		Location newLoc   = new Location(location.getLineNumber(), location.getOffset() + rhsIndex, location.getOffset() + statement.length());
		
		TreeNode child    = decodeRightHandSide(parent, rhs, newLoc);
		
		n.addChild(child);
		
		return n;
	}
	
	/**
	 * Decode the right hand side of an assignment into an TreeNode if
	 * possible. If it is not possible, then null is returned. The right
	 * hand side of an assignment must represent a value.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>45</li>
	 * 	<li>"Bob"</li>
	 * 	<li>new Person(54)</li>
	 * 	<li>width * height / 2</li>
	 * 	<li>array.getSize() + 5</li>
	 * </ul>
	 * 
	 * @param parent The parent of the current statement.
	 * @param rhs The right hand side to decode into an AssignmentNode.
	 * @param location The location of the statement in the source code.
	 * @return The new TreeNode if it decodes properly. If not,
	 * 		it returns null.
	 */
	public static TreeNode decodeRightHandSide(TreeNode parent, String rhs, Location location)
	{
		TreeNode child    = MethodCallNode.decodeStatement(parent, rhs, location);

		if (child == null)
		{
			child = TreeNode.getExistingNode(parent, rhs);
			
			if (child != null)
			{
				child = child.clone();
			}
		}
		if (child == null)
		{
			child = BinaryOperatorNode.decodeStatement(parent, rhs, location);
		}
		if (child == null)
		{
			child = TreeNode.decodeStatement(parent, rhs, location);
		}
		if (child == null)
		{
			LiteralNode node = new LiteralNode();
			node.setValue(rhs, parent.isWithinExternalContext());
			
			child = node;
		}
		
		return child;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public AssignmentNode clone()
	{
		AssignmentNode node = new AssignmentNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given AssignmentNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public AssignmentNode clone(AssignmentNode node)
	{
		super.clone(node);
		
		return node;
	}
}
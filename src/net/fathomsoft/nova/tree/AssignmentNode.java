/**
 * The Nova Programming Language. Write Explosive Code.
 * Copyright (C) 2014  Braden Steffaniak <BradenSteffaniak@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * The Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.VariableNode;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * TreeNode extension that contains information describing a variable
 * assignment.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:19:44 PM
 * @version	v0.2.4 May 17, 2014 at 9:55:04 PM
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
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getChild(0).generateJavaSource()).append(" = ");
		
		for (int i = 1; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateJavaSource());
		}
		
		builder.append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(generateCSourceFragment());
		
		builder.append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
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
		
		int  equalsIndex = SyntaxUtils.findCharInBaseScope(statement, '=');
		int     endIndex = StringUtils.findNextNonWhitespaceIndex(statement, equalsIndex - 1, -1) + 1;
		
		String  variable = statement.substring(0, endIndex);
		
		Location  varLoc = new Location(location);
		varLoc.getBounds().setEnd(varLoc.getStart() + endIndex);
		
		TreeNode varNode = decodeScopeContents(parent, variable, location);
		
		if (varNode == null)
		{
			SyntaxMessage.error("Undeclared variable '" + variable + "'", parent.getFileNode(), location, parent.getController());
			
			return null;
		}
		
		varNode.setLocationIn(varLoc);
		
		if (addDeclaration)
		{
			if (varNode instanceof VariableNode)
			{
				VariableNode var = (VariableNode)varNode;
				
				if (var.isDeclaration())
				{
					TreeNode scope = getAncestorWithScope(parent);
					
					if (scope != null)
					{
						scope.addChild(varNode);
						
						varNode = var.clone();
					}
				}
			}
		}
		
		AssignmentNode n = new AssignmentNode();
		
		n.addChild(varNode);
		
		int    rhsIndex = StringUtils.findNextNonWhitespaceIndex(statement, equalsIndex + 1);
		
		// Right-hand side of the equation.
		String      rhs = statement.substring(rhsIndex);
		
		Location newLoc = new Location(location);
		newLoc.setBounds(location.getStart() + rhsIndex, location.getStart() + statement.length());
		
		TreeNode  child = decodeRightHandSide(parent, rhs, newLoc);
		
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
		TreeNode child = BinaryOperatorNode.decodeStatement(parent, rhs, location);
		
		if (child == null)
		{
			child = decodeScopeContents(parent, rhs, location);//MethodCallNode.decodeStatement(parent, rhs, location);
		}
		
//		if (child == null)
//		{
//			child = InstantiationNode.decodeStatement(parent, rhs, location);
//		}
//		if (child == null)
//		{
//			child = TreeNode.getExistingNode(parent, rhs);
//			
//			if (child != null)
//			{
//				child = child.clone();
//			}
//		}
//		if (child == null)
//		{
//			child = TreeNode.decodeStatement(parent, rhs, location);
//		}
		if (child == null)
		{
			if (SyntaxUtils.isExternal(parent.getFileNode(), rhs))
			{
				rhs = rhs.substring(rhs.indexOf('.') + 1);
			}
			
			LiteralNode node = new LiteralNode();
			node.setValue(rhs, parent.isWithinExternalContext());
			
			child = node;
		}
		
		return child;
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone()
	 */
	@Override
	public AssignmentNode clone()
	{
		AssignmentNode node = new AssignmentNode();
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given AssignmentNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public AssignmentNode cloneTo(AssignmentNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
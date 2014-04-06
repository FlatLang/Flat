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
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;

/**
 * LoopNode extension that represents the declaration of a "for loop"
 * node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:55:15 PM
 * @version	v0.2 Apr 2, 2014 at 8:15:18 PM
 */
public class ForLoopNode extends LoopNode
{
	private LocalVariableNode declarationNode;
	
	/**
	 * Instantiate a new ForLoopNode and initialize its default values.
	 */
	public ForLoopNode()
	{
		ArgumentListNode argumentsNode = new ArgumentListNode();
		
		getChildren().add(argumentsNode);
	}
	
	/**
	 * Get the ArgumentListNode instance that contains the initialization,
	 * condition, and update nodes that instruct the for loop.
	 * 
	 * @return The ArgumentListNode instance containing the arguments of
	 * 		the for loop.
	 */
	public ArgumentListNode getArgumentListNode()
	{
		return (ArgumentListNode)getChild(1);
	}
	
	/**
	 * Get the TreeNode that describes the initialization section of the
	 * for loop. For instance: "for (int i = 0; i < 10; i++)" the first
	 * section containing "int i = 0" is the initialization section.
	 * 
	 * @return The TreeNode instance that describes the initialization
	 * 		section of the for loop.
	 */
	public AssignmentNode getInitializationNode()
	{
		return (AssignmentNode)getArgumentListNode().getChild(0);
	}
	
	/**
	 * Get the TreeNode that describes the condition section of the for
	 * loop. For instance: "for (int i = 0; i < 10; i++)" the middle
	 * section containing "i < 10" is the condition section.
	 * 
	 * @return The TreeNode instance that describes the condition section
	 * 		of the for loop.
	 */
	public TreeNode getConditionNode()
	{
		return getArgumentListNode().getChild(1);
	}
	
	/**
	 * Get the TreeNode that describes the update section of the for loop.
	 * For instance: "for (int i = 0; i < 10; i++)" the last section
	 * containing "i++" is the update section.
	 * 
	 * @return The TreeNode instance that describes the update section of
	 * 		the for loop.
	 */
	public TreeNode getUpdateNode()
	{
		return getArgumentListNode().getChild(2);
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		return null;
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
		StringBuilder  builder = new StringBuilder();
		
		AssignmentNode initialization = getInitializationNode();
		TreeNode       condition      = getConditionNode();
		TreeNode       update         = getUpdateNode();
		
//		builder.append('{').append('\n');
//		
//		if (declarationNode != null)
//		{
//			builder.append(declarationNode.generateCSourceOutput()).append('\n');
//		}
		
		builder.append("for (");
		
		if (initialization != null)
		{
			builder.append(initialization.generateCSourceFragment());
		}
		
		builder.append(';').append(' ');
		
		if (condition != null)
		{
			builder.append(condition.generateCSourceFragment());
		}
		
		builder.append(';').append(' ');
		
		if (update != null)
		{
			builder.append(update.generateCSourceFragment());
		}
		
		builder.append(')').append('\n');
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != getArgumentListNode())
			{
				builder.append(child.generateCSourceOutput());
			}
		}
		
//		builder.append('}').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return null;
	}
	
	/**
	 * Decode the given statement into a ForLoopNode instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>for (int i = 0; i < array.size(); i++)</li>
	 * 	<li>for (index = size - 1; index >= 0; index -= 2) <i>(Where index and size are already declared variables)</i></li>
	 * 	<li>for (;;) <i>(This is an infinite loop. Preferably use "while (true)" instead.)</i></li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ForLoopNode instance.
	 * @param location The location of the statement in the source code.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ForLoopNode.
	 */
	public static ForLoopNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		if (Regex.matches(statement, 0, Patterns.PRE_FOR))
		{
			ForLoopNode n = new ForLoopNode();
			
			Bounds bounds = Regex.boundsOf(statement, Patterns.FOR_CONTENTS);
			
			if (bounds.getStart() >= 0)
			{
				Location newLoc    = new Location();
				newLoc.setLineNumber(location.getLineNumber());
				newLoc.setOffset(location.getOffset() + bounds.getStart());
				
				String contents    = statement.substring(bounds.getStart(), bounds.getEnd());
				
				String arguments[] = contents.split("\\s*;\\s*");
				
				AssignmentNode initialization = AssignmentNode.decodeStatement(parent, arguments[0], newLoc, false);
				n.getArgumentListNode().addChild(initialization);
				
				if (initialization.getVariableNode() instanceof LocalVariableNode)
				{
					LocalVariableNode var = (LocalVariableNode)initialization.getVariableNode().clone();
//					n.declarationNode = var;
					parent.addToNearestScope(var);
				}
				
				TreeNode condition = BinaryOperatorNode.decodeStatement(parent, arguments[1], newLoc);
				n.getArgumentListNode().addChild(condition);
				
				UnaryOperatorNode unaryUpdate = UnaryOperatorNode.decodeStatement(parent, arguments[2], newLoc);
				
				if (unaryUpdate != null)
				{
					n.getArgumentListNode().addChild(unaryUpdate);
				}
				else
				{
					AssignmentNode update = AssignmentNode.decodeStatement(parent, arguments[2], newLoc);
					
					n.getArgumentListNode().addChild(update);
				}
				
				if (n.declarationNode != null)
				{
					n.declarationNode.detach();
				}
				
				return n;
			}
			else
			{
				SyntaxMessage.error("For loop missing arguments", location);
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public ForLoopNode clone()
	{
		ForLoopNode node = new ForLoopNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given ForLoopNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ForLoopNode clone(ForLoopNode node)
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			node.addChild(child.clone());
		}
		
		return node;
	}
}
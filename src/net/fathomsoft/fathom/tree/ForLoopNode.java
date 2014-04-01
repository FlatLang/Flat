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
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:55:15 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:55:15 PM
 * @version	v
 */
public class ForLoopNode extends LoopNode
{
	private LocalVariableNode declarationNode;
	
	public ForLoopNode()
	{
		ArgumentListNode argumentsNode = new ArgumentListNode();
		
		getChildren().add(argumentsNode);
	}
	
	public ArgumentListNode getArgumentListNode()
	{
		return (ArgumentListNode)getChild(1);
	}
	
	public AssignmentNode getInitializationNode()
	{
		return (AssignmentNode)getArgumentListNode().getChild(0);
	}
	
	public TreeNode getConditionNode()
	{
		return getArgumentListNode().getChild(1);
	}
	
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
	
	public static ForLoopNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		if (Regex.matches(statement, 0, Patterns.PRE_FOR))
		{
			ForLoopNode n = new ForLoopNode();
			
			Bounds bounds = Regex.boundsOf(statement, Patterns.POST_FOR);
			
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
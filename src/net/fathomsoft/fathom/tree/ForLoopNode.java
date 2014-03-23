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
	public ForLoopNode()
	{
		ArgumentListNode argumentsNode = new ArgumentListNode();
		
		addChild(argumentsNode);
	}
	
	public ArgumentListNode getArgumentListNode()
	{
		return (ArgumentListNode)getChild(0);
	}
	
	public AssignmentNode getInitializationNode()
	{
		if (getArgumentListNode().getChildren().size() <= 0)
		{
			return null;
		}
		
		return (AssignmentNode)getArgumentListNode().getChild(0);
	}
	
	public TreeNode getConditionNode()
	{
		if (getArgumentListNode().getChildren().size() <= 1)
		{
			return null;
		}
		
		return getArgumentListNode().getChild(1);
	}
	
	public AssignmentNode getUpdateNode()
	{
		if (getArgumentListNode().getChildren().size() <= 2)
		{
			return null;
		}
		
		return (AssignmentNode)getArgumentListNode().getChild(2);
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
		StringBuilder builder = new StringBuilder();
		
		AssignmentNode initialization = getInitializationNode();
		TreeNode       condition      = getConditionNode();
		AssignmentNode update         = getUpdateNode();
		
		builder.append('{').append('\n');
		
//		if (initialization != null)
//		{
//			if (initialization.isDeclared())
//			{
//				builder.append(initialization.generateDeclaration()).append('\n');
//			}
//		}
		
		builder.append("for (");
		
		if (initialization != null)
		{
			builder.append(initialization.generateCSourceFragment());
		}
		
		builder.append(';').append(' ');
		
		if (condition != null)
		{
			builder.append(condition.generateCSourceOutput());
		}
		
		builder.append(';').append(' ');
		
		if (update != null)
		{
			builder.append(update.generateCSourceFragment());
		}
		
		builder.append(')').append('\n');
		builder.append('{').append('\n');
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != getArgumentListNode())
			{
				builder.append(child.generateCSourceOutput());
				
				if (child instanceof MethodCallNode)
				{
					builder.append(';').append('\n');
				}
			}
		}
		
		builder.append('}').append('\n');
		builder.append('}').append('\n');
		
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
				
				AssignmentNode initialization = AssignmentNode.decodeStatement(parent, arguments[0], newLoc);
				
				if (initialization != null)
				{
					n.getArgumentListNode().addChild(initialization);
				}
				
				TreeNode condition = BinaryOperatorNode.decodeStatement(parent, arguments[1], newLoc);
				
				if (condition != null)
				{
					n.getArgumentListNode().addChild(condition);
				}
				
				AssignmentNode update = AssignmentNode.decodeStatement(parent, arguments[2], newLoc);
				
				if (update != null)
				{
					n.getArgumentListNode().addChild(update);
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
		ForLoopNode clone = new ForLoopNode();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			clone.addChild(child.clone());
		}
		
		return clone;
	}
}
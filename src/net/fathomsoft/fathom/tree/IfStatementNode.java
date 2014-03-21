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

import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.error.SyntaxMessage;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:57:13 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:57:13 PM
 * @version	v
 */
public class IfStatementNode extends TreeNode
{
	public IfStatementNode()
	{
		ArgumentListNode condition = new ArgumentListNode();
		
		addChild(condition);
	}
	
	public ArgumentListNode getCondition()
	{
		return (ArgumentListNode)getChild(0);
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
		
		builder.append("if (");
		
		ArgumentListNode condition = getCondition();
		
		for (int i = 0; i < condition.getChildren().size(); i++)
		{
			TreeNode child = condition.getChild(i);
			
			builder.append(child.generateCSourceOutput());
		}
		
		builder.append(')').append('\n');
		builder.append('{').append('\n');
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != getCondition())
			{
				builder.append(child.generateCSourceOutput());
			}
		}
		
		builder.append('}').append('\n');
		
		return builder.toString();
	}
	
	public static IfStatementNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		if (Regex.matches(statement, 0, Patterns.PRE_IF))
		{
			IfStatementNode n = new IfStatementNode();
			
			Bounds bounds = Regex.boundsOf(statement, Patterns.POST_IF);
			
			if (bounds.getStart() >= 0)
			{
				String contents = statement.substring(bounds.getStart(), bounds.getEnd());
				
				Location newLoc = new Location();
				newLoc.setLineNumber(location.getLineNumber());
				newLoc.setOffset(location.getOffset() + bounds.getStart());
				
				TreeNode child = BinaryOperatorNode.decodeStatement(parent, contents, newLoc);
				
				n.getCondition().addChild(child);
				
				return n;
			}
			else
			{
				SyntaxMessage.error("If statement missing condition", location);
			}
		}
		
		return null;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public IfStatementNode clone()
	{
		IfStatementNode clone = new IfStatementNode();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			clone.addChild(child.clone());
		}
		
		return clone;
	}
}

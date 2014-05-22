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
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * TreeNode extension that represents the declaration of an "if statement"
 * node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:57:13 PM
 * @version	v0.2.4 May 17, 2014 at 9:55:04 PM
 */
public class IfStatementNode extends TreeNode
{
	/**
	 * Instantiate a new IfStatementNode and initialize the default
	 * values.
	 */
	public IfStatementNode()
	{
		ArgumentListNode condition = new ArgumentListNode();
		ScopeNode        scopeNode = new ScopeNode();
		
		addChild(condition);
		addChild(scopeNode);
	}
	
	/**
	 * Get the ArgumentListNode that contains the condition for the if
	 * statement.
	 * 
	 * @return The ArgumentListNode instance.
	 */
	public ArgumentListNode getCondition()
	{
		return (ArgumentListNode)getChild(0);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#getScopeNode()
	 */
	@Override
	public ScopeNode getScopeNode()
	{
		return (ScopeNode)getChild(1);
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
		if (child instanceof ScopeNode || child instanceof ArgumentListNode)
		{
			super.addChild(child);
		}
		else
		{
			getScopeNode().addChild(child);
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		return null;
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("if (");
		
		ArgumentListNode condition = getCondition();
		
		for (int i = 0; i < condition.getChildren().size(); i++)
		{
			TreeNode child = condition.getChild(i);
			
			builder.append(child.generateCSourceFragment());
		}
		
		builder.append(')').append('\n');
//		builder.append('{').append('\n');
//		
//		for (int i = 0; i < getChildren().size(); i++)
//		{
//			TreeNode child = getChild(i);
//			
//			if (child != getCondition())
//			{
//				builder.append(child.generateCSourceOutput());
//			}
//		}
//		
//		builder.append('}').append('\n');
		builder.append(getScopeNode().generateCSource());
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return null;
	}
	
	/**
	 * Decode the given statement into a IfStatementNode instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>if (index >= array.size())</li>
	 * 	<li>if (getParent().isAlive())</li>
	 * 	<li>if (!person.canWalk() && !person.isVegetable())</li>
	 * 	<li>if ((age + 2 >= 21 && gender == "male") || gender == "female")</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		IfStatementNode instance.
	 * @param location The location of the statement in the source code.
	 * @return The generated node, if it was possible to translated it
	 * 		into a IfStatementNode.
	 */
	public static IfStatementNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		if (Regex.matches(statement, 0, Patterns.PRE_IF))
		{
			IfStatementNode n = new IfStatementNode();
			
			Bounds bounds = Regex.boundsOf(statement, Patterns.IF_CONTENTS);
			
			if (bounds.getStart() >= 0)
			{
				String contents = statement.substring(bounds.getStart(), bounds.getEnd());
				
				Location newLoc = new Location();
				newLoc.setLineNumber(location.getLineNumber());
				newLoc.setBounds(location.getStart() + bounds.getStart(), location.getStart() + bounds.getEnd());
				
				TreeNode child = BinaryOperatorNode.decodeStatement(parent, contents, newLoc);
				
				if (child == null)
				{
//					SyntaxMessage.error("Could not decode condition", parent.getFileNode(), newLoc, parent.getController());
					
					return null;
				}
				
				n.getCondition().addChild(child);
				
				return n;
			}
			else
			{
				SyntaxMessage.error("If statement missing condition", parent.getFileNode(), location, parent.getController());
			}
		}
		
		return null;
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone()
	 */
	@Override
	public IfStatementNode clone()
	{
		IfStatementNode node = new IfStatementNode();
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given IfStatementNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public IfStatementNode cloneTo(IfStatementNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}

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
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;

/**
 * TreeNode extension that represents a return statement node type.
 * See {@link #decodeStatement(TreeNode, String, Location)} for more
 * details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:58:29 PM
 * @version	v0.2.3 Apr 30, 2014 at 6:18:00 AM
 */
public class ReturnNode extends TreeNode
{
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("return ");
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append(child.generateJavaSource());
		}
		
		builder.append(';').append('\n');
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		return null;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSource()
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
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("return");
		
		if (getChildren().size() > 0)
		{
			builder.append(' ');
		}
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append(child.generateCSourceFragment());
		}
		
		return builder.toString();
	}
	
	/**
	 * Decode the given statement into a ReturnNode instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>return</li>
	 * 	<li>return node</li>
	 * 	<li>return 0</li>
	 * 	<li>return getAge()</li>
	 * 	<li>return age + 32</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ReturnNode instance.
	 * @param location The location of the statement in the source code.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ReturnNode.
	 */
	public static ReturnNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		if (Regex.startsWith(statement, Patterns.PRE_RETURN))
		{
			ReturnNode n = new ReturnNode();
			
			int returnStartIndex = Regex.indexOf(statement, Patterns.POST_RETURN);
			
			if (returnStartIndex >= 0)
			{
				statement = statement.substring(returnStartIndex);
				
				Location newLoc = new Location();
				newLoc.setLineNumber(location.getLineNumber());
				newLoc.setBounds(location.getStart() + returnStartIndex, location.getStart() + statement.length());
				
				TreeNode child = BinaryOperatorNode.decodeStatement(parent, statement, newLoc);
				
				if (child == null)
				{
					child = TreeNode.getExistingNode(parent, statement);
					
					if (child != null)
					{
						child = child.clone();
					}
				}
				if (child == null)
				{
					child = TreeNode.decodeStatement(parent, statement, newLoc);
				}
				if (child == null)
				{
					LiteralNode node = new LiteralNode();
					node.setValue(statement, parent.isWithinExternalContext());
					
					child = node;
				}
				
				n.addChild(child);
			}
			else
			{
				MethodNode parentMethod = (MethodNode)parent.getAncestorOfType(MethodNode.class);
				
				if (parentMethod.getType() != null)
				{
					SyntaxMessage.error("Method '" + parentMethod.getName() + "' must return a type of '" + parentMethod.getType() + "'", parent.getFileNode(), location, parent.getController());
					
					return null;
				}
			}
			
			return n;
		}
		
		return null;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public ReturnNode clone()
	{
		ReturnNode node = new ReturnNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given ReturnNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ReturnNode clone(ReturnNode node)
	{
		super.clone(node);
		
		return node;
	}
}
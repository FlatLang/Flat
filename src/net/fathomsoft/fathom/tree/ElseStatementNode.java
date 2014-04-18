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
import net.fathomsoft.fathom.util.StringUtils;

/**
 * TreeNode extension that represents the declaration of an "else
 * statement" node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:57:13 PM
 * @version	v0.2 Apr 2, 2014 at 8:45:24 PM
 */
public class ElseStatementNode extends TreeNode
{
	/**
	 * Instantiate a new ElseStatementNode and initialize the default
	 * values.
	 */
	public ElseStatementNode()
	{
		ScopeNode scopeNode = new ScopeNode();
		
		addChild(scopeNode);
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#getScopeNode()
	 */
	@Override
	public ScopeNode getScopeNode()
	{
		return (ScopeNode)getChild(0);
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
		if (child instanceof ScopeNode || (getChildren().size() <= 1 && child instanceof IfStatementNode))
		{
			super.addChild(child);
		}
		else
		{
			getScopeNode().addChild(child);
		}
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
		
		builder.append("else");
		
		if (getChildren().size() == 2)
		{
			TreeNode child = getChild(1);
			
			if (child instanceof IfStatementNode)
			{
				builder.append(' ').append(child.generateCSourceOutput());
				
				// Delete the new line at the end.
				builder.deleteCharAt(builder.length() - 1);
			}
		}
		
		builder.append('\n');
	
		builder.append(getScopeNode().generateCSourceOutput());
		
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
	 * Decode the given statement into a ElseStatementNode instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>else</li>
	 * 	<li>else if (!person.canWalk() && !person.isVegetable())</li>
	 * 	<li>else doSomethingInOneLine()</li>
	 * 	<li>else counter++</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ElseStatementNode instance.
	 * @param location The location of the statement in the source code.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ElseStatementNode.
	 */
	public static ElseStatementNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		Bounds bounds = Regex.boundsOf(statement, Patterns.ELSE);
		
		if (bounds.getStart() == 0)
		{
			ElseStatementNode n  = new ElseStatementNode();
			
			String   ending      = statement.substring(bounds.getEnd());
			
			Location newLocation = new Location(location.getLineNumber(), location.getOffset() + bounds.getEnd());
			
			TreeNode contents    = TreeNode.decodeStatement(parent, ending, newLocation);
			
			if (contents != null)
			{
				n.addChild(contents);
			}
			
			return n;
		}
		
		return null;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public ElseStatementNode clone()
	{
		ElseStatementNode node = new ElseStatementNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given IfStatementNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ElseStatementNode clone(ElseStatementNode node)
	{
		super.clone(node);
		
		return node;
	}
}

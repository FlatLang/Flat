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

import net.fathomsoft.nova.util.Location;

/**
 * TreeNode extension that represents the declaration of a LoopNode
 * node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:55:18 PM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class LoopNode extends TreeNode
{
	/**
	 * Instantiate a new LoopNode and initialize the default values.
	 * 
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode)
	 */
	public LoopNode(TreeNode temporaryParent)
	{
		super(temporaryParent);
		
		ScopeNode scopeNode = new ScopeNode(this);
		
		super.addChild(scopeNode);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#getScopeNode()
	 */
	@Override
	public ScopeNode getScopeNode()
	{
		return (ScopeNode)getChild(0);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
		getScopeNode().addChild(child);
	}
	
	/**
	 * Decode the given statement into a LoopNode instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * The statement can be either a while loop or a for loop.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>for (int i = 0; i < 100; i++)</li>
	 * 	<li>for (int i = 0; array != null && i < array.getSize(); i = num * 3 * i)</li>
	 * 	<li>while (currentNode != null)</li>
	 * 	<li>while (true)</li>
	 * 	<li>while (number.isEven())</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		LoopNode instance.
	 * @param location The location of the statement in the source code.
	 * @return The generated node, if it was possible to translated it
	 * 		into a LoopNode.
	 */
	public static LoopNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		LoopNode node = null;
		
		if ((node = ForLoopNode.decodeStatement(parent, statement, location)) != null)
		{
			return node;
		}
		else if ((node = WhileLoopNode.decodeStatement(parent, statement, location)) != null)
		{
			return node;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public LoopNode clone(TreeNode temporaryParent)
	{
		LoopNode node = new LoopNode(temporaryParent);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given LoopNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public LoopNode cloneTo(LoopNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
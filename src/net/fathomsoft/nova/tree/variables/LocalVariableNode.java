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
package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.tree.MethodNode;
import net.fathomsoft.nova.tree.TreeNode;
import net.fathomsoft.nova.util.Location;

/**
 * VariableNode extension that represents the declaration of a local variable
 * node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:12:00 PM
 * @version	v0.2.4 May 17, 2014 at 9:55:04 PM
 */
public class LocalVariableNode extends VariableNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		return super.generateJavaSource();
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		return super.generateCSource();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return super.generateCSourceFragment();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#validate()
	 */
	@Override
	public void validate()
	{
		// If possibly accessing a shadowed field. ONLY for shadowed fields.
		if (getName().equals(MethodNode.getObjectReferenceIdentifier()) && getChildren().size() > 0)
		{
			TreeNode child = getChild(0);
			
			if (child instanceof VariableNode)
			{
				VariableNode var  = (VariableNode)child;
				
				VariableNode node = getExistingNode(var.getClassNode(), var.getName());
				
				if (node instanceof FieldNode)
				{
					node = node.clone();
					
					node.inheritChildren(var);
					
					TreeNode     parent = getParent();
					
					parent.replace(this, node);
				}
			}
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone()
	 */
	@Override
	public LocalVariableNode clone()
	{
		LocalVariableNode node = new LocalVariableNode();
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given LocalVariableNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public LocalVariableNode cloneTo(LocalVariableNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}

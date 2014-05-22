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

import net.fathomsoft.nova.tree.exceptionhandling.ExceptionNode;

/**
 * TreeNode extension that keeps track of all of the arguments that
 * are passed during a method call. The children of this node are
 * all ArgumentNode instances. They are stored in the order that
 * they will be passed to the method call.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 10, 2014 at 3:12:54 AM
 * @version	v0.2.4 May 17, 2014 at 9:55:04 PM
 */
public class ArgumentListNode extends TreeNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateJavaSource());
			
			if (i < getChildren().size() - 1)
			{
				builder.append(", ");
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * Get the MethodCallNode instance that contains the specified
	 * arguments.
	 * 
	 * @return The parent MethodCallNode instance.
	 */
	public MethodCallNode getMethodCall()
	{
		return (MethodCallNode)getParent();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateNovaInput()
	 */
	@Override
	public String generateNovaInput()
	{
		StringBuilder builder = new StringBuilder();
		
		MethodNode    method  = getMethodCall().getMethodNode();
		
		int start = 0;
		
		if (method.isStatic())
		{
			start = 1;
		}
		if (method.isExternal())
		{
			start = 0;
		}
		
		for (int i = start; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append(child.generateNovaInput());
		}
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder  builder = new StringBuilder();
		
		MethodCallNode caller  = getMethodCall();
		
		MethodNode     method  = caller.getMethodNode();
		
		if (!caller.isExternal())
		{
			if (method.needsReference())
			{
				if (method instanceof DestructorNode)
				{
					builder.append('&');
				}
				
				builder.append(caller.getReferenceNode().generateUseOutput()).append(", ");
			}
			
			builder.append(ExceptionNode.EXCEPTION_DATA_IDENTIFIER);
			
			if (getChildren().size() > 0)
			{
				builder.append(", ");
			}
		}
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateCSourceFragment());
			
			if (i < getChildren().size() - 1)
			{
				builder.append(", ");
			}
		}
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone()
	 */
	@Override
	public TreeNode clone()
	{
		ArgumentListNode node = new ArgumentListNode();
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ArgumentListNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ArgumentListNode cloneTo(ArgumentListNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
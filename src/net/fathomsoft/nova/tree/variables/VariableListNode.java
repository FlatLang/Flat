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

import net.fathomsoft.nova.tree.TreeNode;

/**
 * Node that holds the LocalVariableNodes that a method contains.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 9, 2014 at 4:19:57 PM
 * @version	v0.2.4 May 17, 2014 at 9:55:04 PM
 */
public class VariableListNode extends TreeNode
{
	/**
	 * Get whether or not there is a VaraibleNode within the list with
	 * the given name.
	 * 
	 * @param variableName The name of the variable to search for.
	 * @return Whether or not there is a VaraibleNode within the list with
	 * 		the given name.
	 */
	public boolean containsVariable(String variableName)
	{
		return getVariable(variableName) != null;
	}
	
	/**
	 * Get the VariableNode from the list with the given name, if it
	 * exists.
	 * 
	 * @param variableName The name of the variable to get.
	 * @return The VariableNode with the given name.
	 */
	public VariableNode getVariable(String variableName)
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			VariableNode variable = (VariableNode)getChild(i);
			
			if (variable.getName().equals(variableName))
			{
				return variable;
			}
		}
		
		return null;
	}
	
	/**
	 * Generate the output needed to free the variables after they are
	 * finished with.
	 * 
	 * @return The String output of the variables being freed.
	 */
	public String generateFreeVariablesOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			VariableNode variable = (VariableNode)getChild(i);
			
			builder.append(variable.generateFreeOutput());
		}
		
		return builder.toString();
	}
	
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
		}
		
		if (getChildren().size() > 0)
		{
			builder.append('\n');
		}
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		return "";
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append(child.generateCSource());
		}
		
		if (getChildren().size() > 0)
		{
			builder.append('\n');
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone()
	 */
	@Override
	public VariableListNode clone()
	{
		VariableListNode node = new VariableListNode();
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given VariableListNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public VariableListNode cloneTo(VariableListNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
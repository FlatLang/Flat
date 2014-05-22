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

/**
 * TreeNode extension that represents an operator in an operation.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:19:40 PM
 * @version	v0.2 Apr 5, 2014 at 10:14:38 PM
 */
public class OperatorNode extends TreeNode
{
	private String	operator;
	
	/**
	 * Get the value of the operator. For example: '+', '*', '&&', etc.
	 * 
	 * @return The value of the operator.
	 */
	public String getOperator()
	{
		return operator;
	}
	
	/**
	 * Set the operator value. For example: '+', '*', '&&', etc.
	 * 
	 * @param operator The new value of the operator.
	 */
	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		return operator;
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
		return operator;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return operator;
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone()
	 */
	@Override
	public OperatorNode clone()
	{
		OperatorNode node = new OperatorNode();
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given OperatorNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public OperatorNode cloneTo(OperatorNode node)
	{
		super.cloneTo(node);
		
		node.setOperator(getOperator());
		
		return node;
	}
}
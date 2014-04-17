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

import net.fathomsoft.fathom.tree.variables.LocalVariableNode;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * LocalVariableNode extension that represents a Parameter of a method.
 * See {@link #decodeStatement(TreeNode, String, Location)} for more
 * details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:52:01 PM
 * @version	v0.2 Apr 5, 2014 at 10:40:57 PM
 */
public class ParameterNode extends LocalVariableNode
{
	private TreeNode	defaultValue;
	
	/**
	 * Get the default value of the parameter, if no value is passed to
	 * the method.
	 * 
	 * @return The value that the parameter will be set to, if no value is
	 * 		passed to a method.
	 */
	public TreeNode getDefaultValue()
	{
		return defaultValue;
	}
	
	/**
	 * Set the default value of the parameter, if no value is passed to
	 * the method.
	 * 
	 * @param defaultValue The value that the parameter will be set to,
	 * 		if no value is passed to a method.
	 */
	public void setDefaultValue(TreeNode defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		return getType() + " " + getName();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		return generateCSourceOutput();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		if (isConstant())
		{
			builder.append(getConstantText()).append(' ');
		}
		
		builder.append(getType());
		
		if (isArray())
		{
			builder.append(getArrayText());
		}
		
		if (!SyntaxUtils.isPrimitiveType(getType()))// && !isExternal())
		{
			builder.append('*');
		}
		
		builder.append(' ').append(getName());
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return generateVariableUseOutput();
	}
	
	/**
	 * Decode the given statement into a ParameterNode instance, if
	 * possible. If it is not possible, this method returns null.
	 * A parameter node is essentially a variable declaration, but in
	 * the context of a method declaration.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>String name</li>
	 * 	<li>int age</li>
	 * 	<li>TreeNode parent</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ParameterNode instance.
	 * @param location The location of the statement in the source code.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ParameterNode.
	 */
	public static ParameterNode decodeStatement(TreeNode parent, String statement, final Location location)
	{
		LocalVariableNode node = LocalVariableNode.decodeStatement(parent, statement, location);
		
		ParameterNode n = new ParameterNode();
		node.clone(n);
		
		return n;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public ParameterNode clone()
	{
		ParameterNode node = new ParameterNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given ParameterNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ParameterNode clone(ParameterNode node)
	{
		super.clone(node);
		
		node.setDefaultValue(getDefaultValue());
		
		return node;
	}
}
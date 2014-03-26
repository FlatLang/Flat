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
import net.fathomsoft.fathom.tree.variables.LocalVariableNode;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:52:01 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:52:01 PM
 * @version	v
 */
public class ParameterNode extends LocalVariableNode
{
//	private String	type;
	private String	defaultValue;
	
	public ParameterNode()
	{
		
	}
	
//	public String getType()
//	{
//		return type;
//	}
//	
//	public void setType(String type)
//	{
//		this.type = type;
//	}
	
	public String getDefaultValue()
	{
		return defaultValue;
	}
	
	public void setDefaultValue(String defaultValue)
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
	
	public static ParameterNode decodeStatement(TreeNode parentNode, String statement, final Location location)
	{
		LocalVariableNode node = LocalVariableNode.decodeStatement(parentNode, statement, location);
		
		ParameterNode n = new ParameterNode();
		n.setArrayDimensions(node.getArrayDimensions());
		n.setName(node.getName());
		n.setType(node.getType());
		
		return n;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public ParameterNode clone()
	{
		ParameterNode clone = new ParameterNode();
		clone.setName(getName());
		clone.setConst(isConst());
		clone.setArrayDimensions(getArrayDimensions());
		clone.setType(getType());
		clone.setReference(isReference());
		clone.setPointer(isPointer());
		clone.setDefaultValue(getDefaultValue());
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			clone.addChild(child.clone());
		}
		
		return clone;
	}
}
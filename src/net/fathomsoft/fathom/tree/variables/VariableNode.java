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
package net.fathomsoft.fathom.tree.variables;

import net.fathomsoft.fathom.tree.MethodNode;
import net.fathomsoft.fathom.tree.ModifierNode;
import net.fathomsoft.fathom.tree.TreeNode;
import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:02:42 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:02:42 PM
 * @version	v
 */
public class VariableNode extends ModifierNode
{
	private boolean	constVal, external;
	
	private int		arrayDimensions;
	
	private String	type;
	
	private static final String	NULL_TEXT = "0";
	
	public VariableNode()
	{
		
	}
	
	public boolean isExternal()
	{
		return external;
	}
	
	public void setExternal(boolean external)
	{
		this.external = external;
	}
	
	public boolean isPrimitive()
	{
		return isPrimitiveType() && !isArray();
	}
	
	public boolean isPrimitiveType()
	{
		return SyntaxUtils.isPrimitiveType(getType());
	}
	
	public boolean isConst()
	{
		return constVal;
	}
	
	public String getConstText()
	{
		return "const";
	}
	
	public void setConst(boolean constVal)
	{
		this.constVal = constVal;
	}
	
	public boolean isArray()
	{
		return arrayDimensions > 0;
	}
	
	public int getArrayDimensions()
	{
		return arrayDimensions;
	}
	
	public String getArrayText()
	{
		return "*";
	}
	
	public void setArrayDimensions(int arrayDimensions)
	{
		this.arrayDimensions = arrayDimensions;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		if (type != null && type.equals("long"))
		{
			type = "long_long";
		}
		
		this.type = type;
	}
	
	public void setAttribute(String attribute)
	{
		setAttribute(attribute, -1);
	}
	
	public void setAttribute(String attribute, int argNum)
	{
		if (attribute.equals("const"))
		{
			setConst(true);
		}
	}
	
	public static String getNullText()
	{
		return NULL_TEXT;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		if (isConst())
		{
			builder.append(getConstText()).append(' ');
		}
		
		builder.append(getType()).append(' ');
		
		if (isReference())
		{
			builder.append(getReferenceText()).append(' ');
		}
		else if (isPointer())
		{
			builder.append(getPointerText()).append(' ');
		}
		
		builder.append(getName()).append(';').append('\n');
		
		return builder.toString();
	}
	
	public String generateVariableUseOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		String visibility = "";
		
		if (this instanceof FieldNode)
		{
			FieldNode field = (FieldNode)this;
			
			visibility = MethodNode.getObjectReferenceIdentifier() + "->";
			
			if (field.getVisibility() == FieldNode.PRIVATE)
			{
				visibility += "prv->";
			}
		}
		
		builder.append(visibility).append(getName());
		
		return builder.toString();
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
		
		if (isConst())
		{
			builder.append(getConstText()).append(' ');
		}
		
		builder.append(getType());
		
		if (isReference())
		{
			builder.append(getReferenceText());
		}
		else if (isPointer())
		{
			builder.append(getPointerText());
		}
		if (isArray())
		{
			builder.append(getArrayText());
		}
		if (!isPrimitiveType())
		{
			builder.append('*');
		}
		
		builder.append(' ').append(getName()).append(';').append('\n');
		
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
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public VariableNode clone()
	{
		VariableNode node = new VariableNode();
		
		return clone(node);
	}
	
	public VariableNode clone(VariableNode node)
	{
		node.setName(getName());
		node.setConst(isConst());
		node.setArrayDimensions(getArrayDimensions());
		node.setType(getType());
		node.setReference(isReference());
		node.setPointer(isPointer());
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			node.addChild(child.clone());
		}
		
		return node;
	}
}
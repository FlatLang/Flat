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

import net.fathomsoft.fathom.tree.variables.VariableNode;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:10:49 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:10:49 PM
 * @version	v
 */
public class DeclarationNode extends VariableNode
{
	private boolean 		staticVal;
	
	private int				visibility;
	
	public static final int	PRIVATE		= 1;
	
	public static final int	PROTECTED	= 2;
	
	public static final int	PUBLIC		= 3;
	
	public DeclarationNode()
	{
		setVisibility(PRIVATE);
	}
	
	public boolean isStatic()
	{
		return staticVal;
	}
	
	public String getStaticText()
	{
		return "static";
	}
	
	public void setStatic(boolean staticVal)
	{
		this.staticVal = staticVal;
	}
	
	public int getVisibility()
	{
		return visibility;
	}
	
	public boolean isVisibilityValid()
	{
		return visibility >= PRIVATE && visibility <= PUBLIC;
	}
	
	public String getVisibilityText()
	{
		if (visibility == PRIVATE)
		{
			return "private";
		}
		else if (visibility == PROTECTED)
		{
			return "protected";
		}
		else if (visibility == PUBLIC)
		{
			return "public";
		}
		
		return null;
	}
	
	public void setVisibility(int visibility)
	{
		this.visibility = visibility;
	}
	
	public void setAttribute(String attribute)
	{
		setAttribute(attribute, -1);
	}
	
	public void setAttribute(String attribute, int argNum)
	{
		super.setAttribute(attribute, argNum);
		
		if (attribute.equals("static"))
		{
			setStatic(true);
		}
		// Put new stuff between here...
		else if (argNum == 0)
		{
			if (attribute.equals("private"))
			{
				setVisibility(PRIVATE);
			}
			else if (attribute.equals("protected"))
			{
				setVisibility(PROTECTED);
			}
			else if (attribute.equals("public"))
			{
				setVisibility(PUBLIC);
			}
		}
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		if (isVisibilityValid())
		{
			builder.append(getVisibilityText()).append(' ');
		}
		if (isStatic())
		{
			builder.append(getStaticText()).append(' ');
		}
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
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(generateCHeaderFragment());
		builder.append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderFragment()
	 */
	@Override
	public String generateCHeaderFragment()
	{
		StringBuilder builder = new StringBuilder();
		
//		if (isVisibilityValid())
//		{
//			if (getVisibility() == PRIVATE)
//			{
//				return "";
//			}
//			
//			builder.append(getVisibilityText()).append(' ');
//		}
//		if (isStatic())
//		{
//			builder.append(getStaticText()).append(' ');
//		}
//		if (isConst())
//		{
//			builder.append(getConstText()).append(' ');
//		}
		
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
		
		builder.append(' ').append(getName());
		
//		if (!isPrimitiveType())
//		{
//			builder.append(" = 0;");
//		}
		
		return builder.toString();
	}
	
//	/**
//	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
//	 */
//	@Override
//	public String generateCSourceOutput()
//	{
//		return null;
//	}
//	
//	/**
//	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
//	 */
//	@Override
//	public String generateCSourceFragment()
//	{
//		return null;
//	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public DeclarationNode clone()
	{
		DeclarationNode node = new DeclarationNode();
		
		return clone(node);
	}
	
	public DeclarationNode clone(DeclarationNode node)
	{
		node.setStatic(isStatic());
		node.setVisibility(getVisibility());
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
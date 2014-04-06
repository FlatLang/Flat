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

import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.tree.DeclarationNode;
import net.fathomsoft.fathom.tree.TreeNode;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.StringUtils;
import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:12:04 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:12:04 PM
 * @version	v
 */
public class FieldNode extends DeclarationNode
{
	public static final int	VISIBLE	= 4;
	
	public FieldNode()
	{
		
	}
	
	public boolean isVisibilityValid()
	{
		int visibility = getVisibility();
		
		return super.isVisibilityValid() || visibility == VISIBLE;
	}
	
	public String getVisibilityText()
	{
		int visibility = getVisibility();
		
		if (visibility == VISIBLE)
		{
			return "visible";
		}
		
		return super.getVisibilityText();
	}
	
	public void setAttribute(String attribute, int argNum)
	{
		super.setAttribute(attribute, argNum);
		
		if (argNum == 0)
		{
			if (attribute.equals("visibility"))
			{
				setVisibility(VISIBLE);
			}
		}
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		return super.generateJavaSourceOutput();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		return super.generateCHeaderOutput();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		if (isStatic())
		{
			builder.append(getStaticText()).append(' ');
		}
		if (isConst())
		{
			builder.append(getConstText()).append(' ');
		}
		
		builder.append(getType());
		
		if (isReference())
		{
			builder.append('&');
		}
		else if (isPointer())
		{
			builder.append('*');
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
//			builder.append(" = 0");
//		}
		
		builder.append(';').append('\n');
		
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
	
	public static FieldNode decodeStatement(TreeNode parentNode, final String statement, Location location)
	{
		FieldNode n = new FieldNode()
		{
			private boolean	methods = false;
			
			public void interactWord(String word, int argNum, Bounds bounds, int numWords)
			{
				if (word.equals("{"))
				{
					methods = true;
					
					return;
				}
				else if (methods && word.equals("}"))
				{
					return;
				}
				
				if (argNum == numWords - 1)
				{
					setName(word);
					
					// If it is an array declaration.
					if (Regex.matches(statement, bounds.getEnd(), Patterns.EMPTY_ARRAY_BRACKETS))
					{
						int dimensions = SyntaxUtils.getArrayDimensions(statement);
						
						setArrayDimensions(dimensions);
					}
				}
				else if (argNum == numWords - 2)
				{
					setType(word);
				}
				else
				{
					setAttribute(word, argNum);
					
					int  index = StringUtils.findNextNonWhitespaceIndex(statement, bounds.getEnd());
					
					char c     = statement.charAt(index);
					
					if (c == '.')
					{
						setExternal(true);
					}
				}
			}
		};
		
		n.iterateWords(statement, Patterns.IDENTIFIER_BOUNDARIES);
		
		
		if (n.getType() == null)
		{
			SyntaxMessage.error("A type for the field must be declared", n);
		}
		
		return n;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public FieldNode clone()
	{
		FieldNode node = new FieldNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given FieldNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public FieldNode clone(FieldNode node)
	{
		super.clone(node);
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			node.addChild(child.clone());
		}
		
		return node;
	}
}
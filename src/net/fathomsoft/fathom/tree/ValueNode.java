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

import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * TreeNode extension that represents something that returns a value.
 * For the rules on what can and cannot be an value node, refer to
 * {@link #setType(java.lang.String)}
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.4 May 2, 2014 at 11:14:37 PM
 * @version	v0.2.4 May 17, 2014 at 9:55:04 PM
 */
public class ValueNode extends TreeNode
{
	private String	type;
	
	/**
	 * Get whether a variable's type is a primitive type or not.<br>
	 * <br>
	 * For the list of primitive values, see
	 * {@link net.fathomsoft.fathom.util.SyntaxUtils#isPrimitiveType(String)}
	 * 
	 * @return Whether a variable's type is a primitive type or not.
	 */
	public boolean isPrimitiveType()
	{
		return SyntaxUtils.isPrimitiveType(getType());
	}
	
	/**
	 * Get the type that the statement returns. For an example of what a
	 * value type looks like, see {@link #setType(String)}
	 * 
	 * @return The type that the statement returns.
	 */
	public String getType()
	{
		return type;
	}
	
	/**
	 * Set the type that this statement returns.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * private static int index;</pre></blockquote>
	 * The type of the variable returns is "int"
	 * 
	 * @param type The type that this statement returns.
	 */
	public void setType(String type)
	{
		if (type != null && type.equals("long"))
		{
			type = "long_long";
		}
		
		this.type = type;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		return generateCSourceFragment() + ";\n";
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return generateChildrenCSourceFragment(false);
	}
	
	public String generateChildrenCSourceFragment()
	{
		return generateChildrenCSourceFragment(true);
	}
	
	public String generateChildrenCSourceFragment(boolean reference)
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (i > 0 || reference)
			{
				builder.append("->");
			}
			
			builder.append(child.generateCSourceFragment());
		}
		
		return builder.toString();
	}
	

	public String generateSpecialFragment()
	{
		for (int i = getChildren().size() - 1; i >= 0; i--)
		{
			TreeNode child = getChild(i);
			
			if (child instanceof MethodCallNode || child instanceof InstantiationNode)
			{
				return child.generateCSourceFragment();
			}
		}
		
		return null;
	}
	
	public boolean isSpecialFragment()
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child instanceof MethodCallNode || child instanceof InstantiationNode)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public String generateUseOutput()
	{
		return null;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public ValueNode clone()
	{
		ValueNode node = new ValueNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given IdentifierNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ValueNode clone(ValueNode node)
	{
		super.clone(node);
		
		node.type = type;
		
		return node;
	}
}
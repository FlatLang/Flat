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

import net.fathomsoft.nova.tree.variables.VariableNode;

/**
 * VariableNode extension that represents the declaration of a field
 * or method. Contains the modifiers for visibility and whether or not
 * the field/method is static. 
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:10:49 PM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class InstanceDeclarationNode extends VariableNode
{
	private boolean 		staticVal;
	
	private int				visibility;
	
	/**
	 * This visibility allows manipulation and view from only the class
	 * that it is declared in.
	 */
	public static final int	PRIVATE		= 1;
	
	/**
	 * This visibility allows manipulation and viewing from anywhere
	 * within its package.
	 */
	public static final int	PROTECTED	= 2;
	
	/**
	 * This visibility allows open manipulation and viewing from anywhere
	 * in a program.
	 */
	public static final int	PUBLIC		= 3;
	
	/**
	 * Instantiate a new InstanceDeclarationNode and initialize the default
	 * values.
	 * 
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode)
	 */
	public InstanceDeclarationNode(TreeNode temporaryParent)
	{
		super(temporaryParent);
		
		setVisibility(PRIVATE);
	}
	
	/**
	 * Get whether or not the specified InstanceDeclarationNode is static. Static
	 * variables/methods are shared among each of its containing class's
	 * instances.
	 * 
	 * @return Whether or not the specified InstanceDeclarationNode is static.
	 */
	public boolean isStatic()
	{
		return staticVal;
	}
	
	/**
	 * (WARNING: CURRENTLY ONLY RETURNS "static")<br>
	 * Get the text that is associated with the static value of the
	 * specified InstanceDeclarationNode.<br>
	 * <br>
	 * For example: If static is true, the method will return "static" if
	 * it is not, it will return an empty String.
	 * 
	 * @return The value representing the static value.
	 */
	public String getStaticText()
	{
		return "static";
	}
	
	/**
	 * Set whether or not the specified InstanceDeclarationNode is static. Static
	 * variables/methods are shared among each of its containing class's
	 * instances.
	 * 
	 * @param staticVal Whether or not to set it as static.
	 */
	public void setStatic(boolean staticVal)
	{
		this.staticVal = staticVal;
	}
	
	/**
	 * Get the value of the current visibility of the node.<br>
	 * <br>
	 * Possible values include:<br>
	 * <ul>
	 * 	<li>InstanceDeclarationNode.PRIVATE</li>
	 * 	<li>InstanceDeclarationNode.PROTECTED</li>
	 * 	<li>InstanceDeclarationNode.PUBLIC</li>
	 * </ul>
	 * 
	 * @return The current visibility value of the node.
	 */
	public int getVisibility()
	{
		return visibility;
	}
	
	/**
	 * Determine whether or not the visibility valid. Essentially
	 * checking if the visibility value is one of the available three
	 * values.<br>
	 * <br>
	 * Possible values include:<br>
	 * <ul>
	 * 	<li>InstanceDeclarationNode.PRIVATE</li>
	 * 	<li>InstanceDeclarationNode.PROTECTED</li>
	 * 	<li>InstanceDeclarationNode.PUBLIC</li>
	 * </ul>
	 * 
	 * @return Returns whether or not the visibility's value is valid.
	 */
	public boolean isVisibilityValid()
	{
		return visibility >= PRIVATE && visibility <= PUBLIC;
	}
	
	/**
	 * Get the text that is associated with the visibility of the
	 * specified InstanceDeclarationNode.<br>
	 * <br>
	 * For example: InstanceDeclarationNode.PRIVATE would return a String with
	 * the value of "private"
	 * 
	 * @return The text that is associated with the visibility of the
	 * 		current node.
	 */
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
	
	/**
	 * Set the visibility of the field or method that was declared.<br>
	 * <br>
	 * Possible options include:<br>
	 * <ul>
	 * 	<li>InstanceDeclarationNode.PRIVATE</li>
	 * 	<li>InstanceDeclarationNode.PROTECTED</li>
	 * 	<li>InstanceDeclarationNode.PUBLIC</li>
	 * </ul>
	 * 
	 * @param visibility The visibility of the field/method that was
	 * 		declared.
	 */
	public void setVisibility(int visibility)
	{
		this.visibility = visibility;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.variables.VariableNode#setAttribute(java.lang.String)
	 */
	public void setAttribute(String attribute)
	{
		setAttribute(attribute, -1);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.variables.VariableNode#setAttribute(java.lang.String, int)
	 */
	public boolean setAttribute(String attribute, int argNum)
	{
		if (super.setAttribute(attribute, argNum))
		{
			return true;
		}
		
		if (attribute.equals("static"))
		{
			setStatic(true);
		}
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
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
		
		return true;
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
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
		if (isConstant())
		{
			builder.append(getConstantText()).append(' ');
		}
		
		builder.append(getType()).append(' ');
		
		if (isReference())
		{
			builder.append('&').append(' ');
		}
		else if (isPointer())
		{
			builder.append('*').append(' ');
		}
		
		builder.append(getName()).append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(generateCHeaderFragment());
		builder.append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCHeaderFragment()
	 */
	@Override
	public String generateCHeaderFragment()
	{
		StringBuilder builder = new StringBuilder();
		
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
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public InstanceDeclarationNode clone(TreeNode temporaryParent)
	{
		InstanceDeclarationNode node = new InstanceDeclarationNode(temporaryParent);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given InstanceDeclarationNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public InstanceDeclarationNode cloneTo(InstanceDeclarationNode node)
	{
		super.cloneTo(node);
		
		node.staticVal  = staticVal;
		node.visibility = visibility;
		
		return node;
	}
}

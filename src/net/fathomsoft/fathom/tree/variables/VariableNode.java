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
 * ModifierNode extension that represents the declaration of a variable
 * node type. Harnesses the needed information of each variable, such as
 * whether or not it is constant, external, or an array, and its type.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:02:42 PM
 * @version	v0.2 Apr 7, 2014 at 7:40:28 PM
 */
public class VariableNode extends ModifierNode
{
	private boolean				constantVal, external;
	
	private int					arrayDimensions;
	
	private String				type;
	
	private static final String	NULL_TEXT	= "0";
	
	/**
	 * Get whether or not the variable is external. For more information
	 * on external variables, see {@link #setExternal(boolean)}.
	 * 
	 * @return Whether or not the variable is external.
	 */
	public boolean isExternal()
	{
		return external;
	}
	
	/**
	 * Set whether or not the variable is external. A variable is external
	 * if it is referenced from a language outside of Fathom. For example,
	 * a variable from the C language. Furthermore, a variable is external
	 * if it begins with an externally imported C file's name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * import "externalFile.h";
	 * 
	 * ...
	 * 
	 * public static void main(String args[])
	 * {
	 *	// This is the external variable declaration.
	 * 	externalFile.externalType varName;
	 * 	
	 * 	// This is the external variable assignment.
	 * 	varName = externalFile.variableInstance;
	 * }</pre></blockquote>
	 * In this example, 'externalFile' is the C header file that is
	 * imported. 'variableInstance' is the name of a variable that
	 * is contained within the imported header file.<br>
	 * 
	 * @param external Whether or not the variable will be external.
	 */
	public void setExternal(boolean external)
	{
		this.external = external;
	}
	
	/**
	 * Get whether a variable is primitive or not. A variable is primitive
	 * if it has a primitive type AND is NOT an array. Arrays are NOT a
	 * primitive type.<br>
	 * <br>
	 * For the list of primitive values, see
	 * {@link net.fathomsoft.fathom.util.SyntaxUtils#isPrimitiveType(String)}.
	 * 
	 * @return Whether a variable is primitive or not.
	 */
	public boolean isPrimitive()
	{
		return isPrimitiveType() && !isArray();
	}
	
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
	 * Get whether or not the variable's value is constant. To
	 * see more detail, look at {@link #setConstant(boolean)}.
	 * 
	 * @return Whether or not the variable's value is constant.
	 */
	public boolean isConstant()
	{
		return constantVal;
	}
	
	/**
	 * Get the C equivalent of the 'constant' keyword.
	 * 
	 * @return The C equivalent of the 'constant' keyword.
	 */
	public String getConstantText()
	{
		return "const";
	}
	
	/**
	 * Set whether or not the variable's value is constant.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * private constant int MAX_PEOPLE = 10;</pre></blockquote>
	 * This variable is constant, as defined by the 'constant' keyword.
	 * 
	 * @param constVal Whether or not the variable's value
	 * 		is constant.
	 */
	public void setConstant(boolean constVal)
	{
		this.constantVal = constVal;
	}
	
	/**
	 * Get whether or not the variable is an array. A variable is an
	 * array if it has an array dimension of 1 or greater.
	 * 
	 * @return Whether or not the variable is an array.
	 */
	public boolean isArray()
	{
		return getArrayDimensions() > 0;
	}
	
	/**
	 * Get the amount of dimensions that the array has, if any. For an
	 * example of what a array declarations and dimensions look like
	 * {@link #setArrayDimensions(int)}
	 * 
	 * @return The amount of dimensions that the array has, if any.
	 */
	public int getArrayDimensions()
	{
		return arrayDimensions;
	}
	
	/**
	 * The text that represents an array in the C language.
	 * 
	 * @return The text that represents an array in the C language.
	 */
	public String getArrayText()
	{
		return "*";
	}
	
	/**
	 * Set the amount of dimensions that the array has, if any.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * int array[][][];</pre></blockquote>
	 * In the previous example, the variable "array" has three dimensions.
	 * 
	 * @param arrayDimensions The amount of dimensions that the array has,
	 * 		if any.
	 */
	public void setArrayDimensions(int arrayDimensions)
	{
		this.arrayDimensions = arrayDimensions;
	}
	
	/**
	 * Get the type that the variable is. For an example of what a
	 * variable type looks like, see {@link #setType(String)}
	 * 
	 * @return The type that the variable is.
	 */
	public String getType()
	{
		return type;
	}
	
	/**
	 * Set the type that this variable is.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * private static int index;</pre></blockquote>
	 * The type of the variable above is "int"
	 * 
	 * @param type The type that this variable is.
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
	 * Set a specified attribute to true.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * private static int index;</pre></blockquote>
	 * <u><code>private</code></u> sets the visibility of the declaration
	 * to private. <u><code>static</code></u> sets the variable as static.
	 * 
	 * @param attribute The attribute to set true.
	 */
	public void setAttribute(String attribute)
	{
		setAttribute(attribute, -1);
	}
	
	/**
	 * Set a specified attribute to true.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * private static int index;</pre></blockquote>
	 * <u><code>private</code></u> is the first attribute (index: 0) that
	 * sets the visibility of the declaration to private.
	 * <u><code>static</code></u> is the second attribute (index: 1) that
	 * sets the variable as static.
	 * 
	 * @param attribute The attribute to set true.
	 * @param argNum The index of the attribute in the order that it
	 * 		came in.
	 */
	public void setAttribute(String attribute, int argNum)
	{
		if (attribute.equals("constant"))
		{
			setConstant(true);
		}
	}
	
	/**
	 * Get the text that represents the java 'null' in the C language.
	 * 
	 * @return The text that represents the java 'null' in the C language.
	 */
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
		
		if (isConstant())
		{
			builder.append(getConstantText()).append(' ');
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
		
		builder.append(' ').append(getName()).append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * Generate the representation of when the variable is being used, in
	 * action, rather than being declared.<br>
	 * <br>
	 * For example:<br>
	 * <blockquote><pre>
	 * Person p;
	 * p.getName();</pre></blockquote>
	 * The first line shows the declaration of the Variable. The second
	 * line demonstrates a "variable use" for the "p" variable.
	 * Essentially, the "variable use" output is exactly what it says,
	 * what the variable looks like when it is being used to do something.
	 * 
	 * @return What the variable looks like when it is being used to do
	 * 		something.
	 */
	public String generateVariableUseOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		if (this instanceof FieldNode)
		{
			FieldNode field = (FieldNode)this;
			
			builder.append(MethodNode.getObjectReferenceIdentifier()).append("->");
			
			if (field.getVisibility() == FieldNode.PRIVATE)
			{
				builder.append("prv->");
			}
		}
		
		builder.append(getName());
		
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
		
		if (isConstant())
		{
			builder.append(getConstantText()).append(' ');
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
	
	/**
	 * Fill the given VariableNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public VariableNode clone(VariableNode node)
	{
		super.clone(node);
		
		node.setConstant(isConstant());
		node.setArrayDimensions(getArrayDimensions());
		node.setType(getType());
		node.setExternal(isExternal());
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			node.addChild(child.clone());
		}
		
		return node;
	}
}

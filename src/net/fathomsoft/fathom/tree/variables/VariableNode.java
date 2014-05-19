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

import net.fathomsoft.fathom.Fathom;
import net.fathomsoft.fathom.tree.AssignmentNode;
import net.fathomsoft.fathom.tree.ClassNode;
import net.fathomsoft.fathom.tree.InstanceDeclarationNode;
import net.fathomsoft.fathom.tree.IdentifierNode;
import net.fathomsoft.fathom.tree.LocalDeclarationNode;
import net.fathomsoft.fathom.tree.MethodNode;
import net.fathomsoft.fathom.tree.ScopeNode;
import net.fathomsoft.fathom.tree.TreeNode;
import net.fathomsoft.fathom.tree.ValueNode;
import net.fathomsoft.fathom.tree.exceptionhandling.ExceptionNode;
import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * ModifierNode extension that represents the declaration of a variable
 * node type. Harnesses the needed information of each variable, such as
 * whether or not it is constant, external, or an array, and its type.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:02:42 PM
 * @version	v0.2.4 May 17, 2014 at 9:55:04 PM
 */
public class VariableNode extends IdentifierNode
{
	private boolean				referenceVal;
	private boolean				pointerVal;
	private boolean				constantVal, external, forceOriginal;
	
	private int					arrayDimensions;
	
	private static final String	NULL_TEXT	= "0";
	
	/**
	 * Get whether or not the identifier is a reference.
	 * 
	 * @return Whether or not the identifier is a reference.
	 */
	public boolean isReference()
	{
		return referenceVal;
	}
	
	/**
	 * Set whether or not the identifier is a reference.
	 * 
	 * @param referenceVal Whether or not the identifier is a reference.
	 */
	public void setReference(boolean referenceVal)
	{
		this.referenceVal = referenceVal;
	}
	
	/**
	 * Get whether or not the identifier is a pointer.
	 * 
	 * @return Whether or not the identifier is a pointer.
	 */
	public boolean isPointer()
	{
		return pointerVal;
	}
	
	/**
	 * Set whether or not the identifier is a pointer.
	 * 
	 * @param pointerVal Whether or not the identifier is a pointer.
	 */
	public void setPointer(boolean pointerVal)
	{
		this.pointerVal = pointerVal;
	}

	
	/**
	 * Get the ClassNode parent instance of the VariableNode.
	 * 
	 * @return The nearest ClassNode instance that contains this variable.
	 */
	public ClassNode getClassNode()
	{
		return (ClassNode)getAncestorOfType(ClassNode.class);
	}
	
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
		
		forceOriginal = true;
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
	 * Set the name of the Variable.
	 * 
	 * @see net.fathomsoft.fathom.tree.IdentifierNode#setName(java.lang.String)
	 * 
	 * @param name The String to set as the new name.
	 */
	public void setName(String name)
	{
		setName(name, false);
	}
	
	/**
	 * Set the name of the Variable. You specify whether or not you want
	 * the output in the C language to be the original to the given name,
	 * or if it will differentiate it. 
	 * 
	 * @param name The String to set as the new name.
	 * @param forceOriginal Whether or not the name will be output in the
	 * 		c code verbatim.
	 */
	public void setName(String name, boolean forceOriginal)
	{
		this.forceOriginal = forceOriginal;
		
		super.setName(name);
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
	public boolean setAttribute(String attribute, int argNum)
	{
		if (attribute.equals("constant"))
		{
			setConstant(true);
		}
		else if (attribute.equals("external"))
		{
			setExternal(true);
		}
		else
		{
			return false;
		}
		
		return true;
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
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
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
	public String generateUseOutput()
	{
		return generateUseOutput(false);
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
	 * @param pointer Whether or not the variable is to be accessed by a
	 * 		pointer.
	 * @return What the variable looks like when it is being used to do
	 * 		something.
	 */
	public String generateUseOutput(boolean pointer)
	{
		StringBuilder builder = new StringBuilder();
		
		if (isPointer())
		{
			builder.append('*');
		}
		else if (isReference())
		{
			builder.append('&');
		}
		
		if (this instanceof FieldNode)
		{
			FieldNode field = (FieldNode)this;
			
			if (!field.isStatic())
			{
				if (pointer)
				{
					builder.append('(').append('*');
				}
				
				builder.append(MethodNode.getObjectReferenceIdentifier());
				
				if (pointer)
				{
					builder.append(')');
				}
				
				builder.append("->");
			
				if (field.getVisibility() == FieldNode.PRIVATE)
				{
					builder.append("prv").append("->");
				}
			}
		}
		
		builder.append(generateCSourceName());
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		return generateCSource();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		if (!isDeclaration())
		{
			return generateCSourceFragment() + ";\n";
		}
		
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
		if (!isPrimitiveType() && !isExternal())
		{
			builder.append('*');
		}
		
		builder.append(' ').append(generateCSourceFragment());
		
		builder.append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		if (isSpecialFragment())
		{
			return generateSpecialFragment();
		}
		
		return generateUseOutput() + generateChildrenCSourceFragment();
	}
	
	/**
	 * Generate a variable name that will be used to keep the variables
	 * in their own "namespace" per-say.
	 * 
	 * @return The name of the variable that will be output to the C
	 * 		source output.
	 */
	public String generateCSourceName()
	{
		String name = getName();
		
		if (forceOriginal || isExternal())
		{
			return name;
		}
		
		if (this instanceof InstanceDeclarationNode)
		{
			InstanceDeclarationNode node = (InstanceDeclarationNode)this;
			
			if (node.isStatic())
			{
				ClassNode clazz = (ClassNode)getAncestorOfType(ClassNode.class);
				
				return "static_" + Fathom.LANGUAGE_NAME.toLowerCase() + "_" + clazz.generateUniquePrefix() + "_" + name;
			}
		}
		
		VariableNode existing = getExistingNode(getParent(), name);
		
		String str = Fathom.LANGUAGE_NAME.toLowerCase() + "_" + name;
		
		if (this instanceof FieldNode == false && existing instanceof LocalVariableNode)
		{
			ScopeNode scopeNode = TreeNode.getAncestorWithScope(existing.getParent()).getScopeNode();
			
			str += "_" + scopeNode.getID();
		}
		
		return str;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateFathomInput()
	 */
	@Override
	public String generateFathomInput()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getName());
		
		return builder.toString();
	}
	
	/**
	 * Generate a String for the code used to free memory of the
	 * specified variable.
	 * 
	 * @return The generated String for the code.
	 */
	public String generateFreeOutput()
	{
		if (isConstant())
		{
			return "";
		}
		
		StringBuilder builder = new StringBuilder();
		
		if (isPrimitiveType() || isExternal())
		{
			if (!isPrimitive())
			{
				builder.append("free(").append(generateUseOutput(true)).append(");\n");
			}
		}
		else
		{
			builder.append(Fathom.LANGUAGE_NAME.toLowerCase()).append("_del_").append(getType()).append('(').append('&').append(generateUseOutput(true)).append(", ").append(ExceptionNode.EXCEPTION_DATA_IDENTIFIER).append(");\n");
		}
		
		return builder.toString();
	}
	
	/**
	 * Get whether or not the VariableNode instance represents a
	 * declaration of a local variable.
	 * 
	 * @return Whether or not the instance represents a local variable
	 * 		declaration.
	 */
	public boolean isDeclaration()
	{
		return this instanceof LocalDeclarationNode;
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
		
		node.constantVal     = constantVal;
		node.arrayDimensions = arrayDimensions;
		node.external        = external;
		node.referenceVal    = referenceVal;
		node.pointerVal      = pointerVal;
		node.forceOriginal   = forceOriginal;
		
		return node;
	}
}

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

import net.fathomsoft.nova.tree.variables.ArrayAccessNode;
import net.fathomsoft.nova.tree.variables.ArrayNode;
import net.fathomsoft.nova.tree.variables.FieldNode;
import net.fathomsoft.nova.tree.variables.LocalVariableNode;
import net.fathomsoft.nova.tree.variables.VariableNode;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * TreeNode extension that represents something that returns a value.
 * For the rules on what can and cannot be an value node, refer to
 * {@link #setType(java.lang.String)}
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.4 May 2, 2014 at 11:14:37 PM
 * @version	v0.2.6 May 24, 2014 at 6:06:20 PM
 */
public class ValueNode extends TreeNode
{
	private int		arrayDimensions;
	
	private String	type;
	
	/**
	 * Get the TreeNode that represents the variable that contains
	 * the value. For example:<br>
	 * <blockquote><pre>
	 * ClassName obj = new ClassName();
	 * 
	 * obj.methodName();</pre></blockquote>
	 * In the previous statements, 'obj' is the variable and the method
	 * 'methodName()' is being called through the 'obj' variable.
	 * 
	 * @return The TreeNode that represents the calling variable.
	 */
	public ValueNode getReferenceNode()
	{
		return getReferenceNode(getParent());
	}
	
	/**
	 * Get the TreeNode that represents the variable that contains
	 * the value. For example:<br>
	 * <blockquote><pre>
	 * ClassName obj = new ClassName();
	 * 
	 * obj.methodName();</pre></blockquote>
	 * In the previous statements, 'obj' is the variable and the method
	 * 'methodName()' is being called through the 'obj' variable.
	 * 
	 * @param parent The parent of the ValueNode.
	 * @return The TreeNode that represents the calling variable.
	 */
	public ValueNode getReferenceNode(TreeNode parent)
	{
		if (parent instanceof ArrayAccessNode || parent instanceof ArrayNode)
		{
			parent = parent.getParent();
		}
		if (parent instanceof InstantiationNode)
		{
			InstantiationNode instantiation = (InstantiationNode)parent;
			
			IdentifierNode    identifier    = instantiation.getIdentifierNode();
			
			if (this == identifier || identifier instanceof ArrayNode)
			{
				parent = parent.getParent();
			}
			else
			{
				return identifier;
			}
		}
		if (parent instanceof ReturnNode)
		{
			parent = parent.getParent();
		}
		if (parent instanceof ValueNode == false)
		{
			TreeNode value = parent.getAncestorOfType(MethodNode.class);
			
			if (value == null)
			{
				value = parent.getAncestorOfType(ClassNode.class);
				
				//return null;
			}
			
			parent = value;
		}
		if (parent instanceof MethodNode)
		{
//			MethodNode method = parent.getFileNode().getClass(getName());
//			
//			if (method.getFileNode().containsImport(getName()))
//			{
//				
//			}
			
			TreeNode value = getObjectReferenceValue((MethodNode)parent);
			
			if (value == null)
			{
				value = parent.getAncestorOfType(ClassNode.class);
				
				//return null;
			}
			
			parent = value;
		}
		if (parent instanceof ClassNode)
		{
			return (ClassNode)parent;
		}
		
		return (ValueNode)parent;
	}
	
	/**
	 * Get the furthest node that is accessing the specified node.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * tree.next.calculateSize()</pre></blockquote>
	 * In the previous statement, "<code>tree</code>" is the context.
	 * 
	 * @return The furthest node that accesses the specified node.
	 */
	public ValueNode getContextNode()
	{
		return getContextNode(getReferenceNode());
	}
	
	/**
	 * Get the furthest node that is accessing the specified node.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * tree.next.calculateSize()</pre></blockquote>
	 * In the previous statement, "<code>tree</code>" is the context.
	 * 
	 * @param parent The parent of the ValueNode to use.
	 * @return The furthest node that accesses the specified node.
	 */
	public ValueNode getContextNode(ValueNode parent)
	{
		TreeNode next = parent;
		
		while (next instanceof LocalVariableNode || next instanceof FieldNode)
		{
			if (next instanceof LocalVariableNode == false && next instanceof FieldNode == false)//next.containsScope() || next instanceof ReturnNode)
			{
				return parent;
			}
			
			parent = (ValueNode)next;
			next   = parent.getParent();
		}
		
		return parent;
	}
	
	/**
	 * Get the last node that is accessed by the specified node.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * tree.next.calculateSize()</pre></blockquote>
	 * In the previous statement, "<code>calculateSize()</code>" is the
	 * last accessed node.
	 * 
	 * @return The last node that is accessed by the specified node.
	 */
	public ValueNode getLastAccessedNode()
	{
		ValueNode prev = this;
		ValueNode node = getAccessedNode();
		
		while (node != null)
		{
			prev = node;
			
			node = node.getAccessedNode();
		}
		
		return prev;
	}
	
	/**
	 * Get the next node that is accessed by the specified node.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * tree.next.calculateSize()</pre></blockquote>
	 * In the previous statement, "<code>calculateSize()</code>" is the
	 * accessed node of the "<code>next</code>" node, and "<code>next</code>"
	 * is the accessed node of the "<code>tree</code>" node.
	 * 
	 * @return The next node that is accessed by the specified node.
	 */
	public ValueNode getAccessedNode()
	{
		if (getChildren().size() <= 0)
		{
			return null;
		}
		
		return (ValueNode)getChild(0);
	}
	
	/**
	 * Get the name of the object reference identifier for the given
	 * Method node. Static methods return "__static__ClassName" and
	 * non-static methods return "this". The given method cannot be
	 * external.
	 * 
	 * @param method The method to get the object reference identifier
	 * 		name from.
	 * @return The name of the object reference identifier.
	 */
	public String getObjectReferenceIdentifier(MethodNode method)
	{
		if (!method.isStatic())
		{
			return ParameterListNode.OBJECT_REFERENCE_IDENTIFIER;
		}
		
		ClassNode clazz = (ClassNode)method.getAncestorOfType(ClassNode.class);
		
		return "__static__" + clazz.getName();
	}
	
	/**
	 * Get the ValueNode that the method was called with for the given
	 * MethodCallNode's method node, if it was not called with a specific
	 * object. Static methods return "__static__ClassName" and non-static
	 * methods return "this". The call cannot be that of an external
	 * method.
	 * 
	 * @param method The method to get the ValueNode from.
	 * @return The ValueNode that the method was called with.
	 */
	public ValueNode getObjectReferenceValue(MethodNode method)
	{
		String    identifier = getObjectReferenceIdentifier(method);
		
		ValueNode val        = (ValueNode)getExistingNode(method, identifier);
		
		if (val != null)
		{
			return val.clone();
		}
		
		return null;
	}
	
	/**
	 * Get the ClassNode parent instance of the VariableNode.
	 * 
	 * @return The nearest ClassNode instance that contains this variable.
	 */
	public ClassNode getClassNode()
	{
		return (ClassNode)getAncestorOfType(ClassNode.class, true);
	}
	
	/**
	 * Check whether or not the given value is accessed within its direct
	 * parent class.
	 * 
	 * @param The node to check.
	 * @return Whether or not the node was accessed through its parent
	 * 		class.
	 */
	public boolean isContainingClass(ValueNode node)
	{
		ClassNode clazz = node.getClassNode();
		
		if (this == clazz)
		{
			return true;
		}
//		else if (getType().equals(clazz.getName()))
//		{
//			return true;
//		}System.out.println(getType() + " is not " + clazz.getName());
		else if (this instanceof LocalVariableNode)
		{
			LocalVariableNode param = (LocalVariableNode)this;
			
			if (param.getName().equals(MethodNode.getObjectReferenceIdentifier()))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Get whether a variable's type is a primitive type or not.<br>
	 * <br>
	 * For the list of primitive values, see
	 * {@link net.fathomsoft.nova.util.SyntaxUtils#isPrimitiveType(String)}
	 * 
	 * @return Whether a variable's type is a primitive type or not.
	 */
	public boolean isPrimitiveType()
	{
		return SyntaxUtils.isPrimitiveType(getType());
	}
	
	/**
	 * Get whether a value is primitive or not. A value is primitive
	 * if it has a primitive type AND is NOT an array. Arrays are NOT a
	 * primitive type.<br>
	 * <br>
	 * For the list of primitive values, see
	 * {@link net.fathomsoft.nova.util.SyntaxUtils#isPrimitiveType(String)}.
	 * 
	 * @return Whether a variable is primitive or not.
	 */
	public boolean isPrimitive()
	{
		return isPrimitiveType() && !isArray();
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
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		return generateCSourceFragment() + ";\n";
	}
	
	/**
	 * Generate the C output for when this value node is being used
	 * as an argument for a method call.
	 * 
	 * @return The C output for when this value node is being used
	 * 		as an argument for a method call.
	 */
	public String generateArgumentReference()
	{
		return generateUseOutput() + generateChildrenCSourceFragment(true, true);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return generateChildrenCSourceFragment(false);
	}
	
	/**
	 * Generate a String representing the accessed nodes.
	 * 
	 * @return The generated String.
	 */
	public String generateChildrenCSourceFragment()
	{
		return generateChildrenCSourceFragment(true);
	}
	
	/**
	 * Generate a String representing the accessed nodes.
	 * 
	 * @param reference Whether or not to start the string off with
	 * 		a "->" reference operator.
	 * @return The generated String.
	 */
	public String generateChildrenCSourceFragment(boolean reference)
	{
		return generateChildrenCSourceFragment(reference, false);
	}
	
	/**
	 * Generate a String representing the accessed nodes.
	 * 
	 * @param reference Whether or not to start the string off with
	 * 		a "->" reference operator.
	 * @param argument Whether or not the specified node is being
	 * 		output as an argument to a method call.
	 * @return The generated String.
	 */
	public String generateChildrenCSourceFragment(boolean reference, boolean argument)
	{
		StringBuilder builder = new StringBuilder();
		
		if (getChildren().size() <= 0)
		{
			return "";
		}
		
		TreeNode child = getChild(0);
		
		if (argument && (child instanceof MethodCallNode || child instanceof InstantiationNode))
		{
			return "";
		}
		
		if (reference)
		{
			builder.append("->");
		}
		
		if (child instanceof ValueNode)
		{
			ValueNode value = (ValueNode)child;
			
			if (value.isSpecialFragment())
			{
				builder.append(value.generateUseOutput() + value.generateChildrenCSourceFragment(true, true));
				
				return builder.toString();
			}
		}
		
		String s = child.generateCSourceFragment();
		
		if (s != null)
		{
			builder.append(child.generateCSourceFragment());
		}
		else
		{
			if (reference)
			{
				builder.delete(builder.length() - 2, builder.length());
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * If the ValueNode accesses a method call, generate a specialized
	 * output.
	 * 
	 * @return A specialized String generation.
	 */
	public String generateSpecialFragment()
	{
		for (int i = getChildren().size() - 1; i >= 0; i--)
		{
			TreeNode child = getChild(i);
			
			if (child instanceof MethodCallNode || child instanceof InstantiationNode)
			{
				return child.generateCSourceFragment();
			}
			else if (child instanceof ValueNode)
			{
				ValueNode value = (ValueNode)child;
				
				if (value.isSpecialFragment())
				{
					return value.generateSpecialFragment();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Get whether or not the ValueNode accesses a method call.
	 * 
	 * @return Whether or not the ValueNode accesses a method call.
	 */
	public boolean isSpecialFragment()
	{
		for (int i = getChildren().size() - 1; i >= 0; i--)
		{
			TreeNode child = getChild(i);
			
			if (child instanceof MethodCallNode || child instanceof InstantiationNode)
			{
				return true;
			}
			else if (child instanceof ValueNode)
			{
				ValueNode value = (ValueNode)child;
				
				if (value.isSpecialFragment())
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Generate the representation of when the value node is being used
	 * in action.
	 * 
	 * @return What the method call looks like when it is being used in
	 * 		action
	 */
	public String generateUseOutput()
	{
		return getType();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateNovaInput()
	 */
	@Override
	public String generateNovaInput()
	{
//		if (isSpecialFragment())
//		{
//			return getChild(0).generateNovaInput();
//		}
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(generateUseOutput());
		
		if (getChildren().size() > 0)
		{
			builder.append('.').append(getChild(0).generateNovaInput());
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone()
	 */
	@Override
	public ValueNode clone()
	{
		ValueNode node = new ValueNode();
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given IdentifierNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ValueNode cloneTo(ValueNode node)
	{
		super.cloneTo(node);

		node.arrayDimensions = arrayDimensions;
		node.type = type;
		
		return node;
	}
}
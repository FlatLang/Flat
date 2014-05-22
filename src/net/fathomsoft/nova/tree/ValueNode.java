/**
 * The Nova Programming Language. Write Unbelievable Code.
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
package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.tree.variables.LocalVariableNode;
import net.fathomsoft.nova.util.SyntaxUtils;

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
		return (ClassNode)getAncestorOfType(ClassNode.class);
	}
	
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
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
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
	
	/**
	 * Generate the representation of when the value node is being used
	 * in action.
	 * 
	 * @return What the method call looks like when it is being used in
	 * 		action
	 */
	public String generateUseOutput()
	{
		return null;
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
		
		node.type = type;
		
		return node;
	}
}
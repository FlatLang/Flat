package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.ArrayAccessNode;
import net.fathomsoft.nova.tree.variables.ArrayNode;
import net.fathomsoft.nova.tree.variables.FieldNode;
import net.fathomsoft.nova.tree.variables.LocalVariableNode;
import net.fathomsoft.nova.tree.variables.VariableNode;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * TreeNode extension that represents something that returns a value.
 * For the rules on what can and cannot be an value node, refer to
 * {@link #setType(java.lang.String)}
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.4 May 2, 2014 at 11:14:37 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class ValueNode extends TreeNode
{
	private byte	dataType;
	
	private int		arrayDimensions;
	
	private String	type;
	
	public static final byte	VALUE = 1, POINTER = 2, REFERENCE = 3;

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public ValueNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		dataType = VALUE;
	}
	
	/**
	 * Get the node that returns a value. (Which is the specified node).
	 * 
	 * @return Returns the specified ValueNode.
	 */
	public ValueNode getReturnedNode()
	{
		return this;
	}
	
	/**
	 * Get the name of the object reference identifier for the given
	 * Method node. Static methods return "ClassName" and
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
		
		return clazz.getName();
	}
	
	/**
	 * Get the ValueNode that the method was called with for the given
	 * MethodCallNode's method node, if it was not called with a specific
	 * object. Static methods return "ClassName" and non-static
	 * methods return "this". The call cannot be that of an external
	 * method.
	 * 
	 * @param method The method to get the ValueNode from.
	 * @return The ValueNode that the method was called with.
	 */
	public IdentifierNode getObjectReferenceNode(MethodNode method)
	{
		String identifier = getObjectReferenceIdentifier(method);
		
		IdentifierNode id = (IdentifierNode)SyntaxTree.getExistingNode(method, identifier);
		
		if (id != null)
		{
			return id.clone(id.getParent(), id.getLocationIn());
		}
		
		return null;
	}
	
	/**
	 * Check whether or not the given value is accessed within its direct
	 * parent class.
	 * 
	 * @param node The node to check.
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
	 * Get whether the type of the ValueNode is external or not.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * external type FILE;
	 * 
	 * FILE varName;</pre></blockquote>
	 * In the code above <u><code>varName</code></u>'s type of
	 * "<u><code>FILE</code></u>" is external.
	 * 
	 * @return Whether or not the type of the valueNode is external.
	 */
	public boolean isExternalType()
	{
		return type != null && getClassNode().containsExternalType(type);
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
		if (!SyntaxUtils.isValidType(this, type))
		{
			SyntaxMessage.error("Type '" + type + "' does not exist", this);
		}
		
		this.type = type;
	}
	
	/**
	 * Get the ClassNode that represents the type of the specified
	 * ValueNode. If the type is primitive, this will return the
	 * wrapper class of the primitive type.
	 * 
	 * @return The ClassNode instance of the type.
	 */
	public ClassNode getTypeClass()
	{
		ProgramNode program = getProgramNode();
		String      name    = getTypeClassName();
		
		ClassNode   clazz   = program.getClassNode(name);
		
		return clazz;
	}
	
	/**
	 * Get the name of the class that represents the type of the specified
	 * ValueNode. If the type is primitive, this will return the wrapper
	 * class name of the primitive type.
	 * 
	 * @return The name of the class of the type.
	 */
	public String getTypeClassName()
	{
		return getTypeClassName(getArrayDimensions());
	}
	
	/**
	 * Get the name of the class that represents the type of the specified
	 * ValueNode. If the type is primitive, this will return the wrapper
	 * class name of the primitive type.
	 * 
	 * @return The name of the class of the type.
	 */
	public String getTypeClassName(int arrayDimensions)
	{
		ProgramNode program = getProgramNode();
		
		ClassNode   clazz   = program.getClassNode(type);
		
		if (clazz != null)
		{
			return clazz.getName();
		}
		
		if (SyntaxUtils.isPrimitiveType(type))
		{
			String name = SyntaxUtils.getPrimitiveWrapperClassName(type);
			
			if (name != null)
			{
				if (arrayDimensions > 0)
				{
					name += "Array";
				}
				
				clazz = program.getClassNode(name);
				
				if (clazz == null)
				{
					throw new RuntimeException("SADF " + name);
//					SyntaxMessage.error("Could not find class '" + name + "'", this);
				}
				
				return clazz.getName();
			}
		}
		
		return null;
	}
	
	/**
	 * Get whether or not the identifier is a reference.
	 * 
	 * @return Whether or not the identifier is a reference.
	 */
	public boolean isReference()
	{
		return dataType == REFERENCE;
	}
	
	/**
	 * Get whether or not the identifier is a value, pointer, or
	 * reference.<br>
	 * <br>
	 * Possible values include:
	 * <ul>
	 * 	<li><b>VariableNode.VALUE</b> - If the variable type simply refers to a value.</li>
	 * 	<li><b>VariableNode.POINTER</b> - If the variable type is a pointer.</li>
	 * 	<li><b>VariableNode.REFERENCE</b> - If the variable type is a reference.</li>
	 * </ul>
	 * 
	 * @return The data type that the variable is.
	 */
	public byte getDataType()
	{
		return dataType;
	}
	
	/**
	 * Set whether or not the identifier is a value, pointer, or
	 * reference.<br>
	 * <br>
	 * Possible values include:
	 * <ul>
	 * 	<li><b>VariableNode.VALUE</b> - If the variable type simply refers to a value.</li>
	 * 	<li><b>VariableNode.POINTER</b> - If the variable type is a pointer.</li>
	 * 	<li><b>VariableNode.REFERENCE</b> - If the variable type is a reference.</li>
	 * </ul>
	 * 
	 * @param type The data type that the variable is.
	 */
	public void setDataType(byte type)
	{
		this.dataType = type;
	}
	
	/**
	 * Get whether or not the identifier is a pointer.
	 * 
	 * @return Whether or not the identifier is a pointer.
	 */
	public boolean isPointer()
	{
		return dataType == POINTER;
	}
	
	/**
	 * Get the data type that is required within the context that the
	 * specified ValueNode is within.
	 * 
	 * @return The data type that is required within the context that the
	 * 		specified ValueNode is within.
	 */
	public byte getRequiredDataType()
	{
		TreeNode parent = getParent();
		
		byte     type   = dataType;
		
		if (parent instanceof ArgumentListNode)
		{
			MethodCallNode call  = (MethodCallNode)parent.getParent();
			ParameterNode  param = call.getCorrespondingParameter(this);
			
			//TODO: make support for multidimensional arrays too....
			if (!call.isExternal() || !param.isPointer() || dataType != VALUE || !isArray())
			{
				type = param.getDataType();
			}
		}
		else if (parent instanceof AssignmentNode)
		{
			AssignmentNode assignmentNode = (AssignmentNode)parent;
			VariableNode   assignee       = assignmentNode.getAssigneeNode();
			
			if (this instanceof VariableNode == false || !((VariableNode)this).isSameVariable(assignee))
			{
				type = assignee.getDataType();
			}
		}
		else if (parent instanceof ReturnNode)
		{
			MethodNode method = (MethodNode)getAncestorOfType(MethodNode.class);
			
			type = method.getDataType();
		}
		
		return type;
	}
	
	/**
	 * Generate the text that is required for the data type in the
	 * current context.
	 * 
	 * @return The text that is required.
	 */
	public String generateDataTypeOutput()
	{
		return generateDataTypeOutput(dataType);
	}
	
	/**
	 * Generate the text that is required for the data type in the
	 * current context.
	 * 
	 * @param dataType The data type to compare against.
	 * @return The text that is required.
	 */
	public String generateDataTypeOutput(byte dataType)
	{
		byte required = getRequiredDataType();
		
		if (required == dataType)
		{
			return "";
		}
		
		if (required == REFERENCE)
		{
			if (dataType == VALUE)
			{
				return "&";
			}
			else if (dataType == POINTER)
			{
				return "**";
			}
		}
		else if (required == VALUE)
		{
			if (dataType == REFERENCE)
			{
				return "*";
			}
			else if (dataType == POINTER)
			{
				return "*";
			}
		}
		else if (required == POINTER)
		{
			if (dataType == VALUE)
			{
				return "&";
			}
			else if (dataType == REFERENCE)
			{
				return "*";
			}
		}
		
		if (required == POINTER)
		{
			return "*";
		}
		else if (required == REFERENCE)
		{
			return "&";
		}
		
		return "";
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
	 * Generate the C syntax for the type of the specified ValueNode.
	 * 
	 * @return The C syntax for the type of the ValueNode.
	 */
	public String generateCTypeOutput()
	{
		if (type.equals("long"))
		{
			return "long_long";
		}
		
		return type;
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
		return generateCTypeOutput();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateNovaInput(boolean)
	 */
	@Override
	public String generateNovaInput(boolean outputChildren)
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(generateUseOutput());
		
		if (outputChildren)
		{
			IdentifierNode accessed = ((IdentifierNode)this).getAccessedNode();
			
			if (accessed != null)
			{
				builder.append('.').append(accessed.generateNovaInput());
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode, Location)
	 */
	@Override
	public ValueNode clone(TreeNode temporaryParent, Location locationIn)
	{
		ValueNode node = new ValueNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ValueNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ValueNode cloneTo(ValueNode node)
	{
		super.cloneTo(node);

		node.arrayDimensions = arrayDimensions;
		node.type            = type;
		node.dataType        = dataType;
		
		return node;
	}
}
package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.error.UnimplementedOperationException;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Node extension that represents something that returns a value.
 * For the rules on what can and cannot be an value node, refer to
 * {@link #setType(java.lang.String)}
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.4 May 2, 2014 at 11:14:37 PM
 * @version	v0.2.34 Oct 1, 2014 at 9:51:33 PM
 */
public abstract class Value extends Node
{
	public static final byte	VALUE = 1, POINTER = 2, REFERENCE = 3;
	
	public static final String NULL_IDENTIFIER = "nova_null";
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Value(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get the node that returns a value. (Which is the specified node).
	 * 
	 * @return Returns the specified Value.
	 */
	public Value getReturnedNode()
	{
		return this;
	}
	
	/**
	 * Get the name of the object reference identifier for the given
	 * Method node. Static methods return "ClassName" and
	 * non-static methods return "this". The given method cannot be
	 * external.
	 * 
	 * @param methodDeclaration The method to get the object reference identifier
	 * 		name from.
	 * @return The name of the object reference identifier.
	 */
	public String getObjectReferenceIdentifier(CallableMethod methodDeclaration)
	{
		if (methodDeclaration.isInstance())
		{
			return ParameterList.OBJECT_REFERENCE_IDENTIFIER;
		}
		
		ClassDeclaration clazz = ((Node)methodDeclaration).getParentClass();
		
		return clazz.getName();
	}
	
	/**
	 * Get the Value that the method was called with for the given
	 * MethodCall's method node, if it was not called with a specific
	 * object. Static methods return the ClassDeclaration and non-static
	 * methods return the "this" instance. The call cannot be that of an
	 * external method.
	 * 
	 * @param methodDeclaration The method to get the Value from.
	 * @return The Value that the method was called with.
	 */
	public Identifier getObjectReferenceNode(CallableMethod methodDeclaration)
	{
		Node     method     = ((Node)methodDeclaration);
		String   identifier = getObjectReferenceIdentifier(methodDeclaration);
		Variable var        = SyntaxTree.getUsableExistingNode(method, identifier, method.getLocationIn());
		
		if (var != null)
		{
			return var;
		}
		
		return method.getParentClass();
	}
	
	/**
	 * Check to see if the given Value type is valid. If it is not,
	 * this will throw an exception if it is required. If it isn't
	 * required it will return false. If it is valid, it will return
	 * true.<br>
	 * In other words, check if the given String is a primitive type name
	 * or declared class name.
	 * 
	 * @param type The type to validate.
	 * @param require Whether or not throw an error if anything goes
	 * 		wrong.
	 * @return Whether or not the given value is valid.
	 */
	public boolean checkType(String type, boolean require)
	{
		if (!SyntaxUtils.isValidType(this, type))
		{
			return SyntaxMessage.queryError("Type '" + type + "' does not exist", this, require);
		}
		
		return true;
	}
	
	/**
	 * Check whether or not the given value is accessed within its direct
	 * parent class.
	 * 
	 * @param node The node to check.
	 * @return Whether or not the node was accessed through its parent
	 * 		class.
	 */
	public boolean isContainingClass(Value node)
	{
		ClassDeclaration clazz = node.getParentClass();
		
		if (this == clazz)
		{
			return true;
		}
		else if (this instanceof Variable)
		{
			Variable param = (Variable)this;
			
			if (param.getName().equals(ParameterList.OBJECT_REFERENCE_IDENTIFIER))
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
	 * The text that represents an array in the C language.
	 * 
	 * @return The text that represents an array in the C language.
	 */
	public String generateArrayText()
	{
		String text = "";
		
		for (int i = 0; i < getArrayDimensions(); i++)
		{
			text += "*";
		}
		
		return text;
	}
	
	/**
	 * The text that represents an array in the Nova language.
	 * 
	 * @return The text that represents an array in the Nova language.
	 */
	public String generateNovaArrayText()
	{
		String text = "";
		
		for (int i = 0; i < getArrayDimensions(); i++)
		{
			text += "[]";
		}
		
		return text;
	}
	
	/**
	 * Get whether the type of the Value is external or not.<br>
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
		return getType() != null && getParentClass().containsExternalType(getType());
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
	 * @return Whether or not the type was set successfully.
	 */
	public boolean setType(String type)
	{
		return setType(type, true, true);
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
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return Whether or not the type was set successfully.
	 */
	public boolean setType(String type, boolean require)
	{
		return setType(type, require, true);
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
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @param checkType Whether or not to check if the type is valid.
	 * @return Whether or not the type was set successfully.
	 */
	public boolean setType(String type, boolean require, boolean checkType)
	{
		return setType(type, require, checkType, true);
	}
	
	/**
	 * Get the ClassDeclaration that represents the type of the specified
	 * Value. If the type is primitive, this will return the
	 * wrapper class of the primitive type.
	 * 
	 * @return The ClassDeclaration instance of the type.
	 */
	public ClassDeclaration getTypeClass()
	{
		return getProgram().getClassDeclaration(getTypeClassLocation());
	}
	
	/**
	 * Get the name of the class that represents the type of the specified
	 * Value. If the type is primitive, this will return the wrapper
	 * class name of the primitive type.
	 * 
	 * @return The name of the class of the type.
	 */
	public String getTypeClassName()
	{
		return SyntaxUtils.getClassName(getTypeClassLocation());
	}
	
	public String getTypeClassLocation()
	{
		String type = null;
		
		if (isGenericType() && getGenericReturnType() != null)//getParentClass(true).containsGenericParameter(getType()))
		{
			type = getGenericReturnType();//getParentClass().getGenericParameter(getType()).getDefaultType();
		}
		else
		{
			type = SyntaxUtils.getPrimitiveWrapperClassName(getType());
		}
		
		FileDeclaration file = getFileDeclaration();
		
		if (this instanceof Identifier)
		{
			Identifier id = (Identifier)this;
			
			if (id.isAccessed())
			{
				if (isGenericType())
				{
					id = id.getAccessingNode();
				}
				
				file = id.getReferenceNode().getDeclaringClass().getFileDeclaration();
			}
		}
		
		String location = file.getImportList().getAbsoluteClassLocation(type);
		
//		if (location == null)
//		{
//			SyntaxUtils.throwImportException(this, type, getLocationIn());
//		}
		
		return location;
	}
	
	/**
	 * Get whether or not the identifier is a reference.
	 * 
	 * @return Whether or not the identifier is a reference.
	 */
	public boolean isReference()
	{
		return getDataType() == REFERENCE;
	}
	
	/**
	 * Get whether or not the identifier is a pointer.
	 * 
	 * @return Whether or not the identifier is a pointer.
	 */
	public boolean isPointer()
	{
		return getDataType() == POINTER;
	}
	
	/**
	 * Get the data type that is required within the context that the
	 * specified Value is within.
	 * 
	 * @return The data type that is required within the context that the
	 * 		specified Value is within.
	 */
	public byte getRequiredDataType()
	{
		Node parent = getParent();
		
		byte type   = getDataType();
		
		if (parent instanceof MethodCallArgumentList)
		{
			MethodCall call  = (MethodCall)parent.getAncestorOfType(MethodCall.class);
			Value      param = call.getCorrespondingParameter(this);
			
			//TODO: make support for multidimensional arrays too....
			if (!call.isExternal() || !param.isPointer() || type != VALUE || !isArray())
			{
				type = param.getDataType();
			}
		}
		else if (parent instanceof Assignment)
		{
			Assignment assignment = (Assignment)parent;
			Variable   assignee   = (Variable)assignment.getAssigneeNode();
			
			if (this instanceof Variable == false || !((Variable)this).isSameVariable(assignee))
			{
				type = assignee.getDataType();
			}
		}
		else if (parent instanceof Return)
		{
			type = getParentMethod().getDataType();
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
		return generateDataTypeOutput(getDataType());
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
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		return generateCHeaderFragment(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeaderFragment(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeaderFragment(StringBuilder builder)
	{
		return generateCSourceFragment(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return generateCSourceFragment(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
//		Value required  = null;
//		
//		boolean sameType = getType().equals(required.getType());
//		
//		if (!sameType)
//		{
//			required.generateCTypeCast(builder);
//		}
		
		return generateCType(builder);
	}
	
	/**
	 * Generate the C null representation for the given value type.
	 * 
	 * @return The generated null output.
	 */
	public StringBuilder generateCNullOutput()
	{
		return generateCNullOutput(new StringBuilder());
	}
	
	/**
	 * Generate the C null representation for the given value type.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @return The generated null output.
	 */
	public StringBuilder generateCNullOutput(StringBuilder builder)
	{
		return generateCTypeCast(builder).append(NULL_IDENTIFIER);
	}
	
	public StringBuilder generateCTypeClassName()
	{
		return generateCTypeClassName(new StringBuilder());
	}
	
	public StringBuilder generateCTypeClassName(StringBuilder builder)
	{
		String type = getType();
		
		if (isGenericType())
		{
			type = getGenericReturnType();
		}
		
		ClassDeclaration clazz = SyntaxUtils.getImportedClass(getFileDeclaration(), type);
		
		if (clazz == null)
		{
			builder.append(type);
		}
		else
		{
			clazz.generateCSourceName(builder);
		}
		
		return builder;
	}
	
	/**
	 * Generate the C syntax for the type of the specified Value.
	 * 
	 * @return The C syntax for the type of the Value.
	 */
	public StringBuilder generateCType()
	{
		return generateCType(new StringBuilder());
	}
	
	/**
	 * Generate the C syntax for the type of the specified Value.
	 * 
	 * @param builder The StringBuider to append the data to.
	 * @return The C syntax for the type of the Value.
	 */
	public StringBuilder generateCType(StringBuilder builder)
	{
		return generateCType(builder, true);
	}
	
	/**
	 * Generate the C syntax for the type of the specified Value.
	 * 
	 * @param builder The StringBuider to append the data to.
	 * @param checkArray Whether or not to check if the type is an array.
	 * @return The C syntax for the type of the Value.
	 */
	public StringBuilder generateCType(StringBuilder builder, boolean checkArray)
	{
		if (getType() == null)
		{
			builder.append("void");
		}
		else if (getType().equals("long"))
		{
			builder.append("long_long");
		}
		else if (getType().equals("bool"))
		{
			builder.append("char");
		}
		else if (getType().equals("byte"))
		{
			builder.append("char");
		}
		else
		{
			generateCTypeClassName(builder);
		}
		
		if (isReference())
		{
			builder.append('&');
		}
		else if (isPointer())
		{
			builder.append('*');
		}
		if (checkArray && isArray())
		{
			builder.append(generateArrayText());
		}
		if (getParentMethod() instanceof Destructor && this instanceof Identifier)
		{
			if (((Identifier)this).getName().equals(ParameterList.OBJECT_REFERENCE_IDENTIFIER))
			{
				builder.append('*');
			}
		}
//		if (!isPrimitiveType() && !isExternalType())
//		{
//			builder.append('*');
//		}
		
		return builder;
	}
	
	/**
	 * Generate the Nova syntax for the type of the specified Value's type.
	 * 
	 * @return The Nova syntax for the type of the Value.
	 */
	public StringBuilder generateNovaType()
	{
		return generateNovaType(new StringBuilder());
	}
	
	/**
	 * Generate the Nova syntax for the type of the specified Value'stype.
	 * 
	 * @param builder The StringBuider to append the data to.
	 * @return The Nova syntax for the type of the Value.
	 */
	public StringBuilder generateNovaType(StringBuilder builder)
	{
		return generateNovaType(builder, true);
	}
	
	/**
	 * Generate the Nova syntax for the type of the specified Value's type.
	 * 
	 * @param builder The StringBuider to append the data to.
	 * @param checkArray Whether or not to check if the type is an array.
	 * @return The Nova syntax for the type of the Value.
	 */
	public StringBuilder generateNovaType(StringBuilder builder, boolean checkArray)
	{
		builder.append(getType());
		
		if (checkArray && isArray())
		{
			builder.append(generateNovaArrayText());
		}
		
		return builder;
	}
	
	/**
	 * Generate a String representing a type cast for the specified Value
	 * in C syntax.
	 * 
	 * @return The StringBuilder with the appended data.
	 */
	public StringBuilder generateCTypeCast()
	{
		return generateCTypeCast(new StringBuilder());
	}
	
	/**
	 * Generate a String representing a type cast for the specified Value
	 * in C syntax.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @return The StringBuilder with the appended data.
	 */
	public StringBuilder generateCTypeCast(StringBuilder builder)
	{
		return builder.append('(').append(generateCType()).append(')');
	}
	
	/**
	 * Generate the representation of when the value node is being used
	 * in action.
	 * 
	 * @return What the method call looks like when it is being used in
	 * 		action
	 */
	public final StringBuilder generateCUseOutput()
	{
		return generateCUseOutput(new StringBuilder());
	}
	
	/**
	 * Generate the representation of when the value node is being used
	 * in action.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @return What the method call looks like when it is being used in
	 * 		action
	 */
	public StringBuilder generateCUseOutput(StringBuilder builder)
	{
		return generateCType(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		return generateCUseOutput(builder);
	}
	
	/**
	 * Get whether or not the specified Node's type can be determined NOT
	 * to be virtual or not at compilation time, i.e. If the program
	 * doesn't have to use the vtable.
	 * 
	 * @return Wether or not the type can be determined to NOT be virtual
	 * 		at compilation time.
	 */
	public boolean isVirtualTypeKnown()
	{
		return false;
	}
	
	public GenericType getGenericType()
	{
		if (getParentClass() == null)
		{
			return null;
		}
		
		return getParentClass().getGenericParameter(getType());
	}
	
	public final boolean isGenericType()
	{
		return getGenericType() != null;
	}
	
	public String getGenericReturnType()
	{
		throw new UnimplementedOperationException("The getGenericReturnType() method must be implemented by class " + this.getClass().getName());
//		return getGenericType();
	}
	
	public GenericCompatible getGenericDeclaration()
	{
		throw new UnimplementedOperationException("The getGenericDeclaration() method must be implemented by class " + this.getClass().getName());
	}
	
	/**
	 * Get the type that the statement returns. For an example of what a
	 * value type looks like, see {@link #setType(String)}
	 * 
	 * @return The type that the statement returns.
	 */
	public abstract String getType();
	
	/**
	 * Get the amount of dimensions that the array has, if any. For an
	 * example of what a array declarations and dimensions look like
	 * {@link #setArrayDimensions(int)}
	 * 
	 * @return The amount of dimensions that the array has, if any.
	 */
	public abstract int getArrayDimensions();
	
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
	public abstract void setArrayDimensions(int arrayDimensions);
	
	/**
	 * Set the type that this statement returns.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * private static int index;</pre></blockquote>
	 * The type of the variable returns is "int"
	 * 
	 * @param type The type that this statement returns.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @param checkType Whether or not to check if the type is valid.
	 * @param checkDataType Whether or not to check the data type of
	 * 		the given type.
	 * @return Whether or not the type was set successfully.
	 */
	public abstract boolean setType(String type, boolean require, boolean checkType, boolean checkDataType);
	
	/**
	 * Get whether or not the identifier is a value, pointer, or
	 * reference.<br>
	 * <br>
	 * Possible values include:
	 * <ul>
	 * 	<li><b>Variable.VALUE</b> - If the variable type simply refers to a value.</li>
	 * 	<li><b>Variable.POINTER</b> - If the variable type is a pointer.</li>
	 * 	<li><b>Variable.REFERENCE</b> - If the variable type is a reference.</li>
	 * </ul>
	 * 
	 * @return The data type that the variable is.
	 */
	public abstract byte getDataType();
	
	/**
	 * Set whether or not the identifier is a value, pointer, or
	 * reference.<br>
	 * <br>
	 * Possible values include:
	 * <ul>
	 * 	<li><b>Variable.VALUE</b> - If the variable type simply refers to a value.</li>
	 * 	<li><b>Variable.POINTER</b> - If the variable type is a pointer.</li>
	 * 	<li><b>Variable.REFERENCE</b> - If the variable type is a reference.</li>
	 * </ul>
	 * 
	 * @param type The data type that the variable is.
	 */
	public abstract void setDataType(byte type);
	
	/**
	 * Test the Value class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
}
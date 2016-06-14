package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.error.UnimplementedOperationException;
import net.fathomsoft.nova.tree.NovaParameterList.ReturnParameterList;
import net.fathomsoft.nova.tree.generics.GenericTypeArgument;
import net.fathomsoft.nova.tree.generics.GenericTypeArgumentList;
import net.fathomsoft.nova.tree.generics.GenericTypeParameter;
import net.fathomsoft.nova.tree.generics.GenericTypeParameterDeclaration;
import net.fathomsoft.nova.tree.variables.Super;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.tree.variables.VirtualLocalDeclaration;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Node extension that represents something that returns a value.
 * For the rules on what can and cannot be an value node, refer to
 * {@link #setType(java.lang.String)}
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.4 May 2, 2014 at 11:14:37 PM
 * @version	v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public abstract class Value extends Node implements AbstractValue
{
	public static final byte	VALUE = 1, POINTER = 2, REFERENCE = 3, DOUBLE_POINTER = 4;
	
	public static final String NULL_IDENTIFIER = "nova_null";
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Value(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public GenericCompatible getContext()
	{
		if (getParent() instanceof Assignment)
		{
			return ((Assignment)getParent()).getAssignee().getContext();
		}
		else if (getParent() instanceof Return)
		{
			return getParentMethod();
		}
		else if (getParent() instanceof MethodCallArgumentList)
		{
			MethodCallArgumentList args = (MethodCallArgumentList)getParent();
			
			VariableDeclaration d = args.getMethodCall().getMethodDeclaration();
			
			if (d instanceof MutatorMethod)
			{
				MutatorMethod m = (MutatorMethod)d;
				
				return m.getDeclaration();
			}
		}
		
		return this;
	}

	public GenericTypeArgumentList getGenericTypeArgumentList()
	{
		if (getNumChildren() > super.getNumDefaultChildren() + 0 && getChild(super.getNumDefaultChildren() + 0) instanceof GenericTypeArgumentList)
		{
			return (GenericTypeArgumentList)getChild(super.getNumDefaultChildren() + 0);
		}
		
		return null;
	}
	
	/**
	 * Generate a IValue instance from the given type.
	 * 
	 * @param temporaryParent The temporary parent of the new Node.
	 * @param locationIn The location of the new Node.
	 * @param type The type to set for the Value.
	 * @param require Whether or not a successful decode is required.
	 * @return The generated IValue instance.
	 */
	public static IValue generateFromType(Node temporaryParent, Location locationIn, String type, boolean require)
	{
		IValue value = new IValue(temporaryParent, locationIn);
		
		if (!value.setType(type, require, true))
		{
			// We already know that it is not required because if it was,
			// it would have thrown an exception and never reached here.
			return null;
		}
		
		return value;
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
			SyntaxUtils.isValidType(this, type);
			return SyntaxUtils.invalidType(this, type, require);
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
	
	public boolean isReturnParameter()
	{
		return getAncestorOfType(ReturnParameterList.class) != null;
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
		return SyntaxUtils.isPrimitiveType(getType()) || (isWithinExternalContext() && SyntaxUtils.isExternalPrimitiveType(getType())) || getType() != null && getType().equals("Number");
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
		return (isPrimitiveType() || isWithinExternalContext() && SyntaxUtils.isExternalPrimitiveType(getType())) && !isArray();
	}
	
	public void setPrimitiveWrapperType()
	{
		if (isPrimitiveType())
		{
			setDataType(POINTER);
		}
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
	
	public String generateGenericType()
	{
		GenericTypeArgumentList args = getGenericTypeArgumentList();
		
		if (args != null)
		{
			return args.generateNovaInput().toString();
		}
		
		return "";
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
		
		if (isGenericType() && getGenericReturnType() != null)//getParentClass(true).containsGenericTypeParameter(getType()))
		{
			type = getGenericReturnType();//getParentClass().getGenericTypeParameter(getType()).getDefaultType();
		}
		else
		{
			type = SyntaxUtils.getPrimitiveNovaType(getType());
		}
		
		return SyntaxUtils.getTypeClassLocation(this, type);
	}
	
	public FileDeclaration getReferenceFile()
	{
		if (this instanceof Accessible)
		{
			Accessible id = (Accessible)this;
			
			if (id.isAccessed())
			{
				if (isGenericType())
				{
					id = id.getAccessingNode();
				}
				
				Value reference = (Value)id.getReferenceNode();
				
				ClassDeclaration type = reference.getTypeClass();
				
				if (type == null)
				{
					SyntaxUtils.invalidType(reference, reference.getType(), true);
				}
				
				return type.getFileDeclaration();
			}
		}
		if (this instanceof Variable && ((Variable)this).getDeclaration() instanceof VirtualLocalDeclaration)
		{
			return ((VirtualLocalDeclaration)((Variable)this).getDeclaration()).getReference().getReturnedNode().getTypeClass().getFileDeclaration();
		}
		else if (this instanceof VirtualLocalDeclaration)
		{
			return ((VirtualLocalDeclaration)this).getReference().getReturnedNode().getTypeClass().getFileDeclaration();
		}
		
		return getFileDeclaration();
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
			Variable   assignee   = assignment.getAssignedNode();
			
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
			if (dataType == VALUE || dataType == DOUBLE_POINTER)
			{
				return "&";
			}
			else if (dataType == REFERENCE)
			{
				return "*";
			}
		}
		else if (required == DOUBLE_POINTER)
		{
			if (dataType == POINTER)
			{
				return "&";
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
		return generateCType(builder);
	}
	
	/**
	 * Generate the C null representation for the given value type.
	 * 
	 * @return The generated null output.
	 */
	public final StringBuilder generateCNullOutput()
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
	
	public final StringBuilder generateCTypeClassName()
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
		
		if (isExternalType() || SyntaxUtils.isExternalPrimitiveType(type))
		{
			builder.append(type);
		}
		else
		{
			FileDeclaration file = getReferenceFile();//getFileDeclaration();
			
			/*if (this instanceof Identifier && !isGenericType())
			{
				file = ((Identifier)this).getDeclaringClass().getFileDeclaration();
			}*/
			
			ClassDeclaration clazz = SyntaxUtils.getImportedClass(file, type);
			
			if (clazz != null)
			{
				clazz.generateCSourceName(builder);
			}
			else
			{
				builder.append(type);
			}
		}
		
		return builder;
	}
	
	/**
	 * Generate the C syntax for the type of the specified Value.
	 * 
	 * @return The C syntax for the type of the Value.
	 */
	public final StringBuilder generateCType()
	{
		return generateCType(new StringBuilder());
	}
	
	/**
	 * Generate the C syntax for the type of the specified Value.
	 * 
	 * @param builder The StringBuider to append the data to.
	 * @return The C syntax for the type of the Value.
	 */
	public final StringBuilder generateCType(StringBuilder builder)
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
		generateCTypeName(builder);
		
		if (isReference())
		{
			builder.append('&');
		}
		else if (isPointer())
		{
			builder.append('*');
		}
		else if (getDataType() == Value.DOUBLE_POINTER)
		{
			builder.append("**");
		}
		if (checkArray && isArray())
		{
			builder.append(generateArrayText());
		}
		
		return builder;
	}
	
	public StringBuilder generateCTypeName()
	{
		return generateCTypeName(new StringBuilder());
	}
	
	public StringBuilder generateCTypeName(StringBuilder builder)
	{
		String type = getType();
		
		if (isGenericType())
		{
			type = getGenericReturnType();
		}
		
		if (type == null)
		{
			builder.append("void");
		}
		else if (type.equals("long"))
		{
			builder.append("long_long");
		}
		else if (type.equals("bool"))
		{
			builder.append("char");
		}
		else if (type.equals("byte"))
		{
			builder.append("char");
		}
		else if (SyntaxUtils.isPrimitiveType(type) && (getDataType() == VALUE || (isReturnParameter() && getDataType() == POINTER)))
		{
			builder.append(SyntaxUtils.getPrimitiveExternalType(type));
		}
		else
		{
			generateCTypeClassName(builder);
		}
		
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
		builder.append(getNovaType());
		
		if (checkArray && isArray())
		{
			builder.append(generateNovaArrayText());
		}
		if (isExternalType() && isPointer())
		{
			builder.append('*');
		}
		
		builder.append(generateGenericType());
		
		return builder;
	}
	
	public String getNovaType()
	{
		return getType();
	}
	
	public ClassDeclaration getNovaTypeClass()
	{
		return getProgram().getClassDeclaration(SyntaxUtils.getTypeClassLocation(this, getNovaType()));
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
	
	public boolean isConstant()
	{
		return true;
	}
	
	public boolean isConsistent()
	{
		return true;
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
	
	public String getInstanceType()
	{
		return getInstanceType(true);
	}
	
	public String getInstanceType(boolean checkGeneric)
	{
		if (checkGeneric && isGenericType())
		{
			return getGenericReturnType();
		}
		
		return getType();
	}
	
	public GenericTypeParameter getGenericTypeParameter()
	{
		if (getParentClass() == null)
		{
			return null;
		}
		
		return getParentClass().getGenericTypeParameter(getType());
	}
	
	public final boolean isGenericType()
	{
		return getGenericTypeParameter() != null;
	}
	
	public boolean isPrimitiveGenericType()
	{
		return isGenericType() && SyntaxUtils.isPrimitiveType(getGenericReturnType());
	}
	
	public String getGenericReturnType()
	{
		return getParentClass().getGenericTypeParameter(getType()).getDefaultType();
		//throw new UnimplementedOperationException("The getGenericReturnType() method must be implemented by class " + this.getClass().getName());
	}
	
	public GenericTypeParameterDeclaration getGenericTypeParameterDeclaration()
	{
		return getParentClass().getGenericTypeParameterDeclaration();
//		throw new UnimplementedOperationException("The getGenericDeclaration() method must be implemented by class " + this.getClass().getName());
	}
	
	public void setType(Value value)
	{
		setArrayDimensions(value.getArrayDimensions());
		setTypeValue(value.getType());
		setDataType(value.getDataType());
		
		GenericTypeArgumentList args = value.getGenericTypeArgumentList();
		GenericTypeArgumentList thisArgs = getGenericTypeArgumentList();
		
		for (int i = 0; i < args.getNumVisibleChildren(); i++)
		{
			GenericTypeArgument arg = args.getVisibleChild(i);
			
			thisArgs.addChild(arg.clone(thisArgs, arg.getLocationIn().asNew()));
		}
	}
	
	/**
	 * Fill the given {@link Value} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Value cloneTo(Value node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);

		node.setArrayDimensions(getArrayDimensions());
		node.setType(getType(), true, false, false);
		node.setDataType(getDataType());
		
		return node;
	}
	
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
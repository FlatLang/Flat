package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.NovaParameterList.ReturnParameterList;
import net.fathomsoft.nova.tree.annotations.NativeAnnotation;
import net.fathomsoft.nova.tree.annotations.RequireGenericTypeAnnotation;
import net.fathomsoft.nova.tree.generics.GenericTypeArgument;
import net.fathomsoft.nova.tree.generics.GenericTypeArgumentList;
import net.fathomsoft.nova.tree.generics.GenericTypeParameter;
import net.fathomsoft.nova.tree.generics.GenericTypeParameterList;
import net.fathomsoft.nova.tree.lambda.LambdaMethodDeclaration;
import net.fathomsoft.nova.tree.variables.*;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

import java.util.AbstractMap;

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
	
	public ArrayAccess arrayAccess;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Value(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public void addChild(Node node)
	{
		if (node instanceof ArrayAccess)
		{
			arrayAccess = (ArrayAccess)node;
		}
		else
		{
			super.addChild(node);
		}
	}
	
	public GenericCompatible getContext()
	{
		if (getParent() instanceof Assignment && !getParent().isDecoding())
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
	public ObjectReference getObjectReferenceNode(CallableMethod methodDeclaration)
	{
		return methodDeclaration.getObjectReference();
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
			if (require)
			{
				SyntaxUtils.isValidType(this, type);
			}
			
			return SyntaxUtils.invalidType(this, type, require);
		}
		
		return true;
	}
	
	public void convertArrays()
	{
		if (!isWithinExternalContext() && getArrayDimensions() > 0 && !containsAnnotationOfType(NativeAnnotation.class))
		{
			String type = "";
			
			for (int i = 1; i < getArrayDimensions(); i++)
			{
				type += "Array<";
			}
			
//			if ("nova/primitive/number/Int".equals(getTypeClassLocation()))
//			{
//				type += "IntArray";
//			}
//			else if ("nova/primitive/number/Char".equals(getTypeClassLocation()))
//			{
//				type += "CharArray";
//			}
//			else if ("nova/primitive/number/Double".equals(getTypeClassLocation()))
//			{
//				type += "DoubleArray";
//			}
//			else
			{
				type += "Array<" + getNovaType(this, false) + ">";
			}
			
			for (int i = 1; i < getArrayDimensions(); i++)
			{
				type += ">";
			}
			
			GenericTypeArgument[] args = null;
			
			if (getNumGenericTypeArguments() > 0)
			{
				args = new GenericTypeArgument[getNumGenericTypeArguments()];
				
				for (int i = 0; i < getNumGenericTypeArguments(); i++)
				{
					args[i] = getGenericTypeArgument(i);
				}
				
				for (GenericTypeArgument arg : args)
				{
					arg.detach();
				}
			}
			
			setType(type);
			
			if (getProgram().getPhase() >= SyntaxTree.PHASE_METHOD_CONTENTS)
			{
				convertToPrimitiveType();
			}
			
			/*if (args != null)
			{
				GenericTypeArgumentList current = getGenericTypeArgumentList();
				
				for (int i = 0; i < getArrayDimensions(); i++)
				{
					current = current.getVisibleChild(0).getGenericTypeArgumentList();
				}
				
				for (int i = 0; i < args.length; i++)
				{
					current.addChild(args[i]);
				}
			}*/
			
			setArrayDimensions(0);
		}
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
		else if (node.getParentMethod() instanceof ExtensionMethodDeclaration)
		{
			return node.getParentMethod().getParameterList().getReferenceParameter().getTypeClass() == this;
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
	
	public boolean isExternal()
	{
		return false;
	}
	
	public String getDefaultLiteralValue()
	{
		if (isPrimitiveType()) // needed so native arrays are included
		{
			switch (getType())
			{
				case "Bool":
					return "false";
				default:
					return "0";
			}
		}
		
		return "null";
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
		return SyntaxUtils.isPrimitiveType(getType()) || (isWithinExternalContext() && SyntaxUtils.isExternalPrimitiveType(getType()));// || getType() != null && getType().equals("Number");
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
		return getDataType() == VALUE && (isPrimitiveType() || isWithinExternalContext() && SyntaxUtils.isExternalPrimitiveType(getType())) && !isPrimitiveArray() && getArrayDimensions() == 0;
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
	public boolean isPrimitiveArray()
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
		return generateGenericType(null);
	}
	
	public String generateGenericType(Value context)
	{
		GenericTypeArgumentList args = getGenericTypeArgumentList();
		
		if (args != null)
		{
			return args.generateNovaInput(new StringBuilder(), true, context).toString();
		}
		
		return "";
	}
	
	public String getArrayType()
	{
		return getArrayType(0);
	}
	
	public String getArrayType(int arrayOffset)
	{
		String type = "";
		
		for (int i = 1 + arrayOffset; i < getArrayDimensions(); i++)
		{
			type += "Array<";
		}
		
		switch (getType())
		{
//			case "Char": type += "CharArray"; break;
//			case "Int": type += "IntArray"; break;
//			case "Double": type += "DoubleArray"; break;
			default: type += "Array<" + generateNovaInput(new StringBuilder(), false, false) + ">";
		}
		
		for (int i = 1 + arrayOffset; i < getArrayDimensions(); i++)
		{
			type += ">";
		}
		
		return type;
	}
	
	public boolean isPrimitiveGenericTypeWrapper()
	{
//		ClassDeclaration c = getTypeClass();
//		
//		if (c != null)
//		{
//			return c.isOfType("nova/datastruct/list/CharArray") || c.isOfType("nova/datastruct/list/IntArray") || c.isOfType("nova/datastruct/list/DoubleArray") ||
//				c.isOfType("nova/datastruct/list/CharArrayIterator") || c.isOfType("nova/datastruct/list/IntArrayIterator") || c.isOfType("nova/datastruct/list/DoubleArrayIterator");
//		}
		
		return false;
	}
	
	@Override
	public Value getArrayTypeValue()
	{
		if (getArrayDimensions() > 0)
		{
			return this;
		}
		
		if ("nova/datastruct/list/Array".equals(getTypeClassLocation()))
		{
			return getGenericTypeArgument(0);
		}
		else if (isPrimitiveGenericTypeWrapper())
		{
			return getTypeClass().getExtendedClass().getGenericTypeArgument(0);
		}
		
		return null;
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
		return getType() != null && getParentClass() != null && getParentClass().containsExternalType(getType());
	}
	
	public Value getTypeValue()
	{
		Value value = new IValue(this, getLocationIn());
		
		value.setArrayDimensions(getArrayDimensions());
		value.setTypeValue(getType());
		value.setDataType(getDataType());
		
		GenericTypeArgumentList args = getGenericTypeArgumentList();
		GenericTypeArgumentList thisArgs = value.getGenericTypeArgumentList();
		
		if (thisArgs != null)
		{
			for (int i = 0; i < args.getNumVisibleChildren(); i++)
			{
				GenericTypeArgument arg = args.getVisibleChild(i);
				
				thisArgs.addChild(arg.clone(thisArgs, arg.getLocationIn().asNew()));
			}
		}
		
		return value;
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
	
	public void checkDataType(String type)
	{
		if (type != null && !isExternalType())
		{
			boolean primitive = SyntaxUtils.isPrimitiveType(type) || SyntaxUtils.isExternalPrimitiveType(type);
			
			if (primitive)
			{
				setPrimitive();
			}
			else if (getDataType() != Value.DOUBLE_POINTER)//!n.isWithinExternalContext())// || !SyntaxUtils.isExternalPrimitiveType(type))
			{
				setDataType(Value.POINTER);
			}
		}
	}
	
	public void setPrimitive()
	{
		if (getArrayDimensions() == 0)
		{
			setDataType(Value.VALUE);
		}
	}
	
	public AbstractMap.SimpleEntry<Value, Boolean> getCastedType()
	{
		return null;
	}
	
	/**
	 * Get the ClassDeclaration that represents the type of the specified
	 * Value. If the type is primitive, this will return the
	 * wrapper class of the primitive type.
	 * 
	 * @return The ClassDeclaration instance of the type.
	 */
	public final ClassDeclaration getTypeClass()
	{
		return getTypeClass(true);
	}
	
	public final ClassDeclaration getTypeClass(boolean checkCast)
	{
		return getTypeClass(checkCast, false);
	}
	
	public ClassDeclaration getTypeClass(boolean checkCast, boolean defaultGenericType)
	{
		AbstractMap.SimpleEntry<Value, Boolean> casted = getCastedType();
		
		if (casted != null)
		{
			if (!casted.getValue())
			{
				return casted.getKey().getTypeClass();
			}
		}
		
		RequireGenericTypeAnnotation required = (RequireGenericTypeAnnotation)getAnnotationOfType(RequireGenericTypeAnnotation.class, true, true);
		
		if ((required != null || defaultGenericType) && isGenericType(false, checkCast))
		{
			String type = null;
			
			if (required != null)
			{
				type = required.getRequiredType(getType());
			}
			if (defaultGenericType && type == null)
			{
				type = getGenericReturnType(checkCast);
				
				if ("Type".equals(type))
				{
					getGenericReturnType(checkCast);
				}
			}
			
			if (type != null)
			{
				return getFileDeclaration().getImport(type, false).getClassDeclaration();
			}
		}
		
		return getProgram().getClassDeclaration(getTypeClassLocation(checkCast));
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
		return getTypeClassLocation(true);
	}
	
	public String getTypeClassLocation(boolean checkCast)
	{
		if (checkCast && this instanceof Accessible)
		{
			Cast c = ((Accessible)this).getExplicitCast();
			
			if (c != null)
			{
				return SyntaxUtils.getTypeClassLocation(c, c.getType());
			}
		}
		else if (this instanceof Accessible && ((Accessible)this).getParentPriority() != null)
		{
			Priority p = ((Accessible)this).getParentPriority();
			
			if (p.getContents().containsChild(this, true) && p.getContents() instanceof Cast)
			{
				return p.getContents().getTypeClassLocation();
			}
		}
		
		String type = null;
		
		if (isGenericType(false, checkCast) && getGenericReturnType(checkCast) != null)//getParentClass(true).containsGenericTypeParameter(getType()))
		{
			type = getGenericReturnType(checkCast);//getParentClass().getGenericTypeParameter(getType()).getDefaultType();
		}
		else
		{
			type = SyntaxUtils.getPrimitiveNovaType(getType(checkCast));
		}
		
		return SyntaxUtils.getTypeClassLocation(this, type);
	}
	
	@Override
	public FileDeclaration getReferenceFile()
	{
		if (this instanceof Accessible)
		{
			Accessible id = (Accessible)this;
			
			if (id.getCast() != null)
			{
				return getFileDeclaration();
			}
			if (id.isAccessed())
			{
				if (isGenericType())
				{
					id = id.getRootAccessNode();
					
					Value reference = (Value)id.getReferenceNode();
					
					ClassDeclaration type = reference.getTypeClass();
					
					return type.getFileDeclaration();
				}
				
				if (this instanceof MethodCall)
				{
					return ((MethodCall)this).getDeclaration().getFileDeclaration();
				}
				//Nova.debuggingBreakpoint(id instanceof Variable && ((Variable)id).getName().equals("hypotheses"));
				ClassDeclaration type = ((Accessible)this).getDeclaringClass();
				
				if (type == null)
				{
					//SyntaxUtils.invalidType(reference, reference.getType(), true);
				}
				else
				{
					return type.getFileDeclaration();
				}
			}
		}
		if (this instanceof Variable)
		{
			Variable var = (Variable)this;
			
			if (var.getDeclaration() instanceof LocalDeclaration)
			{
				LocalDeclaration decl = (LocalDeclaration)var.getDeclaration();
				
				return decl.getReferenceFile();
			}
			if (var.getDeclaration() instanceof VirtualLocalDeclaration)
			{
				return ((VirtualLocalDeclaration)var.getDeclaration()).getReference().getReturnedNode().getTypeClass().getFileDeclaration();
			}
		}
		else if (this instanceof LocalDeclaration)
		{
			if (this instanceof Parameter && getParentMethod() instanceof LambdaMethodDeclaration)
			{
				LambdaMethodDeclaration lambda = (LambdaMethodDeclaration)getParentMethod();
				
				if (lambda.getParameterList().getVisibleIndex(this) >= 0 && lambda.methodCall != null)
				{
					Value corresponding = lambda.getCorrespondingClosureDeclaration().getParameterList().getParameter(lambda.getParameterList().getVisibleIndex(this));
					
					if (corresponding.isGenericType())
					{
						return getFileDeclaration();
					}
					
					return lambda.getContextDeclaringClass().getFileDeclaration();
				}
			}
			if (this instanceof VirtualLocalDeclaration)
			{
				return ((VirtualLocalDeclaration)this).getReference().getReturnedNode().getTypeClass().getFileDeclaration();
			}
			else
			{
				LocalDeclaration decl = (LocalDeclaration)this;
				
				if (decl.isImplicit() && decl.getImplicitType() != null && !decl.getImplicitType().isDecoding() && !decl.getImplicitType().isGenericType())
				{
					return decl.getImplicitType().getReferenceFile();
				}
			}
		}
		
		if (this instanceof Accessible && this instanceof VariableDeclaration == false &&
			this instanceof Instantiation == false && this instanceof StaticClassReference == false && 
			getParentMethod() instanceof ExtensionMethodDeclaration)
		{
			Accessible ref = ((Accessible)this).getReferenceNode();
			
			if (ref != this && ref instanceof ObjectReference)
			{
				ClassDeclaration clazz = ref.toValue().getTypeClass();
				
				if (clazz != null)
				{
					return clazz.getFileDeclaration();
				}
			}
		}
		
		return getFileDeclaration();
	}
	
	public boolean isValueReference()
	{
		return false;
	}
	
	/**
	 * Get whether or not the identifier is a reference.
	 * 
	 * @return Whether or not the identifier is a reference.
	 */
	public boolean isReference()
	{
		return isReference(true);
	}
	
	public boolean isReference(boolean checkGeneric)
	{
		return getDataType(checkGeneric) == REFERENCE;
	}
	
	/**
	 * Get whether or not the identifier is a pointer.
	 * 
	 * @return Whether or not the identifier is a pointer.
	 */
	public boolean isPointer()
	{
		return isPointer(true);
	}
	
	public boolean isPointer(boolean checkGeneric)
	{
		return getDataType(checkGeneric) == POINTER;
	}
	
	public boolean isDoublePointer()
	{
		return isDoublePointer(true);
	}
	
	public boolean isDoublePointer(boolean checkGeneric)
	{
		return getDataType(checkGeneric) == Value.DOUBLE_POINTER;
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
			if (!call.isExternal() || !param.isPointer() || type != VALUE || !isPrimitiveArray())
			{
				type = param.getDataType();
			}
		}
		else if (parent instanceof Assignment)
		{
			Assignment assignment = (Assignment)parent;
			Value      assignee   = assignment.getAssignedNodeValue();
			
			if (this instanceof Variable == false || (assignee instanceof Variable && !((Variable)this).isSameVariable((Variable)assignee)))
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
	 * Generate the Nova syntax for the type of the specified Value's type.
	 * 
	 * @return The Nova syntax for the type of the Value.
	 */
	public StringBuilder generateNovaType()
	{
		return generateNovaType((Value)null);
	}
	
	public StringBuilder generateNovaType(Value context)
	{
		return generateNovaType(new StringBuilder(), context);
	}
	
	/**
	 * Generate the Nova syntax for the type of the specified Value'stype.
	 * 
	 * @param builder The StringBuider to append the data to.
	 * @return The Nova syntax for the type of the Value.
	 */
	public StringBuilder generateNovaType(StringBuilder builder)
	{
		return generateNovaType(builder, null);
	}
	
	public StringBuilder generateNovaType(StringBuilder builder, Value context)
	{
		return generateNovaType(builder, context, true);
	}
	
	/**
	 * Generate the Nova syntax for the type of the specified Value's type.
	 * 
	 * @param builder The StringBuider to append the data to.
	 * @param checkArray Whether or not to check if the type is an array.
	 * @return The Nova syntax for the type of the Value.
	 */
	public StringBuilder generateNovaType(StringBuilder builder, Value context, boolean checkArray)
	{
		return generateNovaType(builder, context, checkArray, false);
	}
	
	public StringBuilder generateNovaType(StringBuilder builder, Value context, boolean checkArray, boolean defaultGeneric)
	{
		Value type = getNovaTypeValue(context);
		
		GenericTypeArgument arg = null;
		GenericTypeParameter param = null;
		
		if (isGenericType())
		{
			param = getGenericTypeParameter();
			
			if (param != null)
			{
				arg = param.getCorrespondingArgument(context);
			}
		}
		
		if (arg != null && !arg.isGenericType())
		{
			builder.append(SyntaxUtils.getPrimitiveNovaType(arg.generateNovaType().toString()));
		}
		else if (defaultGeneric && param != null)
		{
			builder.append(param.getDefaultType());
		}
		else
		{
			builder.append(SyntaxUtils.getPrimitiveNovaType(type.getType()));
		}
		
		builder.append(type.generateGenericType(context));
		
		if (checkArray && isPrimitiveArray())
		{
			builder.append(generateNovaArrayText());
		}
		if (isExternalType() && isPointer())
		{
			builder.append('*');
		}
		
		return builder;
	}
	
	public BinaryOperation replaceWithNullCheck()
	{
		BinaryOperation operation = BinaryOperation.generateDefault(parent, getLocationIn());
		operation.getOperator().setOperator(Operator.NOT_EQUAL);
		operation.getRightOperand().replaceWith(Literal.decodeStatement(parent, Literal.NULL_IDENTIFIER, getLocationIn(), true, true));
		
		replaceWith(operation);
		
		Priority p = new Priority(getParent(), getLocationIn());
		p.addChild(this);
		
		operation.getLeftOperand().replaceWith(p);
		
		return operation;
	}
	
	public String getNovaType()
	{
		return getNovaType(null);
	}
	
	public String getNovaType(Value context)
	{
		return getNovaType(context, true);
	}
	
	public String getNovaType(Value context, boolean checkArray)
	{
		return getNovaType(context, checkArray, false);
	}
	
	public String getNovaType(Value context, boolean checkArray, boolean defaultGeneric)
	{
		Value value = getNovaTypeValue(context);
		
		return value.generateNovaType(new StringBuilder(), context, checkArray, defaultGeneric).toString();
	}
	
	public Value getNovaTypeValue(Value context)
	{
		return this;
	}
	
	public Value getClonedNovaTypeValue(Value context)
	{
		Value clone = (Value)clone(getParent(), getLocationIn(), false, true);
		
		if (getGenericTypeArgumentList() != null)
		{
			for (GenericTypeArgument arg : getGenericTypeArgumentList())
			{
				clone.getGenericTypeArgumentList().addChild(arg.getNovaTypeValue(context));
			}
		}
		
		return clone;
	}
	
	public ClassDeclaration getNovaTypeClass()
	{
		return getProgram().getClassDeclaration(SyntaxUtils.getTypeClassLocation(this, SyntaxUtils.stripGenerics(getNovaType())));
	}
	
	public ClassDeclaration getNovaTypeClass(Value context)
	{
		return getNovaTypeClass(context, true);
	}
		
	public ClassDeclaration getNovaTypeClass(Value context, boolean checkArray)
	{
		return getNovaTypeClass(context, checkArray, true);
	}
		
	public ClassDeclaration getNovaTypeClass(Value context, boolean checkArray, boolean checkGeneric)
	{
		return getProgram().getClassDeclaration(SyntaxUtils.getTypeClassLocation(this, SyntaxUtils.stripGenerics(getNovaType(context, checkArray, checkGeneric))));
	}
	
	public boolean isOriginallyGenericType()
	{
		return getOriginalGenericType() != null;
	}
	
	public Value getOriginalGenericType()
	{
		if (isGenericType())
		{
			return this;
		}
		
		return null;
	}
	
	public void convertToPrimitiveType()
	{
		ClassDeclaration c = getTypeClass();
		
		if (c != null)
		{
			ClassDeclaration converted = c.getConvertedPrimitiveClass(getGenericTypeArgumentList().getTypes());
			
			if (converted != null)
			{
				getFileDeclaration().addImport(converted.getClassLocation());
				
				setType(converted);
			}
		}
	}
	
	public StringBuilder generateNovaArrayAccess()
	{
		return generateNovaArrayAccess(new StringBuilder());
	}
	
	public StringBuilder generateNovaArrayAccess(StringBuilder builder)
	{
		if (arrayAccess != null)
		{
			return arrayAccess.generateNovaInput(builder);
		}
		
		return builder;
	}
	
	public int getArrayAccessDimensions()
	{
		return arrayAccess != null ? arrayAccess.getNumDimensions() : 0;
	}
	
	/*/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 *
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		return getTarget().generateUseOutput(builder);
	}*/
	
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
		return getParent() instanceof Super;
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
		
		Value original = getOriginalGenericType();
		
		if (original != null)
		{
			return original.getType();
		}
		
		return getNovaType();
	}
	
	public GenericTypeParameter getGenericTypeParameter()
	{
		return getGenericTypeParameter(true);
	}
	
	public GenericTypeParameter getGenericTypeParameter(boolean checkArray)
	{
		if (getParentMethod(true) != null)
		{
			GenericTypeParameter param = getParentMethod(true).getGenericTypeParameter(getType(checkArray));
			
			if (param != null)
			{
				return param;
			}
		}
		if (getAncestorOfType(MethodCall.class) != null)
		{
			MethodCall call = (MethodCall)getAncestorOfType(MethodCall.class);
			
			ClassDeclaration clazz = call.getReferenceNode().toValue().getTypeClass();
			
			if (clazz != null)
			{
				GenericTypeParameter param = clazz.getGenericTypeParameter(getType(checkArray), this);
				
				if (param != null)
				{
					return param;
				}
			}
		}
		if (getParentClass() == null)
		{
			return null;
		}
		
		return getParentClass().getGenericTypeParameter(getType(checkArray), this);
	}
    
	public final boolean isGenericType()
	{
		return isGenericType(false);
	}
	
	public final boolean isGenericType(boolean checkArray)
	{
		return isGenericType(checkArray, true);
	}
	
	public boolean isGenericType(boolean checkArray, boolean checkCast)
	{
		if (getGenericTypeParameter(checkCast) != null)
		{
			return true;
		}
		if (checkArray)
		{
			if (getTypeClass(checkCast) == getProgram().getClassDeclaration("nova/datastruct/list/Array"))
			{
				GenericTypeArgumentList list = getGenericTypeArgumentList();
				
				// TODO: do a deep search
				
				if (list != null)
				{
					return list.getNumVisibleChildren() > 0 && list.getVisibleChild(0).isGenericType();
				}
			}
		}
		
		return false;
	}
	
	public boolean isPrimitiveGenericType()
	{
		return isGenericType() && SyntaxUtils.isPrimitiveType(getGenericReturnType());
	}
	
	public String getGenericReturnType()
	{
		return getGenericReturnType(true);
	}
	
	public String getGenericReturnType(boolean checkCast)
	{
		GenericTypeParameter param = getGenericTypeParameter(checkCast);//getParentClass().getGenericTypeParameter(getType(checkCast), this);
		
		if (param != null)
		{
			return param.getDefaultType();
		}
		
		return null;
		//throw new UnimplementedOperationException("The getGenericReturnType() method must be implemented by class " + this.getClass().getName());
	}
	
	public GenericTypeParameterList getGenericTypeParameterDeclaration()
	{
		return getParentClass().getGenericTypeParameterDeclaration();
//		throw new UnimplementedOperationException("The getGenericDeclaration() method must be implemented by class " + this.getClass().getName());
	}
	
	public final void setType(Value value)
	{
		setType(value, true);
	}
	
	public final void setType(Value value, Value context)
	{
		setType(value, context, true);
	}
	
	public final void setType(Value value, boolean extractType)
	{
		setType(value, value, extractType);
	}
	
	public void setType(Value value, Value context, boolean extractType)
	{
		setArrayDimensions(value.getArrayDimensions());

		Value original = value;
		Value novaType = value.getNovaTypeValue(context);
		
		if (novaType.isGenericType() && !getParentClass().isOfType(original.getGenericTypeParameter().getParentClass()))
		{
			setTypeValue(original.getGenericTypeParameter().getDefaultType());
		}
		else if (value.getType() != null)
		{
			String type = SyntaxUtils.getPrimitiveNovaType((extractType ? novaType : original).getType());
			
			if (extractType && !novaType.isExternalType() && !novaType.isGenericType() && getFileDeclaration().getClassDeclaration(type) == null && !getFileDeclaration().containsImport(type, false))
			{
				if ((novaType instanceof LocalDeclaration == false || !((LocalDeclaration)novaType).isImplicit()) &&
					(this instanceof LocalDeclaration == false || !((LocalDeclaration)this).isImplicit()))
				{
					getFileDeclaration().addImport(novaType.getTypeClassLocation());
				}
			}
			
			setType(type);
			
			if (extractType && value instanceof ClassDeclaration == false)
			{
				type = value.getNovaTypeValue(context).generateGenericType(context);
				
				if (getGenericTypeArgumentList() != null)
				{
					getGenericTypeArgumentList().slaughterEveryLastVisibleChild();
					
					if (type.length() > 0)
					{
						decodeGenericTypeArguments(type, new Bounds(0, type.length()), true);
					}
				}
			}
		}
		
		setDataType(novaType.getDataType());
	}
	
	public Value replaceWithAutoboxedValue()
	{
		Instantiation newValue = SyntaxUtils.autoboxPrimitive(this);
		
		replaceWith(newValue);
		
		return newValue;
	}
	
	public Value replaceWithAutoboxedValue(String type)
	{
		Instantiation newValue = SyntaxUtils.autoboxPrimitive(this, type);
		
		replaceWith(newValue);
		
		return newValue;
	}
	
	public Value replaceWithUnboxedValue()
	{
		return SyntaxUtils.unboxPrimitive(this);
	}
	
	public Value replaceWithUnboxedValue(String type)
	{
		Value newValue = SyntaxUtils.unboxPrimitive(this, type);
		
//		if (!newValue.containsChild(newValue))
//		{
//			replaceWith(newValue);
//		}
		
		return newValue;
	}
	
	public Value replaceWithBoxedValue(Value required, String type)
	{
		if (getReturnedNode().isPrimitive() && !required.isPrimitiveType())
		{
			return replaceWithAutoboxedValue(type);
		}
		else if (required.getType() != null && !getType().equals("void") && !getReturnedNode().isPrimitive() && required.isPrimitive())
		{
			return replaceWithUnboxedValue(type);
		}
		
		return null;
	}
	
	public boolean isImmutable()
	{
		return getTypeClass().isImmutable();
	}
	
	public Value getRealValue()
	{
		return this;
	}
	
	/**
	 * Fill the given {@link Value} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Value cloneTo(Value node, boolean cloneChildren, boolean cloneAnnotations)
	{
		return cloneTo(node, cloneChildren, cloneAnnotations, false);
	}
	
	public Value cloneTo(Value node, boolean cloneChildren, boolean cloneAnnotations, boolean copyFacadeValues)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		if (copyFacadeValues)
		{
			node.setArrayDimensions(getArrayDimensions());
			node.setType(getTypeStringValue(), true, false, false);
			node.setDataType(getDataType(false));
		}
		
		return node;
	}
	
	public Literal generateDefaultValue(Node parent, Location location)
	{
		if (!isPrimitive())
		{
			return (Literal)Literal.decodeStatement(parent, Literal.NULL_IDENTIFIER, location, true, true);
		}
		else if (getTypeClass().isOfType(getProgram().getClassDeclaration("nova/primitive/number/Char")))
		{
			return (Literal)Literal.decodeStatement(parent, "'\\0'", location, true, true);
		}
		else if (getTypeClass().isOfType(getProgram().getClassDeclaration("nova/primitive/number/Number")))
		{
			return (Literal)Literal.decodeStatement(parent, "0", location, true, true);
		}
		else if (getTypeClass().isOfType(getProgram().getClassDeclaration("nova/primitive/Bool")))
		{
			return (Literal)Literal.decodeStatement(parent, "false", location, true, true);
		}
		
		throw new UnsupportedOperationException("Value of type '" + getType() + "' does not have a default value.");
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
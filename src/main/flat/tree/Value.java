package flat.tree;

import flat.Flat;
import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.FlatParameterList.ReturnParameterList;
import flat.tree.annotations.NativeAnnotation;
import flat.tree.annotations.NativeArrayAnnotation;
import flat.tree.annotations.RequireGenericTypeAnnotation;
import flat.tree.generics.GenericTypeArgument;
import flat.tree.generics.GenericTypeArgumentList;
import flat.tree.generics.GenericTypeParameter;
import flat.tree.generics.GenericTypeParameterList;
import flat.tree.lambda.LambdaMethodDeclaration;
import flat.tree.variables.*;
import flat.util.Bounds;
import flat.util.Location;
import flat.util.SyntaxUtils;

import java.util.AbstractMap;

/**
 * Node extension that represents something that returns a value.
 * For the rules on what can and cannot be an value node, refer to
 * {@link #setType(java.lang.String)}
 *
 * @author Braden Steffaniak
 * @since v0.2.4 May 2, 2014 at 11:14:37 PM
 * @version v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public abstract class Value extends Node implements AbstractValue {
    public static final byte VALUE = 1, POINTER = 2, REFERENCE = 3, DOUBLE_POINTER = 4;

    public ArrayAccess arrayAccess;

    public GenericTypeParameter genericParameter;
    public boolean explicitlyNullable;

    /**
     * @see Node#Node(Node, Location)
     */
    public Value(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    @Override
    public void addChild(Node node) {
        if (node instanceof ArrayAccess) {
            arrayAccess = (ArrayAccess) node;
        } else {
            super.addChild(node);
        }
    }

    public GenericCompatible getContext() {
        if (getParent() instanceof Assignment && !getParent().isDecoding()) {
            return ((Assignment) getParent()).getAssignee().getContext();
        } else if (getParent() instanceof Return) {
            return getParentMethod();
        } else if (getParent() instanceof MethodCallArgumentList) {
            MethodCallArgumentList args = (MethodCallArgumentList) getParent();

            VariableDeclaration d = args.getMethodCall().getMethodDeclaration();

            if (d instanceof MutatorMethod) {
                MutatorMethod m = (MutatorMethod) d;

                return m.getDeclaration();
            }
        }

        return this;
    }

    public GenericTypeArgumentList getGenericTypeArgumentList() {
        if (getNumChildren() > super.getNumDefaultChildren() + 0 && getChild(super.getNumDefaultChildren() + 0) instanceof GenericTypeArgumentList) {
            return (GenericTypeArgumentList) getChild(super.getNumDefaultChildren() + 0);
        }

        return null;
    }

    /**
     * Generate a IValue instance from the given type.
     *
     * @param temporaryParent The temporary parent of the new Node.
     * @param locationIn      The location of the new Node.
     * @param type            The type to set for the Value.
     * @param require         Whether or not a successful decode is required.
     * @return The generated IValue instance.
     */
    public static IValue generateFromType(Node temporaryParent, Location locationIn, String type, boolean require) {
        IValue value = new IValue(temporaryParent, locationIn);

        if (!value.setType(type, require, true)) {
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
    public Value getReturnedNode() {
        return this;
    }

    /**
     * Get the name of the object reference identifier for the given
     * Method node. Static methods return "ClassName" and
     * non-static methods return "this". The given method cannot be
     * external.
     *
     * @param methodDeclaration The method to get the object reference identifier
     *                          name from.
     * @return The name of the object reference identifier.
     */
    public String getObjectReferenceIdentifier(CallableMethod methodDeclaration) {
        if (methodDeclaration.isInstance()) {
            return ParameterList.OBJECT_REFERENCE_IDENTIFIER;
        }

        ClassDeclaration clazz = ((Node) methodDeclaration).getParentClass();

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
    public ObjectReference getObjectReferenceNode(CallableMethod methodDeclaration) {
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
     * @param type    The type to validate.
     * @param require Whether or not throw an error if anything goes
     *                wrong.
     * @return Whether or not the given value is valid.
     */
    public boolean checkType(String type, boolean require) {
        if (!SyntaxUtils.isValidType(this, type)) {
            if (require) {
                SyntaxUtils.isValidType(this, type);
            }

            return SyntaxUtils.invalidType(this, type, require);
        }

        return true;
    }

    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        if (phase == SyntaxTree.PHASE_PRE_GENERATION) {
            if (getTypeObject() instanceof FunctionType) {
                ((FunctionType) getTypeObject()).closure.validate(phase);
            }
        }

        return result;
    }

    public void convertArrays() {
        if (!isWithinExternalContext() && getArrayDimensions() > 0 && !isNativeArray()) {
            String type = "";

            for (int i = 1; i < getArrayDimensions(); i++) {
                type += "Array<";
            }

//			if ("flat/primitive/number/Int".equals(getTypeClassLocation()))
//			{
//				type += "IntArray";
//			}
//			else if ("flat/primitive/number/Char".equals(getTypeClassLocation()))
//			{
//				type += "CharArray";
//			}
//			else if ("flat/primitive/number/Double".equals(getTypeClassLocation()))
//			{
//				type += "DoubleArray";
//			}
//			else
            {
                type += "Array<" + getFlatType(this, false) + ">";
            }

            for (int i = 1; i < getArrayDimensions(); i++) {
                type += ">";
            }

            GenericTypeArgument[] args = null;

            if (getNumGenericTypeArguments() > 0) {
                args = new GenericTypeArgument[getNumGenericTypeArguments()];

                for (int i = 0; i < getNumGenericTypeArguments(); i++) {
                    args[i] = getGenericTypeArgument(i);
                }

                for (GenericTypeArgument arg : args) {
                    arg.detach();
                }
            }

            setTypeValue("Array");
            setArrayDimensions(0);
            setType(type);

            if (getProgram().getPhase() >= SyntaxTree.PHASE_METHOD_CONTENTS) {
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
     * class.
     */
    public boolean isContainingClass(Value node) {
        ClassDeclaration clazz = node.getParentClass();

        if (this == clazz) {
            return true;
        } else if (node.getParentMethod() instanceof ExtensionMethodDeclaration) {
            return node.getParentMethod().getParameterList().getReferenceParameter().getTypeClass() == this;
        } else if (this instanceof Variable) {
            Variable param = (Variable) this;

            if (param.getName().equals(ParameterList.OBJECT_REFERENCE_IDENTIFIER)) {
                return true;
            }
        }

        return false;
    }

    public boolean isReturnParameter() {
        return getAncestorOfType(ReturnParameterList.class) != null;
    }

    public boolean isExternal() {
        return false;
    }

    public String getDefaultLiteralValue() {
        if (isPrimitive()) // needed so native arrays are included
        {
            switch (getType()) {
                case "Bool":
                    return "false";
                default:
                    return "0";
            }
        }

        return "null";
    }

    public Value replaceWithDefaultLiteralValue(Value type) {
        Value v = Literal.generateDefaultValue(parent, getLocationIn(), type);

        replaceWith(v);

        return v;
    }

    /**
     * Get whether a variable's type is a primitive type or not.<br>
     * <br>
     * For the list of primitive values, see
     * {@link SyntaxUtils#isPrimitiveType(String)}
     *
     * @return Whether a variable's type is a primitive type or not.
     */
    public boolean isPrimitiveType() {
        return SyntaxUtils.isPrimitiveType(getType()) || (isWithinExternalContext() && SyntaxUtils.isExternalPrimitiveType(getType()));// || getType() != null && getType().equals("Number");
    }

    /**
     * Get whether a value is primitive or not. A value is primitive
     * if it has a primitive type AND is NOT an array. Arrays are NOT a
     * primitive type.<br>
     * <br>
     * For the list of primitive values, see
     * {@link SyntaxUtils#isPrimitiveType(String)}.
     *
     * @return Whether a variable is primitive or not.
     */
    public boolean isPrimitive() {
        return getType() != null && getDataType() == VALUE && (isPrimitiveType() || isWithinExternalContext() && SyntaxUtils.isExternalPrimitiveType(getType())) && !isPrimitiveArray() && getArrayDimensions() == 0;
    }

    public void setPrimitiveWrapperType() {
        if (isPrimitiveType()) {
            setDataType(POINTER);
        }
    }

    /**
     * Get whether or not the variable is an array. A variable is an
     * array if it has an array dimension of 1 or greater.
     *
     * @return Whether or not the variable is an array.
     */
    public boolean isPrimitiveArray() {
        return getArrayDimensions() > 0;
    }

    /**
     * The text that represents an array in the C language.
     *
     * @return The text that represents an array in the C language.
     */
    public String generateArrayText() {
        String text = "";

        for (int i = 0; i < getArrayDimensions(); i++) {
            text += "*";
        }

        return text;
    }

    /**
     * The text that represents an array in the Flat language.
     *
     * @return The text that represents an array in the Flat language.
     */
    public String generateFlatArrayText() {
        String text = "";

        for (int i = 0; i < getArrayDimensions(); i++) {
            text += "[]";
        }

        return text;
    }

    public String generateGenericType() {
        return generateGenericType(null);
    }

    public String generateGenericType(Value context) {
        GenericTypeArgumentList args = getGenericTypeArgumentList();

        if (args != null) {
            return args.generateFlatInput(new StringBuilder(), true, context).toString();
        }

        return "";
    }

    public String getArrayType() {
        return getArrayType(0);
    }

    public String getArrayType(int arrayOffset) {
        String type = "";

        for (int i = 1 + arrayOffset; i < getArrayDimensions(); i++) {
            type += "Array<";
        }

        type += "Array<" + generateFlatInput(new StringBuilder(), false, false) + ">";

        for (int i = 1 + arrayOffset; i < getArrayDimensions(); i++) {
            type += ">";
        }

        return type;
    }

    public boolean isPrimitiveGenericTypeWrapper() {
//		ClassDeclaration c = getTypeClass();
//		
//		if (c != null)
//		{
//			return c.isOfType("flat/datastruct/list/CharArray") || c.isOfType("flat/datastruct/list/IntArray") || c.isOfType("flat/datastruct/list/DoubleArray") ||
//				c.isOfType("flat/datastruct/list/CharArrayIterator") || c.isOfType("flat/datastruct/list/IntArrayIterator") || c.isOfType("flat/datastruct/list/DoubleArrayIterator");
//		}

        return false;
    }

    @Override
    public Value getArrayTypeValue() {
        if (getArrayDimensions() > 0) {
            return this;
        }

        if ("flat/datastruct/list/Array".equals(getTypeClassLocation())) {
            return getGenericTypeArgument(0);
        } else if (isPrimitiveGenericTypeWrapper()) {
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
    public boolean isExternalType() {
        return getType() != null && getParentClass() != null && getParentClass().containsExternalType(getType());
    }

    public boolean isNative() {
        return containsAnnotationOfType(NativeAnnotation.class);
    }

    public boolean isNativeArray() {
        return containsAnnotationOfType(NativeArrayAnnotation.class);
    }

    public Value getTypeValue() {
        Value value = new IIdentifier(this, getLocationIn());

        value.setArrayDimensions(getArrayDimensions());
        value.setTypeValue(getType());

        if (getType() != null) {
            value.setDataType(getDataType());
        }

        GenericTypeArgumentList args = getGenericTypeArgumentList();
        GenericTypeArgumentList thisArgs = value.getGenericTypeArgumentList();

        if (thisArgs != null) {
            for (int i = 0; i < args.getNumVisibleChildren(); i++) {
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
    public boolean setType(String type) {
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
     * @param type    The type that this statement returns.
     * @param require Whether or not to throw an error if anything goes
     *                wrong.
     * @return Whether or not the type was set successfully.
     */
    public boolean setType(String type, boolean require) {
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
     * @param type      The type that this statement returns.
     * @param require   Whether or not to throw an error if anything goes
     *                  wrong.
     * @param checkType Whether or not to check if the type is valid.
     * @return Whether or not the type was set successfully.
     */
    public boolean setType(String type, boolean require, boolean checkType) {
        return setType(type, require, checkType, true);
    }

    public void checkDataType(String type) {
        if (type != null) {
            if (isExternalType() && isDecoding()) {
                setDataType(VALUE);
            } else if (!isExternalType()) {
                boolean primitive = SyntaxUtils.isPrimitiveType(type) || SyntaxUtils.isExternalPrimitiveType(type);

                if (primitive) {
                    setPrimitive();
                } else if (getDataType() != Value.DOUBLE_POINTER)//!n.isWithinExternalContext())// || !SyntaxUtils.isExternalPrimitiveType(type))
                {
                    setDataType(Value.POINTER);
                }
            }
        }
    }

    public void setPrimitive() {
//		if (getArrayDimensions() == 0)
        {
            setDataType(Value.VALUE);
        }
    }

    public AbstractMap.SimpleEntry<Value, Boolean> getCastedType() {
        return null;
    }

    /**
     * Get the ClassDeclaration that represents the type of the specified
     * Value. If the type is primitive, this will return the
     * wrapper class of the primitive type.
     *
     * @return The ClassDeclaration instance of the type.
     */
    public final ClassDeclaration getTypeClass() {
        return getTypeClass(true);
    }

    public final ClassDeclaration getTypeClass(boolean checkCast) {
        return getTypeClass(checkCast, false);
    }

    public ClassDeclaration getTypeClass(boolean checkCast, boolean defaultGenericType) {
        AbstractMap.SimpleEntry<Value, Boolean> casted = getCastedType();

        if (casted != null) {
            if (!casted.getValue()) {
                return casted.getKey().getTypeClass();
            }
        }

        RequireGenericTypeAnnotation required = (RequireGenericTypeAnnotation) getAnnotationOfType(RequireGenericTypeAnnotation.class, true, true);

        if ((required != null || defaultGenericType) && isGenericType(false, checkCast)) {
            String type = null;

            if (required != null) {
                type = required.getRequiredType(getType());
            }
            if (defaultGenericType && type == null) {
                type = getGenericReturnType(checkCast);

                if ("Type".equals(type)) {
                    getGenericReturnType(checkCast);
                }
            }

            if (type != null) {
                Import impor = getFileDeclaration().getImport(type, false);

                if (impor != null) {
                    return impor.getClassDeclaration();
                }
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
    public String getTypeClassName() {
        return SyntaxUtils.getClassName(getTypeClassLocation());
    }

    public String getTypeClassLocation() {
        return getTypeClassLocation(true);
    }

    public String getTypeClassLocation(boolean checkCast) {
        if (checkCast && this instanceof Accessible) {
            Cast c = ((Accessible) this).getExplicitCast();

            if (c != null) {
                return SyntaxUtils.getTypeClassLocation(c, c.getType(), true);
            }
        } else if (this instanceof Accessible && ((Accessible) this).getParentPriority() != null) {
            Priority p = ((Accessible) this).getParentPriority();

            if (p.getContents().getReturnedNode() == this && p.getContents() instanceof Cast) {
                return p.getContents().getTypeClassLocation();
            }
        }

        String type = null;

        if (isGenericType(false, checkCast) && getGenericReturnType(checkCast) != null)//getParentClass(true).containsGenericTypeParameter(getType()))
        {
            type = getGenericReturnType(checkCast);//getParentClass().getGenericTypeParameter(getType()).getDefaultType();
        } else {
            type = SyntaxUtils.getPrimitiveFlatType(getType(checkCast));
        }

        return SyntaxUtils.getTypeClassLocation(this, type, checkCast);
    }

    @Override
    public FileDeclaration getReferenceFile(boolean checkCast) {
        if (this instanceof Accessible) {
            Accessible id = (Accessible) this;

            if (checkCast && id.getCast() != null) {
                return getFileDeclaration();
            }
            if (id.isAccessed()) {
                if (getGenericTypeParameter() != null) {
                    GenericTypeArgument arg = getGenericTypeParameter().getCorrespondingArgument(this);

                    if (arg != null) {
                        return arg.getFileDeclaration();
                    }

                    Value ref = id.getReferenceNode().toValue();

                    if (ref instanceof Variable) {
                        Variable var = (Variable) ref;

                        if (var.declaration instanceof LocalDeclaration) {
                            LocalDeclaration decl = (LocalDeclaration) var.declaration;

                            if (decl.implicitType != null) {
                                Value ret = decl.implicitType.getReturnedNode();

                                if (ret instanceof Variable) {
                                    var = (Variable) ret;

                                    return var.declaration.getFileDeclaration();
                                }
                            }
                        }
                    }

                    id = id.getRootAccessNode();

                    Value reference = (Value) id.getReferenceNode();

                    ClassDeclaration type = reference.getTypeClass();

                    return type.getFileDeclaration();
                }

                if (this instanceof MethodCall && ((MethodCall) this).getDeclaration() != null) {
                    return ((MethodCall) this).getDeclaration().getFileDeclaration();
                }

                //Flat.debuggingBreakpoint(id instanceof Variable && ((Variable)id).getName().equals("hypotheses"));
                ClassDeclaration type = ((Accessible) this).getDeclaringClass();

                if (type == null) {
                    //SyntaxUtils.invalidType(reference, reference.getType(), true);
                } else {
                    return type.getFileDeclaration();
                }
            }
        }
        if (this instanceof Variable) {
            Variable var = (Variable) this;

            if (var.getDeclaration() instanceof LocalDeclaration) {
                LocalDeclaration decl = (LocalDeclaration) var.getDeclaration();

                return decl.getReferenceFile();
            }
            if (var.getDeclaration() instanceof VirtualLocalDeclaration) {
                return ((VirtualLocalDeclaration) var.getDeclaration()).getReference().getReturnedNode().getTypeClass().getFileDeclaration();
            }
        } else if (this instanceof LocalDeclaration) {
            if (this instanceof Parameter && getParentMethod() instanceof LambdaMethodDeclaration) {
                LambdaMethodDeclaration lambda = (LambdaMethodDeclaration) getParentMethod();

                if (lambda.getParameterList().getVisibleIndex(this) >= 0 && lambda.methodCall != null) {
                    Value corresponding = lambda.getCorrespondingClosureDeclaration().getParameterList().getParameter(lambda.getParameterList().getVisibleIndex(this));

                    if (corresponding.isGenericType()) {
                        return getFileDeclaration();
                    }

                    return lambda.getContextDeclaringClass().getFileDeclaration();
                }
            }
            if (this instanceof VirtualLocalDeclaration) {
                return ((VirtualLocalDeclaration) this).getReference().getReturnedNode().getTypeClass().getFileDeclaration();
            } else {
                LocalDeclaration decl = (LocalDeclaration) this;

                if (decl.isImplicit() && decl.getImplicitType() != null && !decl.getImplicitType().isDecoding() && !decl.getImplicitType().isGenericType()) {
                    return decl.getImplicitType().getReferenceFile();
                }
            }
        }

        if (this instanceof Accessible && this instanceof VariableDeclaration == false &&
            this instanceof Instantiation == false && this instanceof StaticClassReference == false &&
            getParentMethod() instanceof ExtensionMethodDeclaration) {
            Accessible ref = ((Accessible) this).getReferenceNode();

            if (ref != this && ref instanceof ObjectReference) {
                ClassDeclaration clazz = ref.toValue().getTypeClass();

                if (clazz != null) {
                    return clazz.getFileDeclaration();
                }
            }
        }

        return getFileDeclaration();
    }

    public boolean isValueReference() {
        return false;
    }

    public boolean isAllocatedOnHeap() {
        return false;
    }

    public boolean requiresHeapAllocation() {
        return false;
    }

    /**
     * Get whether or not the identifier is a reference.
     *
     * @return Whether or not the identifier is a reference.
     */
    public boolean isReference() {
        return isReference(true);
    }

    public boolean isReference(boolean checkGeneric) {
        return getTypeObject() != null && getDataType(checkGeneric) == REFERENCE;
    }

    /**
     * Get whether or not the identifier is a pointer.
     *
     * @return Whether or not the identifier is a pointer.
     */
    public boolean isPointer() {
        return isPointer(true);
    }

    public boolean isPointer(boolean checkGeneric) {
        return getTypeObject() != null && getDataType(checkGeneric) == POINTER;
    }

    public boolean isDoublePointer() {
        return isDoublePointer(true);
    }

    public boolean isDoublePointer(boolean checkGeneric) {
        return getTypeObject() != null && getDataType(checkGeneric) == Value.DOUBLE_POINTER;
    }

    /**
     * Get the data type that is required within the context that the
     * specified Value is within.
     *
     * @return The data type that is required within the context that the
     * specified Value is within.
     */
    public byte getRequiredDataType() {
        Node parent = getParent();

        byte type = getDataType();

        if (parent instanceof MethodCallArgumentList) {
            MethodCall call = (MethodCall) parent.getAncestorOfType(MethodCall.class);
            Value param = call.getCorrespondingParameter(this);

            //TODO: make support for multidimensional arrays too....
            if (!call.isExternal() || !param.isPointer() || type != VALUE || !isPrimitiveArray()) {
                type = param.getDataType();
            }
        } else if (parent instanceof Assignment) {
            Assignment assignment = (Assignment) parent;
            Value assignee = assignment.getAssignedNodeValue();

            if (this instanceof Variable == false || (assignee instanceof Variable && !((Variable) this).isSameVariable((Variable) assignee))) {
                type = assignee.getDataType();
            }
        } else if (parent instanceof Return) {
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
    public String generateDataTypeOutput() {
        return generateDataTypeOutput(getDataType());
    }

    /**
     * Generate the text that is required for the data type in the
     * current context.
     *
     * @param dataType The data type to compare against.
     * @return The text that is required.
     */
    public String generateDataTypeOutput(byte dataType) {
        if (isFunctionType()) {
            return "";
        }

        byte required = getRequiredDataType();

        if (required == dataType) {
            return "";
        }

        if (required == REFERENCE) {
            if (dataType == VALUE) {
                return "&";
            } else if (dataType == POINTER) {
                return "**";
            }
        } else if (required == VALUE) {
            if (dataType == REFERENCE) {
                return "*";
            } else if (dataType == POINTER) {
                return "*";
            }
        } else if (required == POINTER) {
            if (dataType == VALUE || dataType == DOUBLE_POINTER) {
                return "&";
            } else if (dataType == REFERENCE) {
                return "*";
            }
        } else if (required == DOUBLE_POINTER) {
            if (dataType == POINTER) {
                return "&";
            }
        }

        if (required == POINTER) {
            return "*";
        } else if (required == REFERENCE) {
            return "&";
        }

        return "";
    }

    public GenericTypeArgument searchGenericTypeArgument(Value context) {
        if (isGenericType()) {
            GenericTypeParameter param = getGenericTypeParameter();

            if (param != null) {
                if (
                    !param.isMethodGenericParameter() &&
                        context != null &&
                        context.getParentClass() != null &&
                        context.getParentClass().isOfType(param.getParentClass()) &&
                        context.getParentClass() != param.getParentClass()) {
                    return SyntaxUtils.performWalk(context, context, context.getParentClass(), param.getParentClass(), param, true);
                } else if (this instanceof Accessible) {
                    return ((Accessible) this).getGenericTypeArgumentFromParameter(param);
                } else {
                    return param.getCorrespondingArgument(context);
                }
            }
        }

        return null;
    }

    /**
     * Generate the Flat syntax for the type of the specified Value's type.
     *
     * @return The Flat syntax for the type of the Value.
     */
    public final StringBuilder generateFlatType() {
        return generateFlatType((Value) null);
    }

    public final StringBuilder generateFlatType(Value context) {
        return generateFlatType(new StringBuilder(), context);
    }

    /**
     * Generate the Flat syntax for the type of the specified Value'stype.
     *
     * @param builder The StringBuider to append the data to.
     * @return The Flat syntax for the type of the Value.
     */
    public final StringBuilder generateFlatType(StringBuilder builder) {
        return generateFlatType(builder, null);
    }

    public final StringBuilder generateFlatType(StringBuilder builder, Value context) {
        return generateFlatType(builder, context, true);
    }

    /**
     * Generate the Flat syntax for the type of the specified Value's type.
     *
     * @param builder    The StringBuider to append the data to.
     * @param checkArray Whether or not to check if the type is an array.
     * @return The Flat syntax for the type of the Value.
     */
    public final StringBuilder generateFlatType(StringBuilder builder, Value context, boolean checkArray) {
        return generateFlatType(builder, context, checkArray, false);
    }

    public StringBuilder generateFlatType(StringBuilder builder, Value context, boolean checkArray, boolean defaultGeneric) {
        if (getTypeObject() instanceof FunctionType) {
            return ((FunctionType) getTypeObject()).closure.generateFlatInput(builder);
        }

        Value type = getFlatTypeValue(context);

        GenericTypeArgument arg = null;
        GenericTypeParameter param = getGenericTypeParameter();

        if (isGenericType()) {
            if (param != null) {
                if (!param.isMethodGenericParameter() && context != null && context.getParentClass() != null && context.getParentClass().isOfType(param.getParentClass())) {
                    if (context.getParentClass() != param.getParentClass()) {
                        arg = SyntaxUtils.performWalk(context, context, context.getParentClass(), param.getParentClass(), param, true);

                        if (arg == null) {
//							SyntaxUtils.performWalk(context, context.getParentClass(), param.getParentClass(), param, true);
                        } else {
                            return builder.append(arg.generateFlatType());
                        }
                    }
                } else {
                    arg = param.getCorrespondingArgument(context);
                }
            }
        }

        if (arg != null && !arg.isGenericType() && !arg.isAncestorOf(this)) {
            return builder.append(SyntaxUtils.getPrimitiveFlatType(arg.generateFlatType(context).toString()));
        } else if (arg != null && context != null && context instanceof GenericTypeArgument == false && context.getParentClass() != null && arg.getGenericTypeParameter() != null && arg.getGenericTypeParameter().getParentClass().encapsulates(context.getParentClass(), true)) {
            builder.append(arg.getType());
        } else if (defaultGeneric && param != null) {
            builder.append(param.getDefaultType());
        } else {
            builder.append(SyntaxUtils.getPrimitiveFlatType(type.getType()));
        }

        if (explicitlyNullable) {
            builder.append("?");
        }

        writeFlatGenericTypeArguments(builder, context, type);

        if (checkArray && isPrimitiveArray()) {
            builder.append(generateFlatArrayText());
        }
        if (isExternalType() && isPointer()) {
            builder.append('*');
        }

        return builder;
    }

    public void writeFlatGenericTypeArguments(StringBuilder builder, Value context, Value type) {
        builder.append(type.generateGenericType(context));
    }

    public Value replaceWithNullCheck() {
        Value returned = getReturnedNode();

        boolean needsBoxedBoolCheck = returned.isPointer() && "Bool".equals(returned.getType());

        if (needsBoxedBoolCheck) {
            Priority outerP = new Priority(parent, getLocationIn());

            BinaryOperation nullCheck = BinaryOperation.generateNullCheck(outerP.parent, this, this, parent);
            nullCheck.replaceWith(outerP);

            Variable local = TernaryOperation.getLocalVariableFromNullCheck(nullCheck).getDeclaration().generateUsableVariable(nullCheck, getLocationIn());

            BinaryOperation andOp = BinaryOperation.generateDefault(outerP.parent, getLocationIn());
            andOp.getLeftOperand().replaceWith(nullCheck);
            andOp.getOperator().setOperator(Operator.AND);

            Identifier value = SyntaxTree.decodeIdentifier(local, "value", getLocationIn(), false);
            local.setAccessedNode(value);

            andOp.getRightOperand().replaceWith(local);

            outerP.addChild(andOp);

            return outerP;
        }

        BinaryOperation operation = BinaryOperation.generateDefault(parent, getLocationIn());
        operation.getOperator().setOperator(Operator.NOT_EQUAL);
        operation.getRightOperand().replaceWith(Literal.decodeStatement(parent, getDefaultLiteralValue(), getLocationIn(), true, true));

        replaceWith(operation);

        Priority p = new Priority(getParent(), getLocationIn());
        p.addChild(this);

        operation.getLeftOperand().replaceWith(p);

        return operation;
    }

    public final String getFlatType() {
        return getFlatType(null);
    }

    public final String getFlatType(Value context) {
        return getFlatType(context, true);
    }

    public String getFlatParameterType(Value context) {
        return getFlatType(context);
    }

    public final String getFlatType(Value context, boolean checkArray) {
        return getFlatType(context, checkArray, false);
    }

    public String getFlatType(Value context, boolean checkArray, boolean defaultGeneric) {
        Value value = getFlatTypeValue(context);

        return value.generateFlatType(new StringBuilder(), context, checkArray, defaultGeneric).toString();
    }

    public void importFlatType(FileDeclaration toFile, Value context) {
        importFlatType(toFile, context, true);
    }

    public void importFlatType(FileDeclaration toFile, Value context, boolean checkArray) {
        importFlatType(toFile, context, checkArray, false);
    }

    public void importFlatType(FileDeclaration toFile, Value context, boolean checkArray, boolean defaultGeneric) {
        Value type = getFlatTypeValue(context);

        GenericTypeArgument arg = null;
        GenericTypeParameter param = getGenericTypeParameter();

        if (isGenericType()) {
            if (param != null) {
                if (!param.isMethodGenericParameter() && context != null && context.getParentClass() != null && context.getParentClass().isOfType(param.getParentClass())) {
                    if (context.getParentClass() != param.getParentClass()) {
                        arg = SyntaxUtils.performWalk(context, context, context.getParentClass(), param.getParentClass(), param, true);
                    }
                } else {
                    arg = param.getCorrespondingArgument(context);
                }
            }
        }

        if (arg != null && !arg.isGenericType()) {
            toFile.addImport(arg.getTypeClassLocation());
            arg.importGenericArgumentTypesTo(toFile);
        } else if (arg != null && context != null && context.getParentClass() != null && arg.getGenericTypeParameter().getParentClass().encapsulates(context.getParentClass(), true)) {
            toFile.addImport(arg.getTypeClassLocation());
            arg.importGenericArgumentTypesTo(toFile);
        } else if (defaultGeneric && param != null) {
            toFile.addImport(param.getFileDeclaration().getImport(param.getDefaultType(), false).getClassLocation());
        } else {
            toFile.addImport(type.getTypeClassLocation());

            for (GenericTypeArgument a : type.getGenericTypeArgumentList()) {
                a.importFlatType(toFile, context, checkArray, defaultGeneric);
            }
        }
    }

    public Value getFlatTypeValue(Value context) {
        return this;
    }

    public Value getClonedFlatTypeValue(Value context) {
        Value clone = (Value) clone(getParent(), getLocationIn(), false, true);

        if (getGenericTypeArgumentList() != null) {
            for (GenericTypeArgument arg : getGenericTypeArgumentList()) {
                clone.getGenericTypeArgumentList().addChild(arg.getFlatTypeValue(context));
            }
        }

        return clone;
    }

    public ClassDeclaration getFlatTypeClass() {
        return getProgram().getClassDeclaration(SyntaxUtils.getTypeClassLocation(this, SyntaxUtils.stripGenerics(getFlatType(this))));
    }

    public ClassDeclaration getFlatTypeClass(Value context) {
        return getFlatTypeClass(context, true);
    }

    public ClassDeclaration getFlatTypeClass(Value context, boolean checkArray) {
        return getFlatTypeClass(context, checkArray, true);
    }

    public ClassDeclaration getFlatTypeClass(Value context, boolean checkArray, boolean checkGeneric) {
        return getProgram().getClassDeclaration(SyntaxUtils.getTypeClassLocation(this, SyntaxUtils.stripGenerics(getFlatType(context, checkArray, checkGeneric))));
    }

    public boolean isOriginallyGenericType() {
        return getOriginalGenericType() != null;
    }

    public Value getOriginalGenericType() {
        if (isGenericType()) {
            return this;
        }

        return null;
    }

    public boolean convertToPrimitiveType() {
        if (!Flat.PRIMITIVE_OVERLOADS) {
            return false;
        }

        ClassDeclaration c = getTypeClass();

        if (c != null && getGenericTypeArgumentList() != null) {
            ClassDeclaration converted = c.getConvertedPrimitiveClass(getGenericTypeArgumentList().getTypes());

            if (converted != null) {
                getFileDeclaration().addImport(converted.getClassLocation());

                setType(converted);

                return true;
            }
        }

        return false;
    }

    public StringBuilder generateFlatArrayAccess() {
        return generateFlatArrayAccess(new StringBuilder());
    }

    public StringBuilder generateFlatArrayAccess(StringBuilder builder) {
        if (arrayAccess != null) {
            return arrayAccess.generateFlatInput(builder);
        }

        return builder;
    }

    public int getArrayAccessDimensions() {
        return arrayAccess != null ? arrayAccess.getNumDimensions() : 0;
    }
	
	/*/**
	 * @see flat.tree.Node#generateFlatInput(StringBuilder, boolean)
	 *
	@Override
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren)
	{
		return getTarget().generateUseOutput(builder);
	}*/

    public boolean isConstant() {
        return true;
    }

    public boolean isConsistent() {
        return true;
    }

    /**
     * Get whether or not the specified Node's type can be determined NOT
     * to be virtual or not at compilation time, i.e. If the program
     * doesn't have to use the vtable.
     *
     * @return Wether or not the type can be determined to NOT be virtual
     * at compilation time.
     */
    public boolean isVirtualTypeKnown() {
        return getParent() instanceof Super;
    }

    public String getInstanceType() {
        return getInstanceType(true);
    }

    public String getInstanceType(boolean checkGeneric) {
        if (checkGeneric && isGenericType()) {
            return getGenericReturnType();
        }

        Value original = getOriginalGenericType();

        if (original != null) {
            return original.getType();
        }

        return getFlatType();
    }

    public final GenericTypeParameter getGenericTypeParameter() {
        return getGenericTypeParameter(true);
    }

    public GenericTypeParameter getGenericTypeParameter(boolean checkArray) {
        return genericParameter;
    }

    public final GenericTypeParameter searchGenericTypeParameter(int index) {
        return searchGenericTypeParameter(index, true);
    }

    public GenericTypeParameter searchGenericTypeParameter(int index, boolean checkArray) {
        if (genericParameter != null) {
            return genericParameter;
        }
        if (getParentMethod(true) != null) {
            GenericTypeParameter param = getParentMethod(true).getGenericTypeParameter(getFlatType(this, checkArray));

            if (param != null) {
                return param;
            }
        }
//		if (getAncestorOfType(MethodCall.class) != null)
//		{
//			MethodCall call = (MethodCall)getAncestorOfType(MethodCall.class);
//
//			ClassDeclaration clazz = call.getReferenceNode().toValue().getTypeClass();
//
//			if (clazz != null)
//			{
//				GenericTypeParameter param = clazz.getGenericTypeParameter(getType(checkArray), this);
//
//				if (param != null)
//				{
//					return param;
//				}
//			}
//		}
        if (getParentClass() == null) {
            return null;
        }

        ClassDeclaration ref = getReferenceClass();

        if (ref == null) {
            SyntaxMessage.error("Could not find declaring type", this);
        }

        // use getReferenceNode/getDeclaringClass here??
        return getReferenceClass().getGenericTypeParameter(getFlatType(this, checkArray), this);
    }

    public ClassDeclaration getReferenceClass() {
        if (getParentMethod() instanceof ExtensionMethodDeclaration) {
            return getParentMethod().getParameterList().getReferenceParameter().getTypeClass();
        }

        return getParentClass();
    }

    public boolean isFunctionType() {
        return getTypeObject() instanceof FunctionType;
    }

    public final boolean isGenericType() {
        return isGenericType(false);
    }

    public final boolean isGenericType(boolean checkArray) {
        return isGenericType(checkArray, true);
    }

    public boolean isGenericType(boolean checkArray, boolean checkCast) {
        if (isFunctionType()) {
            return false;
        } else if (getGenericTypeParameter() != null) {
            Value ref = this;
            if (this instanceof Accessible && ((Accessible) this).canAccess()) {
                ref = ((Accessible) this).getDeclaringClass();
                if (ref == null) {
                    ref = this;
                }
            }
            String location = SyntaxUtils.getTypeClassLocation(ref, getType(false), true);

            if (location != null) {
                return false;
            }

            return true;
        }

        return false;
    }

    public boolean isPrimitiveGenericType() {
        return isGenericType() && SyntaxUtils.isPrimitiveType(getGenericReturnType());
    }

    public final String getGenericReturnType() {
        return getGenericReturnType(true);
    }

    public final String getGenericReturnType(Value context) {
        return getGenericReturnType(context, true);
    }

    public final String getGenericReturnType(boolean checkCast) {
        return getGenericReturnType(this, checkCast);
    }

    public String getGenericReturnType(Value context, boolean checkCast) {
//		GenericTypeParameter param = getGenericTypeParameter(checkCast);//getParentClass().getGenericTypeParameter(getType(checkCast), this);

        String location = SyntaxUtils.getTypeClassLocation(this, getType(), true);

        if (location != null) {
            return getType();
        }

        if (genericParameter != null) {
            return genericParameter.getDefaultType();
        }

        return null;
        //throw new UnimplementedOperationException("The getGenericReturnType() method must be implemented by class " + this.getClass().getName());
    }

    public GenericTypeParameterList getGenericTypeParameterDeclaration() {
        return getParentClass().getGenericTypeParameterDeclaration();
//		throw new UnimplementedOperationException("The getGenericDeclaration() method must be implemented by class " + this.getClass().getName());
    }

    public void replaceGenericArguments(Value target) {
        GenericTypeArgumentList args = getGenericTypeArgumentList();
        GenericTypeArgumentList targetArgs = target.getGenericTypeArgumentList();

        for (int i = 0; i < Math.min(args.getNumVisibleChildren(), targetArgs.getNumVisibleChildren()); i++) {
            GenericTypeArgument arg = args.getVisibleChild(i);
            GenericTypeArgument targetArg = targetArgs.getVisibleChild(i);

            replaceGenericArguments(this, target.getParentClass(), arg, targetArg);
        }
    }

    private static void replaceGenericArguments(Value context, ClassDeclaration targetClass, GenericTypeArgument arg, GenericTypeArgument target) {
        if (target.isGenericType()) {
            GenericTypeArgument a = SyntaxUtils.performWalk(context, context, context.getParentClass(), targetClass, target.genericParameter, true);

            if (a != null) {
                arg.setType(a);
            }
        } else {
            GenericTypeArgumentList args = arg.getGenericTypeArgumentList();
            GenericTypeArgumentList targetArgs = target.getGenericTypeArgumentList();

            for (int i = 0; i < Math.min(args.getNumVisibleChildren(), targetArgs.getNumVisibleChildren()); i++) {
                GenericTypeArgument searchArg = args.getVisibleChild(i);
                GenericTypeArgument targetArg = targetArgs.getVisibleChild(i);

                replaceGenericArguments(context, targetClass, searchArg, targetArg);
            }
        }
    }

    public void importGenericArgumentTypesTo(FileDeclaration toFile) {
        GenericTypeArgumentList args = getGenericTypeArgumentList();

        for (int i = 0; args != null && i < args.getNumVisibleChildren(); i++) {
            GenericTypeArgument arg = args.getVisibleChild(i);

            if (!arg.isGenericType() && arg.getTypeClass() != null) {
                toFile.addImport(arg.getTypeClass().getClassLocation());
            }

            arg.importGenericArgumentTypesTo(toFile);
        }
    }

    public final void setType(Value value) {
        setType(value, true);
    }

    public final void setType(Value value, Value context) {
        setType(value, context, true);
    }

    public final void setType(Value value, boolean extractType) {
        setType(value, value, extractType);
    }

    public void setType(Value value, Value context, boolean extractType) {
        if (value == null) {
            setTypeValue(null);

            return;
        }

        setArrayDimensions(value.getArrayDimensions());

        Value original = value;
        Value flatType = value.getFlatTypeValue(context);

        if (flatType.isGenericType() && ((context != null && isWithinStaticContext() != context.isWithinStaticContext()) || !getParentClass().isOfType(flatType.getGenericTypeParameter().getParentClass()))) {
            setTypeValue(original.getGenericReturnType());
        } else if (value.getType() != null) {
            String type = SyntaxUtils.getPrimitiveFlatType((extractType ? flatType : original).getType());

            if (extractType && !flatType.isExternalType() && !flatType.isGenericType() && getFileDeclaration().getClassDeclaration(type) == null && !getFileDeclaration().containsImport(type, false)) {
                if ((flatType instanceof LocalDeclaration == false || !((LocalDeclaration) flatType).isImplicit()) &&
                    (this instanceof LocalDeclaration == false || !((LocalDeclaration) this).isImplicit())) {
                    getFileDeclaration().addImport(flatType.getTypeClassLocation());
                }
            }

            ClassDeclaration clazz = getTypeClass();

            setType(type);
            setDataType(original.getDataType());

            if (extractType && (flatType instanceof ClassDeclaration == false || flatType != clazz)) {
                type = value.getFlatTypeValue(context).generateGenericType(context);

                GenericTypeArgumentList args = getGenericTypeArgumentList();

                if (args != null) {
                    GenericTypeArgumentList newArgs = new GenericTypeArgumentList(null, args.getLocationIn());

                    if (original instanceof ClassDeclaration == false && type.length() > 0) {
                        args.slaughterEveryLastVisibleChild();

                        decodeGenericTypeArguments(type, new Bounds(0, type.length()), true);

                        newArgs = args;
                    } else if (!value.isGenericType()) {
                        ClassDeclaration typeClass = getTypeClass();

                        if (typeClass != null) {
                            GenericTypeParameterList params = typeClass.getGenericTypeParameterDeclaration();

                            if (params.getNumParameters() > 0) {
                                for (int i = 0; i < params.getNumParameters(); i++) {
                                    GenericTypeParameter param = params.getParameter(i);
                                    GenericTypeArgument arg = new GenericTypeArgument(args, args.getLocationIn());
                                    arg.autoAdded = true;

                                    if (getParentClass() == typeClass && !isWithinStaticContext()) {
                                        arg.setType(params.getParameter(i).getType());
                                    } else if (clazz != null && typeClass.genericOverload == clazz) {
                                        int index = typeClass.genericOverload.getGenericTypeParameter(param.getName()).getVisibleIndex();

                                        if (typeClass.primitiveOverloadTypes[index] instanceof GenericTypeParameter) {
                                            arg = args.getVisibleChild(index);
                                            arg = arg.clone(newArgs, arg.getLocationIn(), true, true);
                                        } else {
                                            arg.setType(typeClass.primitiveOverloadTypes[index]);
                                        }
                                    } else {
                                        arg.setType(params.getParameter(i).getDefaultType());
                                    }

                                    newArgs.addChild(arg);
                                }
                            }
                        }
                    }

                    args.replaceWith(newArgs);
                    int i = 0;
                    for (GenericTypeArgument arg : newArgs.getVisibleListChildren()) {
                        arg.genericParameter = arg.searchGenericTypeParameter(i++);
                    }
                }
            }
        }

        if (original.isGenericType()) {
            setDataType(POINTER);
        } else if (flatType.getType() != null) {
            setDataType(flatType.getDataType());
        }
    }

    public Value replaceWithAutoboxedValue() {
        Value newValue = SyntaxUtils.autoboxPrimitive(this);

        replaceWith(newValue);

        return newValue;
    }

    public Value replaceWithAutoboxedValue(String type) {
        Value newValue = SyntaxUtils.autoboxPrimitive(this, type);

        replaceWith(newValue);

        return newValue;
    }

    public Value replaceWithUnboxedValue() {
        return SyntaxUtils.unboxPrimitive(this);
    }

    public Value replaceWithUnboxedValue(String type) {
        Value newValue = SyntaxUtils.unboxPrimitive(this, type, true);

//		if (!newValue.containsChild(newValue))
//		{
//			replaceWith(newValue);
//		}

        return newValue;
    }

    public Value replaceWithBoxedValue(Value required, String type) {
        if (getReturnedNode().isPrimitive() && !required.isPrimitive() && required.getType() != null) {
            return replaceWithAutoboxedValue(type);
        } else if (required.getType() != null && !getType().equals("void") && !getReturnedNode().isPrimitive() && required.isPrimitive()) {
            return replaceWithUnboxedValue(type);
        }

        return null;
    }

    public boolean isImmutable() {
        return getTypeClass().isImmutable();
    }

    public Value getRealValue() {
        return this;
    }

    /**
     * Fill the given {@link Value} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Value cloneTo(Value node, boolean cloneChildren, boolean cloneAnnotations) {
        return cloneTo(node, cloneChildren, cloneAnnotations, false);
    }

    public Value cloneTo(Value node, boolean cloneChildren, boolean cloneAnnotations, boolean copyFacadeValues) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        if (copyFacadeValues) {
            node.setArrayDimensions(getArrayDimensions());
            node.setType(getTypeStringValue(), true, false, false);
            node.setDataType(getDataType(false));
        }

        node.arrayAccess = arrayAccess;
        node.genericParameter = genericParameter;
        node.explicitlyNullable = explicitlyNullable;

        return node;
    }

    public Literal generateDefaultValue(Node parent, Location location) {
        if (!isPrimitive()) {
            return (Literal) Literal.decodeStatement(parent, Literal.NULL_IDENTIFIER, location, true, true);
        } else if (getTypeClass().isOfType(getProgram().getClassDeclaration("flat/primitive/number/Char"))) {
            return (Literal) Literal.decodeStatement(parent, "'\\0'", location, true, true);
        } else if (getTypeClass().isOfType(getProgram().getClassDeclaration("flat/primitive/number/Number"))) {
            return (Literal) Literal.decodeStatement(parent, "0", location, true, true);
        } else if (getTypeClass().isOfType(getProgram().getClassDeclaration("flat/primitive/Bool"))) {
            return (Literal) Literal.decodeStatement(parent, "false", location, true, true);
        }

        throw new UnsupportedOperationException("Value of type '" + getType() + "' does not have a default value.");
    }

    /**
     * Test the Value class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
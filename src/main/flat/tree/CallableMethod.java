package flat.tree;

import flat.tree.MethodList.SearchFilter;
import flat.tree.annotations.ImpureFunctionAnnotation;
import flat.tree.annotations.PureFunctionAnnotation;
import flat.tree.generics.GenericTypeParameter;
import flat.tree.variables.ObjectReference;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;
import flat.util.Patterns;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

import java.util.regex.Matcher;

/**
 * @author Braden Steffaniak
 * @since v0.2.14 Jul 1, 2014 at 11:41:22 PM
 * @version v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public interface CallableMethod {
    ClassDeclaration getTypeClass();

    public default VariableDeclaration toDeclaration() {
        return (VariableDeclaration) this;
    }

    /**
     * Get whether or not a call to the method would need to pass a reference to itself to the
     * method as an argument.
     *
     * @return Whether or not a method call needs to pass a reference.
     */
    public boolean isInstance();

    default void setReturnType(String returnType) {
        if (returnType != null) {
            boolean nullable = returnType.endsWith("?");
            if (nullable)
                returnType = returnType.substring(0, returnType.length() - 1);

            int bracketIndex = returnType.indexOf('[');

            if (bracketIndex > 0) {
                ((Value) this).setArrayDimensions(
                    SyntaxUtils.findArrayDimensions(returnType, bracketIndex, false));

                returnType = returnType.substring(0, bracketIndex).trim();
            }

            Matcher m = Patterns.IDENTIFIER.matcher(returnType);
            String symbol = "";

            if (m.find()) {
                int i = m.end();

                while (i < returnType.length()
                    && (StringUtils.isSymbol(returnType.charAt(i))
                        || StringUtils.isWhitespace(returnType.charAt(i)))
                    && returnType.charAt(i) != '<') {
                    i++;
                }

                symbol = returnType.substring(m.end(), i).trim();

                if (!symbol.startsWith("(")) {
                    returnType = returnType.substring(0, m.end()) + returnType.substring(i);
                }
            }

            ((Value) this).setType(returnType, true);
            if (nullable) {
                ((Value) this).setDataType(Value.POINTER);
                ((Value) this).explicitlyNullable = true;
            }

            if (symbol.equals("*")) {
                if (((Value) this).getDataType() == Value.POINTER) {
                    ((Value) this).setDataType(Value.DOUBLE_POINTER);
                } else {
                    ((Value) this).setDataType(Value.POINTER);
                }
            }
        }
    }

    default PureFunctionAnnotation getPureAnnotation() {
        return (PureFunctionAnnotation) ((Node) this)
            .getAnnotationOfType(PureFunctionAnnotation.class, false, true);
    }

    default boolean isPure() {
        return getPureAnnotation() != null;
    }

    default ImpureFunctionAnnotation getImpureAnnotation() {
        return (ImpureFunctionAnnotation) ((Node) this)
            .getAnnotationOfType(ImpureFunctionAnnotation.class, false, true);
    }

    default boolean isImpure() {
        return getImpureAnnotation() != null;
    }

    default void setImpure() {
        if (!isImpure()) {
            ((Node) this)
                .addAnnotation(new ImpureFunctionAnnotation((Node) this, Location.INVALID));
        }
    }

    /**
     * Get whether or not the method is static.
     *
     * @return Whether or not the method is static.
     */
    public boolean isStatic();

    /**
     * Get whether or not the method is external.
     *
     * @return Whether or not the method is external.
     */
    public boolean isExternal();

    /**
     * Get the name of the method.
     *
     * @return The name of the method.
     */
    public String getName();

    /**
     * Get the data type of the method.
     *
     * @return The data type of the method.
     */
    public byte getDataType(boolean checkGeneric);

    /**
     * @return The amount of dimensions that the array has, if any.
     * @see VariableDeclaration#getArrayDimensions()
     */
    public int getArrayDimensions();

    /**
     * @return Whether or not the method is virtual.
     * @see FlatMethodDeclaration#isVirtual()
     */
    public boolean isVirtual();

    public boolean isVirtualTypeKnown();

    /**
     * @return The nearest ClassDeclaration instance that contains this node.
     * @see Node#getParentClass()
     */
    public ClassDeclaration getParentClass();

    /**
     * Get the list of Values that represents the parameters for the Method.
     *
     * @return The list of Values that represents the parameters for the Method.
     */
    public ParameterList getParameterList();

    /**
     * @see Value#getGenericTypeParameter()
     */
    public GenericTypeParameter getGenericTypeParameter();

    /**
     * Check to see if the given types are compatible with the Method's parameters.
     *
     * @param types The types to check against the parameters.
     * @return Whether or not the types are compatible with the parameters.
     */
    public default boolean areCompatibleParameterTypes(GenericCompatible context, Value[] types) {
        return areCompatibleParameterTypes(context, types, false);
    }

    public default boolean areCompatibleParameterTypes(GenericCompatible context, Value[] types,
        boolean reverse) {
        return areCompatibleParameterTypes(new GenericCompatible[] {context}, types, reverse);
    }

    public default boolean areCompatibleParameterTypes(GenericCompatible[] contexts,
        Value[] types) {
        return areCompatibleParameterTypes(contexts, types, false);
    }

    public default boolean areCompatibleParameterTypes(GenericCompatible[] contexts, Value[] types,
        boolean reverse) {
        return areCompatibleParameterTypes(contexts, true, types, reverse);
    }

    /**
     * Check to see if the given types are compatible with the Method's parameters.
     *
     * @param searchGeneric Whether or not to search for the actual generic return type.
     * @param types The types to check against the parameters.
     * @return Whether or not the types are compatible with the parameters.
     */
    public default boolean areCompatibleParameterTypes(GenericCompatible[] contexts,
        boolean searchGeneric, Value[] types) {
        return areCompatibleParameterTypes(contexts, searchGeneric, types, false);
    }

    public default boolean areCompatibleParameterTypes(GenericCompatible[] contexts,
        boolean searchGeneric, Value[] types, boolean reverse) {
        return areCompatibleParameterTypes(contexts, searchGeneric, null, types, reverse);
    }

    public default boolean areCompatibleParameterTypes(GenericCompatible[] contexts,
        boolean searchGeneric, SearchFilter filter, Value[] types) {
        return areCompatibleParameterTypes(contexts, searchGeneric, filter, types, false);
    }

    public default boolean areCompatibleParameterTypes(GenericCompatible[] contexts,
        boolean searchGeneric, SearchFilter filter, Value[] types, boolean reverse) {
        if (getParameterList().getNumVisibleChildren() != types.length) {
            if (filter != null && !filter.allowMoreParameters
                && (types.length < getParameterList().getNumRequiredParameters()
                    || types.length > getParameterList().getNumVisibleChildren())) {
                return false;
            }
        }

        Value[] required = reverse ? types : getParameterList().getTypes();
        Value[] given = !reverse ? types : getParameterList().getTypes();

        if (SyntaxUtils.areTypesCompatible(contexts, required, given, searchGeneric)) {
            if (filter != null && filter.requireExactMatch) {
                SyntaxUtils.ValueDistance pair = SyntaxUtils.getParametersDistance(
                    contexts.length > 0 ? (Value) contexts[0] : null, required, given,
                    filter.defaultGeneric);

                if (pair.a > 0 || pair.b > 0) {
                    required[0].getFlatType((Value) contexts[0]);
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    public boolean isGenericType();

    public FlatMethodDeclaration getRootDeclaration();

    public ObjectReference getObjectReference();
}


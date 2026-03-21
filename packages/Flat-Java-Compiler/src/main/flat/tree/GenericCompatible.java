package flat.tree;

import flat.Flat;
import flat.TestContext;
import flat.error.SyntaxErrorException;
import flat.error.SyntaxMessage;
import flat.tree.generics.GenericTypeArgument;
import flat.tree.generics.GenericTypeArgumentList;
import flat.tree.generics.GenericTypeParameter;
import flat.tree.variables.VariableDeclaration;
import flat.util.Bounds;
import flat.util.Location;
import flat.util.SyntaxUtils;

/**
 * @author Braden Steffaniak
 * @since v0.2.29 Aug 28, 2014 at 11:51:16 PM
 * @version v0.2.44 Jul 13, 2015 at 1:28:17 AM
 */
public interface GenericCompatible {
    public static final String GENERIC_START = "<";
    public static final String GENERIC_END = ">";

    public static final char GENERIC_START_CHAR = GENERIC_START.charAt(0);
    public static final char GENERIC_END_CHAR = GENERIC_END.charAt(0);

    /**
     * Get the list of names that the ClassDeclaration accepts as generic declarations.<br>
     * For example: <blockquote>
     * 
     * <pre>
     * public class Map&lt;Key, Value&gt; {
     *
     * }
     * </pre>
     * 
     * </blockquote> The example above displays two generic parameters "Key" and "Value"<br>
     * These are used to abstract the data type used within the class "Map"
     *
     * @return A String array containing the names of the generic parameters.
     */
    GenericTypeArgumentList getGenericTypeArgumentList();

    default int getNumGenericTypeArguments() {
        return getGenericTypeArgumentList().getNumVisibleChildren();
    }

    default GenericTypeArgument getGenericTypeArgument(int index) {
        return getGenericTypeArgument(index, (Node) this);
    }

    static void throwMissingGenericTypeError(Node value) {
        throwMissingGenericTypeError(value, true);
    }

    static boolean throwMissingGenericTypeError(Node value, boolean require) {
        Flat.debuggingBreakpoint(require);
        return SyntaxMessage.queryError("Missing generic type declaration", value, require);
    }

    default GenericTypeArgument getGenericTypeArgument(int index, boolean require) {
        return getGenericTypeArgument(index, (Node) this, require);
    }

    default GenericTypeArgument getGenericTypeArgument(int index, Node value) {
        return getGenericTypeArgument(index, value, true);
    }

    default GenericTypeArgument getGenericTypeArgument(int index, Node value, boolean require) {
        if (index < 0 || index >= getNumGenericTypeArguments()) {
            throwMissingGenericTypeError(value, require);

            return null;
        }

        return getGenericTypeArgumentList().getVisibleChild(index);
    }

    default String getGenericTypeArgumentType(String parameterName) {
        return getGenericTypeArgumentType(parameterName, (Value) this);
    }

    default String getGenericTypeArgumentType(String parameterName, Value value) {
        Flat.debuggingBreakpoint(getGenericTypeArgumentInstance(parameterName, value) == null);
        GenericTypeArgument type = getGenericTypeArgumentInstance(parameterName, value);

        if (SyntaxMessage.queryError("Unable to find generic argument", (Node) this,
            type == null)) {
            return null;
        }

        if (type.isGenericType()) {
            return type.getDefaultType();
        }

        return type.getType();
    }

    default GenericTypeArgument getGenericTypeArgumentInstance(String parameterName) {
        return getGenericTypeArgumentInstance(parameterName, (Node) this);
    }

    default GenericTypeArgument getGenericTypeArgumentInstance(String parameterName, Node value) {
        return getGenericTypeArgumentInstance(parameterName, value, true);
    }

    default GenericTypeArgument getGenericTypeArgumentInstance(String parameterName, Node value,
        boolean require) {
        VariableDeclaration decl = (VariableDeclaration) this;
        ClassDeclaration clazz = null;

        if (!decl.isGenericType()) {
            clazz = decl.getTypeClass();
        } else {
            clazz = decl.getDeclaringClass();
        }

        if (decl instanceof Parameter && decl.getParentMethod()
            .getMethodGenericTypeParameterDeclaration().containsParameter(parameterName)) {
            int index = decl.getParentMethod().getMethodGenericTypeParameterDeclaration()
                .getParameterIndex(parameterName);

            if (value instanceof MethodCall) {
                GenericTypeArgumentList args =
                    ((MethodCall) value).getMethodGenericTypeArgumentList();

                if (args.getNumVisibleChildren() > index) {
                    return args.getVisibleChild(index);
                }
            }

            GenericTypeParameter param = decl.getParentMethod()
                .getMethodGenericTypeParameterDeclaration().getParameter(parameterName);

            // TODO: needs to support arrays
            GenericTypeArgument arg =
                new GenericTypeArgument((Node) this, ((Node) this).getLocationIn().asNew());
            arg.setTypeValue(param.getDefaultType());

            return arg;
        } else {
            return clazz.getGenericTypeParameter(parameterName, (GenericCompatible) value)
                .getCorrespondingArgument((GenericCompatible) value);
        }
    }

    default void addGenericTypeArgumentName(GenericTypeParameter parameter) {
        addGenericTypeArgumentName(getGenericTypeArgumentList(), parameter);
    }

    default void addGenericTypeArgumentName(String parameterName) {
        addGenericTypeArgumentName(getGenericTypeArgumentList(), parameterName);
    }

    default void addGenericTypeArgumentName(GenericTypeArgumentList list,
        GenericTypeParameter parameter) {
        GenericTypeArgument type = getGenericTypeArgumentName(parameter);

        list.addChild(type);
    }

    default void addGenericTypeArgumentName(GenericTypeArgumentList list, String parameterName) {
        GenericTypeArgument type = getGenericTypeArgumentName(parameterName);

        list.addChild(type);
    }

    default GenericTypeArgument getGenericTypeArgumentName(GenericTypeParameter parameter) {
        return SyntaxUtils.getGenericTypeArgumentName((Node) this, parameter);
    }

    default GenericTypeArgument getGenericTypeArgumentName(String parameterName) {
        return SyntaxUtils.getGenericTypeArgumentName((Node) this, parameterName);
    }

    default void decodeGenericTypeArguments(String statement, Bounds genericBounds) {
        decodeGenericTypeArguments(statement, genericBounds, true);
    }

    default void decodeGenericTypeArguments(String statement, Bounds genericBounds,
        boolean endingsIncluded) {
        Bounds clone = genericBounds.clone();

        if (endingsIncluded) {
            clone.setStart(genericBounds.getStart() + GENERIC_START.length());
            clone.setEnd(genericBounds.getEnd() - GENERIC_END.length());
        }

        String params = clone.extractString(statement);

        decodeGenericTypeArguments(params);
    }

    default void decodeGenericTypeArguments(String params) {
        decodeGenericTypeArguments(params, getGenericTypeArgumentList());
    }

    default void decodeGenericTypeArguments(String params, GenericTypeArgumentList list) {
        for (GenericTypeArgument arg : getGenericTypeArguments(params)) {
            list.addChild(arg);

            if (list.getProgram().getPhase() >= SyntaxTree.PHASE_INSTANCE_DECLARATIONS) {
                arg.convertToPrimitiveType();
            }
        }
    }

    default GenericTypeArgument[] getGenericTypeArguments(String params) {
        return SyntaxUtils.getGenericTypeArguments((Node) this, params);
    }

    /**
     * Test the GenericCompatible class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    static String test(TestContext context) {
        context.importClass("flat/datastruct/list/Stack");

        Node declaration = SyntaxTree.decodeScopeContents(context.method,
            "Stack<String> s = new Stack()", Location.INVALID, false);

        if (declaration == null) {
            return "Could not decode generic declaration";
        }

        context.method.addChild(declaration);

        try {
            SyntaxTree.decodeScopeContents(context.method, "s.push(4)", Location.INVALID, true);

            return "Did not throw an error for passing incorrect generic type";
        } catch (SyntaxErrorException e) {

        }

        try {
            Node node = SyntaxTree.decodeScopeContents(context.method, "s.push(\"str\")",
                Location.INVALID, true);

            context.method.addChild(node);
        } catch (SyntaxErrorException e) {
            return "Could not add correct generic type";
        }

        try {
            Node node = SyntaxTree.decodeScopeContents(context.method, "s.push(null)",
                Location.INVALID, true);

            context.method.addChild(node);
        } catch (SyntaxErrorException e) {
            return "Could not add null generic type";
        }

        return null;
    }
}


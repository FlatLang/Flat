package flat.tree.generics;

import flat.tree.*;
import flat.tree.variables.VariableDeclaration;
import flat.util.Bounds;
import flat.util.Location;
import flat.util.StringUtils;

/**
 * {@link TypeList} extension that represents a generic type declaration. Contains the information
 * of a generic type declaration. Contains all of the generic type parameter names.
 *
 * @author Braden Steffaniak
 * @since v0.2.40 Dec 7, 2014 at 4:05:23 PM
 * @version v0.2.44 Jul 13, 2015 at 1:28:17 AM
 */
public class GenericTypeParameterList extends TypeList<GenericTypeParameter> {
    public static final String EXTENDS_IDENTIFIER = "extends";
    public static final String IMPLEMENTS_IDENTIFIER = "implements";

    /**
     * @see Node#Node(Node, Location)
     */
    public GenericTypeParameterList(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public int getNumParameters() {
        return getNumVisibleChildren();
    }

    public boolean containsParameter(String parameterName) {
        return getParameterIndex(parameterName) >= 0;
    }

    public GenericTypeParameter getParameter(int index) {
        return getVisibleChild(index);
    }

    public GenericTypeParameter getParameter(String parameterName) {
        int index = getParameterIndex(parameterName);

        if (index < 0) {
            return null;
        }

        return getParameter(index);
    }

    public int getParameterIndex(String parameterName) {
        for (int i = 0; i < getNumParameters(); i++) {
            GenericTypeParameter param = getParameter(i);

            if (param.getName().equals(parameterName)) {
                return i;
            }
        }

        return -1;
    }

    public static void searchGenerics(String statement, VariableDeclaration.DeclarationData data) {
        int index = 0;
        Bounds bounds = null;

        do {
            bounds = StringUtils.findContentBoundsWithin(statement, GenericCompatible.GENERIC_START,
                GenericCompatible.GENERIC_END, index, true, '-');

            if (bounds.isValid()) {
                index = bounds.getEnd();

                data.addSkipBounds(bounds);
            }
        } while (bounds.isValid());

        data.setGenericsRemaining(data.getNumSkipBounds());
    }

    public void decodeGenericTypeParameters(String statement, Bounds genericBounds) {
        decodeGenericTypeParameters(statement, genericBounds, true);
    }

    public void decodeGenericTypeParameters(String statement, Bounds genericBounds,
        boolean endingsIncluded) {
        Bounds clone = genericBounds.clone();

        if (endingsIncluded) {
            clone.setStart(genericBounds.getStart() + GenericCompatible.GENERIC_START.length());
            clone.setEnd(genericBounds.getEnd() - GenericCompatible.GENERIC_END.length());
        }

        String params = clone.extractString(statement);

        decodeGenericTypeParameters(params);
    }

    private void addGenericParameterName(String parameterName) {
        addGenericParameterName(new GenericTypeParameter((Node) this, Location.INVALID),
            parameterName);
    }

    private boolean addMethodGenericParameterName(String parameterName) {
        return addGenericParameterName(
            new MethodGenericTypeParameter((Node) this, Location.INVALID), parameterName);
    }

    private boolean addGenericParameterName(GenericTypeParameter type, String parameterName) {
        int numWords = StringUtils.findNumWords(parameterName);

        if (numWords > 1) {
            boolean failed = true;

            Bounds bounds = StringUtils.findNextWordBounds(parameterName);

            if (bounds.extractString(parameterName).equals("reified")) {
                type.setReified(true);
                bounds = StringUtils.findNextWordBounds(parameterName, bounds.getEnd());

                String newParameterName = parameterName.substring(bounds.getStart());

                return addGenericParameterName(type, newParameterName);
            }

            Bounds nextBounds = StringUtils.findNextWordBounds(parameterName, bounds.getEnd());

            if (nextBounds.extractString(parameterName).equals(EXTENDS_IDENTIFIER)
                || nextBounds.extractString(parameterName).equals(IMPLEMENTS_IDENTIFIER)) {
                if (numWords == 3) {
                    String defaultType =
                        StringUtils.findNextWord(parameterName, nextBounds.getEnd());

                    type.setDefaultType(defaultType);

                    parameterName = bounds.extractString(parameterName);
                }

                failed = false;
            }

            if (failed) {
                return false;// SyntaxMessage.error("Could not decode Generic Parameter Declaration
                             // '" + parameterName + "'", this);
            }
        }

        type.setName(parameterName);

        VariableDeclaration.DeclarationData data = new VariableDeclaration.DeclarationData();

        searchGenerics(parameterName, data);
        type.iterateWords(parameterName, data, true);

        if (data.containsSkipBounds()) {
            type.setName(data.getSkipBounds(0).trimString(parameterName));
        }

        addChild(type);

        return true;
    }

    public void decodeGenericTypeParameters(String params) {
        String paramsList[] = StringUtils.splitCommas(params);

        for (String param : paramsList) {
            addGenericParameterName(param);
        }
    }

    public boolean decodeMethodGenericTypeParameters(String params) {
        String paramsList[] = StringUtils.splitCommas(params, 1);

        for (String param : paramsList) {
            if (!addMethodGenericParameterName(param)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren) {
        if (getNumVisibleChildren() > 0) {
            builder.append(GenericCompatible.GENERIC_START);

            boolean comma = false;

            for (GenericTypeParameter param : this) {
                if (comma) {
                    builder.append(", ");
                }

                comma = true;

                builder.append(param.generateFlatInput());
            }

            builder.append(GenericCompatible.GENERIC_END);
        }

        return builder;
    }

    public Value[] getTypes() {
        Value[] types = new Value[getNumVisibleChildren()];

        for (int i = 0; i < types.length; i++) {
            IValue value = new IValue(this, getLocationIn());
            value.setType(getVisibleChild(i).getDefaultType());

            types[i] = value;
        }

        return types;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public GenericTypeParameterList clone(Node temporaryParent, Location locationIn,
        boolean cloneChildren, boolean cloneAnnotations) {
        GenericTypeParameterList node = new GenericTypeParameterList(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public GenericTypeParameterList cloneTo(GenericTypeParameterList node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link GenericTypeParameterList} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public GenericTypeParameterList cloneTo(GenericTypeParameterList node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }
}

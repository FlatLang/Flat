package flat.tree;

import flat.TestContext;
import flat.error.SyntaxErrorException;
import flat.error.SyntaxMessage;
import flat.tree.annotations.Annotation;
import flat.tree.variables.Super;
import flat.util.Bounds;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

/**
 * Value extension that represents what one type is being casted
 * to another. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more information on what a cast looks like.
 *
 * @author Braden Steffaniak
 * @since v0.2.25 Aug 3, 2014 at 1:52:00 PM
 * @version v0.2.44 Jul 13, 2015 at 1:28:17 AM
 */
public class Cast extends IValue {
    /**
     * @see Node#Node(Node, Location)
     */
    public Cast(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * @see Node#getNumDecodedChildren()
     */
    @Override
    public int getNumDecodedChildren() {
        return super.getNumDecodedChildren() + 1;
    }

    public Value getValueNode() {
        return getNumChildren() > super.getNumDefaultChildren() ? (Value) getChild(super.getNumDefaultChildren() + 0) : null;
    }

    @Override
    public Value getReturnedNode() {
        return getValueNode().getReturnedNode();
    }

    public boolean isExplicitCast() {
        if (getParent() instanceof Super) {
            return false;
        }

        return true;
    }

    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren) {
        builder.append('(').append(generateFlatType()).append(')');
        getValueNode().generateFlatInput(builder);

        return builder;
    }

    /**
     * Decode the given statement into a {@link Cast} instance, if
     * possible. If it is not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li>(Int)5.2</li>
     * 	<li>(Value)getChild(getNumChildren())</li>
     * 	<li>(String[])array</li>
     * </ul>
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  {@link Cast} instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a {@link Cast}.
     */
    public static Cast decodeStatement(Node parent, String statement, Location location, boolean require) {
        if (statement.charAt(0) == '(') {
            Cast n = new Cast(parent, location);
            Bounds bounds = SyntaxUtils.findParenthesesBounds(n, statement);
            String contents = StringUtils.removeSurroundingParenthesis(statement, bounds).extractString(statement);

            Annotation a = Annotation.decodeStatement(n, contents, location, false);

            if (a != null) {
                n.addAnnotation(a);
                contents = Annotation.getFragment(contents);
            }

            String value = statement.substring(bounds.getEnd()).trim();

            if (n.decodeType(contents, require) && n.decodeValue(value, bounds, require)) {
                n.setDataType(n.getReturnedNode().getDataType(true, false));
                return n;
            }
        }

        return null;
    }

    private boolean decodeType(String contents, boolean require) {
		/*if (StringUtils.containsMultipleWords(contents))
		{
			return invalidTypeError(contents, require);
		}*/

        String type = StringUtils.findNextWord(contents);

        if (type == null) {
            return invalidTypeError(type, require);
        }

        int index = contents.indexOf(type);

        // If symbols are before the type... can't have that.
        if (index > 0) {
            return invalidTypeError(contents, require);
        }

        if (!checkArray(contents.substring(type.length()), require)) {
            return false;
        }

        return setType(type, require);
    }

    private boolean checkArray(String postfix, boolean require) {
        if (postfix.length() > 0) {
            int dimensions = SyntaxUtils.findArrayDimensions(postfix, false);

            if (dimensions <= 0) {
                return SyntaxMessage.queryError("Invalid symbols '" + postfix + "'", this, require);
            }

            setArrayDimensions(dimensions);
        }

        return true;
    }

    private boolean decodeValue(String value, Bounds bounds, boolean require) {
        Location newLoc = getLocationIn().asNew();

        bounds = bounds.clone();
        bounds.setStart(bounds.getEnd());
        bounds.setEnd(bounds.getStart() + value.length());

        newLoc.setBounds(bounds);

        Value node = SyntaxTree.decodeValue(this, value, newLoc, require);

        if (node == null) {
            if (require) {
                SyntaxMessage.error("Could not decode value '" + value + "'", this);
            }

            return false;
        }

        ClassDeclaration type = node.getReturnedNode().getTypeClass();

        if (type instanceof Trait) {
            type = getProgram().getClassDeclaration("flat/Object");
        }

        boolean array = type != null && type.containsArrayBracketOverload();

        if (getTypeClass() != null && !getTypeClass().isRelatedTo(type) && !array) {
            if (!getProgram().getController().isTesting) {
                node.getReturnedNode().getTypeClass();
                getTypeClass().isRelatedTo(type);
            }

            SyntaxMessage.error("Cannot cast from type '" + node.getReturnedNode().getTypeClassName() + "' to type '" + getTypeClassName() + "'", this);
        }

        if (!array) {
            node = checkPrimitiveType(node);
        }

        addChild(node);

        return true;
    }

    private Value checkPrimitiveType(Value node) {
        if (!node.getReturnedNode().isPrimitive() && isPrimitive()) {
            return SyntaxUtils.unboxPrimitive(node, getType());
        } else if (node.getReturnedNode().isPrimitive() && !isPrimitive()) {
            return SyntaxUtils.autoboxPrimitive(node);
        }

        return node;
    }

    private void invalidTypeError(String type) {
        invalidTypeError(type, true);
    }

    private boolean invalidTypeError(String type, boolean require) {
        return SyntaxMessage.queryError("Cannot cast to invalid type '" + type + "'", this, require);
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public Cast clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        Cast node = new Cast(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public Cast cloneTo(Cast node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Cast} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Cast cloneTo(Cast node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link Cast} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {
        Cast node = null;
        String statement = null;

        statement = "(Int)5.2";
        node = decodeStatement(context.method, statement, Location.INVALID, true);

        if (node != null) {
            statement = "(String)\"test\"";
            node = decodeStatement(context.method, statement, Location.INVALID, true);

            if (node != null) {
                try {
                    statement = "(String)54";
                    node = decodeStatement(context.method, statement, Location.INVALID, true);

                    return "Cast failed test at '" + statement + "'";
                } catch (SyntaxErrorException e) {

                }


            }
        }

        if (node == null) {
            return "Could not decode cast '" + statement + "'";
        }

        return null;
    }
}
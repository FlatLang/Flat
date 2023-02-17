package flat.tree;

import flat.TestContext;
import flat.util.Location;
import flat.util.StringUtils;

/**
 * {@link Node} extension that represents
 *
 * @author Braden Steffaniak
 * @since v0.2.37 Oct 18, 2014 at 9:00:50 PM
 * @version v0.2.39 Dec 7, 2014 at 3:17:17 AM
 */
public class ArrayAccessorMethod extends ArrayOverloadMethod {
    public static final String DISABLED_IDENTIFIER = "no";
    public static final String IDENTIFIER = "get";

    /**
     * @see Node#Node(Node, Location)
     */
    public ArrayAccessorMethod(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * Decode the given statement into a {@link ArrayAccessorMethod} instance, if
     * possible. If it is not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li>no get</li>
     * 	<li>get</li>
     * </ul>
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  {@link ArrayAccessorMethod} instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a {@link ArrayAccessorMethod}.
     */
    public static ArrayAccessorMethod decodeStatement(Node parent, String statement, Location location, boolean require) {
        if (StringUtils.findNextWord(statement).equals(DISABLED_IDENTIFIER)) {
            String remainder = statement.substring(DISABLED_IDENTIFIER.length() + 1).trim();

            if (remainder.equals(IDENTIFIER)) {
                ArrayAccessorMethod n = new ArrayAccessorMethod(parent, location);

                n.setName(IDENTIFIER);
                n.getArrayBracketOverload().cloneTo(n, false, true);
                n.setLocationIn(location);
                n.setType(n.getArrayBracketOverload());
                n.setDisabled(true);

                n.addIndexParameter();

                return n;
            }
        } else if (statement.equals(IDENTIFIER)) {
            ArrayAccessorMethod n = new ArrayAccessorMethod(parent, location);

            n.setName(IDENTIFIER);
            n.getArrayBracketOverload().cloneTo(n, false, true);
            n.setLocationIn(location);
            n.setType(n.getArrayBracketOverload());
            n.setLocationIn(location);

            n.addIndexParameter();

            return n;
        }

        return null;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ArrayAccessorMethod clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ArrayAccessorMethod node = new ArrayAccessorMethod(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ArrayAccessorMethod cloneTo(ArrayAccessorMethod node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ArrayAccessorMethod} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ArrayAccessorMethod cloneTo(ArrayAccessorMethod node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link ArrayAccessorMethod} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
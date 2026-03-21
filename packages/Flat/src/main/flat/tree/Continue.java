package flat.tree;

import flat.TestContext;
import flat.error.SyntaxMessage;
import flat.util.Location;

/**
 * {@link Node} extension that represents
 *
 * @author Braden Steffaniak
 * @version v0.2.36 Oct 13, 2014 at 12:16:42 AM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class Continue extends Node {
    public static final String IDENTIFIER = "continue";

    /**
     * @see Node#Node(Node, Location)
     */
    public Continue(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    @Override
    public void onAdded(Node parent) {
        if (getAncestorOfType(Loop.class) == null) {
            SyntaxMessage.error("'" + IDENTIFIER + "' statement must be within a loop", this);
        }

        super.onAdded(parent);
    }

    /**
     * Decode the given statement into a {@link Continue} instance, if possible. If it is not
     * possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * <li>continue</li>
     * </ul>
     *
     * @param parent The parent node of the statement.
     * @param statement The statement to try to decode into a {@link Continue} instance.
     * @param location The location of the statement in the source code.
     * @param require Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it into a {@link Continue}.
     */
    public static Continue decodeStatement(Node parent, String statement, Location location,
        boolean require) {
        if (statement.equals(IDENTIFIER)) {
            Continue n = new Continue(parent, location);

            return n;
        }

        return null;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public Continue clone(Node temporaryParent, Location locationIn, boolean cloneChildren,
        boolean cloneAnnotations) {
        Continue node = new Continue(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public Continue cloneTo(Continue node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Continue} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Continue cloneTo(Continue node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link Continue} class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}


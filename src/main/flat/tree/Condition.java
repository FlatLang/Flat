package flat.tree;

import flat.TestContext;
import flat.util.Location;

/**
 * Node extension that represents a condition. Conditions can be
 * found in if/else if statements, for/while loops, etc.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 9:57:40 PM
 * @version v0.2.26 Aug 6, 2014 at 2:48:50 PM
 */
public class Condition extends Node {
    /**
     * @see Node#Node(Node, Location)
     */
    public Condition(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public Condition clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        Condition node = new Condition(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public Condition cloneTo(Condition node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Condition} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Condition cloneTo(Condition node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the Condition class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
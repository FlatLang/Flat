package flat.tree;

import flat.TestContext;
import flat.util.Location;

/**
 * {@link Node} extension that represents
 *
 * @author Braden Steffaniak
 */
public class AnonymousClass extends Node {
    /**
     * @see Node#Node(Node, Location)
     */
    public AnonymousClass(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public static AnonymousClass decodeStatement(Node parent, String statement, Location location, boolean require) {


        return null;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public AnonymousClass clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        AnonymousClass node = new AnonymousClass(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public AnonymousClass cloneTo(AnonymousClass node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link AnonymousClass} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public AnonymousClass cloneTo(AnonymousClass node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link AnonymousClass} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
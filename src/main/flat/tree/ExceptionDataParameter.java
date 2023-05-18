package flat.tree;

import flat.TestContext;
import flat.util.Location;

/**
 * {@link Parameter} extension that represents
 *
 * @author Braden Steffaniak
 */
public class ExceptionDataParameter extends Parameter {
    /**
     * @see Node#Node(Node, Location)
     */
    public ExceptionDataParameter(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    @Override
    public boolean isUserMade(boolean checkAncestor) {
        return false;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ExceptionDataParameter clone(Node temporaryParent, Location locationIn,
        boolean cloneChildren, boolean cloneAnnotations) {
        ExceptionDataParameter node = new ExceptionDataParameter(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ExceptionDataParameter cloneTo(ExceptionDataParameter node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ExceptionDataParameter} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ExceptionDataParameter cloneTo(ExceptionDataParameter node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link ExceptionDataParameter} class type to make sure everything is working
     * properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}


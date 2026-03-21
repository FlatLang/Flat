package flat.tree;

import flat.TestContext;
import flat.util.Location;

/**
 * {@link Parameter} extension that represents
 *
 * @author Braden Steffaniak
 */
public class ReferenceParameter extends Parameter {
    /**
     * @see Node#Node(Node, Location)
     */
    public ReferenceParameter(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    @Override
    public boolean isLocal() {
        return false;
    }

    @Override
    public boolean isUserMade(boolean checkAncestor) {
        return false;
    }

    @Override
    public boolean isInstance() {
        return true;
    }

    @Override
    public void checkDataType(String type) {

    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ReferenceParameter clone(Node temporaryParent, Location locationIn,
        boolean cloneChildren, boolean cloneAnnotations) {
        ReferenceParameter node = new ReferenceParameter(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ReferenceParameter cloneTo(ReferenceParameter node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ReferenceParameter} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ReferenceParameter cloneTo(ReferenceParameter node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link ReferenceParameter} class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}


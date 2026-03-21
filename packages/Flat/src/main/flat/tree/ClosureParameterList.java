package flat.tree;

import flat.TestContext;
import flat.util.Location;

/**
 * {@link ParameterList} extension that represents
 *
 * @author Braden Steffaniak
 */
public class ClosureParameterList extends ParameterList<Value> {
    /**
     * @see Node#Node(Node, Location)
     */
    public ClosureParameterList(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ClosureParameterList clone(Node temporaryParent, Location locationIn,
        boolean cloneChildren, boolean cloneAnnotations) {
        ClosureParameterList node = new ClosureParameterList(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ClosureParameterList cloneTo(ClosureParameterList node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ClosureParameterList} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ClosureParameterList cloneTo(ClosureParameterList node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link ClosureParameterList} class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}


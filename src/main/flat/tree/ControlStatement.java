package flat.tree;

import flat.TestContext;
import flat.util.Location;

/**
 * {@link Node} extension that represents
 *
 * @author Braden Steffaniak
 * @since v0.2.37 Oct 16, 2014 at 5:19:17 PM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class ControlStatement extends IValue {
    /**
     * @see Node#Node(Node, Location)
     */
    public ControlStatement(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);

        Scope scope = new Scope(this, locationIn.asNew());
        setScope(scope);
    }

    /**
     * @see Node#getNumDefaultChildren()
     */
    @Override
    public int getNumDefaultChildren() {
        return super.getNumDefaultChildren() + 1;
    }

    /**
     * @see Node#getScope()
     */
    @Override
    public Scope getScope() {
        return (Scope) getChild(super.getNumDefaultChildren() + 0);
    }

    /**
     * @see Node#pendingScopeFragment(Node)
     */
    @Override
    public boolean pendingScopeFragment(Node node) {
        return getScope().isEmpty();
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ControlStatement clone(Node temporaryParent, Location locationIn, boolean cloneChildren,
        boolean cloneAnnotations) {
        ControlStatement node = new ControlStatement(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ControlStatement cloneTo(ControlStatement node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ControlStatement} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ControlStatement cloneTo(ControlStatement node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link ControlStatement} class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}


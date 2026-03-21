package flat.tree;

import flat.TestContext;
import flat.util.Location;

/**
 * {@link Node} extension that represents
 *
 * @author Braden Steffaniak
 */
public class ClosureContextDeclaration extends LocalDeclaration {
    public ClosureContext context;

    private int id;

    private static int idCounter = 1;

    /**
     * @see Node#Node(Node, Location)
     */
    public ClosureContextDeclaration(Node temporaryParent, Location locationIn,
        ClosureContext context) {
        super(temporaryParent, locationIn);

        this.context = context;
        context.declaration = this;
        id = idCounter++;
    }

    @Override
    public boolean isUserMade(boolean checkAncestor) {
        return false;
    }

    public String getName() {
        return "contextArg" + id;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ClosureContextDeclaration clone(Node temporaryParent, Location locationIn,
        boolean cloneChildren, boolean cloneAnnotations) {
        ClosureContextDeclaration node =
            new ClosureContextDeclaration(temporaryParent, locationIn, context);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ClosureContextDeclaration cloneTo(ClosureContextDeclaration node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ClosureContextDeclaration} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ClosureContextDeclaration cloneTo(ClosureContextDeclaration node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link ClosureContextDeclaration} class type to make sure everything is working
     * properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}


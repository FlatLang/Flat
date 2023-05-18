package flat.tree;

import flat.TestContext;
import flat.util.Location;

/**
 * {@link Node} extension that represents
 *
 * @author Braden Steffaniak
 */
public class AnonymousCompilerMethod extends BodyMethodDeclaration {
    /**
     * @see Node#Node(Node, Location)
     */
    public AnonymousCompilerMethod(Node temporaryParent, Location locationIn) {
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
    public AnonymousCompilerMethod clone(Node temporaryParent, Location locationIn,
        boolean cloneChildren, boolean cloneAnnotations) {
        AnonymousCompilerMethod node = new AnonymousCompilerMethod(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public AnonymousCompilerMethod cloneTo(AnonymousCompilerMethod node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link AnonymousCompilerMethod} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public AnonymousCompilerMethod cloneTo(AnonymousCompilerMethod node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link AnonymousCompilerMethod} class type to make sure everything is working
     * properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}


package flat.tree;

import flat.TestContext;
import flat.util.Location;

/**
 * {@link Node} extension that represents
 *
 * @author Braden Steffaniak
 */
public class ExceptionDeclaration extends LocalDeclaration {
    /**
     * @see Node#Node(Node, Location)
     */
    public ExceptionDeclaration(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * Decode the given statement into a {@link ExceptionDeclaration} instance, if possible. If it
     * is not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * <li></li>
     * <li></li>
     * <li></li>
     * </ul>
     *
     * @param parent The parent node of the statement.
     * @param statement The statement to try to decode into a {@link ExceptionDeclaration} instance.
     * @param location The location of the statement in the source code.
     * @param require Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it into a
     *         {@link ExceptionDeclaration}.
     */
    public static ExceptionDeclaration decodeStatement(Node parent, String statement,
        Location location, boolean require) {


        return null;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ExceptionDeclaration clone(Node temporaryParent, Location locationIn,
        boolean cloneChildren, boolean cloneAnnotations) {
        ExceptionDeclaration node = new ExceptionDeclaration(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ExceptionDeclaration cloneTo(ExceptionDeclaration node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ExceptionDeclaration} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ExceptionDeclaration cloneTo(ExceptionDeclaration node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link ExceptionDeclaration} class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}


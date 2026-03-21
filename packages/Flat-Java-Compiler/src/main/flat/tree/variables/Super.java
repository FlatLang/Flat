package flat.tree.variables;

import flat.TestContext;
import flat.tree.Node;
import flat.tree.ParameterList;
import flat.tree.Priority;
import flat.tree.StaticClassReference;
import flat.util.Location;

/**
 * {@link Priority} extension that represents
 *
 * @author Braden Steffaniak
 * @since v0.2.38 Dec 1, 2014 at 5:19:17 PM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class Super extends Priority {
    public static final String IDENTIFIER = "super";

    /**
     * @see Node#Node(Node, Location)
     */
    public Super(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * Decode the given statement into a {@link Super} instance, if possible. If it is not possible,
     * this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * <li>super</li>
     * </ul>
     *
     * @param parent The parent node of the statement.
     * @param statement The statement to try to decode into a {@link Super} instance.
     * @param location The location of the statement in the source code.
     * @param require Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it into a {@link Super}.
     */
    public static Super decodeStatement(Node parent, String statement, Location location,
        boolean require) {
        if (statement.equals(IDENTIFIER)) {
            String type = null;

            if (parent instanceof StaticClassReference) {
                type = ((StaticClassReference) parent).getType();
            } else {
                type = parent.getParentClass().getExtendedClassName();
            }

            Super n = new Super(parent, location);
            Priority p = Priority.decodeStatement(parent,
                "((" + type + ")" + ParameterList.OBJECT_REFERENCE_IDENTIFIER + ")", location,
                require);

            if (p == null) {
                return null;
            }

            p.cloneTo(n);

            return n;
        }

        return null;
    }

    /**
     * @see flat.tree.Variable#clone(Node, Location, boolean)
     */
    @Override
    public Super clone(Node temporaryParent, Location locationIn, boolean cloneChildren,
        boolean cloneAnnotations) {
        Super node = new Super(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public Super cloneTo(Super node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Super} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Super cloneTo(Super node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link Super} class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}


package flat.tree.exceptionhandling;

import flat.TestContext;
import flat.tree.Node;
import flat.util.Location;

/**
 * ExceptionHandler extension that represents the declaration of a finally node type. See
 * {@link #decodeStatement(Node, String, Location, boolean)} for more details on what correct inputs
 * look like.
 *
 * @author Braden Steffaniak
 * @since v0.1 Mar 22, 2014 at 4:02:21 PM
 * @version v0.2.26 Aug 6, 2014 at 2:48:50 PM
 */
public class Finally extends ExceptionHandler {
    /**
     * @see Node#Node(Node, Location)
     */
    public Finally(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * Decode the given statement into a Finally instance, if possible. If it is not possible, this
     * method returns null. <br>
     * The only acceptable input is "finally"
     *
     * @param parent The parent node of the statement.
     * @param statement The statement to try to decode into a Finally instance.
     * @param location The location of the statement in the source code.
     * @param require Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it into a Finally.
     */
    public static Finally decodeStatement(Node parent, String statement, Location location,
        boolean require) {
        if (statement.equals("finally")) {
            Finally n = new Finally(parent, location);

            return n;
        }

        return null;
    }

    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren,
        boolean generateArray) {
        builder.append("finally");

        if (outputChildren) {
            builder.append(" ");

            getScope().generateFlatInput(builder, true);
        }

        return builder;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public Finally clone(Node temporaryParent, Location locationIn, boolean cloneChildren,
        boolean cloneAnnotations) {
        Finally node = new Finally(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public Finally cloneTo(Finally node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Finally} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Finally cloneTo(Finally node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the Finally class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}


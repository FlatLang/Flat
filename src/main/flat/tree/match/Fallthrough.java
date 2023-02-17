package flat.tree.match;

import flat.TestContext;
import flat.error.SyntaxMessage;
import flat.tree.Node;
import flat.util.Location;

/**
 * {@link Node} extension that represents
 *
 * @author Braden Steffaniak
 * @since v0.2.37 Oct 17, 2014 at 11:46:55 PM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class Fallthrough extends Node implements MatchChild {
    public static final String IDENTIFIER = "fallthrough";

    /**
     * @see Node#Node(Node, Location)
     */
    public Fallthrough(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * Decode the given statement into a {@link Fallthrough} instance, if
     * possible. If it is not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li>fallthrough</li>
     * </ul>
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  {@link Fallthrough} instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a {@link Fallthrough}.
     */
    public static Fallthrough decodeStatement(Node parent, String statement, Location location, boolean require) {
        if (statement.equals(IDENTIFIER)) {
            Fallthrough n = new Fallthrough(parent, location);

            return n;
        }

        return null;
    }

    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren, boolean generateArray) {
        return builder.append("fallthrough");
    }

    @Override
    public void onAdded(Node parent) {
        if (parent.getBaseNode() instanceof Case == false) {
            SyntaxMessage.error("Fallthrough statements are only compatible within case statements", this);
        }
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public Fallthrough clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        Fallthrough node = new Fallthrough(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public Fallthrough cloneTo(Fallthrough node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Fallthrough} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Fallthrough cloneTo(Fallthrough node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link Fallthrough} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }

    public String toString() {
        return IDENTIFIER;
    }
}
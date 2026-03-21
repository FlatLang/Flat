package flat.tree.match;

import flat.TestContext;
import flat.error.SyntaxMessage;
import flat.tree.Node;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

/**
 * {@link MatchCase} extension that represents
 *
 * @author Braden Steffaniak
 * @since v0.2.37 Oct 17, 2014 at 7:25:10 PM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class Default extends MatchCase {
    public static final String IDENTIFIER = "default";

    /**
     * @see Node#Node(Node, Location)
     */
    public Default(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * Decode the given statement into a {@link Default} instance, if possible. If it is not
     * possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * <li>default</li>
     * <li>default Console.writeLine("Not found")</li>
     * </ul>
     *
     * @param parent The parent node of the statement.
     * @param statement The statement to try to decode into a {@link Default} instance.
     * @param location The location of the statement in the source code.
     * @param require Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it into a {@link Default}.
     */
    public static Default decodeStatement(Node parent, String statement, Location location,
        boolean require) {
        if (parent instanceof Match && StringUtils.findNextWord(statement).equals(IDENTIFIER)) {
            Default n = new Default(parent, location);

            int index = SyntaxUtils.findStringInBaseScope(statement + IDENTIFIER.length(), "=>");

            if (index < 0) {
                SyntaxMessage.error("Default case missing '=>' arrow", n);
            } else if (!n.decodeScopeFragment(statement, index + 2)) {
                SyntaxMessage.error("Unable to decode '" + IDENTIFIER + "' statement contents", n);
            } else {
                return n;
            }
        }

        return null;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public Default clone(Node temporaryParent, Location locationIn, boolean cloneChildren,
        boolean cloneAnnotations) {
        Default node = new Default(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public Default cloneTo(Default node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Default} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Default cloneTo(Default node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link Default} class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}


package flat.tree;

import flat.TestContext;
import flat.error.SyntaxMessage;
import flat.util.Bounds;
import flat.util.Location;
import flat.util.StringUtils;

/**
 * Node extension that represents an external type of variable or
 * method call.
 *
 * @author Braden Steffaniak
 * @since v0.2.4 May 8, 2014 at 6:55:51 PM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class ExternalType extends IValue {
    public static final String PREFIX = "external";
    public static final String IDENTIFIER = "type";

    /**
     * @see Node#Node(Node, Location)
     */
    public ExternalType(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * @see Node#isWithinExternalContext()
     */
    @Override
    public boolean isWithinExternalContext() {
        return true;
    }

    /**
     * Decode the given statement into a ExternalType instance, if
     * possible. If it is not possible, this method returns null.
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  ExternalType instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a ExternalType.
     */
    public static ExternalType decodeStatement(Node parent, String statement, Location location, boolean require) {
        if (StringUtils.startsWithWord(statement, PREFIX)) {
            Bounds bounds = StringUtils.findWordBounds(statement, IDENTIFIER);

            if (bounds.isValid()) {
                ExternalType n = new ExternalType(parent, location);

                int start = StringUtils.findNextNonWhitespaceIndex(statement, bounds.getEnd());

                if (start < 0) {
                    SyntaxMessage.error("Unfinished external type declaration", n);
                }

                String type = statement.substring(start);

                if (StringUtils.findNextWhitespaceIndex(type, 0) > 0) {
                    SyntaxMessage.error("Could not decode type declaration '" + type + "'", n);
                }

                n.setType(type);

                return n;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return PREFIX + " " + IDENTIFIER + " " + getType();
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ExternalType clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ExternalType node = new ExternalType(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ExternalType cloneTo(ExternalType node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ExternalType} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ExternalType cloneTo(ExternalType node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the ExternalType class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
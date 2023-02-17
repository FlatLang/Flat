package flat.tree;

import flat.TestContext;
import flat.error.SyntaxMessage;
import flat.util.Location;
import flat.util.StringUtils;

/**
 * {@link Instantiation} extension that represents
 *
 * @author Braden Steffaniak
 */
public class RegexLiteral extends Instantiation {
    private String originalValue;

    /**
     * @see Node#Node(Node, Location)
     */
    public RegexLiteral(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * Decode the given statement into a {@link RegexLiteral} instance, if
     * possible. If it is not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li></li>
     * 	<li></li>
     * 	<li></li>
     * </ul>
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  {@link RegexLiteral} instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a {@link RegexLiteral}.
     */
    public static RegexLiteral decodeStatement(Node parent, String statement, Location location, boolean require) {
        if (statement.startsWith("/")) {
            int endIndex = StringUtils.findEndingChar(statement, '/', 2, 1);

            if (endIndex == statement.length() - 1) {
                RegexLiteral n = new RegexLiteral(parent, location);

                n.originalValue = statement.substring(1, statement.length() - 1);

                String escapedString = n.originalValue.replaceAll("[\\\\]", "\\\\\\\\");

                Instantiation inst = Instantiation.decodeStatement(parent, "Pattern(\"" + escapedString + "\")", location, false);

                if (inst == null) {
                    Instantiation.decodeStatement(parent, "Pattern(\"" + escapedString + "\")", location, false);
                    SyntaxMessage.queryError("Unable to decode regex literal '" + statement + "'", n, require);

                    return null;
                }

                inst.cloneTo(n);

                return n;
            }
        }

        return null;
    }

    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren) {
        return builder.append(originalValue);
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public RegexLiteral clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        RegexLiteral node = new RegexLiteral(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public RegexLiteral cloneTo(RegexLiteral node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link RegexLiteral} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public RegexLiteral cloneTo(RegexLiteral node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.originalValue = originalValue;

        return node;
    }

    /**
     * Test the {@link RegexLiteral} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
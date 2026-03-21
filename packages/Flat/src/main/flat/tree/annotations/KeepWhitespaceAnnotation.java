package flat.tree.annotations;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.Node;
import flat.util.Location;
import flat.util.StringUtils;
import flat.util.SyntaxUtils;

public class KeepWhitespaceAnnotation extends Annotation {
    public String indent;

    /**
     * @see Node#Node(Node, Location)
     */
    public KeepWhitespaceAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren) {
        return builder.append("[KeepWhitespace]");
    }

    /**
     * Decode the given statement into a {@link KeepWhitespaceAnnotation} instance, if possible. If
     * it is not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * <li></li>
     * <li></li>
     * <li></li>
     * </ul>
     *
     * @param parent The parent node of the statement.
     * @param parameters The statement to try to decode into a {@link KeepWhitespaceAnnotation}
     *        instance.
     * @param location The location of the statement in the source code.
     * @param require Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it into a
     *         {@link KeepWhitespaceAnnotation}.
     */
    public static KeepWhitespaceAnnotation decodeStatement(Node parent, String name,
        String parameters, Location location, boolean require) {
        if (name.equals("KeepWhitespace")) {
            KeepWhitespaceAnnotation n = new KeepWhitespaceAnnotation(parent, location);

            String[] params = StringUtils.splitCommas(parameters);

            for (String param : params) {
                String[] values = StringUtils.parseNamedArgument(param);

                if (values != null) {
                    if (values[0].equals("indent")) {
                        if (SyntaxUtils.getLiteralTypeName(n, values[1]).equals("String")) {
                            n.indent = StringUtils.removeSurroundingQuotes(values[1]).toLowerCase();
                        }
                    } else {
                        SyntaxMessage.error("Invalid argument '" + values[0]
                            + "' given to KeepWhitespace annotation", n);
                    }
                }
            }

            return n;
        }

        return null;
    }

    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        return result;
    }

    @Override
    public void onAdded(Node parent) {
        super.onAdded(parent);
    }

    @Override
    public boolean onNextStatementDecoded(Node next) {
        return super.onNextStatementDecoded(next);
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public KeepWhitespaceAnnotation clone(Node temporaryParent, Location locationIn,
        boolean cloneChildren, boolean cloneAnnotations) {
        KeepWhitespaceAnnotation node = new KeepWhitespaceAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public KeepWhitespaceAnnotation cloneTo(KeepWhitespaceAnnotation node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link KeepWhitespaceAnnotation} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public KeepWhitespaceAnnotation cloneTo(KeepWhitespaceAnnotation node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link KeepWhitespaceAnnotation} class type to make sure everything is working
     * properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}


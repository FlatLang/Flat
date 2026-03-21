package flat.tree.annotations;

import flat.TestContext;
import flat.ValidationResult;
import flat.tree.Node;
import flat.util.Location;
import flat.util.StringUtils;
import java.util.Arrays;

public class CommentAnnotation extends Annotation {
    public String value;

    /**
     * @see Node#Node(Node, Location)
     */
    public CommentAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * Decode the given statement into a {@link CommentAnnotation} instance, if possible. If it is
     * not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * <li></li>
     * <li></li>
     * <li></li>
     * </ul>
     *
     * @param parent The parent node of the statement.
     * @param parameters The statement to try to decode into a {@link CommentAnnotation} instance.
     * @param location The location of the statement in the source code.
     * @param require Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it into a
     *         {@link CommentAnnotation}.
     */
    public static CommentAnnotation decodeStatement(Node parent, String name, String parameters,
        Location location, boolean require) {
        if (name.equals("Comment")) {
            CommentAnnotation n = new CommentAnnotation(parent, location);

            n.value = StringUtils.removeSurroundingQuotes(parameters.trim());

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
    public boolean onNextStatementDecoded(Node next) {
        return super.onNextStatementDecoded(next);
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public CommentAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren,
        boolean cloneAnnotations) {
        CommentAnnotation node = new CommentAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public CommentAnnotation cloneTo(CommentAnnotation node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link CommentAnnotation} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public CommentAnnotation cloneTo(CommentAnnotation node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link CommentAnnotation} class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}


package flat.tree.annotations;

import flat.TestContext;
import flat.ValidationResult;
import flat.tree.Node;
import flat.util.Location;

import java.util.Arrays;

public class TargetAnnotation extends Annotation {
    public boolean opposite;

    public String[] targets;

    /**
     * @see Node#Node(Node, Location)
     */
    public TargetAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public boolean currentTarget() {
        return currentTarget(getProgram().getController().target);
    }

    public static boolean targetMatches(String target, String value) {
        return value.equals(target) || value.equals("js") && target.equals("es6");
    }

    public boolean currentTarget(String target) {
        for (String t : targets) {
            if (targetMatches(target, t)) {
                return !opposite;
            }
        }

        return opposite;
    }

    /**
     * Decode the given statement into a {@link TargetAnnotation} instance, if
     * possible. If it is not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li></li>
     * 	<li></li>
     * 	<li></li>
     * </ul>
     *
     * @param parent     The parent node of the statement.
     * @param parameters The statement to try to decode into a
     *                   {@link TargetAnnotation} instance.
     * @param location   The location of the statement in the source code.
     * @param require    Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a {@link TargetAnnotation}.
     */
    public static TargetAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require) {
        if (name.equals("Target")) {
            TargetAnnotation n = new TargetAnnotation(parent, location);

            n.targets = parameters.trim().toLowerCase().split("\\s+");
            n.opposite = n.targets[0].equals("not");

            if (n.opposite) {
                n.targets = Arrays.copyOfRange(n.targets, 1, n.targets.length);
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
    public boolean onNextStatementDecoded(Node next) {
        return super.onNextStatementDecoded(next);
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public TargetAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        TargetAnnotation node = new TargetAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public TargetAnnotation cloneTo(TargetAnnotation node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link TargetAnnotation} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public TargetAnnotation cloneTo(TargetAnnotation node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link TargetAnnotation} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
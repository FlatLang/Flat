package flat.tree.annotations;

import flat.TestContext;
import flat.ValidationResult;
import flat.tree.Node;
import flat.util.Location;

import java.util.Arrays;

public class TargetRuntimeAnnotation extends Annotation {
    public boolean opposite;

    public String[] runtimes;

    /**
     * @see Node#Node(Node, Location)
     */
    public TargetRuntimeAnnotation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public boolean currentTargetRuntime() {
        return currentTargetRuntime(getProgram().getController().targetRuntime);
    }

    public boolean currentTargetRuntime(String targetRuntime) {
        for (String t : runtimes) {
            if (t.equals(targetRuntime)) {
                return !opposite;
            }
        }

        return opposite;
    }

    /**
     * Decode the given statement into a {@link TargetRuntimeAnnotation} instance, if
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
     *                   {@link TargetRuntimeAnnotation} instance.
     * @param location   The location of the statement in the source code.
     * @param require    Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a {@link TargetRuntimeAnnotation}.
     */
    public static TargetRuntimeAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require) {
        if (name.equals("TargetRuntime")) {
            TargetRuntimeAnnotation n = new TargetRuntimeAnnotation(parent, location);

            n.runtimes = parameters.trim().toLowerCase().split("\\s+");
            n.opposite = n.runtimes[0].equals("not");

            if (n.opposite) {
                n.runtimes = Arrays.copyOfRange(n.runtimes, 1, n.runtimes.length);
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
    public TargetRuntimeAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        TargetRuntimeAnnotation node = new TargetRuntimeAnnotation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public TargetRuntimeAnnotation cloneTo(TargetRuntimeAnnotation node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link TargetRuntimeAnnotation} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public TargetRuntimeAnnotation cloneTo(TargetRuntimeAnnotation node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link TargetRuntimeAnnotation} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
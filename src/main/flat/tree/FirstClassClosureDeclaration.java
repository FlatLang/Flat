package flat.tree;

import flat.TestContext;
import flat.ValidationResult;
import flat.util.Location;

/**
 * {@link Node} extension that represents
 *
 * @author Braden Steffaniak
 */
public class FirstClassClosureDeclaration extends ClosureDeclaration {
    public volatile Identifier reference;

    /**
     * @see Node#Node(Node, Location)
     */
    public FirstClassClosureDeclaration(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * @param phase The phase that the node is being validated in.
     * @see Node#validate(int)
     */
    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        register();

        if (phase == SyntaxTree.PHASE_PRE_GENERATION) {
            if (reference instanceof ClosureDeclaration) {
                ClosureDeclaration c = (ClosureDeclaration) reference;

                if (c.id == -1) {
                    c.register();
                }
            }
        }

        return result;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public FirstClassClosureDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        FirstClassClosureDeclaration node = new FirstClassClosureDeclaration(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public FirstClassClosureDeclaration cloneTo(FirstClassClosureDeclaration node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link FirstClassClosureDeclaration} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public synchronized FirstClassClosureDeclaration cloneTo(FirstClassClosureDeclaration node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.reference = reference;

        return node;
    }

    /**
     * Test the {@link FirstClassClosureDeclaration} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}

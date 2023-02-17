package flat.tree;

import flat.TestContext;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;

/**
 * {@link VariableDeclaration} extension that represents
 *
 * @author Braden Steffaniak
 */
public class ClosureVariableDeclaration extends VariableDeclaration {
    public VariableDeclaration originalDeclaration;
    public boolean requiresHeapAllocation;

    public static final String CONTEXT_VARIABLE_NAME = "context";

    /**
     * @see Node#Node(Node, Location)
     */
    public ClosureVariableDeclaration(Node temporaryParent, Location locationIn, VariableDeclaration originalDeclaration) {
        super(temporaryParent, locationIn);

        this.originalDeclaration = originalDeclaration;

        setIsValueReference(true);
    }

    public ClosureContext getClosureContext() {
        return (ClosureContext) parent;
    }

    @Override
    public void onReplaced(Node parent, Node replacement) {
        super.onReplaced(parent, replacement);

        getRootDeclaration().closureVariableDeclarations.add((ClosureVariableDeclaration) replacement);
    }

    @Override
    public void onRemoved(Node parent) {
        super.onRemoved(parent);

        getRootDeclaration().closureVariableDeclarations.remove(this);
    }

    @Override
    public void onAdded(Node parent) {
        super.onAdded(parent);

        getRootDeclaration().closureVariableDeclarations.add(this);
    }

    public boolean requiresHeapAllocation() {
        return requiresHeapAllocation;
    }

    public VariableDeclaration getRootDeclaration() {
        if (originalDeclaration instanceof ClosureVariableDeclaration) {
            return ((ClosureVariableDeclaration) originalDeclaration).getRootDeclaration();
        }

        return originalDeclaration;
    }

    @Override
    public VariableDeclaration getOriginalDeclaration() {
        return originalDeclaration.getOriginalDeclaration();
    }

    @Override
    public String getType() {
        return originalDeclaration.getType();
    }

    @Override
    public String getType(boolean checkCast) {
        return originalDeclaration.getType(checkCast);
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ClosureVariableDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ClosureVariableDeclaration node = new ClosureVariableDeclaration(temporaryParent, locationIn, originalDeclaration);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ClosureVariableDeclaration cloneTo(ClosureVariableDeclaration node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ClosureVariableDeclaration} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ClosureVariableDeclaration cloneTo(ClosureVariableDeclaration node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.originalDeclaration = originalDeclaration;
        node.requiresHeapAllocation = requiresHeapAllocation;

        return node;
    }

    /**
     * Test the {@link ClosureVariableDeclaration} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
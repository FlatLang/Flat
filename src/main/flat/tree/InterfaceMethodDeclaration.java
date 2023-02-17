package flat.tree;

import flat.TestContext;
import flat.ValidationResult;
import flat.tree.variables.ObjectReference;
import flat.util.Location;

/**
 * {@link Node} extension that represents
 *
 * @author Braden Steffaniak
 */
public class InterfaceMethodDeclaration extends BodyMethodDeclaration {
    public boolean containsScope;

    /**
     * @see Node#Node(Node, Location)
     */
    public InterfaceMethodDeclaration(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * @see Node#Node(Node, Location)
     */
    public InterfaceMethodDeclaration(Node temporaryParent, Location locationIn, BodyMethodDeclaration method) {
        super(temporaryParent, locationIn);

        int oldId = uniqueID;

        method.cloneTo(this);

        uniqueID = oldId;
    }

    @Override
    public Node followedByScope(boolean scope) {
        containsScope = scope || shorthandAction != null;

        return super.followedByScope(scope);
    }

    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS) {
            if (!containsScope) {
                AbstractMethodDeclaration abst = new AbstractMethodDeclaration(this, getLocationIn());
                abst.createFrom(this);

                replaceWith(abst);

                abst.objectReference = new ObjectReference(abst);

                result.skipCycle = true;
            }
        }

        return result;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public InterfaceMethodDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        InterfaceMethodDeclaration node = new InterfaceMethodDeclaration(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public InterfaceMethodDeclaration cloneTo(InterfaceMethodDeclaration node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link InterfaceMethodDeclaration} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public InterfaceMethodDeclaration cloneTo(InterfaceMethodDeclaration node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.containsScope = containsScope;

        return node;
    }

    /**
     * Test the {@link InterfaceMethodDeclaration} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
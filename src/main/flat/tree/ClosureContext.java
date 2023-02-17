package flat.tree;

import flat.TestContext;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;

/**
 * {@link Node} extension that represents
 *
 * @author Braden Steffaniak
 */
public class ClosureContext extends TypeList<ClosureVariableDeclaration> {
    public int id;

    public ClosureContextDeclaration declaration;

    /**
     * @see Node#Node(Node, Location)
     */
    public ClosureContext(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public VariableDeclaration addDeclaration(VariableDeclaration declaration) {
        for (ClosureVariableDeclaration decl : this) {
            if (decl.originalDeclaration == declaration) {
                return decl;
            }
        }

        ClosureVariableDeclaration var = new ClosureVariableDeclaration(this, getLocationIn(), declaration);

        declaration.cloneTo(var, true, true);
        var.setIsValueReference(true);
        var.requiresHeapAllocation = declaration instanceof LocalDeclaration;// && !declaration.isPrimitive();

        addChild(var);

        return var;
    }

    public String getName() {
        return "Context" + id;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ClosureContext clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ClosureContext node = new ClosureContext(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ClosureContext cloneTo(ClosureContext node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ClosureContext} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ClosureContext cloneTo(ClosureContext node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link ClosureContext} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
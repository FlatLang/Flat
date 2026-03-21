package flat.tree;

import flat.TestContext;
import flat.ValidationResult;
import flat.tree.variables.FieldDeclaration;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;

/**
 * {@link Node} extension that represents
 *
 * @author Braden Steffaniak
 * @since v0.2.37 Oct 18, 2014 at 9:00:50 PM
 * @version v0.2.39 Dec 7, 2014 at 3:17:17 AM
 */
public abstract class PropertyMethod extends BodyMethodDeclaration {
    public static final String PARAMETER_NAME = "value";
    public FieldDeclaration originalField;

    private boolean disabled;

    /**
     * @see Node#Node(Node, Location)
     */
    public PropertyMethod(Node temporaryParent, Location locationIn) {
        super(temporaryParent,
            new Location(
                locationIn.getLineNumber() + temporaryParent.getLocationIn().getLineNumber(),
                locationIn.getOffset(), locationIn.getStart(), locationIn.getEnd()));
    }

    public VariableDeclaration getDeclaration() {
        Node n = getParent().getAncestorWithScopeOrClass();

        while (n != null) {
            if (n instanceof ClassDeclaration) {
                ClassDeclaration c = (ClassDeclaration) n;

                FieldDeclaration f = c.getField(getName());

                if (f != null) {
                    return f;
                }
            } else if (n.getScope() != null) {
                LocalDeclaration d =
                    n.getScope().getVariableList().getVariable(getName(), n.getScope());

                if (d != null) {
                    return d;
                }
            }

            n = n.getParent().getAncestorWithScopeOrClass();
        }

        return null;
    }

    public FieldDeclaration getParentField() {
        return (FieldDeclaration) getAncestorOfType(FieldDeclaration.class);
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;

        correspondingPrimitiveOverloads.forEach(x -> {
            ((PropertyMethod) x).disabled = disabled;
        });
    }

    @Override
    public FlatMethodDeclaration[] getOverridingMethods() {
        if (isDisabled()) {
            return new FlatMethodDeclaration[0];
        }

        return super.getOverridingMethods();
    }

    @Override
    public FlatMethodDeclaration getOverriddenMethod() {
        if (isDisabled()) {
            return null;
        }

        return super.getOverriddenMethod();
    }

    /**
     * Validate the parameters of the method header.
     *
     * @param phase The phase that the node is being validated in.
     * @see Node#validate(int)
     */
    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        return result;
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public PropertyMethod cloneTo(PropertyMethod node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link PropertyMethod} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public PropertyMethod cloneTo(PropertyMethod node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.disabled = disabled;
        node.originalField = originalField;

        return node;
    }

    /**
     * Test the {@link PropertyMethod} class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }

    public abstract String getMethodPrefix();
}


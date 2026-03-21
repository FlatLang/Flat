package flat.tree;

import flat.TestContext;
import flat.ValidationResult;
import flat.util.Location;

/**
 * {@link Node} extension that represents
 *
 * @author Braden Steffaniak
 * @since v0.2.37 Oct 18, 2014 at 9:00:50 PM
 * @version v0.2.39 Dec 7, 2014 at 3:17:17 AM
 */
public abstract class ArrayOverloadMethod extends BodyMethodDeclaration {
    public static final String PARAMETER_NAME = "value";

    private boolean disabled;

    /**
     * @see Node#Node(Node, Location)
     */
    public ArrayOverloadMethod(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);// new Location(locationIn.getLineNumber() +
                                           // temporaryParent.getLocationIn().getLineNumber(),
                                           // locationIn.getOffset(), locationIn.getStart(),
                                           // locationIn.getEnd()));

        setVisibility(PUBLIC);
    }

    public ArrayBracketOverload getArrayBracketOverload() {
        return getParentClass().arrayBracketOverload;
    }

    public void addIndexParameter() {
        Parameter p = Parameter.decodeStatement(this,
            getArrayBracketOverload().getIndexValue().generateFlatInput().toString(),
            getLocationIn().asNew(), true);
        getArrayBracketOverload().getIndexValue().cloneTo(p, false, true);

        getParameterList().addChild(p);
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
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
    public ArrayOverloadMethod cloneTo(ArrayOverloadMethod node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ArrayOverloadMethod} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ArrayOverloadMethod cloneTo(ArrayOverloadMethod node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.disabled = disabled;

        return node;
    }

    /**
     * Test the {@link ArrayOverloadMethod} class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }

    public String getMethodPrefix() {
        return "ArrayValue";
    }
}


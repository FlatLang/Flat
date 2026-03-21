package flat.tree;

import flat.TestContext;
import flat.ValidationResult;
import flat.util.Location;

import java.util.ArrayList;

/**
 * Node extension that represents a list of parameters for a flat method.
 *
 * @author Braden Steffaniak
 * @since v0.2.38 Nov 11, 2014 at 3:44:42 PM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class FlatParameterList extends ParameterList<Parameter> {
    public ReturnParameterList returnParameters;

    public FlatParameterList(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);

        returnParameters = new ReturnParameterList(this, locationIn.asNew());
    }

    public int getNumRequiredParameters() {
        int required = 0;

        for (Parameter p : this) {
            if (p.isOptional()) {
                return required;
            }

            required++;
        }

        return required;
    }

    public Parameter[] getOptionalParameters() {
        ArrayList<Parameter> list = new ArrayList<>();

        for (Parameter p : this) {
            if (p.isOptional()) {
                list.add(p);
            }
        }

        return list.toArray(new Parameter[0]);
    }

    public ReferenceParameter getReferenceParameter() {
        return (ReferenceParameter) getChild(0);
    }

    public int getNumReturnParameters() {
        return returnParameters.getNumVisibleChildren();
    }

    public void addReturnParameter(String type) {
        Parameter p = Parameter.decodeStatement(this,
            type + " ret" + (returnParameters.getNumVisibleChildren() + 1), getLocationIn().asNew(),
            true, false, true);
        p.setForceOriginalName(true);
        p.validateType();
        p.setIsValueReference(true);

        returnParameters.addChild(p);
    }

    public Value[] getReturnTypes() {
        Value[] types = new Value[getNumReturnParameters()];

        for (int i = 0; i < returnParameters.getNumVisibleChildren(); i++) {
            types[i] = returnParameters.getVisibleChild(i);
        }

        return types;
    }

    @Override
    public Parameter getParameter(int parameterIndex) {
        Parameter param = super.getParameter(parameterIndex);

        if (param == null && returnParameters.getNumVisibleChildren() > parameterIndex
            - getNumVisibleChildren()) {
            param = returnParameters.getVisibleChild(parameterIndex - getNumVisibleChildren());
        }

        return param;
    }

    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        return result;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public FlatParameterList clone(Node temporaryParent, Location locationIn, boolean cloneChildren,
        boolean cloneAnnotations) {
        FlatParameterList node = new FlatParameterList(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public FlatParameterList cloneTo(FlatParameterList node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link FlatParameterList} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public FlatParameterList cloneTo(FlatParameterList node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.returnParameters = returnParameters.clone(node, getLocationIn().asNew(), true, true);

        return node;
    }

    /**
     * Test the FlatParameterList class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }

    /**
     * ParameterList extension that represents the extraneous return values that the method returns.
     *
     * @author Braden Steffaniak
     * @since v0.2.42 Dec 26, 2014 at 4:14:42 PM
     * @version v0.2.42 Dec 26, 2014 at 4:14:42 PM
     */
    public static class ReturnParameterList extends ParameterList<Parameter> {
        public ReturnParameterList(Node temporaryParent, Location locationIn) {
            super(temporaryParent, locationIn);

            slaughterEveryLastChild();
        }

        @Override
        public int getParameterOffset() {
            return 0;
        }

        /**
         * @see Node#clone(Node, Location, boolean)
         */
        @Override
        public ReturnParameterList clone(Node temporaryParent, Location locationIn,
            boolean cloneChildren, boolean cloneAnnotations) {
            ReturnParameterList node = new ReturnParameterList(temporaryParent, locationIn);

            return cloneTo(node, cloneChildren, cloneAnnotations);
        }

        /**
         * @see Node#cloneTo(Node)
         */
        public ReturnParameterList cloneTo(ReturnParameterList node) {
            return cloneTo(node, true, true);
        }

        /**
         * Fill the given {@link ReturnParameterList} with the data that is in the specified node.
         *
         * @param node The node to copy the data into.
         * @return The cloned node.
         */
        public ReturnParameterList cloneTo(ReturnParameterList node, boolean cloneChildren,
            boolean cloneAnnotations) {
            super.cloneTo(node, cloneChildren, cloneAnnotations);

            return node;
        }
    }
}


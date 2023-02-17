package flat.tree;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;
import flat.util.StringUtils;

/**
 * {@link IValue} extension that represents
 *
 * @author Braden Steffaniak
 */
public class ArrayBracketOverload extends IValue implements ShorthandAccessible {
    private boolean twoWayBinding;
    private volatile boolean decoded;

    private String accessorValue;

    /**
     * @see Node#Node(Node, Location)
     */
    public ArrayBracketOverload(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    @Override
    public synchronized boolean alreadyDecoded() {
        return decoded;
    }

    @Override
    public boolean isTwoWayBinding() {
        return twoWayBinding;
    }

    @Override
    public void setTwoWayBinding(boolean twoWayBinding) {
        this.twoWayBinding = twoWayBinding;
    }

    @Override
    public String getShorthandAccessor() {
        return accessorValue;
    }

    @Override
    public void setShorthandAccessor(String shorthand) {
        this.accessorValue = shorthand;
    }

    public LocalDeclaration getIndexValue() {
        return (LocalDeclaration) getChild(0);
    }

    @Override
    public int getNumDefaultChildren() {
        return super.getNumDefaultChildren() + 1;
    }

    @Override
    public void addChild(Node node) {
        if (getNumChildren() == getNumDefaultChildren()) {
            getParentClass().addChild(node);
        } else {
            super.addChild(node);
        }
    }

    /**
     * Decode the given statement into a {@link ArrayBracketOverload} instance, if
     * possible. If it is not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li></li>
     * 	<li></li>
     * 	<li></li>
     * </ul>
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  {@link ArrayBracketOverload} instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a {@link ArrayBracketOverload}.
     */
    public static ArrayBracketOverload decodeStatement(Node parent, String statement, Location location, boolean require) {
        ArrayBracketOverload n = new ArrayBracketOverload(parent, location);

        statement = n.parseModifiers(statement);

        if (statement.startsWith("this[")) {
            int end = StringUtils.findEndingMatch(statement, "this".length(), '[', ']');

            if (end > 1) {
                String original = statement;
                String contents = statement.substring("this[".length(), end).trim();

                LocalDeclaration indexValue = LocalDeclaration.decodeStatement(n, contents, location.asNew(), require);

                n.addChild(indexValue);

                int index = n.getShorthandAccessorIndex(statement);

                if (index > 0) {
                    statement = statement.substring(0, index - (n.twoWayBinding ? 1 : 0)).trim();
                }

                String rest = statement.substring(end + 1).trim();

                if (rest.length() > 0) {
                    if (!rest.startsWith("->")) {
                        SyntaxMessage.error("Invalid array bracket overload '" + original + "'", n);
                    }

                    String type = rest.substring(2).trim();

                    n.setType(type);
                }

                return n;
            }
        }

        return null;
    }

    @Override
    public FlatMethodDeclaration addDefaultAccessor() {
        ArrayAccessorMethod method = ArrayAccessorMethod.decodeStatement(this, "get", getLocationIn(), true);

        Return returned = Return.decodeStatement(method, "return null", getLocationIn(), true);

        returned.getValueNode().replaceWith(generateDefaultValue(returned, getLocationIn()));

        method.addChild(returned);

        addChild(method);

        return method;
    }

    @Override
    public FlatMethodDeclaration addDefaultMutator() {
        ArrayMutatorMethod method = ArrayMutatorMethod.decodeStatement(this, "set", getLocationIn(), true);

        addChild(method);

        return method;
    }

    @Override
    public void addDisabledAccessor() {
        ArrayAccessorMethod method = ArrayAccessorMethod.decodeStatement(this, "no get", getLocationIn(), true);

        Value type = getArrayAccessorMethod();

        method.setType(type);

        addChild(method);
    }

    @Override
    public void addDisabledMutator() {
        ArrayMutatorMethod method = ArrayMutatorMethod.decodeStatement(this, "no set", getLocationIn(), true);

        Value type = getArrayAccessorMethod();

        method.setType(type);
        method.getParameter(1).setType(type);

        addChild(method);
    }

    @Override
    public boolean containsAccessorMethod() {
        return getArrayAccessorMethod() != null;
    }

    public ArrayAccessorMethod getArrayAccessorMethod() {
        return getParentClass().getArrayAccessorMethod();
    }

    @Override
    public boolean containsMutatorMethod() {
        return getArrayMutatorMethod() != null;
    }

    public ArrayMutatorMethod getArrayMutatorMethod() {
        return getParentClass().getArrayMutatorMethod();
    }

    @Override
    public synchronized BodyMethodDeclaration decodeShorthandAccessor() {
        decoded = true;
        ArrayAccessorMethod method = ArrayAccessorMethod.decodeStatement(this, "get", getLocationIn(), true);

        Value type = SyntaxTree.decodeValue(method, accessorValue, getLocationIn(), true).getReturnedNode();
        type.onAfterDecoded();
        method.setType(type);

        return method;
    }

    @Override
    public BodyMethodDeclaration decodeShorthandMutator(Value context) {
        ArrayMutatorMethod method = ArrayMutatorMethod.decodeStatement(this, "set", getLocationIn(), true);

        Value type = getArrayAccessorMethod();

        method.setType(type);
        method.getParameter(1).setType(type);

        return method;
    }

    @Override
    public boolean isWithinStaticContext() {
        return false;
    }

    /**
     * @see VariableDeclaration#validate(int)
     */
    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        if (phase == SyntaxTree.PHASE_METHOD_CONTENTS) {
            decodeArrowBinding();
        }

        return result;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ArrayBracketOverload clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ArrayBracketOverload node = new ArrayBracketOverload(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ArrayBracketOverload cloneTo(ArrayBracketOverload node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ArrayBracketOverload} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ArrayBracketOverload cloneTo(ArrayBracketOverload node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.twoWayBinding = twoWayBinding;
        node.decoded = decoded;
        node.accessorValue = accessorValue;

        return node;
    }

    @Override
    public String toString() {
        return "this[" + getIndexValue().generateFlatInput() + "] => " + accessorValue;
    }

    /**
     * Test the {@link ArrayBracketOverload} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
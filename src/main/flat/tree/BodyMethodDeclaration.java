package flat.tree;

import flat.Flat;
import flat.TestContext;
import flat.ValidationResult;
import flat.util.Location;
import flat.util.SyntaxUtils;

/**
 * Declaration extension that represents the declaration of a method
 * node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 *
 * @author Braden Steffaniak
 * @since v0.2.21 Jul 30, 2014 at 1:45:00 PM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class BodyMethodDeclaration extends FlatMethodDeclaration {
    private boolean parsedConvertedContext;

    /**
     * Instantiate and initialize default data.
     *
     * @see Node#Node(Node, Location)
     */
    public BodyMethodDeclaration(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);

        Scope scope = new Scope(this, locationIn.asNew());

        setScope(scope);
    }

    @Override
    public String getType(boolean checkCast) {
        if (super.getType(checkCast) == null) {
            if (shorthandAction != null) {
                decodeShorthandAction();
            } else if (genericOverload != null) {
                genericOverload.getType(checkCast);
            }
        }

        return super.getType(checkCast);
    }

    /**
     * @see Node#getNumDefaultChildren()
     */
    @Override
    public int getNumDefaultChildren() {
        return super.getNumDefaultChildren() + 1;
    }

    /**
     * @see Node#getScope()
     */
    @Override
    public Scope getScope() {
        return (Scope) getChild(super.getNumDefaultChildren() + 0);
    }

    /**
     * Get whether or not the specified MethodDeclaration contains a Body.
     *
     * @return Whether or not the specified MethodDeclaration contains a
     * Body.
     */
    public boolean containsBody() {
        return true;
    }

    @Override
    public boolean isInstance() {
        return super.isInstance() || getName().equals(Constructor.IDENTIFIER) && getParentClass().isPropertyTrue("functionMap");
    }

    /**
     * Decode the given statement into a Method instance, if
     * possible. If it is not possible, this method returns null.
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li>public findPerson(String name, Int age) -&gt; Person</li>
     * 	<li>private calculateArea(Int width, Int height) -&gt; Int</li>
     * 	<li>public doNothing()</li>
     * </ul>
     *
     * @param parent    The parent node of the statement.
     * @param statement The statement to try to decode into a
     *                  Method instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes
     *                  wrong.
     * @return The generated node, if it was possible to translated it
     * into a Method.
     */
    public static BodyMethodDeclaration decodeStatement(Node parent, String statement, Location location, boolean require) {
        FlatMethodDeclaration method = FlatMethodDeclaration.decodeStatement(parent, statement, location, false);

        if (method != null) {
            BodyMethodDeclaration n = new BodyMethodDeclaration(parent, location);

            method.cloneTo(n);
            n.getObjectReference(true);
            n.setLocationIn(location);
            n.uniqueID = 1;

            if (!n.getParentClass().isPropertyTrue("functionMap")) {
                n.addDefaultParameterInitializations();
            }

            if (n.getParentClass() instanceof Trait) {
                return new InterfaceMethodDeclaration(parent, location, n);
            }

            return n;
        }

        return null;
    }

    /**
     * Generate a Method with the given parent and location for
     * temporary use.
     *
     * @param parent     The node to set as the Method's parent.
     * @param locationIn The location to set as the Method's location.
     * @return The generated temporary Method.
     */
    public static BodyMethodDeclaration generateTemporaryMethod(Node parent, Location locationIn) {
        String name = parent.getParentClass(true).generateTemporaryMethodName();

        BodyMethodDeclaration methodDeclaration = decodeStatement(parent, name + "()", locationIn, true);

        return methodDeclaration;
    }

    public static BodyMethodDeclaration generateTemporaryHierarchy(Flat controller) {
        ClassDeclaration c = ClassDeclaration.generateTemporaryHierarchy(controller);

        BodyMethodDeclaration method = generateTemporaryMethod(c, Location.INVALID);
        c.addChild(method);

        return method;
    }

    public void moveShorthandActionToEnd() {
        if (usedShorthandAction) {
            getScope().addChild(getScope().getFirstStatement().detach());
        }
    }

    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        if (phase >= SyntaxTree.PHASE_METHOD_CONTENTS) {
            convertFunctionContents();

            if (phase == SyntaxTree.PHASE_METHOD_CONTENTS && genericOverload == null) {
                moveShorthandActionToEnd();
            }

            if (phase == SyntaxTree.PHASE_PRE_GENERATION) {
                SyntaxTree.validateNodes(getScope(), SyntaxTree.PHASE_METHOD_CONTENTS);
            }
        }

        return result;
    }

    public FlatMethodDeclaration getConversionTarget() {
        FlatMethodDeclaration overload = genericOverload;

        while (overload != null && overload.genericOverload != null) {
            overload = overload.genericOverload;
        }

        return overload;
    }

    public FlatMethodDeclaration getConversionTargetContext() {
        return getConversionTarget();
    }

    public boolean doesConvertToPrimitive() {
        return genericOverload != null;
    }

    public boolean convertFunctionContents() {
        if (!Flat.PRIMITIVE_OVERLOADS) {
            return false;
        }
        if (!parsedConvertedContext && doesConvertToPrimitive() && getConversionTargetContext().isInTree() && getConversionTarget().getScope() != null) {
            SyntaxUtils.parseConvertedContentsTo(getConversionTarget().getScope(), getConversionTargetContext(), this, this);

            parsedConvertedContext = true;

            return true;
        }

        return false;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public BodyMethodDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        BodyMethodDeclaration node = new BodyMethodDeclaration(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    public BodyMethodDeclaration cloneTo(BodyMethodDeclaration node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        for (DefaultParameterInitialization param : node.getScope().getDefaultParameterInitializations()) {
            param.parameter = node.getParameter(param.parameter.getIndex());
        }

        return node;
    }

    /**
     * Test the MethodDeclaration class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
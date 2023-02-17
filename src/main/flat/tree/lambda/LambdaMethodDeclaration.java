package flat.tree.lambda;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.*;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;

/**
 * {@link BodyMethodDeclaration} extension that represents
 *
 * @author Braden Steffaniak
 */
public class LambdaMethodDeclaration extends BodyMethodDeclaration {
    private int unnamedParameterPosition = 0;

    public boolean isInstance = false;

    public Closure closure;
    public ClosureContext context;
    public ClosureContextDeclaration contextDeclaration;

    public int index;

    public MethodCall methodCall;

    public Scope scope;

    /**
     * @see Node#Node(Node, Location)
     */
    public LambdaMethodDeclaration(Node temporaryParent, Location locationIn, Scope scope) {
        super(temporaryParent, locationIn);

        this.scope = scope;
        context = new ClosureContext(this, locationIn);

        context.id = getFileDeclaration().registerClosureContext(context);
    }

    public ClassDeclaration getContextDeclaringClass() {
        return methodCall != null && methodCall.isInTree() ? methodCall.getDeclaringClass() : getParentClass();
    }

    public ClosureDeclaration getCorrespondingClosureDeclaration() {
        if (isDecodingContents()) {
            return (ClosureDeclaration) methodCall.getFlatMethod().getParameter(index);
        }

        return closure.closureDeclaration;//(ClosureDeclaration)methodCall.getFlatMethod().getParameter(methodCall.getArgumentList().getVisibleIndex(closure));
    }

    @Override
    public boolean isWithinStaticContext() {
        return contextDeclaration.isWithinStaticContext();
    }

    @Override
    public boolean doesConvertToPrimitive() {
        return false;
    }

    @Override
    public boolean isUserMade(boolean checkAncestor) {
        return false;
    }

    public boolean isDecodingContents() {
        return closure == null;
    }

    @Override
    public boolean isInstance() {
        return isInstance;
    }

    public Parameter getNextUnnamedParameter() {
        if (getParameterList().getNumParameters() <= unnamedParameterPosition) {
            SyntaxMessage.error("Too many unnamed lambda expression parameters used", this);

            return null;
        }

        return getParameterList().getParameter(unnamedParameterPosition);
    }

    public void updateUnnamedParameterPosition() {
        unnamedParameterPosition++;
    }

    @Override
    public VariableDeclaration searchVariable(Node parent, Scope scope, String name, boolean checkAncestors) {
        VariableDeclaration var = super.searchVariable(parent, scope, name, checkAncestors);

        if (var != null) {
            return var;
        }

        var = this.scope.searchVariable(parent, scope, name, checkAncestors);

        if (var != null) {
            return context.addDeclaration(var);
        }

        return null;
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

        if (methodCall == null) {
//			detach();

            result.skipCycle = true;
        }

        return result;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public LambdaMethodDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        LambdaMethodDeclaration node = new LambdaMethodDeclaration(temporaryParent, locationIn, scope);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public LambdaMethodDeclaration cloneTo(LambdaMethodDeclaration node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link LambdaMethodDeclaration} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public LambdaMethodDeclaration cloneTo(LambdaMethodDeclaration node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.methodCall = methodCall;
        node.isInstance = isInstance;
        node.context = context;
        node.contextDeclaration = contextDeclaration;
        node.scope = scope;
        node.unnamedParameterPosition = unnamedParameterPosition;
        node.closure = closure;

        return node;
    }

    /**
     * Test the {@link LambdaMethodDeclaration} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
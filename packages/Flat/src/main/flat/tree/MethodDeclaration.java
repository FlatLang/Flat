package flat.tree;

import flat.TestContext;
import flat.error.SyntaxMessage;
import flat.tree.variables.VariableDeclaration;
import flat.util.Bounds;
import flat.util.Location;
import flat.util.StringUtils;

/**
 * InstanceDeclaration extension that represents the declaration of a method node type.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 9:10:53 PM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public abstract class MethodDeclaration extends InstanceDeclaration implements CallableMethod {
    /**
     * @see InstanceDeclaration#InstanceDeclaration(Node, Location)
     */
    public MethodDeclaration(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);

        ParameterList<Value> parameterList = new ParameterList<Value>(this, locationIn.asNew());

        addChild(parameterList, this);
    }

    public String getFunctionReferenceType() {
        String params = "";

        for (int i = 0; i < getParameterList().getNumParameters(); i++) {
            if (i > 0) {
                params += ", ";
            }

            Value param = getParameter(i);

            params += param.generateFlatType(param);
        }

        String returnType = "";

        if (getType() != null) {
            returnType += " -> " + generateFlatType(this);
        }

        return getName() + "(" + params + ")" + returnType;
    }

    /**
     * Get whether or not the specified MethodDeclaration contains a body or not. The default is
     * false.
     *
     * @return Whether or not the specified MethodDeclaration contains a body.
     */
    public boolean containsBody() {
        return false;
    }

    /**
     * @see Node#getNumDefaultChildren()
     */
    @Override
    public int getNumDefaultChildren() {
        return super.getNumDefaultChildren() + 1;
    }

    /**
     * @see CallableMethod#isInstance()
     */
    @Override
    public boolean isInstance() {
        return !isStatic();
    }

    /**
     * @see VariableDeclaration#isExternal()
     */
    @Override
    public boolean isExternal() {
        // If the MethodDeclaration is decoding for the
        // ExternalMethodDeclaration.
        if (getParent() instanceof ExternalMethodDeclaration) {
            return true;
        }

        return super.isExternal();
    }

    /**
     * @see VariableDeclaration#setExternal(boolean)
     */
    @Override
    public void setExternal(boolean external) {
        super.setExternal(external);

        setStatic(true);
        setVisibility(PUBLIC);
    }

    /**
     * The the ParameterList that represents the parameters of the method.<br>
     * For example:<br>
     * <blockquote>
     * 
     * <pre>
     * public void methodName(int age, String name);
     * </pre>
     * 
     * </blockquote> In the previous statement, the data within the parenthesis are the parameters
     * of the method. The ParameterList returned by this method would contain a node for each of the
     * parameter specified, in the correct order from left to right.
     *
     * @return The ParameterList that represents the parameters of the method.
     */
    public ParameterList<Parameter> getParameterList() {
        return (ParameterList<Parameter>) getChild(super.getNumDefaultChildren());
    }

    /**
     * Get the Parameter that the given index represents. The parameters are ordered from left to
     * right, 0 being the first.<br>
     * <br>
     * For example: <blockquote>
     * 
     * <pre>
     * public void run(int a, int b, int c)
     * {
     * 	...
     * }
     * </pre>
     * 
     * </blockquote> If you were to call getParameter(2) on the method above, you would receive the
     * c Parameter.
     *
     * @param parameterIndex The index parameter to get.
     * @return The Parameter at the given index.
     */
    public Value getParameter(int parameterIndex) {
        return getParameterList().getParameter(parameterIndex);
    }

    /**
     * Find the String representing the signature of the bodyless method that is currently being
     * decoded from the given statement String.
     *
     * @param statement The String containing the method signature.
     * @param remove The Bounds to remove from the statement.
     * @return The signature for the bodyless method to decode.
     */
    public static String findMethodSignature(String statement, Bounds remove) {
        int paren = statement.indexOf('(');

        if (paren < 0) {
            return null;
        }

        String signature = FlatMethodDeclaration.findMethodSignature(statement);

        if (!remove.isValid()) {
            return null;
        }

        paren -= remove.length();

        return remove.extractPreString(statement) + remove.extractPostString(statement);
    }

    /**
     * Decode the given parameters that were declared for the Method.
     *
     * @param parameterList The statement that contains the parameters separated by commas.
     * @param require Whether or not a successful decode is required.
     * @return Whether or not the parameters were decoded correctly.
     */
    public boolean decodeParameters(String parameterList, boolean require) {
        if (parameterList.length() <= 0) {
            return true;
        }

        String parameters[] = StringUtils.splitCommas(parameterList);

        Location location = new Location(getLocationIn());

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].length() > 0) {
                Parameter param = Parameter.decodeStatement(this, parameters[i], location, require);

                if (param == null) {
                    return SyntaxMessage.queryError("Incorrect parameter definition", this,
                        require);
                }

                getParameterList().addChild(param);
            } else {
                SyntaxMessage.error("Expected a parameter definition", this);
            }
        }

        return true;
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public MethodDeclaration cloneTo(MethodDeclaration node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link MethodDeclaration} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public MethodDeclaration cloneTo(MethodDeclaration node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the MethodDeclaration class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}


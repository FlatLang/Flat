package flat.tree;

import flat.TestContext;
import flat.tree.annotations.FinalAnnotation;
import flat.tree.annotations.VarAnnotation;
import flat.tree.variables.FieldDeclaration;
import flat.tree.variables.Variable;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;

/**
 * {@link FlatMethodDeclaration} extension that represents
 *
 * @author Braden Steffaniak
 */
public class ClosureVariable extends Variable {
    public FunctionType type;

    /**
     * @see Node#Node(Node, Location)
     */
    public ClosureVariable(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    @Override
    public void decodeGenericTypeArguments(String params) {

    }

    /**
     * Decode the given statement into a {@link ClosureVariable} instance, if
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
     *                  {@link ClosureVariable} instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a {@link ClosureVariable}.
     */
    public static VariableDeclaration decodeStatement(Node parent, String statement, Location location, boolean require) {
        FieldDeclaration temp = new FieldDeclaration(parent, location);
        statement = temp.parseModifiers(statement);

        FlatMethodDeclaration method = FlatMethodDeclaration.decodeStatement(parent, statement, location, false);

        if (method != null) {
            VarAnnotation var = (VarAnnotation) temp.getAnnotationOfType(VarAnnotation.class, false, true);
            FinalAnnotation fin = (FinalAnnotation) temp.getAnnotationOfType(FinalAnnotation.class, false, true);

            if (var != null || fin != null && (fin.getAliasUsed() == null || !fin.getAliasUsed().equals("final"))) {
                Node scopeAncestor = parent.getAncestorWithScope();

                VariableDeclaration node;

                if (scopeAncestor != null) {
                    node = new LocalDeclaration(scopeAncestor, location);
                } else {
                    node = new FieldDeclaration(parent, location);
                }

                node.setName(method.getName());
                node.setType(method.getFunctionReferenceType());
                node.inheritAnnotations(temp);

                String[] parameterNames = new String[method.getParameterList().getNumParameters()];

                for (int i = 0; i < parameterNames.length; i++) {
                    parameterNames[i] = method.getParameter(i).getName();
                }

                ((FunctionType) node.getTypeObject()).parameterNames = parameterNames;

                node.inheritAnnotations(method);

                return node;
            }
        }

        return null;
    }

    @Override
    public Type getTypeObject() {
        return type;
    }

    /**
     * @see Value#setDataType(byte)
     */
    @Override
    public void setDataType(byte type) {

    }

    @Override
    public void setTypeValue(String type) {
        this.type = FunctionType.parse(this, type);
    }

    @Override
    public String getType(boolean checkCast) {
        if (type == null) {
            return super.getType(checkCast);
        }

        return type.closure.generateFlatInput().toString();
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ClosureVariable clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ClosureVariable node = new ClosureVariable(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ClosureVariable cloneTo(ClosureVariable node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ClosureVariable} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ClosureVariable cloneTo(ClosureVariable node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        node.type = type;

        return node;
    }

    /**
     * Test the {@link ClosureVariable} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
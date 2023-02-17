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
public class ExtensionMethodDeclaration extends BodyMethodDeclaration {
    /**
     * @see Node#Node(Node, Location)
     */
    public ExtensionMethodDeclaration(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * @see Node#Node(Node, Location)
     */
    public ExtensionMethodDeclaration(Node temporaryParent, Location locationIn, BodyMethodDeclaration method) {
        super(temporaryParent, locationIn);

        int oldId = uniqueID;

        method.cloneTo(this);

        uniqueID = oldId;
    }

    /**
     * Decode the given statement into a {@link ExtensionMethodDeclaration} instance, if
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
     *                  {@link ExtensionMethodDeclaration} instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a {@link ExtensionMethodDeclaration}.
     */
    public static ExtensionMethodDeclaration decodeStatement(Node parent, String statement, Location location, boolean require) {
        if (parent.getParentClass(true) instanceof ExtensionDeclaration) {
            BodyMethodDeclaration method = BodyMethodDeclaration.decodeStatement(parent, statement, location, require);

            if (method != null && method.getParameterList().getNumParameters() > 0 && method.getParameter(0).getName().equals("this")) {
                ExtensionMethodDeclaration n = new ExtensionMethodDeclaration(parent, location);

                method.cloneTo(n, true, true);

                Parameter type = n.getParameterList().getParameter(0);

                n.getParameterList().getReferenceParameter().setType(type);

                type.detach();

                n.objectReference = new ObjectReference(n);

                return n;
            }
        }

        return null;
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
    public ExtensionMethodDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ExtensionMethodDeclaration node = new ExtensionMethodDeclaration(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ExtensionMethodDeclaration cloneTo(ExtensionMethodDeclaration node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ExtensionMethodDeclaration} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ExtensionMethodDeclaration cloneTo(ExtensionMethodDeclaration node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link ExtensionMethodDeclaration} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
package flat.tree;

import flat.TestContext;
import flat.tree.variables.FieldDeclaration;
import flat.util.Bounds;
import flat.util.Location;
import flat.util.Patterns;
import flat.util.Regex;

/**
 * {@link Node} extension that represents
 *
 * @author Braden Steffaniak
 */
public class ExtensionFieldDeclaration extends FieldDeclaration {
    public ClassDeclaration instanceClass;

    /**
     * @see Node#Node(Node, Location)
     */
    public ExtensionFieldDeclaration(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * @see Node#Node(Node, Location)
     */
    public ExtensionFieldDeclaration(Node temporaryParent, Location locationIn, BodyMethodDeclaration method) {
        super(temporaryParent, locationIn);
    }

    /**
     * Decode the given statement into a {@link ExtensionFieldDeclaration} instance, if
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
     *                  {@link ExtensionFieldDeclaration} instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a {@link ExtensionFieldDeclaration}.
     */
    public static ExtensionFieldDeclaration decodeStatement(Node parent, String statement, Location location, boolean require) {
        if (parent.getParentClass(true) instanceof ExtensionDeclaration) {
            Bounds bounds = Regex.boundsOf(statement, Patterns.EXTENSION_IDENTIFIER);

            if (bounds.getStart() > 0) {
                int eqIndex = statement.indexOf('=');
                int dotIndex = statement.indexOf('.', bounds.getStart());

                if (dotIndex < eqIndex || eqIndex < 0) {
                    String type = statement.substring(bounds.getStart(), dotIndex).trim();

                    statement = statement.substring(0, bounds.getStart()).trim() + " " + statement.substring(dotIndex + 1).trim();

                    FieldDeclaration field = FieldDeclaration.decodeStatement(parent, statement, location, require);

                    if (field != null) {
                        ExtensionFieldDeclaration n = new ExtensionFieldDeclaration(parent, location);

                        field.cloneTo(n, true, true);
						
						/*Parameter type = (Parameter)n.getParameterList().getParameter(0).detach();
						
						n.getParameterList().getReferenceParameter().setType(type);
						*/

                        n.instanceClass = parent.getFileDeclaration().getImportedClass(n, type);

                        return n;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public BodyMethodDeclaration decodeShorthandAccessor() {
        BodyMethodDeclaration method = super.decodeShorthandAccessor();

        method.getParameterList().getReferenceParameter().setType(instanceClass);

        return method;
    }

    @Override
    public BodyMethodDeclaration decodeShorthandMutator() {
        BodyMethodDeclaration method = super.decodeShorthandMutator();

        method.getParameterList().getReferenceParameter().setType(instanceClass);

        return method;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ExtensionFieldDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ExtensionFieldDeclaration node = new ExtensionFieldDeclaration(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ExtensionFieldDeclaration cloneTo(ExtensionFieldDeclaration node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ExtensionFieldDeclaration} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ExtensionFieldDeclaration cloneTo(ExtensionFieldDeclaration node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link ExtensionFieldDeclaration} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
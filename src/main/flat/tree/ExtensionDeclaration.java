package flat.tree;

import flat.TestContext;
import flat.util.Location;
import flat.util.SyntaxUtils;

/**
 * {@link ClassDeclaration} extension that represents
 *
 * @author Braden Steffaniak
 */
public class ExtensionDeclaration extends ClassDeclaration {
    public static final String IDENTIFIER = "extension";

    /**
     * @see Node#Node(Node, Location)
     */
    public ExtensionDeclaration(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * Decode the given statement into a {@link ExtensionDeclaration} instance, if
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
     *                  {@link ExtensionDeclaration} instance.
     * @param location  The location of the statement in the source code.
     * @param require   Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * into a {@link ExtensionDeclaration}.
     */
    public static ExtensionDeclaration decodeStatement(Node parent, String statement, Location location, boolean require) {
        int index = SyntaxUtils.findStringInBaseScope(statement, IDENTIFIER);

        if (index >= 0) {
            statement = statement.substring(0, index) + statement.substring(index + IDENTIFIER.length());

            ClassData data = new ClassData(false, false, true);

            ClassDeclaration clazz = decodeStatement(parent, statement, location, require, data);

            if (clazz != null) {
                ExtensionDeclaration n = new ExtensionDeclaration(parent, location);

                clazz.cloneTo(n);
                n.setExtendedClass(null);

                return n;
            }
        }

        return null;
    }

    @Override
    public void addDefaultConstructor() {

    }

    @Override
    public void addDefaultDestructor() {

    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ExtensionDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ExtensionDeclaration node = new ExtensionDeclaration(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ExtensionDeclaration cloneTo(ExtensionDeclaration node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ExtensionDeclaration} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ExtensionDeclaration cloneTo(ExtensionDeclaration node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link ExtensionDeclaration} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
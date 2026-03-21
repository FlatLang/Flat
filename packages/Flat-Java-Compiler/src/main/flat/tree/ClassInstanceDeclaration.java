package flat.tree;

import flat.TestContext;
import flat.tree.annotations.FinalAnnotation;
import flat.tree.variables.FieldDeclaration;
import flat.util.Location;

/**
 * {@link FieldDeclaration} extension that represents
 *
 * @author Braden Steffaniak
 */
public class ClassInstanceDeclaration extends FieldDeclaration {
    /**
     * @see Node#Node(Node, Location)
     */
    public ClassInstanceDeclaration(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);

        setType("Class");// temporaryParent.getProgram().getClassDeclaration("flat/meta/Class"));
        setName("class");
        addAnnotation(new FinalAnnotation(this, locationIn));
        setVisibility(VISIBLE);
    }

    @Override
    public void onRemoved(Node parent) {
        super.onRemoved(parent);

        // silently remove
        setTemporaryParent(parent);
    }

    @Override
    public boolean isUserMade(boolean checkAncestor) {
        return false;
    }

    @Override
    public boolean isTangible() {
        return false;
    }

    /**
     * Decode the given statement into a {@link ClassInstanceDeclaration} instance, if possible. If
     * it is not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * <li></li>
     * <li></li>
     * <li></li>
     * </ul>
     *
     * @param parent The parent node of the statement.
     * @param statement The statement to try to decode into a {@link ClassInstanceDeclaration}
     *        instance.
     * @param location The location of the statement in the source code.
     * @param require Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it into a
     *         {@link ClassInstanceDeclaration}.
     */
    public static ClassInstanceDeclaration decodeStatement(Node parent, String statement,
        Location location, boolean require) {


        return null;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ClassInstanceDeclaration clone(Node temporaryParent, Location locationIn,
        boolean cloneChildren, boolean cloneAnnotations) {
        ClassInstanceDeclaration node = new ClassInstanceDeclaration(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ClassInstanceDeclaration cloneTo(ClassInstanceDeclaration node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ClassInstanceDeclaration} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ClassInstanceDeclaration cloneTo(ClassInstanceDeclaration node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link ClassInstanceDeclaration} class type to make sure everything is working
     * properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}


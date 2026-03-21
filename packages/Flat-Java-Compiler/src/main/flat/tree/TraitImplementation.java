package flat.tree;

import flat.TestContext;
import flat.error.SyntaxMessage;
import flat.tree.generics.GenericTypeArgumentList;
import flat.util.Location;
import flat.util.StringUtils;

/**
 * {@link Node} extension that represents
 *
 * @author Braden Steffaniak
 */
public class TraitImplementation extends IValue implements GenericCompatible {
    /**
     * @see Node#Node(Node, Location)
     */
    public TraitImplementation(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);

        GenericTypeArgumentList implementation =
            new GenericTypeArgumentList(this, locationIn.asNew());
        addChild(implementation, this);
    }

    /**
     * @see Node#getNumDefaultChildren()
     */
    @Override
    public int getNumDefaultChildren() {
        return super.getNumDefaultChildren() + 1;
    }

    /**
     * @see GenericCompatible#getGenericTypeArgumentList()
     */
    @Override
    public GenericTypeArgumentList getGenericTypeArgumentList() {
        return (GenericTypeArgumentList) getChild(super.getNumDefaultChildren() + 0);
    }

    /**
     * Decode the given statement into a {@link TraitImplementation} instance, if possible. If it is
     * not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * <li></li>
     * <li></li>
     * <li></li>
     * </ul>
     *
     * @param parent The parent node of the statement.
     * @param statement The statement to try to decode into a {@link TraitImplementation} instance.
     * @param location The location of the statement in the source code.
     * @param require Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it into a
     *         {@link TraitImplementation}.
     */
    public static TraitImplementation decodeStatement(Node parent, String statement,
        Location location, boolean require) {
        TraitImplementation n = new TraitImplementation(parent, location);

        int numWords = StringUtils.findNumWords(statement);

        if (numWords != 1) {
            SyntaxMessage.queryError("Invalid interface implementation", n, require);

            return null;
        }

        n.setType(statement, true, false);

        return n;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public TraitImplementation clone(Node temporaryParent, Location locationIn,
        boolean cloneChildren, boolean cloneAnnotations) {
        TraitImplementation node = new TraitImplementation(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public TraitImplementation cloneTo(TraitImplementation node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link TraitImplementation} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public TraitImplementation cloneTo(TraitImplementation node, boolean cloneChildren,
        boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    @Override
    public String toString() {
        return "trait " + getTypeClassName();
    }

    /**
     * Test the {@link TraitImplementation} class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}


package flat.tree;

import flat.TestContext;
import flat.util.Location;

/**
 * {@link VTable} extension that represents
 *
 * @author Braden Steffaniak
 */
public class ExtensionVTable extends VTable {
    /**
     * @see Node#Node(Node, Location)
     */
    public ExtensionVTable(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);

        TraitVTable table = new TraitVTable(this, Location.INVALID);

        addChild(table);
    }

    /**
     * @see Node#getNumDefaultChildren()
     */
    @Override
    public int getNumDefaultChildren() {
        return super.getNumDefaultChildren() + 1;
    }

    public TraitVTable getInterfaceVTable() {
        return (TraitVTable) getChild(super.getNumDefaultChildren() + 0);
    }

    /**
     * @see VTable#getVTableType()
     */
    @Override
    public String getVTableType() {
        return "Extension";
    }

    /**
     * @see VTable#getVirtualMethods()
     */
    @Override
    public FlatMethodDeclaration[] getVirtualMethods() {
        return getParentClass().getExtensionVirtualMethods();
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ExtensionVTable clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ExtensionVTable node = new ExtensionVTable(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ExtensionVTable cloneTo(ExtensionVTable node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ExtensionVTable} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ExtensionVTable cloneTo(ExtensionVTable node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link ExtensionVTable} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
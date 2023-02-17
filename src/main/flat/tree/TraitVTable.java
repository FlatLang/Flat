package flat.tree;

import flat.TestContext;
import flat.util.Location;

import java.util.ArrayList;

/**
 * {@link VTable} extension that represents
 *
 * @author Braden Steffaniak
 */
public class TraitVTable extends VTable {
    public static final String IDENTIFIER = "itable";

    public static final String TYPE = "flat_Interface_VTable";

    /**
     * @see Node#Node(Node, Location)
     */
    public TraitVTable(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);

        setTypeValue(TYPE);
        setDataType(VALUE);
    }

    /**
     * @see VTable#getVTableType()
     */
    @Override
    public String getVTableType() {
        return "Trait";
    }

    /**
     * @see VTable#getVirtualMethods()
     */
    @Override
    public FlatMethodDeclaration[] getVirtualMethods() {
        ArrayList<FlatMethodDeclaration> methods = new ArrayList<>();

        FlatMethodDeclaration[] interfaceMethods = getParentClass().getInterfaceVirtualMethods();

        for (FlatMethodDeclaration method : getProgram().getInterfaceMethods()) {
            FlatMethodDeclaration m = null;

            for (FlatMethodDeclaration method2 : interfaceMethods) {
                if (method2.getVirtualMethod() == method) {
                    m = method2;
                }
            }

            methods.add(m);
        }

        return methods.toArray(new FlatMethodDeclaration[0]);
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public TraitVTable clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        TraitVTable node = new TraitVTable(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public TraitVTable cloneTo(TraitVTable node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link TraitVTable} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public TraitVTable cloneTo(TraitVTable node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link TraitVTable} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
package flat.tree;

import flat.TestContext;
import flat.ValidationResult;
import flat.util.Location;

/**
 * @author Braden Steffaniak
 * @since v0.1 Mar 16, 2014 at 1:13:49 AM
 * @version v0.2.43 Jan 16, 2015 at 11:59:17 AM
 */
public abstract class VTable extends IIdentifier {
    public static final String IDENTIFIER = "vtable";

    public VTable(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);

        ClassDeclaration c = getParentClass();

        String type = c.getClassLocation().replace('/', '_').replace('.', '_') + '_'
            + getVTableType() + "_VTable";
        setType(type, true, false, false);

        setName(type + "_val", true);
    }

    /**
     * @see Node#validate(int)
     */
    @Override
    public ValidationResult validate(int phase) {
        ValidationResult result = super.validate(phase);

        if (result.skipValidation()) {
            return result;
        }

        if (phase != SyntaxTree.PHASE_CLASS_DECLARATION) {

        }

        return result;
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public VTable cloneTo(VTable node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link VTable} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public VTable cloneTo(VTable node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the VTable class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }

    public abstract String getVTableType();

    public abstract FlatMethodDeclaration[] getVirtualMethods();
}


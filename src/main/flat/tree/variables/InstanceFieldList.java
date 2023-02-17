package flat.tree.variables;

import flat.TestContext;
import flat.tree.Node;
import flat.tree.TypeList;
import flat.util.Location;

/**
 * {@link FieldList} extensions that contains all of the public Field
 * instances of a ClassDeclaration.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 10:00:50 PM
 * @version v0.2.29 Aug 29, 2014 at 3:17:45 PM
 */
public class InstanceFieldList extends TypeList<FieldDeclaration> {
    /**
     * @see Node#Node(Node, Location)
     */
    public InstanceFieldList(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public InstanceFieldList clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        InstanceFieldList node = new InstanceFieldList(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public InstanceFieldList cloneTo(InstanceFieldList node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link InstanceFieldList} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public InstanceFieldList cloneTo(InstanceFieldList node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the InstanceFieldList class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}

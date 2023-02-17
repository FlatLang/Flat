package flat.tree.variables;

import flat.TestContext;
import flat.tree.List;
import flat.tree.Node;
import flat.util.Location;

/**
 * {@link FieldList} extensions that contains all of the private Field
 * instances of a ClassDeclaration.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 10:00:50 PM
 * @version v0.2.29 Aug 29, 2014 at 3:17:45 PM
 */
public class StaticFieldList extends List {
    /**
     * @see Node#Node(Node, Location)
     */
    public StaticFieldList(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public StaticFieldList clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        StaticFieldList node = new StaticFieldList(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public StaticFieldList cloneTo(StaticFieldList node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link StaticFieldList} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public StaticFieldList cloneTo(StaticFieldList node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the StaticFieldList class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
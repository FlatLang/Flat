package flat.tree;

import flat.TestContext;
import flat.util.Location;

/**
 * Node extension that represents all of the external types that
 * were declared within a class.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 10:29:22 PM
 * @version v0.2.29 Aug 29, 2014 at 3:17:45 PM
 */
public class ExternalTypeList extends TypeList<ExternalType> {
    /**
     * @see Node#Node(Node, Location)
     */
    public ExternalTypeList(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * Get whether or not the ClassDeclaration contains an ExternalType with
     * the specified type name.<br>
     * <br>
     * For example:
     * <blockquote><pre>
     * public class ClassName
     * {
     * 	external type FILE;
     * }</pre></blockquote>
     * <br>
     * A call like: "<code>getType("FILE")</code>" would
     * return the ExternalType for the "<code>FILE</code>" external
     * type.
     *
     * @param typeName The name of the external type to search for.
     * @return Whether or not the ClassDeclaration contains the Method with
     * the specified name.
     */
    public boolean containsType(String typeName) {
        return getType(typeName) != null;
    }

    /**
     * Get the ClassDeclaration's ExternalType with the specified type.<br>
     * <br>
     * For example:
     * <blockquote><pre>
     * public class ClassName
     * {
     * 	external type FILE;
     * }</pre></blockquote>
     * <br>
     * A call like: "<code>getType("FILE")</code>" would
     * return the ExternalType for the "<code>FILE</code>" external
     * type.
     *
     * @param typeName The name of the external type to search for.
     * @return The ExternalType for the external type, if it exists.
     */
    public ExternalType getType(String typeName) {
        for (int i = 0; i < getNumVisibleChildren(); i++) {
            ExternalType type = (ExternalType) getVisibleChild(i);

            if (type.getType().equals(typeName)) {
                return type;
            }
        }

        return null;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ExternalTypeList clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ExternalTypeList node = new ExternalTypeList(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ExternalTypeList cloneTo(ExternalTypeList node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ExternalTypeList} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ExternalTypeList cloneTo(ExternalTypeList node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the ExternalTypeList class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
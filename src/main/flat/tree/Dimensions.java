package flat.tree;

import flat.TestContext;
import flat.util.Location;

/**
 * Node extension that contains all of the dimension attributes
 * for an array declaration.
 *
 * @author Braden Steffaniak
 * @since v0.2.5 May 19, 2014 at 12:09:41 AM
 * @version v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public class Dimensions extends Node {
    /**
     * @see Node#Node(Node, Location)
     */
    public Dimensions(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * @see Node#generateFlatInput(StringBuilder, boolean)
     */
    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren) {
        for (int i = 0; i < getNumChildren(); i++) {
            builder.append('[').append(getChild(i).generateFlatInput()).append(']');
        }

        return builder;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public Dimensions clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        Dimensions node = new Dimensions(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public Dimensions cloneTo(Dimensions node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link Dimensions} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public Dimensions cloneTo(Dimensions node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the Dimensions class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
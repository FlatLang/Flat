package flat.tree;

import flat.TestContext;
import flat.util.Location;

/**
 * Node extension that represents the initialization section of the
 * for loop. For instance: "for (int i = 0; i &lt; 10; i++)" the first
 * section containing "int i = 0" is the initialization section.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 5, 2014 at 10:01:46 PM
 * @version v0.2.26 Aug 6, 2014 at 2:48:50 PM
 */
public class LoopInitialization extends Node {
    /**
     * @see Node#Node(Node, Location)
     */
    public LoopInitialization(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public LoopInitialization clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        LoopInitialization node = new LoopInitialization(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public LoopInitialization cloneTo(LoopInitialization node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link LoopInitialization} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public LoopInitialization cloneTo(LoopInitialization node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the LoopInitialization class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
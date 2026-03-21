package flat.tree;

import flat.TestContext;
import flat.util.Location;

import java.util.ArrayList;

/**
 * {@link Node} extension that abstracts a general list type.
 *
 * @author Braden Steffaniak
 * @since v0.2.29 Aug 21, 2014 at 10:46:16 PM
 * @version v0.2.29 Aug 29, 2014 at 3:17:45 PM
 */
public class List extends Node {
    /**
     * Instantiate and initialize default data.
     */
    public List(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public ArrayList<Node> toArray() {
        ArrayList<Node> nodes = new ArrayList<>();

        for (int i = 0; i < getNumVisibleChildren(); i++) {
            nodes.add(getVisibleChild(i));
        }

        return nodes;
    }

    public int getVisibleIndex(Node node) {
        for (int i = 0; i < getNumVisibleChildren(); i++) {
            if (getVisibleChild(i) == node) {
                return i;
            }
        }

        return -1;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public List clone(Node temporaryParent, Location locationIn, boolean cloneChildren,
        boolean cloneAnnotations) {
        List node = new List(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public List cloneTo(List node) {
        return cloneTo(node, true, true);
    }

    public List cloneTo(List node, boolean cloneChildren) {
        return cloneTo(node, cloneChildren, true);
    }

    /**
     * Fill the given {@link List} with the data that is in the specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public List cloneTo(List node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the FieldList class type to make sure everything is working properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }

    public String toString() {
        String s = "";

        for (int i = 0; i < getNumVisibleChildren(); i++) {
            if (i > 0) {
                s += "\n";
            }

            s += getVisibleChild(i).toString();
        }

        return s;
    }
}


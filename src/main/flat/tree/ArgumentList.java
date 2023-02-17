package flat.tree;

import flat.TestContext;
import flat.util.Location;

import java.util.ArrayList;

/**
 * Node extension that keeps track of a set of arguments that
 * are used in an abstract manner.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jan 10, 2014 at 3:12:54 AM
 * @version v0.2.29 Aug 29, 2014 at 3:17:45 PM
 */
public class ArgumentList extends TypeList<Value> {
    /**
     * @see Node#Node(Node, Location)
     */
    public ArgumentList(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    /**
     * @see Node#generateFlatInput(StringBuilder, boolean)
     */
    @Override
    public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren) {
        for (int i = 0; i < getNumChildren(); i++) {
            if (i > 0) {
                builder.append(", ");
            }

            getChild(i).generateFlatInput(builder);
        }

        return builder;
    }

    /**
     * Get the types that the Argument list is providing for the
     * parameters.
     *
     * @return An array of Values that represent that types in the
     * argument list.
     */
    public Value[] getTypes() {
        ArrayList<Value> types = new ArrayList<>();

        for (int i = 0; i < getNumVisibleChildren(); i++) {
            Value child = ((Value) getVisibleChild(i)).getReturnedNode();

            types.add(child);
        }

        return types.toArray(new Value[0]);
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ArgumentList clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations) {
        ArgumentList node = new ArgumentList(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ArgumentList cloneTo(ArgumentList node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ArgumentList} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ArgumentList cloneTo(ArgumentList node, boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the ArgumentListNode class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * successful, null is returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}
package flat.tree.generics;

import flat.TestContext;
import flat.tree.GenericCompatible;
import flat.tree.IIdentifier;
import flat.tree.Node;
import flat.tree.Value;
import flat.util.Location;

import java.util.ArrayList;

/**
 * {@link Node} extension that represents
 *
 * @author Braden Steffaniak
 */
public class MethodGenericTypeParameter extends GenericTypeParameter {
    private ArrayList<Value> references;

    /**
     * @see Node#Node(Node, Location)
     */
    public MethodGenericTypeParameter(Node temporaryParent, Location locationIn) {
        super(temporaryParent, locationIn);
    }

    public boolean containsReference(Value value) {
        return references != null ? references.contains(value) : false;
    }

    public void addReference(Value argument) {
        references = references == null ? new ArrayList<>() : references;

        if (!references.contains(argument)) {
            references.add(argument);
        }
    }

    @Override
    public Value getTypeValue(GenericCompatible context) {
        // TODO: update this when defaultType is an actual Value type
        IIdentifier value = new IIdentifier(this, Location.INVALID);

        GenericTypeArgument arg = getCorrespondingArgument(context);

        if (arg != null) {
            value.setType(arg);
        } else {
            value.setType(getDefaultType());
        }

        return value;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public MethodGenericTypeParameter clone(Node temporaryParent, Location locationIn,
        boolean cloneChildren, boolean cloneAnnotations) {
        MethodGenericTypeParameter node =
            new MethodGenericTypeParameter(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public MethodGenericTypeParameter cloneTo(MethodGenericTypeParameter node) {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link MethodGenericTypeParameter} with the data that is in the specified
     * node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public MethodGenericTypeParameter cloneTo(MethodGenericTypeParameter node,
        boolean cloneChildren, boolean cloneAnnotations) {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link MethodGenericTypeParameter} class type to make sure everything is working
     * properly.
     *
     * @return The error output, if there was an error. If the test was successful, null is
     *         returned.
     */
    public static String test(TestContext context) {


        return null;
    }
}


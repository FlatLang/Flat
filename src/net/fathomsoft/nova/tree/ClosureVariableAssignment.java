package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * {@link Node} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class ClosureVariableAssignment extends Node
{
    public ClosureVariableDeclaration declaration;

    /**
     * @see Node#Node(Node, Location)
     */
    public ClosureVariableAssignment(Node temporaryParent, Location locationIn, ClosureVariableDeclaration declaration)
    {
        super(temporaryParent, locationIn);

        this.declaration = declaration;
    }

    @Override
    public boolean isUserMade(boolean checkAncestor)
    {
        return false;
    }

    /**
     * @see Node#clone(Node, Location, boolean)
     */
    @Override
    public ClosureVariableAssignment clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
    {
        ClosureVariableAssignment node = new ClosureVariableAssignment(temporaryParent, locationIn, declaration);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see Node#cloneTo(Node)
     */
    public ClosureVariableAssignment cloneTo(ClosureVariableAssignment node)
    {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link ClosureVariableAssignment} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public ClosureVariableAssignment cloneTo(ClosureVariableAssignment node, boolean cloneChildren, boolean cloneAnnotations)
    {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link ClosureVariableAssignment} class type to make sure everything
     * is working properly.
     *
     * @return The error output, if there was an error. If the test was
     * 		successful, null is returned.
     */
    public static String test(TestContext context)
    {


        return null;
    }
}
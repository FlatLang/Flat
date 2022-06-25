package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * {@link IValue} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class DefaultArgument extends IValue
{
    /**
     * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
     */
    public DefaultArgument(Node temporaryParent, Location locationIn)
    {
        super(temporaryParent, locationIn);
    }

    /**
     * Decode the given statement into a {@link DefaultArgument} instance, if
     * possible. If it is not possible, this method returns null.<br>
     * <br>
     * Example inputs include:<br>
     * <ul>
     * 	<li></li>
     * 	<li></li>
     * 	<li></li>
     * </ul>
     *
     * @param parent The parent node of the statement.
     * @param statement The statement to try to decode into a
     * 		{@link DefaultArgument} instance.
     * @param location The location of the statement in the source code.
     * @param require Whether or not to throw an error if anything goes wrong.
     * @return The generated node, if it was possible to translated it
     * 		into a {@link DefaultArgument}.
     */
    public static DefaultArgument decodeStatement(Node parent, String statement, Location location, boolean require)
    {


        return null;
    }

    /**
     * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
     */
    @Override
    public DefaultArgument clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
    {
        DefaultArgument node = new DefaultArgument(temporaryParent, locationIn);

        return cloneTo(node, cloneChildren, cloneAnnotations);
    }

    /**
     * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
     */
    public DefaultArgument cloneTo(DefaultArgument node)
    {
        return cloneTo(node, true, true);
    }

    /**
     * Fill the given {@link DefaultArgument} with the data that is in the
     * specified node.
     *
     * @param node The node to copy the data into.
     * @return The cloned node.
     */
    public DefaultArgument cloneTo(DefaultArgument node, boolean cloneChildren, boolean cloneAnnotations)
    {
        super.cloneTo(node, cloneChildren, cloneAnnotations);

        return node;
    }

    /**
     * Test the {@link DefaultArgument} class type to make sure everything
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
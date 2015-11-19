package net.fathomsoft.nova.tree.generics;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

/**
 * {@link Node} extension that represents
 * 
 * @author	Braden Steffaniak
 */
public class GenericTypeArgument extends Node
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public GenericTypeArgument(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Decode the given statement into a {@link GenericTypeArgument} instance, if
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
	 * 		{@link GenericTypeArgument} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link GenericTypeArgument}.
	 */
	public static GenericTypeArgument decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public GenericTypeArgument clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		GenericTypeArgument node = new GenericTypeArgument(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public GenericTypeArgument cloneTo(GenericTypeArgument node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link GenericTypeArgument} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public GenericTypeArgument cloneTo(GenericTypeArgument node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link GenericTypeArgument} class type to make sure everything
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
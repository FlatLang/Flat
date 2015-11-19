package net.fathomsoft.nova.tree.generics;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

/**
 * {@link Node} extension that represents
 * 
 * @author	Braden Steffaniak
 */
public class GenericTypeParameter extends Node
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public GenericTypeParameter(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Decode the given statement into a {@link GenericTypeParameter} instance, if
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
	 * 		{@link GenericTypeParameter} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link GenericTypeParameter}.
	 */
	public static GenericTypeParameter decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public GenericTypeParameter clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		GenericTypeParameter node = new GenericTypeParameter(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public GenericTypeParameter cloneTo(GenericTypeParameter node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link GenericTypeParameter} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public GenericTypeParameter cloneTo(GenericTypeParameter node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link GenericTypeParameter} class type to make sure everything
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
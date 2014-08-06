package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.util.Location;

/**
 * Node extension that represents
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.
 * @version	v0.2.
 */
public class Skeleton extends Node
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Skeleton(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Decode the given statement into a {@link Skeleton} instance, if
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
	 * 		{@link Skeleton} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link Skeleton}.
	 */
	public static Skeleton decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Skeleton clone(Node temporaryParent, Location locationIn)
	{
		Skeleton node = new Skeleton(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link Skeleton} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Skeleton cloneTo(Skeleton node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the {@link Skeleton} class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(Nova controller, ClassDeclaration clazz, BodyMethodDeclaration method)
	{
		
		
		return null;
	}
}
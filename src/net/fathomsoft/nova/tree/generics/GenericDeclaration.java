package net.fathomsoft.nova.tree.generics;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

/**
 * {@link Node} extension that represents a generic type declaration.
 * Contains the information of a generic type declaration.
 * Contains all of the generic type parameter names.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.40 Dec 7, 2014 at 4:05:23 PM
 * @version	v0.2.40 Dec 7, 2014 at 4:23:17 PM
 */
public class GenericDeclaration extends Node
{
	private String[] names;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public GenericDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public String[] getNames()
	{
		return names;
	}
	
	public void setNames(String[] names)
	{
		this.names = names;
	}
	
	/**
	 * Decode the given statement into a {@link GenericDeclaration} instance, if
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
	 * 		{@link GenericDeclaration} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link GenericDeclaration}.
	 */
	public static GenericDeclaration decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public GenericDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		GenericDeclaration node = new GenericDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link GenericDeclaration} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public GenericDeclaration cloneTo(GenericDeclaration node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the {@link GenericDeclaration} class type to make sure everything
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
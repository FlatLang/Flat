package net.fathomsoft.nova.tree.generics;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.AccessorMethod;
import net.fathomsoft.nova.tree.IValue;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.TypeList;
import net.fathomsoft.nova.util.Location;

/**
 * {@link TypeList} extension that represents a generic type implementation.
 * Contains the information of a generic type implementation.
 * Contains all of the types that are being implemented into a generic
 * declaration.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.41 Dec 7, 2014 at 9:49:27 PM
 * @version	v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public class GenericImplementation extends TypeList<GenericArgument>
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public GenericImplementation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public GenericImplementation clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		GenericImplementation node = new GenericImplementation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public GenericImplementation cloneTo(GenericImplementation node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link GenericImplementation} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public GenericImplementation cloneTo(GenericImplementation node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link GenericImplementation} class type to make sure everything
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
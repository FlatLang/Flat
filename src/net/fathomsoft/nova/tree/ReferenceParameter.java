package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * {@link Parameter} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class ReferenceParameter extends Parameter
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ReferenceParameter(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public boolean isUserMade()
	{
		return false;
	}
	
	@Override
	public boolean isInstance()
	{
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public ReferenceParameter clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		ReferenceParameter node = new ReferenceParameter(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public ReferenceParameter cloneTo(ReferenceParameter node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link ReferenceParameter} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ReferenceParameter cloneTo(ReferenceParameter node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link ReferenceParameter} class type to make sure everything
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
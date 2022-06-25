package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.util.Location;

/**
 * {@link AccessorMethod} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class ShorthandAccessor extends AccessorMethod
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ShorthandAccessor(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public boolean isUserMade(boolean checkAncestor)
	{
		return false;
	}
	
	@Override
	public ValidationResult validate(int phase)
	{
		return super.validate(phase);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public ShorthandAccessor clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		ShorthandAccessor node = new ShorthandAccessor(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public ShorthandAccessor cloneTo(ShorthandAccessor node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link ShorthandAccessor} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ShorthandAccessor cloneTo(ShorthandAccessor node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the {@link ShorthandAccessor} class type to make sure everything
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
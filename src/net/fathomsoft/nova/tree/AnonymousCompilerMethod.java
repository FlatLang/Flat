package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * {@link Node} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class AnonymousCompilerMethod extends BodyMethodDeclaration
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public AnonymousCompilerMethod(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public boolean isUserMade()
	{
		return false;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public AnonymousCompilerMethod clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		AnonymousCompilerMethod node = new AnonymousCompilerMethod(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public AnonymousCompilerMethod cloneTo(AnonymousCompilerMethod node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link AnonymousCompilerMethod} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public AnonymousCompilerMethod cloneTo(AnonymousCompilerMethod node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link AnonymousCompilerMethod} class type to make sure everything
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
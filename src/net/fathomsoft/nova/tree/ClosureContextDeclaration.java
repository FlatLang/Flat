package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * {@link Node} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class ClosureContextDeclaration extends LocalDeclaration
{
	public ClosureContext context;
	
	private int id;
	
	private static int idCounter = 1;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ClosureContextDeclaration(Node temporaryParent, Location locationIn, ClosureContext context)
	{
		super(temporaryParent, locationIn);
		
		this.context = context;
		id = idCounter++;
	}
	
	@Override
	public boolean isUserMade()
	{
		return false;
	}
	
	public String getName()
	{
		return "contextArg" + id;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public ClosureContextDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		ClosureContextDeclaration node = new ClosureContextDeclaration(temporaryParent, locationIn, context);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public ClosureContextDeclaration cloneTo(ClosureContextDeclaration node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link ClosureContextDeclaration} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ClosureContextDeclaration cloneTo(ClosureContextDeclaration node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the {@link ClosureContextDeclaration} class type to make sure everything
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
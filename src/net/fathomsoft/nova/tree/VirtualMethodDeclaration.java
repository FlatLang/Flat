package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * {@link BodyMethodDeclaration} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class VirtualMethodDeclaration extends BodyMethodDeclaration
{
	public NovaMethodDeclaration base;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public VirtualMethodDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public void searchVirtualMethodDeclaration()
	{
		
	}
	
	@Override
	public VirtualMethodDeclaration getVirtualMethod()
	{
		return this;
	}
	
	public NovaParameterList getOriginalParameterList()
	{
		return super.getParameterList();
	}
	
	@Override
	public NovaParameterList getParameterList()
	{
		return base.getParameterList();
	}
	
	@Override
	public void setOverloadIDs(MethodDeclaration[] methods)
	{
		
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public VirtualMethodDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		VirtualMethodDeclaration node = new VirtualMethodDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public VirtualMethodDeclaration cloneTo(VirtualMethodDeclaration node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link VirtualMethodDeclaration} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public VirtualMethodDeclaration cloneTo(VirtualMethodDeclaration node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link VirtualMethodDeclaration} class type to make sure everything
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
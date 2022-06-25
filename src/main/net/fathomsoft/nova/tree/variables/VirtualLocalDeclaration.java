package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.LocalDeclaration;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.Value;
import net.fathomsoft.nova.util.Location;

/**
 * {@link LocalDeclaration} extension that represents
 * 
 * @author	Braden Steffaniak
 */
public class VirtualLocalDeclaration extends LocalDeclaration
{
	private Value reference;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public VirtualLocalDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public Value getReference()
	{
		return reference;
	}
	
	public void setReference(Value reference)
	{
		this.reference = reference;
	}
	
	@Override
	public void addDefaultGenericTypeArguments()
	{
		
	}
	
	@Override
	public boolean isUserMade(boolean checkAncestor)
	{
		return false;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public VirtualLocalDeclaration cloneTo(VirtualLocalDeclaration node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link VirtualLocalDeclaration} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public VirtualLocalDeclaration cloneTo(VirtualLocalDeclaration node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.reference = reference;
		
		return node;
	}
	
	/**
	 * Test the {@link VirtualLocalDeclaration} class type to make sure everything
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
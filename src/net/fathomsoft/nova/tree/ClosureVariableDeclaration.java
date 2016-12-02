package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;

/**
 * {@link VariableDeclaration} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class ClosureVariableDeclaration extends VariableDeclaration
{
	public VariableDeclaration originalDeclaration;
	
	public static final String CONTEXT_VARIABLE_NAME = "context";
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ClosureVariableDeclaration(Node temporaryParent, Location locationIn, VariableDeclaration originalDeclaration)
	{
		super(temporaryParent, locationIn);
		
		this.originalDeclaration = originalDeclaration;
		
		setIsValueReference(true);
	}
	
	@Override
	public VariableDeclaration getOriginalDeclaration()
	{
		return originalDeclaration.getOriginalDeclaration();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public ClosureVariableDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		ClosureVariableDeclaration node = new ClosureVariableDeclaration(temporaryParent, locationIn, originalDeclaration);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public ClosureVariableDeclaration cloneTo(ClosureVariableDeclaration node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link ClosureVariableDeclaration} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ClosureVariableDeclaration cloneTo(ClosureVariableDeclaration node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the {@link ClosureVariableDeclaration} class type to make sure everything
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
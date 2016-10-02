package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TargetC;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
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
	
	public String getName()
	{
		return "contextArg" + id;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public ClosureContextDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		ClosureContextDeclaration node = new ClosureContextDeclaration(temporaryParent, locationIn, context);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public ClosureContextDeclaration cloneTo(ClosureContextDeclaration node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link ClosureContextDeclaration} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ClosureContextDeclaration cloneTo(ClosureContextDeclaration node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
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
	
	@Override
	public TargetC.TargetClosureContextDeclaration getTarget()
	{
		final ClosureContextDeclaration self = this;
		
		return new TargetC.TargetClosureContextDeclaration()
		{
			public ClosureContextDeclaration node()
			{
				return self;
			}
		};
	}
}
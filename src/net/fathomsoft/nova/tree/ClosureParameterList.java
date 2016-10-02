package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TargetC;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * {@link ParameterList} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class ClosureParameterList extends ParameterList<Value>
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ClosureParameterList(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public ClosureParameterList clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		ClosureParameterList node = new ClosureParameterList(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public ClosureParameterList cloneTo(ClosureParameterList node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link ClosureParameterList} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ClosureParameterList cloneTo(ClosureParameterList node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link ClosureParameterList} class type to make sure everything
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
	public TargetC.TargetClosureParameterList getTarget()
	{
		return TargetC.TARGET_CLOSURE_PARAMETER_LIST;
	}
}
package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.TargetC;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.ClassDeclaration;
import net.fathomsoft.nova.tree.List;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

/**
 * {@link FieldList} extensions that contains all of the public Field
 * instances of a ClassDeclaration.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:00:50 PM
 * @version	v0.2.29 Aug 29, 2014 at 3:17:45 PM
 */
public class InstanceFieldList extends List
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public InstanceFieldList(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public InstanceFieldList clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		InstanceFieldList node = new InstanceFieldList(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public InstanceFieldList cloneTo(InstanceFieldList node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link InstanceFieldList} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public InstanceFieldList cloneTo(InstanceFieldList node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the InstanceFieldList class type to make sure everything
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
	public TargetC.TargetInstanceFieldList getTarget()
	{
		final InstanceFieldList self = this;
		
		return new TargetC.TargetInstanceFieldList()
		{
			public InstanceFieldList node()
			{
				return self;
			}
		};
	}
}
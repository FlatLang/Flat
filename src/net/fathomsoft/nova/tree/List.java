package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * {@link Node} extension that abstracts a general list type.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.29 Aug 21, 2014 at 10:46:16 PM
 * @version	v0.2.29 Aug 29, 2014 at 3:17:45 PM
 */
public class List extends Node
{
	/**
	 * Instantiate and initialize default data.
	 */
	public List(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public List clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		List node = new List(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public List cloneTo(List node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link List} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public List cloneTo(List node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the FieldList class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
	
	public String toString()
	{
		String s = "";
		
		for (int i = 0; i < getNumVisibleChildren(); i++)
		{
			if (i > 0)
			{
				s += "\n";
			}
			
			s += getVisibleChild(i).toString();
		}
		
		return s;
	}
}
package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TargetC;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * Node extension that contains all of the dimension attributes
 * for an array declaration.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.5 May 19, 2014 at 12:09:41 AM
 * @version	v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public class Dimensions extends Node
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Dimensions(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			builder.append('[').append(getChild(i).generateNovaInput()).append(']');
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Dimensions clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		Dimensions node = new Dimensions(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public Dimensions cloneTo(Dimensions node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link Dimensions} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Dimensions cloneTo(Dimensions node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the Dimensions class type to make sure everything
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
	public TargetC.TargetDimensions getTarget()
	{
		return TargetC.TARGET_DIMENSIONS;
	}
}
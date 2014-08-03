package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

/**
 * Node extensions that contains all of the private Field
 * instances of a ClassDeclaration.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:00:50 PM
 * @version	v0.2.19 Jul 26, 2014 at 12:30:24 AM
 */
public class StaticFieldList extends Node
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public StaticFieldList(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			getChild(i).generateCHeader(builder);
		}
		
		return builder;
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			getChild(i).generateCSource(builder);
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public StaticFieldList clone(Node temporaryParent, Location locationIn)
	{
		StaticFieldList node = new StaticFieldList(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link StaticFieldList} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public StaticFieldList cloneTo(StaticFieldList node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the StaticFieldList class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test()
	{
		
		
		return null;
	}
}
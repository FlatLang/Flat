package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Location;

/**
 * Value extension that represents the value of a "null" reference
 * type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.14 Jul 5, 2014 at 12:40:35 AM
 * @version	v0.2.14 Jul 19, 2014 at 7:33:13 PM
 */
public class Null extends IValue
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Null(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return generateCSourceFragment(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		return builder.append("(Object*)").append(0);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		return builder.append("null");
	}
	
	/**
	 * Decode the given statement into a Null instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Possible inputs include:<br>
	 * <ul>
	 * 	<li>null</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Null instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Null.
	 */
	public static Null decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (statement.equals("null"))
		{
			Null n = new Null(parent, location);
			
			n.setType("Object");
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Null clone(Node temporaryParent, Location locationIn)
	{
		Null node = new Null(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given Null with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Null cloneTo(Null node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the Null class type to make sure everything
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
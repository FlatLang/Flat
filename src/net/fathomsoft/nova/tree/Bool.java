package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Node extension that represents the value of a "bool" primitive
 * type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.14 Jul 5, 2014 at 12:40:35 AM
 * @version	v0.2.14 Jul 19, 2014 at 7:33:13 PM
 */
public class Bool extends IValue
{
	private String	value;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Bool(Node temporaryParent, Location locationIn)
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
		if (value.equals("true"))
		{
			builder.append(1);
		}
		else
		{
			builder.append(0);
		}
		
		return builder;
	}
	
	/**
	 * Decode the given statement into a Bool instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Possible inputs include:<br>
	 * <ul>
	 * 	<li>true</li>
	 * 	<li>false</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Bool instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Bool.
	 */
	public static Bool decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (statement.equals("true") || statement.equals("false"))
		{
			Bool n = new Bool(parent, location);
			
			n.setType("bool");
			n.value = statement;
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Bool clone(Node temporaryParent, Location locationIn)
	{
		Bool node = new Bool(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given Null with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Bool cloneTo(Bool node)
	{
		super.cloneTo(node);
		
		node.value = value;
		
		return node;
	}
	
	/**
	 * Test the Bool class type to make sure everything
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
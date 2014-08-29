package net.fathomsoft.nova.tree.exceptionhandling;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

/**
 * ExceptionHandler extension that represents the declaration of a
 * finally node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 22, 2014 at 4:02:21 PM
 * @version	v0.2.26 Aug 6, 2014 at 2:48:50 PM
 */
public class Finally extends ExceptionHandler
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Finally(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		builder.append("FINALLY").append('\n');
		
		getScope().generateCSource(builder);
		
		builder.append("END_TRY;").append('\n');
		
		return builder;
	}
	
	/**
	 * Decode the given statement into a Finally instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * The only acceptable input is "finally"
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Finally instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Finally.
	 */
	public static Finally decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (statement.equals("finally"))
		{
			Finally n = new Finally(parent, location);
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Finally clone(Node temporaryParent, Location locationIn)
	{
		Finally node = new Finally(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link Finally} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Finally cloneTo(Finally node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the Finally class type to make sure everything
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
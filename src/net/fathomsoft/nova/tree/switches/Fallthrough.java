package net.fathomsoft.nova.tree.switches;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.Assignment;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Location;

/**
 * {@link Node} extension that represents
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.37 Oct 17, 2014 at 11:46:55 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class Fallthrough extends Node implements SwitchChild
{
	public static final String IDENTIFIER = "fallthrough";
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Fallthrough(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		Variable fall = getParentSwitch().getLocalFallthrough();
		
		if (fall != null)
		{
			fall.generateCSourceFragment(builder).append(" = 1;\n");
		}
		
		return builder;
	}
	
	/**
	 * Decode the given statement into a {@link Fallthrough} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>fallthrough</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link Fallthrough} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link Fallthrough}.
	 */
	public static Fallthrough decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (statement.equals(IDENTIFIER))
		{
			Fallthrough n = new Fallthrough(parent, location);
			
			return n;
		}
		
		return null;
	}
	
	@Override
	public void onAdded(Node parent)
	{
		if (parent.getBaseNode() instanceof Case == false)
		{
			SyntaxMessage.error("Fallthrough statements are only compatible within '" + Case.IDENTIFIER + "' statements", this);
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Fallthrough clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		Fallthrough node = new Fallthrough(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public Fallthrough cloneTo(Fallthrough node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link Fallthrough} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Fallthrough cloneTo(Fallthrough node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link Fallthrough} class type to make sure everything
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
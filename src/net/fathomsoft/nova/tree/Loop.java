package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * Node extension that represents the declaration of a Loop
 * node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:55:18 PM
 * @version	v0.2.28 Aug 20, 2014 at 12:10:45 AM
 */
public class Loop extends Node
{
	/**
	 * Instantiate a new Loop and initialize the default values.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Loop(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		Scope scope = new Scope(this, locationIn.asNew());
		
		setScope(scope);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#pendingScopeFragment()
	 */
	@Override
	public boolean pendingScopeFragment()
	{
		return getScope().isEmpty();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getScope()
	 */
	@Override
	public Scope getScope()
	{
		return (Scope)getChild(super.getNumDefaultChildren());
	}
	
	/**
	 * Decode the given statement into a Loop instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * The statement can be either a while loop or a for loop.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>for (int i = 0; i &lt; 100; i++)</li>
	 * 	<li>for (int i = 0; array != null &amp;&amp; i &lt; array.getSize(); i = num * 3 * i)</li>
	 * 	<li>while (currentNode != null)</li>
	 * 	<li>while (true)</li>
	 * 	<li>while (number.isEven())</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Loop instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Loop.
	 */
	public static Loop decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		Loop node = null;
		
		if ((node = ForLoop.decodeStatement(parent, statement, location, require)) != null)
		{
			return node;
		}
		else if ((node = WhileLoop.decodeStatement(parent, statement, location, require)) != null)
		{
			return node;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Loop clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		Loop node = new Loop(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link Loop} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Loop cloneTo(Loop node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the Loop class type to make sure everything
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
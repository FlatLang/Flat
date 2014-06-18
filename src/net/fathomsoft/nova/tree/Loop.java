package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Location;

/**
 * Node extension that represents the declaration of a Loop
 * node type. See {@link #decodeStatement(Node, String, Location, boolean, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:55:18 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
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
		
		Scope scope = new Scope(this, locationIn);
		
		setScope(scope);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getScope()
	 */
	@Override
	public Scope getScope()
	{
		return (Scope)getChild(0);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#addChild(Node)
	 */
	@Override
	public void addChild(Node child)
	{
		getScope().addChild(child);
	}
	
	/**
	 * Decode the given statement into a Loop instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * The statement can be either a while loop or a for loop.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>for (int i = 0; i < 100; i++)</li>
	 * 	<li>for (int i = 0; array != null && i < array.getSize(); i = num * 3 * i)</li>
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
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Loop.
	 */
	public static Loop decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		Loop node = null;
		
		if ((node = ForLoop.decodeStatement(parent, statement, location, require, scope)) != null)
		{
			return node;
		}
		else if ((node = WhileLoop.decodeStatement(parent, statement, location, require, scope)) != null)
		{
			return node;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Loop clone(Node temporaryParent, Location locationIn)
	{
		Loop node = new Loop(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given Loop with the data that is in the
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
}
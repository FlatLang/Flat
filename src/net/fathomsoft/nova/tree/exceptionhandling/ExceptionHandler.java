package net.fathomsoft.nova.tree.exceptionhandling;

import net.fathomsoft.nova.tree.Scope;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

/**
 * Node extension that represents the declaration of an exception
 * handling node type. See {@link #decodeStatement(Node, String, Location, boolean, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 21, 2014 at 10:50:26 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class ExceptionHandler extends Node
{
	/**
	 * Instantiate and initialize default values.
	 */
	public ExceptionHandler(Node temporaryParent, Location locationIn)
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
	 * Decode the given statement into a ExceptionHandler instance,
	 * if possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>try</li>
	 * 	<li>catch (Exception e)</li>
	 * 	<li>finally</li>
	 * 	<li>throw new IOException()</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ExceptionHandler instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ExceptionHandler.
	 */
	public static ExceptionHandler decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		ExceptionHandler node = null;
		
		if ((node = Try.decodeStatement(parent, statement, location, require, scope)) != null)
		{
			return node;
		}
		else if ((node = Catch.decodeStatement(parent, statement, location, require, scope)) != null)
		{
			return node;
		}
		else if ((node = Finally.decodeStatement(parent, statement, location, require, scope)) != null)
		{
			return node;
		}
		else if ((node = Throw.decodeStatement(parent, statement, location, require, scope)) != null)
		{
			return node;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public ExceptionHandler clone(Node temporaryParent, Location locationIn)
	{
		ExceptionHandler node = new ExceptionHandler(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ExceptionHandler with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ExceptionHandler cloneTo(ExceptionHandler node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
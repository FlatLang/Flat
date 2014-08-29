package net.fathomsoft.nova.tree.exceptionhandling;

import java.util.HashMap;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

/**
 * Node extension that represents an Exception instance that is to
 * be thrown within a Throw statement.
 * <blockquote><pre>
 * // Instantiate a new Exception type.
 * ExceptionName varName = new ExceptionName(... optional arguments ...);
 * 
 * // Throw the generated Exception type.
 * throw varName;</pre></blockquote>
 * For more information on what
 * it looks like to throw an Exception, see
 * {@link Throw#decodeStatement(Node, String, Location, boolean)}.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Apr 6, 2014 at 8:44:35 PM
 * @version	v0.2.26 Aug 6, 2014 at 2:48:50 PM
 */
public class Exception extends Node
{
	private int			id;
	
	private static int	exceptionId	= 1;
	
	private static final HashMap<String, Integer>	ids;
	
	/**
	 * Identifier for the exception data passed to a method.
	 */
	public static final String	EXCEPTION_DATA_IDENTIFIER = "exceptionData";
	
	/**
	 * Initialize static data.
	 */
	static
	{
		ids = new HashMap<String, Integer>();
		
		ids.put("Exception", exceptionId++);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Exception(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get the ID of the Exception. The ID is needed for implementing
	 * the c version of the exception handling.
	 * 
	 * @return The ID of the Exception.
	 */
	public int getID()
	{
		return id;
	}
	
	/**
	 * Set the type of Exception that is being generated. If the type
	 * is unique to anything created prior to this, the id of the
	 * generated Exception will also be unique. If the Exception
	 * type has already been used before, it will use the already
	 * generated id that belongs to the Exception.
	 * 
	 * @param type The name (type) of the Exception that is being
	 * 		generated.
	 */
	public void setType(String type)
	{
		if (ids.containsKey(type))
		{
			id = ids.get(type);
		}
		else
		{
			id = exceptionId++;
			
			ids.put(type, id);
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Exception clone(Node temporaryParent, Location locationIn)
	{
		Exception node = new Exception(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link Exception} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Exception cloneTo(Exception node)
	{
		super.cloneTo(node);
		
		node.id = id;
		
		return node;
	}
	
	/**
	 * Test the Exception class type to make sure everything
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
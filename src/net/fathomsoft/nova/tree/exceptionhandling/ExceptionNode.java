package net.fathomsoft.nova.tree.exceptionhandling;

import java.util.HashMap;

import net.fathomsoft.nova.tree.TreeNode;
import net.fathomsoft.nova.util.Location;

/**
 * TreeNode extension that represents an Exception instance that is to
 * be thrown within a ThrowNode statement.
 * <blockquote><pre>
 * // Instantiate a new Exception type.
 * ExceptionName varName = new ExceptionName(... optional arguments ...);
 * 
 * // Throw the generated Exception type.
 * throw varName;</pre></blockquote>
 * For more information on what
 * it looks like to throw an ExceptionNode, see
 * {@link ThrowNode#decodeStatement(TreeNode, String, Location, boolean, boolean)}.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Apr 6, 2014 at 8:44:35 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class ExceptionNode extends TreeNode
{
	private int			id;
	
	private static int	exceptionId	= 1;
	
	private static final HashMap<String, Integer>	ids;
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public ExceptionNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
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
	 * generated ExceptionNode will also be unique. If the Exception
	 * type has already been used before, it will use the already
	 * generated id that belongs to the ExceptionNode.
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
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode, Location)
	 */
	@Override
	public ExceptionNode clone(TreeNode temporaryParent, Location locationIn)
	{
		ExceptionNode node = new ExceptionNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ExceptionNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ExceptionNode cloneTo(ExceptionNode node)
	{
		super.cloneTo(node);
		
		node.id = id;
		
		return node;
	}
}

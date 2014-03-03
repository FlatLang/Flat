package net.fathomsoft.fathom.error;

import net.fathomsoft.fathom.tree.TreeNode;
import net.fathomsoft.fathom.util.Location;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:28:12 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:28:12 PM
 * @version	v
 */
public class SyntaxError extends Error
{
	public SyntaxError(String message, TreeNode node)
	{
		this(message, node.getLocationIn());
	}
	
	/**
	 * 
	 * 
	 * @param message 
	 */
	public SyntaxError(String message, Location location)
	{
		super("Syntax error: " + message, location);
	}
	
	public static void outputNewError(String message, TreeNode node)
	{
		outputNewError(message, node.getLocationIn());
	}
	
	public static void outputNewError(String message, Location location)
	{
		SyntaxError error = new SyntaxError(message, location);
		
		error.outputError();
	}
}
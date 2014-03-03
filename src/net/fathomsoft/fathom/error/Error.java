package net.fathomsoft.fathom.error;

import net.fathomsoft.fathom.tree.TreeNode;
import net.fathomsoft.fathom.util.Location;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:28:08 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:28:08 PM
 * @version	v
 */
public class Error
{
	private Location	location;
	
	private String		message;
	
	public Error(String message, TreeNode node)
	{
		this(message, node.getLocationIn());
	}
	
	public Error(String message, Location location)
	{
		this.location = location;
		
		this.message  = message;
	}
	
	public void outputError()
	{
		System.err.println(message + " on line number " + location.getLineNumber() + " at offset " + location.getOffset());
	}
}
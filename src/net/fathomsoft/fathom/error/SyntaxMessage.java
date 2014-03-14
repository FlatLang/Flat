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
public class SyntaxMessage
{
	public static void error(String message, TreeNode node)
	{
		error(message, node.getLocationIn());
	}
	
	public static void error(String message, Location location)
	{
		Message error = new Message(message, location);
		
		error.outputMessage(Message.ERROR);
	}
	
	public static void warning(String message, TreeNode node)
	{
		warning(message, node.getLocationIn());
	}
	
	public static void warning(String message, Location location)
	{
		Message warning = new Message(message, location);
		
		warning.outputMessage(Message.WARNING);
	}
}
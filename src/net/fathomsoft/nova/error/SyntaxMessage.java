package net.fathomsoft.nova.error;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.tree.FileNode;
import net.fathomsoft.nova.tree.TreeNode;
import net.fathomsoft.nova.util.Location;

/**
 * Class that outputs an error of a specific type.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:28:12 PM
 * @version	v0.2.1 Apr 24, 2014 at 4:47:12 PM
 */
public class SyntaxMessage
{
	/**
	 * Output an error message from the compiler.
	 * 
	 * @param message The message describing the error.
	 * @param controller The controller of the compiling program.
	 */
	public static void error(String message, Nova controller)
	{
		Message error = new Message(message, controller);
		
		error.outputMessage(Message.ERROR);
	}
	
	/**
	 * Output a warning message from the compiler.
	 * 
	 * @param message The message describing the warning.
	 * @param controller The controller of the compiling program.
	 */
	public static void warning(String message, Nova controller)
	{
		Message error = new Message(message, controller);
		
		error.outputMessage(Message.WARNING);
	}
	
	/**
	 * Output an error message from the compiler.
	 * 
	 * @param message The message describing the error.
	 * @param node The node that the error occurred from.
	 */
	public static void error(String message, TreeNode node)
	{
		error(message, node, node.getLocationIn());
	}
	
	/**
	 * Output a warning message from the compiler.
	 * 
	 * @param message The message describing the warning.
	 * @param node The node that the warning occurred from.
	 */
	public static void warning(String message, TreeNode node)
	{
		warning(message, node, node.getLocationIn());
	}
	
	/**
	 * Output an error message from the compiler.
	 * 
	 * @param message The message describing the error.
	 * @param node The node that the error occurred from.
	 * @param location The location that the error occurred at.
	 */
	public static void error(String message, TreeNode node, Location location)
	{
		Message error = new Message(message, node, location);
		
		error.outputMessage(Message.ERROR);
	}
	
	/**
	 * Output a warning message from the compiler.
	 * 
	 * @param message The message describing the warning.
	 * @param node The node that the warning occurred from.
	 * @param location The location that the warning occurred at.
	 */
	public static void warning(String message, TreeNode node, Location location)
	{
		Message warning = new Message(message, node, location);
		
		warning.outputMessage(Message.WARNING);
	}
}
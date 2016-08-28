package net.fathomsoft.nova.error;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.tree.FileDeclaration;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

/**
 * Class that holds the information for a message that will be
 * output from the compiler.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:28:08 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class Message
{
	private Node    node;
	
	private FileDeclaration	file;
	
	private Location	location;
	
	private String		message;
	
	private Nova		controller;
	
	public static final int	MESSAGE = 1, WARNING = 2, ERROR = 3;
	
	/**
	 * Create a new message instance with the given message.
	 * 
	 * @param message The message that describes what happened.
	 * @param controller The controller of the compiling program.
	 */
	public Message(String message, Nova controller)
	{
		this.message    = message;
		this.controller = controller;
	}
	
	/**
	 * Create a new message instance with the given message that
	 * is representing the given node.
	 * 
	 * @param message The message that describes what happened.
	 * @param node The node that the message is talking about.
	 */
	public Message(String message, Node node, Location location)
	{
		this.file       = node.getFileDeclaration(true);
		this.node       = node;
		this.location   = location;
		this.message    = message;
		this.controller = node.getController();
	}
	
	/**
	 * Output a message from the compiler.
	 * 
	 * @param type The type of message that is being output.
	 */
	public void outputMessage(int type)
	{
		outputMessage(type, type == ERROR);
	}
	
	/**
	 * Output a message from the compiler.
	 * 
	 * @param type The type of message that is being output.
	 * @param throwException Whether or not to throw a
	 * 		SyntaxErrorException.
	 */
	public void outputMessage(int type, boolean throwException)
	{
		String info = message;
		
		if (node != null)
		{
			Location loc   = node.getLocationIn();
			
			int lineNumber = node.getLineNumber();
			
			if (location != null && location.isValid())
			{
				lineNumber += location.getLineNumber();
			}
			
			info += " - in file \"" + file.getFile().getName() + '"';
			info += " on line number " + lineNumber + " at offset " + loc.getOffset() + " [" + loc.getStart() + ", " + loc.getEnd() + "]";
		}
		
		if (type == MESSAGE)
		{
			controller.log(info);
		}
		else if (type == WARNING)
		{
			controller.warning(info);
		}
		else if (type == ERROR)
		{
			controller.error(info);
		}
		
		if (throwException)
		{
			throw new SyntaxErrorException(info, type);
		}
	}
}
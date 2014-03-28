/**
 * The Fathom Programming Language. Write Unbelievable Code.
 *  Copyright (C) 2014  Braden Steffaniak <BradenSteffaniak@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.fathomsoft.fathom.error;

import net.fathomsoft.fathom.tree.TreeNode;
import net.fathomsoft.fathom.util.Location;

/**
 * Class that holds the information for a message that will be
 * output from the compiler.
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:28:08 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:28:08 PM
 * @version	v
 */
public class Message
{
	private Location	location;
	
	private String		message;
	
	public static final int	MESSAGE = 1, WARNING = 2, ERROR = 3;
	
	/**
	 * Create a new message instance with the given message that
	 * is representing the given node.
	 * 
	 * @param message The message that describes what happened.
	 * @param node The node that the message is talking about.
	 */
	public Message(String message, TreeNode node)
	{
		this(message, node.getLocationIn());
	}
	
	/**
	 * Create a new message instance with the given message that
	 * is representing the given location.
	 * 
	 * @param message The message that describes what happened.
	 * @param location The location i the source file that the
	 * 		message is talking about.
	 */
	public Message(String message, Location location)
	{
		this.location = location;
		
		this.message  = message;
	}
	
	/**
	 * Output a message from the compiler.
	 * 
	 * @param type The type of message that is being output.
	 */
	public void outputMessage(int type)
	{
		if (type == MESSAGE)
		{
			System.out.print("Message: ");
		}
		else if (type == WARNING)
		{
			System.err.print("Warning: ");
		}
		else if (type == ERROR)
		{
			System.err.print("Error: ");
		}
		
		System.err.println(message + " on line number " + location.getLineNumber() + " at offset " + location.getOffset());
	}
}

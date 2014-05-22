/**
 * The Nova Programming Language. Write Explosive Code.
 * Copyright (C) 2014  Braden Steffaniak <BradenSteffaniak@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * The Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
		error(message, node.getFileNode(), node.getLocationIn(), node.getController());
	}
	
	/**
	 * Output an error message from the compiler.
	 * 
	 * @param message The message describing the error.
	 * @param file The FileNode that the error occurred in.
	 * @param location The location that the error occurred at.
	 * @param controller The controller of the compiling program.
	 */
	public static void error(String message, FileNode file, Location location, Nova controller)
	{
		Message error = new Message(message, file, location, controller);
		
		error.outputMessage(Message.ERROR);
	}
	
	/**
	 * Output a warning message from the compiler.
	 * 
	 * @param message The message describing the warning.
	 * @param node The node that the warning occurred from.
	 */
	public static void warning(String message, TreeNode node)
	{
		warning(message, node.getFileNode(), node.getLocationIn(), node.getController());
	}
	
	/**
	 * Output a warning message from the compiler.
	 * 
	 * @param message The message describing the warning.
	 * @param file The FileNode that the warning occurred in.
	 * @param location The location that the warning occurred at.
	 * @param controller The controller of the compiling program.
	 */
	public static void warning(String message, FileNode file, Location location, Nova controller)
	{
		Message warning = new Message(message, file, location, controller);
		
		warning.outputMessage(Message.WARNING);
	}
}
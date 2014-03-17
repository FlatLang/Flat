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
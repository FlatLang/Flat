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
package net.fathomsoft.fathom.util;

/**
 * Class used for extracting prototypes from an input String.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 28, 2014 at 5:16:17 PM
 * @version	v0.2 Apr 7, 2014 at 8:26:32 PM
 */
public class PrototypeFinder
{
	/**
	 * Extract all of the prototypes within the input String.
	 * 
	 * @param input The String containing the prototype declarations.
	 * @return A String array containing all of the prototype
	 * 		declarations.
	 */
	public static String[] findPrototypes(String input)
	{
		System.out.println("findingg....");
		return Regex.findAll(input, Patterns.FUNCTION_PROTOTYPE);
	}
}
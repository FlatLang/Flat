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
 * Class that contains information for a line number and character offset.
 * Used in the compilation process, in the event that there is an error
 * or warning, for knowing where exactly it occurred in the source code.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 7, 2014 at 10:11:40 AM
 * @version	v0.2 Apr 5, 2014 at 3:34:59 PM
 */
public class Location
{
	private int		lineNumber, offset;
	
	private Bounds	bounds;
	
	/**
	 * Default constructor.
	 */
	public Location()
	{
		bounds = new Bounds(0, 0);
	}
	
	/**
	 * Copy the location data from the given Location variable into
	 * a newly instantiated Location instance.
	 * 
	 * @param loc The location to copy the data from.
	 */
	public Location(Location loc)
	{
		this(loc.lineNumber, loc.offset, loc.bounds.getStart(), loc.bounds.getEnd());
	}
	
	/**
	 * Constructor that initializes the lineNumber and offset to the
	 * given values.
	 * 
	 * @param lineNumber The lineNumber that the Location represents.
	 * @param offset The character offset horizontally on the current line.
	 * @param start The character offset that the Location represents as
	 * 		the start throughout the whole source text.
	 * @param end The character offset that the Location represents as
	 * 		the end throughout the whole source text.
	 */
	public Location(int lineNumber, int offset, int start, int end)
	{
		// Initialize default data.
		this();
		
		this.offset = offset;
		setLineNumber(lineNumber);
		setBounds(start, end);
	}
	
	/**
	 * Get the line number that the Location represents.
	 * 
	 * @return The line number that the Location represents.
	 */
	public int getLineNumber()
	{
		return lineNumber;
	}
	
	/**
	 * Set the line number that the Location represents.
	 * 
	 * @param lineNumber The line number that the Location represents.
	 */
	public void setLineNumber(int lineNumber)
	{
		this.lineNumber = lineNumber;
	}
	
	/**
	 * Get the character offset that the Location represents as the start
	 * throughout the whole source text.
	 * 
	 * @return The character offset that the Location represents as the
	 * 		start throughout the whole source text.
	 */
	public int getStart()
	{
		return bounds.getStart();
	}
	
	/**
	 * Get the character offset that the Location represents as the end
	 * throughout the whole source text.
	 * 
	 * @return The character offset that the Location represents as the end
	 * 		throughout the whole source text.
	 */
	public int getEnd()
	{
		return bounds.getEnd();
	}
	
	/**
	 * Get character offset that the Location represents on the
	 * specified line number.
	 * 
	 * @return The character offset that the Location represents on the
	 * 		specified line number.
	 */
	public int getOffset()
	{
		return offset;
	}
	
	/**
	 * Get the Bounds of the Location's offset. The starting and ending
	 * offsets.
	 * 
	 * @return The Bounds instance containing the starting and ending
	 * 		offsets.
	 */
	public Bounds getBounds()
	{
		return bounds;
	}
	
	/**
	 * Set the character bound offsets on the specified Location's line
	 * number.
	 * 
	 * @param start The character offset that the Location represents as
	 * 		the start on the specified line.
	 * @param end The character offset that the Location represents as
	 * 		the end on the specified line.
	 */
	public void setBounds(int start, int end)
	{
		bounds.setStart(start);
		bounds.setEnd(end);
	}
}
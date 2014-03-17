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
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 7, 2014 at 10:11:40 AM
 * @since	v
 * @version	Jan 7, 2014 at 10:11:40 AM
 * @version	v
 */
public class Location
{
	private int	lineNumber, offset;
	
	public Location()
	{
		
	}
	
	public Location(int lineNumber, int offset)
	{
		setLineNumber(lineNumber);
		setOffset(offset);
	}
	
	public int getLineNumber()
	{
		return lineNumber;
	}
	
	public void setLineNumber(int lineNumber)
	{
		this.lineNumber = lineNumber;
	}

	public int getOffset()
	{
		return offset;
	}
	
	public void setOffset(int offset)
	{
		this.offset = offset;
	}
}
/**
 * The Nova Programming Language. Write Unbelievable Code.
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
package net.fathomsoft.nova.util;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Feb 9, 2014 at 7:50:00 AM
 * @since	v
 * @version	Feb 9, 2014 at 7:50:00 AM
 * @version	v
 */
public class TierExpressionException extends RuntimeException
{
	public TierExpressionException()
	{
		this("");
	}
	
	public TierExpressionException(String message)
	{
		super(message);
	}
}
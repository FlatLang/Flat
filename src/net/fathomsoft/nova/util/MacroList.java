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

import java.util.ArrayList;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 26, 2014 at 10:16:28 PM
 * @since	v
 * @version	Jan 26, 2014 at 10:16:28 PM
 * @version	v
 */
public class MacroList
{
	private ArrayList<Macro>	macros;
	private ArrayList<Integer>	indices;
	
	public MacroList()
	{
		macros  = new ArrayList<Macro>();
		indices = new ArrayList<Integer>();
	}
	
	public MacroList(int index, Macro macro)
	{
		this();
		
		addMacro(index, macro);
	}
	
	public void addMacro(int index, Macro macro)
	{
		this.indices.add(index);
		this.macros.add(macro);
	}
	
	public Macro getMacro(int index)
	{
		for (int i = 0; i < indices.size(); i++)
		{
			if (index < indices.get(i))
			{
				if (i > 0)
				{
					return macros.get(i - 1);
				}
			}
		}
		
		return macros.get(0);
	}
	
	public String parse(int index, String ... args)
	{
		Macro macro = getMacro(index);
		
		return macro.parse(args);
	}
}
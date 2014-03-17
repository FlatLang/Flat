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

import java.util.regex.Pattern;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 19, 2014 at 1:51:54 AM
 * @since	v
 * @version	Jan 19, 2014 at 1:51:54 AM
 * @version	v
 */
public class Macro
{
	private String	value;
	
	private String	params[];
	
	public Macro(String params[], String value)
	{
		if (params == null)
		{
			params = new String[0];
		}
		
		this.params = params;
		this.value  = value;
	}
	
	public String parse(String ... args)
	{
		String str = value;
		
		for (int i = 0; i < params.length; i++)
		{
			String param = params[i];
			String arg   = args[i];
				
			int index    = 0;
			int offset   = 0;
			
			StringBuilder builder = new StringBuilder(str);
			
			if (param.equals("..."))
			{
				param = "__VA_ARGS__";
				
				StringBuilder b = new StringBuilder();
				
				for (int j = i; j < args.length; j++)
				{
					b.append(args[j]);
					
					if (j < args.length - 1)
					{
						b.append(", ");
					}
				}
				
				arg = b.toString();
			}
				
			Pattern argPattern = Pattern.compile("(?<=[^a-zA-Z0-9_]|^)" + param + "([^a-zA-Z0-9_]|$)");
			
			while (index >= 0)
			{
				index = Regex.indexOf(str, index, argPattern);
				
				if (index < 0)
				{
					break;
				}
				
				builder.replace(index - offset, index + param.length() - offset, arg);
				
				if (index - offset >= 2 && builder.charAt(index - offset - 2) == '#' && builder.charAt(index - offset - 1) == '#')
				{
					builder.delete(index - offset - 2, index - offset);
					
					offset += 2;
				}
				
				offset += param.length() - arg.length();
				
				index += param.length();
			}
			
			str = builder.toString();
		}
		
		return str;
	}
}
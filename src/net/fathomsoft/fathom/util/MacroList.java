package net.fathomsoft.fathom.util;

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
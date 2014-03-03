package net.fathomsoft.fathom.util;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 28, 2014 at 5:16:17 PM
 * @since	v
 * @version	Jan 28, 2014 at 5:16:17 PM
 * @version	v
 */
public class PrototypeFinder
{
	public static String[] findPrototypes(String input)
	{
		System.out.println("findingg....");
		return Regex.findAll(input, Patterns.FUNCTION_PROTOTYPE);
	}
}
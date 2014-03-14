package net.fathomsoft.fathom.util;

/**
 * Class that contains methods used for finding data about
 * a String.
 * 
 * @author	Braden Steffaniak
 * @since	Mar 13, 2014 at 9:38:42 PM
 * @since	v0.1
 * @version	Mar 13, 2014 at 9:38:42 PM
 * @version	v0.1
 */
public class StringUtils
{
	/**
	 * Find the index of the ending char for the match. For instance, to
	 * search for an ending parenthesis, starting from the opening
	 * parenthesis, you would pass findEndingMatch(str, 0, '(', ')');
	 * The method call would return the index of the ending parenthesis
	 * that is paired with the index of 0.
	 * 
	 * @param str The String to search for the pair to the start char.
	 * @param index The index of the start char in the pair.
	 * @param startChar The char that starts off the pair. eg. '('
	 * @param endChar The char that ends the pair. eg: ')'
	 * @return The index of the pair to the starting char, if no pair is
	 * 		found then -1 is returned.
	 */
	public static int findEndingMatch(String str, int index, char startChar, char endChar)
	{
		return findEndingMatch(str, index, startChar, endChar, (char)0);
	}
	
	/**
	 * Find the index of the ending char for the match. For instance, to
	 * search for an ending parenthesis, starting from the opening
	 * parenthesis, you would pass findEndingMatch(str, 0, '(', ')', '\\');
	 * <i>(The backslash would act to escape any parentheses. eg: \\(
	 * would not be counted.)</i> The method call would return the index
	 * of the ending parenthesis that is paired with the index of 0.
	 * 
	 * @param str The String to search for the pair to the start char.
	 * @param index The index of the start char in the pair.
	 * @param startChar The char that starts off the pair. eg. '('
	 * @param endChar The char that ends the pair. eg: ')'
	 * @param escapeChar The char that escapes a start or end char, of
	 * 		there is no escape char, pass '(char)0'.
	 * @return The index of the pair to the starting char, if no pair is
	 * 		found then -1 is returned.
	 */
	public static int findEndingMatch(String str, int index, char startChar, char endChar, char escapeChar)
	{
		int scope = 0;
		
		while (index < str.length())
		{
			char c = str.charAt(index);
			
			if (c == escapeChar)
			{
				if (index < str.length() - 1)
				{
					char next = str.charAt(index + 1);
					
					if (next == startChar || next == endChar)
					{
						index++;
					}
				}
			}
			else if (c == startChar)
			{
				scope++;
			}
			else if (c == endChar)
			{
				scope--;
				
				if (scope == 0)
				{
					return index;
				}
			}
			
			index++;
		}
		
		return -1;
	}
	
	/**
	 * Get the next possible index in the String that is not a whitespace
	 * character.
	 * 
	 * @param str The String to search through.
	 * @param index The index to start the search at.
	 * @return The next possible index in the String that is not
	 * 		whitespace.
	 */
	public static int findNextNonWhitespaceIndex(String str, int index)
	{
		return findNextNonWhitespaceIndex(str, index, 1);
	}
	
	/**
	 * Get the next possible index in the String that is not a whitespace
	 * character, while moving in the specified direction.
	 * 
	 * @param str The String to search through.
	 * @param index The index to start the search at.
	 * @param direction The direction in which to increment the index.
	 * @return The next possible index in the String that is not
	 * 		whitespace.
	 */
	public static int findNextNonWhitespaceIndex(String str, int index, int direction)
	{
		while (index >= 0 && index < str.length())
		{
			char c = str.charAt(index);
			
			if (c != ' ' && c != '\n' && c != '\t' && c != '\r')
			{
				return index;
			}
			
			index += direction;
		}
		
		return -1;
	}
}
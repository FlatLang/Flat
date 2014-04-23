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

import java.util.ArrayList;

/**
 * Class that contains methods used for finding data about
 * a String.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 13, 2014 at 9:38:42 PM
 * @version	v0.2 Mar 13, 2014 at 9:38:42 PM
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
	
	/**
	 * Get the next possible index in the String that is a whitespace
	 * character.
	 * 
	 * @param str The String to search through.
	 * @param index The index to start the search at.
	 * @return The next possible index in the String that is
	 * 		whitespace.
	 */
	public static int findNextWhitespaceIndex(String str, int index)
	{
		return findNextWhitespaceIndex(str, index, 1);
	}
	
	/**
	 * Get the next possible index in the String that is a whitespace
	 * character, while moving in the specified direction.
	 * 
	 * @param str The String to search through.
	 * @param index The index to start the search at.
	 * @param direction The direction in which to increment the index.
	 * @return The next possible index in the String that is
	 * 		whitespace.
	 */
	public static int findNextWhitespaceIndex(String str, int index, int direction)
	{
		while (index >= 0 && index < str.length())
		{
			char c = str.charAt(index);
			
			if (c == ' ' || c == '\n' || c == '\t' || c == '\r')
			{
				return index;
			}
			
			index += direction;
		}
		
		return -1;
	}
	
	/**
	 * Escape the spaces in an input String.<br>
	 * <br>
	 * For example: An input of "this is a test" would return
	 * "this\ is\ a\ test"
	 * 
	 * @param input The String to escape the spaces from.
	 * @return The input String with escaped spaces.
	 */
	public static String escapeSpaces(String input)
	{
		StringBuilder output = new StringBuilder(input);
		
		int           index  = output.indexOf(" ");
		
		while (index >= 0)
		{
			output.insert(index, "\\");
			
			index = output.indexOf(" ", index + 2);
		}
		
		return output.toString();
	}
	
	/**
	 * Split the src by the commas. Makes sure not to split commas that
	 * are within parentheses and quotes.
	 * 
	 * @param src The String to split the commas from.
	 * @return An array of Strings containing the Strings that were split.
	 */
	public static String[] splitCommas(String src)
	{
		ArrayList<String> strs = new ArrayList<String>();
		
		int oldIndex =  0;
		int index    = -1;
		
		StringBuilder builder = new StringBuilder();
		
		while ((index = Regex.indexOf(src, index + 1, ',', new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true })) > 0)
		{
			builder = new StringBuilder(src.substring(oldIndex, index));
			
			trimSurroundingWhitespace(builder);

			strs.add(builder.toString());
			
			oldIndex = index + 1;
		}
		
		builder = new StringBuilder(src.substring(oldIndex, src.length()));
		
		trimSurroundingWhitespace(builder);
		
		strs.add(builder.toString());
		
		return strs.toArray(new String[0]);
	}
	
	/**
	 * Trim the surrounding whitespace off of the String's ends.
	 * 
	 * @param str The String to trim the whitespace from.
	 */
	public static String trimSurroundingWhitespace(String str)
	{
		StringBuilder builder = new StringBuilder(str);
		
		trimSurroundingWhitespace(builder);
		
		return builder.toString();
	}
	
	/**
	 * Trim the surrounding whitespace off of the StringBuilder's ends.
	 * 
	 * @param builder The builder to trim the whitespace from.
	 */
	public static void trimSurroundingWhitespace(StringBuilder builder)
	{
		while (builder.length() > 0 && (builder.charAt(0) == ' ' || builder.charAt(0) == '\n' || builder.charAt(0) == '\t'))
		{
			builder.deleteCharAt(0);
		}
		
		while (builder.length() > 0 && (builder.charAt(builder.length() - 1) == ' ' || builder.charAt(builder.length() - 1) == '\n' || builder.charAt(builder.length() - 1) == '\t'))
		{
			builder.deleteCharAt(builder.length() - 1);
		}
	}
}
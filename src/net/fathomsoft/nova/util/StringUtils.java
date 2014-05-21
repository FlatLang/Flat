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
import java.util.regex.Matcher;

/**
 * Class that contains methods used for finding data about
 * a String.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 13, 2014 at 9:38:42 PM
 * @version	v0.2.4 May 17, 2014 at 9:55:04 PM
 */
public class StringUtils
{
	public static final String	BINARY_OPERATORS[] = new String[] { "+", "-", "/", "*", "==", "!=", "&&", "||", "<=", ">=", "<", ">", "%" };
	
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
	 * Find the index of the ending quote, given the index of the start
	 * quote.
	 * 
	 * @param value The String to search within.
	 * @param start The index of the starting quote.
	 * @return The index of the ending quote. If an end is not found, -1
	 * 		is returned instead.
	 */
	public static int findEndingQuote(String value, int start)
	{
		while (++start < value.length())
		{
			if (value.charAt(start) == '"')
			{
				if (start > 0 && value.charAt(start - 1) != '\\')
				{
					return start;
				}
			}
		}
		
		return -1;
	}
	
	/**
	 * Search through the given value for any match within the strings
	 * array.
	 * 
	 * @param value The String to search through.
	 * @param strings The array to search through.
	 */
	public static Bounds findStrings(String value, String strings[])
	{
		return findStrings(value, 0, strings);
	}
	
	/**
	 * Search through the given value for any match within the strings
	 * array.
	 * 
	 * @param value The String to search through.
	 * @param start The index to start the search at.
	 * @param strings The array to search through.
	 */
	public static Bounds findStrings(String value, int start, String strings[])
	{
		Bounds bounds = new Bounds(-1, -1);
		
		while (start < value.length())
		{
			if (value.charAt(start) == '"')
			{
				start = findEndingQuote(value, start) + 1;
				
				continue;
			}
			
			for (String str : strings)
			{
				for (int i = 0; i < str.length() && start + i < value.length(); i++)
				{
					if (value.charAt(start + i) != str.charAt(i))
					{
						break;
					}
					
					if (i == str.length() - 1)
					{
						bounds.setStart(start);
						bounds.setEnd(start + str.length());
						
						return bounds;
					}
				}
			}
			
			start++;
		}
		
		return bounds;
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
	
	/**
	 * Trim the given statement to strictly an identifier.<br>
	 * <br>
	 * For example: A call of "<code>trimToIdentifier("**identifer322&+")<code>"
	 * would return "<code>identifier322</code>"
	 * 
	 * @param statement The statement to trim down.
	 * @return The trimmed down identifier.
	 */
	public static String trimToIdentifier(String statement)
	{
		Matcher matcher = Patterns.IDENTIFIER_BOUNDARIES.matcher(statement);
		
		matcher.find();
		
		int start = matcher.start();
		
		matcher.find();
		
		int end = matcher.start();
		
		return statement.substring(start, end);
	}
	
	/**
	 * Get whether the given String only contains chars of the
	 * type in the given needles array.
	 * 
	 * @param haystack The String to test.
	 * @param needles The chars that the String can contain.
	 * @return Whether or not the String passes the test.
	 */
	public static boolean containsOnly(String haystack, char needles[])
	{
		for (int i = 0; i < haystack.length(); i++)
		{
			char c = haystack.charAt(i);
			
			if (!containsChar(needles, c))
			{
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Search for the given char 'c' in the given array.
	 * 
	 * @param array The array to search for 'c' in.
	 * @param c The char to search for.
	 * @return Whether or not the array contains the 'c' char.
	 */
	public static boolean containsChar(char array[], char c)
	{
		return searchChar(array, c) >= 0;
	}
	
	/**
	 * Search for the given char 'c' in the given array, if it is found,
	 * return the index at which it was found.
	 * 
	 * @param array The array to search for 'c' in.
	 * @param c The char to search for.
	 * @return The index in the array of the occurrence of char 'c', if
	 * 		it was found in the array.
	 */
	public static int searchChar(char array[], char c)
	{
		for (int i = array.length - 1; i >= 0; i--)
		{
			if (array[i] == c)
			{
				return i;
			}
		}
		
		return -1;
	}
	
//	public String joinIdentifiers(String statement)
//	{
//		String data[][] = splitIdentifiers(statement);
//		
//		
//	}
	
//	public static String[][] splitIdentifiers(String statement)
//	{
//		ArrayList<String> identifiers = new ArrayList<String>();
//		ArrayList<String> delimiters  = new ArrayList<String>();
//		
//		Matcher matcher = Patterns.IDENTIFIER.matcher(statement);
//		
//		int prev = 0;
//		
//		while (matcher.find())
//		{
//			String text = matcher.group();
//			
//			if (SyntaxUtils.isValidIdentifier(text))
//			{
//				identifiers.add(text);
//			}
//			else if (prev)
//			{
//				delimiters.add(text);
//			}
//		}
//		
//		String returnData[][] = new String[2][];
//		
//		returnData[0] = identifiers.toArray(new String[0]);
//		returnData[1] = delimiters.toArray(new String[0]);
//		
//		return returnData;
//	}
	
	/**
	 * Find the String value of the data that is in between the last
	 * two words in the array.<br>
	 * <br>
	 * For example: A call of <code>findLastMissingString([this, is, a, test],
	 * "this.is->a==test")</code> would return "=="
	 * 
	 * @param words The list of words that lead of up the desired value.
	 * @param statement The String to search the words with.
	 * @return The data that was found.
	 */
	public static String findLastMissingString(ArrayList<String> words, String statement)
	{
		int index = 0;
		
		for (int i = 0; i < words.size() - 1; i++)
		{
			String word = words.get(i);
			
			index  = statement.indexOf(word, index);
			
			index += word.length();
		}
		
		String lastWord = words.get(words.size() - 1);
		
		String value    = statement.substring(index, statement.indexOf(lastWord, index));
		
		return value;
	}
	
	public static int reverseIndexOf(String haystack, String needle, int start)
	{
		while (start >= needle.length() - 1)
		{
			if (reverseMatches(haystack, needle, start))
			{
				return start - needle.length() + 1;
			}
			
			start--;
		}
		
		return -1;
	}
	
	/**
	 * Get whether or not the needle matches the haystack at the given
	 * index.<br>
	 * <br>
	 * For example: A call of <code>reverseMatches("this will work",
	 * "will", 8)</code> would return true.
	 * 
	 * @param haystack The String to search for the needle in.
	 * @param needle The String to search in the haystack for.
	 * @param index The index to search for the match at.
	 * @return Whether or not there is a match at the given index.
	 */
	public static boolean reverseMatches(String haystack, String needle, int index)
	{
		for (int i = 0; i < needle.length(); i++)
		{
			if (haystack.charAt(index - i) != needle.charAt(needle.length() - i - 1))
			{
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Generate a modified version of the given String in which for every
	 * character after the "after" char type that is the same type as the
	 * given "removeType" char type will be removed from the String.<br>
	 * <br>
	 * For example: Calling "<code>removeTypeAfterChar("this is an example", 's', ' ')</code>"
	 * would return a String "thisisan example"
	 * 
	 * @param str The String to generate a modified version of.
	 * @param after The char type to search for the remove type directly
	 * 		after.
	 * @param removeType The type of char to remove.
	 * @return The newly formatted String.
	 */
	public static String removeTypeAfterChar(String str, char after, char removeType)
	{
		StringBuilder builder = new StringBuilder(str);
		
		int index = builder.indexOf(after + "");
		
		while (index >= 0)
		{
			char c = builder.charAt(++index);
			
			while (c == removeType)
			{
				builder.deleteCharAt(index);
				
				c = builder.charAt(index);
			}
			
			index = builder.indexOf(after + "", index);
		}
		
		return builder.toString();
	}
}
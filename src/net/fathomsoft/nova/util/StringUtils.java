package net.fathomsoft.nova.util;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * Class that contains methods used for finding data about
 * a String.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 13, 2014 at 9:38:42 PM
 * @version	v0.2.19 Jul 26, 2014 at 12:30:24 AM
 */
public class StringUtils
{
	public static final char	WHITESPACE[]       = new char[] { ' ', '\n', '\t', '\r' };
	public static final char	SYMBOLS_CHARS[]    = new char[] { '-', '+', '~', '!', '=', '%', '^', '&', '|', '*', '/', '>', '<', ',', '"', '\'', '[', ']', '{', '}', ';', '(', ')' };
	public static final char	STMT_CONT_CHARS[]  = new char[] { '-', '+', '~', '!', '=', '%', '^', '&', '|', '*', '/', '>', '<', ',', '.' };
	
	public static final String	BINARY_OPERATORS[] = new String[] { "+", "-", "/", "*", "==", "!=", "&&", "||", "<=", ">=", "<<", ">>", "<", ">", "%" };
	public static final String	UNARY_OPERATORS[]  = new String[] { "--", "-", "++", "!" };
	public static final String	SYMBOLS[];
	
	static
	{
		SYMBOLS = new String[SYMBOLS_CHARS.length];
		
		for (int i = 0; i < SYMBOLS.length; i++)
		{
			SYMBOLS[i] = SYMBOLS_CHARS[i] + "";
		}
	}
	
	/**
	 * Get the next sequence of symbols, if they are symbols.<br>
	 * For example:
	 * <blockquote><pre>
	 * // Scenario 1
	 * getGroupedSymbols("number = ++num2", 9);
	 * 
	 * // Scenario 2
	 * getGroupedSymbols("number = ++num2", 8);</pre></blockquote>
	 * Scenario 1 returns the "++" String and scenario 2 returns ""
	 * because the index that it starts at is whitespace, not a
	 * symbol.
	 * 
	 * @param str The String to search through for the symbols.
	 * @param start The index to start the search at.
	 * @return A String containing the grouped chars. If there
	 * 		were no grouped chars, then an empty String is returned.
	 */
	public static String getGroupedSymbols(CharSequence str, int start)
	{
		return getGroupedSymbols(str, start, 1);
	}
	
	/**
	 * Get the next sequence of symbols, if they are symbols.<br>
	 * For example:
	 * <blockquote><pre>
	 * // Scenario 1
	 * getGroupedSymbols("number = ++num2", 9, 1);
	 * 
	 * // Scenario 2
	 * getGroupedSymbols("number = ++num2", 10, -1);</pre></blockquote>
	 * Scenario 1 and 2 both return the "++" String because they are
	 * searching the same symbols at opposite ends and directions.
	 * 
	 * @param str The String to search through for the symbols.
	 * @param start The index to start the search at.
	 * @param direction The direction to search for the symbols at.
	 * @return A String containing the grouped chars. If there
	 * 		were no grouped chars, then an empty String is returned.
	 */
	public static String getGroupedSymbols(CharSequence str, int start, int direction)
	{
		return getGroupedChars(str, SYMBOLS_CHARS, start, direction);
	}
	
	/**
	 * Get the next sequence of characters, if they are contained within
	 * the given chars array.<br>
	 * For example:
	 * <blockquote><pre>
	 * // Scenario 1
	 * getGroupedChars("number = ++num2", StringUtils.SYMBOLS_CHARS, 9, 1);
	 * 
	 * // Scenario 2
	 * getGroupedChars("number = ++num2", StringUtils.SYMBOLS_CHARS, 10, -1);</pre></blockquote>
	 * Scenario 1 and 2 both return the "++" String because they are
	 * searching the same symbols at opposite ends and directions.
	 * 
	 * @param str The String to search through for the characters.
	 * @param start The index to start the search at.
	 * @param direction The direction to search for the characters at.
	 * @return A String containing the grouped chars. If there
	 * 		were no grouped chars, then an empty String is returned.
	 */
	public static String getGroupedChars(CharSequence str, char chars[], int start, int direction)
	{
		int index = start;
		
		while (index < str.length() && index >= 0 && StringUtils.containsChar(chars, str.charAt(index)))
		{
			index += direction;
		}
		
		if (direction < 0)
		{
			return str.subSequence(index + 1, start + 1).toString();
		}
		
		return str.subSequence(start, index).toString();
	}
	
	/**
	 * Find the next word in the given source starting at the beginning
	 * of the String. If there are no words available, an empty String is
	 * returned.<br>
	 * For example:
	 * <blockquote><pre>
	 * // Scenario 1
	 * findNextWord("number = ++num2");
	 * 
	 * // Scenario 2
	 * findNextWord("asdf = ++num2");</pre></blockquote>
	 * Scenario 1 returns "number"<br>
	 * Scenario 2 returns "asdf"
	 * 
	 * @param source The source to search within.
	 * @return The next available word if available, else it returns
	 * 		an empty String.
	 */
	public static String findNextWord(CharSequence source)
	{
		return findNextWord(source, 0);
	}
	
	/**
	 * Find the next word in the given source starting at the given
	 * index. If there are no words available, an empty String is
	 * returned.<br>
	 * For example:
	 * <blockquote><pre>
	 * // Scenario 1
	 * findNextWord("number = ++num2", 0);
	 * 
	 * // Scenario 2
	 * findNextWord("number = ++num2", 8);</pre></blockquote>
	 * Scenario 1 returns "number"<br>
	 * Scenario 2 returns "num2"
	 * 
	 * @param source The source to search within.
	 * @param start The index to start the search.
	 * @return The next available word if available, else it returns
	 * 		an empty String.
	 */
	public static String findNextWord(CharSequence source, int start)
	{
		return findNextWord(source, start, 1);
	}
	
	/**
	 * Find the next word in the given source starting at the given
	 * index. If there are no words available, an empty String is
	 * returned.<br>
	 * For example:
	 * <blockquote><pre>
	 * // Scenario 1
	 * findNextWord("number = ++num2", 0, 1);
	 * 
	 * // Scenario 2
	 * findNextWord("number = ++num2", 8, -1);</pre></blockquote>
	 * Scenario 1 and 2 both return "number"
	 * 
	 * @param source The source to search within.
	 * @param start The index to start the search.
	 * @param direction The direction to search for the word in.
	 * @return The next available word if available, else it returns
	 * 		an empty String.
	 */
	public static String findNextWord(CharSequence source, int start, int direction)
	{
		start = StringUtils.findNextLetterIndex(source, start, direction, false, true);
		
		int index = StringUtils.findNextLetterIndex(source, start + direction, direction, true, true);
		
		if (direction < 0)
		{
			return source.subSequence(index + 1, start + 1).toString();
		}
		
		return source.subSequence(start, index).toString();
	}
	
	/**
	 * Find the next index in which a letter resides, starting at the
	 * given index. If there are no letters available, -1 is returned.<br>
	 * For example:
	 * <blockquote><pre>
	 * // Scenario 1
	 * findNextWord("number = ++num2", 0, 1);
	 * 
	 * // Scenario 2
	 * findNextWord("number = ++num2", 8, -1);</pre></blockquote>
	 * Scenario 1 returns 0<br>
	 * Scenario 2 returns 5
	 * 
	 * @param source The source to search within.
	 * @param start The index to start the search.
	 * @param direction The direction to search for the word in.
	 * @return The next index in which a letter resides. If no letters
	 * 		are found, -1 is returned.
	 */
	public static int findNextLetterIndex(CharSequence source, int start, int direction)
	{
		return findNextLetterIndex(source, start, direction, false);
	}
	
	/**
	 * Find the next index in which a letter resides, starting at the
	 * given index. If there are no letters available, -1 is returned.
	 * However, if the <u>opposite</u> param is true, then the opposite
	 * of what was stated above is done.<br>
	 * For example:
	 * <blockquote><pre>
	 * // Scenario 1
	 * findNextWord("number = ++num2", 0, 1, true);
	 * 
	 * // Scenario 2
	 * findNextWord("number = ++num2", 8, -1, true);</pre></blockquote>
	 * Scenario 1 returns 6<br>
	 * Scenario 2 returns 8
	 * 
	 * @param source The source to search within.
	 * @param start The index to start the search.
	 * @param direction The direction to search for the word in.
	 * @param opposite Whether or not to search for anything that is NOT
	 * 		a letter (instead of searching for a letter).
	 * @return The next index in which a letter resides. If no letters
	 * 		are found, -1 is returned.
	 */
	public static int findNextLetterIndex(CharSequence source, int start, int direction, boolean opposite)
	{
		return findNextLetterIndex(source, start, direction, opposite, false);
	}
	
	/**
	 * Find the next index in which a letter resides, starting at the
	 * given index. If there are no letters available, -1 is returned.
	 * However, if the <u>opposite</u> param is true, then the opposite
	 * of what was stated above is done. Passing true for the <u>bound</u>
	 * parameter makes sure that -1 is never returned (It will return the
	 * end-point of whichever direction that the method was searching
	 * in).<br>
	 * For example:
	 * <blockquote><pre>
	 * // Scenario 1
	 * findNextWord("number = ++num2", 11, 1, true);
	 * 
	 * // Scenario 2
	 * findNextWord("number = ++num2", 4, -1, true, true);</pre></blockquote>
	 * Scenario 1 returns 15<br>
	 * Scenario 2 returns 0
	 * 
	 * @param source The source to search within.
	 * @param start The index to start the search.
	 * @param direction The direction to search for the word in.
	 * @param opposite Whether or not to search for anything that is NOT
	 * 		a letter (instead of searching for a letter).
	 * @param bound Whether or not to bound the result returned if the a
	 * 		result was not found.
	 * @return The next index in which a letter resides.
	 */
	public static int findNextLetterIndex(CharSequence source, int start, int direction, boolean opposite, boolean bound)
	{
		while (start >= 0 && start < source.length())
		{
			char c = source.charAt(start);
			
			if ((containsChar(WHITESPACE, c) || containsChar(SYMBOLS_CHARS, c)) == opposite)
			{
				return start;
			}
			
			start += direction;
		}
		
		if (bound)
		{
			if (direction > 0)
			{
				return source.length();
			}
			
			return 0;
		}
		
		return -1;
	}
	
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
	public static int findEndingMatch(CharSequence str, int index, char startChar, char endChar)
	{
		return findEndingMatch(str, index, startChar, endChar, '\0');
	}

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
	 * @param direction The direction in which to search for the match in.
	 * @return The index of the pair to the starting char, if no pair is
	 * 		found then -1 is returned.
	 */
	public static int findEndingMatch(CharSequence str, int index, char startChar, char endChar, int direction)
	{
		return findEndingMatch(str, index, startChar, endChar, '\0', direction);
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
	public static int findEndingMatch(CharSequence str, int index, char startChar, char endChar, char escapeChar)
	{
		return findEndingMatch(str, index, startChar, endChar, escapeChar, 1);
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
	 * @param direction The direction in which to search for the match in.
	 * @return The index of the pair to the starting char, if no pair is
	 * 		found then -1 is returned.
	 */
	public static int findEndingMatch(CharSequence str, int index, char startChar, char endChar, char escapeChar, int direction)
	{
		if (direction < 0)
		{
			char temp = startChar;
			startChar = endChar;
			endChar   = temp;
		}
		
		int scope = 0;
		
		while (index >= 0 && index < str.length())
		{
			char c = str.charAt(index);
			
			if (c == escapeChar && direction > 0)
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
			else if (c == startChar && (direction > 0 || index > 0 && str.charAt(index - 1) != escapeChar))
			{
				scope++;
			}
			else if (c == endChar && (direction > 0 || index > 0 && str.charAt(index - 1) != escapeChar))
			{
				scope--;
				
				if (scope == 0)
				{
					return index;
				}
			}
			else if (c == '"' || c == '\'')
			{
				index = findEndingChar(str, c, index, direction);
			}
			
			index += direction;
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
	public static int findEndingQuote(CharSequence value, int start)
	{
		return findEndingQuote(value, start, 1);
	}
	
	/**
	 * Find the index of the ending quote, given the index of the start
	 * quote.
	 * 
	 * @param value The String to search within.
	 * @param start The index of the starting quote.
	 * @param direction The direction in which to search the given String.
	 * @return The index of the ending quote. If an end is not found, -1
	 * 		is returned instead.
	 */
	public static int findEndingQuote(CharSequence value, int start, int direction)
	{
		return findEndingChar(value, '"', start, direction);
	}
	
	/**
	 * Find the index of the ending char that matches the given 'c' param,
	 * given the index of the start char.
	 * 
	 * @param value The String to search within.
	 * @param start The index of the starting quote.
	 * @param direction The direction in which to search the given String.
	 * @return The index of the matching char. If an end is not found, -1
	 * 		is returned instead.
	 */
	public static int findEndingChar(CharSequence value, char c, int start, int direction)
	{
		start += direction;
		
		while (start >= 0 && start < value.length())
		{
			if (value.charAt(start) == c)
			{
				if (start == 0 || value.charAt(start - 1) != '\\')
				{
					return start;
				}
			}
			
			start += direction;
		}
		
		return -1;
	}
	
	/**
	 * Search through the given value for any match within the strings
	 * array.
	 * 
	 * @param value The String to search through.
	 * @param strings The array to search through.
	 * @return A Bounds instance with the end points of the found String.
	 * 		If a String is not found, [-1, -1] is returned.
	 */
	public static Bounds findStrings(CharSequence value, String strings[])
	{
		return findStrings(value, strings, 0);
	}
	
	/**
	 * Search through the given value for any match within the strings
	 * array.
	 * 
	 * @param value The String to search through.
	 * @param strings The array to search through.
	 * @param start The index to start the search at.
	 * @return A Bounds instance with the end points of the found String.
	 * 		If a String is not found, [-1, -1] is returned.
	 */
	public static Bounds findStrings(CharSequence value, String strings[], int start)
	{
		return findStrings(value, strings, start, 1);
	}
	
	/**
	 * Search through the given value for any match within the strings
	 * array.
	 * 
	 * @param value The String to search through.
	 * @param strings The array to search through.
	 * @param start The index to start the search at.
	 * @param direction The direction to search in.
	 * @return A Bounds instance with the end points of the found String.
	 * 		If a String is not found, [-1, -1] is returned.
	 */
	public static Bounds findStrings(CharSequence value, String strings[], int start, int direction)
	{
		while (start >= 0 && start < value.length())
		{
			char c = value.charAt(start);
			
			if (c == '"')
			{
				start = findEndingQuote(value, start, direction) + direction;
				
				continue;
			}
			else if (c == '(' && direction > 0 || c == ')' && direction < 0)
			{
				start = findEndingMatch(value, start, '(', ')', direction) + direction;
				
				if (start <= 0 || start >= value.length())
				{
					return Bounds.EMPTY;
				}
				
				continue;
			}
			else if (c == '[' && direction > 0 || c == ']' && direction < 0)
			{
				start = findEndingMatch(value, start, '[', ']', direction) + direction;
				
				if (start == 0)
				{
					return Bounds.EMPTY;
				}
				
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
						Bounds bounds = new Bounds(start, start + str.length());
						
						return bounds;
					}
				}
			}
			
			start += direction;
		}
		
		return Bounds.EMPTY;
	}
	
	/**
	 * Search the given haystack String for any match within the strings
	 * array starting at the given char start index. If a match is
	 * encountered, the String that makes the match is returned. If not
	 * match is made, null is returned.
	 * 
	 * @param haystack The String try to match.
	 * @param strings The array to search through to try and match.
	 * @param start The index to start to search the value at.
	 * @return The String in the strings array that made the match. If no
	 * 		match was found, null is returned.
	 */
	public static String findMatch(String haystack, String strings[], int start)
	{
		Bounds bounds = findStrings(haystack, strings, start);
		
		if (bounds.getStart() < 0)
		{
			return null;
		}
		
		return haystack.substring(bounds.getStart(), bounds.getEnd());
	}
	
	/**
	 * Search the given haystack String for any match within the strings
	 * array at the given char offset index. If a match is encountered, the
	 * String that makes the match is returned. If not match is made, null
	 * is returned.
	 * 
	 * @param haystack The String try to match at the given index.
	 * @param strings The array to search through to try and match.
	 * @param index The index to search the value at.
	 * @return The String in the strings array that made the match. If no
	 * 		match was found, null is returned.
	 */
	public static String getMatch(String haystack, String strings[], int index)
	{
		for (String str : strings)
		{
			for (int i = 0; i < str.length() && index + i < haystack.length(); i++)
			{
				if (haystack.charAt(index + i) != str.charAt(i))
				{
					break;
				}
				
				if (i == str.length() - 1)
				{
					return str;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Search the given haystack String for any match within the strings
	 * array at the given char offset index.
	 * 
	 * @param haystack The String try to match at the given index.
	 * @param strings The array to search through to try and match.
	 * @param index The index to search the value at.
	 * @return Whether or not there is a match within the strings array at
	 * 		the given index in the haystack String.
	 */
	public static boolean matches(String haystack, String strings[], int index)
	{
		return getMatch(haystack, strings, index) != null;
	}
	
	/**
	 * Check to see if the character is a new-line character. If so,
	 * increment the line-number variable.
	 * 
	 * @param start The index to start the search at.
	 * @param statementStart The index to end the search at.
	 * @param source The source String to search in.
	 * @return The number of lines that the iterator encountered
	 * 		within the given bounds.
	 */
	public static int numNewLines(int start, int statementStart, CharSequence source)
	{
		int lines = 0;
		
		for (int i = start; i < statementStart; i++)
		{
			if (source.charAt(i) == '\n')
			{
				lines++;
			}
		}
		
		return lines;
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
	public static int findNextNonWhitespaceIndex(CharSequence str, int index)
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
	public static int findNextNonWhitespaceIndex(CharSequence str, int index, int direction)
	{
		return findNextCharacter(str, WHITESPACE, index, direction, true);
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
	public static int findNextWhitespaceIndex(CharSequence str, int index)
	{
		return findNextCharacter(str, WHITESPACE, index, 1);
	}
	
	/**
	 * Get the next possible index in the String that contains a char
	 * in the given character array, while moving in the specified
	 * direction. If none exists, -1 is returned.
	 * 
	 * @param str The String to search through.
	 * @param index The index to start the search at.
	 * @param direction The direction in which to increment the index.
	 * @return The next possible index in the String that is
	 * 		a character in the given array.
	 */
	public static int findNextCharacter(CharSequence str, char chars[], int index, int direction)
	{
		return findNextCharacter(str, chars, index, direction, false);
	}
	
	/**
	 * Get the next possible index in the String that contains a char
	 * in the given character array, while moving in the specified
	 * direction. If none exists, -1 is returned.
	 * 
	 * @param str The String to search through.
	 * @param index The index to start the search at.
	 * @param direction The direction in which to increment the index.
	 * @param opposite Whether or not to search for or against what is
	 * 		within the character array.
	 * @return The next possible index in the String that is
	 * 		a character in the given array.
	 */
	public static int findNextCharacter(CharSequence str, char chars[], int index, int direction, boolean opposite)
	{
		while (index >= 0 && index < str.length())
		{
			if (containsChar(chars, str.charAt(index)) != opposite)
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
		
		while ((index = SyntaxUtils.findCharInBaseScope(src, ',', index + 1)) >= 0)//(index = Regex.indexOf(src, index + 1, ',', new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true })) > 0)
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
	 * Search for the char at the given index in the given source String
	 * in the given array.
	 * 
	 * @param source The String to search through.
	 * @param array The array to search for 'c' in.
	 * @param index The index to get the char from the source String at.
	 * @return Whether or not the array contains the 'c' char.
	 */
	public static boolean containsChar(CharSequence source, char array[])
	{
		for (int i = 0; i < source.length(); i++)
		{
			if (containsChar(source, array, i))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Search for the char at the given index in the given source String
	 * in the given array.
	 * 
	 * @param source The String to search through.
	 * @param array The array to search for 'c' in.
	 * @param index The index to get the char from the source String at.
	 * @return Whether or not the array contains the 'c' char.
	 */
	public static boolean containsChar(CharSequence source, char array[], int index)
	{
		if (index < 0 || index >= source.length())
		{
			return false;
		}
		
		return searchChar(array, source.charAt(index)) >= 0;
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
	
	/**
	 * Search for the given String 's' in the given array.
	 * 
	 * @param array The array to search for 's' in.
	 * @param s The String to search for.
	 * @return Whether or not the array contains the 'c' String.
	 */
	public static boolean containsString(String array[], String s)
	{
		return searchString(array, s) >= 0;
	}
	
	/**
	 * Search for the given String 's' in the given array, if it is found,
	 * return the index at which it was found.
	 * 
	 * @param array The array to search for 's' in.
	 * @param s The String to search for.
	 * @return The index in the array of the occurrence of String 's', if
	 * 		it was found in the array.
	 */
	public static int searchString(String array[], String s)
	{
		for (int i = array.length - 1; i >= 0; i--)
		{
			if (array[i].equals(s))
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
	
	/**
	 * Search backwards for the index of the next available match of the
	 * needle within the haystack String, if a match exists. The search
	 * starts at the given index.
	 * 
	 * @param haystack The String to search through.
	 * @param needle The String to search for.
	 * @param start The index to start the reverse search for.
	 * @return The next available index.
	 */
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
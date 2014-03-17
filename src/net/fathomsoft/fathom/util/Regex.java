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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 10, 2014 at 3:12:48 AM
 * @since	v
 * @version	Jan 10, 2014 at 3:12:48 AM
 * @version	v
 */
public class Regex
{
	public static boolean matches(String src, int index, Pattern pattern)
	{
		Matcher matcher = pattern.matcher(src);
		
		if (!matcher.find(index))
		{
			return false;
		}
		
		return matcher.start() == index;
	}
	
	public static int indexOf(String src, String regex)
	{
		return indexOf(src, 0, regex);
	}
	
	public static int indexOf(String src, Matcher matcher)
	{
		return indexOf(src, 0, matcher);
	}
	
	public static int indexOf(String src, Pattern pattern)
	{
		return indexOf(src, 0, pattern);
	}
	
	public static int indexOf(String src, int start, String regex)
	{
		Pattern pattern = Pattern.compile(regex);
		
		return indexOf(src, start, pattern);
	}

	public static int indexOf(String src, int start, Pattern pattern)
	{
		Matcher matcher = pattern.matcher(src);
		
		return indexOf(src, start, matcher);
	}
	
	public static int indexOf(String src, int start, Matcher matcher)
	{
		if (matcher.find(start))
		{
			return matcher.start();
		}
		
		return -1;
	}
	
	public static int indexOf(String src, char c)
	{
		return indexOf(src, 0, c);
	}
	
	public static int indexOf(String src, int start, char c)
	{
		return indexOf(src, start, c, null, null, 1);
	}
	
	public static int indexOf(String src, char c, char excludePrefixes[], char excludePostfixes[])
	{
		return indexOf(src, 0, c, excludePrefixes, excludePostfixes, 1);
	}
	
	public static int lastIndexOf(String src, char c)
	{
		return lastIndexOf(src, src.length() - 1, c);
	}
	
	public static int lastIndexOf(String src, int start, char c)
	{
		return indexOf(src, start, c, null, null, -1);
	}
	
	public static int lastIndexOf(String src, char c, char excludePrefixes[], char excludePostfixes[])
	{
		return indexOf(src, src.length() - 1, c, excludePrefixes, excludePostfixes, -1);
	}
	
	public static int indexOf(String src, int start, char c, int direction)
	{
		return indexOf(src, start, c, null, null, direction);
	}
	
	public static int indexOf(String src, char c, char excludePrefixes[], char excludePostfixes[], int direction)
	{
		return indexOf(src, 0, c, excludePrefixes, excludePostfixes, direction);
	}
	
	public static int indexOf(String src, int start, char c, char excludePrefixes[], char excludePostfixes[], int direction)
	{
		return indexOf(src, start, c, excludePrefixes, excludePostfixes, null, null, null, null, direction);
	}
	
	public static int lastIndexOf(String src, char c, char excludePrefixes[], char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[], boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[])
	{
		return indexOf(src, src.length() - 1, c, excludePrefixes, excludePostfixes, excludeBinary, excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, -1);
	}
	
	public static int indexOf(String src, char c, char excludePrefixes[], char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[], boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[])
	{
		return indexOf(src, 0, c, excludePrefixes, excludePostfixes, excludeBinary, excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, 1);
	}
	
	public static int indexOf(String src, char c, char excludePrefixes[], char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[], boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[], int direction)
	{
		return indexOf(src, 0, c, excludePrefixes, excludePostfixes, excludeBinary, excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, direction);
	}

	public static int indexOf(String src, int start, char c, char excludePrefixes[], char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[], boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[])
	{
		return indexOf(src, start, c, excludePrefixes, excludePostfixes, excludeBinary, excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, 1);
	}
	
	public static int indexOfExcludeText(String src, int start, char c)
	{
		return indexOf(src, start, c, new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true });
	}
	
	public static int indexOfExcludeTextAndParenthesis(String src, int start, char c)
	{
		return indexOf(src, start, c, new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true });
	}
	
	public static int indexOfExcludeText(String src, char c)
	{
		return indexOf(src, 0, c, new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true });
	}
	
	public static int indexOfExcludeTextAndParenthesis(String src, char c)
	{
		return indexOf(src, 0, c, new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true });
	}
	
	public static int indexOfExcludeText(String src, int start, char cs[])
	{
		return indexOf(src, start, cs, new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true });
	}
	
	public static int indexOfExcludeTextAndParenthesis(String src, int start, char cs[])
	{
		return indexOf(src, start, cs, new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true });
	}
	
	public static int indexOfExcludeText(String src, char cs[])
	{
		return indexOf(src, 0, cs, new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true });
	}
	
	public static int indexOfExcludeTextAndParenthesis(String src, char cs[])
	{
		return indexOf(src, 0, cs, new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true });
	}
	
	public static int lastIndexOfExcludeText(String src, int start, char c)
	{
		return indexOf(src, start, c, new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true }, -1);
	}
	
	public static int lastIndexOfExcludeTextAndParenthesis(String src, int start, char c)
	{
		return indexOf(src, start, c, new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true }, -1);
	}
	
	public static int lastIndexOfExcludeText(String src, char c)
	{
		return indexOf(src, src.length() - 1, c, new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true });
	}
	
	public static int lastIndexOfExcludeTextAndParenthesis(String src, char c)
	{
		return indexOf(src, src.length() - 1, c, new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true }, -1);
	}
	
	public static int lastIndexOfExcludeText(String src, int start, char cs[])
	{
		return indexOf(src, start, cs, new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true }, -1);
	}
	
	public static int lastIndexOfExcludeTextAndParenthesis(String src, int start, char cs[])
	{
		return indexOf(src, start, cs, new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true }, -1);
	}
	
	public static int lastIndexOfExcludeText(String src, char cs[])
	{
		return indexOf(src, src.length() - 1, cs, new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true }, -1);
	}
	
	public static int lastIndexOfExcludeTextAndParenthesis(String src, char cs[])
	{
		return indexOf(src, src.length() - 1, cs, new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true }, -1);
	}

	public static int indexOf(String src, int start, char c, char excludePrefixes[], char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[], boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[], int direction)
	{
		return indexOf(src, start, new char[] { c }, excludePrefixes, excludePostfixes, excludeBinary, excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, direction);
	}

	public static int indexOf(String src, int start, char cs[], char excludePrefixes[], char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[], boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[])
	{
		return indexOf(src, start, cs, excludePrefixes, excludePostfixes, excludeBinary, excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, 1);
	}
	
	public static int indexOf(String src, int start, char cs[], char excludePrefixes[], char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[], boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[], int direction)
	{
		return boundsOf(src, start, cs, excludePrefixes, excludePostfixes, excludeBinary, excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, direction).getStart();
	}
	
	public static Bounds boundsOf(String src, String regex)
	{
		return boundsOf(src, 0, regex);
	}
	
	public static Bounds boundsOf(String src, Matcher matcher)
	{
		return boundsOf(src, 0, matcher);
	}
	
	public static Bounds boundsOf(String src, Pattern pattern)
	{
		return boundsOf(src, 0, pattern);
	}
	
	public static Bounds boundsOf(String src, int start, String regex)
	{
		Pattern pattern = Pattern.compile(regex);
		
		return boundsOf(src, start, pattern);
	}

	public static Bounds boundsOf(String src, int start, Pattern pattern)
	{
		Matcher matcher = pattern.matcher(src);
		
		return boundsOf(src, start, matcher);
	}
	
	public static Bounds boundsOf(String src, int start, Matcher matcher)
	{
		if (matcher.find(start))
		{
			return new Bounds(matcher.start(), matcher.end());
		}
		
		return new Bounds(-1, -1);
	}
	
	public static Bounds boundsOf(String src, char c)
	{
		return boundsOf(src, 0, c);
	}
	
	public static Bounds boundsOf(String src, int start, char c)
	{
		return boundsOf(src, start, c, null, null, 1);
	}
	
	public static Bounds boundsOf(String src, char c, char excludePrefixes[], char excludePostfixes[])
	{
		return boundsOf(src, 0, c, excludePrefixes, excludePostfixes, 1);
	}
	
	public static Bounds lastBoundsOf(String src, char c)
	{
		return lastBoundsOf(src, src.length() - 1, c);
	}
	
	public static Bounds lastBoundsOf(String src, int start, char c)
	{
		return boundsOf(src, start, c, null, null, -1);
	}
	
	public static Bounds lastBoundsOf(String src, char c, char excludePrefixes[], char excludePostfixes[])
	{
		return boundsOf(src, src.length() - 1, c, excludePrefixes, excludePostfixes, -1);
	}
	
	public static Bounds boundsOf(String src, int start, char c, int direction)
	{
		return boundsOf(src, start, c, null, null, direction);
	}
	
	public static Bounds boundsOf(String src, char c, char excludePrefixes[], char excludePostfixes[], int direction)
	{
		return boundsOf(src, 0, c, excludePrefixes, excludePostfixes, direction);
	}
	
	public static Bounds boundsOf(String src, int start, char c, char excludePrefixes[], char excludePostfixes[], int direction)
	{
		return boundsOf(src, start, c, excludePrefixes, excludePostfixes, null, null, null, null, direction);
	}
	
	public static Bounds lastBoundsOf(String src, char c, char excludePrefixes[], char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[], boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[])
	{
		return boundsOf(src, src.length() - 1, c, excludePrefixes, excludePostfixes, excludeBinary, excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, -1);
	}
	
	public static Bounds boundsOf(String src, char c, char excludePrefixes[], char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[], boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[])
	{
		return boundsOf(src, 0, c, excludePrefixes, excludePostfixes, excludeBinary, excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, 1);
	}
	
	public static Bounds boundsOf(String src, char c, char excludePrefixes[], char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[], boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[], int direction)
	{
		return boundsOf(src, 0, c, excludePrefixes, excludePostfixes, excludeBinary, excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, direction);
	}

	public static Bounds boundsOf(String src, int start, char c, char excludePrefixes[], char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[], boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[])
	{
		return boundsOf(src, start, c, excludePrefixes, excludePostfixes, excludeBinary, excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, 1);
	}
	
	public static Bounds boundsOfExcludeText(String src, int start, char c)
	{
		return boundsOf(src, start, c, new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true });
	}
	
	public static Bounds boundsOfExcludeTextAndParenthesis(String src, int start, char c)
	{
		return boundsOf(src, start, c, new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true });
	}
	
	public static Bounds boundsOfExcludeText(String src, char c)
	{
		return boundsOf(src, 0, c, new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true });
	}
	
	public static Bounds boundsOfExcludeTextAndParenthesis(String src, char c)
	{
		return boundsOf(src, 0, c, new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true });
	}
	
	public static Bounds boundsOfExcludeText(String src, int start, char cs[])
	{
		return boundsOf(src, start, cs, new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true });
	}
	
	public static Bounds boundsOfExcludeTextAndParenthesis(String src, int start, char cs[])
	{
		return boundsOf(src, start, cs, new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true });
	}
	
	public static Bounds boundsOfExcludeText(String src, char cs[])
	{
		return boundsOf(src, 0, cs, new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true });
	}
	
	public static Bounds boundsOfExcludeTextAndParenthesis(String src, char cs[])
	{
		return boundsOf(src, 0, cs, new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true });
	}
	
	public static Bounds lastBoundsOfExcludeText(String src, int start, char c)
	{
		return boundsOf(src, start, c, new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true }, -1);
	}
	
	public static Bounds lastBoundsOfExcludeTextAndParenthesis(String src, int start, char c)
	{
		return boundsOf(src, start, c, new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true }, -1);
	}
	
	public static Bounds lastBoundsOfExcludeText(String src, char c)
	{
		return boundsOf(src, src.length() - 1, c, new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true });
	}
	
	public static Bounds lastBoundsOfExcludeTextAndParenthesis(String src, char c)
	{
		return boundsOf(src, src.length() - 1, c, new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true }, -1);
	}
	
	public static Bounds lastBoundsOfExcludeText(String src, int start, char cs[])
	{
		return boundsOf(src, start, cs, new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true }, -1);
	}
	
	public static Bounds lastBoundsOfExcludeTextAndParenthesis(String src, int start, char cs[])
	{
		return boundsOf(src, start, cs, new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true }, -1);
	}
	
	public static Bounds lastBoundsOfExcludeText(String src, char cs[])
	{
		return boundsOf(src, src.length() - 1, cs, new char[] { }, new char[] {}, new char[] { '"' }, new boolean[] {}, new boolean[] {}, new boolean[] { true }, -1);
	}
	
	public static Bounds lastBoundsOfExcludeTextAndParenthesis(String src, char cs[])
	{
		return boundsOf(src, src.length() - 1, cs, new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true }, -1);
	}

	public static Bounds boundsOf(String src, int start, char c, char excludePrefixes[], char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[], boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[], int direction)
	{
		return boundsOf(src, start, new char[] { c }, excludePrefixes, excludePostfixes, excludeBinary, excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, direction);
	}

	public static Bounds boundsOf(String src, int start, char cs[], char excludePrefixes[], char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[], boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[])
	{
		return boundsOf(src, start, cs, excludePrefixes, excludePostfixes, excludeBinary, excludePrefixBackslash, excludePostfixBackslash, excludeBinaryBackslash, 1);
	}
	
	public static Bounds boundsOf(String src, int start, char cs[], char excludePrefixes[], char excludePostfixes[], char excludeBinary[], boolean excludePrefixBackslash[], boolean excludePostfixBackslash[], boolean excludeBinaryBackslash[], int direction)
	{
		if (excludePrefixes == null)
		{
			excludePrefixes = new char[0];
		}
		if (excludePostfixes == null)
		{
			excludePostfixes = new char[0];
		}
		if (excludeBinary == null)
		{
			excludeBinary = new char[0];
		}
		if (excludePrefixBackslash == null)
		{
			excludePrefixBackslash = new boolean[excludePrefixes.length];
		}
		if (excludePostfixBackslash == null)
		{
			excludePostfixBackslash = new boolean[excludePostfixes.length];
		}
		
		int first = start;
		
		if (excludePrefixes.length > 0 || excludeBinary.length > 0)
		{
			if (sign(direction) == -1)
			{
				first = src.length() - 1;
			}
			else
			{
				first = 0;
			}
		}
		
		int excluding = 0;
		
		Stack<Character> binaryExclude = new Stack<Character>();
		
		for (int i = first; i < src.length() && i >= 0; i += direction)
		{
			char c = src.charAt(i);
			
			int cIndex = 0;
			
			if ((cIndex = searchChar(excludePrefixes, c)) >= 0)
			{
				excluding += sign(direction);
			}
			else if ((cIndex = searchChar(excludePostfixes, c)) >= 0)
			{
				excluding -= sign(direction);
			}
			else if ((cIndex = searchChar(excludeBinary, c)) >= 0)
			{
				if (excludeBinaryBackslash[cIndex] && i > 0 && src.charAt(i - 1) == '\\')
				{
					// Skip because it starts with a backslash...
				}
				else
				{
					if (!binaryExclude.isEmpty() && binaryExclude.peek() == c)
					{
						binaryExclude.pop();
					}
					else
					{
						binaryExclude.push(c);
					}
				}
			}
			else if (containsChar(cs, c) && excluding == 0 && i >= start && binaryExclude.isEmpty())
			{
				return new Bounds(i, i);
			}
		}
		
		return new Bounds(-1, -1);
	}
	
	public static String[] findAll(String input, Pattern pattern)
	{
		System.out.println("metho1:...");
		Matcher matcher = pattern.matcher(input);
		
		return findAll(matcher);
	}
	
	public static String[] findAll(Matcher matcher)
	{
		System.out.println("metho2:...");
		ArrayList<String> list = new ArrayList<String>();

		System.out.println("metho3:...");
		
		while (matcher.find())
		{
			System.out.println("add:...");
			list.add(matcher.group());
		}
		System.out.println("done with method");
		
		String stringArray[] = list.toArray(new String[0]);
		
		return stringArray;
	}
	
	private static int sign(int num)
	{
		if (num > 0)
		{
			return 1;
		}
		else if (num < 0)
		{
			return -1;
		}
		
		return num;
	}
	
	private static boolean containsChar(char array[], char c)
	{
		return searchChar(array, c) >= 0;
	}
	
	private static int searchChar(char array[], char c)
	{
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] == c)
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public static String[] splitCommas(String src)
	{
		ArrayList<String> strs = new ArrayList<String>();
		
		int oldIndex =  0;
		int index    = -1;
		
		StringBuilder builder = new StringBuilder();
		
		while ((index = indexOf(src, index + 1, ',', new char[] { '(' }, new char[] { ')' }, new char[] { '"' }, new boolean[] { false }, new boolean[] { false }, new boolean[] { true })) > 0)
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
	
	private static void trimSurroundingWhitespace(StringBuilder builder)
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
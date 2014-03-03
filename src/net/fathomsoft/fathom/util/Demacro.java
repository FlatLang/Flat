package net.fathomsoft.fathom.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 19, 2014 at 1:51:48 AM
 * @since	v
 * @version	Jan 19, 2014 at 1:51:48 AM
 * @version	v
 */
public class Demacro
{
	private static final char WHITESPACE[]           = new char[] { ' ', '\t', '\n' };
	private static final char WHITESPACE_AND_PAREN[] = new char[] { ' ', '\t', '\n', ')' };
	private static final char END_PAREN[]            = new char[] { ')' };
	private static final char COMMA[]                = new char[] { ',' };
	private static final char COMMA_AND_PAREN[]      = new char[] { ',', '(' };
	private static final char NEW_LINE[]             = new char[] { '\n' };
	
	public static String[] demacro(String ... sources)
	{
		HashMap<String, MacroList> macros = new HashMap<String, MacroList>();
		
		parseMacros(macros, sources);
		
		return demacro(macros, 0, sources);
	}
	
	public static String demacro(String source)
	{
		HashMap<String, MacroList> macros = new HashMap<String, MacroList>();

		parseMacros(macros, source);

		return demacro(macros, 0, source);
	}
	
	public static String[] demacro(HashMap<String, MacroList> macros, int globalIndex, String ... sources)
	{
		Set<String>   macroIds       = macros.keySet();
		
		String        ids[]          = macroIds.toArray(new String[0]);
		
		StringBuilder patternBuilder = new StringBuilder();
		
		patternBuilder.append("(?<!(#\\s{0,99999999}(ifndef|define|undef)\\s{1,999999}))(?<=[^a-zA-Z0-9_]|^)").append('(');
		
		for (int j = 0; j < ids.length; j++)
		{
			String id = ids[j];
			
			patternBuilder.append(id);
			
			if (j < ids.length - 1)
			{
				patternBuilder.append('|');
			}
		}
		
		patternBuilder.append(')').append("([^a-zA-Z0-9_]|$)");
		
		Pattern pattern = Pattern.compile(patternBuilder.toString());
		
		for (int i = 0; i < sources.length; i++)
		{
			String source = sources[i];
			
			source = source.replaceAll("(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)|(//.*)", "");
			
			sources[i] = demacro(macros, globalIndex, pattern, source);
		}
		
		return sources;
	}
	
	public static String demacro(HashMap<String, MacroList> macros, int globalIndex, String source)
	{
		return demacro(macros, globalIndex, new String[] { source })[0];
	}
	
	public static String demacro(HashMap<String, MacroList> macros, int globalIndex, Pattern pattern, String source)
	{
		Matcher       matcher = pattern.matcher(source);
		
		StringBuilder builder = new StringBuilder(source);
		
		int           offset  = 0;
		
		int           index   = Regex.indexOf(source, matcher);
		
		while (index >= 0)
		{
			int    idStart = index;
			
			String args[]  = null;
			
			String id      = constructId(index, source);
			
			index         += id.length();
			
			int idEnd      = index;
			
			if (index < source.length() - 1)
			{
				char c = source.charAt(index);
				
				while (c == ' ' || c == '\n' || c == '\t')
				{
					index++;
					
					c = source.charAt(index);
				}
				
				if (c == '(')
				{
					args  = constructArgs(index, source);
					
					idEnd = findMatchingBrace(index, source) + 1;
				}
			}
			
			String replacement = null;
			
			if (idEnd == source.length())
			{
				replacement = macros.get(id).parse(globalIndex, args);
				
				replacement = demacro(macros, globalIndex, replacement);
			}
			else
			{
				String subStr = source.substring(idStart, idEnd);
				
				replacement = demacro(macros, globalIndex, subStr);
			}
			
			builder.replace(idStart + offset, idEnd + offset, replacement);
				
			offset     += replacement.length() - (idEnd - idStart);
			
			index       = Regex.indexOf(source, idEnd, matcher);
			
			globalIndex = index;
		}
		
		String newSource = builder.toString();
		
		return newSource;
	}
	
	public static HashMap<String, MacroList> parseMacros(String ... sources)
	{
		HashMap<String, MacroList> macros = new HashMap<String, MacroList>();
		
		parseMacros(macros, sources);
		
		return macros;
	}
	
	public static void parseMacros(HashMap<String, MacroList> macros, String ... sources)
	{
		for (int i = 0; i < sources.length; i++)
		{
			String source = sources[i];

			source    = source.replaceAll("(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)|(//.*)", "");
			
			int index = Regex.indexOf(source, Patterns.DEFINE_PATTERN);
			
			while (index >= 0)
			{
				int    start    = nextIndex(index, source, WHITESPACE, false);
				
				String id       = constructId(start, source);
				
				int    idStart  = start;
				
				int    end      = start + id.length();
				
				String params[] = null;
				
				if (source.charAt(end) == '(')
				{
					params = constructParams(end, source);
					
					end    = nextIndex(end, source, END_PAREN, true);
				}
				
				start        = nextIndex(end + 1, source, WHITESPACE, false);
				
				String value = constructDefinition(start, source);
				
				Macro  macro = new Macro(params, value);
				
				if (!macros.containsKey(id))
				{
					MacroList list = new MacroList();
					
					macros.put(id, list);
				}
				
				macros.get(id).addMacro(idStart, macro);
				
				end   = start + value.length();
				
				index = Regex.indexOf(source, end, Patterns.DEFINE_PATTERN);
			}
		}
	}
	
	private static String constructId(int start, String source)
	{
		int index = Regex.indexOf(source, start, Patterns.NON_ALPHANUMERIC);
		
		if (index < 0)
		{
			index = source.length();
		}
		
		String id = source.substring(start, index);
		
		return id;
	}
	
	private static String[] constructParams(int start, String source)
	{
		ArrayList<String> params = new ArrayList<String>();
		
		int index = start;
		int end   = nextIndex(start + 1, source, END_PAREN, true);
		
		while (index < end)
		{
			start = nextIndex(index + 1, source, WHITESPACE, false);
			index = nextIndex(start + 1, source, COMMA, true);
			
			if (index < 0 || index > end)
			{
				index = end;
			}
			
			String param = source.substring(start, index);
			
			params.add(param);
		}
		
		String paramArray[] = params.toArray(new String[0]);
		
		return paramArray;
	}
	
	private static String[] constructArgs(int start, String source)
	{
		ArrayList<String> args = new ArrayList<String>();
		
		int endI = start;
		int end  = findMatchingBrace(start, source);
		
		while (endI < end && endI >= 0)
		{
			start = nextIndex(endI  + 1, source, WHITESPACE, false);
			endI  = nextIndex(start, source, COMMA_AND_PAREN, true);
			
			if (endI < 0 || endI > end)
			{
				endI = end;
			}
			else
			{
				if (source.charAt(endI) == '(')
				{
					while (source.charAt(endI) == '(')
					{
						int newEnd = findMatchingBrace(endI, source);
						
						endI = nextIndex(newEnd, source, COMMA_AND_PAREN, true);
						
						if (endI < 0 || endI > end)
						{
							endI = end;
						}
					}
				}
			}
			
			String arg = source.substring(start, endI);
			
			args.add(arg);
		}
		
		String argArray[] = args.toArray(new String[0]);
		
		return argArray;
	}
	
	private static String constructDefinition(int start, String source)
	{
		StringBuilder builder = new StringBuilder();
		
		int index = nextIndex(start, source, NEW_LINE, true);
		
		if (index < 0)
		{
			index = source.length() - 1;
		}
		
		int nonWhite = nextIndex(index - 1, source, WHITESPACE, false, -1);
		
		if (source.charAt(nonWhite) == '\\')
		{
			String str = source.substring(start, nonWhite);
			
			if (str.length() > 0)
			{
				builder.append(str).append('\n');
			}
			
			index = nextIndex(index, source, WHITESPACE, false);
			
			String nextLine = constructDefinition(index, source);
			
			builder.append(nextLine);
		}
		else
		{
			builder.append(source.substring(start, index));
		}
		
		return builder.toString();
	}
	
	private static int findMatchingBrace(int index, String source)
	{
		int parenCount = 1;
		
		index++;
		
		char c = source.charAt(index);
		
		while (parenCount > 0)
		{
			if (c == '(')
			{
				parenCount++;
			}
			else if (c == ')')
			{
				parenCount--;
				
				if (parenCount == 0)
				{
					return index;
				}
			}
			
			index++;
			
			if (index >= source.length())
			{
				return -1;
			}
			
			c = source.charAt(index);
		}
		
		return -1;
	}
	
	private static int nextIndex(int index, String source, char chars[], boolean contain)
	{
		return nextIndex(index, source, chars, contain, 1);
	}
	
	private static int nextIndex(int index, String source, char chars[], boolean contain, int direction)
	{
		char c = source.charAt(index);
		
		while (containsChar(chars, c) != contain)
		{
			index += direction;
			
			if (direction > 0 && index >= source.length() || direction < 0 && index < 0)
			{
				return -1;
			}
			
			c = source.charAt(index);
		}
		
		return index;
	}
	
	private static boolean containsChar(char array[], char c)
	{
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] == c)
			{
				return true;
			}
		}
		
		return false;
	}
}
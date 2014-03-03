package net.fathomsoft.fathom.util;

import java.awt.TextComponent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Class used to parse data with a compiled pattern.
 * 
 * @author	Braden Steffaniak
 * @since	Feb 9, 2014 at 5:24:47 PM
 * @since	v0.1
 * @version	Feb 9, 2014 at 5:24:47 PM
 * @version	v0.1
 */
public class TierPattern
{
	private int											mode;
	
	private ArrayList<ArrayList<CollectionCase>>		testCollections;
	
	private static final int							PRE_MODE = 0, MAIN_MODE = 1, POST_MODE = 2;
	
	private static final HashMap<String, Collection<?>>	collections;
	
	private static final char							SPECIAL_CHARS[];
	
	private static final String							keys[];
	
	/**
	 * Initialize the static data.
	 */
	static
	{
		SPECIAL_CHARS = new char[] { '`', '*', '+', '?', '"', '|', '-', '(', ')', '[', ']' };
		
		collections = new HashMap<String, Collection<?>>();

		insertCollection("~",        new Character[]  {  }, true);
		insertCollection(".",        new Character[]  { '\n', '\r' }, true);
		insertCollection("`s",        new Character[]  { '\n', '\t', ' ',  '\r' });
		insertCollection("`S",        new Collection[] { collections.get("`s") }, true);
		insertCollection("a-z",      new Character[]  { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' });
		insertCollection("A-Z",      new Character[]  { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' });
		insertCollection("0-9",      new Character[]  { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' });
		insertCollection("`alph",     new Collection[] { collections.get("a-z"), collections.get("A-Z") });
		insertCollection("`ALPH",     new Collection[] { collections.get("`alph") }, true);
		insertCollection("`alphanum", new Collection[] { collections.get("`alph"), collections.get("0-9") });
		insertCollection("`ALPHANUM", new Collection[] { collections.get("`alphanum") }, true);
		
		keys = collections.keySet().toArray(new String[0]);
	}
	
	/**
	 * Insert a collection into the standard collections used for all
	 * of the patterns.
	 * 
	 * @param key The key used to access the collection.
	 * @param array The array of data that the collection encompasses.
	 */
	private static <E> void insertCollection(String key, E array[])
	{
		insertCollection(key, array, false);
	}
	
	/**
	 * Insert a collection into the standard collections used for all
	 * of the patterns.
	 * 
	 * @param key The key used to access the collection.
	 * @param array The array of data that the collection encompasses.
	 * @param opposite Whether or not the pattern should be tested for or
	 * 		against the data in the array.
	 */
	private static <E> void insertCollection(String key, E array[], boolean opposite)
	{
		Collection<?> collection = createCollection(key, array, opposite);
		
		collections.put(key, collection);
	}
	
	/**
	 * Create a collection with the specified key and data.
	 * 
	 * @param key The key used to access the Collection.
	 * @param array The array holding the data that the Collection
	 * 		will hold.
	 * @param opposite Whether or not the pattern should be tested for or
	 * 		against the data in the array.
	 * @return The new Collection that was created based on the given
	 * 		data.
	 */
	private static <E> Collection<E> createCollection(String key, E array[], boolean opposite)
	{
		Class<?> type = array.getClass().getComponentType();
		
		Collection<E> collection = new Collection<E>(type, key, opposite);
		
		HashSet<E> set = new HashSet<E>();
		
		for (int i = 0; i < array.length; i++)
		{
			set.add(array[i]);
		}
		
		collection.setSet(set);
		
		return collection;
	}
	
	/**
	 * Create a TierPattern instance from the given pattern.
	 * 
	 * @param pattern The pattern describing what the TierPattern instance
	 * 		will look for in the data.
	 */
	public TierPattern(String pattern)
	{
		testCollections = new ArrayList<ArrayList<CollectionCase>>();
		
		String modes[]  = divideModes(pattern);
		
		testCollections = new ArrayList<ArrayList<CollectionCase>>();
		
		for (String mode : modes)
		{
			testCollections.add(compile(mode));
		}
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
	private int findEndingMatch(String str, int index, char startChar, char endChar)
	{
		return findEndingMatch(str, startChar, endChar, (char)0);
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
	 * @param escapeChar The char that escapes a start or end char.
	 * @return The index of the pair to the starting char, if no pair is
	 * 		found then -1 is returned.
	 */
	private int findEndingMatch(String str, int index, char startChar, char endChar, char escapeChar)
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
	 * Divides the given pattern into its three modes.<br>
	 * <br>
	 * The modes include:<br>
	 * <ul>
	 * 	<li><b>PRE_MODE:</b> The Patterns pre-condition data.</li>
	 * 	<li><b>MAIN_MODE:</b> The Patterns main data.</li>
	 * 	<li><b>POST_MODE:</b> The Patterns post-condition data.</li>
	 * </ul>
	 * 
	 * @param pattern The Pattern to split into separate modes.
	 * @return A String array containing the three modes respectively.
	 */
	private String[] divideModes(String pattern)
	{
		String modes[] = new String[3];
		
		int index = 0;
		int lastI = 0;
		int mode  = 0;

		index = findEndingMatch(pattern, index, '(', ')', '\\');
		
		while (index >= 0)
		{
			modes[mode++] = pattern.substring(lastI + 1, index);
			
			lastI = index + 1;
			
			index = findEndingMatch(pattern, index + 1, '(', ')', '\\');
		}
		
		if (modes[2] == null && modes[1] == null)
		{
			modes[2] = modes[1];
			modes[1] = modes[0];
			modes[0] = null;
		}
		
		for (int j = 0; j < modes.length; j++)
		{
			if (modes[j] == null)
			{
				modes[j] = "";
			}
		}
		
		return modes;
	}
	
	/**
	 * Get the index of the first String that matches the pattern.
	 * 
	 * @param data The data to search for the pattern in.
	 * @return The index of the start of the match that was found.
	 */
	public int indexOf(String data)
	{
		return indexOf(data, 0);
	}
	
	/**
	 * Get the index of the String that matches the pattern after the
	 * given index.
	 * 
	 * @param data The data to search for the pattern in.
	 * @param index The index to start the search at.
	 * @return The index of the start of the match that was found.
	 */
	public int indexOf(String data, int index)
	{
		Bounds bounds = boundsOf(data, index);
		
		if (bounds == null)
		{
			return -1;
		}
		
		return bounds.start;
	}
	
	/**
	 * Get the bounds of the first String that matches the pattern.
	 * 
	 * @param data The data to search for the pattern in.
	 * @return A Bounds instance containing the start and end points
	 * 		of the match that was found.
	 */
	public Bounds boundsOf(String data)
	{
		return boundsOf(data, 0);
	}
	
	/**
	 * Get the bounds of the String that matches the pattern after the
	 * given index.
	 * 
	 * @param data The data to search for the pattern in.
	 * @param index The index to start the search at.
	 * @return A Bounds instance containing the start and end points
	 * 		of the match that was found.
	 */
	public Bounds boundsOf(String data, int index)
	{
		if (testCollections.size() <= 0)
		{
			return null;
		}
		
		while (index < data.length())
		{
			int startIndex = index;
			int endIndex   = index;
//			System.out.println("stsart");
			
			for (int modeIndex = 0; modeIndex < testCollections.size() && startIndex >= 0; modeIndex++)
			{
				if (modeIndex == MAIN_MODE)
				{
					startIndex = index;
//					System.out.println("set----------------" + startIndex);
				}
				
				for (int caseIndex = 0; caseIndex < testCollections.get(modeIndex).size(); caseIndex++)
				{
					CollectionCase testCase = testCollections.get(modeIndex).get(caseIndex);
					
					int value = matchLength(data, index, testCase);
					
					if (value >= 0)
					{
						index += value;
					}
					else
					{
						index      = startIndex + 1;
						
						startIndex = -1;
						
						break;
					}
				}
				
				if (startIndex < 0)
				{
					break;
				}
				
				if (modeIndex == MAIN_MODE)
				{
					endIndex = index;
				}
			}
			
//			System.out.println("end -- " + startIndex);
			if (startIndex >= 0)
			{
				Bounds bounds = new Bounds();
				
				bounds.start  = startIndex;
				bounds.end    = endIndex;
				
				return bounds;
			}
		}
		
		return null;
	}
	
	/**
	 * Get the length of the match (If a match can be found) at the
	 * specified offset. If a match is not present at the specified
	 * offset then -1 is returned.
	 * 
	 * @param src The String to search for the match in.
	 * @param offset The offset in the String to search at.
	 * @param test The CollectionCase used to test the data at the
	 * 		offset of the source.
	 * @return The length of the match at the specified offset. If a
	 * 		match is not present then -1 is returned.
	 */
	private int matchLength(String src, int offset, CollectionCase test)
	{
		int     value   = 0;
		boolean success = test.collections.size() <= 0;
		
		if (!success)
		{
			Iterator<Collection<?>> iterator = test.iterator();
			
			while (iterator.hasNext())
			{
				Collection<?> col = iterator.next();
				
				int length = matchLength(src, offset, col, test.getBounds());
				
				if (length >= 0)
				{
					value  += length;
					
					success = true;
				}
			}
		}
		
		if (!success)
		{
			return -1;
		}
		
		for (int i = 0; i < test.getNumChildren(); i++)
		{
			CollectionCase child = test.getChild(i);
			
			int length = matchLength(src, offset + value, child);
			
			if (length < 0)
			{
				return -1;
			}
			
			value += length;
		}
		
		return value;
	}
	
	/**
	 * Get the length of the match (If a match can be found) at the
	 * specified offset. If a match is not present at the specified
	 * offset then -1 is returned.
	 * 
	 * @param src The String to search for the match in.
	 * @param offset The offset in the String to search at.
	 * @param collection The Collection used to test the data at the
	 * 		offset of the source.
	 * @param bounds The Bounds in which the Collection must be within.
	 * @param cumulative Whether or not the combined Collections are
	 * 		cumulative.
	 * @return The length of the match at the specified offset. If a
	 * 		match is not present then -1 is returned.
	 */
	private int matchLength(String src, int offset, Collection<?> collection, Bounds bounds)
	{
		return matchLength(src, offset, collection, bounds, collection.isOpposite());
	}
	
	/**
	 * Get the length of the match (If a match can be found) at the
	 * specified offset. If a match is not present at the specified
	 * offset then -1 is returned.
	 * 
	 * @param src The String to search for the match in.
	 * @param offset The offset in the String to search at.
	 * @param collection The Collection used to test the data at the
	 * 		offset of the source.
	 * @param bounds The Bounds in which the Collection must be within.
	 * @param cumulative Whether or not the combined Collections are
	 * 		cumulative.
	 * @param opposite Whether or not to test for, or against the
	 * 		Collection.
	 * @return The length of the match at the specified offset. If a
	 * 		match is not present then -1 is returned.
	 */
	private int matchLength(String src, int offset, Collection<?> collection, Bounds bounds, boolean opposite)
	{
		int x = bounds.getStart();
		int y = bounds.getEnd();
		
		if (collection.getType() == Character.class)
		{
			int count = 0;
			
			while ((count < y || y == -1) && count + offset < src.length())
			{
				char c = src.charAt(offset + count);
				
				if (collection.isMatch(c, opposite))
				{
					count++;
				}
				else
				{
					break;
				}
			}
			
			if (count >= x && (count <= y || y == -1))
			{
				return count;
			}
		}
		else if (collection.getType() == String.class)
		{
			int count = 0;
			int times = 0;
			
			while (times <= y || y == -1)
			{
				Iterator<?> iterator = collection.getSet().iterator();
				
				while (iterator.hasNext())
				{
					String  str   = (String)iterator.next();
					
					boolean found = !opposite;
					
					for (int i = 0; i < str.length() && found; i++)
					{
						if (str.charAt(i) != src.charAt(offset + i))
						{
							found = opposite;
							
							break;
						}
						
						count++;
					}
					
					if (found)
					{
						times++;
						
						if (times >= y && y >= 0)
						{
							break;
						}
						
						if (opposite)
						{
							if (count > 0)
							{
								offset += count; // + 1?
							}
							else
							{
								offset++;
							}
						}
					}
				}
				
				if (times >= x)
				{
					return count;
				}
				else if (times <= 0)
				{
					return -1;
				}
			}
		}
		else if (collection.getType() == Collection.class)
		{
			Iterator<?> iterator = collection.getSet().iterator();
			
			while (iterator.hasNext())
			{
				Collection<?> col = (Collection<?>)iterator.next();
				
				int size = matchLength(src, offset, col, bounds, false);
				
				if (size >= 0)// && !cumulative)
				{
					return size;
				}
//				else if (size < 0 && cumulative)
//				{
//					return -1;
//				}
			}
		}
		else
		{
			throw new TierExpressionException("Unexpected condition type of '" + collection.getType().getSimpleName() + "'");
		}
		
		return -1;
	}
	
	/**
	 * Create a CollectionCase instance from the given Collection
	 * instance.
	 * 
	 * @param collection The Collection instance to create the
	 * 		CollectionCase from.
	 * @return The new CollectionCase instance.
	 */
	private CollectionCase createCollectionCase(Collection<?> collection)
	{
		CollectionCase colCase = new CollectionCase();
		
		colCase.addCollection(collection);
		
		return colCase;
	}
	
	/**
	 * Compile the pattern for quick searching.
	 * 
	 * @param pattern The pattern to compile into usable data.
	 */
	private ArrayList<CollectionCase> compile(String pattern)
	{
		ArrayList<CollectionCase> cases = new ArrayList<CollectionCase>();
		
		int index = 0;
		
		while (index < pattern.length())
		{
			int startIndex = index;
			
			char c = pattern.charAt(index);
			
			if (c == '`' || c == '"' || c == '[' || c == '(')
			{
				CollectionCase test = getCollectionCase(index, pattern);
				
				index += test.getKey().length();
				
				boolean condensed = false;
				
				if (cases.size() > 0)
				{
					CollectionCase last = cases.get(cases.size() - 1);
					
					if (last.getKey().equals(test.getKey()))
					{
						if (last.bounds.end >= 0)
						{
							last.bounds.start++;
							last.bounds.end++;
							last.condensed = true;
						
							condensed = true;
						}
					}
				}
				
				if (!condensed)
				{
					cases.add(test);
				}
			}
			else if (cases.size() > 0)
			{
				CollectionCase last = cases.get(cases.size() - 1);
				
				if (c == '*')
				{
					last.setBounds(0, -1);
				}
				else if (c == '+')
				{
					last.bounds.end = -1;
				}
				else if (c == '?')
				{
					last.setBounds(0, 1);
				}
				else if (containsChar(c, SPECIAL_CHARS))
				{
					
				}
				
				index++;
			}
			
			if (index == startIndex)
			{
				for (int i = 0; i < keys.length; i++)
				{
					String key = c + "";
					
					if (keys[i].equals(key))
					{
						CollectionCase colCase = createCollectionCase(collections.get(key));
						colCase.setKey(key);
						
						cases.add(colCase);
						
						index += colCase.getKey().length();
						
						break;
					}
				}
			}
			
			if (index == startIndex)
			{
				throw new TierExpressionException("Unexpected character type of '" + c + "' at offset: " + index);
			}
		}
		
		return cases;
	}
	
	/**
	 * Get the CollectionCase that is described at the specified index
	 * in the pattern. Creates the CollectionCase if necessary.
	 * 
	 * @param index The index to start the search at.
	 * @param pattern The pattern String to search in.
	 * @return The next CollectionCase at the specified index.
	 */
	private CollectionCase getCollectionCase(int index, String pattern)
	{
		int  startIndex = index;
		
		char c          = pattern.charAt(index++);
		
		/* If the pattern is starting to describe a pre-defined
		 * Collection.
		 */
		if (c == '`')
		{
			if (index < pattern.length())
			{
				c = pattern.charAt(index++);
				
				while (!containsChar(c, SPECIAL_CHARS))
				{
					if (index >= pattern.length())
					{
						index = pattern.length() + 1;
						
						break;
					}
					
					c = pattern.charAt(index++);
				}
				
				String key = pattern.substring(startIndex, index - 1);
				
				CollectionCase colCase = createCollectionCase(collections.get(key));
				colCase.setKey(key);
				
				return colCase;
			}
			else
			{
				throw new TierExpressionException("Gravemark cannot end the statement; at offset: " + index);
			}
		}
		/* If A String literal is being used as a Collection.
		 */
		else if (c == '"')
		{
			if (index < pattern.length())
			{
				c = pattern.charAt(index++);
				
				while (true)
				{
					if (c == '"')
					{
						if (index > 0)
						{
							int count = 1;
							
							while (pattern.charAt(index - count - 1) == '\\')
							{
								count++;
							}
							
							// If it is the ending quotation.
							if (count % 2 == 1)
							{
								break;
							}
						}
					}
					
					if (index >= pattern.length())
					{
						throw new TierExpressionException("Missing end of starting quotation at offset " + startIndex);
					}
					
					c = pattern.charAt(index++);
				}
				
				String key   = pattern.substring(startIndex, index);
				String value = key.substring(1, key.length() - 1);
				
				if (value.length() > 0)
				{
					String data[] = new String[] { value };
					
					Collection<String> collection = createCollection(key, data, false);
					
					CollectionCase colCase = createCollectionCase(collection);
					colCase.setKey(key);
					colCase.setCumulative(false);
					
					return colCase;
				}
			}
			else
			{
				throw new TierExpressionException("Quotation cannot end the statement; at offset: " + index);
			}
		}
		else if (c == '(')
		{
			if (index < pattern.length())
			{
				index = findEndingMatch(pattern, index - 1, '(', ')', '\\') + 1;
				
				if (index <= 0)
				{
					throw new TierExpressionException("Missing end of starting parenthesis at offset " + startIndex);
				}
				
				String key   = pattern.substring(startIndex, index);
				String value = key.substring(1, key.length() - 1);
				
				CollectionCase colCase = new CollectionCase();
				colCase.setKey(key);
				colCase.setCumulative(true);
				colCase.addChildren(compile(value));
				
				return colCase;
			}
			else
			{
				throw new TierExpressionException("Starting parenthesis cannot end the statement; at offset: " + index);
			}
		}
		/* If a character matcher is being used as a Collection.
		 */
		else if (c == '[')
		{
			if (index < pattern.length())
			{
				index = findEndingMatch(pattern, index - 1, '[', ']', '\\') + 1;
				
				if (index <= 0)
				{
					throw new TierExpressionException("Missing end of starting bracket at offset " + startIndex);
				}
				
				String key   = pattern.substring(startIndex, index);
				String value = key.substring(1, key.length() - 1);
				
				if (value.length() > 0)
				{
					CollectionCase colCase = new CollectionCase();
					colCase.setKey(key);
					colCase.setCumulative(false);
					
					ArrayList<Character> chars = new ArrayList<Character>();
					
					int     i   = 0;
					
					while (i < value.length())
					{
						char current = value.charAt(i);
						
						chars.add(current);
						
						if (current == '-')
						{
							if (i > 0 && i < value.length() - 1)
							{
								Collection<?> alph = collections.get("`alphanum");
								
								char prev = value.charAt(i - 1);
								char next = value.charAt(i + 1);
								
								if (alph.isMatch(prev) && alph.isMatch(next))
								{
									chars.remove(chars.size() - 1);
									chars.remove(chars.size() - 1);
									
									if (!colCase.containsCollection(alph))
									{
										colCase.addCollection(alph);
									}
									
									i++;
								}
							}
						}
						else if (current == '\\')
						{
							if (i < value.length() - 1)
							{
								char next = value.charAt(i + 1);
								
								if (containsChar(next, SPECIAL_CHARS))
								{
									chars.remove(chars.size() - 1);
								}
							}
						}
						
						i++;
					}
					
					Character data[] = chars.toArray(new Character[0]);
					
					Collection<Character> collection = createCollection(key, data, false);
					
					colCase.addCollection(collection);
					
					return colCase;
				}
			}
			else
			{
				throw new TierExpressionException("Bracket statement cannot end the statement; at offset: " + index);
			}
		}
		
		throw new TierExpressionException("Unknown symbol at offset: " + (index - 1));
	}
	
	/**
	 * Search the specified arrays for the given char.
	 * 
	 * @param c The char to search through the arrays for.
	 * @param arrays The list of arrays to search for a match in.
	 * @return Whether or not the arrays contain the char.
	 */
	private static boolean containsChar(char c, char[] ... arrays)
	{
		for (int i = 0; i < arrays.length; i++)
		{
			for (int j = 0; j < arrays[i].length; j++)
			{
				if (arrays[i][j] == c)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Class used to store data about two start and end locations.
	 * 
	 * @author	Braden Steffaniak
	 * @since	Feb 9, 2014 at 9:53:05 PM
	 * @since	v0.1
	 * @version	Feb 9, 2014 at 9:53:05 PM
	 * @version	v0.1
	 */
	static class Bounds
	{
		private int	start, end;
		
		/**
		 * The start position of the Bounds.
		 * 
		 * @return The start position.
		 */
		public int getStart()
		{
			return start;
		}
		
		/**
		 * The end position of the Bounds.
		 * 
		 * @return The end position.
		 */
		public int getEnd()
		{
			return end;
		}
	}
	
	/**
	 * Class used for organizing a Collection used for testing
	 * if there is a match in a given String.
	 * 
	 * @author	Braden Steffaniak
	 * @since	Feb 10, 2014 at 4:01:41 PM
	 * @since	v0.1
	 * @version	Feb 10, 2014 at 4:01:41 PM
	 * @version	v0.1
	 */
	private static class CollectionCase
	{
		private boolean						condensed;
		private boolean						cumulative;
		
		private String						key;
		
		private Bounds						bounds;
		
		private ArrayList<CollectionCase>	children;
		
//		private Class<?>					type;
		
		private ArrayList<Collection<?>>	collections;
		
		/**
		 * Create the collection case and initialize the data to the
		 * default values.
		 */
		public CollectionCase()
		{
			this.bounds      = new Bounds();
			this.collections = new ArrayList<Collection<?>>();
			this.children    = new ArrayList<CollectionCase>();
			
			setCumulative(false);
			setBounds(1, 1);
		}
		
		/**
		 * Get the number of children that the CollectionCase has.
		 * 
		 * @return The number of children the CollectionCase has.
		 */
		public int getNumChildren()
		{
			return children.size();
		}
		
		/**
		 * Get the child CollectionCase of this Case, if it exists.
		 * 
		 * @param index The index to get the child from.
		 * @return The child CollectionCase instance.
		 */
		public CollectionCase getChild(int index)
		{
			return children.get(index);
		}
		
		/**
		 * Add a child CollectionCase to this Case.
		 * 
		 * @param child The new child to add to the CollectionCase.
		 */
		public void addChild(CollectionCase child)
		{
			children.add(child);
		}
		
		/**
		 * Add a bunch of children CollectionCases to this Case.
		 * 
		 * @param children The new children to add to the CollectionCase.
		 */
		public void addChildren(ArrayList<CollectionCase> children)
		{
			this.children.addAll(children);
		}
		
		/**
		 * Get whether or not the Collections are tested in a cumulative
		 * manner, or an 'OR' manner.
		 * 
		 * @return Whether or not the Collections are cumulative.
		 */
		public boolean isCumulative()
		{
			return cumulative;
		}
		
		/**
		 * Set whether or not the Collections are tested in a cumulative
		 * manner, or an 'OR' manner.
		 * 
		 * @param cumulative Whether or not to set as cumulative.
		 */
		public void setCumulative(boolean cumulative)
		{
			this.cumulative = cumulative;
		}
		
		/**
		 * Get the String that represents the Key value in the String
		 * pattern that was compiled.
		 * 
		 * @return The String that represents the Key value in the String
		 * 		pattern that was compiled.
		 */
		public String getKey()
		{
			return key;
		}
		
		/**
		 * Set the String that represents the Key value in the String
		 * pattern that was compiled.
		 * 
		 * @param key The key String.
		 */
		public void setKey(String key)
		{
			this.key = key;
		}
		
		/**
		 * Get whether the CollectionCase contains the specific
		 * Collection.
		 * 
		 * @param collection The Collection to check if it exists.
		 * @return Whether or not the Collection case contains the
		 * 		Collection.
		 */
		public boolean containsCollection(Collection<?> collection)
		{
			return collections.contains(collection);
		}
		
		/**
		 * Add the specified Collection to the list of Collections that
		 * are needed to pass the case.
		 * 
		 * @param collection The Collection instance to add.
		 */
		public void addCollection(Collection<?> collection)
		{
//			if (type != null)
//			{
//				if (type != collection.getType())
//				{
//					throw new TierExpressionException("Expected Collection to be of type: " + type.getSimpleName() + "; instead, found type: " + collection.getType().getSimpleName());
//				}
//			}
//			else
//			{
//				type = collection.getType();
//			}
			
			collections.add(collection);
		}
		
//		/**
//		 * Get the type of data that the CollectionCase tests for, or
//		 * against.
//		 * 
//		 * @return The Class instance that represents the data type.
//		 */
//		public Class<?> getType()
//		{
//			return type;
//		}
		
		/**
		 * Get the Bounds that the Collection requires to be fulfilled.
		 * 
		 * @return The Bounds instance.
		 */
		public Bounds getBounds()
		{
			return bounds;
		}
		
		/**
		 * Set the Bounds that the Collection requires to be fulfilled.
		 * 
		 * @param x The least amount of repetition required for the
		 * 		Collection to be fulfilled.
		 * @param y The most amount of repetition required for the
		 * 		Collection to be fulfilled.
		 */
		public void setBounds(int x, int y)
		{
			bounds.start = x;
			bounds.end = y;
		}
		
		/**
		 * Get the iterator for the Collections.
		 * 
		 * @return The Iterator instance for the Collections.
		 */
		public Iterator<Collection<?>> iterator()
		{
			return collections.iterator();
		}
		
		/**
		 * Check whether or not the specified Object given matches any
		 * of the data.
		 * 
		 * @param test The Object to test for, or against, the
		 * 		CollectionCase.
		 * @return Whether or not it matches the Collections criteria.
		 */
		public boolean isMatch(Object test)
		{
			for (int i = 0; i < collections.size(); i++)
			{
				Collection<?> collection = collections.get(i);
				
				if (collection.isMatch(test))
				{
					return true;
				}
			}
			
			return false;
		}
	}
	
	/**
	 * Class used to hold related information with a common descriptor.
	 * 
	 * @author	Braden Steffaniak
	 * @since	Feb 9, 2014 at 4:12:07 PM
	 * @since	v0.1
	 * @version	Feb 9, 2014 at 4:12:07 PM
	 * @version	v0.1
	 */
	private static class Collection<E>
	{
		private boolean		opposite;
		
		private String		key;
		
		private Class<?>	type;
		
		private HashSet<E>	data;
		
		/**
		 * Create a Collection with the specified Class type.
		 * 
		 * @param type The Class type of the data stored.
		 */
		public Collection(Class<?> type)
		{
			this.type = type;
		}
		
		/**
		 * Create a Collection with the specified Class type that is
		 * described by the given key.
		 * 
		 * @param type The Class type of the data stored.
		 * @param key The String value that describes the data stored.
		 * @param opposite Whether or not to test for, or against the
		 * 		data.
		 */
		public Collection(Class<?> type, String key, boolean opposite)
		{
			this.type   = type;
			
			setOpposite(opposite);
			setKey(key);
		}
		
		/**
		 * Get the type of data that the Collection tests for, or
		 * against.
		 * 
		 * @return The Class instance that represents the data type.
		 */
		public Class<?> getType()
		{
			return type;
		}
		
		/**
		 * Get the key String value; the short String that describes the
		 * data that is stored in the Collection.
		 * 
		 * @return The key String value.
		 */
		public String getKey()
		{
			return key;
		}
		
		/**
		 * Set the String value used to describe the data that is stored
		 * in the Collection.
		 * 
		 * @param key The String value to set as the new key.
		 */
		public void setKey(String key)
		{
			this.key = key;
		}
		
		/**
		 * Get the HashSet that is used to store the data that is related.
		 * 
		 * @return The HashSet of related data.
		 */
		public HashSet<E> getSet()
		{
			return data;
		}
		
		/**
		 * Set the HashSet that is used to store the data that is related.
		 * 
		 * @param data The new HashSet of related data.
		 */
		public void setSet(HashSet<E> data)
		{
			this.data = data;
		}
		
		/**
		 * Get whether the Collection tests for, or against the stored
		 * data.
		 * 
		 * @return Whether the Collection tests for, or against the stored
		 * 		data.
		 */
		public boolean isOpposite()
		{
			return opposite;
		}
		
		/**
		 * Set whether the Collection tests for, or against the stored
		 * data. 
		 * 
		 * @param opposite Whether the Collection tests for, or against
		 * 		the stored data.
		 */
		public void setOpposite(boolean opposite)
		{
			this.opposite = opposite;
		}
		
		/**
		 * Check whether or not the specified Object given matches any
		 * of the data.
		 * 
		 * @param test The Object to test for, or against, the Collection.
		 * @return Whether or not it matches the Collections criteria.
		 */
		public boolean isMatch(Object test)
		{
			return isMatch(test, opposite);
		}
		
		/**
		 * Check whether or not the specified Object given matches any
		 * of the data.
		 * 
		 * @param test The Object to test for, or against, the Collection.
		 * @param opposite An explicit value for whether to check for, or
		 * 		against the data. Overrides whatever value is in the
		 * 		Collection.opposite.
		 * @return Whether or not it matches the Collections criteria.
		 */
		public boolean isMatch(Object test, boolean opposite)
		{
			if (type == Collection.class)
			{
				Iterator<E> i = data.iterator();
				
				while (i.hasNext())
				{
					Collection<?> collection = (Collection<?>)i.next();
					
					if (collection.isMatch(test) != opposite)
					{
						return true;
					}
				}
				
				return false;
			}
			
			return data.contains(test) != opposite;
		}
	}
}
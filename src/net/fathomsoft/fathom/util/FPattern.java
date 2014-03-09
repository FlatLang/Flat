package net.fathomsoft.fathom.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class FPattern
{
	private int					mode;
	
	private CollectionCase		start, brokeAt;
	
	private static final int	PRE_MODE = 0, MAIN_MODE = 1, POST_MODE = 2;
	
	/**
	 * Create a TierPattern instance from the given pattern.
	 * 
	 * @param pattern The pattern describing what the TierPattern instance
	 * 		will look for in the data.
	 */
	public FPattern(String pattern)
	{
		compile(pattern);
	}
	
	/**
	 * Compile the pattern for quick searching.
	 * 
	 * @param pattern The pattern to compile into usable data.
	 * @return The first CollectionCase in the pattern.
	 */
	private CollectionCase compile(String pattern)
	{
		return null;
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
		
		/**
		 * Get whether or not the bounds are endless.
		 * 
		 * @return Whether or not the bounds will search endlessly.
		 */
		public boolean isEndless()
		{
			return end < 0;
		}
		
		/**
		 * Generate a String representation of the Bounds Object
		 * containing the start and end position of the Bounds.
		 * 
		 * @see java.lang.Object#toString()
		 * 
		 * @return A String representation of the Bounds Object.
		 */
		public String toString()
		{
			return "[" + start + ", " + end + "]";
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
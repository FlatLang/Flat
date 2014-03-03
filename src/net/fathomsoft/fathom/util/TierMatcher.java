package net.fathomsoft.fathom.util;

import net.fathomsoft.fathom.util.TierPattern.Bounds;

/**
 * Class used to traverse a Pattern linearly.
 * 
 * @author	Braden Steffaniak
 * @since	Feb 1, 2014 at 3:42:04 PM
 * @since	v0.1
 * @version	Feb 1, 2014 at 3:42:04 PM
 * @version	v0.1
 */
public class TierMatcher
{
	private int			index, end;
	
	private String		data, group;
	
	private TierPattern	pattern;
	
	/**
	 * Create a Matcher object from the given String data to search
	 * through, as well as the Pattern object.
	 * 
	 * @param data The data to search through.
	 * @param pattern The Pattern to search for in the data.
	 */
	public TierMatcher(String data, TierPattern pattern)
	{
		this.data    = data;
		this.pattern = pattern;
		
		reset();
	}
	
	/**
	 * Find the next instance of the Pattern in the data text. After the
	 * method executes, the current state of the matcher will update to
	 * the current circumstances.
	 * 
	 * @return Returns whether or not the Pattern was found.
	 */
	public boolean find()
	{
		Bounds bounds = pattern.boundsOf(data, end);
		
		if (bounds == null)
		{
			return false;
		}
		
		index = bounds.getStart();
		end   = bounds.getEnd();
		group = data.substring(index, end);
		
		return index >= 0;
	}
	
	/**
	 * Get the text that was matched the current time. The find method
	 * has to have been called at least one time prior to the invocation
	 * of this method.
	 * 
	 * @return The String representing the Pattern that was matched in
	 * 		the find() method call.
	 */
	public String group()
	{
		return group;
	}
	
	/**
	 * Get the start index of the match at the current time. The find
	 * method has to have been called at least one time prior to the
	 * invocation of this method.
	 * 
	 * @return The start index of the match at the current time.
	 */
	public int start()
	{
		return index;
	}
	
	/**
	 * Get the end index of the match at the current time. The find
	 * method has to have been called at least one time prior to the
	 * invocation of this method.
	 * 
	 * @return The end index of the match at the current time.
	 */
	public int end()
	{
		if (group() == null)
		{
			return -1;
		}
		
		return start() + group().length();
	}
	
	/**
	 * Reset the values of the Matcher. This will make the find method
	 * start from the beginning again.
	 */
	public void reset()
	{
		this.group = null;
		this.index = -1;
	}
}
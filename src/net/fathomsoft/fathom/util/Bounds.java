package net.fathomsoft.fathom.util;

/**
 * Class used to store data about a start and end location.
 * 
 * @author	Braden Steffaniak
 * @since	Mar 12, 2014 at 4:08:41 PM
 * @since	v0.1
 * @version	Mar 12, 2014 at 4:08:41 PM
 * @version	v0.1
 */
public class Bounds
{
	private int	start, end;
	
	/**
	 * Create a Bounds instance with the specified start and
	 * end location.
	 * 
	 * @param start The start location of the Bound.
	 * @param end The end location of the Bound.
	 */
	public Bounds(int start, int end)
	{
		this.start = start;
		this.end   = end;
	}
	
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
	 * Set the start position of the Bounds.
	 * 
	 * @param end The new start position of the Bounds.
	 */
	public void setStart(int start)
	{
		this.start = start;
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
	 * Set the end position of the Bounds.
	 * 
	 * @param end The new end position of the Bounds.
	 */
	public void setEnd(int end)
	{
		this.end = end;
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
	 * Get whether or not the bounds are optional.
	 * 
	 * @return Whether or not the bounds will search optionally.
	 */
	public boolean isOptional()
	{
		return start == 0;
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
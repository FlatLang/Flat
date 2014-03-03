package net.fathomsoft.fathom.util;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 7, 2014 at 10:11:40 AM
 * @since	v
 * @version	Jan 7, 2014 at 10:11:40 AM
 * @version	v
 */
public class Location
{
	private int	lineNumber, offset;
	
	public Location()
	{
		
	}
	
	public Location(int lineNumber, int offset)
	{
		setLineNumber(lineNumber);
		setOffset(offset);
	}
	
	public int getLineNumber()
	{
		return lineNumber;
	}
	
	public void setLineNumber(int lineNumber)
	{
		this.lineNumber = lineNumber;
	}

	public int getOffset()
	{
		return offset;
	}
	
	public void setOffset(int offset)
	{
		this.offset = offset;
	}
}
package net.fathomsoft.fathom.util;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Feb 9, 2014 at 7:50:00 AM
 * @since	v
 * @version	Feb 9, 2014 at 7:50:00 AM
 * @version	v
 */
public class TierExpressionException extends RuntimeException
{
	public TierExpressionException()
	{
		this("");
	}
	
	public TierExpressionException(String message)
	{
		super(message);
	}
}
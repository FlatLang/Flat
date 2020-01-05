package net.fathomsoft.nova.error;

/**
 * Runtime Exception that is thrown if there is an error that was decoded
 * in the syntax.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.8 May 26, 2014 at 4:24:19 PM
 * @version	v0.2.35 Oct 5, 2014 at 11:22:42 PM
 */
public class SyntaxErrorException extends RuntimeException
{
	private String	info;
	
	/**
	 * Generate an exception that outputs a given error message.
	 * 
	 * @param info The error message to be output.
	 */
	public SyntaxErrorException(String info)
	{
		super(info);
		
		this.info = info;
	}
	
	/**
	 * Get the info describing the error that occurred.
	 * 
	 * @return The info describing the error that occurred.
	 */
	public String getInfo()
	{
		return info;
	}
}
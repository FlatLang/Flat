package net.fathomsoft.nova.error;

/**
 * Runtime Exception that is thrown if there is an error that was decoded
 * in the syntax.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.8 May 26, 2014 at 4:24:19 PM
 * @version	v0.2.8 May 26, 2014 at 11:26:58 PM
 */
public class SyntaxErrorException extends RuntimeException
{
	/**
	 * Generate an exception that outputs a given error message.
	 * 
	 * @param message The error message to be output.
	 */
	public SyntaxErrorException(String message)
	{
		super(message);
	}
}
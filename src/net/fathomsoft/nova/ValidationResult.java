package net.fathomsoft.nova;

import net.fathomsoft.nova.tree.Node;

/**
 * Class used to hold information on how a validation method
 * has resulted.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.35 Oct 4, 2014 at 12:37:45 AM
 * @version	v0.2.35 Oct 5, 2014 at 11:22:42 PM
 */
public class ValidationResult
{
	public Node    returnedNode;
	
	public boolean errorOccurred;
	public boolean continueValidation;
	
	/**
	 * Create a new ValidationResult that has the expectation to return
	 * the given Node.
	 * 
	 * @param returnedNode The Node that the validation method can expect
	 * 		to return.
	 */
	public ValidationResult(Node returnedNode)
	{
		this.returnedNode       = returnedNode;
		
		this.continueValidation = true;
		this.errorOccurred      = false;
	}
	
	/**
	 * Declare that the ValidationResult has had an error occur.
	 * 
	 * @param The ValidationResult that the error occurred on.0
	 */
	public ValidationResult errorOccurred()
	{
		errorOccurred = true;
		
		return this;
	}
}
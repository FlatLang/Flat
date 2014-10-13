package net.fathomsoft.nova;

import net.fathomsoft.nova.tree.Node;

/**
 * Class used to hold information on how a validation method
 * has resulted.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.35 Oct 4, 2014 at 12:37:45 AM
 * @version	v0.2.36 Oct 13, 2014 at 12:16:42 AM
 */
public class ValidationResult
{
	public Node    returnedNode;
	
	public boolean errorOccurred;
	public boolean continueValidation;
	public boolean skipCycle;
	
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
		this.skipCycle          = false;
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
	
	public ValidationResult skippedCycle()
	{
		skipCycle = false;
		
		return this;
	}
}
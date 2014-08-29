package net.fathomsoft.nova.tree;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.29 Aug 23, 2014 at 9:35:01 PM
 * @version	v0.2.29 Aug 29, 2014 at 3:17:45 PM
 */
public interface ScopeAncestor
{
	/**
	 * Get a unique integer used for differentiating local variables
	 * within the scope.
	 * 
	 * @return A unique identifier for local variables.
	 */
	public int generateUniqueID();
}
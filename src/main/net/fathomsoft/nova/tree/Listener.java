package net.fathomsoft.nova.tree;

/**
 * 
 * @author Braden Steffaniak
 * @since	v0.2.37 Oct 18, 2014 at 9:00:50 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public interface Listener
{
	public void onAdded(Node parent, Node added);
}
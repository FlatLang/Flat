package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Location;

/**
 * {@link List} extension that abstracts a general list type that can only
 * hold Value types.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.29 Aug 21, 2014 at 11:00:40 PM
 * @version	v0.2.29 Aug 29, 2014 at 3:17:45 PM
 */
public class TypeList<E extends Node> extends List
{
	/**
	 * Instantiate and initialize default data.
	 */
	public TypeList(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getChild(int)
	 */
	@Override
	public E getChild(int index)
	{
		return (E)super.getChild(index);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getVisibleChild(int)
	 */
	@Override
	public E getVisibleChild(int index)
	{
		return (E)super.getVisibleChild(index);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public TypeList<E> clone(Node temporaryParent, Location locationIn)
	{
		TypeList<E> node = new TypeList<E>(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link TypeList} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public TypeList<E> cloneTo(TypeList<E> node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the FieldList class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
}
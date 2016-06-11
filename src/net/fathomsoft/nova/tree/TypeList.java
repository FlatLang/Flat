package net.fathomsoft.nova.tree;

import java.util.ArrayList;
import java.util.Iterator;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * {@link List} extension that abstracts a general list type that can only
 * hold Value types.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.29 Aug 21, 2014 at 11:00:40 PM
 * @version	v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public class TypeList<E extends Node> extends List implements Iterable<E>
{
	/**
	 * Instantiate and initialize default data.
	 */
	public TypeList(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public ArrayList<E> toTypeArray()
	{
		ArrayList<E> nodes = new ArrayList<>();
		
		for (int i = 0; i < getNumVisibleChildren(); i++)
		{
			nodes.add(getVisibleChild(i));
		}
		
		return nodes;
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
	 * @see net.fathomsoft.nova.tree.Node#getLastChild()
	 */
	@Override
	public E getLastChild()
	{
		return (E)super.getLastChild();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getFirstChild()
	 */
	@Override
	public E getFirstChild()
	{
		return (E)super.getFirstChild();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getLastVisibleChild()
	 */
	@Override
	public E getLastVisibleChild()
	{
		return (E)super.getLastVisibleChild();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getFirstVisibleChild()
	 */
	@Override
	public E getFirstVisibleChild()
	{
		return (E)super.getFirstVisibleChild();
	}

	@Override
	public Iterator<E> iterator()
	{
		return new Iterator<E>()
		{
			private int i = 0;
			
			@Override
			public boolean hasNext()
			{
				return i < getNumVisibleChildren();
			}

			@Override
			public E next()
			{
				return getVisibleChild(i++);
			}
		};
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeaderFragment(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeaderFragment(StringBuilder builder)
	{
		for (E child : this)
		{
			child.generateCHeader(builder);
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		for (E child : this)
		{
			child.generateCSource(builder);
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public TypeList<E> clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		TypeList<E> node = new TypeList<E>(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public TypeList<E> cloneTo(TypeList<E> node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link TypeList} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public TypeList<E> cloneTo(TypeList<E> node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
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
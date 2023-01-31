package flat.tree;

import flat.TestContext;
import flat.util.Location;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

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
	
	public boolean contains(E value)
	{
		for (E e : this)
		{
			if (value == e)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean any(Function<E, Boolean> func)
	{
		return firstWhere(func) != null;
	}
	
	public E firstWhere(Function<E, Boolean> func)
	{
		for (E e : this)
		{
			if (func.apply(e))
			{
				return e;
			}
		}
		
		return null;
	}

	public Stream<E> getChildTypeStream() {
		return (Stream<E>)super.getChildStream();
	}

	public void forEachListChild(Consumer<E> action)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			action.accept(getChild(i));
		}
	}
	
	public void forEachVisibleListChild(Consumer<E> action)
	{
		for (int i = 0; i < getNumVisibleChildren(); i++)
		{
			action.accept(getVisibleChild(i));
		}
	}
	
	public ArrayList<E> filterListChildren(Function<E, Boolean> action)
	{
		ArrayList<E> list = new ArrayList<>();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			if (action.apply(getChild(i)))
			{
				list.add(getChild(i));
			}
		}
		
		return list;
	}
	
	public ArrayList<E> filterVisibleListChildren(Function<E, Boolean> action)
	{
		ArrayList<E> list = new ArrayList<>();
		
		for (int i = 0; i < getNumVisibleChildren(); i++)
		{
			if (action.apply(getVisibleChild(i)))
			{
				list.add(getVisibleChild(i));
			}
		}
		
		return list;
	}

	public ArrayList<E> getVisibleListChildren()
	{
		ArrayList<E> list = new ArrayList<>();

		for (int i = 0; i < getNumVisibleChildren(); i++)
		{
			list.add(getVisibleChild(i));
		}

		return list;
	}
	
	/**
	 * @see Node#getChild(int)
	 */
	@Override
	public E getChild(int index)
	{
		return (E)super.getChild(index);
	}
	
	/**
	 * @see Node#getVisibleChild(int)
	 */
	@Override
	public E getVisibleChild(int index)
	{
		return (E)super.getVisibleChild(index);
	}
	
	/**
	 * @see Node#getLastChild()
	 */
	@Override
	public E getLastChild()
	{
		return (E)super.getLastChild();
	}
	
	/**
	 * @see Node#getFirstChild()
	 */
	@Override
	public E getFirstChild()
	{
		return (E)super.getFirstChild();
	}
	
	/**
	 * @see Node#getLastVisibleChild()
	 */
	@Override
	public E getLastVisibleChild()
	{
		return (E)super.getLastVisibleChild();
	}
	
	/**
	 * @see Node#getFirstVisibleChild()
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
	 * @see Node#clone(Node, Location, boolean)
	 */
	@Override
	public TypeList<E> clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		TypeList<E> node = new TypeList<E>(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see Node#cloneTo(Node)
	 */
	public TypeList<E> cloneTo(TypeList<E> node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link TypeList} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public TypeList<E> cloneTo(TypeList<E> node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
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
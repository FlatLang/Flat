package net.fathomsoft.nova.util;

/**
 * Utility class that keeps track of ListNodes.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.14 Jun 17, 2014 at 11:16:22 PM
 * @version	v0.2.14 Jul 19, 2014 at 7:33:13 PM
 */
public class List<E>
{
	private int			size;
	
	private ListNode<E> start, current;
	
	public int getSize()
	{
		return size;
	}
	
	public int size()
	{
		return size;
	}
	
	public E get(int index)
	{
		return getNode(index).getData();
	}
	
	private ListNode<E> getNode(int index)
	{
		ListNode<E> current = start;
		
		for (int i = 0; i < index && current != null; i++)
		{
			current = current.getNext();
		}
		
		return current;
	}
	
	public boolean contains(E data)
	{
		return indexOf(data) >= 0;
	}
	
	public int indexOf(E data)
	{
		ListNode<E> current = start;
		
		for (int i = 0; current != null; i++)
		{
			if (current.getData() == data)
			{
				return i;
			}
			
			current = current.getNext();
		}
		
		return -1;
	}
	
	public void add(E data)
	{
		ListNode<E> node = new ListNode<E>(data);
		
		if (start == null)
		{
			start  = node;
		}
		else
		{
			current.setNext(node);
		}
		
		current = node;
		
		size++;
	}
	
	public boolean add(int index, E data)
	{
		ListNode<E> node = new ListNode<E>(data);
		
		if (index == 0)
		{
			if (current == null)
			{
				current = node;
			}
			else
			{
				node.setNext(start);
			}
			
			start = node;
		}
		else
		{
			ListNode<E> current = getNode(index - 1);
			
			if (current == this.current)
			{
				this.current = node;
			}
			
			current.setNext(node);
		}
		
		size++;
		
		return true;
	}
	
	public boolean remove(E data)
	{
		int index = indexOf(data) - 1;
		
		if (index < -1)
		{
			return false;
		}
		else if (index == -1)
		{
			start = start.getNext();
		}
		else
		{
			ListNode<E> current = getNode(index);
			
			if (index == size - 2)
			{
				this.current = current;
			}
			
			current.setNext(current.getNext().getNext());
		}
		
		size--;
		
		return true;
	}
	
	public boolean insertBefore(E newData, E beforeThis)
	{
		if (start == null)
		{
			return false;
		}
		
		ListNode<E> newNode = new ListNode<E>(newData);
		
		if (start.getData() == beforeThis)
		{
			newNode.setNext(start);
			start = newNode;
			
			size++;
			
			return true;
		}
		
		ListNode<E> current = start;
		ListNode<E> next = current.getNext();
		
		while (next != null)
		{
			if (next.getData() == beforeThis)
			{
				newNode.setNext(next);
				current.setNext(newNode);
				
				size++;
				
				return true;
			}
			
			current = next;
		}
		
		return false;
	}
	
	public boolean insertAfter(E newData, E afterThis)
	{
		ListNode<E> newNode = new ListNode<E>(newData);
		
		if (start == null)
		{
			start = newNode;
			
			size++;
			
			return true;
		}
		
		ListNode<E> current = start;
		
		while (current != null)
		{
			if (current.getData() == afterThis)
			{
				newNode.setNext(current.getNext());
				current.setNext(newNode);
				
				size++;
				
				return true;
			}
			
			current = current.getNext();
		}
		
		return false;
	}
}
package net.fathomsoft.nova.util;

/**
 * Data container used for a List of items.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.14 Jun 17, 2014 at 11:23:16 PM
 * @version	v0.2.14 Jul 19, 2014 at 7:33:13 PM
 */
public class ListNode<E>
{
	private E data;
	
	private ListNode<E> next;
	
	/**
	 * Create a new ListNode with the given data.
	 * 
	 * @param data The data to set in the container.
	 */
	public ListNode(E data)
	{
		this.data = data;
	}
	
	/**
	 * Create a new ListNode with the given data and the given next
	 * ListNode reference.
	 * 
	 * @param data The data to set in the container.
	 * @param next The ListNode after this one in the List.
	 */
	public ListNode(E data, ListNode<E> next)
	{
		this(data);
		
		this.next = next;
	}
	
	/**
	 * Get the data within the container.
	 * 
	 * @return The data within the container.
	 */
	public E getData()
	{
		return data;
	}
	
	/**
	 * Set the data in the container to the given data.
	 * 
	 * @param data The data to set in the container.
	 */
	public void setData(E data)
	{
		this.data = data;
	}
	
	/**
	 * Get the next ListNode reference in the parent List.
	 * 
	 * @return The ListNode after the specified one in the List.
	 */
	public ListNode<E> getNext()
	{
		return next;
	}
	
	/**
	 * Set the next ListNode reference in the parent List.
	 * 
	 * @param next The ListNode after the specified one in the List.
	 */
	public void setNext(ListNode<E> next)
	{
		this.next = next;
	}
}
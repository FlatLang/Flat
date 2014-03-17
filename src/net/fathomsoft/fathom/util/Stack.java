/**
 * The Fathom Programming Language. Write Unbelievable Code.
 *  Copyright (C) 2014  Braden Steffaniak <BradenSteffaniak@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.fathomsoft.fathom.util;

import java.util.ArrayList;

/**
 * Class that emulates the actions of a Stack.
 * 
 * @author	Braden Steffaniak
 * @since	Jun 7, 2013 at 9:03:38 PM
 * @since	v0.1
 * @version	Jan 9, 2014 at 3:00:00 PM
 * @version	v0.1
 */
public class Stack<E>
{
	private	boolean			allowDuplicates;
	
	private	ArrayList<E>	stack;
	
	/**
	 * Create the Stack instance.
	 */
	public Stack()
	{
		stack = new ArrayList<E>();
		
		setAllowDuplicates(true);
	}
	
	/**
	 * Push an element to the beginning of the Stack.
	 * 
	 * @param element The element to push.
	 * @return Whether the element was successfully pushed or not.
	 */
	public boolean push(E element)
	{
		boolean added = false;
		
		if (allowDuplicates || !stack.contains(element))
		{
			stack.add(0, element);
			
			added = true;
		}
		
		return added;
	}
	
	/**
	 * Pop the last element added (that has yet to be removed)
	 * from the Stack and return it to you.
	 * 
	 * @return The first element that has yet to be removed.
	 */
	public E pop()
	{
		return stack.remove(0);
	}
	
	/**
	 * Peek at the last added element in the Stack without
	 * removing it.
	 * 
	 * @return The last element added in the Stack.
	 */
	public E peek()
	{
		return peek(0);
	}
	
	/**
	 * Peek at the element in the Stack at the specified
	 * index without removing it.
	 * 
	 * @param index The index of the Stack to peek at. 0 == newest,
	 * 		size() - 1 == oldest.
	 * @return The element at the specified index in the Stack.
	 */
	public E peek(int index)
	{
		return stack.get(index);
	}
	
	/**
	 * Get the size of the Stack.
	 * 
	 * @return The number of elements in the Stack.
	 */
	public int size()
	{
		return stack.size();
	}
	
	/**
	 * Returns whether the Stack allows duplicate entries into the Stack.
	 * 
	 * @return Whether the Stack allows duplicate entries into the Stack.
	 */
	public boolean doesAllowDuplicates()
	{
		return allowDuplicates;
	}
	
	/**
	 * Set whether or not to allow duplicate entries to the Stack.
	 * 
	 * @param allow Whether or not to allow duplicate entries to the
	 * 		Stack.
	 */
	public void setAllowDuplicates(boolean allow)
	{
		allowDuplicates = allow;
	}
	
	/**
	 * Return whether or not the Stack contains the specific Element
	 * instance.
	 * 
	 * @param element The Element to check if the Stack contains.
	 * @return Whether or not the Stack contains the specific Element
	 * 		instance.
	 */
	public boolean contains(E element)
	{
		return stack.contains(element);
	}
	
	/**
	 * Return whether or not the Stack is empty of any elements.
	 * 
	 * @return Whether or not the Stack is empty of any elements.
	 */
	public boolean isEmpty()
	{
		return stack.size() <= 0;
	}
	
	/**
	 * Create a String representation of this Stack instance. Represents
	 * the elements from last added to first added.
	 * 
	 * @return  A String representation of this Stack instance.
	 */
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(this.getClass().getSimpleName() + " { ");
		
		for (int i = 0; i < stack.size(); i++)
		{
			builder.append(stack.get(i) + ", ");
		}
		
		builder.deleteCharAt(builder.length() - 1);
		
		if (stack.size() > 0)
		{
			builder.deleteCharAt(builder.length() - 1);
		}
		
		builder.append(" }");
		
		return builder.toString();
	}
}
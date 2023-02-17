package flat.util;

import java.util.ArrayList;

/**
 * Class that emulates the actions of a Stack.
 *
 * @author Braden Steffaniak
 * @since v0.1 Jun 7, 2013 at 9:03:38 PM
 * @version v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class Stack<E> {
    private boolean allowDuplicates;

    private ArrayList<E> stack;

    /**
     * Create the Stack instance.
     */
    public Stack() {
        stack = new ArrayList<E>();

        setAllowDuplicates(true);
    }

    /**
     * Push an element to the beginning of the Stack.
     *
     * @param element The element to push.
     * @return Whether the element was successfully pushed or not.
     */
    public boolean push(E element) {
        boolean added = false;

        if (allowDuplicates || !stack.contains(element)) {
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
    public E pop() {
        return stack.remove(0);
    }

    /**
     * Peek at the most recently added element in the Stack without
     * removing it, if it exists. If it does not exist, .
     *
     * @return The most recently added element added in the Stack.
     */
    public E check() {
        if (isEmpty()) {
            return null;
        }

        return peek(0);
    }

    /**
     * Peek at the most recently added element in the Stack without
     * removing it.
     *
     * @return The most recently added element added in the Stack.
     */
    public E peek() {
        return peek(0);
    }

    /**
     * Peek at the element in the Stack at the specified
     * index without removing it.
     *
     * @param index The index of the Stack to peek at. 0 == newest,
     *              size() - 1 == oldest.
     * @return The element at the specified index in the Stack.
     */
    public E peek(int index) {
        return stack.get(index);
    }

    /**
     * Get the size of the Stack.
     *
     * @return The number of elements in the Stack.
     */
    public int size() {
        return stack.size();
    }

    /**
     * Returns whether the Stack allows duplicate entries into the Stack.
     *
     * @return Whether the Stack allows duplicate entries into the Stack.
     */
    public boolean doesAllowDuplicates() {
        return allowDuplicates;
    }

    /**
     * Set whether or not to allow duplicate entries to the Stack.
     *
     * @param allow Whether or not to allow duplicate entries to the
     *              Stack.
     */
    public void setAllowDuplicates(boolean allow) {
        allowDuplicates = allow;
    }

    /**
     * Return whether or not the Stack contains the specific Element
     * instance.
     *
     * @param element The Element to check if the Stack contains.
     * @return Whether or not the Stack contains the specific Element
     * instance.
     */
    public boolean contains(E element) {
        return stack.contains(element);
    }

    /**
     * Return whether or not the Stack is empty of any elements.
     *
     * @return Whether or not the Stack is empty of any elements.
     */
    public boolean isEmpty() {
        return stack.size() <= 0;
    }

    public void empty() {
        stack = new ArrayList<E>();
    }

    /**
     * Create a String representation of this Stack instance. Represents
     * the elements from last added to first added.
     *
     * @return A String representation of this Stack instance.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(this.getClass().getSimpleName() + " { ");

        for (int i = 0; i < stack.size(); i++) {
            builder.append(stack.get(i) + ", ");
        }

        builder.deleteCharAt(builder.length() - 1);

        if (stack.size() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }

        builder.append(" }");

        return builder.toString();
    }
}
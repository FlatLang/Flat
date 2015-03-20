package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.36 Oct 6, 2014 at 9:58:16 PM
 * @version	v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public interface Accessible
{
	public Node getParent();
	
	/**
	 * Get the next accessed node that is of the given class type.
	 * 
	 * @param type The class type to search for.
	 * @return The next accessed node of the given type. If there are
	 * 		no matches, null is returned.
	 */
	public default Accessible getNextAccessingOfType(Class<?> type)
	{
		return getNextAccessingOfType(new Class<?>[] { type });
	}
	
	/**
	 * Get the next accessed node that is of the given class types.
	 * 
	 * @param types The class types to search for.
	 * @return The next accessed node of the given types. If there are
	 * 		no matches, null is returned.
	 */
	public default Accessible getNextAccessingOfType(Class<?> types[])
	{
		return getNextAccessingOfType(types, false);
	}
	
	/**
	 * Get the next accessed node that is of the given class types.
	 * 
	 * @param types The class types to search for.
	 * @param opposite Whether or not to search for or against the given
	 * 		data.
	 * @return The next accessed node of the given types. If there are
	 * 		no matches, null is returned.
	 */
	public default Accessible getNextAccessingOfType(Class<?> types[], boolean opposite)
	{
		Accessible current = getAccessingNode();
		
		while (current != null && SyntaxUtils.checkTypes(types, current.getClass()) == opposite)
		{
			current = current.getAccessingNode();
		}
		
		return current;
	}
	
	/**
	 * Get the last accessed node of the given type that was in a series.
	 * In other words, if we are looking for the last method call and
	 * there are three consecutive method calls in a row, the third method
	 * call node would be returned.
	 * 
	 * @param type The class type to search for.
	 * @param opposite Whether or not to search for or against the given
	 * 		type.
	 * @return The last accessed node of the given type. If there is not a
	 * 		match, null is returned.
	 */
	public default Accessible getLastAccessingOfType(Class<?> type, boolean opposite)
	{
		return getLastAccessingOfType(new Class<?>[] { type }, opposite);
	}
	
	/**
	 * Get the last accessed node of the given types that was in a series.
	 * In other words, if we are looking for the last method call and
	 * there are three consecutive method calls in a row, the third method
	 * call node would be returned.
	 * 
	 * @param types An array of accepted types.
	 * @param opposite Whether or not to search for or against the given
	 * 		data.
	 * @return The last accessed node of the given types. If there is not
	 * 		a match, null is returned.
	 */
	public default Accessible getLastAccessingOfType(Class<?> types[], boolean opposite)
	{
		Accessible previous = null;
		Accessible current  = this;
		
		while (current != null && SyntaxUtils.checkTypes(types, current.getClass()) != opposite)
		{
			previous = current;
			current  = current.getAccessingNode();
		}
		
		return previous;
	}
	
	/**
	 * Get the next node that this node accesses that is of the given
	 * type.
	 * 
	 * @param type The type to search for.
	 * @return The next accessed of the given type. If there is not a
	 * 		match, null is returned.
	 */
	public default Identifier getNextAccessedOfType(Class<?> type)
	{
		return getNextAccessedOfType(new Class<?>[] { type });
	}
	
	/**
	 * Get the next node that this node accesses that is of the given
	 * types.
	 * 
	 * @param types The types to search for.
	 * @return The next accessed of the given types. If there is not a
	 * 		match, null is returned.
	 */
	public default Identifier getNextAccessedOfType(Class<?> types[])
	{
		Identifier current = getAccessedNode();
		
		while (current != null && !SyntaxUtils.checkTypes(types, current.getClass()))
		{
			current = current.getAccessedNode();
		}
		
		return current;
	}
	
	/**
	 * Get the root variable that is accessing the specified Identifier.
	 * 
	 * @return The root variable that is accessing the specified
	 * 		Identifier.
	 */
	public default Accessible getRootReferenceNode()
	{
		return getRootReferenceNode(false);
	}
	
	/**
	 * Get the root variable that is accessing the specified Identifier.
	 * 
	 * @param inclusive Whether or not to return the specified Identifier
	 * 		if the Identifier is not accessed.
	 * @return The root variable that is accessing the specified
	 * 		Identifier.
	 */
	public default Accessible getRootReferenceNode(boolean inclusive)
	{
		if (!isAccessed())
		{
			if (inclusive)
			{
				return this;
			}
			
			return getReferenceNode();
		}
		
		Accessible reference = getReferenceNode();
		
		Accessible node = reference.getLastAccessingOfType(new Class<?>[] { Closure.class, MethodCall.class }, true);
		
		if (node == null)
		{
			return reference;
		}
		
		return node;
	}
	
	/**
	 * Get the Node that represents the value that contains the value.<br>
	 * For example:<br>
	 * <blockquote><pre>
	 * ClassName obj = new ClassName();
	 * 
	 * obj.data.methodName();</pre></blockquote>
	 * In the previous statements, the method "<u><code>methodName()</code></u>"
	 * is being referenced through the "<u><code>data</code></u>"
	 * identifier.<br>
	 * <br>
	 * If the identifier is not explicitly called through an object, it
	 * will return the instance type that the node was referenced
	 * within.<br>
	 * For example:
	 * <blockquote><pre>
	 * obj.calculateArea().width;</pre></blockquote>
	 * The Identifier for the method call "<u><code>calculateArea()</code></u>"
	 * is returned.<br>
	 * <br>
	 * If the node is referenced within a static context, the containing
	 * ClassDeclaration is returned.<br>
	 * For example:
	 * <blockquote><pre>
	 * Time.currentTimeMillis();</pre></blockquote>
	 * The Identifier for the ClassDeclaration "<u><code>Time</code></u>" is
	 * returned.
	 * 
	 * @return The Node that represents the calling Identifier.
	 */
	public default Accessible getReferenceNode()
	{
		Value n = (Value)this;
		
		Accessible accessing = getAccessingNode();
		
		if (accessing != null)
		{
			return accessing;
		}
		
		MethodDeclaration methodDeclaration = n.getParentMethod();
		
		if (methodDeclaration != null)
		{
			Identifier id = n.getObjectReferenceNode(methodDeclaration);
			
			if (id != null)
			{
				return id;
			}
		}
		
		return n.getParentClass();
	}
	
	/**
	 * Get the farthest node that is accessing the specified identifier.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * tree.next.calculateSize()</pre></blockquote>
	 * In the previous statement, "<code>tree</code>" is the context. If
	 * the identifier is not accessed by any other identifiers, then
	 * null is returned.<br>
	 * For example:
	 * <blockquote><pre>
	 * calculateSize()</pre></blockquote>
	 * The above would return null.
	 * 
	 * @return The furthest node that accesses the specified identifier.
	 */
	public default Accessible getContextNode()
	{
		return getContextNode(getReferenceNode());
	}
	
	/**
	 * Get the furthest node that is accessing the specified identifier.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * tree.next.calculateSize()</pre></blockquote>
	 * In the previous statement, "<code>tree</code>" is the context. If
	 * the identifier is not accessed by any other identifiers, then
	 * null is returned.<br>
	 * For example:
	 * <blockquote><pre>
	 * calculateSize()</pre></blockquote>
	 * The above would return null.
	 * 
	 * @param escape The Identifier if it the specified Identifier
	 * 		is not accessed through any Identifiers.
	 * @return The furthest node that accesses the specified identifier.
	 */
	public default Accessible getContextNode(Accessible escape)
	{
		Accessible node = this;
		
		if (!node.isAccessed())
		{
			return escape;
		}
		
		while (node.isAccessed())
		{
			node = node.getAccessingNode();
		}
		
		return node;
	}
	
	/**
	 * Get the furthest node that is accessing the specified identifier.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * tree.next.calculateSize()</pre></blockquote>
	 * In the previous statement, "<code>tree</code>" is the context. This
	 * differs from the getContextNode() call by returning the specified
	 * node if the identifier is not accessed by any other identifiers.<br>
	 * For example:
	 * <blockquote><pre>
	 * calculateSize()</pre></blockquote>
	 * The above would return the method call identifier for
	 * "<code>calculateSize()</code>".
	 * 
	 * @return The furthest node that accesses the specified identifier.
	 */
	public default Accessible getRootAccessNode()
	{
		return getContextNode(this);
	}
	
	/**
	 * Get the root Node that accesses the specified Identifier. This
	 * only differs from getRootAccessNode() in one aspect: If the
	 * root access Node's parent is an Instantiation, return the
	 * Instantiation and not the MethodCall instance.
	 * 
	 * @return The root Node that accesses the specified Identifier.
	 */
	public default Accessible getRootNode()
	{
		Accessible node = getRootAccessNode();
		Node       n    = (Node)node;
		
		if (n.getParent() instanceof Instantiation)
		{
			return (Instantiation)n.getParent();
		}
		
		return node;
	}
	
	/**
	 * Get the last node that is accessed by the specified node.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * tree.next.calculateSize()</pre></blockquote>
	 * In the previous statement, "<code>calculateSize()</code>" is the
	 * last accessed node.
	 * 
	 * @return The last node that is accessed by the specified node.
	 */
	public default Identifier getLastAccessedNode()
	{
		Identifier prev = null;
		Identifier node = getAccessedNode();
		
		while (node != null)
		{
			prev = node;
			node = node.getAccessedNode();
		}
		
		return prev;
	}
	
	/**
	 * Get the Value that returns a value if it is used in an
	 * expression.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * // Scenario 1
	 * tree.value.next;
	 * 
	 * //Scenario 2
	 * tree;</pre></blockquote>
	 * In scenario 1, "<u><code>next</code></u>" is the returned node
	 * because it is the last accessed node. In scenario 2
	 * "<u><code>tree</code></u>" is the returned node because it does not
	 * access any nodes and is therefore the value that is returned.
	 * 
	 * @return The last accessed node, or if the node does not access any
	 * 		nodes, it returns itself.
	 */
	public default Value getReturnedNode()
	{
		Identifier lastAccessed = getLastAccessedNode();
		
		if (lastAccessed != null)
		{
			return lastAccessed;
		}
		
		return (Value)this;
	}
	
	/**
	 * Get whether or not the specified Identifier accesses another Node.
	 * For more information see {@link #getAccessedNode()}
	 * 
	 * @return The next node that is accessed by the specified node.
	 */
	public default boolean doesAccess()
	{
		return getAccessedNode() != null;
	}
	
	/**
	 * Get the next node that is accessed by the specified node.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * tree.next.calculateSize()</pre></blockquote>
	 * In the previous statement, "<code>calculateSize()</code>" is the
	 * accessed node of the "<code>next</code>" node, and
	 * "<code>next</code>" is the accessed node of the "<code>tree</code>"
	 * node.
	 * 
	 * @return The next node that is accessed by the specified node.
	 */
	public default Identifier getAccessedNode()
	{
		Node n = (Node)this;
		
		if (n.getNumVisibleChildren() <= 0 || n.getVisibleChild(0) instanceof Identifier == false)
		{
			return null;
		}
		
		return (Identifier)n.getVisibleChild(0);
	}
	
	/**
	 * Set the Identifier that this Identifier accesses.
	 * 
	 * @param node The Identifier for this Identifier to access.
	 */
	public default void setAccessedNode(Identifier node)
	{
		Node n = (Node)this;
		
		if (doesAccess())
		{
			n.replace(getAccessedNode(), node);
		}
		else if (node != null)
		{
			n.addChild(node);
		}
	}
	
	/**
	 * Get whether this specified identifier node was accessed through
	 * the dot operator of another Identifier.
	 * 
	 * @return Whether or not the identifier was accessed.
	 */
	public default boolean isAccessed()
	{
		return getAccessingNode() != null;
	}
	
	public default boolean canAccess()
	{
		Value n = (Value)this;
		
		return n.getAncestorWithScope() != null;
	}
	
	/**
	 * Get whether or not the specified identifier is still in the
	 * process of being decoded.
	 * 
	 * @param node The node that is thought to be accessed.
	 * @return Whether or not the specified identifier is still in the
	 * 		process of being decoded.
	 */
	public default boolean isDecodingAccessedNode(Node node)
	{
		return node.getParent() == this && !((Node)this).containsChild(node);
	}
	
	/**
	 * Get the Identifier Node that accesses the specified Identifier
	 * Node. If the Identifier is not accessed through a Node, then
	 * null is returned.
	 * 
	 * @return The Identifier Node that accesses the specified Identifier
	 * 		Node.
	 */
	public default Accessible getAccessingNode()
	{
		Node n = (Node)this;
		
		if (canAccess() && n.getParent() instanceof Accessible && !n.getParent().containsScope())
		{
			Accessible id = (Accessible)n.getParent();
			
			if (id.isDecodingAccessedNode(n) || id.getAccessedNode() == this)
			{
				return id;
			}
		}
		
		return null;
	}
	
	/**
	 * Generate the C output for when this value node is being used
	 * as an argument for a method call.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @param callingMethod The method that is being called by the
	 * 		specified Identifier.
	 * @return The C output for when this value node is being used
	 * 		as an argument for a method call.
	 */
	public default StringBuilder generateCArgumentReference(StringBuilder builder, Identifier callingMethod)
	{
		Value n = (Value)this;
		
		n.generateCUseOutput(builder);

		generateChildrenCSourceFragment(builder, true, callingMethod);
		
		return builder;
	}
	
	/**
	 * Generate a String representing the accessed nodes.
	 * 
	 * @return The generated String.
	 */
	public default StringBuilder generateChildrenCSourceFragment()
	{
		return generateChildrenCSourceFragment(new StringBuilder(), true);
	}
	
	/**
	 * Generate a String representing the accessed nodes.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @return The StringBuilder with the appended generation output.
	 */
	public default StringBuilder generateChildrenCSourceFragment(StringBuilder builder)
	{
		return generateChildrenCSourceFragment(builder, true);
	}
	
	/**
	 * Generate a String representing the accessed nodes.
	 * 
	 * @param reference Whether or not to start the string off with
	 * 		a "-&gt;" reference operator.
	 * @return The generated String.
	 */
	public default StringBuilder generateChildrenCSourceFragment(boolean reference)
	{
		return generateChildrenCSourceFragment(new StringBuilder(), reference, null);
	}
	
	/**
	 * Generate a String representing the accessed nodes.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @param reference Whether or not to start the string off with
	 * 		a "-&gt;" reference operator.
	 * @return The StringBuilder with the appended generation output.
	 */
	public default StringBuilder generateChildrenCSourceFragment(StringBuilder builder, boolean reference)
	{
		return generateChildrenCSourceFragment(builder, reference, null);
	}
	
	/**
	 * Generate a String representing the accessed nodes.
	 * 
	 * @param reference Whether or not to start the string off with
	 * 		a "-&gt;" reference operator.
	 * @param stopBefore The Identifier to stop the generation before.
	 * @return The generated String.
	 */
	public default StringBuilder generateChildrenCSourceFragment(boolean reference, Identifier stopBefore)
	{
		return generateChildrenCSourceFragment(new StringBuilder(), reference, stopBefore);
	}
	
	// TODO: use stopAt instead of stopBefore.
	
	/**
	 * Generate a String representing the accessed nodes.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @param reference Whether or not to start the string off with
	 * 		a "-&gt;" reference operator.
	 * @param stopBefore The Identifier to stop the generation before.
	 * @return The StringBuilder with the appended generation output.
	 */
	public default StringBuilder generateChildrenCSourceFragment(StringBuilder builder, boolean reference, Identifier stopBefore)
	{
		Identifier child = getAccessedNode();
		
		if (child == null)
		{
			return builder;
		}
		
		StringBuilder output = child.generateChildCSourceFragment(reference, stopBefore);
		
		if (output.length() > 0 && reference)
		{
			builder.append("->");
		}
		
		return builder.append(output);
	}
	
	/**
	 * Generate the source fragment for the specified node.
	 * 
	 * @param reference Whether or not to prepend the "->" operator at
	 * 		the beginning of the generated output.
	 * @param stopBefore The Identifier to stop the generation before.
	 * @return The StringBuilder with the appended generation output.
	 */
	public default StringBuilder generateChildCSourceFragment(boolean reference, Identifier stopBefore)
	{
		Value n = (Value)this;
		
		StringBuilder builder = new StringBuilder();
		
		// If generating the output for the use of an argument.
		if (stopBefore != null)
		{
			if (this == stopBefore)//instanceof MethodCall || this instanceof Instantiation)
			{
				return builder;
			}
			
			return n.generateCUseOutput(builder).append(generateChildrenCSourceFragment(true, stopBefore));
		}
		
		if (n instanceof Identifier)
		{
			Identifier id = (Identifier)n;
			
			if (id.isSpecialFragment())
			{
				id.generateSpecialFragment(builder);
			}
		}
		
		return n.generateCSourceFragment(builder);
	}
	
	/**
	 * Generate the Nova input String until the given stopAt Identifier
	 * is found.
	 * 
	 * @param stopAt The Node to stop at.
	 * @return The generated Nova input.
	 */
	public default StringBuilder generateNovaInputUntil(Accessible stopAt)
	{
		return generateNovaInputUntil(new StringBuilder(), stopAt);
	}
	
	/**
	 * Generate the Nova input String until the given stopAt Identifier
	 * is found.
	 * 
	 * @param builder The builder to append the data to.
	 * @param stopAt The Node to stop at. (This Node will output)
	 * @return The generated Nova input.
	 */
	public default StringBuilder generateNovaInputUntil(StringBuilder builder, Accessible stopAt)
	{
		Node n = (Node)this;
		
		if (stopAt == null)
		{
			return n.generateNovaInput(builder, true);
		}
		
		stopAt = stopAt.getAccessedNode();
		
		Accessible current = this;
		
		while (current != stopAt)
		{
			((Node)current).generateNovaInput(builder, false).append('.');
			
			current = current.getAccessedNode();
		}
		
		return builder.deleteCharAt(builder.length() - 1);
	}
	
	/**
	 * Generate the C Source for the Identifier and the Identifiers that
	 * it accesses until the given 'stopAt' Identifier is reached.
	 * 
	 * @param delimiter The String to append in between each Identifier
	 * 		that is accessed.
	 * @param stopAt The Identifier to stop the generation before.
	 * @return The StrignBuilder with the appended data.
	 */
	public default StringBuilder generateCSourceUntil(String delimiter, Identifier stopAt)
	{
		return generateCSourceUntil(new StringBuilder(), delimiter, stopAt);
	}
	
	/**
	 * Generate the C Source for the Identifier and the Identifiers that
	 * it accesses until the given 'stopAt' Identifier is reached.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @param delimiter The String to append in between each Identifier
	 * 		that is accessed.
	 * @param stopAt The Identifier to stop the generation before.
	 * @return The StrignBuilder with the appended data.
	 */
	public default StringBuilder generateCSourceUntil(StringBuilder builder, String delimiter, Identifier stopAt)
	{
		Accessible current = this;
		
		while (current != null && current != stopAt)
		{
			((Value)current).generateCUseOutput(builder).append(delimiter);
			
			current = current.getAccessedNode();
		}
		
		return builder;
	}
	
	public default ClassDeclaration getDeclaringClass()
	{
		if (isAccessed())
		{
			return ((Value)getAccessingNode()).getTypeClass();
		}
		
		return ((Node)this).getParentClass();
		
//		throw new UnimplementedOperationException("Class " + getClass().getName() + " has not implemented the getDeclaringClass() method.");
	}
}
package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.tree.variables.Field;
import net.fathomsoft.nova.tree.variables.LocalVariable;
import net.fathomsoft.nova.util.Location;


/**
 * Value extension that represents an Identifier. For the rules on
 * what can and cannot be an Identifier, refer to
 * {@link net.fathomsoft.nova.tree.Identifier#setName(java.lang.String) setName}
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:00:19 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class Identifier extends Value
{
	private String	name;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Identifier(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get the name of the Identifier. For the rules on what can and
	 * cannot be an Identifier, refer to {@link net.fathomsoft.nova.tree.Identifier#setName(java.lang.String) setName}
	 * 
	 * @return The name of the Identifier.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Set the name of the Identifier. Identifier names consist of the
	 * following character types: [A-Z, a-z, 0-9, _]. However, cannot
	 * start with a number.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>correctGrammar</li>
	 * 	<li>INCORrect_grammaR_but_123123STILL_workz</li>
	 * 	<li>identifierName4</li>
	 * </ul>
	 * <br>
	 * Incorrect inputs include:<br>
	 * <ul>
	 * 	<li>24JeffGordon <i>(Cannot start an identifier with a number)</i></li>
	 * 	<li>This.Doesnt_work <i>(Cannot contain a period (or any other punctuation))</i></li>
	 * 	<li>#omgProgramin <i>(This is not Twitter)</i></li>
	 * </ul>
	 * 
	 * @param name The String containing the name to set as the
	 * 		identifier.
	 */
	public void setName(String name)
	{
//		if (!SyntaxUtils.isValidIdentifier(name))
//		{
//			SyntaxMessage.error("'" + name + "' is not a valid identifier", this);
//		}
		
		this.name = name;
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
	public Value getReturnedNode()
	{
		Identifier lastAccessed = getLastAccessedNode();
		
		if (lastAccessed != null)
		{
			return lastAccessed;
		}
		
		return this;
	}
	
	/**
	 * Get whether this specified identifier node was accessed through
	 * the dot operator of another Identifier.
	 * 
	 * @return Whether or not the identifier was accessed.
	 */
	public boolean isAccessed()
	{
		Node parent = getParent();
		
		if (parent instanceof Identifier)
		{
			Identifier value = (Identifier)parent;
			
			return value.getAccessedNode() == this;
		}
		
		return false;
	}
	
	/**
	 * Get the Node that represents the variable that contains
	 * the value. For example:<br>
	 * <blockquote><pre>
	 * ClassName obj = new ClassName();
	 * 
	 * obj.data.methodName();</pre></blockquote>
	 * In the previous statements, "<code>data</code>" is the reference
	 * because the method "<code>methodName()</code>" is being called
	 * through the "<code>data</code>" variable.
	 * 
	 * @return The Node that represents the calling variable.
	 */
	public Identifier getReferenceNode()
	{
		return getReferenceNode(getParent());
	}
	
	/**
	 * Get the Node that represents the value that contains
	 * the value.<br>
	 * For example:<br>
	 * <blockquote><pre>
	 * ClassName obj = new ClassName();
	 * 
	 * obj.data.methodName();</pre></blockquote>
	 * In the previous statements, "<code>obj</code>" is the variable and
	 * the method "<code>methodName()</code>" is being called through the
	 * "<code>data</code>" identifier.<br>
	 * <br>
	 * If the identifier is not explicitly called through an object, it
	 * will return the instance type that the node was referenced
	 * within.<br>
	 * For example:
	 * <blockquote><pre>
	 * obj.calculateArea().width;</pre></blockquote>
	 * The Identifier for the method call "<code>calculateArea()</code>"
	 * is returned.<br>
	 * <br>
	 * If the node is referenced within a static context, the containing
	 * ClassDeclaration is returned.<br>
	 * For example:
	 * <blockquote><pre>
	 * Time.currentTimeMillis();</pre></blockquote>
	 * The Identifier for the ClassDeclaration "<code>Time</code>" is returned.
	 * 
	 * @param parent The parent of the Identifier.
	 * @return The Node that represents the calling Identifier.
	 */
	private Identifier getReferenceNode(Node parent)
	{
		if (parent instanceof Identifier && !parent.containsScope())
		{
			Identifier id = (Identifier)parent;
			
			// Can't only check if this is the accessed node because the node might
			// still be in creation while this is called, and therefore the parent
			// might only be a temp parent.
			if (id.isDecodingAccessedNode(this) || id.getAccessedNode() == this)
			{
				return id;
			}
		}
		
		Method method = parent.getMethod();
		
		if (method != null)
		{
			Identifier id = getObjectReferenceNode(method);
			
			if (id != null)
			{
				return id;
			}
		}
		
		return parent.getClassDeclaration();
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
	 * @return The furthest node that accesses the specified identifier.
	 */
	public Identifier getContextNode()
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
	 * @param parent The parent of the Identifier to use.
	 * @return The furthest node that accesses the specified identifier.
	 */
	public Identifier getContextNode(Identifier parent)
	{
		Node next = parent;
		
		while (next instanceof LocalVariable || next instanceof Field)
		{
			parent = (Identifier)next;
			next   = parent.getParent();
		}
		
		return parent;
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
	public Value getRootAccessNode()
	{
		return getContextNode(this);
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
	public Identifier getLastAccessedNode()
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
	public Identifier getAccessedNode()
	{
		if (getNumChildren() <= 0)
		{
			return null;
		}
		
		return (Identifier)getChild(0);
	}
	
	/**
	 * Get whether or not the specified identifier is still in the
	 * process of being decoded.
	 * 
	 * @param node The node that is thought to be accessed.
	 * @return Whether or not the specified identifier is still in the
	 * 		process of being decoded.
	 */
	public boolean isDecodingAccessedNode(Node node)
	{
		return node.getParent() == this && !containsChild(node);
	}
	
	/**
	 * Generate the C output for when this value node is being used
	 * as an argument for a method call.
	 * 
	 * @return The C output for when this value node is being used
	 * 		as an argument for a method call.
	 */
	public String generateArgumentReference(MethodCall callingMethod)
	{
		return generateUseOutput() + generateChildrenCSourceFragment(true, callingMethod);
	}
	
	/**
	 * Generate a String representing the accessed nodes.
	 * 
	 * @return The generated String.
	 */
	public String generateChildrenCSourceFragment()
	{
		return generateChildrenCSourceFragment(true);
	}
	
	/**
	 * Generate a String representing the accessed nodes.
	 * 
	 * @param reference Whether or not to start the string off with
	 * 		a "->" reference operator.
	 * @return The generated String.
	 */
	public String generateChildrenCSourceFragment(boolean reference)
	{
		return generateChildrenCSourceFragment(reference, null);
	}
	
	/**
	 * Generate a String representing the accessed nodes.
	 * 
	 * @param reference Whether or not to start the string off with
	 * 		a "->" reference operator.
	 * @param callingMethod The method that the node is calling.
	 * @return The generated String.
	 */
	public String generateChildrenCSourceFragment(boolean reference, MethodCall callingMethod)
	{
		StringBuilder builder = new StringBuilder();
		
		if (getNumChildren() <= 0)
		{
			return "";
		}
		
		Identifier child = ((Identifier)this).getAccessedNode();
		
		if (child == null)
		{
			return "";
		}
		
		if (reference)
		{
			builder.append("->");
		}
		
		if (callingMethod != null)
		{
			if (child instanceof MethodCall || child instanceof Instantiation)
			{
				return "";
			}
			
			builder.append(child.generateUseOutput() + child.generateChildrenCSourceFragment(true, callingMethod));
			
			return builder.toString();
		}
		
		if (child instanceof Identifier)
		{
			Identifier value = (Identifier)child;
			
			if (value.isSpecialFragment())
			{
				builder.append(value.generateSpecialFragment());//value.generateUseOutput() + value.generateChildrenCSourceFragment(true, argument));
				
				return builder.toString();
			}
		}
		
		String s = child.generateCSourceFragment();
		
		if (s != null)
		{
			builder.append(child.generateCSourceFragment());
		}
		else
		{
			if (reference)
			{
				builder.delete(builder.length() - 2, builder.length());
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * If the Value accesses a method call, generate a specialized
	 * output.
	 * 
	 * @return A specialized String generation.
	 */
	public String generateSpecialFragment()
	{
		Node current = ((Identifier)this).getLastAccessedNode();
		
		while (!(current instanceof MethodCall || current instanceof Instantiation))
		{
			current = current.getParent();
		}
		
		return current.generateCSourceFragment();
		
//		while (current != null)
//		{
//			if (current instanceof MethodCall || current instanceof Instantiation)
//			{
//				
//			}
//			else if (current instanceof Value)
//			{
//				Value value = (Value)current;
//				
//				if (value.isSpecialFragment())
//				{
//					return value.generateSpecialFragment();
//				}
//			}
//			
//			current = current.getParent();
//		}
//		
//		return null;
	}
	
	/**
	 * Get whether or not the Value accesses a method call.
	 * 
	 * @return Whether or not the Value accesses a method call.
	 */
	public boolean isSpecialFragment()
	{
		Identifier current = ((Identifier)this).getAccessedNode();
		
		if (current == null)
		{
			return false;
		}
		else if (current instanceof MethodCall || current instanceof Instantiation)
		{
			return true;
		}
		
		return current.isSpecialFragment();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(boolean)
	 */
	@Override
	public String generateNovaInput(boolean outputChildren)
	{
		return name;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		return name;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		return generateCSourceFragment() + ";\n";
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		if (isSpecialFragment())
		{
			return generateSpecialFragment();
		}
		
		return name + generateChildrenCSourceFragment();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#generateUseOutput()
	 */
	@Override
	public String generateUseOutput()
	{
		return name;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Identifier clone(Node temporaryParent, Location locationIn)
	{
		Identifier node = new Identifier(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given Identifier with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Identifier cloneTo(Identifier node)
	{
		super.cloneTo(node);
		
		node.name = name;
		
		return node;
	}
}
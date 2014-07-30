package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.tree.variables.Array;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;


/**
 * Value extension that represents an Identifier. For the rules on
 * what can and cannot be an Identifier, refer to
 * {@link net.fathomsoft.nova.tree.Identifier#setName(java.lang.String) setName}
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:00:19 PM
 * @version	v0.2.20 Jul 29, 2014 at 7:26:50 PM
 */
public abstract class Identifier extends Value
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Identifier(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public boolean isInstance()
	{
		return getProgram().getClassDeclaration(getName()) == null;
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
		setName(name, false);
	}
	
	/**
	 * Get whether or not the Identifier is a declaration.
	 * 
	 * @return Whether or not the Identifier is a declaration.
	 */
	public boolean isDeclaration()
	{
		return false;
	}

	public Identifier getNextAccessingOfType(Class<?> type)
	{
		Identifier current = getAccessingNode();
		
		while (current != null && !type.isAssignableFrom(current.getClass()))
		{
			current = current.getAccessingNode();
		}
		
		return current;
	}

	public Identifier getLastAccessingOfType(Class<?> type, boolean opposite)
	{
		return getLastAccessingOfType(new Class<?>[] { type }, opposite);
	}

	public Identifier getLastAccessingOfType(Class<?> types[], boolean opposite)
	{
		Identifier previous = this;
		Identifier current  = getAccessingNode();
		
		while (current != null && checkTypes(types, current.getClass()) != opposite)
		{
			previous = current;
			current  = current.getAccessingNode();
		}
		
		return previous;
	}
	
	private boolean checkTypes(Class<?> types[], Class<?> clazz)
	{
		for (Class<?> type : types)
		{
			if (type.isAssignableFrom(clazz))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public Identifier getNextAccessedOfType(Class<?> type)
	{
		Identifier current = getAccessedNode();
		
		while (current != null && !type.isAssignableFrom(current.getClass()))
		{
			current = current.getAccessedNode();
		}
		
		return current;
	}
	
	public Node getBaseNode()
	{
		Node prev    = this;
		Node current = getParent();
		
		while (!current.containsScope() && !(current instanceof Scope))
		{
			prev    = current;
			current = current.getParent();
		}
		
		return prev;
	}
	
	public Identifier getRootReferenceNode()
	{
		return getRootReferenceNode(false);
	}
	
	public Identifier getRootReferenceNode(boolean inclusive)
	{
		if (inclusive && !isAccessed())
		{
			return this;
		}
		
		return getReferenceNode().getLastAccessingOfType(new Class<?>[] { Closure.class, MethodCall.class }, true);
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
	public Identifier getReferenceNode()
	{
		Identifier accessing = getAccessingNode();
		
		if (accessing != null)
		{
			return accessing;
		}
		
		MethodDeclaration methodDeclaration = getParentMethod();
		
		if (methodDeclaration != null)
		{
			Identifier id = getObjectReferenceNode(methodDeclaration);
			
			if (id != null)
			{
				return id;
			}
		}
		
		return getParentClass();
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
	 * @param escape The Identifier if it the specified Identifier
	 * 		is not accessed through any Identifiers.
	 * @return The furthest node that accesses the specified identifier.
	 */
	public Identifier getContextNode(Identifier escape)
	{
		Identifier node = this;
		
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
	public Identifier getRootAccessNode()
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
	public Identifier getRootNode()
	{
		Identifier node = getRootAccessNode();
		
		if (node.getParent() instanceof Instantiation)
		{
			return (Identifier)node.getParent();
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
	 * Get whether or not the specified Identifier accesses another Node.
	 * For more information see {@link #getAccessedNode()}
	 * 
	 * @return The next node that is accessed by the specified node.
	 */
	public boolean doesAccess()
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
	public Identifier getAccessedNode()
	{
		if (getNumVisibleChildren() <= 0)
		{
			return null;
		}
		
		return (Identifier)getVisibleChild(0);
	}
	
	public void setAccessedNode(Identifier node)
	{
		if (doesAccess())
		{
			replace(getAccessedNode(), node);
		}
		else
		{
			addChild(node);
		}
	}
	
	/**
	 * Get whether or not the specified Node is both used within a
	 * static context and not accessed by an instance.
	 * 
	 * @return Whether or not the specified Node is both used within a
	 * 		static context and not accessed by an instance.
	 */
	public boolean isAccessedWithinStaticContext()
	{
		return isWithinStaticContext() && !isAccessed() && (!isInstance() || getParentClass().containsMethod(getName()));
	}
	
	/**
	 * Get whether this specified identifier node was accessed through
	 * the dot operator of another Identifier.
	 * 
	 * @return Whether or not the identifier was accessed.
	 */
	public boolean isAccessed()
	{
		return getAccessingNode() != null;
	}
	
	/**
	 * Get the Identifier Node that accesses the specified Identifier
	 * Node. If the Identifier is not accessed through a Node, then
	 * null is returned.
	 * 
	 * @return The Identifier Node that accesses the specified Identifier
	 * 		Node.
	 */
	public Identifier getAccessingNode()
	{
		if (getParent() instanceof Identifier && !getParent().containsScope())
		{
			Identifier id = (Identifier)getParent();
			
			if (id.isDecodingAccessedNode(this) || id.getAccessedNode() == this)
			{
				return id;
			}
		}
		
		return null;
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
	
	public boolean isDecoding()
	{
		return !getParent().containsChild(this);
	}
	
	public StringBuilder generateNovaInputUntil(Identifier stopAt)
	{
		return generateNovaInputUntil(new StringBuilder(), stopAt);
	}
	
	public StringBuilder generateNovaInputUntil(StringBuilder builder, Identifier stopAt)
	{
		if (stopAt == null)
		{
			return generateNovaInput(builder, true);
		}
		
		stopAt = stopAt.getAccessedNode();
		
		Identifier current = this;
		
		while (current != stopAt)
		{
			current.generateNovaInput(builder, false).append('.');
			
			current = current.getAccessedNode();
		}
		
		return builder.deleteCharAt(builder.length() - 1);
	}
	
	/**
	 * Generate the C output for when this value node is being used
	 * as an argument for a method call.
	 * 
	 * @return The C output for when this value node is being used
	 * 		as an argument for a method call.
	 */
	public StringBuilder generateCArgumentReference(StringBuilder builder, Identifier callingMethod)
	{
		if (this instanceof ClassDeclaration && callingMethod instanceof MethodCall)
		{
			CallableMethod declaration = ((MethodCall)callingMethod).getCallableDeclaration();
			
			if (declaration.isStatic() || declaration instanceof Constructor)
			{
				return declaration.getParameterList().getObjectReference().generateCNullOutput(builder);
			}
			else if (declaration instanceof ClosureDeclaration)
			{
				ClosureDeclaration closure = (ClosureDeclaration)declaration;
				
				return closure.generateCSourceName(builder, "ref");
			}
		}
		
		generateCUseOutput(builder);
		
		return generateChildrenCSourceFragment(builder, true, callingMethod);
	}
	
	/**
	 * Generate a String representing the accessed nodes.
	 * 
	 * @return The generated String.
	 */
	public StringBuilder generateChildrenCSourceFragment()
	{
		return generateChildrenCSourceFragment(new StringBuilder(), true);
	}
	
	/**
	 * Generate a String representing the accessed nodes.
	 * 
	 * @return The StringBuilder with the appended generation output.
	 */
	public StringBuilder generateChildrenCSourceFragment(StringBuilder builder)
	{
		return generateChildrenCSourceFragment(builder, true);
	}
	
	/**
	 * Generate a String representing the accessed nodes.
	 * 
	 * @param reference Whether or not to start the string off with
	 * 		a "->" reference operator.
	 * @return The generated String.
	 */
	public StringBuilder generateChildrenCSourceFragment(boolean reference)
	{
		return generateChildrenCSourceFragment(new StringBuilder(), reference, null);
	}
	
	/**
	 * Generate a String representing the accessed nodes.
	 * 
	 * @param reference Whether or not to start the string off with
	 * 		a "->" reference operator.
	 * @return The StringBuilder with the appended generation output.
	 */
	public StringBuilder generateChildrenCSourceFragment(StringBuilder builder, boolean reference)
	{
		return generateChildrenCSourceFragment(builder, reference, null);
	}
	
	/**
	 * Generate a String representing the accessed nodes.
	 * 
	 * @param reference Whether or not to start the string off with
	 * 		a "->" reference operator.
	 * @param stopBefore The Identifier to stop the generation before.
	 * @return The generated String.
	 */
	public StringBuilder generateChildrenCSourceFragment(boolean reference, Identifier stopBefore)
	{
		return generateChildrenCSourceFragment(new StringBuilder(), reference, stopBefore);
	}
	
	// TODO: use stopAt instead of stopBefore.
	
	/**
	 * Generate a String representing the accessed nodes.
	 * 
	 * @param reference Whether or not to start the string off with
	 * 		a "->" reference operator.
	 * @param stopBefore The Identifier to stop the generation before.
	 * @return The StringBuilder with the appended generation output.
	 */
	public StringBuilder generateChildrenCSourceFragment(StringBuilder builder, boolean reference, Identifier stopBefore)
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
	private StringBuilder generateChildCSourceFragment(boolean reference, Identifier stopBefore)
	{
		StringBuilder builder = new StringBuilder();
		
		// If generating the output for the use of an argument.
		if (stopBefore != null)
		{
			if (this == stopBefore)//instanceof MethodCall || this instanceof Instantiation)
			{
				return builder;
			}
			
			return generateCUseOutput(builder).append(generateChildrenCSourceFragment(true, stopBefore));
		}
		
		if (isSpecialFragment())
		{
			generateSpecialFragment(builder);
		}
		
		return generateCSourceFragment(builder);
	}
	
	/**
	 * If the Value accesses a method call, generate a specialized
	 * output.
	 * 
	 * @return A specialized String generation.
	 */
	public StringBuilder generateSpecialFragment(StringBuilder builder)
	{
		Identifier current = getLastAccessedNode();
		
		while (!current.isSpecial())
		{
			current = current.getAccessingNode();
		}
		
		return current.generateCSourceFragment(builder);
	}
	
	/**
	 * Get whether or not the Value accesses a method call.
	 * 
	 * @return Whether or not the Value accesses a method call.
	 */
	public boolean isSpecialFragment()
	{
		Identifier current = getLastAccessedNode();
		
		while (current != this && current != null)
		{
			if (current.isSpecial())// instanceof MethodCall || current instanceof Closure)
			{
				return true;
			}
			
			current = current.getAccessingNode();
		}
		
		return false;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		builder.append(getName());
		
		if (outputChildren && doesAccess())
		{
			builder.append('.').append(getAccessedNode().generateNovaInput());
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return generateCSourceFragment(builder).append(";\n");
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		if (isSpecialFragment())
		{
			return generateSpecialFragment(builder);
		}
		
		return generateCUseOutput(builder).append(generateChildrenCSourceFragment());
	}
	
	/**
	 * Generate the representation of when the variable is being used, in
	 * action, rather than being declared.<br>
	 * <br>
	 * For example:<br>
	 * <blockquote><pre>
	 * Person p;
	 * p.getName();</pre></blockquote>
	 * The first line shows the declaration of the Variable. The second
	 * line demonstrates a "variable use" for the "p" variable.
	 * Essentially, the "variable use" output is exactly what it says,
	 * what the variable looks like when it is being used to do something.
	 * 
	 * @return What the variable looks like when it is being used to do
	 * 		something.
	 */
	public StringBuilder generateCUseOutput(StringBuilder builder)
	{
		return generateCUseOutput(builder, false);
	}
	
	/**
	 * Generate the representation of when the variable is being used, in
	 * action, rather than being declared.<br>
	 * <br>
	 * For example:<br>
	 * <blockquote><pre>
	 * Person p;
	 * p.getName();</pre></blockquote>
	 * The first line shows the declaration of the Variable. The second
	 * line demonstrates a "variable use" for the "p" variable.
	 * Essentially, the "variable use" output is exactly what it says,
	 * what the variable looks like when it is being used to do something.
	 * 
	 * @param pointer Whether or not the variable is to be accessed by a
	 * 		pointer.
	 * @return What the variable looks like when it is being used to do
	 * 		something.
	 */
	public StringBuilder generateCUseOutput(StringBuilder builder, boolean pointer)
	{
//		if (!isSpecialFragment())
//		{
//			builder.append(generateDataTypeOutput());
//		}
		
		FieldDeclaration field = null;
		
		Node parent = getParent();
		
		if (parent instanceof Array)
		{
			VariableDeclaration node = SyntaxTree.findDeclaration(parent.getParent(), getName());
			
			if (node instanceof FieldDeclaration)
			{
				field = (FieldDeclaration)node;
			}
		}
		else if (this instanceof Variable)
		{
			VariableDeclaration decl = ((Variable)this).getDeclaration();
			
			if (decl instanceof FieldDeclaration)
			{
				field = (FieldDeclaration)decl;
			}
		}
		else if (this instanceof FieldDeclaration)
		{
			field = (FieldDeclaration)this;
		}
		
		if (field != null && !field.isExternal())
		{
			if (!field.isStatic())
			{
				Value ref = getReferenceNode();
				
				if (ref.getTypeClass().isContainingClass(this))
				{
					if (!isAccessed())
					{
						if (pointer)
						{
							builder.append('(').append('*');
						}
						
						builder.append(ParameterList.OBJECT_REFERENCE_IDENTIFIER);
						
						if (pointer)
						{
							builder.append(')');
						}
					}
					
					if (!isAccessed())//ref.isContainingClass(this))
					{
						builder.append("->");
					}
					if (field.getVisibility() == FieldDeclaration.PRIVATE)
					{
						builder.append("prv").append("->");
					}
//					else
//					{
//						builder.append("->");
//					}
				}
			}
		}
		
		return generateCSourceName(builder);
	}
	
	/**
	 * Generate a variable name that will be used to keep the variables
	 * in their own "namespace" per-say.
	 * 
	 * @return The name of the variable that will be output to the C
	 * 		source output.
	 */
	public final StringBuilder generateCSourceName()
	{
		return generateCSourceName(new StringBuilder());
	}
	
	/**
	 * Generate a variable name that will be used to keep the variables
	 * in their own "namespace" per-say.
	 * 
	 * @return The name of the variable that will be output to the C
	 * 		source output.
	 */
	public StringBuilder generateCSourceName(StringBuilder builder)
	{
		return generateCSourceName(builder, null);
	}
	
	/**
	 * Generate a variable name that will be used to keep the variables
	 * in their own "namespace" per-say.
	 * 
	 * @return The name of the variable that will be output to the C
	 * 		source output.
	 */
	public final StringBuilder generateCSourceName(String uniquePrefix)
	{
		return generateCSourceName(new StringBuilder(), uniquePrefix);
	}
	
	/**
	 * Generate a variable name that will be used to keep the variables
	 * in their own "namespace" per-say.
	 * 
	 * @return The name of the variable that will be output to the C
	 * 		source output.
	 */
	public StringBuilder generateCSourceName(StringBuilder builder, String uniquePrefix)
	{
		String name = getName();
		
		if (willForceOriginalName())
		{
			return builder.append(name);
		}
		
		VariableDeclaration existing = null;
		
		if (isDeclaration())
		{
			existing = (VariableDeclaration)this;
		}
		else
		{
			existing = SyntaxTree.findDeclaration(getParent(), name);
			
			if (existing == null)
			{
				return builder.append(0);
			}
		}
		
		String classUniquePrefix = existing.getParentClass().generateUniquePrefix();
		
		builder.append(Nova.LANGUAGE_NAME.toLowerCase()).append("_");
		
		if (existing instanceof InstanceDeclaration)
		{
			InstanceDeclaration node = (InstanceDeclaration)existing;
			
			if (node.isStatic())
			{
				if (!(node instanceof MethodDeclaration && ((MethodDeclaration)node).isInstance()))
				{
					builder.append("static_");
				}
			}
		}
		
		if (uniquePrefix != null)
		{
			builder.append(uniquePrefix).append('_');
		}
		
		if (existing instanceof LocalDeclaration)
		{
			LocalDeclaration declaration = (LocalDeclaration)existing;
			
			builder.append(declaration.getScopeID()).append('_');
		}
		else
		{
			builder.append(classUniquePrefix).append('_');
		}
		
		return builder.append(name);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#isVirtualTypeKnown()
	 */
	@Override
	public boolean isVirtualTypeKnown()
	{
		return isAccessedWithinStaticContext() || isAccessed() && !getAccessingNode().isInstance();
	}
	
	/**
	 * Get the name of the Identifier. For the rules on what can and
	 * cannot be an Identifier, refer to {@link net.fathomsoft.nova.tree.Identifier#setName(java.lang.String) setName}
	 * 
	 * @return The name of the Identifier.
	 */
	public abstract String getName();

	/**
	 * Set the name of the Variable. You specify whether or not you want
	 * the output in the C language to be the original given name,
	 * or if it will differentiate it depending on its scope. 
	 * 
	 * @param name The String to set as the new name.
	 * @param forceOriginal Whether or not the name will be output in the
	 * 		c code verbatim.
	 */
	public abstract void setName(String name, boolean forceOriginal);
	
	/**
	 * Whether or not the output in the C language will be the
	 * original given name, or if it will differentiate it depending on
	 * its scope. 
	 * 
	 * @return Whether or not the name will be output in the C code
	 * 		verbatim.
	 */
	public abstract boolean willForceOriginalName();
	
	/**
	 * Whether or not you want the output in the C language to be the
	 * original given name, or if it will differentiate it depending on
	 * its scope. 
	 * 
	 * @param forceOriginal Whether or not the name will be output in the
	 * 		c code verbatim.
	 */
	public abstract void setForceOriginalName(boolean forceOriginal);
	
	/**
	 * Test the Identifier class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test()
	{
		
		
		return null;
	}
}
package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Location;


/**
 * Node extension that represents all of the Methods within
 * a class.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:29:22 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class MethodList extends Node
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public MethodList(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get whether or not the ClassDeclaration contains the Method with the
	 * specified name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class ClassName
	 * {
	 * 	public void doSomething()
	 * 	{
	 * 		...
	 * 	}
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>containsMethod("doSomething")</code>" would
	 * return true.
	 * 
	 * @param methodName The name of the method to search for.
	 * @return Whether or not the ClassDeclaration contains the Method with
	 * 		the specified name.
	 */
	public boolean containsMethod(String methodName)
	{
		return getMethod(methodName) != null;
	}
	
	/**
	 * Get the ClassDeclaration's Method with the specified name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class ClassName
	 * {
	 * 	public void doSomething()
	 * 	{
	 * 		...
	 * 	}
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>getMethod("doSomething")</code>" would
	 * return the Method for the "<code>doSomething</code>" method.
	 * 
	 * @param methodName The name of the method to search for.
	 * @return The Method for the method, if it exists.
	 */
	public Method getMethod(String methodName)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			Method method = (Method)getChild(i);
			
			if (method.getName().equals(methodName))
			{
				return method;
			}
		}
		
		ClassDeclaration classDeclaration = (ClassDeclaration)getAncestorOfType(ClassDeclaration.class);
		ClassDeclaration extended  = classDeclaration.getExtendedClass();
		
		if (extended != null)
		{
			Method method = extended.getMethod(methodName);
			
			if (method != null)
			{
				return method;
			}
		}
		
		ClassDeclaration implemented[] = classDeclaration.getImplementedClasses();
		
		for (ClassDeclaration clazz : implemented)
		{
			Method method = clazz.getMethod(methodName);
			
			if (method != null)
			{
				return method;
			}
		}
		
		return null;
	}
	
	/**
	 * Make sure that the Class is a valid declaration.
	 * 
	 * @param phase The phase that the node is being validated in.
	 * @see net.fathomsoft.nova.tree.Node#validate(int)
	 */
	@Override
	public Node validate(int phase)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			Method method = (Method)getChild(i);
			
			if (method != null)
			{
				method.validate(phase);
			}
		}
		
		return this;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			if (i > 0)
			{
				builder.append('\n');
			}
			
			builder.append(getChild(i).generateJavaSource());
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Method method = (Method)getChild(i);
			
			if (!method.isExternal())
			{
				builder.append(method.generateCHeader());
			}
		}
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		if (getNumChildren() > 0)
		{
			builder.append('\n');
		}
		
		boolean printed = false;
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Method method = (Method)getChild(i);
			
			if (!method.isExternal())
			{
				if (printed)
				{
					builder.append('\n');
				}
				
				builder.append(method.generateCSource());
				
				printed = true;
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * Generate a String containing all of the prototypes for each method
	 * contained within this node. A method prototype follows the
	 * following syntax: "static returnType methodName(arguments);"
	 * 
	 * @return A String containing all of the prototypes for the methods
	 * 		contained within this node.
	 */
	public String generateCSourcePrototypes()
	{
		StringBuilder builder = new StringBuilder();
			
		for (int i = 0; i < getNumChildren(); i++)
		{
			Method child = (Method)getChild(i);
			
			builder.append(child.generateCSourcePrototype()).append('\n');
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public MethodList clone(Node temporaryParent, Location locationIn)
	{
		MethodList node = new MethodList(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given MethodList with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public MethodList cloneTo(MethodList node)
	{
		super.cloneTo(node);
		
		return node;
	}
}

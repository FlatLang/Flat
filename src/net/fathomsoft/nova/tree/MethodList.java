package net.fathomsoft.nova.tree;

import java.util.ArrayList;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;


/**
 * {@link Node} extension that represents all of the Methods within
 * a class.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:29:22 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class MethodList extends List
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
		return getMethods(methodName, SearchFilter.DEFAULT).length > 0;
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
	public MethodDeclaration[] getMethods(String methodName, SearchFilter filter)
	{
		ArrayList<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			MethodDeclaration method = (MethodDeclaration)getChild(i);
			
			if (method.getName() != null && method.getName().equals(methodName) && (!filter.checkStatic || filter.staticValue == method.isStatic()))
			{
				methods.add(method);
			}
		}
		
		return methods.toArray(new MethodDeclaration[0]);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			MethodDeclaration methodDeclaration = (MethodDeclaration)getChild(i);
			
			if (!methodDeclaration.isExternal())
			{
				methodDeclaration.generateCHeader(builder);
			}
		}
		
		return builder;
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		if (getNumChildren() > 0)
		{
			builder.append('\n');
		}
		
		boolean printed = false;
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			MethodDeclaration methodDeclaration = (MethodDeclaration)getChild(i);
			
			if (!methodDeclaration.isExternal())
			{
				if (printed)
				{
					builder.append('\n');
				}
				
				methodDeclaration.generateCSource(builder);
				
				printed = true;
			}
		}
		
		return builder;
	}
	
	/**
	 * Generate a String containing all of the prototypes for each method
	 * contained within this node. A method prototype follows the
	 * following syntax: "static returnType methodName(arguments);"
	 * 
	 * @return A String containing all of the prototypes for the methods
	 * 		contained within this node.
	 */
	public StringBuilder generateCSourcePrototypes(StringBuilder builder)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			MethodDeclaration child = (MethodDeclaration)getChild(i);
			
			child.generateCSourcePrototype(builder).append('\n');
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public MethodList clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		MethodList node = new MethodList(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link MethodList} with the data that is in the
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
	
	/**
	 * Test the MethodList class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
	
	/**
	 * 
	 * 
	 * @author	Braden Steffaniak
	 * @since	v0.2.28 Aug 8, 2014 at 12:35:41 PM
	 * @version	v0.2.28 Aug 8, 2014 at 12:35:41 PM
	 */
	public static class SearchFilter
	{
		public boolean checkAncestor, checkStatic, staticValue, checkConstructors, checkProperties;
		
		public static final SearchFilter DEFAULT = new SearchFilter();
		
		public SearchFilter()
		{
			checkAncestor     = true;
			checkStatic       = false;
			checkConstructors = true;
			checkProperties   = false;
		}
		
		public void checkStatic(boolean staticValue)
		{
			this.staticValue = staticValue;
			
			checkStatic = true;
		}
	}
}
package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Location;


/**
 * TreeNode extension that represents all of the Methods within
 * a class.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:29:22 PM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class MethodListNode extends TreeNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public MethodListNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get whether or not the ClassNode contains the MethodNode with the
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
	 * @return Whether or not the ClassNode contains the MethodNode with
	 * 		the specified name.
	 */
	public boolean containsMethod(String methodName)
	{
		return getMethod(methodName) != null;
	}
	
	/**
	 * Get the ClassNode's MethodNode with the specified name.<br>
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
	 * return the MethodNode for the "<code>doSomething</code>" method.
	 * 
	 * @param methodName The name of the method to search for.
	 * @return The MethodNode for the method, if it exists.
	 */
	public MethodNode getMethod(String methodName)
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			MethodNode method = (MethodNode)getChild(i);
			
			if (method.getName().equals(methodName))
			{
				return method;
			}
		}
		
		ClassNode classNode = (ClassNode)getAncestorOfType(ClassNode.class);
		ClassNode extended  = classNode.getExtendedClass();
		
		if (extended != null)
		{
			MethodNode method = extended.getMethod(methodName);
			
			if (method != null)
			{
				return method;
			}
		}
		
		ClassNode implemented[] = classNode.getImplementedClasses();
		
		for (ClassNode clazz : implemented)
		{
			MethodNode method = clazz.getMethod(methodName);
			
			if (method != null)
			{
				return method;
			}
		}
		
		return null;
	}
	
	/**
	 * Make sure that the Class is a valid declaration.
	 */
	public void validate()
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			MethodNode method = (MethodNode)getChild(i);
			
			if (method != null)
			{
				method.validate();
			}
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
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
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateCHeader());
		}
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		if (getChildren().size() > 0)
		{
			builder.append('\n');
		}
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			if (i > 0)
			{
				builder.append('\n');
			}
			
			builder.append(getChild(i).generateCSource());
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return null;
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
			
		for (int i = 0; i < getChildren().size(); i++)
		{
			MethodNode child = (MethodNode)getChild(i);
			
			builder.append(child.generateCSourcePrototype()).append('\n');
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public MethodListNode clone(TreeNode temporaryParent, Location locationIn)
	{
		MethodListNode node = new MethodListNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given MethodListNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public MethodListNode cloneTo(MethodListNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}

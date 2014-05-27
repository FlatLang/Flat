package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.tree.exceptionhandling.ExceptionNode;
import net.fathomsoft.nova.util.Location;

/**
 * TreeNode extension that represents a list of parameters for a method.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:56:34 PM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class ParameterListNode extends TreeNode
{
	/**
	 * Identifier for the calling object of a method call.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * person.getName();</pre></blockquote>
	 * "person" is the calling object, so this translates to the
	 * following in C:
	 * <blockquote><pre>
	 * person->getName(person);</pre></blockquote>
	 * And this means that the method header in C must include a Person
	 * type as the first parameter for the getName() method. Therefore,
	 * the method header looks like this:
	 * <blockquote><pre>
	 * static String getName(Person __o__);</pre></blockquote>
	 * And "__o__" is the chosen OBJECT_REFERENCE_IDENTIFIER.
	 */
	public static final String OBJECT_REFERENCE_IDENTIFIER = "this";
	
	/**
	 * Instantiate and initialize default data. Generates the
	 * two default parameters for every method: Exception data.
	 * 
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public ParameterListNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		ParameterNode exceptionData = new ParameterNode(this, locationIn);
		exceptionData.setName(ExceptionNode.EXCEPTION_DATA_IDENTIFIER, true);
		exceptionData.setType("ExceptionData");
		
		addChild(exceptionData);
	}
	
	/**
	 * Get the ParameterNode with the given name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public Person getPerson(String name, int age)</pre></blockquote>
	 * Calling getParameter("name") on the Parameter list above would
	 * return the 'String name' ParameterNode.
	 * 
	 * @param parameterName The name of the parameter to find.
	 * @return The ParameterNode with the given name.
	 */
	public ParameterNode getParameter(String parameterName)
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			ParameterNode parameter = (ParameterNode)getChild(i);
			
			if (parameter.getName().equals(parameterName))
			{
				return parameter;
			}
		}
		
		return null;
	}
	
	/**
	 * Validate the parameters that are used for the specified parent
	 * MethodNode. Checks to make sure that if it needs an object
	 * reference as the first parameter that it gives it one.
	 */
	public void validate()
	{
		MethodNode method = (MethodNode)getAncestorOfType(MethodNode.class);
		
		if (method.isExternal())
		{
			removeChild(0);
			
			return;
		}
		else if (method.isStatic())
		{
			return;
		}
		
		ClassNode     classNode = (ClassNode)method.getAncestorOfType(ClassNode.class);
		
		ParameterNode reference = new ParameterNode(this, null);
		
		reference.setType(classNode.getName());
		
		if (method instanceof DestructorNode)
		{
			reference.setPointer(true);
		}
		
		reference.setName(MethodNode.getObjectReferenceIdentifier(), true);
		
		addChild(0, reference);
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
			builder.append(getChild(i).generateJavaSource());
			
			if (i < getChildren().size() - 1)
			{
				builder.append(", ");
			}
		}
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		return generateCSource();
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		MethodNode methodNode = (MethodNode)getAncestorOfType(MethodNode.class);
		
		StringBuilder builder = new StringBuilder();
		
//		builder.append("ExceptionData* ").append(ExceptionNode.EXCEPTION_DATA_IDENTIFIER);
		
		int start = 0;
		
		if (methodNode instanceof ConstructorNode)
		{
			start = 1;
		}
		
		for (int i = start; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append(child.generateCHeader());
			
			if (i < getChildren().size() - 1)
			{
				builder.append(", ");
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public ParameterListNode clone(TreeNode temporaryParent, Location locationIn)
	{
		ParameterListNode node = new ParameterListNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ParameterListNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ParameterListNode cloneTo(ParameterListNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}

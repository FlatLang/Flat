package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.tree.exceptionhandling.Exception;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Location;

/**
 * Node extension that represents a list of parameters for a method.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:56:34 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class ParameterList extends Node
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
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ParameterList(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		Parameter exceptionData = new Parameter(this, locationIn);
		exceptionData.setName(Exception.EXCEPTION_DATA_IDENTIFIER, true);
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
	public Parameter getParameterNode(String parameterName)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			Parameter parameter = (Parameter)getChild(i);
			
			if (parameter.getName().equals(parameterName))
			{
				return parameter;
			}
		}
		
		return null;
	}
	
	/**
	 * Get the ParameterNode that the given index represents. The
	 * parameters are ordered from left to right, 0 being the first.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public void run(int a, int b, int c)
	 * {
	 * 	...
	 * }</pre></blockquote>
	 * If you were to call getParameterNode(2) on the method
	 * above, you would receive the c ParameterNode.
	 * 
	 * @param parameterIndex The index parameter to get.
	 * @return The ParameterNode at the given index.
	 */
	public Parameter getParameterNode(int parameterIndex)
	{
		return getParameterNode(parameterIndex, getParameterOffset());
	}
	
	/**
	 * A helper method for getParameterNode(int) to access the correct
	 * parameter.
	 * 
	 * @param parameterIndex The index parameter to get.
	 * @param offset The offset in which to access the node at.
	 * @return The ParameterNode at the given index.
	 */
	private Parameter getParameterNode(int parameterIndex, int offset)
	{
		parameterIndex += offset;
		
		if (parameterIndex >= getNumChildren())
		{
			return null;
		}
		
		return (Parameter)getChild(parameterIndex);
	}
	
	/**
	 * Get the offset used to access the 'visible' data of the method.
	 * For example, external methods do not have any extra parameters
	 * such as 'this' and 'exceptionData' to deal with, so 0 is returned.
	 * However, if it is an instance method, 2 is returned because the
	 * formerly mentioned parameters are present.
	 * 
	 * @return The offset to search the parameters at.
	 */
	public int getParameterOffset()
	{
		Method method = (Method)getAncestorOfType(Method.class);
		
		if (method.isExternal())
		{
			return 0;
		}
		else if (method.isStatic())
		{
			return 1;
		}
		
		return 2;
	}
	
	/**
	 * Validate the parameters that are used for the specified parent
	 * MethodNode. Checks to make sure that if it needs an object
	 * reference as the first parameter that it gives it one.
	 * 
	 * @param phase The phase that the node is being validated in.
	 * @see net.fathomsoft.nova.tree.Node#validate(int)
	 */
	@Override
	public Node validate(int phase)
	{
		if (phase != 2)
		{
			return this;
		}
		
		Method method = (Method)getAncestorOfType(Method.class);
		
		if (method.isExternal())
		{
			// Remove the exceptionData parameter.
			removeChild(0);
		}
		else if (!method.isStatic())
		{
			ClassDeclaration     classDeclaration = (ClassDeclaration)method.getAncestorOfType(ClassDeclaration.class);
			
			Parameter reference = new Parameter(this, null);
			
			reference.setType(classDeclaration.getName());
			
			if (method instanceof Destructor)
			{
				reference.setDataType(Variable.POINTER);
			}
			
			reference.setName(Method.getObjectReferenceIdentifier(), true);
			
			// Add the object reference identifier to the beginning.
			addChild(0, reference);
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
			builder.append(getChild(i).generateJavaSource());
			
			if (i < getNumChildren() - 1)
			{
				builder.append(", ");
			}
		}
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		return generateCSource();
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		Method method = (Method)getAncestorOfType(Method.class);
		
		StringBuilder builder = new StringBuilder();
		
//		builder.append("ExceptionData* ").append(ExceptionNode.EXCEPTION_DATA_IDENTIFIER);
		
		int start = 0;
		
		if (method instanceof Constructor)
		{
			start = 1;
		}
		
		for (int i = start; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			builder.append(child.generateCHeader());
			
			if (i < getNumChildren() - 1)
			{
				builder.append(", ");
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public ParameterList clone(Node temporaryParent, Location locationIn)
	{
		ParameterList node = new ParameterList(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ParameterListNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ParameterList cloneTo(ParameterList node)
	{
		super.cloneTo(node);
		
		return node;
	}
}

package net.fathomsoft.nova.tree;

import java.util.ArrayList;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.exceptionhandling.Exception;
import net.fathomsoft.nova.util.Location;

/**
 * Node extension that represents a list of parameters for a method.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:56:34 PM
 * @version	v0.2.29 Aug 29, 2014 at 3:17:45 PM
 */
public class ParameterList<E extends Value> extends TypeList<E>
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
	 * person-&gt;getName(person);</pre></blockquote>
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
		
		if (!getMethodDeclaration().isExternal())
		{
			Parameter reference     = generateReferenceParameter();
			Parameter exceptionData = generateExceptionDataParameter(locationIn);
			
			addChild(reference);
			addChild(exceptionData);
		}
	}
	
	private Parameter generateReferenceParameter()
	{
		Parameter reference = new Parameter(this, getLocationIn());
		reference.setType(getParentClass().getName());
		reference.setName(OBJECT_REFERENCE_IDENTIFIER, true);
		
		GenericType types[] = getParentClass().getGenericParameterNames();
		
		for (GenericType type : types)
		{
			reference.addGenericParameterName(type.getDefaultType());
		}
		
		return reference;
	}
	
	private Parameter generateExceptionDataParameter(Location locationIn)
	{
		Parameter exceptionData = new Parameter(this, locationIn);
		exceptionData.setName(Exception.EXCEPTION_DATA_IDENTIFIER, true);
		exceptionData.setType("ExceptionData");
		
		return exceptionData;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 2;
	}
	
	/**
	 * Get the visible child at the given index. Visible children are
	 * those that can be seen in the .nova original source code.
	 * Examples of invisible children are the object reference and
	 * the exception data reference.
	 * 
	 * @param index The index to get the argument from, starting at 0.
	 * @return The specified parameter.
	 */
	public E getVisibleChild(int index)
	{
		return (E)getChild(index + getParameterOffset());
	}
	
	/**
	 * Get the number of parameters - the number of default
	 * parameters.
	 * 
	 * @return The number of actually visible parameters.
	 */
	public int getNumVisibleChildren()
	{
		return getNumChildren() - getParameterOffset();
	}
	
	/**
	 * Get the MethodDeclaration that this ParameterList belongs to.
	 * 
	 * @return The MethodDeclaration that this ParameterList belongs to.
	 */
	public CallableMethod getMethodDeclaration()
	{
		return (CallableMethod)getAncestorOfType(CallableMethod.class);
	}
	
	/**
	 * Get the Object reference Parameter instance, if the containing
	 * MethodDeclaration is not external.
	 * 
	 * @return The Object reference Parameter.
	 */
	public Parameter getObjectReference()
	{
		return (Parameter)getChild(0);
	}
	
	/**
	 * Get the ExceptionData Parameter that is being passed, if the
	 * containing MethodDeclaration is not external.
	 * 
	 * @return The ExceptionData Parameter.
	 */
	public Parameter getExceptionData()
	{
		return (Parameter)getChild(1);
	}
	
	/**
	 * Get the Parameter with the given name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public Person getPerson(String name, int age)</pre></blockquote>
	 * Calling getParameter("name") on the Parameter list above would
	 * return the 'String name' Parameter.
	 * 
	 * @param parameterName The name of the parameter to find.
	 * @return The Parameter with the given name.
	 */
	public E getParameter(String parameterName)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			Parameter parameter = (Parameter)getChild(i);
			
			if (parameter.getName().equals(parameterName))
			{
				return (E)parameter;
			}
		}
		
		return null;
	}
	
	/**
	 * Get the types that the parameter list contains.
	 * 
	 * @return The list of types that the parameter list requires from
	 * 		an argument list.
	 */
	public Value[] getTypes()
	{
		ArrayList<Value> types = new ArrayList<Value>();
		
		for (int i = 0; i < getNumVisibleChildren(); i++)
		{
			types.add(getParameter(i));
		}
		
		return types.toArray(new Value[0]);
	}
	
	/**
	 * Validate that the given Parameter is able to be accessed
	 * within its context.
	 * 
	 * @param parameter The Parameter to validate.
	 * @param parent The Node to use to throw an error if validation
	 * 		fails.
	 */
	public void validateAccess(Parameter parameter, Node parent)
	{
		if (parameter.getName().equals(OBJECT_REFERENCE_IDENTIFIER))
		{
			if (!getParentMethod().isInstance())
			{
				contextError(parent);
			}
		}
	}

	/**
	 * Generate and throw an error message that describes that
	 * there was an error trying to access a specific context.
	 * 
	 * @param parent The Node to use to throw the error.
	 */
	public static void contextError(Node parent)
	{
		contextError(parent, parent.getLocationIn());
	}
	
	/**
	 * Generate and throw an error message that describes that
	 * there was an error trying to access a specific context.
	 * 
	 * @param parent The Node to use to throw the error.
	 * @param location The location to specify in the error message.
	 */
	public static void contextError(Node parent, Location location)
	{
		SyntaxMessage.error("Cannot use keyword '" + ParameterList.OBJECT_REFERENCE_IDENTIFIER + "' within a static context", parent, location);
	}
	
	/**
	 * Get the Parameter that the given index represents. The
	 * parameters are ordered from left to right, 0 being the first.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public void run(int a, int b, int c)
	 * {
	 * 	...
	 * }</pre></blockquote>
	 * If you were to call getParameter(2) on the method
	 * above, you would receive the c Parameter.
	 * 
	 * @param parameterIndex The index parameter to get.
	 * @return The Parameter at the given index.
	 */
	public E getParameter(int parameterIndex)
	{
		return getParameter(parameterIndex, getParameterOffset());
	}
	
	/**
	 * A helper method for getParameter(int) to access the correct
	 * parameter.
	 * 
	 * @param parameterIndex The index parameter to get.
	 * @param offset The offset in which to access the node at.
	 * @return The Parameter at the given index.
	 */
	private E getParameter(int parameterIndex, int offset)
	{
		parameterIndex += offset;
		
		if (parameterIndex >= getNumChildren())
		{
			return null;
		}
		
		return (E)getChild(parameterIndex);
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
		CallableMethod methodDeclaration = getMethodDeclaration();
		
		if (methodDeclaration.isExternal())
		{
			return 0;
		}
		
		return 2;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		return generateParameters(builder, true);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return generateParameters(builder, false);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment(StringBuilder)
	 */
	public StringBuilder generateParameters(StringBuilder builder, boolean header)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			if (header)
			{
				getChild(i).generateCHeader(builder);
			}
			else
			{
				getChild(i).generateCSource(builder);
			}
			
			if (i < getNumChildren() - 1)
			{
				builder.append(", ");
			}
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		for (int i = 0; i < getNumVisibleChildren(); i++)
		{
			if (i > 0)
			{
				builder.append(", ");
			}
			
			getVisibleChild(i).generateNovaInput(builder);
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public ParameterList<E> clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		ParameterList<E> node = new ParameterList<E>(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link ParameterList} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ParameterList<E> cloneTo(ParameterList<E> node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the ParameterList class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
}

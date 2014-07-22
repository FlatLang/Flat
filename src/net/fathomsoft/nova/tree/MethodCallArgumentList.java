package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.tree.exceptionhandling.Exception;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Location;

/**
 * Node extension that keeps track of all of the arguments that
 * are passed during a method call. The children of this node are
 * all Argument instances. They are stored in the order that
 * they will be passed to the method call.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.14 Jun 19, 2014 at 12:14:53 PM
 * @version	v0.2.16 Jul 22, 2014 at 12:47:19 AM
 */
public class MethodCallArgumentList extends ArgumentList
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public MethodCallArgumentList(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get the MethodCall instance that contains the specified arguments.
	 * 
	 * @return The parent MethodCall instance.
	 */
	public MethodCall getMethodCall()
	{
		return (MethodCall)getParent();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return generateCSourceFragment(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		builder.append('(');
		
		generateDefaultArguments(builder);
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			if (i > 0)
			{
				builder.append(", ");
			}
			
			Value child = (Value)getChild(i);
			
			Value param = getMethodCall().getCorrespondingParameter(child);
			
			boolean sameType = child.getReturnedNode().getType().equals(param.getType());
			
			if (!sameType)
			{
				param.generateCTypeCast(builder);
			}
			
			generateCArgumentPrefix(builder, child, i);
			
			if (!sameType)
			{
				builder.append('(');
			}
			
			
			child.generateCSourceFragment(builder);
			
			if (!sameType)
			{
				builder.append(')');
			}
		}
		
		return builder.append(')');
	}
	
	/**
	 * Generate the output of the default arguments. The default arguments
	 * may include the ExceptionData instance as well as the class
	 * instance, if it is non-static.
	 * 
	 * @param builder The StringBuilder to append to.
	 * @return The appended StringBuilder instance.
	 */
	private StringBuilder generateDefaultArguments(StringBuilder builder)
	{
		if (!getMethodCall().isExternal())
		{
			checkReference(builder).append(Exception.EXCEPTION_DATA_IDENTIFIER);
			
			if (getNumChildren() > 0)
			{
				builder.append(", ");
			}
		}
		
		return builder;
	}
	
	/**
	 * Generate any data that needs to be output before the argument
	 * is generated, such as a type cast for a volatile local variable
	 * or a data type change.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @param child The Value that is being output as an argument.
	 * @param argNum The number of argument that the list is outputting.
	 * @return The StringBuilder with the appended data.
	 */
	private StringBuilder generateCArgumentPrefix(StringBuilder builder, Value child, int argNum)
	{
		Value parameter = getMethodCall().getCallableDeclaration().getParameterList().getParameter(argNum);
		
		if (child instanceof Variable)
		{
			Variable var = (Variable)child;
			
			if (var.isVolatile())
			{
				parameter.generateCTypeCast(builder);
			}
		}
		
		builder.append(parameter.generateDataTypeOutput(child.getReturnedNode().getDataType()));
		
		return builder;
	}
	
	/**
	 * If the method call needs to pass a reference of the class instance,
	 * then generate the required argument.
	 * 
	 * @param builder The StringBuilder to append to.
	 * @param call The MethodCall instance that this argument list is
	 * 		contained within.
	 * @return The appended StringBuilder instance.
	 */
	private StringBuilder checkReference(StringBuilder builder)
	{
		CallableMethod method = getMethodCall().getCallableDeclaration();
		
		if (method instanceof Constructor == false)//!method.isStatic())
		{
			if (method instanceof Destructor)
			{
				builder.append('&');
			}
			
			Identifier context  = getMethodCallContext();
			boolean    sameType = context.getReturnedNode().getType().equals(method.getParentClass().getType());
			
			if (!sameType)
			{
				method.getParentClass().generateCTypeCast(builder).append('(');
			}
			
			context.generateCArgumentReference(builder, getMethodCall());
			
			if (!sameType)
			{
				builder.append(')');
			}
			
			builder.append(", ");
		}
		
		return builder;
	}
	
	/**
	 * Get the reference variable/value that is being used to call
	 * the method.
	 * 
	 * @return The Identifier that is calling the method.
	 */
	private Identifier getMethodCallContext()
	{
		return getMethodCall().getRootReferenceNode();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public MethodCallArgumentList clone(Node temporaryParent, Location locationIn)
	{
		MethodCallArgumentList node = new MethodCallArgumentList(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ArgumentList with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public MethodCallArgumentList cloneTo(MethodCallArgumentList node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the MethodCallArgumentList class type to make sure everything
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
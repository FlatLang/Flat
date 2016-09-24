package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.exceptionhandling.Exception;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

import java.util.ArrayList;

/**
 * Node extension that keeps track of all of the arguments that
 * are passed during a method call. The children of this node are
 * all Argument instances. They are stored in the order that
 * they will be passed to the method call.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.14 Jun 19, 2014 at 12:14:53 PM
 * @version	v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public class MethodCallArgumentList extends ArgumentList
{
	private ArrayList<String> argumentNames;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public MethodCallArgumentList(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public boolean containsNamedArguments()
	{
		return argumentNames != null;
	}
	
	public boolean containsNamedArgument(String name)
	{
		return getNamedArgumentIndex(name) >= 0;
	}
	
	public int getNamedArgumentIndex(String name)
	{
		if (argumentNames != null)
		{
			for (int i = 0; i < argumentNames.size(); i++)
			{
				String arg = argumentNames.get(i);
				
				if (name.equals(arg))
				{
					return i;
				}
			}
		}
		
		return -1;
	}
	
	public int getFirstArgumentNameIndex()
	{
		for (int i = 0; i < getNumVisibleChildren(); i++)
		{
			if (getArgumentName(i) != null)
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public String getArgumentName(int index)
	{
		return argumentNames == null || argumentNames.size() <= index ? null : argumentNames.get(index);
	}
	
	public void setArgumentName(int index, String name)
	{
		argumentNames = argumentNames == null ? new ArrayList<>(getNumVisibleChildren()) : argumentNames;
		
		while (argumentNames.size() <= index)
		{
			argumentNames.add(null);
		}
		
		if (getFirstArgumentNameIndex() >= 0 && argumentNames.get(index - 1) == null)
		{
			SyntaxMessage.error("Once a named argument is used, all of the following arguments must be named as well.", this);
		}
		
		argumentNames.set(index, name);
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
		MethodCall call = getMethodCall();
		
		CallableMethod method = call.getInferredDeclaration();
		
		builder.append('(');
		
		generateDefaultArguments(builder);

		int i = 0;
		
		Value[] values = method instanceof NovaMethodDeclaration ? getArgumentsInOrder((NovaMethodDeclaration)method) : getArgumentsInOrder();
		
		while (i < values.length)
		{
			if (i > 0)
			{
				builder.append(", ");
			}
			
			Value arg = values[i];
			Value param = method.getParameterList().getParameter(i);
			
			if (arg instanceof DefaultArgument)
			{
				DefaultArgument.generateDefaultArgumentOutput(builder, param);
			}
			else
			{
				if (method.isVirtual() && !call.isVirtualTypeKnown())
				{
					VirtualMethodDeclaration virtual = ((NovaMethodDeclaration)method).getVirtualMethod();
					
					if (virtual != null)
					{
						param = virtual.getParameter(i);
					}
				}
				
				boolean sameType = SyntaxUtils.isSameType(arg.getReturnedNode(), param, false) || param.isPrimitiveType() && arg.isPrimitiveType();
				
				if (!sameType)
				{
					param.generateCTypeCast(builder).append(arg.getReturnedNode().generatePointerToValueConversion(param));
				}
				
				generateCArgumentPrefix(builder, arg, i);
				
				if (!sameType)
				{
					builder.append('(');
				}
				
				if (param.isValueReference())
				{
					builder.append('&');
				}
				
				arg.generateCArgumentOutput(builder);
				
				if (!sameType)
				{
					builder.append(')');
				}
			}
			
			i++;
		}
		
		ParameterList params = getMethodDeclaration().getParameterList();
		
		while (i < params.getNumVisibleChildren())
		{
			builder.append(", ");
			
			DefaultArgument.generateDefaultArgumentOutput(builder, params.getVisibleChild(i));
			
			i++;
		}
		
		if (getMethodCall().getCallableDeclaration() instanceof ClosureDeclaration)
		{
			builder.append(", ").append(((ClosureDeclaration)getMethodCall().getCallableDeclaration()).getContextName());
		}
		
		return builder.append(')');
	}
	
	/**
	 * Get the types that the Argument list is providing for the
	 * parameters.
	 *
	 * @return An array of Values that represent that types in the
	 * 		argument list.
	 */
	public Value[] getTypes()
	{
		ArrayList<Value> types = new ArrayList<>();

		for (int i = 0; i < getNumVisibleChildren(); i++)
		{
			types.add(getType(i));
		}

		return types.toArray(new Value[0]);
	}
	
	public Value[] getTypes(NovaMethodDeclaration method)
	{
		Value[] values = getArgumentsInOrder(method);
		
		for (int i = 0; i < values.length; i++)
		{
			values[i] = values[i].getReturnedNode();
		}
		
		return values;
	}

	public Value[] getArgumentsInOrder()
	{
		if (getFirstArgumentNameIndex() >= 0)
		{
			CallableMethod callable = getMethodCall().getInferredDeclaration();
			
			if (callable instanceof NovaMethodDeclaration)
			{
				return getArgumentsInOrder((NovaMethodDeclaration)callable);
			}
		}
		
		ArrayList<Value> types = new ArrayList<>();

		for (int i = 0; i < getNumVisibleChildren(); i++)
		{
			types.add((Value)getVisibleChild(i));
		}

		return types.toArray(new Value[0]);
	}
	
	public Value[] getArgumentsInOrder(NovaMethodDeclaration method)
	{
		if (getFirstArgumentNameIndex() < 0)
		{
			return getArgumentsInOrder();
		}
		
		ArrayList<Value> types = new ArrayList<>();
		
		for (int i = 0; i < getFirstArgumentNameIndex(); i++)
		{
			Value child = ((Value)getVisibleChild(i));

			types.add(child);
		}
		
		for (int i = getFirstArgumentNameIndex(); i < getNumVisibleChildren(); i++)
		{
			Parameter param = method.getParameterList().getParameter(i);
			
			if (containsNamedArgument(param.getName()))
			{
				Value value = ((Value)getVisibleChild(getNamedArgumentIndex(param.getName())));
				
				types.add(value);
			}
			else
			{
				types.add(new DefaultArgument(this, Location.INVALID));
				
				i--;
			}
		}

		return types.toArray(new Value[0]);
	}
	
	public Value getType(int index)
	{
		return ((Value)getVisibleChild(index)).getRealValue().getReturnedNode();
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
		Value parameter = getMethodCall().getInferredDeclaration().getParameterList().getParameter(argNum);
		
		if (child instanceof Variable)
		{
			Variable var = (Variable)child;
			
			if (var.isVolatile())
			{
				parameter.generateCTypeCast(builder);
			}
		}
		
		if (parameter.getDataType() != child.getReturnedNode().getDataType())
		{
			if (!getMethodCall().getReferenceNode().toValue().isPrimitiveGenericTypeWrapper())//parameter.getArrayDimensions() == 0 || parameter.isWithinExternalContext() || parameter.getArrayDimensions() != child.getReturnedNode().getArrayDimensions())
			{
				builder.append(parameter.generateDataTypeOutput(child.getReturnedNode().getDataType()));
			}
		}
		
		return builder;
	}
	
	/**
	 * If the method call needs to pass a reference of the class instance,
	 * then generate the required argument.
	 * 
	 * @param builder The StringBuilder to append to.
	 * @return The appended StringBuilder instance.
	 */
	private StringBuilder checkReference(StringBuilder builder)
	{
		CallableMethod method = getMethodCall().getInferredDeclaration();
		
		if (method instanceof Constructor || !getMethodCall().getDeclaration().isInstance())
		{
			builder.append(0);
		}
		else if (method instanceof ClosureDeclaration)
		{
			ClosureDeclaration closure = (ClosureDeclaration)method;
			
			closure.generateCObjectReferenceIdentifier(builder);
		}
		else
		{
			if (method instanceof Destructor)
			{
				builder.append('&');
			}
			
			Accessible context  = getMethodCallContext();
			MethodCall call     = getMethodCall();
			ClassDeclaration castClass = null;
			
			boolean sameType = SyntaxUtils.isSameType((Value)call.getReferenceNode(), method.getParentClass(), false);
			
			if (method.isVirtual() && !call.isVirtualTypeKnown())
			{
				castClass = ((NovaMethodDeclaration)method).getVirtualMethod().getParentClass();
			}
			else if (!sameType)
			{
				castClass = method.getParentClass();
			}
			
			if (castClass != null)
			{
				castClass.generateCTypeCast(builder, true, false).append('(');
			}
			
			// Chop off the method call so it does not get cloned over.
			Accessible accessible = context;
			
			if (accessible.doesAccess())
			{
				Accessible accessed = context.getAccessedNode();
				
				while (accessed != null && accessed != call)
				{
					accessible = accessible.getAccessedNode();
					accessed   = accessible.getAccessedNode();
				}
				
				accessible.setAccessedNode(call);
			}
			
			context.getCArgumentReferenceContext().generateCArgumentReference(builder, call);
			
			if (castClass != null)
			{
				builder.append(')');
			}
		}
		
		builder.append(", ");
		
		return builder;
	}
	
	/**
	 * Get the reference variable/value that is being used to call
	 * the method.
	 * 
	 * @return The Identifier that is calling the method.
	 */
	private Accessible getMethodCallContext()
	{
		return getMethodCall().getRootReferenceNode();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#validate(int)
	 */
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_PRE_GENERATION)
		{
			int numRet = 0;
			
			if (getMethodDeclaration() instanceof NovaMethodDeclaration)
			{
				NovaMethodDeclaration novaMethod = (NovaMethodDeclaration)getMethodDeclaration();
				
				numRet = novaMethod.getParameterList().getNumReturnParameters();
				
				Node base = getMethodCall().getParent();
				
				if (base instanceof Assignment)
				{
					Assignment assignment = (Assignment)base;
					
					numRet -= assignment.getNumAssignees() - 1;
					
					for (int i = 1; i < assignment.getNumAssignees(); i++)
					{
						addChild(assignment.getAssigneeNodes().getVisibleChild(i));
					}
				}
				
				for (int i = numRet; i > 0; i--)
				{
					Literal l = new Literal(this, getLocationIn().asNew());
					l.setValue(Literal.GARBAGE_IDENTIFIER);
					l.setType("void");
					l.setDataType(Value.VALUE);
					
					addChild(l);
				}
				
				if (base instanceof Assignment)
				{
					Assignment assignment = (Assignment)base;
					
					numRet = assignment.getNumAssignees();
				}
				else
				{
					numRet = 0;
				}
			}
			
			Value[] values = getArgumentsInOrder();

			MethodCall call = getMethodCall();
			CallableMethod method = call.getInferredDeclaration();

			for (int i = 0; i < values.length - numRet; i++)
			{
				Value value = values[i];
				Value param = null;
				
				if (method.isVirtual() && !call.isVirtualTypeKnown())
				{
					param = method.getRootDeclaration().getParameterList().getParameter(i);
				}
				else
				{
					param = method.getParameterList().getParameter(i);
				}
				
				if (value.getReturnedNode().isPrimitive() && !param.isPrimitiveType() && !call.getReferenceNode().toValue().isPrimitiveGenericTypeWrapper())
				{
					Instantiation newValue = SyntaxUtils.autoboxPrimitive(value);
					
					replace(value, newValue);
				}
			}
		}
		
		return result;
	}
	
	public CallableMethod getMethodDeclaration()
	{
		return (CallableMethod)getMethodCall().getMethodDeclaration();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public MethodCallArgumentList clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		MethodCallArgumentList node = new MethodCallArgumentList(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public MethodCallArgumentList cloneTo(MethodCallArgumentList node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link ArgumentList} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public MethodCallArgumentList cloneTo(MethodCallArgumentList node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the MethodCallArgumentList class type to make sure everything
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
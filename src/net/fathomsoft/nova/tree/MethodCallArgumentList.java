package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.exceptionhandling.Exception;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

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
		MethodCall call = getMethodCall();
		
		CallableMethod method = call.getInferredDeclaration();
		
		builder.append('(');
		
		generateDefaultArguments(builder);

		for (int i = 0; i < getNumVisibleChildren(); i++)
		{
			if (i > 0)
			{
				builder.append(", ");
			}
			
			Value child = (Value)getChild(i);
			Value param = call.getCorrespondingParameter(child);
			
			if (method.isVirtual() && !call.isVirtualTypeKnown())
			{
				VirtualMethodDeclaration virtual = ((NovaMethodDeclaration)method).getVirtualMethod();
				
				if (virtual != null)
				{
					param = virtual.getParameter(i);
				}
			}
			
			boolean sameType = SyntaxUtils.isSameType(child.getReturnedNode(), param, false) || param.isPrimitiveType() && child.isPrimitiveType();
			
			if (!sameType)
			{
				param.generateCTypeCast(builder).append(child.getReturnedNode().generatePointerToValueConversion(param));
			}
			
			generateCArgumentPrefix(builder, child, i);
			
			if (!sameType)
			{
				builder.append('(');
			}
			
			if (param.isValueReference())
			{
				builder.append('&');
			}
			
			child.generateCArgumentOutput(builder);
			
			if (!sameType)
			{
				builder.append(')');
			}
		}
		
		if (getMethodCall().getCallableDeclaration() instanceof ClosureDeclaration)
		{
			builder.append(", ").append(((ClosureDeclaration)getMethodCall().getCallableDeclaration()).getContextName());
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
				
				accessible.setAccessedNode(null);
				
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
			
			for (int i = 0; i < getNumVisibleChildren() - numRet; i++)
			{
				MethodCall call = getMethodCall();
				CallableMethod method = call.getInferredDeclaration();
				
				
				Value value = (Value)getVisibleChild(i);
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
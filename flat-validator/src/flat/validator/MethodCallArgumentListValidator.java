package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.lambda.LambdaMethodDeclaration;
import flat.util.Location;

import java.util.ArrayList;

public class MethodCallArgumentListValidator extends ArgumentListValidator
{
	private ArrayList<String> argumentNames;
	
	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public MethodCallArgumentListValidator(NodeValidator temporaryParent, Location locationIn)
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

	public int getLastArgumentNameIndex()
	{
		for (int i = getNumVisibleChildren() - 1; i >= 0; i--)
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
	 * Get the types that the Argument list is providing for the
	 * parameters.
	 *
	 * @return An array of Values that represent that types in the
	 * 		argument list.
	 */
	public ValueValidator[] getTypes()
	{
		ArrayList<ValueValidator> types = new ArrayList<>();

		for (int i = 0; i < getNumVisibleChildren(); i++)
		{
			types.add(getType(i));
		}

		return types.toArray(new ValueValidator[0]);
	}
	
	public ValueValidator[] getTypes(FlatMethodDeclarationValidator method)
	{
		ValueValidator[] values = getArgumentsInOrder(method);
		
		for (int i = 0; i < values.length; i++)
		{
			values[i] = values[i].getReturnedNode();
		}
		
		return values;
	}

	public ValueValidator[] getArgumentsInOrder()
	{
		if (getFirstArgumentNameIndex() >= 0)
		{
			CallableMethodValidator callable = getMethodCall().getInferredDeclaration();
			
			if (callable instanceof FlatMethodDeclarationValidator)
			{
				return getArgumentsInOrder((FlatMethodDeclarationValidator)callable);
			}
		}
		
		ArrayList<ValueValidator> types = new ArrayList<>();

		for (int i = 0; i < getNumVisibleChildren(); i++)
		{
			types.add((ValueValidator)getVisibleChild(i));
		}

		return types.toArray(new ValueValidator[0]);
	}
	
	public ValueValidator[] getArgumentsInOrder(FlatMethodDeclarationValidator method)
	{
		if (getFirstArgumentNameIndex() < 0)
		{
			return getArgumentsInOrder();
		}
		
		ArrayList<ValueValidator> types = new ArrayList<>();
		
		for (int i = 0; i < getFirstArgumentNameIndex(); i++)
		{
			ValueValidator child = ((ValueValidator)getVisibleChild(i));

			types.add(child);
		}
		
		int offset = 0;
		
		for (int i = getFirstArgumentNameIndex(); i < getNumVisibleChildren() + offset; i++)
		{
			Parameter param = method.getParameterList().getParameter(i);
			
			if (param != null)
			{
				if (containsNamedArgument(param.getName()))
				{
					ValueValidator value = ((ValueValidator)getVisibleChild(getNamedArgumentIndex(param.getName())));
					
					types.add(value);
				}
				else if (getLastArgumentNameIndex() == getNumVisibleChildren() - 1 && param.isOptional())
				{
					types.add(new DefaultArgument(this, Location.INVALID));
					
					offset++;
				}
				else
				{
					types.add(getVisibleChild(i));
				}
			}
		}
		
		return types.toArray(new ValueValidator[0]);
	}
	
	public ValueValidator getType(int index)
	{
		return ((ValueValidator)getVisibleChild(index)).getRealValue().getReturnedNode();
	}
	
	/**
	 * Get the reference variable/value that is being used to call
	 * the method.
	 * 
	 * @return The Identifier that is calling the method.
	 */
	public AccessibleValidator getMethodCallContext()
	{
		return getMethodCall().getRootReferenceNode();
	}
	
	/**
	 * @see NodeValidator#validate(int)
	 */
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase >= SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			MethodCall call = getMethodCall();
			CallableMethodValidator declaration = call.getCallableMethodBase(true);
			CallableMethodValidator inferred = call.getInferredDeclaration();
			ValueValidator[] args = getArgumentsInOrder();
			
			for (int i = 0; i < getNumVisibleChildren(); i++)
			{
				ValueValidator param;
				ValueValidator arg = args[i];

				if (getArgumentName(i) != null)
				{
					param = declaration.getParameterList().getParameter(getArgumentName(i));
				}
				else
				{
					param = declaration.getParameterList().getParameter(i);
				}

				if (param == null) {
					SyntaxMessage.error("Invalid argument", arg);
					result.errorOccurred = true;
					break;
				}
				
				ValueValidator inferredType = inferred.getParameterList().getParameter(i);
				ValueValidator context = (ValueValidator)getVisibleChild(i);
				ClassDeclarationValidator typeClass = context.getReturnedNode().getTypeClass();
				
				if (typeClass != null)
				{
					String type = inferredType.isGenericType() || "Number".equals(inferredType.getType()) || inferredType.getTypeClass() != null && inferredType.getTypeClass().isOfType(typeClass) ? context.getReturnedNode().getFlatType(context, false) : inferredType.getType();
					
					if (context instanceof Closure)
					{
						Closure c = (Closure)context;
						
						if (c.declaration instanceof LambdaMethodDeclaration && c.getType() != null && param.getType() != null)
						{
							LambdaMethodDeclaration lambda = (LambdaMethodDeclaration)c.declaration;
							
							if (c.isPrimitive())
							{
								if (!param.isPrimitive())
								{
									lambda.setDataType(param.getDataType());
								}
							}
							else if (param.isPrimitive())
							{
								lambda.setDataType(param.getDataType());
							}
						}
					}
					else if (context.getType() != null)
					{
						context.replaceWithBoxedValue(param, context.getReturnedNode().getType());
					}
				}
			}
		}
		
		if (phase == SyntaxTree.PHASE_PRE_GENERATION)
		{
			int numRet = 0;
			
			if (getMethodDeclaration() instanceof FlatMethodDeclarationValidator)
			{
				FlatMethodDeclarationValidator flatMethod = (FlatMethodDeclarationValidator)getMethodDeclaration();
				
				numRet = flatMethod.getParameterList().getNumReturnParameters();
				
				NodeValidator base = getMethodCall().getParent();
				
				if (base instanceof AssignmentValidator)
				{
					AssignmentValidator assignmentValidator = (AssignmentValidator)base;
					
					numRet -= assignmentValidator.getNumAssignees() - 1;
					
					for (int i = 1; i < assignmentValidator.getNumAssignees(); i++)
					{
						addChild(assignmentValidator.getAssigneeNodes().getVisibleChild(i));
					}
				}
				
				for (int i = numRet; i > 0; i--)
				{
					Literal l = new Literal(this, getLocationIn().asNew());
					l.setValue(Literal.GARBAGE_IDENTIFIER);
					l.setType("void");
					l.setDataType(ValueValidator.VALUE);
					
					addChild(l);
				}
				
				if (base instanceof AssignmentValidator)
				{
					AssignmentValidator assignmentValidator = (AssignmentValidator)base;
					
					numRet = assignmentValidator.getNumAssignees();
				}
				else
				{
					numRet = 0;
				}
			}
			
			ValueValidator[] values = getArgumentsInOrder();

			MethodCall call = getMethodCall();
			CallableMethodValidator method = call.getInferredDeclaration();

			for (int i = 0; i < values.length - numRet; i++)
			{
				ValueValidator value = values[i];
				ValueValidator param = null;
				
				if (method.isVirtual() && !call.isVirtualTypeKnown())
				{
					param = method.getRootDeclaration().getParameterList().getParameter(i);
				}
				else
				{
					param = method.getParameterList().getParameter(i);
				}
			}
		}
		
		return result;
	}
	
	public CallableMethodValidator getMethodDeclaration()
	{
		return (CallableMethodValidator)getMethodCall().getMethodDeclaration();
	}

	/**
	 * @see NodeValidator#generateFlatInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			if (i > 0)
			{
				builder.append(", ");
			}

			if (getArgumentName(i) != null) {
				builder.append(getArgumentName(i)).append(": ");
			}

			getChild(i).generateFlatInput(builder);
		}

		return builder;
	}
	
	/**
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public MethodCallArgumentListValidator clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		MethodCallArgumentListValidator node = new MethodCallArgumentListValidator(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
	 */
	public MethodCallArgumentListValidator cloneTo(MethodCallArgumentListValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link ArgumentListValidator} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public MethodCallArgumentListValidator cloneTo(MethodCallArgumentListValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		node.argumentNames = argumentNames;

		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
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
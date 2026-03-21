package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.exceptionhandling.Exception;
import net.fathomsoft.nova.tree.variables.Super;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.SyntaxUtils;

public abstract class MethodCallArgumentListWriter extends ArgumentListWriter
{
	public abstract MethodCallArgumentList node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return generateSourceFragment(builder);
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		MethodCall call = node().getMethodCall();
		
		CallableMethod method = call.getCallableMethodBase();
		
		builder.append('(');
		
		generateDefaultArguments(builder);
		
		int i = 0;
		
		Value[] values = method instanceof NovaMethodDeclaration ? node().getArgumentsInOrder((NovaMethodDeclaration)method) : node().getArgumentsInOrder();
		
		while (i < values.length)
		{
			if (i > 0)
			{
				builder.append(",\n");
			}
			
			Value arg = values[i];
			Value param = method.getParameterList().getParameter(i);
			
			if (arg instanceof DefaultArgument)
			{
				DefaultArgumentWriter.generateDefaultArgumentOutput(builder, param);
			}
			else
			{
				if (method.isVirtual() && !call.isVirtualTypeKnown())
				{
					if (call.getParent() instanceof Super == false)
					{
						VirtualMethodDeclaration virtual = ((NovaMethodDeclaration)method).getVirtualMethod();
						
						if (virtual != null)
						{
							param = virtual.getParameter(i);
						}
					}
				}
				
				boolean sameType = arg instanceof ClosureVariable || SyntaxUtils.isSameType(arg.getReturnedNode(), param, false) || param.isPrimitiveType() && arg.isPrimitiveType();
				
				if (param.isGenericType() && arg instanceof Closure && getWriter((Closure)arg).isPackagedAsFunction())
				{
					sameType = false;
				}
				
				if (arg instanceof Variable)
				{
					VariableDeclaration declaration = ((Variable)arg).declaration;
					
					if (declaration instanceof ClosureDeclaration)
					{
						getWriter(param).generateTypeCast(builder);
					}
				}
				
				if (!sameType)
				{
					Value ret = arg.getReturnedNode();
					
					getWriter(param).generateTypeCast(builder).append(getWriter(ret).generatePointerToValueConversion(param));
				}
				
				generateArgumentPrefix(builder, arg, i);
				
				if (!sameType)
				{
					builder.append('(');
				}
				
				if (param.isValueReference())
				{
					builder.append('&');
				}
				
				getWriter(arg).generateArgumentOutput(builder);
				
				if (param instanceof ClosureDeclaration && arg instanceof Variable && arg.isFunctionType() && arg instanceof ClosureVariable == false)
				{
					builder.append("->func");
				}
				
				if (!sameType)
				{
					builder.append(')');
				}
				
				if (param instanceof ClosureDeclaration && arg instanceof Variable)
				{
					VariableDeclaration declaration = ((Variable)arg).declaration;
					
					if (declaration instanceof ClosureVariableDeclaration)
					{
						ClosureVariableDeclaration decl = (ClosureVariableDeclaration)declaration;
						
						if (decl.getRootDeclaration() instanceof ClosureDeclaration)
						{
							builder.append(", ");
							
							getWriter(decl).generateClosureContextValues(builder, (ClosureDeclaration)decl.getRootDeclaration(), ", ", "");
						}
					}
					else if (declaration.isFunctionType())
					{
						builder.append(", ");
						
						getWriter(arg).generateArgumentOutput(builder).append("->ref, ");
						getWriter(arg).generateArgumentOutput(builder).append("->context");
					}
				}
			}
			
			i++;
		}
		
		ParameterList params = node().getMethodDeclaration().getParameterList();
		
		while (i < params.getNumVisibleChildren())
		{
			builder.append(",\n");
			
			DefaultArgumentWriter.generateDefaultArgumentOutput(builder, params.getVisibleChild(i));
			
			i++;
		}
		
		if (node().getMethodCall().getCallableDeclaration() instanceof FirstClassClosureDeclaration)
		{
			builder.append(",\n");
			
			Identifier identifier = null;
			
			if (call instanceof ChainedMethodCall)
			{
				ChainedMethodCall chained = (ChainedMethodCall)call;
				
				identifier = chained.getChainReference();
			}
			else
			{
				identifier = ((FirstClassClosureDeclaration)method).reference;
			}
			
			getWriter(identifier).generateUseOutput(builder).append("->context");
		}
		else if (node().getMethodCall().getCallableDeclaration() instanceof ClosureDeclaration)
		{
			builder.append(",\n").append(getWriter(((ClosureDeclaration)node().getMethodCall().getCallableDeclaration())).getContextName());
		}
//		if (node().getMethodCall().getCallableDeclaration() instanceof ClosureVariable)
//		{
//			builder.append(",\n");
//			
//			if (!call.isAccessed())
//			{
//				getWriter((ClosureVariable)node().getMethodCall().getCallableDeclaration()).writeInstanceAccess(builder);
//			}
//			
//			builder.append(getWriter((ClosureVariable)node().getMethodCall().getCallableDeclaration()).generateContextName());
//		}
		
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
		if (!node().getMethodCall().isExternal())
		{
			checkReference(builder);
			
			if (node().getNumChildren() > 0)
			{
				builder.append(",\n");
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
	private StringBuilder generateArgumentPrefix(StringBuilder builder, Value child, int argNum)
	{
		Value parameter = node().getMethodCall().getCallableMethodBase().getParameterList().getParameter(argNum);
		
		if (child instanceof Variable)
		{
			Variable var = (Variable)child;
			
			if (var.isVolatile())
			{
				getWriter(parameter).generateTypeCast(builder);
			}
		}
		
		if (!parameter.isGenericType() && parameter.getDataType() != child.getReturnedNode().getDataType())
		{
//			if (!node().getMethodCall().getReferenceNode().toValue().isPrimitiveGenericTypeWrapper())//parameter.getArrayDimensions() == 0 || parameter.isWithinExternalContext() || parameter.getArrayDimensions() != child.getReturnedNode().getArrayDimensions())
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
		MethodCall     call   = node().getMethodCall();
		CallableMethod method = call.getCallableMethodBase();
		
		if (method instanceof Constructor ||
			(!node().getMethodCall().getDeclaration().isInstance() &&
				call.isAccessedWithinStaticContext()))
		{
			builder.append(0);
		}
		else if (method instanceof FirstClassClosureDeclaration)
		{
			FirstClassClosureDeclaration closure = (FirstClassClosureDeclaration)method;
			
			Identifier identifier = null;
			
			if (call instanceof ChainedMethodCall)
			{
				ChainedMethodCall chained = (ChainedMethodCall)call;
				
				identifier = chained.getChainReference();
			}
			else
			{
				identifier = ((FirstClassClosureDeclaration)method).reference;
			}
			
			getWriter(identifier).generateUseOutput(builder).append("->ref");
		}
		else if (method instanceof ClosureDeclaration)
		{
			ClosureDeclaration closure = (ClosureDeclaration)method;
			
			getWriter(closure).generateObjectReferenceIdentifier(builder);
		}
//		else if (method instanceof ClosureVariable)
//		{
//			if (!call.isAccessed())
//			{
//				getWriter((ClosureVariable)method).writeInstanceAccess(builder);
//			}
//			
//			getWriter((ClosureVariable)method).generateReferenceName(builder);
//		}
		else
		{
			if (method instanceof Destructor)
			{
				builder.append('&');
			}
			
			Accessible context  = node().getMethodCallContext();
			ClassDeclaration castClass = null;
			
			ClassDeclaration referenceClass = method.getParentClass();
			
			if (method instanceof NovaMethodDeclaration)
			{
				referenceClass = ((NovaMethodDeclaration)method).getParameterList().getReferenceParameter().getTypeClass(false);
			}
			
			boolean sameType = false;//SyntaxUtils.isSameType((Value)call.getReferenceNode(), referenceClass, false);
			
			if (call.getParent() instanceof Super == false && method.isVirtual() && !call.isVirtualTypeKnown())
			{
				castClass = ((NovaMethodDeclaration)method).getVirtualMethod().getParentClass();
			}
			else if (!sameType)
			{
				castClass = referenceClass;//method.getParentClass();
			}
			
			if (castClass != null)
			{
				getWriter(castClass).generateTypeCast(builder, true, false).append('(');
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
			
			Accessible ref = context.getCArgumentReferenceContext();
			
			if (ref instanceof Variable)
			{
				getWriter((Variable)ref).generateVolatileDereference(builder);
			}
			
			getWriter(ref).generateArgumentReference(builder, call);
			
			if (castClass != null)
			{
				builder.append(')');
			}
		}
		
		return builder;
	}
}
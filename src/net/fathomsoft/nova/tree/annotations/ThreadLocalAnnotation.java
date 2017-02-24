package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.generics.GenericTypeArgument;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Location;

public class ThreadLocalAnnotation extends Annotation implements ModifierAnnotation
{
	public String aliasUsed;
	
	private static NovaMethodDeclaration constructor, accessorFunction, mutatorFunction;
	
	@Override
	public String getAliasUsed()
	{
		return aliasUsed;
	}
	
	@Override
	public void setAliasUsed(String aliasUsed)
	{
		this.aliasUsed = aliasUsed;
	}
	
	public ThreadLocalAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public static ThreadLocalAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("ThreadLocal"))
		{
			ThreadLocalAnnotation n = new ThreadLocalAnnotation(parent, location);
			
			return n;
		}
		
		return null;
	}
	
	public NovaMethodDeclaration getConstructor()
	{
		if (constructor == null)
		{
			ClassDeclaration threadLocal = getProgram().getClassDeclaration("nova/thread/ThreadLocal");
			
			constructor = (NovaMethodDeclaration)threadLocal.getConstructorList().getVisibleChild(0);
		}
		
		return constructor;
	}
	
	public NovaMethodDeclaration getAccessorFunction()
	{
		if (accessorFunction == null)
		{
			ClassDeclaration threadLocal = getProgram().getClassDeclaration("nova/thread/ThreadLocal");
			
			accessorFunction = (NovaMethodDeclaration)threadLocal.getMethod((GenericCompatible)null, "get");
		}
		
		return accessorFunction;
	}
	
	public NovaMethodDeclaration getMutatorFunction()
	{
		if (mutatorFunction == null)
		{
			ClassDeclaration threadLocal = getProgram().getClassDeclaration("nova/thread/ThreadLocal");
			
			mutatorFunction = (NovaMethodDeclaration)threadLocal.getMethod((GenericCompatible)null, "set");
		}
		
		return mutatorFunction;
	}
	
	@Override
	public void onAdded(Node parent)
	{
		ModifierAnnotation.super.onAdded(parent);
		super.onAdded(parent);
	}
	
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		else if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			FieldDeclaration field = (FieldDeclaration)parent;
			
			GenericTypeArgument arg = new GenericTypeArgument(field, Location.INVALID);
			arg.setType(field);
			
			field.getGenericTypeArgumentList().addChild(arg);
			
			field.setTypeValue("ThreadLocal");
			field.convertToPrimitiveType();
			
			if (field.initializationValue != null)
			{
				FieldDeclaration.InitializationContext context = field.getInitializationContext();
				
				Instantiation replacement = Instantiation.decodeStatement(context.parents[0], "new " + field.generateNovaType(field) + "(" + arg.getDefaultLiteralValue() + ")", Location.INVALID, true, false);
				
				Node parent = ((Node)field.initializationValue).parent;
				
				((MethodCall)replacement.getIdentifier()).getArgumentList().getVisibleChild(0).replaceWith((Value)field.initializationValue);
				
				parent.addChild(replacement);
				field.initializationValue = replacement;
			}
			
			for (int i = field.references.size() - 1; i >= 0; i--)
			{
				Variable var = field.references.get(i);
				
				if (var.isInTree())
				{
					MethodCall call = null;
					
					if (var.isBeingModified())
					{
						call = MethodCall.decodeStatement(var, "set(" + arg.getDefaultLiteralValue() + ")", var.getLocationIn(), true, false);

						if (var.parent.parent instanceof Assignment)
						{
							Assignment ass = (Assignment)var.parent.parent;
							
							if (ass.getAssignmentNode() == field.initializationValue)
							{
								continue;
							}
							
							call.getArgumentList().getVisibleChild(0).replaceWith(ass.getAssignmentNode());

							ass.replaceWith(var);
						}
					}
					else
					{
						call = MethodCall.decodeStatement(var, "get()", var.getLocationIn(), true, false);
					}
					
					Identifier temp = var.getAccessedNode();

					var.setAccessedNode(call);
					call.setAccessedNode(temp);
				}
			}
		}
		
		return result;
	}
	
	@Override
	public boolean onApplied(Node next, boolean throwError)
	{
		if (!checkDuplicate(next, throwError))
		{
			if (next instanceof FieldDeclaration)
			{
				next.getFileDeclaration().addImport("nova/thread/ThreadLocal");
				
				return true;
			}
			else
			{
				return invalidApplication(next, throwError);
			}
		}
		
		return super.onApplied(next, throwError);
	}
	
	@Override
	public ThreadLocalAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		ThreadLocalAnnotation node = new ThreadLocalAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public ThreadLocalAnnotation cloneTo(ThreadLocalAnnotation node)
	{
		return cloneTo(node, true, true);
	}
	
	public ThreadLocalAnnotation cloneTo(ThreadLocalAnnotation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "thread" };
	}
}
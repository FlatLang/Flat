package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;

public class SuperParameterModifier extends Annotation implements ModifierAnnotation
{
	public String aliasUsed;
	
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
	
	public SuperParameterModifier(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
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
		
		if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			if (parent instanceof Parameter)
			{
				Parameter param = (Parameter)parent;
				
				NovaMethodDeclaration method = parent.getParentMethod();
				method = method instanceof InitializationMethod ? ((InitializationMethod)method).constructor : method;
				
				if (!method.doesOverride())
				{
					SyntaxMessage.error("Method '" + method.getName() + "' does not override a method, which is required for the super modifier", this);
				}
				else
				{
					NovaMethodDeclaration overridden = method.getOverriddenMethod();
					
					Parameter corresponding = overridden.getParameter(param.getIndex());
					
					if (param.defaultValueString == null)
					{
						param.defaultValueString = corresponding.defaultValueString;
					}
					
					if (corresponding.getAnnotations() != null)
					{
						corresponding.getAnnotations().stream()
							.filter(a -> param.getAnnotations().stream().noneMatch(x -> x.getClass().isAssignableFrom(a.getClass())))
							.forEach(a -> param.addAnnotation((Annotation)a.clone(param, param.getLocationIn())));
					}
				}
			}
			else
			{
				invalidApplication(this, true);
			}
		}
		
		return result;
	}
	
	@Override
	public boolean onApplied(Node next, boolean throwError)
	{
		if (!checkDuplicate(next, throwError))
		{
			if (next instanceof Parameter)
			{
				// valid
			}
			else
			{
				return invalidApplication(next, throwError);
			}
		}
		
		return super.onApplied(next, throwError);
	}
	
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren, boolean generateArray)
	{
		return builder.append(aliasUsed);
	}
	
	@Override
	public SuperParameterModifier clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		SuperParameterModifier node = new SuperParameterModifier(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public SuperParameterModifier cloneTo(SuperParameterModifier node)
	{
		return cloneTo(node, true, true);
	}
	
	public SuperParameterModifier cloneTo(SuperParameterModifier node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.aliasUsed = aliasUsed;
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "super" };
	}
}
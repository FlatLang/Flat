package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.util.Location;

import java.util.Arrays;
import java.util.Optional;

public class ImmutableAnnotation extends Annotation implements ModifierAnnotation
{
	public ClassDeclaration mutableClass;
	
	public ImmutableAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public static ImmutableAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("Immutable"))
		{
			ImmutableAnnotation n = new ImmutableAnnotation(parent, location);
			
			n.parseParameters(parameters);
			
			Object classLocation = n.parameters.get("classLocation");
			
			if (classLocation != null)
			{
				if (classLocation instanceof Literal)
				{
					String value = ((Literal)classLocation).value;
					
					n.parameters.put("classLocation", value.substring(1, value.length() - 1).trim());
				}
				else
				{
					n.invalidArgument("classLocation", true);
				}
			}
			
			return n;
		}
		
		return null;
	}
	
	@Override
	public String[] defaultParameterNames()
	{
		return new String[] { "classLocation" };
	}
	
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		return result;
	}
	
	@Override
	public boolean onNextStatementDecoded(Node next)
	{
		if (next instanceof ClassDeclaration)
		{
			
		}
		else
		{
			if (next instanceof InstanceDeclaration)
			{
				InstanceDeclaration declaration = ((InstanceDeclaration)next);
				
				ClassDeclaration mutableClass = declaration.getTypeClass();
				ClassDeclaration immutableClass = null;
				
				if (!mutableClass.isImmutable())
				{
					immutableClass = getImmutableClass();
					this.mutableClass = mutableClass;
					
					if (immutableClass != null)
					{
						declaration.setType(immutableClass.getName());
					}
					else
					{
						SyntaxMessage.error("No immutable type is specified for type '" + declaration.getType() + "'", this);
					}
				}
			}
		}
		
		return super.onNextStatementDecoded(next);
	}
	
	public ClassDeclaration getImmutableClass()
	{
		if (getParent() instanceof InstanceDeclaration)
		{
			InstanceDeclaration declaration = ((InstanceDeclaration)getParent());
			
			ClassDeclaration mutableClass = declaration.getTypeClass();
			
			ImmutableAnnotation reference = (ImmutableAnnotation)mutableClass.getAnnotationOfType(ImmutableAnnotation.class, false);
			
			if (reference != null)
			{
				return mutableClass.getFileDeclaration().getImportedClass(mutableClass, (String)reference.parameters.get("classLocation"));
			}
		}
		
		return null;
	}
	
	public void convertAssignment(Value assignment)
	{
		if (mutableClass != null)
		{
			MethodDeclaration[] methods = mutableClass.getMethods("toImmutable");
			
			Optional<MethodDeclaration> method = Arrays.stream(methods).filter(x -> {
				for (Parameter p : x.getParameterList())
				{
					if (!p.isOptional())
					{
						return false;
					}
				}
				
				return true;
			}).findFirst();
			
			if (method.isPresent())
			{
				NovaMethodDeclaration toImmutable = (NovaMethodDeclaration)method.get();
				
				MethodCall call = MethodCall.decodeStatement(assignment.getReturnedNode(), "toImmutable()", assignment.getLocationIn(), true, true, toImmutable);
				
				if (call != null)
				{
					((Accessible)assignment.getReturnedNode()).setAccessedNode(call);
				}
			}
		}
	}
	
	@Override
	public boolean onApplied(Node next, boolean throwError)
	{
		if (next instanceof ClassDeclaration)
		{
			
		}
		else
		{
			
			
			if (next instanceof InstanceDeclaration)
			{
				next.toString();
			}
			else
			{
				invalidAppliedTo(next, true);
			}
		}
		
		return super.onApplied(next, throwError);
	}
	
	@Override
	public ImmutableAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		ImmutableAnnotation node = new ImmutableAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public ImmutableAnnotation cloneTo(ImmutableAnnotation node)
	{
		return cloneTo(node, true, true);
	}
	
	public ImmutableAnnotation cloneTo(ImmutableAnnotation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "immutable" };
	}
}
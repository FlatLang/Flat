package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;

import java.util.Arrays;
import java.util.Optional;

public class ImmutableAnnotation extends Annotation implements ModifierAnnotation
{
	public ClassDeclaration mutableClass;
	
	public NovaMethodDeclaration toImmutable;
	
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
			
			Object className = n.parameters.get("className");
			
			if (className != null)
			{
				if (className instanceof Literal)
				{
					String value = ((Literal)className).value;
					
					n.parameters.put("className", value.substring(1, value.length() - 1).trim());
				}
				else
				{
					n.invalidArgument("className", true);
				}
			}
			
			return n;
		}
		
		return null;
	}
	
	public boolean appliedClassIsImmutable()
	{
		return toImmutable == null;
	}
	
	@Override
	public String[] defaultParameterNames()
	{
		return new String[] { "className", "deep" };
	}
	
	@Override
	public String[][] defaultParameterTypes()
	{
		return new String[][] { { "String", "Bool" } };
	}
	
	@Override
	public boolean onNextStatementDecoded(Node next)
	{
		if (next instanceof ClassDeclaration)
		{
			
		}
		else
		{
			if (next instanceof VariableDeclaration)
			{
				VariableDeclaration declaration = ((VariableDeclaration)next);
				
				ClassDeclaration mutableClass = declaration.getTypeClass();
				ClassDeclaration immutableClass = null;
				
				if (mutableClass != null && !mutableClass.isImmutable())
				{
					immutableClass = getImmutableClass();
					this.mutableClass = mutableClass;
					
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
						toImmutable = (NovaMethodDeclaration)method.get();
					}
					else if (parameters.containsKey("className"))
					{
						SyntaxMessage.error("Immutable class '" + immutableClass.getClassLocation() + "' must contain a toImmutable function with 0 required arguments", this);
					}
					
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
		if (getParent() instanceof VariableDeclaration)
		{
			VariableDeclaration declaration = ((VariableDeclaration)getParent());
			
			ClassDeclaration mutableClass = declaration.getTypeClass();
			
			ImmutableAnnotation reference = (ImmutableAnnotation)mutableClass.getAnnotationOfType(ImmutableAnnotation.class, false);
			
			if (reference != null)
			{
				return mutableClass.getFileDeclaration().getImportedClass(mutableClass, (String)reference.parameters.get("className"));
			}
		}
		
		return null;
	}
	
	public void convertAssignment(Value assignment)
	{
		if (toImmutable != null)
		{
			MethodCall call = MethodCall.decodeStatement(assignment.getReturnedNode(), "toImmutable()", assignment.getLocationIn(), true, true, toImmutable);
			
			if (call != null)
			{
				((Accessible)assignment.getReturnedNode()).setAccessedNode(call);
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
			
			
			if (next instanceof VariableDeclaration)
			{
				
			}
			else
			{
				return invalidApplication(next, throwError);
			}
		}
		
		return super.onApplied(next, throwError);
	}
	
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			Node node = getParent();
			
			if (!parameters.containsKey("className"))
			{
				if (node instanceof ClassDeclaration)
				{
					ClassDeclaration clazz = (ClassDeclaration)node;
					
					clazz.getFieldList().getPublicFieldList().forEachVisibleChild(f -> {
						FieldDeclaration field = (FieldDeclaration)f;
						
						if (field.isTangible() && field.getVisibility() == InstanceDeclaration.PUBLIC)
						{
							SyntaxMessage.error("Immutable class '" + clazz.getClassLocation() + "' cannot have public fields", field, false);
							
							result.errorOccurred = true;
						}
						else if (!field.isStatic())
						{
							field.references.forEach(variable -> {
								if (variable.getParentMethod() instanceof Constructor == false &&
									variable.getParentMethod() instanceof AssignmentMethod == false &&
									variable.getParentMethod() instanceof MutatorMethod == false)
								{
									if (variable.isInTree() && variable.isBeingModified())
									{
										SyntaxMessage.error("Field '" + field.getName() + "' of immutable class '" + clazz.getClassLocation() + "' cannot be modified outside of a constructor", variable, false);
										
										result.errorOccurred = true;
									}
								}
							});
						}
					});
				}
			}
		}
		
		return result;
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
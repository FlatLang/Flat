package flat.validator.annotations;

import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.variables.FieldDeclaration;
import flat.util.Location;

public class ImpureFunctionAnnotationValidator extends AnnotationValidator
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
	
	public ImpureFunctionAnnotationValidator(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public static ImpureFunctionAnnotationValidator decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("Impure"))
		{
			ImpureFunctionAnnotationValidator n = new ImpureFunctionAnnotationValidator(parent, location);
			
			return n;
		}
		
		return null;
	}
	
	@Override
	public void onAdded(Node parent)
	{
		ModifierAnnotation.super.onAdded(parent);
		super.onAdded(parent);
	}
	
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			if (getParent() instanceof FieldDeclaration)
			{
				FieldDeclaration field = (FieldDeclaration)getParent();
				
				if (field.getAccessorMethod() == null && field.getMutatorMethod() == null)
				{
					SyntaxMessage.error("Field '" + field.getDeclaringClass().getName() + "." + field.getName() + "' which does not contain an accessor or mutator function cannot contain impure annotation", field, false);
					
					result.errorOccurred = true;
					
					return result;
				}
			}
		}
		
		return result;
	}
	
	@Override
	public boolean onApplied(Node next, boolean throwError)
	{
		if (next instanceof MethodDeclaration ||
			next instanceof ArrayBracketOverload ||
			next instanceof FieldDeclaration)
		{
			// valid
		}
		else
		{
			return invalidApplication(next, throwError);
		}
		
		return super.onApplied(next, throwError);
	}
	
	@Override
	public ImpureFunctionAnnotationValidator clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		ImpureFunctionAnnotationValidator node = new ImpureFunctionAnnotationValidator(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public ImpureFunctionAnnotationValidator cloneTo(ImpureFunctionAnnotationValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	public ImpureFunctionAnnotationValidator cloneTo(ImpureFunctionAnnotationValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.aliasUsed = aliasUsed;
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "impure" };
	}
}
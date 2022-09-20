package flat.validator.annotations;

import flat.ValidationResult;
import flat.error.SyntaxMessage;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;

public class FinalAnnotationValidator extends AnnotationValidator
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
	
	public FinalAnnotationValidator(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public static FinalAnnotationValidator decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("Final"))
		{
			FinalAnnotationValidator n = new FinalAnnotationValidator(parent, location);
			
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
	
	@Override
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren, boolean generateArray)
	{
		return builder.append("let");
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
			VariableDeclaration declaration = (VariableDeclaration)getParent();
			
			declaration.references.forEach(variable ->
			{
				if (variable.getParentMethod() instanceof Constructor == false &&
					variable.getParentMethod() instanceof AssignmentMethod == false &&
					variable.getParentMethod() instanceof MutatorMethod == false &&
					variable.getAncestorOfType(StaticBlock.class) == null)
				{
					if (variable.isUserMade() && variable.isInTree() && variable.isBeingModified() && !variable.declaration.isPropertyTrue("elvis"))
					{
						variable.isUserMade();
						variable.isBeingModified();
						variable.declaration.isPropertyTrue("elvis");
						SyntaxMessage.error("Final variable '" + declaration.getName() + "' cannot be modified in this context", variable, false);
						
						result.errorOccurred = true;
					}
				}
			});
		}
		
		return result;
	}
	
	@Override
	public boolean onApplied(Node next, boolean throwError)
	{
		if (!checkDuplicate(next, throwError))
		{
			if (next instanceof VariableDeclaration)
			{
//			((VariableDeclaration)next).isFinal = true;
			}
			else
			{
				return invalidApplication(next, throwError);
			}
		}
		
		return super.onApplied(next, throwError);
	}
	
	@Override
	public FinalAnnotationValidator clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		FinalAnnotationValidator node = new FinalAnnotationValidator(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public FinalAnnotationValidator cloneTo(FinalAnnotationValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	public FinalAnnotationValidator cloneTo(FinalAnnotationValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.aliasUsed = aliasUsed;
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "let", "final" };
	}
}
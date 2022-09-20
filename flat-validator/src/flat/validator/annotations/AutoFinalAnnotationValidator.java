package flat.validator.annotations;

import flat.ValidationResult;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;
import flat.validator.ValidationResult;

public class AutoFinalAnnotationValidator extends AnnotationValidator
{
	public ValidationResult validate(AutoFinalAnnotation node, int phase)
	{
		ValidationResult result = super.validate(node, phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		Node[] nodes = node.getParent().getChildrenOfType(VariableDeclaration.class);
		
		for (Node n : nodes)
		{
			VariableDeclaration declaration = (VariableDeclaration)n;
			
			if (declaration instanceof FlatMethodDeclaration == false && !declaration.isVar() && declaration.isUserMade() && !declaration.isFinal())
			{
				if (!declaration.isPropertyTrue("forLoopVariable"))
				{
					declaration.isUserMade();
					declaration.isFinal();
					declaration.addAnnotation(new FinalAnnotationValidator(declaration, declaration.getLocationIn()));
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
			if (next instanceof ClassDeclaration || next.containsScope())
			{
//			((InstanceDeclaration)next).setStatic(true);
			}
			else
			{
				return invalidApplication(next, throwError);
			}
		}
		
		return super.onApplied(next, throwError);
	}
	
	@Override
	public AutoFinalAnnotationValidator clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		AutoFinalAnnotationValidator node = new AutoFinalAnnotationValidator(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public AutoFinalAnnotationValidator cloneTo(AutoFinalAnnotationValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	public AutoFinalAnnotationValidator cloneTo(AutoFinalAnnotationValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
}
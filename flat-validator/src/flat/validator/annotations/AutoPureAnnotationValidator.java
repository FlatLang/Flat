package flat.validator.annotations;

import flat.ValidationResult;
import flat.tree.ClassDeclaration;
import flat.tree.FlatMethodDeclaration;
import flat.tree.Node;
import flat.util.Location;

public class AutoPureAnnotationValidator extends AnnotationValidator
{
	public AutoPureAnnotationValidator(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public static AutoPureAnnotationValidator decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("AutoPure"))
		{
			AutoPureAnnotationValidator n = new AutoPureAnnotationValidator(parent, location);
			
			return n;
		}
		
		return null;
	}
	
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		Node[] nodes = getParent().getChildrenOfType(FlatMethodDeclaration.class);
		
		for (Node n : nodes)
		{
			FlatMethodDeclaration declaration = (FlatMethodDeclaration)n;
			
			if (declaration.isUserMade() && declaration.getPureAnnotation() == null && declaration.getImpureAnnotation() == null)
			{
				declaration.isUserMade();
				declaration.isPure();
				declaration.addAnnotation(new PureFunctionAnnotationValidator(declaration, declaration.getLocationIn()));
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
	public AutoPureAnnotationValidator clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		AutoPureAnnotationValidator node = new AutoPureAnnotationValidator(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public AutoPureAnnotationValidator cloneTo(AutoPureAnnotationValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	public AutoPureAnnotationValidator cloneTo(AutoPureAnnotationValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
}
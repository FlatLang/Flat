package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.ClassDeclaration;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.NovaMethodDeclaration;
import net.fathomsoft.nova.util.Location;

public class AutoPureAnnotation extends Annotation
{
	public AutoPureAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public static AutoPureAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("AutoPure"))
		{
			AutoPureAnnotation n = new AutoPureAnnotation(parent, location);
			
			return n;
		}
		
		return null;
	}
	
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		Node[] nodes = getParent().getChildrenOfType(NovaMethodDeclaration.class);
		
		for (Node n : nodes)
		{
			NovaMethodDeclaration declaration = (NovaMethodDeclaration)n;
			
			if (declaration.isUserMade() && declaration.getPureAnnotation() == null && declaration.getImpureAnnotation() == null)
			{
				declaration.isUserMade();
				declaration.isPure();
				declaration.addAnnotation(new PureFunctionAnnotation(declaration, declaration.getLocationIn()));
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
	public AutoPureAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		AutoPureAnnotation node = new AutoPureAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public AutoPureAnnotation cloneTo(AutoPureAnnotation node)
	{
		return cloneTo(node, true, true);
	}
	
	public AutoPureAnnotation cloneTo(AutoPureAnnotation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
}
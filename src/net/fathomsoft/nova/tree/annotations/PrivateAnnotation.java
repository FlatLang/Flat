package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.InstanceDeclaration;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

public class PrivateAnnotation extends ApplicableAnnotationBase implements ModifierAnnotation
{
	public PrivateAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public static PrivateAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("Private") || name.equals("-"))
		{
			PrivateAnnotation n = new PrivateAnnotation(parent, location);
			
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
		
		return result;
	}
	
	@Override
	public boolean onApplied(Node next, boolean throwError)
	{
		if (next instanceof InstanceDeclaration)
		{
			((InstanceDeclaration)next).setVisibility(InstanceDeclaration.PUBLIC);
			
			return true;
		}
		
		return super.onApplied(next, throwError);
	}
	
	@Override
	public PrivateAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		PrivateAnnotation node = new PrivateAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public PrivateAnnotation cloneTo(PrivateAnnotation node)
	{
		return cloneTo(node, true, true);
	}
	
	public PrivateAnnotation cloneTo(PrivateAnnotation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "private" };
	}
}
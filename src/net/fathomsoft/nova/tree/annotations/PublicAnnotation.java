package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.InstanceDeclaration;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

public class PublicAnnotation extends ApplicableAnnotationBase
{
	public PublicAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public static PublicAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("Public") || name.equals("+"))
		{
			PublicAnnotation n = new PublicAnnotation(parent, location);
			
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
	public boolean onApplied(Node next)
	{
		if (next instanceof InstanceDeclaration)
		{
			((InstanceDeclaration)next).setVisibility(InstanceDeclaration.PUBLIC);
			
			return true;
		}
		
		return super.onApplied(next);
	}
	
	@Override
	public PublicAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		PublicAnnotation node = new PublicAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	public PublicAnnotation cloneTo(PublicAnnotation node)
	{
		return cloneTo(node, true);
	}
	
	public PublicAnnotation cloneTo(PublicAnnotation node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
}
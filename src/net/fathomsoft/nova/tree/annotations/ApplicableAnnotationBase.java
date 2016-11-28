package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

public class ApplicableAnnotationBase extends Annotation
{
	public ApplicableAnnotationBase(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
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
		if (next instanceof Annotation == false)
		{
			invalidAppliedTo(next, true);
		}
		
		return super.onApplied(next);
	}
	
	@Override
	public ApplicableAnnotationBase clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		ApplicableAnnotationBase node = new ApplicableAnnotationBase(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	public ApplicableAnnotationBase cloneTo(ApplicableAnnotationBase node)
	{
		return cloneTo(node, true);
	}
	
	public ApplicableAnnotationBase cloneTo(ApplicableAnnotationBase node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
}
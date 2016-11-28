package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.util.Location;

public class VisibleAnnotation extends ApplicableAnnotationBase
{
	public VisibleAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public static VisibleAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("Visible") || name.equals("*"))
		{
			VisibleAnnotation n = new VisibleAnnotation(parent, location);
			
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
		if (next instanceof FieldDeclaration)
		{
			((FieldDeclaration)next).setVisibility(FieldDeclaration.VISIBLE);
			
			return true;
		}
		
		return super.onApplied(next);
	}
	
	@Override
	public VisibleAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		VisibleAnnotation node = new VisibleAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	public VisibleAnnotation cloneTo(VisibleAnnotation node)
	{
		return cloneTo(node, true);
	}
	
	public VisibleAnnotation cloneTo(VisibleAnnotation node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
}
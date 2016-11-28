package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.InstanceDeclaration;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

public class StaticAnnotation extends Annotation
{
	public StaticAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public static StaticAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("Static"))
		{
			StaticAnnotation n = new StaticAnnotation(parent, location);
			
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
			((InstanceDeclaration)next).setStatic(true);
		}
		else
		{
			invalidAppliedTo(next, true);
		}
		
		return super.onApplied(next);
	}
	
	@Override
	public StaticAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		StaticAnnotation node = new StaticAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	public StaticAnnotation cloneTo(StaticAnnotation node)
	{
		return cloneTo(node, true);
	}
	
	public StaticAnnotation cloneTo(StaticAnnotation node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
}
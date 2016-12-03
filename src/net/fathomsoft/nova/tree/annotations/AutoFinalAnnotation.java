package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.ArrayOverloadMethod;
import net.fathomsoft.nova.tree.InstanceDeclaration;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.PropertyMethod;
import net.fathomsoft.nova.util.Location;

public class StaticAnnotation extends Annotation implements ModifierAnnotation
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
	public boolean onApplied(Node next, boolean throwError)
	{
		if (next instanceof PropertyMethod || next instanceof ArrayOverloadMethod)
		{
			return true;
		}
		else if (next instanceof InstanceDeclaration)
		{
			((InstanceDeclaration)next).setStatic(true);
		}
		else
		{
			invalidAppliedTo(next, true);
		}
		
		return super.onApplied(next, throwError);
	}
	
	@Override
	public StaticAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		StaticAnnotation node = new StaticAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public StaticAnnotation cloneTo(StaticAnnotation node)
	{
		return cloneTo(node, true, true);
	}
	
	public StaticAnnotation cloneTo(StaticAnnotation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "static" };
	}
}
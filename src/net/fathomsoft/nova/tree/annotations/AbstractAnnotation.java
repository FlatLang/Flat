package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.ClassDeclaration;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.NovaMethodDeclaration;
import net.fathomsoft.nova.util.Location;

public class AbstractAnnotation extends ApplicableAnnotationBase implements ModifierAnnotation
{
	public AbstractAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public static AbstractAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("Abstract"))
		{
			AbstractAnnotation n = new AbstractAnnotation(parent, location);
			
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
		if (next instanceof ClassDeclaration)
		{
			((ClassDeclaration)next).abstractValue = true;
		}
		else if (next instanceof NovaMethodDeclaration)
		{
			// Abstract method parser will detect this annotation
		}
		else
		{
			return super.onApplied(next, throwError);
		}
		
		return true;
	}
	
	@Override
	public AbstractAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		AbstractAnnotation node = new AbstractAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	public AbstractAnnotation cloneTo(AbstractAnnotation node)
	{
		return cloneTo(node, true);
	}
	
	public AbstractAnnotation cloneTo(AbstractAnnotation node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "abstract" };
	}
}
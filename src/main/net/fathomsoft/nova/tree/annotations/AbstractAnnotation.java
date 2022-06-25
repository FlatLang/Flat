package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.ClassDeclaration;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.NovaMethodDeclaration;
import net.fathomsoft.nova.util.Location;

public class AbstractAnnotation extends ApplicableAnnotationBase implements ModifierAnnotation
{
	public String aliasUsed;
	
	@Override
	public String getAliasUsed()
	{
		return aliasUsed;
	}
	
	@Override
	public void setAliasUsed(String aliasUsed)
	{
		this.aliasUsed = aliasUsed;
	}
	
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
	public void onAdded(Node parent)
	{
		ModifierAnnotation.super.onAdded(parent);
		super.onAdded(parent);
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
		if (!checkDuplicate(next, throwError))
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
		}
		
		return true;
	}
	
	@Override
	public AbstractAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		AbstractAnnotation node = new AbstractAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public AbstractAnnotation cloneTo(AbstractAnnotation node)
	{
		return cloneTo(node, true, true);
	}
	
	public AbstractAnnotation cloneTo(AbstractAnnotation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "abstract" };
	}
}
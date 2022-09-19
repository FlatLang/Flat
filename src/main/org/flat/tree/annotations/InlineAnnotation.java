package org.flat.tree.annotations;

import org.flat.ValidationResult;
import org.flat.error.SyntaxMessage;
import org.flat.tree.FlatMethodDeclaration;
import org.flat.tree.Node;
import org.flat.tree.Value;
import org.flat.util.Location;

public class InlineAnnotation extends Annotation implements ModifierAnnotation
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

	public InlineAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public static InlineAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("Inline"))
		{
			InlineAnnotation n = new InlineAnnotation(parent, location);
			
			n.parseParameters(parameters);
			
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
//			if (next instanceof FlatMethodDeclaration)
//			{
//				// valid
//			}
//			else
			{
				return invalidApplication(next, throwError);
			}
		}
		
		return super.onApplied(next, throwError);
	}
	
	@Override
	public InlineAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		InlineAnnotation node = new InlineAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public InlineAnnotation cloneTo(InlineAnnotation node)
	{
		return cloneTo(node, true, true);
	}
	
	public InlineAnnotation cloneTo(InlineAnnotation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.aliasUsed = aliasUsed;
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "inline" };
	}
}
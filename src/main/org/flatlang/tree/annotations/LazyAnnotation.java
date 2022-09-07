package org.flatlang.tree.annotations;

import org.flatlang.ValidationResult;
import org.flatlang.tree.FlatMethodDeclaration;
import org.flatlang.tree.Node;
import org.flatlang.tree.SyntaxTree;
import org.flatlang.tree.lambda.LambdaExpression;
import org.flatlang.tree.variables.FieldDeclaration;
import org.flatlang.util.Location;

public class LazyAnnotation extends Annotation implements ModifierAnnotation
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

	public LazyAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public static LazyAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("Lazy"))
		{
			LazyAnnotation n = new LazyAnnotation(parent, location);
			
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

		if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{

		}
		
		return result;
	}
	
	@Override
	public boolean onApplied(Node next, boolean throwError)
	{
		if (!checkDuplicate(next, throwError))
		{
			if (next instanceof FieldDeclaration)
			{
				// valid
			}
			else
			{
				return invalidApplication(next, throwError);
			}
		}
		
		return super.onApplied(next, throwError);
	}
	
	@Override
	public LazyAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		LazyAnnotation node = new LazyAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public LazyAnnotation cloneTo(LazyAnnotation node)
	{
		return cloneTo(node, true, true);
	}
	
	public LazyAnnotation cloneTo(LazyAnnotation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.aliasUsed = aliasUsed;
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "lazy" };
	}
}

package org.flatlang.tree.annotations;

import org.flatlang.ValidationResult;
import org.flatlang.tree.Node;
import org.flatlang.tree.FlatMethodDeclaration;
import org.flatlang.tree.Parameter;
import org.flatlang.tree.SyntaxTree;
import org.flatlang.tree.lambda.LambdaExpression;
import org.flatlang.util.Location;

public class AsyncAnnotation extends Annotation implements ModifierAnnotation
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

	public AsyncAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public static AsyncAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("Async"))
		{
			AsyncAnnotation n = new AsyncAnnotation(parent, location);
			
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
			if (next instanceof FlatMethodDeclaration || next instanceof LambdaExpression || next instanceof Parameter)
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
	public AsyncAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		AsyncAnnotation node = new AsyncAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public AsyncAnnotation cloneTo(AsyncAnnotation node)
	{
		return cloneTo(node, true, true);
	}
	
	public AsyncAnnotation cloneTo(AsyncAnnotation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.aliasUsed = aliasUsed;
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "async" };
	}
}
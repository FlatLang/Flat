package org.flatlang.tree.annotations;

import org.flatlang.ValidationResult;
import org.flatlang.error.SyntaxMessage;
import org.flatlang.tree.FlatMethodDeclaration;
import org.flatlang.tree.Node;
import org.flatlang.tree.SyntaxTree;
import org.flatlang.tree.Value;
import org.flatlang.util.Location;

public class AwaitAnnotation extends Annotation implements ModifierAnnotation
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

	public AwaitAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public static AwaitAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("Await"))
		{
			AwaitAnnotation n = new AwaitAnnotation(parent, location);
			
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
			if (next instanceof Value)
			{
				FlatMethodDeclaration method = next.getParentMethod(true);

				if (method == null || !method.isAsync()) {
					SyntaxMessage.error("await must be used within an async function", next);
					return false;
				}
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
	public AwaitAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		AwaitAnnotation node = new AwaitAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public AwaitAnnotation cloneTo(AwaitAnnotation node)
	{
		return cloneTo(node, true, true);
	}
	
	public AwaitAnnotation cloneTo(AwaitAnnotation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.aliasUsed = aliasUsed;
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "await" };
	}
}
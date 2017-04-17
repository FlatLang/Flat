package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.NovaMethodDeclaration;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.util.Location;

public class ExpectSyntaxErrorAnnotation extends Annotation
{
	public ExpectSyntaxErrorAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public static ExpectSyntaxErrorAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("ExpectSyntaxError"))
		{
			ExpectSyntaxErrorAnnotation n = new ExpectSyntaxErrorAnnotation(parent, location);
			
			return n;
		}
		
		return null;
	}
	
	@Override
	public String[] defaultParameterNames()
	{
		return new String[] { "type" };
	}
	
	@Override
	public String[][] defaultParameterTypes()
	{
		return new String[][] { { "identifier" } };
	}
	
	@Override
	public void onAdded(Node parent)
	{
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
			if (next instanceof NovaMethodDeclaration)
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
	public ExpectSyntaxErrorAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		ExpectSyntaxErrorAnnotation node = new ExpectSyntaxErrorAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public ExpectSyntaxErrorAnnotation cloneTo(ExpectSyntaxErrorAnnotation node)
	{
		return cloneTo(node, true, true);
	}
	
	public ExpectSyntaxErrorAnnotation cloneTo(ExpectSyntaxErrorAnnotation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
}
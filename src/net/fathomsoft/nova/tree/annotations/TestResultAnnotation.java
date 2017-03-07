package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.NovaMethodDeclaration;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.util.Location;

public class TestResultAnnotation extends Annotation implements ModifierAnnotation
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
	
	public TestResultAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public static TestResultAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("TestResult"))
		{
			TestResultAnnotation n = new TestResultAnnotation(parent, location);
			
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
		
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
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
	public TestResultAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		TestResultAnnotation node = new TestResultAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public TestResultAnnotation cloneTo(TestResultAnnotation node)
	{
		return cloneTo(node, true, true);
	}
	
	public TestResultAnnotation cloneTo(TestResultAnnotation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "test_result" };
	}
}
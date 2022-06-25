package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;

public class NamedArgumentModifier extends Annotation implements ModifierAnnotation
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
	
	public NamedArgumentModifier(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
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
		
		if (parent instanceof Parameter)
		{
			Parameter param = (Parameter)parent;
			
			param.requireNamed = true;
		}
		else
		{
			invalidApplication(this, true);
		}
		
		return result;
	}
	
	@Override
	public boolean onApplied(Node next, boolean throwError)
	{
		if (!checkDuplicate(next, throwError))
		{
			if (next instanceof VariableDeclaration)
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
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren, boolean generateArray)
	{
		return builder.append(aliasUsed);
	}
	
	@Override
	public NamedArgumentModifier clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		NamedArgumentModifier node = new NamedArgumentModifier(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public NamedArgumentModifier cloneTo(NamedArgumentModifier node)
	{
		return cloneTo(node, true, true);
	}
	
	public NamedArgumentModifier cloneTo(NamedArgumentModifier node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.aliasUsed = aliasUsed;
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "named" };
	}
}
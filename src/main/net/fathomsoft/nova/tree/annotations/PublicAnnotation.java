package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.InstanceDeclaration;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.Parameter;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.util.Location;

public class PublicAnnotation extends ApplicableAnnotationBase implements ModifierAnnotation, VisibilityModifier
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
	
	public PublicAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public static PublicAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("Public") || name.equals("+"))
		{
			PublicAnnotation n = new PublicAnnotation(parent, location);
			
			return n;
		}
		
		return null;
	}
	
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren, boolean generateArray)
	{
		return builder.append("public");
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
			if (!addAssignment())
			{
				result.errorOccurred = true;
			}
		}
		
		return result;
	}
	
	@Override
	public void onAdded(Node parent)
	{
		ModifierAnnotation.super.onAdded(parent);
		super.onAdded(parent);
	}
	
	@Override
	public boolean onNextStatementDecoded(Node next)
	{
		if (next instanceof Parameter && createFieldFromParameter((Parameter)next))
		{
			return true;
		}
		
		return super.onNextStatementDecoded(next);
	}
	
	@Override
	public boolean onApplied(Node next, boolean throwError)
	{
		if (!checkDuplicate(next, throwError))
		{
			if (checkParameter(next))
			{
				return true;
			}
			else if (next instanceof InstanceDeclaration)
			{
				((InstanceDeclaration)next).setVisibility(InstanceDeclaration.PUBLIC);
				
				return true;
			}
		}
		
		return super.onApplied(next, throwError);
	}
	
	@Override
	public PublicAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		PublicAnnotation node = new PublicAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public PublicAnnotation cloneTo(PublicAnnotation node)
	{
		return cloneTo(node, true, true);
	}
	
	public PublicAnnotation cloneTo(PublicAnnotation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.aliasUsed = aliasUsed;
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "public", "+" };
	}
}
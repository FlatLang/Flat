package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;

public class VarAnnotation extends Annotation implements ModifierAnnotation
{
	public VarAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
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
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren, boolean generateArray)
	{
		return builder.append("var");
	}
	
	@Override
	public boolean onNextStatementDecoded(Node next)
	{
//		if (next instanceof LocalDeclaration && next instanceof Parameter == false)
//		{
//			((LocalDeclaration)next).setImplicit(true);
//		}
		
		return super.onNextStatementDecoded(next);
	}
	
	@Override
	public boolean onApplied(Node next, boolean throwError)
	{
		if (next instanceof VariableDeclaration)
		{
			
		}
		else
		{
			return invalidAppliedTo(next, throwError);
		}
		
		return super.onApplied(next, throwError);
	}
	
	@Override
	public VarAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		VarAnnotation node = new VarAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public VarAnnotation cloneTo(VarAnnotation node)
	{
		return cloneTo(node, true, true);
	}
	
	public VarAnnotation cloneTo(VarAnnotation node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[] { "var" };
	}
}
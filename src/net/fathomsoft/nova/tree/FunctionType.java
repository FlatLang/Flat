package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.tree.variables.VariableDeclaration;

public class FunctionType extends Type
{
	public Type type;
	
	public FirstClassClosureDeclaration closure;
	
	public static FunctionType parse(Node parent, String value)
	{
		if (value != null && value.indexOf('(') > 0)
		{
			ClosureDeclaration c = ClosureDeclaration.decodeStatement(parent, value, parent.getLocationIn(), false);
			
			if (c != null)
			{
				FunctionType type = new FunctionType();
				
				FirstClassClosureDeclaration firstClass = new FirstClassClosureDeclaration(parent, c.getLocationIn());
				
				type.value = c.getName();
				type.type = c.getTypeObject();
				type.closure = (FirstClassClosureDeclaration)c.cloneTo(firstClass);
				
				firstClass.reference = (Value)parent;
				
				return type;
			}
		}
		
		return null;
	}
	
	@Override
	public String toNova()
	{
		return closure.generateNovaInput().toString();
	}
	
	public FunctionType cloneTo(FunctionType clone)
	{
		clone.type = type != null ? type.clone() : null;
		clone.closure = closure;
		
		return clone;
	}
	
	public FunctionType clone()
	{
		return cloneTo(new FunctionType());
	}
}
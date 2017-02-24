package net.fathomsoft.nova.tree;

public class FunctionType extends Type
{
	public Type type;
	
	ClosureDeclaration closure;
	
	public static FunctionType parse(Node parent, String value)
	{
		if (value != null && value.indexOf('(') > 0)
		{
			ClosureDeclaration c = ClosureDeclaration.decodeStatement(parent, value, parent.getLocationIn(), false);
			
			if (c != null)
			{
				FunctionType type = new FunctionType();
				
				type.value = c.getName();
				type.type = c.getTypeObject();
				type.closure = c;
				
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
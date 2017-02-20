package net.fathomsoft.nova.tree;

public class FunctionType extends Type
{
	public Type type;
	
	public FunctionType cloneTo(FunctionType clone)
	{
		clone.type = type != null ? type.clone() : null;
		
		return clone;
	}
	
	public FunctionType clone()
	{
		return cloneTo(new FunctionType());
	}
}
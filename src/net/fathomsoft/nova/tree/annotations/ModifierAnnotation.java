package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.tree.Node;

public interface ModifierAnnotation
{
	String[] getAliases();
	
	default boolean apply(Node to)
	{
		if (onAppliedAsModifier(to, false))
		{
			to.addAnnotation((Annotation)this);
			
			return true;
		}
		
		return false;
	}
	
	default boolean onAppliedAsModifier(Node toNode, boolean throwError)
	{
		return ((Annotation)this).onApplied(toNode, throwError);
	}
}
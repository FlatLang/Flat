package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.tree.Node;

public interface ModifierAnnotation
{
	String[] getAliases();
	
	default void onAdded(Node to)
	{
		apply(to);
	}
	
	default boolean apply(Node to)
	{
		if (to != ((Annotation)this).getParent())
		{
			if (onAppliedAsModifier(to, false))
			{
				to.addAnnotation((Annotation)this);
				
				return true;
			}
			else
			{
				return false;
			}
		}
		
		return true;
	}
	
	default boolean onAppliedAsModifier(Node toNode, boolean throwError)
	{
		return ((Annotation)this).onApplied(toNode, throwError);
	}
}
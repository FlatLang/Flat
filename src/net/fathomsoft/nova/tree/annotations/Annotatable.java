package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.tree.Node;

import java.util.ArrayList;

/**
 * @author	Braden Steffaniak
 */
public interface Annotatable
{
	ArrayList<Annotation> getAnnotations();
	void addAnnotation(Annotation annotation);
	
	default boolean containsAnnotationOfType(Class c)
	{
		return containsAnnotationOfType(c, false);
	}
	
	default boolean containsAnnotationOfType(Class c, boolean checkAncestors)
	{
		return getAnnotationOfType(c, checkAncestors) != null;
	}
	
	default Annotation getAnnotationOfType(Class c)
	{
		return getAnnotationOfType(c, false);
	}
	
	default Annotation getAnnotationOfType(Class c, boolean checkAncestors)
	{
		ArrayList<Annotation> annotations = getAnnotations();
		
		if (annotations != null)
		{
			for (Annotation a : annotations)
			{
				if (c.isAssignableFrom(a.getClass()))
				{
					return a;
				}
			}
		}
		
		if (checkAncestors && ((Node)this).getParent() != null)
		{
			return ((Node)this).getParent().getAnnotationOfType(c, true);
		}
		
		return null;
	}
	
	default StringBuilder generateNovaAnnotations(StringBuilder builder)
	{
		return generateNovaAnnotations(builder, true);
	}
	
	default StringBuilder generateNovaAnnotations(StringBuilder builder, boolean newLine)
	{
		ArrayList<Annotation> annotations = getAnnotations();
		
		if (annotations != null)
		{
			for (Annotation a : annotations)
			{
				a.generateNovaInput(builder).append(newLine ? '\n' : " ");
			}
		}
		
		return builder;
	}
}
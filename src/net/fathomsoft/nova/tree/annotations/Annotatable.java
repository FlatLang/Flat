package net.fathomsoft.nova.tree.annotations;

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
		ArrayList<Annotation> annotations = getAnnotations();
		
		if (annotations != null)
		{
			for (Annotation a : annotations)
			{
				if (c.isAssignableFrom(a.getClass()))
				{
					return true;
				}
			}
		}
		
		return false;
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
			
			if (newLine)
			{
				builder.append('\n');
			}
		}
		
		return builder;
	}
}
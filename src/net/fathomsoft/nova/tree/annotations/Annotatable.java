package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.tree.FileDeclaration;
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
		return containsAnnotationOfType(c, checkAncestors, false);
	}
	
	default boolean containsAnnotationOfType(Class c, boolean checkAncestors, boolean checkPending)
	{
		return getAnnotationOfType(c, checkAncestors, checkPending) != null;
	}
	
	default Annotation getAnnotationOfType(Class c)
	{
		return getAnnotationOfType(c, false);
	}
	
	default Annotation getAnnotationOfType(Class c, boolean checkAncestors)
	{
		return getAnnotationOfType(c, checkAncestors, false);
	}
	
	default Annotation getAnnotationOfType(Class c, boolean checkAncestors, boolean checkPending)
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
		
		if (checkAncestors)
		{
			if (((Node)this).getParent() != null && ((Node)this).getParent() instanceof FileDeclaration == false)
			{
				return ((Node)this).getParent().getAnnotationOfType(c, true, checkPending);
			}
			
			if (checkPending && ((Node)this).getFileDeclaration() != null)
			{
				return ((Node)this).getFileDeclaration().getPendingAnnotationOfType(c);
			}
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
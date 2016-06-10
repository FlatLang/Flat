package net.fathomsoft.nova.tree.annotations;

import java.util.ArrayList;

/**
 * @author	Braden Steffaniak
 */
public interface Annotatable
{
	public ArrayList<Annotation> getAnnotations();
	public void addAnnotation(Annotation annotation);
}
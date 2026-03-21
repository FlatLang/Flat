package nova.c.nodewriters;

import net.fathomsoft.nova.tree.annotations.Annotation;

public abstract class AnnotationWriter extends NodeWriter
{
	public abstract Annotation node();
	
	public StringBuilder generateHeaderFragment(StringBuilder builder)
	{
		return builder;
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		return builder;
	}
}
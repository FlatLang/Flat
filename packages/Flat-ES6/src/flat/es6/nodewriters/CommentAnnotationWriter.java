package flat.es6.nodewriters;

import flat.tree.annotations.CommentAnnotation;

public abstract class CommentAnnotationWriter extends NodeWriter
{
	public abstract CommentAnnotation node();
	
	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		return builder.append("/* ").append(node().value).append(" */");
	}
	
	@Override
	public StringBuilder write(StringBuilder builder)
	{
		return writeExpression(builder);
	}
}

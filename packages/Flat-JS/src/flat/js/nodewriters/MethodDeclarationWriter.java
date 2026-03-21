package flat.js.nodewriters;

import flat.tree.*;
import flat.tree.annotations.AsyncAnnotation;

public abstract class MethodDeclarationWriter extends InstanceDeclarationWriter
{
	public abstract MethodDeclaration node();

	public StringBuilder writeSignature()
	{
		return writeSignature(new StringBuilder());
	}

	public StringBuilder writeSignature(StringBuilder builder)
	{
		if (node().containsAnnotationOfType(AsyncAnnotation.class)) {
			builder.append("async ");
		}

		return builder.append("function ").append(getWriter(node().getParameterList()).write());
	}
}
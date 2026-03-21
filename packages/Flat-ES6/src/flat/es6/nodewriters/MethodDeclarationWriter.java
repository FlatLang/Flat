package flat.es6.nodewriters;

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
		return builder.append(getWriter(node().getParameterList()).write());
	}
}
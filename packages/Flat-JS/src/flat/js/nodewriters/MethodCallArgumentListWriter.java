package flat.js.nodewriters;

import flat.tree.*;
import flat.tree.annotations.LazyAnnotation;

public abstract class MethodCallArgumentListWriter extends ArgumentListWriter
{
	public abstract MethodCallArgumentList node();

	@Override
	public StringBuilder write(StringBuilder builder, boolean parenthesis)
	{
		MethodCall call = node().getMethodCall();

		CallableMethod method = call.getInferredDeclaration();

		Value[] values = method instanceof FlatMethodDeclaration ? node().getArgumentsInOrder((FlatMethodDeclaration)method) : node().getArgumentsInOrder();

		if (parenthesis)
		{
			builder.append('(');
		}

		for (int i = 0; i < values.length; i++)
		{
			if (i > 0)
			{
				builder.append(", ");
			}

			boolean lazyParameter = method.getParameterList().getParameter(i).containsAnnotationOfType(LazyAnnotation.class);

			if (lazyParameter) {
				if (node().getParentMethod().isAsync()) {
					builder.append("async ");
				}

				builder.append("() => ");
			}

			getWriter(values[i]).writeExpression(builder);
		}

		if (parenthesis)
		{
			builder.append(')');
		}

		return builder;
	}
}
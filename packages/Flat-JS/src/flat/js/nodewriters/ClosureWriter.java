package flat.js.nodewriters;

import flat.tree.*;
import flat.tree.lambda.LambdaMethodDeclaration;

public abstract class ClosureWriter extends VariableWriter
{
	public abstract Closure node();

	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		if (node().getMethodDeclaration() instanceof LambdaMethodDeclaration)
		{
			return getWriter(node().getMethodDeclaration()).writeExpression(builder);
		}

		if (isInstanceClosure())
		{
			return writeInstanceClosure(builder);
		}

		return super.writeExpression(builder);
	}

	public StringBuilder writeInstanceExpression(StringBuilder builder, Accessible root)
	{
		ParameterList params = node().getMethodDeclaration().getParameterList();

		String context = "context";

		if (node() != root)
		{
			context = getWriter(root).writeUntil(node()).toString();
		}
		else
		{
			builder.append("(function (context) { return ");
		}

		builder.append("function ").append(getWriter(params).write());
		builder.append(" { var self = ").append(context).append("; return self.");
        getWriter(node().getMethodDeclaration()).writeName(builder).append(".call(self");

		if (params.getNumParameters() > 0)
		{
			builder.append(", ").append(getWriter(params).write(false));
		}

		builder.append("); }");

		if (node() == root)
		{
			builder.append(" })(this)");
		}

		return builder;
	}
}
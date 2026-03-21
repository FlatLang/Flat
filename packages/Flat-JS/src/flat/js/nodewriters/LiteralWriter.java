package flat.js.nodewriters;

import flat.tree.*;

public abstract class LiteralWriter extends IValueWriter implements AccessibleWriter
{
	public abstract Literal node();

	public StringBuilder writeUseExpression(StringBuilder builder)
	{
		if (node().isStringInstantiation())
		{
			getWriter(node().getStringInstantiation()).writeExpression(builder);
		}
		else if (node().isNullLiteral()) {
			builder.append("flat_null");
		}
		else
		{
			builder.append(node().value);
		}

		return writeArrayAccess(builder);
	}

	@Override
	public StringBuilder writeExpression(final StringBuilder builder)
	{
		if (isInstanceClosure())
		{
			return writeInstanceClosure(builder);
		}

		writeUseExpression(builder);

		writeAccessedExpression(builder);

		return builder;
	}
}
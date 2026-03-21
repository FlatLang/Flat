package flat.js.nodewriters;

import flat.tree.*;

public abstract class OperatorWriter extends IValueWriter
{
	public abstract Operator node();

	@Override
	public StringBuilder write(StringBuilder builder)
	{
		return writeExpression(builder);
	}

	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		if (node().operator.equals("!=")) {
			return builder.append("!==");
		}

		return builder.append(node().operator);
	}
}
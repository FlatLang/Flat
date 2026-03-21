package flat.js.nodewriters;

import flat.tree.*;

public abstract class UnaryOperationWriter extends IValueWriter
{
	public abstract UnaryOperation node();

	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		node().forEachChild(child -> {
			getWriter(child).writeExpression(builder);
		});

		return builder;
	}
}
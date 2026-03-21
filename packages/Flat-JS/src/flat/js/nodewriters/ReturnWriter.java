package flat.js.nodewriters;

import flat.tree.*;
import flat.tree.exceptionhandling.Throw;

public abstract class ReturnWriter extends IValueWriter
{
	public abstract Return node();

	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		Value value = node().getValueNode();

		if (value != null && value instanceof Throw == false) {
			builder.append("return");
		}

		if (value != null) {
			builder.append(' ');

			getWriter(value).writeExpression(builder);
		} else if (node().getReturnValues().getNumVisibleChildren() > 0) {
			Node n = node().getReturnValues().getVisibleChild(0);

			getWriter(n).write(builder);
		} else {
			builder.append("return");
		}

		return builder;
	}
}
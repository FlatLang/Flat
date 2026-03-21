package flat.js.nodewriters;

import flat.tree.*;

public abstract class BreakWriter extends NodeWriter
{
	public abstract Break node();

	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		return builder.append("break");
	}
}
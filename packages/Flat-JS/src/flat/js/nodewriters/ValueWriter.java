package flat.js.nodewriters;

import flat.tree.*;

public abstract class ValueWriter extends NodeWriter
{
	public abstract Value node();

	public StringBuilder writeUseExpression()
	{
		return writeUseExpression(new StringBuilder());
	}

	public StringBuilder writeUseExpression(StringBuilder builder)
	{
		return builder.append(writeArrayAccess());
	}

	public StringBuilder writeArrayAccess()
	{
		return writeArrayAccess(new StringBuilder());
	}

	public StringBuilder writeArrayAccess(StringBuilder builder)
	{
		if (node().arrayAccess != null)
		{
			return getWriter(node().arrayAccess).writeExpression(builder);
		}

		return builder;
	}
}
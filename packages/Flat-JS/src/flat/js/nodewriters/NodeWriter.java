package flat.js.nodewriters;

import flat.tree.*;

public abstract class NodeWriter extends Writer
{
	public abstract Node node();

	public StringBuilder write()
	{
		return write(new StringBuilder());
	}

	public StringBuilder write(final StringBuilder builder)
	{
		return writeExpression(builder).append(";\n");
	}

	public StringBuilder writeExpression()
	{
		return writeExpression(new StringBuilder());
	}

	public StringBuilder writeExpression(final StringBuilder builder)
	{
		return builder.append("{{").append(node().getClass().getSimpleName()).append("}}");
	}
}
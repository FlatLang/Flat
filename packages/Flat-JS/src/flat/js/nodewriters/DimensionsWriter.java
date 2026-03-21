package flat.js.nodewriters;

import flat.tree.*;

public abstract class DimensionsWriter extends NodeWriter
{
	public abstract Dimensions node();

	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		node().forEachChild(child -> {
			builder.append('[').append(getWriter(child).writeExpression()).append(']');
		});

		return builder;
	}
}
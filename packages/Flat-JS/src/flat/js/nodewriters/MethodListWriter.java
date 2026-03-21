package flat.js.nodewriters;

import flat.tree.*;

public abstract class MethodListWriter extends TypeListWriter
{
	public abstract MethodList node();

	@Override
	public StringBuilder write(final StringBuilder builder)
	{
		node().forEachVisibleChild(method -> {
			getWriter(method).write(builder);
		});

		return builder;
	}
}
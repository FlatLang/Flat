package flat.js.nodewriters;

import flat.tree.*;

public abstract class InstantiationWriter extends IIdentifierWriter
{
	public abstract Instantiation node();

	@Override
	public StringBuilder writeUseExpression(StringBuilder builder)
	{
		getWriter(node().getIdentifier()).writeUseExpression(builder);

		return builder;
	}
}
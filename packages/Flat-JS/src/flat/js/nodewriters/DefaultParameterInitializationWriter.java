package flat.js.nodewriters;

import flat.tree.*;

public abstract class DefaultParameterInitializationWriter extends NodeWriter
{
	public abstract DefaultParameterInitialization node();

	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		String name = node().parameter.getName();

		return builder.append(name).append(" = typeof ").append(name).append(" === 'undefined' ? ")
			.append(getWriter(node().parameter.getDefaultValue()).writeExpression()).append(" : ")
			.append(name);
	}
}
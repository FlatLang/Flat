package flat.es6.nodewriters;

import flat.tree.MethodCall;
import flat.tree.variables.Super;

public abstract class SuperWriter extends PriorityWriter
{
	public abstract Super node();
	
	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		builder.append("super.");

		return builder.append(writeAccessedExpression(false));
	}
}
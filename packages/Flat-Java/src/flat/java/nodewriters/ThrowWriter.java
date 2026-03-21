package flat.java.nodewriters;

import flat.tree.Accessible;
import flat.tree.exceptionhandling.Throw;

public abstract class ThrowWriter extends ValueWriter
{
	public abstract Throw node();
	
	@Override
	public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt)
	{
		builder.append("throw ");

		return getWriter(node().getExceptionInstance()).writeExpression(builder, stopAt);
	}
}
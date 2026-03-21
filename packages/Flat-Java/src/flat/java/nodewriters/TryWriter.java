package flat.java.nodewriters;

import flat.tree.exceptionhandling.ExceptionHandler;
import flat.tree.exceptionhandling.Try;

public abstract class TryWriter extends ExceptionHandlerWriter
{
	public abstract Try node();
	
	@Override
	public StringBuilder write(StringBuilder builder)
	{
		return builder.append("try ").append(getWriter(node().getScope()).write(true, false)).append(" ");
	}
}
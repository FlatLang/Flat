package flat.java.nodewriters;

import flat.tree.*;

public abstract class RepeatWriter extends LoopWriter
{
	public abstract Repeat node();
	
	@Override
	public StringBuilder write(StringBuilder builder)
	{
		if (node().getValueNode() != null)
		{
			builder.append("for (").append(node().getName()).append(" = 0; ").append(node().getName()).append(" < ").append(getWriter(node().getValueNode()).writeExpression()).append("; ").append(node().getName()).append("++)\n");
		}
		else
		{
			builder.append("for (;;)\n");
		}
		
		return getWriter(node().getScope()).write(builder);
	}
}
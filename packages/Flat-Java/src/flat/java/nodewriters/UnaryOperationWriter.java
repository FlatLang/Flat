package flat.java.nodewriters;

import flat.tree.*;

public abstract class UnaryOperationWriter extends IValueWriter
{
	public abstract UnaryOperation node();
	
	@Override
	public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt)
	{
		node().forEachChild(child -> {
			getWriter(child).writeExpression(builder, stopAt);
		});
		
		return builder;
	}
}
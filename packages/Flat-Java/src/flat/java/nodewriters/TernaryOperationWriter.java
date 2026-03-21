package flat.java.nodewriters;

import flat.tree.*;

public abstract class TernaryOperationWriter extends IValueWriter implements AccessibleWriter
{
	public abstract TernaryOperation node();
	
	@Override
	public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt)
	{
		getWriter(node().getCondition()).writeExpression(builder, stopAt).append(" ? ");
		getWriter(node().getTrueValue()).writeExpression(builder, stopAt).append(" : ");
		return getWriter(node().getFalseValue()).writeExpression(builder, stopAt);
	}
}
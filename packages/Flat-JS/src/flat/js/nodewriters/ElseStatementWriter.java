package flat.js.nodewriters;

import flat.tree.*;

public abstract class ElseStatementWriter extends ControlStatementWriter
{
	public abstract ElseStatement node();

	@Override
	public StringBuilder write(StringBuilder builder)
	{
		builder.append("else ");

		if (node().getNumChildren() == 2)
		{
			Node child = node().getChild(1);

			if (child instanceof IfStatement)
			{
				getWriter(child).writeExpression(builder).append(' ');

				getWriter(child.getScope()).write(builder, true, false);

				return builder.append(node().getNextNode() instanceof ElseStatement ? ' ' : '\n');
			}
		}

		return getWriter(node().getScope()).write(builder);
	}
}
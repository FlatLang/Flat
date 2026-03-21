package flat.js.nodewriters;

import flat.tree.*;

public abstract class ForEachLoopWriter extends LoopWriter
{
	public abstract ForEachLoop node();

	@Override
	public StringBuilder write(StringBuilder builder)
	{
		Value hasNextCheck = node().getHasNextCheck();

		builder.append("while (").append(getWriter(hasNextCheck).writeExpression()).append(") ");

		node().forEachChild(child -> {
			if (child != node().getArgumentList())
			{
				getWriter(child).write(builder);
			}
		});

		return builder;
	}
}
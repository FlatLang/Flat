package flat.js.nodewriters;

import flat.tree.*;

public abstract class ArgumentListWriter extends ListWriter
{
	public abstract ArgumentList node();

	@Override
	public StringBuilder write(StringBuilder builder)
	{
		return write(builder, true);
	}

	public StringBuilder write(StringBuilder builder, boolean parenthesis)
	{
		final int[] i = new int[] { 0 };

		if (parenthesis)
		{
			builder.append('(');
		}

		node().forEachVisibleChild(child -> {
			if (i[0]++ > 0)
			{
				builder.append(", ");
			}

			getWriter(child).writeExpression(builder);
		});

		if (parenthesis)
		{
			builder.append(')');
		}

		return builder;
	}
}
package flat.js.nodewriters;

import flat.tree.match.Default;

public abstract class DefaultWriter extends MatchCaseWriter
{
	public abstract Default node();

	@Override
	public StringBuilder write(StringBuilder builder)
	{
		if (node().getParentMatch().isConventionalSwitch())
		{
			builder.append("default:\n");

			getWriter(node().getScope()).write(builder, false);
		}
		else
		{
			builder.append("else ");

			getWriter(node().getScope()).write(builder);
		}

		return builder;
	}
}
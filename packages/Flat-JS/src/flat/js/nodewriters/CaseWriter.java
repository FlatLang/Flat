package flat.js.nodewriters;

import flat.tree.Scope;
import flat.tree.Value;
import flat.tree.match.Case;
import flat.tree.match.MatchChild;
import flat.tree.variables.Variable;

public abstract class CaseWriter extends MatchCaseWriter
{
	public abstract Case node();

	@Override
	public StringBuilder write(StringBuilder builder)
	{
		Scope scope = node().getScope();

		if (node().getParentMatch().isConventionalSwitch())
		{
			Value value = node().getValue();

			builder.append("case ").append(getWriter(value).writeExpression()).append(":\n");

			getWriter(scope).write(builder, false, false);

			if (node().requiresBreak())
			{
				builder.append("break;\n");
			}
		}
		else
		{
			Value conditionValue = node().getCondition();

			String condition = getWriter(conditionValue).writeExpression().toString();

			Case before = null;
			String fall   = "";

			if (node().getParent().getChildBefore(node()) instanceof Case)
			{
				before = (Case)node().getParent().getChildBefore(node());
			}

			if (before != null)
			{
				if (before.containsFallthrough())
				{
					Variable fallthrough = node().getParentMatch().getLocalFallthrough();

					fall = getWriter(fallthrough).writeExpression() + " || ";
				}
				else
				{
					builder.append("else ");
				}
			}

			builder.append("if (").append(fall).append(condition).append(") {\n");

			getWriter(scope).write(builder, false, false);

			if (node().getParentMatch().requiresLoopFacade() && node().requiresBreak())
			{
				builder.append("break;\n");
			}

			builder.append("}");

			if (node().getNextNode() instanceof MatchChild)
			{
				builder.append(' ');
			}
			else
			{
				builder.append('\n');
			}
		}

		return builder;
	}
}
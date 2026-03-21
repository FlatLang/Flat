package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.match.Default;

public abstract class DefaultWriter extends MatchCaseWriter
{
	public abstract Default node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		Scope scope = node().getScope();
		
		if (node().getParentMatch().isConventionalSwitch())
		{
			builder.append("default:").append('\n');
			
			getWriter(scope).generateSource(builder, false);
		}
		else
		{
			builder.append("else").append('\n');
			
			getWriter(scope).generateSource(builder);
		}
		
		return builder;
	}
}
package nova.c.nodewriters;

import net.fathomsoft.nova.tree.match.Fallthrough;
import net.fathomsoft.nova.tree.variables.Variable;

public abstract class FallthroughWriter extends MatchChildWriter
{
	public abstract Fallthrough node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		Variable fall = node().getParentMatch().getLocalFallthrough();
		
		if (fall != null)
		{
			getWriter(fall).generateSourceFragment(builder).append(" = 1;\n");
		}
		
		return builder;
	}
}
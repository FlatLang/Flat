package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class ContinueWriter extends NodeWriter
{
	public abstract Continue node();
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		return builder.append("continue;");
	}
}
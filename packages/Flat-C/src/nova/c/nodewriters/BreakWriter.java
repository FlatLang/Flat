package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class BreakWriter extends NodeWriter
{
	public abstract Break node();
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		return builder.append("break;");
	}
}
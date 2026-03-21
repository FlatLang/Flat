package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class UntilWriter extends IfStatementWriter
{
	public abstract Until node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		super.generateSourceFragment(builder).append('\n');
		
		Scope scope = node().getScope();
		
		return getWriter(scope).generateSource(builder);
	}
}
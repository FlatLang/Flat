package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class IfStatementWriter extends ControlStatementWriter
{
	public abstract IfStatement node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		generateSourceFragment(builder).append('\n');
		
		Scope scope = node().getScope();
		
		getWriter(scope).generateSource(builder);
		
		return builder;
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		Value condition = node().getCondition();
		
		return builder.append("if (").append(getWriter(condition).generateSourceFragment()).append(')');
	}
}
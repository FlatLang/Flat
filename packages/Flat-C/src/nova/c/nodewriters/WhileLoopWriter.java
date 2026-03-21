package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class WhileLoopWriter extends LoopWriter
{
	public abstract WhileLoop node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		Node condition = node().getCondition();
		
		builder.append("while (").append(getWriter(condition).generateSourceFragment()).append(')').append('\n');
		
		Scope scope = node().getScope();
		
		getWriter(scope).generateSource(builder);
		
		return builder;
	}
}
package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.exceptionhandling.Finally;

public abstract class FinallyWriter extends ExceptionHandlerWriter
{
	public abstract Finally node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		builder.append("FINALLY").append('\n');
		
		Scope scope = node().getScope();
		
		getWriter(scope).generateSource(builder);
		
		builder.append("END_TRY;").append('\n');
		
		return builder;
	}
}
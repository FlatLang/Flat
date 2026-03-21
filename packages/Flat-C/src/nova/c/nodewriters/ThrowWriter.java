package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.exceptionhandling.Throw;

public abstract class ThrowWriter extends ValueWriter
{
	public abstract Throw node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return generateSourceFragment(builder).append(";\n");
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		builder.append("THROW").append('(');
		Identifier exception = node().getExceptionInstance();
		
		getWriter(exception).generateSourceFragment(builder).append(", ").append(node().soft ? 1 : 0).append(')');
		
		return builder;
	}
}
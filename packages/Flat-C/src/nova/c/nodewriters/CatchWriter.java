package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.exceptionhandling.Catch;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Location;

public abstract class CatchWriter extends ExceptionHandlerWriter
{
	public abstract Catch node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		ClassDeclarationWriter writer = getWriter(node().getException().type);
		
		builder.append("CATCH ").append('(').append(writer.getVTableClassInstance()).append(")\n");
		
		Scope scope = node().getScope();
		
		getWriter(scope).generateSource(builder);
		
		return builder;
	}
}
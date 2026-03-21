package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.exceptionhandling.Exception;
import net.fathomsoft.nova.tree.exceptionhandling.Try;

public abstract class TryWriter extends ExceptionHandlerWriter
{
	public abstract Try node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		builder.append("TRY").append('\n');
		builder.append('{').append('\n');
		generateExceptionCodes(builder).append('\n');
		
		Scope scope = node().getScope();
		
		getWriter(scope).generateSource(builder);
		
		builder.append('}').append('\n');
		
		return builder;
	}
	
	/**
	 * Generate a String that adds all of the exception codes that node()
	 * try node catches to the exception data instance.
	 *
	 * @return The generated C language String.
	 */
	private StringBuilder generateExceptionCodes(StringBuilder builder)
	{
		String variableName = Exception.EXCEPTION_DATA_IDENTIFIER;
		
		for (int i = 0; i < node().exceptions.size(); i++)
		{
			Exception exception = node().exceptions.get(i);
			ClassDeclarationWriter c = getWriter(exception.type);
			
			builder.append("nova_exception_Nova_ExceptionData_Nova_addCaught(").append(variableName).append(", ").append(c.getVTableClassInstance()).append(", ").append(exception.soft ? 1 : 0).append(");").append('\n');
		}
		
		return builder;
	}
}
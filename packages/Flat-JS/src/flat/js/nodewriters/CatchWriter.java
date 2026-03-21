package flat.js.nodewriters;

import flat.tree.exceptionhandling.Catch;
import flat.tree.exceptionhandling.ExceptionHandler;

public abstract class CatchWriter extends ExceptionHandlerWriter
{
	public abstract Catch node();

	@Override
	public StringBuilder write(StringBuilder builder)
	{
		builder.append("catch (").append(node().getExceptionDeclaration().getName()).append(") ").append(" {\n");

		ClassDeclarationWriter classWriter = getWriter(node().getProgram().getClassDeclaration("flat/exception/Exception"));

		builder.append("if (!(").append(node().getExceptionDeclaration().getName()).append(" instanceof ").append(classWriter.writeName()).append(")) {\n");
		builder.append("console.log(").append(Math.random()).append(");\n");
		builder.append("console.error(").append(node().getExceptionDeclaration().getName()).append(");\n");
		builder.append("process.exit(1);\n");
		builder.append("} else {\n");

		getWriter(node().getScope()).write(builder, false, false);

		builder.append("}\n");

		if (node().getNextNode() instanceof ExceptionHandler)
		{
			builder.append(' ');
		}
		else
		{
			builder.append('\n');
		}

		return builder.append("}\n");
	}
}
package flat.js.nodewriters;

import flat.tree.*;

public abstract class LocalDeclarationWriter extends VariableDeclarationWriter
{
	public abstract LocalDeclaration node();

	@Override
	public StringBuilder write(StringBuilder builder)
	{
		builder.append("var ");

		return writeExpression(builder).append(";\n");
	}
}
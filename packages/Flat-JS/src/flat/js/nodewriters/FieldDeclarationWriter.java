package flat.js.nodewriters;

import flat.tree.variables.FieldDeclaration;

public abstract class FieldDeclarationWriter extends InstanceDeclarationWriter
{
	public abstract FieldDeclaration node();

	@Override
	public StringBuilder write(StringBuilder builder)
	{
		if (node().isStatic()) {
			getWriter(node().getDeclaringClass()).writeUseExpression(builder);
		} else {
			builder.append("this");
		}

		builder.append(".");

		return super.write(builder);
	}
}
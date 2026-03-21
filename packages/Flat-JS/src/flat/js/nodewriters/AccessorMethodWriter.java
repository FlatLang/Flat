package flat.js.nodewriters;

import flat.tree.*;
import flat.tree.lambda.LambdaMethodDeclaration;

public abstract class AccessorMethodWriter extends PropertyMethodWriter
{
	public abstract AccessorMethod node();

	@Override
	public StringBuilder write(StringBuilder builder) {
		if (node().getLazy() && !node().isStatic()) {
			writeLazyName(builder).append(" = undefined;\n");
		}

		return super.write(builder);
	}

	@Override
	public StringBuilder writeBody(StringBuilder builder) {
		builder.append("{\n");

		if (node().getLazy()) {
			builder.append("return typeof ");
			writeLazyAccess(builder).append(" !== 'undefined' ? ");
			writeLazyAccess(builder).append(" : (");
			writeLazyAccess(builder);
			builder.append(" = (() => {\n");
		}

		getWriter(node().getScope()).write(builder, false, false);

		if (node().getLazy()) {
			builder.append("})());\n");
		}

		return builder.append('}');
	}

	public StringBuilder writeLazyName(StringBuilder builder) {
		builder.append("__lazy_");
		return writeName(builder);
	}

	public StringBuilder writeLazyAccess(StringBuilder builder) {
		if (node().isStatic()) {
			getWriter(node().getParentClass()).writeName(builder).append(".");
		} else {
			builder.append("this.");
		}

		return writeLazyName(builder);
	}

	@Override
	public StringBuilder writeName(StringBuilder builder)
	{
		builder.append("accessor_");

		return super.writeName(builder);
	}
}

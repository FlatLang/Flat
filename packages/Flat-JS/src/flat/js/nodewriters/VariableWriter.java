package flat.js.nodewriters;

import flat.tree.Constructor;
import flat.tree.InitializationMethod;
import flat.tree.InstanceDeclaration;
import flat.tree.ReferenceParameter;
import flat.tree.annotations.LazyAnnotation;
import flat.tree.lambda.LambdaMethodDeclaration;
import flat.tree.variables.Variable;
import flat.tree.variables.VariableDeclaration;

public abstract class VariableWriter extends IdentifierWriter
{
	public abstract Variable node();

	public StringBuilder writeUsePrefix(StringBuilder builder) {
		if (!node().isAccessed())
		{
			VariableDeclaration declaration = node().getDeclaration();

			if (!declaration.isExternal())
			{
				// static field
				if (!declaration.isInstance())
				{
					getWriter(declaration.getDeclaringClass()).writeName(builder).append('.');
				}
				else if (declaration instanceof InitializationMethod)
				{
					getWriter(declaration.getDeclaringClass()).writeName(builder).append('.');
				}
				// field access
				else if (!(declaration instanceof Constructor) && !declaration.isLocal())
				{
					if (!(node().getDeclaration() instanceof ReferenceParameter))
					{
						if (declaration instanceof InstanceDeclaration && ((InstanceDeclaration) declaration).isStatic()) {
							getWriter(declaration.getDeclaringClass()).writeUseExpression(builder);
						} else if (node().getParentMethod() instanceof LambdaMethodDeclaration) {
							builder.append("self");
						} else {
							builder.append("this");
						}

						builder.append('.');
					}
				}
			}
		}

		return builder;
	}

	@Override
	public StringBuilder writeUseExpression(StringBuilder builder) {
		boolean lazyParameter = node().getDeclaration().containsAnnotationOfType(LazyAnnotation.class);
		boolean asyncMethod = node().getParentMethod() != null && node().getParentMethod().isAsync();

		if (lazyParameter && asyncMethod) {
			builder.append("(await ");
		}

		writeUsePrefix(builder);

		super.writeUseExpression(builder);

		if (lazyParameter) {
			builder.append("()");

			if (asyncMethod) {
				builder.append(")");
			}
		}

		return builder;
	}

	@Override
	public StringBuilder writeExpression(StringBuilder builder) {
		if (isInstanceClosure())
		{
			return writeInstanceClosure(builder);
		}

		return super.writeExpression(builder);
	}
}

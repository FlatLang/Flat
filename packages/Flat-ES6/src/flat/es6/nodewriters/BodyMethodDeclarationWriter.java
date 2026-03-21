package flat.es6.nodewriters;

import flat.tree.*;
import flat.tree.annotations.AsyncAnnotation;
import flat.tree.exceptionhandling.Throw;
import flat.tree.lambda.LambdaMethodDeclaration;

public abstract class BodyMethodDeclarationWriter extends FlatMethodDeclarationWriter
{
	public abstract BodyMethodDeclaration node();

	@Override
	public StringBuilder write(StringBuilder builder)
	{
		writeSignature(builder, true, true).append(" ");
		writeBody(builder);

		if (shouldUseShorthand()) {
			builder.append(";");
		}

		return builder.append("\n");
	}

	public boolean shouldUseStatic() {
		return node().isStatic() || node() instanceof InitializationMethod || node() instanceof AssignmentMethod || node() instanceof AnonymousCompilerMethod || node() instanceof ExtensionMethodDeclaration;
	}

	public boolean shouldUseSelfVariable() {
		return node() instanceof InitializationMethod || node() instanceof LambdaMethodDeclaration == false && node().whereChildOfType(Closure.class, BodyMethodDeclarationWriter::isLambda);
	}

	public boolean shouldUseShorthand() {
		if (
			false &&
			!node().isExtension() &&
				!shouldUseSelfVariable() &&
				node().getScope().getNumVisibleChildren() == 1 &&
				node().getScope().getVariableList().getNumVisibleChildren() == 0
		) {
			Node child = node().getScope().getVisibleChild(0);

			if (child instanceof Return) {
				Return r = (Return) child;

				if (r.getValueNode() instanceof Throw == false) {
					return true;
				}
			}
		}

		return false;
	}

	public StringBuilder writeSignature(StringBuilder builder, boolean checkStatic, boolean checkAsync) {
		if (checkStatic && shouldUseStatic()) {
			builder.append("static ");
		}

		if (!shouldUseShorthand() && checkAsync && node().isAsync()) {
			builder.append("async ");
		}

		writeName(builder);

		if (shouldUseShorthand()) {
			builder.append(" = ");
		}

		if (shouldUseShorthand() && checkAsync && node().isAsync()) {
			builder.append("async ");
		}

		getWriter(node().getParameterList()).write(builder);

		if (shouldUseShorthand()) {
			builder.append(" =>");
		}

		return builder;
	}

	public StringBuilder writeBody()
	{
		return writeBody(new StringBuilder());
	}

	private static boolean isLambda(Node n)
	{
		return ((Closure)n).isLambda();
	}

	public StringBuilder writeBody(StringBuilder builder)
	{
		if (shouldUseShorthand()) {
			Node child = node().getScope().getVisibleChild(0);
			if (child instanceof Return) {
				child = ((Return)child).getValueNode();
			}
			return getWriter(child).writeExpression(builder);
		}
		builder.append("{\n");

		if (shouldUseSelfVariable())
		{
			builder.append("let self = this;\n\n");
		}

		getWriter(node().getScope()).write(builder, false, false);

		return builder.append('}');
	}
}
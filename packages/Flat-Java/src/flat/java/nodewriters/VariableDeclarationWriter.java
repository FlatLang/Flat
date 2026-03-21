package flat.java.nodewriters;

import flat.tree.Accessible;
import flat.tree.FlatMethodDeclaration;
import flat.tree.Node;
import flat.tree.Value;
import flat.tree.lambda.LambdaMethodDeclaration;
import flat.tree.variables.Variable;
import flat.tree.variables.VariableDeclaration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class VariableDeclarationWriter extends IIdentifierWriter
{
	public abstract VariableDeclaration node();

	public boolean requiresLambdaWrapperClass() {
		FlatMethodDeclaration methodDeclaration = node().getParentMethod();

		if (methodDeclaration == null) return false;

		LambdaMethodDeclaration parentLambda = methodDeclaration instanceof LambdaMethodDeclaration
			? (LambdaMethodDeclaration) methodDeclaration
			: null;

		List<Variable> references = Stream.concat(
				node().references.stream(),
				node().closureVariableDeclarations.stream().flatMap(c -> c.references.stream())
		).collect(Collectors.toList());

		if (references.stream()
			.map(Node::getParentMethod)
			.anyMatch(method -> method != parentLambda && method instanceof LambdaMethodDeclaration)) {
			return true;
		}
		if (references.stream()
				.flatMap(Node::getAncestorsStream)
				.filter(n -> n instanceof Accessible)
				.map(n -> (Accessible)n)
				.anyMatch(Accessible::isChainNavigation)) {
			return true;
		}

		return false;
	}

	@Override
	public StringBuilder write(StringBuilder builder) {
		writeExpression(builder).append(" = ");

		return writeDefaultValue(builder).append(";\n");
	}

	@Override
	public StringBuilder writeExpression(StringBuilder builder, Accessible stopAt) {
		return writeSignature(builder);
	}

	private StringBuilder writeDefaultValue(StringBuilder builder) {
		if (requiresLambdaWrapperClass()) {
			return builder.append("new java.util.concurrent.atomic.AtomicReference<>()");
		}

		if (node().isPrimitive()) {
			switch (node().getType()) {
				case "Bool": return builder.append("false");
				default: return builder.append("0");
			}
		}

		return builder.append("null");
	}

	public StringBuilder writeType(
		StringBuilder builder,
		boolean space,
		boolean convertPrimitive,
		boolean boxPrimitive,
		Value context,
		boolean writeGenerics,
		boolean writeArray,
		boolean checkLambdaWrapperClass
	) {
		if (checkLambdaWrapperClass && requiresLambdaWrapperClass()) {
			builder.append("java.util.concurrent.atomic.AtomicReference<");
			super.writeType(builder, false, convertPrimitive, true, context, writeGenerics, writeArray);
			builder.append('>');

			if (space) {
				builder.append(' ');
			}

			return builder;
		}

		return super.writeType(builder, space, convertPrimitive, boxPrimitive, context, writeGenerics, writeArray);
	}

	public final StringBuilder writeSignature()
	{
		return writeSignature(new StringBuilder());
	}
	
	public final StringBuilder writeSignature(StringBuilder builder) {
		return writeSignature(builder, null);
	}

	public final StringBuilder writeSignature(StringBuilder builder, Value context) {
		return writeSignature(builder, context, null);
	}

	public StringBuilder writeSignature(StringBuilder builder, Value context, String name) {
		writeType(builder, true, true, false, context, true, true,  true);
		return writeName(builder, name);
	}

	@Override
	public StringBuilder writeName(StringBuilder builder, String name) {
		name = name != null ? name : node().getName();

		switch (name) {
			case "class":
				return super.writeName(builder.append("flat_"), name);
			default:
				return super.writeName(builder, name);
		}
	}
}
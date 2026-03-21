package flat.java.nodewriters;

import flat.tree.*;
import flat.tree.variables.Variable;

public abstract class VariableWriter extends IdentifierWriter
{
	public abstract Variable node();

	public boolean isExtensionDeclaration() {
		return node().declaration instanceof ExtensionMethodDeclaration ||
			node().declaration instanceof ExtensionFieldDeclaration ||
			node().declaration instanceof PropertyMethod && getWriter((PropertyMethod)node().declaration).isExtension();
	}

	public boolean requiresLambdaWrapperClass() {
		return getWriter(node().declaration).requiresLambdaWrapperClass();
	}

	public StringBuilder writeExtensionUseExpression(StringBuilder builder, Identifier start) {
		throw new UnsupportedOperationException("should be a method call (" + node().getClass().getName() + "/" + node().declaration.getClass().getName() + "): (" + node().isUserMade() + "/" + node().declaration.isUserMade() + "): " + node().getParentClass().getName() + "." + node().getParentMethod().getName() + ": " + node().generateFlatInput());
	}

	@Override
	public StringBuilder writeName(StringBuilder builder, String name) {
		if (node().getName().equals("this")) {
			FlatMethodDeclaration method = node().getParentMethod();

			if (method instanceof ExtensionMethodDeclaration || method instanceof PropertyMethod && method.getParentClass() instanceof ExtensionDeclaration) {
				return builder.append("_this");
			}
		}

		return super.writeName(builder, name);
	}

	@Override
	public StringBuilder writeExtensionReferenceAccess(StringBuilder builder) {
		if (node().getDeclaringClass() instanceof ExtensionDeclaration) return builder;

		return super.writeExtensionReferenceAccess(builder);
	}

	@Override
	public StringBuilder writeUseExpression(StringBuilder builder) {
		if ((node().getRootNode() instanceof Assignment == false || ((Assignment)node().getRootNode()).getAssignedNode() != node()) &&
			requiresLambdaWrapperClass()) {
			return super.writeUseExpression(builder).append(".get()");
		}

		return super.writeUseExpression(builder);
	}

	@Override
	public StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive, boolean boxPrimitive, Value context, boolean writeGenerics, boolean writeArray) {
		StringBuilder type = getWriter(node().getDeclaration()).writeType(new StringBuilder(), space, convertPrimitive, boxPrimitive, context, writeGenerics, writeArray);

		for (int i = 0; i < node().getArrayAccessDimensions(); i++) {
			type.delete(type.length() - 2, type.length());
		}

		return builder.append(type);
	}
}
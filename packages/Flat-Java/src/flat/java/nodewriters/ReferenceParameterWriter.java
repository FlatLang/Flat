package flat.java.nodewriters;

import flat.tree.*;
import flat.tree.variables.ObjectReference;

public abstract class ReferenceParameterWriter extends ParameterWriter
{
	public abstract ReferenceParameter node();

	@Override
	public StringBuilder writeName(StringBuilder builder, String name) {
		FlatMethodDeclaration method = node().getParentMethod();

		if (method instanceof ExtensionMethodDeclaration || method instanceof PropertyMethod && method.getParentClass() instanceof ExtensionDeclaration) {
			return builder.append("_this");
		}

		return super.writeName(builder, name);
	}
}
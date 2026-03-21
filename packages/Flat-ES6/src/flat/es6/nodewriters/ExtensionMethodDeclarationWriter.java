package flat.es6.nodewriters;

import flat.tree.ExtensionMethodDeclaration;

public abstract class ExtensionMethodDeclarationWriter extends BodyMethodDeclarationWriter
{
	public abstract ExtensionMethodDeclaration node();
	
	public StringBuilder writePrototypeAccess(StringBuilder builder)
	{
		return builder;
	}

	@Override
	public StringBuilder writeName(StringBuilder builder) {
		builder.append(node().getParameterList().getReferenceParameter().getTypeClassName()).append("_");
		return super.writeName(builder);
	}
}

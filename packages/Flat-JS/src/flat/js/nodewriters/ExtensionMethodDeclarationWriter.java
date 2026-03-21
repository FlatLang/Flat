package flat.js.nodewriters;

import flat.tree.ExtensionMethodDeclaration;

public abstract class ExtensionMethodDeclarationWriter extends BodyMethodDeclarationWriter
{
	public abstract ExtensionMethodDeclaration node();

	public StringBuilder writePrototypeAccess(StringBuilder builder)
	{
		return builder;
	}
}
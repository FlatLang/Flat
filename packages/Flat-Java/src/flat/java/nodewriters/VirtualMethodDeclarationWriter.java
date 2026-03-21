package flat.java.nodewriters;

import flat.tree.*;

public abstract class VirtualMethodDeclarationWriter extends BodyMethodDeclarationWriter
{
	public abstract VirtualMethodDeclaration node();

	@Override
	public StringBuilder writeName(StringBuilder builder, String name, boolean appendStatic) {
		return getWriter(node().base).writeName(builder, name, appendStatic);
	}

	@Override
	public StringBuilder writeStaticMethodInstanceOverload(StringBuilder builder) {
		return builder;
	}
}
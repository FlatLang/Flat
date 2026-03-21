package flat.es6.nodewriters;

import flat.tree.*;

public abstract class VirtualMethodDeclarationWriter extends BodyMethodDeclarationWriter
{
	public abstract VirtualMethodDeclaration node();
	
	@Override
	public StringBuilder writeName(StringBuilder builder)
	{
		return getWriter(node().base).writeName(builder);
	}
}
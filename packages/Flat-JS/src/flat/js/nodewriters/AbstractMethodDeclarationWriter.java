package flat.js.nodewriters;

import flat.tree.*;

public abstract class AbstractMethodDeclarationWriter extends FlatMethodDeclarationWriter
{
	public abstract AbstractMethodDeclaration node();

	@Override
	public StringBuilder write(StringBuilder builder)
	{
		return builder;
	}
}
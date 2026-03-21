package flat.es6.nodewriters;

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
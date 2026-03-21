package flat.es6.nodewriters;

import flat.tree.Package;

public abstract class PackageWriter extends NodeWriter
{
	public abstract Package node();
	
	@Override
	public StringBuilder write(StringBuilder builder)
	{
		return builder;
	}
}
package flat.java.nodewriters;

import flat.tree.*;

public abstract class StaticClassReferenceWriter extends IIdentifierWriter
{
	public abstract StaticClassReference node();
	
	@Override
	public StringBuilder writeName(StringBuilder builder, String name)
	{
		return getWriter(node().getTypeClass()).writeName(builder, name);
	}
}
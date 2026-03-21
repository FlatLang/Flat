package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.variables.ArrayAccess;

public abstract class ArrayAccessWriter extends NodeWriter
{
	public abstract ArrayAccess node();
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		Dimensions dimensions = node().getDimensions();
		
		getWriter(dimensions).generateSourceFragment(builder);
		
		return builder;
	}
}
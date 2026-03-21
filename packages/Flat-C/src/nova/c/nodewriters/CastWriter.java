package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class CastWriter extends IValueWriter
{
	public abstract Cast node();
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		builder.append('(').append(generateType()).append(')');
		
		Value value = node().getValueNode();
		Value ret = value.getReturnedNode();
		
		getWriter(ret).generatePointerToValueConversion(builder);
		getWriter(value).generateSourceFragment(builder);
		
		return builder;
	}
}
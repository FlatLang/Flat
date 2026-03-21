package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class DimensionsWriter extends NodeWriter
{
	public abstract Dimensions node();
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		return generateSourceFragment(builder, "[", "]");
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder, String prefix, String postfix)
	{
		for (int i = 0; i < node().getNumChildren(); i++)
		{
			Node child = node().getChild(i);
			
			builder.append(prefix).append(getWriter(child).generateSourceFragment()).append(postfix);
		}
		
		return builder;
	}
}
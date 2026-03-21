package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class ArgumentListWriter extends ListWriter
{
	public abstract ArgumentList node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return generateSourceFragment(builder);
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		for (int i = 0; i < node().getNumChildren(); i++)
		{
			Node child = node().getChild(i);
			
			getWriter(child).generateSourceFragment(builder);
		}
		
		return builder;
	}
}
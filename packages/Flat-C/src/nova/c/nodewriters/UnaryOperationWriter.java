package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class UnaryOperationWriter extends IValueWriter
{
	public abstract UnaryOperation node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return generateSourceFragment(builder).append(";\n");
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
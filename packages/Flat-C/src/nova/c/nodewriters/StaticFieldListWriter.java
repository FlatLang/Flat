package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.variables.StaticFieldList;

public abstract class StaticFieldListWriter extends ListWriter
{
	public abstract StaticFieldList node();
	
	public StringBuilder generateHeader(StringBuilder builder)
	{
		for (int i = 0; i < node().getNumChildren(); i++)
		{
			Node child = node().getChild(i);
			
			getWriter(child).generateHeader(builder);
		}
		
		return builder;
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		for (int i = 0; i < node().getNumChildren(); i++)
		{
			Node child = node().getChild(i);
			
			getWriter(child).generateSource(builder);
		}
		
		return builder;
	}
}
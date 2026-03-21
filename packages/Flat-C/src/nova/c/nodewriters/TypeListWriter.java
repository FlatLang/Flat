package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class TypeListWriter extends ListWriter
{
	public abstract TypeList node();
	
	public StringBuilder generateHeaderFragment(StringBuilder builder)
	{
		for (Object child : node())
		{
			getWriter(((Node)child)).generateHeader(builder);
		}
		
		return builder;
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		for (Object child : node())
		{
			getWriter(((Node)child)).generateSource(builder);
		}
		
		return builder;
	}

	@Override
	public StringBuilder generateSource(StringBuilder builder) {
		node().forEachChild(child -> {
			getWriter(child).generateSource(builder);
		});

		return builder;
	}
}
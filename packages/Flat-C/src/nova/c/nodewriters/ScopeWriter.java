package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class ScopeWriter extends NodeWriter
{
	public abstract Scope node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return generateSource(builder, true);
	}
	
	public StringBuilder generateSource(StringBuilder builder, boolean braces)
	{
		if (node().getNumChildren() <= 1)
		{
			if (node().getParent() instanceof Loop)
			{
				// Insert the semicolon before the new line.
				return builder.insert(builder.length() - 1, ";");
			}
		}
		
		if (braces)
		{
			builder.append('{').append('\n');
		}
		
		for (int i = 0; i < node().getNumChildren(); i++)
		{
			Node child = node().getChild(i);
			
			getWriter(child).generateSource(builder);
		}
		
		if (braces)
		{
			builder.append('}').append('\n');
		}
		
		return builder;
	}
}
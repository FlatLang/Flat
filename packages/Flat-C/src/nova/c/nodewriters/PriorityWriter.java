package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class PriorityWriter extends ValueWriter implements AccessibleWriter
{
	public abstract Priority node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return generateSourceFragment(builder).append(";\n");
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		if (node().isSpecialFragment())
		{
			return generateSpecialFragment(builder);
		}
		else
		{
			Value contents = node().getContents();
			
			return builder.append('(').append(getWriter(contents).generateSourceFragment()).append(')').append(generateArrayAccess()).append(generateChildrenSourceFragment());
		}
	}
	
	public StringBuilder generateUseOutput(StringBuilder builder)
	{
		Value contents = node().getContents();
		
		return builder.append('(').append(getWriter(contents).generateSourceFragment()).append(')').append(generateArrayAccess());
	}
}
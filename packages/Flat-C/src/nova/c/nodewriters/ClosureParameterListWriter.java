package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class ClosureParameterListWriter extends ParameterListWriter
{
	public abstract ClosureParameterList node();
	
	public StringBuilder generateHeader(StringBuilder builder)
	{
		generateHeaderDefaultParameters(builder);
		
		for (Value param : node())
		{
			builder.append(", ");
			
			getWriter(param).generateHeader(builder);
		}
		
		builder.append(", void*");
		
		return builder;
	}
}
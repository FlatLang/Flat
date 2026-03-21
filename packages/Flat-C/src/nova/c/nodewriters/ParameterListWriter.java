package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class ParameterListWriter extends TypeListWriter
{
	public abstract ParameterList node();
	
	public StringBuilder generateHeader(StringBuilder builder)
	{
		return generateHeaderParameters(builder);
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return generateSourceParameters(builder);
	}
	
	public StringBuilder generateHeaderParameters(StringBuilder builder)
	{
		generateHeaderDefaultParameters(builder);
		
		return generateHeaderNovaParameters(builder);
	}
	
	public StringBuilder generateHeaderNovaParameters(StringBuilder builder)
	{
		for (int i = 0; i < node().getNumVisibleChildren(); i++)
		{
			if (i > 0 || node().getParameterOffset() > 0)
			{
				builder.append(", ");
			}
			
			Node child = node().getVisibleChild(i);
			
			getWriter(child).generateHeader(builder);
		}
		
		return builder;
	}
	
	public StringBuilder generateSourceParameters(StringBuilder builder)
	{
		generateSourceDefaultParameters(builder);
		
		for (int i = 0; i < node().getNumVisibleChildren(); i++)
		{
			if (i > 0 || node().getParameterOffset() > 0)
			{
				builder.append(", ");
			}
			
			Node child = node().getVisibleChild(i);
			
			getWriter(child).generateSource(builder);
		}
		
		return builder;
	}
	
	public StringBuilder generateHeaderDefaultParameters(StringBuilder builder)
	{
		for (int i = 0; i < node().getParameterOffset(); i++)
		{
			if (i > 0)
			{
				builder.append(", ");
			}
			
			Node child = node().getChild(i);
			
			getWriter(child).generateHeader(builder);
		}
		
		return builder;
	}
	
	public StringBuilder generateSourceDefaultParameters(StringBuilder builder)
	{
		for (int i = 0; i < node().getParameterOffset(); i++)
		{
			if (i > 0)
			{
				builder.append(", ");
			}
			
			Node child = node().getChild(i);
			
			getWriter(child).generateSource(builder);
		}
		
		return builder;
	}
}
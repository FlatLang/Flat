package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class PropertyMethodWriter extends BodyMethodDeclarationWriter
{
	public abstract PropertyMethod node();
	
	public StringBuilder generateSourceName(StringBuilder builder, String uniquePrefix)
	{
		return super.generateSourceName(builder, node().getMethodPrefix());
	}
	
	public StringBuilder generateHeader(StringBuilder builder)
	{
		if (node().isDisabled())
		{
			return builder;
		}
		
		return super.generateHeader(builder);
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		if (node().isDisabled())
		{
			return builder;
		}
		
		return super.generateSource(builder);
	}
	
	public StringBuilder generateSourcePrototype(StringBuilder builder)
	{
		if (node().isDisabled())
		{
			return builder;
		}
		
		return super.generateSourcePrototype(builder);
	}
}
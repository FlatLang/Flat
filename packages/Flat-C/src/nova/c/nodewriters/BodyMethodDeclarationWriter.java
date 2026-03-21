package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class BodyMethodDeclarationWriter extends NovaMethodDeclarationWriter
{
	public abstract BodyMethodDeclaration node();
	
	public StringBuilder generateHeader(StringBuilder builder)
	{
		if (node().isVisibilityValid())
		{
			if (node().getVisibility() == InstanceDeclaration.PRIVATE)
			{
				return builder;
			}
		}
		
		generateSourcePrototype(builder).append('\n');
		
		return builder;
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		generateSourceSignature(builder).append('\n');
		
		Scope scope = node().getScope();
		
		return getWriter(scope).generateSource(builder);
	}
}
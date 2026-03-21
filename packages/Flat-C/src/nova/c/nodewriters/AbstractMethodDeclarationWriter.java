package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class AbstractMethodDeclarationWriter extends NovaMethodDeclarationWriter
{
	public abstract AbstractMethodDeclaration node();
	
	public StringBuilder generateHeaderFragment(StringBuilder builder)
	{
		return super.generateSourcePrototype(builder);
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return builder;
	}
	
	public StringBuilder generateInterfaceVTableSource(StringBuilder builder)
	{
		return builder.append(0);
	}
}
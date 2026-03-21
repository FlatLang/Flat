package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class ExternalMethodDeclarationWriter extends MethodDeclarationWriter
{
	public abstract ExternalMethodDeclaration node();
	
	public StringBuilder generateSourceName(StringBuilder builder, String uniquePrefix)
	{
		return builder.append(node().alias);
	}
}
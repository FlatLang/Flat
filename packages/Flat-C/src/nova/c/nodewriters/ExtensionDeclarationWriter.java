package nova.c.nodewriters;

import net.fathomsoft.nova.tree.ExtensionDeclaration;
import net.fathomsoft.nova.tree.NovaMethodDeclaration;

public abstract class ExtensionDeclarationWriter extends ClassDeclarationWriter
{
	public abstract ExtensionDeclaration node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return super.generateSource(builder);
	}
}
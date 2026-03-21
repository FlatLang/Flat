package nova.c.nodewriters;

import net.fathomsoft.nova.tree.lambda.LambdaMethodDeclaration;

public abstract class LambdaMethodDeclarationWriter extends BodyMethodDeclarationWriter
{
	public abstract LambdaMethodDeclaration node();
	
	public StringBuilder generateClosureContext(StringBuilder builder)
	{
		return builder.append(node().contextDeclaration.getName());
	}
}
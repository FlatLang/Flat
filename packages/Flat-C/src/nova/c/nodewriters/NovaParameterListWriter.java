package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.lambda.LambdaMethodDeclaration;

public abstract class NovaParameterListWriter extends ParameterListWriter
{
	public abstract NovaParameterList node();
	
	public StringBuilder generateSourceParameters(StringBuilder builder)
	{
		super.generateSourceParameters(builder);
		
		if (node().returnParameters.getNumVisibleChildren() > 0)
		{
			builder.append(", ");
			
			getWriter(node().returnParameters).generateSourceParameters(builder);
		}
		
		if (node().getMethodDeclaration() instanceof LambdaMethodDeclaration)
		{
			builder.append(", ").append(((LambdaMethodDeclaration)node().getMethodDeclaration()).context.getName()).append("* ").append(ClosureVariableDeclaration.CONTEXT_VARIABLE_NAME);
		}
		if (node().getMethodDeclaration() instanceof ClosureVariable)
		{
			builder.append(", void*");
		}
		
		return builder;
	}
	
	public StringBuilder generateHeaderParameters(StringBuilder builder)
	{
		super.generateHeaderParameters(builder);
		
		if (node().returnParameters.getNumVisibleChildren() > 0)
		{
			builder.append(", ");
			
			getWriter(node().returnParameters).generateHeaderParameters(builder);
		}
		
		if (node().getMethodDeclaration() instanceof LambdaMethodDeclaration)
		{
			builder.append(", ").append(((LambdaMethodDeclaration)node().getMethodDeclaration()).context.getName()).append('*');
		}
		if (node().getMethodDeclaration() instanceof ClosureVariable)
		{
			builder.append(", void*");
		}
		
		return builder;
	}
}
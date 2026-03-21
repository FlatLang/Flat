package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class ClosureVariableWriter extends VariableWriter
{
	public abstract ClosureVariable node();
	
	public StringBuilder generateDeclaration(StringBuilder builder)
	{
		builder.append("nova_funcStruct*");
		
		return generateSourceName(builder).append(";\n");
	}
	
	public StringBuilder generateContextName()
	{
		return generateContextName(new StringBuilder());
	}
	
	public StringBuilder generateContextName(StringBuilder builder)
	{
		return generateSourceName(builder, "context");
	}
	
	public StringBuilder generateReferenceName()
	{
		return generateReferenceName(new StringBuilder());
	}
	
	public StringBuilder generateReferenceName(StringBuilder builder)
	{
		return generateSourceName(builder, "ref");
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return builder;//generateSourceFragment(builder);
	}
	
	public boolean isPackagedAsFunction()
	{
		return (node().parent.parent instanceof Return || node().getRootAccessNode() != null && node().getRootAccessNode().toValue().parent instanceof Assignment ||
				(node().parent instanceof MethodCallArgumentList && ((MethodCall)node().parent.parent).getCorrespondingParameter(node().getVisibleIndex()) instanceof ClosureDeclaration == false));
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		if (isPackagedAsFunction())
		{
			builder.append("nova_get_funcStruct3(");
			generateUseOutput(builder).append(", ");
			generateReferenceName(builder).append(", ");
			generateContextName(builder).append(")");
			
			return builder;
		}
		
		return super.generateSourceFragment(builder);
	}
}
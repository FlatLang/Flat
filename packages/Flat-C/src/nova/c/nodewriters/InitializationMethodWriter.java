package nova.c.nodewriters;

import net.fathomsoft.nova.tree.InitializationMethod;
import net.fathomsoft.nova.tree.ParameterList;
import net.fathomsoft.nova.tree.Scope;

public abstract class InitializationMethodWriter extends BodyMethodDeclarationWriter
{
	public abstract InitializationMethod node();
	
	@Override
	public StringBuilder generateSource(StringBuilder builder)
	{
		generateSourceSignature(builder).append(" {\n");
		
		getWriter(node().getScope()).generateSource(builder, false);
		
		builder.append("return ").append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append(";\n");
		builder.append("}");
		
		return builder;
	}
	
	@Override
	public String getFunctionMapPrefix()
	{
		return "";
	}
}
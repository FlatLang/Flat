package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;

public abstract class DefaultParameterInitializationWriter extends NodeWriter
{
	public abstract DefaultParameterInitialization node();
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		if (node().parameter instanceof ClosureDeclaration)
		{
			ClosureDeclaration decl = (ClosureDeclaration)node().parameter;
			Closure c = (Closure)decl.getDefaultValue();
			
			String param = getWriter(decl).generateSourceName().toString();
			String cast = getWriter(decl).generateTypeCast().toString();
			
			builder.append(param).append(" = ").append(cast).append("(").append(param).append(" == 0 ? ").append(cast);
			getWriter(c).generateSourceFunctionReference(builder).append(" : ").append(param).append(");\n");
			
			cast = "(void*)";
			String ref = getWriter(decl).generateObjectReferenceIdentifier(new StringBuilder()).toString();
			
			builder.append(ref).append(" = ").append(cast).append("(").append(ref).append(" == 0 ? ").append(cast);
			getWriter(c).generateClosureInstanceReference(builder).append(" : ").append(ref).append(");\n");
			
			String context = getWriter(decl).getContextName();
			
			builder.append(context).append(" = ").append(cast).append("(").append(context).append(" == 0 ? ").append(cast);
			getWriter(c).generateClosureContextReference(builder).append(" : ").append(context).append(");");
		}
		else
		{
			generateAssignment(builder, node().parameter, node().parameter.getDefaultValue());
		}
		
		return builder;
	}
	
	public StringBuilder generateAssignment(StringBuilder builder, Value param, Value defaultValue)
	{
		String use = getWriter(param).generateUseOutput().toString();

		builder.append(use).append(" = ");
		getWriter(param).generateTypeCast(builder).append('(');
		
		builder.append(use).append(" == ");
		
		if (param.isPrimitive())
		{
			builder.append("(int)(intptr_t)").append(ValueWriter.NULL_IDENTIFIER);
		}
		else
		{
			builder.append(0);
		}
		
		ClassDeclaration obj = node().getProgram().getClassDeclaration("nova/Object");
		
		String cast = !defaultValue.isPrimitive() ? getWriter(obj).generateTypeCast().toString() : "";
		
		builder.append(" ? ").append(cast).append(getWriter(defaultValue).generateSourceFragment()).append(" : ").append(cast).append(use);
		
		return builder.append(");");
	}
}
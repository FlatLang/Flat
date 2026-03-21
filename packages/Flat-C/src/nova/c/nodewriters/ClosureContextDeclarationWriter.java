package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;

public abstract class ClosureContextDeclarationWriter extends LocalDeclarationWriter
{
	public abstract ClosureContextDeclaration node();
	
	public StringBuilder generateDeclarationFragment(StringBuilder builder)
	{
		return builder.append(node().context.getName()).append("* ").append(node().getName());
	}
	
	public StringBuilder generateDefaultValue(StringBuilder builder)
	{
		builder.append("NOVA_MALLOC(sizeof(").append(node().context.getName()).append("))");
		
//		for (ClosureVariableDeclaration var : node().context)
//		{
//			builder.append(";\n");
//			
//			generateDeclaration(builder, var);
//		}
		
		return builder;
	}
}
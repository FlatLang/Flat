package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;

public abstract class ClosureContextWriter extends TypeListWriter
{
	public abstract ClosureContext node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return generateSourceFragment(builder).append(";\n");
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		builder.append("typedef struct\n");
		builder.append("{\n");
		
		for (ClosureVariableDeclaration var : node())
		{
			builder.append("/* ").append(var.originalDeclaration).append(" */ ");
			getWriter(var).generateSource(builder);
			
                /*boolean original = var.originalDeclaration.isValueReference();
                var.originalDeclaration.setIsValueReference(true);
                var.originalDeclaration.generateSource(builder);
                var.originalDeclaration.setIsValueReference(original);*/
			
			VariableDeclaration root = var.getRootDeclaration();
			
			if (root instanceof ClosureDeclaration)
			{
				builder.append("void* ");
				getWriter(root).generateObjectReferenceIdentifier(builder).append(";\n");
				
				builder.append("void* ");
				builder.append(getWriter(root).getContextName()).append(";\n");
			}
		}
		
		builder.append("} ").append(node().getName());
		
		return builder;
	}
}
package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;

public abstract class InstanceDeclarationWriter extends VariableDeclarationWriter
{
	public abstract InstanceDeclaration node();
	
	public StringBuilder writeInstanceAccess(StringBuilder builder)
	{
		return writeInstanceAccess(builder, false);
	}
	
	public StringBuilder writeInstanceAccess(StringBuilder builder, boolean pointer)
	{
		return writeInstanceAccess(builder, pointer, null);
	}
	
	public StringBuilder writeInstanceAccess(StringBuilder builder, boolean pointer, Node context)
	{
		boolean trait = context != null && context.getParentClass() instanceof Trait;
		
		if (trait)
		{
			builder.append('(').append(getWriter(node().getParentClass()).generateTypeCast());
		}
		if (pointer)
		{
			builder.append('(').append('*');
		}
		
		builder.append(ParameterList.OBJECT_REFERENCE_IDENTIFIER);
		
		if (pointer)
		{
			builder.append(')');
		}
		if (trait)
		{
			builder.append(')');
		}
		
		builder.append("->");
		
		if (node().getVisibility() == FieldDeclaration.PRIVATE)
		{
			builder.append("prv").append("->");
		}
		
		return builder;
	}
	
	public StringBuilder generateHeader(StringBuilder builder)
	{
		return generateHeaderFragment(builder).append(";\n");
	}
	
	public StringBuilder generateHeaderFragment(StringBuilder builder)
	{
		return generateModifiersSource(builder).append(' ').append(node().getName());
	}
}
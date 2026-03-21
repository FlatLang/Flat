package nova.c.nodewriters;

import net.fathomsoft.nova.tree.variables.FieldDeclaration;

public abstract class FieldDeclarationWriter extends InstanceDeclarationWriter
{
	public abstract FieldDeclaration node();
	
	public StringBuilder generateHeader(StringBuilder builder)
	{
		if (node().isStatic() && (node().getVisibility() == FieldDeclaration.PUBLIC || node().getVisibility() == FieldDeclaration.VISIBLE))
		{
			builder.append("extern ");
		}
		
		return generateSource(builder);
	}
}
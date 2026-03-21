package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class ExtensionVTableWriter extends VTableWriter
{
	public abstract ExtensionVTable node();
	
	public StringBuilder generateHeader(StringBuilder builder, boolean full)
	{
		TraitVTable table = node().getInterfaceVTable();
		
		getWriter(table).generateHeader(builder, full).append('\n');
		
		super.generateHeader(builder, full);
		
		return builder;
	}
	
	public StringBuilder generateExternDeclaration(StringBuilder builder)
	{
		return generateExternDeclaration(builder, false);
	}
	
	public StringBuilder generateExternDeclaration(StringBuilder builder, boolean full)
	{
		return builder.append("extern ").append(generateTypeName(full)).append(" ").append(generateSourceName(full)).append(";\n");
	}
	
	@Override
	public StringBuilder writeChildrenHeader(StringBuilder builder)
	{
		ClassDeclarationWriter clazz = getWriter(node().getProgram().getClassDeclaration("nova/meta/Class"));
		
		clazz.generateType(builder).append(" ").append(clazz.getClassInstanceVTableName()).append(";\n");
		
		return super.writeChildrenHeader(builder);
	}
	
	@Override
	public StringBuilder writeChildrenSource(StringBuilder builder, boolean full)
	{
		builder.append("0,\n");
		
		return super.writeChildrenSource(builder, full);
	}
}
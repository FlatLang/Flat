package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class TraitVTableWriter extends VTableWriter
{
	public static String MAIN_IDENTIFIER = "nova_interface_vtable";
	
	public abstract TraitVTable node();
	
	public StringBuilder generateHeader(StringBuilder builder, boolean full)
	{
		return builder;
	}
	
	public StringBuilder generateHeaderFragment(StringBuilder builder)
	{
		return generateType(builder).append(" ").append(TraitVTable.IDENTIFIER);
	}
	
	@Override
	public StringBuilder generateType(StringBuilder builder, boolean checkArray, boolean checkValueReference, boolean checkAllocatedOnHeap)
	{
		return super.generateType(builder, checkArray, checkValueReference, checkAllocatedOnHeap).append("*");
	}
	
	@Override
	public StringBuilder generateTypeName(StringBuilder builder)
	{
		return builder.append("nova_basic_function_type");//.append("nova_Interface_VTable");
	}
	
	public StringBuilder generateSource(StringBuilder builder, boolean full)
	{
		return builder;
	}
	
	@Override
	public StringBuilder generateSourceName(StringBuilder builder, boolean full)
	{
		return super.generateSourceName(builder, full).append("_itable");
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		return generateSourceFragment(builder, false);
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder, boolean full)
	{
		return generateInitializerValue(builder);///*full ? builder.append("&").append(generateSourceName(true)) : */builder.append(0);
	}
	
	public StringBuilder generateInitializerValue(StringBuilder builder)
	{
		NovaMethodDeclaration[] methods = node().getVirtualMethods();
		
		builder.append("(nova_basic_function_type[").append(methods.length).append("]){\n");
		
		for (NovaMethodDeclaration method : methods)
		{
			if (method != null)
			{
				builder.append("(void*)");
				getWriter(method).generateInterfaceVTableSource(builder);
			}
			else
			{
				builder.append(0);
			}
			
			builder.append(",\n");
		}
		
		return builder.append("}");
	}
	
	public StringBuilder generateDeclaration(StringBuilder builder)
	{
//		generateTypeName(builder).append("* ");
//		generateSourceName(builder, true).append(" = ");
//		generateInitializerValue(builder).append(";\n");
		
		return builder;
	}
}
package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.exceptionhandling.Exception;

import java.util.HashSet;

public abstract class StaticBlockWriter extends NodeWriter
{
	public abstract StaticBlock node();
	
	public StringBuilder generateHeader(StringBuilder builder, ClassDeclaration clazz)
	{
		builder.append("extern ");
		
		return generateMethodHeader(builder, clazz, false).append(';').append('\n');
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		Scope scope = node().getScope();
		
//		Node[] references = scope.getChildrenOfType(StaticClassReference.class);
//		
//		HashSet<String> called = new HashSet<>();
//		
//		for (Node ref : references)
//		{
//			StaticClassReference reference = (StaticClassReference)ref;
//			
//			TypeList<StaticBlock> blocks = reference.getTypeClass().getStaticBlockList();
//			
//			if (blocks.getNumVisibleChildren() > 0 && !called.contains(blocks.getParentClass().getName()))
//			{
//				called.add(blocks.getParentClass().getName());
//				
//				getWriter(blocks.getVisibleChild(0)).generateMethodName(builder, blocks.getParentClass()).append("();\n");
//			}
//		}
		
		for (ClassDeclaration c : node().getScope().getDependencies())
		{
			TypeList<StaticBlock> blocks = c.getStaticBlockList();

			if (blocks.getNumVisibleChildren() > 0)
			{
				getWriter(blocks.getVisibleChild(0)).generateMethodName(builder, blocks.getParentClass()).append("();\n");
			}
		}
		
		return getWriter(scope).generateSource(builder);
	}
	
	public StringBuilder generateInitedVariable(StringBuilder builder, ClassDeclaration clazz)
	{
		return generateMethodName(builder, clazz).append("_inited");
	}
	
	public StringBuilder generateMethodHeader(StringBuilder builder, ClassDeclaration clazz, boolean source)
	{
		builder.append("char ");
		generateInitedVariable(builder, clazz);
		
		if (source)
		{
			builder.append(" = 0");
		}
		
		builder.append(";\n");
		
		builder.append("void ");
		
		generateMethodName(builder, clazz);
		
		ParameterList params = node().getParameterList();
		
		builder.append('(').append(getWriter(params).generateSource()).append(')');
		
		return builder;
	}
	
	public static StringBuilder generateMethodName(StringBuilder builder, ClassDeclaration clazz)
	{
		return builder.append(getWriter(clazz).generateSourceName()).append(StaticBlock.C_PREFIX).append(StaticBlock.IDENTIFIER);
	}
	
	public static StringBuilder generateMethodCall(StringBuilder builder, ClassDeclaration clazz)
	{
		return generateMethodName(builder, clazz).append("()");
	}
}
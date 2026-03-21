package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.variables.Array;

public abstract class ArrayWriter extends VariableDeclarationWriter
{
	public abstract Array node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return generateSourceFragment(builder);
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		generateTypeCast(builder);
		//		builder.insert(builder.length() - 1, '*');
		
		if (node().getNumDimensions() > 1)
		{
			builder.append("nova_gen_array(");
		}
		
		builder.append("NOVA_MALLOC(sizeof(").append(generateTypeName()).append(")");
		
		Dimensions dim = node().getDimensions();
		
		//		dim.getVisibleChild(0).generateCSourceFragment(builder);
		//		for (int i = 0; i < dim.getNumVisibleChildren(); i++)
		//		{
		//			if (i > 0)
		//			{
		//				builder.append(" * ");
		//			}
		//			
		//			dim.getVisibleChild(i).generateCSourceFragment(builder);
		//		}
		getWriter(dim).generateSourceFragment(builder, " * ", "");
		
		//		builder.append(')');
		
		if (node().getNumDimensions() > 1)
		{
			builder.append("), (int[]) { ");
			
			for (int i = 0; i < dim.getNumVisibleChildren(); i++)
			{
				if (i > 0)
				{
					builder.append(", ");
				}
				
				Node child = dim.getVisibleChild(i);
				
				getWriter(child).generateSourceFragment(builder);
			}
			
			builder.append(" }, 0, ").append(dim.getNumVisibleChildren() - 1).append(", ");
			
			builder.append("sizeof(").append(generateTypeClassName()).append(')');
		}
		
		return builder.append(')');
	}
}
package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class LocalDeclarationWriter extends VariableDeclarationWriter
{
	public abstract LocalDeclaration node();
	
	@Override
	public StringBuilder generatePointers(StringBuilder builder, boolean checkArray, boolean checkValueReference, boolean checkAllocatedOnHeap)
	{
		if (checkAllocatedOnHeap && node().isAllocatedOnHeap())
		{
			builder.append("*");
		}
		
		return super.generatePointers(builder, checkArray, checkValueReference, checkAllocatedOnHeap);
	}
	
	public StringBuilder generateType(StringBuilder builder, boolean checkArray, boolean checkValueReference, boolean checkAllocatedOnHeap)
	{
		if (node().isImplicit())
		{
                /*builder.append("void*");
                
                if (checkValueReference && isValueReference())
                {
                    builder.append('*');
                }
                
                return builder;*/
			getWriter(node().implicitType).generateType(builder, checkArray, checkValueReference, checkAllocatedOnHeap);
			
			
			if (checkAllocatedOnHeap && node().isAllocatedOnHeap())
			{
				builder.append("*");
			}
			
			return builder;
		}
		
		return super.generateType(builder, checkArray, checkValueReference, checkAllocatedOnHeap);
	}
}
package nova.c.nodewriters;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.tree.*;

public abstract class VTableWriter extends IIdentifierWriter
{
	public abstract VTable node();

	public final StringBuilder generateTypedef(StringBuilder builder)
	{
		return generateTypedef(builder, false);
	}
	
	public StringBuilder generateTypedef(StringBuilder builder, boolean full)
	{
		builder.append("typedef struct ").append(generateTypeName(full)).append(' ').append(generateTypeName(full)).append(";");

		return builder;
	}
	
	@Override
	public StringBuilder generateType(StringBuilder builder, boolean checkArray, boolean checkValueReference, boolean checkAllocatedOnHeap)
	{
		return generateTypeName(builder).append("");
	}
	
	@Override
	public StringBuilder generateTypeName(StringBuilder builder)
	{
		return generateTypeName(builder, false);
	}
	
	public StringBuilder generateTypeName(boolean full)
	{
		return generateTypeName(new StringBuilder(), full);
	}
	
	public StringBuilder generateTypeName(StringBuilder builder, boolean full)
	{
		return getWriter(node().getParentClass()).generateSourceName(builder).append(/*full ? "_full" : */"").append("_VTable");
	}
	
	public StringBuilder generateSourceName(boolean full)
	{
		return generateSourceName(new StringBuilder(), full);
	}
	
	@Override
	public StringBuilder generateSourceName(StringBuilder builder, String uniquePrefix)
	{
		return generateSourceName(builder, false);
	}
	
	public StringBuilder generateSourceName(StringBuilder builder, boolean full)
	{
		return generateTypeName(builder, full).append("_val");
	}
	
	public final StringBuilder generateHeader(StringBuilder builder)
	{
		return generateHeader(builder, false);
	}
	
	public StringBuilder generateHeader(StringBuilder builder, boolean full)
	{
		builder.append("struct ").append(generateTypeName(full)).append("\n{\n");
		
		writeChildrenHeader(builder);
		
//		if (full)
		{
			NovaMethodDeclaration methods[] = node().getVirtualMethods();
			
			generateVirtualMethodDeclarations(builder, methods);
		}
		
		builder.append("}").append(";");
		
		return builder;
	}
	
	public StringBuilder writeChildrenHeader(StringBuilder builder)
	{
		for (int i = 0; i < node().getNumChildren(); i++)
		{
			Node child = node().getChild(i);
			
			getWriter(child).generateHeaderFragment(builder).append(";\n");
		}
		
		return builder;
	}
	
	public final StringBuilder generateSource(StringBuilder builder)
	{
		return generateSource(builder, false);
	}
	
	public StringBuilder generateSource(StringBuilder builder, boolean full)
	{
		if (full)
		{
			for (int i = 0; i < node().getNumChildren(); i++)
			{
				Node child = node().getChild(i);
				
				if (child instanceof TraitVTable)
				{
					getWriter((TraitVTable)child).generateDeclaration(builder).append("\n");
				}
			}
		}
		
		generateTypeName(builder, full).append(" ").append(generateSourceName(full)).append(" =\n{\n");
		
		writeChildrenSource(builder, full);
		
//		if (full)
		{
			NovaMethodDeclaration methods[] = node().getVirtualMethods();
			
			generateVirtualMethodValues(builder, methods);
		}
		
		builder.append("};\n");
		
		return builder;
	}
	
	public StringBuilder writeChildrenSource(StringBuilder builder, boolean full)
	{
		for (int i = 0; i < node().getNumChildren(); i++)
		{
			Node child = node().getChild(i);
			
			if (child instanceof TraitVTable)
			{
				getWriter((TraitVTable)child).generateSourceFragment(builder, full);
			}
			else
			{
				getWriter(child).generateSourceFragment(builder);
			}
			
			builder.append(",\n");
		}
		
		return builder;
	}
	
	/**
	 * Generate the virtual method declarations that declares the names
	 * of the methods that are used in the class and its ancestors.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @param methods The methods to add the identifiers from.
	 * @return The StringBuilder with the appended data.
	 */
	public StringBuilder generateVirtualMethodDeclarations(StringBuilder builder, NovaMethodDeclaration methods[])
	{
		for (NovaMethodDeclaration method : methods)
		{
			generateVirtualMethodDeclaration(builder, method);
		}
		
		return builder;
	}
	
	/**
	 * Generate the virtual method declaration that declares the name
	 * of the given method.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @param method The method to add the identifier from.
	 * @return The StringBuilder with the appended data.
	 */
	public StringBuilder generateVirtualMethodDeclaration(StringBuilder builder, NovaMethodDeclaration method)
	{
		VirtualMethodDeclaration virtual = method.getVirtualMethod();
		ParameterList params = method.getParameterList();
		
		return getWriter(method).generateType(builder).append(" (*").append(getWriter(virtual).generateVirtualMethodName(method)).append(")(").append(getWriter(params).generateHeader()).append(");\n");//getWriter(virtual).generateSource(builder);
	}
	
	/**
	 * Add the vtable values that point to the correct virtual method
	 * implementation for the specified class.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @param methods The methods to add the references to.
	 * @return The StringBuilder with the appended data.
	 */
	public StringBuilder generateVirtualMethodValues(StringBuilder builder, NovaMethodDeclaration methods[])
	{
		for (NovaMethodDeclaration method : methods)
		{
			if (method != null)
			{
				VirtualMethodDeclaration virtual = method.getVirtualMethod();
				
//				builder.append("(").append(getWriter(virtual).generateFunctionReferenceTypeName(new StringBuilder())).append(")");
//				builder.append("&");
				
				//				method.generateVirtualMethodName(builder);
				if (method instanceof AbstractMethodDeclaration)
				{
					method = virtual;
				}
				
				builder.append("(void*)");
				getWriter(method).generateSourceName(builder);
			}
			else
			{
				builder.append(0);
			}
			
			builder.append(",\n");
		}
		
		return builder;
	}
}
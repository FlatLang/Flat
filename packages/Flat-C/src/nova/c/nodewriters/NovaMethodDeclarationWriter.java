package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class NovaMethodDeclarationWriter extends MethodDeclarationWriter
{
	public abstract NovaMethodDeclaration node();
	
	public StringBuilder generateInterfaceVTableSource(StringBuilder builder)
	{
		NovaMethodDeclaration root = node().getVirtualMethod();//.getRootDeclaration();
		NovaParameterList params = root.getParameterList();
		
//		builder.append("(").append(getWriter(root).generateType()).append("(*)(").append(getWriter(params).generateHeader()).append("))");
		
		return generateSourceName(builder);
	}
	
	public StringBuilder generateClosureContext(StringBuilder builder)
	{
		return builder.append(NovaMethodDeclarationWriter.NULL_IDENTIFIER);
	}
	
	public StringBuilder generateSourceNativeName(StringBuilder builder, boolean declaration)
	{
		super.generateSourceNativeName(builder, declaration);
		
		if (!declaration && node().isOverloaded())
		{
			for (Parameter param : node().getParameterList())
			{
				builder.append('_');
				
				String location = null;
				
				if (param.isExternalType())
				{
					location = param.getType();
				}
				else
				{
					ClassDeclaration clazz = param.getTypeClass();
					
					if (clazz != null)
					{
						location = clazz.getFileDeclaration().getPackage().getLocation().replace('/', '_');
						
						if (location.length() > 0)
						{
							location += '_';
						}
						
						if (clazz.isPrimitiveOverload())
						{
							location += getWriter(clazz).getPrimitiveOverloadPrefix();
						}
						
						location += clazz.getName();
					}
					else
					{
						location = "void";
					}
				}
				
				builder.append('_');
				
				if (param.isPrimitiveArray())
				{
					builder.append("Array" + param.getArrayDimensions() + "d_");
				}
				
				builder.append(location);
			}
		}
		
		return builder;
	}
	
	public StringBuilder generateInterfaceVTableHeader(StringBuilder builder)
	{
		VirtualMethodDeclaration virtual = node().getVirtualMethod();
		NovaParameterList params = node().getParameterList();
		
		return generateType(builder).append(" (*").append(getWriter(virtual).generateVirtualMethodName()).append(")(").append(getWriter(params).generateHeader()).append(");\n");
	}
	
	public StringBuilder generateFunctionPointer(StringBuilder builder)
	{
		return generateFunctionPointer(builder, null);
	}
	
	public final StringBuilder generateFunctionPointer(StringBuilder builder, String prefix)
	{
		return generateFunctionPointer(builder, prefix, false);
	}
	
	public StringBuilder generateFunctionPointer(StringBuilder builder, String prefix, boolean voidReference)
	{
		return generateFunctionPointer(builder, prefix, voidReference, false);
	}
	
	public StringBuilder generateFunctionPointer(StringBuilder builder, String prefix, boolean voidReference, boolean typeOnly)
	{
		NovaParameterList params = node().getParameterList();
		
		generateType(builder).append(" (*").append(typeOnly ? "" : generateSourceName(prefix)).append(")(");

//		if (voidReference)
//		{
//			builder.append("void*");
//			getWriter(params).generateHeaderNovaParameters(builder);
//		}
//		else
		{
			getWriter(params).generateHeader(builder);
		}
		
		return builder.append(")");
	}
	
	public StringBuilder generateFunctionPointerType(StringBuilder builder)
	{
		return generateFunctionPointerType(builder, null);
	}
		
	public StringBuilder generateFunctionPointerType(StringBuilder builder, String prefix)
	{
		return generateFunctionPointerType(builder, prefix, false);
	}
	
	public StringBuilder generateFunctionPointerType(StringBuilder builder, String prefix, boolean voidReference)
	{
		return generateFunctionPointer(builder, prefix, voidReference, true);
	}
	
	/**
	 * Generate the identifier that will be used to call the method.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @return The updated StringBuilder.
	 */
	public StringBuilder generateMethodCall(StringBuilder builder)
	{
		if (node().isVirtual())
		{
			VirtualMethodDeclaration virtual = node().getVirtualMethod();
			
			return getWriter(virtual).generateVirtualMethodName(builder);
		}
		
		return super.generateMethodCall(builder);
	}
	
	public StringBuilder generateSourceName(StringBuilder builder, String uniquePrefix)
	{
		return generateSourceName(builder, uniquePrefix, true);
	}
	
	@Override
	public StringBuilder generateIdentifierSourceName(StringBuilder builder, String uniquePrefix)
	{
		return generateSourceName(builder, uniquePrefix, true);
	}
	
	public String getFunctionMapPrefix()
	{
		String output = "";
		
		if (node().getParentClass().isPropertyTrue("functionMap"))
		{
			output += "functionMap";
		}
		
		return output;
	}
	
	public StringBuilder generateSourceName(StringBuilder builder, String uniquePrefix, boolean outputOverload)
	{
		String postPrefix = "";
		
		NovaMethodDeclaration corresponding = (NovaMethodDeclaration)node().getProperty("correspondingFunction");
		
		if (corresponding instanceof Constructor)
		{
			postPrefix += node().getParameterList().getReferenceParameter().getType();
		}
		
		return generateSourceName(builder, uniquePrefix, outputOverload ? node().overloadID : -1, postPrefix);
	}
	
	public StringBuilder generateSourceName(StringBuilder builder, String uniquePrefix, int overloadId, String postPrefix)
	{
		uniquePrefix = uniquePrefix == null ? "" : uniquePrefix;
		
		if (node().getParentClass().getField(node().getName()) != null)
		{
			uniquePrefix += "func";
		}
		
		uniquePrefix += getFunctionMapPrefix();
		uniquePrefix += postPrefix;
		
		if (node().isPrimitiveOverload())
		{
			if (uniquePrefix.length() > 0)
			{
				uniquePrefix += "_";
			}
			
			uniquePrefix += getPrimitiveOverloadPrefix();
		}
		
		if (overloadId == -1)
		{
			return super.generateIdentifierSourceName(builder, uniquePrefix.length() == 0 ? null : uniquePrefix);
		}
		else
		{
			uniquePrefix += overloadId;
		}
		
		return super.generateIdentifierSourceName(builder, uniquePrefix);
	}
	
	public String getPrimitiveOverloadPrefix()
	{
		String prefix = "";
		
		NovaParameterList params = node().getParameterList();
		
		for (int i = 0; i < params.getNumParameters(); i++)
		{
			Parameter param = params.getParameter(i);
			
			if (param.isPrimitive())
			{
				prefix += getWriter(param).generateTypeName() + "_";
			}
			else
			{
				prefix += param.getType() + "_";
			}
		}
		
		if (node().getType() == null)
		{
			prefix += "void";
		}
		else if (node().isPrimitive())
		{
			prefix += generateType();
		}
		else
		{
			prefix += node().getType();
		}
		
		return prefix;
	}
}
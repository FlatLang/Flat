package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

import java.io.IOException;
import java.io.PrintWriter;

public abstract class VirtualMethodDeclarationWriter extends BodyMethodDeclarationWriter
{
	public abstract VirtualMethodDeclaration node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		generateSourceSignature(builder);

            /*
            if (getType() == null)
            {
                builder.append("{}");
            }
            else
            {
                builder.append("{return 0;}");
            }
            */

		builder.append("\n{\n");

		if (node().getType() != null)
		{
			builder.append("return ");
		}
		
		if (node().getParentClass() instanceof Trait)
		{
			builder.append("((");
			generateFunctionPointerType(builder);
			builder.append(")");
		}
		
		Parameter ref = node().getOriginalParameterList().getObjectReference();

		getWriter(ref).generateSourceFragment(builder).append("->");

		builder.append(VTable.IDENTIFIER).append("->");

		if (node().getParentClass() instanceof Trait)
		{
			builder.append(TraitVTable.IDENTIFIER).append("[");
		}

		String call = node().getName() + "(";

		for (int i = 0; i < node().getParameterList().getNumVisibleChildren(); i++)
		{
			if (i > 0)
			{
				call += ", ";
			}

			call += node().getParameterList().getVisibleChild(i).getName();
		}

		call += ")";

		MethodCall output = MethodCall.decodeStatement(node().getScope(), call, node().getLocationIn().asNew(), true, true, node());

		if (node().getParentClass() instanceof Trait)
		{
			generateVirtualMethodIndex(builder).append("])");
		}
		else
		{
			generateVirtualMethodName(builder);
		}
		
		getWriter(output.getArgumentList()).generateSourceFragment(builder);

		return builder.append(";\n}\n");
		
//		return generateFunctionReferenceTypeName(builder).append(" ").append(generateSourceName()).append(";\n");
	}
	
	public StringBuilder generateFunctionReferenceTypeName(StringBuilder builder)
	{
		return generateSourceName(builder, "type");
	}
	
	public String getFunctionType()
	{
		return generateFunctionPointer(new StringBuilder(), "type", true).toString();
	}
	
	public StringBuilder generateIndexDefinition(StringBuilder builder, int index)
	{
		builder.append("#define ");
		generateVirtualMethodIndex(builder).append(" ").append(index);
		
		return builder.append("\n");
	}
	
	//	@Override
//	public StringBuilder generateHeader(StringBuilder builder)
//	{
//		builder.append("typedef ");
//		builder.append(getFunctionType()).append(";\n");
//		
//		builder.append("extern ").append(generateFunctionReferenceTypeName(new StringBuilder())).append(" ").append(generateSourceName()).append(";\n");
//		
//		return builder;
//	}
	
	public StringBuilder generateSourceName(StringBuilder builder, String uniquePrefix)
	{
		return generateVirtualMethodName(builder, uniquePrefix);
	}
	
	/**
	 * Get the identifier for the virtual abstract method in the vtable.
	 *
	 * @return The identifier for the virtual method in the vtable.
	 */
	public StringBuilder generateVirtualMethodName()
	{
		return generateVirtualMethodName(node().base);
	}
	
	public StringBuilder generateVirtualMethodName(NovaMethodDeclaration reference)
	{
		return generateVirtualMethodName(new StringBuilder(), reference);
	}
	
	/**
	 * Get the identifier for the virtual abstract method in the vtable.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @return The identifier for the virtual method in the vtable.
	 */
	public StringBuilder generateVirtualMethodName(StringBuilder builder)
	{
		return generateVirtualMethodName(builder, (String)null);
	}
	
	public StringBuilder generateVirtualMethodName(StringBuilder builder, String prefix)
	{
		return generateVirtualMethodName(builder, prefix, node().base);
	}
	
	public StringBuilder generateVirtualMethodName(StringBuilder builder, NovaMethodDeclaration reference)
	{
		return generateVirtualMethodName(builder, null, reference);
	}
		
	public StringBuilder generateVirtualMethodName(StringBuilder builder, String prefix, NovaMethodDeclaration reference)
	{
		prefix = "virtual" + (prefix != null ? "_" + prefix : "");
		String postPrefix = "";
		
		if (node().base instanceof PropertyMethod)
		{
			prefix += "_" + ((PropertyMethod)node().base).getMethodPrefix();
		}
		if (reference != null)
		{
			if (reference.getParentClass().isPropertyTrue("functionMap"))
			{
				NovaMethodDeclaration corresponding = (NovaMethodDeclaration)reference.getProperty("correspondingFunction");
				
				if (corresponding instanceof Constructor)
				{
					postPrefix += corresponding.getName();
				}
//				if (reference.getOverloadID() != -1)
//				{
//					prefix += reference.getOverloadID();
//				}
			}
		}
		
		return generateSourceName(builder, prefix, reference != null ? reference.overloadID : node().overloadID, postPrefix);
	}
	
	public StringBuilder generateVirtualMethodIndex(StringBuilder builder)
	{
		return generateVirtualMethodName(builder, "index");
	}
	
	public java.io.Writer writeVTableDeclaration(java.io.Writer writer) throws IOException
	{
		writer.write(generateType().toString());
		writer.write(" (*");
		writer.write(generateVirtualMethodName().toString());
		writer.write(")(");
		writer.write(getWriter(node().getParameterList()).generateHeader().toString());
		writer.write(");\n");
		
		return writer;
	}
}
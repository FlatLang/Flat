package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import nova.c.engines.CCodeGeneratorEngine;

public abstract class ParameterWriter extends LocalDeclarationWriter
{
	public abstract Parameter node();
	
	public StringBuilder generateTypeName(StringBuilder builder)
	{
		// Upsize the type in case a pointer is passed
		if (node().isOptional() && node().getType() != null && (node().getTypeClass().isOfType("nova/primitive/number/Char") || node().getTypeClass().isOfType("nova/primitive/Bool")))
		{
			return builder.append("int");
		}
		
		if (node().isObjectReference() && node().getType() != null)
		{
			return generateTypeClassName(builder);
		}
		/*else if (getTypeClass() != null && getTypeClass().equals(getProgram().getClassDeclaration(Nova.getClassLocation("Number"))))
		{
			return builder.append("long_long");
		}*/
		
		return super.generateTypeName(builder);
	}
	
	@Override
	public StringBuilder generateIdentifierSourceName(StringBuilder builder, String uniquePrefix)
	{
		if (this instanceof ClosureDeclarationWriter)
		{
			return super.generateIdentifierSourceName(builder, uniquePrefix);
		}
		
		if (uniquePrefix != null)
		{
			builder.append(uniquePrefix);
		}
		
		if (CCodeGeneratorEngine.KEYWORDS.contains(node().getName()))
		{
			builder.append("_");
		}
		
		return builder.append(node().getName());
	}
	
//	@Override
//	public StringBuilder generateSourceName(StringBuilder builder, String uniquePrefix)
//	{
//		return generateIdentifierSourceName(builder, uniquePrefix);
//	}
	
	public StringBuilder generateHeader(StringBuilder builder)
	{
		return generateModifiersSource(builder);
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return generateHeader(builder).append(' ').append(generateSourceName());
	}
}
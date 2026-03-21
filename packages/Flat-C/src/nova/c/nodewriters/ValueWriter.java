package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.util.SyntaxUtils;

public abstract class ValueWriter extends NodeWriter
{
	public static final String NULL_IDENTIFIER = "nova_null";//"(*nova_null_ptr)";
	
	public abstract Value node();
	
	public StringBuilder generateHeader(StringBuilder builder)
	{
		return generateHeaderFragment(builder);
	}
	
	public StringBuilder generateHeaderFragment(StringBuilder builder)
	{
		return generateSourceFragment(builder);
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return generateSourceFragment(builder);
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		return generateType(builder);
	}
	
	public StringBuilder generateSourcePrefix(StringBuilder builder)
	{
		return builder;
	}
	
	/**
	 * Generate the C null representation for the given value type.
	 *
	 * @return The generated null output.
	 */
	public final StringBuilder generateNullOutput()
	{
		return generateNullOutput(new StringBuilder());
	}
	
	/**
	 * Generate the C null representation for the given value type.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @return The generated null output.
	 */
	public StringBuilder generateNullOutput(StringBuilder builder)
	{
		return generateTypeCast(builder).append(ValueWriter.NULL_IDENTIFIER);
	}
	
	public StringBuilder generateArgumentOutput(StringBuilder builder)
	{
		return generateSourceFragment(builder);
	}
	
	public final StringBuilder generateTypeClassName()
	{
		return generateTypeClassName(new StringBuilder());
	}
	
	public StringBuilder generateTypeClassName(StringBuilder builder)
	{
		String type = node().getType();
		
		if (node().isGenericType())
		{
			type = node().getGenericReturnType();
		}
		
		if (node().isExternalType() || SyntaxUtils.isExternalPrimitiveType(type))
		{
			builder.append(type);
		}
		else
		{
			FileDeclaration file = node().getReferenceFile();//getFileDeclaration();
			
			/*if (node() instanceof Identifier && !isGenericType())
			{
				file = ((Identifier)node()).getDeclaringClass().getFileDeclaration();
			}*/
			
			ClassDeclaration clazz = SyntaxUtils.getImportedClass(file, type);
			
			if (clazz != null)
			{
				getWriter(clazz).generateSourceName(builder);
			}
			else
			{
				builder.append(type);
			}
		}
		
		return builder;
	}
	
	/**
	 * Generate the C syntax for the type of the specified Value.
	 *
	 * @return The C syntax for the type of the Value.
	 */
	public final StringBuilder generateType()
	{
		return generateType(new StringBuilder());
	}
	
	/**
	 * Generate the C syntax for the type of the specified Value.
	 *
	 * @param builder The StringBuider to append the data to.
	 * @return The C syntax for the type of the Value.
	 */
	public final StringBuilder generateType(StringBuilder builder)
	{
		return generateType(builder, true);
	}
	
	/**
	 * Generate the C syntax for the type of the specified Value.
	 *
	 * @param builder The StringBuider to append the data to.
	 * @param checkArray Whether or not to check if the type is an array.
	 * @return The C syntax for the type of the Value.
	 */
	public final StringBuilder generateType(StringBuilder builder, boolean checkArray)
	{
		return generateType(builder, checkArray, true);
	}
	
	public final StringBuilder generateType(StringBuilder builder, boolean checkArray, boolean checkValueReference)
	{
		return generateType(builder, checkArray, checkValueReference, true);
	}
	
	public StringBuilder generateType(StringBuilder builder, boolean checkArray, boolean checkValueReference, boolean checkAllocatedOnHeap)
	{
		if (node().getTypeObject() instanceof FunctionType)
		{
			FunctionType type = (FunctionType)node().getTypeObject();
			
			builder.append("/*");
			
			return getWriter(type.closure).generateType(builder, checkArray, checkValueReference, checkAllocatedOnHeap).append("*/nova_funcStruct*");
		}
		
		generateTypeName(builder);
		
		return generatePointers(builder, checkArray, checkValueReference, checkAllocatedOnHeap);
	}
	
	public final StringBuilder generatePointers(StringBuilder builder, boolean checkArray, boolean checkValueReference)
	{
		return generatePointers(builder, checkArray, checkValueReference, true);
	}
	
	public StringBuilder generatePointers(StringBuilder builder, boolean checkArray, boolean checkValueReference, boolean checkAllocatedOnHeap)
	{
		if (node().isReference())
		{
			builder.append('&');
		}
		else if (node().isPointer())
		{
			builder.append('*');
		}
		else if (node().isDoublePointer())
		{
			builder.append("**");
		}
		if (checkValueReference && node().isValueReference())
		{
			builder.append('*');
		}
		if (checkArray && node().isPrimitiveArray())
		{
			builder.append(node().generateArrayText());
		}
		
		return builder;
	}
	
	public StringBuilder generateTypeName()
	{
		return generateTypeName(new StringBuilder());
	}
	
	public StringBuilder generateTypeName(StringBuilder builder)
	{
		String type = node().getType();
		
		if (node().isGenericType())
		{
			type = node().getGenericReturnType();
		}
		
		if (type == null)
		{
			builder.append("void");
		}
		else if (type.equals("long"))
		{
			builder.append("long_long");
		}
		else if (type.equals("bool"))
		{
			builder.append("char");
		}
		else if (type.equals("byte"))
		{
			builder.append("char");
		}
		else if (SyntaxUtils.isPrimitiveType(type) && (node().getDataType() == Value.VALUE || (node().isReturnParameter() && node().getDataType() == Value.POINTER)))
		{
			builder.append(SyntaxUtils.getPrimitiveExternalType(type));
		}
		else
		{
			generateTypeClassName(builder);
		}
		
		return builder;
	}
	
	/**
	 * Generate a String representing a type cast for the specified Value
	 * in C syntax.
	 *
	 * @return The StringBuilder with the appended data.
	 */
	public final StringBuilder generateTypeCast()
	{
		return generateTypeCast(new StringBuilder());
	}
	
	/**
	 * Generate a String representing a type cast for the specified Value
	 * in C syntax.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @return The StringBuilder with the appended data.
	 */
	public final StringBuilder generateTypeCast(StringBuilder builder)
	{
		return generateTypeCast(builder, true, true);
	}
	
	public final StringBuilder generateTypeCast(StringBuilder builder, boolean checkArray, boolean checkValueReference)
	{
		return generateTypeCast(builder, checkArray, checkValueReference, true);
	}
	
	public final StringBuilder generateTypeCast(StringBuilder builder, boolean checkArray, boolean checkValueReference, boolean checkAllocatedOnHeap)
	{
		return builder.append('(').append(generateType(new StringBuilder(), checkArray, checkValueReference, checkAllocatedOnHeap)).append(')').append(generatePointerToValueConversion());
	}
	
	public StringBuilder generatePointerToValueConversion()
	{
		return generatePointerToValueConversion(new StringBuilder());
	}
	
	public StringBuilder generatePointerToValueConversion(StringBuilder builder)
	{
		return generatePointerToValueConversion(builder, node());
	}
	
	public StringBuilder generatePointerToValueConversion(Value required)
	{
		return generatePointerToValueConversion(new StringBuilder(), required);
	}
	
	public StringBuilder generatePointerToValueConversion(StringBuilder builder, Value required)
	{
		if (node().isFunctionType())
		{
			return builder;
		}
		
		boolean ptr = false;
		
		if (/*isGenericType() && */node() instanceof Accessible)
		{
			Accessible ref = ((Accessible)node()).getReferenceNode();
			
			ptr = ref != null && node().getArrayDimensions() == 0 && (required.isOriginallyGenericType() || node().isOriginallyGenericType()) && ref.toValue().isPrimitiveGenericTypeWrapper();
		}
		else
		{
			Node base = node().getBaseNode();
			
			if (base instanceof Value)
			{
				ptr = ((Value)base).isPrimitiveGenericTypeWrapper();
			}
		}
		
		if (ptr)
		{
			builder.append("(intptr_t)");
		}
		
		return builder;
	}
	
	/**
	 * Generate the representation of when the value node is being used
	 * in action.
	 *
	 * @return What the method call looks like when it is being used in
	 * 		action
	 */
	public final StringBuilder generateUseOutput()
	{
		return generateUseOutput(new StringBuilder());
	}
	
	/**
	 * Generate the representation of when the value node is being used
	 * in action.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @return What the method call looks like when it is being used in
	 * 		action
	 */
	public StringBuilder generateUseOutput(StringBuilder builder)
	{
		return generateType(builder);
	}
	
	public StringBuilder generateArrayAccess()
	{
		return generateArrayAccess(new StringBuilder());
	}
	
	public StringBuilder generateArrayAccess(StringBuilder builder)
	{
		if (node().arrayAccess != null)
		{
			return getWriter(node().arrayAccess).generateSourceFragment(builder);
		}
		
		return builder;
	}
}
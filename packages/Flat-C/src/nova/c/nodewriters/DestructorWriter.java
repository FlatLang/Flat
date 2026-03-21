package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.exceptionhandling.Exception;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.tree.variables.InstanceFieldList;

public abstract class DestructorWriter extends BodyMethodDeclarationWriter
{
	public abstract Destructor node();
	
	@Override
	public String getFunctionMapPrefix()
	{
		return "";
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		generateSourceSignature(builder).append('\n').append('{').append('\n');
		
		nullChecker(builder).append('\n');
		
		deleteData(builder).append('\n');
		
		for (int i = 0; i < node().getNumVisibleChildren(); i++)
		{
			Node child = node().getVisibleChild(i);
			
			if (child != node().getParameterList())
			{
				getWriter(child).generateSource(builder);
			}
		}
		
		builder.append("NOVA_FREE(").append('*').append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append(");").append('\n');
		
		builder.append('}').append('\n');
		
		return builder;
	}
	
	/**
	 * Generate the code needed to check if a variable is null before
	 * trying to free its members.
	 *
	 * @return The code needed to check whether a variable is null or not.
	 */
	private StringBuilder nullChecker(StringBuilder builder)
	{
		builder.append("if (!*").append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append(')').append('\n');
		builder.append('{').append('\n');
		builder.append("return;").append('\n');
		builder.append('}').append('\n');
		
		return builder;
	}
	
	/**
	 * Generate the code needed to delete each member of the class.
	 *
	 * @return The code needed to delete each member of the class.
	 */
	private StringBuilder deleteData(StringBuilder builder)
	{
		ClassDeclaration  classDeclaration = node().getParentClass();
		InstanceFieldList privateFields    = classDeclaration.getFieldList().getPrivateFieldList();
		
		for (int i = 0; i < privateFields.getNumChildren(); i++)
		{
			FieldDeclaration field = (FieldDeclaration)privateFields.getChild(i);
			
			generateFreeFieldSource(builder, field).append('\n');
		}
		
		if (classDeclaration.containsNonStaticPrivateData())
		{
			generateFreeMemberSource(builder, "prv").append('\n');
		}
		
		InstanceFieldList publicFields = classDeclaration.getFieldList().getPublicFieldList();
		
		for (int i = 0; i < publicFields.getNumChildren(); i++)
		{
			FieldDeclaration field = (FieldDeclaration)publicFields.getChild(i);

//			field.generateFreeOutput(builder);
			generateFreeFieldSource(builder, field).append('\n');
		}
		
		return builder;
	}
	
	/**
	 * Generate a String for the code used to free memory of an allocated
	 * field variable located within the current class.
	 *
	 * @param field The node that contains the information of the field.
	 * @return The generated String for the code.
	 */
	private StringBuilder generateFreeFieldSource(StringBuilder builder, FieldDeclaration field)
	{
		if (field.isPrimitiveType() || field.isExternalType() || field.isGenericType())
		{
			if (!field.isPrimitive())
			{
				//builder.append("NOVA_FREE(").append(field.generateVariableUseOutput(true)).append(");");builder.append("printf(\"Aft2.\");");
			}
		}
		else
		{
			if (field.isPrimitiveArray() || field.getTypeObject() instanceof FunctionType)
			{
				//				void nova_free_array(void** array, int* dimensionSizes, int dimension, int dimensions, del_function function);
				//				builder.append("nova_free_array(" + field.generateUseOutput(new StringBuilder(), true) + ", );");
				builder.append("NOVA_FREE(" + getWriter(field).generateUseOutput(new StringBuilder(), true) + ");");
			}
			else if (field.getTypeClass().getDestructor() != null)
			{
				Destructor dest = field.getTypeClass().getDestructor();
				
				getWriter(dest).generateSourceName(builder).append('(').append('&');
				
				getWriter(field).generateUseOutput(builder, true).append(");");
			}
		}
		
		return builder;
	}
	
	/**
	 * Generate a String for the code used to free memory of an allocated
	 * member variable with the given name.
	 *
	 * @param name The name of the variable to delete.
	 * @return The generated String for the code.
	 */
	private StringBuilder generateFreeMemberSource(StringBuilder builder, String name)
	{
		return builder.append("NOVA_FREE((*").append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append(")->").append(name).append(");");
	}
	
	//	/**
	//	 * @see net.fathomsoft.nova.tree.MethodDeclaration#generateSourcePrototype( node, StringBuilder)
	//	 */
	//	@Override
	//	public StringBuilder generateSourcePrototype( node, StringBuilder builder)
	//	{
	//		return generateSourceSignature(builder).append(";");
	//	}
	//	
	//	/**
	//	 * @see net.fathomsoft.nova.tree.MethodDeclaration#generateSourceSignature( node, StringBuilder)
	//	 */
	//	@Override
	//	public StringBuilder generateSourceSignature( node, StringBuilder builder)
	//	{
	//		ClassDeclaration classDeclaration = getParentClass();
	//		
	//		generateType(builder).append(' ');
	//		generateSourceName(builder).append('(').append(getParameterList().generateSource()).append(')');
	//		
	//		return builder;
	//	}
}
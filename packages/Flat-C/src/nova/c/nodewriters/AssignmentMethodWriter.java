package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.exceptionhandling.Exception;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.tree.variables.FieldList;
import net.fathomsoft.nova.tree.variables.InstanceFieldList;

public abstract class AssignmentMethodWriter extends BodyMethodDeclarationWriter
{
	public abstract AssignmentMethod node();
	
	@Override
	public String getFunctionMapPrefix()
	{
		return "";
	}
	
	public StringBuilder generateHeader(StringBuilder builder)
	{
		return generateSourcePrototype(builder).append('\n');
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		generateSourceSignature(builder).append('\n');
		
		builder.append('{').append('\n');
		
		generateFieldDefaultAssignments(builder);
		
		for (int i = 0; i < node().getNumVisibleChildren(); i++)
		{
			Node child = node().getVisibleChild(i);
			
			getWriter(child).generateSource(builder);
		}
		
		Scope scope = node().getScope();
		
		getWriter(scope).generateSource(builder, false);
		
		builder.append('}').append('\n');
		
		return builder;
	}
	
	public StringBuilder generateMethodCall(StringBuilder builder)
	{
		return generateMethodCall(builder, false);
	}
	
	/**
	 * @param cast Whether or not to add an explicit type cast for the
	 * 		object reference identifier.
	 */
	public StringBuilder generateMethodCall(StringBuilder builder, boolean cast)
	{
		super.generateMethodCall(builder).append("(");
		
		if (cast)
		{
			ClassDeclaration clazz = node().getParentClass();
			
			builder.append('(').append(getWriter(clazz).generateType()).append(')');
		}
		
		builder.append(ParameterList.OBJECT_REFERENCE_IDENTIFIER);
		
		return builder.append(");\n");
	}
	
	/**
	 * node() method returns a String that contains the code needed to
	 * assign the default null value to each uninitialized/uninstantiated
	 * field variables.
	 *
	 * @param builder The StringBuilder to append the assignments to.
	 * @return The appended buffer.
	 */
	private StringBuilder generateFieldDefaultAssignments(StringBuilder builder)
	{
		FieldList fields = node().getParentClass().getFieldList();
		
		generateFieldDefaultAssignments(builder, fields.getPublicFieldList());
		generateFieldDefaultAssignments(builder, fields.getPrivateFieldList());
		
		return builder;
	}
	
	/**
	 * node() method returns a String that contains the code needed to
	 * assign the default null value to each uninitialized/uninstantiated
	 * field variables.
	 *
	 * @param builder The StringBuilder to append the assignments to.
	 * @param fields The list of fields to assign default values to.
	 * @return The appended buffer.
	 */
	private StringBuilder generateFieldDefaultAssignments(StringBuilder builder, InstanceFieldList fields)
	{
		for (int i = 0; i < fields.getNumChildren(); i++)
		{
			FieldDeclaration field = (FieldDeclaration)fields.getChild(i);
			
			if (!field.isExternal() && !field.isExternalType())
			{
				getWriter(field).generateUseOutput(builder).append(" = ");
				
				if (!field.isPrimitiveType() && !field.isExternalType())
				{
					getWriter(field).generateNullOutput(builder);
				}
				else
				{
					builder.append(0);
				}
				
				builder.append(';').append('\n');
			}
		}
		
		return builder;
	}
}
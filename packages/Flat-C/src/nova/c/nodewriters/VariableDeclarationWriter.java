package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.annotations.ThreadLocalAnnotation;
import net.fathomsoft.nova.tree.exceptionhandling.Exception;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;

public abstract class VariableDeclarationWriter extends IIdentifierWriter
{
	public abstract VariableDeclaration node();
	
	public StringBuilder generateHeader(StringBuilder builder)
	{
		return generateSource(builder);
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return generateDeclarationFragment(builder).append(";\n");
	}
	
	@Override
	public StringBuilder generateSourceName(StringBuilder builder, String uniquePrefix)
	{
		return generateIdentifierSourceName(builder, uniquePrefix);
	}
	
	public StringBuilder generateIdentifierSourceName(StringBuilder builder, String uniquePrefix)
	{
		return super.generateSourceName(builder, uniquePrefix);
	}
	
	public StringBuilder generateSourceClosureVariableName(StringBuilder builder, String uniquePrefix)
	{
		if (node().requiresHeapAllocation())
		{
			return builder.append("(*").append(getWriter(node().closureVariableDeclarations.get(0)).getHeapVariableName()).append(")");
		}
		
		return generateIdentifierSourceName(builder, uniquePrefix);
	}
	
	/**
	 * Generate a String with the declaration modifiers and the name of
	 * the variable declared.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @return The appended StringBuilder.
	 */
	public StringBuilder generateDeclarationFragment(StringBuilder builder)
	{
		return generateModifiersSource(builder).append(' ').append(generateSourceName());
	}
	
	public StringBuilder generatePostAssignment(StringBuilder builder)
	{
		return builder;
	}
	
	public StringBuilder generateClosureArguments(StringBuilder builder, Variable context, NovaMethodDeclaration method)
	{
		generateClosureInstanceReference(builder, context);
		
		builder.append(", ");
		generateClosureContextReference(builder, method);
		
		return builder;
	}
	
	public StringBuilder generateClosureInstanceReference(StringBuilder builder, Variable context)
	{
		if (context.getRootReferenceNode() instanceof ClassDeclaration == false)
		{
			Accessible root = context.getRootReferenceNode();
			
			getWriter(root).generateArgumentReference(builder, context);
		}
		else
		{
			builder.append(ClosureDeclarationWriter.NULL_IDENTIFIER);//method.getParameterList().getObjectReference().generateNullOutput(builder);
		}
		
		return builder;
	}
	
	public StringBuilder generateClosureContextReference(StringBuilder builder, NovaMethodDeclaration method)
	{
		return getWriter(method).generateClosureContext(builder);
	}
	
	public StringBuilder generateObjectReferenceIdentifier(StringBuilder builder)
	{
		return builder.append(generateSourceName("ref"));
	}
	
	public StringBuilder generateContextParameter()
	{
		return generateContextParameter(new StringBuilder());
	}
	
	public String getContextName()
	{
		return generateSourceName(ClosureVariableDeclaration.CONTEXT_VARIABLE_NAME).toString();
	}
	
	public StringBuilder generateContextParameter(StringBuilder builder)
	{
		return builder.append("void* ").append(getContextName());
	}
	
	/**
	 * Generate the modifiers for the specified Variable.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * Person people[] = new Person[42];</pre></blockquote>
	 * In the above Variable declaration, the modifiers are the type of
	 * the variable ("<u><code>Person</code></u>") and the type of
	 * declaration is an array.<br>
	 * node() also checks if the type requires a pointer.
	 *
	 * @param builder The StringBuilder to append to.
	 * @return The appended StringBuilder.
	 */
	public StringBuilder generateModifiersSource(StringBuilder builder)
	{
		if (node().isVolatile())//!(node() instanceof Parameter || node() instanceof FieldDeclaration))
		{
			builder.append(node().getVolatileText()).append(' ');
		}
		if (node().containsAnnotationOfType(ThreadLocalAnnotation.class, false, false))
		{
			builder.append("__thread ");
		}
		
		generateType(builder);
		
		return builder;
	}
	
	public StringBuilder generateDefaultValue(StringBuilder builder)
	{
		if (node().isPrimitive())
		{
			builder.append(0);
		}
		else
		{
			generateTypeCast(builder, true, true, false).append(ValueWriter.NULL_IDENTIFIER);
		}
		
		return builder;
	}
	
	/**
	 * Generate a String for the code used to free memory of the
	 * specified variable.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @return The generated String for the code.
	 */
	public StringBuilder generateFreeOutput(StringBuilder builder)
	{
		if (node().isConstant())
		{
			return builder;
		}
		
		if (node().isPrimitiveType() || node().isExternalType())
		{
			if (!node().isPrimitive())
			{
				builder.append("NOVA_FREE(");
				
				generateUseOutput(builder, true).append(");\n");
			}
		}
		else
		{
			Destructor destructor = node().getTypeClass().getDestructor();
			
			getWriter(destructor).generateSourceName(builder).append('(').append('&');
			
			generateUseOutput(builder, true).append(");\n");
		}
		
		return builder;
	}
}
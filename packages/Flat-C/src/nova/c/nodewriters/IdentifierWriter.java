package nova.c.nodewriters;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.variables.*;

public abstract class IdentifierWriter extends ValueWriter implements AccessibleWriter
{
	public abstract Identifier node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return generateSourceFragment(builder).append(";\n");
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		if (node().isGenericType() && node().doesAccess())
		{
			Value value = node().getReturnedNode();
			
			getWriter(value).generateTypeCast(builder);
			
			builder.append('(');
		}
		
		if (node().isSpecialFragment())
		{
			getWriter(node()).generateSpecialFragment(builder);
		}
		else
		{
			generateUseOutput(builder).append(getWriter(node()).generateChildrenSourceFragment());
		}
		
		if (node().isGenericType() && node().doesAccess())
		{
			builder.append(')');
		}
		
		return builder;
	}
	
	public boolean isPackagedAsFunction()
	{
		return false;
	}
	
	/**
	 * Generate the representation of when the variable is being used, in
	 * action, rather than being declared.<br>
	 * <br>
	 * For example:<br>
	 * <blockquote><pre>
	 * Person p;
	 * p.getName();</pre></blockquote>
	 * The first line shows the declaration of the Variable. The second
	 * line demonstrates a "variable use" for the "p" variable.
	 * Essentially, the "variable use" output is exactly what it says,
	 * what the variable looks like when it is being used to do something.
	 *
	 * @return What the variable looks like when it is being used to do
	 * 		something.
	 */
	public StringBuilder generateUseOutput(StringBuilder builder)
	{
		return generateUseOutput(builder, false);
	}
	
	/**
	 * Generate the representation of when the variable is being used, in
	 * action, rather than being declared.<br>
	 * <br>
	 * For example:<br>
	 * <blockquote><pre>
	 * Person p;
	 * p.getName();</pre></blockquote>
	 * The first line shows the declaration of the Variable. The second
	 * line demonstrates a "variable use" for the "p" variable.
	 * Essentially, the "variable use" output is exactly what it says,
	 * what the variable looks like when it is being used to do something.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @param pointer Whether or not the variable is to be accessed by a
	 * 		pointer.
	 * @return What the variable looks like when it is being used to do
	 * 		something.
	 */
	public StringBuilder generateUseOutput(StringBuilder builder, boolean pointer)
	{
		return generateUseOutput(builder, pointer, true);
	}
	
	public StringBuilder generateUseOutput(StringBuilder builder, boolean pointer, boolean checkAccesses)
	{
		//		if (!isSpecialFragment())
		//		{
		//			builder.append(generateDataTypeOutput());
		//		}
		
		InstanceDeclaration field = null;
		
		Node parent = node().getParent();
		
		if (parent instanceof Array)
		{
			VariableDeclaration n = SyntaxTree.findDeclaration(parent.getParent(), node().getName());
			
			if (n instanceof FieldDeclaration)
			{
				field = (FieldDeclaration)n;
			}
		}
		else if (node() instanceof Variable)
		{
			VariableDeclaration decl = ((Variable)node()).getDeclaration();
			
			if (decl instanceof FieldDeclaration)
			{
				field = (InstanceDeclaration)decl;
			}
		}
		else if (node() instanceof FieldDeclaration)
		{
			field = (FieldDeclaration)node();
		}
		
		if (field != null && !field.isExternal())
		{
			if (!field.isStatic())
			{
				Value ref = (Value)node().getReferenceNode();
				
				if (ref.getTypeClass().isContainingClass(node()) || ref instanceof ObjectReference)
				{
					if (!node().isAccessed())
					{
						getWriter(field).writeInstanceAccess(builder, pointer, node());
					}
					//					else
					//					{
					//						builder.append("->");
					//					}
				}
			}
		}
		
		if (node().isAllocatedOnHeap())
		{
//			builder.append("(*");
		}
		
		if (node().isValueReference())
		{
			builder.append("(");
			
//			if (node().requiresHeapAllocation())
//			{
//				builder.append("*");
//			}
			
			generateSourcePrefix(builder);
		}
//		Nova.debuggingBreakpoint(node().getName().equals("list") && node().getParentMethod() != null && node().getParentMethod().getName().equals("getChildFiles"));
//		if (!node().isValueReference() && node().requiresHeapAllocation() && node() instanceof Variable && ((Variable)node()).declaration.closureVariableDeclaration != null &&
//			(node().getParentMethod() == null || node().getParentMethod() == ((Variable)node()).declaration.closureVariableDeclaration.getParentMethod()))
//		{
//			builder.append("(*").append(getWriter(((Variable)node()).declaration.closureVariableDeclaration).getHeapVariableName()).append(")");
//		}
//		else
		{
			generateSourceName(builder);
		}
		
		if (node().isValueReference())
		{
			builder.append(')');
		}
		if (node().isAllocatedOnHeap())
		{
//			builder.append(')');
		}
		
		generateArrayAccess(builder);
		
		return builder;
	}
	
        /*public GenericTypeArgument getGenericTypeArgumentFromParameter(GenericTypeParameter param)
        {
            return getGenericTypeArgumentFromParameter(param.getType());
        }*/
	
	public String getName()
	{
		return node().getName();
	}
	
	/**
	 * Generate a variable name that will be used to keep the variables
	 * in their own "namespace" per-say.
	 *
	 * @return The name of the variable that will be output to the C
	 * 		source output.
	 */
	public final StringBuilder generateSourceName()
	{
		return generateSourceName(new StringBuilder());
	}
	
	/**
	 * Generate a variable name that will be used to keep the variables
	 * in their own "namespace" per-say.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @return The name of the variable that will be output to the C
	 * 		source output.
	 */
	public final StringBuilder generateSourceName(StringBuilder builder)
	{
		return generateSourceName(builder, null);
	}
	
	/**
	 * Generate a variable name that will be used to keep the variables
	 * in their own "namespace" per-say.
	 *
	 * @param uniquePrefix The unique identifying prefix to prepend to the
	 * 		name output.
	 * @return The name of the variable that will be output to the C
	 * 		source output.
	 */
	public final StringBuilder generateSourceName(String uniquePrefix)
	{
		return generateSourceName(new StringBuilder(), uniquePrefix);
	}
	
	/**
	 * Generate a variable name that will be used to keep the variables
	 * in their own "namespace" per-say.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @param uniquePrefix The unique identifying prefix to prepend to the
	 * 		name output.
	 * @return The name of the variable that will be output to the C
	 * 		source output.
	 */
	public StringBuilder generateSourceName(StringBuilder builder, String uniquePrefix)
	{
		String name = getName();
		
		if (node().doesForceOriginalName())
		{
			return builder.append(node().getProperty("externalName"));
		}
		
		VariableDeclaration existing = null;
		
		if (node().isDeclaration())
		{
			existing = (VariableDeclaration)node();
		}
		else if (node() instanceof Variable)
		{
			existing = ((Variable)node()).getDeclaration();
		}
		else
		{
			existing = SyntaxTree.findDeclaration(node().getParent(), name, false);
			
			if (existing == null)
			{
				Nova.debuggingBreakpoint(true);
				SyntaxMessage.error("Unable to find declaration for variable '" + name + "'", node());
			}
		}
		
		if (!(existing instanceof LocalDeclaration && existing instanceof Parameter == false))
		{
			ClassDeclaration clazz = existing.getParentClass(true);
			
			getWriter(clazz).generateSourceName(builder).append('_');
		}
		
		if (existing instanceof LocalDeclaration && existing instanceof Parameter == false)
		{
			LocalDeclaration declaration = (LocalDeclaration)existing;
			
			builder.append('l').append(declaration.getScopeID()).append('_');
		}
		
		if (uniquePrefix != null)
		{
			builder.append(uniquePrefix).append('_');
		}
		
		if (!node().isInstance())
		{
			builder.append("static_");
		}
		
		builder.append(Nova.LANGUAGE_NAME).append("_");
		
		return builder.append(name);
	}
}
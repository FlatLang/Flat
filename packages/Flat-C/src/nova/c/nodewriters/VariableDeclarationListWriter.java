package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.tree.variables.VariableDeclarationList;

import java.util.Optional;

public abstract class VariableDeclarationListWriter extends ListWriter
{
	public abstract VariableDeclarationList node();
	
	/**
	 * Generate the output needed to free the variables after they are
	 * finished with.
	 *
	 * @return The String output of the variables being freed.
	 */
	public StringBuilder generateFreeVariablesOutput(StringBuilder builder)
	{
		for (int i = 0; i < node().getNumChildren(); i++)
		{
			VariableDeclaration variable = (VariableDeclaration)node().getChild(i);
			
			getWriter(variable).generateFreeOutput(builder);
		}
		
		return builder;
	}
	
	public StringBuilder generateHeader(StringBuilder builder)
	{
		return builder;
	}
	
	public StringBuilder generateInheritedContext(StringBuilder builder, ClosureContextDeclaration declaration)
	{
		ClosureContext context = declaration.context;
		
		for (ClosureVariableDeclaration c : context)
		{
			if (c.originalDeclaration instanceof ClosureVariableDeclaration)
			{
				builder.append(declaration.getName()).append("->");
				getWriter(c).generateSourceName(builder).append(" = context->");
				getWriter(c.originalDeclaration).generateSourceName(builder).append(";\n");
				
				if (c.getRootDeclaration() instanceof ClosureDeclaration)
				{
					getWriter(c).generateClosureContextAssignments(builder, (ClosureDeclaration)c.getRootDeclaration());
				}
			}
		}
		
		return builder;
	}
	
	public StringBuilder generateDeclaration(StringBuilder builder, LocalDeclaration child)
	{
		getWriter(child).generateDeclarationFragment(builder).append(" = ");
		
		if (child.isAllocatedOnHeap())
		{
			getWriter(child).generateTypeCast(builder).append("NOVA_MALLOC(sizeof(");
			getWriter(child).generateType(builder, true, true, false).append("));\n");
			
			getWriter(child).generateUseOutput(builder).append(" = ");
		}
		
		getWriter(child).generateDefaultValue(builder).append(";\n");
		
		getWriter(child).generatePostAssignment(builder);
		
		if (child instanceof ClosureContextDeclaration)
		{
			generateInheritedContext(builder, (ClosureContextDeclaration)child);
		}
		else
		{
			Optional<ClosureVariableDeclaration> first = child.closureVariableDeclarations.stream().findFirst();
			
			for (ClosureVariableDeclaration c : child.closureVariableDeclarations)
			{
				if (c == first.get())
				{
					getWriter(c).generateAssignment(builder);
				}
				else
				{
					getWriter(c).generateAssignment(builder, first.get());
				}
			}
		}
		
		return builder;
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		for (ClosureContextDeclaration child : node().closureContextDeclarations)
		{
			generateDeclaration(builder, child);
		}
		
		if (node().parent.parent instanceof NovaMethodDeclaration)
		{
			NovaMethodDeclaration method = (NovaMethodDeclaration)node().parent.parent;
			
			if (method instanceof Constructor == false)
			{
				if (!method.isPrimitiveOverload() && method instanceof InitializationMethod)
				{
					method = ((InitializationMethod)method).constructor;
				}
				
				for (Parameter param : method.getParameterList())
				{
					Optional<ClosureVariableDeclaration> first = param.closureVariableDeclarations.stream().findFirst();
					
					for (ClosureVariableDeclaration c : param.closureVariableDeclarations)
					{
						if (c == first.get())
						{
							getWriter(c).generateAssignment(builder);
						}
						else
						{
							getWriter(c).generateAssignment(builder, first.get());
						}
					}
				}
			}
		}
		
		for (int i = 0; i < node().getNumChildren(); i++)
		{
			LocalDeclaration child = (LocalDeclaration)node().getChild(i);
			
			generateDeclaration(builder, child);
		}
		
		if (node().getNumChildren() > 0)
		{
			builder.append('\n');
		}
		
		return builder;
	}
}
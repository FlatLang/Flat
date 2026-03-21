package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.generics.GenericTypeArgument;
import net.fathomsoft.nova.tree.generics.GenericTypeArgumentList;
import net.fathomsoft.nova.tree.variables.ObjectReference;
import net.fathomsoft.nova.tree.variables.Variable;

public abstract class VariableWriter extends IdentifierWriter
{
	public abstract Variable node();
	
	/*@Override
	public StringBuilder generateTypeName(StringBuilder builder)
	{
		return getWriter(node().getDeclaration()).generateTypeName(builder);
	}*/
	
	public StringBuilder generateSourcePrefix(StringBuilder builder)
	{
		super.generateSourcePrefix(builder);
		
		if (node().declaration instanceof ClosureVariableDeclaration)
		{
			ClosureVariableDeclaration decl = (ClosureVariableDeclaration)node().declaration;
			
			if (decl.getRootDeclaration() instanceof ClosureDeclaration == false)
			{
				builder.append("*");
			}
			
			builder.append(ClosureVariableDeclaration.CONTEXT_VARIABLE_NAME).append("->");
		}
		else
		{
			builder.append("*");
		}
		
		return builder;
	}
	
	@Override
	public StringBuilder generateSourceName(StringBuilder builder, String uniquePrefix)
	{
		if (node().isValueReference() || node().declaration instanceof Parameter)
		{
			return getWriter(node().getDeclaration()).generateIdentifierSourceName(builder, uniquePrefix);
		}
		
		return getWriter(node().getDeclaration()).generateSourceClosureVariableName(builder, uniquePrefix);
	}
	
	public StringBuilder generateVolatileDereference(StringBuilder builder)
	{
		if (node().isVolatile() && !node().isPrimitive())
		{
			generateTypeCast(builder);
			//builder.append("*(").append(generateTypeName()).append(")&");
		}
		
		return builder;
	}
	
	public StringBuilder generateArgumentOutput(StringBuilder builder)
	{
		generateVolatileDereference(builder);
		
		super.generateArgumentOutput(builder);
		
		generateExtraArguments(builder);
		
		return builder;
	}
	
	public StringBuilder generateExtraArguments(StringBuilder builder)
	{
		if (node().getDeclaration() instanceof ClosureDeclaration && !isPackagedAsFunction())
		{
			builder.append(", ");
			
			ClosureDeclaration declaration = (ClosureDeclaration)node().getDeclaration();
			
			if (declaration.getParent() instanceof NovaParameterList)
			{
				builder.append(getWriter(declaration).getContextName());
			}
			else
			{
				getWriter(declaration).generateClosureArguments(builder, node(), node().getParentMethod());
			}
		}
		
		return builder;
	}
	
	@Override
	public StringBuilder generateUseOutput(StringBuilder builder, boolean pointer, boolean checkAccesses)
	{
		if (node().declaration instanceof ClassInstanceDeclaration)
		{
			return builder.append("vtable->").append(ClassDeclarationWriter.getClassInstanceVTableName());
		}
		
		boolean trait = node().getParentClass() instanceof Trait && node().declaration instanceof ReferenceParameter && node().doesAccess();
		
		if (trait)
		{
			builder.append('(').append(getWriter(node().getAccessedNode().getDeclaringClass()).generateTypeCast());
		}
		
		super.generateUseOutput(builder, pointer, checkAccesses);
		
		if (trait)
		{
			builder.append(')');
		}
		
		if (node().doesAccess() && node().getAccessedNode() instanceof MethodCall == false)
		{
			InstanceDeclaration instance = node().getTypeClass().getField(node().getAccessedNode().getName());
			
			if (instance == null)
			{
//				instance = node().getTypeClass().getClosureVariable(node().getAccessedNode().getName());
			}
			
			if (instance != null && instance.getVisibility() == InstanceDeclaration.PRIVATE)
			{
				builder.append("->prv");
			}
		}
		
		return builder;
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		super.generateSourceFragment(builder);
		
		generateObjectReferenceIdentifier(builder);
		
		return builder;
	}
	
	public StringBuilder generateObjectReferenceIdentifier(StringBuilder builder)
	{
		if (node().getDeclaration() instanceof ClosureDeclaration && node().getParent() instanceof ArgumentList)
		{
			ClosureDeclaration declaration = (ClosureDeclaration)node().getDeclaration();
			
			builder.append(", ");
			getWriter(declaration).generateObjectReferenceIdentifier(builder);
		}
		
		return builder;
	}
	
	public String generateGenericType()
	{
		GenericTypeArgumentList args = node().getGenericTypeArgumentList();
		
		if (args != null && args.getNumVisibleChildren() > 0)
		{
			String s = GenericCompatible.GENERIC_START;
			
			for (int i = 0; i < args.getNumVisibleChildren(); i++)
			{
				if (i > 0)
				{
					s += ", ";
				}
				
				//GenericTypeArgument arg = getGenericTypeArgumentFromParameter(args.getVisibleChild(i).getType());
				GenericTypeArgument arg = args.getVisibleChild(i);
				
				s += arg.generateNovaInput(new StringBuilder(), true, node());
			}
			
			s += GenericCompatible.GENERIC_END;
			
			return s;
		}
		
		return "";
	}
}
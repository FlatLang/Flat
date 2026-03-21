package nova.c.nodewriters;

import net.fathomsoft.nova.tree.ClosureContext;
import net.fathomsoft.nova.tree.ClosureDeclaration;
import net.fathomsoft.nova.tree.ClosureVariableDeclaration;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;

public abstract class ClosureVariableDeclarationWriter extends VariableDeclarationWriter
{
	public abstract ClosureVariableDeclaration node();
	
	@Override
	public StringBuilder generateTypeName(StringBuilder builder)
	{
		return getWriter(node().originalDeclaration).generateTypeName(builder);
	}
	
	public String getHeapVariableName()
	{
		return "heap" + ((ClosureContext)node().getAncestorOfType(ClosureContext.class)).id + "_" + node().getIndex();
	}
	
	@Override
	public StringBuilder generateSourceName(StringBuilder builder, String uniquePrefix)
	{
		return generateIdentifierSourceName(builder, uniquePrefix);
	}
	
	public StringBuilder generateLeftAssignment(StringBuilder builder)
	{
		return builder.append(((ClosureContext)node().parent).declaration.getName()).append("->");
	}
	
	public StringBuilder generateDeclarationValue(StringBuilder builder)
	{
		//Variable v = var.generateUsableVariable(node(), Location.INVALID);
		
		if (node().originalDeclaration instanceof ClosureVariableDeclaration)
		{
			builder.append("context->");
		}
		else if (!node().originalDeclaration.isAllocatedOnHeap() && node().originalDeclaration instanceof ClosureDeclaration == false)
		{
			builder.append('&');
		}
		
		return getWriter(node().originalDeclaration).generateIdentifierSourceName(builder, null);
	}

	public StringBuilder generateValueAssignment(StringBuilder builder) {
		return builder.append("*").append(getHeapVariableName()).append(" = ").append(getWriter(node().originalDeclaration).generateSourceName()).append(";\n");
	}
	
	public StringBuilder generateAssignment(StringBuilder builder)
	{
		return generateAssignment(builder, null);
	}
	
	public StringBuilder generateAssignment(StringBuilder builder, ClosureVariableDeclaration reference)
	{
		if (reference != null && reference.getClosureContext().declaration.getParentMethod() != node().getClosureContext().declaration.getParentMethod())
		{
			return builder;
		}
		
		VariableDeclaration root = node().getRootDeclaration();
		
		boolean heap = node().requiresHeapAllocation && root instanceof ClosureDeclaration == false;
		
		String heapName = null;
		
		if (reference != null)
		{
			if (heap)
			{
				getWriter(node().originalDeclaration).generateType(builder).append("* ").append(getHeapVariableName()).append(" = ").append(getWriter(reference).getHeapVariableName()).append(";\n");
			}
		}
		else if (heap)
		{
			heapName = getHeapVariableName();
			
			getWriter(node().originalDeclaration).generateType(builder).append("* ").append(heapName).append(" = (")
				.append(getWriter(node().originalDeclaration).generateType()).append("*)NOVA_MALLOC(sizeof(").append(getWriter(node().originalDeclaration).generateType()).append("));\n");
		}
		
		generateLeftAssignment(builder);
		generateSourceName(builder).append(" = ");
		
		if (heap)
		{
			if (reference != null)
			{
				builder.append(getWriter(reference).getHeapVariableName());
			}
			else
			{
				builder.append(heapName);
			}
		}
		else
		{
			generateDeclarationValue(builder);
		}
		
		builder.append(";\n");
		
		if (root instanceof ClosureDeclaration)
		{
			generateClosureContextAssignments(builder, (ClosureDeclaration)root);
		}
		
		return builder;
	}
	
	public StringBuilder generateClosureContextAssignments(StringBuilder builder, ClosureDeclaration declaration)
	{
		String context = node().originalDeclaration != declaration ? "context->" : "";
		
		generateLeftAssignment(builder);
		getWriter(declaration).generateObjectReferenceIdentifier(builder).append(" = ").append(context);
		getWriter(declaration).generateObjectReferenceIdentifier(builder).append(";\n");
		
		generateLeftAssignment(builder);
		builder.append(getWriter(declaration).getContextName()).append(" = ").append(context);
		builder.append(getWriter(declaration).getContextName()).append(";\n");
		
		return builder;
	}
	
	public StringBuilder generateClosureContextValues(StringBuilder builder, ClosureDeclaration declaration, String afterObjectReference, String afterClosureContext)
	{
		String context = node().originalDeclaration != declaration ? "context->" : "";
		
		builder.append(context);
		getWriter(declaration).generateObjectReferenceIdentifier(builder).append(afterObjectReference);
		
		builder.append(context);
		builder.append(getWriter(declaration).getContextName()).append(afterClosureContext);
		
		return builder;
	}
	
	@Override
	public StringBuilder generateType(StringBuilder builder, boolean checkArray, boolean checkValueReference, boolean checkAllocatedOnHeap)
	{
		super.generateType(builder, checkArray, checkValueReference, checkAllocatedOnHeap);
		
		if (checkAllocatedOnHeap && node().requiresHeapAllocation)
		{
//			builder.append("*");
		}
		
		return builder;
	}
}
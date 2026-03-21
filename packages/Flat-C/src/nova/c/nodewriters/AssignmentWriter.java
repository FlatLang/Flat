package nova.c.nodewriters;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.tree.*;

public abstract class AssignmentWriter extends ValueWriter
{
	public abstract Assignment node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return generateSourceFragment(builder).append(";\n");
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		if (node().containsProperty("methodCall"))
		{
			builder.append("(");
			
			FunctionType type = (FunctionType)node().getAssignmentNode().getReturnedNode().getNovaTypeValue(node()).getTypeObject();
			
			ClosureDeclaration closure = type.closure;
			
			getWriter(closure).generateTypeCast(builder).append("(");
		}
		
		if (!node().getAssignedNodeValue().isFunctionType())
		{
			if (node().getAssignedNodeValue().getDataType() == Value.POINTER &&
				node().getAssignmentNode().getReturnedNode().getDataType() == Value.VALUE ||
				node().getAssignedNodeValue().getDataType() == Value.DOUBLE_POINTER &&
					node().getAssignmentNode().getReturnedNode().getDataType() == Value.POINTER)
			{
				builder.append('*');
			}
		}
		
		Value assignee = node().getAssigneeNode();
		
		getWriter(assignee).generateSourceFragment(builder).append(" = ").append(generateAssignmentSource());
		
//		if (node().getAssignedNodeValue() instanceof Variable && node().getAssignedNode().declaration instanceof ClosureVariable)
//		{
//			builder.append(";\n");
//			
//			ClosureVariable var = (ClosureVariable)node().getAssignedNode().declaration;
//			ClosureDeclaration closure = ((ClosureCompatible)((Variable)node().getAssignmentNode().getReturnedNode()).declaration).getClosureDeclaration();
//			
//			getWriter(node().getAssignedNode().getRootAccessNode()).generateSourceUntil(builder, "->", node().getAssignedNode());
//			builder.append(getWriter(var).generateReferenceName()).append(" = ").append(getWriter(closure).generateObjectReferenceIdentifier(new StringBuilder())).append(";\n");
//			
//			getWriter(node().getAssignedNode().getRootAccessNode()).generateSourceUntil(builder, "->", node().getAssignedNode());
//			builder.append(getWriter(var).generateContextName()).append(" = ").append(getWriter(closure).getContextName());
//		}
		
		if (node().containsProperty("methodCall"))
		{
			builder.append(")->func)");
			
			generateMethodCallSource(builder);
		}
		
		return builder;
	}
	
	/**
	 * Generate the assignment's right hand value C output.
	 *
	 * @return The assignment's right hand value C output.
	 */
	private StringBuilder generateAssignmentSource()
	{
		return generateAssignmentSource(new StringBuilder());
	}
	
	private StringBuilder generateMethodCallSource(StringBuilder builder)
	{
		MethodCall call = (MethodCall)node().getProperty("methodCall");
		
		getWriter(call.getArgumentList()).generateSourceFragment(builder);
		
		return builder;
	}
	
	/**
	 * Generate the assignment's right hand value C output.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @return The assignment's right hand value C output.
	 */
	private StringBuilder generateAssignmentSource(StringBuilder builder)
	{
		Value assignment = node().getAssignmentNode();
		
		String assignmentType = assignment.getReturnedNode().getType();
		String assignedType = node().getAssignedNodeValue().getType();
		
		boolean sameType = assignedType == null || assignmentType.equals(assignedType);
		
		if (sameType && assignment instanceof Accessible)
		{
			MethodCall call = (MethodCall)((Accessible)assignment).getLastAccessedOfType(MethodCall.class, false);
			
			if (call != null)
			{
				sameType = !call.isVirtual();
			}
		}
		
		if (!sameType)
		{
			Value assigned = node().getAssignedNodeValue();
			Value returned = assignment.getReturnedNode();
			
			getWriter(assigned).generateTypeCast(builder, true, false).append(getWriter(returned).generatePointerToValueConversion(returned)).append('(');
		}
		
		builder.append(assignment.generateDataTypeOutput(node().getAssignedNodeValue().getDataType())).append(getWriter(assignment).generateSourceFragment());
		
		if (!sameType)
		{
			builder.append(')');
		}
		
		return builder;
	}
}
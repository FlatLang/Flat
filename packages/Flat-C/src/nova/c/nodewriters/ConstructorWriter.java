package nova.c.nodewriters;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.util.Stack;

public abstract class ConstructorWriter extends BodyMethodDeclarationWriter
{
	public abstract Constructor node();
	
	public StringBuilder generateTypeName(StringBuilder builder)
	{
		return generateTypeClassName(builder);
	}
	
	@Override
	public String getFunctionMapPrefix()
	{
		return "";
	}
	
	public StringBuilder generateHeader(StringBuilder builder)
	{
		if (node().isVisibilityValid())
		{
			if (node().getVisibility() == InstanceDeclaration.PRIVATE)
			{
				return builder;
			}
		}
		
		if (node().isReference())
		{
			SyntaxMessage.error("Constructor cannot return a reference", node());
		}
		
		return generateSourcePrototype(builder).append('\n');
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		generateSourceSignature(builder).append('\n');
		
		builder.append('{').append('\n');
		
		ClassDeclaration classDeclaration = node().getParentClass();
		
		if (classDeclaration.containsNonStaticData() || classDeclaration.containsVirtualMethods())
		{
			ClassDeclaration clazz = node().getTypeClass();
			
			builder.append("CCLASS_NEW(").append(getWriter(clazz).generateSourceName()).append(", ").append(ParameterList.OBJECT_REFERENCE_IDENTIFIER);
			
			if (!classDeclaration.containsNonStaticPrivateData())
			{
				builder.append(",");
			}
			
			builder.append(");");
		}
		else
		{
			builder.append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append(" = ").append(generateTypeCast()).append("1").append(';');
		}
		
		builder.append('\n');
		
		VTable extension = node().getParentClass().getVTableNodes().getExtensionVTable();
		
		builder.append(ParameterList.OBJECT_REFERENCE_IDENTIFIER).append("->").append(VTable.IDENTIFIER).append(" = &").append(getWriter(extension).generateSourceName(!extension.getFileDeclaration().isLibraryFile())).append(";\n");
		
		{
			Stack<AssignmentMethod> calls = new Stack<>();
			
			ClassDeclaration extended = node().getParentClass().getExtendedClassDeclaration();
			
			while (extended != null)
			{
				calls.push(extended.getAssignmentMethodNode());
				
				extended = extended.getExtendedClassDeclaration();
			}
			
			while (!calls.isEmpty())
			{
				AssignmentMethod method = calls.pop();
				
				if (method != null)
				{
					getWriter(method).generateMethodCall(builder, true);
				}
			}
		}
		
		// Generate super calls.
		{
			Stack<MethodCall> calls = new Stack<>();
			
			node().addSuperCallFor(calls, node());
			
			while (!calls.isEmpty())
			{
				MethodCall call = calls.pop();
				
				getWriter(call).generateSource(builder);
			}
		}
		
		AssignmentMethod assignmentMethod = node().getParentClass().getAssignmentMethodNode();
		
		getWriter(assignmentMethod).generateMethodCall(builder);
		
		builder.append('\n');
		
		Scope scope = node().getScope();
		
		builder.append("return ");
		getWriter(scope).generateSource(builder, false);
		
		builder.append('}').append('\n');
		
		return builder;
	}
	
	public String getName()
	{
		return Constructor.IDENTIFIER;
	}
}
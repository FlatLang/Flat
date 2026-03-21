package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class StaticClassReferenceWriter extends IIdentifierWriter
{
	public abstract StaticClassReference node();
	
	public StringBuilder generateUseOutput(StringBuilder builder, boolean pointer, boolean checkAccesses)
	{
		getWriter(node().getStaticTypeClass().getVTableNodes().getExtensionVTable()).generateSourceName(builder);
		builder.append(".").append(ClassDeclarationWriter.getClassInstanceVTableName());
		
		return builder;//.append(0);
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		if (!node().doesAccess())
		{
			return generateUseOutput(builder);
		}
		
		if (node().isSpecialFragment())
		{
			return generateSpecialFragment(builder);
		}
		
		Identifier accessed = node().getAccessedNode();
		
		return getWriter(accessed).generateSourceFragment(builder);
	}
	
	public StringBuilder generateArgumentReference(StringBuilder builder, Identifier callingMethod)
	{
		if (node().doesAccess())
		{
			if (node().getAccessedNode().getName().equals("class"))
			{
				return generateUseOutput(builder);
			}
			else if (node().getAccessedNode() instanceof MethodCall == false)
			{
				return getWriter(node().getAccessedNode()).generateArgumentReference(builder, callingMethod);
			}
		}
		
		return builder.append(0);//getAccessedNode().generateArgumentReference(builder, callingMethod);
	}
}
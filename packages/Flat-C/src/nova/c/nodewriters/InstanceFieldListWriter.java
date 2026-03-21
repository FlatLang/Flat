package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;
import net.fathomsoft.nova.tree.variables.InstanceFieldList;

public abstract class InstanceFieldListWriter extends ListWriter
{
	public abstract InstanceFieldList node();
	
	public StringBuilder generateHeader(StringBuilder builder)
	{
		ClassDeclaration extended = node().getParentClass().getExtendedClassDeclaration();
		
		if (extended != null)
		{
			boolean publicList = node() == node().getParentClass().getFieldList().getPublicFieldList();
			
			InstanceFieldList fields = null;
			
			if (publicList)
			{
				fields = extended.getFieldList().getPublicFieldList();
			}
			else
			{
				fields = extended.getFieldList().getPrivateFieldList();
			}
			
			getWriter(fields).generateHeader(builder);
		}
		
		for (int i = 0; i < node().getNumChildren(); i++)
		{
			Node child = node().getChild(i);
			
			getWriter(child).generateHeader(builder);
		}
		
		return builder;
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		boolean hasMethods = false;
		
		if (node().getNumChildren() > 0)
		{
			ClassDeclaration parent = node().getParentClass();
			
			if (parent.getMethodList().getNumChildren() > 0)
			{
				hasMethods = true;
			}
		}
		
		for (int i = 0; i < node().getNumChildren(); i++)
		{
			Node child = node().getChild(i);
			
			getWriter(child).generateSource(builder);
		}
		
		if (hasMethods)
		{
			builder.append('\n');
		}
		
		return builder;
	}
}
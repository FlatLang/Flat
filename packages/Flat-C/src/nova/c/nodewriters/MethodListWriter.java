package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class MethodListWriter extends TypeListWriter
{
	public abstract MethodList node();
	
	public StringBuilder generateHeader(StringBuilder builder)
	{
		for (int i = 0; i < node().getNumChildren(); i++)
		{
			MethodDeclaration methodDeclaration = node().getChild(i);
			
			if (!methodDeclaration.isExternal())
			{
				getWriter(methodDeclaration).generateHeader(builder);
			}
		}
		
		return builder;
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		if (node().getNumChildren() > 0)
		{
			builder.append('\n');
		}
		
		boolean printed = false;
		
		for (int i = 0; i < node().getNumChildren(); i++)
		{
			MethodDeclaration methodDeclaration = node().getChild(i);
			
			if (!methodDeclaration.isExternal())
			{
				if (printed)
				{
					builder.append('\n');
				}
				
				getWriter(methodDeclaration).generateSource(builder);
				
				printed = true;
			}
		}
		
		return builder;
	}
	
	/**
	 * Generate a String containing all of the prototypes for each method
	 * contained within node() node(). A method prototype follows the
	 * following syntax: "static returnType methodName(arguments);"
	 *
	 * @return A String containing all of the prototypes for the methods
	 * 		contained within node() node().
	 */
	public StringBuilder generateSourcePrototypes(StringBuilder builder)
	{
		for (int i = 0; i < node().getNumChildren(); i++)
		{
			MethodDeclaration child = node().getChild(i);
			
			getWriter(child).generateSourcePrototype(builder).append('\n');
		}
		
		return builder;
	}
}
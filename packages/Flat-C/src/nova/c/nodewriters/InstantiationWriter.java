package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class InstantiationWriter extends IIdentifierWriter
{
	public abstract Instantiation node();
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return generateSourceFragment(builder).append(";\n");
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		Identifier id = node().getIdentifier();
		
		return getWriter(id).generateSourceFragment(builder);
	}
	
	public StringBuilder generateUseOutput(StringBuilder builder, boolean pointer, boolean checkAccesses)
	{
		Identifier id = node().getIdentifier();
		
		return getWriter(id).generateUseOutput(builder, pointer, checkAccesses);
	}
}
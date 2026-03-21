package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class ImportWriter extends NodeWriter
{
	public abstract Import node();
	
	public StringBuilder generateHeader(StringBuilder builder)
	{
		return generateSource(builder);
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		builder.append("#include ");
		
		String location = node().location;
		
		if (node().isExternal())
		{
			location += ".h";
		}
		else
		{
			ClassDeclaration node = node().getProgram().getClassDeclaration(location);
			
			FileDeclaration f = node.getFileDeclaration();
			
			location = getWriter(f).generateHeaderName();
		}
		
		if (node().isExternal() || !node().getFileDeclaration().getName().equals(node().location))
		{
			return builder.append('<').append(location).append('>').append('\n');
		}
		else
		{
			return builder.append('"').append(location).append('"').append('\n');
		}
	}
}
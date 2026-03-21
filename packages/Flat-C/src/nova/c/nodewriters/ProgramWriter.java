package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class ProgramWriter extends TypeListWriter
{
	public abstract Program node();
	
	public StringBuilder generateHeader(StringBuilder builder)
	{
		for (int i = 0; i < node().getNumChildren(); i++)
		{
			Node child = node().getChild(i);
			
			getWriter(child).generateHeader();
		}
		
		return builder;
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		for (int i = 0; i < node().getNumChildren(); i++)
		{
			Node child = node().getChild(i);
			
			getWriter(child).generateSource();
		}
		
		return builder;
	}
	
	/**
	 * Format the C Header output to follow syntactical rules.
	 */
	public void formatHeaderOutput()
	{
		for (int i = 0; i < node().getNumChildren(); i++)
		{
			FileDeclaration fileDeclaration = (FileDeclaration)node().getChild(i);
			
			getWriter(fileDeclaration).formatHeaderOutput();
		}
	}
	
	/**
	 * Format the C Source output to follow syntactical rules.
	 */
	public void formatSourceOutput()
	{
		for (int i = 0; i < node().getNumChildren(); i++)
		{
			FileDeclaration fileDeclaration = (FileDeclaration)node().getChild(i);
			
			getWriter(fileDeclaration).formatSourceOutput();
		}
	}
	
	/**
	 * Format the C Header and Source output to follow syntactical rules.
	 */
	public void formatOutput()
	{
		formatHeaderOutput();
		formatSourceOutput();
	}
}
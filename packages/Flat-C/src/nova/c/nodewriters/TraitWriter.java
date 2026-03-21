package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

import java.io.IOException;
import java.io.PrintWriter;

public abstract class TraitWriter extends ClassDeclarationWriter
{
	public abstract Trait node();
	
	public java.io.Writer writeVTableDeclaration(java.io.Writer writer) throws IOException
	{
		writer.write(getVTableType() + " " + getVTableValueName() + ";\n");
		
		return writer;
	}
	
	public java.io.Writer writeDefaultVTable(java.io.Writer writer) throws IOException
	{
		writer.write(getVTableTypeName() + " " + getVTableDefaultValueName() + " = {");
		
		NovaMethodDeclaration[] methods = node().getInterfaceVirtualMethods(false);
		
		if (methods.length > 0)
		{
			for (int i = 0; i < methods.length; i++)
			{
				if (i > 0)
				{
					writer.write(",");
				}
				
				writer.write("0");
			}
		}
		else
		{
			writer.write("0");
		}
		
		writer.write("};\n");
		
		return writer;
	}
	
	public java.io.Writer writeVTableAssignment(java.io.Writer writer) throws IOException
	{
		writer.write("struct " + getVTableTypeName() + " {\n");
		
		NovaMethodDeclaration[] methods = node().getInterfaceVirtualMethods(false);
		
		if (methods.length > 0)
		{
			for (NovaMethodDeclaration m : methods)
			{
				getWriter(m.getVirtualMethod()).writeVTableDeclaration(writer);
			}
		}
		else
		{
			writer.write("char x;\n");
		}
		
		writer.write("};\n");
		
		return writer;
	}
	
	public String getVTableValueName()
	{
		return generateSourceName("vtable").toString() + "_value";
	}
	
	public String getVTableDefaultValueName()
	{
		return generateSourceName("vtable").toString() + "_value_default";
	}
	
	public String getVTableTypeName()
	{
		return generateSourceName("vtable").toString();
	}
	
	public String getVTableType()
	{
		return getVTableTypeName() + "*";
	}
	
	public java.io.Writer writeVTableTypedef(java.io.Writer writer) throws IOException
	{
		String name = getVTableTypeName();
		
		writer.write("typedef struct " + name + " " + name + ";\n");
		
		return writer;
	}
	
	public java.io.Writer writeDefaultVTableDeclaration(java.io.Writer writer) throws IOException
	{
		writer.write("extern " + getVTableTypeName() + " " + getVTableDefaultValueName() + ";\n");
		
		return writer;
	}
}
package nova.c.nodewriters;

import net.fathomsoft.nova.tree.Package;

public abstract class PackageWriter extends NodeWriter
{
	public abstract Package node();
	
	public StringBuilder generateHeader(StringBuilder builder)
	{
		return builder;
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return builder;
	}
	
	public StringBuilder generateHeaderLocation()
	{
		return generateHeaderLocation(new StringBuilder());
	}
	
	public StringBuilder generateHeaderLocation(StringBuilder builder)
	{
		return builder.append(node().getLocation());
	}
	
	public StringBuilder generateLocation()
	{
		return generateLocation(new StringBuilder());
	}
	
	public StringBuilder generateLocation(StringBuilder builder)
	{
		if (!node().validLocation())
		{
			return builder;
		}
		
		String output = node().location.replace('/', '_').replace('.', '_');
		
		return builder.append(output);
	}
}
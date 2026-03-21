package nova.c.nodewriters;

import net.fathomsoft.nova.tree.*;

public abstract class MethodDeclarationWriter extends InstanceDeclarationWriter
{
	public abstract MethodDeclaration node();
	
	public StringBuilder generateHeader(StringBuilder builder)
	{
		return generateHeaderFragment(builder).append("\n");
	}
	
	public StringBuilder generateHeaderFragment(StringBuilder builder)
	{
		return builder;
	}
	
	public StringBuilder generateSource(StringBuilder builder)
	{
		return generateSourceFragment(builder);
	}
	
	public StringBuilder generateSourceFragment(StringBuilder builder)
	{
		return builder;
	}
	
	public StringBuilder generateSourceNativeName(StringBuilder builder, boolean declaration)
	{
		if (declaration)
		{
			return generateSourceName(builder, "native");
		}
		
		return builder.append(node().getName());
		//		String location = getFileDeclaration().getPackage().getLocation().replace('/', '_');
		//		String prefix   = "";
		//		
		//		if (declaration)
		//		{
		//			prefix = "native";
		//			
		////			if (location.length() > 0)
		////			{
		////				prefix += '_';
		////			}
		//		}
		//		
		////		if (location.length() > 0)
		////		{
		////			location = location + '_';
		////		}
		//		
		//		builder.append(prefix).append(getName());
		//		
		//		return builder;
	}
	
	/**
	 * Generate the C prototype for the method header.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public void test()
	 * {
	 * 	...
	 * }</pre></blockquote>
	 * will output as "<code>static void test();</code>"<br>
	 * <br>
	 * In essence, node() method is just {@link #generateSourceSignature(StringBuilder)}
	 * with a semi-colon attached to the end.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @return The C prototype for the method header.
	 */
	public StringBuilder generateSourcePrototype(StringBuilder builder)
	{
		return generateSourceSignature(builder).append(";");
	}
	
	/**
	 * Generate the method signature that will appear in the c source
	 * output.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public void test()
	 * {
	 * 	...
	 * }</pre></blockquote>
	 * will output as "<code>static void test()</code>"
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @return The method signature in the C language.
	 */
	public StringBuilder generateSourceSignature(StringBuilder builder)
	{
		generateModifiersSource(builder).append(' ');
		generateSourceName(builder);
		generateParameterOutput(builder);
		
		return builder;
	}
	
	public StringBuilder generateParameterOutput(StringBuilder builder)
	{
		builder.append('(');
		
		ParameterList params = node().getParameterList();
		
		getWriter(params).generateSource(builder);
		
		return builder.append(')');
	}
	
	/**
	 * Generate the identifier that will be used to call the method.
	 *
	 * @param builder The StringBuilder to append the data to.
	 * @return The updated StringBuilder.
	 */
	public StringBuilder generateMethodCall(StringBuilder builder)
	{
		return generateSourceName(builder);
	}
}
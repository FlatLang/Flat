package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.util.Location;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 16, 2014 at 1:13:49 AM
 * @version	v0.2.43 Jan 16, 2015 at 11:59:17 AM
 */
public abstract class VTable extends IIdentifier
{
	public static final String	IDENTIFIER = "vtable";
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public VTable(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		String type = "" + getParentClass().generateCSourceName(getVTableType() + "_VTable");
		setType(type, true, false, false);
		
		setName(type + "_val", true);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.ClassDeclaration#generateCHeader(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		NovaMethodDeclaration methods[] = getVirtualMethods();
		
		builder.append("typedef struct ").append(generateCTypeName()).append(' ').append(generateCTypeName()).append(";\n");
		
		if (methods.length <= 0)
		{
			return builder;
		}
		
		builder.append("struct ").append(generateCTypeName()).append("\n{\n");
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			child.generateCHeaderFragment(builder).append(";\n");
		}
		
		generateVirtualMethodDeclarations(builder, methods);
		builder.append("}").append(";\n\n");
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.ClassDeclaration#generateCSource(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		NovaMethodDeclaration methods[] = getVirtualMethods();
		
		if (methods.length <= 0)
		{
			return builder;
		}
		
		generateCType(builder).append(' ').append(generateCSourceName()).append(" =\n");
		
		builder.append("{\n");
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			child.generateCSourceFragment(builder).append(",\n");
		}
		
		generateVirtualMethodValues(builder, methods);
		builder.append("};\n");
		
		return builder;
	}
	
	/**
	 * Generate the virtual method declarations that declares the names
	 * of the methods that are used in the class and its ancestors.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @param methods The methods to add the identifiers from.
	 * @return The StringBuilder with the appended data.
	 */
	public StringBuilder generateVirtualMethodDeclarations(StringBuilder builder, NovaMethodDeclaration methods[])
	{
		for (NovaMethodDeclaration method : methods)
		{
			generateVirtualMethodDeclaration(builder, method);
		}
		
		return builder;
	}
	
	/**
	 * Generate the virtual method declaration that declares the name
	 * of the given method.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @param method The method to add the identifier from.
	 * @return The StringBuilder with the appended data.
	 */
	public StringBuilder generateVirtualMethodDeclaration(StringBuilder builder, NovaMethodDeclaration method)
	{
		return method.generateCType(builder).append(" (*").append(method.generateCVirtualMethodName()).append(")(").append(method.getParameterList().generateCHeader()).append(");\n");
	}
	
	/**
	 * Add the vtable values that point to the correct virtual method
	 * implementation for the specified class.
	 * 
	 * @param builder The StringBuilder to append the data to.
	 * @param methods The methods to add the references to.
	 * @return The StringBuilder with the appended data.
	 */
	public StringBuilder generateVirtualMethodValues(StringBuilder builder, NovaMethodDeclaration methods[])
	{
		for (NovaMethodDeclaration method : methods)
		{
			if (method != null)
			{
//				method.generateCVirtualMethodName(builder);
				method.generateCSourceName(builder);
			}
			else
			{
				builder.append(0);
			}
			
			builder.append(",\n");
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#validate(int)
	 */
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase != SyntaxTree.PHASE_CLASS_DECLARATION)
		{
			
		}
		
		return result;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public VTable cloneTo(VTable node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link VTable} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public VTable cloneTo(VTable node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the VTable class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
	
	public abstract String getVTableType();
	
	public abstract NovaMethodDeclaration[] getVirtualMethods();
}
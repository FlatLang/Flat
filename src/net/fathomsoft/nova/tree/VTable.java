package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.util.Location;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 16, 2014 at 1:13:49 AM
 * @version	v0.2.14 Jul 19, 2014 at 7:33:13 PM
 */
public class VTable extends IIdentifier
{
	public static final String	IDENTIFIER = "vtable";
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public VTable(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.ClassDeclaration#generateCHeader(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		MethodDeclaration methods[] = getParentClass().getVirtualMethods();
		
		if (methods.length <= 0)
		{
			return builder;
		}
		
		builder.append("typedef struct ").append(generateCTypeOutput()).append('\n');
		builder.append("{\n");
		generateVirtualMethodDeclarations(builder, methods);
		builder.append("} ").append(generateCTypeOutput()).append(";\n");
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.ClassDeclaration#generateCSource(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		MethodDeclaration methods[] = getParentClass().getVirtualMethods();

		if (methods.length <= 0)
		{
			return builder;
		}
		
		generateCTypeOutput(builder).append(' ').append(generateCSourceName()).append(" =\n");
		builder.append("{\n");
		generateVirtualMethodValues(builder, methods);
		builder.append("};");
		
		return builder;
	}
	
//	/**
//	 * @see net.fathomsoft.nova.tree.Identifier#generateCSourceName(java.lang.StringBuilder)
//	 */
//	@Override
//	public StringBuilder generateCSourceName(StringBuilder builder)
//	{
//		return builder.append(Nova.LANGUAGE_NAME.toLowerCase()).append("_VTable_").append(getParentClass().generateUniquePrefix());
//	}
	
	public StringBuilder generateVirtualMethodDeclarations(StringBuilder builder, MethodDeclaration methods[])
	{
		for (MethodDeclaration method : methods)
		{
			generateVirtualMethodDeclaration(builder, method);
		}
		
		return builder;
	}
	
	public StringBuilder generateVirtualMethodDeclaration(StringBuilder builder, MethodDeclaration method)
	{
		return method.generateCTypeOutput(builder).append(" (*").append(method.generateCVirtualMethodName()).append(")(").append(method.getParameterList().generateCHeader()).append(");\n");
	}
	
	public StringBuilder generateVirtualMethodValues(StringBuilder builder, MethodDeclaration methods[])
	{
		for (MethodDeclaration method : methods)
		{
			method.generateCSourceName(builder).append(",\n");
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#validate(int)
	 */
	@Override
	public Node validate(int phase)
	{
		if (phase != SyntaxTree.PHASE_CLASS_DECLARATION)
		{
			String type = Nova.LANGUAGE_NAME.toLowerCase() + "_VTable_" + getParentClass().generateUniquePrefix();
			setType(type, true, false, false);
			
			setName(type + "_val", true);
		}
		
		return this;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public VTable clone(Node temporaryParent, Location locationIn)
	{
		VTable node = new VTable(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given Array with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public VTable cloneTo(VTable node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the VTable class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test()
	{
		
		
		return null;
	}
}
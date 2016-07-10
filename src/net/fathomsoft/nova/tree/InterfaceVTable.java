package net.fathomsoft.nova.tree;

import java.util.ArrayList;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * {@link VTable} extension that represents
 * 
 * @author	Braden Steffaniak
 */
public class InterfaceVTable extends VTable
{
	public static final String IDENTIFIER = "itable";
	
	public static final String TYPE = "nova_Interface_VTable";
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public InterfaceVTable(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		setTypeValue(TYPE);
		setDataType(VALUE);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.VTable#getVTableType()
	 */
	@Override
	public String getVTableType()
	{
		return "Interface";
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.VTable#getVirtualMethods()
	 */
	@Override
	public NovaMethodDeclaration[] getVirtualMethods()
	{
		ArrayList<NovaMethodDeclaration> methods = new ArrayList<NovaMethodDeclaration>();
		
		NovaMethodDeclaration[] interfaceMethods = getParentClass().getInterfaceVirtualMethods();
		
		for (NovaMethodDeclaration method : getProgram().getInterfaceMethods())
		{
			NovaMethodDeclaration m = null;
			
			for (NovaMethodDeclaration method2 : interfaceMethods)
			{
				if (method2.getVirtualMethod() == method)
				{
					m = method2;
				}
			}
			
			methods.add(m);
		}
		
		return methods.toArray(new NovaMethodDeclaration[0]);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.VTable#generateCHeader(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#generateCHeaderFragment(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeaderFragment(StringBuilder builder)
	{
		return generateCType(builder).append(" ").append(IDENTIFIER);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.VTable#generateCSource(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#generateCSourceFragment(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		NovaMethodDeclaration[] methods = getVirtualMethods();
		
		builder.append("{\n");
		
		for (NovaMethodDeclaration method : methods)
		{
			if (method != null)
			{
				method.generateCInterfaceVTableSource(builder);
			}
			else
			{
				builder.append(0);
			}
			
			builder.append(",\n");
		}
		
		builder.append("}");
		
		return builder;
		//return super.generateCSourceFragment(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public InterfaceVTable clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		InterfaceVTable node = new InterfaceVTable(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public InterfaceVTable cloneTo(InterfaceVTable node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link InterfaceVTable} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public InterfaceVTable cloneTo(InterfaceVTable node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link InterfaceVTable} class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
}
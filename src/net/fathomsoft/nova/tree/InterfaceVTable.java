package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

import java.util.ArrayList;

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
		ArrayList<NovaMethodDeclaration> methods = new ArrayList<>();
		
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
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public InterfaceVTable clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		InterfaceVTable node = new InterfaceVTable(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public InterfaceVTable cloneTo(InterfaceVTable node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link InterfaceVTable} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public InterfaceVTable cloneTo(InterfaceVTable node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
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
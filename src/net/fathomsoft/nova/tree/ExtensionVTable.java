package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * {@link VTable} extension that represents
 * 
 * @author	Braden Steffaniak
 */
public class ExtensionVTable extends VTable
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ExtensionVTable(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		InterfaceVTable table = new InterfaceVTable(this, Location.INVALID);
		
		addChild(table);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}
	
	public InterfaceVTable getInterfaceVTable()
	{
		return (InterfaceVTable)getChild(super.getNumDefaultChildren() + 0);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.VTable#getVTableType()
	 */
	@Override
	public String getVTableType()
	{
		return "Extension";
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.VTable#getVirtualMethods()
	 */
	@Override
	public NovaMethodDeclaration[] getVirtualMethods()
	{
		return getParentClass().getExtensionVirtualMethods();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public ExtensionVTable clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		ExtensionVTable node = new ExtensionVTable(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public ExtensionVTable cloneTo(ExtensionVTable node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link ExtensionVTable} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ExtensionVTable cloneTo(ExtensionVTable node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the {@link ExtensionVTable} class type to make sure everything
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
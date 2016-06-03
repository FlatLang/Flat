package net.fathomsoft.nova.tree.generics;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

/**
 * {@link Node} extension that represents
 * 
 * @author	Braden Steffaniak
 */
public class GenericTypeParameter extends Node
{
	private String name, defaultType;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public GenericTypeParameter(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		defaultType = "Object";
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getDefaultType()
	{
		return defaultType;
	}
	
	public void setDefaultType(String type)
	{
		this.defaultType = type;
	}
	
	public GenericTypeParameterDeclaration getGenericDeclaration()
	{
		return (GenericTypeParameterDeclaration)getParent();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public GenericTypeParameter clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		GenericTypeParameter node = new GenericTypeParameter(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public GenericTypeParameter cloneTo(GenericTypeParameter node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link GenericTypeParameter} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public GenericTypeParameter cloneTo(GenericTypeParameter node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		node.name = this.name;
		node.defaultType = this.defaultType;
		
		return node;
	}
	
	/**
	 * Test the {@link GenericTypeParameter} class type to make sure everything
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
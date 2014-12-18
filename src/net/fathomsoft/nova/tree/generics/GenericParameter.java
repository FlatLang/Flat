package net.fathomsoft.nova.tree.generics;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

/**
 * {@link Node} extension that represents a generic type parameter.
 * Contains the information of a generic type parameter. Information
 * such as the name of the parameter and the default type.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.41 Dec 7, 2014 at 11:01:21 PM
 * @version	v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public class GenericParameter extends Node
{
	private String name, defaultType;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public GenericParameter(Node temporaryParent, Location locationIn)
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
	
	public GenericDeclaration getGenericDeclaration()
	{
		return (GenericDeclaration)getParent();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public GenericParameter clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		GenericParameter node = new GenericParameter(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link GenericParameter} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public GenericParameter cloneTo(GenericParameter node)
	{
		super.cloneTo(node);
		
		node.name        = name;
		node.defaultType = defaultType;
		
		return node;
	}
	
	/**
	 * Test the {@link GenericParameter} class type to make sure everything
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
package net.fathomsoft.nova.tree.generics;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.AccessorMethod;
import net.fathomsoft.nova.tree.GenericCompatible;
import net.fathomsoft.nova.tree.IValue;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.TypeList;
import net.fathomsoft.nova.tree.Value;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;

/**
 * {@link IValue} extension that represents a generic type implementation.
 * Contains the information of a generic type implementation.
 * Contains all of the types that are being implemented into a generic
 * declaration.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.41 Dec 7, 2014 at 10:22:46 PM
 * @version	v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public class GenericArgument extends IValue implements GenericCompatible
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public GenericArgument(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}

	/**
	 * @see net.fathomsoft.nova.tree.GenericCompatible#getGenericImplementation()
	 */
	@Override
	public GenericImplementation getGenericImplementation()
	{
		return (GenericImplementation)getParent();
	}
	
	/**
	 * Get the index that the specified argument can be accessed by
	 * at the declaration of the parameters.
	 * 
	 * @return The index.
	 */
	public int getArgumentIndex()
	{
		GenericImplementation implementation = getGenericImplementation();
		
		for (int i = 0; i < implementation.getNumVisibleChildren(); i++)
		{
			if (implementation.getVisibleChild(i) == this)
			{
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.IValue#setTypeValue(java.lang.String)
	 */
	@Override
	public void setTypeValue(String type)
	{
		super.setTypeValue(type);
	}
	
	/**
	 * Get the default type that this generic type extends.
	 * @see net.fathomsoft.nova.tree.generics.GenericParameter#getDefaultType()
	 * 
	 * @return The type that the generic type extends by default.
	 */
	public String getDefaultType()
	{
		return getValue().getTypeClass().getGenericDeclaration().getParameter(getArgumentIndex()).getDefaultType();
	}
	
	/**
	 * Get the Value instance that this generic argument is manifested as.
	 * 
	 * @return The Value instance.
	 */
	public Value getValue()
	{
		return (Value)getGenericImplementation().getParent();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getGenericReturnType()
	 */
	@Override
	public String getGenericReturnType()
	{
		if (getValue().getGenericDeclaration().containsParameter(getType()))
		{
			return getDefaultType();
		}
		
		return getType();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public GenericArgument clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		GenericArgument node = new GenericArgument(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public GenericArgument cloneTo(GenericArgument node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link GenericArgument} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public GenericArgument cloneTo(GenericArgument node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link GenericArgument} class type to make sure everything
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
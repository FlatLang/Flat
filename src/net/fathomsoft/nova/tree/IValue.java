package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * Node extension that represents something that returns a value.
 * For the rules on what can and cannot be an value node, refer to
 * {@link #setType(java.lang.String)}
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.4 May 2, 2014 at 11:14:37 PM
 * @version	v0.2.35 Oct 5, 2014 at 11:22:42 PM
 */
public class IValue extends Value
{
	// Dont forget about IIdentifier!!!!
	private byte	dataType;
	
	private int		arrayDimensions;
	
	private String	type;
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#Value(Node, Location)
	 */
	// Dont forget about IIdentifier!!!!
	public IValue(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		setDataType(VALUE);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getArrayDimensions()
	 */
	@Override
	// Dont forget about IIdentifier!!!!
	public int getArrayDimensions()
	{
		return arrayDimensions - getArrayAccessDimensions();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#setArrayDimensions(int)
	 */
	@Override
	// Dont forget about IIdentifier!!!!
	public void setArrayDimensions(int arrayDimensions)
	{
		this.arrayDimensions = arrayDimensions;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getType()
	 */
	@Override
	// Dont forget about IIdentifier!!!!
	public String getType(boolean checkCast)
	{
		return type;
	}
	
	@Override
	public String getTypeStringValue()
	{
		return type;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.AbstractValue#setTypeValue(java.lang.String)
	 */
	@Override
	public void setTypeValue(String type)
	{
		this.type = type;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getDataType()
	 */
	@Override
	// Dont forget about IIdentifier!!!!
	public byte getDataType(boolean checkGeneric)
	{
		return dataType;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#setDataType(byte)
	 */
	@Override
	// Dont forget about IIdentifier!!!!
	public void setDataType(byte type)
	{
		this.dataType = type;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public IValue clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		IValue node = new IValue(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public IValue cloneTo(IValue node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link Value} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public IValue cloneTo(IValue node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.arrayDimensions = arrayDimensions;
		node.type            = type;
		node.dataType        = dataType;
		
		return node;
	}
	
	@Override
	public String toString()
	{
		return getTypeStringValue();
	}
	
	/**
	 * Test the IValue class type to make sure everything
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
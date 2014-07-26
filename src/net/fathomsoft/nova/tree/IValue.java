package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Node extension that represents something that returns a value.
 * For the rules on what can and cannot be an value node, refer to
 * {@link #setType(java.lang.String)}
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.4 May 2, 2014 at 11:14:37 PM
 * @version	v0.2.19 Jul 26, 2014 at 12:30:24 AM
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
	 * Generate a IValue instance from the given type.
	 * 
	 * @param temporaryParent The temporary parent of the new Node.
	 * @param locationIn The location of the new Node.
	 * @param type The type to set for the Value.
	 * @param require Whether or not a successful decode is required.
	 * @return The generated IValue instance.
	 */
	public static IValue generateFromType(Node temporaryParent, Location locationIn, String type, boolean require)
	{
		IValue value = new IValue(temporaryParent, locationIn);
		
		if (!value.setType(type, require, true))
		{
			// We already know that it is not required because if it was,
			// it would have thrown an exception and never reached here.
			return null;
		}
		
		return value;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getArrayDimensions()
	 */
	@Override
	// Dont forget about IIdentifier!!!!
	public int getArrayDimensions()
	{
		return arrayDimensions;
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
	public String getType()
	{
		return type;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#setType(java.lang.String, boolean, boolean, boolean)
	 */
	@Override
	// Dont forget about IIdentifier!!!!
	public boolean setType(String type, boolean require, boolean checkType, boolean checkDataType)
	{
		// Dont forget about IIdentifier.setType()!!!!
		if (checkType && !checkType(type, require))
		{
			return false;
		}
		
		this.type = type;
		
		if (checkDataType)
		{
			if (isExternalType())
			{
				if (getParentMethod() == null || getAncestorOfType(ExternalMethodDeclaration.class) == null)
				{
					setDataType(POINTER);
				}
			}
			else if (!SyntaxUtils.isPrimitiveType(type))
			{
				setDataType(POINTER);
			}
		}
		
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getDataType()
	 */
	@Override
	// Dont forget about IIdentifier!!!!
	public byte getDataType()
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
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public IValue clone(Node temporaryParent, Location locationIn)
	{
		IValue node = new IValue(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link Value} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public IValue cloneTo(IValue node)
	{
		super.cloneTo(node);
		
		node.arrayDimensions = arrayDimensions;
		node.type            = type;
		node.dataType        = dataType;
		
		return node;
	}
	
	/**
	 * Test the IValue class type to make sure everything
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
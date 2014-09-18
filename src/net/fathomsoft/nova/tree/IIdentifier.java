package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Value extension that represents an Identifier. For the rules on
 * what can and cannot be an Identifier, refer to
 * {@link net.fathomsoft.nova.tree.Identifier#setName(java.lang.String) setName}
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:00:19 PM
 * @version	v0.2.29 Aug 29, 2014 at 3:17:45 PM
 */
public class IIdentifier extends Identifier
{
	private boolean	forceOriginal;
	
	private String	name;
	
	// Value data..... ...
	// Dont forget about IValue!!!!
	private byte	dataType;
	
	private int		arrayDimensions;
	
	private String	type;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	// Dont forget about IValue!!!!
	public IIdentifier(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		setDataType(VALUE);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#getName()
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#setName(java.lang.String, boolean)
	 */
	public void setName(String name, boolean forceOriginal)
	{
		this.forceOriginal = forceOriginal;
		
		this.name = name;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#willForceOriginalName()
	 */
	public boolean willForceOriginalName()
	{
		return forceOriginal;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#setForceOriginalName(boolean)
	 */
	public void setForceOriginalName(boolean forceOriginal)
	{
		this.forceOriginal = forceOriginal;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getArrayDimensions()
	 */
	@Override
	// Dont forget about IValue!!!!
	public int getArrayDimensions()
	{
		return arrayDimensions;
	}

	/**
	 * @see net.fathomsoft.nova.tree.Value#setArrayDimensions(int)
	 */
	@Override
	// Dont forget about IValue!!!!
	public void setArrayDimensions(int arrayDimensions)
	{
		this.arrayDimensions = arrayDimensions;
	}

	/**
	 * @see net.fathomsoft.nova.tree.Value#getType()
	 */
	@Override
	// Dont forget about IValue.getType()!!!!
	public String getType()
	{
		return type;
	}

	/**
	 * @see net.fathomsoft.nova.tree.Value#setType(java.lang.String, boolean, boolean, boolean)
	 */
	@Override
	// Dont forget about IValue!!!!
	public boolean setType(String type, boolean require, boolean checkType, boolean checkDataType)
	{
		// Dont forget about IValue.setType()!!!!
		if (checkType)
		{
			if (!checkType(type, require))
			{
				return false;
			}
			
			type = SyntaxUtils.getValidType(this, type);
		}
		
		this.type = type;
		
		if (checkDataType)
		{
			if (isExternalType())
			{
				if (getAncestorOfType(ExternalMethodDeclaration.class) == null)
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
	// Dont forget about IValue!!!!
	public byte getDataType()
	{
		return dataType;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#setDataType(byte)
	 */
	@Override
	// Dont forget about IValue!!!!
	public void setDataType(byte type)
	{
		this.dataType = type;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public IIdentifier clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		IIdentifier node = new IIdentifier(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link Identifier} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public IIdentifier cloneTo(IIdentifier node)
	{
		super.cloneTo(node);
		
		node.name            = name;
		node.forceOriginal   = forceOriginal;
		node.arrayDimensions = arrayDimensions;
		node.type            = type;
		node.dataType        = dataType;
		
		return node;
	}
	
	/**
	 * Test the IIdentifier class type to make sure everything
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
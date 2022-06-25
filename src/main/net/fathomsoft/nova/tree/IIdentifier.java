package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * Value extension that represents an Identifier. For the rules on
 * what can and cannot be an Identifier, refer to
 * {@link net.fathomsoft.nova.tree.Identifier#setName(java.lang.String) setName}
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:00:19 PM
 * @version	v0.2.35 Oct 5, 2014 at 11:22:42 PM
 */
public class IIdentifier extends Identifier
{
	private String	name;
	
	// Value data..... ...
	// Dont forget about IValue!!!!
//	private byte	dataType;
//	
//	private int		arrayDimensions;
//	
//	private String	type;
	
	public Type type;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	// Dont forget about IValue!!!!
	public IIdentifier(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public GenericCompatible getContext()
	{
		if (isInstance())
		{
			return super.getContext();
		}
		
		return null;
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
		this.name = name;
		
		if (forceOriginal)
		{
			setProperty("externalName", name);
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#doesForceOriginalName()
	 */
	public boolean doesForceOriginalName()
	{
		return containsProperty("externalName");
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#setForceOriginalName(boolean)
	 */
	public void setForceOriginalName(boolean forceOriginal)
	{
		setProperty("externalName", name);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getArrayDimensions()
	 */
	@Override
	// Dont forget about IValue!!!!
	public int getArrayDimensions()
	{
		return type != null ? type.arrayDimensions : 0;//type instanceof ArrayType ? ((ArrayType)type).arrayDimensions - getArrayAccessDimensions() : 0;
	}

	/**
	 * @see net.fathomsoft.nova.tree.Value#setArrayDimensions(int)
	 */
	@Override
	// Dont forget about IValue!!!!
	public void setArrayDimensions(int arrayDimensions)
	{
		/*if (arrayDimensions > 0)
		{
			String value = type != null ? type.value : null;
			byte dataType = type != null ? type.dataType : -1;
			
			if (type instanceof ArrayType == false)
			{
				ArrayType newType = new ArrayType();
				newType.type = type;
				
				type = newType;
			}
			
			((ArrayType)type).arrayDimensions = arrayDimensions;
			
//			if (value != null && dataType == VALUE)
//			{
				type.value = value;
//			}
//			else
//			{
//				type.value = "Array";
//			}
		}
		else if (type instanceof ArrayType)
		{
			type = ((ArrayType)type).type;
		}*/
		type = type == null ? new Type() : type;
		
		type.arrayDimensions = arrayDimensions;
	}

	/**
	 * @see net.fathomsoft.nova.tree.Value#getType()
	 */
	@Override
	// Dont forget about IValue.getType()!!!!
	public String getType(boolean checkCast)
	{
		return type != null ? type.toNova() : null;
	}
	
	@Override
	// Dont forget about IValue.getTypeStringValue()!!!!
	public String getTypeStringValue()
	{
		return type.toNova();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.AbstractValue#setTypeValue(java.lang.String)
	 */
	@Override
	public void setTypeValue(String type)
	{
		if (this.type == null || this.type.value == null && this.type.arrayDimensions == 0 && !isFunctionType())
		{
			this.type = Type.parse(this, type);
		}
		else
		{
			this.type = this.type == null ? new Type() : this.type;
			
			this.type.setType(type);
		}
		
		genericParameter = searchGenericTypeParameter();
	}
	
	@Override
	public Type getTypeObject()
	{
		return type;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getDataType()
	 */
	@Override
	// Dont forget about IValue!!!!
	public byte getDataType(boolean checkGeneric)
	{
		return type != null ? type.dataType : 0;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#setDataType(byte)
	 */
	@Override
	// Dont forget about IValue!!!!
	public void setDataType(byte type)
	{
		this.type.dataType = type;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public IIdentifier clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		IIdentifier node = new IIdentifier(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public IIdentifier cloneTo(IIdentifier node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link Identifier} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public IIdentifier cloneTo(IIdentifier node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.name            = name;
		node.type            = type != null ? type.clone() : null;

		if (getProgram() != null && getProgram().getPhase() > SyntaxTree.PHASE_CLASS_DECLARATION)
		{
			node.genericParameter = node.searchGenericTypeParameter();
		}
		
		return node;
	}
	
	@Override
	public boolean onAfterDecoded()
	{
		if (getProgram() != null && getProgram().getPhase() > SyntaxTree.PHASE_CLASS_DECLARATION)
		{
			genericParameter = searchGenericTypeParameter();
		}
		
		return super.onAfterDecoded();
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
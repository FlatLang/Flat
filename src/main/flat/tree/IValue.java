package flat.tree;

import flat.TestContext;
import flat.util.Location;

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
//	private byte	dataType;
//
//	private int		arrayDimensions;
//
//	private String	type;
	
	public Type type;
	
	/**
	 * @see Value#Value(Node, Location)
	 */
	// Dont forget about IIdentifier!!!!
	public IValue(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see Value#getArrayDimensions()
	 */
	@Override
	// Dont forget about IIdentifier!!!!
	public int getArrayDimensions()
	{
		return type != null ? type.arrayDimensions : 0;//type instanceof ArrayType ? ((ArrayType)type).arrayDimensions - getArrayAccessDimensions() : 0;
	}
	
	/**
	 * @see Value#setArrayDimensions(int)
	 */
	@Override
	// Dont forget about IIdentifier!!!!
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
	 * @see Value#getType()
	 */
	@Override
	// Dont forget about IIdentifier!!!!
	public String getType(boolean checkCast)
	{
		if (checkCast && this instanceof Accessible) {
			Accessible accessible = (Accessible) this;

			Cast cast = accessible.getCast();

			if (cast != null)
			{
				return cast.getType();
			}
		}

		return type != null ? type.toFlat() : null;
	}
	
	@Override
	public String getTypeStringValue()
	{
		return type.toFlat();
	}
	
	/**
	 * @see AbstractValue#setTypeValue(java.lang.String)
	 */
	@Override
	public void setTypeValue(String type)
	{
		if (this.type == null || this.type.value == null && this.type.arrayDimensions == 0)
		{
			this.type = Type.parse(this, type);
		}
		else
		{
			this.type = this.type == null ? new Type() : this.type;
			
			this.type.setType(type);
		}
		
		genericParameter = searchGenericTypeParameter(0);
	}
	
	@Override
	public Type getTypeObject()
	{
		return type;
	}
	
	/**
	 * @see Value#getDataType()
	 */
	@Override
	// Dont forget about IIdentifier!!!!
	public byte getDataType(boolean checkGeneric)
	{
		return getDataType(checkGeneric, true);
	}

	// Dont forget about IIdentifier!!!!
	public byte getDataType(boolean checkGeneric, boolean checkCast)
	{
		return type.dataType;
	}
	
	/**
	 * @see Value#setDataType(byte)
	 */
	@Override
	// Dont forget about IIdentifier!!!!
	public void setDataType(byte type)
	{
		this.type.dataType = type;
	}
	
	/**
	 * @see Node#clone(Node, Location, boolean)
	 */
	@Override
	public IValue clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		IValue node = new IValue(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see Node#cloneTo(Node)
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
		
		node.type = type != null ? type.clone() : null;

		if (getProgram() != null && getProgram().getPhase() > SyntaxTree.PHASE_CLASS_DECLARATION)
		{
			node.genericParameter = genericParameter;
		}
		
		return node;
	}
	
	@Override
	public boolean onAfterDecoded()
	{
		if (getProgram() != null && getProgram().getPhase() > SyntaxTree.PHASE_CLASS_DECLARATION)
		{
			genericParameter = searchGenericTypeParameter(0);
		}
		
		return super.onAfterDecoded();
	}
	
	//	@Override
//	public String toString()
//	{
//		return getTypeStringValue();
//	}
	
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
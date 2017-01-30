package net.fathomsoft.nova.tree.generics;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.UnimplementedOperationException;
import net.fathomsoft.nova.tree.*;
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
public class GenericTypeArgument extends IValue implements GenericCompatible
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public GenericTypeArgument(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
//		if (getParentGenericTypeArgumentList() == null)
		{
			addChild(new GenericTypeArgumentList(this, locationIn), this);
		}
	}

	/**
	 * @see net.fathomsoft.nova.tree.GenericCompatible#getGenericTypeArgumentList()
	 */
	@Override
	public GenericTypeArgumentList getGenericTypeArgumentList()
	{
		return getNumVisibleChildren() > 0 ? (GenericTypeArgumentList)getChild(super.getNumDefaultChildren() + 0) : null;
	}
	
	public GenericTypeArgumentList getParentGenericTypeArgumentList()
	{
		return getParent() instanceof GenericTypeArgumentList ? (GenericTypeArgumentList)getParent() : null;
	}
	
	/**
	 * Get the index that the specified argument can be accessed by
	 * at the declaration of the parameters.
	 * 
	 * @return The index.
	 */
	public int getArgumentIndex()
	{
		List implementation = null;
		
		if (getParent() instanceof ClosureParameterList)
		{
			String type = getType(false);
			
			int index = getParentMethod().getMethodGenericTypeParameterDeclaration().getParameterIndex(type);
			
			if (index < 0) 
			{
				index = getParentClass().getGenericTypeParameterDeclaration().getParameterIndex(type);
			}
			
			return index;
		}
		else
		{
			implementation = (List)getParent();//getGenericTypeArgumentList();
		}
		/*else
		{
			implementation = ((GenericCompatible) getParent()).getGenericTypeArgumentList();
		}*/
		
		for (int i = 0; i < implementation.getNumVisibleChildren(); i++)
		{
			if (((Value)implementation.getVisibleChild(i)).getType().equals(getType()))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public String generateGenericType()
	{
		return "";
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
	 * @see net.fathomsoft.nova.tree.generics.GenericTypeParameter#getDefaultType()
	 * 
	 * @return The type that the generic type extends by default.
	 */
	public String getDefaultType()
	{
		NovaMethodDeclaration method = getParentMethod();
		
		if (method != null && method.containsGenericTypeParameter(getType()))
		{
			return method.getGenericTypeParameter(getType()).getDefaultType();
		}
		
		int index = getArgumentIndex();
		
		if (index < 0)
		{
			getArgumentIndex();
		}
		
		return ((Value)getContext()).getTypeClass().getGenericTypeParameterDeclaration().getParameter(index).getDefaultType();
	}
//	
//	@Override
//	public byte getDataType()
//	{
//		return getAncestorOfType(ClosureDeclaration.class) == null ? POINTER : super.getDataType();
//	}
	
	@Override
	public void setPrimitive()
	{
		if (getAncestorOfType(ClosureDeclaration.class) != null)
		{
			super.setPrimitive();
		}
	}
	
	/**
	 * Get the Value instance that this generic argument is manifested as.
	 * 
	 * @return The Value instance.
	 */
	public Value getValue()
	{
		return (Value)getParentGenericTypeArgumentList().getParent();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getGenericReturnType()
	 */
	@Override
	public String getGenericReturnType()
	{
		if (((Value)getContext()).getGenericTypeParameterDeclaration().containsParameter(getType()))
		{
			return getNovaType(this);//getDefaultType();
		}
		
		return getType();
	}
	
	public GenericCompatible getContext()
	{
		if (getAncestorOfType(MethodCall.class) != null)
		{
			if (getParent().getParent() instanceof Instantiation)
			{
				Instantiation i = (Instantiation)getParent().getParent();

				return i;
			}

			MethodCall call = (MethodCall)getAncestorOfType(MethodCall.class);
			
			if (!call.isDecoding())
			{
				return call.getReferenceNode().toValue().getTypeClass();
			}
		}
		
		return getParentClass();
	}
	
	public static String searchGenericType(String str, int start, boolean backwards)
	{
		if (backwards)
		{
			int stack = 0;
			int index = 0;
			
			for (int i = start; i >= 0; i--)
			{
				String c = str.charAt(i) + "";
				
				if (c.equals(GENERIC_END))
				{
					index = stack == 0 ? i : index;
					stack++;
				}
				else if (c.equals(GENERIC_START))
				{
					stack--;
				}
				
				if (stack == 0)
				{
					if (index > 0)
					{
						return str.substring(i + 1, index);
					}
					
					return null;
				}
			}
		}
		else
		{
			throw new UnimplementedOperationException("forwards checking not implemented yet... Looks like its time to do that.");
		}
		
		return null;
	}
	
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		return generateNovaInput(builder, outputChildren, null);
	}
	
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren, Value context)
	{
		//builder.append(getType());
		
		builder.append(getNovaType(context));// + (getDefaultType() != "Object" ? " extends " + getDefaultType() : ""));
		
		/*GenericTypeArgumentList args = getGenericTypeArgumentList();
		
		if (args != null && args.getNumVisibleChildren() > 0)
		{
			args.generateNovaInput(builder);
		}*/
		
		return builder;
	}
	
	public Value getTangibleNode()
	{
		GenericTypeArgumentList list = getParentGenericTypeArgumentList();
		Node n = list != null ? list.getParent() : null;
		
		while (n instanceof GenericTypeArgument)
		{
			list = ((GenericTypeArgument)n).getParentGenericTypeArgumentList();
			n = list != null ? list.getParent() : null;
		}
		
		if (n instanceof Value)
		{
			return (Value)n;
		}
		
		return null;
	}
	
	public boolean isAccessibleFrom(Value context)
	{
		GenericTypeParameter param = getGenericTypeParameter();
		
		if (param instanceof MethodGenericTypeParameter)
		{
			return context.getParentMethod() == param.getParentMethod();
		}
		
		return param != null && context.getParentClass() == param.getParentClass();
	}
	
	@Override
	public Value getNovaTypeValue(Value context)
	{
		if (context instanceof GenericTypeArgument)
		{
			context = ((GenericTypeArgument)context).getTangibleNode();
		}
		
		if (isGenericType())
		{
			GenericTypeArgument arg = getGenericTypeParameter().getCorrespondingArgument(context);
			
			if (arg != null && arg != this && arg != getParent().getParent())
			{
				return arg.getNovaTypeValue(context);
			}
			
			GenericTypeArgument value = clone(getParent(), getLocationIn(), false, true);
			
			if (context == null || getParentClass() != context.getParentClass())
			{
				value.setTypeValue(getDefaultType());
			}
			
			return value;
		}
		
		if (getGenericTypeArgumentList() != null && getGenericTypeArgumentList().getNumVisibleChildren() > 0)
		{
			GenericTypeArgument clone = clone(getParent(), getLocationIn(), true, true);
			
			for (int i = 0; i < clone.getGenericTypeArgumentList().getNumVisibleChildren(); i++)
			{
				clone.getGenericTypeArgumentList().getVisibleChild(i)
					.replaceWith(getGenericTypeArgumentList().getVisibleChild(i).getNovaTypeValue(context).clone(getGenericTypeArgumentList(), getLocationIn(), true, true));
			}
			
			return clone;
		}
		
		return clone(getParent(), getLocationIn(), false, true);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public GenericTypeArgument clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		GenericTypeArgument node = new GenericTypeArgument(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public GenericTypeArgument cloneTo(GenericTypeArgument node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link GenericTypeArgument} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public GenericTypeArgument cloneTo(GenericTypeArgument node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the {@link GenericTypeArgument} class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
	
	public String toString(boolean carets)
	{
		String str = getType();
		
		GenericTypeArgumentList args = getGenericTypeArgumentList();
		
		if (args != null && args.getNumVisibleChildren() > 0)
		{
			str += GENERIC_START + args.toString() + GENERIC_END;
		}
		else if (carets)
		{
			str = GENERIC_START + str + GENERIC_END;
		}
		
		return str;
	}
	
	public String toString()
	{
		return toString(false);
	}
}
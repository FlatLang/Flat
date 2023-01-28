package flat.tree.generics;

import flat.TestContext;
import flat.error.UnimplementedOperationException;
import flat.tree.*;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;
import flat.tree.GenericCompatible;
import flat.tree.IValue;
import flat.tree.Node;
import flat.tree.Value;

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
public class GenericTypeArgument extends IIdentifier implements GenericCompatible
{
	public boolean allocatedOnHeap;
	public boolean autoAdded;

	/**
	 * @see Node#Node(Node, Location)
	 */
	public GenericTypeArgument(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		addChild(new GenericTypeArgumentList(this, locationIn), this);
	}

	public GenericTypeArgument(Node temporaryParent, Location locationIn, GenericTypeParameter parameter) {
		this(temporaryParent, locationIn);
		genericParameter = parameter;
	}

	@Override
	public synchronized void onReplaced(Node parent, Node replacement)
	{
		if (this != replacement && getTypeObject() instanceof FunctionType)
		{
			FunctionType type = (FunctionType)getTypeObject();
			
			type.closure.reference = (Identifier)replacement;
			type.closure.unregister();
		}
		
		super.onReplaced(parent, replacement);
	}
	
	@Override
	public boolean isExternalType()
	{
		return false;
	}
	
	@Override
	public String getName()
	{
		if (type instanceof FunctionType)
		{
			return ((FunctionType)type).value;
		}
		
		return getType();
	}
	
	@Override
	public boolean isAllocatedOnHeap()
	{
		return allocatedOnHeap;
	}
	
	/**
	 * @see GenericCompatible#getGenericTypeArgumentList()
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
			implementation = (List)getParent();
		}
		
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
	 * @see IValue#setTypeValue(java.lang.String)
	 */
	@Override
	public void setTypeValue(String type)
	{
		super.setTypeValue(type);
	}
	
	/**
	 * Get the default type that this generic type extends.
	 * @see GenericTypeParameter#getDefaultType()
	 * 
	 * @return The type that the generic type extends by default.
	 */
	public String getDefaultType()
	{
		return getGenericTypeParameter().getDefaultType();
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
	 * @see Value#getGenericReturnType()
	 */
	@Override
	public String getGenericReturnType(Value context, boolean checkCast)
	{
		if (((Value)getContext()).getGenericTypeParameterDeclaration().containsParameter(getType()))
		{
			return getFlatType(this, true, true);//getDefaultType();
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
	public GenericTypeParameter getGenericTypeParameter(boolean checkArray)
	{
//		if (getParentGenericTypeArgumentList() != null && getParentGenericTypeArgumentList().parent instanceof GenericTypeArgument)
//		{
//			GenericTypeArgument arg = (GenericTypeArgument)getParentGenericTypeArgumentList().parent;
//			
//			if (arg.getTypeClass() != null)
//			{
//				return arg.getTypeClass().getGenericTypeParameter(getIndex());
//			}
//		}

		if (getParentMethod() != null) {
			GenericTypeParameter methodParam = getParentMethod().getGenericTypeParameter(getType());

			if (methodParam != null) {
				return methodParam;
			}
		}

		return super.getGenericTypeParameter(checkArray);
	}

	@Override
	public GenericTypeParameter searchGenericTypeParameter(int index, boolean checkArray) {
		if (genericParameter == null && getParent() instanceof ClosureDeclaration == false && getParent() instanceof VariableDeclaration && getParent() instanceof ClassDeclaration == false) {
			ClassDeclaration c = ((Value) getParent()).getTypeClass();

			if (c != null) {
				GenericTypeParameter param = c.getGenericTypeParameter(index);

				if (param != null) {
					return param;
				}
			}
		}

		return super.searchGenericTypeParameter(index, checkArray);
	}

	@Override
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren)
	{
		return generateFlatInput(builder, outputChildren, null);
	}
	
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren, Value context)
	{
		return builder.append(getFlatType(context));
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
	
//	@Override
//	public Value getFlatTypeValue(Value context)
//	{
//		if (context instanceof GenericTypeArgument)
//		{
//			context = ((GenericTypeArgument)context).getTangibleNode();
//		}
//		
//		if (isGenericType())
//		{
//			GenericTypeArgument arg = getGenericTypeParameter().getCorrespondingArgument(context);
//			
//			if (arg != null && arg != this && arg != getParent().getParent())
//			{
//				return arg.getFlatTypeValue(context);
//			}
//			
//			GenericTypeArgument value = clone(getParent(), getLocationIn(), false, true);
//			
//			if (context == null || getParentClass() != context.getParentClass())
//			{
//				value.setTypeValue(getDefaultType());
//			}
//			
//			return value;
//		}
//		
//		if (getGenericTypeArgumentList() != null && getGenericTypeArgumentList().getNumVisibleChildren() > 0)
//		{
//			GenericTypeArgument clone = clone(getParent(), getLocationIn(), true, true);
//			
//			for (int i = 0; i < clone.getGenericTypeArgumentList().getNumVisibleChildren(); i++)
//			{
//				clone.getGenericTypeArgumentList().getVisibleChild(i)
//					.replaceWith(getGenericTypeArgumentList().getVisibleChild(i).getFlatTypeValue(context).clone(getGenericTypeArgumentList(), getLocationIn(), true, true));
//			}
//			
//			return clone;
//		}
//		
//		return clone(getParent(), getLocationIn(), false, true);
//	}
	
	/**
	 * @see Node#clone(Node, Location, boolean)
	 */
	@Override
	public GenericTypeArgument clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		GenericTypeArgument node = new GenericTypeArgument(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see Node#cloneTo(Node)
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

		node.allocatedOnHeap = allocatedOnHeap;
		node.autoAdded = autoAdded;
		
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

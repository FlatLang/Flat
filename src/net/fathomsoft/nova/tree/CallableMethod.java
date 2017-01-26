package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.tree.MethodList.SearchFilter;
import net.fathomsoft.nova.tree.annotations.ImpureFunctionAnnotation;
import net.fathomsoft.nova.tree.annotations.PureFunctionAnnotation;
import net.fathomsoft.nova.tree.generics.GenericTypeParameter;
import net.fathomsoft.nova.tree.variables.ObjectReference;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

import java.util.function.Consumer;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.14 Jul 1, 2014 at 11:41:22 PM
 * @version	v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public interface CallableMethod
{
	ClassDeclaration getTypeClass();
	
	/**
	 * Get whether or not a call to the method would need to pass a
	 * reference to itself to the method as an argument.
	 * 
	 * @return Whether or not a method call needs to pass a reference.
	 */
	public boolean isInstance();
	
	default PureFunctionAnnotation getPureAnnotation()
	{
		return (PureFunctionAnnotation)((Node)this).getAnnotationOfType(PureFunctionAnnotation.class, false, true);
	}
	
	default boolean isPure()
	{
		return getPureAnnotation() != null;
	}
	
	default ImpureFunctionAnnotation getImpureAnnotation()
	{
		return (ImpureFunctionAnnotation)((Node)this).getAnnotationOfType(ImpureFunctionAnnotation.class, false, true);
	}
	
	default boolean isImpure()
	{
		return getImpureAnnotation() != null;
	}
	
	default void setImpure()
	{
		if (!isImpure())
		{
			((Node)this).addAnnotation(new ImpureFunctionAnnotation((Node)this, Location.INVALID));
		}
	}
	
	/**
	 * Get whether or not the method is static.
	 * 
	 * @return Whether or not the method is static.
	 */
	public boolean isStatic();
	
	/**
	 * Get whether or not the method is external.
	 * 
	 * @return Whether or not the method is external.
	 */
	public boolean isExternal();
	
	/**
	 * Get the name of the method.
	 * 
	 * @return The name of the method.
	 */
	public String getName();
	
	/**
	 * Get the data type of the method.
	 * 
	 * @return The data type of the method.
	 */
	public byte getDataType(boolean checkGeneric);
	
	/**
	 * @see net.fathomsoft.nova.tree.variables.VariableDeclaration#getArrayDimensions()
	 * 
	 * @return The amount of dimensions that the array has, if any.
	 */
	public int getArrayDimensions();
	
	/**
	 * @see net.fathomsoft.nova.tree.NovaMethodDeclaration#isVirtual()
	 * 
	 * @return Whether or not the method is virtual.
	 */
	public boolean isVirtual();
	
	public boolean isVirtualTypeKnown();
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getParentClass()
	 * 
	 * @return The nearest ClassDeclaration instance that contains this node.
	 */
	public ClassDeclaration getParentClass();
	
	/**
	 * Get the list of Values that represents the parameters for the
	 * Method.
	 * 
	 * @return The list of Values that represents the parameters for the
	 * 		Method.
	 */
	public ParameterList getParameterList();
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getGenericTypeParameter()
	 */
	public GenericTypeParameter getGenericTypeParameter();
	
	/**
	 * Check to see if the given types are compatible with the Method's
	 * parameters.
	 * 
	 * @param types The types to check against the parameters.
	 * @return Whether or not the types are compatible with the
	 * 		parameters.
	 */
	public default boolean areCompatibleParameterTypes(GenericCompatible context, Value[] types)
	{
		return areCompatibleParameterTypes(context, types, false);
	}
	
	public default boolean areCompatibleParameterTypes(GenericCompatible context, Value[] types, boolean reverse)
	{
		return areCompatibleParameterTypes(new GenericCompatible[] { context }, types, reverse);
	}
	
	public default boolean areCompatibleParameterTypes(GenericCompatible[] contexts, Value[] types)
	{
		return areCompatibleParameterTypes(contexts, types, false);
	}
	
	public default boolean areCompatibleParameterTypes(GenericCompatible[] contexts, Value[] types, boolean reverse)
	{
		return areCompatibleParameterTypes(contexts, true, types, reverse);
	}
	
	/**
	 * Check to see if the given types are compatible with the Method's
	 * parameters.
	 * 
	 * @param searchGeneric Whether or not to search for the actual generic
	 * 		return type.
	 * @param types The types to check against the parameters.
	 * @return Whether or not the types are compatible with the
	 * 		parameters.
	 */
	public default boolean areCompatibleParameterTypes(GenericCompatible[] contexts, boolean searchGeneric, Value[] types)
	{
		return areCompatibleParameterTypes(contexts, searchGeneric, types, false);
	}
	
	public default boolean areCompatibleParameterTypes(GenericCompatible[] contexts, boolean searchGeneric, Value[] types, boolean reverse)
	{
		return areCompatibleParameterTypes(contexts, searchGeneric, null, types, reverse);
	}
	
	public default boolean areCompatibleParameterTypes(GenericCompatible[] contexts, boolean searchGeneric, SearchFilter filter, Value[] types)
	{
		return areCompatibleParameterTypes(contexts, searchGeneric, filter, types, false);
	}
	
	public default boolean areCompatibleParameterTypes(GenericCompatible[] contexts, boolean searchGeneric, SearchFilter filter, Value[] types, boolean reverse)
	{
		if (getParameterList().getNumVisibleChildren() != types.length)
		{
			if (filter != null && !filter.allowMoreParameters && (types.length < getParameterList().getNumRequiredParameters() || types.length > getParameterList().getNumVisibleChildren()))
			{
				return false;
			}
		}
		
		Value[] required = reverse ? types : getParameterList().getTypes();
		Value[] given = !reverse ? types : getParameterList().getTypes();
		
		if (SyntaxUtils.areTypesCompatible(contexts, required, given, searchGeneric))
		{
			if (filter != null && filter.requireExactMatch && SyntaxUtils.getParametersDistance(contexts.length > 0 ? (Value)contexts[0] : null, required, given, filter.defaultGeneric) > 0)
			{
				required[0].getNovaType((Value)contexts[0]);
				return false;
			}
			
			return true;
		}
		
		return false;
	}
	
	public boolean isGenericType();
	
	public NovaMethodDeclaration getRootDeclaration();
	
	public ObjectReference getObjectReference();
}
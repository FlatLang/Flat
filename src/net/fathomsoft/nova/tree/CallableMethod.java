package net.fathomsoft.nova.tree;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.14 Jul 1, 2014 at 11:41:22 PM
 * @version	v0.2.19 Jul 26, 2014 at 12:30:24 AM
 */
public interface CallableMethod
{
	/**
	 * Get whether or not a call to the method would need to pass a
	 * reference to itself to the method as an argument.
	 * 
	 * @return Whether or not a method call needs to pass a reference.
	 */
	public boolean isInstance();
	
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
	public byte getDataType();
	
	/**
	 * Get the type returned by the method.
	 * 
	 * @return The type returned by the method.
	 */
	public String getType();
	
	/**
	 * @see net.fathomsoft.nova.tree.variables.VariableDeclaration#getArrayDimensions()
	 */
	public int getArrayDimensions();
	
	/**
	 * @see net.fathomsoft.nova.tree.MethodDeclaration#isVirtual()
	 */
	public boolean isVirtual();
	
	/**
	 * @see net.fathomsoft.nova.tree.MethodDeclaration#generateCVirtualMethodName()
	 */
	public StringBuilder generateCVirtualMethodName();
	
	/**
	 * @see net.fathomsoft.nova.tree.MethodDeclaration#generateCVirtualMethodName(StringBuilder)
	 */
	public StringBuilder generateCVirtualMethodName(StringBuilder builder);
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getParentClass()
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
}
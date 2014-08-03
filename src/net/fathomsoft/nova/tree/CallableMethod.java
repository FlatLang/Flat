package net.fathomsoft.nova.tree;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.14 Jul 1, 2014 at 11:41:22 PM
 * @version	v0.2.21 Jul 30, 2014 at 1:45:00 PM
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
}
package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.ClassDeclaration;
import net.fathomsoft.nova.tree.Identifier;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Location;

/**
 * Identifier extension that represents the use of a variable
 * type. Harnesses the needed information of each variable, such as
 * whether or not it is constant, external, or an array, and its type.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:02:42 PM
 * @version	v0.2.29 Aug 29, 2014 at 3:17:45 PM
 */
public class Variable extends Identifier
{
	private VariableDeclaration	declaration;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Variable(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Compare the specified variable with the given one to see if they
	 * come from the same declaration.
	 * 
	 * @param other The variable to compare with.
	 * @return Whether or not the variables come from the same
	 * 		declaration.
	 */
	public boolean isSameVariable(Variable other)
	{
		VariableDeclaration first  = getDeclaration();
		VariableDeclaration second = other.getDeclaration();
		
		return first == second;
	}
	
	/**
	 * Get whether or not the specified Variable is a local variable. It
	 * is a local variable if it was declared inside a method.
	 * TODO: Can optimize to realize that if a parent is a method only
	 * node, stop there and return true.
	 * 
	 * @return Whether or not the specified Variable is local.
	 */
	public boolean isLocal()
	{
		return getDeclaration().getParentMethod() != null;
	}
	
	/**
	 * Get the ClassDeclaration instance that declared this variable.
	 * 
	 * @return The ClassDeclaration instance that declared this variable.
	 */
	public ClassDeclaration getDeclaringClass()
	{
		VariableDeclaration var = getDeclaration();
		
		return var.getParentClass(true);
	}
	
	/**
	 * Get the Instance/LocalDeclaration that declares the
	 * specified variable.
	 * 
	 * @return The Instance/LocalDeclaration that declares the
	 * 		specified variable.
	 */
	public VariableDeclaration getDeclaration()
	{
		return declaration;
	}
	
	/**
	 * Set the Instance/LocalDeclaration that declares the
	 * specified variable.
	 * 
	 * @param declaration The Instance/LocalDeclaration that declares the
	 * 		specified variable.
	 */
	public void setDeclaration(VariableDeclaration declaration)
	{
		this.declaration = declaration;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#getName()
	 */
	@Override
	public String getName()
	{
		return declaration.getName();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#setName(java.lang.String, boolean)
	 */
	@Override
	public void setName(String name, boolean forceOriginal)
	{
		declaration.setName(name, forceOriginal);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#willForceOriginalName()
	 */
	@Override
	public boolean willForceOriginalName()
	{
		return declaration.willForceOriginalName() || isExternal();
	}
	
	/**
	 * Get whether or not the variable is external. For more information
	 * on external variables, see {@link #setExternal(boolean)}.
	 * 
	 * @return Whether or not the variable is external.
	 */
	public boolean isExternal()
	{
		return declaration.isExternal();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#setForceOriginalName(boolean)
	 */
	@Override
	public void setForceOriginalName(boolean forceOriginal)
	{
		declaration.setForceOriginalName(forceOriginal);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getArrayDimensions()
	 */
	@Override
	public int getArrayDimensions()
	{
		return declaration.getArrayDimensions();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#setArrayDimensions(int)
	 */
	@Override
	public void setArrayDimensions(int arrayDimensions)
	{
		declaration.setArrayDimensions(arrayDimensions);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getType()
	 */
	@Override
	public String getType()
	{
		return declaration.getType();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#setType(java.lang.String, boolean, boolean, boolean)
	 */
	@Override
	public boolean setType(String type, boolean require, boolean checkType, boolean checkExternal)
	{
		return declaration.setType(type, require, checkType, checkExternal);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getDataType()
	 */
	@Override
	public byte getDataType()
	{
		return declaration.getDataType();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#setDataType(byte)
	 */
	@Override
	public void setDataType(byte type)
	{
		declaration.setDataType(type);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.variables.VariableDeclaration#isVolatile()
	 */
	public boolean isVolatile()
	{
		return declaration.isVolatile();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.variables.VariableDeclaration#setVolatile(boolean)
	 */
	public void setVolatile(boolean volatileVal)
	{
		declaration.setVolatile(volatileVal);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Variable clone(Node temporaryParent, Location locationIn)
	{
		Variable node = new Variable(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link Variable} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Variable cloneTo(Variable node)
	{
		super.cloneTo(node);
		
		node.declaration = declaration;
		
		return node;
	}
	
	/**
	 * Test the Variable class type to make sure everything
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
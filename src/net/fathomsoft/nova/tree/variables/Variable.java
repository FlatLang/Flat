package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.Accessible;
import net.fathomsoft.nova.tree.AccessorMethod;
import net.fathomsoft.nova.tree.Assignment;
import net.fathomsoft.nova.tree.ClassDeclaration;
import net.fathomsoft.nova.tree.GenericCompatible;
import net.fathomsoft.nova.tree.Identifier;
import net.fathomsoft.nova.tree.MethodCall;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.tree.generics.GenericTypeArgument;
import net.fathomsoft.nova.tree.generics.GenericTypeParameter;
import net.fathomsoft.nova.util.Location;

/**
 * Identifier extension that represents the use of a variable
 * type. Harnesses the needed information of each variable, such as
 * whether or not it is constant, external, or an array, and its type.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:02:42 PM
 * @version	v0.2.43 Jan 16, 2015 at 11:59:17 AM
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
	
	@Override
	public boolean isConstant()
	{
		return false;
	}
	
	@Override
	public boolean isConsistent()
	{
		return true;
	}
	
	@Override
	public boolean isValid()
	{
		return super.isValid() && getDeclaration() != null && getDeclaration().isValid();
	}
	
	@Override
	public String getGenericReturnType()
	{
		if (isGenericType())
		{
			Accessible ref = getReferenceNode();
			
			if (ref instanceof Variable)
			{
				VariableDeclaration decl = ((Variable)ref).getDeclaration();
				
				GenericTypeArgument type = decl.getGenericTypeArgumentInstance(getType(), this);
				
				if (type.isGenericType())
				{
					return type.getDefaultType();
				}
				
				return type.getType();
			}
			
			return getGenericTypeParameter().getDefaultType();
		}
		
		throw new RuntimeException("Generic return type requested from non-generic type.");
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
		return isValid() && getDeclaration().getParentMethod() != null;
	}
	
	/**
	 * Get the ClassDeclaration instance that declared this variable.
	 * 
	 * @return The ClassDeclaration instance that declared this variable.
	 */
//	public ClassDeclaration getDeclaringClass()
//	{
//		VariableDeclaration var = getDeclaration();
//		
//		Accessible ref = getReferenceNode();
//		FileDeclaration file = null;
//		
//		if (getParent() == ref.getParent())//ref.getName().equals(ParameterList.OBJECT_REFERENCE_IDENTIFIER))
//		{
//			file = getFileDeclaration();
//		}
//		else
//		{
//			file = ref.getDeclaringClass().getFileDeclaration();
//		}
//		
//		if (var.isGenericType())
//		{
//			return SyntaxUtils.getImportedClass(file, var.getGenericReturnType());
//		}
//		
//		ClassDeclaration clazz = SyntaxUtils.getImportedClass(file, var.getType());
//		
//		if (clazz == null)
//		{
//			return super.getDeclaringClass();
//		}
//		
//		return clazz;
//	}
	
	public GenericCompatible getContext()
	{
		return getDeclaration().getContext();
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
		if (declaration != null)
		{
			return declaration;
		}
		
		return null;
	}
	
	@Override
	public ClassDeclaration getDeclaringClass()
	{
		return getDeclaration().getParentClass();
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
	 * @see net.fathomsoft.nova.tree.Identifier#doesForceOriginalName()
	 */
	@Override
	public boolean doesForceOriginalName()
	{
		return declaration.doesForceOriginalName() || isExternal();
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
		if (declaration == null)
		{
			return 0;
		}
		
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
	
	public void swapNames(Variable other)
	{
		swapNames(other.getDeclaration());
	}
	
	public void swapNames(VariableDeclaration other)
	{
		getDeclaration().swapNames(other);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getType()
	 */
	@Override
	public String getType()
	{
		return declaration == null ? null : declaration.getType();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.AbstractValue#setTypeValue(java.lang.String)
	 */
	@Override
	public void setTypeValue(String type)
	{
		declaration.setTypeValue(type);
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
	 * @see net.fathomsoft.nova.tree.Value#getGenericTypeParameter()
	 */
	@Override
	public GenericTypeParameter getGenericTypeParameter()
	{
		if (declaration == null)
		{
			return null;
		}
		
		return declaration.getGenericTypeParameter();
	}
	
	public boolean doesUseGenericTypes()
	{
		return getDeclaration().getGenericTypeParameterDeclaration().getNumParameters() > 0;
	}
	
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			if (getDeclaration() != null)
			{
				if (!getDeclaration().isAccessible())
				{
					SyntaxMessage.error("The field '" + getName() + "' is not accessible", this, false);
					
					getParent().removeChild(this);
					
					return result.errorOccurred();
				}
			}
			
			if (getDeclaration() instanceof FieldDeclaration)
			{
				FieldDeclaration field    = (FieldDeclaration)getDeclaration();
				AccessorMethod   accessor = field.getAccessorMethod();
				
				if (accessor != null && !accessor.isDisabled() && getParentMethod() != accessor)
				{
					MethodCall access = MethodCall.decodeStatement(getParent(), getName() + "()", getLocationIn(), true, false, accessor);
					
					getParent().replace(this, access);
					
					access.inheritChildren(this);
					
					result.returnedNode = access;
					
					return result;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Variable clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		Variable node = new Variable(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public Variable cloneTo(Variable node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link Variable} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Variable cloneTo(Variable node, boolean cloneChildren)
	{
		node.declaration = declaration;
		
		super.cloneTo(node, cloneChildren);
		
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
	
	public String toString()
	{
		return generateNovaInput() + " of type " + getDeclaration().generateNovaType();
	}
}
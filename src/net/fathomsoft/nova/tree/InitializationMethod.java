package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.util.Location;

/**
 * {@link BodyMethodDeclaration} extension that represents
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.27 Aug 6, 2014 at 5:12:43 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class InitializationMethod extends BodyMethodDeclaration
{
	public static final String	SUPER_IDENTIFIER = "super";
	public static final String	IDENTIFIER       = ParameterList.OBJECT_REFERENCE_IDENTIFIER;
	
	public Constructor constructor;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public InitializationMethod(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get whether or not the specified Method has overridden a method
	 * from a super class
	 * 
	 * @return Whether or not the specified Method has overridden a
	 * 		method from a super class.
	 */
	public boolean doesOverride()
	{
		return false;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.NovaMethodDeclaration#isOverridden()
	 */
	@Override
	public boolean isOverridden()
	{
		return false;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.NovaMethodDeclaration#isVirtual()
	 */
	@Override
	public boolean isVirtual()
	{
		return false;
	}
	
	@Override
	public boolean isUserMade(boolean checkAncestor)
	{
		return false;
	}
	
	public void createFrom(Constructor constructor)
	{
		String name   = getParentClass(true).generateTemporaryMethodName();
		String params = constructor.getParameterList().generateNovaInput().toString();
		
		NovaMethodDeclaration method = decodeStatement(getParentClass(true), "public " + name + '(' + params + ')', getLocationIn(), true);

		if (method != null)
		{
			method.setName(ParameterList.OBJECT_REFERENCE_IDENTIFIER);
			method.cloneTo(this);
			setType(constructor);
		}
		else
		{
			decodeStatement(getParentClass(true), "public " + name + '(' + params + ')', getLocationIn(), true);
		}
	}
	
	@Override
	public NovaMethodDeclaration getConversionTarget()
	{
		if (constructor.genericOverload != null)
		{
			return ((Constructor)constructor.genericOverload).initMethod;
		}
		
		return null;
	}
	
	@Override
	public NovaMethodDeclaration getConversionTargetContext()
	{
		return constructor.genericOverload;
	}
	
	@Override
	public boolean doesConvertToPrimitive()
	{
		return getConversionTarget() != null;
	}
	
	@Override
	public ValidationResult validate(int phase)
	{
		setDataType(POINTER);
		return super.validate(phase);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public InitializationMethod clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		InitializationMethod node = new InitializationMethod(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public InitializationMethod cloneTo(InitializationMethod node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link InitializationMethod} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public InitializationMethod cloneTo(InitializationMethod node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.constructor = constructor;
		
		return node;
	}
	
	/**
	 * Test the {@link InitializationMethod} class type to make sure everything
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
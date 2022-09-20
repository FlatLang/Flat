package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.util.Location;

public class InitializationMethodValidator extends BodyMethodDeclarationValidator
{
	public static final String	SUPER_IDENTIFIER = "super";
	public static final String	IDENTIFIER       = ParameterList.OBJECT_REFERENCE_IDENTIFIER;
	
	public Constructor constructor;
	
	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public InitializationMethodValidator(NodeValidator temporaryParent, Location locationIn)
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
	 * @see FlatMethodDeclarationValidator#isOverridden()
	 */
	@Override
	public boolean isOverridden()
	{
		return false;
	}
	
	/**
	 * @see FlatMethodDeclarationValidator#isVirtual()
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
		String params = constructor.getParameterList().generateFlatInput().toString();
		
		FlatMethodDeclarationValidator method = decodeStatement(getParentClass(true), "public " + name + '(' + params + ')', getLocationIn(), true);

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
	public FlatMethodDeclarationValidator getConversionTarget()
	{
		if (constructor.genericOverload != null)
		{
			return ((Constructor)constructor.genericOverload).initMethod;
		}
		
		return null;
	}
	
	@Override
	public FlatMethodDeclarationValidator getConversionTargetContext()
	{
		return constructor.genericOverload;
	}
	
	@Override
	public boolean doesConvertToPrimitive()
	{
		return getConversionTarget() != null;
	}
	
	public ValidationResult validate(int phase)
	{
		setDataType(POINTER);
		return super.validate(phase);
	}
	
	/**
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public InitializationMethodValidator clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		InitializationMethodValidator node = new InitializationMethodValidator(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
	 */
	public InitializationMethodValidator cloneTo(InitializationMethodValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link InitializationMethodValidator} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public InitializationMethodValidator cloneTo(InitializationMethodValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.constructor = constructor;
		
		return node;
	}
	
	/**
	 * Test the {@link InitializationMethodValidator} class type to make sure everything
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
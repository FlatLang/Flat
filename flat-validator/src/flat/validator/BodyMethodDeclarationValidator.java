package flat.validator;

import flat.Flat;
import flat.TestContext;
import flat.ValidationResult;
import flat.util.Location;
import flat.util.SyntaxUtils;

public class BodyMethodDeclarationValidator extends FlatMethodDeclarationValidator
{
	private boolean parsedConvertedContext;
	
	/**
	 * Instantiate and initialize default data.
	 * 
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public BodyMethodDeclarationValidator(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		Scope scope = new Scope(this, locationIn.asNew());
		
		setScope(scope);
	}
	
	@Override
	public String getType(boolean checkCast)
	{
		if (super.getType(checkCast) == null)
		{
			if (shorthandAction != null)
			{
				decodeShorthandAction();
			}
			else if (genericOverload != null)
			{
				genericOverload.getType(checkCast);
			}
		}
		
		return super.getType(checkCast);
	}
	
	/**
	 * @see NodeValidator#getNumDefaultChildren()
	 */
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}
	
	/**
	 * @see NodeValidator#getScope()
	 */
	@Override
	public Scope getScope()
	{
		return (Scope)getChild(super.getNumDefaultChildren() + 0);
	}
	
	/**
	 * Get whether or not the specified MethodDeclaration contains a Body.
	 * 
	 * @return Whether or not the specified MethodDeclaration contains a
	 * 		Body.
	 */
	public boolean containsBody()
	{
		return true;
	}
	
	@Override
	public boolean isInstance()
	{
		return super.isInstance() || getName().equals(Constructor.IDENTIFIER) && getParentClass().isPropertyTrue("functionMap");
	}
	
	/**
	 * Decode the given statement into a Method instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>public findPerson(String name, Int age) -&gt; Person</li>
	 * 	<li>private calculateArea(Int width, Int height) -&gt; Int</li>
	 * 	<li>public doNothing()</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Method instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Method.
	 */
	public static BodyMethodDeclarationValidator decodeStatement(NodeValidator parent, String statement, Location location, boolean require)
	{
		FlatMethodDeclarationValidator method = FlatMethodDeclarationValidator.decodeStatement(parent, statement, location, false);
		
		if (method != null)
		{
			BodyMethodDeclarationValidator n = new BodyMethodDeclarationValidator(parent, location);
			
			method.cloneTo(n);
			n.getObjectReference(true);
			n.setLocationIn(location);
			n.uniqueID = 1;
			
			if (!n.getParentClass().isPropertyTrue("functionMap"))
			{
				n.addDefaultParameterInitializations();
			}
			
			if (n.getParentClass() instanceof Trait)
			{
				return new InterfaceMethodDeclarationValidator(parent, location, n);
			}
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * Generate a Method with the given parent and location for
	 * temporary use.
	 * 
	 * @param parent The node to set as the Method's parent.
	 * @param locationIn The location to set as the Method's location.
	 * @return The generated temporary Method.
	 */
	public static BodyMethodDeclarationValidator generateTemporaryMethod(NodeValidator parent, Location locationIn)
	{
		String name = parent.getParentClass(true).generateTemporaryMethodName();
		
		BodyMethodDeclarationValidator methodDeclaration = decodeStatement(parent, name + "()", locationIn, true);
		
		return methodDeclaration;
	}
	
	public static BodyMethodDeclarationValidator generateTemporaryHierarchy(Flat controller)
	{
		ClassDeclarationValidator c = ClassDeclarationValidator.generateTemporaryHierarchy(controller);
		
		BodyMethodDeclarationValidator method = generateTemporaryMethod(c, Location.INVALID);
		c.addChild(method);
		
		return method;
	}
	
	public void moveShorthandActionToEnd()
	{
		if (usedShorthandAction)
		{
			getScope().addChild(getScope().getFirstStatement().detach());
		}
	}
	
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase >= SyntaxTree.PHASE_METHOD_CONTENTS)
		{
			convertFunctionContents();
			
			if (phase == SyntaxTree.PHASE_METHOD_CONTENTS && genericOverload == null)
			{
				moveShorthandActionToEnd();
			}
			
			if (phase == SyntaxTree.PHASE_PRE_GENERATION)
			{
				SyntaxTree.validateNodes(getScope(), SyntaxTree.PHASE_METHOD_CONTENTS);
			}
		}
		
		return result;
	}
	
	public FlatMethodDeclarationValidator getConversionTarget()
	{
		FlatMethodDeclarationValidator overload = genericOverload;
		
		while (overload != null && overload.genericOverload != null)
		{
			overload = overload.genericOverload;
		}
		
		return overload;
	}
	
	public FlatMethodDeclarationValidator getConversionTargetContext()
	{
		return getConversionTarget();
	}
	
	public boolean doesConvertToPrimitive()
	{
		return genericOverload != null;
	}
	
	public boolean convertFunctionContents()
	{
		if (!Flat.PRIMITIVE_OVERLOADS) {
			return false;
		}
		if (!parsedConvertedContext && doesConvertToPrimitive() && getConversionTargetContext().isInTree() && getConversionTarget().getScope() != null)
		{
			SyntaxUtils.parseConvertedContentsTo(getConversionTarget().getScope(), getConversionTargetContext(), this, this);
			
			parsedConvertedContext = true;
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public BodyMethodDeclarationValidator clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		BodyMethodDeclarationValidator node = new BodyMethodDeclarationValidator(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	public BodyMethodDeclarationValidator cloneTo(BodyMethodDeclarationValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		for (DefaultParameterInitialization param : node.getScope().getDefaultParameterInitializations())
		{
			param.parameter = node.getParameter(param.parameter.getIndex());
		}
		
		return node;
	}
	
	/**
	 * Test the MethodDeclaration class type to make sure everything
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
package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.tree.variables.FieldDeclaration;
import flat.tree.variables.VariableDeclaration;
import flat.util.Location;

public abstract class PropertyMethodValidator extends BodyMethodDeclarationValidator
{
	public static final String PARAMETER_NAME = "value";
	
	private boolean disabled;
	
	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public PropertyMethodValidator(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, new Location(locationIn.getLineNumber() + temporaryParent.getLocationIn().getLineNumber(), locationIn.getOffset(), locationIn.getStart(), locationIn.getEnd()));
	}
	
	public VariableDeclaration getDeclaration()
	{
		NodeValidator n = getParent().getAncestorWithScopeOrClass();
		
		while (n != null)
		{
			if (n instanceof ClassDeclarationValidator)
			{
				ClassDeclarationValidator c = (ClassDeclarationValidator)n;
				
				FieldDeclaration f = c.getField(getName());
				
				if (f != null)
				{
					return f;
				}
			}
			else if (n.getScope() != null)
			{
				LocalDeclarationValidator d = n.getScope().getVariableList().getVariable(getName(), n.getScope());
				
				if (d != null)
				{
					return d;
				}
			}
			
			n = n.getParent().getAncestorWithScopeOrClass();
		}
		
		return null;
	}
	
	public FieldDeclaration getParentField()
	{
		return (FieldDeclaration)getAncestorOfType(FieldDeclaration.class);
	}
	
	public boolean isDisabled()
	{
		return disabled;
	}
	
	public void setDisabled(boolean disabled)
	{
		this.disabled = disabled;
		
		correspondingPrimitiveOverloads.forEach(x -> {
			((PropertyMethodValidator)x).disabled = disabled;
		});
	}
	
	@Override
	public FlatMethodDeclarationValidator[] getOverridingMethods()
	{
		if (isDisabled())
		{
			return new FlatMethodDeclarationValidator[0];
		}
		
		return super.getOverridingMethods();
	}
	
	@Override
	public FlatMethodDeclarationValidator getOverriddenMethod()
	{
		if (isDisabled())
		{
			return null;
		}
		
		return super.getOverriddenMethod();
	}
	
	/**
	 * Validate the parameters of the method header.
	 * 
	 * @param phase The phase that the node is being validated in.
	 * @see NodeValidator#validate(int)
	 */
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		return result;
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
	 */
	public PropertyMethodValidator cloneTo(PropertyMethodValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link PropertyMethodValidator} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public PropertyMethodValidator cloneTo(PropertyMethodValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.disabled = disabled;
		
		return node;
	}
	
	/**
	 * Test the {@link PropertyMethodValidator} class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
	
	public abstract String getMethodPrefix();
}
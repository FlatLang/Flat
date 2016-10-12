package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;

/**
 * {@link Node} extension that represents
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.37 Oct 18, 2014 at 9:00:50 PM
 * @version	v0.2.39 Dec 7, 2014 at 3:17:17 AM
 */
public abstract class ArrayOverloadMethod extends BodyMethodDeclaration
{
	public static final String PARAMETER_NAME = "value";
	
	private boolean disabled;
	
	/**
	 * @see Node#Node(Node, Location)
	 */
	public ArrayOverloadMethod(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);//new Location(locationIn.getLineNumber() + temporaryParent.getLocationIn().getLineNumber(), locationIn.getOffset(), locationIn.getStart(), locationIn.getEnd()));
		
		setVisibility(PUBLIC);
	}
	
	public ArrayBracketOverload getArrayBracketOverload()
	{
		return getParentClass().arrayBracketOverload;
	}
	
	public void addIndexParameter()
	{
		Parameter p = Parameter.decodeStatement(this, getArrayBracketOverload().getIndexValue().generateNovaInput().toString(), getLocationIn().asNew(), true);
		getArrayBracketOverload().getIndexValue().cloneTo(p, false);
		
		getParameterList().addChild(p);
	}
	
	public boolean isDisabled()
	{
		return disabled;
	}
	
	public void setDisabled(boolean disabled)
	{
		this.disabled = disabled;
	}
	
	@Override
	public NovaMethodDeclaration[] getOverridingMethods()
	{
		if (isDisabled())
		{
			return new NovaMethodDeclaration[0];
		}
		
		return super.getOverridingMethods();
	}
	
	@Override
	public NovaMethodDeclaration getOverriddenMethod()
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
	 * @see Node#validate(int)
	 */
	@Override
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
	 * @see Node#cloneTo(Node)
	 */
	public ArrayOverloadMethod cloneTo(ArrayOverloadMethod node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link ArrayOverloadMethod} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ArrayOverloadMethod cloneTo(ArrayOverloadMethod node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		node.disabled = disabled;
		
		return node;
	}
	
	/**
	 * Test the {@link ArrayOverloadMethod} class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
	
	public String getMethodPrefix()
	{
		return "ArrayValue";
	}
}
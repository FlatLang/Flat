package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.util.Location;

public class FirstClassClosureDeclarationValidator extends ClosureDeclarationValidator
{
	public IdentifierValidator reference;
	
	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public FirstClassClosureDeclarationValidator(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see NodeValidator#validate(int)
	 *
	 * @param phase The phase that the node is being validated in.
	 */
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		register();
		
		if (phase == SyntaxTree.PHASE_PRE_GENERATION)
		{
			if (reference instanceof ClosureDeclarationValidator)
			{
				ClosureDeclarationValidator c = (ClosureDeclarationValidator)reference;
				
				if (c.id == -1)
				{
					c.register();
				}
			}
		}
		
		return result;
	}
	
	/**
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public FirstClassClosureDeclarationValidator clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		FirstClassClosureDeclarationValidator node = new FirstClassClosureDeclarationValidator(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
	 */
	public FirstClassClosureDeclarationValidator cloneTo(FirstClassClosureDeclarationValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link FirstClassClosureDeclarationValidator} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public FirstClassClosureDeclarationValidator cloneTo(FirstClassClosureDeclarationValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.reference = reference;
		
		return node;
	}
	
	/**
	 * Test the {@link FirstClassClosureDeclarationValidator} class type to make sure everything
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
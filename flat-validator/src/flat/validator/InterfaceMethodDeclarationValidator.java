package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.tree.variables.ObjectReference;
import flat.util.Location;

public class InterfaceMethodDeclarationValidator extends BodyMethodDeclarationValidator
{
	public boolean containsScope;
	
	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public InterfaceMethodDeclarationValidator(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public InterfaceMethodDeclarationValidator(NodeValidator temporaryParent, Location locationIn, BodyMethodDeclarationValidator method)
	{
		super(temporaryParent, locationIn);
		
		int oldId = uniqueID;
		
		method.cloneTo(this);
		
		uniqueID = oldId;
	}
	
	@Override
	public NodeValidator followedByScope(boolean scope)
	{
		containsScope = scope || shorthandAction != null;
		
		return super.followedByScope(scope);
	}
	
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_INSTANCE_DECLARATIONS)
		{
			if (!containsScope)
			{
				AbstractMethodDeclarationValidator abst = new AbstractMethodDeclarationValidator(this, getLocationIn());
				abst.createFrom(this);
				
				replaceWith(abst);
				
				abst.objectReference = new ObjectReference(abst);
				
				result.skipCycle = true;
			}
		}
		
		return result;
	}
	
	/**
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public InterfaceMethodDeclarationValidator clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		InterfaceMethodDeclarationValidator node = new InterfaceMethodDeclarationValidator(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
	 */
	public InterfaceMethodDeclarationValidator cloneTo(InterfaceMethodDeclarationValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link InterfaceMethodDeclarationValidator} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public InterfaceMethodDeclarationValidator cloneTo(InterfaceMethodDeclarationValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.containsScope = containsScope;
		
		return node;
	}
	
	/**
	 * Test the {@link InterfaceMethodDeclarationValidator} class type to make sure everything
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
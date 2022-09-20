package flat.validator;

import flat.TestContext;
import flat.ValidationResult;
import flat.tree.generics.GenericTypeArgumentList;
import flat.tree.generics.GenericTypeParameterList;
import flat.util.Location;

public class VirtualMethodDeclarationValidator extends BodyMethodDeclarationValidator
{
	public FlatMethodDeclarationValidator base;
	
	/**
	 * @see NodeValidator#NodeValidator(NodeValidator, Location)
	 */
	public VirtualMethodDeclarationValidator(NodeValidator temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public void searchVirtualMethodDeclaration()
	{
		
	}
	
	@Override
	public void moveShorthandActionToEnd()
	{
		
	}
	
	@Override
	public VirtualMethodDeclarationValidator getVirtualMethod()
	{
		return this;
	}
	
	public FlatParameterList getOriginalParameterList()
	{
		return super.getParameterList();
	}
	
	@Override
	public GenericTypeArgumentList getGenericTypeArgumentList()
	{
		return base.getGenericTypeArgumentList();
	}
	
	@Override
	public GenericTypeParameterList getMethodGenericTypeParameterDeclaration()
	{
		return base != null ? base.getMethodGenericTypeParameterDeclaration() : super.getMethodGenericTypeParameterDeclaration();
	}
	
	@Override
	public FlatParameterList getParameterList()
	{
		return base.getParameterList();
	}
	
	@Override
	public void setOverloadIDs(MethodDeclarationValidator[] methods)
	{
		
	}
	
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);
		
		if (result.skipValidation())
		{
			return result;
		}
		
		if (phase == SyntaxTree.PHASE_PRE_GENERATION)
		{
			getParentClass().addChild(this);
		}
		
		return result;
	}
		
	/**
	 * @see NodeValidator#clone(NodeValidator, Location, boolean)
	 */
	@Override
	public VirtualMethodDeclarationValidator clone(NodeValidator temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		VirtualMethodDeclarationValidator node = new VirtualMethodDeclarationValidator(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see NodeValidator#cloneTo(NodeValidator)
	 */
	public VirtualMethodDeclarationValidator cloneTo(VirtualMethodDeclarationValidator node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link VirtualMethodDeclarationValidator} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public VirtualMethodDeclarationValidator cloneTo(VirtualMethodDeclarationValidator node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the {@link VirtualMethodDeclarationValidator} class type to make sure everything
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
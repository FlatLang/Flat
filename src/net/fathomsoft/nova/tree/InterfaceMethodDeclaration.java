package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.variables.ObjectReference;
import net.fathomsoft.nova.util.Location;

/**
 * {@link Node} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class InterfaceMethodDeclaration extends BodyMethodDeclaration
{
	public boolean containsScope;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public InterfaceMethodDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public InterfaceMethodDeclaration(Node temporaryParent, Location locationIn, BodyMethodDeclaration method)
	{
		super(temporaryParent, locationIn);
		
		int oldId = uniqueID;
		
		method.cloneTo(this);
		
		uniqueID = oldId;
	}
	
	@Override
	public void followedByScope(boolean scope)
	{
		containsScope = scope || shorthandAction != null;
		
		super.followedByScope(scope);
	}
	
	@Override
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
				AbstractMethodDeclaration abst = new AbstractMethodDeclaration(this, getLocationIn());
				abst.createFrom(this);
				
				replaceWith(abst);
				
				abst.objectReference = new ObjectReference(abst);
				
				result.skipCycle = true;
			}
		}
		
		return result;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public InterfaceMethodDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		InterfaceMethodDeclaration node = new InterfaceMethodDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public InterfaceMethodDeclaration cloneTo(InterfaceMethodDeclaration node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link InterfaceMethodDeclaration} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public InterfaceMethodDeclaration cloneTo(InterfaceMethodDeclaration node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the {@link InterfaceMethodDeclaration} class type to make sure everything
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
package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * {@link NovaMethodDeclaration} extension that represents
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.28 Aug 12, 2014 at 1:54:08 AM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class AssignmentMethod extends BodyMethodDeclaration
{
	public static final String	IDENTIFIER = InitializationMethod.SUPER_IDENTIFIER;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public AssignmentMethod(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		NovaMethodDeclaration method = decodeStatement(temporaryParent, "public " + IDENTIFIER + "()", Location.INVALID, true);
		
		method.cloneTo(this);
	}
	
	@Override
	public boolean isUserMade()
	{
		return false;
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
	 * @see net.fathomsoft.nova.tree.NovaMethodDeclaration#isVirtual()
	 */
	@Override
	public boolean isVirtual()
	{
		return false;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public AssignmentMethod clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		AssignmentMethod node = new AssignmentMethod(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public AssignmentMethod cloneTo(AssignmentMethod node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link AssignmentMethod} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public AssignmentMethod cloneTo(AssignmentMethod node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the {@link AssignmentMethod} class type to make sure everything
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
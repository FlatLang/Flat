package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;

/**
 * BodyMethodDeclaration extension that represents
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.27 Aug 6, 2014 at 5:12:43 PM
 * @version	v0.2.27 Aug 7, 2014 at 1:32:02 AM
 */
public class InstantiationMethod extends NovaMethodDeclaration
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public InstantiationMethod(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		decodeStatement(temporaryParent, "public  -> " + getParentClass().getName(), locationIn, true);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public InstantiationMethod clone(Node temporaryParent, Location locationIn)
	{
		InstantiationMethod node = new InstantiationMethod(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link InstantiationMethod} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public InstantiationMethod cloneTo(InstantiationMethod node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the {@link InstantiationMethod} class type to make sure everything
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
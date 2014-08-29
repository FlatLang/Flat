package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;

/**
 * {@link NovaMethodDeclaration} extension that represents the declaration of an
 * abstract method node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.19 Jul 26, 2014 at 12:30:24 AM
 * @version	v0.2.26 Aug 6, 2014 at 2:48:50 PM
 */
public class AbstractMethodDeclaration extends NovaMethodDeclaration
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public AbstractMethodDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.MethodDeclaration#containsBody()
	 */
	@Override
	public boolean containsBody()
	{
		return false;
	}
	
	/**
	 * Decode the given statement into a {@link AbstractMethodDeclaration} instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>abstract int getAge(String name, int age)</li>
	 * 	<li>abstract int calculateArea(int width, int height)</li>
	 * 	<li>abstract void overrideMe()</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link AbstractMethodDeclaration} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Method.
	 */
	public static AbstractMethodDeclaration decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		String methodSignature = findMethodSignature(statement, Patterns.ABSTRACT);
		
		if (methodSignature != null && methodSignature.length() > 0)
		{
			AbstractMethodDeclaration n      = new AbstractMethodDeclaration(parent, location);
			MethodDeclaration         method = NovaMethodDeclaration.decodeStatement(n, methodSignature, location.asNew(), require);
			
			if (method != null)
			{
				method.cloneTo(n);
				
				return n;
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public AbstractMethodDeclaration clone(Node temporaryParent, Location locationIn)
	{
		AbstractMethodDeclaration node = new AbstractMethodDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link AbstractMethodDeclaration} with the data that is in
	 * the specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public AbstractMethodDeclaration cloneTo(AbstractMethodDeclaration node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the {@link AbstractMethodDeclaration} class type to make sure everything
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
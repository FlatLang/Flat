package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * {@link IIdentifier} extension that represents the use of a Static
 * Class name to reference a static instance within the specified
 * Class.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.9 Aug 23, 2014 at 11:25:19 PM
 * @version	v0.2.34 Oct 1, 2014 at 9:51:33 PM
 */
public class StaticClassReference extends IIdentifier
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public StaticClassReference(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#generateCUseOutput(java.lang.StringBuilder, boolean, boolean)
	 */
	@Override
	public StringBuilder generateCUseOutput(StringBuilder builder, boolean pointer, boolean checkAccesses)
	{
		return builder.append(0);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#generateCSourceFragment(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		if (!doesAccess())
		{
			return generateCUseOutput(builder);
		}
		
		if (isSpecialFragment())
		{
			return generateSpecialFragment(builder);
		}
		
		return getAccessedNode().generateCSourceFragment(builder);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Identifier#generateCArgumentReference(java.lang.StringBuilder, net.fathomsoft.nova.tree.Identifier)
	 */
	@Override
	public StringBuilder generateCArgumentReference(StringBuilder builder, Identifier callingMethod)
	{
		return builder.append(0);//getAccessedNode().generateCArgumentReference(builder, callingMethod);
	}
	
	/**
	 * Decode the given statement into a {@link StaticClassReference} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>Math</li>
	 * 	<li>Object</li>
	 * 	<li>ClassName</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link StaticClassReference} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link StaticClassReference}.
	 */
	public static StaticClassReference decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (parent.getFileDeclaration().containsImport(statement, false))
		{
			StaticClassReference n = new StaticClassReference(parent, location);
			
			n.setName(statement);
			n.setType(statement);
			
			return n;
		}
		
		return null;
	}
	
	@Override
	public ClassDeclaration getDeclaringClass()
	{
		return SyntaxUtils.getImportedClass(getFileDeclaration(), getName());
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public StaticClassReference clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		StaticClassReference node = new StaticClassReference(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public StaticClassReference cloneTo(StaticClassReference node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link StaticClassReference} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public StaticClassReference cloneTo(StaticClassReference node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link StaticClassReference} class type to make sure everything
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
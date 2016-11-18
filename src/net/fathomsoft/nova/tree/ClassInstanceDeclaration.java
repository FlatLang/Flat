package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.util.Location;

/**
 * {@link FieldDeclaration} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class ClassInstanceDeclaration extends FieldDeclaration
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ClassInstanceDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		setType("Class");//temporaryParent.getProgram().getClassDeclaration("nova/Class"));
		setName("class");
		setVisibility(VISIBLE);
	}
	
	@Override
	public void onRemoved(Node parent)
	{
		super.onRemoved(parent);
		
		// silently remove
		setTemporaryParent(parent);
	}
	
	
	
	@Override
	public boolean isTangible()
	{
		return false;
	}
	
	@Override
	public boolean allowsPropertyMethods()
	{
		return false;
	}
	
	/**
	 * Decode the given statement into a {@link ClassInstanceDeclaration} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li></li>
	 * 	<li></li>
	 * 	<li></li>
	 * </ul>
	 *
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link ClassInstanceDeclaration} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link ClassInstanceDeclaration}.
	 */
	public static ClassInstanceDeclaration decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public ClassInstanceDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		ClassInstanceDeclaration node = new ClassInstanceDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public ClassInstanceDeclaration cloneTo(ClassInstanceDeclaration node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link ClassInstanceDeclaration} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ClassInstanceDeclaration cloneTo(ClassInstanceDeclaration node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link ClassInstanceDeclaration} class type to make sure everything
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
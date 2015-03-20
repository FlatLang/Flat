package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.ClassDeclaration.ClassData;
import net.fathomsoft.nova.tree.generics.GenericImplementation;
import net.fathomsoft.nova.tree.variables.VariableDeclaration.DeclarationData;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;

/**
 * {@link Node} extension that represents
 * 
 * @author	Braden Steffaniak
 */
public class InterfaceImplementation extends IValue implements GenericCompatible
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public InterfaceImplementation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		GenericImplementation implementation = new GenericImplementation(this, locationIn.asNew());
		addChild(implementation, this);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}

	/**
	 * @see net.fathomsoft.nova.tree.GenericCompatible#getGenericImplementation()
	 */
	@Override
	public GenericImplementation getGenericImplementation()
	{
		return (GenericImplementation)getChild(super.getNumDefaultChildren() + 0);
	}
	
	/**
	 * Decode the given statement into a {@link InterfaceImplementation} instance, if
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
	 * 		{@link InterfaceImplementation} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link InterfaceImplementation}.
	 */
	public static InterfaceImplementation decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		InterfaceImplementation n = new InterfaceImplementation(parent, location);
		
		int numWords = StringUtils.findNumWords(statement);
		
		if (numWords != 1)
		{
			SyntaxMessage.queryError("Invalid interface implementation", n, require);
			
			return null;
		}
		
		n.setType(statement, true, false);
		
		return n;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public InterfaceImplementation clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		InterfaceImplementation node = new InterfaceImplementation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public InterfaceImplementation cloneTo(InterfaceImplementation node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link InterfaceImplementation} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public InterfaceImplementation cloneTo(InterfaceImplementation node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link InterfaceImplementation} class type to make sure everything
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
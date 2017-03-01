package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;

/**
 * {@link Node} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class FirstClassClosureDeclaration extends ClosureDeclaration
{
	public VariableDeclaration reference;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public FirstClassClosureDeclaration(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Decode the given statement into a {@link FirstClassClosureDeclaration} instance, if
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
	 * 		{@link FirstClassClosureDeclaration} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link FirstClassClosureDeclaration}.
	 */
	public static FirstClassClosureDeclaration decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#validate(int)
	 *
	 * @param phase The phase that the node is being validated in.
	 */
	@Override
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
			if (reference instanceof ClosureDeclaration)
			{
				ClosureDeclaration c = (ClosureDeclaration)reference;
				
				if (c.id == -1)
				{
					c.register();
				}
			}
		}
		
		return result;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public FirstClassClosureDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		FirstClassClosureDeclaration node = new FirstClassClosureDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public FirstClassClosureDeclaration cloneTo(FirstClassClosureDeclaration node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link FirstClassClosureDeclaration} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public FirstClassClosureDeclaration cloneTo(FirstClassClosureDeclaration node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		node.reference = reference;
		
		return node;
	}
	
	/**
	 * Test the {@link FirstClassClosureDeclaration} class type to make sure everything
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
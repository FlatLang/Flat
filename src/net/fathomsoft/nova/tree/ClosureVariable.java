package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.annotations.FinalAnnotation;
import net.fathomsoft.nova.tree.annotations.VarAnnotation;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;

/**
 * {@link NovaMethodDeclaration} extension that represents
 *
 * @author	Braden Steffaniak
 */
public class ClosureVariable extends NovaMethodDeclaration
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ClosureVariable(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Decode the given statement into a {@link ClosureVariable} instance, if
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
	 * 		{@link ClosureVariable} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link ClosureVariable}.
	 */
	public static ClosureVariable decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		NovaMethodDeclaration method = NovaMethodDeclaration.decodeStatement(parent, statement, location, false);
		
		if (method != null)
		{
			VarAnnotation var = (VarAnnotation)method.getAnnotationOfType(VarAnnotation.class, false, true);
			FinalAnnotation fin = (FinalAnnotation)method.getAnnotationOfType(FinalAnnotation.class, false, true);
			
			if (var != null || fin != null && (fin.getAliasUsed() == null || !fin.getAliasUsed().equals("final")))
			{
				ClosureVariable variable = new ClosureVariable(parent, location);
				
				method.cloneTo(variable);
				
				return variable;
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public ClosureVariable clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		ClosureVariable node = new ClosureVariable(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public ClosureVariable cloneTo(ClosureVariable node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link ClosureVariable} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ClosureVariable cloneTo(ClosureVariable node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
		return node;
	}
	
	/**
	 * Test the {@link ClosureVariable} class type to make sure everything
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
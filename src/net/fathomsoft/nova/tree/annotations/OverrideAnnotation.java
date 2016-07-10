package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.Literal;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.NovaMethodDeclaration;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.tree.variables.FieldDeclaration;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;

public class OverrideAnnotation extends Annotation
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public OverrideAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Decode the given statement into a {@link OverrideAnnotation} instance, if
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
	 * @param parameters The statement to try to decode into a
	 * 		{@link OverrideAnnotation} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link OverrideAnnotation}.
	 */
	public static OverrideAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("Override"))
		{
			OverrideAnnotation n = new OverrideAnnotation(parent, location);
			
			return n;
		}
		
		return null;
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
			Node node = getParent();
			
			if (node instanceof NovaMethodDeclaration == false)
			{
				invalidExpression(this, true);
			}
			
			NovaMethodDeclaration method = (NovaMethodDeclaration)node;
			
			if (!method.doesOverride())
			{
				SyntaxMessage.error("Method " + method + " does not override any other methods", method);
			}
		}
		
		return result;
	}
	
	@Override
	public boolean onNextStatementDecoded(Node next)
	{
		if (next instanceof NovaMethodDeclaration)
		{
			next.addAnnotation(this);
		}
		
		return super.onNextStatementDecoded(next);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public OverrideAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		OverrideAnnotation node = new OverrideAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public OverrideAnnotation cloneTo(OverrideAnnotation node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link OverrideAnnotation} with the data that is in the
	 * specified node.
	 *
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public OverrideAnnotation cloneTo(OverrideAnnotation node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link OverrideAnnotation} class type to make sure everything
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
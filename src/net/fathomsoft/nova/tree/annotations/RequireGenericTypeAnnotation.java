package net.fathomsoft.nova.tree.annotations;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;

/**
 * {@link Annotation} extension that represents
 * 
 * @author	Braden Steffaniak
 */
public class RequireGenericTypeAnnotation extends Annotation
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public RequireGenericTypeAnnotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Decode the given statement into a {@link RequireGenericTypeAnnotation} instance, if
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
	 * 		{@link RequireGenericTypeAnnotation} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link RequireGenericTypeAnnotation}.
	 */
	public static RequireGenericTypeAnnotation decodeStatement(Node parent, String name, String parameters, Location location, boolean require)
	{
		if (name.equals("RequireGenericType"))
		{
			RequireGenericTypeAnnotation n = new RequireGenericTypeAnnotation(parent, location);
			
			if (parameters.length() == 0)
			{
				requiresArguments(n, require);
				
				return null;
			}
			
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
		
		Node node = getParent();
		
		if (node instanceof VariableDeclaration == false)
		{
			invalidExpression(this, true);
		}
		
		return result;
	}
	
	@Override
	public boolean onNextStatementDecoded(Node next)
	{
		if (next instanceof VariableDeclaration)
		{
			next.addAnnotation(this);
		}
		
		return super.onNextStatementDecoded(next);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public RequireGenericTypeAnnotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		RequireGenericTypeAnnotation node = new RequireGenericTypeAnnotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public RequireGenericTypeAnnotation cloneTo(RequireGenericTypeAnnotation node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link RequireGenericTypeAnnotation} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public RequireGenericTypeAnnotation cloneTo(RequireGenericTypeAnnotation node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link RequireGenericTypeAnnotation} class type to make sure everything
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
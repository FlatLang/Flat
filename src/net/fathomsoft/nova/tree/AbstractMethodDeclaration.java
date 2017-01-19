package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.annotations.AbstractAnnotation;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * {@link NovaMethodDeclaration} extension that represents the declaration of an
 * abstract method node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.19 Jul 26, 2014 at 12:30:24 AM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class AbstractMethodDeclaration extends NovaMethodDeclaration
{
	public static final String IDENTIFIER = "abstract";
	
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
	
	@Override
	public boolean isVirtual()
	{
		return true;
	}
	
	@Override
	public boolean isVirtualMethodDeclaration()
	{
		return true;
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
		if (SyntaxUtils.findStringInBaseScope(statement, IDENTIFIER) >= 0)
		{
			statement = findMethodSignature(statement, StringUtils.findWordBounds(statement, IDENTIFIER));
		}
		else if (!parent.getFileDeclaration().containsPendingAnnotationOfType(AbstractAnnotation.class))
		{
			return null;
		}
		
		if (statement != null && statement.length() > 0)
		{
			AbstractMethodDeclaration n = new AbstractMethodDeclaration(parent, location);
			NovaMethodDeclaration method = NovaMethodDeclaration.decodeStatement(n, statement, location.asNew(), require);
			
			if (method != null)
			{
				return n.createFrom(method);
			}
		}
		
		return null;
	}
	
	public AbstractMethodDeclaration createFrom(NovaMethodDeclaration method)
	{
		method.cloneTo(this);
		
		return this;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.NovaMethodDeclaration#validate(int)
	 */
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
			if (!getParentClass().isAbstract())
			{
				result.errorOccurred();
				
				SyntaxMessage.error("The class '" + getParentClass().getName() + "' must be " + IDENTIFIER + " to contain the " + IDENTIFIER + " method " + getName(), this, false);
			}
			
			//searchVirtualMethodDeclaration();
		}
		
		return result;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public AbstractMethodDeclaration clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		AbstractMethodDeclaration node = new AbstractMethodDeclaration(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * Fill the given {@link AbstractMethodDeclaration} with the data that is in
	 * the specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public AbstractMethodDeclaration cloneTo(AbstractMethodDeclaration node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
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
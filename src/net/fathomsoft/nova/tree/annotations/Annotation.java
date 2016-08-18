package net.fathomsoft.nova.tree.annotations;

import jdk.nashorn.internal.runtime.FindProperty;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.ValidationResult;
import net.fathomsoft.nova.error.SyntaxErrorException;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * {@link Node} extension that represents
 * 
 * @author	Braden Steffaniak
 */
public class Annotation extends Node
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Annotation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public boolean isDecoding()
	{
		return false;
	}
	
	@Override
	public void detach()
	{
		if (getParent() != null)
		{
			getParent().removeAnnotation(this);
		}
	}
	
	@Override
	public StringBuilder generateCHeaderFragment(StringBuilder builder)
	{
		return builder;
	}
	
	@Override
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		return builder;
	}
	
	/**
	 * Decode the given statement into a {@link Annotation} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>[RequireGenericType(E extends Number)]</li>
	 * 	<li>[RequireGenericType E extends Number]</li>
	 * 	<li>[SomeAnnotation]</li>
	 * 	<li>[SomethingWithParams(values=["thing1", "Thing2"], size=5)]</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link Annotation} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link Annotation}.
	 */
	public static Annotation decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (statement.startsWith("["))
		{
			int index = StringUtils.findEndingMatch(statement, 0, '[', ']');
			
			if (index > 0)
			{
				statement = statement.substring(1, index).trim();
				
				String name = StringUtils.findNextWord(statement);
				
				if (name != null)
				{
					String arguments = statement.substring(name.length());
					
					arguments = StringUtils.removeSurroundingParenthesis(arguments);
					
					Annotation n = RequireGenericTypeAnnotation.decodeStatement(parent, name, arguments, location, require);
					
					if (n == null)
					{
						n = ObsoleteAnnotation.decodeStatement(parent, name, arguments, location, require);
						
						if (n == null)
						{
							n = OverrideAnnotation.decodeStatement(parent, name, arguments, location, require);
							
							if (n == null)
							{
								n = PrimitiveArrayAnnotation.decodeStatement(parent, name, arguments, location, require);
							}
						}
					}
					
					return n;
				}
			}
		}
		
		return null;
	}
	
	public static String getFragment(String statement)
	{
		int index = StringUtils.findEndingMatch(statement, 0, '[', ']');
		
		return statement.substring(index + 1).trim();
	}
	
	@Override
	public ValidationResult validate(int phase)
	{
		ValidationResult result = super.validate(phase);

		if (result.skipValidation())
		{
			return result;
		}
		
		try
		{
			if (getParent() == null)
			{
				missingExpression(this, true);
			}
		}
		catch (SyntaxErrorException e)
		{
			detach();
		}
		
		
		
		return result;
	}
	
	@Override
	public boolean onNextStatementDecoded(Node next)
	{
		if (getParent() != null)
		{
			getLocationIn().setLineNumber(getLocationIn().getLineNumber() - getParent().getLineNumber());
		}
		
		return super.onNextStatementDecoded(next);
	}
	
	public static boolean invalidExpression(Annotation node, boolean require)
	{
		return SyntaxMessage.queryError("Annotation is applied to an invalid expression", node, require);
	}
	
	public static boolean missingExpression(Annotation node, boolean require)
	{
		return SyntaxMessage.queryError("Annotation is missing an expression to be applied to", node, require);
	}
	
	public static boolean requiresArguments(Annotation node, boolean require)
	{
		return SyntaxMessage.queryError("Missing annotation arguments", node, require);
	}
	
	public static boolean tooManyArguments(Annotation node, boolean require)
	{
		return SyntaxMessage.queryError("Too many arguments given to annotation", node, require);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Annotation clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		Annotation node = new Annotation(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public Annotation cloneTo(Annotation node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link Annotation} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Annotation cloneTo(Annotation node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		return node;
	}
	
	/**
	 * Test the {@link Annotation} class type to make sure everything
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
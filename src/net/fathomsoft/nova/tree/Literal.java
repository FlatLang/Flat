package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.tree.exceptionhandling.Exception;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * ValueNode extension that represents a literal within the code. For
 * example, a number literal and a String literal.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:34:30 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class Literal extends Value
{
	private String	value;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Literal(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get the value of the literal.
	 * 
	 * @return The value of the literal.
	 */
	public String getValue()
	{
		return value;
	}
	
	/**
	 * Set the value of the literal. If the literal's value is set within
	 * an external context, the value will be generated in a c syntax sort
	 * of way.
	 * 
	 * @param value The value to set the literal as.
	 */
	public void setValue(String value)
	{
//		if (!external && SyntaxUtils.isStringLiteral(value))
//		{
//			value = Nova.LANGUAGE_NAME.toLowerCase() + "_String_String(" + ExceptionNode.EXCEPTION_DATA_IDENTIFIER + ", " + value + ")";
//		}
		
		this.value = value;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(boolean)
	 */
	@Override
	public String generateNovaInput(boolean outputChildren)
	{
		if (!isWithinExternalContext() && isStringInstantiation())
		{
			return "new String(" + value + ")";
		}
		
		return value;
	}
	
	/**
	 * Get whether or not the value of the literal is an
	 * instantiation of a String from a String constructor.
	 * 
	 * @return Whether or not the value of the literal is an
	 * 		instantiation of a String from a String constructor.
	 */
	private boolean isStringInstantiation()
	{
		if (SyntaxUtils.isStringLiteral(value))
		{
			if (getParent() instanceof ArgumentList)
			{
				MethodCall node = (MethodCall)getParent().getParent();
				
				if (node.getName().equals("String"))
				{
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		return value;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		return generateCSource();
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		return generateCSourceFragment();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		if (!isWithinExternalContext() && isStringInstantiation())
		{
			return Nova.LANGUAGE_NAME.toLowerCase() + "_String_String(" + Exception.EXCEPTION_DATA_IDENTIFIER + ", " + value + ")";
		}
		
		return value;
	}
	
	/**
	 * Decode the given statement into a LiteralNode instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>123</li>
	 * 	<li>-321</li>
	 * 	<li>121.32</li>
	 * 	<li>'a'</li>
	 * 	<li>'\''</li>
	 * 	<li>"Text String"</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		LiteralNode instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a LiteralNode.
	 */
	public static Literal decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		String literalType = SyntaxUtils.getLiteralTypeName(statement);
		
		if (literalType != null || parent.isWithinExternalContext())
		{
			Literal n = new Literal(parent, location);
			n.setValue(statement);
			n.setType(literalType);
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Literal clone(Node temporaryParent, Location locationIn)
	{
		Literal node = new Literal(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given LiteralNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Literal cloneTo(Literal node)
	{
		super.cloneTo(node);
		
		node.value = value;
		
		return node;
	}
}
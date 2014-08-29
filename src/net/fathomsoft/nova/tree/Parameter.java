package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.VariableDeclaration;
import net.fathomsoft.nova.util.Location;

/**
 * LocalDeclaration extension that represents a Parameter of a method.
 * See {@link #decodeStatement(Node, String, Location, boolean)} for more
 * details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:52:01 PM
 * @version	v0.2.26 Aug 6, 2014 at 2:48:50 PM
 */
public class Parameter extends LocalDeclaration
{
	private Node	defaultValue;
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Parameter(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get the default value of the parameter, if no value is passed to
	 * the method.
	 * 
	 * @return The value that the parameter will be set to, if no value is
	 * 		passed to a method.
	 */
	public Node getDefaultValue()
	{
		return defaultValue;
	}
	
	/**
	 * Set the default value of the parameter, if no value is passed to
	 * the method.
	 * 
	 * @param defaultValue The value that the parameter will be set to,
	 * 		if no value is passed to a method.
	 */
	public void setDefaultValue(Node defaultValue)
	{
		this.defaultValue = defaultValue;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCHeader(StringBuilder)
	 */
	@Override
	public StringBuilder generateCHeader(StringBuilder builder)
	{
		return generateCModifiersSource(builder);
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return generateCHeader(builder).append(' ').append(generateCSourceName());
	}
	
	/**
	 * Decode the given statement into a Parameter instance, if
	 * possible. If it is not possible, this method returns null.
	 * A parameter node is essentially a variable declaration, but in
	 * the context of a method declaration.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>String name</li>
	 * 	<li>int age</li>
	 * 	<li>Node parent</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Parameter instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Parameter.
	 */
	public static Parameter decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		VariableDeclaration node = LocalDeclaration.decodeStatement(parent, statement, location, require);
		
		if (node == null)
		{
			node = ClosureDeclaration.decodeStatement(parent, statement, location, require);
			
			if (node == null)
			{
				SyntaxMessage.queryError("Could not decode parameter", parent, location, require);
				
				return null;
			}
		}
		
		if (node instanceof Parameter)
		{
			return (Parameter)node;
		}
		
		Parameter n = new Parameter(parent, location);
		node.cloneTo(n);
		
		return n;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.LocalDeclaration#clone(Node, Location)
	 */
	@Override
	public Parameter clone(Node temporaryParent, Location locationIn)
	{
		Parameter node = new Parameter(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link Parameter} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Parameter cloneTo(Parameter node)
	{
		super.cloneTo(node);
		
		node.defaultValue = defaultValue;
		
		return node;
	}
	
	/**
	 * Test the Parameter class type to make sure everything
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
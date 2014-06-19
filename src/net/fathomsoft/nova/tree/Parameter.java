package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * LocalVariable extension that represents a Parameter of a method.
 * See {@link #decodeStatement(Node, String, Location, boolean, boolean)} for more
 * details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:52:01 PM
 * @version	v0.2.14 Jun 18, 2014 at 10:11:40 PM
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
		return generateCSource(builder);
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		if (isConstant())
		{
			builder.append(getConstantText()).append(' ');
		}
		
		generateCTypeOutput(builder);
		
		if (isArray())
		{
			builder.append(getArrayText());
		}
		
		if (isPointer())
		{
			builder.append('*');
		}
		else if (isReference())
		{
			builder.append('&');
		}
		
		if (!SyntaxUtils.isPrimitiveType(getType()) && !isExternalType())
		{
			builder.append('*');
		}
		
		builder.append(' ');
		
		return generateCSourceName(builder);
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
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Parameter.
	 */
	public static Parameter decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		LocalDeclaration node = LocalDeclaration.decodeStatement(parent, statement, location, require, scope);
		
		if (node == null)
		{
//			SyntaxMessage.error("Could not asdf", parent, location);
			return null;
		}
		
		Parameter n = new Parameter(parent, location);
		node.cloneTo(n);
		
		return n;
	}
}
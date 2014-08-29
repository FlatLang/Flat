package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Value extension that represents a return statement node type.
 * See {@link #decodeStatement(Node, String, Location, boolean)} for more
 * details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:58:29 PM
 * @version	v0.2.26 Aug 6, 2014 at 2:48:50 PM
 */
public class Return extends IValue
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Return(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get the Value that the return statement returns.
	 * 
	 * @return The Value Node that the return statement returns.
	 */
	public Value getValueNode()
	{
		if (getNumChildren() <= 0)
		{
			return null;
		}
		
		return (Value)getChild(0);
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return generateCSourceFragment(builder).append(";\n");
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		builder.append("return");
		
		if (getValueNode() != null)
		{
			builder.append(' ').append(getValueNode().generateCSourceFragment());
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		builder.append("return");
		
		if (getValueNode() != null)
		{
			builder.append(' ').append(getValueNode().generateNovaInput(outputChildren));
		}
		
		return builder;
	}
	
	/**
	 * Decode the given statement into a Return instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>return</li>
	 * 	<li>return node</li>
	 * 	<li>return 0</li>
	 * 	<li>return getAge()</li>
	 * 	<li>return age + 32</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Return instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Return.
	 */
	public static Return decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (Regex.startsWith(statement, Patterns.PRE_RETURN))
		{
			Return n = new Return(parent, location);
			
			if (n.decodeReturnValue(statement, location, require))
			{
				return n;
			}
		}
		
		return null;
	}
	
	/**
	 * Decode the return value of a return statement, if the return
	 * statement returns a value.<br>
	 * <br>
	 * The value of a return statement looks like the following:
	 * <blockquote><pre>
	 * return node</pre></blockquote>
	 * "<u><code>node</code></u>" is the value in the above return
	 * statement.
	 * 
	 * @param statement The statement to decode the return value from.
	 * @param location The location of the return statement in the source
	 * 		code.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return Whether or not the return value decoded correctly.
	 */
	private boolean decodeReturnValue(String statement, Location location, boolean require)
	{
		String postReturn = generatePostReturn(statement);
		
		if (postReturn != null)
		{
			Location newLoc = location.asNew();
			newLoc.addBounds(statement.indexOf(postReturn), statement.length());
		
			Value abstractValue = decodeReturnValue(postReturn, getParentMethod(), newLoc);
			
			addChild(abstractValue);
		}
		else if (getParentMethod().getType() != null)
		{
			return SyntaxMessage.queryError("Method '" + getParentMethod().getName() + "' must return a type of '" + getParentMethod().getType() + "'", this, require);
		}
		
		setType(getParentMethod().getType());
		
		return true;
	}
	
	/**
	 * Decode the return value of a return statement.<br>
	 * <br>
	 * The value of a return statement looks like the following:
	 * <blockquote><pre>
	 * return node</pre></blockquote>
	 * "<u><code>node</code></u>" is the value in the above return
	 * statement.
	 * 
	 * @param statement The statement containing the return value.
	 * @param method The method that the return statement is returning
	 * 		from.
	 * @param location The location of the return statement value in the
	 * 		source code.
	 * @return The Value representing the return value.
	 */
	private Value decodeReturnValue(String statement, MethodDeclaration method, Location location)
	{
		Value value = SyntaxTree.decodeValue(this, statement, location, false);
		
		if (value == null)
		{
			SyntaxMessage.error("Could not decode return statement '" + statement + "'", this, location);
		}
		else if (!SyntaxUtils.validateCompatibleTypes(method, value.getReturnedNode()))
		{
			SyntaxMessage.error("Method '" + method.getName() + "' must return a type of '" + method.getType() + "'", this, location);
		}
		
		return value;
	}
	
	/**
	 * Get a String representing the return value, if the return
	 * statement returns a value. If it does not, it returns null.<br>
	 * <br>
	 * The value of a return statement looks like the following:
	 * <blockquote><pre>
	 * return node</pre></blockquote>
	 * "<u><code>node</code></u>" is the value in the above return
	 * statement.
	 * 
	 * @param statement The statement to generate the String from.
	 * @return The String representing the return value. If the return
	 * 		statement does not return a value, then null.
	 */
	private static String generatePostReturn(String statement)
	{
		int index = StringUtils.findNextNonWhitespaceIndex(statement, 7);
		
		if (index < 0)
		{
			return null;
		}
		
		return statement.substring(index);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Return clone(Node temporaryParent, Location locationIn)
	{
		Return node = new Return(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link Return} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Return cloneTo(Return node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the Return class type to make sure everything
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
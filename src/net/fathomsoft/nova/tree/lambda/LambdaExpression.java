package net.fathomsoft.nova.tree.lambda;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.Value;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * {@link Node} extension that represents a lambda expression.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.45 Jun 5, 2014 at 9:00:04 PM
 * @version	v0.2.45 Jun 5, 2016 at 11:43:17 PM
 */
public class LambdaExpression extends Value
{
	private int id;
	
	public static final String OPERATOR = "->";
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public LambdaExpression(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	public void register()
	{
		id = getFileDeclaration().registerLambda(this);
	}
	
	/**
	 * Decode the given statement into a {@link LambdaExpression} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>x -> x + 1</li>
	 * 	<li>(x, i) -> Console.writeLine(x * i)</li>
	 * 	<li>asdf -> asdf.doSomething()</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link LambdaExpression} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link LambdaExpression}.
	 */
	public static LambdaExpression decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		String[] variables = null;
		int endingIndex = 0;
		
		if (statement.startsWith("("))
		{
			endingIndex = StringUtils.findEndingMatch(statement, 0, '(', ')');
			
			variables = StringUtils.splitCommas(statement.substring(1, endingIndex));
		}
		else
		{
			variables = new String[] { StringUtils.findNextWord(statement) };
			
			endingIndex = variables[0].length();
		}
		
		if (endingIndex > 0)
		{
			String operation = statement.substring(endingIndex).trim();
			
			if (operation.startsWith(OPERATOR))
			{
				operation = operation.substring(OPERATOR.length()).trim();
				
				LambdaExpression lambda = new LambdaExpression(parent, location);
				lambda.register();
				
				return lambda;
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public LambdaExpression clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		LambdaExpression node = new LambdaExpression(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public LambdaExpression cloneTo(LambdaExpression node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link LambdaExpression} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public LambdaExpression cloneTo(LambdaExpression node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
		node.id = id;
		
		return node;
	}
	
	/**
	 * Test the {@link LambdaExpression} class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
	
	@Override
	public Value getReturnedNode()
	{
		return null;
	}
	
	@Override
	public String getType()
	{
		Value returned = getReturnedNode();
		
		if (returned == null)
		{
			return null;
		}
		
		return getReturnedNode().getType();
	}

	@Override
	public void setTypeValue(String type)
	{
		Value returned = getReturnedNode();
		
		if (returned == null)
		{
			return;
		}
		
		getReturnedNode().setTypeValue(type);
	}

	@Override
	public int getArrayDimensions()
	{
		Value returned = getReturnedNode();
		
		if (returned == null)
		{
			return 0;
		}
		
		return getReturnedNode().getArrayDimensions();
	}

	@Override
	public void setArrayDimensions(int arrayDimensions)
	{
		Value returned = getReturnedNode();
		
		if (returned == null)
		{
			return;
		}
		
		returned.setArrayDimensions(returned.getArrayDimensions());
	}

	@Override
	public byte getDataType()
	{
		Value returned = getReturnedNode();
		
		if (returned == null)
		{
			return 0;
		}
		
		return getReturnedNode().getDataType();
	}

	@Override
	public void setDataType(byte type)
	{
		Value returned = getReturnedNode();
		
		if (returned == null)
		{
			return;
		}
		
		getReturnedNode().setDataType(type);
	}
}
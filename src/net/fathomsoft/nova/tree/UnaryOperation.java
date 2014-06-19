package net.fathomsoft.nova.tree;

import java.util.HashMap;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.ArrayAccess;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;

/**
 * Node extension that represents a unary operator node type.
 * See {@link #decodeStatement(Node, String, Location, boolean, boolean)} for more
 * details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:00:11 PM
 * @version	v0.2.14 Jun 18, 2014 at 10:11:40 PM
 */
public class UnaryOperation extends Value
{
	private static final int	LEFT = -1, EITHER = 0, RIGHT = 1;
	
	private static final HashMap<String, Integer>	SIDES;
	
	/**
	 * Initialize the SIDES HashMap.
	 */
	static
	{
		SIDES = new HashMap<String, Integer>();
		
		SIDES.put("-",  LEFT);
		SIDES.put("--", EITHER);
		SIDES.put("++", EITHER);
		SIDES.put("!",  LEFT);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public UnaryOperation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * The the Node that represents the variable in the operation.<br>
	 * For example:<br>
	 * <blockquote><pre>
	 * var++;</pre></blockquote>
	 * In the previous statement, 'var' is the variable.
	 * 
	 * @return The Node that represents the parameters of the method.
	 */
	public Node getVariable()
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			if (getChild(i) instanceof Operator == false)
			{
				return getChild(i);
			}
		}
		
		return null;
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
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			child.generateCSourceFragment(builder);
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(boolean)
	 */
	@Override
	public String generateNovaInput(boolean outputChildren)
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			builder.append(child.generateNovaInput());
		}
		
		return builder.toString();
	}
	
	/**
	 * Decode the given statement into a UnaryOperation instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>asdf.var++</li>
	 * 	<li>++asdf.var</li>
	 * 	<li>asdf.var--</li>
	 * 	<li>--asdf.var</li>
	 * 	<li>var--</li>
	 * 	<li>--var</li>
	 * 	<li>var++</li>
	 * 	<li>++var</li>
	 * 	<li>++array[5]</li>
	 * 	<li>array[5]--</li>
	 * 	<li>!asdf.var</li>
	 * 	<li>!var</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		UnaryOperation instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a UnaryOperation.
	 */
	public static UnaryOperation decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		Bounds bounds = StringUtils.findStrings(statement, StringUtils.UNARY_OPERATORS);
		
		if (bounds.getStart() >= 0)
		{
			String symbol = StringUtils.findMatch(statement, bounds.getEnd(), StringUtils.SYMBOLS);
			
			if (symbol != null)
			{
				return null;
			}
			
			UnaryOperation n = new UnaryOperation(parent, location);
				
			String operatorVal  = statement.substring(bounds.getStart(), bounds.getEnd());
			
			Operator operator = new Operator(n, location);
			operator.setOperator(operatorVal);
			
			int varStart = 0;
			int varEnd   = 0;
			
			if (bounds.getStart() == 0)
			{
				if (SIDES.get(operatorVal) == RIGHT)
				{
					return null;
				}
				
				varStart = bounds.getEnd();
				varEnd   = statement.length();
				
				n.addChild(operator);
			}
			else if (bounds.getEnd() == statement.length())
			{
				if (SIDES.get(operatorVal) == LEFT)
				{
					return null;
				}
				
				varStart = 0;
				varEnd   = bounds.getStart();
			}
			else
			{
				return null;
			}
			
			Location newLoc = new Location(location);
				
			String variableName = statement.substring(varStart, varEnd);
			
			Identifier variable = SyntaxTree.getExistingNode(parent, variableName);
			
			if (variable == null)
			{
				variable = ArrayAccess.decodeStatement(n, variableName, newLoc, false, scope);
			}
			else
			{
				variable = variable.clone(n, newLoc);
			}
			
			if (variable != null)
			{
				n.addChild(variable);
				
				if (bounds.getStart() > 0)
				{
					n.addChild(operator);
				}
				
				n.setType(variable.getType());
				
				return n;
			}
			
			SyntaxMessage.error("Undeclared variable '" + variableName + "'", n);
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public UnaryOperation clone(Node temporaryParent, Location locationIn)
	{
		UnaryOperation node = new UnaryOperation(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given UnaryOperation with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public UnaryOperation cloneTo(UnaryOperation node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
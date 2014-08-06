package net.fathomsoft.nova.tree;

import java.io.ObjectInputStream.GetField;
import java.util.HashMap;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.ArrayAccess;
import net.fathomsoft.nova.tree.variables.Variable;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Node extension that represents a unary operator node type.
 * See {@link #decodeStatement(Node, String, Location, boolean)} for more
 * details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:00:11 PM
 * @version	v0.2.26 Aug 6, 2014 at 2:48:50 PM
 */
public class UnaryOperation extends IValue
{
	private static final int	LEFT = -1, EITHER = 0, RIGHT = 1;
	
	private static final HashMap<String, Integer>	SIDES;
	private static final HashMap<String, Boolean>	REQUIRE_VARIABLE;
	
	/**
	 * Initialize the SIDES HashMap.
	 */
	static
	{
		SIDES            = new HashMap<String, Integer>();
		REQUIRE_VARIABLE = new HashMap<String, Boolean>();
		
		SIDES.put("-",  LEFT);
		SIDES.put("--", EITHER);
		SIDES.put("++", EITHER);
		SIDES.put("!",  LEFT);
		
		REQUIRE_VARIABLE.put("-",  false);
		REQUIRE_VARIABLE.put("--", true);
		REQUIRE_VARIABLE.put("++", true);
		REQUIRE_VARIABLE.put("!",  false);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public UnaryOperation(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Check whether or not the next sequence of chars in the given source
	 * is a unary operator.
	 * 
	 * @param source The source to check.
	 * @param index The index to start the search at.
	 * @return Whether or not the next sequence of chars is a unary
	 * 		operator.
	 */
	public static boolean isUnaryOperator(CharSequence source, int index)
	{
		return isUnaryOperator(source, index, 1);
	}
	
	/**
	 * Check whether or not the next sequence of chars in the given source
	 * is a unary operator.
	 * 
	 * @param source The source to check.
	 * @param index The index to start the search at.
	 * @param direction The direction to search in, e.g., 1 is forward and
	 * 		-1 is backward.
	 * @return Whether or not the next sequence of chars is a unary
	 * 		operator.
	 */
	public static boolean isUnaryOperator(CharSequence source, int index, int direction)
	{
		Bounds bounds = StringUtils.findStrings(source, StringUtils.UNARY_OPERATORS, index, direction);//StringUtils.getGroupedSymbols(source, index, direction);
		
		if (bounds.isValid())
		{
			String unary = source.subSequence(bounds.getStart(), bounds.getEnd()).toString();
			
			// Get the propper sides;
			int foundSide   = direction * -1;
			int correctSide = SIDES.get(unary);
			
			return correctSide == foundSide || correctSide == EITHER;
		}
		
		return false;
	}
	
	/**
	 * The the Node that represents the variable in the operation.<br>
	 * For example:<br>
	 * <blockquote><pre>
	 * var++;</pre></blockquote>
	 * In the previous statement, 'var' is the variable.
	 * 
	 * @return The Node that represents the variable in the operation.
	 */
	public Value getValue()
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			if (!(child instanceof Operator))
			{
				return (Value)child;
			}
		}
		
		return null;
	}
	
	/**
	 * The the Node that represents the operator in the operation.<br>
	 * For example:<br>
	 * <blockquote><pre>
	 * var++;</pre></blockquote>
	 * In the previous statement, '++' is the operator.
	 * 
	 * @return The Node that represents the operator in the operation.
	 */
	public Operator getOperator()
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			if (child instanceof Operator)
			{
				return (Operator)child;
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
			getChild(i).generateCSourceFragment(builder);
		}
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			getChild(i).generateNovaInput(builder);
		}
		
		return builder;
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
	 * @return The generated node, if it was possible to translated it
	 * 		into a UnaryOperation.
	 */
	public static UnaryOperation decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		Bounds bounds = StringUtils.findStrings(statement, StringUtils.UNARY_OPERATORS);
		
		if (bounds.getStart() >= 0)
		{
			UnaryOperation n  = new UnaryOperation(parent, location);
			
			Location newLoc   = location.asNew();
			
			if (!n.decodeOperator(statement, bounds, newLoc))
			{
				return null;
			}
			
			Bounds idBounds = n.calculateIdentifierBounds(statement, bounds);
			
			if (idBounds == null)
			{
				return null;
			}
			
			String contents = statement.substring(idBounds.getStart(), idBounds.getEnd());
			
			if (n.decodeIdentifierValue(contents, newLoc, bounds, require))
			{
				return n;
			}
		}
		
		return null;
	}
	
	/**
	 * Decode, and add, the Operator if possible.
	 * 
	 * @param statement The statement to extract the Operator from.
	 * @param bounds The bounds of the Operator.
	 * @param location The location that the Operator was found at.
	 * @return Whether or not the operator decoded successfully.
	 */
	private boolean decodeOperator(String statement, Bounds bounds, Location location)
	{
		String symbol = StringUtils.findMatch(statement, StringUtils.UNARY_OPERATORS, bounds.getEnd());
		
		if (symbol != null)
		{
			return false;
		}
		
		String operatorVal = statement.substring(bounds.getStart(), bounds.getEnd());
		
		Operator operator  = new Operator(this, location);
		operator.setOperator(operatorVal);
		
		addChild(operator);
		
		return true;
	}
	
	/**
	 * Calculate the Bounds of the identifier that the unary operation is
	 * acting on.
	 * 
	 * @param statement The statement to find the Bounds in.
	 * @param bounds The Bounds of the unary operator.
	 * @return The calculated Bounds of the found identifier.
	 */
	private Bounds calculateIdentifierBounds(String statement, Bounds bounds)
	{
		int varStart = 0;
		int varEnd   = 0;
		
		Operator operator = getOperator();
		
		if (bounds.getStart() == 0)
		{
			if (SIDES.get(operator.getOperator()) == RIGHT)
			{
				return null;
			}
			
			varStart = bounds.getEnd();
			varEnd   = statement.length();
		}
		else if (bounds.getEnd() == statement.length())
		{
			if (SIDES.get(operator.getOperator()) == LEFT)
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
		
		return new Bounds(varStart, varEnd);
	}
	
	/**
	 * Decode the found Identifier Value of the Unary operation.
	 * 
	 * @param contents The String representing the Identifier Value.
	 * @param location The location that the Identifier represents.
	 * @param bounds The unary operator's Bounds.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return Whether or not the Identifier Value decoded successfully.
	 */
	private boolean decodeIdentifierValue(String contents, Location location, Bounds bounds, boolean require)
	{
		Value value = SyntaxTree.decodeValue(this, contents, location, false);
		
		if (value == null)
		{
			value = ArrayAccess.decodeStatement(this, contents, location, false);
			
			if (value == null)
			{
				SyntaxMessage.queryError("Undeclared value '" + contents + "'", this, require);
				
				return false;
			}
		}
		
		if (bounds.getStart() == 0)
		{
			addChild(value);
		}
		else
		{
			addChild(getNumDefaultChildren(), value);
		}
		
		setType(value.getType());
		
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#validate(int)
	 */
	@Override
	public Node validate(int phase)
	{
		if (getValue() instanceof Variable)
		{
			SyntaxUtils.checkVolatile((Variable)getValue());
		}
		
		return this;
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
	 * Fill the given {@link UnaryOperation} with the data that is in the
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
	
	/**
	 * Test the UnaryOperation class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(Nova controller, ClassDeclaration clazz, BodyMethodDeclaration method)
	{
		
		
		return null;
	}
}
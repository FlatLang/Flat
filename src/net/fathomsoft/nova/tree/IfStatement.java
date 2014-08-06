package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Node extension that represents the declaration of an "if statement"
 * node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:57:13 PM
 * @version	v0.2.26 Aug 6, 2014 at 2:48:50 PM
 */
public class IfStatement extends Node
{
	/**
	 * Instantiate a new IfStatement and initialize the default
	 * values.
	 */
	public IfStatement(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		Scope scope = new Scope(this, locationIn);
		
		setScope(scope);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDefaultChildren()
	{
		return super.getNumDefaultChildren() + 1;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDecodedChildren()
	{
		return super.getNumDecodedChildren() + 1;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#pendingScopeFragment()
	 */
	@Override
	public boolean pendingScopeFragment()
	{
		return getScope().isEmpty();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getScope()
	 */
	@Override
	public Scope getScope()
	{
		return (Scope)getChild(super.getNumDefaultChildren());
	}
	
	/**
	 * Get the ArgumentList that contains the condition for the if
	 * statement.
	 * 
	 * @return The ArgumentList instance.
	 */
	public Value getCondition()
	{
		return (Value)getChild(super.getNumDefaultChildren() + 1);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		generateCSourceFragment(builder).append('\n');
		
		getScope().generateCSource(builder);
		
		return builder;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment(java.lang.StringBuilder)
	 */
	@Override
	public StringBuilder generateCSourceFragment(StringBuilder builder)
	{
		return builder.append("if (").append(getCondition().generateCSourceFragment()).append(')');
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		builder.append("if (").append(getCondition().generateNovaInput()).append(')');
		
		if (outputChildren)
		{
			builder.append('\n').append(getScope().generateNovaInput());
		}
		
		return builder;
	}
	
	/**
	 * Decode the given statement into a IfStatement instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>if (index &gt;= array.size())</li>
	 * 	<li>if (getParent().isAlive())</li>
	 * 	<li>if (!person.canWalk() &amp;&amp; !person.isVegetable())</li>
	 * 	<li>if ((age + 2 &gt;= 21 &amp;&amp; gender == "male") || gender == "female")</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		IfStatement instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a IfStatement.
	 */
	public static IfStatement decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (Regex.matches(statement, 0, Patterns.PRE_IF))
		{
			IfStatement n = new IfStatement(parent, location);
			
			Bounds bounds = SyntaxUtils.findInnerParenthesesBounds(n, statement);
			
			if (bounds.getStart() >= 0)
			{
				String contents = statement.substring(bounds.getStart(), bounds.getEnd());
				
				if (n.decodeCondition(contents, bounds, require) && n.decodeScopeFragment(statement, bounds))
				{
					return n;
				}
			}
			else
			{
				SyntaxMessage.error("If statement missing condition", n);
			}
		}
		
		return null;
	}
	
	/**
	 * Decode the condition within the given contents argument.
	 * 
	 * @param contents The condition to decode. 
	 * @param bounds The bounds of the condition.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return Whether or not the condition decoded correctly.
	 */
	private boolean decodeCondition(String contents, Bounds bounds, boolean require)
	{
		Location newLoc = new Location(getLocationIn());
		newLoc.addBounds(bounds.getStart(), bounds.getEnd());
		
		Node condition = SyntaxTree.decodeScopeContents(this, contents, newLoc, require);
		
		if (condition == null)
		{
			return false;
		}
		
		addChild(condition, this);
		
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public IfStatement clone(Node temporaryParent, Location locationIn)
	{
		IfStatement node = new IfStatement(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link IfStatement} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public IfStatement cloneTo(IfStatement node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the IfStatement class type to make sure everything
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

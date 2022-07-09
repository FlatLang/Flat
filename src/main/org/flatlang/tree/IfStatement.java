package org.flatlang.tree;

import org.flatlang.TestContext;
import org.flatlang.error.SyntaxMessage;
import org.flatlang.util.Bounds;
import org.flatlang.util.Location;
import org.flatlang.util.StringUtils;
import org.flatlang.util.SyntaxUtils;

/**
 * Node extension that represents the declaration of an "if statement"
 * node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:57:13 PM
 * @version	v0.2.38 Dec 6, 2014 at 5:19:17 PM
 */
public class IfStatement extends ControlStatement
{
	public static final String IDENTIFIER = "if";
	
	/**
	 * Instantiate a new IfStatement and initialize the default
	 * values.
	 */
	public IfStatement(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * @see Node#getNumDefaultChildren()
	 */
	@Override
	public int getNumDecodedChildren()
	{
		return super.getNumDecodedChildren() + 1;
	}
	
	/**
	 * Get the ArgumentList that contains the condition for the if
	 * statement.
	 * 
	 * @return The ArgumentList instance.
	 */
	public Value getCondition()
	{
		return getNumChildren() > super.getNumDefaultChildren() ? (Value)getChild(super.getNumDefaultChildren() + 0) : null;
	}
	
	/**
	 * @see Node#generateFlatInput(StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateFlatInput(StringBuilder builder, boolean outputChildren)
	{
		if (getCondition() == null) {
			return builder;
		}

		builder.append("if (").append(getCondition().generateFlatInput()).append(')');
		
		if (outputChildren)
		{
			builder.append('\n').append(getScope().generateFlatInput());
		}
		
		return builder;
	}
	
	public static IfStatement generateDefault(Node parent, Location location)
	{
		return decodeStatement(parent, "if (false)", location, true);
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
		if (StringUtils.startsWithWord(statement, IDENTIFIER))
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
		
		Value condition = SyntaxTree.decodeValue(this, contents, newLoc, require);
		
		if (condition == null)
		{
			return false;
		}
		
		addChild(condition, this);
		
		if (!"Bool".equals(condition.getReturnedNode().getType()))
		{
			condition.replaceWithNullCheck();
		}
		
		return true;
	}
	
	/**
	 * @see Node#clone(Node, Location, boolean)
	 */
	@Override
	public IfStatement clone(Node temporaryParent, Location locationIn, boolean cloneChildren, boolean cloneAnnotations)
	{
		IfStatement node = new IfStatement(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren, cloneAnnotations);
	}
	
	/**
	 * @see Node#cloneTo(Node)
	 */
	public IfStatement cloneTo(IfStatement node)
	{
		return cloneTo(node, true, true);
	}
	
	/**
	 * Fill the given {@link IfStatement} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public IfStatement cloneTo(IfStatement node, boolean cloneChildren, boolean cloneAnnotations)
	{
		super.cloneTo(node, cloneChildren, cloneAnnotations);
		
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

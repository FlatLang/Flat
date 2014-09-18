package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * Node extension that represents the declaration of an "else
 * statement" node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:57:13 PM
 * @version	v0.2.26 Aug 6, 2014 at 2:48:50 PM
 */
public class ElseStatement extends Node
{
	/**
	 * Instantiate a new ElseStatement and initialize the default
	 * values.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ElseStatement(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		Scope scope = new Scope(this, locationIn);
		
		setScope(scope);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#pendingScopeFragment()
	 */
	@Override
	public boolean pendingScopeFragment()
	{
		return getScope().getNumChildren() == 1;
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
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		builder.append("else");
		
		if (getNumChildren() == 2)
		{
			Node child = getChild(1);
			
			if (child instanceof IfStatement)
			{
				builder.append(' ');
				
				child.generateCSourceFragment(builder);
			}
		}
		
		builder.append('\n');
		
		return getScope().generateCSource(builder);
	}
	
	/**
	 * Decode the given statement into a ElseStatement instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>else</li>
	 * 	<li>else if (!person.canWalk() &amp;&amp; !person.isVegetable())</li>
	 * 	<li>else doSomethingInOneLine()</li>
	 * 	<li>else counter++</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ElseStatement instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ElseStatement.
	 */
	public static ElseStatement decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		Bounds bounds = Regex.boundsOf(statement, Patterns.ELSE);
		
		if (bounds.getStart() == 0)
		{
			ElseStatement n = new ElseStatement(parent, location);
			
			String   ending = statement.substring(bounds.getEnd());
			
			Location newLocation = location.asNew();
			newLocation.setBounds(location.getStart() + bounds.getEnd(), location.getStart() + statement.length());
			
			if (ending.length() > 0)
			{
				Node contents = SyntaxTree.decodeScopeContents(parent, ending, newLocation, require);
				
				if (contents != null)
				{
					n.addChild(contents, n);
				}
			}
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public ElseStatement clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		ElseStatement node = new ElseStatement(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link IfStatement} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ElseStatement cloneTo(ElseStatement node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the ElseStatement class type to make sure everything
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

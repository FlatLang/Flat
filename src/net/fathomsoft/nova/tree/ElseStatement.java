package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * Node extension that represents the declaration of an "else
 * statement" node type. See {@link #decodeStatement(Node, String, Location, boolean, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:57:13 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class ElseStatement extends Node
{
	/**
	 * Instantiate a new ElseStatementNode and initialize the default
	 * values.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ElseStatement(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		Scope scope = new Scope(this, locationIn);
		
		setScopeNode(scope);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getScopeNode()
	 */
	@Override
	public Scope getScopeNode()
	{
		return (Scope)getChild(0);
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#addChild(Node)
	 */
	@Override
	public void addChild(Node child)
	{
		if (child instanceof Scope || (getNumChildren() <= 1 && child instanceof IfStatement))
		{
			super.addChild(child);
		}
		else
		{
			getScopeNode().addChild(child);
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("else");
		
		if (getNumChildren() == 2)
		{
			Node child = getChild(1);
			
			if (child instanceof IfStatement)
			{
				builder.append(' ').append(child.generateCSource());
				
				// Delete the new line at the end.
				builder.deleteCharAt(builder.length() - 1);
			}
		}
		
		builder.append('\n');
	
		builder.append(getScopeNode().generateCSource());
		
//		builder.append('{').append('\n');
//		
//		for (int i = 0; i < getNumChildren(); i++)
//		{
//			Node child = getChild(i);
//			
//			if (child != getCondition())
//			{
//				builder.append(child.generateCSourceOutput());
//			}
//		}
//		
//		builder.append('}').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * Decode the given statement into a ElseStatementNode instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>else</li>
	 * 	<li>else if (!person.canWalk() && !person.isVegetable())</li>
	 * 	<li>else doSomethingInOneLine()</li>
	 * 	<li>else counter++</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ElseStatementNode instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ElseStatementNode.
	 */
	public static ElseStatement decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		Bounds bounds = Regex.boundsOf(statement, Patterns.ELSE);
		
		if (bounds.getStart() == 0)
		{
			ElseStatement n  = new ElseStatement(parent, location);
			
			String   ending      = statement.substring(bounds.getEnd());
			
			Location newLocation = new Location(location);
			newLocation.setBounds(location.getStart() + bounds.getEnd(), location.getStart() + statement.length());
			
			if (ending.length() > 0)
			{
				Node contents = SyntaxTree.decodeScopeContents(parent, ending, newLocation, require, false);
				
				if (contents != null)
				{
					n.addChild(contents);
				}
			}
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public ElseStatement clone(Node temporaryParent, Location locationIn)
	{
		ElseStatement node = new ElseStatement(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given IfStatementNode with the data that is in the
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
}

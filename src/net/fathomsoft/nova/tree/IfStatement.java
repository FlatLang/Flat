package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * Node extension that represents the declaration of an "if statement"
 * node type. See {@link #decodeStatement(Node, String, Location, boolean, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:57:13 PM
 * @version	v0.2.14 Jun 18, 2014 at 10:11:40 PM
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
		
		ArgumentList condition = new ArgumentList(this, locationIn);
		Scope        scope = new Scope(this, locationIn);
		
		setScope(scope);
		addChild(condition, this);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getScope()
	 */
	@Override
	public Scope getScope()
	{
		return (Scope)getChild(0);
	}
	
	/**
	 * Get the ArgumentList that contains the condition for the if
	 * statement.
	 * 
	 * @return The ArgumentList instance.
	 */
	public ArgumentList getCondition()
	{
		return (ArgumentList)getChild(1);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		builder.append("if (");
		
		ArgumentList condition = getCondition();
		
		for (int i = 0; i < condition.getNumChildren(); i++)
		{
			Node child = condition.getChild(i);
			
			child.generateCSourceFragment(builder);
		}
		
		builder.append(')').append('\n');
		
		getScope().generateCSource(builder);
		
		return builder;
	}
	
	/**
	 * Decode the given statement into a IfStatement instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>if (index >= array.size())</li>
	 * 	<li>if (getParent().isAlive())</li>
	 * 	<li>if (!person.canWalk() && !person.isVegetable())</li>
	 * 	<li>if ((age + 2 >= 21 && gender == "male") || gender == "female")</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		IfStatement instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a IfStatement.
	 */
	public static IfStatement decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		if (Regex.matches(statement, 0, Patterns.PRE_IF))
		{
			IfStatement n = new IfStatement(parent, location);
			
			Bounds bounds = Regex.boundsOf(statement, Patterns.IF_CONTENTS);
			
			if (bounds.getStart() >= 0)
			{
				String contents = statement.substring(bounds.getStart(), bounds.getEnd());
				
				Location newLoc = new Location();
				newLoc.setLineNumber(location.getLineNumber());
				newLoc.setBounds(location.getStart() + bounds.getStart(), location.getStart() + bounds.getEnd());
				
				Node condition = BinaryOperation.decodeStatement(parent, contents, newLoc, require, false);
				
				if (condition == null)
				{
					condition = SyntaxTree.getExistingNode(parent, contents);
					
//					SyntaxMessage.error("Could not decode condition", parent.getFileDeclaration(), newLoc, parent.getController());
				}
				if (condition == null)
				{
					return null;
				}
				
				n.getCondition().addChild(condition);
				
				return n;
			}
			else
			{
				SyntaxMessage.error("If statement missing condition", n);
			}
		}
		
		return null;
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
	 * Fill the given IfStatement with the data that is in the
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
}

package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * LoopNode extension that represents the declaration of a "for loop"
 * node type. See {@link #decodeStatement(Node, String, Location, boolean, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:55:15 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class ForLoop extends Loop
{
	/**
	 * Instantiate a new ForLoopNode and initialize its default values.
	 * 
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ForLoop(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		ArgumentList argumentsNode = new ArgumentList(this, locationIn);
		
		addChild(argumentsNode, this);
	}
	
	/**
	 * Get the ArgumentListNode instance that contains the initialization,
	 * condition, and update nodes that instruct the for loop.
	 * 
	 * @return The ArgumentListNode instance containing the arguments of
	 * 		the for loop.
	 */
	public ArgumentList getArgumentListNode()
	{
		return (ArgumentList)getChild(1);
	}
	
	/**
	 * Get the Node that describes the initialization section of the
	 * for loop. For instance: "for (int i = 0; i < 10; i++)" the first
	 * section containing "int i = 0" is the initialization section.
	 * 
	 * @return The Node instance that describes the initialization
	 * 		section of the for loop.
	 */
	public Assignment getInitializationNode()
	{
		return (Assignment)getArgumentListNode().getChild(0);
	}
	
	/**
	 * Get the Node that describes the condition section of the for
	 * loop. For instance: "for (int i = 0; i < 10; i++)" the middle
	 * section containing "i < 10" is the condition section.
	 * 
	 * @return The Node instance that describes the condition section
	 * 		of the for loop.
	 */
	public Node getConditionNode()
	{
		return getArgumentListNode().getChild(1);
	}
	
	/**
	 * Get the Node that describes the update section of the for loop.
	 * For instance: "for (int i = 0; i < 10; i++)" the last section
	 * containing "i++" is the update section.
	 * 
	 * @return The Node instance that describes the update section of
	 * 		the for loop.
	 */
	public Node getUpdateNode()
	{
		return getArgumentListNode().getChild(2);
	}

	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder  builder = new StringBuilder();
		
		Assignment initialization = getInitializationNode();
		Node       condition      = getConditionNode();
		Node       update         = getUpdateNode();
		
		if (initialization != null)
		{
			builder.append(initialization.generateCSource()).append('\n');
		}
		
		builder.append("for (");
		
		builder.append(';').append(' ');
		
		if (condition != null)
		{
			builder.append(condition.generateCSourceFragment());
		}
		
		builder.append(';').append(' ');
		
		if (update != null)
		{
			builder.append(update.generateCSourceFragment());
		}
		
		builder.append(')').append('\n');
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
			if (child != getArgumentListNode())
			{
				builder.append(child.generateCSource());
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * Decode the given statement into a ForLoopNode instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>for (int i = 0; i < array.size(); i++)</li>
	 * 	<li>for (index = size - 1; index >= 0; index -= 2) <i>(Where index and size are already declared variables)</i></li>
	 * 	<li>for (;;) <i>(This is an infinite loop. Preferably use "while (true)" instead.)</i></li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ForLoopNode instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ForLoopNode.
	 */
	public static ForLoop decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		if (Regex.matches(statement, 0, Patterns.PRE_FOR))
		{
			ForLoop n = new ForLoop(parent, location);
			
			Bounds bounds = Regex.boundsOf(statement, Patterns.FOR_CONTENTS);
			
			if (bounds.getStart() >= 0)
			{
				Location newLoc    = new Location();
				newLoc.setLineNumber(location.getLineNumber());
				newLoc.setBounds(location.getStart() + bounds.getStart(), location.getStart() + bounds.getEnd());
				
				String contents    = statement.substring(bounds.getStart(), bounds.getEnd());
				
				String arguments[] = contents.split("\\s*;\\s*");
				
				Assignment initialization = Assignment.decodeStatement(parent, arguments[0], newLoc, require, false);
				n.getArgumentListNode().addChild(initialization);
				
				Identifier var      = initialization.getAssigneeNode();
				Identifier existing = SyntaxTree.getExistingNode(parent, var.getName());
				
				if (var.getLocationIn().getBounds().equals(existing.getLocationIn().getBounds()))
				{
					LocalDeclaration declaration = (LocalDeclaration)existing;
					
					declaration.setScopeID(n.getScopeNode().getID());
					
//					n.addChild(var);
//					
//					LocalVariableNode local = var.clone(n, newLoc);
//					
//					initialization.addChild(0, local);
				}
				
				Node condition = BinaryOperator.decodeStatement(parent, arguments[1], newLoc, require, false);
				
				if (condition == null)
				{
					condition = SyntaxTree.getExistingNode(parent, arguments[1]);
					
//					SyntaxMessage.error("Could not decode condition", parent.getFileNode(), newLoc, parent.getController());
				}
				if (condition == null)
				{
					return null;
				}
				
				n.getArgumentListNode().addChild(condition);
				
				UnaryOperation unaryUpdate = UnaryOperation.decodeStatement(parent, arguments[2], newLoc, require, false);
				
				if (unaryUpdate != null)
				{
					n.getArgumentListNode().addChild(unaryUpdate);
				}
				else
				{
					Assignment update = Assignment.decodeStatement(parent, arguments[2], newLoc, require, false);
					
					n.getArgumentListNode().addChild(update);
				}
				
				return n;
			}
			else
			{
				SyntaxMessage.error("For loop missing arguments", n);
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public ForLoop clone(Node temporaryParent, Location locationIn)
	{
		ForLoop node = new ForLoop(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ForLoopNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ForLoop cloneTo(ForLoop node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
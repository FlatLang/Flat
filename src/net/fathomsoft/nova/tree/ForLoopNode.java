package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.variables.VariableNode;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * LoopNode extension that represents the declaration of a "for loop"
 * node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 9:55:15 PM
 * @version	v0.2.12 Jun 1, 2014 at 7:28:35 PM
 */
public class ForLoopNode extends LoopNode
{
	/**
	 * Instantiate a new ForLoopNode and initialize its default values.
	 * 
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public ForLoopNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		ArgumentListNode argumentsNode = new ArgumentListNode(this, locationIn);
		
		addChild(1, argumentsNode);
	}
	
	/**
	 * Get the ArgumentListNode instance that contains the initialization,
	 * condition, and update nodes that instruct the for loop.
	 * 
	 * @return The ArgumentListNode instance containing the arguments of
	 * 		the for loop.
	 */
	public ArgumentListNode getArgumentListNode()
	{
		return (ArgumentListNode)getChild(1);
	}
	
	/**
	 * Get the TreeNode that describes the initialization section of the
	 * for loop. For instance: "for (int i = 0; i < 10; i++)" the first
	 * section containing "int i = 0" is the initialization section.
	 * 
	 * @return The TreeNode instance that describes the initialization
	 * 		section of the for loop.
	 */
	public AssignmentNode getInitializationNode()
	{
		return (AssignmentNode)getArgumentListNode().getChild(0);
	}
	
	/**
	 * Get the TreeNode that describes the condition section of the for
	 * loop. For instance: "for (int i = 0; i < 10; i++)" the middle
	 * section containing "i < 10" is the condition section.
	 * 
	 * @return The TreeNode instance that describes the condition section
	 * 		of the for loop.
	 */
	public TreeNode getConditionNode()
	{
		return getArgumentListNode().getChild(1);
	}
	
	/**
	 * Get the TreeNode that describes the update section of the for loop.
	 * For instance: "for (int i = 0; i < 10; i++)" the last section
	 * containing "i++" is the update section.
	 * 
	 * @return The TreeNode instance that describes the update section of
	 * 		the for loop.
	 */
	public TreeNode getUpdateNode()
	{
		return getArgumentListNode().getChild(2);
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder  builder = new StringBuilder();
		
		AssignmentNode initialization = getInitializationNode();
		TreeNode       condition      = getConditionNode();
		TreeNode       update         = getUpdateNode();
		
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
			TreeNode child = getChild(i);
			
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
	public static ForLoopNode decodeStatement(TreeNode parent, String statement, Location location, boolean require, boolean scope)
	{
		if (Regex.matches(statement, 0, Patterns.PRE_FOR))
		{
			ForLoopNode n = new ForLoopNode(parent, location);
			
			Bounds bounds = Regex.boundsOf(statement, Patterns.FOR_CONTENTS);
			
			if (bounds.getStart() >= 0)
			{
				Location newLoc    = new Location();
				newLoc.setLineNumber(location.getLineNumber());
				newLoc.setBounds(location.getStart() + bounds.getStart(), location.getStart() + bounds.getEnd());
				
				String contents    = statement.substring(bounds.getStart(), bounds.getEnd());
				
				String arguments[] = contents.split("\\s*;\\s*");
				
				AssignmentNode initialization = AssignmentNode.decodeStatement(parent, arguments[0], newLoc, require, false);
				n.getArgumentListNode().addChild(initialization);
				
				IdentifierNode var      = initialization.getAssigneeNode();
				IdentifierNode existing = TreeNode.getExistingNode(parent, var.getName());
				
				if (var.getLocationIn().getBounds().equals(existing.getLocationIn().getBounds()))
				{
					LocalDeclarationNode declaration = (LocalDeclarationNode)existing;
					
					declaration.setScopeID(n.getScopeNode().getID());
					
//					n.addChild(var);
//					
//					LocalVariableNode local = var.clone(n, newLoc);
//					
//					initialization.addChild(0, local);
				}
				
				TreeNode condition = BinaryOperatorNode.decodeStatement(parent, arguments[1], newLoc, require, false);
				
				if (condition == null)
				{
					condition = getExistingNode(parent, arguments[1]);
					
//					SyntaxMessage.error("Could not decode condition", parent.getFileNode(), newLoc, parent.getController());
				}
				if (condition == null)
				{
					return null;
				}
				
				n.getArgumentListNode().addChild(condition);
				
				UnaryOperatorNode unaryUpdate = UnaryOperatorNode.decodeStatement(parent, arguments[2], newLoc, require, false);
				
				if (unaryUpdate != null)
				{
					n.getArgumentListNode().addChild(unaryUpdate);
				}
				else
				{
					AssignmentNode update = AssignmentNode.decodeStatement(parent, arguments[2], newLoc, require, false);
					
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
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public ForLoopNode clone(TreeNode temporaryParent, Location locationIn)
	{
		ForLoopNode node = new ForLoopNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ForLoopNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ForLoopNode cloneTo(ForLoopNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
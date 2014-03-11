package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.error.SyntaxError;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:19:44 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:19:44 PM
 * @version	v
 */
public class AssignmentNode extends TreeNode
{
	public AssignmentNode()
	{
		
	}
	
	public IdentifierNode getIdentifierNode()
	{
		if (getChildren().size() <= 0)
		{
			Location location = getLocationIn();
			
			SyntaxError.outputNewError("Variable missing on left side of assignment", location);
			
			return null;
		}
		
		TreeNode node = getChild(0);
		
		if (node instanceof IdentifierNode)
		{
			return (IdentifierNode)node;
		}
		else
		{
			Location location = getLocationIn();
			
			SyntaxError.outputNewError("Variable or identifier missing on left side of assignment", location);
		
			return null;
		}
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getChild(0).generateJavaSourceOutput()).append(" = ");
		
		for (int i = 1; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateJavaSourceOutput());
		}
		
		builder.append(';').append('\n');
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		return null;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getChild(0).generateCSourceOutput()).append(" = ");
		
		for (int i = 1; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateCSourceOutput());
		}
		
		builder.append(';').append('\n');
		
		return builder.toString();
	}
	
	public static AssignmentNode decodeStatement(TreeNode parentNode, String statement, Location location)
	{
		AssignmentNode n  = new AssignmentNode();
		
		int equalIndex    = Regex.indexOf(statement, Patterns.PRE_EQUALS_SIGN);
		
		if (equalIndex < 0)
		{
			return null;
		}
		
		String variable   = statement.substring(0, equalIndex);
		
		IdentifierNode identifier = LocalVariableNode.decodeStatement(parentNode, variable, location);
		
		if (identifier != null)
		{
			if (parentNode instanceof MethodNode)
			{
				MethodNode methodNode = (MethodNode)parentNode;
				
				methodNode.getLocalVariableListNode().addChild(identifier);
			}
		}
		else
		{
			identifier = new IdentifierNode();
			
			identifier.setName(variable);
		}
		
		// Create a new Identifier node because the tree does not allow duplicate entries.
		IdentifierNode i = new IdentifierNode();
		i.setName(identifier.getName());
		
		n.addChild(i);
		
		int      rhsIndex = Regex.indexOf(statement, Patterns.POST_EQUALS_SIGN);
		
		// Right-hand side of the equation.
		String   rhs      = statement.substring(rhsIndex);
		
		TreeNode value    = BinaryOperatorNode.decodeStatement(parentNode, rhs, location);
		
		n.addChild(value);
		
		return n;
	}
}
package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.StringUtils;
import net.fathomsoft.fathom.util.SyntaxUtils;

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
	
	public VariableNode getVariableNode()
	{
		if (getChildren().size() <= 0)
		{
			Location location = getLocationIn();
			
			SyntaxMessage.error("Variable missing on left side of assignment", location);
			
			return null;
		}
		
		TreeNode node = getChild(0);
		
		if (node instanceof VariableNode)
		{
			return (VariableNode)node;
		}
		else
		{
			Location location = getLocationIn();
			
			SyntaxMessage.error("Variable missing on left side of assignment", location);
		
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
		
		builder.append(getVariableNode().generateVariableUseOutput());
		
		builder.append(" = ");
		
		for (int i = 1; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child instanceof VariableNode)
			{
				builder.append(((VariableNode) child).generateVariableUseOutput());
			}
			else
			{
				builder.append(child.generateCSourceOutput());
			}
		}
		
		builder.append(';').append('\n');
		
		return builder.toString();
	}
	
	public static AssignmentNode decodeStatement(TreeNode parentNode, String statement, Location location)
	{
		AssignmentNode n  = new AssignmentNode();
		
		if (!SyntaxUtils.isVariableAssignment(statement))
		{
			return null;
		}
		
		Bounds bounds   = Regex.boundsOf(statement, Patterns.PRE_EQUALS_SIGN);
		
		int equalsIndex = bounds.getEnd() - 1;
		
		int endIndex    = StringUtils.findNextNonWhitespaceIndex(statement, equalsIndex - 1, -1) + 1;
		
		String variable = statement.substring(0, endIndex);
		
		VariableNode varNode = null;
		
		if (SyntaxUtils.isValidIdentifier(variable))
		{
			varNode = (VariableNode)TreeNode.getExistingNode(parentNode, variable);
			
			varNode = varNode.clone();
		}
		else
		{
			varNode = LocalVariableNode.decodeStatement(parentNode, variable, location);
			
			if (varNode != null)
			{
				MethodNode methodNode = (MethodNode)parentNode.getAncestorOfType(MethodNode.class, true);
				
				if (methodNode != null)
				{
					methodNode.getLocalVariableListNode().addChild(varNode.clone());
				}
			}
			else
			{
				varNode = new VariableNode();
				
				varNode.setName(variable);
			}
		}
		
		n.addChild(varNode);
		
		int      rhsIndex = StringUtils.findNextNonWhitespaceIndex(statement, equalsIndex + 1);
		
		// Right-hand side of the equation.
		String   rhs      = statement.substring(rhsIndex);
		
		TreeNode value    = BinaryOperatorNode.decodeStatement(parentNode, rhs, location);
		
		n.addChild(value);
		
		return n;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public AssignmentNode clone()
	{
		AssignmentNode clone = new AssignmentNode();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			clone.addChild(child.clone());
		}
		
		return clone;
	}
}
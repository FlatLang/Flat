package net.fathomsoft.fathom.tree;

import java.util.regex.Matcher;

import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.SyntaxUtils;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:20:35 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:20:35 PM
 * @version	v
 */
public class BinaryOperatorNode extends TreeNode
{
	public BinaryOperatorNode()
	{
		
	}
	
	public boolean checkOperator()
	{
		if (getChildren().size() <= 0)
		{
			SyntaxMessage.error("Missing operator", getLocationIn());
			
			return false;
		}
		
		TreeNode node = getChild(1);
		
		if (node instanceof OperatorNode)
		{
			return true;
		}
		else
		{
			SyntaxMessage.error("Missing operator", getLocationIn());
		
			return false;
		}
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		if (!checkOperator())
		{
			return null;
		}
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateJavaSourceOutput());
			
			if (i < getChildren().size() - 1)
			{
				builder.append(' ');
			}
		}
		
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
		if (!checkOperator())
		{
			return null;
		}
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateCSourceOutput());
			
			if (i < getChildren().size() - 1)
			{
				builder.append(' ');
			}
		}
		
		return builder.toString();
	}
	
	public static TreeNode decodeStatement(TreeNode parentNode, String statement, Location location)
	{
		// Pattern used to find word boundaries. 
		Matcher matcher = Patterns.BINARY_ARITH_OPERATORS.matcher(statement);
		
		return decodeStatement(parentNode, statement, matcher, location);
	}
	
	private static TreeNode decodeStatement(TreeNode parentNode, String statement, Matcher matcher, Location location)
	{
		return decodeStatement(parentNode, statement, 0, matcher, location);
	}
	
	private static TreeNode decodeStatement(TreeNode parentNode, String statement, int offset, Matcher matcher, Location location)
	{
		if (matcher.find())
		{
			BinaryOperatorNode node = new BinaryOperatorNode();
			
			// Decode the value on the left.
			int    endIndex = Regex.indexOf(statement, Patterns.PRE_OPERATORS);
			
			// The left-hand value.
			String lhv      = statement.substring(0, endIndex);
			
			// The left-hand node.
			TreeNode lhn    = getNode(parentNode, lhv, location);
			
			node.addChild(lhn);
			
			// Decode the operator.
			endIndex              = Regex.indexOf(statement, matcher.start() - offset, Patterns.WHITESPACE);
			String operatorVal    = statement.substring(matcher.start() - offset, endIndex);
			
			OperatorNode operator = new OperatorNode();
			operator.setOperator(operatorVal);
			
			node.addChild(operator);
			
			// Decode the value on the right.
			endIndex  = Regex.indexOf(statement, endIndex, Patterns.NON_WHITESPACE);
			offset   += endIndex;
			statement = statement.substring(endIndex);
			
			node.addChild(decodeStatement(parentNode, statement, offset, matcher, location));
			
			return node;
		}
		else if (matcher.hitEnd())
		{
			return getNode(parentNode, statement, location);
			
//			ClassNode thisClass  = (ClassNode)parentNode.getAncestorOfType(ClassNode.class, true);
//			ClassNode nodesClass = (ClassNode)node.getAncestorOfType(ClassNode.class, true);
//			
//			if (node instanceof VariableNode)
//			{
//				if (thisClass == nodesClass)
//				{
//					
//				}
//			}
		}
		
		return null;
	}
	
	/**
	 * Create a LiteralNode describing what is in the statement.
	 * 
	 * @param parentNode The parent node of the current location.
	 * @param statement The statement to generate a Literal node after.
	 * @param location The location of the statement.
	 * @return The generated LiteralNode.
	 */
	private static TreeNode getNode(TreeNode parentNode, String statement, Location location)
	{
//		IdentifierNode node = TreeNode.getExistingNode(parentNode, statement);
//		
//		if (node != null)
//		{
//			node = node.clone();
//			
//			String visibility = "";
//			
//			if (node instanceof FieldNode)
//			{
//				FieldNode field = (FieldNode)node;
//				
//				if (field.getVisibility() == FieldNode.PRIVATE)
//				{
//					visibility = MethodNode.getObjectReferenceIdentifier() + "->" + "prv->";
//				}
//			}
//			
//			return node;
//		}
		
		if (SyntaxUtils.isLiteral(statement))
		{
			LiteralNode literal = new LiteralNode();
			
			literal.setValue(statement);
			
			return literal;
		}
		else if (SyntaxUtils.isInstantiation(statement))
		{
			InstantiationNode node = InstantiationNode.decodeStatement(parentNode, statement, location);
			
			return node;
		}
		else if (SyntaxUtils.isMethodCall(statement))
		{
			MethodCallNode node = MethodCallNode.decodeStatement(parentNode, statement, location);
			
			return node;
		}
		else if (SyntaxUtils.isValidIdentifier(statement))
		{
			IdentifierNode node = TreeNode.getExistingNode(parentNode, statement);
			
			if (node != null)
			{
				node = node.clone();
				
				return node;
			}
		}
		
		SyntaxMessage.error("Could not parse operation", location);
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public BinaryOperatorNode clone()
	{
		BinaryOperatorNode clone = new BinaryOperatorNode();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			clone.addChild(child.clone());
		}
		
		return clone;
	}
}

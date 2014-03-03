package net.fathomsoft.fathom.tree;

import java.util.regex.Matcher;

import net.fathomsoft.fathom.error.SyntaxError;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;

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
			SyntaxError.outputNewError("Missing operator", getLocationIn());
			
			return false;
		}
		
		TreeNode node = getChild(1);
		
		if (node instanceof OperatorNode)
		{
			return true;
		}
		else
		{
			SyntaxError.outputNewError("Missing operator", getLocationIn());
		
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
		Matcher matcher  = Patterns.OPERATORS.matcher(statement);
		
		return decodeStatement(statement, matcher);
	}
	
	private static TreeNode decodeStatement(String value, Matcher matcher)
	{
		return decodeStatement(value, 0, matcher);
	}
	
	private static TreeNode decodeStatement(String value, int offset, Matcher matcher)
	{
		if (matcher.find())
		{
			BinaryOperatorNode node = new BinaryOperatorNode();
			
			// Decode the value on the left.
			int    endIndex = Regex.indexOf(value, Patterns.PRE_OPERATORS);
			String lhv      = value.substring(0, endIndex);
			
			LiteralNode literal = new LiteralNode();
			literal.setValue(lhv);
			
			node.addChild(literal);
			
			// Decode the operator.
			endIndex              = Regex.indexOf(value, matcher.start() - offset, Patterns.WHITESPACE);
			String operatorVal    = value.substring(matcher.start() - offset, endIndex);
			
			OperatorNode operator = new OperatorNode();
			operator.setOperator(operatorVal);
			
			node.addChild(operator);
			
			// Decode the value on the right.
			endIndex = Regex.indexOf(value, endIndex, Patterns.NON_WHITESPACE);
			offset  += endIndex;
			value    = value.substring(endIndex);
			
			node.addChild(decodeStatement(value, offset, matcher));
			
			return node;
		}
		else if (matcher.hitEnd())
		{
			LiteralNode literal = new LiteralNode();
			literal.setValue(value);
			
			return literal;
		}
		
		return null;
	}
}
package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.error.SyntaxError;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Regex;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 10:04:31 PM
 * @since	v
 * @version	Jan 5, 2014 at 10:04:31 PM
 * @version	v
 */
public class MethodCallNode extends IdentifierNode
{
	public MethodCallNode()
	{
		ArgumentListNode arguments = new ArgumentListNode();
		
		addChild(arguments);
	}
	
	public ArgumentListNode getArgumentListNode()
	{
		return (ArgumentListNode)getChild(0);
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getName()).append('(');
		
		builder.append(getArgumentListNode().generateJavaSourceOutput());
		
		builder.append(");").append('\n');
		
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
		
		builder.append(getName()).append('(');
		
		builder.append(getArgumentListNode().generateJavaSourceOutput());
		
		builder.append(");").append('\n');
		
		return builder.toString();
	}
	
	public static MethodCallNode decodeStatement(TreeNode parentNode, String statement, Location location)
	{
		int whitespaceIndex   = Regex.indexOf(statement, "\\s");
		int firstParenthIndex = Regex.indexOf(statement, '(');
		int lastParenthIndex  = Regex.lastIndexOf(statement, ')');
		
		if (firstParenthIndex > 0 && (whitespaceIndex > firstParenthIndex || whitespaceIndex < 0))
		{
			// TODO: make better check for last parenth. Take a count of each of the starting parenthesis and
			// subtract the ending ones from the number.
			if (lastParenthIndex < 0)
			{
				SyntaxError.outputNewError("Expected a ')' ending parenthesis", location);
				
				return null;
			}
			
			Location argsLocation = new Location();
			argsLocation.setLineNumber(location.getLineNumber());
			argsLocation.setOffset(firstParenthIndex + 1);
			
			String argumentList = statement.substring(firstParenthIndex + 1, lastParenthIndex);
			
			String arguments[]  = Regex.splitCommas(argumentList);
			
			statement = statement.substring(0, firstParenthIndex);
			
			MethodCallNode n = new MethodCallNode()
			{
				public void interactWord(String word, int argNum)
				{
					setName(word);
				}
			};
			
			n.iterateWords(statement);
			
			n.addArguments(parentNode, arguments, argsLocation);
			
			return n;
		}
		
		return null;
	}
	
	private boolean addArguments(TreeNode parent, String arguments[], Location location)
	{
		for (int i = 0; i < arguments.length; i++)
		{
			String argument = arguments[i];
			
			if (argument.length() > 0)
			{
				if (argument.indexOf('(') >= 0)
				{
					if (argument.indexOf('(') == 0)
					{
						argument = argument.substring(1, argument.length() - 1);
					}
				}
				
				TreeNode arg = TreeNode.decodeStatement(this, argument, location);
				
				if (arg == null)
				{
					SyntaxError.outputNewError("Incorrect argument definition", location);
					
					return false;
				}
				
				getArgumentListNode().addChild(arg);
			}
		}
		
		return true;
	}
}
package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;
import net.fathomsoft.fathom.util.StringUtils;

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
	
	public static boolean isMethodCall(String statement)
	{
		Bounds bounds = Regex.boundsOf(statement, Patterns.PRE_METHOD_CALL);
		
		return bounds.getStart() == 0;
	}
	
	public static MethodCallNode decodeStatement(TreeNode parentNode, String statement, Location location)
	{
		if (isMethodCall(statement))
		{
			Bounds bounds  = new Bounds(0, 0);//Regex.boundsOf(statement, Patterns.POST_METHOD_CALL);
			
			int    start   = statement.indexOf('(');
			
			int    nameEnd = StringUtils.findNextNonWhitespaceIndex(statement, start - 1, -1) + 1;
			
			int    end     = StringUtils.findEndingMatch(statement, start, '(', ')');
			
			if (end >= 0)
			{
				end = StringUtils.findNextNonWhitespaceIndex(statement, end - 1, -1) + 1;
			}
			
			start = StringUtils.findNextNonWhitespaceIndex(statement, start + 1);
			
			bounds.setStart(start);
			bounds.setEnd(end);
			
			// TODO: make better check for last parenth. Take a count of each of the starting parenthesis and
			// subtract the ending ones from the number.
			if (bounds.getEnd() < 0)
			{
				SyntaxMessage.error("Expected a ')' ending parenthesis", location);
				
				return null;
			}
			
			Location argsLocation = new Location();
			argsLocation.setLineNumber(location.getLineNumber());
			argsLocation.setOffset(bounds.getStart());
			
			String argumentList = statement.substring(bounds.getStart(), bounds.getEnd());
			
			String arguments[]  = Regex.splitCommas(argumentList);
			
			statement = statement.substring(0, nameEnd);
			
			MethodCallNode n = new MethodCallNode()
			{
				public void interactWord(String word, int argNum, Bounds bounds, int numWords)
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
				
				TreeNode arg = TreeNode.getExistingNode(parent, argument);
				
				if (arg == null)
				{
					arg = TreeNode.decodeStatement(this, argument, location);
				}
				
				if (arg == null)
				{
					SyntaxMessage.error("Incorrect argument definition", location);
					
					return false;
				}
				
				getArgumentListNode().addChild(arg);
			}
		}
		
		return true;
	}
}
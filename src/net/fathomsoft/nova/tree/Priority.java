package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Value extension that represents an operation within parentheses.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.10 May 29, 2014 at 1:50:25 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class Priority extends Value
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Priority(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get the Value that represents the contents inside the
	 * parentheses.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * int j = (32 + 3 * 3);</pre></blockquote>
	 * In the statement above, The binary operation "<u><code>32 + 3 * 3</code></u>"
	 * is the contents of the Priority node.
	 * 
	 * @return The Value that represents the contents inside
	 * 		the parentheses.
	 */
	public Value getContents()
	{
		return (Value)getChild(0);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#getReturnedNode()
	 */
	@Override
	public Value getReturnedNode()
	{
		Value contents = getContents();
		
		return contents.getReturnedNode();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Value#generateNovaInput(boolean)
	 */
	@Override
	public String generateNovaInput(boolean outputChildren)
	{
		StringBuilder builder  = new StringBuilder();
		
		Value     contents = getContents();
		
		builder.append('(').append(contents.generateNovaInput(outputChildren)).append(')');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		return generateCSourceFragment();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		StringBuilder builder  = new StringBuilder();
		
		Value     contents = getContents();
		
		builder.append('(').append(contents.generateCSourceFragment()).append(')');
		
		return builder.toString();
	}
	
	/**
	 * Decode the given statement into a Priority instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>externalHeaderName</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		Priority instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Priority.
	 */
	public static Priority decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		if (statement.charAt(0) == '(')
		{
			Priority n  = new Priority(parent, location);
			
			int endingIndex = StringUtils.findEndingMatch(statement, 0, '(', ')');
			
			if (endingIndex < 0)
			{
				if (!require)
				{
					return null;
				}
				
				SyntaxMessage.error("Missing ending parenthesis", n);
			}
			else if (endingIndex < statement.length() - 1)
			{
				return null;
			}
			
			statement = statement.substring(1, statement.length() - 1);
			
			statement = StringUtils.trimSurroundingWhitespace(statement);
			
			Location contentsLoc = new Location(location);
			contentsLoc.setOffset(location.getOffset() + 1);
			contentsLoc.setBounds(location.getStart() + 1, location.getEnd() - 1);
			
			Value contents = UnaryOperation.decodeStatement(n, statement, contentsLoc, require, false);
			
			if (contents == null)
			{
				contents = BinaryOperation.decodeStatement(n, statement, contentsLoc, require, false);
			}
			if (contents == null && SyntaxUtils.isLiteral(statement))
			{
				Literal literal = Literal.decodeStatement(n, statement, contentsLoc, require, false);
				
				contents = literal;
			}
			if (contents == null)
			{
				contents = SyntaxTree.getExistingNode(n, statement);
			}
			if (contents == null)
			{
				if (!require)
				{
					return null;
				}
				
				SyntaxMessage.error("Could not decode contents '" + statement + "'", n);
			}
			else
			{
				contents = contents.clone(n, contentsLoc);
			}
			
			n.addChild(contents);
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Priority clone(Node temporaryParent, Location locationIn)
	{
		Priority node = new Priority(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given Priority with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Priority cloneTo(Priority node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
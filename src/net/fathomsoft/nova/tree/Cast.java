package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;
import net.fathomsoft.nova.util.SyntaxUtils;

/**
 * Node extension that represents what one type is being casted
 * to another. See 
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.25 Aug 3, 2014 at 1:52:00 PM
 * @version	v0.2.25 Aug 4, 2014 at 3:56:00 PM
 */
public class Cast extends Node
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Cast(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Decode the given statement into a {@link Cast} instance, if
	 * possible. If it is not possible, this method returns null.<br>
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li></li>
	 * 	<li></li>
	 * 	<li></li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		{@link Cast} instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a {@link Cast}.
	 */
	public static Cast decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (statement.charAt(0) == '(')
		{
			Cast   n      = new Cast(parent, location);
			Bounds bounds = SyntaxUtils.findInnerParenthesesBounds(n, statement);
			
			String contents = n.extractContents(statement, bounds);
			
			if (n.decodeType(contents))
			{
				return n;
			}
		}
		
		return null;
	}
	
	private String extractContents(String statement, Bounds bounds)
	{
		String contents = bounds.extractString(statement);
		
		if (StringUtils.containsMultipleWords(contents))
		{
			invalidTypeError(contents);
		}
		
		return contents;
	}
	
	private boolean decodeType(String contents)
	{
		String type = StringUtils.findNextWord(contents);
		
		if (type == null)
		{
			invalidTypeError(type);
		}
		
		int index = contents.indexOf(type);
		
		// If symbols are before the type... can't have that.
		if (index > 0)
		{
			invalidTypeError(contents);
		}
		
		
		
		return true;
	}
	
	private void invalidTypeError(String type)
	{
		SyntaxMessage.error("Cannot cast to invalid type '" + type + "'", this);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Cast clone(Node temporaryParent, Location locationIn)
	{
		Cast node = new Cast(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link Cast} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Cast cloneTo(Cast node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the {@link Cast} class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test()
	{
		
		
		return null;
	}
}
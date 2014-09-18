package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;
import net.fathomsoft.nova.util.StringUtils;

/**
 * Node extension that represents an external type of variable or
 * method call.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2.4 May 8, 2014 at 6:55:51 PM
 * @version	v0.2.29 Aug 29, 2014 at 3:17:45 PM
 */
public class ExternalType extends IValue
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public ExternalType(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Decode the given statement into a ExternalType instance, if
	 * possible. If it is not possible, this method returns null.
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ExternalType instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ExternalType.
	 */
	public static ExternalType decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		Bounds bounds = Regex.boundsOf(statement, Patterns.EXTERNAL_TYPE);
		
		if (bounds.getStart() == 0)
		{
			ExternalType n = new ExternalType(parent, location);
			
			int start = StringUtils.findNextNonWhitespaceIndex(statement, bounds.getEnd());
			
			if (start < 0)
			{
				SyntaxMessage.error("Unfinished external type declaration", n);
			}
			
			String type = statement.substring(start);
			
			if (StringUtils.findNextWhitespaceIndex(type, 0) > 0)
			{
				SyntaxMessage.error("Could not decode type declaration '" + type + "'", n);
			}
			
			n.setType(type);
			
			return n;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public ExternalType clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		ExternalType node = new ExternalType(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given {@link ExternalType} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ExternalType cloneTo(ExternalType node)
	{
		super.cloneTo(node);
		
		return node;
	}
	
	/**
	 * Test the ExternalType class type to make sure everything
	 * is working properly.
	 * 
	 * @return The error output, if there was an error. If the test was
	 * 		successful, null is returned.
	 */
	public static String test(TestContext context)
	{
		
		
		return null;
	}
}
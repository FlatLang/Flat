package net.fathomsoft.nova.tree.exceptionhandling;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.Identifier;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * ExceptionHandlingNode extension that represents the declaration of a
 * throw node type. See {@link #decodeStatement(Node, String, Location, boolean, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 22, 2014 at 11:02:52 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class Throw extends ExceptionHandler
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Throw(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get the ExceptionNode that contains the information about the type
	 * of exception that was thrown.
	 * 
	 * @return The ExceptionNode instance that contains the information
	 * 		about the exception type.
	 */
	public Exception getException()
	{
		return (Exception)getChild(1);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("THROW").append('(').append(getException().getID()).append(')').append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * Decode the given statement into a ThrowNode instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>throw new IOException()</li>
	 * 	<li>throw exceptionInstance;</li>
	 * 	<li>throw new IllegalArgumentException()</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ThrowNode instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ThrowNode.
	 */
	public static Throw decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		if (Regex.startsWith(statement, Patterns.PRE_THROW))
		{
			Bounds bounds = Regex.boundsOf(statement, Patterns.POST_THROW);
			
			Throw n   = new Throw(parent, location);
				
			if (bounds.getStart() > 0)
			{
				Location  newLoc     = new Location(location);
				newLoc.setLineNumber(location.getLineNumber());
				newLoc.setBounds(location.getStart() + bounds.getStart(), location.getStart() + bounds.getEnd());
				
				String    thrown     = statement.substring(bounds.getStart(), bounds.getEnd());
				
				Node  thrownNode = SyntaxTree.decodeScopeContents(parent, thrown, newLoc, require, false);
				
				if (thrownNode instanceof Identifier)
				{
					Identifier node = (Identifier)thrownNode;
					
					Exception exception = new Exception(n, newLoc);
					exception.setType(node.getName());
					
					n.addChild(exception, n);
					
					return n;
				}
				
				SyntaxMessage.error("Incorrect form of exception thrown", n);
			}
			
			SyntaxMessage.error("Throw statement missing exception type", n);
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Throw clone(Node temporaryParent, Location locationIn)
	{
		Throw node = new Throw(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ThrowNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Throw cloneTo(Throw node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
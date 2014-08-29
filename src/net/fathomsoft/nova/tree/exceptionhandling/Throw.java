package net.fathomsoft.nova.tree.exceptionhandling;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.Identifier;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * ExceptionHandler extension that represents the declaration of a
 * throw node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 22, 2014 at 11:02:52 PM
 * @version	v0.2.26 Aug 6, 2014 at 2:48:50 PM
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
	 * @see net.fathomsoft.nova.tree.Node#getNumDecodedChildren()
	 */
	@Override
	public int getNumDecodedChildren()
	{
		return super.getNumDecodedChildren() + 1;
	}
	
	/**
	 * Get the Exception that contains the information about the type
	 * of exception that was thrown.
	 * 
	 * @return The Exception instance that contains the information
	 * 		about the exception type.
	 */
	public Exception getException()
	{
		return (Exception)getChild(1);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource(StringBuilder)
	 */
	@Override
	public StringBuilder generateCSource(StringBuilder builder)
	{
		return builder.append("THROW").append('(').append(getException().getID()).append(')').append(';').append('\n');
	}
	
	/**
	 * Decode the given statement into a Throw instance, if
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
	 * 		Throw instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a Throw.
	 */
	public static Throw decodeStatement(Node parent, String statement, Location location, boolean require)
	{
		if (Regex.startsWith(statement, Patterns.PRE_THROW))
		{
			Throw    n      = new Throw(parent, location);
			Location newLoc = location.asNew();
			
			Bounds   bounds = n.calculateThrowContents(statement);
			String   thrown = statement.substring(bounds.getStart(), bounds.getEnd());
			
			newLoc.addBounds(bounds.getStart(), bounds.getEnd());
			
			if (n.decodeThrownNode(thrown, newLoc, require))
			{
				return n;
			}
		}
		
		return null;
	}
	
	/**
	 * Calculate the Bounds that contain the contents that the Throw is
	 * throwing.
	 * 
	 * @param statement The statement containing the data.
	 * @return The Bounds of the contents of the Throw.
	 */
	private Bounds calculateThrowContents(String statement)
	{
		Bounds bounds = Regex.boundsOf(statement, Patterns.POST_THROW);
		
		if (!bounds.isValid())
		{
			SyntaxMessage.error("Throw statement missing exception type", this);
		}
		
		return bounds;
	}
	
	/**
	 * Decode the identifier that is being thrown by the Throw statement.
	 * 
	 * @param thrown The data representing what is being thrown.
	 * @param location The location that the data is within the source
	 * 		code.
	 * @param require Whether or not to throw an error if anything goes
	 * 		wrong.
	 * @return Whether or not the data was decoded successfully.
	 */
	private boolean decodeThrownNode(String thrown, Location location, boolean require)
	{
		Node thrownNode = SyntaxTree.decodeScopeContents(this, thrown, location, require);
		
		if (!(thrownNode instanceof Identifier))
		{
			SyntaxMessage.error("Incorrect form of exception thrown", this);
		}
		
		Identifier node      = (Identifier)thrownNode;
		
		Exception  exception = new Exception(this, location);
		exception.setType(node.getName());
		
		addChild(exception, this);
		
		return true;
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
	 * Fill the given {@link Throw} with the data that is in the
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
	
	/**
	 * Test the Throw class type to make sure everything
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
package net.fathomsoft.nova.tree.exceptionhandling;

import net.fathomsoft.nova.TestContext;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.Identifier;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.tree.Scope;
import net.fathomsoft.nova.tree.SyntaxTree;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.StringUtils;

/**
 * ExceptionHandler extension that represents the declaration of a
 * throw node type. See {@link #decodeStatement(Node, String, Location, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 22, 2014 at 11:02:52 PM
 * @version	v0.2.41 Dec 17, 2014 at 7:48:17 PM
 */
public class Throw extends ExceptionHandler
{
	public static final String IDENTIFIER = "throw";
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#Node(Node, Location)
	 */
	public Throw(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	@Override
	public Scope getScope()
	{
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#getNumDecodedChildren()
	 */
	@Override
	public int getNumDecodedChildren()
	{
		return super.getNumDecodedChildren() + 2;
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
		return (Exception)getChild(super.getNumDefaultChildren() + 0);
	}
	
	/**
	 * Get the Exception Object Instance that is being thrown by the
	 * specified Throw instance.
	 * 
	 * @return The Exception Object Instance that is being thrown.
	 */
	public Identifier getExceptionInstance()
	{
		return (Identifier)getChild(super.getNumDefaultChildren() + 1);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateNovaInput(java.lang.StringBuilder, boolean)
	 */
	@Override
	public StringBuilder generateNovaInput(StringBuilder builder, boolean outputChildren)
	{
		return builder.append(IDENTIFIER).append(" ").append(getExceptionInstance().generateNovaInput());
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
		if (StringUtils.startsWithWord(statement, IDENTIFIER))
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
		Bounds bounds = new Bounds(StringUtils.findNextNonWhitespaceIndex(statement, IDENTIFIER.length() + 1), statement.length());
		
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
			SyntaxTree.decodeScopeContents(this, thrown, location, require);
			SyntaxMessage.error("Incorrect form of exception thrown", this);
		}
		
		Identifier node      = (Identifier)thrownNode;
		Exception  exception = new Exception(this, location);
		exception.setType(node.getName());
		
		addChild(exception, this);
		addChild(node, this);
		
		return true;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location, boolean)
	 */
	@Override
	public Throw clone(Node temporaryParent, Location locationIn, boolean cloneChildren)
	{
		Throw node = new Throw(temporaryParent, locationIn);
		
		return cloneTo(node, cloneChildren);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#cloneTo(Node)
	 */
	public Throw cloneTo(Throw node)
	{
		return cloneTo(node, true);
	}
	
	/**
	 * Fill the given {@link Throw} with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Throw cloneTo(Throw node, boolean cloneChildren)
	{
		super.cloneTo(node, cloneChildren);
		
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
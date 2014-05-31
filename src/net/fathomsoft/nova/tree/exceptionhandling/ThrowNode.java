package net.fathomsoft.nova.tree.exceptionhandling;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.IdentifierNode;
import net.fathomsoft.nova.tree.TreeNode;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * ExceptionHandlingNode extension that represents the declaration of a
 * throw node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 22, 2014 at 11:02:52 PM
 * @version	v0.2.11 May 31, 2014 at 1:19:11 PM
 */
public class ThrowNode extends ExceptionHandlingNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public ThrowNode(TreeNode temporaryParent, Location locationIn)
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
	public ExceptionNode getException()
	{
		return (ExceptionNode)getChild(1);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
		if (child instanceof ExceptionNode)
		{
			addChild(1, child);
		}
		else
		{
			super.addChild(child);
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("THROW").append('(').append(getException().getID()).append(')').append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return null;
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
	 * @return The generated node, if it was possible to translated it
	 * 		into a ThrowNode.
	 */
	public static ThrowNode decodeStatement(TreeNode parent, String statement, Location location, boolean require)
	{
		if (Regex.startsWith(statement, Patterns.PRE_THROW))
		{
			Bounds bounds = Regex.boundsOf(statement, Patterns.POST_THROW);
			
			ThrowNode n   = new ThrowNode(parent, location);
				
			if (bounds.getStart() > 0)
			{
				Location  newLoc     = new Location(location);
				newLoc.setBounds(location.getStart() + bounds.getStart(), location.getStart() + bounds.getEnd());
				
				String    thrown     = statement.substring(bounds.getStart(), bounds.getEnd());
				
				TreeNode  thrownNode = TreeNode.decodeStatement(parent, thrown, newLoc, require);
				
				if (thrownNode instanceof IdentifierNode)
				{
					IdentifierNode node = (IdentifierNode)thrownNode;
					
					ExceptionNode exception = new ExceptionNode(n, newLoc);
					exception.setType(node.getName());
					
					n.addChild(exception);
					
					return n;
				}
				
				SyntaxMessage.error("Incorrect form of exception thrown", n, newLoc);
			}
			
			SyntaxMessage.error("Throw statement missing exception type", n);
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public ThrowNode clone(TreeNode temporaryParent, Location locationIn)
	{
		ThrowNode node = new ThrowNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ThrowNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ThrowNode cloneTo(ThrowNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
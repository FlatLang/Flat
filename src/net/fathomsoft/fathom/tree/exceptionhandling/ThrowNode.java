package net.fathomsoft.fathom.tree.exceptionhandling;

import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.tree.IdentifierNode;
import net.fathomsoft.fathom.tree.InstantiationNode;
import net.fathomsoft.fathom.tree.ScopeNode;
import net.fathomsoft.fathom.tree.TreeNode;
import net.fathomsoft.fathom.tree.variables.LocalVariableNode;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;

/**
 * ExceptionHandlingNode extension that represents the declaration of a
 * throw node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 22, 2014 at 11:02:52 PM
 */
public class ThrowNode extends ExceptionHandlingNode
{
	public ExceptionNode getException()
	{
		return (ExceptionNode)getChild(1);
	}
	
//	public ExceptionNode getExceptionInstance()
//	{
//		return (ExceptionNode)getChild(1);
//	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
		if (child instanceof ExceptionNode)
		{
			getChildren().add(child);
		}
		else
		{
			super.addChild(child);
		}
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		return null;
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
		
		builder.append("THROW").append('(').append(getException().getID()).append(')').append(';').append('\n');
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
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
	 * @return The generated node, if it was possible to translated it
	 * 		into a ThrowNode.
	 */
	public static ThrowNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		if (Regex.matches(statement, 0, Patterns.PRE_THROW))
		{
			Bounds bounds = Regex.boundsOf(statement, Patterns.POST_THROW);
			
			if (bounds.getStart() > 0)
			{
				ThrowNode n          = new ThrowNode();
				
				Location  newLoc     = new Location(location.getLineNumber(), location.getOffset() + bounds.getStart());
				
				String    thrown     = statement.substring(bounds.getStart(), bounds.getEnd());
				
				TreeNode  thrownNode = TreeNode.decodeStatement(parent, thrown, newLoc);
				
				if (thrownNode instanceof IdentifierNode)
				{
					IdentifierNode node = (IdentifierNode)thrownNode;
					
					ExceptionNode exception = new ExceptionNode();
					exception.setType(node.getName());
					
					n.addChild(exception);
					
					return n;
				}
				
				SyntaxMessage.error("Incorrect form of exception thrown", newLoc);
				
				return null;
			}
			
			SyntaxMessage.error("Throw statement missing exception type", location);
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public ThrowNode clone()
	{
		ThrowNode node = new ThrowNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given ThrowNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ThrowNode clone(ThrowNode node)
	{
		super.clone(node);
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			node.addChild(child.clone());
		}
		
		return node;
	}
}
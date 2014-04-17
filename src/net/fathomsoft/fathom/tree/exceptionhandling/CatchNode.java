package net.fathomsoft.fathom.tree.exceptionhandling;

import net.fathomsoft.fathom.error.SyntaxMessage;
import net.fathomsoft.fathom.tree.TreeNode;
import net.fathomsoft.fathom.tree.variables.LocalVariableNode;
import net.fathomsoft.fathom.util.Bounds;
import net.fathomsoft.fathom.util.Location;
import net.fathomsoft.fathom.util.Patterns;
import net.fathomsoft.fathom.util.Regex;

/**
 * ExceptionHandlingNode extension that represents the declaration of a
 * catch node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 22, 2014 at 4:01:44 PM
 * @version	v0.2 Apr 7, 2014 at 8:23:51 PM
 */
public class CatchNode extends ExceptionHandlingNode
{
	/**
	 * Get the instance of the Exception variable that is being caught.
	 * 
	 * @return The Exception variable instance that is being caught.
	 */
	public LocalVariableNode getExceptionInstance()
	{
		return (LocalVariableNode)getChild(1);
	}
	
	/**
	 * Get the ExceptionNode that is being caught by this node.
	 * 
	 * @return The ExceptionNode instance.
	 */
	public ExceptionNode getException()
	{
		return (ExceptionNode)getChild(2);
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
		if (child instanceof ExceptionNode || getChildren().size() <= 1)
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
		
		builder.append("CATCH ").append('(').append(getException().getID()).append(')').append('\n');
		builder.append('{').append('\n');
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != getExceptionInstance() && child != getException())
			{
				builder.append(child.generateCSourceOutput());
			}
		}
		
		builder.append('}').append('\n');
		
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
	 * Get the TryNode that this CatchNode is referring to.
	 * 
	 * @param parent The parent node of this CatchNode.
	 * @return The TryNode instance that this CatchNode is referring to.
	 */
	private TryNode getCurrentTry(TreeNode parent)
	{
		if (parent.containsScope())
		{
			parent = parent.getScopeNode();
		}
		
		for (int i = parent.getChildren().size() - 1; i >= 0; i--)
		{
			TreeNode child = parent.getChild(i);
			
			if (child instanceof TryNode)
			{
				return (TryNode)child;
			}
			else if (child instanceof CatchNode == false)
			{
				return null;
			}
		}
		
		return null;
	}
	
	/**
	 * Decode the given statement into a CatchNode instance, if
	 * possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>catch (Exception e)</li>
	 * 	<li>catch (DivideByZeroException e)</li>
	 * 	<li>catch (IOException e)</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		CatchNode instance.
	 * @param location The location of the statement in the source code.
	 * @return The generated node, if it was possible to translated it
	 * 		into a CatchNode.
	 */
	public static CatchNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		if (Regex.matches(statement, 0, Patterns.PRE_CATCH))
		{
			CatchNode n = new CatchNode();
			
			Bounds bounds = Regex.boundsOf(statement, Patterns.POST_CATCH);
			
			if (bounds.getStart() >= 0)
			{
				String contents = statement.substring(bounds.getStart(), bounds.getEnd());
				
				Location newLoc = new Location();
				newLoc.setLineNumber(location.getLineNumber());
				newLoc.setOffset(location.getOffset() + bounds.getStart());
				
				LocalVariableNode exceptionInstance = LocalVariableNode.decodeStatement(parent, contents, newLoc);
				
				if (exceptionInstance != null)
				{
					n.addChild(exceptionInstance);
					
					ExceptionNode exception = new ExceptionNode();
					exception.setType(exceptionInstance.getType());
					
					if (exception.getID() > 0)
					{
						n.addChild(exception);
						
						n.getCurrentTry(parent).addExceptionCode(exception.getID());
						
						return n;
					}
					
					SyntaxMessage.error("Unknown exception type", newLoc);
				}
				
				SyntaxMessage.error("Incorrect Exception declaration", newLoc);
			}
			else
			{
				SyntaxMessage.error("Catch declaration missing Exception type", location);
			}
		}
		
		return null;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public CatchNode clone()
	{
		CatchNode node = new CatchNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given CatchNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public CatchNode clone(CatchNode node)
	{
		super.clone(node);
		
		return node;
	}
}
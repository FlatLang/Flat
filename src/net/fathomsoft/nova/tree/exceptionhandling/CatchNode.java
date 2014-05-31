package net.fathomsoft.nova.tree.exceptionhandling;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.LocalDeclarationNode;
import net.fathomsoft.nova.tree.TreeNode;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * ExceptionHandlingNode extension that represents the declaration of a
 * catch node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 22, 2014 at 4:01:44 PM
 * @version	v0.2.11 May 31, 2014 at 1:19:11 PM
 */
public class CatchNode extends ExceptionHandlingNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public CatchNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}

	/**
	 * Get the instance of the Exception variable that is being caught.
	 * 
	 * @return The Exception variable instance that is being caught.
	 */
	public LocalDeclarationNode getExceptionInstance()
	{
		return (LocalDeclarationNode)getChild(2);
	}
	
	/**
	 * Get the ExceptionNode that is being caught by this node.
	 * 
	 * @return The ExceptionNode instance.
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
		if (getChildren().size() == 1 || getChildren().size() == 2)
		{
			addChild(1, child);
		}
		else
		{
			super.addChild(child);
		}
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("CATCH ").append('(').append(getException().getID()).append(')').append('\n');
		builder.append('{').append('\n');
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child != getExceptionInstance() && child != getException())
			{
				builder.append(child.generateCSource());
			}
		}
		
		builder.append('}').append('\n');
		
		return builder.toString();
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
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @return The generated node, if it was possible to translated it
	 * 		into a CatchNode.
	 */
	public static CatchNode decodeStatement(TreeNode parent, String statement, Location location, boolean require)
	{
		if (Regex.matches(statement, 0, Patterns.PRE_CATCH))
		{
			CatchNode n = new CatchNode(parent, location);
			
			Bounds bounds = Regex.boundsOf(statement, Patterns.POST_CATCH);
			
			if (bounds.getStart() >= 0)
			{
				String contents = statement.substring(bounds.getStart(), bounds.getEnd());
				
				Location newLoc = new Location();
				newLoc.setLineNumber(location.getLineNumber());
				newLoc.setBounds(location.getStart() + bounds.getStart(), location.getStart() + bounds.getEnd());
				
				LocalDeclarationNode exceptionInstance = LocalDeclarationNode.decodeStatement(parent, contents, newLoc, require);
				
				if (exceptionInstance != null)
				{
					n.addChild(exceptionInstance);
					
					ExceptionNode exception = new ExceptionNode(n, newLoc);
					exception.setType(exceptionInstance.getType());
					
					if (exception.getID() > 0)
					{
						n.addChild(exception);
						
						TryNode tryNode = n.getCurrentTry(parent);
						
						if (tryNode == null)
						{
							SyntaxMessage.error("Parent try block not found", exceptionInstance);
						}
						
						tryNode.addExceptionCode(exception.getID());
						
						return n;
					}
					
					SyntaxMessage.error("Unknown exception type", exceptionInstance);
				}
				
				SyntaxMessage.error("Incorrect Exception declaration", n, newLoc);
			}
			else
			{
				SyntaxMessage.error("Catch declaration missing Exception type", n);
			}
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public CatchNode clone(TreeNode temporaryParent, Location locationIn)
	{
		CatchNode node = new CatchNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given CatchNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public CatchNode cloneTo(CatchNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
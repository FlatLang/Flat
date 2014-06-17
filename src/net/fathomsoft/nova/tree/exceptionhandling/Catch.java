package net.fathomsoft.nova.tree.exceptionhandling;

import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.LocalDeclaration;
import net.fathomsoft.nova.tree.Node;
import net.fathomsoft.nova.util.Bounds;
import net.fathomsoft.nova.util.Location;
import net.fathomsoft.nova.util.Patterns;
import net.fathomsoft.nova.util.Regex;

/**
 * ExceptionHandlingNode extension that represents the declaration of a
 * catch node type. See {@link #decodeStatement(Node, String, Location, boolean, boolean)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 22, 2014 at 4:01:44 PM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class Catch extends ExceptionHandler
{
	/**
	 * @see net.fathomsoft.nova.tree.Node#TreeNode(Node, Location)
	 */
	public Catch(Node temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}

	/**
	 * Get the instance of the Exception variable that is being caught.
	 * 
	 * @return The Exception variable instance that is being caught.
	 */
	public LocalDeclaration getExceptionInstance()
	{
		return (LocalDeclaration)getChild(1);
	}
	
	/**
	 * Get the ExceptionNode that is being caught by this node.
	 * 
	 * @return The ExceptionNode instance.
	 */
	public Exception getException()
	{
		return (Exception)getChild(2);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.Node#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("CATCH ").append('(').append(getException().getID()).append(')').append('\n');
		builder.append('{').append('\n');
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			Node child = getChild(i);
			
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
	private TryNode getCurrentTry(Node parent)
	{
		if (parent.containsScope())
		{
			parent = parent.getScopeNode();
		}
		
		for (int i = parent.getNumChildren() - 1; i >= 0; i--)
		{
			Node child = parent.getChild(i);
			
			if (child instanceof TryNode)
			{
				return (TryNode)child;
			}
			else if (child instanceof Catch == false)
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
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a CatchNode.
	 */
	public static Catch decodeStatement(Node parent, String statement, Location location, boolean require, boolean scope)
	{
		if (Regex.matches(statement, 0, Patterns.PRE_CATCH))
		{
			Catch n = new Catch(parent, location);
			
			Bounds bounds = Regex.boundsOf(statement, Patterns.POST_CATCH);
			
			if (bounds.getStart() >= 0)
			{
				String contents = statement.substring(bounds.getStart(), bounds.getEnd());
				
				Location newLoc = new Location();
				newLoc.setLineNumber(location.getLineNumber());
				newLoc.setBounds(location.getStart() + bounds.getStart(), location.getStart() + bounds.getEnd());
				
				LocalDeclaration exceptionInstance = LocalDeclaration.decodeStatement(parent, contents, newLoc, require, false);
				
				if (exceptionInstance != null)
				{
					n.addChild(exceptionInstance, n);
					
					Exception exception = new Exception(n, newLoc);
					exception.setType(exceptionInstance.getType());
					
					if (exception.getID() > 0)
					{
						n.addChild(exception, n);
						
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
	 * @see net.fathomsoft.nova.tree.Node#clone(Node, Location)
	 */
	@Override
	public Catch clone(Node temporaryParent, Location locationIn)
	{
		Catch node = new Catch(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given CatchNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public Catch cloneTo(Catch node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
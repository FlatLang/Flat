package net.fathomsoft.nova.tree.exceptionhandling;

import net.fathomsoft.nova.tree.ScopeNode;
import net.fathomsoft.nova.tree.TreeNode;
import net.fathomsoft.nova.util.Location;

/**
 * TreeNode extension that represents the declaration of an exception
 * handling node type. See {@link #decodeStatement(TreeNode, String, Location)}
 * for more details on what correct inputs look like.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Mar 21, 2014 at 10:50:26 PM
 * @version	v0.2.11 May 31, 2014 at 1:19:11 PM
 */
public class ExceptionHandlingNode extends TreeNode
{
	/**
	 * Instantiate and initialize default values.
	 */
	public ExceptionHandlingNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
		
		ScopeNode scopeNode = new ScopeNode(this, locationIn);
		
		addChild(scopeNode);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#getScopeNode()
	 */
	@Override
	public ScopeNode getScopeNode()
	{
		return (ScopeNode)getChild(0);
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
		if (child instanceof ScopeNode)
		{
			super.addChild(child);
		}
		else
		{
			getScopeNode().addChild(child);
		}
	}
	
	/**
	 * Decode the given statement into a ExceptionHandlingNode instance,
	 * if possible. If it is not possible, this method returns null.
	 * <br>
	 * Example inputs include:<br>
	 * <ul>
	 * 	<li>try</li>
	 * 	<li>catch (Exception e)</li>
	 * 	<li>finally</li>
	 * 	<li>throw new IOException()</li>
	 * </ul>
	 * 
	 * @param parent The parent node of the statement.
	 * @param statement The statement to try to decode into a
	 * 		ExceptionHandlingNode instance.
	 * @param location The location of the statement in the source code.
	 * @param require Whether or not to throw an error if anything goes wrong.
	 * @param scope Whether or not the given statement is the beginning of
	 * 		a scope.
	 * @return The generated node, if it was possible to translated it
	 * 		into a ExceptionHandlingNode.
	 */
	public static ExceptionHandlingNode decodeStatement(TreeNode parent, String statement, Location location, boolean require, boolean scope)
	{
		ExceptionHandlingNode node = null;
		
		if ((node = TryNode.decodeStatement(parent, statement, location, require, scope)) != null)
		{
			return node;
		}
		else if ((node = CatchNode.decodeStatement(parent, statement, location, require, scope)) != null)
		{
			return node;
		}
		else if ((node = FinallyNode.decodeStatement(parent, statement, location, require, scope)) != null)
		{
			return node;
		}
		else if ((node = ThrowNode.decodeStatement(parent, statement, location, require, scope)) != null)
		{
			return node;
		}
		
		return null;
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public ExceptionHandlingNode clone(TreeNode temporaryParent, Location locationIn)
	{
		ExceptionHandlingNode node = new ExceptionHandlingNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ExceptionHandlingNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ExceptionHandlingNode cloneTo(ExceptionHandlingNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
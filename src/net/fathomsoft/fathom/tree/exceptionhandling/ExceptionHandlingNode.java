package net.fathomsoft.fathom.tree.exceptionhandling;

import net.fathomsoft.fathom.tree.ScopeNode;
import net.fathomsoft.fathom.tree.TreeNode;
import net.fathomsoft.fathom.util.Location;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Mar 21, 2014 at 10:50:26 PM
 * @since	v
 * @version	Mar 21, 2014 at 10:50:26 PM
 * @version	v
 */
public class ExceptionHandlingNode extends TreeNode
{
	public ExceptionHandlingNode()
	{
		ScopeNode scopeNode = new ScopeNode();
		
		addChild(scopeNode);
	}
	
	public ScopeNode getScopeNode()
	{
		return (ScopeNode)getChild(0);
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#addChild(TreeNode)
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
		return null;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return null;
	}
	
	public static ExceptionHandlingNode decodeStatement(TreeNode parent, String statement, Location location)
	{
		ExceptionHandlingNode node = null;
		
		if ((node = TryNode.decodeStatement(parent, statement, location)) != null)
		{
			return node;
		}
		else if ((node = CatchNode.decodeStatement(parent, statement, location)) != null)
		{
			return node;
		}
		else if ((node = FinallyNode.decodeStatement(parent, statement, location)) != null)
		{
			return node;
		}
		else if ((node = ThrowNode.decodeStatement(parent, statement, location)) != null)
		{
			return node;
		}
		
		return null;
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public ExceptionHandlingNode clone()
	{
		ExceptionHandlingNode node = new ExceptionHandlingNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given ExceptionHandlingNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ExceptionHandlingNode clone(ExceptionHandlingNode node)
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			node.addChild(child.clone());
		}
		
		return node;
	}
}
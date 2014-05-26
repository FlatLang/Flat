package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.tree.exceptionhandling.ExceptionNode;

/**
 * TreeNode extension that keeps track of all of the arguments that
 * are passed during a method call. The children of this node are
 * all ArgumentNode instances. They are stored in the order that
 * they will be passed to the method call.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 10, 2014 at 3:12:54 AM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class ArgumentListNode extends TreeNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode)
	 */
	public ArgumentListNode(TreeNode temporaryParent)
	{
		super(temporaryParent);
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateJavaSource());
			
			if (i < getChildren().size() - 1)
			{
				builder.append(", ");
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * Get the MethodCallNode instance that contains the specified
	 * arguments.
	 * 
	 * @return The parent MethodCallNode instance.
	 */
	public MethodCallNode getMethodCall()
	{
		return (MethodCallNode)getParent();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateNovaInput()
	 */
	@Override
	public String generateNovaInput()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append(child.generateNovaInput());
		}
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder  builder = new StringBuilder();
		
		MethodCallNode caller  = getMethodCall();
		
		MethodNode     method  = caller.getMethodNode();
		
		if (!caller.isExternal())
		{
			if (method.needsReference())
			{
				if (method instanceof DestructorNode)
				{
					builder.append('&');
				}
				
				builder.append(caller.getContextNode().generateArgumentReference()).append(", ");
			}
			
			builder.append(ExceptionNode.EXCEPTION_DATA_IDENTIFIER);
			
			if (getChildren().size() > 0)
			{
				builder.append(", ");
			}
		}
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateCSourceFragment());
			
			if (i < getChildren().size() - 1)
			{
				builder.append(", ");
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public ArgumentListNode clone(TreeNode temporaryParent)
	{
		ArgumentListNode node = new ArgumentListNode(temporaryParent);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given ArgumentListNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ArgumentListNode cloneTo(ArgumentListNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
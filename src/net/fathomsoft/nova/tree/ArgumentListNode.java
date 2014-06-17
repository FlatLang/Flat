package net.fathomsoft.nova.tree;

import net.fathomsoft.nova.tree.exceptionhandling.ExceptionNode;
import net.fathomsoft.nova.util.Location;

/**
 * TreeNode extension that keeps track of all of the arguments that
 * are passed during a method call. The children of this node are
 * all ArgumentNode instances. They are stored in the order that
 * they will be passed to the method call.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 10, 2014 at 3:12:54 AM
 * @version	v0.2.13 Jun 17, 2014 at 8:45:35 AM
 */
public class ArgumentListNode extends TreeNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public ArgumentListNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			builder.append(getChild(i).generateJavaSource());
			
			if (i < getNumChildren() - 1)
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
	 * @see net.fathomsoft.nova.tree.TreeNode#generateNovaInput(boolean)
	 */
	@Override
	public String generateNovaInput(boolean outputChildren)
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			TreeNode child = getChild(i);
			
			if (i > 0)
			{
				builder.append(", ");
			}
			
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
		
		MethodNode     method  = caller.getMethodDeclarationNode();
		
		if (!caller.isExternal())
		{
			if (method.needsReference())
			{
				if (method instanceof DestructorNode)
				{
					builder.append('&');
				}
				
				IdentifierNode context = caller.getContextNode();
				
				builder.append(context.generateArgumentReference(getMethodCall())).append(", ");
			}
			
			builder.append(ExceptionNode.EXCEPTION_DATA_IDENTIFIER);
			
			if (getNumChildren() > 0)
			{
				builder.append(", ");
			}
		}
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			builder.append(getChild(i).generateCSourceFragment());
			
			if (i < getNumChildren() - 1)
			{
				builder.append(", ");
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode, Location)
	 */
	@Override
	public ArgumentListNode clone(TreeNode temporaryParent, Location locationIn)
	{
		ArgumentListNode node = new ArgumentListNode(temporaryParent, locationIn);
		
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
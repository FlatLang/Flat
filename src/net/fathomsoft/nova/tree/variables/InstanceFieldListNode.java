package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.tree.ClassNode;
import net.fathomsoft.nova.tree.TreeNode;

/**
 * TreeNode extensions that contains all of the public FieldNode
 * instances of a ClassNode.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 5, 2014 at 10:00:50 PM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class InstanceFieldListNode extends TreeNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode)
	 */
	public InstanceFieldListNode(TreeNode temporaryParent)
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
		}
		
		if (getChildren().size() > 0)
		{
			ClassNode parent = (ClassNode)getAncestorOfType(ClassNode.class, true);
			
			if (parent.getMethodListNode().getChildren().size() > 0)
			{
				builder.append('\n');
			}
		}
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		StringBuilder builder = new StringBuilder();
		
		ClassNode clazz = (ClassNode)getAncestorOfType(ClassNode.class);
		
		ClassNode extended = clazz.getExtendedClass();
		
		if (extended != null)
		{
			boolean publicList = this == clazz.getFieldListNode().getPublicFieldListNode();
			
			InstanceFieldListNode fields = null;
			
			if (publicList)
			{
				fields = extended.getFieldListNode().getPublicFieldListNode();
			}
			else
			{
				fields = extended.getFieldListNode().getPrivateFieldListNode();
			}
			
			builder.append(fields.generateCHeader());
		}
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateCHeader());
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		boolean hasMethods    = false;
		
		if (getChildren().size() > 0)
		{
			ClassNode parent = (ClassNode)getAncestorOfType(ClassNode.class, true);
			
			if (parent.getMethodListNode().getChildren().size() > 0)
			{
				hasMethods = true;
			}
		}
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateCSource());
		}
		
		if (hasMethods)
		{
			builder.append('\n');
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public InstanceFieldListNode clone(TreeNode temporaryParent)
	{
		InstanceFieldListNode node = new InstanceFieldListNode(temporaryParent);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given PublicFieldListNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public InstanceFieldListNode cloneTo(InstanceFieldListNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}
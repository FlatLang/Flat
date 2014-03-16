package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.error.SyntaxMessage;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 10:00:50 PM
 * @since	v
 * @version	Jan 5, 2014 at 10:00:50 PM
 * @version	v
 */
public class FieldListNode extends TreeNode
{
	public FieldListNode()
	{
		PrivateFieldListNode privateFields = new PrivateFieldListNode();
		PublicFieldListNode  publicFields  = new PublicFieldListNode();
		
		addChild(privateFields);
		addChild(publicFields);
	}
	
	public PrivateFieldListNode getPrivateFieldListNode()
	{
		return (PrivateFieldListNode)getChild(0);
	}
	
	public PublicFieldListNode getPublicFieldListNode()
	{
		return (PublicFieldListNode)getChild(1);
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#addChild(TreeNode node)
	 */
	@Override
	public void addChild(TreeNode node)
	{
		if (node instanceof FieldNode)
		{
			FieldNode field = (FieldNode)node;
			
			if (field.getVisibility() == DeclarationNode.PRIVATE)
			{
				getPrivateFieldListNode().addChild(field);
			}
			else if (field.getVisibility() == DeclarationNode.PUBLIC)
			{
				getPublicFieldListNode().addChild(field);
			}
			else
			{
				SyntaxMessage.error("Missing visibility declaration", field);
			}
		}
		else
		{
			super.addChild(node);
		}
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateJavaSourceOutput());
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
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateCHeaderOutput());
		}
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
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
			builder.append(getChild(i).generateCSourceOutput());
		}
		
		if (hasMethods)
		{
			builder.append('\n');
		}
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public FieldListNode clone()
	{
		FieldListNode clone = new FieldListNode();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			clone.addChild(child.clone());
		}
		
		return clone;
	}
}
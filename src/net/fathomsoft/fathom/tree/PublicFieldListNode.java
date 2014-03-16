package net.fathomsoft.fathom.tree;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 10:00:50 PM
 * @since	v
 * @version	Jan 5, 2014 at 10:00:50 PM
 * @version	v
 */
public class PublicFieldListNode extends TreeNode
{
	public PublicFieldListNode()
	{
		
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
	public PublicFieldListNode clone()
	{
		PublicFieldListNode clone = new PublicFieldListNode();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			clone.addChild(child.clone());
		}
		
		return clone;
	}
}
package net.fathomsoft.fathom.tree;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 10:29:22 PM
 * @since	v
 * @version	Jan 5, 2014 at 10:29:22 PM
 * @version	v
 */
public class MethodListNode extends TreeNode
{
	public MethodListNode()
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
			if (i > 0)
			{
				builder.append('\n');
			}
			
			builder.append(getChild(i).generateJavaSourceOutput());
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
		
		ClassNode classNode = (ClassNode)getAncestorOfType(ClassNode.class, true);
		
		if (getChildren().size() > 0 && classNode.getFieldListNode().getPublicFieldListNode().getChildren().size() <= 0)
		{
			builder.append('\n');
		}
			
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
		
		if (getChildren().size() > 0)
		{
			builder.append('\n');
		}
			
		for (int i = 0; i < getChildren().size(); i++)
		{
			if (i > 0)
			{
				builder.append('\n');
			}
			
			builder.append(getChild(i).generateCSourceOutput());
		}
		
		return builder.toString();
	}
	
	public String generateCSourcePrototypes()
	{
		StringBuilder builder = new StringBuilder();
			
		for (int i = 0; i < getChildren().size(); i++)
		{
			MethodNode child = (MethodNode)getChild(i);
			
			builder.append(child.generateCSourcePrototype()).append('\n');
		}
		
		return builder.toString();
	}
}
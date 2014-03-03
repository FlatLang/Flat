package net.fathomsoft.fathom.tree;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 5, 2014 at 9:56:34 PM
 * @since	v
 * @version	Jan 5, 2014 at 9:56:34 PM
 * @version	v
 */
public class ParameterListNode extends TreeNode
{
	public ParameterListNode()
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
			
			if (i < getChildren().size() - 1)
			{
				builder.append(", ");
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
		
		ClassNode classNode = (ClassNode)getParent().getParent().getParent();
		
		builder.append(classNode.getName()).append("* o");
		
		if (getChildren().size() > 0)
		{
			builder.append(", ");
		}
		
		builder.append(generateJavaSourceOutput());
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		ClassNode classNode = (ClassNode)getAncestorOfType(ClassNode.class, true);
		
		builder.append(classNode.getName()).append("* o");
		
		if (getChildren().size() > 0)
		{
			builder.append(", ");
		}
		
		builder.append(generateJavaSourceOutput());
		
		return builder.toString();
	}
}
package net.fathomsoft.fathom.tree;

/**
 * Node that holds the LocalVariableNodes that a method contains.
 * 
 * @author	Braden Steffaniak
 * @since	Jan 9, 2014 at 4:19:57 PM
 * @since	v0.1
 * @version	Jan 9, 2014 at 4:19:57 PM
 * @version	v0.1
 */
public class LocalVariableListNode extends TreeNode
{
	public LocalVariableListNode()
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
			builder.append(getChild(i).generateJavaSourceOutput()).append('\n');
		}
		
		if (getChildren().size() > 0)
		{
			builder.append('\n');
		}
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		return "";
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateCSourceOutput()).append('\n');
		}
		
		if (getChildren().size() > 0)
		{
			builder.append('\n');
		}
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public LocalVariableListNode clone()
	{
		LocalVariableListNode clone = new LocalVariableListNode();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			clone.addChild(child.clone());
		}
		
		return clone;
	}
}
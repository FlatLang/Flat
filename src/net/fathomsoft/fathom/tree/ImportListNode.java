package net.fathomsoft.fathom.tree;

public class ImportListNode extends TreeNode
{
	/**
	 * Get whether or not the given location has been imported.
	 * 
	 * @return Whether or not the given location has been imported.
	 */
	public boolean contains(String importLocation)
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			ImportNode child = (ImportNode)getChild(i);
			
			if (importLocation.equals(child.getImportLocation()))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Get whether or not the given location is an external C import.
	 * 
	 * @return Whether or not the given location is an external C import.
	 */
	public boolean isExternal(String importLocation)
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			ImportNode child = (ImportNode)getChild(i);
			
			if (importLocation.equals(child.getImportLocation()) && child.isCSource())
			{
				return true;
			}
		}
		
		return false;
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
		return generateCSourceOutput();
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
			builder.append(getChild(i).generateCSourceOutput());
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
	public ImportListNode clone()
	{
		ImportListNode clone = new ImportListNode();
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			clone.addChild(child.clone());
		}
		
		return clone;
	}
}

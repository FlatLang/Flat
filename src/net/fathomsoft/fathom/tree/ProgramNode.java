package net.fathomsoft.fathom.tree;

/**
 * TreeNode extension that represents a whole Fathom program. The
 * purpose of this Node is to keep track of each FileNode within
 * a compiled program and contain methods to manipulate the
 * FileNodes.
 * 
 * @author	Braden Steffaniak
 * @since	v0.2 Apr 14, 2014 at 11:52:33 PM
 * @version	v0.2 Apr 14, 2014 at 11:52:33 PM
 */
public class ProgramNode extends TreeNode
{
	/**
	 * Get the ProgramNode's ClassNode with the specified name.<br>
	 * <br>
	 * For example:
	 * <blockquote><pre>
	 * public class Person
	 * {
	 * 	
	 * 	...
	 * 	
	 * }</pre></blockquote>
	 * <br>
	 * A call like: "<code>getClass("Person")</code>" would return the
	 * ClassNode for the "<code>age</code>" int field.
	 * 
	 * @param className The name of the class to search for.
	 * @return The ClassNode for the class, if it exists.
	 */
	public ClassNode getClass(String className)
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			FileNode  node  = (FileNode)getChild(i);
			
			ClassNode clazz = node.getClass(className);
			
			if (clazz != null)
			{
				return clazz;
			}
		}
		
		return null;
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
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateCSourceOutput());
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceFragment()
	 */
	@Override
	public String generateCSourceFragment()
	{
		return null;
	}
	
	/**
	 * Format the C Header output to follow syntactical rules.
	 */
	public void formatCHeaderOutput()
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child instanceof FileNode)
			{
				FileNode fileNode = (FileNode)child;
				
				fileNode.formatCHeaderOutput();
			}
		}
	}
	
	/**
	 * Format the C Source output to follow syntactical rules.
	 */
	public void formatCSourceOutput()
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			if (child instanceof FileNode)
			{
				FileNode fileNode = (FileNode)child;
				
				fileNode.formatCSourceOutput();
			}
		}
	}
	
	/**
	 * Format the C Header and Source output to follow syntactical rules.
	 */
	public void formatCOutput()
	{
		formatCHeaderOutput();
		formatCSourceOutput();
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public ProgramNode clone()
	{
		ProgramNode node = new ProgramNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given ProgramNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ProgramNode clone(ProgramNode node)
	{
		super.clone(node);
		
		return node;
	}
}
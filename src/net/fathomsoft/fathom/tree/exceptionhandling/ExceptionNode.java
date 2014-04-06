package net.fathomsoft.fathom.tree.exceptionhandling;

import java.util.HashMap;

import net.fathomsoft.fathom.tree.TreeNode;
import net.fathomsoft.fathom.tree.variables.LocalVariableNode;

public class ExceptionNode extends TreeNode
{
	private int	id;
	
	private static int	exceptionId	= 1;
	
	private static HashMap<String, Integer>	ids;
	
	static
	{
		ids = new HashMap<String, Integer>();
		
		ids.put("Exception", exceptionId++);
	}
	
	public ExceptionNode()
	{
		
	}
	
	/**
	 * Get the ID of the Exception. The ID is needed for implementing
	 * the c version of the exception handling.
	 * 
	 * @return The ID of the Exception.
	 */
	public int getID()
	{
		return id;
	}
	
	public void setType(String type)
	{
		if (ids.containsKey(type))
		{
			id = ids.get(type);
		}
		else
		{
			id = exceptionId++;
			
			ids.put(type, id);
		}
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateJavaSourceOutput()
	 */
	@Override
	public String generateJavaSourceOutput()
	{
		return null;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCHeaderOutput()
	 */
	@Override
	public String generateCHeaderOutput()
	{
		return null;
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#generateCSourceOutput()
	 */
	@Override
	public String generateCSourceOutput()
	{
		return null;
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
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public ExceptionNode clone()
	{
		ExceptionNode node = new ExceptionNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given ExceptionNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ExceptionNode clone(ExceptionNode node)
	{
		node.id = id;
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			node.addChild(child.clone());
		}
		
		return node;
	}
}

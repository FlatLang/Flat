package net.fathomsoft.fathom.tree;

import net.fathomsoft.fathom.tree.variables.VariableListNode;
import net.fathomsoft.fathom.tree.variables.VariableNode;

/**
 * TreeNode extension that represents a scope of code. In essence, a
 * collection of statements within a pair of curly braces.<br>
 * <br>
 * For example:
 * <blockquote><pre>
 * {
 * 	...
 * 
 * 	// Statements within here...
 * 
 * 	...
 * }</pre></blockquote>
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Apr 5, 2014 at 10:54:20 PM
 * @version	v0.2 Apr 5, 2014 at 10:54:20 PM
 */
public class ScopeNode extends TreeNode
{
	/**
	 * Instantiate and initialize the default values.
	 */
	public ScopeNode()
	{
		VariableListNode variablesNode = new VariableListNode();
		
		super.addChild(variablesNode);
	}
	
	/**
	 * Get the VariableListNode that contains all of the variables
	 * that have been declared within this ScopeNode.
	 * 
	 * @return The VariableListNode instance.
	 */
	public VariableListNode getVariableListNode()
	{
		return (VariableListNode)getChild(0);
	}
	
	/**
	 * @see net.fathomsoft.fathom.tree.TreeNode#addChild(TreeNode)
	 */
	@Override
	public void addChild(TreeNode child)
	{
		if (child instanceof VariableNode)
		{
			getVariableListNode().addChild(child);
		}
		else
		{
			super.addChild(child);
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
		if (getChildren().size() <= 1 && getParent() instanceof MethodNode == false)
		{
			return "";
		}
		
		StringBuilder builder = new StringBuilder();
		
		builder.append('{').append('\n');
		
		for (int i = 0; i < getChildren().size(); i++)
		{
			builder.append(getChild(i).generateCSourceOutput());
		}
		
		builder.append('}').append('\n');
		
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
	 * @see net.fathomsoft.fathom.tree.TreeNode#clone()
	 */
	@Override
	public ScopeNode clone()
	{
		ScopeNode node = new ScopeNode();
		
		return clone(node);
	}
	
	/**
	 * Fill the given ScopeNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public ScopeNode clone(ScopeNode node)
	{
		for (int i = 0; i < getChildren().size(); i++)
		{
			TreeNode child = getChild(i);
			
			node.addChild(child.clone());
		}
		
		return node;
	}
}
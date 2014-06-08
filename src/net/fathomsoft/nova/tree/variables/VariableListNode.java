package net.fathomsoft.nova.tree.variables;

import net.fathomsoft.nova.tree.TreeNode;
import net.fathomsoft.nova.util.Location;

/**
 * Node that holds the LocalVariableNodes that a method contains.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 9, 2014 at 4:19:57 PM
 * @version	v0.2.7 May 25, 2014 at 9:16:48 PM
 */
public class VariableListNode extends TreeNode
{
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#TreeNode(TreeNode, Location)
	 */
	public VariableListNode(TreeNode temporaryParent, Location locationIn)
	{
		super(temporaryParent, locationIn);
	}
	
	/**
	 * Get whether or not there is a VaraibleNode within the list with
	 * the given name.
	 * 
	 * @param variableName The name of the variable to search for.
	 * @return Whether or not there is a VaraibleNode within the list with
	 * 		the given name.
	 */
	public boolean containsVariable(String variableName)
	{
		return getVariable(variableName) != null;
	}
	
	/**
	 * Get the VariableNode from the list with the given name, if it
	 * exists.
	 * 
	 * @param variableName The name of the variable to get.
	 * @return The VariableNode with the given name.
	 */
	public VariableNode getVariable(String variableName)
	{
		for (int i = 0; i < getNumChildren(); i++)
		{
			VariableNode variable = (VariableNode)getChild(i);
			
			if (variable.getName().equals(variableName))
			{
				return variable;
			}
		}
		
		return null;
	}
	
	/**
	 * Generate the output needed to free the variables after they are
	 * finished with.
	 * 
	 * @return The String output of the variables being freed.
	 */
	public String generateFreeVariablesOutput()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			VariableNode variable = (VariableNode)getChild(i);
			
			builder.append(variable.generateFreeOutput());
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateJavaSource()
	 */
	@Override
	public String generateJavaSource()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			builder.append(getChild(i).generateJavaSource());
		}
		
		if (getNumChildren() > 0)
		{
			builder.append('\n');
		}
		
		return builder.toString();
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCHeader()
	 */
	@Override
	public String generateCHeader()
	{
		return "";
	}

	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#generateCSource()
	 */
	@Override
	public String generateCSource()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < getNumChildren(); i++)
		{
			TreeNode child = getChild(i);
			
			builder.append(child.generateCSource());
		}
		
		if (getNumChildren() > 0)
		{
			builder.append('\n');
		}
		
		return builder.toString();
	}
	
	/**
	 * @see net.fathomsoft.nova.tree.TreeNode#clone(TreeNode)
	 */
	@Override
	public VariableListNode clone(TreeNode temporaryParent, Location locationIn)
	{
		VariableListNode node = new VariableListNode(temporaryParent, locationIn);
		
		return cloneTo(node);
	}
	
	/**
	 * Fill the given VariableListNode with the data that is in the
	 * specified node.
	 * 
	 * @param node The node to copy the data into.
	 * @return The cloned node.
	 */
	public VariableListNode cloneTo(VariableListNode node)
	{
		super.cloneTo(node);
		
		return node;
	}
}